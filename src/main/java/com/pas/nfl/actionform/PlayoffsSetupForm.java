package com.pas.nfl.actionform;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.constants.INFLMessageConstants;
import com.pas.nfl.valueObject.PlayoffsSetup;
import com.pas.util.StringUtil;
/** 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PlayoffsSetupForm extends NFLBaseActionForm
{	
	private static final long serialVersionUID = 1L;
	
	private String playoffsSetupFormTitle;
	
	private List<TblTeam> afcTeamsList = new ArrayList<>();
	private List<TblTeam> nfcTeamsList = new ArrayList<>();
	
	private Integer totalPlayoffTeamsEachConference = 0;
	private Integer totalByesByConf = 0;
	
	public PlayoffsSetupForm()
	{		
	}	
		
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		String methodName = "validate :: ";
		log.debug(methodName + " In");		
		

		ActionErrors ae = new ActionErrors();
		
		int afcSelectedTeams = 0;
		int nfcSelectedTeams = 0;
		
		for (int i = 0; i < this.getAfcTeamsList().size(); i++) 
		{
			TblTeam selectedTeam = this.getAfcTeamsList().get(i);
			if (selectedTeam.isSelectedInd())
			{
				afcSelectedTeams++;
			}
		}
		
		for (int i = 0; i < this.getNfcTeamsList().size(); i++) 
		{
			TblTeam selectedTeam = this.getNfcTeamsList().get(i);
			if (selectedTeam.isSelectedInd())
			{
				nfcSelectedTeams++;				
			}
		}
		
		if (afcSelectedTeams != this.getTotalPlayoffTeamsEachConference())
		{
			ae.add(INFLMessageConstants.INCORRECT_NUMBER_OF_SEEDS, new ActionMessage(INFLMessageConstants.INCORRECT_NUMBER_OF_SEEDS));
		}
		else //correct number of teams selected; are their seeds proper?
		{
			for (int i = 0; i < this.getAfcTeamsList().size(); i++) 
			{
				TblTeam selectedTeam = this.getAfcTeamsList().get(i);
				if (selectedTeam.isSelectedInd())
				{
					if (StringUtil.isNumeric(selectedTeam.getPlayoffSeedStr()))
					{
						selectedTeam.setPlayoffSeedInt(Integer.parseInt(selectedTeam.getPlayoffSeedStr()));
						
						if (selectedTeam.getPlayoffSeedInt() < 1 || selectedTeam.getPlayoffSeedInt() > this.getTotalPlayoffTeamsEachConference())
						{
							ae.add(INFLMessageConstants.SEED_INVALID, new ActionMessage(INFLMessageConstants.SEED_INVALID));
						}	
					}
					else
					{
						ae.add(INFLMessageConstants.SEED_INVALID, new ActionMessage(INFLMessageConstants.SEED_INVALID));
					}
					
				}
			}
			
		}
			
		if (nfcSelectedTeams != this.getTotalPlayoffTeamsEachConference())
		{
			ae.add(INFLMessageConstants.INCORRECT_NUMBER_OF_SEEDS, new ActionMessage(INFLMessageConstants.INCORRECT_NUMBER_OF_SEEDS));
		}
		else //correct number of teams selected; are their seeds proper?
		{
			for (int i = 0; i < this.getNfcTeamsList().size(); i++) 
			{
				TblTeam selectedTeam = this.getNfcTeamsList().get(i);
				if (selectedTeam.isSelectedInd())
				{
					if (StringUtil.isNumeric(selectedTeam.getPlayoffSeedStr()))
					{
						selectedTeam.setPlayoffSeedInt(Integer.parseInt(selectedTeam.getPlayoffSeedStr()));
						
						if (selectedTeam.getPlayoffSeedInt() < 1 || selectedTeam.getPlayoffSeedInt() > this.getTotalPlayoffTeamsEachConference())
						{
							ae.add(INFLMessageConstants.SEED_INVALID, new ActionMessage(INFLMessageConstants.SEED_INVALID));
						}	
					}
					else
					{
						ae.add(INFLMessageConstants.SEED_INVALID, new ActionMessage(INFLMessageConstants.SEED_INVALID));
					}
				}
			}
			
		}
				
		return ae;
	}

	public String getPlayoffsSetupFormTitle() {
		return playoffsSetupFormTitle;
	}


	public void setPlayoffsSetupFormTitle(String playoffsSetupFormTitle) {
		this.playoffsSetupFormTitle = playoffsSetupFormTitle;
	}

	public List<TblTeam> getAfcTeamsList() {
		return afcTeamsList;
	}

	public void setAfcTeamsList(List<TblTeam> afcTeamsList) {
		this.afcTeamsList = afcTeamsList;
	}

	public List<TblTeam> getNfcTeamsList() {
		return nfcTeamsList;
	}

	public void setNfcTeamsList(List<TblTeam> nfcTeamsList) {
		this.nfcTeamsList = nfcTeamsList;
	}

	public Integer getTotalPlayoffTeamsEachConference() {
		return totalPlayoffTeamsEachConference;
	}

	public void setTotalPlayoffTeamsEachConference(Integer totalPlayoffTeamsEachConference) {
		this.totalPlayoffTeamsEachConference = totalPlayoffTeamsEachConference;
	}

	public Integer getTotalByesByConf() {
		return totalByesByConf;
	}

	public void setTotalByesByConf(Integer totalByesByConf) {
		this.totalByesByConf = totalByesByConf;
	}
	
	//  this is the method that will be called to save
    //  the indexed properties when the form is saved
    public TblTeam getAfcTeamItem(int index)
    {
        // make sure that orderList is not null
        if (this.afcTeamsList == null)
        {
            this.afcTeamsList = new ArrayList<TblTeam>();
        }
 
        // indexes do not come in order, populate empty spots
        while(index >= this.afcTeamsList.size())
        {
            this.afcTeamsList.add(new TblTeam());
        }
 
        // return the requested item
        return afcTeamsList.get(index);
    }
    
    //  this is the method that will be called to save
    //  the indexed properties when the form is saved
    public TblTeam getNfcTeamItem(int index)
    {
        // make sure that orderList is not null
        if (this.nfcTeamsList == null)
        {
            this.nfcTeamsList = new ArrayList<TblTeam>();
        }
 
        // indexes do not come in order, populate empty spots
        while(index >= this.nfcTeamsList.size())
        {
            this.nfcTeamsList.add(new TblTeam());
        }
 
        // return the requested item
        return nfcTeamsList.get(index);
    }
	

}
