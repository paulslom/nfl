package com.pas.nfl.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.beans.NflSeason;
import com.pas.dynamodb.DynamoClients;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;

public class NflSeasonDAO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(NflSeasonDAO.class);
	
	private Map<String,NflSeason> fullNflSeasonsMapByYear = new HashMap<>(); 
	private Map<Integer,NflSeason> fullNflSeasonsMapBySeasonId = new HashMap<>(); 
	private List<NflSeason> fullNflSeasonList = new ArrayList<>();
	private List<NflSeason> seasons2000sList = new ArrayList<>();
	private List<NflSeason> seasons2010sList = new ArrayList<>();
	private List<NflSeason> seasons2020sList = new ArrayList<>();
	private List<NflSeason> seasons2030sList = new ArrayList<>();
	private List<NflSeason> seasons2040sList = new ArrayList<>();
	
	private static DynamoClients dynamoClients;
	private static DynamoDbTable<NflSeason> nflSeasonsTable;
	private static final String AWS_TABLE_NAME = "nflseasons";
	
	private String maxSeasonYear = "";
	
	public NflSeasonDAO(DynamoClients dynamoClients2) 
	{
	   try 
	   {
	       dynamoClients = dynamoClients2;
	       nflSeasonsTable = dynamoClients.getDynamoDbEnhancedClient().table(AWS_TABLE_NAME, TableSchema.fromBean(NflSeason.class));
	   } 
	   catch (final Exception ex) 
	   {
	      logger.error("Got exception while initializing NflSeasonsDAO. Ex = " + ex.getMessage(), ex);
	   }	   
	}
	
	public Integer addNflSeason(NflSeason nflseason) throws Exception
	{
		NflSeason nflSeason2 = dynamoUpsert(nflseason);		
		 
		nflseason.setiSeasonID(nflSeason2.getiSeasonID());
		
		logger.info("LoggedDBOperation: function-add; table:nflseason; rows:1");
		
		refreshListsAndMaps("add", nflseason);	
				
		logger.info("addNflSeason complete");		
		
		return nflSeason2.getiSeasonID(); //this is the key that was just added
	}
	
	private NflSeason dynamoUpsert(NflSeason nflseason) throws Exception 
	{
		PutItemEnhancedRequest<NflSeason> putItemEnhancedRequest = PutItemEnhancedRequest.builder(NflSeason.class).item(nflseason).build();
		nflSeasonsTable.putItem(putItemEnhancedRequest);
			
		return nflseason;
	}

	public void updateNflSeason(NflSeason nflseason)  throws Exception
	{
		dynamoUpsert(nflseason);		
			
		logger.info("LoggedDBOperation: function-update; table:nflseason; rows:1");
		
		refreshListsAndMaps("update", nflseason);	
		
		logger.debug("update nflseason table complete");		
	}
	
	public void readNflSeasonsFromDB() 
    {
		Iterator<NflSeason> results = nflSeasonsTable.scan().items().iterator();
		
		while (results.hasNext()) 
        {
			NflSeason nflSeason = results.next();						
            this.getFullNflSeasonList().add(nflSeason);            
        }
		
		logger.info("LoggedDBOperation: function-inquiry; table:nflseason; rows:" + this.getFullNflSeasonList().size());
		
		this.setFullNflSeasonsMapBySeasonId(this.getFullNflSeasonList().stream().collect(Collectors.toMap(NflSeason::getiSeasonID, ply -> ply)));
		this.setFullNflSeasonsMapByYear(this.getFullNflSeasonList().stream().collect(Collectors.toMap(NflSeason::getcYear, ply -> ply)));
		
		Collections.sort(this.getFullNflSeasonList(), new Comparator<NflSeason>() 
		{
		   public int compare(NflSeason o1, NflSeason o2) 
		   {
		      return o1.getiSeasonID().compareTo(o2.getiSeasonID());
		   }
		});
		
		int maxSeasonID = 0;
		
		for (int i = 0; i < this.getFullNflSeasonList().size(); i++) 
		{
			NflSeason nflseason = this.getFullNflSeasonList().get(i);
			Integer seasonYearInt = Integer.parseInt(nflseason.getcYear());
			
			if (seasonYearInt >= 2000 && seasonYearInt <= 2009)
			{
				this.getSeasons2000sList().add(nflseason);
			}
			else if (seasonYearInt >= 2010 && seasonYearInt <= 2019)
			{
				this.getSeasons2010sList().add(nflseason);
			}
			else if (seasonYearInt >= 2020 && seasonYearInt <= 2029)
			{
				this.getSeasons2020sList().add(nflseason);
			}
			else if (seasonYearInt >= 2030 && seasonYearInt <= 2039)
			{
				this.getSeasons2030sList().add(nflseason);
			}
			else if (seasonYearInt >= 2040 && seasonYearInt <= 2049)
			{
				this.getSeasons2040sList().add(nflseason);
			}
			
			if (nflseason.getiSeasonID() > maxSeasonID)
			{
				maxSeasonID = nflseason.getiSeasonID();
				this.setMaxSeasonYear(nflseason.getcYear());
			}
		} 
	}
	
	private void refreshListsAndMaps(String function, NflSeason nflseason)
	{
		if (function.equalsIgnoreCase("delete"))
		{
			this.getFullNflSeasonsMapBySeasonId().remove(nflseason.getiSeasonID());		
		}
		else if (function.equalsIgnoreCase("add"))
		{
			this.getFullNflSeasonsMapBySeasonId().put(nflseason.getiSeasonID(), nflseason);		
		}
		else if (function.equalsIgnoreCase("update"))
		{
			this.getFullNflSeasonsMapBySeasonId().remove(nflseason.getiSeasonID());		
			this.getFullNflSeasonsMapBySeasonId().put(nflseason.getiSeasonID(), nflseason);	
		}
		
		this.getFullNflSeasonList().clear();
		Collection<NflSeason> values = this.getFullNflSeasonsMapBySeasonId().values();
		this.setFullNflSeasonList(new ArrayList<>(values));
		
		Collections.sort(this.getFullNflSeasonList(), new Comparator<NflSeason>() 
		{
		   public int compare(NflSeason o1, NflSeason o2) 
		   {
		      return o1.getiSeasonID().compareTo(o2.getiSeasonID());
		   }
		});
				
	}
	
	public NflSeason getMaxSeason() 
	{
		NflSeason maxSeason = this.getFullNflSeasonList().get(this.getFullNflSeasonList().size() - 1);
		return maxSeason;
	}
	
	public void createNextSeason() throws Exception
	{
		NflSeason newSeason = getMaxSeason();
		
		Integer newSeasonID = newSeason.getiSeasonID() + 1;
		Integer newSeasonYear = Integer.parseInt(newSeason.getcYear());
		newSeasonYear++;
		String newSeasonYearString = newSeasonYear.toString();
		newSeason.setcYear(newSeasonYearString);
		newSeason.setIntYear(newSeasonYear);
		newSeason.setiSeasonID(newSeasonID);
		addNflSeason(newSeason);		
	}
	
	public List<NflSeason> getFullNflSeasonList() 
	{
		return fullNflSeasonList;
	}

	public void setFullNflSeasonList(List<NflSeason> fullNflSeasonList) 
	{
		this.fullNflSeasonList = fullNflSeasonList;
	}

	public Map<Integer, NflSeason> getFullNflSeasonsMapBySeasonId() {
		return fullNflSeasonsMapBySeasonId;
	}

	public void setFullNflSeasonsMapBySeasonId(Map<Integer, NflSeason> fullNflSeasonsMapBySeasonId) {
		this.fullNflSeasonsMapBySeasonId = fullNflSeasonsMapBySeasonId;
	}

	public List<NflSeason> getSeasons2000sList() {
		return seasons2000sList;
	}

	public void setSeasons2000sList(List<NflSeason> seasons2000sList) {
		this.seasons2000sList = seasons2000sList;
	}

	public List<NflSeason> getSeasons2010sList() {
		return seasons2010sList;
	}

	public void setSeasons2010sList(List<NflSeason> seasons2010sList) {
		this.seasons2010sList = seasons2010sList;
	}

	public List<NflSeason> getSeasons2020sList() {
		return seasons2020sList;
	}

	public void setSeasons2020sList(List<NflSeason> seasons2020sList) {
		this.seasons2020sList = seasons2020sList;
	}

	public List<NflSeason> getSeasons2030sList() {
		return seasons2030sList;
	}

	public void setSeasons2030sList(List<NflSeason> seasons2030sList) {
		this.seasons2030sList = seasons2030sList;
	}

	public List<NflSeason> getSeasons2040sList() {
		return seasons2040sList;
	}

	public void setSeasons2040sList(List<NflSeason> seasons2040sList) {
		this.seasons2040sList = seasons2040sList;
	}

	public Map<String, NflSeason> getFullNflSeasonsMapByYear() {
		return fullNflSeasonsMapByYear;
	}

	public void setFullNflSeasonsMapByYear(Map<String, NflSeason> fullNflSeasonsMapByYear) {
		this.fullNflSeasonsMapByYear = fullNflSeasonsMapByYear;
	}

	public String getMaxSeasonYear() {
		return maxSeasonYear;
	}

	public void setMaxSeasonYear(String maxSeasonYear) {
		this.maxSeasonYear = maxSeasonYear;
	}

	

	

}
