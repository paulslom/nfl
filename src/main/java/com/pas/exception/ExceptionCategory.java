/*
 * Created on Mar 9, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.exception;

/**
 * @author SGanapathy
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ExceptionCategory {
	public static final ExceptionCategory INFO = new ExceptionCategory(1);
	public static final ExceptionCategory WARNING = new ExceptionCategory(2);
	public static final ExceptionCategory GENERAL_ISSUE = new ExceptionCategory(3);
	public static final ExceptionCategory DATA_ISSUE = new ExceptionCategory(4);
	public static final ExceptionCategory CONFIG_ISSUE = new ExceptionCategory(5);
	public static final ExceptionCategory FATAL= new ExceptionCategory(6);
	
	int exceptionCategory;
	private ExceptionCategory (int i){
		exceptionCategory = i;
		
	}

}
