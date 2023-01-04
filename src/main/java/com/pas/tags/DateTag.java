/**
 * Title: DateTag Project: Client Service Workbench Description: This CustomTag
 * class contains logic to format Date Field Copyright: Copyright (c) 2005
 * Company: Lincoln Life
 * 
 * @author CGI/Mala K
 * @version 1.0
 */
package com.pas.tags;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.tagext.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.constants.IAppConstants;
import com.pas.util.PASUtil;

public class DateTag extends BaseTag {
	private Logger log = LogManager.getLogger(this.getClass()); //log4j for Logging

	javax.servlet.jsp.JspWriter cobjout;

	Object cobjvalue = null;

	/**
	 * Formats Date value to MM/DD/YYYY format
	 * 
	 * @throws javax.servlet.jsp.JspException
	 */
	public int doStartTag() throws javax.servlet.jsp.JspException {
		String lSDisplayDate = "";

		try {
			cobjvalue = getPropertyvalue(name, property, scope);
			if (cobjvalue != null) {
				if (cobjvalue instanceof com.pas.valueObject.AppDate) {
					com.pas.valueObject.AppDate lobjvalue = (com.pas.valueObject.AppDate) cobjvalue;
					String lsMonth = lobjvalue.getAppmonth();
					String lsDay = lobjvalue.getAppday();
					String lsYear = String.valueOf(lobjvalue.getAppyear());
					lSDisplayDate = formatDateForDisplay(lsDay, lsMonth, lsYear);
				} 
			}
			cobjout = pageContext.getOut();
			if (readonly == null) {
				readonly = "true";
			}
			if (textbox == null) {
				textbox = "true";
			}
			if(width == null) 
				width = "100";
			if(size == null) 
				size = "10";
			if (readonly.equalsIgnoreCase("true")
					&& textbox.equalsIgnoreCase("true")) {
				cobjout
						.print("<input style=\"width: " + width + "px;\" maxlength=\""+size+"\" value=\""
								+ lSDisplayDate + "\" readonly  tabindex=\"-1\">");

			} else if (readonly.equalsIgnoreCase("false")
					&& textbox.equalsIgnoreCase("true")) {
				cobjout
						.print("<input style=\"width: " + width + "px;background-color: cyan;\" maxlength=\""+size+"\" value=\""
								+ lSDisplayDate + "\" >");
			} else {
				cobjout.print(lSDisplayDate);
			}

		} catch (Exception e) {
			log.warn(e);
		}
		if (lSDisplayDate.equals("")) {
			log.debug("Property Value in Date Custom Tag: "+cobjvalue);
		}

		return Tag.SKIP_BODY;
	}

	/**
	 * Formats Date
	 * 
	 * @param Day
	 *            Month Year
	 * @return formatted Date
	 */
	public String formatDateForDisplay(String psDay, String psMonth,
			String psYear) {
		StringBuffer lobjDate = new StringBuffer();
		String lSDisplayDate = "";
		try {
			if (psMonth != null && PASUtil.validMonth(psMonth)) {
				if (psMonth.length() == 1)
					lobjDate.append("0" + psMonth);
				else
					lobjDate.append(psMonth);
			}
			if (psDay != null && PASUtil.validDay(psDay)) {
				if (psDay.length() == 1)
					lobjDate.append("0" + psDay);
				else
					lobjDate.append(psDay);
			}
			if (psYear != null && PASUtil.validYear(psYear)) {
				lobjDate.append(psYear);
			}

			SimpleDateFormat lobjDateInputFormat = new SimpleDateFormat(
					"MMddyyyy");
			SimpleDateFormat lobjDateOutputFormat = new SimpleDateFormat(
					IAppConstants.CUSTOM_TAG_DATE_FORMAT);
			Date lobjDateFormat = lobjDateInputFormat
					.parse(lobjDate.toString());
			lSDisplayDate = lobjDateOutputFormat.format(lobjDateFormat);
		} catch (Exception e) {
			log.info(e);
		}
		return lSDisplayDate;
	}

}