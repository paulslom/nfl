package com.pas.nfl.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.pas.nfl.dao.GameDAO;
import com.pas.nfl.dao.GameTypeDAO;
import com.pas.nfl.dao.NFLDAOFactory;
import com.pas.nfl.dao.TeamDAO;
import com.pas.nfl.dao.WeekDAO;

public class GameUpdateAction extends NFLStandardAction
{
	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{
		log.debug("inside GameUpdateAction pre - process");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		
		TblGame game = new TblGame();
		
		String reqParm = req.getParameter("operation");
		
		//go to DAO but do not do anything when cancelling an update or delete, or returning from an inquire
		if (reqParm.equalsIgnoreCase(INFLAppConstants.BUTTON_CANCELADD)
		||  reqParm.equalsIgnoreCase(INFLAppConstants.BUTTON_CANCELUPDATE)
		||  reqParm.equalsIgnoreCase(INFLAppConstants.BUTTON_CANCELDELETE)
		||  reqParm.equalsIgnoreCase(INFLAppConstants.BUTTON_RETURN))
			cache.setGoToDBInd(req.getSession(), false);
		
		if (operation == IAppConstants.DELETE_ACTION
		||	operation == IAppConstants.UPDATE_ACTION)
		{
			String gameID = req.getParameter("gameID"); //hidden field on jsp
			GameDAO gameDAOReference;
			
			try
			{
				gameDAOReference = (GameDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.GAME_DAO);
				List<TblGame> gameList = gameDAOReference.inquire(new Integer(gameID));
				game = gameList.get(0);			
			}
			catch (SystemException e1)
			{
				log.error("SystemException encountered: " + e1.getMessage());
				e1.printStackTrace();
				throw new PASSystemException(e1);
			}
		}
		
		if (operation == IAppConstants.ADD_ACTION
		||  operation == IAppConstants.UPDATE_ACTION)
		{
			GameUpdateForm gameForm = (GameUpdateForm)form;
		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
			java.util.Date date;
			try
			{
				date = sdf.parse(gameForm.getGameDateTime());
				java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
				game.setDgameDateTime(timestamp);
			}
			catch (ParseException e)
			{	
				log.error("error parsing Game Date in GameUpdateAction pre-process");
				e.printStackTrace();
				throw new PresentationException("error parsing Game date in GameUpdateAction pre-process");				
			}	
						
			TeamDAO teamDAOReference;
			WeekDAO weekDAOReference;
			GameTypeDAO gameTypeDAOReference;
			
			try
			{
				teamDAOReference = (TeamDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.TEAM_DAO);		
				List<TblTeam> awayteamList = teamDAOReference.inquire(gameForm.getAwayTeamID());
				TblTeam awayTeam = awayteamList.get(0);
				game.setIawayTeamID(awayTeam.getIteamId());
				
				List<TblTeam> hometeamList = teamDAOReference.inquire(gameForm.getHomeTeamID());
				TblTeam homeTeam = hometeamList.get(0);
				game.setIhomeTeamID(homeTeam.getIteamId());
				
				weekDAOReference = (WeekDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.WEEK_DAO);		
				List<TblWeek> weekList = weekDAOReference.inquire(gameForm.getWeekID());
				TblWeek week = weekList.get(0);
				game.setIweekId(week.getIweekId());
				
				gameTypeDAOReference = (GameTypeDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.GAMETYPE_DAO);		
				List<TblGameType> gameTypeList = gameTypeDAOReference.inquire(gameForm.getGameTypeID());
				TblGameType gameType = gameTypeList.get(0);
				game.setIgameTypeId(gameType.getIgameTypeId());
				
			} 
			catch (SystemException e) 
			{
				throw new DAOException(e);
			}
			
			cache.setGoToDBInd(req.getSession(), true);
									
		}
		
		cache.setObject("RequestObject", game, req.getSession());	
					
		return true;
	}	
	@SuppressWarnings("unchecked")
	public void postprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation,
			ActionComposite ac) throws PresentationException,
			BusinessException, DAOException, PASSystemException
	{    	
		log.debug("inside GameUpdateAction postprocessAction");
	
		req.getSession().removeAttribute("gameShowParm");
		
		ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));		
				
		log.debug("exiting GameUpdateAction postprocessAction");
				
	}

}
