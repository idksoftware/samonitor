package samonitor;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class SAMStationsList extends ArrayList<SAMNode> {
	public boolean isPresent(SAMNode samNode) {
		for (SAMNode item : this) {
			if (item.getSamID() == samNode.getSamID()) {
				return true;
			}
		}
		return false;
	}
}