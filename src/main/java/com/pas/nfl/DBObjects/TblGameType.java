package com.pas.nfl.DBObjects;

import java.io.Serializable;

public class TblGameType implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private Integer igameTypeId;
    private String sgameTypeDesc;
    private Integer iplayoffRound;

    public Integer getIgameTypeId() {
        return this.igameTypeId;
    }

    public void setIgameTypeId(Integer igameTypeId) {
        this.igameTypeId = igameTypeId;
    }

    public String getSgameTypeDesc() {
        return this.sgameTypeDesc;
    }

    public void setSgameTypeDesc(String sgameTypeDesc) {
        this.sgameTypeDesc = sgameTypeDesc;
    }

    public Integer getIplayoffRound() {
        return this.iplayoffRound;
    }

    public void setIplayoffRound(Integer iplayoffRound) {
        this.iplayoffRound = iplayoffRound;
    }   

}
