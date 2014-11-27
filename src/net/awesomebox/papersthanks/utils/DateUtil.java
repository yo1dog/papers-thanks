package net.awesomebox.papersthanks.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
	private static String shortDateFormatStr = "MM.dd.yy";
	private static String longDateFormatStr  = "MM.dd.yyyy";
	
	private static SimpleDateFormat shortDateFormat = new SimpleDateFormat(shortDateFormatStr);
	private static SimpleDateFormat longDateFormat  = new SimpleDateFormat(longDateFormatStr);
	
	
	public static Date parseDate(String dateStr) {
		SimpleDateFormat format;
		if (dateStr.length() == shortDateFormatStr.length())
			format = shortDateFormat;
		else if (dateStr.length() == longDateFormatStr.length())
			format = longDateFormat;
		else
			return null;
		
		try
		{
			return format.parse(dateStr);
		}
		catch (ParseException e)
		{
			return null;
		}
	}
	
	public static String toString(Date date)
	{
		return toString(date, false);
	}
	public static String toString(Date date, boolean _short)
	{
		return (_short? shortDateFormat : longDateFormat).format(date);
	}
}
