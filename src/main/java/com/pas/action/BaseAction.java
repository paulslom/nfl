package com.pas.action; 

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;

import com.pas.business.ValidationMessage;
import com.pas.business.ValidationMessages;
import com.pas.constants.IAppConstants;
import com.pas.constants.IMessageConstants;
import com.pas.exception.BaseException;
import com.pas.exception.BusinessException;
import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;
import com.pas.exception.PresentationException;
import com.pas.nfl.actionform.LoginForm;
import com.pas.valueObject.User;

/**
 * Title: BaseSuperAction
 * Project: NFL System
 * 
 * Description: BaseAction is abstract base class for all actions which encapsulates common data
 * and behavior for actions 
 * 
 * Copyright: Copyright (c) 2006
 * @since   1.0
 */
public abstract class BaseAction extends DispatchAction
{
    protected static Logger log = LogManager.getLogger(BaseAction.class);

    protected MessageResources messageRes = MessageResources.getMessageResources(IAppConstants.ERROR_MESSAGES);

	public final ActionForward add(ActionMapping mapping, ActionForm form,
		HttpServletRequest req,	HttpServletResponse res) throws Exception
	{
		String methodName = "add :: ";
		log.debug(methodName + "start");
		return processAction(mapping, form, req, res, IAppConstants.ADD);
	}

	public final ActionForward update(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		String methodName = "update :: ";
		log.debug(methodName + "start");
		return processAction(mapping, form, req, res, IAppConstants.UPDATE);
	}
	
	public final ActionForward delete(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		String methodName = "delete :: ";
		log.debug(methodName + "start");
		return processAction(mapping, form, req, res, IAppConstants.DELETE);
	}
	
	public final ActionForward inquire(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		String methodName = "inquiry :: ";
		log.debug(methodName + "start");
		return processAction(mapping, form, req, res, IAppConstants.INQUIRE);
	}
	
	public final ActionForward cancel(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		String methodName = "cancel :: ";
		log.debug(methodName + "start");
		return mapping.findForward(IAppConstants.CANCEL);
	}

	public final ActionForward clear(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		String methodName = "clear :: ";
		log.debug(methodName + "start");
		return processAction(mapping, form, req, res, IAppConstants.CLEAR);
	}

	public final ActionForward navigate(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		String methodName = "navigate :: ";
		log.debug(methodName + "start");
		return processAction(mapping, form, req, res, IAppConstants.NAVIGATE);
	}

	public final ActionForward initialize(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		String methodName = "initialize :: ";
		log.debug(methodName + "start");
		return processAction(mapping, form, req, res, IAppConstants.INITIALIZE);
	}

	public final ActionForward override(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		String methodName = "override :: ";
		log.debug(methodName + "start");
		return processAction(mapping, form, req, res, IAppConstants.OVERRIDE);
	}
	/**
	 * The processAction does following things
	 *    1. checks whether user is valid
	 *    2. checks whether session time out occurred
	 *    3. invokes appropriate action
	 *    4. returns appropriate actionForward object
	 */
	public final ActionForward processAction(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res, String method) throws Exception
	{
		String methodName = "processAction :: ";
		log.debug(methodName + " In");

		log.debug(methodName + "Servlet Path = " + req.getServletPath());
		log.debug(methodName + "Context Path = " + req.getContextPath());

		ActionComposite ac = new ActionComposite();
		
		try
		{	
			//check if user is logged in & return with errors if required
			
			if (!(form instanceof LoginForm))
			{
				ActionMessages messages = doSecurityCheck(req);
				if (!messages.isEmpty())
				{
					saveMessages(req, messages);
					return (mapping.findForward(IAppConstants.LOGIN_DEFINITION));
				}
			}	

			log.debug(methodName + "******************** before calling action subclass methods. Method = " + method);

			initializeData(mapping, form, req);
			
			if (method.equals(IAppConstants.ADD)){
				ac = executeAdd(mapping, form, req, res);
			} else if (method.equals(IAppConstants.UPDATE)) {
				ac = executeUpdate(mapping, form, req, res);
			} else if (method.equals(IAppConstants.DELETE)) {
				ac = executeDelete(mapping, form, req, res);
			} else if (method.equals(IAppConstants.INQUIRE)) {
				ac = executeInquire(mapping, form, req, res);
			} else if (method.equals(IAppConstants.CANCEL)) {
				ac = executeCancel(mapping, form, req, res);
			} else if (method.equals(IAppConstants.CLEAR)) {
				ac = executeClear(mapping, form, req, res);
			} else if (method.equals(IAppConstants.NAVIGATE)) {
				ac = executeNavigate(mapping, form, req, res);
			} else if (method.equals(IAppConstants.INITIALIZE)) {
				ac = executeInitialize(mapping, form, req, res);
			} else if (method.equals(IAppConstants.OVERRIDE)) {
				ac = executeOverride(mapping, form, req, res);
			} 

			if (ac.messages != null )
			{
				saveMessages(req, ac.messages);
			}

			// changed the condition as the forward should be to 
			// action forward set in the post process and not to the 
			// input forward for some conditions
			
			if (ac.messages != null && ac.getActionForward()==null)
			{
				return (mapping.getInputForward());
			}
			
		}
		catch (Exception e)
		{
			// Log the application exception by calling the generic exception handling routine
			ac.setActionForward(processExceptions(req, mapping, e, method));
		}  
		
		if (ac.getActionForward() == null)
		{
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("exception.msg", "action forward set to null on : "+mapping.getPath()));
			saveMessages(req, messages);
			log.error("action forward set to null on :"+mapping.getPath());
			return (mapping.findForward(IAppConstants.EXCEPTION_DEFINITION));
		}	

		log.debug(methodName + "out");
		return ac.getActionForward();
	}
	

	protected void initializeData(ActionMapping mapping, ActionForm form,
			HttpServletRequest req) throws PASSystemException
	{
	}

	public ActionComposite executeAdd(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res)
		throws PresentationException, BusinessException, DAOException, PASSystemException
	{
		log.error("executeAdd:: Not implemented");
		throw new PASSystemException("executeAdd is not implemented");
	}

	public ActionComposite executeInquire(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res)
		throws PresentationException, BusinessException, DAOException, PASSystemException
	{
		log.error("executeInquire:: Not implemented");
		throw new PASSystemException("executeInquire is not implemented");
	}

	public ActionComposite executeDelete(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res)
		throws PresentationException, BusinessException, DAOException, PASSystemException
	{
		log.error("executeDelete:: Not implemented");
		throw new PASSystemException("executeDelete is not implemented");
	}

	public ActionComposite executeUpdate(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res)
		throws PresentationException, BusinessException, DAOException, PASSystemException
	{
		log.error("executeUpdate:: Not implemented");
		throw new PASSystemException("executeUpdate is not implemented");
	}

	public ActionComposite executeCancel(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res)
		throws PresentationException, BusinessException, DAOException, PASSystemException
	{
		log.error("executeCancel:: Not implemeted");
		throw new PASSystemException("executeCancel is not implemented");
	}

	public ActionComposite executeClear(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res)
		throws PresentationException, BusinessException, DAOException, PASSystemException
	{
		log.error("executeClear:: Not implemented");
		throw new PASSystemException("executeClear is not implemented");
	}

	public ActionComposite executeNavigate(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res)
		throws PresentationException, BusinessException, DAOException, PASSystemException
	{
		log.error("executeNavigate:: Not implemented");
		throw new PASSystemException("executeNavigate is not implemented");
	}

	public ActionComposite executeInitialize(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res)
		throws PresentationException, BusinessException, DAOException, PASSystemException
	{
		log.error("executeInitialize:: Not implemented");
		throw new PASSystemException("executeInitialize is not implemented");
	}

	public ActionComposite executeOverride(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res)
		throws PresentationException, BusinessException, DAOException, PASSystemException
	{
		log.error("executeOverride:: Not implemented");
		throw new PASSystemException("executeInitialize is not implemented");
	}
	
	public ActionForward processExceptions(HttpServletRequest req, ActionMapping mapping,
		Exception ex, String method)
	{
		final String methodName = "processExceptions::";
		log.debug(methodName + "In");

		BaseException be;
		
		ActionMessages messages = new ActionMessages();
		ActionForward forward = null;
		
		StringWriter stringWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(stringWriter));
		
		String stackTraceString = stringWriter.toString();
		
		if (ex instanceof BusinessException)
		{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(IMessageConstants.EXCEPTION_BUSINESS_TEXT));
			be=(BaseException)ex;
		}
		else
		if (ex instanceof DAOException)
		{
			if (ex.getMessage()!=null)
			{	
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(IMessageConstants.EXCEPTION_DAO_TEXT));				
			}	
			be=(BaseException)ex;
		}
		else
		if (ex instanceof PASSystemException)
		{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(IMessageConstants.EXCEPTION_SYSTEM_TEXT));
			be=(BaseException)ex;
		}
		else
		if (ex instanceof PresentationException)
		{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(IMessageConstants.EXCEPTION_PRESENTATION_TEXT));
			be=(BaseException)ex;
		}
		else
		{
			be = new PASSystemException(ex.toString(),ex);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(IMessageConstants.EXCEPTION_SYSTEM_TEXT));
			
		}
		
		forward = mapping.findForward(IAppConstants.EXCEPTION_DEFINITION);
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(IMessageConstants.EXCEPTION_MSGID_TEXT, be.getMsgId()));
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(IMessageConstants.EXCEPTION_MSG_TEXT, be.getMessage()));
		
		try
		{
			addApplicationDetailToError(req, mapping, messages);
		}
		catch (Exception e)
		{
		}		
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(IMessageConstants.EXCEPTION_STACKTRACE, stackTraceString));
								
		log.error("Exception caught in base action ::" + methodName);
		be.logException(log);
					
		saveMessages(req, messages);
		
		log.debug(methodName + "Out");

		// Finally return the action forward
		return forward;
	}
 
	
    /**
     * Checks whether UserData object is available in session, if not return an error message
     *
     * @param session HttpSession
     *
     * @return ActionMessages
     */
	protected void addApplicationDetailToError(HttpServletRequest req, ActionMapping mapping,
			  ActionMessages messages) throws PASSystemException
	{
		return;
	}
	
    public final ActionMessages doSecurityCheck(HttpServletRequest req) throws PASSystemException
    {
        String methodName = "doSecurityCheck :: ";
        log.debug(methodName + " In");
        
        ActionMessages messages = new ActionMessages();
        User uData = getUser(req.getSession());
        
        log.debug("User object in session = " + uData);
        
        if (uData == null)
        {
            log.error(methodName + "User not found in session");
            
            messages.add(ActionMessages.GLOBAL_MESSAGE,
                new ActionMessage(IMessageConstants.ERROR_SECURITY_LOGIN_REQUIRED));
        }
        log.debug(methodName + "end");
        return messages;
    }

       /**
     * A utility method to convert the <code>ValidationErrors</code> object 
     * passed by the business layer to the Struts <code>ActionMessages</code>
     * 
     * @param validationErrors
     * @return ActionMessages
     */
    @SuppressWarnings("unchecked")
	protected ActionMessages extractActionMessages(ValidationMessages validationMessages)
    {
        ActionMessages actionMessages = new ActionMessages();
        Iterator<ValidationMessage> validationErrorsIter = validationMessages.getMessages().iterator();
        
        while (validationErrorsIter.hasNext())
        {
            ValidationMessage validationMsg = (ValidationMessage) validationErrorsIter.next();
            ActionMessage actionMessage = null;
            
            if (validationMsg.getDynamicReplacementValues() != null)
            {
                actionMessage = new ActionMessage(validationMsg.getKey(), validationMsg.getDynamicReplacementValues());
            }
            else
            {
                actionMessage = new ActionMessage(validationMsg.getKey());
            }

            if (validationMsg.screenFieldName() != null)
            {
                actionMessages.add(validationMsg.screenFieldName(), actionMessage);
            }
            else
            {            
                actionMessages.add(ActionMessages.GLOBAL_MESSAGE, actionMessage);
            }
        }
        return actionMessages;
    }
    
    protected abstract User getUser(HttpSession session) throws PASSystemException;
	
}