package com.pas.nfl.actionform;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.pas.nfl.DBObjects.TblTeam;

public class CommonGamesSelectionForm extends NFLBaseActionForm
{
	private String reportTitle;
	
	private List<TblTeam> afcTeamsList = new ArrayList<>();
	private List<TblTeam> nfcTeamsList = new ArrayList<>();
	
	public String getReportTitle()
	{
		return reportTitle;
	}

	public void setReportTitle(String reportTitle)
	{
		this.reportTitle = reportTitle;
	}

	public CommonGamesSelectionForm()
	{		
	}
		
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request)
	{

		String methodName = "validate :: ";
		log.debug(methodName + " In");		
		
		ActionErrors ae = new ActionErrors();
				
		return ae;

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
