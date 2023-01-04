/*
 * Created on Apr 7, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.util;

import com.pas.exception.PASSystemException;

/**
 * @author SGanapathy
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IPropertyCollection extends IPropertyReader {
	
	public String getPropertyNodeName(String propertyName) throws PASSystemException;
	

}
