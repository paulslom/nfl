package com.pas.nfl.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;
import com.pas.exception.SystemException;
import com.pas.nfl.DBObjects.TblLeagueSetup;
import com.pas.nfl.DBObjects.TblSeason;
import com.pas.nfl.DBObjects.TblWeek;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.valueObject.SeasonSelection;
import com.pas.util.Utils;

public class SeasonDAO extends BaseDBDAO
{
    private static final SeasonDAO currentInstance = new SeasonDAO();   
    
    private SeasonDAO()
    {
        super();        
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return seasonDAO
     */
    public static SeasonDAO getDAOInstance()
    {
    	currentInstance.dataSource = Utils.getDatasourceProperties();
        return currentInstance;
    }    
   
    public List<TblSeason> add(Object Info) throws DAOException
   	{       
        final String methodName = "add::";
   		log.debug(methodName + "in");
   		
   		log.debug("the add from SeasonDAO is used as SeasonCloneAction's DAO");
   		
   		List<TblSeason> maxSeasonList = getMaxSeasonID();
   		TblSeason maxExistingSeason = maxSeasonList.get(0);
   		
   		String maxExistingSeasonYearString = maxExistingSeason.getCyear();
   		Integer maxExistingSeasonYearInteger = new Integer(maxExistingSeasonYearString);
   		Integer nextSeasonYearInteger = maxExistingSeasonYearInteger + 1;
   		String nextSeasonYearString = nextSeasonYearInteger.toString();
   		
   		TblSeason clonedSeason = new TblSeason();
   		clonedSeason.setCyear(nextSeasonYearString);
   		clonedSeason.setiConferencePlayoffTeams(maxExistingSeason.getiConferencePlayoffTeams());
   		clonedSeason.setiPlayoffByesByConf(maxExistingSeason.getiPlayoffByesByConf());
   		
   		//first of 3 inserts - 1) simply add the new TblSeason row
   		
   		log.debug("about to add TblSeason with year = " + nextSeasonYearString);
     	
   		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);	
		String insertStr = " INSERT INTO TblSeason (cYear, vSuperBowl, iConferencePlayoffTeams, iPlayoffByesByConf) values(?,?,?,?)";			
		jdbcTemplate.update(insertStr, new Object[] {clonedSeason.getCyear(), clonedSeason.getVsuperBowl(), clonedSeason.getiConferencePlayoffTeams(), clonedSeason.getiPlayoffByesByConf()});	

		log.debug("Season with year = " + nextSeasonYearString + " successfully added");
		
		//Re-retrieve max season now that you've inserted the new one.
		
		maxSeasonList = getMaxSeasonID();
   		TblSeason newSeason = maxSeasonList.get(0);
   		
   		log.debug("New season's seasonID = " + newSeason.getIseasonId());
   		
   		//next, need to insert the TblLeagueSetup rows.  One per division for the season.  8 as of 2020
   		
   		LeagueSetupDAO leagueSetupDAOReference;
   		try
   		{
   			leagueSetupDAOReference = (LeagueSetupDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.LEAGUESETUP_DAO);
   			List<TblLeagueSetup> leagueSetupList = leagueSetupDAOReference.inquire(maxExistingSeason);			
   			
   			log.debug("looping through list of LeagueSetup objects to insert new season's league setup rows");
   			
   			for (int i = 0; i < leagueSetupList.size(); i++)
   			{
   				TblLeagueSetup leagueSetup = leagueSetupList.get(i);
   				TblLeagueSetup clonedLeagueSetup = new TblLeagueSetup();
   				clonedLeagueSetup.setiDivisionId(leagueSetup.getiDivisionId());
   				clonedLeagueSetup.setiSeasonId(newSeason.getIseasonId());
   				leagueSetupDAOReference.add(clonedLeagueSetup);
   			}			
   		}
   		catch (SystemException e)
   		{
   			throw new DAOException(e);
   		}
   						
   		//finally, need to insert the TblWeek rows.  17 weeks per season as of 2020, then 18 regular season weeks as of 2021 season.
   		
   		WeekDAO weekDAOReference;
   		try
   		{
   			weekDAOReference = (WeekDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.WEEK_DAO);
   			List<TblWeek> weekList = weekDAOReference.inquire(maxExistingSeason.getCyear());			
   			
   			log.debug("looping through list of TblWeek objects to insert new season's TblWeek rows");
   			
   			for (int i = 0; i < weekList.size(); i++)
   			{
   				TblWeek week = weekList.get(i);
   				TblWeek clonedWeek = new TblWeek();
   				clonedWeek.setiSeasonId(newSeason.getIseasonId());
   				clonedWeek.setIweekNumber(week.getIweekNumber());
   				clonedWeek.setSweekDescription(week.getSweekDescription());
   				weekDAOReference.add(clonedWeek);
   			}			
   		}
   		catch (SystemException e)
   		{
   			throw new DAOException(e);
   		}
   		
   		log.debug(methodName + "out");
   		
   		//no list to pass back on an add
   		return null;	

   	}
   
    @SuppressWarnings("unchecked")
	public List<TblSeason> inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
		
		String sql = "";
		List<TblSeason> seasonList = new ArrayList<>();
		
		if (Info instanceof SeasonSelection)
		{
			SeasonSelection seasonSel = (SeasonSelection)Info;
			
			if (seasonSel.getSeasonID() != null && seasonSel.getSeasonID() > 0)
			{
				sql = "select * from TblSeason where iseasonId = " + seasonSel.getSeasonID();	
			}
			else
			{
				sql = "select * from TblSeason where cYear = " + seasonSel.getSeasonYear();					
			}
	    	
	    	log.debug("query to be run: " + sql);
	    	
			JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
			TblSeason season = jdbcTemplate.queryForObject(sql, new SeasonRowMapper());			
			seasonList.add(season);		
		}		
		else //means we have no seasonSelection object - this is an inquire request for a list of seasons
		{	
			log.debug("Will perform multiple rows retrieval for seasons");	
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);	    	
			sql = "select * from TblSeason order by cyear";		 
			seasonList = jdbcTemplate.query(sql, new SeasonRowMapper()); 	
		}				
			
		log.debug("final list size is = " + seasonList.size());
		log.debug(methodName + "out");
		
		return seasonList;	
	}
    
   
	@SuppressWarnings("unchecked")
	public List<TblSeason> getMaxSeasonID() throws DAOException 
	{
		final String methodName = "getMaxSeasonID::";
		log.debug(methodName + "in");
						
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("select max(cyear) from TblSeason");
					
		log.debug("about to run query: " + sbuf.toString());
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);			
		String cyear = jdbcTemplate.queryForObject(sbuf.toString(), String.class);
		
		if (cyear == null)
		{
			throw new DAOException("cannot determine max season");
		}
		
		List<TblSeason> seasonList = new ArrayList<>();
		
		String sql = "select * from TblSeason where cyear = :cyear";	
	   	log.debug(methodName + "before inquiring for season. Year of season is = " + cyear);	    	
		SqlParameterSource param = new MapSqlParameterSource("cyear", cyear);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate  = new NamedParameterJdbcTemplate(dataSource);
		TblSeason season = namedParameterJdbcTemplate.queryForObject(sql, param, new SeasonRowMapper());			
		seasonList.add(season);			
											
		log.debug("final list size is = " + seasonList.size());
		log.debug(methodName + "out");
		
		return seasonList;	
	}
	
}
