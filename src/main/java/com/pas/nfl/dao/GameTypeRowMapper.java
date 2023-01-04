package com.pas.nfl.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pas.nfl.DBObjects.TblGameType;

public class GameTypeRowMapper implements RowMapper<TblGameType>, Serializable 
{
    private static final long serialVersionUID = 1L;

	@Override
    public TblGameType mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
		TblGameType gametype = new TblGameType();
    	
		gametype.setIgameTypeId(rs.getInt("iGameTypeID"));
		gametype.setSgameTypeDesc(rs.getString("sGameTypeDesc"));
		gametype.setIplayoffRound(rs.getInt("iPlayoffRound"));
			
 		return gametype; 	    	
    }
}
