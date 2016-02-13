package xmlutils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLWriteHelper extends XMLHelper {

	public static Document creatXMLFile() throws ParserConfigurationException {
		Document doc = null;

		// Create instance of DocumentBuilderFactory
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// Get the DocumentBuilder
		final DocumentBuilder parser = factory.newDocumentBuilder();
		// Create blank DOM Document

		doc = parser.newDocument();

		return doc;
	}

	public static void writeXmlFile(Document doc, String filename) throws IOException, FileNotFoundException {
		if (doc == null) {
			throw new RuntimeException("Invaid Document");
		}
		FileOutputStream fos = null;
		// File destinationFile = new File(filename);
		fos = new FileOutputStream(filename);

		// XERCES 1 or 2 additionnal classes.
		final OutputFormat of = new OutputFormat("XML", "ISO-8859-1", true);
		of.setIndent(1);
		of.setIndenting(true);
		// of.setDoctype(null,"users.dtd");
		final XMLSerializer serializer = new XMLSerializer(fos, of);
		// As a DOM Serializer

		serializer.asDOMSerializer();
		serializer.serialize(doc.getDocumentElement());
		fos.close();

	}

	public static String writeXmlString(Document doc) throws IOException {
		final StringWriter stringWriter = new StringWriter();
		// XERCES 1 or 2 additionnal classes.
		final OutputFormat of = new OutputFormat("XML", "ISO-8859-1", true);
		of.setIndent(1);
		of.setIndenting(true);
		// of.setDoctype(null,"users.dtd");
		final XMLSerializer serializer = new XMLSerializer(stringWriter, of);
		// As a DOM Serializer
		serializer.asDOMSerializer();
		serializer.serialize(doc.getDocumentElement());

		return stringWriter.toString();
	}

	public static String transform2String(Node node) throws TransformerException {
		final TransformerFactory transFactory = TransformerFactory.newInstance();
		final Transformer transformer = transFactory.newTransformer();
		final StringWriter buffer = new StringWriter();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.transform(new DOMSource(node), new StreamResult(buffer));
		return buffer.toString();
	}

	public static Element getElement(Document doc, String tag, String text) {
		final Element labelElem = doc.createElement(tag);
		if (text != null) {
			final Node textNode = doc.createTextNode(text);
			labelElem.appendChild(textNode);
		}
		return labelElem;
	}

	public static Element getElement(Document doc, String tag, long num, boolean toHex) {
		final Element labelElem = doc.createElement(tag);
		Node textNode = null;
		if (toHex) {
			textNode = doc.createTextNode(Long.toHexString(num));
		} else {
			textNode = doc.createTextNode(Long.toString(num));
		}
		labelElem.appendChild(textNode);
		return labelElem;
	}

	public static Element getElement(Document doc, String tag, int num, boolean toHex) {
		final Element labelElem = doc.createElement(tag);
		Node textNode = null;
		if (toHex) {
			textNode = doc.createTextNode(Integer.toHexString(num));
		} else {
			textNode = doc.createTextNode(Integer.toString(num));
		}
		labelElem.appendChild(textNode);
		return labelElem;
	}

	public static Element getElement(Document doc, String tag, boolean val) {
		final Element labelElem = doc.createElement(tag);
		Node textNode = null;
		textNode = doc.createTextNode(Boolean.toString(val));
		labelElem.appendChild(textNode);
		return labelElem;
	}

	public static Element getElement(Document doc, String tag, Date date) {

		final Element labelElem = doc.createElement(tag);
		Node textNode = null;
		if (date != null) {
			final String dateString = DateUtils.formatDDMMYYYY(date);
			textNode = doc.createTextNode(dateString);
			labelElem.appendChild(textNode);
		}

		return labelElem;
	}
}
