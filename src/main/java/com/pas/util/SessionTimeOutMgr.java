package com.pas.util;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.valueObject.User;
import com.pas.constants.IAppConstants;

/**
 * Title: SessionTimeOutMgr
 * Project: Claims Replacement System
 * 
 * Description: 
 * 
 * Copyright: Copyright (c) 2003
 * C ompany: Lincoln Life
 * 
 * @author psinghal
 * @version 
 */
public class SessionTimeOutMgr implements HttpSessionBindingListener {
    private Logger log = LogManager.getLogger(this.getClass());

    public SessionTimeOutMgr() {
        super();
    }

    /**
     * This method is invoked by container when user session is invalidated.
     * This unlocks the current claim from database and removes User and
     * Claim objects from session
     * @param event	HttpSessionBindingEvent
     */
    public void valueUnbound(HttpSessionBindingEvent event) {
        String methodName = "valueUnbound :: ";
        log.debug(methodName + "In");
        HttpSession session = event.getSession();
        try{
        User userObject = (User) session.getAttribute(IAppConstants.USER);
        if (userObject != null && !userObject.equals("")) {
         	performAuditCleanUp(session);
         	cleanUpSession(session);
        }
        log.debug(methodName + "out");
        }catch(java.lang.IllegalStateException excep){
        }
       
    }

    
		/**
		 * This function needs to be defined. 
		 */
	private void performAuditCleanUp(HttpSession session) {

	}
    /**
     * This method removes USER and CLAIM objects from session
     * @param session 	current user session that needs to be cleaned 
     */
    private void cleanUpSession(HttpSession session) {
        String methodName = "cleanUpSession :: ";
        log.debug(methodName + "In");

		
        session.invalidate();
        
        log.debug(methodName + "out");
    }

    /**
     * 	no implementation is required
     */
    public void valueBound(HttpSessionBindingEvent event) {
        log.debug("valueBound method is invoked. But no action takes place here");
    }

}