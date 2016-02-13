package telnet.user.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import telnet.user.UserAccounts;
import telnet.user.commands.Getopt;

public class UserAdd extends UserCommand {
	
	public String command(String args[])		// Create a new user or update default new user information 
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
	    case M_UID_USED: 	/* ERROR: uid %d is already in use.  Choose another. */
	    	return errmsg(UserAccounts.UserExitCode.M_UID_USED, U_UID_USED); 
	    case M_USED:		/* ERROR: %s is already in use.  Choose another. */
	    	return errmsg(UserAccounts.UserExitCode.M_USED, U_USED);
	    case M_RESERVED:	/* WARNING: uid %d is reserved. */
	    	return errmsg(UserAccounts.UserExitCode.M_RESERVED, U_RESERVED);
	    case M_PERM_DENIED:
	    	return errmsg(UserAccounts.UserExitCode.M_PERM_DENIED, U_PERM_DENIED);
	    case M_FAILED:
	    	return errmsg(UserAccounts.UserExitCode.M_FAILED, U_FAILED);
	    }
	    return errmsg(UserAccounts.UserExitCode.M_SUCCESS, U_SUCCESS);
	}
}
