package com.pas.nfl.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;
import com.pas.exception.SystemException;
import com.pas.nfl.DBObjects.TblGame;
import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.valueObject.GameSelection;
import com.pas.nfl.valueObject.TeamSelection;
import com.pas.nfl.valueObject.TempOpponent;
import com.pas.nfl.valueObject.TempScheduleRow;
import com.pas.util.Utils;

public class ScheduleDAO extends BaseDBDAO
{
    private static final ScheduleDAO currentInstance = new ScheduleDAO();

    private ScheduleDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return conferenceDAO
     */
    public static ScheduleDAO getDAOInstance()
    {
    	currentInstance.dataSource = Utils.getDatasourceProperties();
        return currentInstance;
    }	
      
	public List<TempScheduleRow> inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
		
		//tempScheduleList is what this method needs to ultimately return...
		List<TempScheduleRow> tempScheduleList = new ArrayList<TempScheduleRow>();
		
		String seasonYear = (String)Info;
			
		//First remove all records from tbltempschedule
		
		log.debug("about to delete all records from table tbltempschedule");
		
		String deleteStr = "delete from tbltempschedule";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
		jdbcTemplate.update(deleteStr);	
		
		log.debug("returned from deleting all records from table tbltempschedule");
		
		//Next, get all games for the season provided in info object
		
		TeamDAO teamDAOReference;
		GameDAO gameDAOReference;
		WeekDAO weekDAOReference;
		String opponentString = "";
		GameSelection gameSel = new GameSelection();
		gameSel.setSeasonYear(new Integer(seasonYear));
		List<TempOpponent> opponents = new ArrayList<TempOpponent>();
		TempScheduleRow titleRow = new TempScheduleRow();
				
		try
		{
			//First get all the teams for this season in order by city
			teamDAOReference = (TeamDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.TEAM_DAO);
			TeamSelection teamSel = new TeamSelection();
			
			teamSel.setSeasonYear(seasonYear);
			teamSel.setTeamSelectionType("byCity");
			List<TblTeam> teamList = teamDAOReference.inquire(teamSel);
			
			//Next get the total regular season weeks for this season
			weekDAOReference = (WeekDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.WEEK_DAO);
			Integer regularSeasonWeeks = weekDAOReference.getRegularSeasonTotalWeeks(seasonYear);
			
			//Next come up with the title row, which contains weeks.
			//Do this by getting the games for a week, then picking the Sunday Date.
			
			gameDAOReference = (GameDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.GAME_DAO);
			
			for (int weekNum = 1; weekNum <= regularSeasonWeeks; weekNum++)
			{	
				gameSel.setWeekNumber(weekNum);
				List<TblGame> gamesList = gameDAOReference.inquire(gameSel);
				
				for (int g = 0; g < gamesList.size(); g++)
				{
					TblGame game = gamesList.get(g);
					
					if (game.getGameDayOfWeek().equalsIgnoreCase("Sun"))
					{
						String month = game.getGameDateOnly().substring(0, 3);
						String day = game.getGameDateOnly().substring(4, 6);
						String opponentName = "Week " + weekNum + " " + month + "-" + day;
						TempOpponent tempOpp = new TempOpponent();
						tempOpp.setOpponentName(opponentName);
						opponents.add(tempOpp);
						break;
					}
				}
			}
			titleRow.setTeam("Team"); 
			titleRow.setOpponents(opponents); 
			tempScheduleList.add(titleRow);
						
			//Go get the games specific to each team.			
			
			for (int i = 0; i < teamList.size(); i++)
			{
				TblTeam team = teamList.get(i);
		
				log.debug("in ScheduleDAO, working on team " + team.getCteamCityAbbr());
				
				gameSel.setWeekNumber(null);
				gameSel.setTeamID(team.getIteamId());
			
				List<TblGame> gamesList = gameDAOReference.inquire(gameSel);
								
				int lastWeekNumber = 0;
				opponents = new ArrayList<TempOpponent>();
				
				for (int j = 0; j < gamesList.size(); j++)
				{					
					TblGame game = gamesList.get(j);
				
					int thisWeekNumber = game.getWeek().getIweekNumber();
					
					if (thisWeekNumber - lastWeekNumber > 1)
					{
						opponentString = "*BYE*";
						TempOpponent tempOpp = new TempOpponent();
						tempOpp.setOpponentName(opponentString);
						opponents.add(tempOpp);
					}
					
					if (game.getAwayTeam().getIteamId() == team.getIteamId())
					{
						opponentString = "@ " + game.getHomeTeam().getCteamCityAbbr().toLowerCase();
					}
					else
					{
						opponentString = game.getAwayTeam().getCteamCityAbbr().toUpperCase();
					}
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(game.getDgameDateTime());
					Integer timeHour = cal.get(Calendar.HOUR_OF_DAY);
					timeHour = timeHour - 12;
					
					opponentString = opponentString + timeHour.toString().substring(0, 1);						
					
					TempOpponent tempOpp = new TempOpponent();
					tempOpp.setOpponentName(opponentString);
					opponents.add(tempOpp);
										
					lastWeekNumber = thisWeekNumber;
				
				}
				
				TempScheduleRow teamRow = new TempScheduleRow();
				teamRow.setTeam(team.getCteamCityAbbr());
				teamRow.setOpponents(opponents);
				
				tempScheduleList.add(teamRow);				
				
			} 
			
		}
		catch (SystemException e)
		{
			throw new DAOException(e);
		}
				
		log.debug("final list size is = " + tempScheduleList.size());
		log.debug(methodName + "out");
		return tempScheduleList;	
	}
	
}
