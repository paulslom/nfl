package com.pas.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.naming.InitialContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.constants.IAppConstants;
import com.pas.exception.PASSystemException;
import com.pas.valueObject.Address;

public class PASUtil
{

	static Logger log = LogManager.getLogger(PASUtil.class);

	/**
	 * constructor
	 */
	public PASUtil()
	{
	}

	/**
	 * validates that the imput has only numbers from 0 to 9
	 */
	public static boolean isValidNumeric(String value)
	{
		char ch;

		try
		{
			for (int i = 0; i < value.length(); i++)
			{
				ch = value.charAt(i);
				if (!(ch >= '0' && ch <= '9'))
					return false;
			}
		} catch (NumberFormatException ne)
		{
			return false;
		}
		return true;
	}

	/**
	 * validates that the input has only numbers from 0 to 9 and that there are at most 2 decimals after the point
	 */
	public static boolean isValidDecimal(String value)
	{
		char ch;

		int decimalPosition = 99;

		for (int i = 0; i < value.length(); i++)
		{
			ch = value.charAt(i);
			if (!(ch >= '0' && ch <= '9') && ch != '.')
				return false;
			if (ch == '.')
			{
				if (decimalPosition == 99)
					decimalPosition = i + 1;
				else
					return false;
			}
		}

		if (decimalPosition != 99)
		{
			if (value.length() - decimalPosition > 2)
				return false;
		}
		return true;
	}

	/**
	 * validates that the input has only numbers from 0 to 9 and that there are at most 4 decimals after the point
	 */
	public static boolean isValidDecimalForUnits(String value)
	{
		char ch;

		int decimalPosition = 99;

		for (int i = 0; i < value.length(); i++)
		{
			ch = value.charAt(i);
			if (!(ch >= '0' && ch <= '9') && ch != '.')
				return false;
			if (ch == '.')
			{
				if (decimalPosition == 99)
					decimalPosition = i + 1;
				else
					return false;
			}
		}

		if (decimalPosition != 99)
		{
			if (value.length() - decimalPosition > 4)
				return false;
		}
		return true;
	}
	/**
	 * Check whether month entered is valid
	 */
	public static boolean validMonth(String mon)
	{
		if (!isValidNumeric(mon))
		{
			return false;

		} 
		try
		{
			int month = Integer.parseInt(mon);

			if (month < 0 || month > 12)
			{
				return false;
			}
			
			return true;
			
		}
		catch (NumberFormatException ne)
		{
			return false;
		}
	}
	
	public static String getJ2EESYS()
	{
		String currentEnv = null;
		
		try 
		{
			Object env = new InitialContext().lookup("java:comp/env/J2EE_SYS");
			currentEnv = env.toString();
		} 
		catch (Exception e) 
		{
			log.error("Exception encountered retrieving J2EE_SYS: " + e.getMessage());
		}
		
		return currentEnv;
	}
	/**
	 * Check whether day entered is valid
	 */

	public static boolean validDay(String day)
	{
		if (!isValidNumeric(day))
		{
			return false;

		}
		
		try
		{
			int theDay = Integer.parseInt(day);
			if (theDay < 0 || theDay > 31)
			{
				return false;
			}
			return true;
			
		}
		catch (NumberFormatException ne)
		{
			return false;
		}
		
	}

	/**
	 * Check whether year entered is valid
	 */

	public static boolean validYear(String yr)
	{
		if (yr.trim().length() < 4)
		{
			return false;
		}
		
		if (!isValidNumeric(yr))
		{
			return false;
		} 
		
		try
		{
			if (Integer.parseInt(yr) < 1900)
			{
				return false;
			} 
			return true;
		}
		catch (NumberFormatException ne)
		{
			return false;
		}
	}

	/**
	 * Validates that any input in the format mm/dd/yyyy or mm/yyyy is a valid
	 * date.
	 */
	public static boolean isValidDate(String value)
	{
		StringBuffer rawDate = new StringBuffer();
		int i;
		String token;
		int tokenIndex = 0;
		int year = 0;
		try
		{
			value = value.trim();
			StringTokenizer dashes = new StringTokenizer(value, "-");
			i = dashes.countTokens();
			// If more than three dashes then bad format
			if (i > 3)
			{
				return false;
			}
			// Replace "-" with "/" and if only two tokens then default day(dd)
			// to "01"
			while (dashes.hasMoreTokens())
			{
				tokenIndex++;
				token = dashes.nextToken();
				// lenght of mm should be 2
				if (tokenIndex == 1 && token.length() != 2)
				{
					return false;
				}

				rawDate.append(token);
				rawDate.append("/");
				// if in mm-yyyy format then default dd to 01 so that the date
				// can be parsed
				if (i == 2 && tokenIndex == 1)
				{
					rawDate.append("01/");
				}

				// Get the year part so it can checked for a minimum value.
				if (i == 2 && tokenIndex == 2)
				{
					// lenght of yyyy should be 4
					if (token.length() != 4)
					{
						return false;
					}
					year = Integer.valueOf(token).intValue();
				} else if (i == 3 && tokenIndex == 3)
				{
					// lenght of yyyy should be 4
					if (token.length() != 4)
					{
						return false;
					}
					year = Integer.valueOf(token).intValue();
				}

				// lenght of dd should be 4
				if (i == 3 && tokenIndex == 2 && token.length() != 2)
				{
					return false;
				}
			}

			if (year < 1900)
			{
				return false;
			}

			// Parse the date.
			DateFormat dateFormat = DateFormat
					.getDateInstance(DateFormat.SHORT);
			dateFormat.setLenient(false);

		} catch (NumberFormatException nfe)
		{
			return false;
		}

		return true;
	}

	/**
	 * Validates that any input in the format mm/dd/yyyy is a valid date.
	 */
	public static boolean isValidFullDate(String value)
	{
		StringBuffer rawDate = new StringBuffer();
		int i;
		String token;
		int tokenIndex = 0;
		int year = 0;
		try
		{
			value = value.trim();
			StringTokenizer dashes = new StringTokenizer(value, "/");
			i = dashes.countTokens();
			// If more than three dashes then bad format
			if (i > 3)
			{
				return false;
			}
			// Replace "-" with "/" and if only two tokens then default day(dd)
			// to "01"
			while (dashes.hasMoreTokens())
			{
				tokenIndex++;
				token = dashes.nextToken();

				// lenght of mm should be 2
				if (tokenIndex == 1 && token.length() != 2)
				{
					return false;
				}

				rawDate.append(token);
				rawDate.append("/");

				// Get the year part so it can checked for a minimum value.
				if (i == 2 && tokenIndex == 2)
				{
					// lenght of yyyy should be 4
					if (token.length() != 4)
					{
						return false;
					}
					year = Integer.valueOf(token).intValue();
				} else if (i == 3 && tokenIndex == 3)
				{
					// lenght of yyyy should be 4
					if (token.length() != 4)
					{
						return false;
					}
					year = Integer.valueOf(token).intValue();
				}

				// lenght of dd should be 4
				if (i == 3 && tokenIndex == 2 && token.length() != 2)
				{
					return false;
				}

			}

			if (year < 1900)
			{
				return false;
			}

			// Parse the date.
			DateFormat dateFormat = DateFormat
					.getDateInstance(DateFormat.SHORT);
			dateFormat.setLenient(false);

		} catch (NumberFormatException nfe)
		{

			return false;
		}

		return true;
	} // isValidFullDate

	/**
	 * Checks if the given date is greater than the current date.
	 */

	public static boolean isInTheFuture(String value)
	{
		int monthBirth, dayBirth, yearBirth;
		int monthCurr, dayCurr, yearCurr;
		// If string is empty return without error
		if (value == null || value.trim().length() == 0)
		{
			return true;
		}
		// If not greater than the current date return false
		// Get the current date into a string
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String currDate = dateFormat.format(calendar.getTime());
		StringTokenizer hyphensThisDate = new StringTokenizer(value, "/");
		StringTokenizer hyphensCurrDate = new StringTokenizer(currDate, "/");
		int tokensDate = hyphensThisDate.countTokens();
		if (tokensDate == 3)
		{
			try
			{
				monthBirth = Integer.valueOf(hyphensThisDate.nextToken())
						.intValue();
				dayBirth = Integer.valueOf(hyphensThisDate.nextToken())
						.intValue();
				yearBirth = Integer.valueOf(hyphensThisDate.nextToken())
						.intValue();
				monthCurr = Integer.valueOf(hyphensCurrDate.nextToken())
						.intValue();
				dayCurr = Integer.valueOf(hyphensCurrDate.nextToken())
						.intValue();
				yearCurr = Integer.valueOf(hyphensCurrDate.nextToken())
						.intValue();
			} catch (NumberFormatException e)
			{
				return false;
			}
			// Create calendars for all dates.
			GregorianCalendar thisDate = new GregorianCalendar(yearBirth,
					monthBirth - 1, dayBirth);
			GregorianCalendar currentDate = new GregorianCalendar(yearCurr,
					monthCurr - 1, dayCurr);
			// Compare dates
			if (thisDate.before(currentDate))
			{
				return false;
			} else if (thisDate.equals(currentDate))
			{
				return false;
			} else
			{
				return true;
			}
		} else if (tokensDate == 2)
		{
			try
			{
				// Validate for "MM/YYYY" format dates
				monthBirth = Integer.valueOf(hyphensThisDate.nextToken())
						.intValue();
				yearBirth = Integer.valueOf(hyphensThisDate.nextToken())
						.intValue();
				monthCurr = Integer.valueOf(hyphensCurrDate.nextToken())
						.intValue();
				dayCurr = Integer.valueOf(hyphensCurrDate.nextToken())
						.intValue();
				yearCurr = Integer.valueOf(hyphensCurrDate.nextToken())
						.intValue();

				java.util.Date thisDate = dateFormat.parse(yearBirth + "/"
						+ monthBirth + "/" + dayCurr);
				java.util.Date currentDate = dateFormat.parse(yearCurr + "/"
						+ monthCurr + "/" + dayCurr);

				if (thisDate.compareTo(currentDate) > 0)
				{
					return true;
				} 
				
				return false;
				
			} // try
			catch (NumberFormatException e)
			{
				return false;
			} catch (ParseException e)
			{
				return false;
			}
		} else
		{
			return false;
		}
	}

	/**
	 * @param stringToCheck
	 * @return <code>true</code> if the supplied string is either null or
	 *         empty
	 */
	public static boolean isNullOrEmpty(String stringToCheck)
	{
		if ((stringToCheck == null) || stringToCheck.trim().equals(""))
		{
			return true;
		}
		return false;
	}

	/**
	 * pad a string StrField with a size of iSize with char cFiller on the left
	 * (bJustify = True) or on the right(bJustify = False)
	 * 
	 * @param strField -
	 *            Field to Justify
	 * @param iSize -
	 *            Size of the Field
	 * @param cFiller -
	 *            Filler Character to Pad
	 * @param cJustify -
	 *            Specifies Left padding or Right padding
	 * @return String - Padded String
	 */

	public static String fieldJustify(String strField, int iSize, char cFiller,
			char cJustify)
	{

		String strArg = strField;
		if (strField == null)
			strArg = "";
		StringBuffer strBuffer = new StringBuffer(strArg);
		int iLength = strBuffer.length();
		if (iSize > 0 && iSize > iLength)
		{
			for (int i = 0; i <= iSize; i++)
			{
				if (cJustify == 'r' || cJustify == 'R')
				{
					if (i < iSize - iLength)
						strBuffer.insert(0, cFiller);
				} else if (cJustify == 'l' || cJustify == 'L')
				{
					if (i > iLength)
						strBuffer.append(cFiller);
				}
			}
		}
		return strBuffer.toString();
	}

	/**
	 * Trims the field to the required length
	 * 
	 * @param strField -
	 *            Field to Truncate
	 * @param iSize -
	 *            Size of the Field to Truncate to
	 * @return String - Truncated String
	 */

	public static String fieldTruncate(String strField, int iSize)
	{

		String strRet = strField.substring(0, iSize);
		return strRet;
	}

	/**
	 * Removes the cFiller character(s) from the left of strField
	 * 
	 * @param strField -
	 *            Field to remove characters from
	 * @param cJustify -
	 *            Specifies if cFiller needs to be removed from Left or Right
	 * @param cFiller -
	 *            Filler character to remove from strField
	 * @return String - String after cFiller removed
	 */

	public static String removeExtraFiller(String strField, char cFiller,
			char cJustify)
	{
		int iLen = 0;
		int iCnt = 0;
		String strRet = new String("");

		if (cJustify == 'r' || cJustify == 'R')
		{
			iLen = strField.length();
			while (iCnt < iLen && strField.charAt(iCnt) == cFiller)
				++iCnt;
			strRet = strField.substring(iCnt);
		} else if (cJustify == 'l' || cJustify == 'L')
		{
			iLen = strField.length();
			while (iLen > iCnt && strField.charAt(iLen - 1) == cFiller)
				--iLen;
			strRet = strField.substring(0, iLen);
		} else
		{
			strRet = strField;
		}
		return strRet;
	}

	/**
	 * Tokenizes strField as per the Token passed
	 * 
	 * @param strField -
	 *            Field to Tokenize
	 * @param strToken -
	 *            Token
	 * @return ArrayList - List containing the Tokens
	 */

	public static List<String> tokenizeAndRetnList(String strField, String strToken)
	{
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(strField, strToken);
		while (st.hasMoreTokens())
		{
			list.add(st.nextToken());
		}
		return list;
	}

	/**
	 * Tokenizes strField as per the Token passed
	 * 
	 * @param strField -
	 *            Field to Tokenize
	 * @param strToken -
	 *            Token
	 * @return Array - List containing the Tokens
	 */

	public static String[] tokenize(String strField, String strToken)
	{
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(strField, strToken);
		while (st.hasMoreTokens())
		{
			list.add(st.nextToken());
		}
		Object[] objArr = list.toArray();
		int size = objArr.length;
		String arr[] = new String[size];
		for (int i = 0; i < size; i++)
		{
			arr[i] = (String) objArr[i];
		}

		return arr;
	}

	/**
	 * Converts String to java.util.date
	 * 
	 * @param String
	 * @param Simple
	 *            Date Format
	 * @return Date
	 * @exception ParseException
	 */

	public static Date getStringAsDate(String strDate, String strFormat)
			throws ParseException
	{
		if (strDate == null || strFormat == null || strDate.equals("")
				|| strFormat.equals(""))
			return null;
		SimpleDateFormat sdfFormat = new SimpleDateFormat(strFormat);
		sdfFormat.setLenient(false);
		Date dtOut = sdfFormat.parse(strDate);
		return dtOut;
	}

	/**
	 * Converts date to String
	 * 
	 * @param int -
	 *            Days from today - if Todays date is required enter 0
	 * @param Simple
	 *            Date Format
	 * @return String
	 */
	public static String getDateAsString(int iDaysFromToday, String strFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		String strRet = "";
		Calendar calendar = Calendar.getInstance();
		calendar.add(java.util.Calendar.DATE, iDaysFromToday);
		java.util.Date requiredDate = calendar.getTime();
		strRet = sdf.format(requiredDate);
		return strRet;
	}

	/**
	 * Converts date to String
	 * 
	 * @param date -
	 * @param Simple
	 *            Date Format
	 * @return String
	 */
	public static String getDateAsString(Date date, String strFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		String s = sdf.format(date);
		return s;
	}

	/**
	 * Converts time to String if current time is required in string format
	 * enter both hrsFromNow and minutesFromNow as 0
	 * 
	 * @param int -
	 *            hrsFromNow
	 * @param int -
	 *            minutesFromNow
	 * @param boolean -
	 *            enter true if 24 Hour Format, enter false if 12 hour format
	 * @return String
	 */

	public static String getTimeAsString(int hrsFromNow, int minutesFromNow,
			boolean twentyFourHourFormat)
	{
		SimpleDateFormat sdf = null;
		int timeDelayInMinutes = hrsFromNow * 60 + minutesFromNow;
		String retVal = null;
		Calendar cal = Calendar.getInstance();
		if (twentyFourHourFormat)
		{
			sdf = new SimpleDateFormat("HH:mm:ss");
		} else
		{
			sdf = new SimpleDateFormat("hh:mm:ss a");
		}
		cal.add(Calendar.MINUTE, timeDelayInMinutes);
		java.util.Date timeAfterSpecifiedMinutes = cal.getTime();
		retVal = sdf.format(timeAfterSpecifiedMinutes);
		return retVal;
	}

	/**
	 * Converts String to Float method will return 200.01 if strVal is
	 * "000200.01"
	 * 
	 * @param String -
	 * @return Float
	 */
	public static float convertStringToFloat(String strVal)
	{
		float fVal = Float.valueOf(strVal.trim()).floatValue();
		return fVal;
	}

	/**
	 * checks to see if an entered year is a leap year or not
	 * 
	 * @param Int -
	 * @return boolean - true if Leap , false if not a leap year
	 */

	public static boolean IsLeapYear(int year)
	{

		/* If multiple of 100, leap year iff multiple of 400. */
		if ((year % 100) == 0)
			return ((year % 400) == 0);

		/* Otherwise leap year iff multiple of 4. */
		return ((year % 4) == 0);
	} // IsLeapYear

	/**
	 * Converts a date passed in the format yyyddd (Lincolns Julian Dates) to a
	 * string representation of the date as per format passed
	 * 
	 * @param String -
	 *            strDate - this will be in format yyyddd
	 * @param Simple
	 *            Date Format - sdf - format of the output reuired
	 * @return String
	 */

	public static String convertJulianDate(String strDate, String format)
	{
		String methodName = "convertJulianDate :: ";
		log.debug(methodName + "in");

		if ((strDate == null) || (format == null))
			throw new NullPointerException(
					"Null argument(s) passed! A date string argument in the format - yyyddd - expected. A date format (e.g. \"MM/dd/yyyy\") string argument expected.");

		DateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);

		if (strDate.length() != 6)
			throw new IllegalArgumentException(
					"Invalid format for the date string argument passed! -"
							+ strDate
							+ " - \t A string argument in format - yyyddd - expected!");

		StringBuffer strBuf = new StringBuffer(strDate);
		String strYear = strBuf.substring(0, 3);
		String strDays = strBuf.substring(3);
		int iYear = 0;
		int iDays = 0;
		try
		{
			iYear = Integer.parseInt(strYear) + 1800;
			iDays = Integer.parseInt(strDays);
		} catch (Exception e)
		{
			throw new IllegalArgumentException(
					"Invalid format for the date string argument passed! -"
							+ strDate
							+ " - \t A string argument in format - yyyddd - expected!");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, iYear);
		calendar.set(Calendar.DAY_OF_YEAR, iDays);
		java.util.Date date = new java.util.Date(calendar.getTime().getTime());
		String strOut = df.format(date);
		return strOut;
	}

	/**
	 * @param d1
	 *            date 1
	 * @param d2
	 *            date 2
	 * @return returns no of days i.e. difference between two input dates
	 */
	public static long dateDiffInDays(Date d1, Date d2)
	{
		log.debug("dateDiffInDays :: In");

		if (d1 == null || d2 == null)
		{
			return 0;
		}

		long days = 0;

		long ms1 = d1.getTime();
		long ms2 = d2.getTime();

		log.debug("dateDiffInDays :: ms1 = " + ms1);
		log.debug("dateDiffInDays :: ms2 = " + ms2);

		if (ms1 > ms2)
			days = (ms1 - ms2) / (1000 * 60 * 60 * 24);

		if (ms2 > ms1)
			days = (ms2 - ms1) / (1000 * 60 * 60 * 24);

		log.debug("dateDiffInDays :: Out Days = " + days);

		return days;
	}

	/**
	 * @param date
	 * @return returns string date in mm/dd/yyyy (Lincoln's Display format)
	 */
	public static String getStringDate(Date date)
	{
		if (date == null || date.equals(""))
		{
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(IAppConstants.SDF_STRING);
		return sdf.format(date);
	}

	/*
	 * Splits Date into three parts MM, DD, YYYY and returns them in String
	 * array
	 */
	public static String[] getSplitDate(Date utilDate)
	{
		String methodName = "getSplitDate :: ";
		log.debug(methodName + "In");

		String splitDate[] = new String[3];

		try
		{

			if (utilDate == null || utilDate.equals(""))
			{
				log.debug("getSplitDate :: Out");
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(
					IAppConstants.SDF_STRING);
			String displayDate = sdf.format(utilDate);

			splitDate[0] = displayDate.substring(0, 2); // MM
			splitDate[1] = displayDate.substring(3, 5); // DD
			splitDate[2] = displayDate.substring(6); // YYYY
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error(methodName, e);
		}

		log.debug(methodName + "Out");
		return splitDate;
	}

	/**
	 * Converts Database value to Check box value for display on the screen
	 * Retunrs String "On" if dbValue values is "Y" Returns String "Off" if
	 * dbValue value is "N"
	 * 
	 * @param dbValue
	 * @return
	 */
	public static String getValueForCheckBox(String dbValue)
	{
		String checkBoxValue = null;

		if (dbValue == null || dbValue.equals(""))
		{
			return checkBoxValue;
		}
		if (dbValue.equals("Y"))
		{
			checkBoxValue = "on";
		}

		return checkBoxValue;
	}

	public static String convertCheckBoxValue(String screenValue)
	{
		String dbValue = "N";

		if (screenValue == null || screenValue.equals(""))
		{
			return dbValue;
		}
		if (screenValue.equals("on"))
		{
			dbValue = "Y";
		}

		return dbValue;
	}

	public static boolean isValidString(String value)
	{
		String methodName = "isValidString :: ";
		char ch;
		try
		{
			for (int i = 0; i < value.length(); i++)
			{
				ch = value.charAt(i);
				if (ch >= '0' && ch <= '9')
					return false;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error(methodName, e);
		}
		return true;
	}

	/**
	 * Utility method that inspects the provided String to verify if it contains
	 * only valid characters allowed in names
	 * 
	 * @param sampleString
	 * @return <code>true</code> if the String contains special characters
	 */
	public static boolean isValidNameString(String sampleString)
	{
		final char SPACE = ' ';
		final char APOSTROPHE = '\'';
		final char COMMA = ',';
		final char PERIOD = '.';
		final char DASH = '-';
		final char AMPERSAND = '&';

		char[] charsInString = sampleString.toCharArray();
		for (int i = 0; i < charsInString.length; i++)
		{
			if (!Character.isLetterOrDigit(charsInString[i])
					&& (charsInString[i] != SPACE)
					&& (charsInString[i] != APOSTROPHE)
					&& (charsInString[i] != COMMA)
					&& (charsInString[i] != PERIOD)
					&& (charsInString[i] != AMPERSAND)
					&& (charsInString[i] != DASH))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns date as Date type, but returns null if any input parameter is
	 * null
	 * 
	 * @param mm
	 *            month as shown on screen
	 * @param dd
	 *            date
	 * @param yyyy
	 *            year 4 digit year
	 * @return java.util.Date
	 */
	public static Date getDateforScreenDate(String strMM, String strDD,
			String strYYYY)
	{

		if (strMM == null || strDD == null || strYYYY == null
				|| strMM.equals("") || strDD.equals("") || strYYYY.equals(""))
		{
			return null;
		}

		int mm = Integer.parseInt(strMM);
		int dd = Integer.parseInt(strDD);
		int yyyy = Integer.parseInt(strYYYY);

		Calendar cal = Calendar.getInstance();

		cal.set(yyyy, mm - 1, dd);

		return cal.getTime();
	}

	/**
	 * Returns formatted SSN String
	 * 
	 * @param ssnString -
	 *            containing unformatted SSN string
	 * @return String
	 */
	public static String formatSSN(String ssnString, String partyTypeCode)
	{

		final String methodName = "formatSSN :: ";
		log.debug(methodName + "In");

		String newSSN = "";

		if (ssnString != null && !ssnString.equals(""))
		{
			newSSN = formatSSN(ssnString);
		}
		

		log.debug(methodName + "Out");

		return newSSN;
	}

	/**
	 * Returns formatted SSN String
	 * 
	 * @param ssnString -
	 *            containing unformatted SSN string
	 * @return String
	 */
	public static String formatSSN(String ssnString)
	{

		String newSSN = "";

		if (ssnString != null && !ssnString.equals(""))
		{
			char[] charsInString = ssnString.toCharArray();
			for (int i = 0; i < charsInString.length; i++)
			{
				// ignore the wildcard if it is the last character
				if ((i == 3) || (i == 5))
				{
					newSSN = newSSN + "-";
				}
				newSSN = newSSN + charsInString[i];
			}
		}

		return newSSN;
	}

	/**
	 * Returns formatted TIN String
	 * 
	 * @param ssnString -
	 *            containing unformatted TIN string
	 * @return String
	 */
	public static String formatTIN(String ssnString)
	{

		String newTIN = "";

		if (ssnString != null && !ssnString.equals(""))
		{
			char[] charsInString = ssnString.toCharArray();
			for (int i = 0; i < charsInString.length; i++)
			{
				// ignore the wildcard if it is the last character
				if (i == 2)
				{
					newTIN = newTIN + "-";
				}
				newTIN = newTIN + charsInString[i];
			}
		}

		return newTIN;
	}

	/**
	 * Returns formatted Name String for an Individual Party Type Code
	 * 
	 * @param firstName
	 * @param lastName
	 * @param middleName
	 * @param suffixCode
	 * @return String
	 */
	public static String formatNameForDisplay(String firstName,
			String lastName, String middleName, String suffixCode)
	{

		final String methodName = "formatNameForDisplay :: ";
		log.debug(methodName + "In");

		StringBuffer buf = new StringBuffer();
		boolean spaceAppend = false;

		if (lastName != null && (!lastName.equals("")))
		{
			spaceAppend = true;
			buf.append(lastName);
		}

		if (suffixCode != null && (!suffixCode.equals("")))
		{
			if (spaceAppend)
			{
				buf.append(" ");
			}
			buf.append(suffixCode.toUpperCase());
			spaceAppend = true;
		}

		if (firstName != null && (!firstName.equals("")))
		{
			if (spaceAppend)
			{
				buf.append(", ");
			}
			buf.append(firstName);
			spaceAppend = true;
		}

		if (middleName != null && (!middleName.equals("")))
		{
			if (spaceAppend)
			{
				buf.append(" ");
			}
			buf.append(middleName);
			spaceAppend = true;
		}

		log.debug(methodName + "Out");

		return buf.toString();
	}

	/**
	 * Returns formatted Name String for an Individual Party Type Code For
	 * Display on the UI Headers
	 * 
	 * @param firstName
	 * @param lastName
	 * @param middleName
	 * @param suffixCode
	 * @return String
	 */
	public static String formatNameForHeaderDisplay(String firstName,
			String lastName, String middleName, String suffixCode)
	{

		final String methodName = "formatNameForHeaderDisplay :: ";
		log.debug(methodName + "In");

		StringBuffer buf = new StringBuffer();
		boolean spaceAppend = false;

		if (firstName != null && (!firstName.equals("")))
		{
			buf.append(firstName);
			spaceAppend = true;
		}

		if (middleName != null && (!middleName.equals("")))
		{
			if (spaceAppend)
			{
				buf.append(" ");
			}
			buf.append(middleName);
			spaceAppend = true;
		}

		if (lastName != null && (!lastName.equals("")))
		{
			if (spaceAppend)
			{
				buf.append(" ");
			}
			buf.append(lastName);
			spaceAppend = true;
		}

		if (suffixCode != null && (!suffixCode.equals("")))
		{
			if (spaceAppend)
			{
				buf.append(" ");
			}
			buf.append(suffixCode.toUpperCase());
			spaceAppend = true;
		}

		log.debug(methodName + "Out");

		return buf.toString();
	}

	/**
	 * Returns formatted Address String
	 * 
	 * @param addrFormat
	 * @param addrLine1
	 * @param addrLine2
	 * @param addrLine3
	 * @param addrLine4
	 * @param city
	 * @param state
	 * @param zip
	 * @param zipPlus4
	 * @param province
	 * @param postal
	 * @param country
	 * 
	 * return String - containing formatted address
	 */
	public static String formatAddressForDisplay(String addrFormat,
			String addrLine1, String addrLine2, String addrLine3,
			String addrLine4, String city, String state, String zipCode,
			String zipPlus4, String province, String postalCode, String country)
	{

		final String methodName = "formatAddressForDisplay :: ";
		log.debug(methodName + "In");

		log.debug("addrFormat :" + addrFormat + ":");
		log.debug("addrLine1 :" + addrLine1 + ":");
		log.debug("addrLine2 :" + addrLine2 + ":");
		log.debug("addrLine3 :" + addrLine3 + ":");
		log.debug("addrLine4 :" + addrLine4 + ":");
		log.debug("city :" + city + ":");
		log.debug("state :" + state + ":");
		log.debug("zipCode :" + zipCode + ":");
		log.debug("zipPlus4 :" + zipPlus4 + ":");
		log.debug("province :" + province + ":");
		log.debug("postalCode :" + postalCode + ":");
		log.debug("country :" + country + ":");

		StringBuffer buf = new StringBuffer();
		addrFormat = trimStringValue(addrFormat);
		if (addrFormat != null)
		{
			if (!PASUtil.isNullOrEmpty(addrLine1))
					buf.append(addrLine1);

				if (!PASUtil.isNullOrEmpty(addrLine2))
				{
					if (!PASUtil.isNullOrEmpty(addrLine1))
						buf.append(", ");
					buf.append(addrLine2);
				}

				if (!PASUtil.isNullOrEmpty(city))
				{
					if (!PASUtil.isNullOrEmpty(addrLine1)
							|| !PASUtil.isNullOrEmpty(addrLine2))
						buf.append(", ");
					buf.append(city);
				}

				if (!PASUtil.isNullOrEmpty(state))
				{
					if (!PASUtil.isNullOrEmpty(addrLine1)
							|| !PASUtil.isNullOrEmpty(addrLine2)
							|| !PASUtil.isNullOrEmpty(city))
						buf.append(", ");
					buf.append(state);
				}

				if (!PASUtil.isNullOrEmpty(zipCode))
				{
					if (!PASUtil.isNullOrEmpty(addrLine1)
							|| !PASUtil.isNullOrEmpty(addrLine2)
							|| !PASUtil.isNullOrEmpty(city)
							|| !PASUtil.isNullOrEmpty(state))
						buf.append(", ");
					buf.append(zipCode);
					if (!PASUtil.isNullOrEmpty(zipPlus4))
					{
						if (!PASUtil.isNullOrEmpty(addrLine1)
								|| !PASUtil.isNullOrEmpty(addrLine2)
								|| !PASUtil.isNullOrEmpty(city)
								|| !PASUtil.isNullOrEmpty(state)
								|| !PASUtil.isNullOrEmpty(zipCode))
							buf.append("-");
						buf.append(zipPlus4);
					}
				}

			}
		
		log.debug(methodName + "Out");

		return buf.toString();
	}

	/**
	 * Tokenizes phone
	 * 
	 * @param phone -
	 *            Field to Tokenize
	 * @return ArrayList - List containing the Tokens
	 */

	public static List<String> convertPhoneNumber(String phone)
	{
		ArrayList<String> list = new ArrayList<String>();
		if (phone != null)
		{
			phone.trim();
			int len = phone.length();
			list.add(0, phone.substring(0, 3));
			list.add(1, phone.substring(3, 6));
			list.add(2, phone.substring(6, len));
		}
		return list;
	}

	/**
	 * Validates email to contain '@'
	 * 
	 * @param email -
	 *            Field to Validate
	 * @return boolean - whether valid or not
	 */

	public static boolean IsValidEmail(String email)
	{
		if (email != null && !email.equals(""))
		{
			if (email.indexOf("@") == -1)
				return false;
		}
		return true;
	}

	/**
	 * unformat ssn to remove dashes
	 * 
	 * @param ssn -
	 *            Field to Validate
	 * @return String - unformatted string
	 */

	public static String removeDashesFromSSN(String ssn)
	{
		String returnString = null;

		if (ssn != null && !ssn.equals(""))
		{
			int len = ssn.length();
			String one = null;
			String two = null;
			String three = null;

			if (len == 11)
			{
				one = ssn.substring(0, 3);
				two = ssn.substring(4, 6);
				three = ssn.substring(7, len);
				returnString = one + two + three;
			} else if (len == 10)
			{
				one = ssn.substring(0, 2);
				two = ssn.substring(3, len);
				returnString = one + two;
			}
		}
		return returnString;
	}

	

	/**
	 * get # separated string from list
	 * 
	 * @param strField -
	 *            Field to Tokenize
	 * @param strToken -
	 *            Token
	 * @return ArrayList - List containing the Tokens
	 */

	public static String getStringFromList(List<String> list, String token)
	{
		String formattedString = "";
		if (list != null)
		{
			for (int i = 0; i < list.size(); i++)
			{
				String listValue = (String) list.get(i);
				if (formattedString != null && !formattedString.equals(""))
				{
					formattedString = formattedString + token + listValue;
				} else
				{
					formattedString = listValue;
				}

			}
		}
		return formattedString;
	}

	/**
	 * returns sql date for a given screen date(MM/dd/yyyy)
	 * 
	 * @param screenDate
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Date getSqlDate(String screenDate)
			throws ParseException
	{
		if (screenDate == null || screenDate.equals(""))
			return null;

		Date utilDate = getStringAsDate(screenDate, IAppConstants.SDF_STRING);

		return new java.sql.Date(utilDate.getTime());
	}

	/**
	 * get Checkbox value based on role
	 * 
	 * @param roleName -
	 *            rolename to be
	 * @param checkboxvalue -
	 *            whether checked or not
	 * @return String roleName corresponding to the values passed
	 */

	public static void printList(List<String> passedList) throws Exception
	{
		String methodName = "printList :: ";
		log.debug(methodName + "In");

		try
		{
			if (passedList != null)
			{
				Iterator<String> i = passedList.iterator();
				while (i.hasNext())
				{
					log.debug(methodName + "List Element :" + i.next() + ":");
				}
			}
		} catch (Exception e)
		{
			throw new Exception();
		}

		log.debug(methodName + "Out");
	}

	/**
	 * Unformats SSN/TIN
	 * 
	 * @param ssn -
	 *            formatted ssn
	 * @return String - unformatted ssn
	 */

	public static String unformatSSNTIN(String ssn)
	{

		int len = 0;
		int ind = 0;

		if (ssn != null && !ssn.equals(""))
		{
			len = ssn.length();
		}
		for (int i = 0; i < len; i++)
		{

			ind = ssn.indexOf('-');

			if (ind == -1)
			{
				break;
			}
			
			if (ssn != null && !ssn.equals(""))
			{
				len = ssn.length();
			}
			
			String one = ssn.substring(0, ind);
			String two = ssn.substring(ind + 1, len);
			ssn = one + two;
		}
		return ssn;
	}

	public static boolean areDatesSame(java.util.Date srcDt1,
			java.util.Date srcDt2)
	{

		Calendar c1 = Calendar.getInstance();

		Calendar c2 = Calendar.getInstance();

		c1.setTime(srcDt1);

		c2.setTime(srcDt2);

		if ((c1.get(Calendar.DATE) == c2.get(Calendar.DATE))
				&& (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
				&& (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)))
			return true;

		return false;

	}

	public static boolean isWeekend(java.util.Date srcDt)
	{

		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(srcDt);

		int iDayOfWeek = gc.get(GregorianCalendar.DAY_OF_WEEK);

		if ((iDayOfWeek == GregorianCalendar.SATURDAY)
				|| (iDayOfWeek == GregorianCalendar.SUNDAY))
			return true;

		return false;

	}

	public static String getStringFromDouble(double d)
	{
		DecimalFormat df = new DecimalFormat("#########.##");
		return df.format(d);
	}

	public static boolean isValidDecimalRate(String s)
	{
		if (s.length() > 6)
			return false;
		
		return isValidDecimal(s);
	}

	public static boolean isValidDecimalAmountWithNeg(String s)
	{
		int len = s.length();
		boolean neg = false;
		if (s.startsWith("-"))
		{
			len--;
			neg = true;
		}
		if (len > 12)
			return false;
		
		if (neg)
			return isValidDecimal(s.substring(1));
		
		return isValidDecimal(s);
		
	}

	public static boolean isValidDecimalAmount(String s)
	{
		if (s.length() > 12)
			return false;
		
		return isValidDecimal(s);
	}

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return number of days between fromDate & toDate, 0 if both dates are
	 *         same, or if fromDate is after toDate
	 */
	/*
	 * BWR 9427 - Rewrote to build dates from mm/dd/yy. IPC Calc cch having
	 * problems with timezones and Daylight Saving Time when setting hrs, mins
	 * and sec to zero
	 */
	public static int getCalendarDaysBetweenTwoDates(Date fromDate, Date toDate)
	{
		int numberOfDays = 0;
		Calendar c1 = Calendar.getInstance();
		c1.setTime(fromDate);
		int mm1 = c1.get(Calendar.MONTH);
		int dd1 = c1.get(Calendar.DAY_OF_MONTH);
		int yy1 = c1.get(Calendar.YEAR);
		c1.clear();
		c1.setTime(toDate);
		int mm2 = c1.get(Calendar.MONTH);
		int dd2 = c1.get(Calendar.DAY_OF_MONTH);
		int yy2 = c1.get(Calendar.YEAR);
		Calendar c2 = Calendar.getInstance();
		Calendar c3 = Calendar.getInstance();
		c2.set(yy1, mm1, dd1, 0, 0, 0);
		c3.set(yy2, mm2, dd2, 0, 0, 0);

		long fromDateInMilliseconds = c2.getTime().getTime();
		long toDateInMilliseconds = c3.getTime().getTime();

		if (toDateInMilliseconds > fromDateInMilliseconds)
		{
			numberOfDays = (int) ((toDateInMilliseconds - fromDateInMilliseconds) / (24 * 60 * 60 * 1000));
		}

		return numberOfDays;
	}

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return number of days between fromDate & toDate, 0 if both dates are
	 *         same, or if fromDate is after toDate
	 */
	public static int getCalendarDaysBetweenTwoDatesOld(Date fromDate,
			Date toDate)
	{
		int numberOfDays = 0;

		long fromDateInMilliseconds = fromDate.getTime();
		long toDateInMilliseconds = toDate.getTime();

		if (toDateInMilliseconds > fromDateInMilliseconds)
		{
			numberOfDays = (int) ((toDateInMilliseconds - fromDateInMilliseconds) / (24 * 60 * 60 * 1000));
		}

		return numberOfDays;
	}

	/**
	 * 
	 * @param addressobject
	 * @return boolean true if valid address or false if address is invalid
	 */
	public static boolean isAddressValid(Address addressObj)
	{
		boolean isValid = true;
		if (addressObj != null)
		{
			String addressFormat = addressObj.getAddressFormat();
			if (!PASUtil.isNullOrEmpty(addressFormat))
			{
				if ((addressObj.getAddressLine1() == null || addressObj
							.getAddressLine1() == "")
							|| (addressObj.getAddressLine2() == null || addressObj
									.getAddressLine2() == "")
							|| (addressObj.getCity() == null || addressObj
									.getCity() == "")
							|| (addressObj.getState() == null || addressObj
									.getState() == "")
							|| (addressObj.getZip() == null || addressObj
									.getZip() == ""))
					{
						isValid = false;
					}
				}			
			
		} else
		{
			log.debug("Address Object passed is empty");
		}

		return isValid;
	}

	/**
	 * A utility method that concatenates the year, month & date fields &
	 * returns it in the mm/dd/yyyy format
	 * 
	 * @return String
	 */
	public static String getConcatenatedDate(String mm, String dd, String yyyy)
	{
		String concatenatedDate = null;

		if (!PASUtil.isNullOrEmpty(mm) && !PASUtil.isNullOrEmpty(dd)
				&& !PASUtil.isNullOrEmpty(yyyy))
		{
			StringBuffer dtBuf = new StringBuffer();
			dtBuf.append(mm).append("/").append(dd).append("/").append(yyyy);
			concatenatedDate = dtBuf.toString();
		}
		return concatenatedDate;
	}

	/**
	 * if given date is incomplete, this method returns true
	 * 
	 * @param mm
	 * @param dd
	 * @param yyyy
	 * @return
	 */
	public static boolean isIncompleteDate(String mm, String dd, String yyyy)
	{
		boolean answer = false;
		if (!PASUtil.isNullOrEmpty(mm) || !PASUtil.isNullOrEmpty(dd)
				|| !PASUtil.isNullOrEmpty(yyyy))
		{
			if (PASUtil.isNullOrEmpty(mm) || PASUtil.isNullOrEmpty(dd)
					|| PASUtil.isNullOrEmpty(yyyy))
			{
				answer = true;
			}
		}
		return answer;
	}

	/**
	 * Utility method that inspects the provided String to verify that it
	 * contains valid tin format
	 * 
	 * @param ssnString
	 * @return <code>true</code> if valid
	 */
	public static boolean checkTINFormat(String ssnString)
	{
		char[] charsInString = ssnString.toCharArray();

		boolean ssnValid = true;

		if (charsInString.length != 10)
		{
			ssnValid = false;
		} else
		{
			for (int i = 0; i < charsInString.length; i++)
			{
				if (i == 2)
				{
					if (charsInString[i] != '-')
					{
						ssnValid = false;
					}
				} else
				{
					if (charsInString[i] != '0' && charsInString[i] != '1'
							&& charsInString[i] != '2'
							&& charsInString[i] != '3'
							&& charsInString[i] != '4'
							&& charsInString[i] != '5'
							&& charsInString[i] != '6'
							&& charsInString[i] != '7'
							&& charsInString[i] != '8'
							&& charsInString[i] != '9')
					{
						ssnValid = false;
					}
				}
			}
		}
		return ssnValid;
	}

	public static boolean containsSpecialCharactersIgnoreStar(String s)
	{
		String stringToInspect = s;
		// take out * if it is the last character
		int indexOfStar = stringToInspect.lastIndexOf('*');
		if (indexOfStar == 0)
		{
			stringToInspect = "";
		} else
		{
			int lastIndexInString = stringToInspect.length() - 1;
			if (indexOfStar == lastIndexInString)
			{
				stringToInspect = stringToInspect.substring(0,
						lastIndexInString);
			}
		}

		return containsSpecialCharacters(stringToInspect);
	}

	public static boolean containsSpecialCharacters(String s)
	{
		if (!isValidNameString(s))
		{
			return true;
		} 
		
		return false;
		
	}

	/**
	 * Given a date & a certain number of days to skip, this method returns a
	 * date from the given date after that many days. The skipped days may
	 * include holidays & non-business days
	 * 
	 * @param fromDate
	 * @param calendarDaysToSkip
	 * @return
	 */
	public static java.util.Date getFutureCalendarDay(java.util.Date fromDate,
			int calendarDaysToSkip)
	{
		Calendar fromDateCal = Calendar.getInstance();
		fromDateCal.setTime(fromDate);
		fromDateCal.add(Calendar.DATE, calendarDaysToSkip);

		return fromDateCal.getTime();
	}

	/**
	 * Trims a string value
	 * 
	 * @param trimString -
	 *            Field to trim
	 * @return String - String with trimmed value
	 */

	public static String trimStringValue(String trimString)
	{
		if (trimString == null)
			return trimString;
		
		return trimString.trim();
	}

	/**
	 * Trims leading & trailing spaces from the supplied String if it is not
	 * null, returns null otherwise
	 * 
	 * @param stringToTrim
	 * @return
	 */
	public static String trimIfNotNull(String stringToTrim)
	{
		String trimmedString = null;
		if (stringToTrim != null)
		{
			trimmedString = stringToTrim.trim();
		}
		return trimmedString;
	}

	// following checks for database column size
	public static boolean isValidAmount(double d)
	{
		if (d < 1000000000.00)
			return true;
		
		return false;
	}

	/**
	 * Returns a rounded version for the supplied value Rounding is done by
	 * half-up rule
	 * 
	 * @param unroundedValue
	 * @return
	 */
	public static double getRoundedUpValue(double unroundedValue)
	{
		BigDecimal unroundedValueDec = new BigDecimal(unroundedValue);
		BigDecimal roundedValueDec = unroundedValueDec.setScale(2,
				BigDecimal.ROUND_HALF_UP);
		return roundedValueDec.doubleValue();
	}

	/**
	 * Returns a rounded version for the supplied value Rounding is done by
	 * half-up rule
	 * 
	 * @param unroundedValue
	 * @return
	 */
	public static BigDecimal getRoundedUpValue(BigDecimal unroundedValue)
	{
		BigDecimal roundedValueDec = unroundedValue.setScale(2,
				BigDecimal.ROUND_HALF_UP);
		return roundedValueDec;
	}

	/**
	 * Returns a boolean value indicating whether a string is alphanumeric or
	 * not
	 * 
	 * @param String
	 * @return
	 */
	public static boolean isAlphaNumeric(String alphanumStr)
	{
		boolean retValue = true;
		if (!isNullOrEmpty(alphanumStr))
		{
			int len = alphanumStr.length();
			String strUpperCase = alphanumStr.toUpperCase();
			String alphaNumComplete = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			for (int i = 0; i < len; i++)
			{
				char charStr = strUpperCase.charAt(i);
				if (alphaNumComplete.indexOf(charStr) != -1)
					retValue = true;
				else
					retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * Returns a boolean value indicating whether the postal code for Canadian
	 * address format is valid or not
	 * 
	 * @param String
	 * @return
	 */
	public static boolean isValidPostal(String postal)
	{
		boolean retValue = true;
		if (!isNullOrEmpty(postal))
		{
			int len = postal.length();
			// If length # 7 display error
			if (len != 7)
			{
				retValue = false;
			}
			if (retValue)
			{
				String strUpperCase = postal.toUpperCase();
				String alphaNumComplete = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
				char spaceChar = ' ';
				boolean noSpace = false;
				for (int i = 0; i < len; i++)
				{
					// first three characters should be alphanumeric
					// Forth character should be space
					// last three characters should be alphanumeric
					char charStr = strUpperCase.charAt(i);
					if (i == 0 || i == 1 || i == 2 || i == 4 || i == 5
							|| i == 6)
					{
						if (alphaNumComplete.indexOf(charStr) != -1)
							retValue = true;
						else
							retValue = false;
					} else if (i == 3)
					{
						if (charStr != spaceChar)
						{
							noSpace = true;
						}

					}
				}
				if (noSpace)
				{
					retValue = false;
				}
			}
		}
		return retValue;
	}

	/**
	 * rounds the given double using 5/4 rule
	 * 
	 * @param dAmount
	 * @return
	 */
	public static double getRoundedValue(double dAmount)
	{
		DecimalFormat df = new DecimalFormat("#########.##");
		String strValue = df.format(dAmount);
		BigDecimal bDec = new BigDecimal(strValue);
		BigDecimal newB = bDec.setScale(2, BigDecimal.ROUND_HALF_UP);
		return newB.doubleValue();
	}

	public static java.util.Date getPastBusinessDay(java.util.Date srcDt,
			int BusinessDaysToSkip) throws PASSystemException
	{

		if (BusinessDaysToSkip < 1)
			throw new PASSystemException(
					"Invalid value for argument BusinessDaysToSkip passed! an integer value >= 1 expected!");

		Calendar cal = Calendar.getInstance();

		cal.setTime(srcDt);

		int counter = 0;

		java.util.Date returnDate = null; // date to be returned.

		boolean isBusinessDay = true;

		while (counter < BusinessDaysToSkip)
		{

			cal.add(Calendar.DATE, -1); // go to previous date..

			returnDate = cal.getTime();

			isBusinessDay = true; // reset boolean variable...

			// check if date falls on a weekend...

			if (isWeekend(returnDate))
				isBusinessDay = false;

			// placeholder for logic to see if date is in a list of holidays,
			// which would also exclude it from being a business day...

			if (isBusinessDay)
				counter++; // increment the business day counter by 1.

		}

		return returnDate;

	}

	public static java.util.Date getPreviousBusinessDay(java.util.Date srcDt)
			throws PASSystemException
	{

		return getPastBusinessDay(srcDt, 1);

	}

	public static java.util.Date getFutureBusinessDay(java.util.Date srcDt,
			int BusinessDaysToSkip) throws PASSystemException
	{

		if (BusinessDaysToSkip < 1)
			throw new PASSystemException(
					"Invalid value for argument BusinessDaysToSkip passed! an integer value >= 1 expected!");

		Calendar cal = Calendar.getInstance();

		cal.setTime(srcDt);

		int counter = 0;

		java.util.Date returnDate = null; // date to be returned.

		boolean isBusinessDay = true;

		while (counter < BusinessDaysToSkip)
		{

			cal.add(Calendar.DATE, 1); // go to next date..

			returnDate = cal.getTime();

			isBusinessDay = true; // reset boolean variable...

			// check if date falls on a weekend...

			if (isWeekend(returnDate))
				isBusinessDay = false;

			// placeholder for logic to see if date is in a list of holidays,
			// which would also exclude it from being a business day...

			if (isBusinessDay)
				counter++; // increment the business day counter by 1.

		}

		return returnDate;

	}

	public static java.util.Date getNextBusinessDay(java.util.Date srcDt)
			throws PASSystemException
	{

		return getFutureBusinessDay(srcDt, 1);

	}

	public static String getDescription(String code, String key)
	{
		String description = "";
		String defValue = "";
		try
		{
			IPropertyCollection ipcUrl = PropertyReaderFactory
					.getPropertyReader().getCollection(
							"NameValuePairs." + code + ".entry");

			if (ipcUrl != null && ipcUrl.hasNext())
			{
				while (ipcUrl.hasNext())
				{
					ipcUrl.next();
					if (ipcUrl.getProperty("code").equalsIgnoreCase(key))
					{
						description = ipcUrl.getProperty("value");
						break;
					} else if (ipcUrl.getPropertyBoolean("default", false))
					{
						defValue = ipcUrl.getProperty("value");
					}
				}
			}
			if (description.equals(""))
			{
				description = defValue;
			}
		} catch (PASSystemException e)
		{
		}
		return description;
	}
	
	/**
	 * Formats Text for special characters
	 * 
	 * @param String
	 * @return formatted Text
	 */

	public static String replaceSpecialCharacters(String pSText)
	{
		StringBuffer lobjTextbuff = new StringBuffer();
		int liTextLength = pSText.length();
		for (int liCount = 0; liCount < liTextLength; liCount++)
		{
			char lcTextChar = pSText.charAt(liCount);
			switch (lcTextChar)
			{
			case '&':
				lobjTextbuff.append("&amp;");
				break;
			default:
				lobjTextbuff.append(lcTextChar);
				break;
			}
		}

		return lobjTextbuff.toString();
	}

}