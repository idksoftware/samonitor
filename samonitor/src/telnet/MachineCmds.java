package telnet;

import java.util.LinkedList;

import samonitor.MachineDetails;
import samonitor.Samonitor;

// machine add -i "Test Machine" -h Robin -a 10.0.0.1 -t 4 -d 60

public class MachineCmds extends TelnetCmds {
	
	public static String process(String[] args) {
		if (args[0].matches("list")) {
			return list();
		}
		if (args[0].matches("add")) {
			return add(args);
		}
		if (args[0].matches("remove")) {
			return add(args);
		}
		if (args[0].matches("edit")) {
			return edit(args);
		}
		return "Unknown option \"" + args[0] + "\"";
	}
	static String list() {
		return Samonitor.machineList();
	}
	
	
	static String parseArgs(String[] args, MachineDetails machineDetails) {
		args = removeFirstArg(args);
		Getopt g = new Getopt(args, "-iahdt");
		int c;
		
        
		while ((c = g.getopt()) != -1)
		{
			 switch(c)
			 {
			 case 'i':
				 machineDetails.setId(g.getOptarg());
				 break;
			 case 'a':
				 machineDetails.setAddressString(g.getOptarg());
				 break;
			 case 'd':
			     {
				 int delay = 0;
				 try {
					 delay = Integer.parseInt(g.getOptarg());
					 machineDetails.setDelay(delay);
				 } catch (NumberFormatException e) {
					 return "option \"Delay\" needs to be a number";
				 }
			 	 }
				 break;
			 case 'h':
				 machineDetails.setHostName(g.getOptarg());
				 break;
			 case 't':
			     {
			     int trys = 0;
				 try {
					 trys = Integer.parseInt(g.getOptarg());
					 machineDetails.setTrys(trys);
				 } catch (NumberFormatException e) {
					 return "option \"Trys\" needs to be a number";
				 }
			     }
				 break;	 
			 case '?':
				 return "Unknown option \"" + args[0] + "\"";
			 default:
				 return "Unknown option \"" + args[0] + "\"";
			 }
		 }
		return null;
	}
	
	static String add(String[] args) {
		
		MachineDetails machineDetails = new MachineDetails();
		String out = null;
		if ((out = parseArgs(args, machineDetails)) != null) {
			return out;
		}
		
		pauseProcessing();
		if (Samonitor.machineAdd(machineDetails) == false) {
			return "Not add? \"" + machineDetails.getId() + "\" is duplicated";
		}
		unpauseProcessing();
		return "Successfully added \"" + machineDetails.getId() + "\"";
	}
	
	static String edit(String[] args) {
		MachineDetails machineDetails = new MachineDetails();
		String out = null;
		if ((out = parseArgs(args, machineDetails)) != null) {
			return out;
		}
		
		pauseProcessing();
		if (Samonitor.machineEdit(machineDetails) == false) {
			return "Not add? \"" + machineDetails.getId() + "\" is duplicated";
		}
		unpauseProcessing();
		return "Successfully added \"" + machineDetails.getId() + "\"";
	}
	
	static String remove(String[] args) {
		if (args.length < 2) {
			return "Machine id required";
		}
		pauseProcessing();
		Samonitor.machineRemove(args[1]);
		unpauseProcessing();
		return "Successfully removed \"" + args[1] + "\"";
	}
	
	static void pauseProcessing() {
		Samonitor.setPauseOn(true);
		while (Samonitor.isPaused() == false) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	static void unpauseProcessing() {
		Samonitor.setPauseOn(false);
	}
}
