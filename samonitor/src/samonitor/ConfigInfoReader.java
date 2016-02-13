package samonitor;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import samonitor.ConfigInfo.NTPServer;
import samonitor.ConfigInfo.Paths;
import samonitor.ConfigInfo.SAMonitor;
import samonitor.ConfigInfo.TelnetServer;
import samonitor.ConfigInfo.WEBServer;

import xmlutils.XMLReadHelper;
import xmlutils.XMLUtil;


public class ConfigInfoReader {
	public static void decode(String fileName, ConfigInfo configInfo) throws ParseException, IOException {

		final Document document = XMLUtil.parse(fileName);
		final Element root = document.getDocumentElement();
		
		Element samonoitorElem = XMLUtil.getElement(root, "SAMonitor");
		final boolean multi = XMLReadHelper.booleanValue(samonoitorElem, "Multi", true, false);
		final int id = XMLReadHelper.integerValue(samonoitorElem, "Id", true, false);
		final String name = XMLReadHelper.stringValue(samonoitorElem, "Name", true);
		final int priority = XMLReadHelper.integerValue(samonoitorElem, "Priority", true, false);
		final String levelStr = XMLReadHelper.stringValue(samonoitorElem, "LogLevel", true);
		Level level = Level.parse(levelStr);
		SAMonitor.setSAMonitor(id, name, multi, priority, level);
		
		Element webServerElem = XMLUtil.getElement(root, "WEBServer");		
		boolean state = XMLReadHelper.booleanValue(webServerElem, "State", true, false);
		int port = XMLReadHelper.integerValue(webServerElem, "Port", true, false);
		WEBServer.setWEBServer(state, port);
		
		Element npsServerElem = XMLUtil.getElement(root, "Ntp");		
		state = XMLReadHelper.booleanValue(npsServerElem, "State", true, false);
		String address = XMLReadHelper.stringValue(npsServerElem, "IpAddress", true);
		NTPServer.setNTPServer(state, address);
		
		Element telnetElem = XMLUtil.getElement(root, "Telnet");		
		state = XMLReadHelper.booleanValue(telnetElem, "State", true, false);
		port = XMLReadHelper.integerValue(telnetElem, "Port", true, false);
		int timeout = XMLReadHelper.integerValue(telnetElem, "Timeout", true, false);
		int maxConnections = XMLReadHelper.integerValue(telnetElem, "MaxConnections", true, false);

		TelnetServer.setTelnetServer(state, port, timeout, maxConnections);
		
		Element pathElem = XMLUtil.getElement(root, "Paths");
		String rootPath = XMLReadHelper.stringValue(pathElem, "RootPath", true);
		String tempPath = XMLReadHelper.stringValue(pathElem, "TempPath", true);
		String configPath = XMLReadHelper.stringValue(pathElem, "ConfigPath", true);
		String systemPath = XMLReadHelper.stringValue(pathElem, "SystemPath", true);
		String schemaPath = XMLReadHelper.stringValue(pathElem, "SchemaPath", true);
		String scriptsPath = XMLReadHelper.stringValue(pathElem, "ScriptsPath", true);
		String xslPath = XMLReadHelper.stringValue(pathElem, "XslPath", true);
		String cssPath = XMLReadHelper.stringValue(pathElem, "CssPath", true);
		String dataPath = XMLReadHelper.stringValue(pathElem, "DataPath", true);
		String logPath = XMLReadHelper.stringValue(pathElem, "LogPath", true);
		String websitePath = XMLReadHelper.stringValue(pathElem, "WebsitePath", true);
		
		Paths.setPaths(
				configPath,
				dataPath,
		//static private Level logLevel = Level.ALL;
				logPath,
				rootPath,
				schemaPath,
				scriptsPath,
				systemPath,
				tempPath,
				websitePath,
				xslPath,
				cssPath
				);
		

	}
}
