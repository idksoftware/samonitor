package ipcomms;

import samonitor.SAMIdentity;
import samonitor.SAMNode;

public class SAMOnline extends SAMNode {
	public SAMOnline() {
		super(SAMIdentity.getSamID(), SAMIdentity.getName(),
				SAMIdentity.getPriority(), SAMIdentity.getAddr().getHostAddress());
	}
	public SAMOnline(int samID, String name, int priority, String address) {
		super(samID, name, priority, address);
	}
}
