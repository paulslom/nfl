package com.pas.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.util.IPropertyReader;
import com.pas.util.PropertyReaderFactory;


/**
 * Title: BaseBusiness
 * Project: Claims Replacement System
 * 
 * Description: Super class for all business implementation classes. For the entire application
 * transaction management takes place in this class.
 * 
 * Copyright: Copyright (c) 2003
 * Company: Lincoln Life
 * 
 * @author CGI
 * @version 
 */

public abstract class BaseBusiness implements IBusiness {
	
    // handle to logger class to log messages
    protected Logger log = LogManager.getLogger(this.getClass());
    protected static IPropertyReader pr = PropertyReaderFactory.getPropertyReader();
     
}
