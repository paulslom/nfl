package com.pas.nfl.DBObjects;

import java.io.Serializable;

public class TblDivision implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private Integer idivisionId;
	private Integer iconferenceId;
    private String vdivisionName;
    private String vdivisionLifespan;
    
    private TblConference conference;
    
	public Integer getIdivisionId() {
		return idivisionId;
	}
	public void setIdivisionId(Integer idivisionId) {
		this.idivisionId = idivisionId;
	}
	public Integer getIconferenceId() {
		return iconferenceId;
	}
	public void setIconferenceId(Integer iconferenceId) {
		this.iconferenceId = iconferenceId;
	}
	public String getVdivisionName() {
		return vdivisionName;
	}
	public void setVdivisionName(String vdivisionName) {
		this.vdivisionName = vdivisionName;
	}
	public String getVdivisionLifespan() {
		return vdivisionLifespan;
	}
	public void setVdivisionLifespan(String vdivisionLifespan) {
		this.vdivisionLifespan = vdivisionLifespan;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public TblConference getConference() {
		return conference;
	}
	public void setConference(TblConference conference) {
		this.conference = conference;
	}


}
