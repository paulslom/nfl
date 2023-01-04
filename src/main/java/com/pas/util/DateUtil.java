package com.pas.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.validator.DateValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.exception.EDateFormatNotValid;
import com.pas.exception.PASSystemException;
import com.pas.valueObject.AppDate;
import com.pas.valueObject.AppDateTimeStamp;

/**
 * Insert the type's description here.
 * Creation date: (9/18/00 1:57:48 PM)
 * @author: Administrator
 */
public class DateUtil {

	protected static Logger logger = LogManager.getLogger(DateUtil.class);

	/**
	 * DateUtil constructor comment.
	 */
	public DateUtil() {
		super();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (9/18/00 1:59:23 PM)
	 * @return java.util.Date
	 * @param stringDateMMDDYYYY java.lang.String
	 */
	public static java.util.Date getDateObj(String stringDateMMDDYYYY) {
		Date d = null;
		if (stringDateMMDDYYYY != null) {
			//Check for correct format with slashes, if not add them.
			if (!stringDateMMDDYYYY.substring(2).equals("/")) {
				if (stringDateMMDDYYYY.length() == 8) {
					String s =
						stringDateMMDDYYYY.substring(0, 2)
							+ "/"
							+ stringDateMMDDYYYY.substring(2, 4)
							+ "/"
							+ stringDateMMDDYYYY.substring(4);
					stringDateMMDDYYYY = s;
				}
			}
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
			try {
				d = df.parse(stringDateMMDDYYYY);
			} catch (Exception e) {
				logger.error("getDateObj()  Error: " + e);
			}
		}
		return d;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (9/18/00 1:59:23 PM)
	 * @return java.util.Date
	 * @param stringDateMMDDYYYY java.lang.String
	 */
	public static java.util.Date getDateObj(
		String month,
		String day,
		String year)
		throws EDateFormatNotValid {
		Date d = null;

		// ensure that all fields are entered
		if (month == null || day == null || year == null)
			throw new EDateFormatNotValid();

		month = month.trim();
		day = day.trim();
		year = year.trim();

		// ensure that all fields are entered
		if (month.length() == 0 || day.length() == 0 || year.length() == 0)
			throw new EDateFormatNotValid();

		// convert to "mm/dd/yyyy" and parse
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		try {
			StringBuffer bufferDateMMDDYYYY = new StringBuffer(25);
			bufferDateMMDDYYYY.append(
				(month.length() > 2) ? month.substring(0, 2) : month);
			bufferDateMMDDYYYY.append("/");
			bufferDateMMDDYYYY.append(
				(day.length() > 2) ? day.substring(0, 2) : day);
			bufferDateMMDDYYYY.append("/");
			bufferDateMMDDYYYY.append(
				(year.length() > 4) ? year.substring(0, 4) : year);
			d = df.parse(bufferDateMMDDYYYY.toString());
		} catch (Exception e) {
			throw new EDateFormatNotValid();
		}
		return d;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (9/20/00 4:56:52 PM)
	 * @return java.util.Date
	 * @param day short
	 * @param month short
	 * @param year short
	 */
	public static Date getDateObj(short day, short month, short year) {
		String d = new Short(day).toString();
		String m = new Short(month).toString();
		String y = new Short(year).toString();
		Date dte = new Date();
		try {
			//Using the String version as the base for getting the date object	
			dte = getDateObj(m, d, y);
		} catch (Exception e) {
			logger.error(
				"DateUtil.getDateObj()  Method with short as input params.   Exception: "
					+ e);
		}
		return dte;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (9/27/00 11:34:20 AM)
	 * @return java.util.Date
	 * @param YYYYMMDD java.lang.String
	 */
	public static Date getDateObjFromLegacy(String YYYYMMDD) {

		//Check for correct format with slashes, if not add them.
		Date d = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		try {
			String correct =
				YYYYMMDD.substring(4, 6).trim()
					+ "/"
					+ YYYYMMDD.substring(6).trim()
					+ "/"
					+ YYYYMMDD.substring(0, 4).trim();
			d = df.parse(correct);
		} catch (Exception e) {
			logger.error("DateUtil.getDateObjFromLegacy() Exception: " + e);
		}
		return d;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (9/20/00 4:34:30 PM)
	 * @return java.lang.String
	 * @param d java.util.Date
	 */
	public static String getLegacyDate(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);

		String dd =
			com.pas.util.StringUtil.padStringBegin(
				new Integer(c.get(Calendar.DAY_OF_MONTH)).toString().trim(),
				2,
				"0");
		String mm =
			com.pas.util.StringUtil.padStringBegin(
				new Integer(c.get(Calendar.MONTH) + 1).toString().trim(),
				2,
				"0");
		String yyyy =
			com.pas.util.StringUtil.padStringBegin(
				new Integer(c.get(Calendar.YEAR)).toString().trim(),
				4,
				"0");

		return yyyy + mm + dd;

	}
	/**
	 * Insert the method's description here.
	 * Creation date: (9/20/00 4:31:00 PM)
	 * @return short
	 */
	public static short getMonth(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);

		return new Integer(c.get(Calendar.MONTH) + 1).shortValue();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (9/20/00 4:34:30 PM)
	 * @return java.lang.String
	 * @param d java.util.Date
	 */
	public static String getNormalDate(Date d) {
		String normalDate = new String("");
		if (d != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			try {
				String day =
					StringUtil.padStringBegin(
						new Integer(c.get(Calendar.DAY_OF_MONTH)).toString().trim(),
						2,
						"0");
				String month =
					StringUtil.padStringBegin(
						new Integer(c.get(Calendar.MONTH) + 1).toString().trim(),
						2,
						"0");
				String year =
					StringUtil.padStringBegin(
						new Integer(c.get(Calendar.YEAR)).toString().trim(),
						4,
						"0");
				normalDate = month + "/" + day + "/" + year;
			} catch (Exception e) {
				logger.error("DateUtil.getNormalDate()  Exception: " + e);
			}
		}
		return normalDate;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (9/25/00 12:54:18 PM)
	 * @return java.lang.String
	 * @param legacyDate java.lang.String
	 */
	public static String getSybaseDateTimeString(String legacyDate) {

		//The legacy date is formatted as follows coming in: YYYYMMDD
		//The Sybase DateTime String is formatted as follows: MM/DD/YYYY

		if (legacyDate.length() == 8)
			return (
				legacyDate.substring(4, 6)
					+ "/"
					+ legacyDate.substring(6)
					+ "/"
					+ legacyDate.substring(0, 4));
		
		return "";

	}
	/**
	 * Insert the method's description here.
	 * Creation date: (9/20/00 4:32:30 PM)
	 * @return short
	 * @param d java.util.Date
	 */
	public static short getYear(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);

		return new Integer(c.get(Calendar.YEAR)).shortValue();
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (9/20/00 4:31:00 PM)
	 * @return short
	 */
	public static String getDateMMDDYYYY(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);

		String mm =
			StringUtil.padStringBegin(
				new Integer(c.get(Calendar.MONTH) + 1).toString().trim(),
				2,
				"0");
		String dd =
			StringUtil.padStringBegin(
				new Integer(c.get(Calendar.DAY_OF_MONTH)).toString().trim(),
				2,
				"0");
		String yyyy =
			StringUtil.padStringBegin(
				new Integer(c.get(Calendar.YEAR)).toString().trim(),
				4,
				"0");

		return mm + dd + yyyy;

	}

	/**
	 * Insert the method's description here.
	 * Creation date: (9/20/00 4:12:27 PM)
	 * @return short
	 * @param d java.util.Date
	 */
	public static int getDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);

		return new Integer(c.get(Calendar.DAY_OF_MONTH)).shortValue();
	}

	public static int getDifferenceInDays(Date date1, Date date2)
	{
		final long MS_IN_A_DAY = 1000*60*60*24;
		
		long diff = date1.getTime() - date2.getTime();
		 
		Long numDays = diff / MS_IN_A_DAY;

		return numDays.intValue();
	}
	
	public static Date addDaystoDate(Date dateFrom, int daysToAdd)
	{
		final long MS_IN_A_DAY = 1000*60*60*24;
		
		long millisecondsToAdd = daysToAdd * MS_IN_A_DAY;
		
		long millisecondsOfNewDate = dateFrom.getTime() + millisecondsToAdd;
		 
		return new Date(millisecondsOfNewDate);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (10/18/01 1:37:16 PM)
	 * @return java.lang.String
	 * @param d java.util.Date
	 */
	public static String getStringDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);

		return new Integer(c.get(Calendar.DAY_OF_MONTH)).toString();
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (9/20/00 4:31:00 PM)
	 * @return short
	 */
	public static String getStringMonth(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);

		return new Integer(c.get(Calendar.MONTH) + 1).toString();
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (9/20/00 4:32:30 PM)
	 * @return short
	 * @param d java.util.Date
	 */
	public static String getStringYear(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);

		return new Integer(c.get(Calendar.YEAR)).toString();
	}

public static String addLeadingZero(String string){
		
		if (string != null){
			if (string.length() < 2 ){
				string = "0" + string;
			}
		}
		return string;
	}
	
	/**
	 * returns 0 if date1>date2, 1 if date2>date1, 2 if
	 * dates are same
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws PASSystemException
	 */
	public static int compareDate(Object date1, Object date2)
			throws PASSystemException {
		if ((date1 instanceof AppDate) && (date2 instanceof AppDate)) {
			AppDate AppDate1 = (AppDate) date1;
			AppDate AppDate2 = (AppDate) date2;
			return compareAppDate(AppDate1, AppDate2);
		}		

		return -1;
	}

	/**
	 * comapres two AppDates returns 0 if date1>date2, 1 if date2>date1, 2 if
	 * dates are same
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws PASSystemException
	 */
	private static int compareAppDate(AppDate date1, AppDate date2)
			throws PASSystemException {

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.set(Integer.parseInt(date1.getAppyear()), Integer.parseInt(date1
				.getAppmonth())-1, Integer.parseInt(date1.getAppday()), 0, 0, 0);
		cal2.set(Integer.parseInt(date2.getAppyear()), Integer.parseInt(date2
				.getAppmonth())-1, Integer.parseInt(date2.getAppday()), 0, 0, 0);
		if (cal1.after(cal2))
			return 0;
		else if (cal1.before(cal2))
			return 1;
		else
			return 2;
	}	

	public static AppDate getCurrentDate() {

		java.util.Date today = new java.util.Date(System.currentTimeMillis());
		String[] splitCurrentDate = PASUtil.getSplitDate(today);
		AppDate value = new AppDate();
		value.setAppmonth(splitCurrentDate[0]);
		value.setAppday(splitCurrentDate[1]);
		value.setAppyear(splitCurrentDate[2]);

		return value;
	}
	
	public static AppDateTimeStamp getCurrentDateTimeStamp() {
		Calendar lobjcal = Calendar.getInstance();
		java.util.Date today = new java.util.Date();
		lobjcal.setTime(today);

		AppDateTimeStamp value = new AppDateTimeStamp();
		value.setAppmonth(addLeadingZero(Integer.toString(lobjcal.get(Calendar.MONTH) + 1)));
		value.setAppday(addLeadingZero(Integer.toString(lobjcal.get(Calendar.DAY_OF_MONTH))));
		value.setAppyear(Integer.toString(lobjcal.get(Calendar.YEAR)));
		value.setHour(addLeadingZero(Integer.toString(lobjcal.get(Calendar.HOUR_OF_DAY))));
		value.setMinute(addLeadingZero(Integer.toString(lobjcal.get(Calendar.MINUTE))));
		value.setSecond(addLeadingZero(Integer.toString(lobjcal.get(Calendar.SECOND))));
		value.setMilliseconds(Integer.toString(lobjcal.get(Calendar.MILLISECOND)));
		return value;
	}
	public static AppDate getDeepCopy(AppDate date)
	{
		AppDate newDate = new AppDate();
		if(date!=null)
		{		
			newDate.setAppday(date.getAppday());
			newDate.setAppmonth(date.getAppmonth());
			newDate.setAppyear(date.getAppyear());
		}		
		return newDate;
	}

	public static int getThirdFridayInt(int weekdayOfFirstOfMonth)
	{
		int firstFridayOfTheMonth = 0;
	    
	    switch (weekdayOfFirstOfMonth)
	    {
            case 1 : //Sunday
            {
            	firstFridayOfTheMonth = 6;
                break;
            }
            case 2 : //Monday
            {
            	firstFridayOfTheMonth = 5;
                break;
            }
            case 3 : //Tuesday
            {
            	firstFridayOfTheMonth = 4;
                break;
            }
            case 4 : //Wednesday
            {
            	firstFridayOfTheMonth = 3;
                break;
            }
            case 5 : //Thursday
            {
            	firstFridayOfTheMonth = 2;
                break;
            }
            case 6 : //Friday
            {
            	firstFridayOfTheMonth = 1;
                break;
            }
            case 7 : //Saturday
            {
            	firstFridayOfTheMonth = 7;
                break;
            }
	    }
	    
	    return firstFridayOfTheMonth + 14;
	}
	
	public static AppDate getNextThirdFriday() throws ParseException
	{
		AppDate thirdFriday = new AppDate();
		
		Calendar calToday = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
		String currDate = dateFormat.format(calToday.getTime());
	
		//now that we have today, create a date of this month, 1st of the month
		//also create next month, first of the month
		
		Integer thisMonth = Integer.valueOf(currDate.substring(0,2));
		Integer nextMonth = thisMonth + 1;
		Integer thisYear = Integer.valueOf(currDate.substring(4,8));
		Integer nextMonthYear = Integer.valueOf(currDate.substring(4,8));
		
		if (nextMonth > 12)
		{
			nextMonth = 1;
			nextMonthYear = nextMonthYear + 1;
		}	
		
		java.util.Date firstOfThisMonth;
		
		if (thisMonth < 10)
		   firstOfThisMonth = dateFormat.parse("0" + thisMonth.toString() + "01" + thisYear.toString());
		else 
			firstOfThisMonth = dateFormat.parse(thisMonth.toString() + "01" + thisYear.toString());
		
		java.util.Date firstOfNextMonth;
		
		if (nextMonth < 10)
		   firstOfNextMonth = dateFormat.parse("0" + nextMonth.toString() + "01" + nextMonthYear.toString());
		else
		   firstOfNextMonth = dateFormat.parse(nextMonth.toString() + "01" + nextMonthYear.toString());
		
		//now that we have the first of this month, figure out what day of the week that is.
		
		Calendar calTemp = Calendar.getInstance();
		calTemp.setTime(firstOfThisMonth); //transactionDate is a Timestamp, which extends Date
	    int dowFirstOfThisMonth = calTemp.get(Calendar.DAY_OF_WEEK);
	     
	    java.util.Date thirdFridayThisMonthDate;
	    
	    if (thisMonth < 10)
	       thirdFridayThisMonthDate= dateFormat.parse("0" + thisMonth.toString() + getThirdFridayInt(dowFirstOfThisMonth) + thisYear.toString());
	    else
	       thirdFridayThisMonthDate= dateFormat.parse(thisMonth.toString() + getThirdFridayInt(dowFirstOfThisMonth) + thisYear.toString());
		
	    Calendar calThirdFriThisMonth = Calendar.getInstance();
		calThirdFriThisMonth.setTime(thirdFridayThisMonthDate);
		
		if (calThirdFriThisMonth.after(calToday))
		{
			thirdFriday = translateDate(thirdFridayThisMonthDate);
		}
		else
		{
			Calendar calTemp2 = Calendar.getInstance();
			calTemp2.setTime(firstOfNextMonth); //transactionDate is a Timestamp, which extends Date
		    int dowFirstOfNextMonth = calTemp2.get(Calendar.DAY_OF_WEEK);
		     
		    java.util.Date thirdFridayNextMonthDate;
		    if (nextMonth < 10)
			   thirdFridayNextMonthDate = dateFormat.parse("0" + nextMonth.toString() + getThirdFridayInt(dowFirstOfNextMonth) + nextMonthYear.toString());
		    else
			   thirdFridayNextMonthDate = dateFormat.parse(nextMonth.toString() + getThirdFridayInt(dowFirstOfNextMonth) + nextMonthYear.toString());
			   	
			thirdFriday = translateDate(thirdFridayNextMonthDate);
		}
		
		
		return thirdFriday;
	}
	
	public static AppDateTimeStamp getDeepCopy(AppDateTimeStamp dateTime)
	{
		AppDateTimeStamp newDateTime = new AppDateTimeStamp();
		
		if(dateTime!=null)
		{		
			newDateTime.setAppday(dateTime.getAppday());
			newDateTime.setAppmonth(dateTime.getAppmonth());
			newDateTime.setAppyear(dateTime.getAppyear());
			newDateTime.setHour(dateTime.getHour());
			newDateTime.setMinute(dateTime.getMinute());
			newDateTime.setSecond(dateTime.getSecond());
			newDateTime.setMilliseconds(dateTime.getMilliseconds());
		}		
		return newDateTime;
	}

	public static AppDate getIncrementedMonth(AppDate date) {
		
		AppDate newDate = new AppDate();
		
		int tempMonth = Integer.parseInt(date.getAppmonth());
		int tempYear = Integer.parseInt(date.getAppyear());
		
		tempMonth = tempMonth + 1;
		if (tempMonth > 12)
		{
			tempMonth = 1;
			tempYear = tempYear + 1;
		}
		
		newDate.setAppmonth(Integer.toString(tempMonth));
		newDate.setAppday(date.getAppday());
		newDate.setAppyear(Integer.toString(tempYear));
		
		return newDate;
	}		

	public static AppDate getMaxDate(AppDate d1, AppDate d2) {
		AppDate date = null;
		try {
			if (compareDate(d1, d2) == 0)
				date = d1;
			else
				date = d2;
		} catch (Exception e) {
			logger.error("getMaxDate", e);
		}
		return date;
	}



	
	public static AppDate getMinDate(AppDate d1, AppDate d2) {
		AppDate date = null;
		try {
			if (compareDate(d1, d2) == 0)
				date = d2;
			else
				date = d1;
		} catch (Exception e) {
			logger.error("getMinDate", e);
		}
		return date;
	}	

	public static boolean isDateInRange(Object date, Object minDate,
			Object maxDate) 
	{
		boolean RetVal = true;
		try {
			// reverse the dates if minDate is greater than maxDate.
			if (compareDate(minDate, maxDate) == 0) {
				Object temp = minDate;
				minDate = maxDate;
				maxDate = temp;
			}
		}catch(Exception e){
			logger.error("isDateInRange", e);
		}

		if (IsLessThan(date, minDate))
		    RetVal = false;  
		
		if (RetVal && IsGreaterThan(date, maxDate))
  		    RetVal = false;

   	    return RetVal;
 
	}

	//	True if datetobeChecked == comparedToDate
	public static boolean IsEqual(Object date, Object compareToDate) {
		boolean ret = false;
		try {
			if (compareDate(date, compareToDate) == 2)
				ret = true;
			else
				ret = false;
		} catch (Exception e) {
			logger.error("IsEqual", e);
		}
		return ret;
	}

	//True if datetobeChecked > comparedToDate
	public static boolean IsGreaterThan(Object date, Object compareToDate) {
		boolean ret = false;
		try {
			if (compareDate(date, compareToDate) == 0)
				ret = true;
			else
				ret = false;
		} catch (Exception e) {
			logger.error("IsGreaterThan", e);
		}
		return ret;
	}

	//- obj can be AppDate/Date
	public static boolean IsLessThan(Object date, Object compareToDate) {
		boolean ret = false;
		try {
			if (compareDate(date, compareToDate) == 1)
				ret = true;
			else
				ret = false;
		} catch (Exception e) {
			logger.error("IsLessThan", e);
		}
		return ret;
	}

	//	- obj can be AppDate/Date
	public static boolean IsLessThanOrEqual(Object date, Object compareToDate) {
		boolean ret = false;
		try {
			int compare = compareDate(date, compareToDate);
			if (compare > 0)
				ret = true;
			else
				ret = false;
		} catch (Exception e) {
			logger.error("IsLessThan", e);
		}
		return ret;
	}
    /**
     * Validates that any input in the format mm/dd/yyyy is a valid date.
     */
    public static boolean isValidDate(String value)
    {
        StringBuffer rawDate = new StringBuffer();
        int totalTokens;
        String token;
        int tokenIndex = 0;
        int year = 0;
        boolean valDate = false;
        
        value = value.trim();
        StringTokenizer dashes = new StringTokenizer(value, "-");
        totalTokens = dashes.countTokens();
        
        //If not exactly 3 tokens then bad format
        if (!(totalTokens == 3))
        {
            return false;
        }
        
        //Replace "-" with "/" and if only two tokens then default day(dd) to "01"
        
        while (dashes.hasMoreTokens())
        {
            tokenIndex++;
            token = dashes.nextToken();
            
            //length of mm can be 1 or 2.  If 1, add leading zero
            
            if (tokenIndex == 1 || tokenIndex == 2)
            {	
            	switch (token.length())
            	{
            	   case 1:
            		 token = "0" + token;
            		 break;
            	   case 2:
            		 break;
            	   default:	
                     return false;
                }

                rawDate.append(token);
                rawDate.append("/");
            }
            
            else //this is token 3, the year    
            
            {
                //length of yyyy should be 4
                if (token.length() != 4)
                {
                    return false;
                }
                year = Integer.valueOf(token).intValue();
                
                if (year < 1900)
                {
                    return false;
                }
                
                rawDate.append(token);
            }
        }
        
        DateValidator dv = DateValidator.getInstance();
        
        valDate = dv.isValid(rawDate.toString(), "MM/dd/yyyy", true);
      
        return valDate;
       
    }

    /**
     * Validates that any input in the format mm/dd/yyyy is a valid date.
     */
    public static boolean isValidFullDate(String value) {
        StringBuffer rawDate = new StringBuffer();
        int i;
        String token;
        int tokenIndex = 0;
        int year = 0;
        try {
            value = value.trim();
            StringTokenizer dashes = new StringTokenizer(value, "/");
            i = dashes.countTokens();
            //If more than three dashes then bad format
            if (i > 3) {
                return false;
            }
            //Replace "-" with "/" and if only two tokens then default day(dd) to "01"
            while (dashes.hasMoreTokens()) {
                tokenIndex++;
                token = dashes.nextToken();

                //lenght of mm should be 2
                if (tokenIndex == 1 && token.length() != 2) {
                    return false;
                }

                rawDate.append(token);
                rawDate.append("/");

                //Get the year part so it can checked for a minimum value.
                if (i == 2 && tokenIndex == 2) {
                    //lenght of yyyy should be 4
                    if (token.length() != 4) {
                        return false;
                    }
                    year = Integer.valueOf(token).intValue();
                } else if (i == 3 && tokenIndex == 3) {
                    //lenght of yyyy should be 4
                    if (token.length() != 4) {
                        return false;
                    }
                    year = Integer.valueOf(token).intValue();
                }

                //lenght of dd should be 4
                if (i == 3 && tokenIndex == 2 && token.length() != 2) {
                    return false;
                }

            }

            if (year < 1900) {
                return false;
            }

            //Parse the date.
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
            dateFormat.setLenient(false);

        } catch (NumberFormatException nfe) {

            return false;
        }

        return true;
    } // isValidFullDate

	// should return a CSW Date.
	public static AppDate translateDate(java.util.Date date) {

		String[] splitCurrentDate = PASUtil.getSplitDate(date);
		AppDate value = new AppDate();
		value.setAppmonth(splitCurrentDate[0]);
		value.setAppday(splitCurrentDate[1]);
		value.setAppyear(splitCurrentDate[2]);

		return value;
	}
	
	public static Date getNextMonthFirstDay(Calendar cal)
	{
		//Calendar class uses Month indices 0..11 for January to December...
		
		int thisMonth = cal.get(Calendar.MONTH);
		int nextMonth = thisMonth + 1;
		
		if (nextMonth == 12)
		{	
			nextMonth = 0; //January
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
		}
		
		cal.set(Calendar.MONTH, nextMonth);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
		
	    return new Date(cal.getTimeInMillis());
	}
	
	public static Date getPriorMonthFirstDay(Calendar cal)
	{
		//Calendar class uses Month indices 0..11 for January to December...
		
		int thisMonth = cal.get(Calendar.MONTH);
		int lastMonth = thisMonth - 1;
		
		if (lastMonth == -1)
		{	
			lastMonth = 11; //December
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
		}
		
		cal.set(Calendar.MONTH, lastMonth);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
		
	    return new Date(cal.getTimeInMillis());
	}
	
	public static Date getPriorMonthLastDay(Calendar cal)
	{
		//Calendar class uses Month indices 0..11 for January to December...
		
		int thisMonth = cal.get(Calendar.MONTH);
		int lastMonth = thisMonth;

		if (lastMonth == -1)
		{	
			lastMonth = 11; //December
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
		}
		
		cal.set(Calendar.MONTH, lastMonth);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new Date(cal.getTimeInMillis());
	}
	
	public static void main(String[] args)
	{		
		AppDate ad = new AppDate();
		try
		{
			ad = getNextThirdFriday();
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ad.toStringYYYYMMDD());
	}

}