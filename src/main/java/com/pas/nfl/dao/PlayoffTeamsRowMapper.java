package com.pas.nfl.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PlayoffTeamsRowMapper implements RowMapper<TblPlayoffTeams>, Serializable 
{
    private static final long serialVersionUID = 1L;

	@Override
    public TblPlayoffTeams mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
		TblPlayoffTeams playoffTeam = new TblPlayoffTeams();
    	
		playoffTeam.setIseasonId(rs.getInt("iSeasonId"));
		playoffTeam.setbBye(rs.getBoolean("bBye"));
		playoffTeam.setiConferenceId(rs.getInt("iConferenceId"));
		playoffTeam.setiSeed(rs.getInt("iSeed"));
		playoffTeam.setiTeamID(rs.getInt("iTeamID"));
		playoffTeam.setTeamName(rs.getString("teamName"));
		playoffTeam.setSeasonYear(rs.getString("seasonYear"));
	
 		return playoffTeam; 
    	
    }
}
