package com.pas.nfl.util;

import java.util.Comparator;

import com.pas.nfl.valueObject.Standings;

public class StandingsComparator implements Comparator<Standings>
{
	public int compare(Standings firstStandingsDetail, Standings secondStandingsDetail)
	{
		String standingsDivisionName1 = firstStandingsDetail.getDivisionName();		
		Integer standingsLosses1 = firstStandingsDetail.getLosses();
		Integer standingsDivisionLosses1 = firstStandingsDetail.getDivisionLosses();
		Integer standingsConferenceLosses1 = firstStandingsDetail.getConferenceLosses();
		
		String standingsDivisionName2 = secondStandingsDetail.getDivisionName();
		Integer standingsLosses2 = secondStandingsDetail.getLosses();
		Integer standingsDivisionLosses2 = secondStandingsDetail.getDivisionLosses();
		Integer standingsConferenceLosses2 = secondStandingsDetail.getConferenceLosses();
		
		//Sort by division name first, then overall record, then division record, then conference record.
		if (standingsDivisionName1.compareTo(standingsDivisionName2) != 0)
		    return standingsDivisionName1.compareTo(standingsDivisionName2);
		
		if (standingsLosses1.compareTo(standingsLosses2) != 0)
			return standingsLosses1.compareTo(standingsLosses2);
		
		if (standingsDivisionLosses1.compareTo(standingsDivisionLosses2) != 0)	
		    return standingsDivisionLosses1.compareTo(standingsDivisionLosses2);					
		
		return standingsConferenceLosses1.compareTo(standingsConferenceLosses2);	
	}

}
