package com.pas.exception;

/**
 * Insert the type's description here.
 * Creation date: (10/9/00 4:01:41 PM)
 * @author: Administrator
 */
public class EDateFormatNotValid extends BaseException 
{
   /**
    * EDateFormatNotValid constructor comment.
    */
   public EDateFormatNotValid()
   {
	  super();
	  setErrorCode("Date must be  mm/dd/yyyy.");	  
   }
}