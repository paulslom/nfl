package com.pas.nfl.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SeasonRowMapper implements RowMapper<TblSeason>, Serializable 
{
    private static final long serialVersionUID = 1L;

	@Override
    public TblSeason mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
		TblSeason season = new TblSeason();
    	
		season.setIseasonId(rs.getInt("iseasonId"));
		season.setCyear(rs.getString("cyear"));
		season.setVsuperBowl(rs.getString("vsuperBowl"));
		season.setiPlayoffByesByConf(rs.getInt("iPlayoffByesByConf"));
		season.setiConferencePlayoffTeams(rs.getInt("iConferencePlayoffTeams"));
		
 		return season; 	
    	
    }
}
