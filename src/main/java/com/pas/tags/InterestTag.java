/*
 * Created on Jul 5, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pas.tags;

import javax.servlet.jsp.tagext.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Mala K
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InterestTag extends BaseTag {
	private Logger log = LogManager.getLogger(this.getClass()); //log4j for Logging

	javax.servlet.jsp.JspWriter cobjout;

	protected String onBlur = null;

	Object cobjvalue = null;

	public int doStartTag() throws javax.servlet.jsp.JspException {
		String lSCode = "";
		String lSonBlur ="";
		String elemName = null;
		try {
			cobjvalue = getPropertyvalue(name, property, scope);

			if (cobjvalue != null) {
				lSCode = cobjvalue.toString().trim();
			}
			cobjout = pageContext.getOut();
			if(width == null) 
				width = "100";
			
			if(size == null) 
				size = "15";	
			
			if (readonly == null) {
				readonly = "true";
			}
			if (textbox == null) {
				textbox = "true";
			}
			if (id == null) {
				if(readonly.equalsIgnoreCase("true"))
					elemName = property+"ReadOnly";
				else elemName = property;
			} else
				elemName = id;
			
			if(lSCode == null || lSCode.equals(""))
				lSCode="0%";
			else if(lSCode.length() > 0)
				    lSCode = lSCode.concat("%");
			
			if (readonly.equalsIgnoreCase("true")
					&& textbox.equalsIgnoreCase("true") ) {
				cobjout.print("<input style=\"width: "+width+" px;\" name=\""
						+ elemName + "\" maxlength=\""+size+"\" value=\"" + lSCode
						+ "\" readonly  tabindex=\"-1\">");

			} else if (readonly.equalsIgnoreCase("false")
					&& textbox.equalsIgnoreCase("true")) {
				if(onBlur!=null)
					lSonBlur = " onBlur=\""+getOnBlur()+"\"";
				cobjout
						.print("<input onFocus=\"this.value=unFormatRate(this.value);this.select();\" style=\"width: "+width+" px;background-color: cyan;\" name=\""
								+ elemName + "\" maxlength=\""+size+"\" value=\"" + lSCode
								+ "\""+lSonBlur+">");
			} else {
				cobjout.print(lSCode);
			}

		} catch (Exception e) {
			log.warn(e);
		}
		if (lSCode.equals("")) {
			log.debug("Property Value in Interest Custom Tag: " + cobjvalue);
		}
		return Tag.SKIP_BODY;
	}
	
	public void release() {
		super.release();
		id = null;
	}
	
	/**
	 * @return Returns the onBlur.
	 */
	public String getOnBlur() {
		return onBlur;
	}
	/**
	 * @param onBlur The onBlur to set.
	 */
	public void setOnBlur(String onBlur) {
		this.onBlur = onBlur;
	}
}

