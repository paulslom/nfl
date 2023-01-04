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
import com.pas.exception.SystemException;
import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.actionform.CommonGamesSelectionForm;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.dao.NFLDAOFactory;
import com.pas.nfl.dao.TeamDAO;
import com.pas.nfl.valueObject.TeamSelection;

public class CommonGamesShowFormAction extends NFLStandardAction
{
	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{
		log.debug("inside CommonGamesShowFormAction pre - process");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		cache.setGoToDBInd(req.getSession(), false);
		
		log.debug("exiting CommonGamesShowFormAction pre - process");		
				
		return true;
	}	
	
	@SuppressWarnings("unchecked")
	public void postprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation,
			ActionComposite ac) throws PresentationException,
			BusinessException, DAOException, PASSystemException
	{    	
		log.debug("inside CommonGamesShowFormAction postprocessAction");
	
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		CommonGamesSelectionForm commonGamesSelectionForm = (CommonGamesSelectionForm)form;
		
		TeamDAO teamDAOReference;
		
		try
		{
			teamDAOReference = (TeamDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.TEAM_DAO);
			TeamSelection teamSel = new TeamSelection();
			teamSel.setSeasonYear(cache.getSeasonYear(req.getSession()));
			
			teamSel.setTeamSelectionType("afcTeams");
			teamSel.setConferenceID(INFLAppConstants.AFC);
			List<TblTeam> afcTeamList = teamDAOReference.inquire(teamSel);
			commonGamesSelectionForm.setAfcTeamsList(afcTeamList);
			//req.getSession().setAttribute(INFLAppConstants.SESSION_AFCTEAMSLIST, afcTeamList);
			
			teamSel.setTeamSelectionType("nfcTeams");
			teamSel.setConferenceID(INFLAppConstants.NFC);
			List<TblTeam> nfcTeamList = teamDAOReference.inquire(teamSel);
			commonGamesSelectionForm.setNfcTeamsList(nfcTeamList);
			//req.getSession().setAttribute(INFLAppConstants.SESSION_NFCTEAMSLIST, nfcTeamList);
		} 
		catch (SystemException e) 
		{
			throw new DAOException(e);
		}
		ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));		
				
		log.debug("exiting CommonGamesShowFormAction postprocessAction");				
	}

}
