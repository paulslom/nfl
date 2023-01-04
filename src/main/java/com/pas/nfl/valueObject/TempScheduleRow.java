package com.pas.nfl.valueObject;

import java.util.ArrayList;
import java.util.List;

import com.pas.valueObject.IValueObject;

public class TempScheduleRow implements IValueObject
{
	private String team;
	private List<TempOpponent> opponents = new ArrayList<TempOpponent>();
	
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public List<TempOpponent> getOpponents() {
		return opponents;
	}

	public void setOpponents(List<TempOpponent> opponents) {
		this.opponents = opponents;
	}	
		
}
