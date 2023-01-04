package com.pas.nfl.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/** 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PlayoffBracketsForm extends NFLBaseActionForm
{
	private static final long serialVersionUID = 1L;

	private String bracketTitle;
	
	private Boolean threeWildCardGames = false;
	
	private String afcChampionSeed;
	private String afcChampionTeamAbbr;
	private String afcChampionPicture;
	private String afcChampionScore;
	
	private String nfcChampionSeed;
	private String nfcChampionTeamAbbr;
	private String nfcChampionPicture;
	private String nfcChampionScore;
	
	private String afcChampionshipRoadSeed;
	private String afcChampionshipRoadTeamAbbr;
	private String afcChampionshipRoadPicture;
	private String afcChampionshipRoadScore;
	
	private String afcChampionshipHomeSeed;
	private String afcChampionshipHomeTeamAbbr;
	private String afcChampionshipHomePicture;
	private String afcChampionshipHomeScore;
	
	private String nfcChampionshipRoadSeed;
	private String nfcChampionshipRoadTeamAbbr;
	private String nfcChampionshipRoadPicture;
	private String nfcChampionshipRoadScore;
	
	private String nfcChampionshipHomeSeed;
	private String nfcChampionshipHomeTeamAbbr;
	private String nfcChampionshipHomePicture;
	private String nfcChampionshipHomeScore;	

	private String afcDivisional1RoadSeed;
	private String afcDivisional1RoadTeamAbbr;
	private String afcDivisional1RoadPicture;
	private String afcDivisional1RoadScore;
	
	private String afcDivisional1HomeSeed;
	private String afcDivisional1HomeTeamAbbr;
	private String afcDivisional1HomePicture;
	private String afcDivisional1HomeScore;
	
	private String nfcDivisional1RoadSeed;
	private String nfcDivisional1RoadTeamAbbr;
	private String nfcDivisional1RoadPicture;
	private String nfcDivisional1RoadScore;
	
	private String nfcDivisional1HomeSeed;
	private String nfcDivisional1HomeTeamAbbr;
	private String nfcDivisional1HomePicture;
	private String nfcDivisional1HomeScore;
	
	private String afcDivisional2RoadSeed;
	private String afcDivisional2RoadTeamAbbr;
	private String afcDivisional2RoadPicture;
	private String afcDivisional2RoadScore;
	
	private String afcDivisional2HomeSeed;
	private String afcDivisional2HomeTeamAbbr;
	private String afcDivisional2HomePicture;
	private String afcDivisional2HomeScore;
	
	private String nfcDivisional2RoadSeed;
	private String nfcDivisional2RoadTeamAbbr;
	private String nfcDivisional2RoadPicture;
	private String nfcDivisional2RoadScore;
	
	private String nfcDivisional2HomeSeed;
	private String nfcDivisional2HomeTeamAbbr;
	private String nfcDivisional2HomePicture;
	private String nfcDivisional2HomeScore;
		
	private String afcWildCard1RoadSeed;
	private String afcWildCard1RoadTeamAbbr;
	private String afcWildCard1RoadPicture;
	private String afcWildCard1RoadScore;
	
	private String afcWildCard1HomeSeed;
	private String afcWildCard1HomeTeamAbbr;
	private String afcWildCard1HomePicture;
	private String afcWildCard1HomeScore;
	
	private String nfcWildCard1RoadSeed;
	private String nfcWildCard1RoadTeamAbbr;
	private String nfcWildCard1RoadPicture;
	private String nfcWildCard1RoadScore;
	
	private String nfcWildCard1HomeSeed;
	private String nfcWildCard1HomeTeamAbbr;
	private String nfcWildCard1HomePicture;
	private String nfcWildCard1HomeScore;
	
	private String afcWildCard2RoadSeed;
	private String afcWildCard2RoadTeamAbbr;
	private String afcWildCard2RoadPicture;
	private String afcWildCard2RoadScore;
	
	private String afcWildCard2HomeSeed;
	private String afcWildCard2HomeTeamAbbr;
	private String afcWildCard2HomePicture;
	private String afcWildCard2HomeScore;
	
	private String nfcWildCard2RoadSeed;
	private String nfcWildCard2RoadTeamAbbr;
	private String nfcWildCard2RoadPicture;
	private String nfcWildCard2RoadScore;
	
	private String nfcWildCard2HomeSeed;
	private String nfcWildCard2HomeTeamAbbr;
	private String nfcWildCard2HomePicture;
	private String nfcWildCard2HomeScore;
	
	private String afcWildCard3RoadSeed;
	private String afcWildCard3RoadTeamAbbr;
	private String afcWildCard3RoadPicture;
	private String afcWildCard3RoadScore;
	
	private String afcWildCard3HomeSeed;
	private String afcWildCard3HomeTeamAbbr;
	private String afcWildCard3HomePicture;
	private String afcWildCard3HomeScore;
	
	private String nfcWildCard3RoadSeed;
	private String nfcWildCard3RoadTeamAbbr;
	private String nfcWildCard3RoadPicture;
	private String nfcWildCard3RoadScore;
	
	private String nfcWildCard3HomeSeed;
	private String nfcWildCard3HomeTeamAbbr;
	private String nfcWildCard3HomePicture;
	private String nfcWildCard3HomeScore;
	
	public PlayoffBracketsForm()
	{		
	}
		
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request)
	{

		String methodName = "validate :: ";
		log.debug(methodName + " In");		
		
		ActionErrors ae = new ActionErrors();
				
		return ae;

	}

	public String getBracketTitle() {
		return bracketTitle;
	}

	public void setBracketTitle(String bracketTitle) {
		this.bracketTitle = bracketTitle;
	}

	public String getAfcChampionSeed() {
		return afcChampionSeed;
	}

	public void setAfcChampionSeed(String afcChampionSeed) {
		this.afcChampionSeed = afcChampionSeed;
	}

	public String getAfcChampionTeamAbbr() {
		return afcChampionTeamAbbr;
	}

	public void setAfcChampionTeamAbbr(String afcChampionTeamAbbr) {
		this.afcChampionTeamAbbr = afcChampionTeamAbbr;
	}

	public String getAfcChampionPicture() {
		return afcChampionPicture;
	}

	public void setAfcChampionPicture(String afcChampionPicture) {
		this.afcChampionPicture = afcChampionPicture;
	}

	public String getAfcChampionScore() {
		return afcChampionScore;
	}

	public void setAfcChampionScore(String afcChampionScore) {
		this.afcChampionScore = afcChampionScore;
	}

	public String getNfcChampionSeed() {
		return nfcChampionSeed;
	}

	public void setNfcChampionSeed(String nfcChampionSeed) {
		this.nfcChampionSeed = nfcChampionSeed;
	}

	public String getNfcChampionTeamAbbr() {
		return nfcChampionTeamAbbr;
	}

	public void setNfcChampionTeamAbbr(String nfcChampionTeamAbbr) {
		this.nfcChampionTeamAbbr = nfcChampionTeamAbbr;
	}

	public String getNfcChampionPicture() {
		return nfcChampionPicture;
	}

	public void setNfcChampionPicture(String nfcChampionPicture) {
		this.nfcChampionPicture = nfcChampionPicture;
	}

	public String getNfcChampionScore() {
		return nfcChampionScore;
	}

	public void setNfcChampionScore(String nfcChampionScore) {
		this.nfcChampionScore = nfcChampionScore;
	}

	public String getAfcChampionshipRoadSeed() {
		return afcChampionshipRoadSeed;
	}

	public void setAfcChampionshipRoadSeed(String afcChampionshipRoadSeed) {
		this.afcChampionshipRoadSeed = afcChampionshipRoadSeed;
	}

	public String getAfcChampionshipRoadTeamAbbr() {
		return afcChampionshipRoadTeamAbbr;
	}

	public void setAfcChampionshipRoadTeamAbbr(String afcChampionshipRoadTeamAbbr) {
		this.afcChampionshipRoadTeamAbbr = afcChampionshipRoadTeamAbbr;
	}

	public String getAfcChampionshipRoadPicture() {
		return afcChampionshipRoadPicture;
	}

	public void setAfcChampionshipRoadPicture(String afcChampionshipRoadPicture) {
		this.afcChampionshipRoadPicture = afcChampionshipRoadPicture;
	}

	public String getAfcChampionshipRoadScore() {
		return afcChampionshipRoadScore;
	}

	public void setAfcChampionshipRoadScore(String afcChampionshipRoadScore) {
		this.afcChampionshipRoadScore = afcChampionshipRoadScore;
	}

	public String getAfcChampionshipHomeSeed() {
		return afcChampionshipHomeSeed;
	}

	public void setAfcChampionshipHomeSeed(String afcChampionshipHomeSeed) {
		this.afcChampionshipHomeSeed = afcChampionshipHomeSeed;
	}

	public String getAfcChampionshipHomeTeamAbbr() {
		return afcChampionshipHomeTeamAbbr;
	}

	public void setAfcChampionshipHomeTeamAbbr(String afcChampionshipHomeTeamAbbr) {
		this.afcChampionshipHomeTeamAbbr = afcChampionshipHomeTeamAbbr;
	}

	public String getAfcChampionshipHomePicture() {
		return afcChampionshipHomePicture;
	}

	public void setAfcChampionshipHomePicture(String afcChampionshipHomePicture) {
		this.afcChampionshipHomePicture = afcChampionshipHomePicture;
	}

	public String getAfcChampionshipHomeScore() {
		return afcChampionshipHomeScore;
	}

	public void setAfcChampionshipHomeScore(String afcChampionshipHomeScore) {
		this.afcChampionshipHomeScore = afcChampionshipHomeScore;
	}

	public String getNfcChampionshipRoadSeed() {
		return nfcChampionshipRoadSeed;
	}

	public void setNfcChampionshipRoadSeed(String nfcChampionshipRoadSeed) {
		this.nfcChampionshipRoadSeed = nfcChampionshipRoadSeed;
	}

	public String getNfcChampionshipRoadTeamAbbr() {
		return nfcChampionshipRoadTeamAbbr;
	}

	public void setNfcChampionshipRoadTeamAbbr(String nfcChampionshipRoadTeamAbbr) {
		this.nfcChampionshipRoadTeamAbbr = nfcChampionshipRoadTeamAbbr;
	}

	public String getNfcChampionshipRoadPicture() {
		return nfcChampionshipRoadPicture;
	}

	public void setNfcChampionshipRoadPicture(String nfcChampionshipRoadPicture) {
		this.nfcChampionshipRoadPicture = nfcChampionshipRoadPicture;
	}

	public String getNfcChampionshipRoadScore() {
		return nfcChampionshipRoadScore;
	}

	public void setNfcChampionshipRoadScore(String nfcChampionshipRoadScore) {
		this.nfcChampionshipRoadScore = nfcChampionshipRoadScore;
	}

	public String getNfcChampionshipHomeSeed() {
		return nfcChampionshipHomeSeed;
	}

	public void setNfcChampionshipHomeSeed(String nfcChampionshipHomeSeed) {
		this.nfcChampionshipHomeSeed = nfcChampionshipHomeSeed;
	}

	public String getNfcChampionshipHomeTeamAbbr() {
		return nfcChampionshipHomeTeamAbbr;
	}

	public void setNfcChampionshipHomeTeamAbbr(String nfcChampionshipHomeTeamAbbr) {
		this.nfcChampionshipHomeTeamAbbr = nfcChampionshipHomeTeamAbbr;
	}

	public String getNfcChampionshipHomePicture() {
		return nfcChampionshipHomePicture;
	}

	public void setNfcChampionshipHomePicture(String nfcChampionshipHomePicture) {
		this.nfcChampionshipHomePicture = nfcChampionshipHomePicture;
	}

	public String getNfcChampionshipHomeScore() {
		return nfcChampionshipHomeScore;
	}

	public void setNfcChampionshipHomeScore(String nfcChampionshipHomeScore) {
		this.nfcChampionshipHomeScore = nfcChampionshipHomeScore;
	}

	public String getAfcDivisional1RoadSeed() {
		return afcDivisional1RoadSeed;
	}

	public void setAfcDivisional1RoadSeed(String afcDivisional1RoadSeed) {
		this.afcDivisional1RoadSeed = afcDivisional1RoadSeed;
	}

	public String getAfcDivisional1RoadTeamAbbr() {
		return afcDivisional1RoadTeamAbbr;
	}

	public void setAfcDivisional1RoadTeamAbbr(String afcDivisional1RoadTeamAbbr) {
		this.afcDivisional1RoadTeamAbbr = afcDivisional1RoadTeamAbbr;
	}

	public String getAfcDivisional1RoadPicture() {
		return afcDivisional1RoadPicture;
	}

	public void setAfcDivisional1RoadPicture(String afcDivisional1RoadPicture) {
		this.afcDivisional1RoadPicture = afcDivisional1RoadPicture;
	}

	public String getAfcDivisional1RoadScore() {
		return afcDivisional1RoadScore;
	}

	public void setAfcDivisional1RoadScore(String afcDivisional1RoadScore) {
		this.afcDivisional1RoadScore = afcDivisional1RoadScore;
	}

	public String getAfcDivisional1HomeSeed() {
		return afcDivisional1HomeSeed;
	}

	public void setAfcDivisional1HomeSeed(String afcDivisional1HomeSeed) {
		this.afcDivisional1HomeSeed = afcDivisional1HomeSeed;
	}

	public String getAfcDivisional1HomeTeamAbbr() {
		return afcDivisional1HomeTeamAbbr;
	}

	public void setAfcDivisional1HomeTeamAbbr(String afcDivisional1HomeTeamAbbr) {
		this.afcDivisional1HomeTeamAbbr = afcDivisional1HomeTeamAbbr;
	}

	public String getAfcDivisional1HomePicture() {
		return afcDivisional1HomePicture;
	}

	public void setAfcDivisional1HomePicture(String afcDivisional1HomePicture) {
		this.afcDivisional1HomePicture = afcDivisional1HomePicture;
	}

	public String getAfcDivisional1HomeScore() {
		return afcDivisional1HomeScore;
	}

	public void setAfcDivisional1HomeScore(String afcDivisional1HomeScore) {
		this.afcDivisional1HomeScore = afcDivisional1HomeScore;
	}

	public String getNfcDivisional1RoadSeed() {
		return nfcDivisional1RoadSeed;
	}

	public void setNfcDivisional1RoadSeed(String nfcDivisional1RoadSeed) {
		this.nfcDivisional1RoadSeed = nfcDivisional1RoadSeed;
	}

	public String getNfcDivisional1RoadTeamAbbr() {
		return nfcDivisional1RoadTeamAbbr;
	}

	public void setNfcDivisional1RoadTeamAbbr(String nfcDivisional1RoadTeamAbbr) {
		this.nfcDivisional1RoadTeamAbbr = nfcDivisional1RoadTeamAbbr;
	}

	public String getNfcDivisional1RoadPicture() {
		return nfcDivisional1RoadPicture;
	}

	public void setNfcDivisional1RoadPicture(String nfcDivisional1RoadPicture) {
		this.nfcDivisional1RoadPicture = nfcDivisional1RoadPicture;
	}

	public String getNfcDivisional1RoadScore() {
		return nfcDivisional1RoadScore;
	}

	public void setNfcDivisional1RoadScore(String nfcDivisional1RoadScore) {
		this.nfcDivisional1RoadScore = nfcDivisional1RoadScore;
	}

	public String getNfcDivisional1HomeSeed() {
		return nfcDivisional1HomeSeed;
	}

	public void setNfcDivisional1HomeSeed(String nfcDivisional1HomeSeed) {
		this.nfcDivisional1HomeSeed = nfcDivisional1HomeSeed;
	}

	public String getNfcDivisional1HomeTeamAbbr() {
		return nfcDivisional1HomeTeamAbbr;
	}

	public void setNfcDivisional1HomeTeamAbbr(String nfcDivisional1HomeTeamAbbr) {
		this.nfcDivisional1HomeTeamAbbr = nfcDivisional1HomeTeamAbbr;
	}

	public String getNfcDivisional1HomePicture() {
		return nfcDivisional1HomePicture;
	}

	public void setNfcDivisional1HomePicture(String nfcDivisional1HomePicture) {
		this.nfcDivisional1HomePicture = nfcDivisional1HomePicture;
	}

	public String getNfcDivisional1HomeScore() {
		return nfcDivisional1HomeScore;
	}

	public void setNfcDivisional1HomeScore(String nfcDivisional1HomeScore) {
		this.nfcDivisional1HomeScore = nfcDivisional1HomeScore;
	}

	public String getAfcDivisional2RoadSeed() {
		return afcDivisional2RoadSeed;
	}

	public void setAfcDivisional2RoadSeed(String afcDivisional2RoadSeed) {
		this.afcDivisional2RoadSeed = afcDivisional2RoadSeed;
	}

	public String getAfcDivisional2RoadTeamAbbr() {
		return afcDivisional2RoadTeamAbbr;
	}

	public void setAfcDivisional2RoadTeamAbbr(String afcDivisional2RoadTeamAbbr) {
		this.afcDivisional2RoadTeamAbbr = afcDivisional2RoadTeamAbbr;
	}

	public String getAfcDivisional2RoadPicture() {
		return afcDivisional2RoadPicture;
	}

	public void setAfcDivisional2RoadPicture(String afcDivisional2RoadPicture) {
		this.afcDivisional2RoadPicture = afcDivisional2RoadPicture;
	}

	public String getAfcDivisional2RoadScore() {
		return afcDivisional2RoadScore;
	}

	public void setAfcDivisional2RoadScore(String afcDivisional2RoadScore) {
		this.afcDivisional2RoadScore = afcDivisional2RoadScore;
	}

	public String getAfcDivisional2HomeSeed() {
		return afcDivisional2HomeSeed;
	}

	public void setAfcDivisional2HomeSeed(String afcDivisional2HomeSeed) {
		this.afcDivisional2HomeSeed = afcDivisional2HomeSeed;
	}

	public String getAfcDivisional2HomeTeamAbbr() {
		return afcDivisional2HomeTeamAbbr;
	}

	public void setAfcDivisional2HomeTeamAbbr(String afcDivisional2HomeTeamAbbr) {
		this.afcDivisional2HomeTeamAbbr = afcDivisional2HomeTeamAbbr;
	}

	public String getAfcDivisional2HomePicture() {
		return afcDivisional2HomePicture;
	}

	public void setAfcDivisional2HomePicture(String afcDivisional2HomePicture) {
		this.afcDivisional2HomePicture = afcDivisional2HomePicture;
	}

	public String getAfcDivisional2HomeScore() {
		return afcDivisional2HomeScore;
	}

	public void setAfcDivisional2HomeScore(String afcDivisional2HomeScore) {
		this.afcDivisional2HomeScore = afcDivisional2HomeScore;
	}

	public String getNfcDivisional2RoadSeed() {
		return nfcDivisional2RoadSeed;
	}

	public void setNfcDivisional2RoadSeed(String nfcDivisional2RoadSeed) {
		this.nfcDivisional2RoadSeed = nfcDivisional2RoadSeed;
	}

	public String getNfcDivisional2RoadTeamAbbr() {
		return nfcDivisional2RoadTeamAbbr;
	}

	public void setNfcDivisional2RoadTeamAbbr(String nfcDivisional2RoadTeamAbbr) {
		this.nfcDivisional2RoadTeamAbbr = nfcDivisional2RoadTeamAbbr;
	}

	public String getNfcDivisional2RoadPicture() {
		return nfcDivisional2RoadPicture;
	}

	public void setNfcDivisional2RoadPicture(String nfcDivisional2RoadPicture) {
		this.nfcDivisional2RoadPicture = nfcDivisional2RoadPicture;
	}

	public String getNfcDivisional2RoadScore() {
		return nfcDivisional2RoadScore;
	}

	public void setNfcDivisional2RoadScore(String nfcDivisional2RoadScore) {
		this.nfcDivisional2RoadScore = nfcDivisional2RoadScore;
	}

	public String getNfcDivisional2HomeSeed() {
		return nfcDivisional2HomeSeed;
	}

	public void setNfcDivisional2HomeSeed(String nfcDivisional2HomeSeed) {
		this.nfcDivisional2HomeSeed = nfcDivisional2HomeSeed;
	}

	public String getNfcDivisional2HomeTeamAbbr() {
		return nfcDivisional2HomeTeamAbbr;
	}

	public void setNfcDivisional2HomeTeamAbbr(String nfcDivisional2HomeTeamAbbr) {
		this.nfcDivisional2HomeTeamAbbr = nfcDivisional2HomeTeamAbbr;
	}

	public String getNfcDivisional2HomePicture() {
		return nfcDivisional2HomePicture;
	}

	public void setNfcDivisional2HomePicture(String nfcDivisional2HomePicture) {
		this.nfcDivisional2HomePicture = nfcDivisional2HomePicture;
	}

	public String getNfcDivisional2HomeScore() {
		return nfcDivisional2HomeScore;
	}

	public void setNfcDivisional2HomeScore(String nfcDivisional2HomeScore) {
		this.nfcDivisional2HomeScore = nfcDivisional2HomeScore;
	}

	public String getAfcWildCard1RoadSeed() {
		return afcWildCard1RoadSeed;
	}

	public void setAfcWildCard1RoadSeed(String afcWildCard1RoadSeed) {
		this.afcWildCard1RoadSeed = afcWildCard1RoadSeed;
	}

	public String getAfcWildCard1RoadTeamAbbr() {
		return afcWildCard1RoadTeamAbbr;
	}

	public void setAfcWildCard1RoadTeamAbbr(String afcWildCard1RoadTeamAbbr) {
		this.afcWildCard1RoadTeamAbbr = afcWildCard1RoadTeamAbbr;
	}

	public String getAfcWildCard1RoadPicture() {
		return afcWildCard1RoadPicture;
	}

	public void setAfcWildCard1RoadPicture(String afcWildCard1RoadPicture) {
		this.afcWildCard1RoadPicture = afcWildCard1RoadPicture;
	}

	public String getAfcWildCard1RoadScore() {
		return afcWildCard1RoadScore;
	}

	public void setAfcWildCard1RoadScore(String afcWildCard1RoadScore) {
		this.afcWildCard1RoadScore = afcWildCard1RoadScore;
	}

	public String getAfcWildCard1HomeSeed() {
		return afcWildCard1HomeSeed;
	}

	public void setAfcWildCard1HomeSeed(String afcWildCard1HomeSeed) {
		this.afcWildCard1HomeSeed = afcWildCard1HomeSeed;
	}

	public String getAfcWildCard1HomeTeamAbbr() {
		return afcWildCard1HomeTeamAbbr;
	}

	public void setAfcWildCard1HomeTeamAbbr(String afcWildCard1HomeTeamAbbr) {
		this.afcWildCard1HomeTeamAbbr = afcWildCard1HomeTeamAbbr;
	}

	public String getAfcWildCard1HomePicture() {
		return afcWildCard1HomePicture;
	}

	public void setAfcWildCard1HomePicture(String afcWildCard1HomePicture) {
		this.afcWildCard1HomePicture = afcWildCard1HomePicture;
	}

	public String getAfcWildCard1HomeScore() {
		return afcWildCard1HomeScore;
	}

	public void setAfcWildCard1HomeScore(String afcWildCard1HomeScore) {
		this.afcWildCard1HomeScore = afcWildCard1HomeScore;
	}

	public String getNfcWildCard1RoadSeed() {
		return nfcWildCard1RoadSeed;
	}

	public void setNfcWildCard1RoadSeed(String nfcWildCard1RoadSeed) {
		this.nfcWildCard1RoadSeed = nfcWildCard1RoadSeed;
	}

	public String getNfcWildCard1RoadTeamAbbr() {
		return nfcWildCard1RoadTeamAbbr;
	}

	public void setNfcWildCard1RoadTeamAbbr(String nfcWildCard1RoadTeamAbbr) {
		this.nfcWildCard1RoadTeamAbbr = nfcWildCard1RoadTeamAbbr;
	}

	public String getNfcWildCard1RoadPicture() {
		return nfcWildCard1RoadPicture;
	}

	public void setNfcWildCard1RoadPicture(String nfcWildCard1RoadPicture) {
		this.nfcWildCard1RoadPicture = nfcWildCard1RoadPicture;
	}

	public String getNfcWildCard1RoadScore() {
		return nfcWildCard1RoadScore;
	}

	public void setNfcWildCard1RoadScore(String nfcWildCard1RoadScore) {
		this.nfcWildCard1RoadScore = nfcWildCard1RoadScore;
	}

	public String getNfcWildCard1HomeSeed() {
		return nfcWildCard1HomeSeed;
	}

	public void setNfcWildCard1HomeSeed(String nfcWildCard1HomeSeed) {
		this.nfcWildCard1HomeSeed = nfcWildCard1HomeSeed;
	}

	public String getNfcWildCard1HomeTeamAbbr() {
		return nfcWildCard1HomeTeamAbbr;
	}

	public void setNfcWildCard1HomeTeamAbbr(String nfcWildCard1HomeTeamAbbr) {
		this.nfcWildCard1HomeTeamAbbr = nfcWildCard1HomeTeamAbbr;
	}

	public String getNfcWildCard1HomePicture() {
		return nfcWildCard1HomePicture;
	}

	public void setNfcWildCard1HomePicture(String nfcWildCard1HomePicture) {
		this.nfcWildCard1HomePicture = nfcWildCard1HomePicture;
	}

	public String getNfcWildCard1HomeScore() {
		return nfcWildCard1HomeScore;
	}

	public void setNfcWildCard1HomeScore(String nfcWildCard1HomeScore) {
		this.nfcWildCard1HomeScore = nfcWildCard1HomeScore;
	}

	public String getAfcWildCard2RoadSeed() {
		return afcWildCard2RoadSeed;
	}

	public void setAfcWildCard2RoadSeed(String afcWildCard2RoadSeed) {
		this.afcWildCard2RoadSeed = afcWildCard2RoadSeed;
	}

	public String getAfcWildCard2RoadTeamAbbr() {
		return afcWildCard2RoadTeamAbbr;
	}

	public void setAfcWildCard2RoadTeamAbbr(String afcWildCard2RoadTeamAbbr) {
		this.afcWildCard2RoadTeamAbbr = afcWildCard2RoadTeamAbbr;
	}

	public String getAfcWildCard2RoadPicture() {
		return afcWildCard2RoadPicture;
	}

	public void setAfcWildCard2RoadPicture(String afcWildCard2RoadPicture) {
		this.afcWildCard2RoadPicture = afcWildCard2RoadPicture;
	}

	public String getAfcWildCard2RoadScore() {
		return afcWildCard2RoadScore;
	}

	public void setAfcWildCard2RoadScore(String afcWildCard2RoadScore) {
		this.afcWildCard2RoadScore = afcWildCard2RoadScore;
	}

	public String getAfcWildCard2HomeSeed() {
		return afcWildCard2HomeSeed;
	}

	public void setAfcWildCard2HomeSeed(String afcWildCard2HomeSeed) {
		this.afcWildCard2HomeSeed = afcWildCard2HomeSeed;
	}

	public String getAfcWildCard2HomeTeamAbbr() {
		return afcWildCard2HomeTeamAbbr;
	}

	public void setAfcWildCard2HomeTeamAbbr(String afcWildCard2HomeTeamAbbr) {
		this.afcWildCard2HomeTeamAbbr = afcWildCard2HomeTeamAbbr;
	}

	public String getAfcWildCard2HomePicture() {
		return afcWildCard2HomePicture;
	}

	public void setAfcWildCard2HomePicture(String afcWildCard2HomePicture) {
		this.afcWildCard2HomePicture = afcWildCard2HomePicture;
	}

	public String getAfcWildCard2HomeScore() {
		return afcWildCard2HomeScore;
	}

	public void setAfcWildCard2HomeScore(String afcWildCard2HomeScore) {
		this.afcWildCard2HomeScore = afcWildCard2HomeScore;
	}

	public String getNfcWildCard2RoadSeed() {
		return nfcWildCard2RoadSeed;
	}

	public void setNfcWildCard2RoadSeed(String nfcWildCard2RoadSeed) {
		this.nfcWildCard2RoadSeed = nfcWildCard2RoadSeed;
	}

	public String getNfcWildCard2RoadTeamAbbr() {
		return nfcWildCard2RoadTeamAbbr;
	}

	public void setNfcWildCard2RoadTeamAbbr(String nfcWildCard2RoadTeamAbbr) {
		this.nfcWildCard2RoadTeamAbbr = nfcWildCard2RoadTeamAbbr;
	}

	public String getNfcWildCard2RoadPicture() {
		return nfcWildCard2RoadPicture;
	}

	public void setNfcWildCard2RoadPicture(String nfcWildCard2RoadPicture) {
		this.nfcWildCard2RoadPicture = nfcWildCard2RoadPicture;
	}

	public String getNfcWildCard2RoadScore() {
		return nfcWildCard2RoadScore;
	}

	public void setNfcWildCard2RoadScore(String nfcWildCard2RoadScore) {
		this.nfcWildCard2RoadScore = nfcWildCard2RoadScore;
	}

	public String getNfcWildCard2HomeSeed() {
		return nfcWildCard2HomeSeed;
	}

	public void setNfcWildCard2HomeSeed(String nfcWildCard2HomeSeed) {
		this.nfcWildCard2HomeSeed = nfcWildCard2HomeSeed;
	}

	public String getNfcWildCard2HomeTeamAbbr() {
		return nfcWildCard2HomeTeamAbbr;
	}

	public void setNfcWildCard2HomeTeamAbbr(String nfcWildCard2HomeTeamAbbr) {
		this.nfcWildCard2HomeTeamAbbr = nfcWildCard2HomeTeamAbbr;
	}

	public String getNfcWildCard2HomePicture() {
		return nfcWildCard2HomePicture;
	}

	public void setNfcWildCard2HomePicture(String nfcWildCard2HomePicture) {
		this.nfcWildCard2HomePicture = nfcWildCard2HomePicture;
	}

	public String getNfcWildCard2HomeScore() {
		return nfcWildCard2HomeScore;
	}

	public void setNfcWildCard2HomeScore(String nfcWildCard2HomeScore) {
		this.nfcWildCard2HomeScore = nfcWildCard2HomeScore;
	}

	public String getAfcWildCard3RoadSeed() {
		return afcWildCard3RoadSeed;
	}

	public void setAfcWildCard3RoadSeed(String afcWildCard3RoadSeed) {
		this.afcWildCard3RoadSeed = afcWildCard3RoadSeed;
	}

	public String getAfcWildCard3RoadTeamAbbr() {
		return afcWildCard3RoadTeamAbbr;
	}

	public void setAfcWildCard3RoadTeamAbbr(String afcWildCard3RoadTeamAbbr) {
		this.afcWildCard3RoadTeamAbbr = afcWildCard3RoadTeamAbbr;
	}

	public String getAfcWildCard3RoadPicture() {
		return afcWildCard3RoadPicture;
	}

	public void setAfcWildCard3RoadPicture(String afcWildCard3RoadPicture) {
		this.afcWildCard3RoadPicture = afcWildCard3RoadPicture;
	}

	public String getAfcWildCard3RoadScore() {
		return afcWildCard3RoadScore;
	}

	public void setAfcWildCard3RoadScore(String afcWildCard3RoadScore) {
		this.afcWildCard3RoadScore = afcWildCard3RoadScore;
	}

	public String getAfcWildCard3HomeSeed() {
		return afcWildCard3HomeSeed;
	}

	public void setAfcWildCard3HomeSeed(String afcWildCard3HomeSeed) {
		this.afcWildCard3HomeSeed = afcWildCard3HomeSeed;
	}

	public String getAfcWildCard3HomeTeamAbbr() {
		return afcWildCard3HomeTeamAbbr;
	}

	public void setAfcWildCard3HomeTeamAbbr(String afcWildCard3HomeTeamAbbr) {
		this.afcWildCard3HomeTeamAbbr = afcWildCard3HomeTeamAbbr;
	}

	public String getAfcWildCard3HomePicture() {
		return afcWildCard3HomePicture;
	}

	public void setAfcWildCard3HomePicture(String afcWildCard3HomePicture) {
		this.afcWildCard3HomePicture = afcWildCard3HomePicture;
	}

	public String getAfcWildCard3HomeScore() {
		return afcWildCard3HomeScore;
	}

	public void setAfcWildCard3HomeScore(String afcWildCard3HomeScore) {
		this.afcWildCard3HomeScore = afcWildCard3HomeScore;
	}

	public String getNfcWildCard3RoadSeed() {
		return nfcWildCard3RoadSeed;
	}

	public void setNfcWildCard3RoadSeed(String nfcWildCard3RoadSeed) {
		this.nfcWildCard3RoadSeed = nfcWildCard3RoadSeed;
	}

	public String getNfcWildCard3RoadTeamAbbr() {
		return nfcWildCard3RoadTeamAbbr;
	}

	public void setNfcWildCard3RoadTeamAbbr(String nfcWildCard3RoadTeamAbbr) {
		this.nfcWildCard3RoadTeamAbbr = nfcWildCard3RoadTeamAbbr;
	}

	public String getNfcWildCard3RoadPicture() {
		return nfcWildCard3RoadPicture;
	}

	public void setNfcWildCard3RoadPicture(String nfcWildCard3RoadPicture) {
		this.nfcWildCard3RoadPicture = nfcWildCard3RoadPicture;
	}

	public String getNfcWildCard3RoadScore() {
		return nfcWildCard3RoadScore;
	}

	public void setNfcWildCard3RoadScore(String nfcWildCard3RoadScore) {
		this.nfcWildCard3RoadScore = nfcWildCard3RoadScore;
	}

	public String getNfcWildCard3HomeSeed() {
		return nfcWildCard3HomeSeed;
	}

	public void setNfcWildCard3HomeSeed(String nfcWildCard3HomeSeed) {
		this.nfcWildCard3HomeSeed = nfcWildCard3HomeSeed;
	}

	public String getNfcWildCard3HomeTeamAbbr() {
		return nfcWildCard3HomeTeamAbbr;
	}

	public void setNfcWildCard3HomeTeamAbbr(String nfcWildCard3HomeTeamAbbr) {
		this.nfcWildCard3HomeTeamAbbr = nfcWildCard3HomeTeamAbbr;
	}

	public String getNfcWildCard3HomePicture() {
		return nfcWildCard3HomePicture;
	}

	public void setNfcWildCard3HomePicture(String nfcWildCard3HomePicture) {
		this.nfcWildCard3HomePicture = nfcWildCard3HomePicture;
	}

	public String getNfcWildCard3HomeScore() {
		return nfcWildCard3HomeScore;
	}

	public void setNfcWildCard3HomeScore(String nfcWildCard3HomeScore) {
		this.nfcWildCard3HomeScore = nfcWildCard3HomeScore;
	}

	public Boolean getThreeWildCardGames() {
		return threeWildCardGames;
	}

	public void setThreeWildCardGames(Boolean threeWildCardGames) {
		this.threeWildCardGames = threeWildCardGames;
	}

	
	
}
