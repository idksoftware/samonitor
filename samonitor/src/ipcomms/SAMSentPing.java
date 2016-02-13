package ipcomms;

import java.util.Date;

import samonitor.SAMNode;

public class SAMSentPing extends SAMNode {

	Date timestamp = null;
	boolean master = false;
	
	public SAMSentPing(int samID, String name, int priority, String address, Date timestamp, boolean master) {
		super(samID, name, priority, address);
		this.timestamp = timestamp;
		this.master = master;
		
	}

	
	/**
	 * @return the timestamp
	 */
	public final Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the master
	 */
	public final boolean isMaster() {
		return master;
	}
}
