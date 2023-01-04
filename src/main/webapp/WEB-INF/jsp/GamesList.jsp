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

<html:form action="GameUpdateAction">

  <c:if test="${GamesList!=null}">
   
   <div  style="OVERFLOW: auto; WIDTH: 100%; HEIGHT: 100%">
     
    <display:table name="sessionScope.GamesList" export="true" uid="Game" requestURI="" >        
       
       <display:column title="View">
          <c:url value="/GameShowUpdateFormAction.do" var="viewURL">
            <c:param name="operation" value="inquire"/>
            <c:param name="gameShowParm" value="inquire"/>
            <c:param name="gameID" value="${Game.igameId}"/>         
          </c:url>
          <a href="<c:out value='${viewURL}'/>">
             View
          </a>      
       </display:column>
       
       <display:column title="Del">
          <c:url value="/GameShowUpdateFormAction.do" var="deleteURL">
            <c:param name="operation" value="inquire"/>
            <c:param name="gameShowParm" value="delete"/>
            <c:param name="gameID" value="${Game.igameId}"/>         
          </c:url>
          <a href="<c:out value='${deleteURL}'/>">
             Del
          </a>      
       </display:column>
       
       <display:column title="Chg">
          <c:url value="/GameShowUpdateFormAction.do" var="updateURL">
            <c:param name="operation" value="inquire"/>
            <c:param name="gameShowParm" value="update"/>
            <c:param name="gameID" value="${Game.igameId}"/>         
          </c:url>
          <a href="<c:out value='${updateURL}'/>">
             Chg
          </a>      
       </display:column>
       
       <display:column property="gameDayOfWeek" title="Day" sortable="true"/>
       <display:column property="gameDateOnly" title="Date" sortable="true"/>
       <display:column property="gameTimeOnly" title="Time" sortable="true"/>
       <display:column property="week.iweekNumber" title="Week" sortable="true"/>
       
       <c:choose>
          <c:when test="${Game.iawayTeamScore!=null && Game.ihomeTeamScore!=null}">
             <c:choose>
               <c:when test="${Game.iawayTeamScore > Game.ihomeTeamScore}">
	             <display:column class="bodyBoldRed" property="awayTeam.fullTeamName" title="Away" sortable="true"/>
	       		 <display:column class="bodyBoldRed" property="iawayTeamScore" title="Away Score" sortable="true"/>
		         <display:column class="body" property="homeTeam.fullTeamName" title="Home" sortable="true"/>
	             <display:column class="body" property="ihomeTeamScore" title="Home Score" sortable="true"/>
               </c:when>
               <c:when test="${Game.iawayTeamScore < Game.ihomeTeamScore}">
	             <display:column class="body" property="awayTeam.fullTeamName" title="Away" sortable="true"/>
	       		 <display:column class="body" property="iawayTeamScore" title="Away Score" sortable="true"/>
		         <display:column class="bodyBoldRed" property="homeTeam.fullTeamName" title="Home" sortable="true"/>
	             <display:column class="bodyBoldRed" property="ihomeTeamScore" title="Home Score" sortable="true"/>
               </c:when>
               <c:otherwise>
	             <display:column class="body" property="awayTeam.fullTeamName" title="Away" sortable="true"/>
	       		 <display:column class="body" property="iawayTeamScore" title="Away Score" sortable="true"/>
		         <display:column class="body" property="homeTeam.fullTeamName" title="Home" sortable="true"/>
	             <display:column class="body" property="ihomeTeamScore" title="Home Score" sortable="true"/>
               </c:otherwise>
             </c:choose>
          </c:when>
          <c:otherwise>
             <display:column class="body" property="awayTeam.fullTeamName" title="Away" sortable="true"/>
       		 <display:column class="body" property="iawayTeamScore" title="Away Score" sortable="true"/>
	         <display:column class="body" property="homeTeam.fullTeamName" title="Home" sortable="true"/>
             <display:column class="body" property="ihomeTeamScore" title="Home Score" sortable="true"/>
          </c:otherwise>
       </c:choose>
       	      
       <display:setProperty name="sort.behavior" value="list" />
 	   <display:setProperty name="paging.banner.include_first_last" value="" />   
 	   	      	
     </display:table>

    </div>
            
  </c:if> 
        
</html:form>

</BODY> 
