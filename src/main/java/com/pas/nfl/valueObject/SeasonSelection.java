package com.pas.nfl.valueObject;

import com.pas.valueObject.IValueObject;

public class SeasonSelection  implements IValueObject
{
	private static final long serialVersionUID = 1L;
	
	private Integer seasonYear;
	private Integer seasonID;
	
	public Integer getSeasonYear()
	{
		return seasonYear;
	}

	public void setSeasonYear(Integer seasonYear)
	{
		this.seasonYear = seasonYear;
	}


	public Integer getSeasonID() {
		return seasonID;
	}

	public void setSeasonID(Integer seasonID) {
		this.seasonID = seasonID;
	}
	
}
