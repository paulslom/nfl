package com.pas.nfl.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.beans.NflPlayoffTeam;
import com.pas.dynamodb.DynamoClients;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;

public class NflPlayoffTeamDAO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(NflPlayoffTeamDAO.class);
	
	private Map<Integer,List<NflPlayoffTeam>> nflPlayoffTeamsMapBySeason = new HashMap<>(); 
	private List<NflPlayoffTeam> fullNflPlayoffTeamList = new ArrayList<>();
	private List<NflPlayoffTeam> playoffTeamsList = new ArrayList<>();
	
	private static DynamoClients dynamoClients;
	private static DynamoDbTable<NflPlayoffTeam> nflTeamsTable;
	private static final String AWS_TABLE_NAME = "nflplayoffteams";
		
	public NflPlayoffTeamDAO(DynamoClients dynamoClients2) 
	{
	   try 
	   {
	       dynamoClients = dynamoClients2;
	       nflTeamsTable = dynamoClients.getDynamoDbEnhancedClient().table(AWS_TABLE_NAME, TableSchema.fromBean(NflPlayoffTeam.class));
	   } 
	   catch (final Exception ex) 
	   {
	      logger.error("Got exception while initializing NflPlayoffTeamsDAO. Ex = " + ex.getMessage(), ex);
	   }	   
	}
	
	public Integer addNflPlayoffTeam(NflPlayoffTeam nflplayoffteam) throws Exception
	{
		NflPlayoffTeam nflTeam2 = dynamoUpsert(nflplayoffteam);		
		 
		nflplayoffteam.setiTeamID(nflTeam2.getiTeamID());
		
		logger.info("LoggedDBOperation: function-add; table:nflplayoffteam; rows:1");
		
		refreshListsAndMaps("add", nflplayoffteam);	
				
		logger.info("addNflPlayoffTeam complete");		
		
		return nflTeam2.getiTeamID(); //this is the key that was just added
	}
	
	private NflPlayoffTeam dynamoUpsert(NflPlayoffTeam nflplayoffteam) throws Exception 
	{
		NflPlayoffTeam dynamoNflPlayoffTeam = new NflPlayoffTeam();
        
		if (nflplayoffteam.getiTeamID() == null)
		{
			Integer currentMaxTeamID = 0;
			for (int i = 0; i < this.getFullNflPlayoffTeamList().size(); i++) 
			{
				NflPlayoffTeam nflTeam = this.getFullNflPlayoffTeamList().get(i);
				currentMaxTeamID = nflTeam.getiTeamID();
			}
			dynamoNflPlayoffTeam.setiTeamID(currentMaxTeamID + 1);
		}
		else
		{
			dynamoNflPlayoffTeam.setiTeamID(nflplayoffteam.getiTeamID());
		}
				
		PutItemEnhancedRequest<NflPlayoffTeam> putItemEnhancedRequest = PutItemEnhancedRequest.builder(NflPlayoffTeam.class).item(dynamoNflPlayoffTeam).build();
		nflTeamsTable.putItem(putItemEnhancedRequest);
			
		return dynamoNflPlayoffTeam;
	}

	public void updateNflPlayoffTeam(NflPlayoffTeam nflplayoffteam)  throws Exception
	{
		dynamoUpsert(nflplayoffteam);		
			
		logger.info("LoggedDBOperation: function-update; table:nflplayoffteam; rows:1");
		
		refreshListsAndMaps("update", nflplayoffteam);	
		
		logger.debug("update nflplayoffteam table complete");		
	}
	
	public void readNflPlayoffTeamsFromDB() 
    {
		Iterator<NflPlayoffTeam> results = nflTeamsTable.scan().items().iterator();
		
		while (results.hasNext()) 
        {
			NflPlayoffTeam nflPlayoffTeam = results.next();						
            this.getFullNflPlayoffTeamList().add(nflPlayoffTeam);            
        }
		
		logger.info("LoggedDBOperation: function-inquiry; table:nflplayoffteam; rows:" + this.getFullNflPlayoffTeamList().size());
		
		Collections.sort(this.getFullNflPlayoffTeamList(), new Comparator<NflPlayoffTeam>() 
		{
		   public int compare(NflPlayoffTeam o1, NflPlayoffTeam o2) 
		   {
		      return o1.getIseasonId().compareTo(o2.getIseasonId());
		   }
		});
		
		int maxSeasonID = 0;
		
		for (int i = 0; i < this.getFullNflPlayoffTeamList().size(); i++) 
		{			
			NflPlayoffTeam nflplayoffteam = this.getFullNflPlayoffTeamList().get(i);
			
			if (this.getNflPlayoffTeamsMapBySeason().containsKey(nflplayoffteam.getIseasonId()))
			{
				List<NflPlayoffTeam> tempPlayoffTeamList = this.getNflPlayoffTeamsMapBySeason().get(nflplayoffteam.getIseasonId());
				tempPlayoffTeamList.add(nflplayoffteam);
				this.getNflPlayoffTeamsMapBySeason().replace(nflplayoffteam.getIseasonId(), tempPlayoffTeamList);
			}
			else
			{
				List<NflPlayoffTeam> tempPlayoffTeamList = new ArrayList<>();
				tempPlayoffTeamList.add(nflplayoffteam);
				this.getNflPlayoffTeamsMapBySeason().put(nflplayoffteam.getIseasonId(), tempPlayoffTeamList);
				if (nflplayoffteam.getIseasonId() > maxSeasonID)
				{
					maxSeasonID = nflplayoffteam.getIseasonId();
				}
			}
		}
		
		//establish this season's playoffteams.  Default to max season id
		this.setPlayoffTeamsList(this.getNflPlayoffTeamsMapBySeason().get(maxSeasonID));
	}
	
	private void refreshListsAndMaps(String function, NflPlayoffTeam nflplayoffteam)
	{
		Integer seasonId = nflplayoffteam.getIseasonId();
		
		if (function.equalsIgnoreCase("delete"))
		{
			//todo
		}
		else if (function.equalsIgnoreCase("add"))
		{			
			ArrayList<NflPlayoffTeam> newList = new ArrayList<>(this.getNflPlayoffTeamsMapBySeason().get(seasonId));
			newList.add(nflplayoffteam);
			this.getNflPlayoffTeamsMapBySeason().replace(seasonId, newList);		
		}
		else if (function.equalsIgnoreCase("update"))
		{
			//todo
		}
		
		ArrayList<NflPlayoffTeam> newList = new ArrayList<>(this.getNflPlayoffTeamsMapBySeason().get(seasonId));
		this.getPlayoffTeamsList().clear();
		this.setPlayoffTeamsList(newList);				
	}
	
	public List<NflPlayoffTeam> getFullNflPlayoffTeamList() 
	{
		return fullNflPlayoffTeamList;
	}

	public void setFullNflPlayoffTeamList(List<NflPlayoffTeam> fullNflPlayoffTeamList) 
	{
		this.fullNflPlayoffTeamList = fullNflPlayoffTeamList;
	}

	public List<NflPlayoffTeam> getPlayoffTeamsList() {
		return playoffTeamsList;
	}

	public void setPlayoffTeamsList(List<NflPlayoffTeam> playoffTeamsList) {
		this.playoffTeamsList = playoffTeamsList;
	}

	public Map<Integer, List<NflPlayoffTeam>> getNflPlayoffTeamsMapBySeason() {
		return nflPlayoffTeamsMapBySeason;
	}

	public void setNflPlayoffTeamsMapBySeason(Map<Integer, List<NflPlayoffTeam>> nflPlayoffTeamsMapBySeason) {
		this.nflPlayoffTeamsMapBySeason = nflPlayoffTeamsMapBySeason;
	}


}
