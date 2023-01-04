package com.pas.nfl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Title: LogoutAction
 * Project: Claims Replacement System
 * 
 * Description: 
 * 
 * Copyright: Copyright (c) 2003
 * Company: Lincoln Life
 * 
 * @author CGI
 * @version 
 */
public class LogoutAction extends Action
{
    Logger log = LogManager.getLogger(this.getClass());

    public final ActionForward execute(ActionMapping mapping, ActionForm form, 
    	HttpServletRequest req, HttpServletResponse res) throws Exception
    {
        String methodName = "logout :: ";
        log.debug(methodName + "In");
        
        HttpSession session = req.getSession();
        session.invalidate();
        
        log.debug(methodName + "out");
    
        return mapping.findForward("success");
    }
}
