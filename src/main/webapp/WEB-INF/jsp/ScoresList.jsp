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

<html:form action="ScoresUpdateAction">

  <TABLE align="center" id="scoreTable" WIDTH="100%" CELLSPACING=0 CELLPADDING=0 BORDER=0>
  	<TR>
  	    <TD class="bodyBold" align="center">Season: <c:out value="${sessionScope.SeasonYear}"/> Week: <c:out value="${sessionScope.CurrentWeek}"/></TD>	
  	</TR>
  </TABLE>
    	  
  <TABLE align="center" id="scoreTable" WIDTH="80%" CELLSPACING=0 CELLPADDING=0 BORDER=0>
			 
	 <colgroup>
	    <col width="5%">
	    <col width="5%">
	    <col width="5%">
	    <col width="17%">			   
	    <col width="16%">
	    <col width="10%">
	    <col width="17%">			   
	    <col width="16%">
	    <col width="9%">
	 </colgroup>	
	 
	 <TR class="tableHeading">
	    <td align="center">Day</td>
	  	<td align="center">Date</td>
	 	<td align="center">Time</td>
	 	<td align="center">Away Logo</td>
	 	<td align="center">Away Team</td>
	 	<td align="center">Away Score</td>	
	 	<td align="center">Home Logo</td>
	 	<td align="center">Home Team</td>
	 	<td align="center">Home Score</td>						 	
	 </TR>
	 
	 <c:forEach var="gameItem" items="${sessionScope.scoresListForm.scoresList}" varStatus="status">
	   <TR>
	      <td align="center">
	      	<c:out value="${gameItem.gameDayOfWeek}"/>		      	 
	      </td>	
	      <td align="center">
	      	<c:out value="${gameItem.gameDateOnly}"/>		      	 
	      </td>	
	      <td align="center">
	      	<c:out value="${gameItem.gameTimeOnly}"/>		      	 
	      </td>
	      <td align="center">
	        <center><IMG style="vertical-align:middle" src="<c:url value="/images/${gameItem.awayTeam.spictureFile}"/>" height="50" width="50"/></center>	
	      </td>
	      <td align="center">
	      	<c:out value="${gameItem.awayTeam.fullTeamName}"/>
	      </td>
	      <td align="center">
	      	<html:text name="gameItem" indexed="true" property="iawayTeamScore" tabindex="${status.count*2}" size="3" maxlength="2" onkeyup="return autoTab(this, 2, event);"/>		      	 
	      </td>
	       <td align="center">
	        <center><IMG style="vertical-align:middle" src="<c:url value="/images/${gameItem.homeTeam.spictureFile}"/>" height="50" width="50"/></center>	
	      </td>
	      <td align="center">
	      	<c:out value="${gameItem.homeTeam.fullTeamName}"/>
	      </td>
	      <td align="center">
	      	<html:text name="gameItem" indexed="true" property="ihomeTeamScore" tabindex="${status.count*2 + 1}" maxlength="2" size="3" onkeyup="return autoTab(this, 2, event);"/>		      	 
	      </td>						      	      		     
	   </TR>    			
	</c:forEach>
	
	<TR>
	  <TD align="right" colspan="5">
		<html:submit property="operation" value="Update" />
	  </TD>
	  <TD align="left" colspan="4">
	  	<html:submit property="operation" value="Cancel Update"/>
	  </TD>	 
  	</TR>	
  
  </TABLE>
        
</html:form>

</BODY> 
