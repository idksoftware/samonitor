package telnet;



import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;

import telnet.user.UserAccounts;


/**
 * The Server class handles all of the incomming connections, passing them to new Descriptor objects, which 
 * handle the rest. The Server class also initializes all necessary objects during bootup, and handles shutdown 
 * procedures.
 */
public final class TelnetServer extends Thread
{
	private static boolean stopping = false;
	
	
	public static UserAccounts users = new UserAccounts();
	/** Should we keep the server running (isClosed=false), or shutdown (isClosed=true)? */
	private static boolean isClosed = false;	//are we open or closed?
	
	/** Port to bind to */
	private static final int SERVICE_PORT = 5000;

	/** Timeout length, in millis - 10 minutes of inactivity */
	protected static final int TIMEOUT_LENGTH = 600000;
        
        /** The maximum number of players we will allow on at one time. Set this value to 0 if you dont 
         *  want to cap the chatters. (You would set it above 0 if you knew you had a certain amount of 
         *  resources, and didnt want to exceed them) */
        private static int MAX_CONNECTIONS = 10;
        
        /** A list of all our current connections. We put <Descriptor> in to define the Vector as a list of Descriptors.
          * Also, we initialize the vector with an argument of 1 to set the initial size of the vector to 1. From there, it
          * will automatically increase to allocate the new data. */
        protected static Vector<Descriptor> descriptorList = new Vector<Descriptor>(1);
        
        /** The number of connections we currently have. We increment this as players connect. */
        private static int connections = 0;

	/** Server Connection Object, which binds to the specified port, and listens for new connections. Instantiated in Main */
	private static ServerSocket serverSocket;
        /** Our thread pool. Instantiated in Main */
	private static ExecutorService pool;

	/** 
	 * Initializes a new Server object.
	 */
	public TelnetServer()
	{
		
		UserAccounts.Init("C:\\ProgramData\\IDK Software\\sam\\config");	
		UserAccounts.SetDefaultHomePath("C:\\ProgramData\\IDK Software\\sam\\config\\Users");
	}
	/**
	 * Waits for incomming connections, and passes them to a new Descriptor, which handles I|O.
	 * Runs on a loop which blocks while waiting for a connection request, passes the connection to a new Descriptor object, and resumes listening for connections requests.
	 * The loop is broken when the shutdown method is called. 
	 *
	 * @throws	exception if one is encountered.
	 * @param	args an array of arguments passed while running.
	 */
	public static void Run() throws Exception
	{
		/** Log a message that we're starting up. */
		System.out.println("Server starting up on port " + SERVICE_PORT);

                /** We need to create a new instance of Server, because our "main" method does not create one. This seems like odd behavior
                  * at first, but its just the way it is. Since everything is static, we don't need to reference it in a variable. */
		new TelnetServer();
		
		/** Bind to a local port and init threads */
		try
		{
                        /** Bind to our port by instantiating a new ServerSocket */
			TelnetServer.serverSocket = new ServerSocket (SERVICE_PORT);

                        /** Create a new thread pool with a fixed size, if max connections has been set. 
                         * Java uses ~ 5 threads of its own, plus the number of chatters we will have on. */
                        if (TelnetServer.MAX_CONNECTIONS > 0)
                        	TelnetServer.pool = Executors.newFixedThreadPool( TelnetServer.MAX_CONNECTIONS + 5);
                        /** Otherwise, create a new thread pool with an expandable size, using the default
                         *  thread factory to generate those threads. */
                        else
                        	TelnetServer.pool = Executors.newCachedThreadPool();
                        
                        /** Now, to run a new thread, we use the command:
                         *  Server.pool.execute( thread ); */

			/** Listen for new connections */
			while (TelnetServer.isClosed==false)
			{
				/** Accept the next connection */
				Socket connection = TelnetServer.serverSocket.accept();
                                /** Set some values on that connection to ensure that it doesnt lag wrongly. */
				connection.setTcpNoDelay(true);
				connection.setSoLinger(false,0);
                                /** Find the address of the connection. We can check this against a ban list, 
                                 *  and we can use this for logs */
				InetAddress addr = connection.getInetAddress();
				
				/** Create our address and output objects, so we can process output and log */
				PrintStream pout = new PrintStream (connection.getOutputStream());
				System.out.println("New connection request from " + addr.getHostName() + "@" + addr.getHostAddress());
	
				/** If we only allow a certain number of connections, then heres where we deny
                                 *  any exceeding that number */
				if (TelnetServer.MAX_CONNECTIONS > 0 && TelnetServer.connections >= TelnetServer.MAX_CONNECTIONS)
				{
					/** Send a message to the user*/
					pout.println ("I'm sorry, we are not accepting more connections at the moment. Please try back later.");
                                        /** Print a message */
					System.out.println("User " + addr.getHostName() + "." + addr.getHostAddress() + " denied access.");
					System.out.println("Already " + TelnetServer.connections + " connections.");
                                        /** Close the connection */
					connection.close();
                                        /** Break out of our loop */
					continue;
				}
                                /** Otherwise, accept the connection and process the request */
				else
				{
                                    /** Heres where we handle creating the new user. NIO would handle this
                                     *  a little differently. 
                                     */
					/** Create new Descriptor object, which stores our player info */
					Descriptor desc = new Descriptor(connection);
                                        /** Descriptor takes care of the rest. Send some messages */
					desc.send("SAMonitor 1.0 IDK Software Ltd\n\r", true);
                                        /** Run the descriptor */
					
					
                	

					TelnetServer.pool.execute( desc );

                                        /** Continue our loop, and wait for new players */
					continue;
				}
			}
		}
                /** Catch some exceptions */
		catch (BindException be)
		{
			System.out.printf("Bind exception, %d probably in use", SERVICE_PORT);
			be.printStackTrace();
			return;
		}
		catch (SocketException se)
		{
			/** If we didnt want to continue, a SocketException is normal here when shutting down.
                         *  Therefore, check if a close was requested. If not, only then do we print a message */
			if (TelnetServer.isClosed!=true)
			{
				System.out.println("Socket exception caught in Server, possibly closing ServerSocket.");
				se.printStackTrace();
			}
		}
		catch (IOException ioe)
		{
			System.out.println("IOException caught in Server, possibly executing new thread in ThreadPool");
			ioe.printStackTrace();
		}
                /** Catch any other exeption we didnt prepare for */
		catch (Exception e )
		{
			System.out.println("Exception caught in Server.");
			e.printStackTrace();
		}
                /** Then, when were all done, print our final messages, and quit */
		finally
		{
			System.out.println("Server class has been shut down.");
			/** If it was a valid shutdown, close System, otherwise leave open for debugging */
			if (TelnetServer.isClosed==true)
				System.exit(1);
		}
	}

        /** Shutdown our Server object */
	public static void shutdown()
	{
		/** Set our variable so we know 1) Not to continue, and 2) It was requested */
		isClosed = true;
		/** Close all connections, and log everything */
		System.out.println("Server shutting down!");
		try
		{
                    serverSocket.close();
		}
		catch (IOException ioe)
		{
			System.out.println("IOException caught while closing ServerSocket");
			ioe.printStackTrace();
		}
		catch (Exception e )
		{
			System.out.println("Exception caught shutting down");
			e.printStackTrace();
		}
	}

    /** Add the specified descriptor to our list */
    public static void addDescriptor( Descriptor desc )
    {
        /** Check the validity of the descriptor */
        if (desc==null)
        {
            /** Print a message */
            System.out.println("Invalid descriptor passed");
            return;
        }
        descriptorList.add(desc);
        /** Increase our connections */
        connections++;
    }
    
    /** Removes the specified descriptor from our list */
    public static void removeDescriptor( Descriptor desc )
    {
        /** Check the validity of the descriptor */
        if (desc==null)
        {
            /** Print a message */
            System.out.println("Invalid descriptor passed");
            return;
        }
        descriptorList.remove(desc);
        /** Decrease our connections */
        connections--;
    }
    
    public static boolean isClosed()
    {
    	return false;
    }
    
	public static boolean isStopping() {
		// TODO Auto-generated method stub
		return stopping;
	}
	/**
	 * @param stopping the stopping to set
	 */
	public static void setStopping() {
		TelnetServer.stopping = true;
	}

}
