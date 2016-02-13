package ipcomms;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

import samonitor.SAMIdentity;
import samonitor.SAMNode;
import samonitor.SAMStations;
import samonitor.SAMStationsList;
import samonitor.Samonitor;

import xmlutils.DateUtils;

public class MachinePing {
	SAMStationsList list = null;
	
	public MachinePing() {
		list = SAMStations.get();
	}
	
	public void pingMessageOut(String address) {
		
		DatagramSocket socket = null;
		
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	
        for (int i = 0; i < 4; i++) {
            try {
                byte[] buf = new byte[256];
                Date timeStamp = new Date();
                SAMSendPingMessage message = new SAMSendPingMessage(SAMIdentity.getSamID(), SAMIdentity.getName(),
                					SAMIdentity.getPriority(), SAMIdentity.getAddr().getHostAddress(), timeStamp, false);
                buf = message.makeMessage().getBytes();
		// send the response to the client at "address" and "port"
                SAMStationsList list = SAMStations.get();
                for (SAMNode item : list) {
                	InetAddress sendAddress = InetAddress.getByName(item.getAddress());
                	DatagramPacket packet = new DatagramPacket(buf, buf.length, sendAddress, 4445);
                	socket.send(packet);
                }
            } catch (IOException e) {
                e.printStackTrace();
		
            }
        }
        socket.close();
		return;
	}
	
	
}
