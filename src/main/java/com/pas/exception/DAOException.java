package com.pas.exception;

import java.sql.SQLException;

import com.pas.constants.IMessageConstants;

/**
 * Title: DAOException
 * Project: Claims Replacement System
 * 
 * Description: Represents datasource related exceptions originating in the 
 * DAO layer. If the source exception is of type <code>java.sql.SqlException</code>, this 
 * class populates the errorCode & errorState with the SQL error code & SQL error state.
 * 
 * Copyright: Copyright (c) 2003
 * Company: Lincoln Life
 * 
 * @author psinghal
 * @version 
 */
public class DAOException extends BaseException
{
    public static final String ERROR_CODE_GENERAL_DAO = "ERROR_CODE_GENERAL_DAO";
    public static final String ERROR_STATE_GENERAL_DAO = "ERROR_STATE_GENERAL_DAO";

    public DAOException()
    {
        super();
        setErrorMessageKey(IMessageConstants.ERROR_DAO);
    }

    public DAOException(String exceptionMessage)
    {
        super(exceptionMessage);
 		setExceptionParameters();
 	}

    /**
     * Creates the exception using the source exception. If the source
     * exception is an instance of SQLException, the errorCode & error
     * state are populated with the SQL Code & SQL State.
     * @param sourceExceptionParam
     */
    public DAOException(Exception sourceExceptionParam)
    {
        super(sourceExceptionParam);
        
        //populate with SQL Code & SQL State if exception is of type SQLException
        
        if (sourceExceptionParam instanceof SQLException)
        {
            SQLException sqlException = (SQLException) sourceExceptionParam;
            setErrorCode(String.valueOf(sqlException.getErrorCode()));
            setErrorState(sqlException.getSQLState());
			setErrorMessageKey(IMessageConstants.ERROR_DAO);
        } else {
			setExceptionParameters();
        }
    }
    
	public DAOException(String source, String exceptionMessageParam, Exception sourceExceptionParam)
	{
		 this(exceptionMessageParam, sourceExceptionParam);
		 this.setSourceName(source);
	 }


    public DAOException(String exceptionMessageParam, Exception sourceExceptionParam)
    {
        super(exceptionMessageParam, sourceExceptionParam);
		if (sourceExceptionParam instanceof SQLException)
		{
			SQLException sqlException = (SQLException) sourceExceptionParam;
			setErrorCode(String.valueOf(sqlException.getErrorCode()));
			setErrorState(sqlException.getSQLState());
			setErrorMessageKey(IMessageConstants.ERROR_DAO);
		} else {
			setExceptionParameters();
		}
 
        
    }
	private void setExceptionParameters()
	{
	    setErrorCode(ERROR_CODE_GENERAL_DAO);
		setErrorCode(ERROR_STATE_GENERAL_DAO);
		setErrorMessageKey(IMessageConstants.ERROR_DAO);
		
	}

}
