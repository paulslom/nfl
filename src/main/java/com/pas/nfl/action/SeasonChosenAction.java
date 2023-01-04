package com.pas.nfl.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.pas.action.ActionComposite;
import com.pas.cache.CacheManagerFactory;
import com.pas.cache.ICacheManager;
import com.pas.constants.IAppConstants;
import com.pas.exception.BusinessException;
import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;
import com.pas.exception.PresentationException;
import com.pas.nfl.DBObjects.TblSeason;
import com.pas.nfl.constants.INFLMessageConstants;
import com.pas.nfl.valueObject.SeasonSelection;

public class SeasonChosenAction extends NFLStandardAction
{
	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{
		log.debug("inside SeasonChosenAction pre - process");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		String seasonChosenID = req.getParameter("seasonChosenID");
		SeasonSelection seasonSelection = new SeasonSelection();
		seasonSelection.setSeasonID(Integer.parseInt(seasonChosenID));
		cache.setGoToDBInd(req.getSession(), true);
		cache.setObject("RequestObject", seasonSelection, req.getSession());
				
		return true;
	}	
	@SuppressWarnings("unchecked")
	public void postprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation,
			ActionComposite ac) throws PresentationException,
			BusinessException, DAOException, PASSystemException
	{    	
		log.debug("inside SeasonChosenAction postprocessAction");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();		
		
		List<TblSeason> seasonList = (List<TblSeason>)cache.getObject("ResponseObject",req.getSession());		
		
		//Update ActionForm with Value Object returned from Business Layer
		
		if (seasonList.size() < 1)
		{
			ActionMessages am = new ActionMessages();
			am.add(INFLMessageConstants.NO_RECORDS_FOUND,new ActionMessage(INFLMessageConstants.NO_RECORDS_FOUND));
			ac.setMessages(am);
			ac.setActionForward(mapping.findForward(IAppConstants.AF_FAILURE));		
		}
		else
		{
			TblSeason season = seasonList.get(0);
			Integer seasonID = season.getIseasonId();
			cache.setSeasonID(req.getSession(),seasonID);
			String seasonYear = season.getCyear();
			cache.setSeasonYear(req.getSession(),seasonYear);

			ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));		
		}	
		
		log.debug("exiting SeasonChosenAction postprocessAction");
				
	}

}
