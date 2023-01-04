package com.pas.nfl.valueObject;

import com.pas.valueObject.IValueObject;

public class Standings  implements IValueObject
{
	private String conferenceName;
	private String divisionName;
	private Integer teamID;
	private String teamName;
	private Integer wins;
	private Integer losses;
	private Integer ties;
	private Integer divisionLosses;
	private Integer conferenceLosses;
	private String divisionRecord;
	private String conferenceRecord;
	private String strengthOfVictoryRecord;
	private String strengthOfVictoryPct;
	private String gamesRemaining;
	
	
	public String getGamesRemaining()
	{
		return gamesRemaining;
	}
	public void setGamesRemaining(String gamesRemaining)
	{
		this.gamesRemaining = gamesRemaining;
	}
	public String getConferenceName()
	{
		return conferenceName;
	}
	public void setConferenceName(String conferenceName)
	{
		this.conferenceName = conferenceName;
	}
	public String getDivisionName()
	{
		return divisionName;
	}
	public void setDivisionName(String divisionName)
	{
		this.divisionName = divisionName;
	}
	public Integer getTeamID()
	{
		return teamID;
	}
	public void setTeamID(Integer teamID)
	{
		this.teamID = teamID;
	}
	public String getTeamName()
	{
		return teamName;
	}
	public void setTeamName(String teamName)
	{
		this.teamName = teamName;
	}
	public Integer getWins()
	{
		return wins;
	}
	public void setWins(Integer wins)
	{
		this.wins = wins;
	}
	public Integer getLosses()
	{
		return losses;
	}
	public void setLosses(Integer losses)
	{
		this.losses = losses;
	}
	public Integer getTies()
	{
		return ties;
	}
	public void setTies(Integer ties)
	{
		this.ties = ties;
	}
	public String getDivisionRecord()
	{
		return divisionRecord;
	}
	public void setDivisionRecord(String divisionRecord)
	{
		this.divisionRecord = divisionRecord;
	}
	public String getConferenceRecord()
	{
		return conferenceRecord;
	}
	public void setConferenceRecord(String conferenceRecord)
	{
		this.conferenceRecord = conferenceRecord;
	}
	public String getStrengthOfVictoryRecord()
	{
		return strengthOfVictoryRecord;
	}
	public void setStrengthOfVictoryRecord(String strengthOfVictoryRecord)
	{
		this.strengthOfVictoryRecord = strengthOfVictoryRecord;
	}
	public String getStrengthOfVictoryPct()
	{
		return strengthOfVictoryPct;
	}
	public void setStrengthOfVictoryPct(String strengthOfVictoryPct)
	{
		this.strengthOfVictoryPct = strengthOfVictoryPct;
	}
	public Integer getDivisionLosses()
	{
		return divisionLosses;
	}
	public void setDivisionLosses(Integer divisionLosses)
	{
		this.divisionLosses = divisionLosses;
	}
	public Integer getConferenceLosses()
	{
		return conferenceLosses;
	}
	public void setConferenceLosses(Integer conferenceLosses)
	{
		this.conferenceLosses = conferenceLosses;
	}
		
	
	
	
}
