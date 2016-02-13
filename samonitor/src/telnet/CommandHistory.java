package telnet;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Stack;


public class CommandHistory {
	private LinkedList<String> m_History = new LinkedList<String>();
	private ListIterator<String> iterator = null;
	boolean l_First;
	private int currentIndex = 0;
	String GetFirst()
	{
		currentIndex = 0;
		return m_History.getFirst();
		
	}

	String GetLast()
	{
		currentIndex = m_History.size()-1;
		return m_History.getLast();
	}

	String GetNext()
	{
		currentIndex++;
		if (currentIndex >= m_History.size())
		{
			return null;
		}
		return m_History.get(currentIndex);
	}

	String GetPrev()
	{
		currentIndex--;
		if (currentIndex < 0)
		{
			return null;
		}
		return m_History.get(currentIndex);
	}
	
	public CommandHistory()
	{
		l_First = true;
	};

	void Reset()
	{
		l_First = true;
	}
	
	void Add(String szLine)
	{
		
		m_History.push(szLine);
		
		System.out.println("History size " + m_History.size());
		iterator = null;
	}

	void Bell()
	{

	}

	public String Prev(boolean rotate)
	{
		if (IsEmpty() == true)
		{
			return null;
		}
		String l_szLine = null;
		
		if (l_First)
		{
			l_szLine = GetLast();
			l_First = false;
			return l_szLine;
		}
		
		l_szLine = GetPrev();
		if ((l_szLine == null) && rotate)
		{
			l_szLine = GetLast();
		}
		return l_szLine;
	}

	public String Next(boolean rotate)
	{
		String l_szLine = null;
		
		if (l_First)
		{
			l_szLine = GetFirst();
			l_First = false;
			return l_szLine;
		}
		
		l_szLine = GetNext();
		
		if ((l_szLine == null) && rotate)
		{
			l_szLine = GetFirst();
		}
		return l_szLine;
	}

	public boolean IsEmpty()
	{
		return m_History.isEmpty();
	}
}
