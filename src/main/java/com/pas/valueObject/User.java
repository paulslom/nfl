package com.pas.valueObject;

import com.pas.util.PASUtil;


/**
 * Title: 		User
 * Description: Represents a user of the system
 * Copyright: 	Copyright (c) 2003
 * Company: 	Lincoln Life
 * @author 		CGI
 * @version 	1.0
 */
public class User implements IValueObject
{
    private String firstName;
    private String lastName;
    private String securityLevel;
    private String securityLevelDesc;
    private String userId;
    private String sessionId;
   	private String password;
	
    /**
     * constructor 
     *
     */
    public User() {
        super();
    }

    /**
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return String
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @return String
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @return String
     */
    public String getSecurityLevel() {
        return securityLevel;
    }

    /**
     * @param string
     */
    public void setFirstName(String string) {
        firstName = string;
    }

    /**
     * @param string
     */
    public void setLastName(String string) {
        lastName = string;
    }

    /**
     * @param String
     */
    public void setSecurityLevel(String i) {
        securityLevel = i;
    }

    /**
     * @param String
     */
    public void setSessionId(String l) {
        sessionId = l;
    }

    /**
     * @param String
     */
    public void setUserId(String s) {
        userId = s;
    }

    /**
	 * helper method to get Name in the display format
	 * @return string
	 */
    public String getDisplayName()
    {
    	return PASUtil.formatNameForDisplay(firstName, lastName, "", "");
    }
    
	/**
	 * debug method to display the contents of the user object
	 * @return string
	 */
    public String toString()
    {
    	StringBuffer sbuf = new StringBuffer();
    	
		sbuf.append("\n User First Name      = " + firstName);
		sbuf.append("\n User Last Name       = " + lastName);
		sbuf.append("\n User Security Num    = " + securityLevel);
		sbuf.append("\n User Security Desc   = " + securityLevelDesc);
		sbuf.append("\n User Id              = " + userId);
		sbuf.append("\n User Session ID      = " + sessionId);
				
    	return sbuf.toString();
    }

    /**
     * @return String
     */
    public String getSecurityLevelDesc() {
        return securityLevelDesc;
    }

    /**
     * @param string
     */
    public void setSecurityLevelDesc(String string) {
        securityLevelDesc = string;
    }

    /**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

}