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

<html:form action="ReportDraftAction">  
    
  <c:if test="${DraftList!=null}">
        
      <display:table name="sessionScope.DraftList" export="true" uid="sch" requestURI="" defaultsort="1">      
        
       <display:caption>NFL Draft Order</display:caption> 
       
       <display:column property="position" title="Position" sortable="true"/>     
       <display:column property="teamName" title="Team" sortable="true"/>
	   <display:column property="wins" title="Wins" sortable="true"/>
	   <display:column property="losses" title="Losses" sortable="true"/>
	   <display:column property="ties" title="Ties" sortable="true"/>
	   <display:column property="opponentWins" title="Opponent Wins" sortable="true"/>
	   <display:column property="opponentLosses" title="Opponent Losses" sortable="true"/>
	   <display:column property="opponentTies" title="Opponent Ties" sortable="true"/>
	   <display:column property="scheduleStrengthPct" title="Sked Strength Pct" sortable="true"/>
	   <display:setProperty name="sort.behavior" value="list" />
       <display:setProperty name="paging.banner.include_first_last" value="" />   

      </display:table>
            
  </c:if>
 
</html:form>

</BODY> 
