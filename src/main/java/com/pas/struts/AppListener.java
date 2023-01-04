package com.pas.struts;

import com.pas.struts.Config;

import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppListener implements ServletContextListener
{
	//handle to logger class to log messages
    protected Logger log = LogManager.getLogger(this.getClass());
    
	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent cse)
	{
	  try
	  {
		  Config config = Config.getInstance();
		  ServletContext servletContext = cse.getServletContext();
		  Enumeration<String> parameters = servletContext.getInitParameterNames();
		  
		  while (parameters.hasMoreElements())
		  {
			  String parameter = (String) parameters.nextElement();
			  config.addKeyValue(parameter, servletContext.getInitParameter(parameter));
		  }   
	  } 
	  catch (Exception ex)
	  {
	     log.debug("App listener error: " + ex.getMessage());
	  }
	}

	public void contextDestroyed(ServletContextEvent cse)
	{
    }
}
