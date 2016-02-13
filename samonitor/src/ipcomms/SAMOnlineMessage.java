package ipcomms;

import java.text.ParseException;

import org.w3c.dom.Element;

import samonitor.SAMIdentity;
import samonitor.SAMNode;


public class SAMOnlineMessage extends SAMNodeMessage {
	SAMOnline message = null;
	public SAMOnlineMessage() {
		super(SAMIdentity.getSamID(), SAMIdentity.getName(),
				SAMIdentity.getPriority(), SAMIdentity.getAddr().getHostAddress());
	}
	public SAMOnlineMessage(int samID, String name, int priority, String address) {
		super(samID, name, priority, address);
		this.message = new SAMOnline(this.getSamID(), this.getName(), this.getPriority(), this.getAddress());
	}
	public SAMOnlineMessage(Element parent) throws NumberFormatException, ParseException {
		super(parent);
		this.message = new SAMOnline(this.getSamID(), this.getName(), this.getPriority(), this.getAddress());
	}
	public SAMOnlineMessage(SAMOnline message)  {
		super(message.getSamID(), message.getName(), message.getPriority(), message.getAddress());
		this.message = message;
	}
	@Override
	public String makeMessage() {
        String returnValue = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\r" +
        					 "<Online>" +
        					 "<SamID>" + this.getSamID() + "</SamID>" +
        					 "<Name>" + this.getName() + "</Name>" +
        					 "<Priority>" + this.getPriority() + "</Priority>" +
        					 "<Address>" + this.getAddress() + "</Address>" +
        					 "</Online>";
        return returnValue;
    }
	
	
	@Override
	public SAMNode getMessage() {
		return message;
	}
	
	
}
