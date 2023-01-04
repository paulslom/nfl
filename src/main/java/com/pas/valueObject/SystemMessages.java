package com.pas.valueObject;

import com.pas.valueObject.IValueObject;

/**
 * Title: 		ContractInfo
 * Project: 	Claims Replacement System
 * Description: Contract Information Value Object
 * Copyright: 	Copyright (c) 2003
 * Company: 	Lincoln Life
 * @author 		CGI
 * @version 	1.0
 */
public class SystemMessages implements IValueObject {

	private String[] sysMessages;
	
	/**
	 * constructor
	 */
	public SystemMessages() {
		super();
	}
	
	
	
	/**
	 * @return Returns the sysMessages.
	 */
	public String[] getSysMessages() {
		return sysMessages;
	}
	/**
	 * @param sysMessages The sysMessages to set.
	 */
	public void setSysMessages(String[] sysMessages) {
		this.sysMessages = sysMessages;
	}
}
