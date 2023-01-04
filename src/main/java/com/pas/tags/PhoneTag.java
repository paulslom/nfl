/**
 * Title: PhoneTag Project: Client Service Workbench Description: This CustomTag
 * class contains logic to format Phone Field Copyright: Copyright (c) 2005
 * Company: Lincoln Life
 * 
 * @author CGI/Mala K
 * @version 1.0
 */
package com.pas.tags;

import javax.servlet.jsp.tagext.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhoneTag extends BaseTag {
	private Logger log = LogManager.getLogger(this.getClass()); //log4j for Logging

	javax.servlet.jsp.JspWriter cobjout;

	Object cobjvalue = null;

	/**
	 * Formats Phone number
	 * 
	 * @throws javax.servlet.jsp.JspException
	 */
	public int doStartTag() throws javax.servlet.jsp.JspException {
		String lSCode = null;
		String lSDisplayPhonenumerationber = "";
		try {
			cobjvalue = getPropertyvalue(name, property, scope);
			
			if (cobjvalue != null) {
				lSCode = cobjvalue.toString().trim();
			}
			if (lSCode != null && lSCode.length() > 0) {
				StringBuffer lobjFormatedPhonenumerationber = new StringBuffer("(");
				if (lSCode.length() > 0) {
					lobjFormatedPhonenumerationber.append(lSCode.substring(0, 3));
					lobjFormatedPhonenumerationber.append(") ");
				}
				if (lSCode.length() > 3) {
					lobjFormatedPhonenumerationber.append(lSCode.substring(3, 6));
				}
				if (lSCode.length() > 6) {
					lobjFormatedPhonenumerationber.append("-");
					lobjFormatedPhonenumerationber.append(lSCode.substring(6, 10));
				}
				lSDisplayPhonenumerationber = lobjFormatedPhonenumerationber.toString();
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
				size = "30";
			if (readonly.equalsIgnoreCase("true")
					&& textbox.equalsIgnoreCase("true")) {
				cobjout
						.print("<input style=\"width: "+width+" px;\" maxlength=\""+size+"\" value=\""
								+ lSDisplayPhonenumerationber + "\" readonly >");

			} else if (readonly.equalsIgnoreCase("false")
					&& textbox.equalsIgnoreCase("true")) {
				cobjout
						.print("<input style=\"width: "+width+" px;background-color: cyan;\" maxlength=\""+size+"\" value=\""
								+ lSDisplayPhonenumerationber + "\" >");
			} else {
				cobjout.print(lSDisplayPhonenumerationber);
			}

		} catch (Exception e) {
			log.warn(e);
		}
		if (lSDisplayPhonenumerationber.equals("")) {
			log.debug("Property value in Phone Custom Tag: "+cobjvalue);
		}
		return Tag.SKIP_BODY;
	}

}