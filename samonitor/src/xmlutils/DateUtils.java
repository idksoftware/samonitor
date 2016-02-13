package xmlutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	static final String formatDDMMYYYY = "dd/MM/yyyy HH:mm:ss";
	static final String formatYYYYMMDD = "yyyy/MM/dd HH:mm:ss";
	static final String formatYYYYMMDDtoMillsec = "yyyy/MM/dd HH:mm:ss:SSS";
	public static Date parseDDMMYYYY(String dateString) throws ParseException {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(formatDDMMYYYY);
		return dateFormat.parse(dateString);
	}

	public static Date parseYYYYMMDD(String dateString) throws ParseException {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(formatYYYYMMDD);
		return dateFormat.parse(dateString);
	}
	public static Date parseYYYYMMDDtoMillsec(String dateString) throws ParseException {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(formatYYYYMMDDtoMillsec);
		return dateFormat.parse(dateString);
	}
	public static String formatDDMMYYYY(Date date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(formatDDMMYYYY);
		return dateFormat.format(date);
	}

	public static String formatYYYYMMDD(Date date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(formatYYYYMMDD);
		return dateFormat.format(date);
	}
	public static String formatYYYYMMDDtoMillsec(Date date) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(formatYYYYMMDDtoMillsec);
		return dateFormat.format(date);
	}
	
}
