package com.pas.pojo;

import java.util.ArrayList;
import java.util.List;

public class TempScheduleRow
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
