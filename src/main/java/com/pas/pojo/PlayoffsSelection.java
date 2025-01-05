package com.pas.pojo;

public class PlayoffsSelection
{
	private String function;
	private Integer seasonYear;
	private Integer seasonID;
	private Integer totalPlayoffTeamsByConf;
	private Integer playoffByesByConf;
	
	public Integer getSeasonYear()
	{
		return seasonYear;
	}

	public void setSeasonYear(Integer seasonYear)
	{
		this.seasonYear = seasonYear;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public Integer getTotalPlayoffTeamsByConf() {
		return totalPlayoffTeamsByConf;
	}

	public void setTotalPlayoffTeamsByConf(Integer totalPlayoffTeamsByConf) {
		this.totalPlayoffTeamsByConf = totalPlayoffTeamsByConf;
	}

	public Integer getPlayoffByesByConf() {
		return playoffByesByConf;
	}

	public void setPlayoffByesByConf(Integer playoffByesByConf) {
		this.playoffByesByConf = playoffByesByConf;
	}

	public Integer getSeasonID() {
		return seasonID;
	}

	public void setSeasonID(Integer seasonID) {
		this.seasonID = seasonID;
	}
	
}
