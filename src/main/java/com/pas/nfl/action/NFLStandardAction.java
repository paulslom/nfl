/*
 * Created on Apr 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.nfl.action; 

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.pas.business.BusinessComposite;
import com.pas.cache.CacheManagerFactory;
import com.pas.cache.ICacheManager;
import com.pas.constants.IAppConstants;
import com.pas.exception.BusinessException;
import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;
import com.pas.exception.PresentationException;
import com.pas.action.ActionComposite;
import com.pas.valueObject.PASActionSettingsDefinition;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.util.IPropertyCollection;

/**
 * @author SGanapathy
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NFLStandardAction extends NFLBaseAction {

	static protected HashMap<String, PASActionSettingsDefinition> actionSettings = new HashMap<String, PASActionSettingsDefinition>();

	protected String getMethodName(ActionMapping mapping, ActionForm form,
		      HttpServletRequest request, HttpServletResponse response, String parameter) throws Exception
	{
		if (request.getParameter(parameter) == null)
		   return IAppConstants.CANCEL;
		
		String methName = request.getParameter(parameter);		
		
		if (methName.equalsIgnoreCase(IAppConstants.LOGIN))
		   return IAppConstants.INQUIRE;
		else
		if (methName.equalsIgnoreCase(IAppConstants.ADD) )
		   return IAppConstants.ADD;
		else
		if (methName.equalsIgnoreCase(IAppConstants.UPDATE) )
		   return IAppConstants.UPDATE;
		else
		if (methName.equalsIgnoreCase(IAppConstants.DELETE) )
		   return IAppConstants.DELETE;
		else
		if (methName.equalsIgnoreCase(IAppConstants.INQUIRE) )
		   return IAppConstants.INQUIRE;
		else
		if (methName.equalsIgnoreCase(IAppConstants.NAVIGATE) )
		   return IAppConstants.NAVIGATE;
		else
		if (methName.equalsIgnoreCase(IAppConstants.CANCEL) )
		   return IAppConstants.CANCEL;
		else
		if (methName.equalsIgnoreCase(INFLAppConstants.BUTTON_SUBMIT))
		   return IAppConstants.INQUIRE;
		else
		if (methName.equalsIgnoreCase(INFLAppConstants.BUTTON_RESUBMIT))
		   return IAppConstants.INQUIRE;
		else
		if (methName.equalsIgnoreCase(INFLAppConstants.BUTTON_ADDCASHSPENT))
		   return IAppConstants.ADD;
		else
		if (methName.equalsIgnoreCase(INFLAppConstants.BUTTON_ADDTHENADDANOTHERCASHSPENT))
		   return IAppConstants.ADD;
		else
		if (methName.equalsIgnoreCase(INFLAppConstants.BUTTON_CANCELADD))
		   return IAppConstants.INQUIRE;
		else
		if (methName.equalsIgnoreCase(INFLAppConstants.BUTTON_CANCELUPDATE))
		   return IAppConstants.INQUIRE;
		else
		if (methName.equalsIgnoreCase(INFLAppConstants.BUTTON_CANCELDELETE))
		   return IAppConstants.INQUIRE;
		else
		if (methName.equalsIgnoreCase(INFLAppConstants.BUTTON_RETURN))
		   return IAppConstants.INQUIRE;
		else
		if (methName.equals(IAppConstants.LOGOUT) )
		   return IAppConstants.INQUIRE;
		else
		   return methName;
	}
	
	public ActionComposite executeInquire(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws PresentationException, BusinessException, DAOException,
			PASSystemException
	{
		log.debug("Invoking executeInquire:: method");
		return processExecuteAction(mapping, form, req, res, IAppConstants.INQUIRE_ACTION);
	}

	public ActionComposite executeAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res)
			throws PresentationException, BusinessException, DAOException,
			PASSystemException
	{
		log.debug("Invoking executeAdd:: method");
		return processExecuteAction(mapping, form, req, res, IAppConstants.ADD_ACTION);
	}

	public ActionComposite executeUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws PresentationException, BusinessException, DAOException,
			PASSystemException
	{
		log.debug("Invoking executeUpdate:: method");
		return processExecuteAction(mapping, form, req, res, IAppConstants.UPDATE_ACTION);
	}

	public ActionComposite executeDelete(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws PresentationException, BusinessException, DAOException,
			PASSystemException
	{
		log.debug("Invoking executeDelete:: method");
		return processExecuteAction(mapping, form, req, res, IAppConstants.DELETE_ACTION);
	}
	
	public ActionComposite executeNavigate(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws PresentationException, BusinessException, DAOException,
			PASSystemException
	{
		log.debug("Invoking executeNavigate:: method");
		return processExecuteAction(mapping, form, req, res, IAppConstants.NAVIGATE_ACTION);
	}
	
	public ActionComposite processExecuteAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res,
			int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{

		String methodName = "processExecuteAction ::";
		log.debug(methodName + "In::");
		ActionComposite ac = new ActionComposite();

		// get the Action Name
		String actionName = getActionName(mapping);
		log.debug("Action Name is " + actionName);

		PASActionSettingsDefinition definitionSettings = actionSettings.get(actionName);

		if (definitionSettings == null)
		{
			log.error("No action Setting found for " + actionName + ".  Please check action_def.xml file");
			throw new PresentationException("No action Setting found for " + actionName + ".  Please check action_def.xml file");
		}
		
		String actionForward = definitionSettings.getNavigationPath();
		log.debug("Action Forward is " + actionForward);
		ac.setActionForward(mapping.findForward(actionForward));

		if (preprocessAction(mapping, form, req, res, operation))
		{
			//get value Object
			ICacheManager cache = CacheManagerFactory.getCacheManager();
			Object reqObj = cache.getObject("RequestObject", req.getSession());
			boolean goToDBInd = cache.getGoToDBInd(req.getSession());
			
			BusinessComposite bc = invokeBusiness(mapping, form, req, res, operation, reqObj, goToDBInd);
			   
			// extract action errors
			   
			if (bc.hasValidationMessages())
			{
				log.debug("There are action errors from business layer .....");
				ActionMessages am = this.extractActionMessages(bc.getValidationMessages());
				ac.setMessages(am);				
			}
			   
			Object valueObject = bc.getValueObject();
			//log.debug("Value object returned " + valueObject);
			   
			if (valueObject != null)
			{
			   // The value object returned from Inquiry is an Array List
			   // because the DAO interface returns an ArrayList
			   cache.setObject("ResponseObject", valueObject,req.getSession());
			}	  
						
			postprocessAction(mapping, form, req, res, operation, ac);
		}
		
		log.debug(methodName + "Out::");
		return ac;

	}

	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException {
		log.debug("inside pre - process");
		return true;
	}


	public void postprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation,
			ActionComposite ac) throws PresentationException,
			BusinessException, DAOException, PASSystemException {

		log.debug("inside post - process");
	}
	
	public BusinessComposite invokeBusiness(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res,
			int operation, Object reqObj, boolean goToDBInd) throws BusinessException,
			DAOException, PASSystemException
	{
		String method = "invokeBusiness ::";
		log.debug(method + " in");
		String actionName = getActionName(mapping);
		
		PASActionSettingsDefinition definitionSettings = actionSettings
				.get(actionName);

		String businessService = definitionSettings.getBusinessServiceName();
		log.debug("business service name - " + businessService);

		BusinessComposite bc = new BusinessComposite();
		
		if (businessService != null && goToDBInd)
		{
			switch (operation)
			{
			case IAppConstants.ADD_ACTION:
				bc = this.addBusiness(businessService, reqObj, req);
				break;
			case IAppConstants.UPDATE_ACTION:
				bc = this.updateBusiness(businessService, reqObj, req);
				break;
			case IAppConstants.DELETE_ACTION:
				bc = this.deleteBusiness(businessService, reqObj, req);
				break;
			case IAppConstants.INQUIRE_ACTION:
				bc = this.inquireBusiness(businessService, reqObj, req);
				break;		
			}
		}
		log.debug(method + " out");
		return bc;
	}

	public static void initializeActionSettings() throws PASSystemException {

		log.debug("initializing Action Settings ....");
		IPropertyCollection ipc = pr
				.getCollection("NFLStandardActionSettings.Action");

		int actionIndex = -1;
		while (ipc.hasNext()) {
			ipc.next();
			actionIndex++;
			String actionName = ipc.getProperty("name");
			log.debug("actionName is " + actionName);
			if (actionName == null) {
				log.error(" actionName for index [" + actionIndex
						+ "] is NULL. This is not correct !!!");
				continue;
			}
			PASActionSettingsDefinition definitionSettings = new PASActionSettingsDefinition();
			definitionSettings.setActionName(actionName);
			actionSettings.put(actionName, definitionSettings);
			String actionForward = ipc.getProperty("actionForward");
			log.debug("actionforward is " + actionForward);
			if (actionForward == null) {
				log.error(" Action forward for " + actionName
						+ " is NULL. This is not correct !!!");
			}
			//actionSettings.put(actionName + ACTION_FORWARD, actionForward);
			definitionSettings.setNavigationPath(actionForward);
			
			String businessServiceName = ipc.getProperty("businessServiceRef");
			log.debug("businessServiceName is " + businessServiceName);
			if (businessServiceName == null) {
				log.debug(" Business Service for " + actionName
						+ " is NULL. This may not be correct.");
			}
			definitionSettings.setBusinessServiceName(businessServiceName);
			
			int messageCount = 0;
			definitionSettings.setMessageCount(messageCount);

		}
		dumpKeys();

	}	

	@SuppressWarnings("unchecked")
	private static void dumpKeys() {
		Set keySet = actionSettings.keySet();
		Iterator itr = keySet.iterator();
		log.debug("%%%%%%%%%%%%%%ACTION SETTING BEGIN%%%%%%%%%%%%%%%%%%%");
		for (; itr.hasNext();) 
		{
			String key = (String) itr.next();
			PASActionSettingsDefinition settings = actionSettings.get(key);
			settings.printSettings();
		}
		log.debug("%%%%%%%%%%%%%%%ACTION SETTING END%%%%%%%%%%%%%%%%%%%%%");
	}	

}
