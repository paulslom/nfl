package com.pas.nfl.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import com.pas.dynamodb.DynamoClients;
import com.pas.dynamodb.DynamoNflGame;
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
	
	private List<DynamoNflGame> fullNflGameList = new ArrayList<>();
	private List<DynamoNflGame> seasonGamesList = new ArrayList<>();
	private List<DynamoNflGame> playoffGamesList = new ArrayList<>();
	private List<SelectItem> gameTypesList = new ArrayList<>();
	
	private static DynamoClients dynamoClients;
	private static DynamoDbTable<DynamoNflGame> nflGamesTable;
	private static final String AWS_TABLE_NAME = "nflgames";
	
	private int maxSeasonID = 0;
	private int maxRegularSeasonWeekID = 0;
	private int maxRegularSeasonWeekNumber = 0;
	
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
	
	public void readNflGamesFromDB() 
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
				if (nflgame.getiSeasonId() > this.getMaxSeasonID())
				{
					this.setMaxSeasonID(nflgame.getiSeasonId());
				}
			}
		}
		
		//establish this season's games.  Default to max season id
		this.setSeasonGamesList(this.getGamesMapBySeason().get(this.getMaxSeasonID()));
		
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

	public void setMaxRegularSeasonWeek()
	{
		int maxweekid = 0;
		int maxweeknumber = 0;
		
		for (int i = 0; i < this.getSeasonGamesList().size(); i++) 
		{
			DynamoNflGame nflgame = this.getSeasonGamesList().get(i);
			if (nflgame.getSgameTypeDesc().equalsIgnoreCase("Regular Season") && nflgame.getIweekId() > maxweekid)
			{
				maxweekid = nflgame.getIweekId();
				maxweeknumber = nflgame.getIweekNumber();
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
		
		ArrayList<DynamoNflGame> newList = new ArrayList<>(this.getGamesMapBySeason().get(seasonId));
		this.getSeasonGamesList().clear();
		this.setSeasonGamesList(newList);		
		Collections.sort(this.getSeasonGamesList(), new GameComparator());			
	}
	
	public void importNextSeasonSchedule(String year) throws Exception
	{
	    InputStream is = getClass().getClassLoader().getResourceAsStream("data/NFLScheduleData" + year + ".csv"); 

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    String line;
	    
	    while ((line = reader.readLine()) != null) 
	    {
	       logger.info("line - " + line);
	    }
	    
	    reader.close();		
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

	public int getMaxSeasonID() {
		return maxSeasonID;
	}

	public void setMaxSeasonID(int maxSeasonID) {
		this.maxSeasonID = maxSeasonID;
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

	

}
