package com.pas.businesslogic;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.beans.NflMain;
import com.pas.beans.NflTeam;
import com.pas.dynamodb.DynamoNflGame;
import com.pas.nfl.constants.INFLAppConstants;
import com.pas.pojo.Schedule;
import com.pas.util.NFLUtil;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("pc_ScheduleLogic")
@SessionScoped
public class ScheduleLogic implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(ScheduleLogic.class);   
	
	@Inject NflMain nflMain;	

	private List<Schedule> scheduleList = new ArrayList<>();

	public void scheduleReport(ActionEvent event) 
	{
		logger.info("schedule selected from menu");
		
		try 
        {		
			this.setSchedule();
			
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String targetURL = "/nfl/schedule.xhtml";
		    ec.redirect(targetURL);
            logger.info("successfully redirected to: " + targetURL);
        } 
        catch (Exception e) 
        {
            logger.error("scheduleReport exception: " + e.getMessage(), e);
        }
	}  	

	private List<Schedule> setSchedule() throws Exception 
	{
		return scheduleList;	
	}
		
   	public List<Schedule> getScheduleList() {
		return scheduleList;
	}

	public void setScheduleList(List<Schedule> scheduleList) {
		this.scheduleList = scheduleList;
	}
	
}
