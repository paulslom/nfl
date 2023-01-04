package com.pas.nfl.DBObjects;

import java.io.Serializable;

public class TblSeason implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private Integer iseasonId;
    private String cyear;
    private String vsuperBowl;
    private Integer iConferencePlayoffTeams;
    private Integer iPlayoffByesByConf;

    public Integer getIseasonId() {
        return this.iseasonId;
    }

    public void setIseasonId(Integer iseasonId) {
        this.iseasonId = iseasonId;
    }

    public String getCyear() {
        return this.cyear;
    }

    public void setCyear(String cyear) {
        this.cyear = cyear;
    }

    public String getVsuperBowl() {
        return this.vsuperBowl;
    }

    public void setVsuperBowl(String vsuperBowl) {
        this.vsuperBowl = vsuperBowl;
    }

	public Integer getiConferencePlayoffTeams() {
		return iConferencePlayoffTeams;
	}

	public void setiConferencePlayoffTeams(Integer iConferencePlayoffTeams) {
		this.iConferencePlayoffTeams = iConferencePlayoffTeams;
	}

	public Integer getiPlayoffByesByConf() {
		return iPlayoffByesByConf;
	}

	public void setiPlayoffByesByConf(Integer iPlayoffByesByConf) {
		this.iPlayoffByesByConf = iPlayoffByesByConf;
	}  

}
