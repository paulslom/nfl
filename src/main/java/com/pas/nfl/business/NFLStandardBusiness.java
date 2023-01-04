/*
 * Created on Mar 9, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.nfl.business;

import java.util.List;

import com.pas.business.BusinessComposite;
import com.pas.business.IBusiness;
import com.pas.nfl.dao.NFLDAOFactory;

import com.pas.exception.BusinessException;
import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;
import com.pas.exception.SystemException;
import com.pas.dao.IDBDAO;

/**
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NFLStandardBusiness extends NFLBaseBusiness {

	private IDBDAO daoReference = null;

	private String daoName;

	private String serviceName;	

	public NFLStandardBusiness(String businessServiceName, String daoRefName)
	    throws PASSystemException, DAOException, SystemException
	{
		serviceName = businessServiceName;
		daoName = daoRefName;
		daoReference = NFLDAOFactory.getDAOInstance(daoName);
		log.debug("service name = " + serviceName);
	}

	protected BusinessComposite executeInquire(Object valueObject)
	    throws BusinessException, DAOException, PASSystemException
	{
		String methodName = "executeInquire";
		log.debug("Operation :: " + methodName);
		return executeBusinessAction(valueObject, IBusiness.INQUIRE);
	}

	protected BusinessComposite executeAdd(Object valueObject)
	    throws BusinessException, DAOException, PASSystemException
	{
		String methodName = "executeAdd";
		log.debug("Operation :: " + methodName);
		return executeBusinessAction(valueObject, IBusiness.ADD);
	}

	protected BusinessComposite executeUpdate(Object valueObject)
	    throws BusinessException, DAOException, PASSystemException
	{
		String methodName = "executeUpdate";
		log.debug("Operation :: " + methodName);
		return executeBusinessAction(valueObject,IBusiness.UPDATE);
	}

	protected BusinessComposite executeDelete(Object valueObject)
	    throws BusinessException, DAOException, PASSystemException
	{
		String methodName = "executeDelete";
		log.debug("Operation :: " + methodName);
		return executeBusinessAction(valueObject, IBusiness.DELETE);
	}

	@SuppressWarnings("unchecked")
	private BusinessComposite executeBusinessAction(Object valueObject, int operation)
	   throws BusinessException, DAOException, PASSystemException
	{
		String methodName = "executeBusinessAction";
		log.debug("Operation :: " + methodName);

		if (daoReference == null)
		{
			throw new PASSystemException("DAO reference for " + daoName
				+ " is not set up properly. Check DAO Names used in Resources folder: Business_def.xml and DAO_Def.xml");
		}
		BusinessComposite bc = new BusinessComposite();
		
		preProcess(valueObject, operation);
		
		List retVal;
		
		switch (operation)
		{
			case IBusiness.ADD:
				retVal = daoReference.add(valueObject);
				bc.setValueObject(retVal);
				break;
			case IBusiness.UPDATE:
				retVal = daoReference.update(valueObject);
				bc.setValueObject(retVal);
				break;
			case IBusiness.DELETE:
				retVal = daoReference.delete(valueObject);
				bc.setValueObject(retVal);
				break;
			case IBusiness.INQUIRE:
				retVal = daoReference.inquire(valueObject);
				bc.setValueObject(retVal);
				break;
		}
		
		bc = postProcess(valueObject, operation, bc);
		log.debug(methodName + "Out ");
		
		return bc;
	}

	protected void preProcess(Object valueObject, int operation)
	   throws PASSystemException
	{
		log.debug(" There is no preprocessing");
	}

	// << Value object is an array List for inquiry call
	protected BusinessComposite postProcess(Object valueObject, int operation,
			BusinessComposite bc) throws PASSystemException
	{
		log.debug(" There is no postprocessing");
		return bc;
	}	

}
