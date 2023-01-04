/*
 * Created on Mar 7, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.actionform; 

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.validator.ValidatorForm;

/**
 * @author SGanapathy
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BaseActionForm extends ValidatorForm
{
	private String operation;

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{
		return super.validate(mapping, request);
	}

	public String getOperation()
	{
		return operation;
	}
}
