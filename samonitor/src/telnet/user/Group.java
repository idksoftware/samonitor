package telnet.user;

import java.util.Iterator;
import java.util.LinkedList;

public class Group
{
	LinkedList<String> groupList = new LinkedList<String>();
	int gid;
	String group;
	
	protected Group(int gid, String group)
	{
		this.gid = gid;
		this.group = group;
	}
	protected void add(String user)
	{
		groupList.add(user);
	}
	
	protected Iterator<String> GroupList()
	{
		Iterator<String> it = groupList.iterator();
		return it;
	}
	
}
