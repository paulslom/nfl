/*
 * Created on Mar 9, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.exception;

import com.pas.constants.IMessageConstants;

/**
 * @author SGanapathy
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PresentationException extends BaseException {
	public static final String ERROR_CODE_PRESENTATION = "ERROR_CODE_GENERAL_PRESENTATION";
	 public static final String ERROR_STATE_PRESENTATION = "ERROR_STATE_GENERAL_PRESENTATION";

	 public PresentationException() {
		 super();
		 setExceptionParameters();
	 }

	 /**
	  * @param exceptionMessage
	  */
	 public PresentationException(String exceptionMessage) {
		 super(exceptionMessage);
		 setExceptionParameters();
	 }

	 /**
	  * @param sourceExceptionParam
	  */
	 public PresentationException(Exception sourceExceptionParam) {
		 super(sourceExceptionParam);
		 setExceptionParameters();
	 }

	 /**
	  * @param exceptionMessageParam
	  * @param sourceExceptionParam
	  */
	 public PresentationException(String exceptionMessageParam, Exception sourceExceptionParam) {
		 super(exceptionMessageParam, sourceExceptionParam);
		 setExceptionParameters();
	 }
    
	 public PresentationException(String source, String exceptionMessageParam, Exception sourceExceptionParam) {
		  this(exceptionMessageParam, sourceExceptionParam);
		  this.setSourceName(source);
	  }

	 private void setExceptionParameters() {
		 setErrorCode(ERROR_CODE_PRESENTATION);
		 setErrorState(ERROR_STATE_PRESENTATION);
		 setErrorMessageKey(IMessageConstants.ERROR_PRESENTATION);
	 }

}
