package samonitor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xmlutils.XMLWriteHelper;


public class MachineStateTable {
	ArrayList<MachineStateItem> stateTable = new ArrayList<MachineStateItem>();
	static boolean firstLoop = true; // The first loop will give the inital state
									 // of the machines
	public MachineStateTable() {
		try {
			MachineHistory.open();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	MachineStateItem add(MachineDetails d) {
		MachineStateItem item = new MachineStateItem(d);
		stateTable.add(item);
		return item;
	}

	public void process() {
		for (MachineStateItem item : stateTable) {
			if (item.isChange() == true) {
				MachineHistory.addEntry(item.getId(), "Test", item.getMachineState());
				item.getMachineHistoryItem().addEntry("Test", item.getMachineState());
			}
		}
		for (MachineStateItem item : stateTable) {
			item.resetChange();
		}
		firstLoop = false;
	}

	public void closeHistory() throws FileNotFoundException, IOException {
		MachineHistory.close();
		
	}
	
	public void write(String filename) throws ParserConfigurationException, FileNotFoundException, IOException {
		Document doc = XMLWriteHelper.creatXMLFile();
		Element root = doc.createElement("CurrentMachineStates");
		doc.appendChild(root);
		for (MachineStateItem item : stateTable) {
			root.appendChild(writeItem(item, doc));
		}
		
		XMLWriteHelper.writeXmlFile(doc, filename);
	}
	
	private Element writeItem(MachineStateItem item, Document doc) {
		MachineDetails msi = item.getMachineDetails();
		Element itemElem = doc.createElement("Item");
		Element idElem = XMLWriteHelper.getElement(doc, "ID", msi.getId());
		itemElem.appendChild(idElem);
		Element hostNameElem = XMLWriteHelper.getElement(doc, "HostName", msi.getHostName());
		itemElem.appendChild(hostNameElem);
		Element addressStringElem = XMLWriteHelper.getElement(doc, "IPAddress", msi.getAddressString());
		itemElem.appendChild(addressStringElem);
		Element macAddressElem = XMLWriteHelper.getElement(doc, "MacAddress", msi.getMacAddress());
		itemElem.appendChild(macAddressElem);
		Element trysElem = XMLWriteHelper.getElement(doc, "Trys", msi.getTrys(), false);
		itemElem.appendChild(trysElem);
		Element delayElem = XMLWriteHelper.getElement(doc, "Delay", msi.getDelay(), false);
		itemElem.appendChild(delayElem);
		Element stateElem = XMLWriteHelper.getElement(doc, "State", item.getMachineState().toString());
		itemElem.appendChild(stateElem);
		return itemElem;
		
	}
	public String writeString() {
		StringBuilder sb = new StringBuilder("Machine states" + "\r\n");
								   sb.append("--------------" + "\r\n" + "\r\n");
		for (MachineStateItem it : stateTable) {
			sb.append(it.toString());
		}
		return sb.toString();
	}
	
	public MachineStateItem getMachineStateItem(String id) {
		for (MachineStateItem it : stateTable) {
			if (it.getId().equals(id) == true) {
				return it;
			}
		}
		return null;
	}
	
	
}

