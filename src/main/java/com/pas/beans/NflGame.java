package com.pas.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.dynamodb.DateToStringConverter;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
public class NflGame implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(NflGame.class);	
	
	private Integer igameId;
    private String dgameDateTime;
    private Integer iawayTeamID;
    private String cawayteamCityAbbr;
    private String awayteamName;
    private Integer ihomeTeamID;
    private String chometeamCityAbbr;
    private String hometeamName;
    private Integer iawayTeamScore;
    private Integer ihomeTeamScore;
    private Integer iweekId;
    private Integer igameTypeId;
    private String sgameTypeDesc;
    private Integer iplayoffRound;
    private Integer iSeasonId;
    private Integer iweekNumber;
    private String sweekDescription;
    
    //these next ones are not true game table fields but we need them here 
    private String gameDayOfWeek;
    private String gameDateOnly;
    private String gameTimeOnly;
    
    public String toString()
    {
    	return "seasonid: " + iSeasonId + " game date: " + dgameDateTime + " " + cawayteamCityAbbr + " @ " + chometeamCityAbbr;
    }
    
    @DynamoDbPartitionKey
	public Integer getIgameId() 
    {
    	logger.debug("returning igameid");
		return igameId;
	}
	public void setIgameId(Integer igameId) {
		this.igameId = igameId;
	}
	@DynamoDbSecondaryPartitionKey(indexNames = "gsi_GameDate")
	public String getDgameDateTime() {
		return dgameDateTime;
	}
	public void setDgameDateTime(String dgameDateTime) 
	{
		this.dgameDateTime = dgameDateTime;
	}
	public Integer getIawayTeamID() {
		return iawayTeamID;
	}
	public void setIawayTeamID(Integer iawayTeamID) {
		this.iawayTeamID = iawayTeamID;
	}
	public String getCawayteamCityAbbr() {
		return cawayteamCityAbbr;
	}
	public void setCawayteamCityAbbr(String cawayteamCityAbbr) {
		this.cawayteamCityAbbr = cawayteamCityAbbr;
	}
	public Integer getIhomeTeamID() {
		return ihomeTeamID;
	}
	public void setIhomeTeamID(Integer ihomeTeamID) {
		this.ihomeTeamID = ihomeTeamID;
	}
	public String getChometeamCityAbbr() {
		return chometeamCityAbbr;
	}
	public void setChometeamCityAbbr(String chometeamCityAbbr) {
		this.chometeamCityAbbr = chometeamCityAbbr;
	}
	public Integer getIawayTeamScore() {
		return iawayTeamScore;
	}
	public void setIawayTeamScore(Integer iawayTeamScore) {
		this.iawayTeamScore = iawayTeamScore;
	}
	public Integer getIhomeTeamScore() {
		return ihomeTeamScore;
	}
	public void setIhomeTeamScore(Integer ihomeTeamScore) {
		this.ihomeTeamScore = ihomeTeamScore;
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
	public String getSgameTypeDesc() {
		return sgameTypeDesc;
	}
	public void setSgameTypeDesc(String sgameTypeDesc) {
		this.sgameTypeDesc = sgameTypeDesc;
	}
	public Integer getIplayoffRound() {
		return iplayoffRound;
	}
	public void setIplayoffRound(Integer iplayoffRound) {
		this.iplayoffRound = iplayoffRound;
	}
	public Integer getiSeasonId() {
		return iSeasonId;
	}
	public void setiSeasonId(Integer iSeasonId) {
		this.iSeasonId = iSeasonId;
	}
	public Integer getIweekNumber() {
		return iweekNumber;
	}
	public void setIweekNumber(Integer iweekNumber) {
		this.iweekNumber = iweekNumber;
	}
	public String getSweekDescription() {
		return sweekDescription;
	}
	public void setSweekDescription(String sweekDescription) {
		this.sweekDescription = sweekDescription;
	}
	
	public String getGameDayOfWeek() 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("EEE");
		Date gameDate = DateToStringConverter.unconvert(this.getDgameDateTime());
		this.setGameDayOfWeek(sdf.format(gameDate));
		return gameDayOfWeek;
	}
	public void setGameDayOfWeek(String gameDayOfWeek) 
	{		
		this.gameDayOfWeek = gameDayOfWeek;
	}
	public String getGameDateOnly() 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
		Date gameDate = DateToStringConverter.unconvert(this.getDgameDateTime());
		this.setGameDateOnly(sdf.format(gameDate));
		return gameDateOnly;
	}	
	public void setGameDateOnly(String gameDateOnly) 
	{		
		this.gameDateOnly = gameDateOnly;
	}
	
	public String getGameTimeOnly() 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
		Date gameDate = DateToStringConverter.unconvert(this.getDgameDateTime());
		this.setGameTimeOnly(sdf.format(gameDate));
		return gameTimeOnly;
	}
	public void setGameTimeOnly(String gameTimeOnly) 
	{
		this.gameTimeOnly = gameTimeOnly;
	}

	public String getAwayteamName() {
		return awayteamName;
	}

	public void setAwayteamName(String awayteamName) {
		this.awayteamName = awayteamName;
	}

	public String getHometeamName() {
		return hometeamName;
	}

	public void setHometeamName(String hometeamName) {
		this.hometeamName = hometeamName;
	}
	
}
