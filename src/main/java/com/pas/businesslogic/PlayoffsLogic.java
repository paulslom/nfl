package com.pas.businesslogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.beans.NflMain;
import com.pas.beans.NflTeam;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("pc_PlayoffsLogic")
@SessionScoped
public class PlayoffsLogic implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(PlayoffsLogic.class);   
	
	@Inject NflMain nflMain;	

	private List<NflTeam> afcTeamsList = new ArrayList<>();
	private List<NflTeam> nfcTeamsList = new ArrayList<>();

	public void showBrackets(ActionEvent event) 
	{
		logger.info("show playoff Brackets selected from menu");
		
		try 
        {		
	        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String targetURL = "/nfl/playoffBrackets.xhtml";
		    ec.redirect(targetURL);
            logger.info("successfully redirected to: " + targetURL);
        } 
        catch (Exception e) 
        {
            logger.error("standingsReport exception: " + e.getMessage(), e);
        }
	}  	

	public void playoffBracketsSetup(ActionEvent event) 
	{
		logger.info("playoff Brackets Setup selected from menu");
		
		try 
        {		
			this.setAfcTeamsList(insertBlankRowsInList(nflMain.getAfcTeamsList()));
			this.setNfcTeamsList(insertBlankRowsInList(nflMain.getNfcTeamsList()));
			
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String targetURL = "/nfl/playoffBracketsSetup.xhtml";
		    ec.redirect(targetURL);
            logger.info("successfully redirected to: " + targetURL);
        } 
        catch (Exception e) 
        {
            logger.error("playoff Brackets setup exception: " + e.getMessage(), e);
        }
	}  	
	
	public String doPlayoffsSetup() 
	{	 
		logger.info("entering doPlayoffsSetup");			
		return "";
	}

	private List<NflTeam> insertBlankRowsInList(List<NflTeam> inputList)
	{
		//now let's tweak to insert a blank row between divisions and blank out division name after the first one
		
		List<NflTeam> newList = new ArrayList<>();
		String lastDivisionName = "";
		NflTeam blankTeam = new NflTeam();
		
		for (int j = 0; j < inputList.size(); j++) 
		{
			NflTeam nflteam = inputList.get(j);
			
			if (!nflteam.getvDivisionName().equalsIgnoreCase(lastDivisionName)) 
			{				
				if (j > 0) //don't add blank row first time through list
				{
					newList.add(blankTeam);			
				}
			}
			
			lastDivisionName = nflteam.getvDivisionName();
			newList.add(nflteam);
		}
		
		return newList;
	}
	public List<NflTeam> getAfcTeamsList() {
		return afcTeamsList;
	}

	public void setAfcTeamsList(List<NflTeam> afcTeamsList) {
		this.afcTeamsList = afcTeamsList;
	}

	public List<NflTeam> getNfcTeamsList() {
		return nfcTeamsList;
	}

	public void setNfcTeamsList(List<NflTeam> nfcTeamsList) {
		this.nfcTeamsList = nfcTeamsList;
	}
	
}
