package com.pas.nfl.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;
import com.pas.nfl.DBObjects.TblLeagueSetup;
import com.pas.nfl.DBObjects.TblSeason;
import com.pas.util.Utils;

public class LeagueSetupDAO extends BaseDBDAO
{
    private static final LeagueSetupDAO currentInstance = new LeagueSetupDAO();

    private LeagueSetupDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return leagueSetupDAO
     */
    public static LeagueSetupDAO getDAOInstance()
    {
    	currentInstance.dataSource = Utils.getDatasourceProperties();
        return currentInstance;
    }	
    
    public List<TblLeagueSetup> add(Object Info) throws DAOException
	{       
        final String methodName = "add::";
		log.debug(methodName + "in");
		
		TblLeagueSetup leagueSetup = (TblLeagueSetup)Info;
		
		log.debug("about to add TblLeagueSetup with season id = " + leagueSetup.getiSeasonId() + " and division id = " + leagueSetup.getiDivisionId());
     	
   		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);	
		String insertStr = " INSERT INTO TblLeagueSetup (iSeasonID, iDivisionID) values(?,?)";			
		jdbcTemplate.update(insertStr, new Object[] {leagueSetup.getiSeasonId(), leagueSetup.getiDivisionId()});	

		log.debug("TblLeagueSetup with season id = " + leagueSetup.getiSeasonId() + " and division id = " + leagueSetup.getiDivisionId() + " successfully added" );
		
		log.debug(methodName + "out");
		
		//no list to pass back on an add
		return null;
	}
	    
	@SuppressWarnings("unchecked")
	public List<TblLeagueSetup> inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
		
		List<TblLeagueSetup> leagueSetupList = new ArrayList<>();
			
		if (Info instanceof Integer)
		{
			String sql = "select * from TblLeagueSetup where iLeagueSetupId = :iLeagueSetupId";	
	    	Integer leagueSetupID = (Integer)Info;	    	
	    	log.debug(methodName + "before inquiring for league setup. Key value is = " + leagueSetupID.toString());	    	
			SqlParameterSource param = new MapSqlParameterSource("iLeagueSetupId", leagueSetupID);
			NamedParameterJdbcTemplate namedParameterJdbcTemplate  = new NamedParameterJdbcTemplate(dataSource);
			TblLeagueSetup leagueSetup = namedParameterJdbcTemplate.queryForObject(sql, param, new LeagueSetupRowMapper());			
			leagueSetupList.add(leagueSetup);		
		}		
		else //means we have a TblSeason object to use to get LeagueSetup data for
		{
			log.debug("TblSeason object provided - will retrieve all TblLeagueSetup rows for that season from DB");			
			String sql = "select * from TblLeagueSetup where iSeasonId = :iSeasonId";	
			TblSeason season = (TblSeason)Info;
			SqlParameterSource param = new MapSqlParameterSource("iSeasonId", season.getIseasonId());
			NamedParameterJdbcTemplate namedParameterJdbcTemplate  = new NamedParameterJdbcTemplate(dataSource);
			leagueSetupList = namedParameterJdbcTemplate.query(sql, param, new LeagueSetupRowMapper());	
		}			
									
		log.debug("final list size is = " + leagueSetupList.size());
		log.debug(methodName + "out");
		return leagueSetupList;	
	}
		
}
