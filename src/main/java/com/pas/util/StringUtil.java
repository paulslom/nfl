package com.pas.util;

import java.io.IOException;
import java.io.StringReader;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StringUtil extends Object {

	public static final String lineSeparator =
		System.getProperty("line.separator", "\n");
		
	protected static Logger logger = LogManager.getLogger(StringUtil.class);

	/**
	 * Answer a string which is the original string converted to all lowercase characters
	 * Creation date: (10/19/00 1:19:26 PM)
	 * @return String
	 * @param aString java.lang.String
	 */
	public static String asLowerCase(String aString) {
		StringBuffer answer = new StringBuffer();

		for (int i = 0; i < aString.length(); i++) {
			answer.append(Character.toLowerCase(aString.charAt(i)));
		}
		return answer.toString();
	}
	/**
	 * Answer a string which is the original string converted to all uppercase characters
	 * Creation date: (10/19/00 1:19:26 PM)
	 * @return String
	 * @param aString java.lang.String
	 */
	public static String asUpperCase(String aString) {
		StringBuffer answer = new StringBuffer();

		for (int i = 0; i < aString.length(); i++) {
			answer.append(Character.toUpperCase(aString.charAt(i)));
		}
		return answer.toString();
	}
	/**
	 * Format a date to a displayable string
	 * Creation date: (10/19/00 1:57:53 PM)
	 * @return java.lang.String
	 * @param aDate PASDate
	 */
	public static String formatDateMMDDYYYY(PASDate aDate, String delimiter) {
		String m = "MM";
		String d = "dd";
		String y = "yyyy";
		String pattern = m + delimiter + d + delimiter + y;

		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String dateString = formatter.format(aDate.getTime());
		return dateString;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (10/19/00 1:57:53 PM)
	 * @return java.lang.String
	 * @param aDate PASDate
	 */
	public static String formatDateYYYYMMDD(PASDate aDate, String delimiter) {
		String m = "MM";
		String d = "dd";
		String y = "yyyy";
		String pattern = y + delimiter + m + delimiter + d;

		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String dateString = formatter.format(aDate.getTime());
		return dateString;
	}
	/**
	 * Format a String value in to a proper monetary repreentation
	 * Creation date: (11/27/00 1:10:42 PM)
	 */

	public static String formatMoney(String amt) {
		String s = "";
		NumberFormat format = NumberFormat.getCurrencyInstance();

		// display no decimal places
		format.setMaximumFractionDigits(0);

		try {
			s = format.format(Double.valueOf(amt));
		} catch (NumberFormatException nfe) {
			logger.error("StringUtil.formatMoney()   NumberFormatException:" + nfe.getMessage());
		}

		return s;
	}
	/**
	 * Answer a boolean indicating whether aString all spaces
	 * Creation date: (10/19/00 1:19:26 PM)
	 * @return boolean
	 * @param aString java.lang.String
	 */
	public static boolean isAllSpaces(String aString) {
		boolean answer = true;

		for (int i = 0; i < aString.length(); i++) {
			if (!Character.isSpaceChar(aString.charAt(i))) {
				answer = false;
				break;
			}
		}
		return answer;
	}
	/**
	 * Answer a boolean indicating whether aString all leters or spaces
	 * Creation date: (10/19/00 1:19:26 PM)
	 * @return boolean
	 * @param aString java.lang.String
	 */
	public static boolean isAlphabetic(String aString) {
		boolean answer = true;

		for (int i = 0; i < aString.length(); i++) {
			char c = aString.charAt(i);
			if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
				answer = false;
				break;
			}
		}
		return answer;
	}
	public static boolean isAlphabeticWithChars(String aString, String mask) {
		boolean answer = true;

		for (int i = 0; i < aString.length(); i++) {
			char c = aString.charAt(i);
			// check if c is in the mask
			if (mask.indexOf(c) != -1)
				continue;
			if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
				answer = false;
				break;
			}
		}
		return answer;
	}
	/**
	  * Utility to determine if aString has content
	  * @param aString
	  * @return boolean
	  */
	public static boolean isNullOrEmpty(String aString) {
		if (aString == null) 
		{
			return true;
		}
		if (aString.length() == 0)
		{
			return true;
		}
		return false;
	}
	public static boolean isNullOrSpaces(String aString) {
		if (aString == null) {
			return true;
		}
		return isAllSpaces(aString);
		
	}
	/**
	 * Answer a boolean indicating whether aString all numeric
	 * Creation date: (10/19/00 1:19:26 PM)
	 * @return boolean
	 * @param aString java.lang.String
	 */
	public static boolean isNumeric(String aString) {
		boolean answer = true;

		for (int i = 0; i < aString.length(); i++) {
			if (!Character.isDigit(aString.charAt(i))) {
				answer = false;
				break;
			}
		}
		return answer;
	}
	/**
	  * Generic utility to pad the begining of a string with some character
	  *
	  * s :  The original String passed into method to be padded.
	  * len: The length of the entire String including original and the padding.
	  * pad: The pad character that needs to be added to the beginning of the original String.
	  *
	**/
	public static String padStringBegin(String s, int len, String pad) {
		StringBuffer value = new StringBuffer();
		int lengS;

		if (s != null)
			lengS = s.length();
		else
			lengS = 0;
		for (int i = 0; i < (len - lengS); i++) {
			value.append(pad.toString());
		}
		if (s != null) {
			value.append(s);
		}

		return value.toString();
	}
	/**
	  * Generic utility to pad the end of a string with some character
	  *
	  * s :  The original String passed into method to be padded.
	  * len: The length of the entire String including original and the padding.
	  * pad: The pad character that needs to be added to the end of the original String.
	  *
	**/

	public static String padStringEnd(String s, int len, String pad) {
		StringBuffer value = new StringBuffer();
		int leng = 0;
		if (s != null) {
			value.append(s);
			leng = s.length();
		}
		for (int i = 1; i <= (len - leng); i++) {
			value.append(pad.toString());
		}
		return value.toString();
	}
	public static String searchAndReplace(
		String searchString,
		char searchForChar,
		String replaceWithString) {
		if (searchString == null)
			return null;
		String ss = searchString.trim();
		int iL = ss.length();
		if (iL == 0)
			return null;
		int iI = ss.indexOf(searchForChar, 0);
		if (iI > -1) //found a searchForChar
			{
			ss =
				ss.substring(0, iI)
					+ replaceWithString
					+ ss.substring(iI + 1, iL);
			ss = searchAndReplace(ss, searchForChar, replaceWithString);
			return ss;
		}
		return ss;
	}

	/**
	 * Answer a string which is the original string with the first character converted to uppercase characters
	 * Creation date: (10/19/00 1:19:26 PM)
	 * @return String
	 * @param aString java.lang.String
	 */
	public static String initialUpperCase(String aString) {
		StringBuffer answer = new StringBuffer();

		for (int i = 0; i < aString.length(); i++) {
			if (i == 0) {
				answer.append(Character.toUpperCase(aString.charAt(i)));
			} else {
				answer.append(aString.charAt(i));
			}
		}
		return answer.toString();
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (3/30/01 1:21:10 PM)
	 */

	public static boolean containsChar(String arg, char c) {
		return arg.indexOf(c) >= 0;
	}
	
		
	public static String makeProper(String theString) throws IOException
	{		 
		 StringReader in = new StringReader(theString.toLowerCase());
		 
		 boolean precededBySpace = true;
		 
		 StringBuffer properCase = new StringBuffer();    
		 
		 while(true)
		 {      
		 	int i = in.read();
		  
		 	if (i == -1)
		 		break;      
		 	
		 	char c = (char)i;      
		 	
		 	if (c == ' ')
		 	{
		 	      properCase.append(c);
		 	      precededBySpace = true;
		 	}
		 	else
		 	{
		 	    if (precededBySpace)
		 	    { 
		 		   properCase.append(Character.toUpperCase(c));
		 	    }
		 	    else
		 	    { 
		 	         properCase.append(c); 
		 	    }
		 	    precededBySpace = false;
		 	}
		}
		 
		return properCase.toString();    
		 
	}
	
	public String stripToken(String incomingString, String tokenToStrip)
	{
		String outgoingString = incomingString;
		
		int tokenPosition = incomingString.indexOf(tokenToStrip);
		
        if (tokenPosition!= -1)
        {
        	outgoingString = outgoingString.substring(0,tokenPosition);
        }
        
        return outgoingString;
	}
	public static String make2ndCharLower(String sddvalueColName)  throws IOException
	{
		StringReader in = new StringReader(sddvalueColName);
		 
		 StringBuffer outString = new StringBuffer();    
		 
		 int loopCounter = 0;
		 
		 while(true)
		 {      
		 	int i = in.read();
		  
		 	if (i == -1)
		 		break;      
		 	
		 	char c = (char)i;      
		 	
		 	if (loopCounter == 1)
		 	{ 
		 	   outString.append(Character.toLowerCase(c));
		 	}
		 	else
		 	{ 
		 	   outString.append(c); 
		 	}
		 			    
		 loopCounter++;
		    
		}
		 
		return outString.toString();    
	}
}