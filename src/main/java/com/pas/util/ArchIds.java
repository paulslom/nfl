package com.pas.util;

/**
 * Insert the type's description here.
 * Creation date: (5/3/01 3:14:02 PM)
 * @author: Administrator
 */
public final class ArchIds {
	public final static java.lang.String LAST_ACCESS 	= "lastAccess";
	public final static long HOLD_CACHE_MAX_LENGTH 		= 3600000;
	public final static long CACHE_CLEAN_INTERVAL 		= 900000;
	
	public final static java.lang.String REASON_CODE_NO_MESSAGE 	= "NM";	
	public final static java.lang.String REASON_CODE_ADMIN_SYS_ERROR = "AE";	
	public final static java.lang.String REASON_CODE_TIME_OUT 		= "TO";	
	public final static java.lang.String REASON_CODE_NO_MATCH 		= "MA";	

	public final static java.lang.String ERROR_FREE_STATE 			= "ErrorFree";  
   	public final static java.lang.String REDISPLAY_PAGE_STATE 		= "RedisplayPage";
   	public final static java.lang.String DISPLAY_ERROR_PAGE_STATE 	= "DisplayErrorPage";

	public final static long FILE_CLEAN_INTERVAL 		= 900000;
	public final static long FILE_RETENTION_PERIOD		= 300000;

	/**
	 * ArchIDs constructor comment.
	 */
	public ArchIds() {
		super();
	}
}