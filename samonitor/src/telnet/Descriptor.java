package telnet;

import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import telnet.user.UserAccounts;



/**
 * When a character logs into the game, the Descriptor is loaded from
 * the Server class, and then the I|O responsibility is passed to the Descriptor object. NIO would handle this
 * process differently.
 */
public class Descriptor extends Thread
{
	
	enum ControlKey {
		DB_NONE,
		DB_UP_ARROW,
		DB_DOWN_ARROW,
		DB_LEFT_ARROW,
		DB_RIGHT_ARROW,
		DB_INSERT,
		DB_DELETE,
		DB_BACKSPACE
	}
	
	static byte IAC = (byte)255;
	static byte SB = (byte)250;
	static byte SE = (byte)240;
	static byte WILL = (byte)251;
	static byte DO = (byte)252;
	static byte WONT = (byte)253;
	static byte DONT = (byte)254;
	static byte ECHO = (byte)1;
	static byte SUPPGOAHEAD = (byte)3;
	// Input received from the account.
	//private BufferedReader input;
	private InputStreamReader input;
	private boolean echoOn = true;
	private boolean passwordOn = false;
	boolean loggedIn = false; 
	private CommandHistory commandHistory = new CommandHistory();
	private CommandHandler commandHandler = new CommandHandler();
	/** The socket associated with the account */
	private Socket socket;
	private CommandProcessor commandProcessor;
	
        /** Output to the account */
	private PrintStream output;

	/** Should thread continue or has character logged out? */
	private boolean isClosed = false;

	/** Initialize the account object, and set the socket */
	public Descriptor( Socket socket)
	{
		
		/** Set the socket reference */
		this.socket = socket;
		
		commandProcessor = new CommandProcessor(this.socket);
	}
/*
		try
		{
            //  Set the input object
			//this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.input = new InputStreamReader(this.socket.getInputStream());
            // Set the output object
			this.output = new PrintStream(this.socket.getOutputStream());
                        // Error checking
                        if (this.input==null || this.output==null)
                        {
                            // Log him out, and return
                            logout();
                            return;
                        }

			try
			{
                            // Set the standard timeout length, in case the connection dies
                            this.socket.setSoTimeout(TelnetServer.TIMEOUT_LENGTH);
			}
                        // Catch a socket exception
			catch(SocketException se)
			{
                                // Print the error messages and stack trace, then close and return
                                System.out.println("SocketException in generating new DescriptorData");
				System.out.println("Unable to set socket option SO_TIMEOUT");
				se.printStackTrace();
                                // Send a message
                                this.send("A problem has occured setting up your account. Please try again later.", true);
                                // Then close out
                                this.close();
                                // Stop processing
				return;
			}
		}
		catch(IOException ioe)
		{
            // Print the error messages and stack trace
			System.out.println("IOException caught in generating new DescriptorData");
			ioe.printStackTrace();

			// send a message to the character, if possible
			this.send("A problem has occured setting up your account. Please try again later.", true);
			// close the thread
			this.close();

                        // Stop processing
			return;
		}
	}
*/
	
	public void send(String message, boolean withCR)
	{
		commandProcessor.send(message, withCR);
	}
	
	/** Receive user input and process */
	public void run()
	{
		telnet.user.User user = null;
		
        try
        {
        	
        		String userName = null;
        		int num = 0;
                byte[] cmd = new byte[] { IAC, WILL, SUPPGOAHEAD };
                char[] rep = new char[100];
                commandProcessor.write(cmd);
                num = commandProcessor.read(rep, 100);
                System.out.printf("IAC 0x%-2X, type of operation 0x%-2X, option 0x%-2X", (byte)rep[0], (byte)rep[1], (byte)rep[2]);
                byte[] cmd1 = new byte[] { IAC, WILL, ECHO };
                commandProcessor.write(cmd1);
               
                //userName = commandProcessor.readLine();
                    /** First, ask them for their name */
                    
                        /** Send the request message */
                commandProcessor.send("User: ", false);
                        /** Run the method to set it, and if set properly, break from the loop */
        		
                userName = commandProcessor.readLine();
        		
        		this.name(userName);
    			       
                    /** Now, loop so long as we should be waiting for input, and process */
        		
        		for (int i = 0; i < 3; i++) 
        		{
        			if (TelnetServer.isClosed()==true && this.isClosed==true)
        			{
        				
        			}
        			commandProcessor.send("\n331 Password required for " + userName, true);
        			commandProcessor.send("Password: ", false);
                        /** Find our input */
                    	// echo off
        			
                    	//passwordOn = true;
        			echoOn = false;
        			String password = null;
        			
                	password = commandProcessor.readLine();
                	
        			
        			echoOn = true;
                        //passwordOn = false;
        			if (userName == null || password == null)
        			{
        				commandProcessor.send("530 Not logged in, user or password incorrect!", true);
        				commandProcessor.logout(this.getName());
        			}
        			else
        			{
        				user = TelnetServer.users.findUser(userName);
        				if (user == null)
        				{
        					commandProcessor.send("530 Not logged in, user or password incorrect!", true);
        					commandProcessor.logout(this.getName());
        					continue;
        				}
        				
        				if (user.matchPassword(password) == true)
        				{
        					commandProcessor.send("230 User successfully logged in.", true);
        					loggedIn = true;
        					break;
        				}
        				
        				commandProcessor.send("530 Not logged in, user or password incorrect!", true);
        			}
        		}
        		if (loggedIn == false)
        		{
        			commandProcessor.logout(this.getName());
        			return;
        		}
        		while (TelnetServer.isClosed()==false && this.isClosed==false)
            	{
        			commandProcessor.Prompt();	
        			
        			// echo on
        			
        			//cmd = new byte[] { (byte) 0xFF, (byte) 0xFC, 0x01 };
                        
        			//cmd = new byte[] { (byte)033, (byte)0133, (byte)061, (byte)062, (byte)0154 };
        			//033 133 061 062 154
                    //this.output.write(cmd);
            
        		
        			
        			/** Run the method to set it, and if set properly, break from the loop */
        			String in = commandProcessor.readLine();
        			commandProcessor.send("You sent: " + in, true);  
        			String out = commandHandler.Process(in);
        			if (out != null)
        			{
        				commandProcessor.send(out, true);  
        			}
        			
        			/** Otherwise, well continue... asking again! */
        		} 
        
        	/** Kill the connection on timeout */
        }
       
        catch (InterruptedIOException iioe)
        {
                    /** Log it */
        		System.out.println("Timeout occurred - killing connection to " + this.getName());
        		/** Send a message */
        		commandProcessor.send("You vanish in a puff of smoke.", true);
        		/** Logout */
        		commandProcessor.logout(this.getName());
        }
            
  
        /** Catch termination of session so we dont get an error */
        catch (IOException ioe)
        {
        	System.out.println(getName() + " has logged out");
        	//terminate thread at end of run, automatically
        }
        
        catch (Exception e)
        {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
       
	}
        
        /** User name */
        public boolean name( String name )
        {
            /** Check if the name is already logged on */
            Iterator it = TelnetServer.descriptorList.iterator();
            /** Iterate through each one */
            while (it.hasNext())
            {
                /** Find the descriptor */
                Descriptor desc = (Descriptor)it.next();
                /** Check if its equal, ignoring the case. NEVER USE string == string in Java! */
                if ( desc.getName().equalsIgnoreCase(name) )
                {
                    /** Send a message */
                	commandProcessor.send("Already logged in on another session.", true);
                    /** Return false */
                    return false;
                }
            }
            try
            {
                /** No match found. Set the name */
                this.setName( name ); // User name used to set the thread name
                /** Return true */
                return true;
            }
            /** Catch a security exception */
            catch (SecurityException se)
            {
                /** Print a message and a stack trace */
                System.out.println("SecurityException caught while setting name of descriptor '" + name + "'");
                se.printStackTrace();
                /** Return false */
                return false;
            }
        }
}

