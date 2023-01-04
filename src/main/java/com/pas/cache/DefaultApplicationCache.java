package com.pas.cache;

/**
 * Insert the type's description here. Creation date: (9/19/00 5:19:07 PM)
 * 
 * @author: Administrator
 */
public final class DefaultApplicationCache extends ApplicationCache
{
	/**
	 * DefaultResponseCache constructor comment.
	 */
	public DefaultApplicationCache()
	{
		super();
	}

	public Object getRequestObject(String appName, String appState)
	{
		return null;
	}

	/**
	 * getCacheObject method comment.
	 */
	public Object getResponseObject(String appName, String appEvent)
	{
		return null;
	}

	/**
	 * haveErrors method comment.
	 */
	public boolean hasErrorExceptions(java.lang.String appName,
			java.lang.String appState)
	{
		return false;
	}

	/**
	 * haveErrors method comment.
	 */
	public boolean haveErrors(java.lang.String appName,
			java.lang.String appState)
	{
		return false;
	}

	public void setRequestObject(String appName, String appState, Object request)
	{
	}

	/**
	 * setResponseObject method comment.
	 */
	public void setResponseObject(String appName, String appEvent,
			Object response)
	{
	}
}
