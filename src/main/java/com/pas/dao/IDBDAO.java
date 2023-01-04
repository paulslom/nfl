package com.pas.dao; 

import java.util.List;

import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;

/**
 * Title: IDAO
 * Project: Claims Replacement System
 * 
 * Description: 
 * 
 * Copyright: Copyright (c) 2003
 * Company: Lincoln Life
 * 
 * @author psinghal
 * @version 
 */
public interface IDBDAO
{
    
	@SuppressWarnings("unchecked")
	List update(Object valueObject) throws DAOException, PASSystemException;
    
	@SuppressWarnings("unchecked")
	List inquire(Object valueObject) throws DAOException, PASSystemException;
	
	@SuppressWarnings("unchecked")
	List add(Object valueObject) throws DAOException, PASSystemException;

	@SuppressWarnings("unchecked")
	List delete(Object valueObject) throws DAOException, PASSystemException;
}
