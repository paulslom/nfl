/*
 * Created on Mar 8, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.nfl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.pas.business.BusinessComposite;
import com.pas.cache.CacheManagerFactory;
import com.pas.cache.ICacheManager;
import com.pas.constants.IAppConstants;
import com.pas.valueObject.User;
import com.pas.nfl.actionform.NFLBaseActionForm;
import com.pas.nfl.business.INFLBusiness;
import com.pas.nfl.business.NFLBusinessFactory;
import com.pas.exception.BusinessException;
import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;
import com.pas.action.BaseAction;
import com.pas.util.IPropertyReader;
import com.pas.util.PropertyReaderFactory;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class NFLBaseAction extends BaseAction
{
	
	protected static IPropertyReader pr = PropertyReaderFactory.getPropertyReader();
	
     protected ICacheManager getCacheManager(HttpSession session)
     {
     	return CacheManagerFactory.getCacheManager();
     }
     
	protected User getUser(HttpSession session) throws PASSystemException
	{
		ICacheManager cacheMgr = getCacheManager(session);
     	return (cacheMgr.getUser(session));
	}	
	
	/**
	 * Provides handle to Business Facade
	 *
	 * @param name  Business Facade name
	 *
	 * @return  handle to requested business facade
	 */
	public final INFLBusiness getBusinessInstance(String name) throws BusinessException, PASSystemException 
	{
		return NFLBusinessFactory.getBusinessInstance(name);
	}

	/**
	 * Provides a helper method to call the business service for inquire
	 *
	 * @param name  IValueObject
	 * @param name  IBusiness
	 * @param name  HTTPServletRequest
	 *
	 * @return  BusinessComposite 
	 */
	public final BusinessComposite inquireBusiness(String businessServiceName, Object valObj,
		HttpServletRequest req) throws DAOException, BusinessException, PASSystemException
	{
		String methodName = "inquireBusiness :: ";
		log.debug(methodName + "In");
		BusinessComposite bc = null;
		
		INFLBusiness businessService = getBusinessInstance(businessServiceName);
				
		bc = businessService.inquire(valObj);
		log.debug(methodName + "Out");
		
		return bc;
	}

	/**
	 * Provides a helper method to call the business service for add
	 *
	 * @param name  IValueObject
	 * @param name  IBusiness
	 * @param name  HTTPServletRequest
	 *
	 * @return  BusinessComposite 
	 */
	public final BusinessComposite addBusiness(String businessServiceName, Object valObj,
		HttpServletRequest req) throws DAOException, BusinessException, PASSystemException
	{
		String methodName = "addBusiness :: ";
		log.debug(methodName + "In");

		BusinessComposite bc = null;
		INFLBusiness businessService = getBusinessInstance(businessServiceName);
		
		bc = businessService.add(valObj);
		log.debug(methodName + "Out");
		
		return bc;
	}
	
	/**
	 * Provides a helper method to call the business service for update
	 *
	 * @param name  IValueObject
	 * @param name  IBusiness
	 * @param name  HTTPServletRequest
	 *
	 * @return  BusinessComposite 
	 */
	public final BusinessComposite updateBusiness(String businessServiceName, Object valObj,
		HttpServletRequest req) throws DAOException, BusinessException, PASSystemException
	{
		String methodName = "updateBusiness :: ";
		log.debug(methodName + "In");

		BusinessComposite bc = null;
		INFLBusiness businessService = getBusinessInstance(businessServiceName);
				
		bc = businessService.update(valObj);
		log.debug(methodName + "Out");
		
		return bc;
	}

	/**
	 * Provides a helper method to call the business service for delete
	 *
	 * @param name  IValueObject
	 * @param name  IBusiness
	 * @param name  HTTPServletRequest
	 *
	 * @return  BusinessComposite 
	 */
	public final BusinessComposite deleteBusiness(String businessServiceName, Object valObj,
		HttpServletRequest req) throws DAOException, BusinessException, PASSystemException
	{
		String methodName = "deleteBusiness :: ";
		log.debug(methodName + "In");

		BusinessComposite bc = null;
		INFLBusiness businessService = getBusinessInstance(businessServiceName);
				
		bc = businessService.delete(valObj);
		log.debug(methodName + "Out");
		
		return bc;
	}
	
	/**
	 * Overriden method of the base class and is used to update the form bean 
	 * with GeneralMessage and user information.
	 */
	protected void initializeData(ActionMapping mapping, ActionForm form, HttpServletRequest req)
	    throws PASSystemException
	{
		if (form != null)
		{
			NFLBaseActionForm appForm = (NFLBaseActionForm)form;
			appForm.setUser(getUser(req.getSession()));
		}
						
		String actionName  = getActionName(mapping);
		log.debug("Input Form name is ...." + mapping.getName());
		
		req.setAttribute(IAppConstants.ACTION_NAME, actionName);
		req.setAttribute(IAppConstants.ACTION_FORM_NAME, mapping.getName());
	
	}
	
	//This method removes '/' from the path and also '.do' from the path
	
	protected String getActionName(ActionMapping mapping )
	{
		String path = mapping.getPath();
		return path.substring(1);
	}

	protected void 	addApplicationDetailToError(HttpServletRequest req,
				 ActionMapping mapping,	ActionMessages messages) throws PASSystemException
	{
		String actionName = getActionName(mapping);
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("exception.actionname", actionName));		
	}
	
}
