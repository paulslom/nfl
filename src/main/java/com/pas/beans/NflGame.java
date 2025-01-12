package com.pas.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.dynamodb.DateToStringConverter;
import com.pas.dynamodb.DynamoNflGame;
import com.pas.util.Utils;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("pc_NflGame")
@SessionScoped
public class NflGame implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(NflGame.class);	
	
	private DynamoNflGame selectedGame;
        
    @Inject NflMain nflMain;
    
    public String toString()
    {
    	return "seasonid: " + this.getSelectedGame().getiSeasonId() + " game date: " + selectedGame.getDgameDateTime() + " " + this.getSelectedGame().getCawayteamCityAbbr() + " @ " + this.getSelectedGame().getChometeamCityAbbr();
    }
   
    public String changeGameScores()
    {
    	logger.info("entering changeGameScores");
		
		try
		{
			List<DynamoNflGame> newList = new ArrayList<>(nflMain.getGameScoresList());	
			for (int i = 0; i < newList.size(); i++) 
			{
				DynamoNflGame game = newList.get(i);
				Utils.updateScoreStyles(game);
				nflMain.getNflGameDAO().updateNflGame(game);
				logger.info("update game score count: " + i);
			}
			
		}
		catch (Exception e)
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
	        logger.error("changeGameScores exception: " + e.getMessage(), e);
			return "";
		}
	    return "gamesList.xhtml";
    }
    
    public String addChangeDelGame() 
	{	 
		logger.info("entering addChangeDelGame.  Action will be: " + nflMain.getGameAcidSetting());
		
		if (!validateGameEntry()) //will be true if all good.  If false, we leave
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Please enter all required fields",null);
	        FacesContext.getCurrentInstance().addMessage(null, msg);    
			return "";
		}
		
		try
		{
			if (nflMain.getGameAcidSetting().equalsIgnoreCase("Add"))
			{			
				setAddUpdateFields();			   
				nflMain.getNflGameDAO().addNflGame(this.getSelectedGame());
			}
			else if (nflMain.getGameAcidSetting().equalsIgnoreCase("Update"))
			{
				setAddUpdateFields();		
				nflMain.getNflGameDAO().updateNflGame(this.getSelectedGame());
			}
			else if (nflMain.getGameAcidSetting().equalsIgnoreCase("Delete"))
			{
				nflMain.getNflGameDAO().deleteNflGame(this.getSelectedGame());
			}
		}
		catch (Exception e)
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
	        FacesContext.getCurrentInstance().addMessage(null, msg);    
			return "";
		}
	    return "gamesList.xhtml";
	}
  	private void setAddUpdateFields() 
    {
    	/* already set on page:
		iweeknumber
		igametypeid
		iawayteamid
		ihometeamid
		iawayteamscore
		ihometeamscore
		date - but we have to manipulate this
		*/
    	
		this.getSelectedGame().setiSeasonId(nflMain.getCurrentSelectedSeason().getiSeasonID());
		this.getSelectedGame().setcYear(nflMain.getCurrentSelectedSeason().getcYear());
		
		Utils.updateScoreStyles(this.getSelectedGame());
		        
		this.getSelectedGame().setGameDayOfWeek(this.getSelectedGame().getGameDateTimeDisplay().substring(0, 3));
		this.getSelectedGame().setGameDateOnly(this.getSelectedGame().getGameDateTimeDisplay().substring(4, 14));
		this.getSelectedGame().setGameTimeOnly(this.getSelectedGame().getGameDateTimeDisplay().substring(15));
		
		this.getSelectedGame().setDgameDateTime(DateToStringConverter.convertDateToDynamoStringFormat(this.getSelectedGame().getGameDateTimeDisplay()));
		
		NflTeam homeTeam = nflMain.getTeamByTeamID(this.getSelectedGame().getIhomeTeamID());
		NflTeam awayTeam = nflMain.getTeamByTeamID(this.getSelectedGame().getIawayTeamID());
		
		this.getSelectedGame().setCawayteamCityAbbr(awayTeam.getcTeamCityAbbr());
		this.getSelectedGame().setAwayteamName(awayTeam.getFullTeamName());
		this.getSelectedGame().setChometeamCityAbbr(homeTeam.getcTeamCityAbbr());
		this.getSelectedGame().setHometeamName(homeTeam.getFullTeamName());
					   
		this.getSelectedGame().setSgameTypeDesc(nflMain.getGameTypeDescriptionByGameTypeId(this.getSelectedGame().getIgameTypeId()));
		this.getSelectedGame().setSweekDescription(nflMain.getGameTypeDescriptionByGameTypeId(this.getSelectedGame().getIgameTypeId()));
		this.getSelectedGame().setIweekId(nflMain.getAddedWeekId(this.getSelectedGame().getSgameTypeDesc()));
		
		if (this.getSelectedGame().getSgameTypeDesc().equalsIgnoreCase("Regular Season"))
		{
			this.getSelectedGame().setIplayoffRound(0);
		}
		else if (this.getSelectedGame().getSgameTypeDesc().equalsIgnoreCase(Utils.WILD_CARD))
		{
			this.getSelectedGame().setIplayoffRound(1);
		}
		else if (this.getSelectedGame().getSgameTypeDesc().equalsIgnoreCase(Utils.DIVISIONALS))
		{
			this.getSelectedGame().setIplayoffRound(2);
		}
		else if (this.getSelectedGame().getSgameTypeDesc().equalsIgnoreCase(Utils.CONFCHAMPIONSHIPS))
		{
			this.getSelectedGame().setIplayoffRound(3);
		}
		else if (this.getSelectedGame().getSgameTypeDesc().equalsIgnoreCase(Utils.SUPERBOWL))
		{
			this.getSelectedGame().setIplayoffRound(4);
		}
	}

	public void selectGameforAcid(ActionEvent event) 
	{
		logger.info("game selected for add-change-inquire-delete");
		
		try 
        {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		    String acid = ec.getRequestParameterMap().get("operation");
		    String gameId = ec.getRequestParameterMap().get("gameId");
		    nflMain.setGameAcidSetting(acid);
		    logger.info("game id selected: " + gameId);
		    
		    if (nflMain.getGameAcidSetting().equalsIgnoreCase("add"))
		    {
		    	this.setSelectedGame(new DynamoNflGame()); 
		    }
		    else //go get the existing game
		    {
		    	this.setSelectedGame(nflMain.getGameByGameID(Integer.parseInt(gameId)));
		    }
		    
		    nflMain.setRenderGameViewAddUpdateDelete(); 
		    
		    String targetURL = "/nfl/gameAddUpdate.xhtml";
		    ec.redirect(targetURL);
            logger.info("successfully redirected to: " + targetURL + " with operation: " + acid);
        } 
        catch (Exception e) 
        {
            logger.error("selectGameforAcid exception: " + e.getMessage(), e);
        }
	}
    
    private boolean validateGameEntry() 
    {
		boolean fieldsValidated = true; //assume true, if anything wrong make it false and get out
		
		if (this.getSelectedGame().getIweekNumber() == null)
		{
			fieldsValidated = false;
		}
		if (this.getSelectedGame().getIawayTeamID() == null)
		{
			fieldsValidated = false;
		}
		if (this.getSelectedGame().getIhomeTeamID() == null)
		{
			fieldsValidated = false;
		}
		
		if (this.getSelectedGame().getGameDateTimeDisplay() == null || this.getSelectedGame().getGameDateTimeDisplay().trim().length() == 0)
		{
			fieldsValidated = false;
		}
		else //something in the gamedatetime - make sure it parses in our format
		{
			SimpleDateFormat sdf = new SimpleDateFormat("E yyyy-MM-dd HH:mm a");
	        try 
	        {
	            Date parsedDate = sdf.parse(this.getSelectedGame().getGameDateTimeDisplay());
	            logger.debug("parsed date = " + parsedDate);
	        }
	        catch (Exception e) 
	        {
	        	logger.error("Error parsing date: " + this.getSelectedGame().getGameDateTimeDisplay());
	        	fieldsValidated = false;
	        }
		}
		return fieldsValidated;
	}

	public DynamoNflGame getSelectedGame() {
		return selectedGame;
	}

	public void setSelectedGame(DynamoNflGame selectedGame) {
		this.selectedGame = selectedGame;
	}
	
}
