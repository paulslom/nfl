package com.pas.nfl.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pas.beans.NflTeam;

public class TeamsRowMapper implements RowMapper<NflTeam>, Serializable 
{
    private static final long serialVersionUID = 1L;

	@Override
    public NflTeam mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
		NflTeam team = new NflTeam();
    	
		team.setiTeamID(rs.getInt("iTeamID"));
		team.setiDivisionID(rs.getInt("iDivisionID"));
		team.setiFirstSeasonIDAsTeam(rs.getInt("iFirstSeasonIDAsTeam"));
		team.setiLastSeasonIDAsTeam(rs.getInt("iLastSeasonIDAsTeam"));
		team.setvTeamCity(rs.getString("vteamCity"));
		team.setvTeamNickname(rs.getString("vteamNickname"));
		team.setcTeamCityAbbr(rs.getString("cteamCityAbbr"));
		team.setsPictureFile(rs.getString("spictureFile"));
		team.setiConferenceID(rs.getInt("iConferenceID"));
		team.setvConferenceName(rs.getString("vconferenceName"));
		team.setvDivisionLifespan(rs.getString("vdivisionLifespan"));
		team.setvDivisionName(rs.getString("vdivisionName"));
					
 		return team; 	    	
    }
}
