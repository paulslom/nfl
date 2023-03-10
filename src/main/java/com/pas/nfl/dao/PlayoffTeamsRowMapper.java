package com.pas.nfl.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pas.nfl.DBObjects.TblPlayoffTeams;

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
	
 		return playoffTeam; 
    	
    }
}
