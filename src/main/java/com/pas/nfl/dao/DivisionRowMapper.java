package com.pas.nfl.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pas.nfl.DBObjects.TblDivision;

public class DivisionRowMapper implements RowMapper<TblDivision>, Serializable 
{
    private static final long serialVersionUID = 1L;

	@Override
    public TblDivision mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
		TblDivision division = new TblDivision();
    	
		division.setIdivisionId(rs.getInt("idivisionId"));
		division.setIconferenceId(rs.getInt("iconferenceId"));
		division.setVdivisionName(rs.getString("vDivisionName"));
		division.setVdivisionLifespan(rs.getString("vDivisionLifespan"));
		
 		return division; 	
    	
    }
}
