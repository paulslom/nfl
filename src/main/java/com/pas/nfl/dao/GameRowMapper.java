package com.pas.nfl.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.pas.nfl.DBObjects.TblConference;
import com.pas.nfl.DBObjects.TblDivision;
import com.pas.nfl.DBObjects.TblGame;
import com.pas.nfl.DBObjects.TblGameType;
import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.DBObjects.TblWeek;

public class GameRowMapper implements RowMapper<TblGame>, Serializable 
{
    private static final long serialVersionUID = 1L;

	@Override
    public TblGame mapRow(ResultSet rs, int rowNum) throws SQLException 
    {
		TblGame game = new TblGame();
    	
		game.setDgameDateTime(rs.getTimestamp("dGameDateTime"));
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
		
		TblGameType gameType = new TblGameType();
		gameType.setIgameTypeId(game.getIgameTypeId());
		gameType.setIplayoffRound(rs.getInt("iPlayoffRound"));
		gameType.setSgameTypeDesc(rs.getString("sGameTypeDesc"));
		
		TblTeam awayTeam = new TblTeam();
		awayTeam.setIteamId(game.getIawayTeamID());
		awayTeam.setCteamCityAbbr(rs.getString("awayTeamAbbr"));
		awayTeam.setIdivisionId(rs.getInt("awayTeamDivisionID"));
		awayTeam.setSpictureFile(rs.getString("awayTeamPic"));
		awayTeam.setVteamCity(rs.getString("awayTeamCity"));
		awayTeam.setVteamNickname(rs.getString("awayTeamNickname"));
		
		TblDivision awayDivision = new TblDivision();
		awayDivision.setVdivisionName(rs.getString("awayDivisionName"));
		awayDivision.setIdivisionId(rs.getInt("awayTeamDivisionID"));
		TblConference awayConference = new TblConference();
		awayConference.setIconferenceId(rs.getInt("awayConferenceID"));
		awayConference.setVconferenceName(rs.getString("awayConferenceName"));		
		awayDivision.setConference(awayConference);
		awayTeam.setDivision(awayDivision);
		
		TblTeam homeTeam = new TblTeam();
		homeTeam.setIteamId(game.getIhomeTeamID());
		homeTeam.setCteamCityAbbr(rs.getString("homeTeamAbbr"));
		homeTeam.setIdivisionId(rs.getInt("homeTeamDivisionID"));
		homeTeam.setSpictureFile(rs.getString("homeTeamPic"));
		homeTeam.setVteamCity(rs.getString("homeTeamCity"));
		homeTeam.setVteamNickname(rs.getString("homeTeamNickname"));
		
		TblDivision homeDivision = new TblDivision();
		homeDivision.setVdivisionName(rs.getString("homeDivisionName"));
		homeDivision.setIdivisionId(rs.getInt("homeTeamDivisionID"));
		TblConference homeConference = new TblConference();
		homeConference.setIconferenceId(rs.getInt("homeConferenceID"));
		homeConference.setVconferenceName(rs.getString("homeConferenceName"));		
		homeDivision.setConference(homeConference);
		homeTeam.setDivision(homeDivision);
		
		TblWeek week = new TblWeek(); 
		week.setIweekId(game.getIweekId());
		week.setiSeasonId(rs.getInt("iSeasonId"));
		week.setIweekNumber(rs.getInt("iweekNumber"));
		week.setSweekDescription(rs.getString("sweekDescription"));
			
		game.setAwayTeam(awayTeam);
		game.setHomeTeam(homeTeam);
		game.setWeek(week);
		game.setGameType(gameType);
			
 		return game; 	    	
    }
}
