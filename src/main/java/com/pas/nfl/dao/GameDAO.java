package com.pas.nfl.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.pas.constants.IAppConstants;
import com.pas.dao.BaseDBDAO;
import com.pas.exception.DAOException;
import com.pas.nfl.DBObjects.TblGame;
import com.pas.nfl.valueObject.GameSelection;
import com.pas.util.Utils;

public class GameDAO extends BaseDBDAO
{
    private static final GameDAO currentInstance = new GameDAO();

    private GameDAO()
    {
        super();
    }
    /**
     * Returns the singleton instance of this class
     * 
     * @return gameDAO
     */
    public static GameDAO getDAOInstance()
    {
    	currentInstance.dataSource = Utils.getDatasourceProperties();
        return currentInstance;
    }	
    	
    public List<TblGame> add(Object Info) throws DAOException
	{       
        final String methodName = "add::";
		log.debug(methodName + "in");
		
		TblGame game = (TblGame)Info;
		
		TblGame game2 = nullOutZeroZeroGame(game);		
		
		String insertStr = " INSERT INTO tblgame (dGameDateTime, iAwayTeamID, iGameTypeID, iHomeTeamID, iWeekId) values(?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(insertStr, new Object[] {game2.getDgameDateTime(), game2.getIawayTeamID(), game2.getIgameTypeId(), game2.getIhomeTeamID(), game2.getIweekId()});	
	
		log.debug(methodName + "out");
		
		//no list to pass back on an add
		return null;
	}    
    
	public List<TblGame> delete(Object Info) throws DAOException
	{
		final String methodName = "delete::";
		log.debug(methodName + "in");
		
		TblGame game = (TblGame)Info;
		
		String deleteStr = " delete from tblgame where iGameID = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(deleteStr, game.getIgameId());
		
		log.debug(methodName + "out");
		
		//no list to pass back on a delete
		return null;
	}    
    
	@SuppressWarnings("unchecked")
	public List<TblGame> inquire(Object Info) throws DAOException 
	{
		final String methodName = "inquire::";
		log.debug(methodName + "in");
		
		List<TblGame> gameList = new ArrayList<>();
		
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("select gm.*, gmtyp.sGameTypeDesc, gmtyp.*, wk.*,"); 
		sbuf.append(" awayteam.iDivisionID as awayTeamDivisionID, awaydivision.vDivisionName as awayDivisionName,");
		sbuf.append(" awayTeam.vTeamCity as awayTeamCity, awayTeam.vTeamNickname as awayTeamNickname,"); 
		sbuf.append("     awayTeam.cTeamCityAbbr as awayTeamAbbr,awayTeam.sPictureFile as awayTeamPic,");
	    sbuf.append("      awayconference.vConferenceName as awayConferenceName, awayconference.iConferenceID as awayConferenceID,");
	    sbuf.append("      hometeam.iDivisionID as homeTeamDivisionID, homedivision.vDivisionName as homeDivisionName,");
	    sbuf.append("      homeTeam.vTeamCity as homeTeamCity, homeTeam.vTeamNickname as homeTeamNickname,"); 
	    sbuf.append("      homeTeam.cTeamCityAbbr as homeTeamAbbr, homeTeam.sPictureFile as homeTeamPic,"); 
	    sbuf.append("   homeconference.vConferenceName as homeConferenceName, homeconference.iConferenceID as homeConferenceID"); 
	    sbuf.append("  from tblgame gm");
	    sbuf.append(" INNER JOIN tblgametype gmtyp on gm.iGameTypeID = gmtyp.iGameTypeID"); 
		sbuf.append(" INNER JOIN tblweek wk on gm.iweekid = wk.iweekid");
		sbuf.append(" INNER JOIN tblseason seas on wk.iSeasonID = seas.iSeasonID ");
		sbuf.append(" INNER JOIN tblteam awayteam on gm.iawayteamID = awayteam.iteamID"); 
		sbuf.append(" INNER JOIN tbldivision awaydivision on awayteam.iDivisionID = awaydivision.iDivisionID");
		sbuf.append(" INNER JOIN tblconference awayconference on awaydivision.iConferenceID = awayconference.iConferenceID");
		sbuf.append(" INNER JOIN tblteam hometeam on gm.ihometeamid = hometeam.iteamID"); 
		sbuf.append(" INNER JOIN tbldivision homedivision on hometeam.iDivisionID = homedivision.iDivisionID");
		sbuf.append(" INNER JOIN tblconference homeconference on homedivision.iConferenceID = homeconference.iConferenceID");
		
		if (Info instanceof Integer) //we're after one particular game
		{
			Integer gameID = (Integer)Info;
			sbuf.append(" where gm.igameId = :iGameId");
			String sql = sbuf.toString();
			log.debug("sql about to be run: " + sql);	   
	    	log.debug(methodName + "before inquiring for Game. Key value is = " + gameID);	    	
			SqlParameterSource param = new MapSqlParameterSource("iGameId", gameID);
			NamedParameterJdbcTemplate namedParameterJdbcTemplate  = new NamedParameterJdbcTemplate(dataSource);
			TblGame game = namedParameterJdbcTemplate.queryForObject(sql, param, new GameRowMapper());			
			gameList.add(game);		
		}		
		else //means we have an gameSelection object - this is an inquire request for a list of games.
		{			
			GameSelection gameSel = (GameSelection)Info;
			
			sbuf.append(" where seas.cyear = ");
			sbuf.append(gameSel.getSeasonYear());
			
			if (gameSel.getWeekNumber() != null)  //looking for games by week
			{	
				sbuf.append(" and wk.iweekNumber = ");
				sbuf.append(gameSel.getWeekNumber());
				sbuf.append(" order by gm.dgameDateTime");
				String sql = sbuf.toString();
				log.debug("sql about to be run: " + sql);	   
		    	log.debug(methodName + "before inquiring for Games by week. Key value is = " + gameSel.getWeekNumber());	   		    
			    JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
				gameList = jdbcTemplate.query(sql, new GameRowMapper());			    
			}
			else if (gameSel.getTeamID() != null)  //looking for games by team
			{	
				sbuf.append(" and (awayteam.iteamId = ");
				sbuf.append(gameSel.getTeamID());
				sbuf.append("  or  hometeam.iteamId = ");
				sbuf.append(gameSel.getTeamID());
				sbuf.append(") ");				
				sbuf.append(" order by gm.dgameDateTime");
				String sql = sbuf.toString();
				log.debug("sql about to be run: " + sql);  		    
			    JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
				gameList = jdbcTemplate.query(sql, new GameRowMapper());		
			}
			else if (gameSel.getPlayoffsOnly())  //looking for only playoff games for this season
			{	
				sbuf.append(" and gmtyp.iplayoffRound > 0 ");
				sbuf.append(" order by gm.dgameDateTime");
				String sql = sbuf.toString();
				log.debug("sql about to be run: " + sql);  		    
			    JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
				gameList = jdbcTemplate.query(sql, new GameRowMapper());		
			}
			else //this means no option set - just get all the games for the season
			{
				log.debug("retrieving all games by for the season = " + gameSel.getSeasonYear());				
				sbuf.append(" order by gm.dgameDateTime");
				String sql = sbuf.toString();
				log.debug("sql about to be run: " + sql);   		    
			    JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
				gameList = jdbcTemplate.query(sql, new GameRowMapper());			
			}			
						
		}				
											
		log.debug("final list size is = " + gameList.size());
		log.debug(methodName + "out");
		return gameList;	
	}

	public boolean isSeasonComplete(String seasonYear)
	{
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(" SELECT count(*) FROM tblgame gm");
		sbuf.append("  inner join tblWeek wk on gm.iweekid = wk.iweekid");
		sbuf.append("  inner join tblgametype gmtyp on gm.igametypeid = gmtyp.igametypeid");
		sbuf.append("  inner join tblseason seas on wk.iseasonid = seas.iseasonid");
		sbuf.append("  where seas.cYear = ");
		sbuf.append(seasonYear);
		sbuf.append("    and gmtyp.igametypeid = ");
		sbuf.append(IAppConstants.REGULAR_SEASON_GAME_TYPE);
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
		Integer totalSeasonGames = jdbcTemplate.queryForObject(sbuf.toString(), Integer.class);
		
		log.debug("total regular season games for " + seasonYear + " season: " + totalSeasonGames);
		
		sbuf.setLength(0); //clear buffer		
		
		sbuf.append(" SELECT count(*) FROM tblgame gm");
		sbuf.append("  inner join tblWeek wk on gm.iweekid = wk.iweekid");
		sbuf.append("  inner join tblgametype gmtyp on gm.igametypeid = gmtyp.igametypeid");
		sbuf.append("  inner join tblseason seas on wk.iseasonid = seas.iseasonid");
		sbuf.append("  where seas.cYear = ");
		sbuf.append(seasonYear);
		sbuf.append("    and gmtyp.igametypeid = ");
		sbuf.append(IAppConstants.REGULAR_SEASON_GAME_TYPE);
		sbuf.append("    and gm.iawayteamscore is not null");
		sbuf.append("   and gm.ihometeamscore is not null");
		
		Integer totalSeasonCompletedGames = jdbcTemplate.queryForObject(sbuf.toString(), Integer.class);
		
		log.debug("total COMPLETED regular season games for " + seasonYear + " season: " + totalSeasonCompletedGames);
		
		return totalSeasonGames.equals(totalSeasonCompletedGames);		
	}
	
	private TblGame nullOutZeroZeroGame(TblGame game)
	{
		TblGame game2 = new TblGame();
		game2.setIgameId(game.getIgameId());
		game2.setIweekId(game.getIweekId());
		game2.setIgameTypeId(game.getIgameTypeId());
		game2.setDgameDateTime(game.getDgameDateTime());
		game2.setIawayTeamScore(game.getIawayTeamScore());
		game2.setIhomeTeamScore(game.getIhomeTeamScore());
		game2.setIhomeTeamID(game.getIhomeTeamID());
		game2.setIawayTeamID(game.getIawayTeamID());
		
		Integer awayScore = game2.getIawayTeamScore();
		Integer homeScore = game2.getIhomeTeamScore();
		
		if (awayScore != null && homeScore != null )
		{	
			//there won't be a zero-zero game, unless they tie after 5 quarters with no score...
			
			if (awayScore.compareTo(new Integer(0)) == 0
			&&  homeScore.compareTo(new Integer(0)) == 0)
			{
				game2.setIawayTeamScore(null);
				game2.setIhomeTeamScore(null);
			}
		}
		return game2;
	}

	@SuppressWarnings("unchecked")
	public List<TblGame> update(Object Info) throws DAOException
	{
		final String methodName = "add::";
		log.debug(methodName + "in");
		
		log.debug("entering gameDAO update");
		
		if (Info instanceof TblGame)
		{
			TblGame game = (TblGame)Info;			
			TblGame game2 = nullOutZeroZeroGame(game);
			updateGame(game2);
		}
		else //must be a list of TblGame objects 
		{		
			 List<TblGame> gameList = (List<TblGame>)Info;
			 
			 for (int i=0; i<gameList.size(); i++)
			 {	
				TblGame game = gameList.get(i);			
				TblGame game2 = nullOutZeroZeroGame(game);	
				updateGame(game2);
			 }	
		}
		//no need to pass back a list on an update
		return null;	
	}
	
	private void updateGame(TblGame game)
	{		
		String updateStr = " UPDATE tblGame SET ";				
		updateStr = updateStr + " dgameDateTime = ?," ;
		updateStr = updateStr + " iawayTeamID = ?," ;
		updateStr = updateStr + " ihomeTeamID = ?," ;
		updateStr = updateStr + " iawayTeamScore = ?," ;
		updateStr = updateStr + " ihomeTeamScore = ?," ;
		updateStr = updateStr + " iweekId = ?," ;
		updateStr = updateStr + " igameTypeId = ?";			
		updateStr = updateStr + " WHERE igameId = ?";
	
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(updateStr, game.getDgameDateTime(), game.getIawayTeamID(), game.getIhomeTeamID(), game.getIawayTeamScore(), game.getIhomeTeamScore(), 
				game.getIweekId(), game.getIgameTypeId(), game.getIgameId());
		
		log.debug("update game table complete");		
	}
	
	
}
