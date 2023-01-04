package com.pas.nfl.dao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;
import com.pas.exception.SystemException;
import com.pas.nfl.DBObjects.TblGame;
import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.util.StandingsComparator;
import com.pas.nfl.valueObject.Standings;
import com.pas.nfl.valueObject.GameSelection;
import com.pas.nfl.util.NFLUtil;
import com.pas.nfl.valueObject.TeamSelection;
import com.pas.util.Utils;

public class StandingsDAO extends BaseDBDAO
{
	private Map<Integer,TblTeam> thisSeasonsTeams = new HashMap<>();
   
    private static final StandingsDAO currentInstance = new StandingsDAO();

    private StandingsDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return conferenceDAO
     */
    public static StandingsDAO getDAOInstance()
    {
    	currentInstance.dataSource = Utils.getDatasourceProperties();
        return currentInstance;
    }	
      
	@SuppressWarnings("unchecked")
	public List<Standings> inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
		
		String seasonYear = (String)Info;
				
		//Standings is what this method needs to ultimately return...
		List<Standings> standingsList = new ArrayList<Standings>();
						
		//Get all games for the season provided in info object
		
		TeamDAO teamDAOReference;
		GameDAO gameDAOReference;
		
		int tempWins = 0;
		int tempLosses = 0;
		int tempTies = 0;
		Integer tempDivisionWins = 0;
		Integer tempDivisionLosses = 0;
		Integer tempDivisionTies = 0;
		Integer tempConferenceWins = 0;
		Integer tempConferenceLosses = 0;
		Integer tempConferenceTies = 0;
		
		try
		{
			//First get all the teams for this season in order by city
			teamDAOReference = (TeamDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.TEAM_DAO);
			TeamSelection teamSel = new TeamSelection();
			teamSel.setSeasonYear(seasonYear);
			teamSel.setTeamSelectionType("byCity");
			List<TblTeam> teamList = teamDAOReference.inquire(teamSel);
			
			//Stream all the teams into a Map
			thisSeasonsTeams = teamList.stream().collect(Collectors.toMap(TblTeam::getIteamId, tm -> tm));	   
			
			//now that we have all the teams, go get the games specific to each team.
			gameDAOReference = (GameDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.GAME_DAO);
			
			for (int i = 0; i < teamList.size(); i++)
			{
				TblTeam team = teamList.get(i);
				
				Standings standings = new Standings();
				standings.setTeamName(team.getFullTeamName());
				standings.setTeamID(team.getIteamId());
				standings.setConferenceName(team.getDivision().getConference().getVconferenceName());
				standings.setDivisionName(team.getDivision().getVdivisionName());
				
				log.debug("in StandingsDAO, working on team " + team.getFullTeamName());
			
				tempLosses = 0;
				tempWins = 0;
				tempTies = 0;
				tempDivisionWins = 0;
				tempDivisionLosses = 0;
				tempDivisionTies = 0;
				tempConferenceWins = 0;
				tempConferenceLosses = 0;
				tempConferenceTies = 0;
				
				GameSelection gameSel = new GameSelection();
				gameSel.setSeasonYear(new Integer(seasonYear));
				gameSel.setTeamID(team.getIteamId());
			
				List<TblGame> gamesList = gameDAOReference.inquire(gameSel);
				
				StringBuffer tempGamesRemaining = new StringBuffer();
				int gamesRemaining = 0;
				
				for (int j = 0; j < gamesList.size(); j++)
				{
					TblGame game = gamesList.get(j);
					
					//If score not present, add to games Remaining List
					if (game.getIawayTeamScore() == null
					|| 	game.getIhomeTeamScore() == null)
					{
						gamesRemaining++;
						String thisOpponent = NFLUtil.getOpponent(team.getIteamId(), game);	
						if (gamesRemaining > 1)
						{
							tempGamesRemaining.append(", ");							
						}
						tempGamesRemaining.append(thisOpponent);
						continue;
					}
					
					//only count regular season games in wins and losses.  
					//if this game's type is not a regular season game, do not add to wins and losses.
									
					if (game.getGameType().getIplayoffRound() == 0) //zero means regular season
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
						
						//is this a division game?
						
						if (isDivisionalGame(game))
							switch (wlt)
							{
								case INFLAppConstants.LOSS:
									tempDivisionLosses = tempDivisionLosses + 1;
									break;
								case INFLAppConstants.WIN:
									tempDivisionWins = tempDivisionWins + 1;
									break;
								case INFLAppConstants.TIE: 
									tempDivisionTies = tempDivisionTies + 1;
									break;
							}
						
						//is this a conference game?
						
						if (isConferenceGame(game))
							switch (wlt)
							{
								case INFLAppConstants.LOSS:
									tempConferenceLosses = tempConferenceLosses + 1;
									break;
								case INFLAppConstants.WIN:
									tempConferenceWins = tempConferenceWins + 1;
									break;
								case INFLAppConstants.TIE: 
									tempConferenceTies = tempConferenceTies + 1;
									break;
							}
						
					} //end of if logic to determine if this is a regular season game
						
				} //end of games loop
				
				log.debug("Wins = " + tempWins);
				log.debug("Losses = " + tempLosses);
				log.debug("Ties = " + tempTies);
				
				standings.setWins(tempWins);
				standings.setLosses(tempLosses);
				standings.setTies(tempTies);
				standings.setDivisionLosses(tempDivisionLosses);
				standings.setConferenceLosses(tempConferenceLosses);
				
				String divisionRecord = tempDivisionWins.toString() + "-" + tempDivisionLosses.toString() + "-" + tempDivisionTies.toString();
				log.debug("Division Record = " + divisionRecord);
				standings.setDivisionRecord(divisionRecord);
				
				String conferenceRecord = tempConferenceWins.toString() + "-" + tempConferenceLosses.toString() + "-" + tempConferenceTies.toString();
				log.debug("Conference Record = " + conferenceRecord);
				standings.setConferenceRecord(conferenceRecord);
				
				log.debug("Games Remaining = " + tempGamesRemaining.toString());
				standings.setGamesRemaining(tempGamesRemaining.toString());
	
				standingsList.add(standings);
								
			} //end of teams loop 
			
		}
		catch (SystemException e)
		{
			throw new DAOException(e);
		}
		
		//now we need to assign strength of Victory
		
		Integer tempOpponentWins = 0;
		Integer tempOpponentLosses = 0;
		Integer currentStandingsTeamID = 0;
		Integer opponentTeamID = 0;
		String opponentTeamName = "";
		
		//Loop on standingsList already created...this has team records in it.
		
		for (int i = 0; i < standingsList.size(); i++)
		{
			Standings standings = standingsList.get(i);
			
			log.debug("assigning opponent wins and losses, working on team " + standings.getTeamName());
			
			GameSelection gameSel = new GameSelection();
			gameSel.setSeasonYear(new Integer(seasonYear));
			gameSel.setTeamID(standings.getTeamID());
		
			currentStandingsTeamID = standings.getTeamID();
			
			List<TblGame> gamesList = gameDAOReference.inquire(gameSel);
			
			tempOpponentWins = 0;
			tempOpponentLosses = 0;
				
			for (int j = 0; j < gamesList.size(); j++)
			{
				TblGame game = gamesList.get(j);
				
				//go back to top of loop if not a regular season game (zero)
				if (game.getGameType().getIplayoffRound() != 0)
					continue;
				
				//go back to top of loop if score not present
				if (game.getIawayTeamScore() == null
				|| 	game.getIhomeTeamScore() == null)
					continue;
				
				//go back to top of loop if this was not a win..
				if (NFLUtil.getWinLossTie(standings.getTeamID(), game) != INFLAppConstants.WIN)
					continue;
				
				//is the team we're working on the away or home team in this game?
				if (currentStandingsTeamID == game.getAwayTeam().getIteamId())
				{	
					opponentTeamID = game.getHomeTeam().getIteamId();
					opponentTeamName = game.getHomeTeam().getFullTeamName();
				}
				else //team we're working on is the home team
				{	
					opponentTeamID = game.getAwayTeam().getIteamId();
					opponentTeamName = game.getAwayTeam().getFullTeamName();
				}
				
				Integer weekNumber = game.getWeek().getIweekNumber();
				
				log.debug("In week " + weekNumber + ", opponent is = " + opponentTeamName);
				
				//now that we know this opponent, go get that team's record.
				
				for (int k = 0; k < standingsList.size(); k++)
				{
					Standings opponentStandings = standingsList.get(k);
					
					//loop until we find the opponent
					if (opponentTeamID != opponentStandings.getTeamID())
						continue;
					
					//if we reach here we found the team
					log.debug("Opponent record is: wins = " + opponentStandings.getWins() + " losses = " + opponentStandings.getLosses()); 
					
					tempOpponentWins = tempOpponentWins + opponentStandings.getWins();
					tempOpponentLosses = tempOpponentLosses + opponentStandings.getLosses();
				}
				
			} //end of loop on this team's games
			
			//assign strength of victory here
			String sovRecord = tempOpponentWins.toString() + "-" + tempOpponentLosses.toString();
			log.debug("Strength of Victory Record = " + sovRecord);
			standings.setStrengthOfVictoryRecord(sovRecord);
			Double sovPct = tempOpponentWins.doubleValue() / (tempOpponentWins.doubleValue() + tempOpponentLosses.doubleValue());
			DecimalFormat df1 = new DecimalFormat("0.0000");
			standings.setStrengthOfVictoryPct(df1.format(sovPct));
			standingsList.set(i,standings);
						
		} //end of opponent wins-losses assignment loop
		
		log.debug("Sorting the list for display on jsp...");
		
		Collections.sort(standingsList, new StandingsComparator());
	
		log.debug("final list size is = " + standingsList.size());
		log.debug(methodName + "out");
		return standingsList;	
	}
	
	private boolean isDivisionalGame(TblGame game)
	{
		boolean divisionGame = false;
		
		Integer awayTeamID = game.getIawayTeamID();
		Integer homeTeamID = game.getIhomeTeamID();
		
		TblTeam awayTeam = thisSeasonsTeams.get(awayTeamID);
		TblTeam homeTeam = thisSeasonsTeams.get(homeTeamID);
		
		Integer awayDivision = awayTeam.getDivision().getIdivisionId();
		Integer homeDivision = homeTeam.getDivision().getIdivisionId();
		
		if (awayDivision.compareTo(homeDivision) == 0)
		{
			divisionGame = true;
		}
		
		return divisionGame;
	}
	
	private boolean isConferenceGame(TblGame game)
	{
		boolean conferenceGame = false;
		
		Integer awayTeamID = game.getIawayTeamID();
		Integer homeTeamID = game.getIhomeTeamID();
		
		TblTeam awayTeam = thisSeasonsTeams.get(awayTeamID);
		TblTeam homeTeam = thisSeasonsTeams.get(homeTeamID);
		
		Integer awayConference = awayTeam.getDivision().getConference().getIconferenceId();
		Integer homeConference = homeTeam.getDivision().getConference().getIconferenceId();
		
		if (awayConference.compareTo(homeConference) == 0)
		{
			conferenceGame = true;
		}
		
		return conferenceGame;
	}
	
}
