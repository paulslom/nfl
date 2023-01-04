package com.pas.nfl.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.pas.action.ActionComposite;
import com.pas.cache.CacheManagerFactory;
import com.pas.cache.ICacheManager;
import com.pas.constants.IAppConstants;
import com.pas.exception.BusinessException;
import com.pas.exception.DAOException;
import com.pas.exception.PASSystemException;
import com.pas.exception.PresentationException;
import com.pas.exception.SystemException;
import com.pas.nfl.DBObjects.TblPlayoffTeams;
import com.pas.nfl.DBObjects.TblSeason;
import com.pas.nfl.DBObjects.TblTeam;
import com.pas.nfl.actionform.PlayoffsSetupForm;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.dao.NFLDAOFactory;
import com.pas.nfl.dao.SeasonDAO;
import com.pas.nfl.dao.TeamDAO;
import com.pas.nfl.valueObject.PlayoffsSelection;
import com.pas.nfl.valueObject.PlayoffsSetup;
import com.pas.nfl.valueObject.SeasonSelection;
import com.pas.nfl.valueObject.TeamSelection;

public class PlayoffsSetupShowFormAction extends NFLStandardAction
{
	private static final Integer AFC = 1;
	private static final Integer NFC = 2;
	
	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{
		log.debug("inside PlayoffsSetupShowFormAction pre - process");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		
		String seasonYear = cache.getSeasonYear(req.getSession());
		Integer seasonID = cache.getSeasonID(req.getSession());
		
		PlayoffsSelection playoffsSelection = new PlayoffsSelection();
		playoffsSelection.setSeasonYear(Integer.parseInt(seasonYear));
		playoffsSelection.setSeasonID(seasonID);
		playoffsSelection.setFunction("showSetupForm");
		
		SeasonDAO seasonDAOReference;
		
		TblSeason season;
		
		try
		{
			seasonDAOReference = (SeasonDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.SEASON_DAO);
			SeasonSelection seasonSelection = new SeasonSelection();
			seasonSelection.setSeasonYear(Integer.parseInt(seasonYear));
			List<TblSeason> seasonList = seasonDAOReference.inquire(seasonSelection);
			season = seasonList.get(0);	
			playoffsSelection.setPlayoffByesByConf(season.getiPlayoffByesByConf());
			playoffsSelection.setTotalPlayoffTeamsByConf(season.getiConferencePlayoffTeams());
		}
		catch (SystemException e1)
		{
			log.error("SystemException encountered: " + e1.getMessage());
			e1.printStackTrace();
			throw new PASSystemException(e1);
		}
		cache.setGoToDBInd(req.getSession(), true);
		cache.setObject("RequestObject", playoffsSelection, req.getSession());
				
		return true;
	}	
	@SuppressWarnings("unchecked")
	public void postprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation,
			ActionComposite ac) throws PresentationException,
			BusinessException, DAOException, PASSystemException
	{    	
		log.debug("inside PlayoffsSetupShowFormAction postprocessAction");
	
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		String seasonYear = cache.getSeasonYear(req.getSession());
		
		List responseList = (List)cache.getObject("ResponseObject",req.getSession());	
		
		PlayoffsSetupForm playoffsSetupForm = (PlayoffsSetupForm)form;		
		
		//first, what kind of list did we get back from response?  Code differently based on whether there was DB data...
		boolean dbDataPresent = false;
		
		for (int i = 0; i < responseList.size(); i++) 
		{
			Object obj = responseList.get(i);
			if (obj instanceof TblPlayoffTeams)
			{
				dbDataPresent = true;
			}
			break;
		}
		
		int playoffTeamsByConf = 0;
		int totalByesByConf = 0;
		
		if (dbDataPresent)
		{
			for (int i = 0; i < responseList.size(); i++) 
			{
				TblPlayoffTeams playoffTeam = (TblPlayoffTeams)responseList.get(i);
				
				if (playoffTeam.getiConferenceId() == AFC)
				{
					playoffTeamsByConf++;
					if (playoffTeam.getbBye())
					{
						totalByesByConf++;
					}
				}
				
			}
		}
		else //no data present on DB
		{
			//divide up into afc and nfc lists
			for (int i = 0; i < responseList.size(); i++) 
			{
				PlayoffsSetup playoffsSetup = (PlayoffsSetup)responseList.get(i);
				if (playoffsSetup.getConferenceName().equalsIgnoreCase("AFC"))
				{
					playoffTeamsByConf++;
					if (playoffsSetup.getBye())
					{
						totalByesByConf++;
					}
				}				
			}
		}		
		
		playoffsSetupForm.setTotalPlayoffTeamsEachConference(playoffTeamsByConf);
		playoffsSetupForm.setTotalByesByConf(totalByesByConf);
		
		playoffsSetupForm.setPlayoffsSetupFormTitle("Playoffs setup for " + seasonYear + " season.  Choose " + playoffTeamsByConf + " teams in each Conference.");
	
		TeamDAO teamDAOReference;
		
		try
		{
			teamDAOReference = (TeamDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.TEAM_DAO);
			TeamSelection teamSel = new TeamSelection();
			teamSel.setSeasonYear(cache.getSeasonYear(req.getSession()));
			
			teamSel.setTeamSelectionType("afcTeams");
			teamSel.setConferenceID(INFLAppConstants.AFC);
			List<TblTeam> afcTeamList = teamDAOReference.inquire(teamSel);
			
			teamSel.setTeamSelectionType("nfcTeams");
			teamSel.setConferenceID(INFLAppConstants.NFC);
			List<TblTeam> nfcTeamList = teamDAOReference.inquire(teamSel);
			
			if (dbDataPresent)
			{
				for (int i = 0; i < responseList.size(); i++) 
				{
					TblPlayoffTeams playoffTeam = (TblPlayoffTeams)responseList.get(i);
					
					if (playoffTeam.getiConferenceId() == AFC)
					{
						for (int j = 0; j < afcTeamList.size(); j++) 
						{
							TblTeam team = afcTeamList.get(j);
							if (playoffTeam.getiTeamID() == team.getIteamId())
							{
								team.setPlayoffSeedStr(playoffTeam.getiSeed().toString());
								team.setPlayoffSeedInt(playoffTeam.getiSeed());
								team.setSelectedInd(true);
							}
						}						
					}
					else //NFC
					{
						for (int j = 0; j < nfcTeamList.size(); j++) 
						{
							TblTeam team = nfcTeamList.get(j);
							if (playoffTeam.getiTeamID() == team.getIteamId())
							{
								team.setPlayoffSeedStr(playoffTeam.getiSeed().toString());
								team.setPlayoffSeedInt(playoffTeam.getiSeed());
								team.setSelectedInd(true);
							}
						}		
					}
					
				}
			}
			
			playoffsSetupForm.setAfcTeamsList(afcTeamList);			
			playoffsSetupForm.setNfcTeamsList(nfcTeamList);
			
		} 
		catch (SystemException e) 
		{
			throw new DAOException(e);
		}
		
		ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));		
				
		log.debug("exiting PlayoffsSetupShowFormAction postprocessAction");				
	}

}
