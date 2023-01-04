package com.pas.struts;

import java.util.Date;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionListener implements HttpSessionListener
{
	protected static Logger log = LogManager.getLogger(SessionListener.class);
	
	public void sessionCreated (HttpSessionEvent se)
	{
		HttpSession session = se.getSession();
		String outMsg = "Creating Session. Session id: " + session.getId() + " " + new Date(session.getCreationTime());
		System.out.println(outMsg);
		log.debug(outMsg);
   	}

	public void sessionDestroyed (HttpSessionEvent se)
	{
		HttpSession session = se.getSession();
		String outMsg = "Destroying Session. Session id:" + session.getId(); 
		System.out.println(outMsg);
		log.debug(outMsg);
	}
}