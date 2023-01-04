package com.pas.nfl.business;

import com.pas.business.BusinessComposite;
import com.pas.business.IBusiness;
import com.pas.exception.BusinessException;
import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;

/**
 * Title: INFLBusiness
 * 
 * Description: Defines the interface for all business logic objects
 * in the application. This interface provides the methods for common business
 * logic operations that all business layer objects would need to implement 
 *  
 */
public interface INFLBusiness extends IBusiness
{
    /**
     * Method to add business object
     * @param valueObject
     * @param userObject
     * @throws BusinessException
     * @return BusinessComposite
     */
	public BusinessComposite add(Object valueObject)
        throws BusinessException, DAOException, PASSystemException;

    /**
     * Method to update the business object
     *
     * @param valueObject
     * @param userObject
     * @throws BusinessException
         * @return BusinessComposite
     */
	public BusinessComposite update(Object valueObject)
        throws BusinessException, DAOException, PASSystemException;

    /**
     * Method to inquire for business object
     * @param criteriaObject
     * @return List
     * @throws BusinessException
         * @return BusinessComposite
     */
	public BusinessComposite inquire(Object valueObject)
        throws BusinessException, DAOException, PASSystemException;

    /**
     * Method to delete business object
     * @param deleteKey
     * @throws BusinessException
     */
	public BusinessComposite delete(Object valueObject)
        throws BusinessException, DAOException, PASSystemException;

        
   
}
