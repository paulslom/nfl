package com.pas.beans;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class NflPlayoffTeam implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(NflPlayoffTeam.class);	
	
	private String playoffTeamID;
	private Integer iseasonId;
	private Integer iConferenceId;
    private Integer iTeamID;
    private Integer iSeed;
    private Boolean bBye;
    
	public Integer getIseasonId() 
	{
		logger.debug("returning iseasonId");
		return iseasonId;
	}
	public void setIseasonId(Integer iseasonId) {
		this.iseasonId = iseasonId;
	}
	public Integer getiConferenceId() {
		return iConferenceId;
	}
	public void setiConferenceId(Integer iConferenceId) {
		this.iConferenceId = iConferenceId;
	}
	public Integer getiTeamID() {
		return iTeamID;
	}
	public void setiTeamID(Integer iTeamID) {
		this.iTeamID = iTeamID;
	}
	public Integer getiSeed() {
		return iSeed;
	}
	public void setiSeed(Integer iSeed) {
		this.iSeed = iSeed;
	}
	public Boolean getbBye() {
		return bBye;
	}
	public void setbBye(Boolean bBye) {
		this.bBye = bBye;
	}
	
	@DynamoDbPartitionKey
	public String getPlayoffTeamID() {
		return playoffTeamID;
	}
	public void setPlayoffTeamID(String playoffTeamID) {
		this.playoffTeamID = playoffTeamID;
	}
   	
}
