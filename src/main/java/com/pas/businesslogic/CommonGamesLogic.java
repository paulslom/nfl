package com.pas.businesslogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.pas.beans.NflMain;
import com.pas.beans.NflTeam;
import com.pas.dynamodb.DynamoNflGame;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.spring.SpringBean;
import com.pas.util.NFLUtil;
import com.pas.util.Utils;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("pc_CommonGamesLogic")
@SessionScoped
public class CommonGamesLogic implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(CommonGamesLogic.class);   
	
	@Inject NflMain nflMain;	

	public static String GREEN_STYLECLASS = "resultGreen";
	
	private List<NflTeam> afcTeamsList = new ArrayList<>();
	private List<NflTeam> nfcTeamsList = new ArrayList<>();
	
	private List<String> commonGamesResultsList = new ArrayList<>();
	
	@PostConstruct
    public void init() 
	{
        logger.info("inside init of common games logic");
    }

	public void commonGamesSelection(ActionEvent event) 
	{
		logger.info("common games selected from menu");
		
		try 
        {		
			this.setAfcTeamsList(Utils.insertBlankRowsInList(nflMain.getAfcTeamsList()));
			this.setNfcTeamsList(Utils.insertBlankRowsInList(nflMain.getNfcTeamsList()));	
			
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String targetURL = Utils.getContextRoot() + "/commonGamesSelection.xhtml";
		    ec.redirect(targetURL);
            logger.info("successfully redirected to: " + targetURL);
        } 
        catch (Exception e) 
        {
            logger.error("scheduleReport exception: " + e.getMessage(), e);
        }
	}  	

	public String calculateCommonGames() 
	{	 
		logger.info("entering calculateCommonGames");
		
		List<NflTeam> commonGamesTeams = new ArrayList<>();
		try 
        {			
			for (int i = 0; i < this.getAfcTeamsList().size(); i++) 
        	{  
				NflTeam afcteam = this.getAfcTeamsList().get(i);
				
				if (afcteam.isPlayoffTeam())
				{
					logger.info(afcteam.getFullTeamName() + " selected for common games");	
					commonGamesTeams.add(afcteam);
				}
        	}
			
			for (int i = 0; i < this.getNfcTeamsList().size(); i++) 
	    	{
	    		NflTeam nfcteam = this.getNfcTeamsList().get(i);
	    		
	    		if (nfcteam.isPlayoffTeam())
				{
	    			logger.info(nfcteam.getFullTeamName() + " selected for common games");	
					commonGamesTeams.add(nfcteam);
				}
			}
			
			if (commonGamesTeams.size() < 2) 
			{
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Please select at least 2 teams",null);
		        FacesContext.getCurrentInstance().addMessage(null, msg);    
				return "";
			}
			
			if (commonGamesTeams.size() > 3) 
			{
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Please select no more than 3 teams",null);
		        FacesContext.getCurrentInstance().addMessage(null, msg);    
				return "";
			}
			
			createCommonGamesResults(commonGamesTeams);		
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String targetURL = Utils.getContextRoot() + "/commonGamesResults.xhtml";
		    ec.redirect(targetURL);
            logger.info("successfully redirected to: " + targetURL);
						
        }
		catch (Exception e) 
        {
            logger.error("calculate common games save exception: " + e.getMessage(), e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
        }
		
		return "";
	}
	
	private void createCommonGamesResults(List<NflTeam> commonGamesTeams) throws Exception 
	{
		this.getCommonGamesResultsList().clear();
		
		String titleString = establishTitleString(commonGamesTeams);
		this.getCommonGamesResultsList().add(titleString);
		
		this.getCommonGamesResultsList().add("  ");
		
		Map<NflTeam, List<DynamoNflGame>> teamGamesMap = establishOpponents(commonGamesTeams);		
		MapDifference<Integer, String> mapDifference = establishCommonOpponents(teamGamesMap);		
		Map<Integer, String> commonOpponentsMap = mapDifference.entriesInCommon();
		
		for (var cgTeam : commonOpponentsMap.entrySet())
		{
			this.getCommonGamesResultsList().add("Common Opponent: " + cgTeam.getValue());
		}			
		
		this.getCommonGamesResultsList().add("  ");
		
		establishRecordsVsOpponents(commonOpponentsMap, teamGamesMap);		
		
		this.getCommonGamesResultsList().add("  ");
		
		doGameDetails(commonOpponentsMap, teamGamesMap);						
	}

	private void doGameDetails(Map<Integer, String> commonOpponentsMap, Map<NflTeam, List<DynamoNflGame>> teamGamesMap) 
	{
		for (var cgTeam : teamGamesMap.entrySet())
		{
			List<DynamoNflGame> teamGamesList = cgTeam.getValue();
			
			for (int i = 0; i < teamGamesList.size(); i++) 
			{				
				DynamoNflGame game = teamGamesList.get(i);
				
				if (!game.getSgameTypeDesc().equalsIgnoreCase("Regular Season"))
				{
					continue;
				}
				
				boolean thisGameMatters = false;
				
				if (commonOpponentsMap.containsKey(game.getIhomeTeamID()))
				{
					thisGameMatters = true;
				}
				else if (commonOpponentsMap.containsKey(game.getIawayTeamID()))
				{
					thisGameMatters = true;
				}
				
				if (thisGameMatters)
				{
					StringBuffer tempsbuf = new StringBuffer();
					
					if (game.getIawayTeamScore() == null
					|| 	game.getIhomeTeamScore() == null)
					{
						tempsbuf.append("Upcoming in week ");
						tempsbuf.append(game.getIweekNumber());
						tempsbuf.append(": ");
						tempsbuf.append(game.getCawayteamCityAbbr());
						tempsbuf.append(" @ ");						
						tempsbuf.append(game.getChometeamCityAbbr());
					}
					else //game has been played
					{
						tempsbuf.append("Week ");
						tempsbuf.append(game.getIweekNumber());
						tempsbuf.append(": ");
						tempsbuf.append(game.getCawayteamCityAbbr());
						tempsbuf.append(" ");
						tempsbuf.append(game.getIawayTeamScore());
						tempsbuf.append(" ");
						tempsbuf.append(game.getChometeamCityAbbr());
						tempsbuf.append(" ");
						tempsbuf.append(game.getIhomeTeamScore());
					}					
					this.getCommonGamesResultsList().add(tempsbuf.toString());
				}
							
			}
			
			this.getCommonGamesResultsList().add("  ");
		}
		
	}

	private void establishRecordsVsOpponents(Map<Integer, String> commonOpponentsMap, Map<NflTeam, List<DynamoNflGame>> teamGamesMap)
	{
		Integer tempWins = 0;
		Integer tempLosses = 0;
		Integer tempTies = 0;
		
		for (var cgTeam : teamGamesMap.entrySet())
		{
			NflTeam team = cgTeam.getKey();
			List<DynamoNflGame> teamGamesList = cgTeam.getValue();
			
			tempWins = 0;
			tempLosses = 0;
			tempTies = 0;
			
			for (int i = 0; i < teamGamesList.size(); i++) 
			{				
				DynamoNflGame game = teamGamesList.get(i);
				
				if (!game.getSgameTypeDesc().equalsIgnoreCase("Regular Season"))
				{
					continue;
				}
				
				boolean thisGameMatters = false;
				
				if (commonOpponentsMap.containsKey(game.getIhomeTeamID()))
				{
					thisGameMatters = true;
				}
				else if (commonOpponentsMap.containsKey(game.getIawayTeamID()))
				{
					thisGameMatters = true;
				}
				
				if (thisGameMatters)
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
							
			}	
			
			this.getCommonGamesResultsList().add(team.getFullTeamName() + " " + tempWins.toString() + "-" + tempLosses.toString() + "-" + tempTies.toString());
		}
		
	}

	private MapDifference<Integer, String> establishCommonOpponents(Map<NflTeam, List<DynamoNflGame>> opponentsMap)
	{
		Map<Integer,String> team1OpponentsMap = new HashMap<>();
		Map<Integer,String> team2OpponentsMap = new HashMap<>();
		Map<Integer,String> team3OpponentsMap = new HashMap<>();
		
		int count = 0;
		for (var cgTeam : opponentsMap.entrySet())
		{
			count++;
			
		    NflTeam team = cgTeam.getKey();	
		    List<DynamoNflGame> opponentList = cgTeam.getValue();
		    
		    if (count == 1)
		    {
		    	for (int i = 0; i < opponentList.size(); i++) 
			    {
					DynamoNflGame game = opponentList.get(i);
					if (game.getIawayTeamID() == team.getiTeamID())
					{
						team1OpponentsMap.put(game.getIhomeTeamID(), game.getHometeamName());
					}
					else
					{
						team1OpponentsMap.put(game.getIawayTeamID(), game.getAwayteamName());
					}
				}
		    }
		    
		    if (count == 2)
		    {
		    	for (int i = 0; i < opponentList.size(); i++) 
			    {
					DynamoNflGame game = opponentList.get(i);
					if (game.getIawayTeamID() == team.getiTeamID())
					{
						team2OpponentsMap.put(game.getIhomeTeamID(), game.getHometeamName());
					}
					else
					{
						team2OpponentsMap.put(game.getIawayTeamID(), game.getAwayteamName());
					}
				}
		    }
		    
		    if (count == 3)
		    {
		    	for (int i = 0; i < opponentList.size(); i++) 
			    {
					DynamoNflGame game = opponentList.get(i);
					if (game.getIawayTeamID() == team.getiTeamID())
					{
						team3OpponentsMap.put(game.getIhomeTeamID(), game.getHometeamName());
					}
					else
					{
						team3OpponentsMap.put(game.getIawayTeamID(), game.getAwayteamName());
					}
				}
		    }
		   
		}
		
		MapDifference<Integer, String> diff = Maps.difference(team1OpponentsMap, team2OpponentsMap);
		
		if (count == 3)
		{
			diff = Maps.difference(diff.entriesInCommon(), team3OpponentsMap);
		}
		
		return diff;
	}
	
	//Which teams are we doing common opponents for?  That belongs in the title.
	private String establishTitleString(List<NflTeam> inputList) 
	{
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(" ");
		sbuf.append("--- Common Opponents for: ");
		
		for (int i = 0; i < inputList.size(); i++) 
		{
			NflTeam tempTeam = inputList.get(i);
			sbuf.append(tempTeam.getFullTeamName() + " ");
			
		}
		sbuf.append(" ---");
		return sbuf.toString();
	}
	
	//Creates a map of opponents for each team.	
	private Map<NflTeam, List<DynamoNflGame>> establishOpponents(List<NflTeam> inputList) throws Exception
	{
		Map<NflTeam, List<DynamoNflGame>> opponentsMap = new HashMap<>();
		
		for (int i = 0; i < inputList.size(); i++) 
		{
			NflTeam inputTeam = inputList.get(i);		
			logger.debug("establishing opponents for team " + inputTeam.getFullTeamName());		
			List<DynamoNflGame> gamesList = nflMain.getScoresByTeam(inputTeam.getiTeamID());			
			opponentsMap.put(inputTeam, gamesList);					
		}				
		
		return opponentsMap;
	}
		
	public void styleTheRow(AjaxBehaviorEvent event)
	{
		UIComponent component = event.getComponent();
        if (component instanceof UIInput) 
        {
            UIInput inputComponent = (UIInput) component;
            Boolean checkBoxChecked = (Boolean) inputComponent.getValue();
            String clientID = inputComponent.getClientId();
            
            if (clientID.contains("afcCheckBoxId"))
            {
            	for (int i = 0; i < this.getAfcTeamsList().size(); i++) 
            	{            		
            		if (clientID.contains(":" + i +":"))
    				{
            			NflTeam afcteam = this.getAfcTeamsList().get(i);
                    	
    					if (checkBoxChecked)  
    					{ 
    						afcteam.setRowStyleClass(GREEN_STYLECLASS);	
    					}
    					else
    					{
    						afcteam.setRowStyleClass("");	
    					}
    					break;
    				}
				}
            }
            else if (clientID.contains("nfcCheckBoxId"))
            {
            	for (int i = 0; i < this.getNfcTeamsList().size(); i++) 
            	{
            		if (clientID.contains(":" + i +":"))
    				{
	            		NflTeam nfcteam = this.getNfcTeamsList().get(i);
	                	
						if (checkBoxChecked)  
						{ 
							nfcteam.setRowStyleClass(GREEN_STYLECLASS);	
						}
						else
						{
							nfcteam.setRowStyleClass("");	
						}
						break;
    				}
				}
            }
        }
        
	}	
	
	public List<NflTeam> getAfcTeamsList() {
		return afcTeamsList;
	}

	public void setAfcTeamsList(List<NflTeam> afcTeamsList) {
		this.afcTeamsList = afcTeamsList;
	}

	public List<NflTeam> getNfcTeamsList() {
		return nfcTeamsList;
	}

	public void setNfcTeamsList(List<NflTeam> nfcTeamsList) {
		this.nfcTeamsList = nfcTeamsList;
	}

	public List<String> getCommonGamesResultsList() {
		return commonGamesResultsList;
	}

	public void setCommonGamesResultsList(List<String> commonGamesResultsList) {
		this.commonGamesResultsList = commonGamesResultsList;
	}
		
}
