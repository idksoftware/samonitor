package ipcomms;

import java.text.ParseException;

import org.w3c.dom.Element;

import xmlutils.XMLReadHelper;


public abstract class SAMNodeMessage  extends UDPMessage {
	private int samID = -1;
	private String name = null;
	private int priority = -1;
	private String address = null;
	
	
	public SAMNodeMessage(int samID, String name, int priority, String address) {
		this.samID = samID;
		this.name = name;
		this.priority = priority;
		this.address = address;
	}
	
	public SAMNodeMessage(Element parent) throws NumberFormatException, ParseException {
		decode(parent);
	}
	
	public abstract String makeMessage();
	
	public void decode(Element parent) throws NumberFormatException, ParseException {
	
			samID = XMLReadHelper.integerValue(parent, "SamID", false, true);
			name = XMLReadHelper.stringValue(parent, "Name", true);
			priority = XMLReadHelper.integerValue(parent, "Priority", false, true);
			address = XMLReadHelper.stringValue(parent, "Address", true);
		
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
