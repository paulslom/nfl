package com.pas.nfl.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;
import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.valueObject.TeamSelection;
import com.pas.util.Utils;

public class TeamDAO extends BaseDBDAO
{ 
    private static final TeamDAO currentInstance = new TeamDAO();
 
    private TeamDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return teamDAO
     */
    public static TeamDAO getDAOInstance()
    {
    	currentInstance.dataSource = Utils.getDatasourceProperties();
        return currentInstance;
    }	
    	
    @SuppressWarnings("unchecked")
	public List<TblTeam> inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
		
		List<TblTeam> teamList = new ArrayList<>();
		
		if (Info instanceof Integer)
		{
			String sql = "select tm.*, divis.*, confer.* from tblteam tm, tblDivision divis, tblConference confer " +
							" where tm.iDivisionID = divis.iDivisionID and divis.iConferenceID = confer.iConferenceID and iTeamId = :iTeamId";	 
			log.debug("sql about to be run: " + sql);	   
	    	Integer teamID = (Integer)Info;	    	
	    	log.debug(methodName + "before inquiring for Team. Key value is = " + teamID);	    	
			SqlParameterSource param = new MapSqlParameterSource("iTeamId", teamID);
			NamedParameterJdbcTemplate namedParameterJdbcTemplate  = new NamedParameterJdbcTemplate(dataSource);
			TblTeam team = namedParameterJdbcTemplate.queryForObject(sql, param, new TeamRowMapper());			
			teamList.add(team);		
		}		
		else //means we have a TeamSel object
		{
			TeamSelection teamSel = (TeamSelection)Info;
			String seasonyear = teamSel.getSeasonYear();
			String selType = teamSel.getTeamSelectionType();
			Integer conferenceSelection = teamSel.getConferenceID();
			
			StringBuffer sbuf = new StringBuffer();
			sbuf.append("select tm.*, divis.*, confer.* from tblteam tm, tblDivision divis, tblConference confer where tm.iDivisionID = divis.iDivisionID ");
			sbuf.append("and divis.iConferenceID = confer.iConferenceID and tm.iteamId in ");
			sbuf.append("(Select team.iteamId ");
			sbuf.append("from TblTeam team, TblSeason season ");
			sbuf.append("where season.cyear = :cyear");
			sbuf.append(" and (team.iLastSeasonIDAsTeam >= season.iseasonId or team.iLastSeasonIDAsTeam is null) ");
			sbuf.append(" and team.iFirstSeasonIDAsTeam <= season.iseasonId )"); 
			
			if (selType.equalsIgnoreCase("byDivision"))
			{	
				sbuf.append(" order by confer.vconferenceName, divis.vdivisionName, tm.vteamCity");
			}
			else if (selType.equalsIgnoreCase("afcTeams") || selType.equalsIgnoreCase("nfcTeams"))
			{
				sbuf.append(" and confer.iConferenceID = " + conferenceSelection);				
				sbuf.append(" order by confer.vconferenceName, divis.vdivisionName, tm.vteamCity");
			}
			else
			{
				sbuf.append(" order by tm.vteamCity");    				
			}
			String sql = sbuf.toString();
			
			log.debug("Will perform multiple rows retrieval for teams with the following query: " + sql);			
			
			SqlParameterSource param = new MapSqlParameterSource("cyear", seasonyear);
			NamedParameterJdbcTemplate namedParameterJdbcTemplate  = new NamedParameterJdbcTemplate(dataSource);
			teamList = namedParameterJdbcTemplate.query(sql, param, new TeamRowMapper());			
		}	
										
		log.debug("final list size is = " + teamList.size());
		log.debug(methodName + "out");
		return teamList;	
	}
	
}
