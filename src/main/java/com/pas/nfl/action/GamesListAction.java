package com.pas.nfl.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.pas.action.ActionComposite;
import com.pas.cache.CacheManagerFactory;
import com.pas.cache.ICacheManager;
import com.pas.constants.IAppConstants;
import com.pas.exception.BusinessException;
import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;
import com.pas.exception.PresentationException;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.constants.INFLMessageConstants;
import com.pas.nfl.valueObject.GameSelection;
import com.pas.nfl.DBObjects.TblGame;

public class GamesListAction extends NFLStandardAction
{
	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{
		log.debug("inside GamesListAction pre - process");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		String seasonYear = cache.getSeasonYear(req.getSession());
		GameSelection gameSelection = new GameSelection();
		gameSelection.setSeasonYear(new Integer(seasonYear));
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
		log.debug("inside AccountListAction postprocessAction");
	
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		List<TblGame> gameList = (List<TblGame>)cache.getObject("ResponseObject",req.getSession());		
		
		//Update ActionForm with Value Object returned from Business Layer
		
		if (gameList.size() < 1)
		{
			ActionMessages am = new ActionMessages();
			am.add(INFLMessageConstants.NO_RECORDS_FOUND,new ActionMessage(INFLMessageConstants.NO_RECORDS_FOUND));
			ac.setMessages(am);
			ac.setActionForward(mapping.findForward(IAppConstants.AF_FAILURE));		
		}
					
		req.getSession().setAttribute(INFLAppConstants.SESSION_GAMESLIST, gameList);	
			
		ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));		
				
		log.debug("exiting AccountListAction postprocessAction");
				
	}

}
