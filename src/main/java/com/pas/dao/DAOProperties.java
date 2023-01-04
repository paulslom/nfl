/*
 * Created on May 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.dao;

/**
 * @author SGanapathy
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DAOProperties {
	
	private String daoName;
	private boolean interactWithDB;
	

	/**
	 * @return
	 */
	public boolean isInteractWithDB() {
		return interactWithDB;
	}

	/**
	 * @param b
	 */
	public void setInteractWithDB(boolean b) {
		interactWithDB = b;
	}

	/**
	 * @return Returns the daoName.
	 */
	public String getDaoName() {
		return daoName;
	}
	/**
	 * @param daoName The daoName to set.
	 */
	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}
}
