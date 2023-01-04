package com.pas.valueObject;

import java.io.Serializable;

public class DropDownBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String Id;
	private String Description;
	
	public DropDownBean()
	{
		super();		
	}
	
	public void setId(String id)
	{
		Id = id;
	}
	
	public void setDescription(String desc)
	{
		Description = desc;
	}
	
	public String getId()
	{
		return Id;
	}
	
	public String getDescription()
	{
		return Description;
	}
}