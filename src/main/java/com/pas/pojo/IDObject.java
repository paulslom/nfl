package com.pas.pojo;

public class IDObject
{
	
	private String id;
	private String idDescriptor;
	
	public String toString()
	{
		StringBuffer buf = new StringBuffer();

		buf.append("ID  = " + id + "\n");
		buf.append("ID Descriptor  = " + idDescriptor + "\n");
				
		return buf.toString();
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getIdDescriptor()
	{
		return idDescriptor;
	}

	public void setIdDescriptor(String idDescriptor)
	{
		this.idDescriptor = idDescriptor;
	}
		
}