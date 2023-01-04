/*
 * Created on Mar 9, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pas.constants;

/**
 * @author SGanapathy
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IAppConstants {

	public static final String YES = "Y";
	public static final String NO = "N";
	
	public static final String ERROR_MESSAGES = "errorMessages";
	public static final String USER = "user";
	public static final String USER_ID = "UserId";
	
    public static final String METHOD = "method";
	public static final String ADD = "add";
	public static final String UPDATE = "update";
	public static final String NEXT = "next";
	public static final String PREVIOUS = "previous";
	public static final String DELETE = "delete";
	public static final String INQUIRE = "inquire";
	public static final String CANCEL = "cancel";
	public static final String NAVIGATE = "navigate";
	public static final String INITIALIZE = "initialize";
	public static final String OVERRIDE = "override";
	public static final String CLEAR = "clear";
	public static final String LOGIN = "login";
	public static final String LOGOUT = "logout";
	public static final String CALC = "calc";
	public static final String SUBMIT = "submit";
	public static final String FAILURE = "failure";

	public static final int ADD_ACTION = 1;
	public static final int UPDATE_ACTION = 2;
	public static final int DELETE_ACTION = 3;
	public static final int INQUIRE_ACTION = 4;
	public static final int CANCEL_ACTION = 5;
	public static final int NAVIGATE_ACTION = 6;
	
	public static final int REGULAR_SEASON_GAME_TYPE = 2;
	
	public static final char NEWLINE='\n';
	public static final String ACTION_NAME = "ActionName";
	public static final String ACTION_FORM_NAME = "FormName";
	
	//Cache related constants
	public static final String CACHE_MAP="AppCache";
	public static final String APPUSER="AppUser";
	public static final String SEASONID="SeasonID";
	public static final String SEASONYEAR="SeasonYear";
	public static final String SESSION_TIMEOUT_MANAGER = "SessionTimeoutManager";
	
	public static final String DATE_SEPARATOR = "/";
	public static final String SDF_STRING = "MM/dd/yyyy";
	public static final String DATE_SEPERATOR_SLASH = "/";
	public static final String SPACE_CONSTANT = "";

	public static final String DB_USE_JNDI = "usejndi";
	public static final String DB_DRIVER_TYPE = "drivertype";
	public static final String DB_WS_SQL_SERVER = "WebsphereSQLServer";
	public static final String DB_USERNAME_PASSWORD_FROM = "usernamepasswordfrom";
	public static final String DB_PASSWORD_UNENCRYPTED = "dbconfig-unencrypted";
	public static final String DB_PASSWORD_ENCRYPTED = "dbconfig-encrypted"; 
	public static final String DB_USERNAME_PASSWORD_FROM_SIGNON = "usersignon";
	public static final String DRIVER_MGR_CLASS = "drivermanager";
	public static final String DB_URL = "dburl";
	public static final String DB_USER_ID = "dbuserid";
	public static final String DB_PASSWORD = "dbpassword";
	public static final String DB_TXN_ISOLATION_LVL = "txnisolation";
	public static final String JNDI_DSN = "jndidsn";

	public static final String REQUESTCRITERIA = "RequestCriteria";
	
	// PropertiesFileUtil specific constants..
    public static final String DB_CONFIG_FILE = "dbconfig.properties";

    //Generic Action Forwards
    public static final String AF_SUCCESS="success";
    public static final String AF_FAILURE="failure";
    public static final String AF_CANCEL="cancel";
    
    //Parameter strings
    public static final String JSTL_TRUE = "true";
    public static final String JSTL_FALSE = "false";
    public static final String QS_OWNED = "owned";
        
	public static final String EXCEPTION_DEFINITION="ToException";
	public static final String LOGIN_DEFINITION="ToLogin";
	
	//Custom Tag constants
	public static final String CUSTOM_TAG_DATE_FORMAT = "MM/dd/yyyy";

    //For DAO Objects
	public static final String METHOD_MAPPING_DAO = "MethodMappingDao";
	public static final String DROPDOWN_DAO = "DropDownDAO";
	
	//For sorting
	public final static java.lang.String sortableRecords = "sortableRecords";
	public final static java.lang.String day = "day";
	public final static java.lang.String month = "month";
	public final static java.lang.String year = "year";
	public final static java.lang.String stampYear = "stampYear";
	public final static java.lang.String stampMonth = "stampMonth";
	public final static java.lang.String stampDay = "stampDay";
	public final static java.lang.String stampHours = "stampHours";
	public final static java.lang.String stampMilitaryHours = "stampMilitaryHours";
	public final static java.lang.String stampMinutes = "stampMinutes";
	public final static java.lang.String stampSeconds = "stampSeconds";
	public final static java.lang.String stampAmPm = "stampAmPm";
	public final static java.lang.String dateStamp = "dateStamp";
	public final static java.lang.String timeStamp = "timeStamp";
	public final static java.lang.String noKeys = "noKeys";
	public final static java.lang.String haveKeys = "haveKeys";
	public final static java.lang.String keySet = "keySet";
	public final static java.lang.String sortType = "sortType";
	public final static java.lang.String responseMessages = "responseMessages";
	public final static java.lang.String responseMessage = "responseMessage";
	public final static java.lang.String eventId = "eventId";
	public final static java.lang.String userId = "userId";
}
