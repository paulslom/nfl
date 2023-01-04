package com.pas.nfl.action;

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

public class CommonGamesResultAction extends NFLStandardAction
{
	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{
		log.debug("inside CommonGamesResultAction pre - process");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		cache.setGoToDBInd(req.getSession(), false);
		
		log.debug("exiting CommonGamesResultAction pre - process");		
				
		return true;
	}	
	
	@SuppressWarnings("unchecked")
	public void postprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation,
			ActionComposite ac) throws PresentationException,
			BusinessException, DAOException, PASSystemException
	{    	
		log.debug("inside CommonGamesResultAction postprocessAction");
		ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));		
		log.debug("exiting CommonGamesResultAction postprocessAction");				
	}

}
