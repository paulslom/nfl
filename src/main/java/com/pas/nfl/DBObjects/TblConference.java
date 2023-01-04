package com.pas.nfl.DBObjects;

import java.io.Serializable;

public class TblConference implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
	private Integer iconferenceId;
    private String vconferenceName;

    public Integer getIconferenceId() {
        return this.iconferenceId;
    }

    public void setIconferenceId(Integer iconferenceId) {
        this.iconferenceId = iconferenceId;
    }

    public String getVconferenceName() {
        return this.vconferenceName;
    }

    public void setVconferenceName(String vconferenceName) {
        this.vconferenceName = vconferenceName;
    }

}
