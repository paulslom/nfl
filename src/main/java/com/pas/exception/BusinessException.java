package com.pas.exception;

import com.pas.constants.IMessageConstants;

/**
 * Title: BusinessException
 * Project: Claims Replacement System
 * 
 * Description: Represents business logic related exceptions originating in the 
 * business layer.
 * 
 * Copyright: Copyright (c) 2003
 * Company: Lincoln Life
 * 
 * @author psinghal
 * @version 
 */
public class BusinessException extends BaseException {

    public static final String ERROR_CODE_GENERAL_BUSINESS = "ERROR_CODE_GENERAL_BUSINESS";
    public static final String ERROR_STATE_GENERAL_BUSINESS = "ERROR_STATE_GENERAL_BUSINESS";

    public BusinessException() {
        super();
		setExceptionParameters();
    }

    /**
     * @param exceptionMessage
     */
    public BusinessException(String exceptionMessage) {
        super(exceptionMessage);
		setExceptionParameters();
    }

    /**
     * @param sourceExceptionParam
     */
    public BusinessException(Exception sourceExceptionParam) {
        super(sourceExceptionParam);
		setExceptionParameters();
    }

    /**
     * @param exceptionMessageParam
     * @param sourceExceptionParam
     */
    public BusinessException(String exceptionMessageParam, Exception sourceExceptionParam) {
        super(exceptionMessageParam, sourceExceptionParam);
		setExceptionParameters();
    }
    
	public BusinessException(String source, String exceptionMessageParam, Exception sourceExceptionParam) {
		 this(exceptionMessageParam, sourceExceptionParam);
		 this.setSourceName(source);
	 }

	private void setExceptionParameters()
	{
		setErrorMessageKey(IMessageConstants.ERROR_BUSINESS);
	}
    

}
