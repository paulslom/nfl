/**
 * Title: DescriptionTag Project: Client Service Workbench Description: This
 * CustomTag class contains logic to format Description Field Copyright:
 * Copyright (c) 2005 Company: Lincoln Life
 * 
 * @author CGI/Mala K
 * @version 1.0
 */
package com.pas.tags;

import javax.servlet.jsp.tagext.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pas.util.IPropertyCollection;
import com.pas.util.IPropertyReader;
import com.pas.util.PropertyReaderFactory;

public class DescriptionTag extends BaseTag {
	private Logger log = LogManager.getLogger(this.getClass()); //log4j for

	// Application Log

	private String entity;

	javax.servlet.jsp.JspWriter cobjout;

	private IPropertyReader pr;

	Object cobjvalue = null;

	// Constructor data is added for testing this tag functionality this will be
	// removed on using proprety reader interface

	/**
	 * @return String
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * Sets the entity.
	 * 
	 * @param entity
	 *            The entity to set
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * Format description value
	 * 
	 * @throws javax.servlet.jsp.JspException
	 */
	public int doStartTag() throws javax.servlet.jsp.JspException {
		String lSCode = null;
		String lSDescription = "";
		String defValue = "";
		try {
			cobjvalue = getPropertyvalue(name, property, scope);

			if (cobjvalue != null) {
				lSCode = cobjvalue.toString();
			}

			//Pull the description for the code attribute from property reader

			if (entity != null && lSCode != null) {
				String rootProperty = "NameValuePairs";
				String propertyName = rootProperty + "." + entity + ".entry";
				pr = PropertyReaderFactory.getPropertyReader();
				IPropertyCollection ipc = pr.getCollection(propertyName);

				while (ipc.hasNext()) {
					ipc.next();
					if (ipc.getProperty("code").equalsIgnoreCase(lSCode)) {
						lSDescription = ipc.getProperty("value");
						break;
					} else if (ipc.getPropertyBoolean("default", false)) {
						defValue = ipc.getProperty("value");
					}
				}
			}
			if (lSDescription.equals("")) {
				lSDescription = defValue;
			}
			cobjout = pageContext.getOut();
			if(width == null) 
				width = "250";
			
			if(size == null) 
				size = "75";	
			
			if (readonly == null) {
				readonly = "true";
			}
			if (textbox == null) {
				textbox = "true";
			}
			if (readonly.equalsIgnoreCase("true")
					&& textbox.equalsIgnoreCase("true")) {
				cobjout
						.print("<input style=\"width: "+width+" px;\" maxlength=\""+size+"\" value=\""
								+ lSDescription + "\" readonly >");

			} else if (readonly.equalsIgnoreCase("false")
					&& textbox.equalsIgnoreCase("true")) {
				cobjout
						.print("<input style=\"width: "+width+" px;background-color: cyan;\" maxlength=\""+size+"\" value=\""
								+ lSDescription + "\" >");
			} else {
				cobjout.print(lSDescription);
			}

		} catch (Exception e) {
			log.warn(e);
		}
		if (lSDescription.equals("")) {
			log
					.debug("Property value in Description Custom Tag: "+cobjvalue);
		}
		return Tag.SKIP_BODY;
	}

	public void release() {
		super.release();
		entity = null;

	}
}