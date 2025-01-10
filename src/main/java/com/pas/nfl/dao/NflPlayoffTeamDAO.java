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

import com.pas.beans.NflPlayoffTeam;
import com.pas.dynamodb.DynamoClients;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;

public class NflPlayoffTeamDAO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(NflPlayoffTeamDAO.class);
	
	private Map<String,NflPlayoffTeam> fullNflPlayoffTeamsMap = new HashMap<>(); 
	private List<NflPlayoffTeam> fullNflPlayoffTeamList = new ArrayList<>();
	
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
			NflPlayoffTeam nflSeason = results.next();						
            this.getFullNflPlayoffTeamList().add(nflSeason);            
        }
		
		logger.info("LoggedDBOperation: function-inquiry; table:nflplayoffteam; rows:" + this.getFullNflPlayoffTeamList().size());
		
		this.setFullNflPlayoffTeamsMap(this.getFullNflPlayoffTeamList().stream().collect(Collectors.toMap(NflPlayoffTeam::getPlayoffTeamID, ply -> ply)));
		
		Collections.sort(this.getFullNflPlayoffTeamList(), new Comparator<NflPlayoffTeam>() 
		{
		   public int compare(NflPlayoffTeam o1, NflPlayoffTeam o2) 
		   {
		      return o1.getIseasonId().compareTo(o2.getIseasonId());
		   }
		});
	}
	
	private void refreshListsAndMaps(String function, NflPlayoffTeam nflplayoffteam)
	{
		if (function.equalsIgnoreCase("delete"))
		{
			this.getFullNflPlayoffTeamsMap().remove(nflplayoffteam.getPlayoffTeamID());		
		}
		else if (function.equalsIgnoreCase("add"))
		{
			this.getFullNflPlayoffTeamsMap().put(nflplayoffteam.getPlayoffTeamID(), nflplayoffteam);		
		}
		else if (function.equalsIgnoreCase("update"))
		{
			this.getFullNflPlayoffTeamsMap().remove(nflplayoffteam.getPlayoffTeamID());		
			this.getFullNflPlayoffTeamsMap().put(nflplayoffteam.getPlayoffTeamID(), nflplayoffteam);	
		}
		
		this.getFullNflPlayoffTeamList().clear();
		Collection<NflPlayoffTeam> values = this.getFullNflPlayoffTeamsMap().values();
		this.setFullNflPlayoffTeamList(new ArrayList<>(values));
		
		Collections.sort(this.getFullNflPlayoffTeamList(), new Comparator<NflPlayoffTeam>() 
		{
		   public int compare(NflPlayoffTeam o1, NflPlayoffTeam o2) 
		   {
		      return o1.getiTeamID().compareTo(o2.getiTeamID());
		   }
		});
				
	}
	
	public List<NflPlayoffTeam> getFullNflPlayoffTeamList() 
	{
		return fullNflPlayoffTeamList;
	}

	public void setFullNflPlayoffTeamList(List<NflPlayoffTeam> fullNflPlayoffTeamList) 
	{
		this.fullNflPlayoffTeamList = fullNflPlayoffTeamList;
	}

	public Map<String, NflPlayoffTeam> getFullNflPlayoffTeamsMap() {
		return fullNflPlayoffTeamsMap;
	}

	public void setFullNflPlayoffTeamsMap(Map<String, NflPlayoffTeam> fullNflPlayoffTeamsMap) {
		this.fullNflPlayoffTeamsMap = fullNflPlayoffTeamsMap;
	}


}
