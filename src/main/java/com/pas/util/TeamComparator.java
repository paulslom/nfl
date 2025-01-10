package com.pas.util;

import java.util.Comparator;

import jakarta.faces.model.SelectItem;

public class TeamComparator implements Comparator<SelectItem>
{
	//each item label contains the full team name.
	public int compare(SelectItem team1, SelectItem team2)
	{
		return team1.getLabel().compareTo(team2.getLabel());
	}

}
