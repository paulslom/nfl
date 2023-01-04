package com.pas.nfl.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.nfl.DBObjects.TblGame;
import com.pas.nfl.constants.INFLAppConstants;

public class NFLUtil
{

	static Logger log = LogManager.getLogger(NFLUtil.class);

	/**
	 * constructor
	 */
	public NFLUtil()
	{
	}

	//This method will return the opponent of the game and team provided.
	//It will also put the @ sign in front if the game is away, 
	//or capitalize the opponent if it is a home game.
	
	public static String getOpponent(Integer teamID, TblGame game)
    {
		String opponent = "";
		
		//is the team we're working on the away or home team in this game?
		
		if (teamID == game.getIawayTeamID())
		{	
		   opponent = "@ " + game.getHomeTeam().getCteamCityAbbr().toLowerCase();
		   
		}
		else //team we're working on is the home team
		{	
		   opponent = game.getAwayTeam().getCteamCityAbbr().toUpperCase();
		}

		return opponent;
	}
	
	//This method will determine if the game is a win loss or tie
	//for the team provided.
	//-1 lost, 0 tie, 1 win default to tie
	
	public static int getWinLossTie(Integer teamID, TblGame game)
    {
		int wlt = INFLAppConstants.TIE; 
		
		//is the team we're working on the away or home team in this game?
		
		if (teamID == game.getIawayTeamID())
		{	
		   if (game.getIawayTeamScore() > game.getIhomeTeamScore())
		  	   wlt = INFLAppConstants.WIN;
		   else if (game.getIawayTeamScore() < game.getIhomeTeamScore())
			   wlt = INFLAppConstants.LOSS;
		}
		else //team we're working on is the home team
		{	
		   if (game.getIhomeTeamScore() > game.getIawayTeamScore())
			   wlt = INFLAppConstants.WIN;
		   else if (game.getIhomeTeamScore() < game.getIawayTeamScore())
			   wlt = INFLAppConstants.LOSS;  
		}

		return wlt;
	}

}