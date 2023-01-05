package com.pas.nfl.action;

import java.util.List;
import java.util.Map;

import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.valueObject.Menu;

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

/**
 * Title: 		MenuAction
 * Description: User has chosen which investor to work on - build the menus specific for this investor
 * Copyright: 	Copyright (c) 2006
 */
public class MenuAction extends NFLStandardAction
{
	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{
		log.debug("inside MenuAction pre - process");
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		cache.setGoToDBInd(req.getSession(), true);
		String seasonYear = cache.getSeasonYear(req.getSession());
		cache.setObject("RequestObject", seasonYear, req.getSession());		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public void postprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation,
			ActionComposite ac) throws PresentationException,
			BusinessException, DAOException, PASSystemException
	{    	
		log.debug("inside MenuAction postprocessAction");
	
		//get Value object from the request - in this case it is a list of menu components
		ICacheManager cache = CacheManagerFactory.getCacheManager();
		List<Map> menuList = (List)cache.getObject("ResponseObject", req.getSession());		
		
		Map<String, List<Menu>> menuMap = menuList.get(0); //should only be one entry here
  			
		req.getSession().setAttribute(INFLAppConstants.MENU_SCORES_BY_WEEK, menuMap.get(INFLAppConstants.MENU_SCORES_BY_WEEK)); 
		req.getSession().setAttribute(INFLAppConstants.MENU_SCORES_BY_TEAM, menuMap.get(INFLAppConstants.MENU_SCORES_BY_TEAM)); 
		req.getSession().setAttribute(INFLAppConstants.MENU_REPORTS, menuMap.get(INFLAppConstants.MENU_REPORTS)); 
		req.getSession().setAttribute(INFLAppConstants.MENU_GAMES, menuMap.get(INFLAppConstants.MENU_GAMES)); 
		req.getSession().setAttribute(INFLAppConstants.MENU_PLAYOFFS, menuMap.get(INFLAppConstants.MENU_PLAYOFFS)); 
		req.getSession().setAttribute(INFLAppConstants.MENU_MISC, menuMap.get(INFLAppConstants.MENU_MISC)); 		
		
		ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));
		
		log.debug("exiting MenuAction postprocessAction");		
		
	}
}
