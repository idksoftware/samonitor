

package telnet.user;

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
public class UsersFile {

	
	private UserProperties  userProperties = new UserProperties();
	
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

    private UsersFile(Document doc)
    {
        xmpDocument = doc;
        init();
    }

    private void init() {
    	User user = new User();
    	user.UserAdd("Admin", "admin", null, null, null, 0, 0, "qwerty", 0, null);
    	userProperties.add(user);
    }
    
    public static UsersFile load(String file) throws IOException
    {
       return new UsersFile(XMLUtil.parse(file));
    }

    public UserProperties GetUserProperties()
    {
    	return userProperties;
    }
    
    public void decode() throws ParseException, NumberFormatException
    {
        Element Users = xmpDocument.getDocumentElement();
        NodeList usersChildren = Users.getElementsByTagName("User");
        int n = usersChildren.getLength();
        for (int i = 0; i < n; i++)
        {
        	User user = new User();
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

            user.UserAdd(nameString, userNameString, commentString, homeDirString, dateExpire,
            				inactiveDaysInt, initialGroupInt, passwordString, uidInt, emailString);
            userProperties.add(user);

        }
        
        
    }
    
    /*
     this.name = name;
		this.userName = userName;
		this.comment = comment;
		this.homeDir = homeDir;
		this.expireDate = expireDate;
		this.inactiveDays = inactiveDays;
		this.initialGroup = initialGroup;
		this.passwd = passwd;
		this.uid = uid;
		this.email = email;
		
     */
    
    public void Save(String path, String name)
	{
		
		XmlWriter xmlWriter = new XmlWriter(path,name);
		xmlWriter.startElement("Users");
		Iterator<User> it = userProperties.iterator();
		while (it.hasNext())
		{
			xmlWriter.startElement("User");
			
			User user = it.next();
			
			xmlWriter.startElement("Name");
            xmlWriter.element(user.name);
			xmlWriter.endElement("Name");
			
			xmlWriter.startElement("UserName");
            xmlWriter.element(user.userName);
			xmlWriter.endElement("UserName");
			
			xmlWriter.startElement("Comment");
            xmlWriter.element(user.comment);
			xmlWriter.endElement("Comment");			
			
			xmlWriter.startElement("HomeDir");
            xmlWriter.element(user.homeDir);
			xmlWriter.endElement("HomeDir");			
			
			xmlWriter.startElement("ExpireDate");
            xmlWriter.element(user.expireDate.toString());
			xmlWriter.endElement("ExpireDate");			
			
			xmlWriter.startElement("InactiveDays");
			Integer inactiveDaysInt = new Integer(user.inactiveDays);
            xmlWriter.element(inactiveDaysInt.toString());
			xmlWriter.endElement("InactiveDays");
			
			xmlWriter.startElement("InitialGroup");
			Integer initialGroupInt = new Integer(user.initialGroup);
            xmlWriter.element(initialGroupInt.toString());
			xmlWriter.endElement("InitialGroup");
			
			xmlWriter.startElement("Password");
            xmlWriter.element(user.passwd);
			xmlWriter.endElement("Password");
			
			xmlWriter.startElement("Uid");
			Integer uidInt = new Integer(user.uid);
            xmlWriter.element(uidInt.toString());
			xmlWriter.endElement("Uid");
			
			xmlWriter.startElement("Email");
            xmlWriter.element(user.email);
			xmlWriter.endElement("Email");			
			
			xmlWriter.endElement("User");
		}
		xmlWriter.endElement("Users");
		xmlWriter.close();
	}
   
}
