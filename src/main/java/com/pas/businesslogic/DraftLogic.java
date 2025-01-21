package com.pas.businesslogic;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.beans.NflMain;
import com.pas.beans.NflPlayoffTeam;
import com.pas.beans.NflTeam;
import com.pas.dynamodb.DynamoNflGame;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.pojo.Draft;
import com.pas.util.DraftComparator;
import com.pas.util.NFLUtil;
import com.pas.util.Utils;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("pc_DraftLogic")
@SessionScoped
public class DraftLogic implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(DraftLogic.class);   
	
	private static Integer NON_PLAYOFF_TEAM = 0;
	private static Integer REG_SEASON_GAME = 2;
	private static Integer SUPER_BOWL_LOSER = 4;
	private static Integer SUPER_BOWL_WINNER = 5;
	private static Integer STILL_ALIVE_IN_PLAYOFFS = 6;
	    
	@Inject NflMain nflMain;	

	private List<Draft> draftList = new ArrayList<>();

	public void draftReport(ActionEvent event) 
	{
		logger.info("draft selected from menu");
		
		try 
        {		
			this.setDraft();
			
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String targetURL = Utils.getContextRoot() + "/draft.xhtml";
		    ec.redirect(targetURL);
            logger.info("successfully redirected to: " + targetURL);
        } 
        catch (Exception e) 
        {
            logger.error("draftReport exception: " + e.getMessage(), e);
        }
	}  	

	private List<Draft> setDraft() throws Exception 
	{
		try
		{
			//get the playoff teams if any yet.
			boolean playoffsHaveStarted = false; //assume false;
			
			for (int j = 0; j < nflMain.getPlayoffTeamsList().size();) 
			{
				playoffsHaveStarted = true;
				logger.debug("j: " + j);
				break;
			}
			
			int tempWins = 0;
			int tempLosses = 0;
			int tempTies = 0;
			
			for (int i = 0; i < nflMain.getTeamsListCurrentSeason().size(); i++)
			{
				NflTeam team = nflMain.getTeamsListCurrentSeason().get(i);
				
				Draft draft = new Draft();
				draft.setTeamName(team.getFullTeamName());
				draft.setTeamID(team.getiTeamID());
				draft.setPlayoffRoundExit(NON_PLAYOFF_TEAM); //default to non-playoff team until proven different
				
				//is this team a playoff team?  if so assign playoffroundexit = 6 (STILL_ALIVE_IN_PLAYOFFS)
				if (playoffsHaveStarted)
				{
					for (int j = 0; j < nflMain.getPlayoffTeamsList().size(); j++) 
					{
						NflPlayoffTeam playoffTeam = nflMain.getPlayoffTeamsList().get(j);
						if (playoffTeam.getiTeamID() == team.getiTeamID())
						{
							draft.setPlayoffRoundExit(STILL_ALIVE_IN_PLAYOFFS);
							break;
						}
					}
				}				
				
				logger.debug("in DraftLogic, working on team " + team.getFullTeamName());
				
				tempWins = 0;
				tempLosses = 0;
				tempTies = 0;
				
				nflMain.setSelectedWeekNumber(0);
			    nflMain.setSelectedWeekDescription("");
			    nflMain.setSelectedTeamID(team.getiTeamID());
			    
				for (int j = 0; j < nflMain.getGameScoresList().size(); j++)
				{
					DynamoNflGame game = nflMain.getGameScoresList().get(j);
					
					//go back to top of loop if score not present
					if (game.getIawayTeamScore() == null
					|| 	game.getIhomeTeamScore() == null)
						continue;
					
					//only count regular season games in wins and losses.  
					//if this game's type is not a regular season game, do not add to wins and losses.
										
					if (game.getIgameTypeId() == REG_SEASON_GAME) 
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
					}
					else //this is a playoff game of some kind
					{
						//first off, did the team win or lose the playoff game?
						
						boolean playoffWinner = false;
						
						if (team.getiTeamID() == game.getIawayTeamID())
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
						
						Integer gamePlayoffRound = game.getIplayoffRound();
						
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
				
				logger.debug("Wins = " + tempWins);
				logger.debug("Losses = " + tempLosses);
				
				draft.setWins(tempWins);
				draft.setLosses(tempLosses);
				draft.setTies(tempTies);
				
				draft.setOpponentLosses(0);
				draft.setOpponentWins(0);
				draft.setOpponentTies(0);
				
				draftList.add(draft);
			}
		}
		catch (Exception e)
		{
			logger.error("Exception in DraftLogic: " + e.getMessage(), e);
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
			
			logger.debug("assigning opponent wins and losses, working on team " + draft.getTeamName());
			
			currentDraftTeamID = draft.getTeamID();
			
			tempOpponentWins = 0;
			tempOpponentLosses = 0;
			tempOpponentTies = 0;
			
			nflMain.setSelectedWeekNumber(0);
		    nflMain.setSelectedWeekDescription("");
		    nflMain.setSelectedTeamID(currentDraftTeamID);
		    
			for (int j = 0; j < nflMain.getGameScoresList().size(); j++)
			{
				DynamoNflGame game = nflMain.getGameScoresList().get(j);
				
				//go back to top of loop if not a regular season game (zero)
				if (game.getIgameTypeId() != REG_SEASON_GAME) 
				{
					continue;
				}
				
				//is the team we're working on the away or home team in this game?
				if (currentDraftTeamID == game.getIawayTeamID())
				{	
					opponentTeamID = game.getIhomeTeamID();
					opponentTeamName = game.getAwayteamName();
				}
				else //team we're working on is the home team
				{	
					opponentTeamID = game.getIawayTeamID();
					opponentTeamName = game.getHometeamName();
				}
				
				Integer weekNumber = game.getIweekNumber();
				
				logger.debug("In week " + weekNumber + ", opponent is = " + opponentTeamName);
				
				//now that we know this opponent, go get that team's record.
				
				for (int k = 0; k < draftList.size(); k++)
				{
					Draft opponentDraft = draftList.get(k);
					
					//loop until we find the opponent
					if (opponentTeamID != opponentDraft.getTeamID())
						continue;
					
					//if we reach here we found the team
					logger.debug("Opponent record is: wins = " + opponentDraft.getWins() + " losses = " + opponentDraft.getLosses()); 
					
					tempOpponentWins = tempOpponentWins + opponentDraft.getWins();
					tempOpponentLosses = tempOpponentLosses + opponentDraft.getLosses();
					tempOpponentTies = tempOpponentTies + opponentDraft.getTies();
				}
				
			} //end of loop on this team's games
			
			logger.debug("Opponent Wins = " + tempOpponentWins);
			logger.debug("Opponent Losses = " + tempOpponentLosses);
			logger.debug("Opponent Ties = " + tempOpponentTies);
			
			draft.setOpponentWins(tempOpponentWins);
			draft.setOpponentLosses(tempOpponentLosses);
			draft.setOpponentTies(tempOpponentTies);
			
			double skedStrengthPct = 0.0;
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
		
		logger.debug("Sorting the list so we can assign position...");
		
		Collections.sort(draftList, new DraftComparator());
		
		for (int i = 0; i < draftList.size(); i++)
		{
			Draft draft = draftList.get(i);
			draft.setPosition(i+1);
			draftList.set(i, draft);
		}
				
		logger.debug("final list size is = " + draftList.size());
			
		return draftList;		
	}
	
   	public List<Draft> getDraftList() {
		return draftList;
	}

	public void setDraftList(List<Draft> draftList) {
		this.draftList = draftList;
	}
	
}
