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

<html:form action="ReportScheduleAction">  
    
  <c:if test="${ScheduleList!=null}">
        
      <display:table name="sessionScope.ScheduleList" export="true" uid="sch" requestURI="">      
        
       <display:caption>NFL Schedule</display:caption> 
       
       <display:column property="team" />
       
       <c:forEach var="opp" items="${sch.opponents}">
          <display:column>
            <c:out value="${opp.opponentName}" />
          </display:column>  
       </c:forEach>
  	   
	   <display:setProperty name="sort.behavior" value="list" />
       <display:setProperty name="paging.banner.include_first_last" value="" />   

      </display:table>
            
  </c:if>
  
 
 
</html:form>

</BODY> 

<script type="text/javascript">
<!--
    var table = document.getElementById("sch");    
    
    for (var i = 0, row; row = table.rows[i]; i++) 
    {      
       for (var j = 0, col; col = row.cells[j]; j++) 
       {    	   
    	   var cellContents = col.innerHTML;
    	       	   
    	   if (cellContents.trim() == "*BYE*")    		   
           {   		  
              col.style.backgroundColor = "red";
           }
       }  
    }    
    
//-->
</script>
