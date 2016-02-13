package ipcomms;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;


import samonitor.SAMIdentity;
import samonitor.SAMNode;
import samonitor.SAMStations;
import samonitor.SAMStationsList;
import samonitor.Samonitor;

/**
 * This class Responds to a announcement from a remote station
 * @author FergusonI
 *
 */

public class SAMResponderThread extends Thread {
	boolean shutdown = false;
	SAMStationsList list = null;

	
	public SAMResponderThread() {
		list = Samonitor.getStationsList();
	}
	

	public void run() { 
		
		MulticastSocket socket = null;
		try {
			socket = new MulticastSocket(4446);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InetAddress group = null;
		try {
			group = InetAddress.getByName("224.0.1.200");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket.joinGroup(group);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DatagramPacket packet;
		while (!shutdown) {
		    byte[] buf = new byte[256];
		    packet = new DatagramPacket(buf, buf.length);
		    try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    String received = new String(packet.getData());
		    String msg = received.substring(0, packet.getLength());
		    System.out.println("SAMResponderThread: " + msg);
		    try {
				UDPMessage udpMessage = UDPMessageDecoder.decode(msg);
				if(udpMessage instanceof SAMNodeMessage) {
					SAMNode samNode = ((SAMNodeMessage) udpMessage).getMessage();
					if (list.isPresent(samNode) == false) {
						list.add(samNode);
						returnThisIntroMessage(samNode.getAddress());
						
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			socket.leaveGroup(group);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket.close();
	} 
	
	

	
	
	void returnThisIntroMessage(String address) {
		
		DatagramSocket socket = null;
		
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		String returnValue = (new SAMOnlineMessage(new SAMOnline())).makeMessage();
    	
        for (int i = 0; i < 10; i++) {
            try {
                byte[] buf = new byte[256];
                buf = returnValue.getBytes();
		// send the response to the client at "address" and "port"
                InetAddress sendAddress = InetAddress.getByName(address);
                DatagramPacket packet = new DatagramPacket(buf, buf.length, sendAddress, 4445);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
		
            }
        }
        socket.close();
		return;
	}
}
