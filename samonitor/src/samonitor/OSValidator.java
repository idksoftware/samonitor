package samonitor;

public class OSValidator {
	enum eOSType {
		OS_Windows,
		OS_Solaris,
		OS_Linux,
		OS_Mac,
		OS_UNIX,
		OS_Unknown
	}
	private String os = null;
	private eOSType OSType = eOSType.OS_Unknown;
	
	public OSValidator()
	{
		String os = System.getProperty("os.name").toLowerCase();
		//windows
		if ((os.indexOf("win")) >= 0)
		{
			OSType = eOSType.OS_Windows;
		}
		else if ((os.indexOf("lin")) >= 0)
		{
			OSType = eOSType.OS_Linux;
		}
		else if((os.indexOf("mac")) >= 0)
		{
			OSType = eOSType.OS_Mac;
		}
		else if ((os.indexOf("sol")) >= 0)
		{
			OSType = eOSType.OS_Solaris;
		}
		else if (((os.indexOf("hp-ux")) >= 0)	  	||
				 (( os.indexOf("aix")) >= 0 ) 	 	||
				 (( os.indexOf("freebsd")) >= 0 )	||
				 (( os.indexOf("irix")) >= 0 ) 		||
				 (( os.indexOf("digital")) >= 0 ))
		    
		{		  
			OSType = eOSType.OS_UNIX;
		}
		else
		{
			OSType = eOSType.OS_Unknown;	
		}
	}
	
	public String GetOSString()
	{
		return os;
	}
	private eOSType GetOSType()
	{
		return OSType;
	}
	
	public String DefaultInstallPath()
	{
		if (OSType == eOSType.OS_Windows)
		{
			return "C:\\Program Files\\";
		}
		else
		{
			return "/usr/local/bin/";
		}
	}
}
