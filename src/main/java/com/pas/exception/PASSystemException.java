package com.pas.exception;

/**
 * Title: SystemException
 * Project: Claims Replacement System
 * 
 * Description: Represents a critical system exception
 * 
 * Copyright: Copyright (c) 2003
 * Company: Lincoln Life
 * 
 * @author psinghal
 * @version 
 */
public class PASSystemException extends BaseException {

    public PASSystemException() {
        super();
    }

    public PASSystemException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public PASSystemException(Exception sourceExceptionParam) {
        super(sourceExceptionParam);
    }

    public PASSystemException(String exceptionMessageParam, Exception sourceExceptionParam) {
        super(exceptionMessageParam, sourceExceptionParam);
    }

}
