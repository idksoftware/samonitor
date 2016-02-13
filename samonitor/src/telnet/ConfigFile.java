package telnet;
import java.io.IOException;
import java.text.ParseException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xmlutils.XMLUtil;
import xmlutils.XmlWriter;

import java.util.Date;
import java.util.Iterator;
import java.text.*;

	
/**
 *
 * @author Iain Ferguson
 */
public class ConfigFile {

		
		private static Properties  properties = new Properties();
		
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

	    private ConfigFile(Document doc)
	    {
	        xmpDocument = doc;
	    //    init();
	    }

	    public static ConfigFile load(String file) throws IOException
	    {
	       return new ConfigFile(XMLUtil.parse(file));
	    }

	    public Properties GetProperties()
	    {
	    	return properties;
	    }
	    
	    public void decode() throws ParseException, NumberFormatException
	    {
	        Element Users = xmpDocument.getDocumentElement();
	        NodeList usersChildren = Users.getElementsByTagName("User");
	        int n = usersChildren.getLength();
	        for (int i = 0; i < n; i++)
	        {
	        	
	            Element userElement = (Element)usersChildren.item(i);
	            
	            Element name = XMLUtil.getElement(userElement, "Name");
	            String nameString = name.getTextContent();
	            System.out.println(nameString);
	            
	            Element userName = XMLUtil.getElement(userElement, "UserName");
	            String userNameString = userName.getTextContent();
	            System.out.println(userNameString);
	            
	            Element comment = XMLUtil.getElement(userElement, "Comment");
	            String commentString = comment.getTextContent();
	            System.out.println(commentString);
	            
	            Element homeDir = XMLUtil.getElement(userElement, "HomeDir");
	            String homeDirString = homeDir.getTextContent();
	            System.out.println(homeDirString);
	            
	            Element expireDate = XMLUtil.getElement(userElement, "ExpireDate");
	            String expireDateString = expireDate.getTextContent();
	            System.out.println(expireDateString);
	            
	            Element inactiveDays = XMLUtil.getElement(userElement, "InactiveDays");
	            String inactiveDaysString = inactiveDays.getTextContent();
	            System.out.println(inactiveDaysString);
	            
	            Element initialGroup = XMLUtil.getElement(userElement, "InitialGroup");
	            String initialGroupString = initialGroup.getTextContent();
	            System.out.println(initialGroupString);
	            
	            Element password = XMLUtil.getElement(userElement, "Password");
	            String passwordString = password.getTextContent();
	            System.out.println(passwordString);
	            
	            Element uid = XMLUtil.getElement(userElement, "Uid");
	            String uidString = uid.getTextContent();
	            System.out.println(uidString);
	            
	            Element email = XMLUtil.getElement(userElement, "Email");
	            String emailString = email.getTextContent();
	            System.out.println(emailString);
	            
	            int inactiveDaysInt = Integer.parseInt(inactiveDaysString);
	            int initialGroupInt = Integer.parseInt(inactiveDaysString);
	            int uidInt = Integer.parseInt(uidString);
	            
	            SimpleDateFormat df1 = new SimpleDateFormat( "dd/MM/yy" );

	            Date dateExpire = df1.parse(expireDateString);

	           
	        }
	        
	        
	    }
	    
	  
	    
	    public static void Save(String path, String name)
		{
	    	if (properties == null)
	    	{
	    		properties = new Properties();
	    	}
			
			XmlWriter xmlWriter = new XmlWriter(path,name);
			xmlWriter.startElement("TelnetOptions");
			
				xmlWriter.startElement("DefaultHomePath");
	            xmlWriter.element(Properties.defaultHomePath);
				xmlWriter.endElement("DefaultHomePath");
	
				xmlWriter.startElement("DefaultGID");
				Integer gidInt = Properties.defaultGID;
	            xmlWriter.element(gidInt.toString());
				xmlWriter.endElement("DefaultGID");
				
				xmlWriter.startElement("PortNo");
				Integer portInt = Properties.portNo;
	            xmlWriter.element(portInt.toString());
				xmlWriter.endElement("DefaultGID");
				
				xmlWriter.startElement("AdminUser");
	            xmlWriter.element(Properties.adminUser);
				xmlWriter.endElement("AdminUser");

				xmlWriter.startElement("AdminPassword");
	            xmlWriter.element(Properties.adminPassword);
				xmlWriter.endElement("AdminPassword");

				xmlWriter.startElement("AdminHomePath");
	            xmlWriter.element(Properties.adminHomePath);
				xmlWriter.endElement("AdminHomePath");
				
			xmlWriter.endElement("TelnetOptions");
			xmlWriter.close();
		}
	    
	   
	}

