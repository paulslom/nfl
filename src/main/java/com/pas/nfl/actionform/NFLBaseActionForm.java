/*
 * Created on Mar 8, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.nfl.actionform;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.valueObject.User;
import com.pas.actionform.BaseActionForm;

/**
 * @author sganapathy
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NFLBaseActionForm extends BaseActionForm {
	
//	private LfgMessageDocument lfgMsgDoc;
	private User user;
	private String systemMsgs="";
	Logger log = LogManager.getLogger(this.getClass());
	private boolean systemMsgsFlag;
	

	public NFLBaseActionForm(){
	}
	

	/**
	 * @return
	 */
	public User getUser() {
		return user;
	}


	/**
	 * @param user
	 */
	public void setUser(User userObject) {
		user = userObject;
	}

	/**
	 * @return
	 */
	public String getSystemMsgs() {
		return systemMsgs;
	}
	
	/**
	 * @return
	 */
	public void setSystemMsgs(String systemMsgs) {
		this.systemMsgs = systemMsgs;
	}

	/**
	 * @return Returns the systemMsgsFlag.
	 */
	public boolean isSystemMsgsFlag() {
		return systemMsgsFlag;
	}
	/**
	 * @param systemMsgsFlag The systemMsgsFlag to set.
	 */
	public void setSystemMsgsFlag(boolean systemMsgsFlag) {
		this.systemMsgsFlag = systemMsgsFlag;
	}
}
