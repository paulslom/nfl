package com.pas.nfl.actionform;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.pas.nfl.DBObjects.TblGame;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.constants.INFLMessageConstants;
import com.pas.util.PASUtil;

/** 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ScoresListForm extends NFLBaseActionForm
{
	public ScoresListForm()
	{	
		initialize();
	}
		
	private List<TblGame> scoresList = new ArrayList<TblGame>();
		
	public void initialize()
	{
		//initialize all variables
		
		String methodName = "initialize :: ";
		log.debug(methodName + " In");
		scoresList.clear();
		log.debug(methodName + " Out");
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request)
	{

		String methodName = "validate :: ";
		log.debug(methodName + " In");
		
		ActionErrors ae = new ActionErrors();

		String reqParm = request.getParameter("operation");
		
		//do not perform validation when cancelling
		if (reqParm.equalsIgnoreCase(INFLAppConstants.BUTTON_CANCELADD))
			return ae;		
		
		for (int i=0; i<scoresList.size(); i++)
		{	
			TblGame game = scoresList.get(i);
			
			//if (game.getIawayTeamScore().toString().length() == 0)
			//{
			//	ae.add(INFLMessageConstants.SCORE_MISSING,
			//		new ActionMessage(INFLMessageConstants.SCORE_MISSING));
			//}
			//else
			   if (!PASUtil.isValidNumeric(game.getIawayTeamScore().toString()))
			   {
				   ae.add(INFLMessageConstants.SCORE_INVALID,
							new ActionMessage(INFLMessageConstants.SCORE_INVALID));
			   }
			
			//if (game.getIhomeTeamScore().toString().length() == 0)
			//{
			//	ae.add(INFLMessageConstants.SCORE_MISSING,
			//		new ActionMessage(INFLMessageConstants.SCORE_MISSING));
			//}
			//else
			   if (!PASUtil.isValidNumeric(game.getIhomeTeamScore().toString()))
			   {
				   ae.add(INFLMessageConstants.SCORE_INVALID,
							new ActionMessage(INFLMessageConstants.SCORE_INVALID));
			   }			
		}
		
		return ae;
	}	   
   
    //  this is the method that will be called to save
    //  the indexed properties when the form is saved
    public TblGame getGameItem(int index)
    {
        // make sure that orderList is not null
        if(this.scoresList == null)
        {
            this.scoresList = new ArrayList<TblGame>();
        }
 
        // indexes do not come in order, populate empty spots
        while(index >= this.scoresList.size())
        {
            this.scoresList.add(new TblGame());
        }
 
        // return the requested item
        return scoresList.get(index);
    }
	public List<TblGame> getScoresList()
	{
		return scoresList;
	}
	public void setScoresList(List<TblGame> scoresList)
	{
		this.scoresList = scoresList;
	}
	
}
