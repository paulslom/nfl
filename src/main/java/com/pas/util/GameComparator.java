package com.pas.util;

import java.util.Comparator;
import java.util.Date;

import com.pas.beans.NflGame;
import com.pas.dynamodb.DateToStringConverter;

public class GameComparator implements Comparator<NflGame>
{
	public int compare(NflGame game1, NflGame game2)
	{
		Integer seasonId1 = game1.getiSeasonId();
		Integer weekNumber1 = game1.getIweekNumber();
		Date gameDate1 = DateToStringConverter.unconvert(game1.getDgameDateTime());
		
		Integer seasonId2 = game2.getiSeasonId();
		Integer weekNumber2 = game2.getIweekNumber();
		Date gameDate2 = DateToStringConverter.unconvert(game2.getDgameDateTime());	
		
		//Sort by season first, then week number, then game date time
		if (seasonId1.compareTo(seasonId2) != 0)
		{
			return seasonId1.compareTo(seasonId2);
		}
		
		if (weekNumber1.compareTo(weekNumber2) != 0)
		{
			return weekNumber1.compareTo(weekNumber2);	
		}
		
		return gameDate1.compareTo(gameDate2);
	}

}
