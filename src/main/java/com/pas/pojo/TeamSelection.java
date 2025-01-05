package com.pas.pojo;

public class TeamSelection
{
	private String seasonYear;
	private String teamSelectionType;
	private Integer conferenceID;
	
	public String toString()
	{
		StringBuffer buf = new StringBuffer();

		buf.append("seasonYear = " + seasonYear + "\n");
								
		return buf.toString();
	}

	public String getSeasonYear()
	{
		return seasonYear;
	}
	
	public void setSeasonYear(String seasonYear)
	{
		this.seasonYear = seasonYear;
	}

	public String getTeamSelectionType()
	{
		return teamSelectionType;
	}

	public void setTeamSelectionType(String teamSelectionType)
	{
		this.teamSelectionType = teamSelectionType;
	}

	public Integer getConferenceID() {
		return conferenceID;
	}

	public void setConferenceID(Integer conferenceID) {
		this.conferenceID = conferenceID;
	}


}
