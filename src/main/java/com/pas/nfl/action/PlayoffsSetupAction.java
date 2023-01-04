package com.pas.nfl.action;

import java.util.ArrayList;
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
import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.actionform.PlayoffsSetupForm;
import com.pas.nfl.actionform.ScoresListForm;

public class PlayoffsSetupAction extends NFLStandardAction
{
	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{
		log.debug("inside PlayoffsSetupAction pre - process");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		
		String reqParm = req.getParameter("operation");
		//do not do anything when cancelling
		
		if (reqParm.equalsIgnoreCase(INFLAppConstants.BUTTON_CANCELUPDATE))
		{	
			cache.setGoToDBInd(req.getSession(), false);
			cache.setObject("RequestObject", new String(), req.getSession());	
		}
		else
		{
			PlayoffsSetupForm playoffsSetupForm = (PlayoffsSetupForm)form;
			List<TblTeam> playoffTeamsFromForm = new ArrayList<>();
			
			//no other way to get seasonid into PlayoffsDAO class
			Integer seasonID = cache.getSeasonID(req.getSession());
			
			TblTeam dummyTeamforSeason = new TblTeam();
			dummyTeamforSeason.setIteamId(seasonID);
			dummyTeamforSeason.setIdivisionId(playoffsSetupForm.getTotalByesByConf());
			playoffTeamsFromForm.add(dummyTeamforSeason);		
			
			for (int i = 0; i < playoffsSetupForm.getAfcTeamsList().size(); i++) 
			{
				TblTeam selectedTeam = playoffsSetupForm.getAfcTeamsList().get(i);
				if (selectedTeam.isSelectedInd())
				{
					playoffTeamsFromForm.add(selectedTeam);
				}
			}	
			for (int i = 0; i < playoffsSetupForm.getNfcTeamsList().size(); i++) 
			{
				TblTeam selectedTeam = playoffsSetupForm.getNfcTeamsList().get(i);
				if (selectedTeam.isSelectedInd())
				{
					playoffTeamsFromForm.add(selectedTeam);
				}
			}	
			cache.setGoToDBInd(req.getSession(), true);			
			cache.setObject("RequestObject", playoffTeamsFromForm, req.getSession());
		}
				
		return true;
	}	
	@SuppressWarnings("unchecked")
	public void postprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation,
			ActionComposite ac) throws PresentationException,
			BusinessException, DAOException, PASSystemException
	{    	
		log.debug("inside PlayoffsSetupAction postprocessAction");
	
		ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));		
				
		log.debug("exiting PlayoffsSetupAction postprocessAction");
				
	}

}
