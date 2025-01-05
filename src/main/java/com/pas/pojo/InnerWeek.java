package com.pas.pojo;

public class InnerWeek 
{
	private Integer weekId;
	private Integer weekNumber;
	
	public String toString()
    {
    	return "weekid: " + weekId + " week number: " + weekNumber;
    }
	
	public Integer getWeekId() {
		return weekId;
	}
	public void setWeekId(Integer weekId) {
		this.weekId = weekId;
	}
	public Integer getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(Integer weekNumber) {
		this.weekNumber = weekNumber;
	}
	
	
}
