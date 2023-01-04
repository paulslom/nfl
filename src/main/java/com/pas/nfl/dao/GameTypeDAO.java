package com.pas.nfl.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;
import com.pas.nfl.DBObjects.TblGameType;
import com.pas.util.Utils;

public class GameTypeDAO extends BaseDBDAO
{
   private static final GameTypeDAO currentInstance = new GameTypeDAO();

    private GameTypeDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return gameTypeDAO
     */
    public static GameTypeDAO getDAOInstance()
    {
    	currentInstance.dataSource = Utils.getDatasourceProperties();
        return currentInstance;
    }	
      
	@SuppressWarnings("unchecked")
	public List<TblGameType> inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
						
		List<TblGameType> gameTypeList = new ArrayList<>();
		
		if (Info instanceof Integer)
		{
			String sql = "select * from TblGameType where iGameTypeId = :iGameTypeId";	
	    	Integer gameTypeID = (Integer)Info;	    	
	    	log.debug(methodName + "before inquiring for game type. Key value is = " + gameTypeID);	    	
			SqlParameterSource param = new MapSqlParameterSource("iGameTypeId", gameTypeID);
			NamedParameterJdbcTemplate namedParameterJdbcTemplate  = new NamedParameterJdbcTemplate(dataSource);
			TblGameType gameType = namedParameterJdbcTemplate.queryForObject(sql, param, new GameTypeRowMapper());			
			gameTypeList.add(gameType);		
		}		
		else //means get all conferences
		{
			log.debug("read all gametypes from DB");			
			String sql = "select * from TblGameType";	
			JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
			gameTypeList = jdbcTemplate.query(sql, new GameTypeRowMapper());
		}			
		
		log.debug("final list size is = " + gameTypeList.size());
		log.debug(methodName + "out");
		return gameTypeList;	
	}
	
}
