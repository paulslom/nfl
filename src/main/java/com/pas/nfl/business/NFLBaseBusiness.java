/*
 * Created on Mar 9, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.nfl.business;

import com.pas.business.BaseBusiness;
import com.pas.business.BusinessComposite;
import com.pas.business.IBusiness;
import com.pas.business.ValidationMessage;
import com.pas.business.ValidationMessages;

import com.pas.nfl.dao.NFLDAOFactory;
import com.pas.exception.BusinessException;
import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;
import com.pas.exception.SystemException;
import com.pas.dao.IDBDAO;

/**
 * @author SGanapathy
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NFLBaseBusiness extends BaseBusiness implements INFLBusiness
{
	/**
	   *  Method to get handle to DAO Interface
	   *  @param daoName name of the dao class
	   *  @return IDAO
	   * @exception DAOException
	   * @exception SystemException
	   */
	  protected IDBDAO getDAOInstance(String daoName) throws DAOException, SystemException {
		  return NFLDAOFactory.getDAOInstance(daoName);
	  }
	  
	/**
	  * This method delegates data entered by user to concrete class for add. Actual business logic needs
	  * to be implemented in concrete class. This controls database transaction management for add.
	  *
	  * 
	  * @return BusinessComposite
	  * 
	  * @exception BusinessException
	  * @exception DAOException
	  * @exception SystemException
	  */
	public BusinessComposite add(Object valueObject)
		throws BusinessException, DAOException, PASSystemException {
		String methodName = "add";
		log.debug("Operation :: " + methodName );
		return processBusinessAction(valueObject,IBusiness.ADD);

	 }

	 /**
	  * This method delegates data entered by user to concrete class for update. Actual business logic needs
	  * to be implemented in concrete class. This controls database transaction management for update
	  * 
	  * @param IValueObject valueObject
	  * @param User userObject
	  * 
	  * @return BusinessComposite
	  * 
	  * @exception BusinessException
	  * @exception DAOException
	  * @exception SystemException
	  */
	 public BusinessComposite update(Object valueObject)
		 throws BusinessException, DAOException, PASSystemException
	 {	
		String methodName = "update";
		log.debug("Operation :: " + methodName );
		return processBusinessAction(valueObject, IBusiness.UPDATE);
	 }

	 
	 /**
	  * This method delegates inquiry request from user to concreate class. Actual business logic needs
	  * to be implemented in concrete class. This controles database transaction management for inquiry
	  *
	  * @param IValueObject criteriaObject  - that holds more than one value later used to query database
	  * @param ClaimNotificationHeader cho
	  * @param String reqClaimId
	  * 
	  * @return BusinessComposite
	  * 
	  * @exception BusinessException
	  * @exception DAOException
	  * @exception SystemException
	  */
	 public BusinessComposite inquire(Object valueObject)
		 throws BusinessException, DAOException, PASSystemException
	 {
		 String methodName = "inquire";
		 log.debug("Operation :: " + methodName );
		 return processBusinessAction(valueObject, IBusiness.INQUIRE);
	 }


	 /**
	  * This method delegates users delete request to concrete class for delete. Actual business logic needs
	  * to be implemented in concrete class. This controls database transaction management for delete
	  *
	  * @param IValueObject deleteKey   This holds set of values that are required to delete the record in database
	  * @param User userObject
	  * @param ClaimNotificationHeader cho
	  * @param String reqClaimId
	  * 
	  * @return BusinessComposite
	  * 
	  * @exception BusinessException
	  * @exception DAOException
	  * @exception SystemException
	  */
	 public BusinessComposite delete(Object valueObject)
		 throws BusinessException, DAOException, PASSystemException
	 {
		 String methodName = "delete ::";
		 log.debug("Operation :: " + methodName );
		 return processBusinessAction(valueObject, IBusiness.DELETE);
	 }


	private BusinessComposite processBusinessAction(Object valueObject, int operation)
	   throws BusinessException, DAOException, PASSystemException
	{
		String methodName = "processBusinessAction ::";
		log.debug(methodName + "in");
		BusinessComposite bc = null;		
		
		bc = this.validate(operation, valueObject);
			
		if ( bc != null )
		{
			ValidationMessages msgList = bc.getValidationMessages();
			if( msgList != null && ! msgList.isEmpty()){
				return bc;
			}
		}
		
		switch (operation)
		{
			case IBusiness.ADD:
				bc = executeAdd(valueObject);
				break;
			case IBusiness.UPDATE:
				bc = executeUpdate(valueObject);
				break;
			case IBusiness.DELETE:
				bc = executeDelete(valueObject);
				break;
			case IBusiness.INQUIRE:
				bc = executeInquire(valueObject);
				break;
		}
      			
		log.debug(methodName + "out");
		return bc;	 
	}

	 /**
	  * This method contains the actual functionality & is the implementation
	  * point for subclasses. This base implementation throws a SystemException 
	  * throws 
	  * @param valueObject  this holds set of key values that are used for searching in the database
	  * @param conn  database connection
	  * 
	  * @exception BusinessException
	  * @exception DAOException
	  * @exception SystemException
	  */
	 protected BusinessComposite executeInquire(Object valueObject)
		 throws BusinessException, DAOException, PASSystemException
	 {
		 log.error("inquire:: Not implemented");
		 throw new PASSystemException("inquire business function is not implemented");
	 }

	 
	 /**
	  * This method contains the actual functionality & is the implementation
	  * point for subclasses. This base implementation throws a SystemException 
	  * throws 
	  * @param valueObject  
	  * @param User userObj
	  * @param conn  database connection
	  * 
	  * @exception BusinessException
	  * @exception DAOException
	  * @exception SystemException
	  */
	 protected BusinessComposite executeAdd(Object valueObject)
		 throws BusinessException, DAOException, PASSystemException
	 {
		 log.error("add:: Not implemented");
		 throw new PASSystemException("Add business function is not implemented");
	 }

	 /**
	  * This method contains the actual functionality & is the implementation
	  * point for subclasses. This base implementation throws a SystemException 
	  * throws 
	  * @param valueObject
	  * @param User userObj
	  * @param conn  database connection
	  * 
	  * @exception BusinessException
	  * @exception DAOException
	  * @exception SystemException
	  */
	 protected BusinessComposite executeUpdate(Object valueObject)
		 throws BusinessException, DAOException, PASSystemException
	 {
		 log.error("update:: Not implemented");
		 throw new PASSystemException("Update business function  is not implemented");
	 }

	 /**
	  * This method contains the actual functionality & is the implementation
	  * point for subclasses. This base implementation throws a SystemException 
	  * throws 
	  * @param valueObject  
	  * @param User userObj
	  * @param conn  database connection
	  * 
	  * @exception BusinessException
	  * @exception DAOException
	  * @exception SystemException
	  */
	 protected BusinessComposite executeDelete(Object valueObject)
		 throws BusinessException, DAOException, PASSystemException
	 {
		 log.error("delete:: Not implemented");
		 throw new PASSystemException("Delete business function is not implemented");
	 }



	public BusinessComposite validate(int operation, Object valueObject)
				throws BusinessException, DAOException, PASSystemException
	{
		return null;
	}
    
	
	
	protected BusinessComposite validationFailed(String validationMsgKey,String screenFieldName,
			BusinessComposite bc) {
		log.debug("validation failed....");
		if (bc == null) {
			bc = new BusinessComposite();
		}
		ValidationMessages validationMessages = bc.getValidationMessages();
		if (validationMessages == null) {
			validationMessages = new ValidationMessages();
		}
		validationMessages.add(new ValidationMessage(screenFieldName,validationMsgKey));
		bc.setValidationMessages(validationMessages);
		return bc;
	}
	
	/* Added by Balaraj P */
	
	// method that is called from child classes when a validation fails
	// This method will return the message key and action layer will 
	// use this key to load the message from the resource bundle
	
	protected BusinessComposite validationFailed(String validationMsgKey,BusinessComposite bc){
		log.debug("validation failed....");
		if(bc == null){
			bc = new BusinessComposite();
		}
		ValidationMessages validationMessages = bc.getValidationMessages();
		if(validationMessages == null){
			validationMessages = new ValidationMessages();
		}							
		validationMessages.add(new ValidationMessage(validationMsgKey));				
		bc.setValidationMessages(validationMessages);
		return bc;
	}
	
	/* End of Added by Balaraj P */

}
