package com.pas.util;

/**
 * Insert the type's description here.
 * Creation date: (11/3/00 1:19:01 PM)
 * @author: Administrator
 */
public class SocialSecurityNumber
{
	private String socialsecuritynumber;
/**
 * SocialSecurityNumberUtil constructor comment.
 */
public SocialSecurityNumber() {
	super();
	socialsecuritynumber = new String();
}
/**
 * Insert the method's description here.
 * Creation date: (11/3/00 3:43:46 PM)
 * @return java.lang.String
 */
public java.lang.String getSocialSecurityNumber() {
	return socialsecuritynumber;
}
/**
 * Insert the method's description here.
 * Creation date: (11/3/00 3:43:46 PM)
 * @param newSocialsecuritynumber java.lang.String
 */
public void setSocialSecurityNumber(java.lang.String newSocialsecuritynumber) {
	socialsecuritynumber = newSocialsecuritynumber;
}
/**
 * Insert the method's description here.
 * Creation date: (11/3/00 1:20:01 PM)
 * @param SSN3 java.lang.String
 * @param SSN2 java.lang.String
 * @param SSN4 java.lang.String
 */
public void setSocialSecurityNumber(String SSN3, String SSN2, String SSN4)
{
	setSocialSecurityNumber(SSN3.trim() + SSN2.trim() + SSN4.trim());
}
/**
 * Insert the method's description here.
 * Creation date: (11/6/00 7:56:53 AM)
 * @return java.lang.String
 */
public String toString() {
	return getSocialSecurityNumber();
}
}