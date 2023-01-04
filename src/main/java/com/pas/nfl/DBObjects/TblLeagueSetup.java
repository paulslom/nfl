package com.pas.nfl.DBObjects;

import java.io.Serializable;

public class TblLeagueSetup implements Serializable 
{   
    private static final long serialVersionUID = 1L;
    
	private Integer iLeagueSetupId;
    private Integer iSeasonId;
    private Integer iDivisionId;
    
	public Integer getiLeagueSetupId() {
		return iLeagueSetupId;
	}
	public void setiLeagueSetupId(Integer iLeagueSetupId) {
		this.iLeagueSetupId = iLeagueSetupId;
	}
	public Integer getiSeasonId() {
		return iSeasonId;
	}
	public void setiSeasonId(Integer iSeasonId) {
		this.iSeasonId = iSeasonId;
	}
	public Integer getiDivisionId() {
		return iDivisionId;
	}
	public void setiDivisionId(Integer iDivisionId) {
		this.iDivisionId = iDivisionId;
	}
}
