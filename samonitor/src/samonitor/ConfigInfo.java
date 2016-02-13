package samonitor;
import java.util.logging.Level;






/**
 * This class is used to provide configurations information to all classes
 * within the application that need this information. This information is read
 * once out of a file. but maybe further updated within the application. There
 * is only one copy of this information therefore it is a static class.
 * 
 * @author Iain Ferguson
 */
public final class ConfigInfo {
	
	static ConfigInfo thisConfigInfo = null;
	
	static private SAMonitor samonitor = new SAMonitor();
	static private WEBServer webServer = new WEBServer();
	static private Paths paths = new Paths();
	
	static public final String SAM_HOME = "SAM_HOME";
	//static public final String SAMONITOR = "SAMonitor";
	static public final String IMGARCHIVE_WIN = "IDK SAMonitor";
	static public final String TEMPLATES_FOLDER = "Templates";
	static public final String CONFIG_DIR_NAME = "config";
	static public final String CONFIG_FILE_NAME = "config.xml";

	
	static public class SAMonitor {
		static private int id = 0;
		static private boolean multi = false;
		static private String name = null;
		static private int priority = 0;
		static private Level logLevel = Level.ALL;
		
		static public void setSAMonitor(int id, String name, boolean multi, int priority, Level logLevel) {
			SAMonitor.id = id;
			SAMonitor.name = name;
			SAMonitor.multi = multi;
			SAMonitor.priority = priority;
			SAMonitor.logLevel = logLevel;
		}
		
		public static boolean isMulti() {
			return multi;
		}

		public static void setMulti(boolean multi) {
			SAMonitor.multi = multi;
		}

		/**
		 * @return the id
		 */
		public static final int getId() {
			return id;
		}
		/**
		 * @return the name
		 */
		public static final String getName() {
			return name;
		}

		/**
		 * @return the priority
		 */
		public static final int getPriority() {
			return priority;
		}
		
		/**
		 * @return the logLevel
		 */
		public static final Level getLogLevel() {
			return logLevel;
		}
	}
	
	static public class WEBServer {
		static private boolean state = false;
		static private int port = 0;
		
		
		static public void setWEBServer(boolean state, int port) {
			WEBServer.state = state;
			WEBServer.port = port;
			
		}

		/**
		 * @return the name
		 */
		public static final boolean getState() {
			return state;
		}

		/**
		 * @return the priority
		 */
		public static final int getPort() {
			return port;
		}
		
		
	}
	
	static public class NTPServer {
		static private boolean state = false;
		static private String address = null;
		
		static public void setNTPServer(boolean state, String address) {
			NTPServer.state = state;
			NTPServer.address = address;
			
		}

		/**
		 * @return the name
		 */
		public static final boolean getState() {
			return state;
		}

		/**
		 * @return the priority
		 */
		public static final String getAddress() {
			return address;
		}
	}
	
	static public class TelnetServer {
		static private boolean state = false;
		static private int port = 0;
		static private int timeout = 10;
		// The maximum number of telnet sessions allowed on at one time. 
		static private int maxConnections = 10;
		static public void setTelnetServer(boolean state, int port, int timeout, int maxConnections) {
			TelnetServer.state = state;
			TelnetServer.port = port;
			
		}

	
		/**
		 * @return the name
		 */
		public static final boolean getState() {
			return state;
		}

		/**
		 * @return the priority
		 */
		public static final int getPort() {
			return port;
		}


		/**
		 * @return the timeout
		 */
		public static final int getTimeout() {
			return timeout;
		}


		/**
		 * @return the maxConnections
		 */
		public static final int getMaxConnections() {
			return maxConnections;
		}
	}
	
	public static final ConfigInfo create() {
		if (thisConfigInfo == null) {
			thisConfigInfo = new ConfigInfo();
		}
		return thisConfigInfo;
	}
	
	
	static public class Paths {
		static private String configPath = null;
		static private String dataPath = null;
		//
		static private String logPath = null;
		static private String rootPath = null;
		static private String schemaPath = null;
		static private String scriptPath = null;
		static private String systemPath = null;
		static private String tempPath = null;
		static private String websitePath = null;
		static private String xslPath = null;
		static private String cssPath = null;
		
		static public void setPaths(
				String configPath,
				String dataPath,
		//static private Level logLevel = Level.ALL;
				String logPath,
				String rootPath,
				String schemaPath,
				String scriptPath,
				String systemPath,
				String tempPath,
				String websitePath,
				String xslPath,
				String cssPath
				) {
			Paths.configPath = configPath;
			Paths.dataPath = dataPath;
	//static private Level logLevel = Level.ALL;
			Paths.logPath = logPath;
			Paths.rootPath = rootPath;
			Paths.schemaPath = schemaPath;
			Paths.scriptPath = scriptPath;
			Paths.systemPath = systemPath;
			Paths.systemPath = systemPath;
			Paths.systemPath = systemPath;
			Paths.xslPath = xslPath;
			Paths.cssPath = cssPath;
		}
		
		/**
		 * @return the configPath
		 */
		public static final String getConfigPath() {
			return configPath;
		}
		/**
		 * @return the dataPath
		 */
		public static final String getDataPath() {
			return dataPath;
		}
		
		/**
		 * @return the logPath
		 */
		public static final String getLogPath() {
			return logPath;
		}
		/**
		 * @return the rootPath
		 */
		public static final String getRootPath() {
			return rootPath;
		}
		/**
		 * @return the schemaPath
		 */
		public static final String getSchemaPath() {
			return schemaPath;
		}
		/**
		 * @return the scriptPath
		 */
		public static final String getScriptPath() {
			return scriptPath;
		}
		/**
		 * @return the systemPath
		 */
		public static final String getSystemPath() {
			return systemPath;
		}
		/**
		 * @return the tempPath
		 */
		public static final String getTempPath() {
			return tempPath;
		}
		/**
		 * @return the websitePath
		 */
		public static final String getWebsitePath() {
			return websitePath;
		}
		/**
		 * @return the xslPath
		 */
		public static final String getXslPath() {
			return xslPath;
		}
		/**
		 * @return the cssPath
		 */
		public static final String getCssPath() {
			return cssPath;
		}
	}
	
	/**
	 * @return the samonitor
	 */
	static final SAMonitor getSamonitor() {
		return samonitor;
	}
	/**
	 * @return the WEBServer
	 */
	static final WEBServer getWEBServer() {
		return webServer;
	}
	/**
	 * @return the WEBServer
	 */
	static final Paths getPaths() {
		return paths;
	}
	/**
	 * @return the imgarchiveWin
	 */
	static final String getImgarchiveWin() {
		return IMGARCHIVE_WIN;
	}
	
	
	
	
}
