package com.pas.nfl.business;

import java.util.List;

import com.pas.business.BusinessComposite;

import com.pas.valueObject.IValueObject;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.constants.INFLMessageConstants;
import com.pas.nfl.dao.NFLDAOFactory;
import com.pas.valueObject.User;

import com.pas.dao.IDBDAO;
import com.pas.exception.BusinessException;
import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;
import com.pas.exception.SystemException;


/**
 *  
 */
public class LoginBusiness extends NFLStandardBusiness
{
	public LoginBusiness(String serviceName, String daoRefName)
			throws PASSystemException, DAOException, SystemException
	{
		super(serviceName, daoRefName);
	}

	// generates validation message key if
	// effDate is not less than or equal to paidToDate

	@SuppressWarnings("unchecked")
	public BusinessComposite validate(int operation, IValueObject valueObject)
			throws BusinessException, DAOException, PASSystemException
	{
		
		log.debug("Inside validate method :: LoginBusiness");
		
		BusinessComposite bc = super.validate(operation, valueObject);
				
		try
		{
			IDBDAO daoReference = NFLDAOFactory.getDAOInstance(INFLAppConstants.LOGIN_DAO);

			log.debug("accessing security table to see if there is a record with value object's criteria");

			List<User> securityList = daoReference.inquire(valueObject);
				
			if (securityList.size() == 0)
			{
				// an empty list indicates no userID password found
				
			  log.debug("no user id and password found");
			  bc = validationFailed(INFLMessageConstants.LOGIN_USERLOGIN_FAILED, 
					  INFLMessageConstants.LOGIN_USERLOGIN_FAILED, bc);
			  
			}
		}
		catch (PASSystemException e)
		{
			log.error("LFGSystemException encountered in LoginBusiness");
			e.printStackTrace();
			throw new PASSystemException(e);
		}
		catch (DAOException e)
		{
			log.error("DAOException encountered in LoginBusiness");
			e.printStackTrace();
			throw new DAOException(e);
		}
		catch (SystemException e)
		{
			log.error("SystemException encountered in LoginBusiness");
			e.printStackTrace();
		}

		log.debug("exiting validate method :: LoginBusiness");
		return bc;
	}


}

