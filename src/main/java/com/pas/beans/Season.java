package com.pas.beans;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Season implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(Season.class);	
		
	private Integer iSeasonID;
    private String cYear;
    private String vSuperBowl;
    private Integer iConferencePlayoffTeams;
    private Integer iPlayoffByesByConf;
	
    public Integer getiSeasonID() {
		return iSeasonID;
	}
	public void setiSeasonID(Integer iSeasonID) {
		this.iSeasonID = iSeasonID;
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
	public String getcYear() {
		return cYear;
	}
	public void setcYear(String cYear) {
		this.cYear = cYear;
	}
	public String getvSuperBowl() {
		return vSuperBowl;
	}
	public void setvSuperBowl(String vSuperBowl) {
		this.vSuperBowl = vSuperBowl;
	}	
	
}
