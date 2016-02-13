package telnet.user;

import java.util.Date;

public class User {
	protected String name;
	protected String userName;
	protected String comment;
	protected String homeDir;
	protected Date expireDate;
	protected int inactiveDays;
	protected int initialGroup;
	protected String passwd;
	protected int uid;
	protected String email;
	
	
	
	protected void UserAdd(String name, String userName, String comment, String homeDir, Date expireDate, int inactiveDays,
			int initialGroup, String passwd, int uid, String email) 
	{
		if (name == null)
			name = "";
		this.name = name;
		if (userName == null)
			userName = "";
		this.userName = userName;
		if (comment == null)
			comment = "";
		this.comment = comment;
		if (homeDir == null)
			homeDir = "";
		this.homeDir = homeDir;
		if (expireDate == null)
			expireDate = new Date();
		this.expireDate = expireDate;
		this.inactiveDays = inactiveDays;
		this.initialGroup = initialGroup;
		if (passwd == null)
			passwd = "";
		this.passwd = passwd;
		this.uid = uid;
		if (email == null)
			email = "";
		this.email = email;
	}
	
	public boolean matchPassword(String pw)
	{
		if (pw.matches(this.passwd))
		{
			return true;
		}
		return false;
	}
}
