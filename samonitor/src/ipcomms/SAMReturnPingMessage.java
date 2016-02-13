package ipcomms;

import java.text.ParseException;
import java.util.Date;

import org.w3c.dom.Element;

import samonitor.SAMNode;
import xmlutils.DateUtils;
import xmlutils.XMLReadHelper;

public class SAMReturnPingMessage extends SAMNodeMessage {
	SAMReturnedPing message = null;
	String toAddress = null;
	String fromAddress = null;
	boolean master = false;
	Date timeStamp = null;
	
	public SAMReturnPingMessage(int samID, String name, int priority, String fromAddress, String toAddress, Date timeStamp, boolean master) {
		super(samID, name, priority, fromAddress);
		message = new SAMReturnedPing(samID, name, priority, fromAddress, toAddress, timeStamp, master);
		this.master = master;
		this.timeStamp = timeStamp;
		this.toAddress = toAddress;
		this.fromAddress = fromAddress;
	}
	public SAMReturnPingMessage(Element parent) throws NumberFormatException, ParseException {
		super(parent);
		this.fromAddress = XMLReadHelper.stringValue(parent, "FromAddress", true);
		this.toAddress = XMLReadHelper.stringValue(parent, "ToAddress", true);
		timeStamp = XMLReadHelper.dateValueMillsec(parent, "TimeStamp", true);
		String masterStr = XMLReadHelper.stringValue(parent, "Master", true);
		master = (masterStr.compareToIgnoreCase("yes") == 0)?true:false;
		message = new SAMReturnedPing(this.getSamID(), this.getName(), this.getPriority(),
															fromAddress, toAddress, timeStamp, master);
	}
	
	public SAMReturnPingMessage(SAMReturnedPing returnedPing) {
		super(returnedPing.getSamID(), returnedPing.getName(), returnedPing.getPriority(), returnedPing.getAddress());
		this.master = returnedPing.isMaster();
		this.timeStamp = returnedPing.getTimestamp();
		this.toAddress = returnedPing.getToAddress();
		this.fromAddress = returnedPing.getAddress();
		message = new SAMReturnedPing(this.getSamID(), this.getName(), this.getPriority(),
															fromAddress, toAddress, timeStamp, master);
	}
	/**
	 * @return the samPing
	 */
	@Override
	public SAMNode getMessage() {
		return message;
	}

	/**
	 * @return the master
	 */
	public final boolean isMaster() {
		return master;
	}
	/**
	 * @return the timeStamp
	 */
	public final Date getTimeStamp() {
		return timeStamp;
	}
	
	@Override
	public String makeMessage() {
	
		String masterStr = (this.master)?"Yes":"No";
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\r" +
				 "<ReturnedPing>" +
				 "<SamID>" + Integer.toString(this.getSamID())  + "</SamID>" +
				 "<Name>" + this.getName() + "</Name>" +
				 "<Priority>" + Integer.toString(this.getPriority()) + "</Priority>" +
				 "<FromAddress>" + this.getAddress() + "</FromAddress>" +
				 "<ToAddress>" + toAddress + "</ToAddress>" +
				 "<TimeStamp>" + DateUtils.formatYYYYMMDDtoMillsec(this.timeStamp) + "</TimeStamp>" +
				 "<Master>" + masterStr + "</Master>" +
				 "</ReturnedPing>";
		return s;
	}
}
