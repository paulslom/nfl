package com.pas.nfl.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.pas.nfl.constants.INFLAppConstants;


/** 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GameUpdateForm extends NFLBaseActionForm
{
	
	private Integer gameID;
	private Integer weekID;
	private Integer weekNumber;
	private String gameDateTime;
	private Integer gameTypeID;
	private String gameTypeDesc;
	private Integer awayTeamID;
	private String awayTeamDesc;
	private Integer homeTeamID;	
	private String homeTeamDesc;
		
	public String getGameTypeDesc()
	{
		return gameTypeDesc;
	}
	public void setGameTypeDesc(String gameTypeDesc)
	{
		this.gameTypeDesc = gameTypeDesc;
	}
	public String getAwayTeamDesc()
	{
		return awayTeamDesc;
	}
	public void setAwayTeamDesc(String awayTeamDesc)
	{
		this.awayTeamDesc = awayTeamDesc;
	}
	public String getHomeTeamDesc()
	{
		return homeTeamDesc;
	}
	public void setHomeTeamDesc(String homeTeamDesc)
	{
		this.homeTeamDesc = homeTeamDesc;
	}
	public void initialize()
	{
		//initialize all variables
		
		String methodName = "initialize :: ";
		log.debug(methodName + " In");
		log.debug(methodName + " Out");
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request)
	{

		String methodName = "validate :: ";
		log.debug(methodName + " In");
		
		ActionErrors ae = new ActionErrors();

		String reqParm = request.getParameter("operation");
		
		//do not perform validation when cancelling or returning from an inquire or delete
		if (reqParm.equalsIgnoreCase(INFLAppConstants.BUTTON_CANCELADD)
		||  reqParm.equalsIgnoreCase(INFLAppConstants.BUTTON_CANCELUPDATE)
		||  reqParm.equalsIgnoreCase(INFLAppConstants.BUTTON_CANCELDELETE)
		||  reqParm.equalsIgnoreCase(INFLAppConstants.BUTTON_DELETE)
		||  reqParm.equalsIgnoreCase(INFLAppConstants.BUTTON_RETURN))
			return ae;
		
		ae = super.validate(mapping, request);		
		
		return ae;
		
	}
	public Integer getWeekNumber()
	{
		return weekNumber;
	}
	public void setWeekNumber(Integer weekNumber)
	{
		this.weekNumber = weekNumber;
	}
	public String getGameDateTime()
	{
		return gameDateTime;
	}
	public void setGameDateTime(String gameDateTime)
	{
		this.gameDateTime = gameDateTime;
	}
	public Integer getGameTypeID()
	{
		return gameTypeID;
	}
	public void setGameTypeID(Integer gameTypeID)
	{
		this.gameTypeID = gameTypeID;
	}
	public Integer getAwayTeamID()
	{
		return awayTeamID;
	}
	public void setAwayTeamID(Integer awayTeamID)
	{
		this.awayTeamID = awayTeamID;
	}
	public Integer getHomeTeamID()
	{
		return homeTeamID;
	}
	public void setHomeTeamID(Integer homeTeamID)
	{
		this.homeTeamID = homeTeamID;
	}
	public Integer getGameID()
	{
		return gameID;
	}
	public void setGameID(Integer gameID)
	{
		this.gameID = gameID;
	}
	public Integer getWeekID()
	{
		return weekID;
	}
	public void setWeekID(Integer weekID)
	{
		this.weekID = weekID;
	}
	
}
