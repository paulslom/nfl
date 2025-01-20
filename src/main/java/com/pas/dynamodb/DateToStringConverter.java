package com.pas.dynamodb;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.pas.util.Utils;

public class DateToStringConverter
{
	public static String userInputDateTimePattern = "E yyyy-MM-dd hh:mm a";
	public static String mysqlDateTimePattern = "yyyy-MM-dd HH:mm:ss.s";  //example: 2004-09-09 21:00:00.0
	public static String dynamoDbDateTimePattern = "yyyy-MM-dd'T'HH:mm:ss";
	public static String schedulePattern = "MMM-dd";
	
    public static Date unconvert(String s) 
    {
    	//Example of what we are unconverting: 2017-12-03T13:00:00
    	
    	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(dynamoDbDateTimePattern);
    	LocalDateTime ldt = LocalDateTime.parse(s, inputFormatter);
    	Date returnDate = Date.from(ldt.atZone(ZoneId.of(Utils.MY_TIME_ZONE)).toInstant());
        return returnDate;
    }
    
    public static String convertDateToDynamoStringFormat(String gamedatetimedisplay)
    {
    	//Example of what we are converting to dynamo: 2017-12-03T13:00:00
    	
    	String returnString = "";
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern(userInputDateTimePattern); 
    	LocalDateTime datetime = LocalDateTime.parse(gamedatetimedisplay, dtf); 
    	returnString = datetime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    	return returnString;
    }
    
    public static String convertMySqlDateTimeToUserInputFormat(Timestamp inputMySqlTimeStamp)
    {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern(mysqlDateTimePattern); 
    	LocalDateTime ldt = LocalDateTime.parse(inputMySqlTimeStamp.toString(), dtf);
    	DateTimeFormatter userInputFormatter = DateTimeFormatter.ofPattern(userInputDateTimePattern);
       	String returnString = ldt.format(userInputFormatter);
    	return returnString;
    }
    
    public static String convertToScheduleFormat(String s)
    {
    	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(dynamoDbDateTimePattern);
    	LocalDateTime ldt = LocalDateTime.parse(s, inputFormatter);
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern(schedulePattern);  
    	return dtf.format(ldt);
    }
    
    public static void main(String[] args)
    {
    	/*
    	String dateTimeString = "Mon 2025-01-13 08:00 PM";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(userInputDateTimePattern);  
    	LocalDateTime datetime = LocalDateTime.parse(dateTimeString, dtf); 
    	System.out.println("date format : " + datetime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    	
    	
    	String dateTimeString = "2025-01-13 20:00:00";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(mysqlDateTimePattern);  
    	LocalDateTime datetime = LocalDateTime.parse(dateTimeString, dtf); 
    	System.out.println("date format : " + datetime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    	*/    	
    	
    	String s = "2024-09-08T13:00:00";
    	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(dynamoDbDateTimePattern);
    	LocalDateTime ldt = LocalDateTime.parse(s, inputFormatter);
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern(schedulePattern);  
    	System.out.println("date format : " + dtf.format(ldt));
    	
    }
}
