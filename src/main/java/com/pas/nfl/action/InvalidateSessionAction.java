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

public class InvalidateSessionAction extends Action
{
	protected static Logger log = LogManager.getLogger(InvalidateSessionAction.class);
	
    public ActionForward execute(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response)
    {
     
       HttpSession session = request.getSession(false);
      
       if (session!=null)
       {
    	  log.debug("inside InvalidateSessionAction"); 
          session.invalidate();
          System.out.println("session has been invalidated");
          log.debug("exiting InvalidateSessionAction");
       }  
      
       return null;
  
    }
}