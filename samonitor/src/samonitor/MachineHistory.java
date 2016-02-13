package samonitor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xmlutils.XMLWriteHelper;


public class MachineHistory {
	//Map<String, MachineHistoryItem> machineList = new Map<String, MachineHistoryItem>
	static Document document = null;
	static Element root = null;
	
	static void open() throws ParserConfigurationException {
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
	static void addEntry(String id, String message, MachineState state) {
		
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
	
	static void close() throws FileNotFoundException, IOException {
		XMLWriteHelper.writeXmlFile(document, "c:\\temp\\test.xml"); 
	}
}
