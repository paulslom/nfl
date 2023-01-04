package com.pas.cache;

/**
 * Insert the type's description here.
 * Creation date: (9/14/00 3:37:50 PM)
 * @author: Administrator
 */

public abstract class ApplicationCache
{

	/**
	 * ResultsCache constructor comment.
	 */
	public ApplicationCache()
	{
		super();
	}

	/**
	 * Insert the method's description here. Creation date: (9/14/00 3:43:01 PM)
	 * 
	 * @param response
	 *            com.pas.common.factories.responses.ResponseObject
	 * @param request
	 *            javax.servlet.http.HttpServletRequest
	 */
	public void execute(String appName, String appState, Object response)
	{
		setResponseObject(appName, appState, response);
	}

	/**
	 * Insert the method's description here. Creation date: (10/11/00 4:56:38
	 * PM)
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAppState(String appName)
	{
		return null;
	}

	/**
	 * Insert the method's description here. Creation date: (10/26/00 3:55:23
	 * PM)
	 * 
	 * @return com.pas.common.exceptions.PASSystemException
	 * @param appName
	 *            java.lang.String
	 * @param appState
	 *            java.lang.String
	 */
	public abstract Object getRequestObject(String appName, String appState);

	/**
	 * Insert the method's description here. Creation date: (9/20/00 11:14:40
	 * AM)
	 * 
	 * @return com.pas.common.factories.responses.ResponseObject
	 * @param appName
	 *            java.lang.String
	 * @param appEvent
	 *            java.lang.String
	 */
	public abstract Object getResponseObject(String appName, String appEvent);

	/**
	 * Insert the method's description here. Creation date: (11/7/00 8:32:49 AM)
	 * 
	 * @param appName
	 *            java.lang.String
	 * @param appState
	 *            java.lang.String
	 */
	public abstract boolean hasErrorExceptions(String appName, String appState);

	/**
	 * Insert the method's description here. Creation date: (11/7/00 8:32:49 AM)
	 * 
	 * @param appName
	 *            java.lang.String
	 * @param appState
	 *            java.lang.String
	 */
	public abstract boolean haveErrors(String appName, String appState);

	/**
	 * Insert the method's description here. Creation date: (10/11/00 4:56:38
	 * PM)
	 * 
	 * @param newAppState
	 *            java.lang.String
	 */
	public void setAppState(String appName, String newAppState)
	{
	}

	/**
	 * Insert the method's description here. Creation date: (10/27/00 10:17:10
	 * AM)
	 * 
	 * @param appName
	 *            java.lang.String
	 * @param appState
	 *            java.lang.String
	 * @param exceptions
	 *            java.util.Vector
	 */
	public abstract void setResponseObject(String appName, String appEvent,
			Object response);

	/**
	 * Insert the method's description here. Creation date: (10/12/00 4:25:55
	 * PM)
	 * 
	 * @return java.util.Hashtable
	 * @param appName
	 *            java.lang.String
	 * @param appState
	 *            java.lang.String
	 * @param request
	 *            java.util.Hashtable
	 */
	public abstract void setRequestObject(String appName, String appState,
			Object request);
}
