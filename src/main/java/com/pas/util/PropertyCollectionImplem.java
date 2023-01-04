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
public class PropertyCollectionImplem implements IPropertyCollection {
	
	private int index = -1;
	private String rootProperty;
	private IPropertyReader pr;
	static IPropertyCollection getPropertyCollection( IPropertyReader propertyReader, 
													String property)
	{
		IPropertyCollection prc = new PropertyCollectionImplem(propertyReader, property);
		return prc; 
	}
	private PropertyCollectionImplem(IPropertyReader propertyReader, 
									 String property)
	{
		pr = propertyReader;
		rootProperty = property;
	}
	
	public void clearProperties()throws PASSystemException {
		throw new PASSystemException ("method not supported");
	}
	public void init() throws PASSystemException {
		throw new PASSystemException ("method not supported");
	}
	public void addFile(String fileName) throws PASSystemException {
		throw new PASSystemException ("method not supported");
	}

	public String getProperty( String propertyName) throws PASSystemException{
		return pr.getProperty(buildProperty(propertyName));
	}
	public String getProperty( String propertyName, String defaultValue) throws PASSystemException {
		return pr.getProperty(buildProperty(propertyName), defaultValue);
		
	}

	public int getPropertyInt( String propertyName) throws PASSystemException {
		return pr.getPropertyInt(buildProperty(propertyName));
	}
	public int getPropertyInt( String propertyName, int defaultValue) throws PASSystemException{
		return pr.getPropertyInt(buildProperty(propertyName), defaultValue);
	}

	public boolean getPropertyBoolean( String propertyName) throws PASSystemException {
		return pr.getPropertyBoolean(buildProperty(propertyName));
		
	}
	public boolean getPropertyBoolean( String propertyName, boolean fDefaultValue)
									throws PASSystemException {
		return pr.getPropertyBoolean(buildProperty(propertyName), fDefaultValue);
	}

	public String[] getPropertyArray( String propertyName) throws PASSystemException{
		return pr.getPropertyArray(buildProperty(propertyName));
	}
	public String[] getPropertyArray( String propertyName, String[] defaultArray) 
					throws PASSystemException {
		return pr.getPropertyArray(buildProperty(propertyName), defaultArray);
	}
	
	public IPropertyCollection getCollection(String propertyName) throws PASSystemException{
		String subCollectionProperty = buildProperty(propertyName);
		return pr.getCollection(subCollectionProperty);
	}
	
	public boolean hasNext() throws PASSystemException{
		String DEFAULT_VALUE = "$D%F";
		String nextProperty = buildProperty("", index + 1);
		//PropertyReaderFactory prf = (PropertyReaderFactory)pr;
		//return prf.hasNext(nextProperty);
		String nextValue = pr.getProperty(nextProperty, DEFAULT_VALUE);
		if ( nextValue != null && nextValue.equals(DEFAULT_VALUE))
			return false;
		return true;
		
	}
	
	public void next() throws PASSystemException{
		index++;
	}
	
	private String buildProperty(String propertyName)throws PASSystemException {
		return buildProperty(propertyName, index);
	}
	
	private String buildProperty(String propertyName, int currentIndex)
			throws PASSystemException {
		if (currentIndex == -1){
			throw new PASSystemException("Property Reader not intialized for traversing");
		}
		
		StringBuffer sb = new StringBuffer(rootProperty);
		sb.append('[');
		sb.append(currentIndex);
		sb.append(']');
		if( propertyName != null && !propertyName.equals("")){
			sb.append('.');
			sb.append(propertyName);
		}
		//System.out.println("build Property String is " + sb.toString());
		return sb.toString();
		
	}
	
	public String getPropertyNodeName(String propertyName) throws PASSystemException {
		return buildProperty(propertyName);
	}
}
