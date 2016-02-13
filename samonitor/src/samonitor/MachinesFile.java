package samonitor;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//package samonitor;

import java.io.IOException;
import java.text.ParseException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;

import javax.xml.transform.TransformerException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Element;

import xmlutils.XMLReadHelper;
import xmlutils.XMLUtil;

import java.text.ParseException;
/**
 *
 * @author Iain Ferguson
 */
public class MachinesFile {

	
	private MachineProperties  machineProperties = new MachineProperties();
	
    /**
     * Supported encoding for persisted XML.
     */
    public static final String ENCODING_UTF8 = "UTF-8";

    /**
     * Supported encoding for persisted XML.
     */
    public static final String ENCODING_UTF16BE = "UTF-16BE";

    /**
     * Supported encoding for persisted XML.
     */
    public static final String ENCODING_UTF16LE = "UTF-16LE";

    /**
     * The DOM representation of the metadata.
     */
    protected Document xmpDocument;

    /**
     * The encoding of the XMP document. Default is UTF8.
     */
    protected String encoding = ENCODING_UTF8;

    protected String command;
    protected String scope;

    private MachinesFile(Document doc)
    {
        xmpDocument = doc;
    //    init();
    }

    public static MachinesFile load(String file) throws IOException
    {
       return new MachinesFile(XMLUtil.parse(file));
    }

    public MachineProperties GetMachineProperties()
    {
    	return machineProperties;
    }
    
    public void decode() throws ParseException
    {
    	
		final Element root = xmpDocument.getDocumentElement();
		
    	
        Element Machines = xmpDocument.getDocumentElement();
        NodeList children = Machines.getElementsByTagName("Machine");
        int n = children.getLength();
        for (int i = 0; i < n; i++)
        {
            Element Machine = (Element)children.item(i);
            String idString = XMLReadHelper.stringValue(Machine, "Id", true);

            
            String hostNameString = XMLReadHelper.stringValue(Machine, "HostName", false);
            System.out.println(hostNameString);

            
            String addressString = XMLReadHelper.stringValue(Machine, "Address", false);
            System.out.println(addressString);

            
            int trys = XMLReadHelper.integerValue(Machine, "Trys", false, false);
            System.out.println(trys);

            
            int delay = XMLReadHelper.integerValue(Machine, "Delay", false, false);
            System.out.println(delay);

            MachineDetails machineDetails = new MachineDetails();
            machineDetails.setId(idString);
            machineDetails.setAddressString(addressString);
            machineDetails.setHostName(hostNameString);
            machineDetails.setTrys(trys);
            machineDetails.setDelay(delay);
            
            machineProperties.add(machineDetails);
        }
        /*
        Element CommandScope = XMLUtil.getElement(Header, "Scope");
        if (CommandScope != null)
        {
            scope = CommandScope.getTextContent();
        }
        else
        {
            throw new ParseException("Scope missing", 1);
        }
        Element Body = XMLUtil.getElement(Env, "Body");
        if (Body != null)
        {
            CommandBase.CommandType commandType = CommandBase.CommandType.valueOf(command);
            CommandBase.CommandScope commandScope = CommandBase.CommandScope.valueOf(scope);
            commandObj = CommandFactory.Create(commandType, commandScope);
            try {
                commandObj.decode(Body);
            } catch (ParseException e) {
                throw e;
            }

        }
        else
        {
            // Error
        }
         */
        
        
    }
    
   
}
