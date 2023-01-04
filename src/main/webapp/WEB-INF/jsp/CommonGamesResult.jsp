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

<html:form action="CommonGamesResultAction">  
   
   <TABLE align="center" id="resultsTable" CLASS="myBox" WIDTH="100%" CELLSPACING=0 CELLPADDING=2 BORDER=1>
				 
	  <TR>
	    
	    <TD class="bodyBold" align="center">
	        <c:out value="Common Games Results"/>
	    </TD>
	     	
	  </TR>
					 
	  <c:forEach var="item" items="${sessionScope.CommonGamesResultList}" varStatus="itemloop">
										  
	     <c:if test="${itemloop.index == 0 or itemloop.index == 1}"> 
              <TR>
                 <td>
                 	  <br/>
                 </td>
              </TR>   
          </c:if>
                    
		   <TR>
		      <td align="center">
		      	<c:out value="${item}"/>		      	 
		      </td>			          
		   </TR>  
		     			
	  </c:forEach>	
  
   </TABLE>   
     
</html:form>

</BODY> 

<script type="text/javascript">

</script>
