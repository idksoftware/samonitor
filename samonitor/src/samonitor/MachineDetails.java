package samonitor;

public class MachineDetails {
	private String id = null;
	private String hostName = null;
	private String addressString = null;
	private String macAddress = null;
	private int trys = 0;
	private int delay = 0;
	private MachineStateItem machineStateItem = null;
	
	public String toTextString() {
		String out = 	"Id: " + id + "\r\n" +  
						"  Hostname: " + hostName + "\r\n" +  
						"   Address: " + addressString + "\r\n" +  
						"      Trys: " + trys + "\r\n" +  
						"     Delay: " + delay + "\r\n";
		
		return out;
	}
	
	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}
	
	public static String padLeft(String s, int n) {
		return String.format("%1$#" + n + "s", s);
	}

	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the hostName
	 */
	public final String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName the hostName to set
	 */
	public final void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the addressString
	 */
	public final String getAddressString() {
		return addressString;
	}

	/**
	 * @param addressString the addressString to set
	 */
	public final void setAddressString(String addressString) {
		this.addressString = addressString;
	}

	/**
	 * @return the trys
	 */
	public final int getTrys() {
		return trys;
	}

	/**
	 * @param trys the trys to set
	 */
	public final void setTrys(int trys) {
		this.trys = trys;
	}

	/**
	 * @return the delay
	 */
	public final int getDelay() {
		return delay;
	}

	/**
	 * @param delay the delay to set
	 */
	public final void setDelay(int delay) {
		this.delay = delay;
	}

	/**
	 * @return the machineStateItem
	 */
	public final MachineStateItem getMachineStateItem() {
		return machineStateItem;
	}

	/**
	 * @param machineStateItem the machineStateItem to set
	 */
	public final void setMachineStateItem(MachineStateItem machineStateItem) {
		this.machineStateItem = machineStateItem;
	}  
	
	/**
	 * @return the macAddress
	 */
	public final String getMacAddress() {
		return macAddress;
	}

	/**
	 * @param macAddress the macAddress to set
	 */
	public final void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public void edit(MachineDetails newDetails) {
		if (newDetails.id == null) {
			return;
		}
		this.id = newDetails.id;
		if (newDetails.hostName == null) {
			this.hostName = newDetails.hostName;
		}
		if (newDetails.addressString == null) {
			this.addressString = newDetails.addressString;
		}
		if (newDetails.macAddress == null) {
			this.macAddress = newDetails.macAddress;
		}
		if (newDetails.trys == 0) {
			this.trys = newDetails.trys;
		}
		if (newDetails.delay == 0) {
			this.delay = newDetails.delay;
		}
		
	}
}
