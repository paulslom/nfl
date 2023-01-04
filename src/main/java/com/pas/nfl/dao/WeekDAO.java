package com.pas.nfl.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.pas.constants.IAppConstants;
import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;
import com.pas.nfl.DBObjects.TblWeek;
import com.pas.util.Utils;

public class WeekDAO extends BaseDBDAO
{	 
  private static final WeekDAO currentInstance = new WeekDAO();

    private WeekDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return weekDAO
     */
    public static WeekDAO getDAOInstance()
    {
    	currentInstance.dataSource = Utils.getDatasourceProperties();
        return currentInstance;
    }	
    	
    public List<TblWeek> add(Object Info) throws DAOException
	{       
        final String methodName = "add::";
		log.debug(methodName + "in");
		
		TblWeek week = (TblWeek)Info;
		
		log.debug("about to add TblWeek with season id = " + week.getiSeasonId() + " and week number = " + week.getIweekNumber());
     	
   		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);	
		String insertStr = " INSERT INTO TblWeek (iSeasonID, iWeekNumber, sWeekDescription) values(?,?,?)";			
		jdbcTemplate.update(insertStr, new Object[] {week.getiSeasonId(), week.getIweekNumber(), week.getSweekDescription()});	

		log.debug("TblWeek with season id = " + week.getiSeasonId() + " and week number = " + week.getIweekNumber() + " successfully added" );
		
		log.debug(methodName + "out");
		
		//no list to pass back on an add
		return null;	

	}
	    
	@SuppressWarnings("unchecked")
	public List<TblWeek> inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
						
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("select * from TblWeek");
		
		if (Info instanceof Integer) //looking for a particular week
		{
			Integer weekID = (Integer)Info;
				
			log.debug("Integer object provided - will perform a single row retrieval from DB");			
			
			sbuf.append(" where iweekId = ");
			sbuf.append(weekID.toString());
			
			log.debug(methodName + "before inquiring for week. Key value is = " + weekID.toString());
	
		}		
		else //means we are looking for all the weeks for a given season
		{
			String seasonyear = (String)Info;
			log.debug("Will perform multiple rows retrieval for weeks");
			sbuf.append(" where tblSeason.cyear = ");
			sbuf.append(seasonyear);
			sbuf.append(" order by iweekNumber");
						
		}				
			
		List<TblWeek> weekList = new ArrayList<>();
		
		if (Info instanceof Integer) //looking for a particular week
		{
			String sql = "select * from TblWeek where iWeekId = :iWeekId";	
	    	Integer weekID = (Integer)Info;	    	
	    	log.debug(methodName + "before inquiring for week. Key value is = " + weekID.toString());	    	
			SqlParameterSource param = new MapSqlParameterSource("iWeekId", weekID);
			NamedParameterJdbcTemplate namedParameterJdbcTemplate  = new NamedParameterJdbcTemplate(dataSource);
			TblWeek week = namedParameterJdbcTemplate.queryForObject(sql, param, new WeekRowMapper());			
			weekList.add(week);		
		}		
		else //means we are looking for all the weeks for a given season
		{
			String seasonyear = (String)Info;
			log.debug("Will perform multiple rows retrieval for weeks");
			String sql = "select * from TblWeek wk, TblSeason ss where wk.iSeasonId = ss.iSeasonID and ss.cyear = :cyear order by iweekNumber";	
			SqlParameterSource param = new MapSqlParameterSource("cyear", seasonyear);
			NamedParameterJdbcTemplate namedParameterJdbcTemplate  = new NamedParameterJdbcTemplate(dataSource);
			weekList = namedParameterJdbcTemplate.query(sql, param, new WeekRowMapper());								
		}			
									
		log.debug("final list size is = " + weekList.size());
		log.debug(methodName + "out");
		return weekList;	
	}
	
	public Integer getRegularSeasonTotalWeeks(String seasonYear)
	{
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(" select max(wk.iweeknumber) from tblgame gm ");
		sbuf.append("  inner join tblweek wk on gm.iweekid = wk.iweekid");
		sbuf.append("  inner join tblgametype gmtyp on gm.igametypeid = gmtyp.igametypeid");
		sbuf.append("  inner join tblseason seas on wk.iseasonid = seas.iseasonid");
		sbuf.append("  where seas.cYear = ");
		sbuf.append(seasonYear);
		sbuf.append("    and gmtyp.igametypeid = ");
		sbuf.append(IAppConstants.REGULAR_SEASON_GAME_TYPE);
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
		
		Integer totalWeeks = jdbcTemplate.queryForObject(sbuf.toString(), Integer.class);
		
		log.debug("total regular season weeks for " + seasonYear + " season: " + totalWeeks);
		
		return totalWeeks;		
	}
	
}
