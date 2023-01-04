/*
 * Created on Mar 9, 2005
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

public interface IPropertyReader
{
	public void clearProperties() throws PASSystemException;
	public void init() throws PASSystemException;
	public void addFile(String fileName) throws PASSystemException;

	public String getProperty( String propertyName) throws PASSystemException;
	public String getProperty( String propertyName, String defaultValue) 
							throws PASSystemException;

	public int getPropertyInt( String propertyName) 
							throws PASSystemException;
	public int getPropertyInt( String propertyName, int defaultValue) 
							throws PASSystemException;

	public boolean getPropertyBoolean( String propertyName) 
							throws PASSystemException;
	public boolean getPropertyBoolean( String propertyName, boolean fDefaultValue)
							throws PASSystemException;

	public String[] getPropertyArray( String propertyName) 
							throws PASSystemException ;
	public String[] getPropertyArray( String propertyName, String[] defaultArray)
							throws PASSystemException;;
	
	public IPropertyCollection getCollection(String propertyName) throws PASSystemException;
	
	public boolean hasNext() throws PASSystemException;
	public void next() throws PASSystemException;

}

