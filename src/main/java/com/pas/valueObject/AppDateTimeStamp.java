/*
 * Created on Jun 7, 2005
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pas.valueObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * @author Mala K
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class AppDateTimeStamp extends AppDate {
	private String hour ;
	private String minute ;
	private String second  ;
	private String milliseconds  ;
	
	
	private Logger log = LogManager.getLogger(this.getClass()); //log4j for Logging
	
	/**
	 * @return
	 */
	public String getHour() {
		return hour;
	}

	/**
	 * @return
	 */
	public Logger getLog() {
		return log;
	}

	/**
	 * @return
	 */
	public String getMilliseconds() {
		return milliseconds;
	}

	/**
	 * @return
	 */
	public String getMinute() {
		return minute;
	}

	/**
	 * @return
	 */
	public String getSecond() {
		return second;
	}

	/**
	 * @param string
	 */
	public void setHour(String string) {
		hour = string;
	}

	/**
	 * @param logger
	 */
	public void setLog(Logger logger) {
		log = logger;
	}

	/**
	 * @param string
	 */
	public void setMilliseconds(String string) {
		milliseconds = string;
	}

	/**
	 * @param string
	 */
	public void setMinute(String string) {
		minute = string;
	}

	/**
	 * @param string
	 */
	public void setSecond(String string) {
		second = string;
	}

}
