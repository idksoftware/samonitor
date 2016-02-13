package telnet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


//import Descriptor.ControlKey;


public class CommandProcessor {
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
	static byte WILL = (byte)251;
	static byte DO = (byte)252;
	static byte WONT = (byte)253;
	static byte DONT = (byte)254;
	static byte ECHO = (byte)1;
	
	private String userName;
	// Input received from the account.
	//private BufferedReader input;
	private InputStreamReader input;
	private boolean echoOn = true;
	private boolean passwordOn = false;
	private CommandHistory commandHistory = new CommandHistory();
    /** Output to the account */
	private PrintStream output;
	private Socket socket;
	boolean loggedIn = false; 
	/** Should thread continue or has character logged out? */
	private boolean isClosed = false;
	
	public CommandProcessor( Socket socket )
	{
		
		/** Set the socket reference */
		this.socket = socket;

		try
		{
                        /** Set the input object */
			//this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.input = new InputStreamReader(this.socket.getInputStream());
                        /** Set the output object */
			this.output = new PrintStream(this.socket.getOutputStream());
                        /** Error checking */
                        if (this.input==null || this.output==null)
                        {
                            /** Log him out, and return */
                            logout("Unknown user");
                            return;
                        }

			try
			{
                            /** Set the standard timeout length, in case the connection dies */
                            this.socket.setSoTimeout(TelnetServer.TIMEOUT_LENGTH);
			}
                        /** Catch a socket exception */
			catch(SocketException se)
			{
                                /** Print the error messages and stack trace, then close and return */
                                System.out.println("SocketException in generating new DescriptorData");
				System.out.println("Unable to set socket option SO_TIMEOUT");
				se.printStackTrace();
                                /** Send a message */
                                this.send("A problem has occured setting up your account. Please try again later.", true);
                                /** Then close out */
                                this.close();
                                
                                /** Stop processing */
				return;
			}
		}
		catch(IOException ioe)
		{
                        /** Print the error messages and stack trace */
			System.out.println("IOException caught in generating new DescriptorData");
			ioe.printStackTrace();

			// send a message to the character, if possible
			this.send("A problem has occured setting up your account. Please try again later.", true);
			// close the thread
			this.close();

                        /** Stop processing */
			return;
		}
	}
	
	public String getName()
	{
		return userName;
	}
	
	public void SetName(String name)
	{
		userName = name;
	}
	
	public String readLine() throws InterruptedIOException
	{
		ControlKey ck = ControlKey.DB_NONE;
		//char[] inputBuffer = new char[1000];
		LineEditor lineEditor = new LineEditor(1000);
		int offset = 0;
		String ret = null; 
		boolean crFound = false;
		while (true)
		{
			
		    
			char[] buffer = new char[100];
			int num = 0;
			try {
				num = this.input.read(buffer, 0, 100);
			} catch (SocketTimeoutException iioe) {
				throw new InterruptedIOException("Client unexpectedly disconnected");
	        } catch (IOException e) {
	        	throw new InterruptedIOException("Client unexpectedly disconnected");
			}
			
			if (buffer[0] == 0)
			{
				throw new InterruptedIOException("Client unexpectedly disconnected");
			}
			if (buffer[0] == 8)
			{
				ck = ControlKey.DB_BACKSPACE;
				DeleteChar(lineEditor);
				continue;
			}
			if (buffer[0] == 127)
			{
				ck = ControlKey.DB_DELETE;
				DeleteChar(lineEditor);
				continue;
			}
			if (buffer[0] == 27)
			{
				//System.out.println("Esc");
				if (buffer[1] == '[')
				{
					
					switch(buffer[2])
					{
					case 'A':
						ck = ControlKey.DB_UP_ARROW;
						if (commandHistory.IsEmpty() == false)
						{
							// Move cursor back to the start of line
							//this.output.print((char)27 + "[" + (lineEditor.GetCurrentPos()) + "D") ;
							this.output.print((char)27 + "[u");
							// Clear to the end of line
							this.output.print((char)27 + "[K");
							lineEditor.Clear();
							String s = commandHistory.Prev(true);
							lineEditor.LoadLine(s.toCharArray());
							this.output.print(s);
							lineEditor.End();
							//this.output.print((char)27 + "[" + s.length() + "D") ;
						}
						break;
					case 'B':
						ck = ControlKey.DB_DOWN_ARROW;
						if (commandHistory.IsEmpty() == false)
						{
							// Move cursor back to the start of line
							//this.output.print((char)27 + "[" + (lineEditor.GetCurrentPos()) + "D") ;
							this.output.print((char)27 + "[u");
							// Clear to the end of line
							this.output.print((char)27 + "[K");
							lineEditor.Clear();
							String s = commandHistory.Next(true);
							lineEditor.LoadLine(s.toCharArray());
							this.output.print(s);
							lineEditor.End();
							//this.output.print((char)27 + "[" + s.length() + "D") ;
						}
						break;
					case 'C':
						ck = ControlKey.DB_RIGHT_ARROW;
						if (lineEditor.CursorRight() == true)
						{
							this.output.print((char)27 + "[C");
						}
						break;
					case 'D':
						ck = ControlKey.DB_LEFT_ARROW;
						if (lineEditor.CursorLeft() == true)
						{
							this.output.print((char)27 + "[D");
						}
						break;
					case '2':
						if(buffer[3] == '~')
						{
							ck = ControlKey.DB_INSERT;
							break;
						}
						break;
					case '3':
						if(buffer[3] == '~')
						{
							ck = ControlKey.DB_DELETE;
							this.output.print((char)27 + "[3~");
							break;
						}
						break;
					default:
						ck = ControlKey.DB_NONE;
						continue;
					}
					
				}
				if (ck == ControlKey.DB_NONE)
				{
					continue;
				}
			}
			if ((buffer[0] < ' ' || buffer[0] > '~') && buffer[0] != '\r')
			{
				int ch = (int)buffer[0];
				//System.out.println("Not valid " + buffer + "value " + ch);
				continue;
			}
			//System.out.println(buffer);
			for (int i = 0; i < num; i++)
			{
				
				
				if (echoOn == true)
				{
					this.output.append(buffer[i]);
					this.output.flush();
				}
				
				if (buffer[i] == '\r')
				{
					ret = new String(lineEditor.GetBuffer(),0,lineEditor.GetSize());
					if (ret.length() != 0)
					{
						// Only put text in the history not empty lines
						if (loggedIn == true)
						{
							// 
							commandHistory.Add(ret);
						}
					}
					return ret;
					
				}
				char c = buffer[i];
				String restOfLine = lineEditor.Insert(c);
				if (restOfLine != null)
				{
					this.output.print(restOfLine);
					this.output.print((char)27 + "[" + restOfLine.length() + "D") ;
				}
			}
			
			
		}
	}
	public void setEcho(boolean on)
	{
		echoOn = on;
	}
	
	public void flush()
	{   
		char[] buffer = new char[100];
		int num = 0;
		try {
			//this.input.
			num = this.input.read(buffer, 0, 100);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void DeleteChar(LineEditor lineEditor)
	{
		if (lineEditor.GetCurrentPos() == 0)
		{
			return;
		}
		this.output.print((char)27 + "[D");
		this.output.print((char)27 + "[K");
		String restOfLine = lineEditor.BackSpace();
		if (restOfLine != null)
		{
			this.output.print(restOfLine);
			this.output.print((char)27 + "[" + restOfLine.length() + "D") ;
		}
		return;
	}
	
	public void Prompt()
	{
		this.send("sam> ", false);
		this.output.print((char)27 + "[s");
	}
	/** Send a message to the user */
	public void send(String message, boolean withCR)
	{
		/** println only returns "message + \n" here, which results in spacing problems */
		if (withCR == true)
		{
			this.output.print( message + "\n\r");
		}
		else
		{
			this.output.print( message);
		}
		this.output.flush();
	}

	public void write(byte[] cmd)
	{
		try {
			this.output.write(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int read(char[] buffer, int size)
	{
		int num = 0;
		try {
			num = this.input.read(buffer, 0, size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
        /** Close the account */
	public void close()
	{
		this.isClosed=true;

		try
		{
                    /** Close everything out */
                    if (this.input!=null)
			this.input.close();
                    if (this.output!=null)
			this.output.close();
                    if (this.socket!=null)
			this.socket.close();
		}
                /** Catch any resulting exceptions */
		catch (IOException ieo)
		{
			System.out.println("IOException caught closing input|output");
			ieo.printStackTrace();
		}
	}
	
	/** Log the user out */
	public void logout(String name)
	{
            /** Send a message */
            this.send("Connection Closing... Goodbye", true);
            System.out.println(name + " has logged out.");
            /** Close */
            this.close();
	}
	
}
