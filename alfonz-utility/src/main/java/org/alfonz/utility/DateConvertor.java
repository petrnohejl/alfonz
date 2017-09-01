package org.alfonz.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


// date format: https://developer.android.com/reference/java/text/SimpleDateFormat.html
public final class DateConvertor
{
	private DateConvertor() {}


	public static String dateToString(Date date, String format)
	{
		String str = null;
		if(date != null)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
			str = dateFormat.format(date);
		}
		return str;
	}


	public static Date stringToDate(String str, String format)
	{
		Date date = null;
		if(str != null)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
			try { date = dateFormat.parse(str); }
			catch(ParseException e) { e.printStackTrace(); }
		}
		return date;
	}


	public static Calendar dateToCalendar(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}


	public static Date calendarToDate(Calendar calendar)
	{
		return calendar.getTime();
	}


	public static Calendar stringToCalendar(String str, String format)
	{
		Date date = stringToDate(str, format);
		return dateToCalendar(date);
	}


	public static String calendarToString(Calendar calendar, String format)
	{
		Date date = calendarToDate(calendar);
		return dateToString(date, format);
	}
}
