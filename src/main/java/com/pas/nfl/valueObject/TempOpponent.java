package com.pas.nfl.valueObject;

import java.util.ArrayList;
import java.util.List;

import com.pas.valueObject.IValueObject;

public class TempOpponent implements IValueObject
{
	private String opponentName;

	public String getOpponentName() {
		return opponentName;
	}

	public void setOpponentName(String opponentName) {
		this.opponentName = opponentName;
	}
	
		
}
