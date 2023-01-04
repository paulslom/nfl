package com.pas.nfl.action;

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
import com.pas.nfl.DBObjects.TblGame;
import com.pas.nfl.actionform.ScoresListForm;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.valueObject.GameSelection;

public class ScoresListAction extends NFLStandardAction
{
	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{
		log.debug("inside ScoresListAction pre - process");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		String seasonYear = cache.getSeasonYear(req.getSession());
		GameSelection gameSelection = new GameSelection();
		gameSelection.setSeasonYear(new Integer(seasonYear));
		String teamID = req.getParameter("teamID");
		if (teamID == null)
		{
			String weekNumber = req.getParameter("weekNumber");
			req.getSession().setAttribute(INFLAppConstants.SESSION_CURRENTWEEK, weekNumber);
			gameSelection.setWeekNumber(new Integer(weekNumber));
		}
		else
			gameSelection.setTeamID(new Integer(teamID));
		cache.setGoToDBInd(req.getSession(), true);
		cache.setObject("RequestObject", gameSelection, req.getSession());
				
		return true;
	}	
	@SuppressWarnings("unchecked")
	public void postprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation,
			ActionComposite ac) throws PresentationException,
			BusinessException, DAOException, PASSystemException
	{    	
		log.debug("inside ScoresListAction postprocessAction");
	
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		List<TblGame> gameList = (List<TblGame>)cache.getObject("ResponseObject",req.getSession());		
		
		ScoresListForm scoresListForm = (ScoresListForm)form; 
		
		scoresListForm.setScoresList(gameList); 
		
		ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));		
				
		log.debug("exiting ScoresListAction postprocessAction");
				
	}

}
