package com.pas.nfl.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pas.nfl.DBObjects.TblConference;

public class ConferenceRowMapper implements RowMapper<TblConference>, Serializable 
{
    private static final long serialVersionUID = 1L;

	@Override
    public TblConference mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
		TblConference conference = new TblConference();
    	
		conference.setIconferenceId(rs.getInt("iconferenceId"));
		conference.setVconferenceName(rs.getString("vconferenceName"));
		
 		return conference; 	
    	
    }
}
