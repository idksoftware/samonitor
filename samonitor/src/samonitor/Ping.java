package samonitor;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Ping {
	
	static int error = 0;
	private Logger logger = Logger.getLogger("App");
	
	public MachineState Process(MachineDetails md)
	{
		boolean status = false;
	    String host = md.getHostName();
	    String address = md.getAddressString();
	    int timeOut = md.getDelay();
	    timeOut = 3000;
	    MachineState state = MachineState.Unknown;
	    InetAddress ip = null;
	    
        try
        {
        	if (host != null)
        	{
        		ip = InetAddress.getByName(host);
        		if (address == null) {
        			md.setAddressString(ip.getHostAddress());
        		}
        	} else if (address != null) {
        		ip = InetAddress.getByName(address);
        		if (host == null) {
        			md.setHostName((ip.getHostName()));
        		}
        	}
        	status = ip.isReachable(timeOut);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            error = -1;
            state = MachineState.Error;
            status = false;
        }
        if (status)
        {
        	state = MachineState.Reachable;
            //System.out.println("ICMP ping successfull\n");
            //LogRecord lr = new LogRecord();
            //logger.log(Level.WARNING,"ICMP ping successfull"); 
        }
        else
        {
        	state = MachineState.NotReachable;
            //System.out.println("ICMP ping unsuccessfull\n");
            logger.log(Level.WARNING,"ICMP ping unsuccessfull"); 
        }
		return state;
	}
}
