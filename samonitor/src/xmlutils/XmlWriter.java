package xmlutils;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Iain Ferguson
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.namespace.QName;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


public class XmlWriter
{
String         filepath   = null;
FileWriter     writer     = null;
XMLSerializer  xmlWriter  = null;
AttributesImpl attributes = new AttributesImpl();

public XmlWriter(String filepath, String name)
{
   this.filepath = filepath;
   File file = new File(filepath, name);

   try
   {
      if(file.exists())
         file.delete();
   }
   catch(Exception e)
   {
      throw new RuntimeException("Can't delete file: " + filepath);
   }
   try
   {
      writer = new FileWriter(file, true);
   }
   catch(IOException e)
   {
      throw new RuntimeException("Can't create writer for file: " + filepath, e);
   }
   if(writer != null)
   {
      xmlWriter = new XMLSerializer(writer, new OutputFormat());
      //xmlWriter.setNamespaces(true);


      try
      {
         if(xmlWriter != null)
         {
            xmlWriter. startDocument();
           
         }
         else
         {
            throw new RuntimeException("No xml writer available.");
         }
      }
      catch(SAXException e)
      {
         throw new RuntimeException(e);
      }
   }
}

public void close()
{
   
   if(xmlWriter != null)
      {
         
         try
         {
            xmlWriter.endDocument();
            writer.close();
         }
         catch(Exception e)
         {
            throw new RuntimeException("Can't delete file: " + filepath, e);
         }
         finally
         {
             xmlWriter = null;
         }
      }
     
   
}

public FileWriter getWriter()
{
    return writer;
}

private void resetAttributes()
{
   attributes = new AttributesImpl();
}

public void addAttribute(String name, String value)
{
   attributes.addAttribute((String)null, (String)null, name, "", value);
}

/**
 * before startElement: accumulate Attribute into the internal buffer by calling addAttribute(..);
 * attributes are written when you call startElement. After writing the element the attributes
 * buffer ist reset.
 * @param qname
 */
public void startElement(String qname)
{
   
      if(xmlWriter != null)
      {
         org.xml.sax.Attributes attr = attributes;
         xmlWriter.startElement((String)null, (String)null, qname, attr);
       
         resetAttributes();
      }
      else
      {
         throw new RuntimeException("No xml writer available.");
      }
   
  
}
/**
 * before startElement: accumulate Attribute into the internal buffer by calling addAttribute(..);
 * attributes are written when you call startElement. After writing the element the attributes
 * buffer ist reset.
 * @param qname
 */
public void startElement(String NameSpaceURL, String qname)
{

   
      if(xmlWriter != null)
      {
         org.xml.sax.Attributes attr = attributes;

         xmlWriter.startElement( NameSpaceURL, (String)null, qname, attr);

         resetAttributes();
      }
      else
      {
         throw new RuntimeException("No xml writer available.");
      }
   
  
}
public void element(String content)
{
  
      if(xmlWriter != null)
      {
         xmlWriter.characters(content.toCharArray(), 0, content.length());
      }
      else
      {
         throw new RuntimeException("No xml writer available.");
      }
  
}

public void endElement(String qname)
{
  
      if(xmlWriter != null)
      {
         xmlWriter.endElement((String)null, (String)null, qname);
         resetAttributes();
      }
      else
      {
         throw new RuntimeException("No xml writer available.");
      }
  
}

public void endElement(String NameSpaceURL, String qname)
{
   
      if(xmlWriter != null)
      {
         xmlWriter.endElement(NameSpaceURL, (String)null, qname);
         resetAttributes();
      }
      else
      {
         throw new RuntimeException("No xml writer available.");
      }
   
}
}
