/*
 * Created on Apr 21, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.tags;

import javax.servlet.jsp.JspException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pas.util.PASUtil;

/**
 * Title: DisplayFieldTag Project: CSW Description: To Display field values from
 * the form bean in scope in JSP Copyright: Copyright (c) 2003 Company: CGI
 * 
 * @author partha
 * @version
 */

public class DisplayFieldTag extends BaseTag {

	javax.servlet.jsp.JspWriter cobjout;

	private Logger log = LogManager.getLogger(this.getClass());
	
	private String onchange = null;

	private String disabled = null;
	
	private String onfocus = null;
	
	private String onblur = null;
	
	private String tabindex = null;
	
	private String autocomplete = null;
	
	private String onclick = null;
	
	private boolean uppercase;
	
	public int doStartTag() throws JspException {
		Object cobjvalue = null;
		try {

			cobjvalue = getPropertyvalue(name, property, scope);
			
			if (cobjvalue == null) {
				if(defaultValue!=null)
				    cobjvalue = defaultValue.toString();
				else cobjvalue = "";
			}
			if(uppercase)
				cobjvalue = cobjvalue.toString().toUpperCase();
			cobjout = pageContext.getOut();

			if (readonly == null) {
				readonly = "true";
			}
			if (textbox == null) {
				textbox = "true";
			}

			StringBuffer sbOut = new StringBuffer();
			if (textbox.equalsIgnoreCase("true")) {				
				sbOut.append("<input name=\"");
				if(id==null){
					sbOut.append(property+"\" ");
				}	
				else
					sbOut.append(id+"\" ");			
				sbOut.append("value=\"" + cobjvalue.toString().trim() + "\" ");
				if(onkeyup!=null){
					sbOut.append("onKeyUp=\""+ onkeyup +"\" ");
				}
				if (onchange != null) {
					sbOut.append(" onchange=\"" + getOnchange() + "\" ");
				}
				if (onfocus != null) {
					sbOut.append(" onFocus=\"" + getOnfocus() + "\" ");
				}
				if (onblur != null) {
					sbOut.append(" onBlur=\"" + getOnblur() + "\" ");
				}
				if (disabled != null && disabled.equalsIgnoreCase("true"))
					sbOut.append(" disabled ");
				
				if (tabindex != null)
					if (PASUtil.isValidNumeric(tabindex))
					   sbOut.append("tabindex=\"" + tabindex + "\" ");
								
				if (size != null) {
					sbOut.append("maxlength=\"" + size + "\" ");
				}
				if (autocomplete != null) {
					sbOut.append("autocomplete=\"" + autocomplete + "\" ");
				}
				if (onclick != null) {
					sbOut.append("onclick=\"" + onclick + "\" ");
				}
				if (width != null) {
					sbOut.append("style=\"width: " + width + "px;");
				}				
				if (readonly.equals("true")) {
					if(width != null)
						sbOut.append(" border:none; background-color:transparent;\" ");
					else
						sbOut.append("style=\"border:none; background-color:transparent;\"");
					sbOut.append(" readonly tabindex=\"-1\"");
				}					
				else if(!"true".equals(disabled)) sbOut.append(" background-color: cyan;\"");
				else{
					if(width != null)
						sbOut.append("\" ");
				}
				sbOut.append(">");
			} else {
				sbOut.append(cobjvalue.toString().trim());
			}

			cobjout.print(sbOut.toString());

		} catch (Exception e) {
			log.warn(e);
		}
		if (cobjvalue == null || (cobjvalue !=null && cobjvalue.equals(""))) {
			log
					.debug("Property value in DisplayField Custom Tag: "+ cobjvalue );
		}
		return EVAL_PAGE;
	}

	public void release() {
		super.release();
		onchange = null;
		disabled = null;
		onfocus = null;
		onblur = null;
	}
	/**
	 * @return Returns the onchange.
	 */
	public String getOnchange() {
		return onchange;
	}

	/**
	 * @param onchange
	 *            The onchange to set.
	 */
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	/**
	 * @return Returns the disabled.
	 */
	public String getDisabled() {
		return disabled;
	}
	/**
	 * @param disabled The disabled to set.
	 */
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	/**
	 * @return Returns the onfocus.
	 */
	public String getOnfocus() {
		return onfocus;
	}
	/**
	 * @param onfocus The onfocus to set.
	 */
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}
	/**
	 * @return Returns the onblur.
	 */
	public String getOnblur() {
		return onblur;
	}
	/**
	 * @param onblur The onblur to set.
	 */
	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}
	/**
	 * @return
	 */
	public boolean isUppercase() {
		return uppercase;
	}

	/**
	 * @param b
	 */
	public void setUppercase(boolean b) {
		uppercase = b;
	}

	/**
	 * @return Returns the tabindex.
	 */
	public String getTabindex() {
		return tabindex;
	}
	/**
	 * @param tabindex The tabindex to set.
	 */
	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	public String getAutocomplete()
	{
		return autocomplete;
	}

	public void setAutocomplete(String autocomplete)
	{
		this.autocomplete = autocomplete;
	}

	public String getOnclick()
	{
		return onclick;
	}

	public void setOnclick(String onclick)
	{
		this.onclick = onclick;
	}
}