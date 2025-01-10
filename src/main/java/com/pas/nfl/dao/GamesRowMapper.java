package com.pas.nfl.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.pas.dynamodb.DateToStringConverter;
import com.pas.dynamodb.DynamoNflGame;
import com.pas.util.Utils;

public class GamesRowMapper implements RowMapper<DynamoNflGame>, Serializable 
{
    private static final long serialVersionUID = 1L;

	@Override
    public DynamoNflGame mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
		DynamoNflGame game = new DynamoNflGame();
    			
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
		
		game.setcYear(rs.getString("cYear"));
		
		game.setIgameId(rs.getInt("iGameId"));
		game.setIgameTypeId(rs.getInt("iGameTypeID"));
		game.setIhomeTeamID(rs.getInt("iHomeTeamID"));
		
		game.setAwayteamName(rs.getString("awayteamName"));
		game.setHometeamName(rs.getString("hometeamName"));
		
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
		
		Timestamp ts = rs.getTimestamp("dGameDateTime");
		String gameDateString = DateToStringConverter.convertMySqlDateTimeToUserInputFormat(ts);
		
		game.setGameDateTimeDisplay(gameDateString);
		game.setDgameDateTime(DateToStringConverter.convertDateToDynamoStringFormat(gameDateString));			
		game.setGameDayOfWeek(Utils.getGameDayOfWeek(gameDateString));
		game.setGameDateOnly(Utils.getGameDateOnly(gameDateString));
		game.setGameTimeOnly(Utils.getGameTimeOnly(gameDateString));
		
		game.setHomeTeamScoreStyleClass(Utils.getHomeTeamScoreStyleClass(game.getIhomeTeamScore(), game.getIawayTeamScore()));
		game.setAwayTeamScoreStyleClass(Utils.getAwayTeamScoreStyleClass(game.getIhomeTeamScore(), game.getIawayTeamScore()));
		
	
 		return game; 	    	
    }
}
