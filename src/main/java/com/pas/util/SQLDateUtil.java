package com.pas.util;

/**
 * Insert the type's description here.
 * Creation date: (10/13/00 2:30:05 PM)
 * @author: Administrator
 */
public class SQLDateUtil
{
	private short YYYY;
	private short MM;
	private short DD;
/**
 * SQLDateUtil constructor comment.
 */
public SQLDateUtil() {
	super();
}
	public SQLDateUtil(short YYYY,short MM,short DD)
	{
		super();
		this.YYYY = YYYY;
		this.MM = MM;
		this.DD = DD;
	}
/**
 * Insert the method's description here.
 * Creation date: (10/13/00 2:33:43 PM)
 * @return java.lang.String
 */
public String formatDate()
{
	String resultDate = null;
	try
	{
		String year = new Integer(YYYY).toString().trim();
		String month = new Integer(MM).toString().trim();
		String day = new Integer(DD).toString().trim();

		if (year.equals("0") || month.equals("0") || day.equals("0"))
			resultDate = "01/01/1800";		
		else
		if (year.length() == 0 || month.length() == 0 || day.length() == 0)
			resultDate = "01/01/1800";		
		else
			resultDate = month + "/" + day + "/" + year;
	}
	catch (Exception e)
	{
		resultDate = "01/01/1800";		
	}			
	return resultDate;
}
}