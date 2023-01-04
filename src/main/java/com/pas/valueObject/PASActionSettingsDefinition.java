/*
 * Created on May 12, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.valueObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.valueObject.IValueObject;

/**
 * @author SGanapathy
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PASActionSettingsDefinition implements IValueObject
{
	private String actionName;
	private String navigationPath;
	private String businessServiceName;
	private int messageCount = 0; 
			
	private static String CLEAR_CACHE_SETTINGS="ClearCache";
	// Note following hash Map message .
	// Each element in the ArrayList corresponds to an array list of MessageMap.
	private HashMap<String, ArrayList<String>> mapDefinitions = new HashMap<String, ArrayList<String>>();
	
	private Logger log = LogManager.getLogger(this.getClass()); //log4j for Logging
		
	public void setClearCacheProperty(int messageId, int messageType, int operation, String propertyName)
	{
		String msgMapKey = buildKey(CLEAR_CACHE_SETTINGS, messageId, messageType, operation);
		ArrayList<String> clearCacheProps;
		
		if( mapDefinitions.containsKey(msgMapKey))
		{
			clearCacheProps=mapDefinitions.get(msgMapKey);
		}
		else
		{
			clearCacheProps= new ArrayList<String>();
			mapDefinitions.put(msgMapKey, clearCacheProps);
		}
		clearCacheProps.add(propertyName);
	}
	
	private String buildKey(String keyName, int messageId, int messageType, int operation)
	{
		StringBuffer sb = new StringBuffer(keyName);
	
		if(messageId > 0)
			sb.append(messageId);
		
		if(messageType > 0)
			sb.append(messageType);

		if(operation > 0)
			sb.append(operation);
		
		return sb.toString();
	}

	public String getBusinessServiceName(){
		return businessServiceName;
	}
	public String getActionName() {
		return actionName;
	}
	public String getNavigationPath() {
		return navigationPath;
	}
	public void setActionName(String name) {
		actionName = name;
	}
	public void setNavigationPath(String navPath) {
		navigationPath = navPath;
	}
	public void setBusinessServiceName(String businessServiceName) {
		this.businessServiceName = businessServiceName;
	}	
	public int getMessageCount() {
		return messageCount;
	}	
	public void setMessageCount(int msgCount) {
		messageCount = msgCount;
	}
	
	public void printSettings()
	{
		if(log.isDebugEnabled())
		{
			Set<String> keySet = mapDefinitions.keySet();
			Iterator<String> itr = keySet.iterator();
			log.debug("++++++++++++++++++");
			log.debug("Action Name = " + actionName);
			log.debug("Action forward = "+ navigationPath);
			log.debug("Business Service Name = " + businessServiceName);
			
			for( ; itr.hasNext(); )
			{
				String key = (String)itr.next();
				Object value = mapDefinitions.get(key);
				
				log.debug( "Key = " + key + ", value = " + value);
			}
		}
	}


}
