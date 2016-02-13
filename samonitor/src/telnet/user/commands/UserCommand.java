package telnet.user.commands;

import telnet.user.UserAccounts;

public class UserCommand {
	
	String command = null;
	
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

    protected String errmsg(UserAccounts.UserExitCode exSyntax, String err_txt)
    {
     	String out = String.format("%s: %s %s\n", command, exSyntax.toString(), err_txt);
     	return out;
    }

   
    
}
