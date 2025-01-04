package com.pas.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pas.pojo.Decade;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

public class Utils 
{
	private static Logger logger = LogManager.getLogger(Utils.class);	
		
	public static String MY_TIME_ZONE = "America/New_York";
		
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
		String currentCamGroupUser = (String) session.getAttribute("currentUser");
		
		currentCamGroupUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
	    return currentCamGroupUser == null ? null : currentCamGroupUser.toLowerCase().trim();
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
	
			
}
