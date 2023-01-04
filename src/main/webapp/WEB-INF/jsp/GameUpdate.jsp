<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ include file="Common_Tags.jsp" %> 
 
<HEAD>

   <%@ page language="java" contentType="text/html" %>
   <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <META http-equiv="Content-Style-Type" content="text/css">
   <script type="text/javascript" src="scripts/common.js"></script>
    
</HEAD>

<BODY>

<html:form action="GameUpdateAction">

  <html:hidden property="gameID"/>
        
  <TABLE align="center" CLASS="myBox" WIDTH="100%" CELLSPACING=0 CELLPADDING=2 BORDER=1>
	 
	 <colgroup>
	    <col width="35%">
	    <col width="65%">
	 </colgroup>	
	 	  
	 <c:if test="${gameShowParm!='add'}">
		 <TR>
		    <td ALIGN="right">
		        <pas:label value="Game ID"/>
		    </td>    
		    <td ALIGN="left">	       
		       <pas:text property="gameID" textbox="false" readonly="true"/>
		    </td>
		 </TR>
	 </c:if>
	 
	 <TR>
	    <td ALIGN="right">
	        <pas:label value="Week"/>
	    </td>
	    <c:choose>
	      <c:when test="${(gameShowParm=='add' || gameShowParm =='update')}">
	    	<td ALIGN="left">	       
	       	  <html:select name="gameUpdateForm" property="weekID" tabindex="1">
			    <html:option value="">-Select-</html:option>
			    <html:options collection="WeeksList" property="id" labelProperty="description" />					    	   
			  </html:select>
	    	</td>
	      </c:when>
	      <c:otherwise>
	        <td ALIGN="left">	       
	       		<pas:text property="weekNumber" textbox="false" readonly="true"/>
	    	</td>
	      </c:otherwise>   
	    </c:choose> 
	 </TR>
	 
	 <TR>
	    <td ALIGN="right">
	        <pas:label value="Date (format: yyyy-mm-dd hh:mm PM)"/>
	    </td>
	    <c:choose>
          <c:when test="${gameShowParm=='add' || gameShowParm =='update'}">
    	    <td ALIGN="left">	       
       	       <pas:text property="gameDateTime" textbox="true" readonly="false" width="150" size="40" tabindex="3"/>
    	    </td>
          </c:when>
          <c:otherwise>
            <td ALIGN="left">	       
	          <pas:text property="gameDateTime" textbox="false" readonly="true"/>
	        </td>
          </c:otherwise>   
        </c:choose> 	    
	 </TR> 
	 
	 <TR>
	    <td ALIGN="right">
	        <pas:label value="Game Type"/>
	    </td>
	    <c:choose>
	      <c:when test="${(gameShowParm=='add' || gameShowParm =='update')}">
	    	<td ALIGN="left">	       
	       	  <html:select name="gameUpdateForm" property="gameTypeID" tabindex="4">
			    <html:option value="">-Select-</html:option>
			    <html:options collection="GameTypesList" property="id" labelProperty="description" />			    	   
			  </html:select>
	    	</td>
	      </c:when>
	      <c:otherwise>
	        <td ALIGN="left">	       
	       		<pas:text property="gameTypeDesc" textbox="false" readonly="true"/>
	    	</td>
	      </c:otherwise>   
	    </c:choose> 
	 </TR>
	 
	 <TR>
	    <td ALIGN="right">
	        <pas:label value="Away Team"/>
	    </td>
	    <c:choose>
	      <c:when test="${(gameShowParm=='add' || gameShowParm =='update')}">
	    	<td ALIGN="left">	       
	       	  <html:select name="gameUpdateForm" property="awayTeamID" tabindex="5">
			    <html:option value="">-Select-</html:option>
			    <html:options collection="TeamsList" property="id" labelProperty="description" />				    	   
			  </html:select>
	    	</td>
	      </c:when>
	      <c:otherwise>
	        <td ALIGN="left">	       
	       		<pas:text property="awayTeamDesc" textbox="false" readonly="true"/>
	    	</td>
	      </c:otherwise>   
	    </c:choose> 
	 </TR>
	 
	 <TR>
	    <td ALIGN="right">
	        <pas:label value="Home Team"/>
	    </td>
	    <c:choose>
	      <c:when test="${(gameShowParm=='add' || gameShowParm =='update')}">
	    	<td ALIGN="left">	       
	       	  <html:select name="gameUpdateForm" property="homeTeamID" tabindex="5">
			    <html:option value="">-Select-</html:option>
			    <html:options collection="TeamsList" property="id" labelProperty="description" />				    	   
			  </html:select>
	    	</td>
	      </c:when>
	      <c:otherwise>
	        <td ALIGN="left">	       
	       		<pas:text property="homeTeamDesc" textbox="false" readonly="true"/>
	    	</td>
	      </c:otherwise>   
	    </c:choose> 
	 </TR>
	   
  </TABLE> 
  
  <TABLE align="center" CLASS="myBox" WIDTH="100%" CELLSPACING=0 CELLPADDING=4 BORDER=0>
	<TR>
	 <c:choose>
	    <c:when test="${gameShowParm=='add'}">
		  <TD align="center">
		     <html:submit property="operation" value="Add" />
		  </TD>
		  <TD align="center">
		     <html:submit property="operation" value="Cancel Add"/>
		  </TD>	 
		</c:when>
		<c:when test="${gameShowParm=='update'}">    
		  <TD align="center">
		    <html:submit property="operation" value="Update" />
		  </TD>
		  <TD align="center">
		    <html:submit property="operation" value="Cancel Update" />
		  </TD>
		</c:when>
		<c:when test="${gameShowParm=='delete'}">
		  <TD align="center">
		    <html:submit property="operation" value="Delete" />
		  </TD>
		  <TD align="center">
		    <html:submit property="operation" value="Cancel Delete" />
		  </TD>
		</c:when>
		<c:when test="${gameShowParm=='inquire'}">
		  <TD colspan="2" align="center">
		     <html:submit property="operation" value="Return"/>
		  </TD>
		</c:when>
	 </c:choose>	 	
	</TR>
	
  </TABLE>
    
</html:form>

</BODY> 
