package com.pas.nfl.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.pas.beans.NflGame;
import com.pas.dynamodb.DateToStringConverter;

public class GamesRowMapper implements RowMapper<NflGame>, Serializable 
{
    private static final long serialVersionUID = 1L;

	@Override
    public NflGame mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
		NflGame game = new NflGame();
    	
		Timestamp ts = rs.getTimestamp("dGameDateTime");
		game.setDgameDateTime(DateToStringConverter.convertSqlTimestampToDynamoStringFormat(ts));	
		
		game.setIawayTeamID(rs.getInt("iAwayTeamID"));
		
		Integer awayScore = rs.getInt("iAwayTeamScore");
		if (rs.wasNull())
		{
			game.setIawayTeamScore(null);
		}
		else
		{
			game.setIawayTeamScore(awayScore);
		}
		
		game.setIgameId(rs.getInt("iGameId"));
		game.setIgameTypeId(rs.getInt("iGameTypeID"));
		game.setIhomeTeamID(rs.getInt("iHomeTeamID"));
		
		Integer homeScore = rs.getInt("iHomeTeamScore");
		if (rs.wasNull())
		{
			game.setIhomeTeamScore(null);
		}
		else
		{
			game.setIhomeTeamScore(homeScore);
		}		
		
		game.setIweekId(rs.getInt("iWeekId"));		
		game.setIplayoffRound(rs.getInt("iPlayoffRound"));
		game.setSgameTypeDesc(rs.getString("sGameTypeDesc"));		
		game.setCawayteamCityAbbr(rs.getString("awayTeamAbbr"));
		game.setChometeamCityAbbr(rs.getString("homeTeamAbbr"));
		game.setiSeasonId(rs.getInt("iSeasonId"));
		game.setIweekNumber(rs.getInt("iweekNumber"));
		game.setSweekDescription(rs.getString("sweekDescription"));
					
 		return game; 	    	
    }
}
