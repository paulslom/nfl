package com.pas.nfl.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.dynamodb.DateToStringConverter;
import com.pas.dynamodb.DynamoClients;
import com.pas.dynamodb.DynamoNflGame;
import com.pas.pojo.Schedule;
import com.pas.util.GameComparator;

import jakarta.faces.model.SelectItem;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;

public class NflGameDAO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(NflGameDAO.class);
	
	private Map<Integer,DynamoNflGame> fullNflGamesMap = new HashMap<>(); 
	private Map<Integer,List<DynamoNflGame>> gamesMapBySeason = new HashMap<>(); 
	private Map<Integer,String> gameTypesMap = new HashMap<>();
	private Map<Integer,Integer> weeksMapByWeekNumber = new HashMap<>();
	private Map<String,Map<Integer,String>> teamRegularSeasonGamesMap = new HashMap<>(); 
	
	private List<DynamoNflGame> fullNflGameList = new ArrayList<>();
	private List<DynamoNflGame> seasonGamesList = new ArrayList<>();	
	private List<DynamoNflGame> playoffGamesList = new ArrayList<>();
	private List<SelectItem> gameTypesList = new ArrayList<>();
	
	private static DynamoClients dynamoClients;
	private static DynamoDbTable<DynamoNflGame> nflGamesTable;
	private static final String AWS_TABLE_NAME = "nflgames";
	
	private int maxRegularSeasonWeekID = 0;
	private int maxRegularSeasonWeekNumber = 0;
	
	private Schedule scheduleTitleRow = new Schedule();
	
	//private static String HTML_CRLF = "<br>";
	//private static String PDF_CRLF = "\r\n\r\n";
		
	public NflGameDAO(DynamoClients dynamoClients2) 
	{		
	   try 
	   {
	       dynamoClients = dynamoClients2;
	       nflGamesTable = dynamoClients.getDynamoDbEnhancedClient().table(AWS_TABLE_NAME, TableSchema.fromBean(DynamoNflGame.class));
	   } 
	   catch (final Exception ex) 
	   {
	      logger.error("Got exception while initializing NflGamesDAO. Ex = " + ex.getMessage(), ex);
	   }	   
	}
	
	public Integer addNflGame(DynamoNflGame dynamoNflGame) throws Exception
	{
		DynamoNflGame nflGame2 = dynamoUpsert(dynamoNflGame);		
		 
		dynamoNflGame.setIgameId(nflGame2.getIgameId());
		
		logger.info("LoggedDBOperation: function-add; table:nflgame; rows:1");
	
		refreshListsAndMaps("add", dynamoNflGame);	
					
		logger.info("addNflGame complete. added gameID: " + nflGame2.getIgameId());		
		
		return nflGame2.getIgameId(); //this is the key that was just added
	}
	
	public DynamoNflGame getGameByGameID(Integer gameId)
	{
		return this.getFullNflGamesMap().get(gameId);
	}
	
	private DynamoNflGame dynamoUpsert(DynamoNflGame dynamoNflGame) throws Exception 
	{
		if (dynamoNflGame.getIgameId() == null)
		{
			Integer nextGameID = determineNextGameId();
			dynamoNflGame.setIgameId(nextGameID);
		}
						
		PutItemEnhancedRequest<DynamoNflGame> putItemEnhancedRequest = PutItemEnhancedRequest.builder(DynamoNflGame.class).item(dynamoNflGame).build();
		nflGamesTable.putItem(putItemEnhancedRequest);
			
		return dynamoNflGame;
	}

	private Integer determineNextGameId() 
	{
		Integer nextGameID = 0;
		
		List<Integer> gameIds = this.getFullNflGamesMap().keySet().stream().collect(Collectors.toList());
		int max = Collections.max(gameIds);
        nextGameID = max + 1;
        
		return nextGameID;
	}

	public void updateNflGame(DynamoNflGame dynamoNflgame)  throws Exception
	{
		dynamoUpsert(dynamoNflgame);		
			
		logger.info("LoggedDBOperation: function-update; table:nflgame; rows:1");
		
		refreshListsAndMaps("update", dynamoNflgame);	
		
		logger.debug("update nflgame table complete");		
	}
	
	public void deleteNflGame(DynamoNflGame nflgame) throws Exception 
	{
		Key key = Key.builder().partitionValue(nflgame.getIgameId()).build();
		DeleteItemEnhancedRequest deleteItemEnhancedRequest = DeleteItemEnhancedRequest.builder().key(key).build();
		nflGamesTable.deleteItem(deleteItemEnhancedRequest);
		
		logger.info("LoggedDBOperation: function-delete; table:nflgame; rows:1");
		
		refreshListsAndMaps("delete", nflgame);		
		
		logger.info(" deleteGame complete");	
	}
	
	public void readNflGamesFromDB(int seasonID) 
    {
		Iterator<DynamoNflGame> results = nflGamesTable.scan().items().iterator();
		
		while (results.hasNext()) 
        {
			DynamoNflGame dynamoNflGame = results.next();			
            this.getFullNflGameList().add(dynamoNflGame);            
        }
		
		logger.info("LoggedDBOperation: function-inquiry; table:nflgame; rows:" + this.getFullNflGameList().size());
		
		this.setFullNflGamesMap(this.getFullNflGameList().stream().collect(Collectors.toMap(DynamoNflGame::getIgameId, ply -> ply)));
		
		Collections.sort(this.getFullNflGameList(), new GameComparator());
		
		for (int i = 0; i < this.getFullNflGameList().size(); i++) 
		{			
			DynamoNflGame nflgame = this.getFullNflGameList().get(i);
			
			logger.debug("looping game number: " + i + " for seasonid: " + nflgame.getiSeasonId());
			
			if (!this.getGameTypesMap().containsKey(nflgame.getIgameTypeId()))
			{
				this.getGameTypesMap().put(nflgame.getIgameTypeId(), nflgame.getSgameTypeDesc());
			}
			
			if (!this.getWeeksMapByWeekNumber().containsKey(nflgame.getIweekNumber()))
			{
				this.getWeeksMapByWeekNumber().put(nflgame.getIweekNumber(), nflgame.getIweekId());
			}
			
			if (this.getGamesMapBySeason().containsKey(nflgame.getiSeasonId()))
			{
				List<DynamoNflGame> tempGameList = this.getGamesMapBySeason().get(nflgame.getiSeasonId());
				tempGameList.add(nflgame);
				this.getGamesMapBySeason().replace(nflgame.getiSeasonId(), tempGameList);
			}
			else
			{
				List<DynamoNflGame> tempGameList = new ArrayList<>();
				tempGameList.add(nflgame);
				this.getGamesMapBySeason().put(nflgame.getiSeasonId(), tempGameList);				
			}
		}
		
		//establish this season's games.  Use default season id, supplied from NflMain
		this.setSeasonGamesList(this.getGamesMapBySeason().get(seasonID));
		
		//establish this season's games by team
		this.setTeamGamesMap(seasonID);
		
		//establish schedule title row
		this.establishScheduleTitleRow(seasonID);
		
		//establish this season's game types.
		for (Integer key : this.getGameTypesMap().keySet()) 
		{
			SelectItem si = new SelectItem();
			si.setLabel(this.getGameTypesMap().get(key));
			si.setValue(key);
			this.getGameTypesList().add(si);
        }
		
		setMaxRegularSeasonWeek();
	}


	public void establishScheduleTitleRow(Integer getiSeasonID) 
	{	
		Map<Integer,String> tempWeeksMap = new HashMap<>();
		
		for (int i = 0; i < this.getSeasonGamesList().size(); i++) 
		{
			DynamoNflGame game = this.getSeasonGamesList().get(i);
			
			if (!game.getSgameTypeDesc().equalsIgnoreCase("Regular Season"))
			{
				continue;
			}
			else if (tempWeeksMap.containsKey(game.getIweekNumber()))
			{
				continue;
			}
			else if (game.getGameDayOfWeek().equalsIgnoreCase("Sun"))
			{
				String dtstr = DateToStringConverter.convertToScheduleFormat(game.getDgameDateTime());
				String opponentName = "";
				if (game.getIweekNumber() > 9)
				{
					//opponentName = "Wk " + game.getIweekNumber() + HTML_CRLF + dtstr;
					opponentName = "Wk " + game.getIweekNumber() + " " + dtstr;
				}
				else
				{
					opponentName = "Wk " + game.getIweekNumber() + " " + dtstr;
				}
				tempWeeksMap.put(game.getIweekNumber(),opponentName);				
			}
		}
		
		Schedule tempSchedule = new Schedule();
		tempSchedule.setTeam("");
		
		List<String> opponentsList = tempWeeksMap.values().stream().collect(Collectors.toList());
		
		tempSchedule.setOpponentsList(opponentsList);
		this.setScheduleTitleRow(tempSchedule);
		
	}
	
	public void setTeamGamesMap(Integer getiSeasonID) 
	{
		this.getTeamRegularSeasonGamesMap().clear();
		
		for (int i = 0; i < this.getSeasonGamesList().size(); i++) 
		{
			DynamoNflGame game = this.getSeasonGamesList().get(i);
			
			if (!game.getSgameTypeDesc().equalsIgnoreCase("Regular Season"))
			{
				continue;
			}
			
			if (this.getTeamRegularSeasonGamesMap().containsKey(game.getCawayteamCityAbbr()))
			{
				Map<Integer,String> tempMap = new HashMap<>(this.getTeamRegularSeasonGamesMap().get(game.getCawayteamCityAbbr()));
				tempMap.put(game.getIweekNumber(), "@" + game.getChometeamCityAbbr().toLowerCase() + game.getGameTimeOnly().substring(1, 2));
				this.getTeamRegularSeasonGamesMap().replace(game.getCawayteamCityAbbr(),tempMap);
			}
			else
			{
				Map<Integer,String> tempMap = new HashMap<>();
				tempMap.put(game.getIweekNumber(), "@" + game.getChometeamCityAbbr().toLowerCase() + game.getGameTimeOnly().substring(1, 2));
				this.getTeamRegularSeasonGamesMap().put(game.getCawayteamCityAbbr(),tempMap);
			}
			
			if (this.getTeamRegularSeasonGamesMap().containsKey(game.getChometeamCityAbbr()))
			{
				Map<Integer,String> tempMap = new HashMap<>(this.getTeamRegularSeasonGamesMap().get(game.getChometeamCityAbbr()));
				tempMap.put(game.getIweekNumber(), game.getCawayteamCityAbbr().toUpperCase() + game.getGameTimeOnly().substring(1, 2));
				this.getTeamRegularSeasonGamesMap().replace(game.getChometeamCityAbbr(),tempMap);
			}
			else
			{
				Map<Integer,String> tempMap = new HashMap<>();
				tempMap.put(game.getIweekNumber(), game.getCawayteamCityAbbr().toUpperCase() + game.getGameTimeOnly().substring(1, 2));
				this.getTeamRegularSeasonGamesMap().put(game.getChometeamCityAbbr(),tempMap);
			}
		}
		
		establishByeWeeks();
		
		logger.info("done setting team regular season games map");
	}
		
	private void establishByeWeeks() 
	{
		Map<String, Integer> byeWeeksMap = new HashMap<>();
		
		for (var team : this.getTeamRegularSeasonGamesMap().entrySet()) 
		{
		    Map<Integer, String> opponentsMap = team.getValue();
		    
		    int lastWeekNum = 1;
		    for (var opponentsEntry : opponentsMap.entrySet()) 
			{
			    if (opponentsEntry.getKey() - lastWeekNum > 1)
			    {
			    	byeWeeksMap.put(team.getKey(), Integer.valueOf(lastWeekNum + 1));
			    }
			    lastWeekNum = opponentsEntry.getKey();
			}
		}
		
		for (var byeWeeksEntry : byeWeeksMap.entrySet()) 
		{
		    logger.debug("team: " + byeWeeksEntry.getKey() + " bye week: " + byeWeeksEntry.getValue());	
		    
		    Map<Integer,String> tempMap = new HashMap<>(this.getTeamRegularSeasonGamesMap().get(byeWeeksEntry.getKey()));
			tempMap.put(byeWeeksEntry.getValue(), "*BYE*");
			this.getTeamRegularSeasonGamesMap().replace(byeWeeksEntry.getKey(),tempMap);
		}
		
	}

	public void setMaxRegularSeasonWeek()
	{
		int maxweekid = 0;
		int maxweeknumber = 0;
		
		if (this.getSeasonGamesList() != null)
		{
			for (int i = 0; i < this.getSeasonGamesList().size(); i++) 
			{
				DynamoNflGame nflgame = this.getSeasonGamesList().get(i);
				if (nflgame.getSgameTypeDesc().equalsIgnoreCase("Regular Season") && nflgame.getIweekId() > maxweekid)
				{
					maxweekid = nflgame.getIweekId();
					maxweeknumber = nflgame.getIweekNumber();
				}
			}			
		}
		
		this.setMaxRegularSeasonWeekID(maxweekid);
		this.setMaxRegularSeasonWeekNumber(maxweeknumber);
	}
	
	public List<DynamoNflGame> getGameScoresList(String byTeamOrWeek, Integer weekNumberOrTeamID) 
	{
		List<DynamoNflGame> returnList = new ArrayList<>();
		
		int tabCount = 1;
		
		for (int i = 0; i < this.getSeasonGamesList().size(); i++) 
		{
			DynamoNflGame game = this.getSeasonGamesList().get(i);
			
			if (byTeamOrWeek.equalsIgnoreCase("byWeek"))
			{
				if (game.getIweekNumber() == weekNumberOrTeamID)
				{
					game.setTabIndexAwayTeam(tabCount++);
					game.setTabIndexHomeTeam(tabCount++);
					returnList.add(game);
				}
			}
			else if (byTeamOrWeek.equalsIgnoreCase("byTeam"))
			{
				if (game.getIhomeTeamID() == weekNumberOrTeamID
				||  game.getIawayTeamID() == weekNumberOrTeamID)
				{
					game.setTabIndexAwayTeam(tabCount++);
					game.setTabIndexHomeTeam(tabCount++);
					returnList.add(game);
				}
			}
			
		} 
		
		return returnList;
	}
		
	private void refreshListsAndMaps(String function, DynamoNflGame dynamoNflgame)
	{
		Integer seasonId = dynamoNflgame.getiSeasonId();
		
		if (function.equalsIgnoreCase("delete"))
		{
			this.getFullNflGamesMap().remove(dynamoNflgame.getIgameId());	
			
			List<DynamoNflGame> found = new ArrayList<DynamoNflGame>();
			List<DynamoNflGame> seasonGamesList = this.getGamesMapBySeason().get(seasonId);
			List<DynamoNflGame> newList = this.getGamesMapBySeason().get(seasonId);
			for (int i = 0; i < seasonGamesList.size(); i++) 
			{
				DynamoNflGame game = seasonGamesList.get(i);
				if (game.getIgameId() == dynamoNflgame.getIgameId())
				{
					found.add(game);
					break;
				}
			}
			newList.removeAll(found);
			this.getGamesMapBySeason().replace(seasonId, newList);	
		}
		else if (function.equalsIgnoreCase("add"))
		{
			this.getFullNflGamesMap().put(dynamoNflgame.getIgameId(), dynamoNflgame);
			
			//First game of an import new season's games won't have this yet, add it
			if (!this.getGamesMapBySeason().containsKey(seasonId))
			{
				this.getGamesMapBySeason().put(seasonId, new ArrayList<DynamoNflGame>());
			}
			ArrayList<DynamoNflGame> newList = new ArrayList<>(this.getGamesMapBySeason().get(seasonId));
			newList.add(dynamoNflgame);
			this.getGamesMapBySeason().replace(seasonId, newList);			
		}
		else if (function.equalsIgnoreCase("update"))
		{
			this.getFullNflGamesMap().remove(dynamoNflgame.getIgameId());		
			this.getFullNflGamesMap().put(dynamoNflgame.getIgameId(), dynamoNflgame);	
			List<DynamoNflGame> newList = new ArrayList<>(this.getGamesMapBySeason().get(seasonId));
			
			List<DynamoNflGame> found = new ArrayList<DynamoNflGame>();
			List<DynamoNflGame> seasonGamesList = this.getGamesMapBySeason().get(seasonId);
			
			for (int i = 0; i < seasonGamesList.size(); i++) 
			{
				DynamoNflGame game = seasonGamesList.get(i);
				if (game.getIgameId() == dynamoNflgame.getIgameId())
				{
					found.add(game);
					break;
				}
			}
			newList.removeAll(found);
			newList.add(dynamoNflgame);
			this.getGamesMapBySeason().replace(seasonId, newList);	
		}
		
		this.getFullNflGameList().clear();
		Collection<DynamoNflGame> values = this.getFullNflGamesMap().values();
		this.setFullNflGameList(new ArrayList<>(values));		
		Collections.sort(this.getFullNflGameList(), new GameComparator());	
		
		this.setSeasonGamesList(this.getGamesMapBySeason().get(seasonId));        
		Collections.sort(this.getSeasonGamesList(), new GameComparator());			
	}
	
	public List<DynamoNflGame> getFullNflGameList() 
	{
		return fullNflGameList;
	}

	public void setFullNflGameList(List<DynamoNflGame> fullNflGameList) 
	{
		this.fullNflGameList = fullNflGameList;
	}

	public Map<Integer, DynamoNflGame> getFullNflGamesMap() {
		return fullNflGamesMap;
	}

	public void setFullNflGamesMap(Map<Integer, DynamoNflGame> fullNflGamesMap) {
		this.fullNflGamesMap = fullNflGamesMap;
	}

	public Map<Integer, List<DynamoNflGame>> getGamesMapBySeason() {
		return gamesMapBySeason;
	}

	public void setGamesMapBySeason(Map<Integer, List<DynamoNflGame>> gamesMapBySeason) {
		this.gamesMapBySeason = gamesMapBySeason;
	}

	public List<DynamoNflGame> getSeasonGamesList() {
		return seasonGamesList;
	}

	public void setSeasonGamesList(List<DynamoNflGame> seasonGamesList) {
		this.seasonGamesList = seasonGamesList;
	}

	public List<SelectItem> getGameTypesList() {
		return gameTypesList;
	}

	public void setGameTypesList(List<SelectItem> gameTypesList) {
		this.gameTypesList = gameTypesList;
	}

	public Map<Integer, String> getGameTypesMap() {
		return gameTypesMap;
	}

	public void setGameTypesMap(Map<Integer, String> gameTypesMap) {
		this.gameTypesMap = gameTypesMap;
	}

	public String getGameTypeDescriptionByGameTypeId(int gameTypeId) 
	{
		return this.getGameTypesMap().get(gameTypeId);
	}

	public Map<Integer, Integer> getWeeksMapByWeekNumber() {
		return weeksMapByWeekNumber;
	}

	public void setWeeksMapByWeekNumber(Map<Integer, Integer> weeksMapByWeekNumber) {
		this.weeksMapByWeekNumber = weeksMapByWeekNumber;
	}

	public Integer getWeekIdByWeekNumber(int weekNumber) 
	{
		return this.getWeeksMapByWeekNumber().get(weekNumber);
	}

	public int getMaxRegularSeasonWeekID() {
		return maxRegularSeasonWeekID;
	}

	public void setMaxRegularSeasonWeekID(int maxRegularSeasonWeekID) {
		this.maxRegularSeasonWeekID = maxRegularSeasonWeekID;
	}

	public int getMaxRegularSeasonWeekNumber() {
		return maxRegularSeasonWeekNumber;
	}

	public void setMaxRegularSeasonWeekNumber(int maxRegularSeasonWeekNumber) {
		this.maxRegularSeasonWeekNumber = maxRegularSeasonWeekNumber;
	}

	public List<DynamoNflGame> getPlayoffGamesList() 
	{
		playoffGamesList.clear();
		
		for (int i = 0; i < this.getSeasonGamesList().size(); i++) 
		{
			DynamoNflGame nflgame = this.getSeasonGamesList().get(i);
			if (nflgame.getSgameTypeDesc().equalsIgnoreCase("Regular Season"))
			{
				continue;
			}
			else
			{
				playoffGamesList.add(nflgame);
			}
		}
		return playoffGamesList;
	}

	public void setPlayoffGamesList(List<DynamoNflGame> playoffGamesList) {
		this.playoffGamesList = playoffGamesList;
	}

	public Schedule getScheduleTitleRow() 
	{
		return scheduleTitleRow;
	}
	
	public void setScheduleTitleRow(Schedule scheduleTitleRow) {
		this.scheduleTitleRow = scheduleTitleRow;
	}

	public Map<String, Map<Integer, String>> getTeamRegularSeasonGamesMap() {
		return teamRegularSeasonGamesMap;
	}

	public void setTeamRegularSeasonGamesMap(Map<String, Map<Integer, String>> teamRegularSeasonGamesMap) {
		this.teamRegularSeasonGamesMap = teamRegularSeasonGamesMap;
	}

}
