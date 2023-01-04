package com.pas.nfl.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.pas.nfl.DBObjects.TblGame;
import com.pas.nfl.DBObjects.TblPlayoffTeams;
import com.pas.nfl.DBObjects.TblSeason;
import com.pas.nfl.actionform.PlayoffBracketsForm;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.nfl.dao.GameDAO;
import com.pas.nfl.dao.NFLDAOFactory;
import com.pas.nfl.dao.SeasonDAO;
import com.pas.nfl.valueObject.GameSelection;
import com.pas.nfl.valueObject.PlayoffsSelection;
import com.pas.nfl.valueObject.PlayoffsSetup;
import com.pas.nfl.valueObject.SeasonSelection;

public class PlayoffBracketsAction extends NFLStandardAction
{
	private static final Integer AFC = 1;
	private static final Integer NFC = 2;
	
	private Map<Integer, Integer> playoffTeamSeedsMap = new HashMap<>();
	
	public boolean preprocessAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res, int operation) throws PresentationException, BusinessException,
			DAOException, PASSystemException
	{
		log.debug("inside PlayoffBracketsAction pre - process");
		
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		
		String seasonYear = cache.getSeasonYear(req.getSession());
		Integer seasonID = cache.getSeasonID(req.getSession());
		
		PlayoffsSelection playoffsSelection = new PlayoffsSelection();
		playoffsSelection.setSeasonYear(Integer.parseInt(seasonYear));
		playoffsSelection.setSeasonID(seasonID);
		playoffsSelection.setFunction("playoffBrackets");
		
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
		log.debug("inside PlayoffBracketsAction postprocessAction");
	
		ICacheManager cache =  CacheManagerFactory.getCacheManager();
		List responseList = (List)cache.getObject("ResponseObject",req.getSession());	
		String seasonYear = cache.getSeasonYear(req.getSession());
		PlayoffBracketsForm playoffBracketsForm = (PlayoffBracketsForm)form;
		
		GameDAO gameDAO;
		
		try
		{
			gameDAO = (GameDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.GAME_DAO);
			boolean seasonCompleted = gameDAO.isSeasonComplete(seasonYear);

			if (seasonCompleted)
			{				
				playoffBracketsForm.setBracketTitle("Playoff Bracket");
			}
			else
			{
				playoffBracketsForm.setBracketTitle("Projected Playoff Bracket");
			}
		} 
		catch (SystemException e) 
		{
			throw new DAOException(e);
		}
		
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
				playoffTeamSeedsMap.put(playoffTeam.getiTeamID(), playoffTeam.getiSeed());				
			}
			
			//go get playoff games
			
			GameDAO gameDAOReference;
			try
			{
				gameDAOReference = (GameDAO)NFLDAOFactory.getDAOInstance(INFLAppConstants.GAME_DAO);
				
				GameSelection gameSel = new GameSelection();
				gameSel.setSeasonYear(new Integer(seasonYear));
				gameSel.setPlayoffsOnly(true);
				List<TblGame> gamesList = gameDAOReference.inquire(gameSel);
			
				for (int j = 0; j < gamesList.size(); j++)
				{
					TblGame game = gamesList.get(j);
					if (game.getGameType().getSgameTypeDesc().equalsIgnoreCase("Playoffs: Super Bowl"))
					{
						if (game.getAwayTeam().getDivision().getConference().getVconferenceName().equalsIgnoreCase("AFC"))
						{
							playoffBracketsForm.setAfcChampionSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
							playoffBracketsForm.setAfcChampionPicture(game.getAwayTeam().getSpictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBracketsForm.setAfcChampionScore(game.getIawayTeamScore().toString());
							}
							playoffBracketsForm.setAfcChampionTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
							playoffBracketsForm.setNfcChampionPicture(game.getHomeTeam().getSpictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBracketsForm.setNfcChampionScore(game.getIhomeTeamScore().toString());
							}
							playoffBracketsForm.setNfcChampionTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
							playoffBracketsForm.setNfcChampionSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");
						}
						else
						{
							playoffBracketsForm.setAfcChampionSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");
							playoffBracketsForm.setAfcChampionPicture(game.getHomeTeam().getSpictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBracketsForm.setAfcChampionScore(game.getIhomeTeamScore().toString());
							}
							playoffBracketsForm.setAfcChampionTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
							playoffBracketsForm.setNfcChampionPicture(game.getAwayTeam().getSpictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBracketsForm.setNfcChampionScore(game.getIawayTeamScore().toString());
							}
							playoffBracketsForm.setNfcChampionTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
							playoffBracketsForm.setNfcChampionSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
						}
					}
					else if (game.getGameType().getSgameTypeDesc().equalsIgnoreCase("Playoffs: Conference Finals"))
					{
						if (game.getAwayTeam().getDivision().getConference().getVconferenceName().equalsIgnoreCase("AFC"))
						{
							playoffBracketsForm.setAfcChampionshipRoadSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
							playoffBracketsForm.setAfcChampionshipRoadPicture(game.getAwayTeam().getSpictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBracketsForm.setAfcChampionshipRoadScore(game.getIawayTeamScore().toString());
							}
							playoffBracketsForm.setAfcChampionshipRoadTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
							playoffBracketsForm.setAfcChampionshipHomePicture(game.getHomeTeam().getSpictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBracketsForm.setAfcChampionshipHomeScore(game.getIhomeTeamScore().toString());
							}
							playoffBracketsForm.setAfcChampionshipHomeTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
							playoffBracketsForm.setAfcChampionshipHomeSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");
						}
						else //NFC championship
						{
							playoffBracketsForm.setNfcChampionshipHomeSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");
							playoffBracketsForm.setNfcChampionshipHomePicture(game.getHomeTeam().getSpictureFile());
							if (game.getIhomeTeamScore() != null)
							{
								playoffBracketsForm.setNfcChampionshipHomeScore(game.getIhomeTeamScore().toString());
							}
							playoffBracketsForm.setNfcChampionshipHomeTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
							playoffBracketsForm.setNfcChampionshipRoadPicture(game.getAwayTeam().getSpictureFile());
							if (game.getIawayTeamScore() != null)
							{
								playoffBracketsForm.setNfcChampionshipRoadScore(game.getIawayTeamScore().toString());
							}
							playoffBracketsForm.setNfcChampionshipRoadTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
							playoffBracketsForm.setNfcChampionshipRoadSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
						}
					}
					else if (game.getGameType().getSgameTypeDesc().equalsIgnoreCase("Playoffs: Divisional Round"))
					{
						if (game.getAwayTeam().getDivision().getConference().getVconferenceName().equalsIgnoreCase("AFC"))
						{
							if (playoffBracketsForm.getAfcDivisional1RoadSeed() == null || playoffBracketsForm.getAfcDivisional1RoadSeed().trim().length() == 0)
							{
								playoffBracketsForm.setAfcDivisional1RoadSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
								playoffBracketsForm.setAfcDivisional1RoadPicture(game.getAwayTeam().getSpictureFile());
								if (game.getIawayTeamScore() != null)
								{
									playoffBracketsForm.setAfcDivisional1RoadScore(game.getIawayTeamScore().toString());
								}
								playoffBracketsForm.setAfcDivisional1RoadTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
								playoffBracketsForm.setAfcDivisional1HomePicture(game.getHomeTeam().getSpictureFile());
								if (game.getIhomeTeamScore() != null)
								{
									playoffBracketsForm.setAfcDivisional1HomeScore(game.getIhomeTeamScore().toString());
								}
								playoffBracketsForm.setAfcDivisional1HomeTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
								playoffBracketsForm.setAfcDivisional1HomeSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");
							}
							else //populate afc divisional 2
							{
								playoffBracketsForm.setAfcDivisional2RoadSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
								playoffBracketsForm.setAfcDivisional2RoadPicture(game.getAwayTeam().getSpictureFile());
								if (game.getIawayTeamScore() != null)
								{
									playoffBracketsForm.setAfcDivisional2RoadScore(game.getIawayTeamScore().toString());
								}
								playoffBracketsForm.setAfcDivisional2RoadTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
								playoffBracketsForm.setAfcDivisional2HomePicture(game.getHomeTeam().getSpictureFile());
								if (game.getIhomeTeamScore() != null)
								{
									playoffBracketsForm.setAfcDivisional2HomeScore(game.getIhomeTeamScore().toString());
								}
								playoffBracketsForm.setAfcDivisional2HomeTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
								playoffBracketsForm.setAfcDivisional2HomeSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");					
							}							
						}
						else //NFC Divisional
						{
							if (playoffBracketsForm.getNfcDivisional1RoadSeed() == null || playoffBracketsForm.getNfcDivisional1RoadSeed().trim().length() == 0)
							{
								playoffBracketsForm.setNfcDivisional1RoadSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
								playoffBracketsForm.setNfcDivisional1RoadPicture(game.getAwayTeam().getSpictureFile());
								if (game.getIawayTeamScore() != null)
								{
									playoffBracketsForm.setNfcDivisional1RoadScore(game.getIawayTeamScore().toString());
								}
								playoffBracketsForm.setNfcDivisional1RoadTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
								playoffBracketsForm.setNfcDivisional1HomePicture(game.getHomeTeam().getSpictureFile());
								if (game.getIhomeTeamScore() != null)
								{
									playoffBracketsForm.setNfcDivisional1HomeScore(game.getIhomeTeamScore().toString());
								}
								playoffBracketsForm.setNfcDivisional1HomeTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
								playoffBracketsForm.setNfcDivisional1HomeSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");
							}
							else //populate nfc divisional 2
							{
								playoffBracketsForm.setNfcDivisional2RoadSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
								playoffBracketsForm.setNfcDivisional2RoadPicture(game.getAwayTeam().getSpictureFile());
								if (game.getIawayTeamScore() != null)
								{
									playoffBracketsForm.setNfcDivisional2RoadScore(game.getIawayTeamScore().toString());
								}
								playoffBracketsForm.setNfcDivisional2RoadTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
								playoffBracketsForm.setNfcDivisional2HomePicture(game.getHomeTeam().getSpictureFile());
								if (game.getIhomeTeamScore() != null)
								{
									playoffBracketsForm.setNfcDivisional2HomeScore(game.getIhomeTeamScore().toString());
								}
								playoffBracketsForm.setNfcDivisional2HomeTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
								playoffBracketsForm.setNfcDivisional2HomeSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");					
							}
						}
					}
					else if (game.getGameType().getSgameTypeDesc().equalsIgnoreCase("Playoffs: Wild Card Round"))
					{
						if (game.getAwayTeam().getDivision().getConference().getVconferenceName().equalsIgnoreCase("AFC"))
						{
							if (playoffBracketsForm.getAfcWildCard1RoadSeed() == null || playoffBracketsForm.getAfcWildCard1RoadSeed().trim().length() == 0)
							{
								playoffBracketsForm.setAfcWildCard1RoadSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
								playoffBracketsForm.setAfcWildCard1RoadPicture(game.getAwayTeam().getSpictureFile());
								if (game.getIawayTeamScore() != null)
								{
									playoffBracketsForm.setAfcWildCard1RoadScore(game.getIawayTeamScore().toString());
								}
								playoffBracketsForm.setAfcWildCard1RoadTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
								playoffBracketsForm.setAfcWildCard1HomePicture(game.getHomeTeam().getSpictureFile());
								if (game.getIhomeTeamScore() != null)
								{
									playoffBracketsForm.setAfcWildCard1HomeScore(game.getIhomeTeamScore().toString());
								}
								playoffBracketsForm.setAfcWildCard1HomeTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
								playoffBracketsForm.setAfcWildCard1HomeSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");
							}
							else if (playoffBracketsForm.getAfcWildCard2RoadSeed() == null || playoffBracketsForm.getAfcWildCard2RoadSeed().trim().length() == 0)
							{
								playoffBracketsForm.setAfcWildCard2RoadSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
								playoffBracketsForm.setAfcWildCard2RoadPicture(game.getAwayTeam().getSpictureFile());
								if (game.getIawayTeamScore() != null)
								{
									playoffBracketsForm.setAfcWildCard2RoadScore(game.getIawayTeamScore().toString());
								}
								playoffBracketsForm.setAfcWildCard2RoadTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
								playoffBracketsForm.setAfcWildCard2HomePicture(game.getHomeTeam().getSpictureFile());
								if (game.getIhomeTeamScore() != null)
								{
									playoffBracketsForm.setAfcWildCard2HomeScore(game.getIhomeTeamScore().toString());
								}
								playoffBracketsForm.setAfcWildCard2HomeTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
								playoffBracketsForm.setAfcWildCard2HomeSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");
							}
							else //populate afc wild card 3
							{
								playoffBracketsForm.setThreeWildCardGames(true);
								
								playoffBracketsForm.setAfcWildCard3RoadSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
								playoffBracketsForm.setAfcWildCard3RoadPicture(game.getAwayTeam().getSpictureFile());
								if (game.getIawayTeamScore() != null)
								{
									playoffBracketsForm.setAfcWildCard3RoadScore(game.getIawayTeamScore().toString());
								}
								playoffBracketsForm.setAfcWildCard3RoadTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
								playoffBracketsForm.setAfcWildCard3HomePicture(game.getHomeTeam().getSpictureFile());
								if (game.getIhomeTeamScore() != null)
								{
									playoffBracketsForm.setAfcWildCard3HomeScore(game.getIhomeTeamScore().toString());
								}
								playoffBracketsForm.setAfcWildCard3HomeTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
								playoffBracketsForm.setAfcWildCard3HomeSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");	
							}				
						}
						else //NFC
						{
							if (playoffBracketsForm.getNfcWildCard1RoadSeed() == null || playoffBracketsForm.getNfcWildCard1RoadSeed().trim().length() == 0)
							{
								playoffBracketsForm.setNfcWildCard1RoadSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
								playoffBracketsForm.setNfcWildCard1RoadPicture(game.getAwayTeam().getSpictureFile());
								if (game.getIawayTeamScore() != null)
								{
									playoffBracketsForm.setNfcWildCard1RoadScore(game.getIawayTeamScore().toString());
								}
								playoffBracketsForm.setNfcWildCard1RoadTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
								playoffBracketsForm.setNfcWildCard1HomePicture(game.getHomeTeam().getSpictureFile());
								if (game.getIhomeTeamScore() != null)
								{
									playoffBracketsForm.setNfcWildCard1HomeScore(game.getIhomeTeamScore().toString());
								}
								playoffBracketsForm.setNfcWildCard1HomeTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
								playoffBracketsForm.setNfcWildCard1HomeSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");
							}
							else if (playoffBracketsForm.getNfcWildCard2RoadSeed() == null || playoffBracketsForm.getNfcWildCard2RoadSeed().trim().length() == 0)
							{
								playoffBracketsForm.setNfcWildCard2RoadSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
								playoffBracketsForm.setNfcWildCard2RoadPicture(game.getAwayTeam().getSpictureFile());
								if (game.getIawayTeamScore() != null)
								{
									playoffBracketsForm.setNfcWildCard2RoadScore(game.getIawayTeamScore().toString());
								}
								playoffBracketsForm.setNfcWildCard2RoadTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
								playoffBracketsForm.setNfcWildCard2HomePicture(game.getHomeTeam().getSpictureFile());
								if (game.getIhomeTeamScore() != null)
								{
									playoffBracketsForm.setNfcWildCard2HomeScore(game.getIhomeTeamScore().toString());
								}
								playoffBracketsForm.setNfcWildCard2HomeTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
								playoffBracketsForm.setNfcWildCard2HomeSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");
							}
							else //populate nfc wild card 3
							{
								playoffBracketsForm.setNfcWildCard3RoadSeed("(" + playoffTeamSeedsMap.get(game.getAwayTeam().getIteamId()) + ")");
								playoffBracketsForm.setNfcWildCard3RoadPicture(game.getAwayTeam().getSpictureFile());
								if (game.getIawayTeamScore() != null)
								{
									playoffBracketsForm.setNfcWildCard3RoadScore(game.getIawayTeamScore().toString());
								}
								playoffBracketsForm.setNfcWildCard3RoadTeamAbbr(game.getAwayTeam().getCteamCityAbbr());
								playoffBracketsForm.setNfcWildCard3HomePicture(game.getHomeTeam().getSpictureFile());
								if (game.getIhomeTeamScore() != null)
								{
									playoffBracketsForm.setNfcWildCard3HomeScore(game.getIhomeTeamScore().toString());
								}
								playoffBracketsForm.setNfcWildCard3HomeTeamAbbr(game.getHomeTeam().getCteamCityAbbr());
								playoffBracketsForm.setNfcWildCard3HomeSeed("(" + playoffTeamSeedsMap.get(game.getHomeTeam().getIteamId())  + ")");	
							}				
						}
					}
											
				}	
					
			}	
			catch (SystemException e)
			{
				throw new DAOException(e);
			}
			
		}
		else //no data present on DB
		{
			
		}		
		ac.setActionForward(mapping.findForward(IAppConstants.AF_SUCCESS));		
				
		log.debug("exiting PlayoffBracketsAction postprocessAction");
				
	}

}
