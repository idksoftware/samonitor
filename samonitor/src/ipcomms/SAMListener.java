package ipcomms;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;

import samonitor.SAMIdentity;
import samonitor.SAMNode;
import samonitor.SAMStations;
import samonitor.SAMStationsList;
import xmlutils.DateUtils;

/**
 * Response Listener, listens on 4445 (non Multicast UDP socket. All non
 * Multicast UDP messages will be picked up here. 
 * @author 501726576
 *
 */
// 
public class SAMListener  extends Thread {
	
	DatagramSocket socket = null;
	boolean shutdown = false;
	SAMStationsList stationsList = SAMStations.get();
	
	Date timestamp = new Date();
	boolean master = false;
	
	public SAMListener() {
		try {
			socket = new DatagramSocket(4445);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		DatagramPacket packet = null;
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
		    System.out.println("SAMFinderResponseListener: " + msg);
		 
		    try {
		    	UDPMessage udpMessage = UDPMessageDecoder.decode(msg);
				if(udpMessage instanceof SAMNodeMessage) {
					SAMNode samNode = ((SAMNodeMessage) udpMessage).getMessage();
					if (stationsList.isPresent(samNode) == false) {
						stationsList.add(samNode);
					}
				}
				if(udpMessage instanceof SAMSendPingMessage) {
					SAMSentPing sentPing = (SAMSentPing) ((SAMSendPingMessage) udpMessage).getMessage();
					InetAddress sendAddress = InetAddress.getByName(sentPing.getAddress());
					if (sendAddress.equals(SAMIdentity.getAddr()) == true) {
						continue;
					}
					SAMReturnedPing returnedPing = new SAMReturnedPing(SAMIdentity.getSamID(), SAMIdentity.getName(), SAMIdentity.getPriority(), sentPing.getAddress(), sentPing.getAddress(), timestamp, master);
					buf = (new SAMReturnPingMessage(returnedPing)).makeMessage().getBytes();
	             	
					DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, sendAddress, 4445);
					socket.send(sendPacket);	
				}
			
				
				if(udpMessage instanceof SAMReturnPingMessage) {
					SAMReturnedPing returnedPing = (SAMReturnedPing) ((SAMReturnPingMessage) udpMessage).getMessage();
					System.out.println("Returns ping: " + returnedPing.getSamID()); 
	                
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		
		socket.close();
	}
	
}
