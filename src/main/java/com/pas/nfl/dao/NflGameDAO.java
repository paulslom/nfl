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

import com.pas.beans.NflGame;
import com.pas.dynamodb.DynamoClients;
import com.pas.util.GameComparator;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;

public class NflGameDAO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(NflGameDAO.class);
	
	private Map<Integer,NflGame> fullNflGamesMap = new HashMap<>(); 
	private Map<Integer,List<NflGame>> gamesMapBySeason = new HashMap<>(); 
	private List<NflGame> fullNflGameList = new ArrayList<>();
	private List<NflGame> seasonGamesList = new ArrayList<>();
	
	private static DynamoClients dynamoClients;
	private static DynamoDbTable<NflGame> nflGamesTable;
	private static final String AWS_TABLE_NAME = "nflgames";
	
	public NflGameDAO(DynamoClients dynamoClients2) 
	{		
	   try 
	   {
	       dynamoClients = dynamoClients2;
	       nflGamesTable = dynamoClients.getDynamoDbEnhancedClient().table(AWS_TABLE_NAME, TableSchema.fromBean(NflGame.class));
	   } 
	   catch (final Exception ex) 
	   {
	      logger.error("Got exception while initializing NflGamesDAO. Ex = " + ex.getMessage(), ex);
	   }	   
	}
	
	public Integer addNflGame(NflGame nflgame) throws Exception
	{
		NflGame nflGame2 = dynamoUpsert(nflgame);		
		 
		nflgame.setIgameId(nflGame2.getIgameId());
		
		logger.info("LoggedDBOperation: function-add; table:nflgame; rows:1");
		
		refreshListsAndMaps("add", nflgame);	
				
		logger.info("addNflGame complete");		
		
		return nflGame2.getIgameId(); //this is the key that was just added
	}
	
	private NflGame dynamoUpsert(NflGame nflgame) throws Exception 
	{
		NflGame dynamoNflGame = new NflGame();
        
		if (nflgame.getIgameId() == null)
		{
			Integer currentMaxGameID = 0;
			for (int i = 0; i < this.getFullNflGameList().size(); i++) 
			{
				NflGame nflGame = this.getFullNflGameList().get(i);
				currentMaxGameID = nflGame.getIgameId();
			}
			dynamoNflGame.setIgameId(currentMaxGameID + 1);
		}
		else
		{
			dynamoNflGame.setIgameId(nflgame.getIgameId());
		}
				
		PutItemEnhancedRequest<NflGame> putItemEnhancedRequest = PutItemEnhancedRequest.builder(NflGame.class).item(dynamoNflGame).build();
		nflGamesTable.putItem(putItemEnhancedRequest);
			
		return dynamoNflGame;
	}

	public void updateNflGame(NflGame nflgame)  throws Exception
	{
		dynamoUpsert(nflgame);		
			
		logger.info("LoggedDBOperation: function-update; table:nflgame; rows:1");
		
		refreshListsAndMaps("update", nflgame);	
		
		logger.debug("update nflgame table complete");		
	}
	
	public void readNflGamesFromDB() 
    {
		Iterator<NflGame> results = nflGamesTable.scan().items().iterator();
		
		while (results.hasNext()) 
        {
			NflGame nflSeason = results.next();						
            this.getFullNflGameList().add(nflSeason);            
        }
		
		logger.info("LoggedDBOperation: function-inquiry; table:nflgame; rows:" + this.getFullNflGameList().size());
		
		this.setFullNflGamesMap(this.getFullNflGameList().stream().collect(Collectors.toMap(NflGame::getIgameId, ply -> ply)));
		
		Collections.sort(this.getFullNflGameList(), new GameComparator());
		
		int maxSeasonID = 0;
		
		for (int i = 0; i < this.getFullNflGameList().size(); i++) 
		{			
			NflGame nflgame = this.getFullNflGameList().get(i);
			int currentSeasonID = nflgame.getiSeasonId();
			logger.debug("looping game number: " + i + " for seasonid: " + currentSeasonID);
			
			if (this.getGamesMapBySeason().containsKey(currentSeasonID))
			{
				List<NflGame> tempGameList = this.getGamesMapBySeason().get(currentSeasonID);
				tempGameList.add(nflgame);
				this.getGamesMapBySeason().replace(currentSeasonID, tempGameList);
			}
			else
			{
				List<NflGame> tempGameList = new ArrayList<>();
				tempGameList.add(nflgame);
				this.getGamesMapBySeason().put(currentSeasonID, tempGameList);
				if (currentSeasonID > maxSeasonID)
				{
					maxSeasonID = currentSeasonID;
				}
			}
		}
		
		//establish this season's games.  Default to max season id
		this.setSeasonGamesList(this.getGamesMapBySeason().get(maxSeasonID));
	}
	
	private void refreshListsAndMaps(String function, NflGame nflgame)
	{
		if (function.equalsIgnoreCase("delete"))
		{
			this.getFullNflGamesMap().remove(nflgame.getIgameId());		
		}
		else if (function.equalsIgnoreCase("add"))
		{
			this.getFullNflGamesMap().put(nflgame.getIgameId(), nflgame);		
		}
		else if (function.equalsIgnoreCase("update"))
		{
			this.getFullNflGamesMap().remove(nflgame.getIgameId());		
			this.getFullNflGamesMap().put(nflgame.getIgameId(), nflgame);	
		}
		
		this.getFullNflGameList().clear();
		Collection<NflGame> values = this.getFullNflGamesMap().values();
		this.setFullNflGameList(new ArrayList<>(values));
		
		Collections.sort(this.getFullNflGameList(), new GameComparator());				
	}
	
	public List<NflGame> getFullNflGameList() 
	{
		return fullNflGameList;
	}

	public void setFullNflGameList(List<NflGame> fullNflGameList) 
	{
		this.fullNflGameList = fullNflGameList;
	}

	public Map<Integer, NflGame> getFullNflGamesMap() {
		return fullNflGamesMap;
	}

	public void setFullNflGamesMap(Map<Integer, NflGame> fullNflGamesMap) {
		this.fullNflGamesMap = fullNflGamesMap;
	}

	public Map<Integer, List<NflGame>> getGamesMapBySeason() {
		return gamesMapBySeason;
	}

	public void setGamesMapBySeason(Map<Integer, List<NflGame>> gamesMapBySeason) {
		this.gamesMapBySeason = gamesMapBySeason;
	}

	public List<NflGame> getSeasonGamesList() {
		return seasonGamesList;
	}

	public void setSeasonGamesList(List<NflGame> seasonGamesList) {
		this.seasonGamesList = seasonGamesList;
	}


}
