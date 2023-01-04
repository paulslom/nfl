package com.pas.nfl.valueObject;

import com.pas.valueObject.IValueObject;

public class Draft  implements IValueObject
{
	private Integer position;
	private Integer teamID;
	private String teamName;
	private Integer wins;
	private Integer losses;
	private Integer ties;
	private Integer opponentWins;
	private Integer opponentLosses;
	private Integer opponentTies;
	private String scheduleStrengthPct;
	private Integer playoffRoundExit; 
	
	//playoffRoundExit definition:
	//zero = non-playoff team
	//1 = lost in wild card round
	//2 = lost in divisionals
	//3 = lost in conference championship
	//4 = lost in super bowl (always picks 31st)
	//5 = won super bowl (always picks last, 32nd)
	//6 = in playoffs but has not played playoff game yet
	
	public String toString()
	{
		StringBuffer buf = new StringBuffer();

		buf.append("team = " + teamName + "\n");
		buf.append("Position = " + position + "\n");
							
		return buf.toString();
	}

	public Integer getPosition()
	{
		return position;
	}

	public void setPosition(Integer position)
	{
		this.position = position;
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

	public Integer getOpponentWins()
	{
		return opponentWins;
	}

	public void setOpponentWins(Integer opponentWins)
	{
		this.opponentWins = opponentWins;
	}

	public Integer getOpponentLosses()
	{
		return opponentLosses;
	}

	public void setOpponentLosses(Integer opponentLosses)
	{
		this.opponentLosses = opponentLosses;
	}

	public Integer getPlayoffRoundExit()
	{
		return playoffRoundExit;
	}

	public void setPlayoffRoundExit(Integer playoffRoundExit)
	{
		this.playoffRoundExit = playoffRoundExit;
	}

	public Integer getTeamID()
	{
		return teamID;
	}

	public void setTeamID(Integer teamID)
	{
		this.teamID = teamID;
	}

	public Integer getTies() {
		return ties;
	}

	public void setTies(Integer ties) {
		this.ties = ties;
	}

	public Integer getOpponentTies() {
		return opponentTies;
	}

	public void setOpponentTies(Integer opponentTies) {
		this.opponentTies = opponentTies;
	}

	public String getScheduleStrengthPct() {
		return scheduleStrengthPct;
	}

	public void setScheduleStrengthPct(String scheduleStrengthPct) {
		this.scheduleStrengthPct = scheduleStrengthPct;
	}

	
	
}
