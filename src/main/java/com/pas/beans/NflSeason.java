package com.pas.beans;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class NflSeason implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(NflSeason.class);	
	
	private Integer iSeasonID;
    private String cYear;
    private Integer intYear;
    private String vSuperBowl;
    private Integer iConferencePlayoffTeams;
    private Integer iPlayoffByesByConf;
    
    @DynamoDbPartitionKey
	public Integer getiSeasonID() {
		return iSeasonID;
	}
	public void setiSeasonID(Integer iSeasonID) {
		this.iSeasonID = iSeasonID;
	}
	public String getcYear() {
		return cYear;
	}
	public void setcYear(String cYear) {
		this.cYear = cYear;
	}
	public String getvSuperBowl() {
		return vSuperBowl;
	}
	public void setvSuperBowl(String vSuperBowl) {
		this.vSuperBowl = vSuperBowl;
	}
	public Integer getiConferencePlayoffTeams() {
		return iConferencePlayoffTeams;
	}
	public void setiConferencePlayoffTeams(Integer iConferencePlayoffTeams) {
		this.iConferencePlayoffTeams = iConferencePlayoffTeams;
	}
	public Integer getiPlayoffByesByConf() {
		return iPlayoffByesByConf;
	}
	public void setiPlayoffByesByConf(Integer iPlayoffByesByConf) {
		this.iPlayoffByesByConf = iPlayoffByesByConf;
	}
	public Integer getIntYear() 
	{		
		return Integer.parseInt(cYear);
	}
	public void setIntYear(Integer intYear) {
		this.intYear = intYear;
	}
 	
	
	
}
