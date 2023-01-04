package com.pas.nfl.DBObjects;

import java.io.Serializable;

public class TblTeam implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private Integer iteamId;
	private Integer idivisionId;
	private Integer playoffSeedInt;
	private String playoffSeedStr;
    private String vteamCity;
    private String vteamNickname;
    private String cteamCityAbbr;
    private String spictureFile;
    private Integer iFirstSeasonIDAsTeam;    
    private Integer iLastSeasonIDAsTeam;       
        
    private String fullTeamName;
    private TblDivision division;
    
    private boolean selectedInd; //used to pick teams on common games form

	public Integer getIteamId() {
        return this.iteamId;
    }

    public void setIteamId(Integer iteamId) {
        this.iteamId = iteamId;
    }

    public String getVteamCity() {
        return this.vteamCity;
    }

    public void setVteamCity(String vteamCity)
    {
        this.vteamCity = vteamCity;
        setFullTeamName();
    }

    public String getVteamNickname() {
        return this.vteamNickname;
    }

    public void setVteamNickname(String vteamNickname) 
    {
        this.vteamNickname = vteamNickname;
        setFullTeamName();
    }

    public String getCteamCityAbbr() {
        return this.cteamCityAbbr;
    }

    public void setCteamCityAbbr(String cteamCityAbbr) {
        this.cteamCityAbbr = cteamCityAbbr;
    }

   	public String getSpictureFile()
	{
		return spictureFile;
	}

	public void setSpictureFile(String pictureFile)
	{
		spictureFile = pictureFile;
	}
	
	public String getFullTeamName()
	{
		return fullTeamName;
	}

	public void setFullTeamName()
	{
		String teamName = "";
		if (vteamCity != null && vteamNickname != null)
		{
			teamName = vteamCity + " " + vteamNickname;
		}
		this.fullTeamName = teamName;
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

	public Integer getIdivisionId() {
		return idivisionId;
	}

	public void setIdivisionId(Integer idivisionId) {
		this.idivisionId = idivisionId;
	}
	
	public TblDivision getDivision() {
		return division;
	}

	public void setDivision(TblDivision division) {
		this.division = division;
	}

	public void setFullTeamName(String fullTeamName) {
		this.fullTeamName = fullTeamName;
	}

	public boolean isSelectedInd() {
		return selectedInd;
	}

	public void setSelectedInd(boolean selectedInd) {
		this.selectedInd = selectedInd;
	}

	public Integer getPlayoffSeedInt() {
		return playoffSeedInt;
	}

	public void setPlayoffSeedInt(Integer playoffSeedInt) {
		this.playoffSeedInt = playoffSeedInt;
	}

	public String getPlayoffSeedStr() {
		return playoffSeedStr;
	}

	public void setPlayoffSeedStr(String playoffSeedStr) {
		this.playoffSeedStr = playoffSeedStr;
	}

}
