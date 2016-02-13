package samonitor;
//package samonitor;
import java.util.*;



@SuppressWarnings("serial")
public class MachineProperties extends LinkedList<MachineDetails> {
	
	public MachineProperties() { 
		
	}
	public String toTextString() {
		StringBuilder sb = new StringBuilder("\r\nMachine List\r\n");
								   sb.append("------------\r\n\r\n");
		for (MachineDetails it : this) {
			sb.append(it.toTextString());
		}
		return sb.toString();
	}
	boolean isDup(MachineDetails md) {
		for (MachineDetails it : this) {
			if (it.getId().matches(md.getId())) {
				return true;
			}
		}
		return false;
	}
	public MachineDetails find(String id) {
		
		for (MachineDetails it : this) {
			if (it.getId().matches(id)) {
				return it;
			}
		}
		return null;
	}
}


