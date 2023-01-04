package com.pas.util;

import java.util.*;
import java.text.*;

import com.pas.exception.EDateIsInvalid;

/**
 * Insert the type's description here. Creation date: (10/19/00 2:10:58 PM)
 * 
 * @author: Administrator
 */

public class PASDate extends GregorianCalendar
{
	/**
	 * Insert the method's description here. Creation date: (1/24/01 11:00:59
	 * AM)
	 */
	public PASDate()
	{
	}

	/**
	 * PASDate constructor .
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int - month is zero-based
	 * @param date
	 *            int
	 */
	public PASDate(int year, int month, int date) throws EDateIsInvalid
	{
		super(year, month - 1, date);

		yyyy = year;
		mm = month;
		dd = date;

		validate();
	}

	/**
	 * PASDate constructor .
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int - month is zero-based
	 * @param date
	 *            int
	 * @param hour
	 *            int
	 * @param minute
	 *            int
	 */
	public PASDate(int year, int month, int date, int hour, int minute)
			throws EDateIsInvalid
	{
		super(year, month - 1, date, hour, minute);

		yyyy = year;
		mm = month;
		dd = date;
		hh = hour;
		min = minute;

		validate();
	}

	/**
	 * PASDate constructor
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int - month is zero-based
	 * @param date
	 *            int
	 * @param hour
	 *            int
	 * @param minute
	 *            int
	 * @param second
	 *            int
	 */
	public PASDate(int year, int month, int date, int hour, int minute,
			int second) throws EDateIsInvalid
	{
		super(year, month - 1, date, hour, minute, second);

		yyyy = year;
		mm = month;
		dd = date;
		hh = hour;
		min = minute;
		ss = second;

		validate();
	}

	/**
	 * Create an instance of PASDate from a String
	 * 
	 * @return PASDate or null if the formatter cannot resolve the date
	 * @param aString
	 *            java.lang.String The input string must be in the following
	 *            format: "07/10/1996 3:10 PM EDT", that is, it must use slashes
	 *            for separators and include am/pm and time zone
	 */

	public PASDate(String aDateString)
	{
		
	}

	/**
	 * Answer the day of month Creation date: (10/20/00 4:24:57 PM)
	 */
	public int getDayOfMonth()
	{
		return get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Answer the day of month Creation date: (10/20/00 4:24:57 PM)
	 */
	public int getDay()
	{
		return getDayOfMonth();
	}

	/**
	 * Answer the day of week Creation date: (10/20/00 4:24:57 PM)
	 */

	public int getDayOfWeek()
	{
		return get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Answer the month Creation date: (10/20/00 4:24:57 PM)
	 */

	public int getMonth()
	{
		int month = get(Calendar.MONTH);
		return ++month;
	}

	/**
	 * Answer the year Creation date: (10/20/00 4:24:57 PM)
	 */

	public int getYear()
	{
		return get(Calendar.YEAR);
	}

	/**
	 * Insert the method's description here. Creation date: (10/20/00 4:24:57
	 * PM)
	 */

	public void setDayOfMonth(int day)
	{
		set(DATE, day);
	}

	/**
	 * Insert the method's description here. Creation date: (10/20/00 4:24:57
	 * PM)
	 */

	public void setDay(int day)
	{
		setDayOfMonth(day);
	}

	/**
	 * Change only the month attribute of the date Month is 0-based Creation
	 * date: (10/20/00 4:24:57 PM)
	 */

	public void setMonth(int month)
	{
		set(MONTH, --month);
	}

	/**
	 * Change only the year attribute of the date Creation date: (10/20/00
	 * 4:24:57 PM)
	 */

	public void setYear(int year)
	{
		set(YEAR, year);
	}

	/**
	 * Answer a displayable date in the format: "Monday, 10/23/2000 at 09:33:09
	 * EDT" Creation date: (10/20/00 3:33:46 PM)
	 * 
	 * @return java.lang.String
	 */

	public String toString()
	{
		// for some reason this does NOT display the actual time zone - only the
		// local time zone
		// use getTimeZone() for true zone
		SimpleDateFormat formatter = new SimpleDateFormat(
				"EEEE, MM/dd/yyyy 'at' hh:mm:ss z");
		String dateString = formatter.format(getTime());
		return dateString;
	}

	private int dd;

	private int hh;

	private int min;

	private int mm;

	private int ss;

	private int yyyy;

	/**
	 * Insert the method's description here. Creation date: (1/24/01 11:02:40
	 * AM)
	 * 
	 * @param year
	 *            java.lang.String
	 * @param month
	 *            java.lang.String
	 * @param date
	 *            java.lang.String
	 */

	public PASDate(String year, String month, String date)
			throws EDateIsInvalid
	{
		this(Integer.parseInt(year), Integer.parseInt(month), Integer
				.parseInt(date));
	}

	/**
	 * Insert the method's description here. Creation date: (1/24/01 11:02:40
	 * AM)
	 * 
	 * @param year
	 *            java.lang.String
	 * @param month
	 *            java.lang.String
	 * @param date
	 *            java.lang.String
	 */

	public PASDate(String year, String month, String date, String hour,
			String minute) throws EDateIsInvalid
	{
		this(Integer.parseInt(year), Integer.parseInt(month), Integer
				.parseInt(date), Integer.parseInt(hour), Integer
				.parseInt(minute));
	}

	/**
	 * Insert the method's description here. Creation date: (1/24/01 11:02:40
	 * AM)
	 * 
	 * @param year
	 *            java.lang.String
	 * @param month
	 *            java.lang.String
	 * @param date
	 *            java.lang.String
	 */

	public PASDate(String year, String month, String date, String hour,
			String minute, String second) throws EDateIsInvalid
	{
		this(Integer.parseInt(year), Integer.parseInt(month), Integer
				.parseInt(date), Integer.parseInt(hour), Integer
				.parseInt(minute), Integer.parseInt(second));
	}

	/**
	 * Answer the year Creation date: (10/20/00 4:24:57 PM)
	 */

	public int getHour()
	{
		return get(Calendar.HOUR);
	}

	/**
	 * Answer the year Creation date: (10/20/00 4:24:57 PM)
	 */

	public int getMinute()
	{
		return get(Calendar.MINUTE);
	}

	/**
	 * Answer the year Creation date: (10/20/00 4:24:57 PM)
	 */

	public int getSecond()
	{
		return get(Calendar.SECOND);
	}

	/**
	 * When the PASDate instance is created, store the values passed to
	 * GregorianCalendar. GC will 'adjust' the date to the nearest valid date -
	 * therefore if the new values aren't the same as the stored values, assume
	 * the date is invalid. Creation date: (1/24/01 3:32:47 PM)
	 */

	private void validate() throws EDateIsInvalid
	{
		if (!((this.getDayOfMonth() == dd) && (this.getMonth() == mm)
				&& (this.getYear() == yyyy) && (this.getHour() == hh)
				&& (this.getMinute() == min) && (this.getSecond() == ss)))
		{
			throw new EDateIsInvalid("Invalid date entered");
		}

	}

	/**
	 * Insert the method's description here. Creation date: (2/5/01 1:06:25 PM)
	 */

	public static int calculateAge(PASDate birthdate)
	{
		PASDate birthday = null;
		PASDate today = new PASDate();

		// create this year's birthday
		try
		{
			birthday = new PASDate(today.getYear(), birthdate.getMonth(),
					birthdate.getDayOfMonth());
		} catch (EDateIsInvalid e)
		{
		}

		int age = today.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR);
		return birthday.after(today) ? --age : age;
	}

	/**
	 * Answer a displayable date in the format: "Monday, 10/23/2000 at 09:33:09
	 * EDT" Creation date: (10/20/00 3:33:46 PM)
	 * 
	 * @return java.lang.String
	 */

	public String dateString()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String dateString = formatter.format(getTime());
		return dateString;
	}

	public String dateDashString()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(getTime());
		return dateString;
	}

	/**
	 * Return a sort date in the format: "yyyyMMdd" Creation date: (09/06/2002
	 * 12:33:46 PM)
	 * 
	 * @return java.lang.String
	 */
	public String sortDateString()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(getTime());
		return dateString;
	}

	/**
	 * Return a date in the format: "MM/dd/yyy" Creation date: (09/06/2002
	 * 12:33:46 PM)
	 * 
	 * @return java.lang.String
	 */
	public String slashDateString()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String dateString = formatter.format(getTime());
		return dateString;
	}

	/**
	 * Answer a displayable time in the format: "09:33:09 EDT" Creation date:
	 * (10/20/00 3:33:46 PM)
	 * 
	 * @return java.lang.String
	 */

	public String timeString()
	{
		// for some reason this does NOT display the actual time zone - only the
		// local time zone
		// use getTimeZone() for true zone
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
		String timeString = formatter.format(getTime());
		return timeString;
	}

	public PASDate(java.sql.Timestamp aTimestamp)
	{
		this();
		setTime(aTimestamp);
	}

	/**
	 * Create an instance using a Date instance
	 */
	public PASDate(java.util.Date aDate)
	{
		this();
		setTime(aDate);
	}

	/**
	 * Answer the year Creation date: (10/20/00 4:24:57 PM)
	 */

	public int getAmPm()
	{
		return get(Calendar.AM_PM);
	}

	/**
	 * Answer the year Creation date: (10/20/00 4:24:57 PM)
	 */

	public String getAmPmString()
	{
		return getAmPm() == AM ? "AM" : "PM";
	}

	/**
	 * Change only the year attribute of the date Creation date: (10/20/00
	 * 4:24:57 PM)
	 */

	public void setAmPm(int ampm)
	{
		set(AM_PM, ampm);
	}

	/**
	 * Change only the year attribute of the date Creation date: (10/20/00
	 * 4:24:57 PM)
	 */

	public void setHour(int hour)
	{
		set(HOUR, hour);
	}

	/**
	 * Change only the year attribute of the date Creation date: (10/20/00
	 * 4:24:57 PM)
	 */

	public void setMinute(int minute)
	{
		set(MINUTE, minute);
	}

	/**
	 * Change only the year attribute of the date Creation date: (10/20/00
	 * 4:24:57 PM)
	 */

	public void setHour(String hour)
	{
		setHour(Integer.parseInt(hour));
	}

	/**
	 * Change only the year attribute of the date Creation date: (10/20/00
	 * 4:24:57 PM)
	 */

	public void setMinute(String minute)
	{
		setMinute(Integer.parseInt(minute));
	}

	/**
	 * Change only the second attribute of the date Creation date: (10/20/00
	 * 4:24:57 PM)
	 */

	public void setSecond(int second)
	{
		set(SECOND, second);
	}

	/**
	 * Insert the method's description here. Creation date: (3/28/01 2:38:37 PM)
	 */

	public void setTimeZone(String zone)
	{
		setTimeZone(TimeZone.getTimeZone(zone));
	}

	/**
	 * Change only the year attribute of the date Creation date: (10/20/00
	 * 4:24:57 PM)
	 */

	public void setAmPm(String ampm)
	{
		set(AM_PM, ampm == "AM" ? Calendar.AM : Calendar.PM);
	}

	/**
	 * Answer a displayable time in the format: "09:33:09 EDT" Creation date:
	 * (10/20/00 3:33:46 PM)
	 * 
	 * @return java.lang.String
	 */

	public String dateTimeString()
	{
		// for some reason this does NOT display the actual time zone - only the
		// local time zone
		// use getTimeZone() for true zone
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String timeString = formatter.format(getTime());
		return timeString;
	}

	/**
	 * Answer the day of month Creation date: (10/20/00 4:24:57 PM)
	 */

	public String getDayOfMonthString()
	{
		return StringUtil.padStringBegin("" + getDayOfMonth(), 2, "0");
	}

	/**
	 * Answer the year Creation date: (10/20/00 4:24:57 PM)
	 */

	public String getHourString()
	{
		return StringUtil.padStringBegin("" + getHour(), 2, "0");
	}

	/**
	 * Answer the month Creation date: (10/20/00 4:24:57 PM)
	 */

	public String getMonthString()
	{
		return StringUtil.padStringBegin("" + getMonth(), 2, "0");
	}

	public String toDateAndTimeString()
	{
		// for some reason this does NOT display the actual time zone - only the
		// local time zone
		// use getTimeZone() for true zone
		SimpleDateFormat formatter = new SimpleDateFormat(
				"EEEE, MM/dd/yyyy 'at' hh:mm");
		String dateString = formatter.format(getTime());
		return dateString;
	}

	/**
	 * Answer the year Creation date: (10/20/00 4:24:57 PM)
	 */

	public int getMilitaryHour()
	{
		return get(Calendar.HOUR_OF_DAY);
	}
}