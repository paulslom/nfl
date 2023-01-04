/**
 * Title: CSWBaseTag Project: Client Service Workbench Description: This
 * CustomTag class is the base class for all custom tags
 *  Field Copyright: Copyright
 * (c) 2005 Company: Lincoln Life
 * 
 * @author CGI/Mala K
 * @version 1.0
 */

package com.pas.tags;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.FormTag;

/**
 * @author Mala K To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class BaseTag extends TagSupport {
	protected String name;

	protected String value;

	protected String property;

	protected String scope;

	protected String textbox = "true";

	protected String readonly = "true";

	protected String size;

	protected String width;

	protected String id;

	protected Logger log = LogManager.getLogger(this.getClass());

	protected String onkeyup;

	protected String defaultValue;

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the property.
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property
	 *            The property to set.
	 */
	public void setProperty(String property) {
		this.property = property;
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
	 * @return Returns the scope.
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *            The scope to set.
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return Returns the size.
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size
	 *            The size to set.
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return Returns the textbox.
	 */
	public String getTextbox() {
		return textbox;
	}

	/**
	 * @param textbox
	 *            The textbox to set.
	 */
	public void setTextbox(String textbox) {
		this.textbox = textbox;
	}

	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return Returns the width.
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            The width to set.
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	public Object getPropertyvalue(String name, String property, String scope)
	{
		Object cobjvalue = null;
		FormTag formTag = null;
		TagUtils tagUtils = TagUtils.getInstance();
		
		try
		{
			if (scope == null)
			{
				scope = "request";
			}

			if (scope.equalsIgnoreCase("null"))
			{
				cobjvalue = tagUtils.lookup(pageContext, getName(), property, null);
			}
			else
			{
				if (name == null)
				{
					formTag = (FormTag) pageContext.getAttribute(Constants.FORM_KEY, PageContext.REQUEST_SCOPE);
					if (formTag != null)
						name = formTag.getBeanName();
				}
				//	get data from bean
				if (name != null)
				{
					cobjvalue = tagUtils.lookup(pageContext, name, property, scope);
				}
			}
		}
		catch (IllegalArgumentException ioe)
		{
		}
		catch (javax.servlet.jsp.JspException ioe)
		{
			try
			{
				if (formTag != null)
				{
					name = formTag.getBeanName();
					if (name != null)
					{
						cobjvalue = tagUtils.lookup(pageContext, name, property, "session");
					}
				}
				else
					log.warn(ioe);
			}
			catch (Exception e)
			{
				log.warn(e);
			}
		}
		catch (Exception ioe)
		{
			log.warn(ioe);
		}
		return cobjvalue;
	}

	public void release() {
		super.release();
		value = null;
		property = null;
		name = null;
		scope = null;
		onkeyup = null;
		textbox = null;
		readonly = null;
		size = null;
		width = null;
		defaultValue = null;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param string
	 */
	public void setId(String string) {
		id = string;
	}

	/**
	 * @return
	 */
	public String getOnkeyup() {
		return onkeyup;
	}

	/**
	 * @param string
	 */
	public void setOnkeyup(String string) {
		onkeyup = string;
	}

	/**
	 * @return
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param string
	 */
	public void setDefaultValue(String string) {
		defaultValue = string;
	}

}