package com.pas.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.pojo.Draft;

public class DraftComparator implements Comparator<Draft>
{
	protected static Logger log = LogManager.getLogger(DraftComparator.class);  
	
	static int FIRST_ITEM_LESS_THAN_SECOND = -1;
	static int ITEMS_ARE_EQUAL = 0;
	static int FIRST_ITEM_GREATER_THAN_SECOND = 1;
	static int DECIMAL_SCALE = 4;
	
	public int compare(Draft team1, Draft team2)
	{
		log.info("inside draft comparator");
		log.info("Team 1 is = " + team1.getTeamName());
		log.info("Team 2 is = " + team2.getTeamName());
				
		//Rule 1: if you are a playoff team (indicated by playoff round exit > 0),
		//        then you are better than a non-playoff team and therefore
		//        have a worse draft position.
		
		int playoffCompare = team1.getPlayoffRoundExit().compareTo(team2.getPlayoffRoundExit());
		
		if (playoffCompare != ITEMS_ARE_EQUAL) //one team further in playoffs than another; or one team made it, another didn't
		{
			log.info("Rule 1 invoked; either one team further in playoffs than another; or one team made it, another did not");
			return playoffCompare; 
		}
				
		//Rule 2: compare regular season records.
		//if we get this far, then both teams have either same playoff round exit or neither made playoffs
		//need to determine their w-l-t percentages and compare
		
		BigDecimal team1TotalGames = new BigDecimal(team1.getWins() + team1.getLosses() + team1.getTies());
		BigDecimal team1WinsBigDecimal = new BigDecimal(team1.getWins() + team1.getTies() *.5);
		BigDecimal team1WinningPct = team1WinsBigDecimal.divide(team1TotalGames,DECIMAL_SCALE, RoundingMode.HALF_UP);
		
		BigDecimal team2TotalGames = new BigDecimal(team2.getWins() + team2.getLosses() + team2.getTies());
		BigDecimal team2WinsBigDecimal = new BigDecimal(team2.getWins() + team2.getTies() *.5);
		BigDecimal team2WinningPct = team2WinsBigDecimal.divide(team2TotalGames,DECIMAL_SCALE, RoundingMode.HALF_UP);
		
		int wltRecordCompare = team1WinningPct.compareTo(team2WinningPct);
		
		if (wltRecordCompare != 0) //records are not equal
		{
			log.info("Rule 2 invoked; regular season records different.");
			log.info(team1.getTeamName() + ": " + team1.getWins() + "-" + team1.getLosses() + "-" + team1.getTies());
			log.info(team2.getTeamName() + ": " + team2.getWins() + "-" + team2.getLosses() + "-" + team2.getTies());
			return wltRecordCompare; 
		}
		
		//Rule 3: if still tied, use strength of regular season schedule.
		//Strength of schedule is determined by the combined records of each team's opponents.
		
		BigDecimal team1OpponentTotalGames = new BigDecimal(team1.getOpponentWins() + team1.getOpponentLosses() + team1.getOpponentTies());
		BigDecimal team1OpponentWinsBigDecimal = new BigDecimal(team1.getOpponentWins() + team1.getOpponentTies() *.5);
		BigDecimal team1OpponentWinningPct = team1OpponentWinsBigDecimal.divide(team1OpponentTotalGames,DECIMAL_SCALE, RoundingMode.HALF_UP);
		
		BigDecimal team2OpponentTotalGames = new BigDecimal(team2.getOpponentWins() + team2.getOpponentLosses() + team2.getOpponentTies());
		BigDecimal team2OpponentWinsBigDecimal = new BigDecimal(team2.getOpponentWins() + team2.getOpponentTies() *.5);
		BigDecimal team2OpponentWinningPct = team2OpponentWinsBigDecimal.divide(team2OpponentTotalGames,DECIMAL_SCALE, RoundingMode.HALF_UP);
		
		int scheduleStrengthCompare = team1OpponentWinningPct.compareTo(team2OpponentWinningPct);
		
		log.info("Rule 3 invoked; strength of schedule");
		log.info(team1.getTeamName() + ": " + team1.getOpponentWins() + "-" + team1.getOpponentLosses() + "-" + team1.getOpponentTies());
		log.info(team2.getTeamName() + ": " + team2.getOpponentWins() + "-" + team2.getOpponentLosses() + "-" + team2.getOpponentTies());
						
		return scheduleStrengthCompare; 
	}

}
