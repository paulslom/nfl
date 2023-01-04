package com.pas.nfl.valueObject;

import com.pas.valueObject.IValueObject;

public class GameSelection  implements IValueObject
{
	private Integer seasonYear;
	private Integer weekNumber;
	private Integer teamID;
	private Boolean playoffsOnly = false;
	
	public String toString()
	{
		StringBuffer buf = new StringBuffer();

		buf.append("seasonYear = " + seasonYear + "\n");
		buf.append("week Number = " + weekNumber + "\n");
		buf.append("Team ID = " + teamID + "\n");
							
		return buf.toString();
	}

	

	public Integer getSeasonYear()
	{
		return seasonYear;
	}



	public void setSeasonYear(Integer seasonYear)
	{
		this.seasonYear = seasonYear;
	}



	public Integer getWeekNumber()
	{
		return weekNumber;
	}

	public void setWeekNumber(Integer weekNumber)
	{
		this.weekNumber = weekNumber;
	}

	public Integer getTeamID()
	{
		return teamID;
	}

	public void setTeamID(Integer teamID)
	{
		this.teamID = teamID;
	}



	public Boolean getPlayoffsOnly() {
		return playoffsOnly;
	}



	public void setPlayoffsOnly(Boolean playoffsOnly) {
		this.playoffsOnly = playoffsOnly;
	}

	
}
