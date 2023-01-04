package com.pas.nfl.DBObjects;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TblGame implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private Integer igameId;
    private Date dgameDateTime;
    private Integer iawayTeamID;
    private Integer ihomeTeamID;
    private Integer iawayTeamScore;
    private Integer ihomeTeamScore;
    private Integer iweekId;
    private Integer igameTypeId;

    //these next ones are not true game table fields but we need them here 
    private String gameDayOfWeek;
    private String gameDateOnly;
    private String gameTimeOnly;  
    
    private TblGameType gameType;
    private TblTeam awayTeam;
    private TblTeam homeTeam;
    private TblWeek week;   
    
    public Integer getIgameId() {
        return this.igameId;
    }

    public void setIgameId(Integer igameId) {
        this.igameId = igameId;
    }

    public Date getDgameDateTime() {
        return this.dgameDateTime;
    }

    public void setDgameDateTime(Date dgameDateTime)
    {
        this.dgameDateTime = dgameDateTime;
        setGameDayOfWeek(dgameDateTime);
        setGameDateOnly(dgameDateTime);
        setGameTimeOnly(dgameDateTime);
    }

    public String getGameDayOfWeek() {
		return gameDayOfWeek;
	}

	public void setGameDayOfWeek(Date dgameDateTime)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("EEE");
		this.gameDayOfWeek = sdf.format(dgameDateTime);
	}

	public String getGameDateOnly() {
		return gameDateOnly;
	}

	public void setGameDateOnly(Date dgameDateTime)
	{		
		SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
		this.gameDateOnly = sdf.format(dgameDateTime);
	}

	public String getGameTimeOnly() {
		return gameTimeOnly;
	}

	public void setGameTimeOnly(Date dgameDateTime)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
		this.gameTimeOnly = sdf.format(dgameDateTime);
	}

    public Integer getIawayTeamScore() {
        return this.iawayTeamScore;
    }

    public void setIawayTeamScore(Integer iawayTeamScore) {
        this.iawayTeamScore = iawayTeamScore;
    }

    public Integer getIhomeTeamScore() {
        return this.ihomeTeamScore;
    }

    public void setIhomeTeamScore(Integer ihomeTeamScore) {
        this.ihomeTeamScore = ihomeTeamScore;
    }

	public Integer getIawayTeamID() {
		return iawayTeamID;
	}

	public void setIawayTeamID(Integer iawayTeamID) {
		this.iawayTeamID = iawayTeamID;
	}

	public Integer getIhomeTeamID() {
		return ihomeTeamID;
	}

	public void setIhomeTeamID(Integer ihomeTeamID) {
		this.ihomeTeamID = ihomeTeamID;
	}

	public Integer getIweekId() {
		return iweekId;
	}

	public void setIweekId(Integer iweekId) {
		this.iweekId = iweekId;
	}

	public Integer getIgameTypeId() {
		return igameTypeId;
	}

	public void setIgameTypeId(Integer igameTypeId) {
		this.igameTypeId = igameTypeId;
	}

	public TblGameType getGameType() {
		return gameType;
	}

	public void setGameType(TblGameType gameType) {
		this.gameType = gameType;
	}

	public TblTeam getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(TblTeam awayTeam) {
		this.awayTeam = awayTeam;
	}

	public TblTeam getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(TblTeam homeTeam) {
		this.homeTeam = homeTeam;
	}

	public TblWeek getWeek() {
		return week;
	}

	public void setWeek(TblWeek week) {
		this.week = week;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setGameDayOfWeek(String gameDayOfWeek) {
		this.gameDayOfWeek = gameDayOfWeek;
	}

	public void setGameDateOnly(String gameDateOnly) {
		this.gameDateOnly = gameDateOnly;
	}

	public void setGameTimeOnly(String gameTimeOnly) {
		this.gameTimeOnly = gameTimeOnly;
	}	

}
