package telnet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import samonitor.Samonitor;
import telnet.user.UserAccounts;
import telnet.user.UserAccounts.GroupExitCode;
import telnet.user.UserAccounts.UserExitCode;
import telnet.user.commands.UserAdd;



public class CommandHandler
{
	String args[] = null;
	String command = null;
	
	
    String M_RESERVED =		"WARNING: gid %d is reserved.\r\n"; 
    String M_AUSAGE  =		"ERROR: invalid syntax.\nusage:  groupadd [-g gid [-r]] group\n";
    String M_DUSAGE =		"ERROR: invalid syntax.\nusage:  groupdel group\n";
    String M_MUSAGE =		"ERROR: invalid syntax.\nusage:  groupmod -g gid [-o] | -n name group\n";
    String M_UPDATE = 		"ERROR: Cannot update system files - group cannot be %s.\n";
    String M_GID_INVALID =	"ERROR: %s is not a valid group id.  Choose another.\n";
    String M_GRP_USED =		"ERROR: %s is already in use.  Choose another.\n";
    String M_GRP_INVALID =	"ERROR: %s is not a valid group name.  Choose another.\n";
    String M_NO_GROUP =		"ERROR: %s does not exist.\n";
    String M_TOOBIG =		"ERROR: Group id %d is too big.  Choose another.\n";
    String M_PERM_DENIED =	"ERROR: Permission denied.\n";
    String M_SYNTAX =		"ERROR: Syntax error in group file at line %d.\n";
    
    String U_SUCCESS =		"Ok.\r\n"; 
    String U_RESERVED =		"WARNING: uid %d is reserved.\r\n";
    String U_MAXGROUPS =	"WARNING: more than NGROUPS_MAX(%d) groups specified.\r\n";
    String U_AUSAGE =		"ERROR: invalid syntax.\nusage:  useradd ...\r\n";
    String U_DUSAGE	=		"ERROR: Invalid syntax.\nusage:  userdel [-r] login\r\n";
    String U_MUSAGE	=		"ERROR: Invalid syntax.\nusage:  usermod ...\r\n";
    String U_FAILED	=		"ERROR: Unexpected failure.  Defaults unchanged.\r\n";
    String U_RMFILES =		"ERROR: Unable to remove files from home directory.\r\n";
    String U_RMHOME =		"ERROR: Unable to remove home directory.\r\n";
    String U_UPDATE	=		"ERROR: Cannot update system files - login cannot be %s.\r\n";
    String U_UID_USED =		"ERROR: uid %d is already in use.  Choose another.\r\n";
    String U_USED =			"ERROR: %s is already in use.  Choose another.\r\n";
    String U_EXIST =		"ERROR: %s does not exist.\r\n";
    String U_INVALID =		"ERROR: %s is not a valid %s.  Choose another.\r\n";
    String U_BUSY =			"ERROR: %s is in use.  Cannot %s it.\r\n";
    String U_NO_PERM =		"WARNING: %s has no permissions to use %s.\r\n";
    String U_NOSPACE =		"ERROR: There is not sufficient space to move %s home directory to %s\r\n";
    String U_TOOBIG =		"ERROR: %s %d is too big.  Choose another.\r\n";
    String U_GRP_NOTUSED =	"ERROR: group %s does not exist.  Choose another.\r\n";
    String U_OOPS =			"ERROR: Unable to %s: %s\r\n";
    String U_RELPATH =		"ERROR: %s is not a full path name.  Choose another.\r\n";
    String U_SAME_GRP =		"ERROR: %s is the primary group name.  Choose another.\r\n";
    String U_HOSED_FILES =	"ERROR: Inconsistent password files.  See pwconv(1M).\r\n";
    String U_NONLOCAL =		"ERROR: %s is not a local user.\r\n";
    String U_PERM_DENIED =	"ERROR: Permission denied.\r\n";
    String U_GROUP_ENTRY_OVF =  "WARNING: Group entry exceeds 2048 char: /etc/group entry truncated.\r\n";
    String U_ARUSAGE =		"ERROR: invalid syntax.\nusage:  roleadd ...\r\n";
    String U_DRUSAGE =		"ERROR: Invalid syntax.\nusage:  roledel [-r] login\r\n";
    String U_MRUSAGE =		"ERROR: Invalid syntax.\nusage:  rolemod -u ...\r\n";
    String U_PROJ_NOTUSED =	"ERROR: project %s does not exist.  Choose another.\r\n";
    String U_MAXPROJECTS =	"WARNING: more than NPROJECTS_MAX(%d) projects specified.\r\n";
    String U_PROJ_ENTRY_OVF = "WARNING: Project entry exceeds 512 char: /etc/project entry truncated.\r\n";
    String U_INVALID_KEY =	"ERROR: Invalid key.\r\n";
    String U_INVALID_VALUE =	"ERROR: Missing value specification.\r\n";
    String U_REDEFINED_KEY =	"ERROR: Multiple definitions of key ``%s''.\r\n";
    String U_ISROLE =		"ERROR: Roles must be modified with rolemod\r\n";
    String U_ISUSER =		"ERROR: Users must be modified with usermod\r\n";
    String U_RESERVED_GID =	"WARNING: gid %d is reserved.\r\n";
    String U_READ_ERROR	= 	"ERROR: Failed to read /etc/group file due to invalid entry or read error.\r\n";

	public String Process(String line)
	{
		try {
			args = Parseline(line);
		} catch (Exception e) {
			return ("Invalid command." + e.getMessage());
		}
		if (args == null)
		{
			return null;
		}
		
		/*
		if (command.matches("chage"))			// Used to change the time the user's password will expire.
		{
			return chage();		
		}
		else if (command.matches("groupadd"))	// Create a new group.
		{
			return groupadd();		
		}
		else if (command.matches("groupdel"))	// Delete a group
		{
			return groupdel();		
		}
		else if (command.matches("groupmod"))	// Modify a group
		{
			return groupmod();		
		}
		else if (command.matches("groups"))		// print the groups a user is in
		{
			return groups();	
		}
		else if (command.matches("grpck"))		// Verify the integrity of group files.
		{
			return grpck();		
		}
		else if (command.matches("id"))			// Print group or user ID numbers for the specified user.
		{
			return id();		
		}
		else if (command.matches("newgrp"))		// Allows a user to log in to a new group.
		{
			return newgrp();	
		}
		*/
		if (command.matches("newusers"))	// Update and create new users in batch form.
		{
			return newusers();	
		}
		else if (command.matches("passwd"))		// Used to update a user's password. The command "passwd username" will set the password for the given user.
		{
			return passwd();	
		}
		/*
		"useradd - Create a new user or update default new user information\r\n" + 
		"\r\n" +
		"useradd [-c comment] [-d home_dir]\r\n" +
		"[-e expire_date] [-f inactive_time] \r\n" +
		"[-g initial_group]\r\n" +
		"[-G group[,...]] \r\n" +
		"[-p passwd]\r\n" +
		"[-u uid] \r\n" +
		"\r\n";
		*/
		else if (command.matches("useradd"))		// Create a new user or update default new user information
		{
		    UserAdd useradd = new UserAdd();
			return useradd.command(args);	
		}
		else if (command.matches("userdel"))		// Delete a user account and their files from the system. The command "userdel -r newuser" will remove the user and deletes their home directory.
		{
			return userdel();	
		}
		else if (command.matches("usermod"))		// Modify a user account.
		{
			return userdel();	
		}
		else if (command.matches("help"))		// Modify a user account.
		{
			return help();	
		}
		else if (command.matches("help"))		// Modify a user account.
		{
			return help();	
		}
		else if (command.matches("shutdown"))		// Shutdown SAM monitor
		{
			return stop();	
		}
		else if (command.matches("machine"))		// Shutdown SAM monitor
		{
			return MachineCmds.process(args);
		}
		else if (command.matches("avail") || command.matches("availability"))		// Shutdown SAM monitor
		{
			return AvailabilityCmds.process(args);
		}
		return "Invalid command.";
	}
	

	
	private String chage()		// Used to change the time the user's password will expire. 
	{
		return "Used to change the time the user's password will expire."; 
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
	
	
	private String groupadd()	// Create a new group. 
	{
		
		Getopt g = new Getopt(args, "g:r");
		
	    int gid = -1;				// group id
	    int oflag = 0;			// flags
	    int rc;
	    String gidstr = null;	// gid from command line
	    String grpname = null;			// group name from command line
	    int warning;
	    int c;
	    if (args.length <= 0)
	    {
	    	 return errmsg(UserAccounts.GroupExitCode.EX_SYNTAX, M_AUSAGE);
	    }
	    while ((c = g.getopt()) != -1)
	    {
	     switch (c) {
	     case 'g':
	    	gidstr = g.getOptarg();
	    	break;
	     case 'r':
	      	oflag++;
	      	break;
	     case '?':
	    	 grpname = g.getOptarg();
	     }
	     
	    }
	    if (gidstr != null)
	    try
	    {
	    	gid = Integer.parseInt(grpname);
	    }
	    catch (Exception e)	    	  
	    {
	    	return errmsg(UserAccounts.GroupExitCode.EX_SYNTAX, M_GID_INVALID);
	    }
	    GroupExitCode exit = UserAccounts.groupadd(gid, gidstr);
	    return errmsg(UserAccounts.GroupExitCode.EX_SYNTAX, M_GID_INVALID);
	}
	    
	private String groupdel()	// Delete a group 
	{
		return "Delete a group.";
	}
	private String groupmod()	// Modify a group 
	{
		return "Modify a group.";
	}
	private String groups()		// print the groups a user is in 
	{
		return "Print the groups a user is in.";
	}
	private String grpck()		// Verify the integrity of group files. 
	{
		return "Verify the integrity of group files.";
	}
	private String id()			// Print group or user ID numbers for the specified user. 
	{
		return "Print group or user ID numbers for the specified user.";
	}
	private String newgrp()		// Allows a user to log in to a new group. 
	{
		return "Allows a user to log in to a new group.";
	}
	private String newusers()	// Update and create new users in batch form. 
	{
		return "Update and create new users in batch form.";
	}
	private String passwd()		// Used to update a user's password.
	{
		return "Used to update a user's password.";
	}
	/*
	private String useradd()		// Create a new user or update default new user information 
	{
		Getopt g = new Getopt(args, "g:r");
		 
		String name = null;
		String userName = null;
		String comment = null;
		String homeDir = null;
		Date expireDate = null;
		int inactiveDays = -1;
		int initialGroup = -1;
		String passwd = null;
		int uid = -1;
		String email = null;
		
	    int c;
	    if (args.length <= 0)
	    {
	    	 return errmsg(UserAccounts.UserExitCode.M_DUSAGE, U_AUSAGE);
	    }
	    while ((c = g.getopt()) != -1)
	    {
	     switch (c) {
	     case 'n':	// [-n name]
	    	 name = g.getOptarg();
	    	 break;
	     case 'c':	// [-c comment]
	    	 comment = g.getOptarg();
	    	 break;
	     case 'd':	// [-d home_dir]\r\n" +
	    	 homeDir = g.getOptarg();
	    	 break;
	     case 'e':	// [-e expire_date]
	    	 String expireDateStr = g.getOptarg();
	    	 SimpleDateFormat df1 = new SimpleDateFormat( "dd/MM/yy" );
	         try {
				expireDate = df1.parse(expireDateStr);
			} catch (ParseException e1) {
				return errmsg(UserAccounts.UserExitCode.M_DUSAGE, U_AUSAGE);
			}
	    	 break;
	     case 'f':	// [-f inactive_time] \r\n" +
	    	 try
	    	 {
	    		 inactiveDays = Integer.parseInt(g.getOptarg());
	    	 }
	    	 catch (NumberFormatException e)
	    	 {
	    		 return errmsg(UserAccounts.UserExitCode.M_DUSAGE, U_AUSAGE);
	    	 }
	    	 break;
	     case 'g':	// [-g initial_group]\r\n" +
	    	 try
	    	 {
	    		 initialGroup = Integer.parseInt(g.getOptarg());
	    	 }
	    	 catch (NumberFormatException e)
	    	 {
	    		 return errmsg(UserAccounts.UserExitCode.M_DUSAGE, U_AUSAGE);
	    	 }
	    	 break;
	     //case 'c':	// [-G group[,...]] \r\n" +
	     case 'p':	// [-p passwd]\r\n" +
	    	 passwd = g.getOptarg();
	    	 break;
	     case 'u':	// [-u uid] \r\n" +
	    	 
	    	 try
	    	 {
	    		 uid = Integer.parseInt(g.getOptarg());
	    	 }
	    	 catch (NumberFormatException e)
	    	 {
	    		 return errmsg(UserAccounts.UserExitCode.M_DUSAGE, U_AUSAGE);
	    	 }
	    	 break;
	     case 'm':	// [-m email] \r\n" +
	    	 email = g.getOptarg();
	    	 break;
	     case '?':
	    	 userName = g.getOptarg();
	 
	     }
	     
	    }
	    
	    if (userName == null)
	    {
	    	return errmsg(UserAccounts.UserExitCode.M_DUSAGE, U_AUSAGE);
	    }
	    
		UserAccounts.UserExitCode exit = UserAccounts.useradd(name, userName, comment, homeDir, expireDate, inactiveDays, initialGroup, passwd, uid, email);
	    switch (exit)
	    {
	    case M_UID_USED: 	// ERROR: uid %d is already in use.  Choose another. 
	    	return errmsg(UserAccounts.UserExitCode.M_UID_USED, U_UID_USED); 
	    case M_USED:		// ERROR: %s is already in use.  Choose another.
	    	return errmsg(UserAccounts.UserExitCode.M_USED, U_USED);
	    case M_RESERVED:	// WARNING: uid %d is reserved.
	    	return errmsg(UserAccounts.UserExitCode.M_RESERVED, U_RESERVED);
	    case M_PERM_DENIED:
	    	return errmsg(UserAccounts.UserExitCode.M_PERM_DENIED, U_PERM_DENIED);
	    case M_FAILED:
	    	return errmsg(UserAccounts.UserExitCode.M_FAILED, U_FAILED);
	    }
	    return errmsg(UserAccounts.UserExitCode.M_SUCCESS, U_SUCCESS);
	}
	*/
	private String userdel()		// Delete a user account.
	{
		return "Delete a user account.";
	}
	private String usermod()		// Modify a user account. 
	{
		return "Modify a user account.";
	}
	
	private String stop()		// Modify a user account. 
	{
		TelnetServer.setStopping();
		Samonitor.shutdowm();
		return "Stopping SAMomitor";
	}
	
	private String help()
	{
		String help = 
				/* Only simple user handling required ????
		"\r\n\r\nUser and Group Commands\r\n" +
		"-----------------------\r\n\r\n" +
		"chage    - Used to change the time the user's password will expire.\r\n" + 
		"groupadd - Create a new group.\r\n" +  
		"groupdel - Delete a group.\r\n" +  
		"groupmod - Modify a group.\r\n" +
		"groups   - print the groups a user is in.\r\n" +  
		"id       - Print group or user ID numbers for the specified user.\r\n" +  
		"newgrp   - Allows a user to log in to a new group.\r\n" +  
		"newusers - Update and create new users in batch form.\r\n" +  
		"passwd   - Used to update a user's password.\r\n" +  
		"useradd  - Create a new user or update default new user information\r\n" +  
		"userdel  - Delete a user account\r\n" +  
		"usermod  - Modify a user account.\r\n";
		*/
				"newuser  - Update and create new users in batch form.\r\n" +  
				"passwd   - Used to update a user's password.\r\n" +  
				"useradd  - Create a new user or update default new user information\r\n" +  
				"userdel  - Delete a user account\r\n" +  
				"usermod  - Modify a user account.\r\n";
		return help;

	}
	
	public String errmsg(UserAccounts.UserExitCode exSyntax, String err_txt)
	
    {
     	String pn = "errmsg";
     	
     	String out = String.format("%s: %s %s\n", command, exSyntax.toString(), err_txt);
     	return out;
    }

	public String errmsg(UserAccounts.GroupExitCode exSyntax, String err_txt)
	
    {
     	String pn = "errmsg";
     	
     	String out = String.format("errmsg" + "%s: %s\n" + "error set to %d\n" , command, err_txt, exSyntax);
     	return out;
    }

	private String addString(char[] buffer,int s, int e) {
		String str = new String(buffer,s,e);
		str = str.trim();
		if (str.isEmpty()) return null;
		if (str.charAt(0) == '"') {
			str = str.substring(1,str.length()-1);
		}
		return str;
	}
	
	public String[] Parseline(String line)
	{
		
		int j = 0;
		boolean inStringLitral = false;
		LinkedList<String> argsll = new LinkedList<String>();
		char[] buffer = new char[100];
		
		for (int i = 0; i < line.length(); i++)
		{
			char c = line.charAt(i);
			if (inStringLitral == false)
			{
				if (c == ' ')
				{
					String s = null;
					if ((s = addString(buffer,0,j)) == null) {
						return null;
					}
					argsll.add(s);
					j = 0;
					
				}
			}
			
			if (c == '\"')
			{
				inStringLitral = (inStringLitral)?false:true;
			}
			buffer[j++] = c;
		}
		String s = null;
		if ((s = addString(buffer,0,j)) == null) {
			return null;
		}
		argsll.add(addString(buffer,0,j));
		
		String[] argList = new String[argsll.size()-1];
		command = argsll.get(0);
		
		for (int i = 1; i < argsll.size(); i++)
		{
			argList[i-1] = argsll.get(i);
		}
		
		return argList;
		
		 
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
		"\r\n" +
		"Creating New Users When invoked without the -D option, the useradd command creates a new user\r\n" +
		"account using the values specified on the command line and the default values from the\r\n" +
		"system.\r\n" +
		"The new user account will be entered into the system files as needed.\r\n" +
		"The options which apply to the useradd command are:\r\n" +
		"\r\n" +
		"-c comment\r\n" +
		"The new user's password file comment field. \r\n" +
		"\r\n" +
		"-d home_dir\r\n" + 
		"The new user will be created using home_dir as the value for the user's login directory. The default is to\r\n" +
		"append the login name to default_home and use that as the login directory name.\r\n" + 
		"-e expire_date\r\n" +
		"The date on which the user account will be disabled. The date is specified in the format YYYY-MM-DD. \r\n" +
		"-f inactive_days\r\n" +
		"The number of days after a password expires until the account is permanently disabled. A value of 0\r\n" +
		"disables the account as soon as the password has expired, and a value of -1 disables the feature.\r\n" +
		"The default value is -1. \r\n" +
		"-g initial_group\r\n" +
		"The group name or number of the user's initial login group. The group name must exist.\r\n" +
		"A group number must refer to an already existing group.\r\n" +
		"-G group,[...]\r\n" +
		"A list of supplementary groups which the user is also a member of. Each group is separated from\r\n" +
		"the next by a comma, with no intervening whitespace. The groups are subject to the same\r\n" +
		"restrictions as the group given with the -g option. The default is for the user to belong only\r\n" +
		"to the initial group. \r\n" +
		"\r\n" +
		"-n\r\n" +
		"A group having the same name as the user being added to the system will be created by default.\r\n" +
		"\r\n" +
		"-p passwd\r\n" +
		"The encrypted password, as returned by crypt(3). The default is to disable the account. \r\n" +
		"\r\n" +
		"-u uid\r\n" +
		"The numerical value of the user's ID. This value must be unique, unless the -o option is used.\r\n" +
		"The value must be non-negative. The default is to use the smallest ID value greater than 99\r\n" +
		"and greater than every other user. Values between 0 and 99 are typically reserved for\r\n" +
		"system accounts. \r\n" +
		"Changing the default values\r\n" +
		"When invoked with the -D option, useradd will either display the current default values,\r\n" +
		"or update the default values from the command line. The valid options are \r\n" +
		"\r\n" +
		"-e default_expire_date\r\n" +
		"The date on which the user account is disabled. \r\n" +
		"-f default_inactive\r\n" +
		"The number of days after a password has expired before the account will be disabled. \r\n" +
		"-g default_group\r\n" +
		"The group name or ID for a new user's initial group. The named group must exist, and a\r\n" +
		"numerical group ID must have an existing entry . \r\n";

}


