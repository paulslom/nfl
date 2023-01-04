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

<html:form action="PlayoffBracketsAction">  
     
     <TABLE align="center" id="outerTable" WIDTH="100%" CELLSPACING=0 CELLPADDING=2 BORDER=0>
	     
	     <colgroup>
	       <col width="39%">
	       <col width="22%">
	       <col width="39%">
	    </colgroup>	
		     
	     <TR>
	     	 <td>
	           <center><IMG style="vertical-align:middle"  src="<c:url value="/images/AFC.svg"/>" height="50" width="50"/></center>	
	         </td>
	         <td>
	         </td>
	     	 <td align="center">
	           <center><IMG style="vertical-align:middle"  src="<c:url value="/images/NFC.svg"/>" height="50" width="50"/></center>	
	         </td>
	     </TR>
	     	
	     <TR>
	     
	     	<TD>
	     	
		     	<TABLE align="center" id="afcTeamsTable" WIDTH="100%" CELLSPACING=0 CELLPADDING=2 BORDER=0>
				 
					<colgroup>
				       <col width="33%">
				       <col width="33%">
				       <col width="34%">
				    </colgroup>	
						 
					<TR>
					 	<td class="bodyBoldCenter" align="center">Wild Card</td>
					 	<td class="bodyBoldCenter" align="center">Divisional</td>					 	
					 	<td class="bodyBoldCenter" align="center">AFC Championship</td>
					</TR>
				                    
                    <TR>	                       
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.afcWildCard1RoadSeed}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.afcWildCard1RoadPicture}"/>" height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.afcWildCard1RoadScore}"/>		
					   </td>
					   <td>
					   </td>		
					   <td>
					   </td>					   	          
				   </TR> 
				   
				   <TR>	                       
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.afcWildCard1HomeSeed}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.afcWildCard1HomePicture}"/>" height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.afcWildCard1HomeScore}"/>		
					   </td>
					   <td>
					   </td>		
					   <td>
					   </td>					   	          
				   </TR>                    
                    
                   <TR>
                       <td>
					   </td>
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.afcDivisional1RoadSeed}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.afcDivisional1RoadPicture}"/>" height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.afcDivisional1RoadScore}"/>		
					   </td>		
					   <td>
					   </td>					   	          
					</TR> 
					
					<TR>
                       <td>
					   </td>
					    <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.afcDivisional1HomeSeed}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.afcDivisional1HomePicture}"/>"  height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.afcDivisional1HomeScore}"/>		
					   </td>	
					   <td>
					   </td>					  		          
					</TR> 
					
			        <TR>	                       
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.afcWildCard2RoadSeed}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.afcWildCard2RoadPicture}"/>" height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.afcWildCard2RoadScore}"/>		
					   </td>
					   <td>
					   </td>		
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.afcChampionshipRoadSeed}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.afcChampionshipRoadPicture}"/>"  height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.afcChampionshipRoadScore}"/>		
					   </td>						   	          
				    </TR> 
				   
				    <TR>	                       
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.afcWildCard2HomeSeed}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.afcWildCard2HomePicture}"/>" height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.afcWildCard2HomeScore}"/>		
					   </td>
					   <td>
					   </td>		
					   <td class="bodyBoldCenter">
					    	<c:out value="${playoffBracketsForm.afcChampionshipHomeSeed}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.afcChampionshipHomePicture}"/>"  height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.afcChampionshipHomeScore}"/>		
					   </td>								   	          
				    </TR> 
              	  
					<TR>
                       <td>
					   </td>
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.afcDivisional2RoadSeed}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.afcDivisional2RoadPicture}"/>"  height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.afcDivisional2RoadScore}"/>		
					   </td>		
					   <td>
					   </td>					   	          
					</TR> 
					
					<TR>
                       <td>
					   </td>
					    <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.afcDivisional2HomeSeed}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.afcDivisional2HomePicture}"/>" height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.afcDivisional2HomeScore}"/>		
					   </td>	
					   <td>
					   </td>					  		          
					</TR> 
					
					 <c:if test="${playoffBracketsForm.threeWildCardGames}">
                       <TR>	                       
						   <td class="bodyBoldCenter">
						   	    <c:out value="${playoffBracketsForm.afcWildCard3RoadSeed}"/>
					     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.afcWildCard3RoadPicture}"/>" height="50" width="50"/>	
					     		<c:out value="${playoffBracketsForm.afcWildCard3RoadScore}"/>		
						   </td>
						   <td>
						   </td>		
						   <td>
						   </td>					   	          
					   </TR> 
					   
					    <TR>	                       
						   <td class="bodyBoldCenter">
						   	    <c:out value="${playoffBracketsForm.afcWildCard3HomeSeed}"/>
					     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.afcWildCard3HomePicture}"/>" height="50" width="50"/>	
					     		<c:out value="${playoffBracketsForm.afcWildCard3HomeScore}"/>		
						   </td>
						   <td>
						   </td>		
						   <td>
						   </td>					   	          
					   </TR> 
                    </c:if>					
				  
				</TABLE> 
				
	     	</TD>
	     	
	     	<TD class="bodyBoldCenter">
	     	    <c:out value="${playoffBracketsForm.afcChampionSeed}"/>
	     	  	<IMG style="vertical-align:middle" src="<c:url value="/images/${playoffBracketsForm.afcChampionPicture}"/>" height="50" width="50"/>	
	     		<c:out value="${playoffBracketsForm.afcChampionScore}"/> 
	     		&nbsp;&nbsp;    		
	     		<c:out value="${playoffBracketsForm.nfcChampionScore}"/> 
	     	  	<IMG style="vertical-align:middle" src="<c:url value="/images/${playoffBracketsForm.nfcChampionPicture}"/>" height="50" width="50"/>	
	     		<c:out value="${playoffBracketsForm.nfcChampionSeed}"/> 
	     	</TD>
	     	
	     	<TD>
	     	
	     		<TABLE align="center" id="nfcTeamsTable" WIDTH="100%" CELLSPACING=0 CELLPADDING=2 BORDER=0>
				 
					<colgroup>
				       <col width="34%">
				       <col width="33%">
				       <col width="33%">				      
				    </colgroup>	
						 
					<TR>
					 	<td class="bodyBoldCenter" align="center">NFC Championship</td>
					 	<td class="bodyBoldCenter" align="center">Divisional</td>
					 	<td class="bodyBoldCenter" align="center">Wild Card</td>
					</TR>
					 
					  <TR>	                       
					   <td>
					   </td>		
					   <td>
					   </td>
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.nfcWildCard1RoadScore}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.nfcWildCard1RoadPicture}"/>" height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.nfcWildCard1RoadSeed}"/>		
					   </td>										   	          
				   </TR> 
				   
				   <TR>	                       
					   <td>
					   </td>		
					   <td>
					   </td>
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.nfcWildCard1HomeScore}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.nfcWildCard1HomePicture}"/>" height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.nfcWildCard1HomeSeed}"/>		
					   </td>
										   	          
				   </TR>         
                    
                    <TR>
                       <td>
					   </td>
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.nfcDivisional1RoadScore}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.nfcDivisional1RoadPicture}"/>"  height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.nfcDivisional1RoadSeed}"/>		
					   </td>		
					   <td>
					   </td>					   	          
					</TR> 
					
					<TR>
                       <td>
					   </td>
					    <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.nfcDivisional1HomeScore}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.nfcDivisional1HomePicture}"/>"  height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.nfcDivisional1HomeSeed}"/>		
					   </td>	
					   <td>
					   </td>					  		          
					</TR> 
					  
                    <TR>                      
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.nfcChampionshipRoadScore}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.nfcChampionshipRoadPicture}"/>"  height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.nfcChampionshipRoadSeed}"/>		
					   </td>
					   <td>
					   </td>
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.nfcWildCard2RoadScore}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.nfcWildCard2RoadPicture}"/>" height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.nfcWildCard2RoadSeed}"/>		
					   </td>			          
					</TR> 
					
					<TR>						
					    <td class="bodyBoldCenter">
					    	<c:out value="${playoffBracketsForm.nfcChampionshipHomeScore}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.nfcChampionshipHomePicture}"/>"  height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.nfcChampionshipHomeSeed}"/>		
					    </td>
					    <td>
						</td>
						<td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.nfcWildCard2HomeScore}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.nfcWildCard2HomePicture}"/>" height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.nfcWildCard2HomeSeed}"/>		
					   </td>			          
					</TR> 
					
					 <TR>
                       <td>
					   </td>
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.nfcDivisional2RoadScore}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.nfcDivisional2RoadPicture}"/>"  height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.nfcDivisional2RoadSeed}"/>		
					   </td>		
					   <td>
					   </td>					   	          
					</TR> 
					
					<TR>
                       <td>
					   </td>
					   <td class="bodyBoldCenter">
					   	    <c:out value="${playoffBracketsForm.nfcDivisional2HomeScore}"/>
				     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.nfcDivisional2HomePicture}"/>"  height="50" width="50"/>	
				     		<c:out value="${playoffBracketsForm.nfcDivisional2HomeSeed}"/>		
					   </td>	
					   <td>
					   </td>					  		          
					</TR> 
					
					<c:if test="${playoffBracketsForm.threeWildCardGames}">
                       <TR>	
						   <td>
						   </td>		
						   <td>
						   </td>
						   <td class="bodyBoldCenter">
						   	    <c:out value="${playoffBracketsForm.nfcWildCard3RoadScore}"/>
					     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.nfcWildCard3RoadPicture}"/>" height="50" width="50"/>	
					     		<c:out value="${playoffBracketsForm.nfcWildCard3RoadSeed}"/>		
						   </td>					   	          
					   </TR> 
					   
					    <TR>	                       
						   <td>
						   </td>		
						   <td>
						   </td>
						      <td class="bodyBoldCenter">
						   	    <c:out value="${playoffBracketsForm.nfcWildCard3HomeScore}"/>
					     	  	<IMG style="vertical-align:middle"  src="<c:url value="/images/${playoffBracketsForm.nfcWildCard3HomePicture}"/>" height="50" width="50"/>	
					     		<c:out value="${playoffBracketsForm.nfcWildCard3HomeSeed}"/>		
						   </td>											   	          
					   </TR> 
                    </c:if>										
				  
				</TABLE> 
				
	     	</TD>
	     	
	     </TR>    
	     
     </TABLE>
   
</html:form>

</BODY> 

