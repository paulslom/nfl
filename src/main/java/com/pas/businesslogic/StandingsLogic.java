package com.pas.businesslogic;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.beans.NflMain;
import com.pas.beans.NflTeam;
import com.pas.dynamodb.DynamoNflGame;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.pojo.Standings;
import com.pas.util.NFLUtil;
import com.pas.util.StandingsComparator;
import com.pas.util.Utils;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("pc_StandingsLogic")
@SessionScoped
public class StandingsLogic implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(StandingsLogic.class);   
	
	@Inject NflMain nflMain;	

	private List<Standings> standingsList = new ArrayList<>();

	public void standingsReport(ActionEvent event) 
	{
		logger.info("standings selected from menu");
		
		try 
        {		
			this.setStandings();
			
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String targetURL = Utils.getContextRoot() + "/standings.xhtml";
		    ec.redirect(targetURL);
            logger.info("successfully redirected to: " + targetURL);
        } 
        catch (Exception e) 
        {
            logger.error("standingsReport exception: " + e.getMessage(), e);
        }
	}  	

	private List<Standings> setStandings() throws Exception 
	{
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
			//get the games specific to each team.
			
			for (int i = 0; i < nflMain.getTeamsListCurrentSeason().size(); i++)
			{
				NflTeam team = nflMain.getTeamsListCurrentSeason().get(i);
				
				Standings standings = new Standings();
				standings.setTeamName(team.getFullTeamName());
				standings.setTeamID(team.getiTeamID());
				standings.setConferenceName(team.getvConferenceName());
				standings.setDivisionName(team.getvDivisionName());
				
				logger.debug("in StandingsLogic, working on team " + team.getFullTeamName());
			
				tempLosses = 0;
				tempWins = 0;
				tempTies = 0;
				tempDivisionWins = 0;
				tempDivisionLosses = 0;
				tempDivisionTies = 0;
				tempConferenceWins = 0;
				tempConferenceLosses = 0;
				tempConferenceTies = 0;
				
			    nflMain.setSelectedWeekNumber(0);
			    nflMain.setSelectedWeekDescription("");
			    nflMain.setSelectedTeamID(team.getiTeamID());
			
			    StringBuffer tempGamesRemaining = new StringBuffer();
				int gamesRemaining = 0;
				
				for (int j = 0; j < nflMain.getGameScoresList().size(); j++)
				{
					DynamoNflGame game = nflMain.getGameScoresList().get(j);
					
					//If score not present, add to games Remaining List
					if (game.getIawayTeamScore() == null
					|| 	game.getIhomeTeamScore() == null)
					{
						gamesRemaining++;
						String thisOpponent = NFLUtil.getOpponent(team.getiTeamID(), game);	
						if (gamesRemaining > 1)
						{
							tempGamesRemaining.append(", ");							
						}
						tempGamesRemaining.append(thisOpponent);
						continue;
					}
					
					//only count regular season games in wins and losses.  
					//if this game's type is not a regular season game, do not add to wins and losses.
									
					if (game.getIplayoffRound() == 0) //zero means regular season
					{
						int wlt = NFLUtil.getWinLossTie(team.getiTeamID(), game);						
						
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
				
				logger.debug("Wins = " + tempWins);
				logger.debug("Losses = " + tempLosses);
				logger.debug("Ties = " + tempTies);
				
				standings.setWins(tempWins);
				standings.setLosses(tempLosses);
				standings.setTies(tempTies);
				standings.setDivisionLosses(tempDivisionLosses);
				standings.setConferenceLosses(tempConferenceLosses);
				
				String divisionRecord = tempDivisionWins.toString() + "-" + tempDivisionLosses.toString() + "-" + tempDivisionTies.toString();
				logger.debug("Division Record = " + divisionRecord);
				standings.setDivisionRecord(divisionRecord);
				
				String conferenceRecord = tempConferenceWins.toString() + "-" + tempConferenceLosses.toString() + "-" + tempConferenceTies.toString();
				logger.debug("Conference Record = " + conferenceRecord);
				standings.setConferenceRecord(conferenceRecord);
				
				logger.debug("Games Remaining = " + tempGamesRemaining.toString());
				standings.setGamesRemaining(tempGamesRemaining.toString());
	
				standingsList.add(standings);
								
			} //end of teams loop 
			
		}
		catch (Exception e)
		{
			logger.error("Exception in StandingsLogic: " + e.getMessage(), e);
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
			
			logger.debug("assigning opponent wins and losses, working on team " + standings.getTeamName());
			
			currentStandingsTeamID = standings.getTeamID();
			
			nflMain.setSelectedWeekNumber(0);
		    nflMain.setSelectedWeekDescription("");
		    nflMain.setSelectedTeamID(currentStandingsTeamID);
		
		    tempOpponentWins = 0;
			tempOpponentLosses = 0;
			
			for (int j = 0; j < nflMain.getGameScoresList().size(); j++)
			{
				DynamoNflGame game = nflMain.getGameScoresList().get(j);
					
				//go back to top of loop if not a regular season game (zero)
				if (game.getIplayoffRound() != 0)
					continue;
				
				//go back to top of loop if score not present
				if (game.getIawayTeamScore() == null
				|| 	game.getIhomeTeamScore() == null)
					continue;
				
				//go back to top of loop if this was not a win..
				if (NFLUtil.getWinLossTie(standings.getTeamID(), game) != INFLAppConstants.WIN)
					continue;
				
				//is the team we're working on the away or home team in this game?
				if (currentStandingsTeamID == game.getIawayTeamID())
				{	
					opponentTeamID = game.getIhomeTeamID();
					opponentTeamName = game.getHometeamName();
				}
				else //team we're working on is the home team
				{	
					opponentTeamID = game.getIawayTeamID();
					opponentTeamName = game.getAwayteamName();
				}
				
				Integer weekNumber = game.getIweekNumber();
				
				logger.debug("In week " + weekNumber + ", opponent is = " + opponentTeamName);
				
				//now that we know this opponent, go get that team's record.
				
				for (int k = 0; k < standingsList.size(); k++)
				{
					Standings opponentStandings = standingsList.get(k);
					
					//loop until we find the opponent
					if (opponentTeamID != opponentStandings.getTeamID())
						continue;
					
					//if we reach here we found the team
					logger.debug("Opponent record is: wins = " + opponentStandings.getWins() + " losses = " + opponentStandings.getLosses()); 
					
					tempOpponentWins = tempOpponentWins + opponentStandings.getWins();
					tempOpponentLosses = tempOpponentLosses + opponentStandings.getLosses();
				}
				
			} //end of loop on this team's games
			
			//assign strength of victory here
			String sovRecord = tempOpponentWins.toString() + "-" + tempOpponentLosses.toString();
			logger.debug("Strength of Victory Record = " + sovRecord);
			standings.setStrengthOfVictoryRecord(sovRecord);
			Double sovPct = tempOpponentWins.doubleValue() / (tempOpponentWins.doubleValue() + tempOpponentLosses.doubleValue());
			DecimalFormat df1 = new DecimalFormat("0.0000");
			standings.setStrengthOfVictoryPct(df1.format(sovPct));
			standingsList.set(i,standings);
						
		} //end of opponent wins-losses assignment loop
		
		logger.debug("Sorting the list for display...");
		
		Collections.sort(standingsList, new StandingsComparator());
	
		//now let's tweak to insert a blank row between divisions and blank out division name after the first one
		
		List<Standings> newList = new ArrayList<>();
		String lastDivisionName = "";
		Standings blankStandings = new Standings();
		
		for (int j = 0; j < this.getStandingsList().size(); j++) 
		{
			Standings standings = this.getStandingsList().get(j);
			
			if (standings.getDivisionName().equalsIgnoreCase(lastDivisionName)) 
			{
				lastDivisionName = standings.getDivisionName();
				standings.setDivisionName("");					
			}
			else //first row of the division
			{
				lastDivisionName = standings.getDivisionName();
				if (j > 0) //don't add blank row first time through list
				{
					newList.add(blankStandings);			
				}
			}
			
			newList.add(standings);
		}
		
		this.setStandingsList(newList);
		logger.debug("final list size is = " + standingsList.size());
		
		return standingsList;	
	}
	
	private boolean isDivisionalGame(DynamoNflGame game)
	{
		boolean divisionGame = false;
		
		NflTeam awayTeam = nflMain.getTeamByTeamID(game.getIawayTeamID());
		NflTeam homeTeam = nflMain.getTeamByTeamID(game.getIhomeTeamID());
		
		if (awayTeam.getiDivisionID() == homeTeam.getiDivisionID())
		{
			divisionGame = true;
		}
		
		return divisionGame;
	}
	
	private boolean isConferenceGame(DynamoNflGame game)
	{
		boolean conferenceGame = false;
		
		NflTeam awayTeam = nflMain.getTeamByTeamID(game.getIawayTeamID());
		NflTeam homeTeam = nflMain.getTeamByTeamID(game.getIhomeTeamID());
		
		if (awayTeam.getiConferenceID() == homeTeam.getiConferenceID())
		{
			conferenceGame = true;
		}
				
		return conferenceGame;
	}
	
   	public List<Standings> getStandingsList() {
		return standingsList;
	}

	public void setStandingsList(List<Standings> standingsList) {
		this.standingsList = standingsList;
	}
	
}
