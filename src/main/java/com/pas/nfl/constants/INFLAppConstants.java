package com.pas.nfl.constants;


public interface INFLAppConstants
{
    //Action Forwards
    public static final String AF_ADDANOTHER="addAnother";
   
	//Business references
	public static final String DBNAME_BUSINESS = "DBNameBusiness";
	
	//Button text
    public static final String BUTTON_ADDCASHSPENT = "Add Cash Transaction";
    public static final String BUTTON_ADDTHENADDANOTHERCASHSPENT = "Add then Add another Cash Transaction";
    public static final String BUTTON_RESUBMIT = "Re-submit";
    public static final String BUTTON_SUBMIT = "Submit";
    public static final String BUTTON_CANCELADD = "Cancel Add";
    public static final String BUTTON_CANCELUPDATE = "Cancel Update";
    public static final String BUTTON_CANCELDELETE = "Cancel Delete";
    public static final String BUTTON_RETURN = "Return";
    public static final String BUTTON_DELETE = "Delete";
    
	// Cache related constants
	
	public static final String CURRENTDBNAME="CurrentDBName";
	public static final String CURRENTSEASONID="CurrentSeasonID";
	public static final String CURRENTSEASONYEAR="CurrentSeasonYear";
	public static final String GOTODBIND="GoToDBInd";
	
	//DAO references
	public static final String GAME_DAO = "GameDAO";
	public static final String LEAGUESETUP_DAO = "LeagueSetupDAO";
	public static final String LOGIN_DAO = "LoginDAO";	
	public static final String SEASON_DAO = "SeasonDAO";
	public static final String PLAYOFFS_DAO = "PlayoffsDAO";
	public static final String TEAM_DAO = "TeamDAO";	
	public static final String WEEK_DAO = "WeekDAO";
	public static final String CONFERENCE_DAO = "ConferenceDAO";
	public static final String GAMETYPE_DAO = "GameTypeDAO";
	
	//Menu references
	public static final String MENU_SCORES_BY_WEEK = "MenuScoresByWeek";
	public static final String MENU_SCORES_BY_TEAM = "MenuScoresByTeam";
	public static final String MENU_REPORTS = "MenuReports";
	public static final String MENU_GAMES = "MenuGames";
	public static final String MENU_PLAYOFFS = "MenuPlayoffs";
	public static final String MENU_MISC = "MenuMisc";
	
	//Misc Constants       
    public static final Integer DROPDOWN_NOT_SELECTED = 0;
    	
	//Session vars
    public static final String SESSION_CURRENTDB = "CurrentDB";
    public static final String SESSION_SEASONLIST = "SeasonList";   
    public static final String SESSION_GAMESLIST = "GamesList";
    public static final String SESSION_DRAFTLIST = "DraftList";
    public static final String SESSION_STANDINGSLIST = "StandingsList";
    public static final String SESSION_SCHEDULELIST = "ScheduleList";
    public static final String SESSION_COMMONGAMESRESULTLIST = "CommonGamesResultList";
    public static final String SESSION_TEAMSLIST = "TeamsList";
    public static final String SESSION_WEEKSLIST = "WeeksList";
    public static final String SESSION_GAMETYPESLIST = "GameTypesList";
    public static final String SESSION_PlayoffBrackets = "PlayoffBrackets";       
    public static final String SESSION_SCORESLIST = "ScoresList"; 
    public static final String SESSION_CURRENTWEEK = "CurrentWeek";       
    
    public static final int WIN = 1;
    public static final int LOSS = -1;
    public static final int TIE = 0;
    
    public static final int AFC = 1;
    public static final int NFC = 2;

	public static final String SESSION_AFCTEAMSLIST = "AFCTeams";
	public static final String SESSION_NFCTEAMSLIST = "NFCTeams";
}