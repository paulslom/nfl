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

<html:form action="SeasonChosenAction">

  <TABLE align="center" CLASS="myBox" WIDTH="100%" CELLSPACING=0 CELLPADDING=4 BORDER=0>
	 
	<colgroup>
	   <col width="40%">
	   <col width="60%">
	</colgroup>   
	
    <c:forEach var="season" items="${SeasonList}" varStatus="status">
	  <c:choose>
	     <c:when test="${status.count%2==0}">
	       <c:set var="rowclassjstl" value="rowEven"/>
	     </c:when>
	     <c:otherwise>
	       <c:set var="rowclassjstl" value="rowOdd"/>
	     </c:otherwise>
	   </c:choose>
	   <TR class="<c:out value="${rowclassjstl}"/>">
	      <TD align="center">
	         <c:set var="seasonLink" value="${pageContext.request.contextPath}/SeasonChosenAction.do?operation=inquire&seasonChosenID=${season.iseasonId}"/>
	         <html:link href="${seasonLink}">
	             <c:out value="${season.cyear}"/>
	         </html:link>
	      </TD>
	   </TR>    			
	</c:forEach>	
   
   </TABLE>
        
</html:form>

</BODY> 
