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

public class ScoresUpdateAction extends NFLStandardAction
{
	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{
		log.debug("inside ScoresUpdateAction pre - process");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		TblGame game = new TblGame();
			
		String reqParm = req.getParameter("operation");
		
		//go to DAO but do not do anything when cancelling an add
		if (reqParm.equalsIgnoreCase(INFLAppConstants.BUTTON_CANCELUPDATE))
		{	
			cache.setGoToDBInd(req.getSession(), false);
			cache.setObject("RequestObject", game, req.getSession());	
		}
		else
		{
			ScoresListForm scoresListForm = (ScoresListForm)form;
			List<TblGame> scoresListFromForm = scoresListForm.getScoresList();
			cache.setGoToDBInd(req.getSession(), true);			
			cache.setObject("RequestObject", scoresListFromForm, req.getSession());
		}
	
		return true;
	
	}	
	
	public void postprocessAction(ActionMapping mapping, ActionForm form,
		HttpServletRequest req, HttpServletResponse res, int operation,
		ActionComposite ac) throws PresentationException,
		BusinessException, DAOException, PASSystemException
	{    	
		log.debug("inside ScoresUpdateAction postprocessAction");
	
		ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));		
	
		log.debug("exiting ScoresUpdateAction postprocessAction");
			
	}

}
