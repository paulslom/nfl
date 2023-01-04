package com.pas.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.valueObject.User;
import com.pas.constants.IMessageConstants;
import com.pas.util.UniqueIDGenerator;

/**
 * Title: BaseException
 *
 *
 * Description: Forms the base class for the application's exception hierarchy.
 * This class provides the following common functionality
 * 1. Source Exception: Allows this instance to carry a reference to another exception
 * that may have occurred necessiating the creation of this custom exception.
 * 2. Error Code: A custom code representing the exception
 * 3. Error State: A custom code to further qualify the error code, if required.
 * 4. Error Message Key: Represents the Struts error message key (Referenced in the ApplicationResources)
 *
 * Copyright: Copyright (c) 2003
 * Company: Lincoln Life
 *
 * @author psinghal
 * @version
 */
public class BaseException extends Exception
{
    private Exception sourceException = null;
    private String errorMessageKey = IMessageConstants.ERROR_UNEXPECTED;
    private String errorCode = null; //this is for SQL Exceptions - SQL Code
    private String errorState = null; //this is for SQL Exceptions - SQL State
    private ExceptionCategory exceptionCategory=ExceptionCategory.FATAL;
    private String sourceName="not set";
	private String msgId=UniqueIDGenerator.generateUniqueID();
	private User userObj;

    /**
     * Creates the BaseException in native state.
     */
    public BaseException()
    {
        super();
    }

    /**
     * Creates the BaseException in native state, but with an
     * exception message.
     * @param s
     */
    public BaseException(String exceptionMessage)
    {
        super(exceptionMessage);
    }

	/**
	 * Creates the BaseException with a source exception
	 * @param exceptionMessageParam
	 * @param sourceExceptionParam
	 */
	public BaseException(Exception sourceExceptionParam)
	{
		this(sourceExceptionParam.getMessage());
		this.sourceException = sourceExceptionParam;
	}

    /**
     * Creates the BaseException with a source exception & an exception message
     * @param exceptionMessageParam
     * @param sourceExceptionParam
     */
    public BaseException(String exceptionMessageParam, Exception sourceExceptionParam)
    {
        this(exceptionMessageParam);
        this.sourceException = sourceExceptionParam;
    }

	public BaseException(String source, String exceptionMessageParam, Exception sourceExceptionParam)
	{
		 this(exceptionMessageParam);
		 this.sourceException = sourceExceptionParam;
		 sourceName=source;
	 }

     /**
     * Returns the Struts error message key associated with the exception.
     * This key must exist in the ApplicationResources file.
     *
     * @return String
     */
    public String getErrorMessageKey() {
        return errorMessageKey;
    }

    /**
     * Returns the source exception that caused this BaseException
     * instance to be thrown
     * @return <code>Exception</code> source exception
     */
    public Exception getSourceException() {
        return sourceException;
    }

     /**
     * Allows for setting of a Struts error message key on the exception
     * This key should be made available in the ApplicationResources file
     *
     * @param string
     */
    public void setErrorMessageKey(String string)
    {
        errorMessageKey = string;
    }

    /**
	 * @return
	 */
	public ExceptionCategory getExceptionCategory()
	{
		return exceptionCategory;
	}

	/**
	 * @return
	 */
	public String getSourceName()
	{
		return sourceName;
	}

	public void logException(Logger log)
	{
		log.error("********************************************************");
		log.error("Exception ID ::" + msgId);
		
		log.error("Source of exception ::" + this.getSourceName());
		log.error("Message Text:: " + this.getMessage());
		
		if(this.getErrorCode()!=null)
        {
            log.error("Error Code ::" + this.getErrorCode());
        }
		
		if(this.getErrorState()!=null)
        {
            log.error("Error State ::" + this.getErrorState());
        }
		
        if(sourceException!=null)
        {
            log.error( sourceException.getMessage(), sourceException);
        }
		log.error("********************************************************");
	}

	/**
	 * @param category
	 */
	public void setExceptionCategory(ExceptionCategory category) {
		exceptionCategory = category;
	}

	/**
	 * @param exception
	 */
	public void setSourceException(Exception exception) {
		sourceException = exception;
	}

	/**
	 * @param string
	 */
	public void setSourceName(String string) {
		sourceName = string;
	}

	/**
	 * @return
	 */
	public String getMsgId() {
		return msgId;
	}

	/**
	 * @return
	 */
	public User getUser() {
		return userObj;
	}

	/**
	 * @param user
	 */
	public void setUser(User user) {
		userObj = user;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	public String getErrorState()
	{
		return errorState;
	}

	public void setErrorState(String errorState)
	{
		this.errorState = errorState;
	}

}
