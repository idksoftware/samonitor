package samonitor;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;


public class MacAddress {
 
   public static void main(String[] args){
	   System.out.println("Hi There");
 
	InetAddress ip;

	try {
 
		//ip = InetAddress.getLocalHost();
		//ip = InetAddress.getByName("UKNAIW-J1V5Y4J");
		ip = InetAddress.getByName("naiw2fkzs3j");
		System.out.println("Current IP address : " + ip.getHostAddress());
 
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);

		byte[] mac = network.getHardwareAddress();
 
		System.out.print("Current MAC address : ");
 
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
		}
		System.out.println(sb.toString());

	} catch (UnknownHostException e) {
 
		e.printStackTrace();
 
	} catch (SocketException e){
 
		e.printStackTrace();
 
	}
 
   }
 
}