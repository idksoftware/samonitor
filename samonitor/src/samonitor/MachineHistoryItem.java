package samonitor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xmlutils.XMLWriteHelper;



public class MachineHistoryItem {
	boolean isOpen = false;
	public class MachineHistoryStateItem {
		
		String message;
		MachineState state;
		Date dateStamp = new Date();
		
		MachineHistoryStateItem(String message, MachineState state) {
			
			this.message = message;
			this.state = state;
		}
	}
	
	String id;
	ArrayList<MachineHistoryStateItem> historyList = new ArrayList<MachineHistoryStateItem>();
	Document document = null;
	Element root = null;
	
	public MachineHistoryItem(String id) {
		this.id = id;
	}
	
	void open() throws ParserConfigurationException {
		document = XMLWriteHelper.creatXMLFile();
		root = document.createElement("History");
		document.appendChild(root);
	}
	
	/**
	 * This is the full history for all machines.
	 * @param id
	 * @param message
	 * @param state
	 */
	void addEntry(String message, MachineState state) {
		if (isOpen == false) {
			try {
				open();
				isOpen = true;
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		historyList.add(new MachineHistoryStateItem(message, state));
		Element itemElement = document.createElement("Item");
		Element idElement = XMLWriteHelper.getElement(document, "Id", id);	
		itemElement.appendChild(idElement);
		Element timeStampElement = XMLWriteHelper.getElement(document, "Date", new Date());
		itemElement.appendChild(timeStampElement);
		Element messageElement = XMLWriteHelper.getElement(document, "Message", message);
		itemElement.appendChild(messageElement);
		
		Element stateElement = XMLWriteHelper.getElement(document, "State", state.toString());
		itemElement.appendChild(stateElement);
		root.appendChild(itemElement);
	}
	
	void close() throws FileNotFoundException, IOException {
		XMLWriteHelper.writeXmlFile(document, "c:\\temp\\test.xml"); 
	}
	
	public ArrayList<MachineHistoryStateItem> getHistoryList() {
		return historyList;
	}
	
	public String historyListToString() {
		StringBuilder sb = new StringBuilder("History List: " + id + "\r\n");
		   sb.append("---------------" + "\r\n" + "\r\n");
		for (MachineHistoryStateItem it : historyList) {
			sb.append(it.toString());
		}
		return sb.toString();
	}
}
