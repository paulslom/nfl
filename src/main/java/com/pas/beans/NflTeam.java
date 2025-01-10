package com.pas.beans;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class NflTeam implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(NflTeam.class);	
	
	private Integer iTeamID;
	private Integer iDivisionID;
    private String vTeamCity;
    private String vTeamNickname;
    private String cTeamCityAbbr;
    private Integer iFirstSeasonIDAsTeam;
    private Integer iLastSeasonIDAsTeam;
    private String sPictureFile;
    private Integer iConferenceID;
    private String vDivisionLifespan;
    private String vDivisionName;
    private String vConferenceName;    
    
    private String fullTeamName;
    
    @DynamoDbPartitionKey
	public Integer getiTeamID() 
    {
    	logger.debug("returning iTeamID");
		return iTeamID;
	}
	public void setiTeamID(Integer iTeamID) {
		this.iTeamID = iTeamID;
	}
	public Integer getiDivisionID() {
		return iDivisionID;
	}
	public void setiDivisionID(Integer iDivisionID) {
		this.iDivisionID = iDivisionID;
	}
	public String getvTeamCity() {
		return vTeamCity;
	}
	public void setvTeamCity(String vTeamCity) {
		this.vTeamCity = vTeamCity;
	}
	public String getvTeamNickname() {
		return vTeamNickname;
	}
	public void setvTeamNickname(String vTeamNickname) {
		this.vTeamNickname = vTeamNickname;
	}
	public String getcTeamCityAbbr() {
		return cTeamCityAbbr;
	}
	public void setcTeamCityAbbr(String cTeamCityAbbr) {
		this.cTeamCityAbbr = cTeamCityAbbr;
	}
	public Integer getiFirstSeasonIDAsTeam() {
		return iFirstSeasonIDAsTeam;
	}
	public void setiFirstSeasonIDAsTeam(Integer iFirstSeasonIDAsTeam) {
		this.iFirstSeasonIDAsTeam = iFirstSeasonIDAsTeam;
	}
	public Integer getiLastSeasonIDAsTeam() {
		return iLastSeasonIDAsTeam;
	}
	public void setiLastSeasonIDAsTeam(Integer iLastSeasonIDAsTeam) {
		this.iLastSeasonIDAsTeam = iLastSeasonIDAsTeam;
	}
	public String getsPictureFile() {
		return sPictureFile;
	}
	public void setsPictureFile(String sPictureFile) {
		this.sPictureFile = sPictureFile;
	}
	public Integer getiConferenceID() {
		return iConferenceID;
	}
	public void setiConferenceID(Integer iConferenceID) {
		this.iConferenceID = iConferenceID;
	}
	public String getvDivisionLifespan() {
		return vDivisionLifespan;
	}
	public void setvDivisionLifespan(String vDivisionLifespan) {
		this.vDivisionLifespan = vDivisionLifespan;
	}
	public String getvDivisionName() {
		return vDivisionName;
	}
	public void setvDivisionName(String vDivisionName) {
		this.vDivisionName = vDivisionName;
	}
	public String getvConferenceName() {
		return vConferenceName;
	}
	public void setvConferenceName(String vConferenceName) {
		this.vConferenceName = vConferenceName;
	}
    
    public String getFullTeamName()
    {    	
    	return fullTeamName;
    }
    
	public void setFullTeamName() 
	{
		this.fullTeamName = this.getvTeamCity() + " " + this.getvTeamNickname();
	}
	
	public void setFullTeamName(String fullTeamName) 
	{
		this.fullTeamName = fullTeamName;
	}
	
}
