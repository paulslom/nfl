package com.pas.nfl.DBObjects;

import java.io.Serializable;

public class TblWeek implements Serializable 
{    
    private static final long serialVersionUID = 1L;
    
	private Integer iweekId;
    private Integer iSeasonId;
    private Integer iweekNumber;
    private String sweekDescription;

    public Integer getIweekId() {
        return this.iweekId;
    }

    public void setIweekId(Integer iweekId) {
        this.iweekId = iweekId;
    }

    public Integer getIweekNumber() {
        return this.iweekNumber;
    }

    public void setIweekNumber(Integer iweekNumber) {
        this.iweekNumber = iweekNumber;
    }

    public String getSweekDescription() {
        return this.sweekDescription;
    }

    public void setSweekDescription(String sweekDescription) {
        this.sweekDescription = sweekDescription;
    }

	public Integer getiSeasonId() {
		return iSeasonId;
	}

	public void setiSeasonId(Integer iSeasonId) {
		this.iSeasonId = iSeasonId;
	}

    

}
