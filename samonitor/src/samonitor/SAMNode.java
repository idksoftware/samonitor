package samonitor;


public class SAMNode {
	private int samID = -1;
	private String name = null;
	private int priority = -1;
	private String address = null;
	
	
	public SAMNode(int samID, String name, int priority, String address) {
		this.samID = samID;
		this.name = name;
		this.priority = priority;
		this.address = address;
	}
	
	/**
	 * @return the samID
	 */
	public final int getSamID() {
		return samID;
	}
	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}
	/**
	 * @return the priority
	 */
	public final int getPriority() {
		return priority;
	}
	/**
	 * @return the address
	 */
	public final String getAddress() {
		return address;
	}
}
