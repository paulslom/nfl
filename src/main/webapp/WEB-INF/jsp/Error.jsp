<%@ page isErrorPage="true" %>
<%@ include file="Common_Tags.jsp" %> 

<HTML>	

	<logic:messagesPresent message="true"> 
	
	   <h3><font color="red"><bean:message key="messages.header" /></font></h3>
    
         <ul>	      
	       
	       <html:messages id="errorMsgs" message="true">
       		   <li CLASS="errorMessage">        
  		         <c:out value="${errorMsgs}" />
  	           </li>	
	       </html:messages>	
	       
	    </ul>
	
	</logic:messagesPresent> 
	
	<logic:messagesPresent> 
	
	   <h3><font color="red"><bean:message key="messages.header" /></font></h3>
    
         <ul>	      
	       
	       <html:messages id="validatorMsgs" message="false">
               <li CLASS="errorMessage">    
  		         <c:out value="${validatorMsgs}" />
	           </li>
	       </html:messages>
	       
	    </ul>
	
	</logic:messagesPresent> 

</HTML>
