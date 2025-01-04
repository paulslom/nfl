package com.pas.nfl.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.beans.NflMain;
import com.pas.beans.NflSecurity;
import com.pas.dynamodb.DynamoClients;
import com.pas.util.Utils;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;

public class NflSecurityDAO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(NflSecurityDAO.class);
	
	private Map<String,NflSecurity> fullUserMap = new HashMap<>();

	private static DynamoClients dynamoClients;
	private static DynamoDbTable<NflSecurity> usersTable;
	private static final String AWS_TABLE_NAME = "nflsecurity";
	
	public NflSecurityDAO(DynamoClients dynamoClients2, NflMain nflmain) 
	{
	   try 
	   {
	       dynamoClients = dynamoClients2;
	       usersTable = dynamoClients.getDynamoDbEnhancedClient().table(AWS_TABLE_NAME, TableSchema.fromBean(NflSecurity.class));
	   } 
	   catch (final Exception ex) 
	   {
	      logger.error("Got exception while initializing NflSecurityDAO. Ex = " + ex.getMessage(), ex);
	   }	   
	}
	
	public void readAllUsersFromDB() throws Exception
	{				
		Iterator<NflSecurity> results = usersTable.scan().items().iterator();
            
        while (results.hasNext()) 
        {
            NflSecurity gu = results.next();
            
            if (this.getFullUserMap().containsKey(gu.getUserName()))
			{
				logger.error("duplicate user: " + gu.getUserName());
			}
			else
			{
				this.getFullUserMap().put(gu.getUserName(), gu);				
			}
        }
          	
		logger.info("LoggedDBOperation: function-inquiry; table:nflsecurity; rows:" + this.getFullUserMap().size());
				
		//this loop only for debugging purposes
		/*
		for (Map.Entry<String, NflSecurity> entry : this.getFullUserMap().entrySet()) 
		{
		    String key = entry.getKey();
		    NflSecurity golfUser = entry.getValue();

		    logger.info("Key = " + key + ", value = " + golfUser.getUserName());
		}
		*/
		
		logger.info("exiting");
		
	}
		
	public NflSecurity getNflSecurity(String username)
    {	    	
		NflSecurity gu = this.getFullUserMap().get(username);			
    	return gu;
    }	
	
	private void deleteUser(String username) throws Exception
	{
		Key key = Key.builder().partitionValue(username).build();
		DeleteItemEnhancedRequest deleteItemEnhancedRequest = DeleteItemEnhancedRequest.builder().key(key).build();
		usersTable.deleteItem(deleteItemEnhancedRequest);
		
		logger.info("LoggedDBOperation: function-delete; table:nflsecurity; rows:1");
		
		NflSecurity gu = new NflSecurity();
		gu.setUserName(username);
		refreshListsAndMaps("delete", gu);	
	}
	
	public void addUser(NflSecurity gu, String pw) throws Exception
	{
		String encodedPW = "";
		
		if (pw == null || pw.trim().length() == 0)
		{	
			encodedPW = Utils.getEncryptedPassword(gu.getUserName());
		}
		else
		{
			encodedPW = Utils.getEncryptedPassword(pw);
			
		}
		
		gu.setPassword(encodedPW);		
		
		PutItemEnhancedRequest<NflSecurity> putItemEnhancedRequest = PutItemEnhancedRequest.builder(NflSecurity.class).item(gu).build();
		usersTable.putItem(putItemEnhancedRequest);
			
		logger.info("LoggedDBOperation: function-update; table:nflsecurity; rows:1");
					
		refreshListsAndMaps("add", gu);	
	}
	
	public void updateUser(NflSecurity gu) throws Exception
	{
		deleteUser(gu.getUserName());
		addUser(gu, gu.getPassword());		
		refreshListsAndMaps("update", gu);	
	}

	private void refreshListsAndMaps(String function, NflSecurity golfuser) 
	{
		if (function.equalsIgnoreCase("delete"))
		{
			this.getFullUserMap().remove(golfuser.getUserName());	
		}
		else if (function.equalsIgnoreCase("add"))
		{
			this.getFullUserMap().put(golfuser.getUserName(), golfuser);	
		}
		else if (function.equalsIgnoreCase("update"))
		{
			this.getFullUserMap().remove(golfuser.getUserName());	
			this.getFullUserMap().put(golfuser.getUserName(), golfuser);		
		}
		
	}
	
	public Map<String, NflSecurity> getFullUserMap() 
	{
		return fullUserMap;
	}

	public void setFullUserMap(Map<String, NflSecurity> fullUserMap) 
	{
		this.fullUserMap = fullUserMap;
	}

	
}
