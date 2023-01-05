package com.pas.nfl.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;
import com.pas.exception.SystemException;
import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.DBObjects.TblWeek;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.valueObject.Menu;
import com.pas.nfl.valueObject.TeamSelection;
/**
 * Title: 		MenuDAO
 * Project: 	Slomkowski Financial Application
 * Description: Menu DAO extends BaseDBDAO. 
 * Copyright: 	Copyright (c) 2006
 */
public class MenuDAO extends BaseDBDAO
{ 
 
  private static final String CONTEXT_ROOT = "/NFL";

  private static final MenuDAO currentInstance = new MenuDAO();

    private MenuDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return LoginDAO
     */
    public static MenuDAO getDAOInstance() {
        return currentInstance;
    }
        
	@SuppressWarnings("unchecked")
	public List inquire(Object info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
		
		log.debug("entering MenuDAO inquire");		
		
		String seasonYear = (String)info;
				
		List<Menu> scoresByWeekList = menuStrutsCreateScoresByWeek(seasonYear);		
		List<Menu> scoresByTeamList = menuStrutsCreateScoresByTeam(seasonYear);		
 		List<Menu> reportsList = menuStrutsCreateReports();
 		List<Menu> gamesList = menuStrutsCreateGames();
 		List<Menu> playoffsList = menuStrutsCreatePlayoffs();
 		List<Menu> miscList = menuStrutsCreateMisc();
 		
 		List<Map> menuList = new ArrayList<Map>();
  		
  		Map<String, List<Menu>> menuMap = new HashMap<>();
  		
  		menuMap.put(INFLAppConstants.MENU_SCORES_BY_WEEK, scoresByWeekList);
  		menuMap.put(INFLAppConstants.MENU_SCORES_BY_TEAM, scoresByTeamList);
  		menuMap.put(INFLAppConstants.MENU_REPORTS, reportsList);
  		menuMap.put(INFLAppConstants.MENU_GAMES, gamesList);
  		menuMap.put(INFLAppConstants.MENU_PLAYOFFS, playoffsList);
  		menuMap.put(INFLAppConstants.MENU_MISC, miscList);
  		
  		menuList.add(menuMap);
		
		log.debug("final list size is = " + menuList.size());
		log.debug(methodName + "out");
		
		return menuList;
	}
	
	@SuppressWarnings("unchecked")
	private List<Menu> menuStrutsCreateScoresByWeek(String seasonYear) throws DAOException
	{			
		List<Menu> mList = new ArrayList<Menu>();
		
		Menu menuDetail = new Menu();
				
		WeekDAO weekDAOReference;
		
		try
		{
			weekDAOReference = (WeekDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.WEEK_DAO);
		
			List<TblWeek> weekList = weekDAOReference.inquire(seasonYear);			
			log.debug("looping through list of weeks to build menu items for scores");
		
			for (int i = 0; i < weekList.size(); i++)
			{
				TblWeek week = weekList.get(i);
					
				menuDetail = new Menu();
				menuDetail.setMenuName("ScoresWeek" + week.getIweekNumber());
				menuDetail.setMenuTitle("Week " + week.getIweekNumber());
				menuDetail.setMenuLocation(CONTEXT_ROOT + "/ScoresListAction.do?&operation=inquire&weekNumber=" + week.getIweekNumber());		
				menuDetail.setMenuOrder(1);
				menuDetail.setMenuSubOrder(4+i);
				mList.add(menuDetail);
			
				log.debug("added ScoresWeek submenu: Week Number = " + week.getIweekNumber());
			}
		} 
		catch (SystemException e) 
		{
			throw new DAOException(e);
		}		

		return mList;		
	}
	
	@SuppressWarnings("unchecked")
	private List<Menu> menuStrutsCreateScoresByTeam(String seasonYear) throws DAOException
	{
		List<Menu> mList = new ArrayList<Menu>();
		
		Menu menuDetail = new Menu();
				
		String currentParentDivision = "";
		
		TeamDAO teamDAOReference;
		
		try
		{
			teamDAOReference = (TeamDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.TEAM_DAO);
		
			TeamSelection teamSel = new TeamSelection();
			teamSel.setSeasonYear(seasonYear);
			teamSel.setTeamSelectionType("byDivision");
			List<TblTeam> teamList = teamDAOReference.inquire(teamSel);			
			log.debug("looping through list of teams to build menu items for scores");
		
			for (int i = 0; i < teamList.size(); i++)
			{
				TblTeam team = teamList.get(i);
							
				menuDetail = new Menu();
				menuDetail.setMenuParentName("ScoresDivision" + team.getIdivisionId());
				menuDetail.setMenuName("ScoresTeam" + team.getIteamId());
				menuDetail.setMenuTitle(team.getVteamCity() + " " + team.getVteamNickname());
				menuDetail.setMenuLocation(CONTEXT_ROOT + "/ScoresListAction.do?&operation=inquire&teamID=" + team.getIteamId());		
				menuDetail.setMenuOrder(1);
				menuDetail.setMenuSubOrder(110+i);
				mList.add(menuDetail);
			
				log.debug("added ScoresWeek submenu: Team = " + team.getVteamNickname());
			}
		} 
		catch (SystemException e) 
		{
			throw new DAOException(e);
		}
		
		return mList;
	}
	
	@SuppressWarnings("unchecked")
	private List<Menu> menuStrutsCreateReports()
	{
		List<Menu> mList = new ArrayList<Menu>();
		
		Menu menuDetail = new Menu();
		
		menuDetail.setMenuParentName("Reports");
		menuDetail.setMenuName("ReportsStandings");
		menuDetail.setMenuTitle("Standings");
		menuDetail.setMenuLocation(CONTEXT_ROOT + "/ReportStandingsAction.do?operation=inquire");		
		menuDetail.setMenuOrder(2);
		menuDetail.setMenuSubOrder(1);
		mList.add(menuDetail);	
			
		menuDetail = new Menu();
		menuDetail.setMenuParentName("Reports");
		menuDetail.setMenuName("ReportsDraft");
		menuDetail.setMenuTitle("Draft");
		menuDetail.setMenuLocation(CONTEXT_ROOT + "/ReportDraftAction.do?operation=inquire");		
		menuDetail.setMenuOrder(2);
		menuDetail.setMenuSubOrder(2);
		mList.add(menuDetail);	
		
		menuDetail = new Menu();
		menuDetail.setMenuParentName("Reports");
		menuDetail.setMenuName("ReportsSchedule");
		menuDetail.setMenuTitle("Schedule");
		menuDetail.setMenuLocation(CONTEXT_ROOT + "/ReportScheduleAction.do?operation=inquire");		
		menuDetail.setMenuOrder(2);
		menuDetail.setMenuSubOrder(3);
		mList.add(menuDetail);	
		
		menuDetail = new Menu();
		menuDetail.setMenuParentName("Reports");
		menuDetail.setMenuName("ReportsCommonGames");
		menuDetail.setMenuTitle("Common Games");
		menuDetail.setMenuLocation(CONTEXT_ROOT + "/CommonGamesShowFormAction.do?operation=inquire");		
		menuDetail.setMenuOrder(2);
		menuDetail.setMenuSubOrder(4);
		mList.add(menuDetail);		
		
		return mList;
	}	
	
	@SuppressWarnings("unchecked")
	private List<Menu> menuStrutsCreateGames()
	{
		List<Menu> mList = new ArrayList<Menu>();
		
		Menu menuDetail = new Menu();
		
		menuDetail.setMenuParentName("Games");
		menuDetail.setMenuName("GamesVCD");
		menuDetail.setMenuTitle("View-Chg-Del");
		menuDetail.setMenuLocation(CONTEXT_ROOT + "/GamesListAction.do?&operation=inquire");
		menuDetail.setMenuOrder(3);
		menuDetail.setMenuSubOrder(1);
		mList.add(menuDetail);	
		
		menuDetail = new Menu();
		menuDetail.setMenuParentName("Games");
		menuDetail.setMenuName("GameAdd");
		menuDetail.setMenuTitle("Add");
		menuDetail.setMenuLocation(CONTEXT_ROOT + "/GameShowUpdateFormAction.do?&operation=inquire&gameShowParm=add");
		menuDetail.setMenuOrder(3);
		menuDetail.setMenuSubOrder(2);
		mList.add(menuDetail);	
				
		return mList;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private List<Menu> menuStrutsCreatePlayoffs()
	{
		List<Menu> mList = new ArrayList<Menu>();
		
		Menu menuDetail = new Menu();
		
		menuDetail.setMenuParentName("Playoffs");
		menuDetail.setMenuName("PlayoffsInquire");
		menuDetail.setMenuTitle("Brackets");
		menuDetail.setMenuLocation(CONTEXT_ROOT + "/PlayoffBracketsAction.do?&operation=inquire");
		menuDetail.setMenuOrder(4);
		menuDetail.setMenuSubOrder(1);
		mList.add(menuDetail);	
		
		menuDetail = new Menu();
		menuDetail.setMenuParentName("Playoffs");
		menuDetail.setMenuName("PlayoffsSetup");
		menuDetail.setMenuTitle("Add Setup");
		menuDetail.setMenuLocation(CONTEXT_ROOT + "/PlayoffsSetupShowFormAction.do?&operation=inquire");
		menuDetail.setMenuOrder(4);
		menuDetail.setMenuSubOrder(2);
		mList.add(menuDetail);	
		
		return mList;
	}	
		
	private List<Menu> menuStrutsCreateMisc()
	{
		List<Menu> mList = new ArrayList<Menu>();
		
		Menu menuDetail = new Menu();
		
		//top-level menu
		menuDetail.setMenuName("Miscellaneous");
		menuDetail.setMenuTitle("Miscellaneous");
		menuDetail.setMenuOrder(5);
		menuDetail.setMenuSubOrder(0);		
		mList.add(menuDetail);
		
		//submenus
	
		menuDetail = new Menu();
		menuDetail.setMenuParentName("Miscellaneous");
		menuDetail.setMenuName("Tiebreaker");
		menuDetail.setMenuTitle("TieBreakers");
		menuDetail.setMenuLocation("http://www.nfl.com/standings/tiebreakingprocedures");
		menuDetail.setMenuOrder(5);
		menuDetail.setMenuSubOrder(1);
		mList.add(menuDetail);
		
		menuDetail = new Menu();
		menuDetail.setMenuParentName("Miscellaneous");
		menuDetail.setMenuName("DraftOrderRules");
		menuDetail.setMenuTitle("Draft Order Rules");
		menuDetail.setMenuLocation(CONTEXT_ROOT + "/DraftOrderRulesShowFormAction.do?operation=inquire");
		menuDetail.setMenuOrder(5);
		menuDetail.setMenuSubOrder(2);
		mList.add(menuDetail);
		
		menuDetail = new Menu();
		menuDetail.setMenuParentName("Miscellaneous");
		menuDetail.setMenuName("SeasonClone");
		menuDetail.setMenuTitle("Clone last season");
		menuDetail.setMenuLocation(CONTEXT_ROOT + "/SeasonCloneAction.do?operation=add");
		menuDetail.setMenuOrder(5);
		menuDetail.setMenuSubOrder(2);
		mList.add(menuDetail);
		
		return mList;
	}	
	

}
