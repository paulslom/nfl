<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ include file="Common_Tags.jsp" %> 
 
<HEAD>

   <%@ page language="java" contentType="text/html" %>
   <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <META http-equiv="Content-Style-Type" content="text/css">
   <link rel="stylesheet" type="text/css" href="styles/displaytag.css">
   <script type="text/javascript" src="scripts/common.js"></script>
      
</HEAD>

<BODY>

<html:form action="ReportStandingsAction">  
    
  <c:if test="${StandingsList!=null}">
        
      <display:table name="sessionScope.StandingsList" export="true" uid="sch" requestURI=""
                     class="totalTable" decorator="org.displaytag.decorator.TotalTableDecorator">      
         
       <display:caption>NFL Standings</display:caption> 
       
       <display:column property="divisionName" title="Division" group="1"/>
       <display:column property="teamName" title="Team"/>
	   <display:column property="wins" title="W"/>
	   <display:column property="losses" title="L"/>
	   <display:column property="ties" title="T"/>
	   <display:column property="divisionRecord" title="Division"/>
	   <display:column property="conferenceRecord" title="Conference"/>
	   <display:column property="strengthOfVictoryRecord" title="Strength of Victory"/>
	   <display:column property="strengthOfVictoryPct" title="Strength of Victory Pct"/>
	   <display:column property="gamesRemaining" title="Games Remaining"/>
	   
	   <display:setProperty name="sort.behavior" value="list" />
       <display:setProperty name="paging.banner.include_first_last" value="" />   

      </display:table>
            
  </c:if>
 
</html:form>

</BODY> 
