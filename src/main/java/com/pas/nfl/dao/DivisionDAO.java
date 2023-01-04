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
import com.pas.nfl.DBObjects.TblDivision;
import com.pas.util.Utils;

public class DivisionDAO extends BaseDBDAO
{
    private static final DivisionDAO currentInstance = new DivisionDAO();

    private DivisionDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return divisionDAO
     */
    public static DivisionDAO getDAOInstance()
    {
    	currentInstance.dataSource = Utils.getDatasourceProperties();
        return currentInstance;
    }	
       
	@SuppressWarnings("unchecked")
	public List<TblDivision> inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
		
		List<TblDivision> divisionList = new ArrayList<>();
		
		if (Info instanceof Integer)
		{
			String sql = "select * from TblDivision where iDivisionId = :iDivisionId";	
	    	Integer divisionID = (Integer)Info;	    	
	    	log.debug(methodName + "before inquiring for division. Key value is = " + divisionID);	    	
			SqlParameterSource param = new MapSqlParameterSource("iDivisionId", divisionID);
			NamedParameterJdbcTemplate namedParameterJdbcTemplate  = new NamedParameterJdbcTemplate(dataSource);
			TblDivision division = namedParameterJdbcTemplate.queryForObject(sql, param, new DivisionRowMapper());			
			divisionList.add(division);		
		}		
		else //means get all conferences
		{
			log.debug("read all divisions from DB");			
			String sql = "select * from TblDivision";	
			JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
			divisionList = jdbcTemplate.query(sql, new DivisionRowMapper());
		}			
		log.debug("final list size is = " + divisionList.size());
		log.debug(methodName + "out");
		return divisionList;	
	}
		
}
