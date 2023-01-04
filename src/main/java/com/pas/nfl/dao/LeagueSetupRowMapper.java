package com.pas.nfl.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pas.nfl.DBObjects.TblLeagueSetup;

public class LeagueSetupRowMapper implements RowMapper<TblLeagueSetup>, Serializable 
{
    private static final long serialVersionUID = 1L;

	@Override
    public TblLeagueSetup mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
		TblLeagueSetup leagueSetup = new TblLeagueSetup();
    	
		leagueSetup.setiLeagueSetupId(rs.getInt("iLeagueSetupId"));
		leagueSetup.setiSeasonId(rs.getInt("iSeasonId"));
		leagueSetup.setiDivisionId(rs.getInt("iDivisionId"));
		
 		return leagueSetup; 	
    	
    }
}
