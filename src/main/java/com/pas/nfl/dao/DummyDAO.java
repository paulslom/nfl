package com.pas.nfl.dao;

import java.util.List;

import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;

/**
 * Title: 		BudgetDAO
 * Project: 	Slomkowski Financial Application
 * Description: Mortgage DAO extends BaseDBDAO. Implements the data access to the tblMortgageHistory table
 * Copyright: 	Copyright (c) 2007
 */
public class DummyDAO extends BaseDBDAO {

  private static final DummyDAO currentInstance = new DummyDAO();

    private DummyDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
    */
    public static DummyDAO getDAOInstance() {
        return currentInstance;
    }
    	
	@SuppressWarnings("unchecked")
	public List inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
				
		log.debug(methodName + "out");
		return null;	
	}

}
