package telnet.user;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Handler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.*;

//import UserAccounts.User;

public class UserAccounts {
	private static UsersFile usersFile = null;
	private static GroupsFile groupsFile = null;
	private static int defaultGID = 100;
	private static String defaultHomePath = null;
	/* exit() values for user/group commands */
	public enum UserExitCode {
		
		/* Everything succeeded */
		M_SUCCESS,
		
		/* WARNING: uid %d is reserved. */
		M_RESERVED,

		/* WARNING: more than NGROUPS_MAX(%d) groups specified. */
		M_MAXGROUPS,

		/* ERROR: invalid syntax.\nusage:  useradd ... */
		M_AUSAGE,

		/* ERROR: Invalid syntax.\nusage:  userdel [-r] login\n" */
		M_DUSAGE,

		/* ERROR: Invalid syntax.\nusage:  usermod ... */
		M_MUSAGE,


		/* ERROR: Unexpected failure.  Defaults unchanged. */
		M_FAILED,

		/* ERROR: Unable to remove files from home directory. */
		M_RMFILES,

		/* ERROR: Unable to remove home directory. */
		M_RMHOME,

		/* ERROR: Cannot update system files - login cannot be %s. */
		M_UPDATE,

		/* ERROR: uid %d is already in use.  Choose another. */
		M_UID_USED,

		/* ERROR: %s is already in use.  Choose another. */
		M_USED,

		/* ERROR: %s does not exist. */
		M_EXIST,

		/* ERROR: %s is not a valid %s.  Choose another. */
		M_INVALID,

		/* ERROR: %s is in use.  Cannot %s it. */
		M_BUSY,

		/* WARNING: %s has no permissions to use %s. */
		M_NO_PERM,

		/* ERROR: There is not sufficient space to move %s home directory to %s */
		M_NOSPACE,

		/* ERROR: %s %d is too big.  Choose another. */
		M_TOOBIG,

		/* ERROR: group %s does not exist.  Choose another. */
		M_GRP_NOTUSED,

		/* ERROR: Unable to %s: %s */
		M_OOPS,

		/* ERROR: %s is not a full path name.  Choose another. */
		M_RELPATH,

		/* ERROR: %s is the primary group name.  Choose another. */
		M_SAME_GRP,

		/* ERROR: Inconsistent password files.  See pwconv(1M). */
		M_HOSED_FILES,

		/* ERROR: %s is not a local user. */
		M_NONLOCAL,

		/* ERROR: Permission denied. */
		M_PERM_DENIED,

		/* WARNING: Group entry exceeds 2048 char: /etc/group entry truncated. */
		M_GROUP_ENTRY_OVF,

		/* ERROR: invalid syntax.\nusage:  roleadd ... */
		M_ARUSAGE,

		/* ERROR: Invalid syntax.\nusage:  roledel [-r] login\n" */
		M_DRUSAGE,

		/* ERROR: Invalid syntax.\nusage:  rolemod -u ... */
		M_MRUSAGE,

		/* ERROR: project %s does not exist.  Choose another. */
		M_PROJ_NOTUSED,

		/* WARNING: more than NPROJECTS_MAX(%d) projects specified. */
		M_MAXPROJECTS,

		/* WARNING: Project entry exceeds 512 char: /etc/project entry truncated. */
		M_PROJ_ENTRY_OVF,

		/* ERROR: Invalid key. */
		M_INVALID_KEY,

		/* ERROR: Missing value specification. */
		M_INVALID_VALUE,

		/* ERROR: Multiple definitions of key ``%s''. */
		M_REDEFINED_KEY,

		/* ERROR: Roles must be modified with rolemod */
		M_ISROLE,

		/* ERROR: Users must be modified with usermod */
		M_ISUSER,

		/* WARNING: gid %d is reserved. */
		M_RESERVED_GID,

		/* ERROR: Failed to read /etc/group file due to invalid entry or read error. */
		M_READ_ERROR,
	}
	public enum GroupExitCode {
		

		/* Everything succeeded */
		EX_SUCCESS,

		/* No permission */
		EX_NO_PERM,

		/* Command syntax error */
		EX_SYNTAX,

		/* Invalid argument given */
		EX_BADARG,

		/* A gid or uid already exists */
		EX_ID_EXISTS,

		/* PASSWD and SHADOW are inconsistent with each other */
		EX_INCONSISTENT,

		/* A group or user name  doesn't exist */
		EX_NAME_NOT_EXIST,

		/* GROUP, PASSWD, or SHADOW file missing */
		EX_MISSING,

		/* GROUP, PASSWD, or SHAWOW file is busy */
		EX_BUSY,

		/* A group or user name already exists */
		EX_NAME_EXISTS,

		/* Unable to update GROUP, PASSWD, or SHADOW file */
		EX_UPDATE,

		/* Not enough space */
		EX_NOSPACE,

		/* Unable to create/remove/move home directory */
		EX_HOMEDIR,

		/* new login already in use */
		EX_NL_USED,

		/* Unexpected failure */
		EX_FAILURE,

		/* A user name is in a non-local name service */
		EX_NOT_LOCAL


	}
	
	protected static UserProperties users = new UserProperties();
	protected static GroupProperties groups = new GroupProperties();
	
	public static void Init(String configPath)
	{
		 
	        try {
	        	groupsFile = GroupsFile.load(configPath + "\\groups.xml");
	        	if (groupsFile != null) {
	        		groupsFile.decode();
	        	}
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e3) {
				e3.printStackTrace();
			}
			
			
			 try {
		        	usersFile = UsersFile.load(configPath + "\\users.xml");
		        	if (usersFile != null) {
		        		usersFile.decode();
		        	}
		        	//usersFile.Save("D:\\sam\\config", "user1.xml");
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e3) {
					e3.printStackTrace();
				}
	        
			 	if (usersFile == null || usersFile == null) {
			 		return;
			 	}
				UserAccounts.Init(usersFile.GetUserProperties(), groupsFile.GetGroupProperties());	
	}

	public static void SetDefaultHomePath(String path)
	{
		defaultHomePath = path;
	}
	
	public static void Init(UserProperties u, GroupProperties g)
	{
		users = u;
		groups = g;
	}
	
	
	static UserAccounts usersInstance = new UserAccounts();
	
	public String chage()		// Used to change the time the user's password will expire. 
	{
		return "Used to change the time the user's password will expire."; 
	}
	
	protected Group createGroup(int gid, String group)
	{
		return new Group(gid, group);
	}
	
	protected GroupExitCode readGroupFile()
	{
		return GroupExitCode.EX_MISSING;
	}
	/*
		groupadd - Create a new group 
		SYNOPSIS
		groupadd [-g gid [-o]] [-r] [-f] group 
		DESCRIPTION
		The groupadd command creates a new group account using the values specified on the command
		line and the default values from the system. The new group will be entered into the system
		files as needed. The options which apply to the groupadd command are 
		-g gid
		The numerical value of the group's ID. This value must be unique, unless the -o option is
		used. The value must be non-negative. The default is to use the smallest ID value greater
		than 500 and greater than every other group. Values between 0 and 499 are typically reserved
		for system accounts. 
		-r
		This flag instructs groupadd to add a system account. The first available gid lower than 499
		will be automatically selected unless the -g option is also given on the command line. 
		This is an option added by Red Hat. 
		-f
		This is the force flag. This will cause groupadd to exit with an error when the group about to
		be added already exists on the system. If that is the case, the group won't be altered
		(or added again). 
		This option also modifies the way -g option works. When you request a gid that it is not unique
		and you don't specify the -o option too, the group creation will fall back to the standard
		behavior (adding a group as if neither -g or -o options were specified). 

	 */
	public static GroupExitCode groupadd(int gid, String name)	// Create a new group. 
	{
		//readGroupFile();
		if (gid == -1)
		{
			
		}
		Group group = usersInstance.createGroup(gid, name);
		groups.add(group);
		return GroupExitCode.EX_SUCCESS;
	}
	public static boolean groupdel(String name)	// Delete a group 
	{
		Group group = usersInstance.findGroup(name);
		if (group == null)
		{
			return false;
		}
		usersInstance.groups.remove(group);
		return true;
	}
	public static String groupmod()	// Modify a group 
	{
		return "Modify a group.";
	}
	public String groups()		// print the groups a user is in 
	{
		return "Print the groups a user is in.";
	}
	public String grpck()		// Verify the integrity of group files. 
	{
		StringBuffer sb = new StringBuffer();
		Iterator<Group> it = usersInstance.groups.iterator();
		while (it.hasNext())
		{
			/** Pull the descriptor from the iterator */
			Group group = it.next();
			Integer intGID = group.gid;
			sb.append("\r\n" + group.group + " " + intGID.toString() + " " + "iain" + "\r\n");
		}
		return sb.toString();
	}
	public String id()			// Print group or user ID numbers for the specified user. 
	{
		return "Print group or user ID numbers for the specified user.";
	}
	public String newgrp()		// Allows a user to log in to a new group. 
	{
		return "Allows a user to log in to a new group.";
	}
	public String newusers()	// Update and create new users in batch form. 
	{
		return "Update and create new users in batch form.";
	}
	public String passwd()		// Used to update a user's password.
	{
		return "Used to update a user's password.";
	}
	
	String useraddHelp =

		"useradd - Create a new user or update default new user information\r\n" + 
		"\r\n" +
		"useradd [-c comment] [-d home_dir]\r\n" +
		"[-e expire_date] [-f inactive_time] \r\n" +
		"[-g initial_group]\r\n" +
		"[-G group[,...]] \r\n" +
		"[-p passwd]\r\n" +
		"[-u uid] \r\n" +
		"\r\n";
	
	public static UserExitCode useradd(String name, String userName, String comment, String homeDir, Date expireDate, int inactiveDays,
			int initialGroup, String passwd, int uid, String email)		// Create a new user or update default new user information 
	{
		
		if (uid <= 0)
		{
			uid = findNextUid();
			if (uid == -1)
			{
				return UserExitCode.M_TOOBIG;
			}
		}
		if (UserAccounts.findUid(uid) != null)
		{
			return UserExitCode.M_UID_USED;
		}
		if (UserAccounts.findUser(userName) != null)
		{
			return UserExitCode.M_USED;
		}
		if (initialGroup <= 0)
		{
			initialGroup = defaultGID;    
		}
		
		if (homeDir == null)
		{
			homeDir = defaultHomePath + File.separator + userName;
			// Create multiple directories
			boolean success = true;
			try {
				success = (new File(homeDir)).mkdirs();
			}
			catch (SecurityException e)
			{
				return UserExitCode.M_PERM_DENIED;
			}
		    if (success == false)
		    {
		    	return UserExitCode.M_FAILED;
		    }
		}
		User user = new User();
		user.UserAdd(name, userName, comment, homeDir, expireDate,
				inactiveDays, initialGroup, passwd, uid, email);
		UserAccounts.users.add(user);
		usersFile.Save("D:\\sam\\config", "user1.xml");
		return UserExitCode.M_SUCCESS;
	}
	public String userdel()		// Delete a user account.
	{
		return "Delete a user account.";
	}
	public String usermod()		// Modify a user account. 
	{
		return "Modify a user account.";
	}

	public static User findUser(String  userName)
	{
		Iterator<User> it = usersInstance.users.iterator();
		while (it.hasNext())
		{
			/** Pull the descriptor from the iterator */
			User user = it.next();
			if (user.userName.matches(userName) == true)
			{
				return user;
			}
		}
		return null;	
	}
	public static User findUid(int uid)
	{
		Iterator<User> it = usersInstance.users.iterator();
		while (it.hasNext())
		{
			/** Pull the descriptor from the iterator */
			User user = it.next();
			if (user.uid == uid)
			{
				return user;
			}
		}
		return null;	
	}
	Group findGroup(String  name)
	{
		Iterator<Group> it = usersInstance.groups.iterator();
		while (it.hasNext())
		{
			/** Pull the descriptor from the iterator */
			Group group = it.next();
			if (group.group.matches(name) == true)
			{
				return group;
			}
		}
		return null;	
	}
	
	static int findNextUid()
	{
		int uid = 0;
		Iterator<User> it = usersInstance.users.iterator();
		while (it.hasNext())
		{
			/** Pull the descriptor from the iterator */
			User user = it.next();
			if (user.uid > uid)
			{
				uid = user.uid;
			}
		}
		if (uid == 0)
		{
			return 100; // first user.
		}
		return (++uid);
	}
	public String getHead(Handler h)
	{
		return "<?xml version=\"1.0\" encoding=\"windows-1252\" standalone=\"no\"?>\n"
			//+ "<!DOCTYPE log SYSTEM \"logger.dtd\">\n"
			+ "<Users>\n";
	}

	// This method is called just after the handler using this
	// formatter is closed
	public String getTail(Handler h)
	{
		return "</Users>\n";
	}
}
