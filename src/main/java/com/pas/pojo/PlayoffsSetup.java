package com.pas.pojo;

public class PlayoffsSetup
{
	private Integer seed;
	private Integer conferenceID;
	private String conferenceName;
	private Integer teamID;
	private String teamPic;
	private String teamAbbr;
	private Boolean bye;
	
	public Integer getSeed() {
		return seed;
	}
	public void setSeed(Integer seed) {
		this.seed = seed;
	}
	public Integer getTeamID() {
		return teamID;
	}
	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}
	public Boolean getBye() {
		return bye;
	}
	public void setBye(Boolean bye) {
		this.bye = bye;
	}
	public String getTeamPic() {
		return teamPic;
	}
	public void setTeamPic(String teamPic) {
		this.teamPic = teamPic;
	}
	public String getTeamAbbr() {
		return teamAbbr;
	}
	public void setTeamAbbr(String teamAbbr) {
		this.teamAbbr = teamAbbr;
	}
	public Integer getConferenceID() {
		return conferenceID;
	}
	public void setConferenceID(Integer conferenceID) {
		this.conferenceID = conferenceID;
	}
	public String getConferenceName() {
		return conferenceName;
	}
	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}
	
	
}
