/*
 * Created on Mar 8, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.cache;

import javax.servlet.http.HttpSession;

import com.pas.valueObject.User;
import com.pas.exception.PASSystemException;

/**
 * @author sganapathy
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface ICacheManager
{
	public Object getObject(String name, HttpSession session) throws PASSystemException;
	public void setObject(String name, Object valObj, HttpSession session) throws PASSystemException;
	public void removeObject(String name, HttpSession session) throws PASSystemException;
	public void clearCache(HttpSession session) throws PASSystemException;
	public void clearCache(String name,HttpSession session) throws PASSystemException;
	public User getUser(HttpSession session) throws PASSystemException;
	public void setUser(HttpSession session, User user) throws PASSystemException;
	public void setGoToDBInd(HttpSession session, boolean goToDBInd) throws PASSystemException;
	public boolean getGoToDBInd(HttpSession session) throws PASSystemException;
	public Integer getSeasonID(HttpSession session) throws PASSystemException;
	public void setSeasonID(HttpSession session, Integer seasonID) throws PASSystemException;
	public String getSeasonYear(HttpSession session) throws PASSystemException;
	public void setSeasonYear(HttpSession session, String seasonYear) throws PASSystemException;

}
