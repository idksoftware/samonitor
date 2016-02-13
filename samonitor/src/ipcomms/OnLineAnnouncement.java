package ipcomms;

import java.io.*;
import java.net.*;
import java.util.*;

import samonitor.SAMIdentity;
// Introduction Announcement
// 
public class OnLineAnnouncement {
	protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    //protected String samID = "01";
    //protected int priority = 99;
    //protected String address = null;
    
    public OnLineAnnouncement() throws IOException {
    	
    }
 
    public void announce() {
    	try {
			socket = new DatagramSocket();
		} catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	
        for (int i = 0; i < 10; i++) {
            try {
                byte[] buf = new byte[256];
                buf = (new SAMOnlineMessage(new SAMOnline())).makeMessage().getBytes();
                System.out.println("Sending from new SAM: " + (new SAMOnlineMessage()).makeMessage());
		// send the response to the client at "address" and "port"
                InetAddress group = InetAddress.getByName("224.0.1.200");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
		
            }
        }
        socket.close();
    }

    
}

