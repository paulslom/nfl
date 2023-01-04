package com.pas.util;

import java.util.Comparator;

import com.pas.nfl.valueObject.Menu;

public class MenuComparator implements Comparator<Menu>
{
	public int compare(Menu menuDetail, Menu anotherMenuDetail)
	{	
		Integer menuOrder1 = menuDetail.getMenuOrder();
		Integer menuSubOrder1 = menuDetail.getMenuSubOrder();
		
		Integer menuOrder2 = anotherMenuDetail.getMenuOrder();
		Integer menuSubOrder2 = anotherMenuDetail.getMenuSubOrder();
		
		if (menuOrder1.compareTo(menuOrder2) == 0) //means they are equal - need to go to subOrder
			return menuSubOrder1.compareTo(menuSubOrder2);
			
		return menuOrder1.compareTo(menuOrder2);
	}

}
