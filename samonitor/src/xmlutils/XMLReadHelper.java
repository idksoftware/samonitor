package xmlutils;

import static org.w3c.dom.Node.ELEMENT_NODE;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xmlutils.XMLUtil;

public class XMLReadHelper extends XMLHelper {

	public static final boolean booleanValue(Element parent, String tag, boolean obligatory, boolean defaultValue)
			throws ParseException {
		boolean result = false;
		final Element name = XMLUtil.getElement(parent, tag);
		if (name == null) {
			if (obligatory == true) {
				throw new ParseException("Failed to find tag \"" + tag + "\" which is obligatory", 0);
			} else {
				return defaultValue;
			}
		}
		try {
			result = Boolean.valueOf(name.getTextContent());
		} catch (final IllegalArgumentException e) {
			throw new ParseException(e.getMessage(), 0);
		}
		return result;
	}

	/*
	 * public static final String dateString(Date d) { SimpleDateFormat
	 * dateFormat = new SimpleDateFormat(DATE_FORMAT); return
	 * dateFormat.format(new Date()); }
	 */
	public static final Date dateValue(Element parent, String tag, boolean obligatory) throws ParseException {
		final Element Name = XMLUtil.getElement(parent, tag);
		if (Name == null) {
			if (obligatory == true) {
				throw new ParseException("Failed to find tag \"" + tag + "\" which is obligatory", 0);
			} else {
				return null;
			}
		}
		final String nameStr = Name.getTextContent();
		if ((nameStr == null || nameStr.length() == 0) && obligatory == false) {
			return null;
		}

		Date date = null;
		try {
			date = DateUtils.parseDDMMYYYY(nameStr);
		} catch (final ParseException e) {
			throw new ParseException(e.getMessage(), 0);
		}
		return date;
	}

	public static final Date dateValueMillsec(Element parent, String tag, boolean obligatory) throws ParseException {
		final Element Name = XMLUtil.getElement(parent, tag);
		if (Name == null) {
			if (obligatory == true) {
				throw new ParseException("Failed to find tag \"" + tag + "\" which is obligatory", 0);
			} else {
				return null;
			}
		}
		final String nameStr = Name.getTextContent();
		if ((nameStr == null || nameStr.length() == 0) && obligatory == false) {
			return null;
		}

		Date date = null;
		try {
			date = DateUtils.parseDDMMYYYY(nameStr);
		} catch (final ParseException e) {
			throw new ParseException(e.getMessage(), 0);
		}
		return date;
	}
	
	public static final Document decode(File inputFile) throws ParseException, IOException {
		Document document = null;
		document = XMLUtil.parse(inputFile.getAbsolutePath());
		return document;
	}

	public static final int integerValue(Element parent, String tag, boolean obligatory, boolean hex) throws ParseException,
			NumberFormatException {
		final Element Name = XMLUtil.getElement(parent, tag);
		if (Name == null) {
			if (obligatory == true) {
				throw new ParseException("Failed to find tag \"" + tag + "\" which is obligatory", 0);
			} else {
				return -1;
			}
		}
		int base = 10;
		if (hex == true) {
			base = 16;
		}
		final String value = Name.getTextContent();
		if ((value == null || value.length() == 0) && obligatory == false) {
			return -1;
		}
		return Integer.parseInt(value, base);
	}

	public static final long longValue(Element parent, String tag, boolean obligatory, boolean hex) throws ParseException,
			NumberFormatException {
		final Element Name = XMLUtil.getElement(parent, tag);
		if (Name == null) {
			if (obligatory == true) {
				throw new ParseException("Failed to find tag \"" + tag + "\" which is obligatory", 0);
			} else {
				return -1;
			}
		}
		int base = 10;
		if (hex == true) {
			base = 16;
		}
		return Long.parseLong(Name.getTextContent(), base);
	}

	public static final String stringValue(Element parent, String tag, boolean obligatory) throws ParseException {
		final Element Name = XMLUtil.getElement(parent, tag);
		if (Name == null) {
			if (obligatory == true) {
				throw new ParseException("Failed to find tag \"" + tag + "\" which is obligatory", 0);
			} else {
				return null;
			}
		}
		final String value = Name.getTextContent();
		if (value.length() == 0) {
			return null;
		}
		return value;
	}

	public static final String stringValue(Element parent, String tag, String defaultValue) throws ParseException {
		if (defaultValue == null) {
			throw new RuntimeException("Default value for \"" + tag + "\" cannot be empty");
		}
		final Element Name = XMLUtil.getElement(parent, tag);
		if (Name == null) {
			return defaultValue;
		}
		final String value = Name.getTextContent();
		if (value.length() == 0) {
			if (defaultValue != null) {
				return defaultValue;
			}
			return null;
		}
		return value;
	}

	public static final String stringAttibValue(Element parent, String tag, String defaultValue) throws ParseException {
		if (defaultValue == null) {
			throw new RuntimeException("Default value for \"" + tag + "\" cannot be empty");
		}
		final Element Name = XMLUtil.getElement(parent, tag);
		if (Name == null) {
			return defaultValue;
		}
		final String value = Name.getTextContent();
		if (value.length() == 0) {
			if (defaultValue != null) {
				return defaultValue;
			}
			return null;
		}
		return value;
	}
	
	public static final Element elementValue(Element parent, String tag, boolean obligatory) throws ParseException {
		final Element Name = XMLUtil.getElement(parent, tag);
		if (Name == null) {
			if (obligatory == true) {
				throw new ParseException("Failed to find tag \"" + tag + "\" which is obligatory", 0);
			} else {
				return null;
			}
		}
		return Name;
	}

	public static int getNextElement(NodeList list, int idx) {

		for (; idx < list.getLength(); idx++)
		{
			final Node fileNode = list.item(idx);
	
			final short type = fileNode.getNodeType();
			if (type == ELEMENT_NODE) {
				return idx;
			}
		}
		return -1;
	}
	
	public static Element isElement(NodeList list, int idx) {

		final Node fileNode = list.item(idx);
		if (fileNode == null)
		{
			return null;
		}
		final short type = fileNode.getNodeType();
		if (type == ELEMENT_NODE) {
			return (Element) fileNode;
		}
		return null;
	}
}
