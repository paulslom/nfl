/*
 * Created on Apr 28, 2005
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.pas.valueObject;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import com.pas.valueObject.IValueObject;

/**
 * @author 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class AppDate implements IValueObject
{
	private String appmonth;

	private String appday;

	private String appyear;

	private String appfulldate;

	private boolean empty = true;
	
    public AppDate()
	{
		super();		
	}
    
	public AppDate(String strDate)
    {
        super();
        
        int tokenIndex = 0;
        strDate = strDate.trim();
        StringTokenizer dashes = new StringTokenizer(strDate, "-");
        int totalTokens = dashes.countTokens();
        
        //If not exactly 3 tokens then bad format
        if (totalTokens == 3)
        {
           while (dashes.hasMoreTokens())
           {
               tokenIndex++;
               String token = dashes.nextToken();
               
               switch (tokenIndex)
               {
                 case 1:
                     this.setAppmonth(token);
                 case 2:
                     this.setAppday(token);
                 case 3:
                     this.setAppyear(token);
               }                 
           }            
        }
    }
	/**
	 * @return Returns the appday.
	 */
	public String getAppday() {
		return appday;
	}

	public void initAppDateToToday()
	{
		GregorianCalendar cal = new GregorianCalendar();
		
		int calYear = cal.get( Calendar.YEAR );
		int calMonth = cal.get( Calendar.MONTH ) + 1;
		int calDay = cal.get( Calendar.DAY_OF_MONTH );
		
		setAppday(String.valueOf(calDay));
		setAppmonth(String.valueOf(calMonth));
		setAppyear(String.valueOf(calYear));
	}
	public void initAppDate()
	{
		setAppday("");
		setAppmonth("");
		setAppyear("");				
	}
	/**
	 * @param appday
	 *            The appday to set.
	 */
	public void setAppday(String appday) {		
		if(appday!=null && !appday.equals(""))
		{   
			this.appday = appday;
			empty = false;
		}
	}

	/**
	 * @return Returns the appfulldate.
	 */
	public String getAppfulldate() {
		return appfulldate;
		
	}

	public String toStringMMDDYYYY()
	{
		return getAppmonth() + "-" + getAppday() + "-" + getAppyear();
	}
	public String toStringYYYYMMDD()
	{
		String mm = getAppmonth();
		String dd = getAppday();
		
		if (getAppmonth().trim().length() == 1)
		   mm = "0" + mm;
		
		if (getAppday().trim().length() == 1)
		   dd = "0" + dd;
		
		return getAppyear() + "-" + mm + "-" + dd;
	}
	/**
	 * @param appfulldate
	 *            The appfulldate to set.
	 */
	public void setAppfulldate(String appfulldate) {		
		if(appfulldate!=null && !appfulldate.equals(""))
		{
			this.appfulldate = appfulldate;
			empty=false;
		}
	}

	/**
	 * @return Returns the appmonth.
	 */
	public String getAppmonth() {
		return appmonth;
		
	}

	/**
	 * @param appmonth
	 *            The appmonth to set.
	 */
	public void setAppmonth(String appmonth) {		
		if(appmonth!=null && !appmonth.equals(""))
		{
			this.appmonth = appmonth;
			empty=false;
		}
	}

	/**
	 * @return Returns the appyear.
	 */
	public String getAppyear() {		
		return appyear;
	}

	/**
	 * @param appyear
	 *            The appyear to set.
	 */
	public void setAppyear(String appyear) {		
		if(appyear!=null && !appyear.equals(""))
		{	
			this.appyear = appyear;
			empty=false;
		}
	}
	/**
	 * @return
	 */
	public boolean isEmpty() {
		return empty;
	}

}