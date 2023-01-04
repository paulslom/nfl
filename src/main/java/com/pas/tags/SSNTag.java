/**
 * Title: SSNTag Project: Client Service Workbench Description: This CustomTag
 * class contains logic to format SSN Field Copyright: Copyright (c) 2005
 * Company: Lincoln Life
 * 
 * @author CGI/Mala K
 * @version 1.0
 */

package com.pas.tags;

import javax.servlet.jsp.tagext.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SSNTag extends BaseTag {
	private Logger log = LogManager.getLogger(this.getClass()); //log4j for Logging

	private String typeValue;

	private String typeProperty;

	javax.servlet.jsp.JspWriter cobjout;

	Object cobjvalue = null;

	Object cobjTypeproperty = null;

	/**
	 * Formats SSN value
	 * 
	 * @throws javax.servlet.jsp.JspException
	 */
	public int doStartTag() throws javax.servlet.jsp.JspException
	{
		String lSssnvalue = null;
		String lSTypeproperty = null;
		String lSFormattedvalue = "";
	
		try
		{
			cobjvalue = getPropertyvalue(name, property, scope);
			
			if (cobjvalue != null)
			{
				lSssnvalue = cobjvalue.toString().trim();
				if(lSssnvalue.equalsIgnoreCase("0"))
					lSssnvalue = null;
			}
			
			if (lSssnvalue != null && lSssnvalue.length() > 0)
			{
				if (typeValue != null && typeValue.length() > 0)
				{
					lSFormattedvalue = formatSSNForDisplay(lSssnvalue);
				}
			}
			else 
				if (typeProperty != null && typeProperty.length() > 0)
				{
					cobjTypeproperty = getPropertyvalue(name, typeProperty,scope);
					if (cobjTypeproperty != null)
					{
						lSTypeproperty = cobjTypeproperty.toString();
					}
					else
					{
						lSFormattedvalue = formatSSNForDisplay(lSssnvalue);
					}
					if (lSTypeproperty != null)
					{
						lSFormattedvalue = formatSSNForDisplay(lSssnvalue);
					} 
				}
				else
				{
					lSFormattedvalue = formatSSNForDisplay(lSssnvalue);
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
				size = "50";
			if (readonly.equalsIgnoreCase("true")
					&& textbox.equalsIgnoreCase("true")) {
				cobjout
						.print("<input style=\"width: "+width+" px;\" maxlength=\""+size+"\" value=\""
								+ lSFormattedvalue + "\" readonly >");

			} else if (readonly.equalsIgnoreCase("false")
					&& textbox.equalsIgnoreCase("true")) {
				cobjout
						.print("<input style=\"width: "+width+" px;background-color: cyan;\" maxlength=\""+size+"\" value=\""
								+ lSFormattedvalue + "\" >");
			} else {
				cobjout.print(lSFormattedvalue);
			}

		} catch (Exception e) {
			log.warn(e);
		}
		if (lSFormattedvalue.equals("")) {
			log.debug("Property value in SSN Custom Tag: "+cobjvalue);
		}
		return Tag.SKIP_BODY;
	}

	/**
	 * Formats SSN
	 * 
	 * @param SSN
	 *            Number
	 * @return formatted SSN Number
	 */
	public String formatSSNForDisplay(String psvalue) {
		
		StringBuffer lobjSsnbuff = new StringBuffer();
		StringBuffer lobjStemp = new StringBuffer();
		try {
			if(psvalue.length()< 9){
				for(int icount=psvalue.length(); icount < 9;icount++)
					lobjStemp.append("0");
				lobjStemp.append(psvalue);
				psvalue = lobjStemp.toString();
			}
			
			lobjSsnbuff.append(psvalue.substring(0, 3));
			if (psvalue.length() > 3) {
				lobjSsnbuff.append("-");
				lobjSsnbuff.append(psvalue.substring(3, 5));
			}
			if (psvalue.length() > 5 ) {
				lobjSsnbuff.append("-");
				lobjSsnbuff.append(psvalue.substring(5, 9));
			}
			
		} catch (Exception e) {
			log.info(e);
		}
		return lobjSsnbuff.toString();
	}

	/**
	 * Formats TIN Tax Id Type Number
	 * 
	 * @param Tax
	 *            Id Number
	 * @return formatted TIN Tax Id Type Number
	 */
	public String formatTINForDisplay(String psvalue) {
		StringBuffer lobjTinbuff = new StringBuffer();
		StringBuffer lobjStemp = new StringBuffer();
		try {
			if(psvalue.length()< 9){
				for(int icount=psvalue.length(); icount < 9;icount++)
					lobjStemp.append("0");
				lobjStemp.append(psvalue);
				psvalue = lobjStemp.toString();
			}
			lobjTinbuff.append(psvalue.substring(0, 2));
			if (psvalue.length() > 2) {
				lobjTinbuff.append("-");
				lobjTinbuff.append(psvalue.substring(2, 9));
			}
		} catch (Exception e) {
			log.info(e);
		}
		return lobjTinbuff.toString();
	}

	/**
	 * @return String
	 */
	public String getTypeProperty() {
		return typeProperty;
	}

	/**
	 * @return String
	 */
	public String getTypeValue() {
		return typeValue;
	}

	/**
	 * Sets the typeProperty.
	 * 
	 * @param typeProperty
	 *            The typeProperty to set
	 */
	public void setTypeProperty(String typeProperty) {
		this.typeProperty = typeProperty;
	}

	/**
	 * Sets the typeValue.
	 * 
	 * @param typeValue
	 *            The typeValue to set
	 */
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public void release() {
		super.release();
		typeValue = null;
		typeProperty = null;
	}

}