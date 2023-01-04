package com.pas.nfl.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;
import com.pas.exception.SystemException;
import com.pas.nfl.DBObjects.TblGame;
import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.util.NFLUtil;
import com.pas.nfl.valueObject.GameSelection;
import com.pas.nfl.valueObject.TempOpponent;
import com.pas.nfl.valueObject.TempScheduleRow;
import com.pas.util.Utils;

public class CommonGamesDAO extends BaseDBDAO
{
    private static final CommonGamesDAO currentInstance = new CommonGamesDAO();

    private CommonGamesDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return conferenceDAO
     */
    public static CommonGamesDAO getDAOInstance()
    {
    	currentInstance.dataSource = Utils.getDatasourceProperties();
        return currentInstance;
    }	
      
	public List<String> inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
		
		List<String> outputList = new ArrayList<>();
				
		try 
		{
			List<TblTeam> inputList = (List<TblTeam>)Info; //a list of teams is what is sent in here.		
			
			if (inputList != null && inputList.size() >= 3) //don't do anything unless 2 or more teams selected.  Use 3 here because first item in list is seasonyear
			{
				TblTeam tempTeam = inputList.get(0);
				Integer seasonYear = tempTeam.getIteamId();
				
				String titleString = establishTitleString(inputList);
				
				Map<TblTeam, List<TblGame>> opponentsMap = establishOpponents(inputList, seasonYear);
				
				List<Integer> commonOpponents = establishCommonOpponents(inputList, seasonYear, opponentsMap);
				
				outputList.add(titleString);
				
				List<TblTeam> commonOpponentDetails = whoAreTheCommonOpponents(commonOpponents);
				
				for (int i = 0; i < commonOpponentDetails.size(); i++) 
				{
					TblTeam tempTeam2 = commonOpponentDetails.get(i);
					outputList.add("Common Opponent: " + tempTeam2.getFullTeamName());
				}
				
				outputList.add(" .. ");
				
				List<String> recordsAgainstCommons = establishRecordsVsOpponents(opponentsMap,commonOpponents);
				
				for (int i = 0; i < recordsAgainstCommons.size(); i++) 
				{
					outputList.add(recordsAgainstCommons.get(i));
				}
				
				outputList.add(" .. ");
				
				List<String> gameDetails = getGameDetails(opponentsMap,commonOpponents);
				
				for (int i = 0; i < gameDetails.size(); i++) 
				{
					outputList.add(gameDetails.get(i));
				}
												
			}
			else
			{
				outputList.add("Please select 2 or more teams to run common games for.");
			}
		} 
		catch (Exception e) 
		{
			log.error("Exception encountered! " + e.getMessage());
			throw new DAOException(e);
		}		
				
		log.debug("final list size is = " + outputList.size());
		log.debug(methodName + "out");
		return outputList;	
	}
	
	private List<String> getGameDetails(Map<TblTeam, List<TblGame>> opponentsMap, List<Integer> commonOpponents) 
	{
		List<String> gameDetails = new ArrayList<>();
		
		Map<Integer, Integer> commonOpponentsMap = commonOpponents.stream().collect(Collectors.toMap(Function.identity(), Function.identity()));	   
		
		for (Map.Entry<TblTeam, List<TblGame>> entry : opponentsMap.entrySet())  
		{
			TblTeam currentTeam = entry.getKey();			
			
			List<TblGame> currentTeamGamesList = entry.getValue();
			
			for (int i = 0; i < currentTeamGamesList.size(); i++) 
			{
				TblGame tempGame = currentTeamGamesList.get(i);	
				
				boolean countThisGame = false;
				
				if (currentTeam.getIteamId() == tempGame.getIawayTeamID())
				{
					if (commonOpponentsMap.containsKey(tempGame.getIhomeTeamID()))
					{
						countThisGame = true;
					}
				}
				else
				{
					if (commonOpponentsMap.containsKey(tempGame.getIawayTeamID()))
					{
						countThisGame = true;
					}
				}
				
				if (countThisGame)
				{
					StringBuffer tempsbuf = new StringBuffer();
					
					if (tempGame.getIawayTeamScore() == null
					|| 	tempGame.getIhomeTeamScore() == null)
					{
						tempsbuf.append("Upcoming in week ");
						tempsbuf.append(tempGame.getWeek().getIweekNumber());
						tempsbuf.append(": ");
						tempsbuf.append(tempGame.getAwayTeam().getCteamCityAbbr());
						tempsbuf.append(" @ ");						
						tempsbuf.append(tempGame.getHomeTeam().getCteamCityAbbr());
					}
					else //game has been played
					{
						tempsbuf.append("Week ");
						tempsbuf.append(tempGame.getWeek().getIweekNumber());
						tempsbuf.append(": ");
						tempsbuf.append(tempGame.getAwayTeam().getCteamCityAbbr());
						tempsbuf.append(" ");
						tempsbuf.append(tempGame.getIawayTeamScore());
						tempsbuf.append(" ");
						tempsbuf.append(tempGame.getHomeTeam().getCteamCityAbbr());
						tempsbuf.append(" ");
						tempsbuf.append(tempGame.getIhomeTeamScore());
					}					
					
					gameDetails.add(tempsbuf.toString());
				}				
			}
			
			gameDetails.add(" .. "); //just some visual breaking on the page after a team is done		
			
		}	
		
		return gameDetails;
	}
	
	private List<String> establishRecordsVsOpponents(Map<TblTeam, List<TblGame>> opponentsMap, List<Integer> commonOpponents) 
	{
		List<String> recordsAgainstCommons = new ArrayList<>();
		
		Map<Integer, Integer> commonOpponentsMap = commonOpponents.stream().collect(Collectors.toMap(Function.identity(), Function.identity()));	   
		
		for (Map.Entry<TblTeam, List<TblGame>> entry : opponentsMap.entrySet())  
		{
			TblTeam currentTeam = entry.getKey();
			
			int tempWins = 0;
			int tempLosses = 0;
			int tempTies = 0;
			
			List<TblGame> currentTeamGamesList = entry.getValue();
			
			for (int i = 0; i < currentTeamGamesList.size(); i++) 
			{
				TblGame tempGame = currentTeamGamesList.get(i);	
				
				if (tempGame.getIawayTeamScore() == null
				|| 	tempGame.getIhomeTeamScore() == null)
				{
					continue; //bypass any game without a score yet
				}
				
				int wlt = NFLUtil.getWinLossTie(currentTeam.getIteamId(), tempGame);						
				
				boolean countThisGame = false;
				
				if (currentTeam.getIteamId() == tempGame.getIawayTeamID())
				{
					if (commonOpponentsMap.containsKey(tempGame.getIhomeTeamID()))
					{
						countThisGame = true;
					}
				}
				else
				{
					if (commonOpponentsMap.containsKey(tempGame.getIawayTeamID()))
					{
						countThisGame = true;
					}
				}
				
				if (countThisGame)
				{
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
			}
			
			recordsAgainstCommons.add(currentTeam.getFullTeamName() + ": " + tempWins + "-" + tempLosses + "-" + tempTies);
		}	
		return recordsAgainstCommons;
	}
	
	//gets the details of the now-established commonopponents
	private List<TblTeam> whoAreTheCommonOpponents(List<Integer> commonOpponents) throws DAOException, SystemException 
	{
		List<TblTeam> commonOpponentDetailedList = new ArrayList<>();
		
		for (int i = 0; i < commonOpponents.size(); i++) 
		{
			Integer teamID = commonOpponents.get(i);			
			TeamDAO teamDAOReference = (TeamDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.TEAM_DAO);
			List<TblTeam> teamList = teamDAOReference.inquire(teamID); //should only be one here
			commonOpponentDetailedList.add(teamList.get(0));
		}
		return commonOpponentDetailedList;
	}
	
	//Which teams are we doing common opponents for?  That belongs in the title.
	private String establishTitleString(List<TblTeam> inputList) 
	{
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(" ");
		sbuf.append("--- Common Opponents for: ");
		
		for (int i = 0; i < inputList.size(); i++) 
		{
			if (i == 0) //dummy row for seasonYear
			{
				continue;
			}
			else
			{
				TblTeam tempTeam = inputList.get(i);
				sbuf.append(tempTeam.getFullTeamName() + " ");
			}
		}
		sbuf.append(" ---");
		return sbuf.toString();
	}
	
	//Creates a map of opponents for each team.	
	private Map<TblTeam, List<TblGame>> establishOpponents(List<TblTeam> inputList, Integer seasonYear) throws DAOException, SystemException
	{
		Map<TblTeam, List<TblGame>> opponentsMap = new HashMap<>();
		
		for (int i = 1; i < inputList.size(); i++) 
		{
			TblTeam inputTeam = inputList.get(i);
			
			//get opponents for team1
			log.debug("establishing opponents for team " + inputTeam.getFullTeamName());
			
			GameSelection gameSel = new GameSelection();
			gameSel.setSeasonYear(new Integer(seasonYear));
			gameSel.setTeamID(inputTeam.getIteamId());
			GameDAO gameDAOReference = (GameDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.GAME_DAO);
			List<TblGame> gamesList = gameDAOReference.inquire(gameSel);
			
			opponentsMap.put(inputTeam, gamesList);					
		}				
		
		return opponentsMap;
	}
	
	//Creates a list of Integers representing team IDs of common opponents all input teams share.
	private List<Integer> establishCommonOpponents(List<TblTeam> inputList, Integer seasonYear, Map<TblTeam, List<TblGame>> opponentsMap) throws DAOException, SystemException
	{	
		Map.Entry<TblTeam, List<TblGame>> firstTeamEntry = opponentsMap.entrySet().iterator().next();
		TblTeam firstTeam = firstTeamEntry.getKey();
		
		Map<Integer,TblGame> firstTeamUniqueOpponents = establishFirstTeamOpponents(firstTeam, opponentsMap);
		List<Integer> commonOpponents = removeUncommonOpponents(firstTeam, firstTeamUniqueOpponents,opponentsMap);	
		
		return commonOpponents;		
	}
	
	//loop input teams mapped opponents and only add those that are shared
	private List<Integer> removeUncommonOpponents(TblTeam firstTeam, Map<Integer, TblGame> firstTeamUniqueOpponents, Map<TblTeam, List<TblGame>> opponentsMap) 
	{
		List<Integer> commonOpponentsList = new ArrayList<>();
		
		for (Map.Entry<Integer, TblGame> firstTeamEntry : firstTeamUniqueOpponents.entrySet())  
		{
			Integer opponentTeamID = firstTeamEntry.getKey();
			
			boolean opponentFound = true; //assume true.  Loop all the games; and if not found in any of them for a given team, it's not added later.
			
			for (Map.Entry<TblTeam, List<TblGame>> entry : opponentsMap.entrySet())  
			{
				TblTeam currentTeam = entry.getKey();
				
				if (currentTeam.getIteamId() == firstTeam.getIteamId())
				{
					continue;
				}
									
				List<TblGame> currentTeamGamesList = entry.getValue();  
				
				boolean tempFound = false;
				
				for (int g = 0; g < currentTeamGamesList.size(); g++)
				{
					TblGame game = currentTeamGamesList.get(g);

					Integer tempOpponentTeamID = game.getIawayTeamID();
					if (currentTeam.getIteamId() == game.getIawayTeamID())
					{
						tempOpponentTeamID = game.getIhomeTeamID();
					}
					
					if (tempOpponentTeamID == opponentTeamID)
					{
						tempFound = true;
						break;
					}
				}
				
				if (!tempFound)
				{
					opponentFound = false;
					break;
				}
			}  
			
			if (opponentFound)
			{
				commonOpponentsList.add(opponentTeamID);
			}
			
		}	
		
		return commonOpponentsList;
	}
	
	//Creates initial commonOpponentsMap
	private Map<Integer, TblGame> establishFirstTeamOpponents(TblTeam firstTeam, Map<TblTeam, List<TblGame>> opponentsMap) 
	{
		Map<Integer,TblGame> firstTeamsuniqueOpponents = new HashMap<>();
		
		//Establish the first team's unique opponents. Take the first team and put its opponents into a hashmap. 
		
		Map.Entry<TblTeam, List<TblGame>> firstTeamEntry = opponentsMap.entrySet().iterator().next();
		List<TblGame> firstTeamGamesList = firstTeamEntry.getValue();  
		
		// Key (Integer) is opponent teamID, value is the game object.  We really don't care here that much about the game object.	
		for (int i = 0; i < firstTeamGamesList.size(); i++) 
		{
			TblGame game = firstTeamGamesList.get(i);
			
			//is the opponent the home team or away team?
			if (firstTeam.getIteamId() == game.getIawayTeamID())
			{
				firstTeamsuniqueOpponents.put(game.getIhomeTeamID(), game);
			}
			else
			{
				firstTeamsuniqueOpponents.put(game.getIawayTeamID(), game);
			}
			
		}
		return firstTeamsuniqueOpponents;
	}
	
}
