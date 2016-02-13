package samonitor;
import ipcomms.MachinePing;
import ipcomms.OnLineAnnouncement;
import ipcomms.SAMListener;
import ipcomms.SAMResponderThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import ntp.NtpConnection;
import ntp.TimeManager;
import samonitor.ConfigInfo.NTPServer;
import samonitor.ConfigInfo.Paths;
import samonitor.ConfigInfo.SAMonitor;
import samonitor.ConfigInfo.WEBServer;
import WebServer.WebServer;



public class Samonitor {
	static private MachineStateTable machineStateTable = null;
	static private MachinesFile machinesFile = null;
	static SAMListener samListener = null;
	static Ping ping = null;
	static boolean pauseOn = false; 
	static boolean isPaused = false; 
	static boolean isShuttingdowm = false; 
	static MachinePing machinePing = null;
	static SAMStationsList stationsList = null;
	
	/**
	 * @return the samNodesList
	 */
	public static final SAMStationsList getStationsList() {
		return stationsList;
	}

	static void init(String homePath) {
		
		setIdentity();
		stationsList = SAMStations.get();
		machinePing = new MachinePing();
		ping = new Ping();
        try
        {
            machinesFile = MachinesFile.load(homePath + File.separator + "config" + File.separator + "machines.xml");
            try
            {
                machinesFile.decode();
            }
            catch (ParseException e)
            {
                System.out.println("Parse Exception\n");
                return;
            }
        }
        catch (IOException e)
        {
            System.out.println("File not found\n");
            return;
        }
        machineStateTable = new MachineStateTable();
        MachineProperties mp = machinesFile.GetMachineProperties();
        for (MachineDetails d : mp) {
        	d.setMachineStateItem(machineStateTable.add(d));
        }
        
        
        for (MachineDetails d : mp) {
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		MachineStateItem item = d.getMachineStateItem();
    		item.setMachineState(ping.Process(d));
    	}
        
	}
	
	static void startMultiStationMode() {
		// This server responds to any requests on the multicast socket normally 4446
				// This will respond to a OnLine Announcement message
				new SAMResponderThread().start();
	}
	
	
	static void startServers() {
		
		
		
		// This server listens on the normal directed port. i.e an external station has send a UDP
		// packet to this station.
		samListener = new SAMListener();
        samListener.start();
		
		if (WEBServer.getState() == true) {
	        WebServer.SetConfigPath(Paths.getWebsitePath());
	        try
	        {
	        	//WebServer.Run("8090");
	        	WebServerManager.Start(WEBServer.getPort());
	        }
	        catch (Exception e) {
	        	System.out.println(e.getMessage());
	        }
        }
		if (NTPServer.getState() == true) {
	        try
	        {
	        	NtpConnection ntpConnection= 
	        	      new NtpConnection(InetAddress.getByName("ntp2c.mcc.ac.uk"));
	        	TimeManager timeManager = TimeManager.getInstance();
	        	Date now = ntpConnection.getTime();
	        	timeManager.setTime(now);
	        }
	        catch (Exception e)
	        {
	        	System.out.println(e.getMessage());
	        }
		}
		TelnetServerManager.create();
	}
	
	private static void setIdentity()
	{
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new SAMIdentity(SAMonitor.getId(), SAMonitor.getName(), SAMonitor.getPriority(), addr);
	}
	
	static void announceStationOnline() throws IOException {
		OnLineAnnouncement ola = new OnLineAnnouncement();
		ola.announce();
	}
	
	static void run() {
		
		Ping ping = new Ping();
    	MachineProperties mp = machinesFile.GetMachineProperties();
    	
        while(true)
        {
        	if (pauseOn == true) {
        		isPaused = true;
        		while (pauseOn == true) {
        			if (isShuttingdowm == true) {
        				break;
        			}
        			try {
        				Thread.sleep(1000);
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        		}
        		isPaused = false;
        	}
        	if (mainLoop(ping, mp) == true) {
        		break;
        	}
        	
			
    		// test only end
    		try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	machinePing.pingMessageOut(SAMIdentity.getAddr().getHostAddress());
        }
        
        
	}
	
	private static boolean mainLoop(Ping ping, MachineProperties mp) {
		
		for (MachineDetails d : mp) {
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		MachineStateItem item = d.getMachineStateItem();
    		item.setMachineState(ping.Process(d));
    	}
    	machineStateTable.process();
    	if (TelnetServerManager.isStopping() == true) {
    		try {
				machineStateTable.closeHistory();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return true;
    	}
    	
    	return false;
	}
	
	public static void writeMachineStateTable() {
		try {
			machineStateTable.write("c:\\Temp\\StateTable.xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void shutdowm() {
		isShuttingdowm = true;
		System.out.println("Ending\n");
		writeMachineStateTable();
		   //     WebServerManager.Stop();
		TelnetServerManager.Stop();
	}
	
	
	/**
	 * @return the isPaused
	 */
	public static final boolean isPaused() {
		return isPaused;
	}
	/**
	 * @param pauseOn the pauseOn to set
	 */
	public static final void setPauseOn(boolean pauseOn) {
		Samonitor.pauseOn = pauseOn;
	}
	
	public static String availabilityList() {
		return machineStateTable.writeString();
	}
	
	public static String historyItemList(String id) {
		MachineStateItem tmp;
		if ((tmp = machineStateTable.getMachineStateItem(id)) == null) {
			return null;
		}
		return tmp.getMachineHistoryItem().historyListToString();
	}
	
	
	public static String machineList() {
		MachineProperties tmp = machinesFile.GetMachineProperties();
		return tmp.toTextString();	
	}
	public static String machineShow() {
		MachineProperties tmp = machinesFile.GetMachineProperties();
		return tmp.toTextString();	
	}
	
	public static boolean machineAdd(MachineDetails machineDetails) {
		MachineProperties tmp = machinesFile.GetMachineProperties();
		if (tmp.isDup(machineDetails)) {
			return false;
		}
		tmp.add(machineDetails);
		return true;
	}
	public static boolean machineRemove(String id) {
		MachineProperties tmp = machinesFile.GetMachineProperties();
		MachineDetails machineDetails = null;
		if ((machineDetails = tmp.find(id)) == null) {
			return false;
		}
		tmp.remove(machineDetails);
		return true;
	}
	public static boolean machineEdit(MachineDetails newDetails) {
		MachineProperties tmp = machinesFile.GetMachineProperties();
		MachineDetails machineDetails = null;
		if ((machineDetails = tmp.find(newDetails.getId())) == null) {
			return false;
		}
		machineDetails.edit(newDetails);
		return true;
	}
}
