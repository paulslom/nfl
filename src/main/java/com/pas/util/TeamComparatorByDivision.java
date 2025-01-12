package com.pas.util;

import java.util.Comparator;

import com.pas.beans.NflTeam;

public class TeamComparatorByDivision implements Comparator<NflTeam>
{
	//each item label contains the full team name.
	public int compare(NflTeam team1, NflTeam team2)
	{
		if (team1.getvDivisionName().compareTo(team2.getvDivisionName()) != 0)
		{
			return team1.getvDivisionName().compareTo(team2.getvDivisionName());
		}
		
		return team1.getFullTeamName().compareTo(team2.getFullTeamName());
	}

}
