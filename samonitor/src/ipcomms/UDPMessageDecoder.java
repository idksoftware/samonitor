package ipcomms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import samonitor.SAMNode;
import xmlutils.DateUtils;
import xmlutils.XMLReadHelper;
import xmlutils.XMLUtil;

public class UDPMessageDecoder {
	
	private static final String ENCODING = "UTF-8";
	
	private static InputStream fromString(String str) throws UnsupportedEncodingException
	{
		byte[] bytes = str.getBytes(ENCODING);
		return new ByteArrayInputStream(bytes);
	}
	
	public static UDPMessage decode(String msg) throws IOException, ParseException {
		
		Document document = XMLUtil.parse(fromString(msg));
		final Element root = document.getDocumentElement();
		String rootName = root.getNodeName();
		if (rootName.matches("Online") == true) {
			return new SAMOnlineMessage(root);
		}
		
		if (rootName.matches("SentPing") == true) {
			return new SAMSendPingMessage(root);
		}
		
		if (rootName.matches("ReturnPing") == true) {
			return new SAMReturnPingMessage(root);
		}
		return null;
	}

	
}
