package com.pas.nfl.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pas.nfl.DBObjects.TblWeek;

public class WeekRowMapper implements RowMapper<TblWeek>, Serializable 
{
    private static final long serialVersionUID = 1L;

	@Override
    public TblWeek mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
		TblWeek week = new TblWeek();
    	
		week.setIweekId(rs.getInt("iWeekID"));
		week.setiSeasonId(rs.getInt("iSeasonId"));
		week.setIweekNumber(rs.getInt("iWeekNumber"));
		week.setSweekDescription(rs.getString("sWeekDescription"));
		
 		return week; 	
    	
    }
}
