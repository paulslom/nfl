/*
 * Created on Mar 9, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.util; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

import org.xml.sax.InputSource;

import com.pas.exception.PASSystemException;

/**
 * @author SGanapathy
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PropertyReaderFactory implements IPropertyReader {

	private HashMap<String, String> propertiesMap = null;
	private ArrayList<String> fileList;
	private static IPropertyReader pr=null;

	private Logger log = LogManager.getLogger(this.getClass()); 

	public static IPropertyReader getPropertyReader()
	{
		if ( pr == null)
			pr = getPropertyReader(null);
		return pr;
	}

	public static IPropertyReader getPropertyReader(String fileName)
	{
		return new PropertyReaderFactory(fileName);
	}

	private PropertyReaderFactory(String fileName)
	{
		fileList = new ArrayList<String>();
		if ( fileName != null )
		  fileList.add(fileName);
		propertiesMap = new HashMap<String, String>();
	}

	/* clear the properties Hash Map and file name list */
	public void clearProperties() throws PASSystemException
	{
		fileList.clear();
	}

	//should pass the file name without the full path
	public void addFile(String fileName) throws PASSystemException
	{
		log.debug("adding file: " + fileName);
		fileList.add(fileName);
	}
	
	public void init() throws PASSystemException
	{
		log.debug("entering init method..");
		
		try
		{
		synchronized(propertiesMap)
		{
		   propertiesMap.clear();
		   for ( Iterator<String> itr = fileList.iterator(); itr.hasNext() ; )
		   {		   	
				String fileName = itr.next();
				log.debug("processing file: " + fileName);
				parseInputSource(fileName);
		   }
		  }
		}catch(Exception e){
			throw new PASSystemException(e);
			//e.printStackTrace();
	   }
		
	   log.debug("exiting init method..");
	}

	private void parseInputSource(String fileName) throws PASSystemException
	{
		log.debug("entering parseInputSource method..");
		
		try
		{
		  SaxReader sr = new SaxReader();
		  InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName); 
		  log.debug("inputStream built for " + fileName);
		  InputSource inputSource = new InputSource(inputStream);
		  log.debug("inputSource built for " + fileName);
		  HashMap<String, String> prMap = sr.parseInputSource(inputSource);
		  inputStream.close();
		  String APPEND_TO_NODE = "Property[0].AppendToNode";
		  boolean reParse = false;
		  
		  for (int i = 0; prMap.containsKey(APPEND_TO_NODE+ '['+ i + ']'); i++)
		  {
		  	log.debug("HashMap prMap does contain AppendToNode");
		  	
			reParse=true;
			String nodeToAppend = (String)prMap.get(APPEND_TO_NODE+ '['+ i + ']');
			log.debug("before Translation ..." + nodeToAppend);
			nodeToAppend = translatePropertyURI(nodeToAppend);
			log.debug("after Translation ..." + nodeToAppend);
			StringTokenizer st = new StringTokenizer(nodeToAppend, ".");
			String nextToken ="";
			
			while( st.hasMoreTokens())
			{
				nextToken = nextToken + st.nextToken();
				log.debug("Removing Node " + nextToken);
				propertiesMap.remove(nextToken);
				nextToken = nextToken +'.';
			}
		  
		  }
		  
		  if (reParse)
		  {
		  	inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName); 
			inputSource = new InputSource(inputStream);
			sr.parseInputSource(inputSource, propertiesMap);
			inputStream.close();
			log.debug("returned from re-Parsing");
		  }
		  else
		  {
			  Set<String> keySet = prMap.keySet();
			  Iterator<String> itr = keySet.iterator();
	
			  for( ; itr.hasNext(); )
			  {
				  String key = itr.next();
				  String value = prMap.get(key);
				  propertiesMap.put(key, value);
			  }
		  }
		  
		}
		catch(Exception e)
		{
			throw new PASSystemException(e);
		}
		
		log.debug("exiting parseInputSource method..");
		
	}

	private String translatePropertyURI (String xPath) {

		StringTokenizer st = new StringTokenizer(xPath, ".");
		StringBuffer parentURI = new StringBuffer("Property[0]");
		int count = st.countTokens();
		String nextToken;
		for (int i = 0; i < count ; i++) {
			nextToken = st.nextToken();
			int startIndex = nextToken.indexOf('[');

			parentURI.append(".");
			parentURI.append(nextToken);
			if( startIndex == -1 )
				parentURI.append("[0]"); // default index is 0

		}
		return parentURI.toString();
	}


	public String getProperty(String propertyName) throws PASSystemException
	{
		return (getPropertyValue(propertyName));
	}
	public String getProperty( String propertyName, String defaultValue) 
				throws PASSystemException
	{
		String propertyValue = getPropertyValue(propertyName);
		propertyValue = ( propertyValue == null)? defaultValue:propertyValue;
		return propertyValue;
	}

	public int getPropertyInt( String propertyName) throws PASSystemException
	{
		String propertyValue = getPropertyValue(propertyName);
		return (Integer.parseInt(propertyValue));
	}
	public int getPropertyInt( String propertyName, int defaultValue) 
								throws PASSystemException
	{
		String propertyValue = getPropertyValue(propertyName);
		int returnval = (propertyValue == null)? defaultValue : Integer.parseInt(propertyValue);
		return(returnval);
	}

	public boolean getPropertyBoolean(String propertyName) throws PASSystemException
	{
		String propertyValue = getPropertyValue(propertyName);
		return(Boolean.valueOf(propertyValue).booleanValue());
	}

	public boolean getPropertyBoolean( String propertyName, boolean fDefaultValue)
			throws PASSystemException
	{
		String propertyValue = getPropertyValue(propertyName);
		boolean fReturnVal = (propertyValue == null)? fDefaultValue : Boolean.valueOf(propertyValue).booleanValue();
		return(fReturnVal);
	}

	public String[] getPropertyArray( String propertyName) throws PASSystemException
	{
		return (getPropertyArray(propertyName, null));
	}


	public String[] getPropertyArray( String propertyName, String[] defaultArray)
		throws PASSystemException
	{

		String translatedName = translatePropertyURI(propertyName);
		StringBuffer sb = null;
		ArrayList<String> al = new ArrayList<String>();

		for ( int i = 0;  ; i++ )
		{
			sb = new StringBuffer(translatedName);
			sb.append(".value");
			sb.append('[');
			sb.append(i);
			sb.append(']');

			String value = propertiesMap.get(sb.toString());
			if ( value == null) break;
			al.add(value);
		}
		int arraySize = al.size();
			String [] retArray = (arraySize>0) ? new String[arraySize] : defaultArray;
		for ( int i = 0; i < arraySize; i++ )
			 retArray[i] = al.get(i);
		return(retArray);
	}


	private String getPropertyValue(String propertyName)
	{
		String translatedName = translatePropertyURI(propertyName);
		String value = propertiesMap.get(translatedName);
		
		return value;

	}
	
	// need to be implemented
	public IPropertyCollection getCollection(String propertyName) throws PASSystemException{
		
		IPropertyCollection ipc = PropertyCollectionImplem.getPropertyCollection(this, propertyName);
		return ipc;
	}
	
	public boolean hasNext() throws PASSystemException{
		throw new PASSystemException("funtion not supported, it is supported for collection only");
	}
	public void next() throws PASSystemException{
		throw new PASSystemException("function not supported, it is supported for collection only");
		
	}

}
