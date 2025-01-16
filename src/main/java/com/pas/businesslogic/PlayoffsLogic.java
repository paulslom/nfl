package com.pas.businesslogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.beans.NflMain;
import com.pas.beans.NflPlayoffTeam;
import com.pas.beans.NflTeam;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("pc_PlayoffsLogic")
@SessionScoped
public class PlayoffsLogic implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(PlayoffsLogic.class);   
	
	@Inject NflMain nflMain;	

	public static String GREEN_STYLECLASS = "resultGreen";
	
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

	public void styleTheRow(AjaxBehaviorEvent event)
	{
		UIComponent component = event.getComponent();
        if (component instanceof UIInput) 
        {
            UIInput inputComponent = (UIInput) component;
            Boolean checkBoxChecked = (Boolean) inputComponent.getValue();
            String clientID = inputComponent.getClientId();
            
            if (clientID.contains("afcCheckBoxId"))
            {
            	for (int i = 0; i < this.getAfcTeamsList().size(); i++) 
            	{            		
            		if (clientID.contains(":" + i +":"))
    				{
            			NflTeam afcteam = this.getAfcTeamsList().get(i);
                    	
    					if (checkBoxChecked)  
    					{ 
    						afcteam.setRowStyleClass(GREEN_STYLECLASS);	
    					}
    					else
    					{
    						afcteam.setRowStyleClass("");	
    					}
    					break;
    				}
				}
            }
            else if (clientID.contains("nfcCheckBoxId"))
            {
            	for (int i = 0; i < this.getNfcTeamsList().size(); i++) 
            	{
            		if (clientID.contains(":" + i +":"))
    				{
	            		NflTeam nfcteam = this.getNfcTeamsList().get(i);
	                	
						if (checkBoxChecked)  
						{ 
							nfcteam.setRowStyleClass(GREEN_STYLECLASS);	
						}
						else
						{
							nfcteam.setRowStyleClass("");	
						}
						break;
    				}
				}
            }
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
		
		try 
        {			
			for (int i = 0; i < this.getAfcTeamsList().size(); i++) 
        	{  
				NflTeam afcteam = this.getAfcTeamsList().get(i);
				
				if (afcteam.isPlayoffTeam())
				{
					logger.info(afcteam.getFullTeamName() + " is a playoff team");
					
					NflPlayoffTeam nflPlayoffTeam = new NflPlayoffTeam();
					nflPlayoffTeam.setSeasonYear(nflMain.getCurrentSelectedSeason().getcYear());
					nflPlayoffTeam.setIseasonId(nflMain.getCurrentSelectedSeason().getiSeasonID());
					nflPlayoffTeam.setiConferenceId(afcteam.getiConferenceID());
					nflPlayoffTeam.setiTeamID(afcteam.getiTeamID());
					nflPlayoffTeam.setTeamName(afcteam.getFullTeamName());
					nflPlayoffTeam.setiSeed(afcteam.getiSeed());
					
					if (afcteam.getiSeed() == null)
					{
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"afc playoff team seed is missing",null);
				        FacesContext.getCurrentInstance().addMessage(null, msg); 
				        return "";
					}
					else if (afcteam.getiSeed() == 1)
					{
						nflPlayoffTeam.setbBye(true);
					}
					else
					{
						nflPlayoffTeam.setbBye(false);
					}
					nflMain.addUpdateNflPlayoffTeam(nflPlayoffTeam);
				}
        	}
			
			for (int i = 0; i < this.getNfcTeamsList().size(); i++) 
	    	{
	    		NflTeam nfcteam = this.getNfcTeamsList().get(i);
	    		
	    		if (nfcteam.isPlayoffTeam())
				{
					logger.info(nfcteam.getFullTeamName() + " is a playoff team");
					
					NflPlayoffTeam nflPlayoffTeam = new NflPlayoffTeam();
					nflPlayoffTeam.setSeasonYear(nflMain.getCurrentSelectedSeason().getcYear());
					nflPlayoffTeam.setIseasonId(nflMain.getCurrentSelectedSeason().getiSeasonID());
					nflPlayoffTeam.setiConferenceId(nfcteam.getiConferenceID());
					nflPlayoffTeam.setiTeamID(nfcteam.getiTeamID());
					nflPlayoffTeam.setTeamName(nfcteam.getFullTeamName());
					nflPlayoffTeam.setiSeed(nfcteam.getiSeed());
					
					if (nfcteam.getiSeed() == null)
					{
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"nfc playoff team seed is missing",null);
				        FacesContext.getCurrentInstance().addMessage(null, msg); 
				        return "";
					}
					else if (nfcteam.getiSeed() == 1)
					{
						nflPlayoffTeam.setbBye(true);
					}
					else
					{
						nflPlayoffTeam.setbBye(false);
					}
					nflMain.addUpdateNflPlayoffTeam(nflPlayoffTeam);
				}
			}
			
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Playoffs setup successfully saved",null);
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
			
        }
		catch (Exception e) 
        {
            logger.error("playoff Brackets save exception: " + e.getMessage(), e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
        }
		
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
