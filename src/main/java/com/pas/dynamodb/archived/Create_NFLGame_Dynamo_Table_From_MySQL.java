package com.pas.dynamodb.archived;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.pas.dynamodb.DynamoClients;
import com.pas.dynamodb.DynamoNflGame;
import com.pas.dynamodb.DynamoUtil;
import com.pas.nfl.dao.GamesRowMapper;

import software.amazon.awssdk.core.internal.waiters.ResponseOrException;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.EnhancedGlobalSecondaryIndex;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;
import software.amazon.awssdk.services.dynamodb.model.ProjectionType;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

public class Create_NFLGame_Dynamo_Table_From_MySQL
{
	private static Logger logger = LogManager.getLogger(Create_NFLGame_Dynamo_Table_From_MySQL.class); //log4j for Logging 
	
	private static String AWS_TABLE_NAME = "nflgames";
		
    public static void main(String[] args) throws Exception
    { 
    	logger.debug("**********  START of program ***********");   	
    	
    	 try 
         {
    		 DynamoClients dynamoClients = DynamoUtil.getDynamoClients();
    
    		 List<DynamoNflGame> gamesList = getGamesFromMySQLDB();	
    		 loadTable(dynamoClients, gamesList);       	
	    	
			 logger.debug("**********  END of program ***********");
         }
    	 catch (Exception e)
    	 {
    		 logger.error("Exception in Create_NFLGame_Dynamo_Table_From_MySQL " + e.getMessage(), e);
    	 }
		System.exit(1);
	}

    private static List<DynamoNflGame> getGamesFromMySQLDB() 
	{
		MysqlDataSource ds = getMySQLDatasource();
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);    
    	String sql = " select game.iGameID, game.iWeekID, game.dGameDateTime, game.iAwayTeamID, game.iHomeTeamID,"
    			+ "   	game.iAwayTeamScore, game.iHomeTeamScore, game.iGameTypeID,"
    			+ "    gametype.sGameTypeDesc, gametype.iPlayoffRound,"
    			+ "    CONCAT(awayteam.vTeamCity,' ',awayteam.vTeamNickname) as awayteamName,"
    			+ "    CONCAT(hometeam.vTeamCity,' ',hometeam.vTeamNickname) as hometeamName,"
    			+ "    hometeam.cTeamCityAbbr as homeTeamAbbr, awayteam.cTeamCityAbbr as awayTeamAbbr,"
    			+ "    week.iSeasonID, week.iWeekNumber, week.sWeekDescription, season.cYear"
    			+ "    from tblgame game inner join tblweek week on game.iWeekID = week.iWeekID "
    			+ "        inner join tblgametype gametype on game.iGameTypeID = gametype.iGameTypeID"
    			+ "        inner join tblteam hometeam on game.iHomeTeamID = hometeam.iTeamID"
    			+ "        inner join tblteam awayteam on game.iAwayTeamID = awayteam.iTeamID"
    			+ "        inner join tblseason season on week.iSeasonID = season.iSeasonID"
    			+ "     order by week.iSeasonID, week.iWeekNumber, game.dGameDateTime";		 
    	List<DynamoNflGame> gamesList = jdbcTemplate.query(sql, new GamesRowMapper());
		return gamesList;
	}
   
    private static void loadTable(DynamoClients dynamoClients, List<DynamoNflGame> gamesList) throws Exception 
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
        DynamoDbTable<DynamoNflGame> gameTable = createTable(dynamoClients.getDynamoDbEnhancedClient(), dynamoClients.getDdbClient());           

        // Insert data into the table
    	logger.info("Inserting data into the table:" + AWS_TABLE_NAME);
        
    	int putCount = 0;
    	
        if (gamesList == null)
        {
        	logger.error("games list is Empty - can't do anything more so exiting");
        }
        else
        {
        	logger.info("About to try to put " + gamesList.size() + " rows into table " + AWS_TABLE_NAME);
        	
        	for (int i = 0; i < gamesList.size(); i++) 
        	{
        		DynamoNflGame nflGame = gamesList.get(i);
        		gameTable.putItem(nflGame); 
				putCount++;
				logger.info(AWS_TABLE_NAME + " Put game row count: " + putCount);
			} 
        	
        	logger.info("FINISHED inserting " + putCount + " rows into the table:" + AWS_TABLE_NAME);
        }        
	}
   
    private static DynamoDbTable<DynamoNflGame> createTable(DynamoDbEnhancedClient ddbEnhancedClient, DynamoDbClient ddbClient) 
    {
        DynamoDbTable<DynamoNflGame> gamesTable = ddbEnhancedClient.table(AWS_TABLE_NAME, TableSchema.fromBean(DynamoNflGame.class));
        
        // Create the DynamoDB table.  If it exists, it'll throw an exception
        
        try
        {        	
          	ArrayList<EnhancedGlobalSecondaryIndex> gsindices = new ArrayList<>();
            	
        	EnhancedGlobalSecondaryIndex gsi1 = EnhancedGlobalSecondaryIndex.builder()
        			.indexName("gsi_GameDate")
        			.projection(p -> p.projectionType(ProjectionType.ALL))
        			.build();
        	gsindices.add(gsi1);
        	            	  	
        	gamesTable.createTable(r -> r.globalSecondaryIndices(gsindices).build());
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
        
        return gamesTable;
    }    
    
    private static void deleteTable(DynamoDbEnhancedClient ddbEnhancedClient) throws Exception
    {
    	DynamoDbTable<DynamoNflGame> teamTable = ddbEnhancedClient.table(AWS_TABLE_NAME, TableSchema.fromBean(DynamoNflGame.class));
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