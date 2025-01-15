package com.pas.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pas.beans.NflMain;
import com.pas.beans.NflTeam;
import com.pas.dynamodb.DynamoNflGame;
import com.pas.pojo.Decade;
import com.pas.pojo.OuterWeek;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

public class Utils 
{
	private static Logger logger = LogManager.getLogger(Utils.class);	
		
	public static String MY_TIME_ZONE = "America/New_York";
	
	public static String WILD_CARD = "Playoffs: Wild Card Round";
	public static String DIVISIONALS = "Playoffs: Divisional Round";
	public static String CONFCHAMPIONSHIPS = "Playoffs: Conference Finals";
	public static String SUPERBOWL = "Playoffs: Super Bowl";
		
	public static String getLastYearsLastDayDate() 
	{
	    Calendar prevYear = Calendar.getInstance();
	    prevYear.add(Calendar.YEAR, -1);
	    String returnDate = prevYear.get(Calendar.YEAR) + "-12-31";
	    return returnDate;
	}
	
	public static String getOneMonthAgoDate() 
	{
	    Calendar calOneMonthAgo = Calendar.getInstance();
	    calOneMonthAgo.add(Calendar.MONTH, -1);
	    Date dateOneMonthAgo = calOneMonthAgo.getTime();
	    Locale locale = Locale.getDefault();
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", locale);
	    String returnDate = formatter.format(dateOneMonthAgo);
	    return returnDate;
	}
			
	@SuppressWarnings("unchecked")
	public static boolean isAdminUser()
	{
		boolean adminUser = false;
	
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		
		for (Iterator<SimpleGrantedAuthority> iterator = authorities.iterator(); iterator.hasNext();) 
		{
			SimpleGrantedAuthority simpleGrantedAuthority = (SimpleGrantedAuthority) iterator.next();
			if (simpleGrantedAuthority.getAuthority().contains("ADMIN"))
			{
				adminUser = true; //admin user can see all the rounds
				break;
			}			
		}
		
		return adminUser;
		
	}
	
	public static String getLoggedInUserName()
	{		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		String currentUser = (String) session.getAttribute("currentUser");
		
		currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		logger.info("current logged in user is: " + currentUser);
		
	    return currentUser == null ? null : currentUser.toLowerCase().trim();
	}	

	public static String getDayofWeekString(Date date) 
	{
		Locale locale = Locale.getDefault();
	    DateFormat formatter = new SimpleDateFormat("EEEE", locale);
	    return formatter.format(date);
	}
		
	public static boolean isLocalEnv()
	{
		boolean isLocal = true; //just set to true when running locally
		/*
		try 
		{
			Path dir = (Path)Paths.get("/Paul", "GitHub");
			isLocal = Files.isDirectory(dir);
		} 
		catch (Exception e) 
		{			
		}
		*/		
		return isLocal;
	}
	
	public static String getEncryptedPassword(String unencryptedPassword)
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encryptedPW = passwordEncoder.encode(unencryptedPassword);
		return encryptedPW;
	}
	
	public static List<Decade> getDecadesList()
	{
		List<Decade> decadesList = new ArrayList<>();
		
		Decade decade = new Decade();
		decade.setDecadeDescription("2000s");
		decade.setDecadeStart(2000);
		decade.setDecadeEnd(2009);
		decadesList.add(decade);
		
		decade = new Decade();
		decade.setDecadeDescription("2010s");
		decade.setDecadeStart(2010);
		decade.setDecadeEnd(2019);
		decadesList.add(decade);
		
		decade = new Decade();
		decade.setDecadeDescription("2020s");
		decade.setDecadeStart(2020);
		decade.setDecadeEnd(2029);
		decadesList.add(decade);
		
		/* uncomment when it is time to. also in menubar.xhtml add lines to display
		decade = new Decade();
		decade.setDecadeDescription("2030s");
		decade.setDecadeStart(2030);
		decade.setDecadeEnd(2039);
		decadesList.add(decade);
		
		decade = new Decade();
		decade.setDecadeDescription("2040s");
		decade.setDecadeStart(2040);
		decade.setDecadeEnd(2049);
		decadesList.add(decade);
		*/
		return decadesList;
	}
	
	public static List<OuterWeek> getOuterWeeksList()
	{
		List<OuterWeek> outerWeeksList = new ArrayList<>();
		
		OuterWeek outerWeek = new OuterWeek();
		outerWeek.setOuterWeekDescription("Reg Season 1-9");
		outerWeek.setWeekNumberStart(1);
		outerWeek.setWeekNumberEnd(9);
		outerWeeksList.add(outerWeek);
		
		outerWeek = new OuterWeek();
		outerWeek.setOuterWeekDescription("Reg Season 10-18");
		outerWeek.setWeekNumberStart(10);
		outerWeek.setWeekNumberEnd(18);
		outerWeeksList.add(outerWeek);
	
		outerWeek = new OuterWeek();
		outerWeek.setOuterWeekDescription(WILD_CARD);
		outerWeek.setWeekNumberStart(19);
		outerWeek.setWeekNumberEnd(null);
		outerWeeksList.add(outerWeek);
		
		outerWeek = new OuterWeek();
		outerWeek.setOuterWeekDescription(DIVISIONALS);
		outerWeek.setWeekNumberStart(20);
		outerWeek.setWeekNumberEnd(null);
		outerWeeksList.add(outerWeek);
		
		outerWeek = new OuterWeek();
		outerWeek.setOuterWeekDescription(CONFCHAMPIONSHIPS);
		outerWeek.setWeekNumberStart(21);
		outerWeek.setWeekNumberEnd(null);
		outerWeeksList.add(outerWeek);
		
		outerWeek = new OuterWeek();
		outerWeek.setOuterWeekDescription(SUPERBOWL);
		outerWeek.setWeekNumberStart(22);
		outerWeek.setWeekNumberEnd(null);
		outerWeeksList.add(outerWeek);
		
		return outerWeeksList;
	}
	
	public static String getGameDayOfWeek(String gameDateTime)
	{
		String gameDayOfWeek = gameDateTime.substring(0, 3);
		return gameDayOfWeek;
	}
	
	public static String getGameDateOnly(String gameDateTime)
	{
		String gameDateOnly = gameDateTime.substring(4, 14);
		return gameDateOnly;
	}
	
	public static String getGameTimeOnly(String gameDateTime)
	{
		String gameTimeOnly = gameDateTime.substring(15);
		return gameTimeOnly;
	}
	
	public static String getHomeTeamScoreStyleClass(Integer ihomeTeamScore, Integer iawayTeamScore) 
	{
		String returnString = "";
		
		if (ihomeTeamScore == null || iawayTeamScore == null)
		{
			returnString = "";
		}
		else if (ihomeTeamScore > iawayTeamScore)
		{
			returnString = NflMain.GREEN_STYLECLASS;
		}
		else if (ihomeTeamScore < iawayTeamScore)
		{
			returnString = NflMain.RED_STYLECLASS;
		}
		else if (ihomeTeamScore == iawayTeamScore) //tie
		{
			returnString = NflMain.YELLOW_STYLECLASS;
		}
		
		return returnString;
	}

	public static String getAwayTeamScoreStyleClass(Integer ihomeTeamScore, Integer iawayTeamScore) 
	{
		String returnString = "";
		
		if (ihomeTeamScore == null || iawayTeamScore == null)
		{
			returnString = "";
		}
		else if (ihomeTeamScore > iawayTeamScore)
		{
			returnString = NflMain.RED_STYLECLASS;
		}
		else if (ihomeTeamScore < iawayTeamScore)
		{
			returnString = NflMain.GREEN_STYLECLASS;
		}
		else if (ihomeTeamScore == iawayTeamScore) //tie
		{
			returnString = NflMain.YELLOW_STYLECLASS;
		}
		
		return returnString;
	}
	
	public static String getGameDateTimeDisplay(String gameDateTime)
	{
		String returnString = "";
		returnString = getGameDayOfWeek(gameDateTime)  + " " + getGameDateOnly(gameDateTime) + " " + getGameTimeOnly(gameDateTime);
		return returnString;
	}

	public static List<String> getDivisionsList(List<NflTeam> teamsList) 
	{
		Map<Integer, String> divisionsMap = new HashMap<>();
		
		for (int i = 0; i < teamsList.size(); i++) 
		{
			NflTeam nflteam = teamsList.get(i);
			
			if (!divisionsMap.containsKey(nflteam.getiDivisionID()))
			{
				divisionsMap.put(nflteam.getiDivisionID(), nflteam.getvDivisionName());
			}
		}
		
		List<String> divisionsList = new ArrayList<>(divisionsMap.values());
		
		return divisionsList;
	}

	public static void updateScoreStyles(DynamoNflGame game) 
	{
		if (game.getIhomeTeamScore() == null || game.getIawayTeamScore() == null)
		{
			game.setHomeTeamScoreStyleClass("");
		}
		else if (game.getIhomeTeamScore() > game.getIawayTeamScore())
		{
			game.setHomeTeamScoreStyleClass(NflMain.GREEN_STYLECLASS);
		}
		else if (game.getIhomeTeamScore() < game.getIawayTeamScore())
		{
			game.setHomeTeamScoreStyleClass(NflMain.RED_STYLECLASS);
		}
		else if (game.getIhomeTeamScore() == game.getIawayTeamScore()) //tie
		{
			game.setHomeTeamScoreStyleClass(NflMain.YELLOW_STYLECLASS);
		}
		
		if (game.getIhomeTeamScore() == null || game.getIawayTeamScore() == null)
		{
			game.setAwayTeamScoreStyleClass("");
		}
		else if (game.getIhomeTeamScore() < game.getIawayTeamScore())
		{
			game.setAwayTeamScoreStyleClass(NflMain.GREEN_STYLECLASS);
		}
		else if (game.getIhomeTeamScore() > game.getIawayTeamScore())
		{
			game.setAwayTeamScoreStyleClass(NflMain.RED_STYLECLASS);
		}
		else if (game.getIhomeTeamScore() == game.getIawayTeamScore()) //tie
		{
			game.setAwayTeamScoreStyleClass(NflMain.YELLOW_STYLECLASS);
		}
		
	}
	
	
}
