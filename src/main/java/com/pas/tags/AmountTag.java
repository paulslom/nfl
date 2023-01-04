/**
 * Title: AmountTag Project: Client Service Workbench Description: This
 * CustomTag class contains logic to format Amount Field Copyright: Copyright
 * (c) 2005 Company: Lincoln Life
 * 
 * @author CGI/Mala K
 * @version 1.0
 */

package com.pas.tags;

import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.jsp.tagext.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AmountTag extends BaseTag {
	private Logger log = LogManager.getLogger(this.getClass()); //log4j for Logging

	javax.servlet.jsp.JspWriter cobjout;

	protected String onBlur = null;

	Object cobjvalue = null;
	
	private boolean absolute;

	public int doStartTag() throws javax.servlet.jsp.JspException {
		String lSCode = null;
		String lSAmount = "$0.00";
		String elemName = null;
		String lSonBlur = ""; 
		try {
			cobjvalue = getPropertyvalue(name, property, scope);

			if (cobjvalue != null) {
				lSCode = cobjvalue.toString();
			}

			if (lSCode != null && lSCode.length() > 0) {
				double ldAmount = Double.parseDouble(lSCode);
				if(absolute && ldAmount<0){
					ldAmount = ldAmount*-1;
				}
				NumberFormat lobjAmountformat = NumberFormat
						.getCurrencyInstance(Locale.US);
				lSAmount = lobjAmountformat.format(ldAmount);
			}
			cobjout = pageContext.getOut();

			if (width == null)
				width = "100";

			if (size == null)
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
			if (readonly.equalsIgnoreCase("true")
					&& textbox.equalsIgnoreCase("true")) {
				cobjout.print("<input style=\"width: " + width
						+ " px;\" name=\"" + elemName + "\" maxlength=\""
						+ size + "\" value=\"" + lSAmount + "\" readonly  tabindex=\"-1\">");

			} else if (readonly.equalsIgnoreCase("false")
					&& textbox.equalsIgnoreCase("true")) {
				if (onBlur != null)
					lSonBlur = " onBlur=\"" + getOnBlur() + "\"";

				cobjout.print("<input onFocus=\"this.value=unFormatAmount(this.value);this.select();\" style=\"width: " + width
						+ " px;background-color: cyan;\" name=\"" + elemName
						+ "\" maxlength=\"" + size + "\" value=\"" + lSAmount + "\"" + lSonBlur + ">");
			} else {
				cobjout.print(lSAmount);
			}

		} catch (Exception e) {
			log.warn(e);
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
	 * @param onBlur
	 *            The onBlur to set.
	 */
	public void setOnBlur(String onBlur) {
		this.onBlur = onBlur;
	}
	/**
	 * @return
	 */
	public boolean isAbsolute() {
		return absolute;
	}

	/**
	 * @param b
	 */
	public void setAbsolute(boolean b) {
		absolute = b;
	}

}