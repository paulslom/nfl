/**
 * Title: DropdownTag Project: Client Service Workbench Description: This
 * CustomTag class contains logic to format DropDown Field Copyright: Copyright
 * (c) 2005 Company: Lincoln Life
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
import com.pas.util.PASUtil;
import com.pas.util.PropertyReaderFactory;

public class DropdownTag extends BaseTag {
	private Logger log = LogManager.getLogger(this.getClass()); //log4j for Logging

	private String collection;

	private IPropertyReader cobjPropertyreader;

	javax.servlet.jsp.JspWriter cobjout;

	private String onchange = null;

	private String onclick = null;
	
	private String id = null;
	
	private String readonly = "false";
	
	private String tabindex = null;

	Object cobjvalue = null;

	/**
	 * Build and format values for dropdown box
	 * 
	 * @throws javax.servlet.jsp.JspException
	 */
	public int doStartTag() throws javax.servlet.jsp.JspException {
		String lSCode = null;
		String lScodedescription;
		String lSdefaultvalue;
		try {
			cobjvalue = getPropertyvalue(name, property, scope);

			if (cobjvalue != null) {
				lSCode = cobjvalue.toString();
			}

			StringBuffer lobjbuffer = new StringBuffer("");
			cobjPropertyreader = PropertyReaderFactory.getPropertyReader();

			//Pull the description for the code attribute from property reader
			// to build the dropdown values IPropertyReader
			if (collection != null) {
				String rootProperty = "NameValuePairs";
				String propertyName = rootProperty + "." + collection
						+ ".entry";
				IPropertyCollection lobjprpcollec = cobjPropertyreader
						.getCollection(propertyName);
				lobjbuffer.append(" <select name=\"");
				if(id !=null)
				lobjbuffer.append(id);
				else 
				lobjbuffer.append(property);
				lobjbuffer.append("\"");
				if (onclick != null) {
					lobjbuffer.append(" onclick=\"");
					lobjbuffer.append(getOnclick());
					lobjbuffer.append("\"");
				}
				if (onchange != null) {
					lobjbuffer.append(" onchange=\"");
					lobjbuffer.append(getOnchange());
					lobjbuffer.append("\"");
				}
				if (tabindex != null) {
					if (PASUtil.isValidNumeric(tabindex))
					   lobjbuffer.append("tabindex=\"" + tabindex + "\" ");
				}					
				if (readonly != null && readonly.equals("true")) {
					lobjbuffer.append("  disabled");
				} else{
					lobjbuffer.append(" style=\"background-color: cyan;\" ");
				}
				lobjbuffer.append(" >\n");
				while (lobjprpcollec.hasNext()) {
					lobjprpcollec.next();
					String lsCodevalue = lobjprpcollec.getProperty("code");
					lScodedescription = lobjprpcollec.getProperty("value");
					lSdefaultvalue = lobjprpcollec.getProperty("default");
					if (lSCode != null && lSCode.length() > 0) {
						if (lSCode.equalsIgnoreCase(lsCodevalue)) {
							lobjbuffer.append(" <option value=\"");
							lobjbuffer.append(lsCodevalue);
							lobjbuffer.append("\"selected=\"selected\">");
							lobjbuffer.append(lScodedescription);
							lobjbuffer.append("</option>\n");
						} else {
							lobjbuffer.append(" <option value=\"");
							lobjbuffer.append(lsCodevalue);
							lobjbuffer.append("\">");
							lobjbuffer.append(lScodedescription);
							lobjbuffer.append("</option>\n");
						}
					} else if (lSdefaultvalue != null
							&& lSdefaultvalue.equalsIgnoreCase("true")) {
						lobjbuffer.append(" <option value=\"");
						lobjbuffer.append(lsCodevalue);
						lobjbuffer.append("\"selected=\"selected\">");
						lobjbuffer.append(lScodedescription);
						lobjbuffer.append("</option>\n");
					} else {
						lobjbuffer.append(" <option value=\"");
						lobjbuffer.append(lsCodevalue);
						lobjbuffer.append("\">");
						lobjbuffer.append(lScodedescription);
						lobjbuffer.append("</option>\n");
					}
				}
				lobjbuffer.append(" </select>\n");
			}

			cobjout = pageContext.getOut();
			cobjout.print(lobjbuffer.toString());
			if (lobjbuffer.toString().equals("")) {
				log.debug("Property value in Dropdown Custom Tag: "+cobjvalue);
			}

		} catch (Exception e) {
			log.warn(e);
		}
		return Tag.SKIP_BODY;
	}

	/**
	 * @return String
	 */
	public String getCollection() {
		return collection;
	}

	/**
	 * Sets the collection.
	 * 
	 * @param collection
	 *            The collection to set
	 */
	public void setCollection(String collection) {
		this.collection = collection;
	}

	public void release() {
		super.release();
		collection = null;
		onchange = null;
		onclick = null;
		id = null;
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
	 * @return Returns the onclick.
	 */
	public String getOnclick() {
		return onclick;
	}

	/**
	 * @param onclick
	 *            The onclick to set.
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return
	 */
	public String getReadonly() {
		return readonly;
	}

	/**
	 * @param string
	 */
	public void setReadonly(String string) {
		readonly = string;
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
}