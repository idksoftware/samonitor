package ipcomms;

import java.text.ParseException;
import java.util.Date;

import org.w3c.dom.Element;

import samonitor.SAMNode;
import xmlutils.DateUtils;
import xmlutils.XMLReadHelper;

public class SAMSendPingMessage extends SAMNodeMessage {
	SAMSentPing message = null;
	boolean master = false;
	Date timeStamp = null;
	
	SAMSendPingMessage(int samID, String name, int priority, String address, Date timeStamp, boolean master) {
		super(samID, name, priority, address);
		this.master = master;
		this.timeStamp = timeStamp;
		message = new SAMSentPing(samID, name, priority, address, timeStamp, master);
	}
	public SAMSendPingMessage(Element parent) throws NumberFormatException, ParseException {
		super(parent);
		timeStamp = XMLReadHelper.dateValueMillsec(parent, "TimeStamp", true);
		String masterStr = XMLReadHelper.stringValue(parent, "Master", true);
		master = (masterStr.compareToIgnoreCase("yes") == 0)?true:false;
		message = new SAMSentPing(this.getSamID(), this.getName(), this.getPriority(), this.getAddress(), timeStamp, master);
	}
	public SAMSendPingMessage(SAMSentPing sentPing) {
		super(sentPing.getSamID(), sentPing.getName(), sentPing.getPriority(), sentPing.getAddress());
		this.master = sentPing.isMaster();
		this.timeStamp = sentPing.getTimestamp();
		message = new SAMSentPing(this.getSamID(), this.getName(), this.getPriority(), this.getAddress(), timeStamp, master);
	}
	/**
	 * @return the samPing
	 */
	@Override
	public SAMNode getMessage() {
		return message;
	}
	
	@Override
	public String makeMessage() {
		timeStamp = new Date();
		String masterStr = (master)?"Yes":"No";
		String returnValue = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\r" +
				 "<SentPing>" +
				 "<SamID>" + Integer.toString(this.getSamID()) + "</SamID>" +
				 "<Name>" + this.getName() + "</Name>" +
				 "<Priority>" + Integer.toString(this.getPriority()) + "</Priority>" +
				 "<Address>" + this.getAddress() + "</Address>" +
				 "<TimeStamp>" + DateUtils.formatYYYYMMDDtoMillsec(timeStamp) + "</TimeStamp>" +
				 "<Master>" + masterStr + "</Master>" +
				 "</SentPing>";
		return returnValue;
	}
}
