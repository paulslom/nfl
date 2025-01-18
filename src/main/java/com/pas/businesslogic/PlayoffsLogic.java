package com.pas.businesslogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.beans.NflMain;
import com.pas.beans.NflPlayoffTeam;
import com.pas.beans.NflTeam;
import com.pas.dynamodb.DynamoNflGame;
import com.pas.pojo.PlayoffBrackets;
import com.pas.util.Utils;

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

@Named("pc_PlayoffsLogic")
@SessionScoped
public class PlayoffsLogic implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(PlayoffsLogic.class);   
	
	//private static final Integer AFC = 1;
	//private static final Integer NFC = 2;
	
	@Inject NflMain nflMain;	

	public static String GREEN_STYLECLASS = "resultGreen";
	
	private List<NflTeam> afcTeamsList = new ArrayList<>();
	private List<NflTeam> nfcTeamsList = new ArrayList<>();

	private Map<Integer, Integer> playoffTeamSeedsMap = new HashMap<>();
	
	private PlayoffBrackets playoffBrackets = new PlayoffBrackets();
	
	public void showBrackets(ActionEvent event) 
	{
		logger.info("show playoff Brackets selected from menu");
		
		try 
        {	
			calculatePlayoffBrackets();
			
	        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String targetURL = "/nfl/playoffBrackets.xhtml";
		    ec.redirect(targetURL);
            logger.info("successfully redirected to: " + targetURL);
        } 
        catch (Exception e) 
        {
            logger.error("standingsReport exception: " + e.getMessage(), e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
        }
	}  	

	private void calculatePlayoffBrackets() throws Exception
	{
		List<NflPlayoffTeam> playoffTeamsList = nflMain.getPlayoffTeamsList();		
		
		if (playoffTeamsList.size() > 0)
		{
			for (int i = 0; i < playoffTeamsList.size(); i++) 
			{
				NflPlayoffTeam playoffTeam = playoffTeamsList.get(i);				
				playoffTeamSeedsMap.put(playoffTeam.getiTeamID(), playoffTeam.getiSeed());				
			}
			
			for (int j = 0; j < nflMain.getPlayoffGamesList().size(); j++)
			{
				DynamoNflGame game = nflMain.getPlayoffGamesList().get(j);
				
				NflTeam awayTeam = nflMain.getTeamByTeamID(game.getIawayTeamID());
				NflTeam homeTeam = nflMain.getTeamByTeamID(game.getIhomeTeamID());
		
				if (game.getSgameTypeDesc().equalsIgnoreCase(Utils.SUPERBOWL))
				{					
					if (awayTeam.getvConferenceName().equalsIgnoreCase("AFC"))
					{
						playoffBrackets.setAfcChampionSeed("(" + playoffTeamSeedsMap.get(game.getIawayTeamID()) + ")");
						playoffBrackets.setAfcChampionPicture("/images/" + awayTeam.getsPictureFile());
						if (game.getIawayTeamScore() != null)
						{
							playoffBrackets.setAfcChampionScore(game.getIawayTeamScore().toString());
						}
						playoffBrackets.setAfcChampionTeamAbbr(awayTeam.getcTeamCityAbbr());
						playoffBrackets.setNfcChampionPicture("/images/" + homeTeam.getsPictureFile());
						if (game.getIhomeTeamScore() != null)
						{
							playoffBrackets.setNfcChampionScore(game.getIhomeTeamScore().toString());
						}
						playoffBrackets.setNfcChampionTeamAbbr(homeTeam.getcTeamCityAbbr());
						playoffBrackets.setNfcChampionSeed("(" + playoffTeamSeedsMap.get(game.getIhomeTeamID())  + ")");
					}
					else
					{
						playoffBrackets.setAfcChampionSeed("(" + playoffTeamSeedsMap.get(game.getIhomeTeamID())  + ")");
						playoffBrackets.setAfcChampionPicture("/images/" + homeTeam.getsPictureFile());
						if (game.getIhomeTeamScore() != null)
						{
							playoffBrackets.setAfcChampionScore(game.getIhomeTeamScore().toString());
						}
						playoffBrackets.setAfcChampionTeamAbbr(homeTeam.getcTeamCityAbbr());
						playoffBrackets.setNfcChampionPicture("/images/" + awayTeam.getsPictureFile());
						if (game.getIawayTeamScore() != null)
						{
							playoffBrackets.setNfcChampionScore(game.getIawayTeamScore().toString());
						}
						playoffBrackets.setNfcChampionTeamAbbr(awayTeam.getcTeamCityAbbr());
						playoffBrackets.setNfcChampionSeed("(" + playoffTeamSeedsMap.get(game.getIawayTeamID()) + ")");
					}
				}
				else if (game.getSgameTypeDesc().equalsIgnoreCase(Utils.CONFCHAMPIONSHIPS))
				{
					if (awayTeam.getvConferenceName().equalsIgnoreCase("AFC"))
					{
						playoffBrackets.setAfcChampionshipRoadSeed("(" + playoffTeamSeedsMap.get(game.getIawayTeamID()) + ")");
						playoffBrackets.setAfcChampionshipRoadPicture("/images/" + awayTeam.getsPictureFile());
						if (game.getIawayTeamScore() != null)
						{
							playoffBrackets.setAfcChampionshipRoadScore(game.getIawayTeamScore().toString());
						}
						playoffBrackets.setAfcChampionshipRoadTeamAbbr(awayTeam.getcTeamCityAbbr());
						playoffBrackets.setAfcChampionshipHomePicture("/images/" + homeTeam.getsPictureFile());
						if (game.getIhomeTeamScore() != null)
						{
							playoffBrackets.setAfcChampionshipHomeScore(game.getIhomeTeamScore().toString());
						}
						playoffBrackets.setAfcChampionshipHomeTeamAbbr(homeTeam.getcTeamCityAbbr());
						playoffBrackets.setAfcChampionshipHomeSeed("(" + playoffTeamSeedsMap.get(game.getIhomeTeamID())  + ")");
					}
					else //NFC championship
					{
						playoffBrackets.setNfcChampionshipHomeSeed("(" + playoffTeamSeedsMap.get(game.getIhomeTeamID())  + ")");
						playoffBrackets.setNfcChampionshipHomePicture("/images/" + homeTeam.getsPictureFile());
						if (game.getIhomeTeamScore() != null)
						{
							playoffBrackets.setNfcChampionshipHomeScore(game.getIhomeTeamScore().toString());
						}
						playoffBrackets.setNfcChampionshipHomeTeamAbbr(homeTeam.getcTeamCityAbbr());
						playoffBrackets.setNfcChampionshipRoadPicture("/images/" + awayTeam.getsPictureFile());
						if (game.getIawayTeamScore() != null)
						{
							playoffBrackets.setNfcChampionshipRoadScore(game.getIawayTeamScore().toString());
						}
						playoffBrackets.setNfcChampionshipRoadTeamAbbr(awayTeam.getcTeamCityAbbr());
						playoffBrackets.setNfcChampionshipRoadSeed("(" + playoffTeamSeedsMap.get(game.getIawayTeamID()) + ")");
					}
				}
				else if (game.getSgameTypeDesc().equalsIgnoreCase(Utils.DIVISIONALS))
				{
					if (awayTeam.getvConferenceName().equalsIgnoreCase("AFC"))
					{
						if (playoffBrackets.getAfcDivisional1RoadSeed() == null || playoffBrackets.getAfcDivisional1RoadSeed().trim().length() == 0)
						{
							playoffBrackets.setAfcDivisional1RoadSeed("(" + playoffTeamSeedsMap.get(game.getIawayTeamID()) + ")");
							playoffBrackets.setAfcDivisional1RoadPicture("/images/" + awayTeam.getsPictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBrackets.setAfcDivisional1RoadScore(game.getIawayTeamScore().toString());
							}
							playoffBrackets.setAfcDivisional1RoadTeamAbbr(awayTeam.getcTeamCityAbbr());
							playoffBrackets.setAfcDivisional1HomePicture("/images/" + homeTeam.getsPictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBrackets.setAfcDivisional1HomeScore(game.getIhomeTeamScore().toString());
							}
							playoffBrackets.setAfcDivisional1HomeTeamAbbr(homeTeam.getcTeamCityAbbr());
							playoffBrackets.setAfcDivisional1HomeSeed("(" + playoffTeamSeedsMap.get(game.getIhomeTeamID())  + ")");
						}
						else //populate afc divisional 2
						{
							playoffBrackets.setAfcDivisional2RoadSeed("(" + playoffTeamSeedsMap.get(game.getIawayTeamID()) + ")");
							playoffBrackets.setAfcDivisional2RoadPicture("/images/" + awayTeam.getsPictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBrackets.setAfcDivisional2RoadScore(game.getIawayTeamScore().toString());
							}
							playoffBrackets.setAfcDivisional2RoadTeamAbbr(awayTeam.getcTeamCityAbbr());
							playoffBrackets.setAfcDivisional2HomePicture("/images/" + homeTeam.getsPictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBrackets.setAfcDivisional2HomeScore(game.getIhomeTeamScore().toString());
							}
							playoffBrackets.setAfcDivisional2HomeTeamAbbr(homeTeam.getcTeamCityAbbr());
							playoffBrackets.setAfcDivisional2HomeSeed("(" + playoffTeamSeedsMap.get(game.getIhomeTeamID())  + ")");					
						}							
					}
					else //NFC Divisional
					{
						if (playoffBrackets.getNfcDivisional1RoadSeed() == null || playoffBrackets.getNfcDivisional1RoadSeed().trim().length() == 0)
						{
							playoffBrackets.setNfcDivisional1RoadSeed("(" + playoffTeamSeedsMap.get(game.getIawayTeamID()) + ")");
							playoffBrackets.setNfcDivisional1RoadPicture("/images/" + awayTeam.getsPictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBrackets.setNfcDivisional1RoadScore(game.getIawayTeamScore().toString());
							}
							playoffBrackets.setNfcDivisional1RoadTeamAbbr(awayTeam.getcTeamCityAbbr());
							playoffBrackets.setNfcDivisional1HomePicture("/images/" + homeTeam.getsPictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBrackets.setNfcDivisional1HomeScore(game.getIhomeTeamScore().toString());
							}
							playoffBrackets.setNfcDivisional1HomeTeamAbbr(homeTeam.getcTeamCityAbbr());
							playoffBrackets.setNfcDivisional1HomeSeed("(" + playoffTeamSeedsMap.get(game.getIhomeTeamID())  + ")");
						}
						else //populate nfc divisional 2
						{
							playoffBrackets.setNfcDivisional2RoadSeed("(" + playoffTeamSeedsMap.get(game.getIawayTeamID()) + ")");
							playoffBrackets.setNfcDivisional2RoadPicture("/images/" + awayTeam.getsPictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBrackets.setNfcDivisional2RoadScore(game.getIawayTeamScore().toString());
							}
							playoffBrackets.setNfcDivisional2RoadTeamAbbr(awayTeam.getcTeamCityAbbr());
							playoffBrackets.setNfcDivisional2HomePicture("/images/" + homeTeam.getsPictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBrackets.setNfcDivisional2HomeScore(game.getIhomeTeamScore().toString());
							}
							playoffBrackets.setNfcDivisional2HomeTeamAbbr(homeTeam.getcTeamCityAbbr());
							playoffBrackets.setNfcDivisional2HomeSeed("(" + playoffTeamSeedsMap.get(game.getIhomeTeamID())  + ")");					
						}
					}
				}
				else if (game.getSgameTypeDesc().equalsIgnoreCase(Utils.WILD_CARD))
				{
					if (awayTeam.getvConferenceName().equalsIgnoreCase("AFC"))
					{
						if (playoffBrackets.getAfcWildCard1RoadSeed() == null || playoffBrackets.getAfcWildCard1RoadSeed().trim().length() == 0)
						{
							playoffBrackets.setAfcWildCard1RoadSeed("(" + playoffTeamSeedsMap.get(game.getIawayTeamID()) + ")");
							playoffBrackets.setAfcWildCard1RoadPicture("/images/" + awayTeam.getsPictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBrackets.setAfcWildCard1RoadScore(game.getIawayTeamScore().toString());
							}
							playoffBrackets.setAfcWildCard1RoadTeamAbbr(awayTeam.getcTeamCityAbbr());
							playoffBrackets.setAfcWildCard1HomePicture("/images/" + homeTeam.getsPictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBrackets.setAfcWildCard1HomeScore(game.getIhomeTeamScore().toString());
							}
							playoffBrackets.setAfcWildCard1HomeTeamAbbr(homeTeam.getcTeamCityAbbr());
							playoffBrackets.setAfcWildCard1HomeSeed("(" + playoffTeamSeedsMap.get(game.getIhomeTeamID())  + ")");
						}
						else if (playoffBrackets.getAfcWildCard2RoadSeed() == null || playoffBrackets.getAfcWildCard2RoadSeed().trim().length() == 0)
						{
							playoffBrackets.setAfcWildCard2RoadSeed("(" + playoffTeamSeedsMap.get(game.getIawayTeamID()) + ")");
							playoffBrackets.setAfcWildCard2RoadPicture("/images/" + awayTeam.getsPictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBrackets.setAfcWildCard2RoadScore(game.getIawayTeamScore().toString());
							}
							playoffBrackets.setAfcWildCard2RoadTeamAbbr(awayTeam.getcTeamCityAbbr());
							playoffBrackets.setAfcWildCard2HomePicture("/images/" + homeTeam.getsPictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBrackets.setAfcWildCard2HomeScore(game.getIhomeTeamScore().toString());
							}
							playoffBrackets.setAfcWildCard2HomeTeamAbbr(homeTeam.getcTeamCityAbbr());
							playoffBrackets.setAfcWildCard2HomeSeed("(" + playoffTeamSeedsMap.get(game.getIhomeTeamID())  + ")");
						}
						else //populate afc wild card 3
						{
							playoffBrackets.setThreeWildCardGames(true);
							
							playoffBrackets.setAfcWildCard3RoadSeed("(" + playoffTeamSeedsMap.get(game.getIawayTeamID()) + ")");
							playoffBrackets.setAfcWildCard3RoadPicture("/images/" + awayTeam.getsPictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBrackets.setAfcWildCard3RoadScore(game.getIawayTeamScore().toString());
							}
							playoffBrackets.setAfcWildCard3RoadTeamAbbr(awayTeam.getcTeamCityAbbr());
							playoffBrackets.setAfcWildCard3HomePicture("/images/" + homeTeam.getsPictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBrackets.setAfcWildCard3HomeScore(game.getIhomeTeamScore().toString());
							}
							playoffBrackets.setAfcWildCard3HomeTeamAbbr(homeTeam.getcTeamCityAbbr());
							playoffBrackets.setAfcWildCard3HomeSeed("(" + playoffTeamSeedsMap.get(homeTeam.getiTeamID())  + ")");	
						}				
					}
					else //NFC
					{
						if (playoffBrackets.getNfcWildCard1RoadSeed() == null || playoffBrackets.getNfcWildCard1RoadSeed().trim().length() == 0)
						{
							playoffBrackets.setNfcWildCard1RoadSeed("(" + playoffTeamSeedsMap.get(awayTeam.getiTeamID()) + ")");
							playoffBrackets.setNfcWildCard1RoadPicture("/images/" + awayTeam.getsPictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBrackets.setNfcWildCard1RoadScore(game.getIawayTeamScore().toString());
							}
							playoffBrackets.setNfcWildCard1RoadTeamAbbr(awayTeam.getcTeamCityAbbr());
							playoffBrackets.setNfcWildCard1HomePicture("/images/" + homeTeam.getsPictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBrackets.setNfcWildCard1HomeScore(game.getIhomeTeamScore().toString());
							}
							playoffBrackets.setNfcWildCard1HomeTeamAbbr(homeTeam.getcTeamCityAbbr());
							playoffBrackets.setNfcWildCard1HomeSeed("(" + playoffTeamSeedsMap.get(homeTeam.getiTeamID())  + ")");
						}
						else if (playoffBrackets.getNfcWildCard2RoadSeed() == null || playoffBrackets.getNfcWildCard2RoadSeed().trim().length() == 0)
						{
							playoffBrackets.setNfcWildCard2RoadSeed("(" + playoffTeamSeedsMap.get(awayTeam.getiTeamID()) + ")");
							playoffBrackets.setNfcWildCard2RoadPicture("/images/" + awayTeam.getsPictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBrackets.setNfcWildCard2RoadScore(game.getIawayTeamScore().toString());
							}
							playoffBrackets.setNfcWildCard2RoadTeamAbbr(awayTeam.getcTeamCityAbbr());
							playoffBrackets.setNfcWildCard2HomePicture("/images/" + homeTeam.getsPictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBrackets.setNfcWildCard2HomeScore(game.getIhomeTeamScore().toString());
							}
							playoffBrackets.setNfcWildCard2HomeTeamAbbr(homeTeam.getcTeamCityAbbr());
							playoffBrackets.setNfcWildCard2HomeSeed("(" + playoffTeamSeedsMap.get(homeTeam.getiTeamID())  + ")");
						}
						else //populate nfc wild card 3
						{
							playoffBrackets.setNfcWildCard3RoadSeed("(" + playoffTeamSeedsMap.get(awayTeam.getiTeamID()) + ")");
							playoffBrackets.setNfcWildCard3RoadPicture("/images/" + awayTeam.getsPictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBrackets.setNfcWildCard3RoadScore(game.getIawayTeamScore().toString());
							}
							playoffBrackets.setNfcWildCard3RoadTeamAbbr(awayTeam.getcTeamCityAbbr());
							playoffBrackets.setNfcWildCard3HomePicture("/images/" + homeTeam.getsPictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBrackets.setNfcWildCard3HomeScore(game.getIhomeTeamScore().toString());
							}
							playoffBrackets.setNfcWildCard3HomeTeamAbbr(homeTeam.getcTeamCityAbbr());
							playoffBrackets.setNfcWildCard3HomeSeed("(" + playoffTeamSeedsMap.get(homeTeam.getiTeamID())  + ")");	
						}				
					}
				}
										
			}		
			
		}
		else //playoffs not ready yet
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"No playoffs setup complete yet",null);
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
			
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
	
	
	public void playoffBracketsSetup(ActionEvent event) 
	{
		logger.info("playoff Brackets Setup selected from menu");
		
		try 
        {		
			this.setAfcTeamsList(insertBlankRowsInList(nflMain.getAfcTeamsList()));
			this.setNfcTeamsList(insertBlankRowsInList(nflMain.getNfcTeamsList()));
			
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String targetURL = "/nfl/playoffBracketsSetup.xhtml";
		    ec.redirect(targetURL);
            logger.info("successfully redirected to: " + targetURL);
        } 
        catch (Exception e) 
        {
            logger.error("playoff Brackets setup exception: " + e.getMessage(), e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
        }
	}  	
	
	public String doPlayoffsSetup() 
	{	 
		logger.info("entering doPlayoffsSetup");
		
		try 
        {			
			for (int i = 0; i < this.getAfcTeamsList().size(); i++) 
        	{  
				NflTeam afcteam = this.getAfcTeamsList().get(i);
				
				if (afcteam.isPlayoffTeam())
				{
					logger.info(afcteam.getFullTeamName() + " is a playoff team");
					
					NflPlayoffTeam nflPlayoffTeam = new NflPlayoffTeam();
					nflPlayoffTeam.setSeasonYear(nflMain.getCurrentSelectedSeason().getcYear());
					nflPlayoffTeam.setIseasonId(nflMain.getCurrentSelectedSeason().getiSeasonID());
					nflPlayoffTeam.setiConferenceId(afcteam.getiConferenceID());
					nflPlayoffTeam.setiTeamID(afcteam.getiTeamID());
					nflPlayoffTeam.setTeamName(afcteam.getFullTeamName());
					nflPlayoffTeam.setiSeed(afcteam.getiSeed());
					
					if (afcteam.getiSeed() == null)
					{
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"afc playoff team seed is missing",null);
				        FacesContext.getCurrentInstance().addMessage(null, msg); 
				        return "";
					}
					else if (afcteam.getiSeed() == 1)
					{
						nflPlayoffTeam.setbBye(true);
					}
					else
					{
						nflPlayoffTeam.setbBye(false);
					}
					nflMain.addUpdateNflPlayoffTeam(nflPlayoffTeam);
				}
        	}
			
			for (int i = 0; i < this.getNfcTeamsList().size(); i++) 
	    	{
	    		NflTeam nfcteam = this.getNfcTeamsList().get(i);
	    		
	    		if (nfcteam.isPlayoffTeam())
				{
					logger.info(nfcteam.getFullTeamName() + " is a playoff team");
					
					NflPlayoffTeam nflPlayoffTeam = new NflPlayoffTeam();
					nflPlayoffTeam.setSeasonYear(nflMain.getCurrentSelectedSeason().getcYear());
					nflPlayoffTeam.setIseasonId(nflMain.getCurrentSelectedSeason().getiSeasonID());
					nflPlayoffTeam.setiConferenceId(nfcteam.getiConferenceID());
					nflPlayoffTeam.setiTeamID(nfcteam.getiTeamID());
					nflPlayoffTeam.setTeamName(nfcteam.getFullTeamName());
					nflPlayoffTeam.setiSeed(nfcteam.getiSeed());
					
					if (nfcteam.getiSeed() == null)
					{
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"nfc playoff team seed is missing",null);
				        FacesContext.getCurrentInstance().addMessage(null, msg); 
				        return "";
					}
					else if (nfcteam.getiSeed() == 1)
					{
						nflPlayoffTeam.setbBye(true);
					}
					else
					{
						nflPlayoffTeam.setbBye(false);
					}
					nflMain.addUpdateNflPlayoffTeam(nflPlayoffTeam);
				}
			}
			
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Playoffs setup successfully saved",null);
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
			
        }
		catch (Exception e) 
        {
            logger.error("playoff Brackets save exception: " + e.getMessage(), e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
        }
		
		return "";
	}

	private List<NflTeam> insertBlankRowsInList(List<NflTeam> inputList)
	{
		//now let's tweak to insert a blank row between divisions and blank out division name after the first one
		
		List<NflTeam> newList = new ArrayList<>();
		String lastDivisionName = "";
		NflTeam blankTeam = new NflTeam();
		
		for (int j = 0; j < inputList.size(); j++) 
		{
			NflTeam nflteam = inputList.get(j);
			
			if (!nflteam.getvDivisionName().equalsIgnoreCase(lastDivisionName)) 
			{				
				if (j > 0) //don't add blank row first time through list
				{
					newList.add(blankTeam);			
				}
			}
			
			lastDivisionName = nflteam.getvDivisionName();
			newList.add(nflteam);
		}
		
		return newList;
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

	public PlayoffBrackets getPlayoffBrackets() {
		return playoffBrackets;
	}

	public void setPlayoffBrackets(PlayoffBrackets playoffBrackets) {
		this.playoffBrackets = playoffBrackets;
	}
	
}
