package telnet;
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class LineEditor {
	
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
	
	//private InputStreamReader input;
	//private boolean echoOn = true;
	//private boolean passwordOn = false;
	
	private String prompt;
	private int m_iSize;
	private char[] m_szBuffer;
	private int m_CurPos;
	private int m_CurrSize;
	private char[] m_szRightBuffer;
	//private Socket socket;
	
	public LineEditor(int size)
	{
		
		m_iSize = size;
		m_szBuffer = new char [m_iSize + 1];
		
		Clear();
		/*
		try
		{
			this.input = new InputStreamReader(socket.getInputStream());
		}
		catch(IOException ioe)
		{
                        // Print the error messages and stack trace
			System.out.println("IOException caught in generating new DescriptorData");
			ioe.printStackTrace();

			// send a message to the character, if possible
			//this.send("A problem has occured setting up your account. Please try again later.", true);
			// close the thread
			//this.close();

                        // Stop processing
			return;
		}
	*/
	}

	public void LoadLine(char[] szLine)
	{
		if (szLine == null)
		{
			return;
		}
		strncpy(m_szBuffer, szLine, szLine.length);
		m_CurrSize = szLine.length;
		End();
	}

	void strncpy(char[] d, char[] s, int size)
	{
		for (int i = 0; i < size; i++)
		{
			d[i] = s[i];
		}
	}
	
	public void Home()
	{
		m_CurPos = 0;
		//System.out.println("Home " + m_CurrSize + " " + m_CurPos);
	}

	public void End()
	{
		m_CurPos = m_CurrSize;
		//System.out.println("End " + m_CurrSize + " " + m_CurPos);
	}

	public void Clear()
	{	
		m_CurPos = 0;
		m_CurrSize = 0;
		m_szBuffer[m_CurrSize] = '\0';
		//System.out.println("Clear " + m_CurrSize + " " + m_CurPos);
	}
	public boolean CursorLeft()
	{
		m_CurPos--;
		if (m_CurPos < 0)
		{
			// Bell?
			m_CurPos = 0;
			//System.out.println("CursorLeft " + m_CurrSize + " " + m_CurPos);
			return false;
		}
		//System.out.println("CursorLeft " + m_CurrSize + " " + m_CurPos);
		return true;
	}
	public boolean CursorRight()
	{
		m_CurPos++;
		if (m_CurPos > m_CurrSize)
		{
			// Bell?
			m_CurPos = m_CurrSize;
			return false;
		}
		
		return true;
	}
	public String BackSpace()
	{
		
		if (m_CurPos <= 0)
		{
			// Bell?
			return null;
		}
		
		String rightBuffer = null;
		if (m_CurPos < m_CurrSize)
		{
			try
			{
				rightBuffer = new String(m_szBuffer, m_CurPos, m_CurrSize - m_CurPos);
			}
			catch(StringIndexOutOfBoundsException ex)
			{
				System.out.println("StringIndexOutOfBoundsException caught " + m_CurrSize + " " + m_CurPos + ex.getMessage());
			}
		}
		MoveLeft();
		CursorLeft();
		return rightBuffer;
	}

	public void MoveLeft()
	{
		for (int i = m_CurPos; i < m_CurrSize; i++)
		{
			m_szBuffer[i - 1] = m_szBuffer[i];
		}
		m_CurrSize--;
		m_szBuffer[m_CurrSize] = 0;
		//System.out.println("MoveLeft " + m_CurrSize + " " + m_CurPos);
	}

	public void MoveRight()
	{
		for (int i = m_CurrSize; i > m_CurPos-1; i--)
		{
			m_szBuffer[i + 1] = m_szBuffer[i];
		}
		m_CurrSize++;
		m_szBuffer[m_CurrSize+1] = 0;
		//System.out.println("MoveRight CurrSize: " + m_CurrSize + " CurPos: " + m_CurPos);
	}

	public char[] GetBuffer()
	{
		return m_szBuffer;
	}
	
	public int GetSize()
	{
		return m_CurrSize;
	}
	public char[] GetRight()
	{
		return m_szRightBuffer;
	}


	public String Insert(char c)
	{
		if (m_CurPos < m_CurrSize)
		{
			// Insert
			String rightBuffer = null;
			MoveRight();
			m_szBuffer[m_CurPos] = c;
			CursorRight();	
			try
			{
				rightBuffer = new String(m_szBuffer, m_CurPos, m_CurrSize - m_CurPos);
			}
			catch(StringIndexOutOfBoundsException ex)
			{
				System.out.println("StringIndexOutOfBoundsException caught " + m_CurrSize + " " + m_CurPos + ex.getMessage());
			}
			//System.out.println("Insert " + m_CurrSize + " " + m_CurPos + " " +  m_szBuffer);
			return rightBuffer;
		}
		
		// append
		MoveRight();
		m_szBuffer[m_CurPos] = c;
		CursorRight();	
		//System.out.println("Insert " + m_CurrSize + " " + m_CurPos);
		return null;
	}
	
	int GetCurrentPos()
	{
		return m_CurPos;
	}
}
