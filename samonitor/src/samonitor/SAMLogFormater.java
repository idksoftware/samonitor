package samonitor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.XMLFormatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

//This custom formatter formats parts of a log record to a single line
class SAMLogFormatter extends XMLFormatter
{
	// This method is called for every log records
	public String format(LogRecord rec)
	{
		StringBuffer buf = new StringBuffer(1000);
		// Bold any levels >= WARNING
		buf.append("<rec>\n");

		//if (rec.getLevel().intValue() >= Level.WARNING.intValue())
		//{
			buf.append("<l>\n");
			buf.append(rec.getLevel());
			buf.append("\n</l>\n");
		//} else
		//{
		//	buf.append(rec.getLevel());
		//}
		buf.append("<td>\n");
		buf.append(calcDate(rec.getMillis()));
		buf.append("</td>\n");
		buf.append("\n<m>\n");
		buf.append(formatMessage(rec));
		buf.append("\n</m>\n");
		buf.append("</rec>\n");
		return buf.toString();
	}

	private String calcDate(long millisecs)
	{
		SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}

	// This method is called just after the handler using this
	// formatter is created
	public String getHead(Handler h)
	{
		return "<?xml version=\"1.0\" encoding=\"windows-1252\" standalone=\"no\"?>\n"
			//+ "<!DOCTYPE log SYSTEM \"logger.dtd\">\n"
			+ "<log>\n";

		
		//return "<HTML>\n<HEAD>\n" + (new Date()) + "\n</HEAD>\n<BODY>\n<PRE>\n"
		//		+ "<table border>\n  "
		//		+ "<tr><th>Time</th><th>Log Message</th></tr>\n";
	}

	// This method is called just after the handler using this
	// formatter is closed
	public String getTail(Handler h)
	{
		return "</log>\n";
	}
}
