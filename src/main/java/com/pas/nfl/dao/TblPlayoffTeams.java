package com.pas.nfl.dao;

import java.io.Serializable;

public class TblPlayoffTeams implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private Integer iseasonId;
	private Integer iConferenceId;
    private Integer iTeamID;
    private Integer iSeed;
    private Boolean bBye;

    public Integer getIseasonId() {
        return this.iseasonId;
    }

    public void setIseasonId(Integer iseasonId) {
        this.iseasonId = iseasonId;
    }

	public Integer getiTeamID() {
		return iTeamID;
	}

	public void setiTeamID(Integer iTeamID) {
		this.iTeamID = iTeamID;
	}

	public Integer getiSeed() {
		return iSeed;
	}

	public void setiSeed(Integer iSeed) {
		this.iSeed = iSeed;
	}

	public Boolean getbBye() {
		return bBye;
	}

	public void setbBye(Boolean bBye) {
		this.bBye = bBye;
	}

	public Integer getiConferenceId() {
		return iConferenceId;
	}

	public void setiConferenceId(Integer iConferenceId) {
		this.iConferenceId = iConferenceId;
	}

   
}
