package com.pas.nfl.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.pas.action.ActionComposite;
import com.pas.cache.CacheManagerFactory;
import com.pas.cache.ICacheManager;
import com.pas.constants.IAppConstants;
import com.pas.exception.BusinessException;
import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;
import com.pas.exception.PresentationException;
import com.pas.exception.SystemException;
import com.pas.nfl.DBObjects.TblGame;
import com.pas.nfl.DBObjects.TblGameType;
import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.DBObjects.TblWeek;
import com.pas.nfl.actionform.GameUpdateForm;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.dao.GameTypeDAO;
import com.pas.nfl.dao.NFLDAOFactory;
import com.pas.nfl.dao.TeamDAO;
import com.pas.nfl.dao.WeekDAO;
import com.pas.nfl.valueObject.TeamSelection;
import com.pas.valueObject.DropDownBean;

public class GameShowUpdateFormAction extends NFLStandardAction
{
	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{
		log.debug("inside GameShowUpdateFormAction pre - process");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		Integer gameID = new Integer(0);
        		
		String gameShowParm = req.getParameter("gameShowParm");
				
		if (gameShowParm.equalsIgnoreCase(IAppConstants.ADD))
			cache.setGoToDBInd(req.getSession(), false);
		else
		{
		   String parmGameID = req.getParameter("gameID");
		   gameID = Integer.valueOf(parmGameID);
		   cache.setGoToDBInd(req.getSession(), true);
		}   
						
		cache.setObject("RequestObject", gameID, req.getSession());		
		
		log.debug("exiting GameShowUpdateFormAction pre - process");
		
				
		return true;
	}	
	@SuppressWarnings("unchecked")
	public void postprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation,
			ActionComposite ac) throws PresentationException,
			BusinessException, DAOException, PASSystemException
	{    	
		log.debug("inside GameShowUpdateFormAction postprocessAction");
	
		String gameShowParm = req.getParameter("gameShowParm");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
				
		List<TblGame> gameList = null;
		
		if (!(gameShowParm.equalsIgnoreCase(IAppConstants.ADD)))
		   gameList = (List<TblGame>)cache.getObject("ResponseObject",req.getSession());		
		
		GameUpdateForm gameForm = (GameUpdateForm)form; 
				
		if (gameShowParm.equalsIgnoreCase(IAppConstants.ADD))
		{
			gameForm.initialize();
		}
		else //not an add	
		{	
		   	TblGame game = gameList.get(0);			
			
			gameForm.setGameID(game.getIgameId());			
			
			if (game.getDgameDateTime() == null)
				gameForm.setGameDateTime(null);
			else
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
				gameForm.setGameDateTime(sdf.format(game.getDgameDateTime()));
			}
			
			if (game.getWeek().getIweekNumber() == null)
			{
				gameForm.setWeekID(null);
				gameForm.setWeekNumber(null);
			}
			else
			{
			   gameForm.setWeekID(game.getIweekId());	
			   gameForm.setWeekNumber(game.getWeek().getIweekNumber());	
			}
			
			if (game.getIgameTypeId() == null)
			{
				gameForm.setGameTypeID(null);
				gameForm.setGameTypeDesc(null);
			}
			else if (game.getIgameTypeId() == null)
			{
				gameForm.setGameTypeID(null);
				gameForm.setGameTypeDesc(null);
			}
			else
			{	
				gameForm.setGameTypeID(game.getIgameTypeId());
				gameForm.setGameTypeDesc(game.getGameType().getSgameTypeDesc());
			}
			
			if (game.getIawayTeamID() == null)
			{
				gameForm.setAwayTeamID(null);
				gameForm.setAwayTeamDesc(null);
			}
			else if (game.getIawayTeamID() == null)
			{
				gameForm.setAwayTeamID(null);
				gameForm.setAwayTeamDesc(null);
			}
			else
			{	
				gameForm.setAwayTeamID(game.getIawayTeamID());
				gameForm.setAwayTeamDesc(game.getAwayTeam().getVteamCity() + " " + game.getAwayTeam().getVteamNickname());
			}
			
			if (game.getIhomeTeamID() == null)
			{
				gameForm.setHomeTeamID(null);
				gameForm.setHomeTeamDesc(null);
			}
			else if (game.getIhomeTeamID() == null)
			{
				gameForm.setHomeTeamID(null);
				gameForm.setHomeTeamDesc(null);
			}
			else
			{	
				gameForm.setHomeTeamID(game.getIhomeTeamID());
				gameForm.setHomeTeamDesc(game.getHomeTeam().getVteamCity() + " " + game.getHomeTeam().getVteamNickname());
			}
			
		}	
		
		TeamDAO teamDAOReference;
		WeekDAO weekDAOReference;
		GameTypeDAO gameTypeDAOReference;
		
		try
		{
			teamDAOReference = (TeamDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.TEAM_DAO);
			TeamSelection teamSel = new TeamSelection();
			teamSel.setSeasonYear(cache.getSeasonYear(req.getSession()));
			teamSel.setTeamSelectionType("cityOrder");
			List<TblTeam> teamList = teamDAOReference.inquire(teamSel);
			List<DropDownBean> teamDropdownList = new ArrayList<DropDownBean>();
			for (int i = 0; i < teamList.size(); i++)
			{
				TblTeam team = teamList.get(i);
				DropDownBean ddBean = new DropDownBean();
				ddBean.setId(team.getIteamId().toString());
				ddBean.setDescription(team.getVteamCity() + " " + team.getVteamNickname());
				teamDropdownList.add(ddBean);
			}
			req.getSession().setAttribute(INFLAppConstants.SESSION_TEAMSLIST, teamDropdownList);
			
			weekDAOReference = (WeekDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.WEEK_DAO);		
			List<TblWeek> weekList = weekDAOReference.inquire(cache.getSeasonYear(req.getSession()));
			List<DropDownBean> weekDropdownList = new ArrayList<DropDownBean>();
			for (int i = 0; i < weekList.size(); i++)
			{
				TblWeek week = weekList.get(i);
				DropDownBean ddBean = new DropDownBean();
				ddBean.setId(week.getIweekId().toString());
				ddBean.setDescription(week.getIweekNumber().toString());
				weekDropdownList.add(ddBean);
			}
			req.getSession().setAttribute(INFLAppConstants.SESSION_WEEKSLIST, weekDropdownList);
			
			gameTypeDAOReference = (GameTypeDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.GAMETYPE_DAO);		
			List<TblGameType> gameTypeList = gameTypeDAOReference.inquire(new String(""));
			List<DropDownBean> gameTypeDropdownList = new ArrayList<DropDownBean>();
			for (int i = 0; i < gameTypeList.size(); i++)
			{
				TblGameType gameType = gameTypeList.get(i);
				DropDownBean ddBean = new DropDownBean();
				ddBean.setId(gameType.getIgameTypeId().toString());
				ddBean.setDescription(gameType.getSgameTypeDesc());
				gameTypeDropdownList.add(ddBean);
			}
			req.getSession().setAttribute(INFLAppConstants.SESSION_GAMETYPESLIST, gameTypeDropdownList);
			
		} 
		catch (SystemException e) 
		{
			throw new DAOException(e);
		}
		req.getSession().setAttribute("gameShowParm",gameShowParm);
		ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));		
				
		log.debug("exiting AccountListAction postprocessAction");
				
	}

}
