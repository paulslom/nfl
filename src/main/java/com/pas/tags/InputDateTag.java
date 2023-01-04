/**
 * Title: InputDateTag Project: Client Service Workbench Description: This
 * CustomTag class populates 3 text fields to read month, day and year values
 * and set the same to a hidden after concatenating them. Copyright:
 * Copyright(c) 2005 Company: Lincoln Life
 * 
 * @author CGI/Balaraj P
 * @version 1.0
 */

package com.pas.tags;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.util.PASUtil;
import com.pas.valueObject.AppDate;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

public class InputDateTag extends DisplayFieldTag {

	private Logger log = LogManager.getLogger(this.getClass()); //log4j for Logging

	private String name;

	private String property;

	private String scope;
	
	private String tabindex = null;

	Object cobjvalue = null;

	javax.servlet.jsp.JspWriter cobjout;
	
	private String onkeypress = null;
	
	protected String readonly = "false";

	/**
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return String
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @return String
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param name
	 *            The name to set
	 */
	public void setName(String n) {
		name = n;
	}

	/**
	 * @param property
	 *            The property to set
	 */
	public void setProperty(String n) {
		property = n;
	}

	/**
	 * @param scope
	 *            The scope to set
	 */
	public void setScope(String n) {
		scope = n;
	}

	/**
	 * @return Returns the readonly.
	 */
	public String getReadonly() {
		return readonly;
	}

	/**
	 * @param readonly
	 *            The readonly to set.
	 */
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	/**
	 * display 3 text fields to read month, day and year
	 * 
	 * @throws javax.servlet.jsp.JspException
	 */
	public int doStartTag() throws JspException {
		try {
			String bgcolor = "";
			String monthTabIndex = "";
			String dayTabIndex = "";
			String yearTabIndex = "";
			String lsDay = null;
			String lsMonth = null;
			String lsYear = null;
			String lsKeyPress="";

			if (getReadonly().equals("false"))
				bgcolor = "background : cyan;";
			else 
				bgcolor = "border:none; background-color:transparent;";
			
			AppDate lobjvalue = null;

			cobjvalue = getPropertyvalue(name, property, scope);

			if (cobjvalue != null) {

				if (cobjvalue instanceof AppDate) {

					lobjvalue = (AppDate) cobjvalue;

				} 
				
				lsDay = lobjvalue.getAppday();
				lsMonth = lobjvalue.getAppmonth();
				if (lobjvalue.getAppyear() != null)
					lsYear = lobjvalue.getAppyear().toString();
			}

			cobjout = pageContext.getOut();
			StringBuffer sbOut = new StringBuffer();
			StringBuffer lobjbuff = new StringBuffer();
			sbOut.append("<input type=\"text\" maxlength = 2 size = 2 style = \"width : 25 px; ");
			sbOut.append(bgcolor);
			sbOut.append(" \"");
			
			lobjbuff.append("<input type=\"text\" maxlength = 4 size = 4 style = \"width : 50 px; ");
			lobjbuff.append(bgcolor);
			lobjbuff.append(" \"");
			
			if (onkeypress != null) {
				lsKeyPress = "onkeypress=\""+getOnkeypress()+"\"";
			}
			if (getReadonly().equals("true")) {
				sbOut.append("  readonly ");
				lobjbuff.append("  readonly ");
			}
			if (tabindex != null)
			{
				if (PASUtil.isValidNumeric(tabindex))
				{
					monthTabIndex = " tabindex=\"" + tabindex + "\"";
					int intTabIndex = Integer.parseInt(tabindex);
					intTabIndex = intTabIndex + 1;
					dayTabIndex = " tabindex=\"" + String.valueOf(intTabIndex) + "\"";
					intTabIndex = intTabIndex + 1;
					yearTabIndex = " tabindex=\"" + String.valueOf(intTabIndex) + "\"";
				}
			}
				
			if (lsDay != null && lsMonth != null && lsYear != null)
			{
				cobjout.write(sbOut.toString()
						+ " id= \"" + property
						+ "month\" name=\"" + property
						+ ".appmonth\"  value=\"" + lsMonth
						+ "\"  onKeyUp=\"return autoTab(this, 2, event);\" "
						+ monthTabIndex
						+ lsKeyPress +" >/");
				cobjout.write(sbOut.toString()
						+ " id = \""
						+ property
						+ "day\" name=\""
						+ property
						+ ".appday\"  value=\""
						+ lsDay 						
						+ "\"    onKeyUp=\"return autoTab(this, 2, event);\" "
						+ dayTabIndex
						+ lsKeyPress +" >/");
				cobjout.write(lobjbuff.toString()
						+ " id = \""
						+ property
						+ "year\" name=\""
						+ property
						+ ".appyear\"  value=\""
						+ lsYear + "\" onKeyUp=\"return autoTab(this, 4, event);\" "
						+ yearTabIndex 
						+ lsKeyPress + " >");
			}
			else
			{
				cobjout.write(sbOut.toString()
								+ " id= \""
								+ property
								+ "month\" name=\""
								+ property
								+ ".appmonth\"   onKeyUp=\"return autoTab(this, 2, event);\" "
								+ monthTabIndex
								+ lsKeyPress + " >/");
				cobjout.write(sbOut.toString()
								+ " id = \""
								+ property
								+ "day\" name=\""
								+ property
								+ ".appday\"  onKeyUp=\"return autoTab(this, 2, event);\" "
								+ dayTabIndex
								+ lsKeyPress + " >/");
				cobjout.write(lobjbuff.toString()
								+ " id = \""
								+ property
								+ "year\" name=\""
								+ property
								+ ".appyear\" onKeyUp=\"return autoTab(this, 4, event);\" maxlength = 4 size = 4 "
								+ yearTabIndex
								+ lsKeyPress + " >");
			}
			cobjout.write("<input id = \"" + property
					+ "fulldate\" type=\"hidden\" name=\"" + property
					+ ".appfulldate\">");


		} catch (Exception e) {

			log.warn("Input Date Custom Tag"
					+ e);
		}

		return Tag.SKIP_BODY;
	}

	public void release() {
		super.release();
		name = null;
		property = null;
		scope = null;
		onkeypress = null;
		
	}
	
	/**
	 * @return Returns the onkeypress.
	 */
	public String getOnkeypress() {
		return onkeypress;
	}
	/**
	 * @param onkeypress The onkeypress to set.
	 */
	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}
	/**
	 * @return Returns the tabIndex.
	 */
	public String getTabindex() {
		return tabindex;
	}
	/**
	 * @param tabIndex The tabIndex to set.
	 */
	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}
}