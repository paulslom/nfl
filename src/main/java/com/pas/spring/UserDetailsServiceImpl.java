package com.pas.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pas.beans.NflSecurity;
import com.pas.dynamodb.DynamoClients;
import com.pas.dynamodb.DynamoUtil;
import com.pas.nfl.dao.NflSecurityDAO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService 
{
	  private static Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);	
	  
	  @Override
	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	  {
		  logger.info("User " + username + " attempting to log in");
		  
		  DynamoClients dynamoClients;
		  NflSecurity nflsecurity2 = null;
		  
		  try 
		  {
			  dynamoClients = DynamoUtil.getDynamoClients();
			  NflSecurityDAO nflsecurityDao = new NflSecurityDAO(dynamoClients, null);
		      nflsecurityDao.readAllUsersFromDB();
		      nflsecurity2 = nflsecurityDao.getNflSecurity(username);
		  } 
		  catch (Exception e) 
		  {
			  logger.error("Exception loading security table: " + e.getMessage());
		  }
	      

	      UserBuilder builder = null;
	   
	     if (nflsecurity2 != null && nflsecurity2.getUserName() != null && nflsecurity2.getUserName().trim().length() > 0) 
	     {
	         builder = org.springframework.security.core.userdetails.User.withUsername(username);
	         builder.password(nflsecurity2.getPassword());
	         
	         logger.info("User " + username + " successfully found on database as " + nflsecurity2.getUserName());
	     } 
	     else 
	     {
	    	 logger.info("User " + username + " not found on database.");
	         throw new UsernameNotFoundException("User not found.");
	     }

	     return builder.build();
	  }
	  
}