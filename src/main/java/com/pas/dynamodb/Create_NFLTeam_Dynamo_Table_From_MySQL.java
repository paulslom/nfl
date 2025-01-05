package com.pas.dynamodb;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.pas.beans.NflTeam;
import com.pas.nfl.dao.TeamsRowMapper;

import software.amazon.awssdk.core.internal.waiters.ResponseOrException;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

public class Create_NFLTeam_Dynamo_Table_From_MySQL
{
	private static Logger logger = LogManager.getLogger(Create_NFLTeam_Dynamo_Table_From_MySQL.class); //log4j for Logging 
	
	private static String AWS_TABLE_NAME = "nflteams";
	
    public static void main(String[] args) throws Exception
    { 
    	logger.debug("**********  START of program ***********");   	
    	
    	 try 
         {
    		 DynamoClients dynamoClients = DynamoUtil.getDynamoClients();
    
    		 List<NflTeam> teamsList = getTeamsFromMySQLDB();	
    		 loadTable(dynamoClients, teamsList);
	       	
	    	 DynamoUtil.stopDynamoServer();
	    	
			 logger.debug("**********  END of program ***********");
         }
    	 catch (Exception e)
    	 {
    		 logger.error("Exception in Create_All_Dynamo_Tables " + e.getMessage(), e);
    	 }
		System.exit(1);
	}

    private static List<NflTeam> getTeamsFromMySQLDB() 
	{
		MysqlDataSource ds = getMySQLDatasource();
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);    
    	String sql = "select team.iTeamID, team.iDivisionID, team.vTeamCity, team.vTeamNickname, team.cTeamCityAbbr,"
    			+ "	team.iFirstSeasonIDAsTeam, team.iLastSeasonIDAsTeam, team.sPictureFile,"
    			+ "    divis.iConferenceID, divis.vDivisionLifespan, divis.vDivisionName, conf.vConferenceName"
    			+ "  from tblteam team inner join tbldivision divis on team.idivisionid = divis.idivisionid"
    			+ "     inner join tblconference conf on divis.iConferenceID = conf.iConferenceID";		 
    	List<NflTeam> teamsList = jdbcTemplate.query(sql, new TeamsRowMapper());
		return teamsList;
	}
   
    private static void loadTable(DynamoClients dynamoClients, List<NflTeam> teamsList) throws Exception 
    {
        //Delete the table in DynamoDB Local if it exists.  If not, just catch the exception and move on
        try
        {
        	deleteTable(dynamoClients.getDynamoDbEnhancedClient());
        }
        catch (Exception e)
        {
        	logger.info(e.getMessage());
        }
        
        // Create a table in DynamoDB Local
        DynamoDbTable<NflTeam> teamTable = createTable(dynamoClients.getDynamoDbEnhancedClient(), dynamoClients.getDdbClient());           

        // Insert data into the table
    	logger.info("Inserting data into the table:" + AWS_TABLE_NAME);
         
        if (teamsList == null)
        {
        	logger.error("teams list is Empty - can't do anything more so exiting");
        }
        else
        {
        	for (int i = 0; i < teamsList.size(); i++) 
        	{
        		NflTeam nflTeam = teamsList.get(i);
				teamTable.putItem(nflTeam); 
			}           
        }        
	}
   
    private static DynamoDbTable<NflTeam> createTable(DynamoDbEnhancedClient ddbEnhancedClient, DynamoDbClient ddbClient) 
    {
        DynamoDbTable<NflTeam> teamTable = ddbEnhancedClient.table(AWS_TABLE_NAME, TableSchema.fromBean(NflTeam.class));
        
        // Create the DynamoDB table.  If it exists, it'll throw an exception
        
        try
        {
	        teamTable.createTable(builder -> builder.build());
        }
        catch (ResourceInUseException riue)
        {
        	logger.info("Table already exists! " + riue.getMessage());
        	throw riue;
        }
        // The 'dynamoDbClient' instance that's passed to the builder for the DynamoDbWaiter is the same instance
        // that was passed to the builder of the DynamoDbEnhancedClient instance used to create the 'customerDynamoDbTable'.
        // This means that the same Region that was configured on the standard 'dynamoDbClient' instance is used for all service clients.
        
        try (DynamoDbWaiter waiter = DynamoDbWaiter.builder().client(ddbClient).build()) // DynamoDbWaiter is Autocloseable
        { 
            ResponseOrException<DescribeTableResponse> response = waiter
                    .waitUntilTableExists(builder -> builder.tableName(AWS_TABLE_NAME).build())
                    .matched();
            
            response.response().orElseThrow(
                    () -> new RuntimeException(AWS_TABLE_NAME + " was not created."));
            
            // The actual error can be inspected in response.exception()
            logger.info(AWS_TABLE_NAME + " table was created.");
        }        
        
        return teamTable;
    }    
    
    private static void deleteTable(DynamoDbEnhancedClient ddbEnhancedClient) throws Exception
    {
    	DynamoDbTable<NflTeam> teamTable = ddbEnhancedClient.table(AWS_TABLE_NAME, TableSchema.fromBean(NflTeam.class));
       	teamTable.deleteTable();		
	}

    private static MysqlDataSource getMySQLDatasource()
	{
		MysqlDataSource ds = null;
		
		Properties prop = new Properties();
		
	    try 
	    {
	    	//Use the prior project for these properties - they don't exist in this one
	    	InputStream stream = new FileInputStream(new File("C:\\EclipseProjects\\NFLWS-MySQL\\Servers\\Tomcat v9.0 Server at localhost-config/catalina.properties"));
	    	prop.load(stream);   		
		
	    	ds = new MysqlDataSource();
	    	
		    String dbName = prop.getProperty("NFL_DB_NAME");
		    String userName = prop.getProperty("NFL_USERNAME");
		    String password = prop.getProperty("NFL_PASSWORD");
		    String hostname = prop.getProperty("NFL_HOSTNAME");
		    String port = prop.getProperty("NFL_PORT");
		    String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
		    
		    //logger.info("jdbcUrl for datasource: " + jdbcUrl);
		    
		    ds.setURL(jdbcUrl);
		    ds.setPassword(password);
		    ds.setUser(userName);
		    
		 }
		 catch (Exception e) 
	     { 
		    logger.error(e.toString(), e);
		 }     		
       	
       	return ds;
	}
		
}