package com.pas.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.component.menuitem.UIMenuItem;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.pas.dynamodb.DynamoClients;
import com.pas.dynamodb.DynamoUtil;
import com.pas.nfl.dao.NflGameDAO;
import com.pas.nfl.dao.NflPlayoffTeamDAO;
import com.pas.nfl.dao.NflSeasonDAO;
import com.pas.nfl.dao.NflTeamDAO;
import com.pas.pojo.Decade;
import com.pas.util.Utils;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;

@Named("pc_NflMain")
@SessionScoped
public class NflMain implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(NflMain.class);	
		
	private final double id = Math.random();	
	
	public static String GREEN_STYLECLASS = "menuGreen";
	public static String RED_STYLECLASS = "menuRed";
	public static String YELLOW_STYLECLASS = "menuYellow";
	
	private String siteTitle;	
	
	private String currentSeasonDisplay;
	private NflSeason currentSelectedSeason;
	
	private NflGameDAO nflGameDAO;
	private NflPlayoffTeamDAO nflPlayoffTeamDAO;
	private NflTeamDAO nflTeamDAO;
	private NflSeasonDAO nflSeasonDAO;
		
	public NflMain() 
	{
		logger.info("Entering NflMain constructor.  Should only be here ONE time");	
		logger.info("NflMain id is: " + this.getId());
		this.setSiteTitle("Slomkowski NFL");
		
		try 
		{
			//this gets populated at app startup, no need to do it again when someone logs in.
			if (nflSeasonDAO == null || nflSeasonDAO.getFullNflSeasonList().isEmpty())
			{
				DynamoClients dynamoClients = DynamoUtil.getDynamoClients();				
				
				loadNflSeasons(dynamoClients);
				loadNflTeams(dynamoClients);
				loadNflGames(dynamoClients);
				loadNflPlayoffTeams(dynamoClients);
			}
		} 
		catch (Exception e) 
		{
			logger.error(e.getMessage(), e);
		}		
	}				
	
	private void loadNflSeasons(DynamoClients dynamoClients)  throws Exception
	{
		logger.info("entering loadNflSeasons");
		nflSeasonDAO = new NflSeasonDAO(dynamoClients, this);
		nflSeasonDAO.readNflSeasonsFromDB();
		this.setCurrentSeasonDisplay("Working on Season: " + nflSeasonDAO.getMaxSeason().getcYear());
		logger.info("Nfl Seasons read in. List size = " + nflSeasonDAO.getFullNflSeasonList().size());		
    }
	
	private void loadNflTeams(DynamoClients dynamoClients)  throws Exception
	{
		logger.info("entering loadNflTeams");
		nflTeamDAO = new NflTeamDAO(dynamoClients, this);
		nflTeamDAO.readNflTeamsFromDB();
		logger.info("Nfl Teams read in. List size = " + nflTeamDAO.getFullNflTeamList().size());		
    }
	
	private void loadNflGames(DynamoClients dynamoClients)  throws Exception
	{
		logger.info("entering loadNflGames");
		nflGameDAO = new NflGameDAO(dynamoClients, this);
		nflGameDAO.readNflGamesFromDB();
		logger.info("Nfl Games read in. List size = " + nflGameDAO.getFullNflGameList().size());		
    }
	
	private void loadNflPlayoffTeams(DynamoClients dynamoClients)  throws Exception
	{
		logger.info("entering loadNflPLayoffTeams");
		nflPlayoffTeamDAO = new NflPlayoffTeamDAO(dynamoClients, this);
		nflPlayoffTeamDAO.readNflPlayoffTeamsFromDB();
		logger.info("Nfl Playoff Teams read in. List size = " + nflPlayoffTeamDAO.getFullNflPlayoffTeamList().size());		
    }
	
	public void seasonChange(ActionEvent event) 
	{
		logger.info("new season selected from menu");
		
        try 
        {
            UIMenuItem mi = (UIMenuItem) event.getSource();
            String seasonYear = ((String) mi.getValue());
            this.setCurrentSelectedSeason(this.getFullNflSeasonsMapByYear().get(seasonYear));
            this.setCurrentSeasonDisplay("Working on Season: " + seasonYear);
        } 
        catch (Exception e) 
        {
            logger.error("changeMenu exception: " + e.getMessage(), e);
        }
       
    }
		
	public String getSignedOnUserName() 
	{
		String username = "";
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) 
		{
		   username = ((UserDetails)principal).getUsername();
		} 
		else 
		{
		   username = principal.toString();
		}
		
		if (username != null)
		{
			username = username.toLowerCase();
		}
		return username;
	}
	
	public double getId() {
		return id;
	}
		
	public String getSiteTitle() {
		return siteTitle;
	}

	public void setSiteTitle(String siteTitle) {
		this.siteTitle = siteTitle;
	}

	public NflGameDAO getNflGameDAO() {
		return nflGameDAO;
	}

	public void setNflGameDAO(NflGameDAO nflGameDAO) {
		this.nflGameDAO = nflGameDAO;
	}

	public NflPlayoffTeamDAO getNflPlayoffTeamDAO() {
		return nflPlayoffTeamDAO;
	}

	public void setNflPlayoffTeamDAO(NflPlayoffTeamDAO nflPlayoffTeamDAO) {
		this.nflPlayoffTeamDAO = nflPlayoffTeamDAO;
	}

	public NflTeamDAO getNflTeamDAO() {
		return nflTeamDAO;
	}

	public void setNflTeamDAO(NflTeamDAO nflTeamDAO) {
		this.nflTeamDAO = nflTeamDAO;
	}

	public NflSeasonDAO getNflSeasonDAO() {
		return nflSeasonDAO;
	}

	public void setNflSeasonDAO(NflSeasonDAO nflSeasonDAO) {
		this.nflSeasonDAO = nflSeasonDAO;
	}

	public List<NflSeason> getFullNflSeasonList() 
	{
		return nflSeasonDAO.getFullNflSeasonList();
	}
	
	public List<NflSeason> getSeasons2000sList() 
	{
		return nflSeasonDAO.getSeasons2000sList();
	}
	
	public List<NflSeason> getSeasons2010sList() 
	{
		return nflSeasonDAO.getSeasons2010sList();
	}
	
	public List<NflSeason> getSeasons2020sList() 
	{
		return nflSeasonDAO.getSeasons2020sList();
	}
	
	public List<NflSeason> getSeasons2030sList() 
	{
		return nflSeasonDAO.getSeasons2030sList();
	}
	
	public List<NflSeason> getSeasons2040sList() 
	{
		return nflSeasonDAO.getSeasons2040sList();
	}
	
	public Map<String, NflSeason> getFullNflSeasonsMapByYear() 
	{
		return nflSeasonDAO.getFullNflSeasonsMapByYear();
	}
	
	public List<Decade> getDecadesList()
	{
		return Utils.getDecadesList();
	}

	public String getCurrentSeasonDisplay() {
		return currentSeasonDisplay;
	}

	public void setCurrentSeasonDisplay(String currentSeasonDisplay) {
		this.currentSeasonDisplay = currentSeasonDisplay;
	}

	public NflSeason getCurrentSelectedSeason() {
		return currentSelectedSeason;
	}

	public void setCurrentSelectedSeason(NflSeason currentSelectedSeason) {
		this.currentSelectedSeason = currentSelectedSeason;
	}
		
}
