package com.pas.nfl.dao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;
import com.pas.exception.SystemException;
import com.pas.nfl.DBObjects.TblGame;
import com.pas.nfl.DBObjects.TblPlayoffTeams;
import com.pas.nfl.DBObjects.TblSeason;
import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.util.DraftComparator;
import com.pas.nfl.util.NFLUtil;
import com.pas.nfl.valueObject.Draft;
import com.pas.nfl.valueObject.GameSelection;
import com.pas.nfl.valueObject.PlayoffsSelection;
import com.pas.nfl.valueObject.SeasonSelection;
import com.pas.nfl.valueObject.TeamSelection;

/**
 * Title: 		DraftDAO
 * Project: 	Slomkowski Financial Application
 * Description: Investor DAO extends BaseDBDAO. Implements the data access to the TblConference table
 * Copyright: 	Copyright (c) 2006
 */
public class DraftDAO extends BaseDBDAO
{
    private static Integer NON_PLAYOFF_TEAM = 0;
    private static Integer REG_SEASON_GAME = 2;
    private static Integer SUPER_BOWL_LOSER = 4;
    private static Integer SUPER_BOWL_WINNER = 5;
    private static Integer STILL_ALIVE_IN_PLAYOFFS = 6;
  
    private static final DraftDAO currentInstance = new DraftDAO();

    private DraftDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return conferenceDAO
     */
    public static DraftDAO getDAOInstance()
    {
        return currentInstance;
    }	
      
	@SuppressWarnings("unchecked")
	public List<Draft> inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
		
		String seasonYear = (String)Info;
		
		//draftList is what this method needs to ultimately return...
		List<Draft> draftList = new ArrayList<Draft>();
				
		//Get all games for the season provided in info object
		
		TeamDAO teamDAOReference;
		GameDAO gameDAOReference;
		PlayoffsDAO playoffDAOReference;
		SeasonDAO seasonDAOReference;
		
		List<TblTeam> teamList = new ArrayList<TblTeam>();
		List playoffTeamList = new ArrayList();
		
		try
		{
			//First get all the teams for this season in order by city
			teamDAOReference = (TeamDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.TEAM_DAO);
			TeamSelection teamSel = new TeamSelection();
			
			seasonDAOReference = (SeasonDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.SEASON_DAO);
			SeasonSelection seasonSel = new SeasonSelection();
			seasonSel.setSeasonYear(Integer.parseInt(seasonYear));
			List<TblSeason> tblSeasonList = seasonDAOReference.inquire(seasonSel);
			TblSeason tblSeason = tblSeasonList.get(0);
			Integer seasonID = tblSeason.getIseasonId();
			
			teamSel.setSeasonYear(seasonYear);
			teamSel.setTeamSelectionType("byCity");
			teamList = teamDAOReference.inquire(teamSel);
			
			//now that we have all the teams, go get the games specific to each team.
			gameDAOReference = (GameDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.GAME_DAO);
			
			//get the playoff teams if any yet.
			playoffDAOReference = (PlayoffsDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.PLAYOFFS_DAO);
			PlayoffsSelection playoffsSelection = new PlayoffsSelection();
			playoffsSelection.setSeasonYear(Integer.parseInt(seasonYear));
			playoffsSelection.setSeasonID(seasonID);
			playoffsSelection.setPlayoffByesByConf(tblSeason.getiPlayoffByesByConf());
			playoffsSelection.setTotalPlayoffTeamsByConf(tblSeason.getiConferencePlayoffTeams());
			playoffsSelection.setFunction("showSetupForm");
			playoffTeamList = playoffDAOReference.inquire(playoffsSelection);
			
			boolean playoffsHaveStarted = false; //assume false;
			
			for (int j = 0; j < playoffTeamList.size(); j++) 
			{
				Object obj = playoffTeamList.get(j);
				if (obj instanceof TblPlayoffTeams)
				{
					playoffsHaveStarted = true;
				}
				break;
			}
			
			int tempWins = 0;
			int tempLosses = 0;
			int tempTies = 0;
			
			for (int i = 0; i < teamList.size(); i++)
			{
				TblTeam team = teamList.get(i);
				
				Draft draft = new Draft();
				draft.setTeamName(team.getFullTeamName());
				draft.setTeamID(team.getIteamId());
				draft.setPlayoffRoundExit(NON_PLAYOFF_TEAM); //default to non-playoff team until proven different
				
				//is this team a playoff team?  if so assign playoffroundexit = 6 (STILL_ALIVE_IN_PLAYOFFS)
				if (playoffsHaveStarted)
				{
					for (int j = 0; j < playoffTeamList.size(); j++) 
					{
						TblPlayoffTeams playoffTeam = (TblPlayoffTeams)playoffTeamList.get(j);
						if (playoffTeam.getiTeamID() == team.getIteamId())
						{
							draft.setPlayoffRoundExit(STILL_ALIVE_IN_PLAYOFFS);
							break;
						}
					}
				}				
				
				log.debug("in DraftDAO, working on team " + team.getFullTeamName());
				
				GameSelection gameSel = new GameSelection();
				gameSel.setSeasonYear(new Integer(seasonYear));
				gameSel.setTeamID(team.getIteamId());
			
				List<TblGame> gamesList = gameDAOReference.inquire(gameSel);
				
				tempWins = 0;
				tempLosses = 0;
				tempTies = 0;
				
				for (int j = 0; j < gamesList.size(); j++)
				{
					TblGame game = gamesList.get(j);
					
					//go back to top of loop if score not present
					if (game.getIawayTeamScore() == null
					|| 	game.getIhomeTeamScore() == null)
						continue;
					
					//only count regular season games in wins and losses.  
					//if this game's type is not a regular season game, do not add to wins and losses.
										
					if (game.getIgameTypeId() == REG_SEASON_GAME) 
					{	
						int wlt = NFLUtil.getWinLossTie(team.getIteamId(), game);						
						
						switch (wlt)
						{
							case INFLAppConstants.LOSS:
								tempLosses = tempLosses + 1;
								break;
							case INFLAppConstants.WIN:
								tempWins = tempWins + 1;
								break;
							case INFLAppConstants.TIE:
								tempTies = tempTies + 1;
								break;
						}
					}
					else //this is a playoff game of some kind
					{
						//first off, did the team win or lose the playoff game?
						
						boolean playoffWinner = false;
						
						if (team.getIteamId() == game.getIawayTeamID())
						{
							if (game.getIawayTeamScore() > game.getIhomeTeamScore())
							{
								playoffWinner = true;
							}
						}
						else //team we're working on is the home team
						{
						   if (game.getIhomeTeamScore() > game.getIawayTeamScore())						
						   {
							   playoffWinner = true;
						   }
						}
						Integer gamePlayoffRound = game.getGameType().getIplayoffRound();
						
						if (playoffWinner)
						{
							draft.setPlayoffRoundExit(STILL_ALIVE_IN_PLAYOFFS);
						}
						else //this team lost in playoffs.  Assign when.
						{
							if (gamePlayoffRound == SUPER_BOWL_LOSER || gamePlayoffRound == SUPER_BOWL_WINNER) //Super Bowl
							{
								draft.setPlayoffRoundExit(gamePlayoffRound); //super bowl winner
							}
							else
							{							
								draft.setPlayoffRoundExit(gamePlayoffRound);
							}
						}						
					}
				}
				
				log.debug("Wins = " + tempWins);
				log.debug("Losses = " + tempLosses);
				
				draft.setWins(tempWins);
				draft.setLosses(tempLosses);
				draft.setTies(tempTies);
				
				draft.setOpponentLosses(0);
				draft.setOpponentWins(0);
				draft.setOpponentTies(0);
				
				draftList.add(draft);
							
			} 
			
		}
		catch (SystemException e)
		{
			throw new DAOException(e);
		}
		
		//now we need to assign opponent wins and losses...this is a little complicated
		
		int tempOpponentWins = 0;
		int tempOpponentLosses = 0;
		int tempOpponentTies = 0;
		
		Integer currentDraftTeamID = 0;
		Integer opponentTeamID = 0;
		String opponentTeamName = "";
		
		//Loop on draftList already created...this has team records in it.
		
		for (int i = 0; i < draftList.size(); i++)
		{
			Draft draft = draftList.get(i);
			
			log.debug("assigning opponent wins and losses, working on team " + draft.getTeamName());
			
			GameSelection gameSel = new GameSelection();
			gameSel.setSeasonYear(new Integer(seasonYear));
			gameSel.setTeamID(draft.getTeamID());
		
			currentDraftTeamID = draft.getTeamID();
			
			List<TblGame> gamesList = gameDAOReference.inquire(gameSel);
			
			tempOpponentWins = 0;
			tempOpponentLosses = 0;
			tempOpponentTies = 0;
			
			for (int j = 0; j < gamesList.size(); j++)
			{
				TblGame game = gamesList.get(j);
				
				//go back to top of loop if not a regular season game (zero)
				if (game.getIgameTypeId() != REG_SEASON_GAME) 
				{
					continue;
				}
				
				//is the team we're working on the away or home team in this game?
				if (currentDraftTeamID == game.getIawayTeamID())
				{	
					opponentTeamID = game.getIhomeTeamID();
					opponentTeamName = game.getHomeTeam().getVteamCity() + " " + game.getHomeTeam().getVteamNickname();
				}
				else //team we're working on is the home team
				{	
					opponentTeamID = game.getIawayTeamID();
					opponentTeamName = game.getAwayTeam().getVteamCity() + " " + game.getAwayTeam().getVteamNickname();
				}
				
				Integer weekNumber = game.getWeek().getIweekNumber();
				
				log.debug("In week " + weekNumber + ", opponent is = " + opponentTeamName);
				
				//now that we know this opponent, go get that team's record.
				
				for (int k = 0; k < draftList.size(); k++)
				{
					Draft opponentDraft = draftList.get(k);
					
					//loop until we find the opponent
					if (opponentTeamID != opponentDraft.getTeamID())
						continue;
					
					//if we reach here we found the team
					log.debug("Opponent record is: wins = " + opponentDraft.getWins() + " losses = " + opponentDraft.getLosses()); 
					
					tempOpponentWins = tempOpponentWins + opponentDraft.getWins();
					tempOpponentLosses = tempOpponentLosses + opponentDraft.getLosses();
					tempOpponentTies = tempOpponentTies + opponentDraft.getTies();
				}
				
			} //end of loop on this team's games
			
			log.debug("Opponent Wins = " + tempOpponentWins);
			log.debug("Opponent Losses = " + tempOpponentLosses);
			log.debug("Opponent Ties = " + tempOpponentTies);
			
			draft.setOpponentWins(tempOpponentWins);
			draft.setOpponentLosses(tempOpponentLosses);
			draft.setOpponentTies(tempOpponentTies);
			
			Double skedStrengthPct = new Double(0.0);
			skedStrengthPct = draft.getOpponentWins().doubleValue() / (draft.getOpponentWins().doubleValue() + draft.getOpponentLosses().doubleValue());
			if (tempOpponentTies > 0)
			{
				Double wins = draft.getOpponentWins().doubleValue();
				wins = wins + (tempOpponentTies*.5);
				Double losses = draft.getOpponentLosses().doubleValue();
				losses = losses + (tempOpponentTies*.5);
				Double totalGames = wins + losses;
				skedStrengthPct = wins / totalGames;
			}
			DecimalFormat df1 = new DecimalFormat("0.0000");
			draft.setScheduleStrengthPct(df1.format(skedStrengthPct));
	
			draftList.set(i,draft);
						
		} //end of opponent wins-losses assignment loop
		
		log.debug("Sorting the list so we can assign position...");
		
		Collections.sort(draftList, new DraftComparator());
		
		for (int i = 0; i < draftList.size(); i++)
		{
			Draft draft = draftList.get(i);
			draft.setPosition(i+1);
			draftList.set(i, draft);
		}
				
		log.debug("final list size is = " + draftList.size());
		log.debug(methodName + "out");
		
		return draftList;	
	}
	
}
