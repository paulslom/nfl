package com.pas.nfl.action;

import java.util.ArrayList;
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
import com.pas.exception.SystemException;
import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.actionform.CommonGamesSelectionForm;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.constants.INFLMessageConstants;
import com.pas.nfl.dao.NFLDAOFactory;
import com.pas.nfl.dao.TeamDAO;
import com.pas.nfl.valueObject.TeamSelection;

public class CommonGamesSelectionAction extends NFLStandardAction
{
	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{
		log.debug("inside CommonGamesSelectionAction pre - process");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		cache.setGoToDBInd(req.getSession(), true);
		
		CommonGamesSelectionForm commonGamesSelectionForm =  (CommonGamesSelectionForm)form;
		
		List<TblTeam> commonGamesTeamList = new ArrayList<>();
		
		//no other way to get seasonyear into CommonGamesDAO class
		String seasonYearStr = cache.getSeasonYear(req.getSession());
		Integer seasonYear = Integer.parseInt(seasonYearStr);
		
		TblTeam dummyTeamforSeasonYear = new TblTeam();
		dummyTeamforSeasonYear.setIteamId(seasonYear);
		commonGamesTeamList.add(dummyTeamforSeasonYear);
				
		if (commonGamesSelectionForm.getAfcTeamsList() != null && commonGamesSelectionForm.getAfcTeamsList().size() > 0)
		{
			for (int i = 0; i < commonGamesSelectionForm.getAfcTeamsList().size(); i++) 
			{
				TblTeam selectedTeam = commonGamesSelectionForm.getAfcTeamsList().get(i);
				if (selectedTeam.isSelectedInd())
				{
					commonGamesTeamList.add(selectedTeam);
				}
			}
		}
		
		if (commonGamesSelectionForm.getNfcTeamsList() != null && commonGamesSelectionForm.getNfcTeamsList().size() > 0)
		{
			for (int i = 0; i < commonGamesSelectionForm.getNfcTeamsList().size(); i++) 
			{
				TblTeam selectedTeam = commonGamesSelectionForm.getNfcTeamsList().get(i);
				if (selectedTeam.isSelectedInd())
				{
					commonGamesTeamList.add(selectedTeam);
				}
			}
		}	
		
		for (int i = 0; i < commonGamesTeamList.size(); i++) 
		{
			TblTeam selectedTeam = commonGamesTeamList.get(i);
			log.debug("common Games selected team = " + selectedTeam.getFullTeamName());
		} 	
		
		cache.setObject("RequestObject", commonGamesTeamList, req.getSession());
		
		log.debug("exiting CommonGamesSelectionAction pre - process");		
				
		return true;
	}	
	
	@SuppressWarnings("unchecked")
	public void postprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation,
			ActionComposite ac) throws PresentationException,
			BusinessException, DAOException, PASSystemException
	{    	
		log.debug("inside CommonGamesSelectionAction postprocessAction");
	
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		
		List<String> outputList = (List<String>)cache.getObject("ResponseObject",req.getSession());		
		
		//Update ActionForm with Value Object returned from Business Layer
		
		if (outputList.size() < 1)
		{
			ActionMessages am = new ActionMessages();
			am.add(INFLMessageConstants.NO_RECORDS_FOUND,new ActionMessage(INFLMessageConstants.NO_RECORDS_FOUND));
			ac.setMessages(am);
			ac.setActionForward(mapping.findForward(IAppConstants.AF_FAILURE));		
		}
		else
		{
			req.getSession().setAttribute(INFLAppConstants.SESSION_COMMONGAMESRESULTLIST, outputList);			
			ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));		
		}					
				
		log.debug("exiting CommonGamesSelectionAction postprocessAction");				
	}

}
