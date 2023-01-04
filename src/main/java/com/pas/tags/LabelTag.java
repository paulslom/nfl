package com.pas.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionMessages;

/**
 * Title: LabelTag Project: Claims Replacement System Description: To Display
 * Labels in JSP (For Errors) Copyright: Copyright (c) 2003 Company: Lincoln
 * Life
 * 
 * @author pkoppula
 * @version
 */

public class LabelTag extends BaseTag
{
	private String showerror;
	
	private boolean writeSwitch;
	
	private ArrayList<String> propertylist;
	
	private Logger log = LogManager.getLogger(this.getClass()); //log4j for Logging

	public void setShowerror(String string)
	{
		this.showerror = string;
	}

	public void setPropertylist(ArrayList<String> propertylist)
	{
		this.propertylist = propertylist;
	}
	
	public int doStartTag() throws JspException
	{
		try
		{
			//now set properties to defaults if they were not set by the user.
			showerror = showerror != null ? showerror : "false"; //safety
		    name = name !=null ? name: "";
		    value = value !=null ? value: "";
		    
			if (showerror.equals("false"))
			{
				pcNoError();				
			}
			else //means we are showing errors
			{
				if (propertylist != null) //propertylist, if filled in, overrides individual property.
					processPropertyErrorList();
				else
					if (property != null)
					{
						writeSwitch = true;
						processIndividualPropertyError(property);
					}
					else //showing errors, but neither property nor propertylist defined.  Strange...
						pcNoError();
			}	
		}
		catch (IOException ioe)
		{
			throw new JspException("Error in LabelTag : IOException	while writing to client");
		}
		catch (Exception e)
		{
			log.warn(e);
		}

		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException
	{
		return EVAL_PAGE;
	}

	@SuppressWarnings("unchecked")
	private void processPropertyErrorList() throws IOException
	{
		writeSwitch = false;
		String nextProperty = "";
		Iterator<String> PropList = propertylist.iterator();
		while ((PropList.hasNext()) && !writeSwitch)
		{
			nextProperty = (String)PropList.next();
			if (!PropList.hasNext())
				writeSwitch = true;
			processIndividualPropertyError(nextProperty);
		}
	}
	
	private void processIndividualPropertyError(String propertyName) throws IOException
	{
		if (this.isAnError(propertyName))
		{
			if (value != null)
				pageContext.getOut().print("<LABEL CLASS=\"errorMessage\">" + "*" + value + "</LABEL>");
			else
				pageContext.getOut().print("<LABEL CLASS=\"errorMessage\">" + "* </LABEL>");
			
			writeSwitch = true;
		}
		else //no error on this property
		{
			if (writeSwitch)
			   if (value != null)
				 pcNoError();		
		}	
	}
	
	@SuppressWarnings("unchecked")
	private boolean isAnError(String property)
	{
		final String ERROR = "org.apache.struts.action.ERROR";
		ServletRequest request = pageContext.getRequest();
		Object obj = request.getAttribute(ERROR);
	
		if (obj != null)
		{
			ActionMessages am = (ActionMessages) obj;
			for (Iterator<String> i = am.properties(); i.hasNext();)
			{
				String propertyname = i.next();
				if (propertyname.equals(property)
				|| propertyname.startsWith(property))
					return true; //matched
			}
			return false; //no errors on page.
		
		}
		
		return false;
		
	}
	
	private void pcNoError() throws IOException
	{
		if (name.length()== 0)
		   if (value.length() == 0)
		   	   pageContext.getOut().print("<LABEL></LABEL>");
		   else
		   	   pageContext.getOut().print("<LABEL>" + value + "</LABEL>");
		else
		   pageContext.getOut().print("<LABEL ID=\"" + name + "\" FOR=\"" + name + "\">" + value + "</LABEL>");
	}
	
	public void release()
	{
		super.release();
		showerror = null;
	}
		
}