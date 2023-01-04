/*
 * Created on Mar 8, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.valueObject.User;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.constants.IAppConstants;
import com.pas.exception.PASSystemException;

/**
 * @author sganapathy
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SessionCacheManager implements ICacheManager
{
	private static ICacheManager cacheMgrInstance = new SessionCacheManager();

	private Logger log = LogManager.getLogger(getClass());
	
	private SessionCacheManager ()
	{
	}

	public static ICacheManager getInstance()
	{
		return cacheMgrInstance;
	}

	@SuppressWarnings("unchecked")
	public Object getObject(String name, HttpSession session) throws PASSystemException
	{	
		Map cacheMap = (Map) session.getAttribute(IAppConstants.CACHE_MAP);
		if ( cacheMap == null )
			return null;
		return cacheMap.get(name);
	}
	
	@SuppressWarnings("unchecked")
	public void setObject(String name, Object valObj, HttpSession session) throws PASSystemException
	{		
		Map<String, Object> cacheMap = (Map<String, Object>) session.getAttribute(IAppConstants.CACHE_MAP);
		if ( cacheMap == null ){
			cacheMap = Collections.synchronizedMap(new HashMap<String, Object>());
			session.setAttribute(IAppConstants.CACHE_MAP, cacheMap);
		}
		cacheMap.put(name, valObj);
	}
	@SuppressWarnings("unchecked")
	public void removeObject(String name, HttpSession session) throws PASSystemException
	{
		Map cacheMap = (Map) session.getAttribute(IAppConstants.CACHE_MAP);
		if ( cacheMap != null ){
			cacheMap.remove(name);
		}
	}
	public void clearCache(HttpSession session) throws PASSystemException
	{
		session.removeAttribute(IAppConstants.CACHE_MAP);
	}

	@SuppressWarnings("unchecked")
	public void clearCache(String prefix, HttpSession session) throws PASSystemException
	{
		Map cacheMap = (Map) session.getAttribute(IAppConstants.CACHE_MAP);
		if ( cacheMap != null ){
			if( prefix == null){
				clearCache(session);
				return;
			}
			Set keyset = cacheMap.keySet();
			Iterator itr = keyset.iterator();
			ArrayList<String> removeList = new ArrayList<String>();
			while (itr.hasNext()){
				String key = (String)itr.next();
				if(key.startsWith(prefix)){
					log.debug("remvoing Key :: " + key);
					removeList.add(key);	
				}
			}
			if( removeList.size()>0){
				itr = removeList.iterator();
				while (itr.hasNext()){
					String key = (String)itr.next();
					cacheMap.remove(key);
				}
			}
		}
	}
	
	public Integer getSeasonID(HttpSession session) throws PASSystemException
	{
		return (Integer) session.getAttribute(IAppConstants.SEASONID);
	}
	
	public void setSeasonID(HttpSession session, Integer seasonID) throws PASSystemException
	{
		session.setAttribute(IAppConstants.SEASONID,seasonID);
	}
	
	public String getSeasonYear(HttpSession session) throws PASSystemException
	{
		return (String) session.getAttribute(IAppConstants.SEASONYEAR);
	}
	
	public void setSeasonYear(HttpSession session, String seasonYear) throws PASSystemException
	{
		session.setAttribute(IAppConstants.SEASONYEAR,seasonYear);
	}
	
	public User getUser(HttpSession session) throws PASSystemException
	{
		return (User) session.getAttribute(IAppConstants.APPUSER);
	}
	
	public void setUser(HttpSession session, User user) throws PASSystemException
	{
		session.setAttribute(IAppConstants.APPUSER,user);
	}
		
	public boolean getGoToDBInd(HttpSession session) throws PASSystemException
	{
		return ((Boolean)session.getAttribute(INFLAppConstants.GOTODBIND)).booleanValue();
	}
	
	public void setGoToDBInd(HttpSession session, boolean goToDBInd)
			throws PASSystemException
	{
		session.setAttribute(INFLAppConstants.GOTODBIND,goToDBInd);		
	}
	
}
