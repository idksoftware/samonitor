package ipcomms;

import java.util.Date;
import samonitor.SAMNode;


public class SAMReturnedPing extends SAMNode {
	String toAddress = null;
	Date timestamp = null;
	boolean master = false;
	
	public SAMReturnedPing(int samID, String name, int priority, String fromAddress, String toAddress, Date timestamp, boolean master) {
		super(samID, name, priority, fromAddress);
		this.toAddress = toAddress;
		this.timestamp = timestamp;
		this.master = master;
	}

	/**
	 * @return the master
	 */
	public final boolean isMaster() {
		return master;
	}

	/**
	 * @return the timestamp
	 */
	public final Date getTimestamp() {
		return timestamp;
	}

	
	public String getToAddress() {
		
		return toAddress;
	}
}
