package com.pas.nfl.action;


import java.util.List;

import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.valueObject.Menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

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
		List<Menu> menuList = (List)cache.getObject("ResponseObject", req.getSession());
		
		MenuRepository sfRepository = new MenuRepository();
		
		//Get the repository from the application scope - and copy the
		//DisplayerMappings from it.
		
		MenuRepository defaultRepository = (MenuRepository) req.getSession().getServletContext().getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
		sfRepository.setDisplayers(defaultRepository.getDisplayers());

		for (int i = 0; i<menuList.size(); i++)         
		{
			Menu menuEntryFromList = menuList.get(i);
			
		    MenuComponent menuComponent = new MenuComponent();
		   	
		    String name = menuEntryFromList.getMenuName(); 
		    menuComponent.setName(name);
		    
		    String parent = menuEntryFromList.getMenuParentName();
		    
		    log.debug("menu is: " + name + ", parent is: " + parent);
		    
		    if (parent != null)
		    {
		        MenuComponent parentMenu = sfRepository.getMenu(parent);
		        if (parentMenu == null)
		        {
		            log.debug("parentMenu '" + parent + "' doesn't exist!");
		            
		            // create a temporary parentMenu
		            parentMenu = new MenuComponent();
		            parentMenu.setName(parent);
		            sfRepository.addMenu(parentMenu);
		        }

		        menuComponent.setParent(parentMenu);
		    }
		    String title = menuEntryFromList.getMenuTitle();
		    menuComponent.setTitle(title);
		    
		    String location = menuEntryFromList.getMenuLocation();
		    menuComponent.setLocation(location);
		    
		    sfRepository.addMenu(menuComponent);
		}
		req.getSession().setAttribute(INFLAppConstants.SF_MENUREPOSITORY, sfRepository); 
		
		ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));
		
		log.debug("exiting MenuAction postprocessAction");		
		
	}
}
