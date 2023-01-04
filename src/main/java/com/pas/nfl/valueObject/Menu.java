package com.pas.nfl.valueObject;

import com.pas.valueObject.IValueObject;

/**
 * Title: 		Menu
 * Project: 	Slomkowski Financial Application
 * Copyright: 	Copyright (c) 2006
 */
public class Menu implements IValueObject {
	
	private Integer menuOrder;
	private Integer menuSubOrder;
	private String menuParentName;
	private String menuName;
	private String menuTitle;
	private String menuLocation;
	private String menuTarget;
	private String menuAction;
	private String menuID;
	private String menuDescription;
	
	/**
	 * @return String
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer();

		buf.append("Menu Order  = " + menuOrder + "\n");
		buf.append("Menu SubOrder = " + menuSubOrder + "\n");
		buf.append("Menu Parent Name = " + menuParentName + "\n");
		buf.append("Menu Name = " + menuName + "\n");
		buf.append("Menu Title = " + menuTitle + "\n");
		buf.append("Menu Location = " + menuLocation + "\n");
		buf.append("Menu Target = " + menuTarget + "\n");
		buf.append("Menu Action = " + menuAction + "\n");
		buf.append("Menu ID = " + menuID + "\n");
		buf.append("Menu Description = " + menuDescription + "\n");
		
		return buf.toString();
	}

	public Menu menuClone(Menu menu1)
	{
		Menu menu2 = new Menu();
		menu2.setMenuName(menu1.getMenuName());
		menu2.setMenuParentName(menu1.getMenuParentName());
		menu2.setMenuTitle(menu1.getMenuTitle());
		menu2.setMenuLocation(menu1.getMenuLocation());
		menu2.setMenuOrder(menu1.getMenuOrder());
		menu2.setMenuSubOrder(menu1.getMenuSubOrder());
		menu2.setMenuDescription(menu1.getMenuDescription());
		
		return menu2;
	}
	
	public Integer getMenuOrder()
	{
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) 
	{
		this.menuOrder = menuOrder;
	}

	public Integer getMenuSubOrder()
	{
		return menuSubOrder;
	}

	public void setMenuSubOrder(Integer menuSubOrder) 
	{
		this.menuSubOrder = menuSubOrder;
	}

	public String getMenuParentName() 
	{
		return menuParentName;
	}

	public void setMenuParentName(String menuParentName) 
	{
		this.menuParentName = menuParentName;
	}

	public String getMenuName() 
	{
		return menuName;
	}

	public void setMenuName(String menuName) 
	{
		this.menuName = menuName;
	}

	public String getMenuTitle() 
	{
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) 
	{
		this.menuTitle = menuTitle;
	}

	public String getMenuLocation() 
	{
		return menuLocation;
	}

	public void setMenuLocation(String menuLocation) 
	{
		this.menuLocation = menuLocation;
	}

	public String getMenuTarget() 
	{
		return menuTarget;
	}

	public void setMenuTarget(String menuTarget) 
	{
		this.menuTarget = menuTarget;
	}

	public String getMenuAction() 
	{
		return menuAction;
	}

	public void setMenuAction(String menuAction) 
	{
		this.menuAction = menuAction;
	}

	public String getMenuID() 
	{
		return menuID;
	}

	public void setMenuID(String menuID) 
	{
		this.menuID = menuID;
	}

	public String getMenuDescription() 
	{
		return menuDescription;
	}

	public void setMenuDescription(String menuDescription) 
	{
		this.menuDescription = menuDescription;
	}
	
}