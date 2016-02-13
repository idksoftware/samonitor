package telnet.user;

import java.io.IOException;
import java.text.ParseException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import xmlutils.XMLUtil;
/**
 *
 * @author Iain Ferguson
 */
public class GroupsFile {

	
	private GroupProperties  groupProperties = new GroupProperties();
	
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

    private GroupsFile(Document doc)
    {
        xmpDocument = doc;
    //    init();
    }

    public static GroupsFile load(String file) throws IOException
    {
       return new GroupsFile(XMLUtil.parse(file));
    }

    public GroupProperties GetGroupProperties()
    {
    	return groupProperties;
    }
    
    public void decode() throws ParseException, NumberFormatException
    {
    	/*
        Element Groups = xmpDocument.getDocumentElement();
        NodeList groupChildren = Groups.getElementsByTagName("Group");
        int n = groupChildren.getLength();
        for (int i = 0; i < n; i++)
        {
        	 
            Element groupElement = (Element)groupChildren.item(i);
            
            Element name = XMLUtil.getElement(groupElement, "Name");
            String nameString = name.getTextContent();
            System.out.println(nameString);
            
            Element gid = XMLUtil.getElement(groupElement, "GID");
            String groupIDString = gid.getTextContent();
            System.out.println(groupIDString);
            
            int gidInt = Integer.parseInt(groupIDString);
            Group group = new Group(gidInt, nameString);
            
            NodeList userChildren = groupElement.getElementsByTagName("GroupList");
            int u = userChildren.getLength();
            for (int j = 0; j < u; j++)
            {
            	Element userElement = (Element)userChildren.item(j);
            	Element user = XMLUtil.getElement(userElement, "User");
                String userString = user.getTextContent();
                System.out.println(userString);
                group.add(userString);
            }            
            groupProperties.add(group);

        }
        */
        
    }
    
   
}
