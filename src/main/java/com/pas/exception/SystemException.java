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
public class SystemException extends BaseException {

    public SystemException() {
        super();
    }

    public SystemException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public SystemException(Exception sourceExceptionParam) {
        super(sourceExceptionParam);
    }

    public SystemException(String exceptionMessageParam, Exception sourceExceptionParam) {
        super(exceptionMessageParam, sourceExceptionParam);
    }

}
