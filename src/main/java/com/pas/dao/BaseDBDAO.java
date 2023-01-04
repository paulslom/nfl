package com.pas.dao; 

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;
import com.pas.util.IPropertyReader;

/**
 * Title: 		BaseDBDAO
 * 
 * Description: This is the base class for the data access layer. All DAO
 * concrete classes need to extend this class. 
 *  
 */
public class BaseDBDAO implements IDBDAO
{
    // reference to Logger
	protected static Logger log = LogManager.getLogger(BaseDBDAO.class);  
      
    protected static IPropertyReader pr;
    
    public MysqlDataSource dataSource = null;
	 
    /**
     * This implementation throws a SystemException
     */
    @SuppressWarnings("unchecked")
	public List add(Object valueObject) throws DAOException, PASSystemException
    {
        log.error("add:: Not implemented");
        throw new PASSystemException("add not implemented");
    }

    /**
     * This implementation throws a SystemException
     */
    @SuppressWarnings("unchecked")
	public List update(Object valueObject) throws DAOException, PASSystemException
    {
        log.error("update(Object):: Not implemented");
        throw new PASSystemException("update not implemented");
    }   

    /**
     * This implementation throws a SystemException
     */
    @SuppressWarnings("unchecked")
	public List delete(Object deleteCriteria) throws DAOException, PASSystemException
    {
        log.error("delete(Object):: Not implemented");
        throw new PASSystemException("delete not implemented");
    }   

    /**
     * This implementation throws a SystemException
     */
    @SuppressWarnings("unchecked")
	public List inquire(Object searchCriteria) throws DAOException, PASSystemException {
        log.error("inquire(Object):: Not implemented");
        throw new PASSystemException("inquire not implemented");
    }
	
}
