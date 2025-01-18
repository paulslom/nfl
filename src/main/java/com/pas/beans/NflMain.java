package com.pas.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.component.menuitem.UIMenuItem;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.pas.dynamodb.DynamoClients;
import com.pas.dynamodb.DynamoNflGame;
import com.pas.dynamodb.DynamoUtil;
import com.pas.nfl.dao.NflGameDAO;
import com.pas.nfl.dao.NflPlayoffTeamDAO;
import com.pas.nfl.dao.NflSeasonDAO;
import com.pas.nfl.dao.NflTeamDAO;
import com.pas.pojo.Decade;
import com.pas.pojo.InnerWeek;
import com.pas.pojo.OuterWeek;
import com.pas.util.Utils;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;

@Named("pc_NflMain")
@SessionScoped
public class NflMain implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(NflMain.class);	
		
	private final double id = Math.random();	
	
	public static String GREEN_STYLECLASS_MENU = "menuGreen";
	public static String RED_STYLECLASS_MENU = "menuRed";
	
	public static String GREEN_STYLECLASS = "resultGreen";
	public static String RED_STYLECLASS = "resultRed";
	public static String YELLOW_STYLECLASS = "resultYellow";
	
	private String siteTitle;	
	
	private List<InnerWeek> currentWeekList = new ArrayList<>();
	private List<InnerWeek> currentWeekFirstHalfList = new ArrayList<>();
	private List<InnerWeek> currentWeekSecondHalfList = new ArrayList<>();
		
	private String currentSeasonDisplay;
	private NflSeason currentSelectedSeason;
	private Integer selectedTeamID;
	private Integer selectedWeekNumber;
	private String selectedWeekDescription;
	private List<Integer> weekNumbersList = new ArrayList<>();
	
	private String gameAcidSetting = "";
	
	private boolean renderGameUpdateFields = false;
	private boolean renderGameId = false;
	
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
				loadNflGames(dynamoClients);
				loadNflTeams(dynamoClients,this.getFullNflSeasonList());
				
				loadNflPlayoffTeams(dynamoClients);
				
				this.setCurrentWeekList(calculateCurrentWeekList());
				breakUpWeekLists();
				
				Integer[] weeks = new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
				weekNumbersList = Arrays.asList(weeks);
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
		nflSeasonDAO = new NflSeasonDAO(dynamoClients);
		nflSeasonDAO.readNflSeasonsFromDB();
		this.setCurrentSeasonDisplay("Working on Season: " + nflSeasonDAO.getMaxSeason().getcYear());
		this.setCurrentSelectedSeason(nflSeasonDAO.getMaxSeason());
		logger.info("Nfl Seasons read in. List size = " + nflSeasonDAO.getFullNflSeasonList().size());		
    }
	
	private void loadNflTeams(DynamoClients dynamoClients, List<NflSeason> nflSeasonsList)  throws Exception
	{
		logger.info("entering loadNflTeams");
		nflTeamDAO = new NflTeamDAO(dynamoClients);
		nflTeamDAO.readNflTeamsFromDB(nflSeasonsList);
		logger.info("Nfl Teams read in. List size = " + nflTeamDAO.getFullNflTeamList().size());		
    }
	
	private void loadNflGames(DynamoClients dynamoClients)  throws Exception
	{
		logger.info("entering loadNflGames");
		nflGameDAO = new NflGameDAO(dynamoClients);
		nflGameDAO.readNflGamesFromDB();
		logger.info("Nfl Games read in. List size = " + nflGameDAO.getFullNflGameList().size());		
    }
	
	private void loadNflPlayoffTeams(DynamoClients dynamoClients)  throws Exception
	{
		logger.info("entering loadNflPLayoffTeams");
		nflPlayoffTeamDAO = new NflPlayoffTeamDAO(dynamoClients);
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
            this.setCurrentWeekList(calculateCurrentWeekList());
            nflTeamDAO.setThisSeasonsTeams(this.getCurrentSelectedSeason().getiSeasonID());
            nflGameDAO.setMaxRegularSeasonWeek();
            nflGameDAO.setSeasonGamesList(nflGameDAO.getGamesMapBySeason().get(this.getCurrentSelectedSeason().getiSeasonID()));
        } 
        catch (Exception e) 
        {
            logger.error("changeMenu exception: " + e.getMessage(), e);
        }
       
    }
	
	public void selectGameScoresWeek(ActionEvent event) 
	{
		logger.info("game scores week selected from menu");
		
		try 
        {
            UIMenuItem mi = (UIMenuItem) event.getSource();
            Object menusel = mi.getValue();
            
            Integer weekNumber = 0;
            String weekDescription = "";
            
            if (menusel instanceof Integer)
            {
            	weekNumber = (Integer) menusel;
            	logger.info("week number picked: " + weekNumber);
            	this.setSelectedWeekNumber(weekNumber);
            }
            else if (menusel instanceof String)
            {
            	weekDescription = (String) menusel;
            	logger.info("week description picked: " + weekDescription);
            	this.setSelectedWeekDescription(weekDescription);
            }
             
            this.setSelectedTeamID(0); //so as to assure we don't pick a team in getGameScoresList
            
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();		   		    
		    String targetURL = "/nfl/gameScores.xhtml";
		    ec.redirect(targetURL);
            logger.info("successfully redirected to: " + targetURL);
        } 
        catch (Exception e) 
        {
            logger.error("selectGameScoresWeek exception: " + e.getMessage(), e);
        }
	}
	
	public String createNextSeason()
	{
		try
		{
			nflSeasonDAO.createNextSeason();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Next season successfully created",null);
			FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
		catch (Exception e)
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, msg); 
			logger.error("createNextSeason exception: " + e.getMessage(), e);
		}
		
		return "";
		
	}
	
	public String importScheduleData()
	{
		try
		{
			String nextYear = nflSeasonDAO.getMaxSeason().getcYear();
			nflGameDAO.importNextSeasonSchedule(nextYear);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Games successfully imported",null);
			FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
		catch (Exception e)
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
			FacesContext.getCurrentInstance().addMessage(null, msg); 
			logger.error("importScheduleData exception: " + e.getMessage(), e);
		}
		
		return "";
		
	}
	
	public void selectGameScoresTeam(ActionEvent event) 
	{
		logger.info("game scores team selected from menu");
		
		try 
        {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		    String teamid = ec.getRequestParameterMap().get("teamid");
		    
            UIMenuItem mi = (UIMenuItem) event.getSource();
            Object menusel = mi.getValue();
            
            String teamname = (String) menusel;
            logger.info("team picked: " + teamname);
            this.setSelectedTeamID(Integer.parseInt(teamid));  
            
            this.setSelectedWeekNumber(0); //so as to assure we don't pick a week in getGameScoresList
            this.setSelectedWeekDescription("");; //so as to assure we don't pick a week in getGameScoresList

            String targetURL = "/nfl/gameScores.xhtml";
		    ec.redirect(targetURL);
            logger.info("successfully redirected to: " + targetURL);
        } 
        catch (Exception e) 
        {
            logger.error("selectGameScoresWeek exception: " + e.getMessage(), e);
        }
	}
	
	
			
	private List<InnerWeek> calculateCurrentWeekList() 
	{		
		List<InnerWeek> returnList = new ArrayList<>();
		List<DynamoNflGame> thisSeasonsGamesList = nflGameDAO.getGamesMapBySeason().get(this.getCurrentSelectedSeason().getiSeasonID());
		
		//we need a list of unique weeks for this season.  Have the key be the week number, and the value be an InnerWeek.  Then we can stream to a list from the map later.
		Map<Integer, InnerWeek> tempMap = new HashMap<>();
		
		for (int i = 0; i < thisSeasonsGamesList.size(); i++) 
		{
			DynamoNflGame nflgame = thisSeasonsGamesList.get(i);
			if (!tempMap.containsKey(nflgame.getIweekNumber()))
			{
				InnerWeek innerweek = new InnerWeek();
				innerweek.setWeekId(nflgame.getIweekId());
				innerweek.setWeekNumber(nflgame.getIweekNumber());
				tempMap.put(nflgame.getIweekNumber(), innerweek);
			}
		}
		
		Collection<InnerWeek> values = tempMap.values();
		returnList = new ArrayList<>(values);
		
		return returnList;
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

	public List<DynamoNflGame> getGameScoresList()
	{
		boolean byWeekNumber = false;
		boolean byWeekDescription = false;
		boolean byTeam = false;
		
		if (this.getSelectedWeekNumber() != null && this.getSelectedWeekNumber() != 0)
		{
			byWeekNumber = true;
		}
		else if (this.getSelectedWeekDescription() != null && this.getSelectedWeekDescription().trim().length() > 0)
		{
			byWeekDescription = true;
		}
		else if (this.getSelectedTeamID() != null && this.getSelectedTeamID() != 0)
		{
			byTeam = true;
		}
		
		if (byWeekNumber)
		{
			return nflGameDAO.getGameScoresList("byWeek",this.getSelectedWeekNumber());
		}
		else if (byTeam)
		{
			return nflGameDAO.getGameScoresList("byTeam", this.getSelectedTeamID());
		}
		else //must be a playoff week selection...
		{
			logger.debug("going by week description - must have selected a playoff week " + byWeekDescription);
			Integer maxRegSeasonWeekNum = nflGameDAO.getMaxRegularSeasonWeekNumber();
			if (this.getSelectedWeekDescription().equalsIgnoreCase(Utils.WILD_CARD))
			{
				return nflGameDAO.getGameScoresList("byWeek",maxRegSeasonWeekNum + 1);
			}
			else if (this.getSelectedWeekDescription().equalsIgnoreCase(Utils.DIVISIONALS))
			{
				return nflGameDAO.getGameScoresList("byWeek",maxRegSeasonWeekNum + 2);
			}
			else if (this.getSelectedWeekDescription().equalsIgnoreCase(Utils.CONFCHAMPIONSHIPS))
			{
				return nflGameDAO.getGameScoresList("byWeek",maxRegSeasonWeekNum + 3);
			}
			else if (this.getSelectedWeekDescription().equalsIgnoreCase(Utils.SUPERBOWL))
			{
				return nflGameDAO.getGameScoresList("byWeek",maxRegSeasonWeekNum + 4);
			}
			else //can't be anything else
			{
				return new ArrayList<DynamoNflGame>();
			}
		}
	}
	
	public List<DynamoNflGame> getSeasonGamesList()
	{
		return nflGameDAO.getSeasonGamesList();
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

	public List<String> getDivisionsList()
	{
		return Utils.getDivisionsList(this.getTeamsListCurrentSeason());
	}
	
	public List<OuterWeek> getOuterWeeksList()
	{
		return Utils.getOuterWeeksList();
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

	public List<InnerWeek> getCurrentWeekList() {
		return currentWeekList;
	}

	public void setCurrentWeekList(List<InnerWeek> currentWeekList) {
		this.currentWeekList = currentWeekList;
	}

	public List<InnerWeek> getCurrentWeekFirstHalfList() 
	{		
		return currentWeekFirstHalfList;
	}

	public void setCurrentWeekFirstHalfList(List<InnerWeek> currentWeekFirstHalfList) {
		this.currentWeekFirstHalfList = currentWeekFirstHalfList;
	}

	private void breakUpWeekLists()
	{
		List<InnerWeek> tempList1 = new ArrayList<>();
		for (int i = 0; i < this.getCurrentWeekList().size(); i++) 
		{
			InnerWeek innerweek = this.getCurrentWeekList().get(i);
			if (innerweek.getWeekNumber() <= 9)
			{
				tempList1.add(innerweek);
			}
		}
		setCurrentWeekFirstHalfList(tempList1);
		
		List<InnerWeek> tempList2 = new ArrayList<>();
		for (int i = 0; i < this.getCurrentWeekList().size(); i++) 
		{
			InnerWeek innerweek = this.getCurrentWeekList().get(i);
			if (innerweek.getWeekNumber() >= 10)
			{
				tempList2.add(innerweek);
			}
		}
		setCurrentWeekSecondHalfList(tempList2);
	}
	
	public List<DynamoNflGame> getPlayoffGamesList() 
	{		
		return nflGameDAO.getPlayoffGamesList();
	}
	
	public List<SelectItem> getGameTypesList() 
	{		
		return nflGameDAO.getGameTypesList();
	}
	
	public List<SelectItem> getTeamsDropdownListCurrentSeason() 
	{		
		return nflTeamDAO.getTeamsDropdownListCurrentSeason();
	}
	
	public List<NflTeam> getTeamsListCurrentSeason() 
	{		
		return nflTeamDAO.getTeamsListCurrentSeason();
	}
	
	public List<NflPlayoffTeam> getPlayoffTeamsList() 
	{		
		return nflPlayoffTeamDAO.getPlayoffTeamsList();
	}
	
	public void addUpdateNflPlayoffTeam(NflPlayoffTeam nflPlayoffTeam) throws Exception
	{		
		nflPlayoffTeamDAO.addUpdateNflPlayoffTeam(nflPlayoffTeam);
	}

	public void setCurrentWeekSecondHalfList(List<InnerWeek> currentWeekSecondHalfList) 
	{
		this.currentWeekSecondHalfList = currentWeekSecondHalfList;
	}

	public String getGameAcidSetting() {
		return gameAcidSetting;
	}

	public void setGameAcidSetting(String gameAcidSetting) {
		this.gameAcidSetting = gameAcidSetting;
	}

	public void setRenderGameViewAddUpdateDelete() 
	{
		if (gameAcidSetting == null)
		{
			this.setRenderGameUpdateFields(false);
			this.setRenderGameId(false);
		}
		else
		{
			if (gameAcidSetting.equalsIgnoreCase("View"))
			{
				this.setRenderGameUpdateFields(false);
				this.setRenderGameId(false);
			}
			else if (gameAcidSetting.equalsIgnoreCase("Add"))
			{
				this.setRenderGameUpdateFields(true);
				this.setRenderGameId(false);
			}
			else if (gameAcidSetting.equalsIgnoreCase("Update"))
			{
				this.setRenderGameUpdateFields(true);
				this.setRenderGameId(true);
			}
			else if (gameAcidSetting.equalsIgnoreCase("Delete"))
			{
				this.setRenderGameUpdateFields(false);
				this.setRenderGameId(true);
			}
			else //not sure what to do then...
			{
				this.setRenderGameUpdateFields(false);
				this.setRenderGameId(false);
			}
		}
		
	}

	public List<Integer> getWeekNumbersList() 
	{
		return weekNumbersList;
	}

	public void setWeekNumbersList(List<Integer> weekNumbersList) {
		this.weekNumbersList = weekNumbersList;
	}

	public DynamoNflGame getGameByGameID(int gameId)
	{
		return nflGameDAO.getGameByGameID(gameId);
	}
	
	public NflTeam getTeamByTeamID(int teamId)
	{
		return nflTeamDAO.getTeamByTeamID(teamId);
	}
	
	public String getGameTypeDescriptionByGameTypeId(int gameTypeId)
	{
		return nflGameDAO.getGameTypeDescriptionByGameTypeId(gameTypeId);
	}
	
	public Integer getWeekIdByWeekNumber(int weekNumber)
	{
		return nflGameDAO.getWeekIdByWeekNumber(weekNumber);
	}

	public Integer getAddedWeekId(String gameTypeDescription) 
	{
		//need to know the max week id for all regular season games; this is really only for playoff games
		Integer maxRegSeasonWeekid = nflGameDAO.getMaxRegularSeasonWeekID();
		
		Integer weekToAdd = 0;
		
		if (gameTypeDescription.equalsIgnoreCase("Regular Season"))
		{
			weekToAdd = maxRegSeasonWeekid;
		}
		else if (gameTypeDescription.equalsIgnoreCase(Utils.WILD_CARD))
		{
			weekToAdd = maxRegSeasonWeekid + 1;
		}
		else if (gameTypeDescription.equalsIgnoreCase(Utils.DIVISIONALS))
		{
			weekToAdd = maxRegSeasonWeekid + 2;
		}
		else if (gameTypeDescription.equalsIgnoreCase(Utils.CONFCHAMPIONSHIPS))
		{
			weekToAdd = maxRegSeasonWeekid + 3;
		}
		else if (gameTypeDescription.equalsIgnoreCase(Utils.SUPERBOWL))
		{
			weekToAdd = maxRegSeasonWeekid + 4;
		}
		
		return weekToAdd;
    }

	public boolean isRenderGameUpdateFields() {
		return renderGameUpdateFields;
	}

	public void setRenderGameUpdateFields(boolean renderGameUpdateFields) {
		this.renderGameUpdateFields = renderGameUpdateFields;
	}

	public boolean isRenderGameId() {
		return renderGameId;
	}

	public void setRenderGameId(boolean renderGameId) {
		this.renderGameId = renderGameId;
	}

	public Integer getSelectedWeekNumber() {
		return selectedWeekNumber;
	}

	public void setSelectedWeekNumber(Integer selectedWeekNumber) {
		this.selectedWeekNumber = selectedWeekNumber;
	}

	public String getSelectedWeekDescription() {
		return selectedWeekDescription;
	}

	public void setSelectedWeekDescription(String selectedWeekDescription) {
		this.selectedWeekDescription = selectedWeekDescription;
	}

	public Integer getSelectedTeamID() {
		return selectedTeamID;
	}

	public void setSelectedTeamID(Integer selectedTeamID) {
		this.selectedTeamID = selectedTeamID;
	}

	public List<NflTeam> getAfcTeamsList() 
	{
		return nflTeamDAO.getAfcTeamsList();
	}

	public List<NflTeam> getNfcTeamsList() 
	{
		return nflTeamDAO.getNfcTeamsList();
	}

	public List<InnerWeek> getCurrentWeekSecondHalfList() {
		return currentWeekSecondHalfList;
	}

	
}
