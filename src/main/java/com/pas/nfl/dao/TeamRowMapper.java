package com.pas.nfl.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pas.nfl.DBObjects.TblConference;
import com.pas.nfl.DBObjects.TblDivision;
import com.pas.nfl.DBObjects.TblTeam;

public class TeamRowMapper implements RowMapper<TblTeam>, Serializable 
{
    private static final long serialVersionUID = 1L;

	@Override
    public TblTeam mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
		TblTeam team = new TblTeam();
    	
		team.setIteamId(rs.getInt("iTeamID"));
		team.setIdivisionId(rs.getInt("iDivisionID"));
		team.setiFirstSeasonIDAsTeam(rs.getInt("iFirstSeasonIDAsTeam"));
		team.setiLastSeasonIDAsTeam(rs.getInt("iLastSeasonIDAsTeam"));
		team.setVteamCity(rs.getString("vteamCity"));
		team.setVteamNickname(rs.getString("vteamNickname"));
		team.setCteamCityAbbr(rs.getString("cteamCityAbbr"));
		team.setSpictureFile(rs.getString("spictureFile"));
		
		TblConference conference = new TblConference();
		conference.setIconferenceId(rs.getInt("iConferenceID"));
		conference.setVconferenceName(rs.getString("vconferenceName"));
		
		TblDivision division = new TblDivision();
		division.setConference(conference);
		division.setIdivisionId(rs.getInt("iDivisionID"));
		division.setIconferenceId(rs.getInt("iConferenceID"));
		division.setVdivisionLifespan(rs.getString("vdivisionLifespan"));
		division.setVdivisionName(rs.getString("vdivisionName"));
		
		team.setDivision(division);
			
 		return team; 	    	
    }
}
