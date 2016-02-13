package telnet;

import java.util.LinkedList;

import samonitor.MachineDetails;
import samonitor.Samonitor;

// machine add -i "Test Machine" -h Robin -a 10.0.0.1 -t 4 -d 60

public class AvailabilityCmds extends TelnetCmds {
	static String id;
	
	public static String process(String[] args) {
		if (args[0].matches("list")) {
			return list();
		}
		if (args[0].matches("history")) {
			return history(args);
		}
		return "Unknown option \"" + args[0] + "\"";
	}
	
	static String list() {
		return Samonitor.availabilityList();
	}
	
	static String history(String[] args) {
		id = null;
		parseArgs(args); 
		return Samonitor.historyItemList(id);
	}
	
	
	static String parseArgs(String[] args) {
		args = removeFirstArg(args);
		Getopt g = new Getopt(args, "-i");
		int c;
        
		while ((c = g.getopt()) != -1)
		{
			 switch(c)
			 {
			 case 'i':
				 id = g.getOptarg();
				 break;
			 case '?':
				 return "Unknown option \"" + args[0] + "\"";
			 default:
				 return "Unknown option \"" + args[0] + "\"";
			 }
		 }
		return null;
	}
	
	
	
}
