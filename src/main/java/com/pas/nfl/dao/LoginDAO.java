package com.pas.nfl.dao;

import java.util.ArrayList;
import java.util.List;

import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;

import com.pas.valueObject.User;

public class LoginDAO extends BaseDBDAO
{ 	 
  private static final LoginDAO currentInstance = new LoginDAO();

    private LoginDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return LoginDAO
     */
    public static LoginDAO getDAOInstance() 
    {
        return currentInstance;
    }
        
	@SuppressWarnings("unchecked")
	public List<User> inquire(Object info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
		
		log.debug("entering LoginDAO inquire");
		
		//execute a query against the security table - if successful then user is valid
		
		User user =  (User)info;
				
		log.debug("no security in play - just pass back the user in a list");
				
		List<User> securityList = new ArrayList<User>();     
	    
		securityList.add(user);
		
		log.debug("final list size is = " + securityList.size());
		log.debug(methodName + "out");
		return securityList;
	}

}
