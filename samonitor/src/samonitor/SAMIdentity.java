package samonitor;

import java.net.InetAddress;

// Identity
public class SAMIdentity {
	private static int samID = -1;
	private static String name = null;
	private static int priority = -1;
	private static InetAddress addr;
	
	public SAMIdentity(int samID, String name, int priority, InetAddress addr) {
		SAMIdentity.samID = samID;
		SAMIdentity.name = name;
		SAMIdentity.priority = priority;
		SAMIdentity.addr = addr;
	}
	/**
	 * @return the samID
	 */
	public static final int getSamID() {
		return samID;
	}
	/**
	 * @return the name
	 */
	public static final String getName() {
		return name;
	}
	/**
	 * @return the priority
	 */
	public static final int getPriority() {
		return priority;
	}
	/**
	 * @return the addr
	 */
	public static final InetAddress getAddr() {
		return addr;
	}
}
