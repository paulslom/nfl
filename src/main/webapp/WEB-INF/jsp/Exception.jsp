<%@ include file="Common_Tags.jsp" %> 

<HTML>	

   <HEAD>
      <%@ page language="java" contentType="text/html" %>
      <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <META http-equiv="Content-Style-Type" content="text/css">
      <script type="text/javascript" src="scripts/common.js"></script>
   </HEAD>

   <BODY>

	<logic:messagesPresent message="true"> 
	
	   <h3><font color="red"><bean:message key="exceptions.header" /></font></h3>
    
         <ul>	      
	       
	       <html:messages id="errorMsgs" message="true">
       		   <li CLASS="errorMessage">        
  		         <c:out value="${errorMsgs}" />
  	           </li>	
	       </html:messages>	
	       
	    </ul>
	
	</logic:messagesPresent> 	

</HTML>
