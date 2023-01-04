/*
 * Created on May 23, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.tags;

import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;


/**
 * @author partha
 *
 * Appends red asterisk to fields which require input/correction
 */
public class ErrorFieldTag extends BaseTag {
	
	private static final String ERROR ="org.apache.struts.action.ERROR";
	private static final String ASTERISK ="<font color=\"red\">*</font>";
	javax.servlet.jsp.JspWriter cobjout;
		
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException
	{
		cobjout = pageContext.getOut();
		
		TagUtils tagUtils = TagUtils.getInstance();
		
		try {
			
			ActionMessages ae = tagUtils.getActionMessages(pageContext,ERROR);
			boolean exists = false;
			if(ae!=null){				
				Iterator<String> iterator = ae.properties();				
				while(iterator.hasNext()){
					String report = iterator.next();
					//String msg = RequestUtils.message(pageContext, null, null, report.getKey(), report.getValues());					
					if(report.equals(property) || report.startsWith(property)){
						exists = true;
						log.debug("error field!!");
						break;
					}
					
				}				
				if(exists)
					cobjout.write(ASTERISK);
			}			
		} catch (Exception ioe) {
			log.warn(ioe);
		}
	
		return EVAL_PAGE;
	}

	public void release() {
		super.release();		
	}
}
