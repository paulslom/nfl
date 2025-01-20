package com.pas.businesslogic;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.pas.beans.NflMain;
import com.pas.pojo.Schedule;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
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

	private PDFOptions pdfOpt;
	
	private static float leftMargin = 25.5f;
	private static float rightMargin = 25.5f;
	private static float topMargin = 45.5f;
	private static float bottomMargin = 45.5f;
	
	@PostConstruct
    public void init() 
	{
        setPdfExportOptions();
    }
	
	private void setPdfExportOptions() 
	{
		pdfOpt = new PDFOptions();
        pdfOpt.setFacetBgColor("#F88017");
        pdfOpt.setFacetFontColor("#0000ff");
        pdfOpt.setFacetFontStyle("BOLD");
        pdfOpt.setCellFontSize("8");
        pdfOpt.setFontName("Arial");
        pdfOpt.setOrientation(PDFOrientationType.LANDSCAPE);
	}

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
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
        }
	}  	

	private void setSchedule() throws Exception 
	{		
		Schedule titleRow = nflMain.getScheduleTitleRow();
		Schedule scheduleTitleRow = new Schedule();
		
		for (int j = 1; j <= titleRow.getOpponentsList().size(); j++)
		{		
			switch (j)
			{
 				case 1:
 					scheduleTitleRow.setWeek1(titleRow.getOpponentsList().get(j-1));
 					break;
 					
				case 2:
					scheduleTitleRow.setWeek2(titleRow.getOpponentsList().get(j-1));
					break;
					
				case 3:
					scheduleTitleRow.setWeek3(titleRow.getOpponentsList().get(j-1));
 					break;
 					
				case 4:
					scheduleTitleRow.setWeek4(titleRow.getOpponentsList().get(j-1));
					break;
					
				case 5:
					scheduleTitleRow.setWeek5(titleRow.getOpponentsList().get(j-1));
 					break;
 					
				case 6:
					scheduleTitleRow.setWeek6(titleRow.getOpponentsList().get(j-1));
					break;
					
				case 7:
					scheduleTitleRow.setWeek7(titleRow.getOpponentsList().get(j-1));
 					break;
 					
				case 8:
					scheduleTitleRow.setWeek8(titleRow.getOpponentsList().get(j-1));
					break;
					
				case 9:
					scheduleTitleRow.setWeek9(titleRow.getOpponentsList().get(j-1));
 					break;
 					
				case 10:
					scheduleTitleRow.setWeek10(titleRow.getOpponentsList().get(j-1));
					break;
					
				case 11:
					scheduleTitleRow.setWeek11(titleRow.getOpponentsList().get(j-1));
 					break;
 					
				case 12:
					scheduleTitleRow.setWeek12(titleRow.getOpponentsList().get(j-1));
					break;
					
				case 13:
					scheduleTitleRow.setWeek13(titleRow.getOpponentsList().get(j-1));
 					break;
 					
				case 14:
					scheduleTitleRow.setWeek14(titleRow.getOpponentsList().get(j-1));
					break;
					
				case 15:
					scheduleTitleRow.setWeek15(titleRow.getOpponentsList().get(j-1));
 					break;
 					
				case 16:
					scheduleTitleRow.setWeek16(titleRow.getOpponentsList().get(j-1));
					break;
					
				case 17:
					scheduleTitleRow.setRenderWeek17(true);
					scheduleTitleRow.setWeek17(titleRow.getOpponentsList().get(j-1));
 					break;
 					
				case 18:
					scheduleTitleRow.setRenderWeek18(true);
					scheduleTitleRow.setWeek18(titleRow.getOpponentsList().get(j-1));
					break;
			}		
			
		}	
		
		this.getScheduleList().add(scheduleTitleRow);
		
		List<String> teamsList = nflMain.getTeamRegularSeasonGamesMap().keySet().stream().collect(Collectors.toList());
		Collections.sort(teamsList);
		
		for (int i = 0; i < teamsList.size(); i++)
		{
			Schedule teamRow = new Schedule();
			teamRow.setTeam(teamsList.get(i));
		
			Map<Integer, String> opponentsMap = nflMain.getTeamRegularSeasonGamesMap().get(teamsList.get(i));
			List<String> opponentsList = opponentsMap.values().stream().collect(Collectors.toList());
			
			for (int j = 1; j <= opponentsList.size(); j++) 
			{
				switch (j)
				{
	 				case 1:
	 					teamRow.setWeek1(opponentsList.get(j-1));
	 					if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek1Style(NflMain.RED_STYLECLASS);
	 					}
	 					break;
	 					
					case 2:
						teamRow.setWeek2(opponentsList.get(j-1));
						if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek2Style(NflMain.RED_STYLECLASS);
	 					}
						break;
						
					case 3:
	 					teamRow.setWeek3(opponentsList.get(j-1));
	 					if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek3Style(NflMain.RED_STYLECLASS);
	 					}
	 					break;
	 					
					case 4:
						teamRow.setWeek4(opponentsList.get(j-1));
						if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek4Style(NflMain.RED_STYLECLASS);
	 					}
						break;
						
					case 5:
	 					teamRow.setWeek5(opponentsList.get(j-1));
	 					if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek5Style(NflMain.RED_STYLECLASS);
	 					}
	 					break;
	 					
					case 6:
						teamRow.setWeek6(opponentsList.get(j-1));
						if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek6Style(NflMain.RED_STYLECLASS);
	 					}
						break;
						
					case 7:
	 					teamRow.setWeek7(opponentsList.get(j-1));
	 					if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek7Style(NflMain.RED_STYLECLASS);
	 					}
	 					break;
	 					
					case 8:
						teamRow.setWeek8(opponentsList.get(j-1));
						if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek8Style(NflMain.RED_STYLECLASS);
	 					}
						break;
						
					case 9:
	 					teamRow.setWeek9(opponentsList.get(j-1));
	 					if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek9Style(NflMain.RED_STYLECLASS);
	 					}
	 					break;
	 					
					case 10:
						teamRow.setWeek10(opponentsList.get(j-1));
						if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek10Style(NflMain.RED_STYLECLASS);
	 					}
						break;
						
					case 11:
	 					teamRow.setWeek11(opponentsList.get(j-1));
	 					if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek11Style(NflMain.RED_STYLECLASS);
	 					}
	 					break;
	 					
					case 12:
						teamRow.setWeek12(opponentsList.get(j-1));
						if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek12Style(NflMain.RED_STYLECLASS);
	 					}
						break;
						
					case 13:
	 					teamRow.setWeek13(opponentsList.get(j-1));
	 					if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek13Style(NflMain.RED_STYLECLASS);
	 					}
	 					break;
	 					
					case 14:
						teamRow.setWeek14(opponentsList.get(j-1));
						if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek14Style(NflMain.RED_STYLECLASS);
	 					}
						break;
						
					case 15:
	 					teamRow.setWeek15(opponentsList.get(j-1));
	 					if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek15Style(NflMain.RED_STYLECLASS);
	 					}
	 					break;
	 					
					case 16:
						teamRow.setWeek16(opponentsList.get(j-1));
						if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek16Style(NflMain.RED_STYLECLASS);
	 					}
						break;
						
					case 17:
						teamRow.setRenderWeek17(true);
	 					teamRow.setWeek17(opponentsList.get(j-1));
	 					if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek17Style(NflMain.RED_STYLECLASS);
	 					}
	 					break;
	 					
					case 18:
						teamRow.setRenderWeek18(true);
						teamRow.setWeek18(opponentsList.get(j-1));
						if (opponentsList.get(j-1).equalsIgnoreCase("*BYE*"))
	 					{
	 						teamRow.setWeek18Style(NflMain.RED_STYLECLASS);
	 					}
						break;
				}
			}
						
			this.getScheduleList().add(teamRow);
		}
		
		logger.info("schedule established");
		
	}
	
	public void preProcessPDF(Object document)
	{
		try 
        {
	        Document pdf = (Document) document;
	        pdf.setMargins(leftMargin, rightMargin, topMargin, bottomMargin);
	        pdf.open();
	        
	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	
	        String separator = File.separator;
	        String logo = externalContext.getRealPath("") + separator + "images" + separator + "nfl-logo-small.png";
	   
			pdf.add(Image.getInstance(logo));
		} 
		catch (Exception e) 
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null);
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
	        logger.error("preProcessPDF error: " + e.getMessage(), e);
		} 
    }
		
   	public List<Schedule> getScheduleList() {
		return scheduleList;
	}

	public void setScheduleList(List<Schedule> scheduleList) {
		this.scheduleList = scheduleList;
	}

	public PDFOptions getPdfOpt() {
		return pdfOpt;
	}

	public void setPdfOpt(PDFOptions pdfOpt) {
		this.pdfOpt = pdfOpt;
	}
	
}
