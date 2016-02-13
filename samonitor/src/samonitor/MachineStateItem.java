package samonitor;

import samonitor.MachineHistoryItem.MachineHistoryStateItem;

public class MachineStateItem {
	

	MachineHistoryItem machineHistoryItem = null;
	MachineDetails machineDetails = null;
	MachineState machineState = MachineState.Unknown;
	boolean change = false;
	
	MachineStateItem(MachineDetails machineDetails) {
		this.machineDetails = machineDetails;
		machineHistoryItem = new MachineHistoryItem(machineDetails.getId());
	}

	/**
	 * @return the machineState
	 */
	public final MachineState getMachineState() {
		return machineState;
	}

	/**
	 * @param machineState the machineState to set
	 */
	public final void setMachineState(MachineState machineState) {
		if (this.machineState != machineState) {
			change = true;
		}
		this.machineState = machineState;
	}

	/**
	 * @return the id
	 */
	public final String getId() {
		return machineDetails.getId();
	}
	
	public MachineHistoryItem getMachineHistoryItem() {
		return machineHistoryItem;
	}
	
	public void resetChange() {
		change = false;
	}

	/**
	 * @return the change
	 */
	public final boolean isChange() {
		return change;
	}
	
	/**
	 * @return the machineDetails
	 */
	public final MachineDetails getMachineDetails() {
		return machineDetails;
	}
	
	public String toString() {
		String out = 	"Id: " + getId() + "\r\n" +  
						"     State: " + getMachineState().toString() + "\r\n";
		return out;
	}
	
	public String historyString() {
		StringBuilder sb = new StringBuilder("Machine History" + "\r\n");
								   sb.append("---------------" + "\r\n" + "\r\n");
		for (MachineHistoryStateItem it : machineHistoryItem.getHistoryList()) {
			sb.append(it.toString());
		}
		return sb.toString();
	}
} 
