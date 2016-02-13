package samonitor;


//import java.io.*;

public class UnexpectedDisconnectException extends Exception
{
	String mistake;
	//----------------------------------------------
	// Default constructor - initializes instance variable to unknown
	  public UnexpectedDisconnectException()
	  {
	    super();             // call superclass constructor
	    mistake = "unknown";
	  }
	  
	//-----------------------------------------------
	// Constructor receives some kind of message that is saved in an instance variable.
	  public UnexpectedDisconnectException(String err)
	  {
	    super(err);     // call super class constructor
	    mistake = err;  // save message
	  }
	  
	//------------------------------------------------  
	// public method, callable by exception catcher. It returns the error message.
	  public String getError()
	  {
	    return mistake;
	  }
}
