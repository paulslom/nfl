package com.pas.util;

import java.util.*;
import java.util.GregorianCalendar;

/**
 * Insert the type's description here.
 * Creation date: (11/16/00 9:21:16 AM)
 * @author: Administrator
 */
public class VIPDate {
private short DD;
private short MM;
private short YYYY;
private java.util.Date Date;
private java.util.GregorianCalendar GregorianDate;
private boolean isValid;

/**
 * IsValidDate constructor comment.
 */
public VIPDate() {
	super();
	this.isValid = true;
	try 
	{
		GregorianCalendar d = new GregorianCalendar();
		this.GregorianDate = d;
		this.YYYY = (short) Calendar.YEAR;
		//fix for 00 being entered as a date, if 00 GregorianDate gets converted to 1 anyway
		if (this.YYYY == 0) this.YYYY = 1;
		this.MM =   (short) Calendar.MONTH;
		this.DD =   (short) Calendar.DAY_OF_MONTH;
		this.Date = d.getTime();
		this.validateDate();
	}
	catch (Exception e)
	{
		this.isValid = false;
		return;
	}	
}
public VIPDate(int YYYY,int MM,int DD) {
	this.isValid = true;
	try 
	{
		this.YYYY = (short) YYYY;
		//fix for 00 being entered as a date, if 00 GregorianDate gets converted to 1 anyway
		if (this.YYYY == 0) this.YYYY = 1;
		this.MM = (short) MM;
		this.DD = (short) DD;
		GregorianCalendar d = new GregorianCalendar();
		d.set(YYYY, MM-1, DD);
		this.GregorianDate = d;
		this.validateDate();
	}
	catch (Exception e)
	{
		this.isValid = false;
		return;
	}	
}
public VIPDate(Object YYYYo,Object MMo,Object DDo) {
	this.isValid = true;
	try 
	{
		String YYYY = YYYYo.toString();
		String MM = MMo.toString();
		String DD = DDo.toString();
		this.YYYY = new Integer(YYYY).shortValue();
		//fix for 00 being entered as a date, if 00 GregorianDate gets converted to 1 anyway
		if (this.YYYY == 0) this.YYYY = 1;
		this.MM = new Integer(MM).shortValue();
		this.DD = new Integer(DD).shortValue();
		GregorianCalendar d = new GregorianCalendar();
		d.set(this.YYYY, this.MM-1, this.DD);
		this.GregorianDate = d;
		this.validateDate();
	}
	catch (Exception e)
	{
		this.isValid = false;
		return;
	}	
}
public VIPDate(String YYYY,String MM,String DD) {
	this.isValid = true;
	try 
	{
		this.YYYY = new Integer(YYYY).shortValue();
		//fix for 00 being entered as a date, if 00 GregorianDate gets converted to 1 anyway
		if (this.YYYY == 0) this.YYYY = 1;
		this.MM = new Integer(MM).shortValue();
		this.DD = new Integer(DD).shortValue();
		GregorianCalendar d = new GregorianCalendar();
		d.set(this.YYYY, this.MM-1, this.DD);
		this.GregorianDate = d;
		this.validateDate();		
	}
	catch (Exception e)
	{
		this.isValid = false;
		return;
	}	
}
public VIPDate(java.util.Date date) {
	this.isValid = true;
	try 
	{
		this.Date = date;
		this.GregorianDate.setTime(date);	
		this.YYYY = (short) Calendar.YEAR;
		//fix for 00 being entered as a date, if 00 GregorianDate gets converted to 1 anyway
		if (this.YYYY == 0) this.YYYY = 1;
		this.MM =   (short) Calendar.MONTH;
		this.DD =   (short) Calendar.DAY_OF_MONTH;
		GregorianCalendar d = new GregorianCalendar();
		d.set(this.YYYY, this.MM -1 , this.DD);
		this.GregorianDate = d;
		this.validateDate();
	}
	catch (Exception e)
	{
		this.isValid = false;
		return;
	}	
}
public VIPDate(short YYYY,short MM,short DD) {
	this.isValid = true;
	try 
	{
		this.YYYY = YYYY;
		//fix for 00 being entered as a date, if 00 GregorianDate gets converted to 1 anyway
		if (this.YYYY == 0) this.YYYY = 1;
		this.MM = MM;
		this.DD = DD;
		GregorianCalendar d = new GregorianCalendar();
		d.set(YYYY, MM-1, DD);
		this.GregorianDate = d;
		this.validateDate();
	}
	catch (Exception e)
	{
		this.isValid = false;
		return;
	}	
}
public Date GetDate() {
return this.Date;
}
public int GetDay() {
return this.DD;
}
public GregorianCalendar GetGregorianDate() {
return this.GregorianDate;
}
public int GetMonth() {
return this.MM;
}
public int GetYear() {
return this.YYYY;
}
public boolean IsAValidFourDigitYear() {
	return (YYYY > 1800);
}
public boolean IsFourDigitYear() {
	return (YYYY > 999);
}
public boolean IsGreaterThanToday() {
Calendar now = Calendar.getInstance();
return this.GregorianDate.after(now);
}
public boolean IsGreaterThanXYears(int years) {
Calendar then = Calendar.getInstance();
then.add(Calendar.YEAR, - years);
return then.after(this.GregorianDate);
}
public boolean IsInValid() {
	return !this.isValid;
}
public boolean IsLessThanToday() {
Calendar now = Calendar.getInstance();
//return now.after(this.GregorianDate);
return now.after(this.GregorianDate);
}
public boolean IsLessThanXYears(int years) {
Calendar then = Calendar.getInstance();
then.add(Calendar.YEAR, - years);
return this.GregorianDate.after(then);
}
public boolean IsValid() {
	return this.isValid;
}
private void validateDate() {
if ((GregorianDate.get(Calendar.DAY_OF_MONTH) ==  DD) &&
	(GregorianDate.get(Calendar.MONTH) == MM-1) && 
	(GregorianDate.get(Calendar.YEAR) == YYYY)) 
{
	//do nada
	this.isValid = true;
}
else	
{
	this.isValid = false;
}
}
}