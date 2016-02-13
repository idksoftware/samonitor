package samonitor;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//package samonitor;

import java.net.InetAddress;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.ListIterator;
import java.net.UnknownHostException;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.XMLFormatter;
import java.util.logging.FileHandler;

import ntp.NtpConnection;
import ntp.TimeManager;

import org.w3c.dom.Document;

import WebServer.WebServer;

import samonitor.ConfigInfo.NTPServer;
import samonitor.ConfigInfo.Paths;
import samonitor.ConfigInfo.SAMonitor;
import samonitor.ConfigInfo.WEBServer;
import telnet.ConfigFile;

/**
 *
 * @author Iain Ferguson
 */
public class Main {

    static int error = 0;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    	
    	String homePath = System.getenv("SAM_HOME");
        if (homePath == null) {
             Logger.getLogger("App").log(Level.SEVERE, "Can’t read enviroment variable \"SAM_HOME\", check configuration: using \"C:\"");
          return;
        }
    	Logger logger = Logger.getLogger("App");
    	String configFilePath = null;
    	String logFilePath = null;
        FileHandler fh;
        try {

            // This block configure the logger with handler and formatter
        	configFilePath = homePath + File.separator + "config" + File.separator + "config.xml";
        	logFilePath = homePath + File.separator + "logs" + File.separator + "log.xml";
            fh = new FileHandler(logFilePath, true);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            //SAMLogFormatter formatter = new SAMLogFormatter();
            XMLFormatter formatter = new XMLFormatter();
            
            fh.setFormatter(formatter);

            logger.log(Level.INFO,"Logging started");
            
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConfigInfo configInfo = ConfigInfo.create();
        try {
			ConfigInfoReader.decode(configFilePath, configInfo);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        ConfigFile.Save(homePath + File.separator, "telnet.xml");
        
        Samonitor.init(homePath);
        Samonitor.startServers();
        
		if (SAMonitor.isMulti()) {
        	try {
        		Samonitor.announceStationOnline();
        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }
        Samonitor.run();
        Samonitor.shutdowm();
       
    }
  //      

    
}
