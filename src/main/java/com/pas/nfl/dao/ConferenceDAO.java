package com.pas.nfl.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;
import com.pas.nfl.DBObjects.TblConference;
import com.pas.nfl.DBObjects.TblLeagueSetup;
import com.pas.nfl.DBObjects.TblSeason;
import com.pas.util.Utils;

public class ConferenceDAO extends BaseDBDAO
{ 	 
    private static final ConferenceDAO currentInstance = new ConferenceDAO();

    private ConferenceDAO()
    {
        super();
    }
    
    public static ConferenceDAO getDAOInstance()
    {
    	currentInstance.dataSource = Utils.getDatasourceProperties();
        return currentInstance;
    }	   
    
	@SuppressWarnings("unchecked")
	public List<TblConference> inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
		
		List<TblConference> conferenceList = new ArrayList<>();
		
		if (Info instanceof Integer)
		{
			String sql = "select * from TblConference where iConferenceId = :iConferenceId";	
	    	Integer conferenceID = (Integer)Info;	    	
	    	log.debug(methodName + "before inquiring for conference. Key value is = " + conferenceID);	    	
			SqlParameterSource param = new MapSqlParameterSource("iConferenceId", conferenceID);
			NamedParameterJdbcTemplate namedParameterJdbcTemplate  = new NamedParameterJdbcTemplate(dataSource);
			TblConference conference = namedParameterJdbcTemplate.queryForObject(sql, param, new ConferenceRowMapper());			
			conferenceList.add(conference);		
		}		
		else //means get all conferences
		{
			log.debug("read all conferences from DB");			
			String sql = "select * from TblConference";	
			JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
			conferenceList = jdbcTemplate.query(sql, new ConferenceRowMapper());
		}			
								
		log.debug("final list size is = " + conferenceList.size());
		log.debug(methodName + "out");
		return conferenceList;	
	}
	
}
