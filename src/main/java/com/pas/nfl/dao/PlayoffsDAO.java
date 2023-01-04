package com.pas.nfl.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;
import com.pas.exception.SystemException;
import com.pas.nfl.DBObjects.TblConference;
import com.pas.nfl.DBObjects.TblPlayoffTeams;
import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.valueObject.PlayoffsSelection;
import com.pas.nfl.valueObject.PlayoffsSetup;
import com.pas.util.Utils;

public class PlayoffsDAO extends BaseDBDAO
{
    private static final PlayoffsDAO currentInstance = new PlayoffsDAO();

    private PlayoffsDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return conferenceDAO
     */
    public static PlayoffsDAO getDAOInstance()
    {
    	currentInstance.dataSource = Utils.getDatasourceProperties();
        return currentInstance;
    }	
      
	@SuppressWarnings("unchecked")
	public List inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
		
		List tempList = new ArrayList<>();
		
		List<TblConference> conferencesList = new ArrayList<>();		
		ConferenceDAO conferenceDAO;	
		
		try
		{
			conferenceDAO = (ConferenceDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.CONFERENCE_DAO);
			conferencesList = conferenceDAO.inquire(new String()); //string is just something other than Integer so we get all conferences
		}	
		catch (SystemException e)
		{
			throw new DAOException(e);
		}
		
		PlayoffsSelection playoffsSelection = (PlayoffsSelection)Info;
		
		if (playoffsSelection.getFunction().equalsIgnoreCase("showSetupForm"))
		{
			log.debug("playoffsSelection is showSetupForm");
			
			//see if there are entries in DB table first...
			StringBuffer sbuf = new StringBuffer();
			sbuf.append("select * from Tblplayoffteams where iseasonID = " + playoffsSelection.getSeasonID());
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);			
			List<TblPlayoffTeams> playoffTeamsFromDB = jdbcTemplate.query(sbuf.toString(), new PlayoffTeamsRowMapper());
			
			if (playoffTeamsFromDB != null && playoffTeamsFromDB.size() > 0)
			{
				//populate return list from DB
				
				for (int j = 0; j < playoffTeamsFromDB.size(); j++) 
				{
					TblPlayoffTeams tblplayoffteam = playoffTeamsFromDB.get(j);
					tempList.add(tblplayoffteam);					
				}
			}
			else
			{
				//populate return list knowning no existing rows for this season in db
				
				for (int j = 0; j < conferencesList.size(); j++) 
				{
					TblConference conf = conferencesList.get(j);
					
					for (int i = 1; i <= playoffsSelection.getTotalPlayoffTeamsByConf(); i++) 
					{
						PlayoffsSetup playoffsSetup = new PlayoffsSetup();
						playoffsSetup.setSeed(i);
						playoffsSetup.setConferenceID(conf.getIconferenceId());
						playoffsSetup.setConferenceName(conf.getVconferenceName());
						if (i <= playoffsSelection.getPlayoffByesByConf())
						{
							playoffsSetup.setBye(true);
						}
						else
						{
							playoffsSetup.setBye(false);
						}
						
						tempList.add(playoffsSetup);
					}
				}
				
			}
			
		}
		else if (playoffsSelection.getFunction().equalsIgnoreCase("playoffBrackets"))
		{
			log.debug("playoffsSelection is playoffBrackets");
			
			//see if there are entries in DB table first...
			StringBuffer sbuf = new StringBuffer();
			sbuf.append("select * from Tblplayoffteams where iseasonID = " + playoffsSelection.getSeasonID());
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);			
			List<TblPlayoffTeams> playoffTeamsListFromDB = jdbcTemplate.query(sbuf.toString(), new PlayoffTeamsRowMapper());
			
			if (playoffTeamsListFromDB != null && playoffTeamsListFromDB.size() > 0)
			{
				//populate return list from DB
				
				for (int j = 0; j < playoffTeamsListFromDB.size(); j++) 
				{
					TblPlayoffTeams tblplayoffteam = playoffTeamsListFromDB.get(j);
					tempList.add(tblplayoffteam);					
				}
			}
		}
		
		log.debug("final list size is = " + tempList.size());
		log.debug(methodName + "out");
		return tempList;	
	}
	
	public List<TblPlayoffTeams> add(Object Info) throws DAOException
	{       
        final String methodName = "add::";
		log.debug(methodName + "in");
		
		TblPlayoffTeams playoffTeam = (TblPlayoffTeams)Info;
		
		String insertStr = " INSERT INTO tblPlayoffTeams (iSeasonID, iConferenceID, iTeamID, iSeed, bBye) values(?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(insertStr, new Object[] {playoffTeam.getIseasonId(), playoffTeam.getiConferenceId(), playoffTeam.getiTeamID(), playoffTeam.getiSeed(), playoffTeam.getbBye()});	
	
		log.debug(methodName + "out");
		
		//no list to pass back on an add
		return null;
	}	 
	
	public List<TblPlayoffTeams> delete(Object Info) throws DAOException
	{
		final String methodName = "delete:";
		log.debug(methodName + "in");
		
		Integer seasonID = (Integer)Info;
		
		//coded to clear out all playoff teams for given season.  this way we can just re-add them all after user changes them.
		String deleteStr = " delete from tblplayoffteams where iSeasonID = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(deleteStr, seasonID);
		
		log.debug(methodName + "out");
		
		//no list to pass back on a delete
		return null;
	} 
	
	@SuppressWarnings("unchecked")
	public List<TblPlayoffTeams> update(Object Info) throws DAOException
	{
		final String methodName = "update:";
		log.debug(methodName + "in");
		
		//expecting a list of teams here.  
		List<TblTeam> playoffTeamList = (List<TblTeam>)Info;
		
		//first item in list holds season id (not a real team)
		
		TblTeam tempTeam = playoffTeamList.get(0);
		Integer seasonID = tempTeam.getIteamId();
		Integer totalByes = tempTeam.getIdivisionId();
		
		//first need to clear out all playoff teams for this season
		delete(seasonID);
	
		for (int i=1; i < playoffTeamList.size(); i++)
		{	
			TblTeam team = playoffTeamList.get(i);			
			TblPlayoffTeams playoffTeam = new TblPlayoffTeams();
			playoffTeam.setIseasonId(seasonID);
			playoffTeam.setiConferenceId(team.getDivision().getIconferenceId());
			playoffTeam.setiSeed(team.getPlayoffSeedInt());
			playoffTeam.setiTeamID(team.getIteamId());
			if (team.getPlayoffSeedInt() <= totalByes)
			{
				playoffTeam.setbBye(true);
			}
			else
			{
				playoffTeam.setbBye(false);
			}
			add(playoffTeam);
		}	
		
		//no need to pass back a list on an update
		return null;	
	}
	
	//find out what conference this team is in
	private Integer getConferenceID(Integer idivisionId)
	{		
		return null;
	}
	
}
