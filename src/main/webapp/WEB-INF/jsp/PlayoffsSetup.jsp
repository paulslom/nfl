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

<script language="JavaScript">

	function trColors(ckBox)
	{ 
	  ckBoxClicked = ckBox;	  
	  	   
	  //go up 2 levels to change the row color -- parent of checkbox is TD element, parent of TD is the TR
	  rowElement = ckBoxClicked.parentNode.parentNode
	  	  
	  if (rowElement.className == "rowGreen")
	  { 	    
	  	rowElement.className = "rowRed";
	  }
	  else
	  {	
	    rowElement.className = "rowGreen";
	  }
	    
	}

</script>

<html:form action="PlayoffsSetupAction">  
     
     <TABLE align="center" id="outerTable" CLASS="myBox" WIDTH="100%" CELLSPACING=0 CELLPADDING=2 BORDER=1>
	     
	    <colgroup>
	       <col width="50%">
	       <col width="50%">
	    </colgroup>	
	    
	      <TR>
	     
	     	<TD class="bodyBold" colspan="2" align="center">
	     	    <c:out value="${playoffsSetupForm.playoffsSetupFormTitle}"/>
	     	</TD>
	     	
	     </TR>
	     
	     <TR>
	     	 <td>
	           <center><IMG src="<c:url value="/images/afc.png"/>"/></center>	
	         </td>
	     	<td align="center">
	           <center><IMG src="<c:url value="/images/nfc.png"/>"/></center>	
	         </td>
	     </TR>
	     	
	     <TR>
	     
	     	<TD>
	     	
		     	<TABLE align="center" id="afcTeamsTable" CLASS="myBox" WIDTH="100%" CELLSPACING=0 CELLPADDING=2 BORDER=1>
				 
				 	<colgroup>
				       <col width="10%">
				       <col width="10%">
				       <col width="20%">
				       <col width="60%">
				    </colgroup>	
						 
					<TR>
					 	<td align="center">Y/N</td>
					 	<td align="center">Seed</td>
					 	<td align="center">Division</td>					 	
					 	<td align="center">Team</td>
					</TR>
					 
					<c:forEach var="afcTeamItem" items="${sessionScope.playoffsSetupForm.afcTeamsList}" varStatus="afcloop">
										  
					   <c:if test="${not afcloop.first and afcloop.index % 4 == 0}"> 
                           <TR>
                              <td colspan="4">
                              	  <br/>
                              </td>
                           </TR>   
                       </c:if>
                       
					   <TR>
					      <td align="center">
					      	<html:checkbox name="afcTeamItem" indexed="true" property="selectedInd" onclick="javascript:trColors(this)"/>		      	 
					      </td>	
					      <td align="center">
					      	<html:text name="afcTeamItem" indexed="true" property="playoffSeedStr" size="2" maxlength="1"/>		      	 
					      </td>					     
					      <td align="center">
					      	<c:out value="${afcTeamItem.division.vdivisionName}"/>		      	 
					      </td>	
					      <td align="center">
					      	<c:out value="${afcTeamItem.fullTeamName}"/>		      	 
					      </td>			          
					   </TR>  
					     			
					</c:forEach>	
									  
				</TABLE> 
				
	     	</TD>	     	
	     	
	     	<TD>
	     	
	     		<TABLE align="center" id="nfcTeamsTable" CLASS="myBox" WIDTH="100%" CELLSPACING=0 CELLPADDING=2 BORDER=1>
				 
					<colgroup>
				       <col width="10%">
				       <col width="10%">
				       <col width="20%">
				       <col width="60%">
				    </colgroup>	
						 
					<TR>
					 	<td align="center">Y/N</td>
					 	<td align="center">Seed</td>
					 	<td align="center">Division</td>					 	
					 	<td align="center">Team</td>
					</TR>
					
					<c:forEach var="nfcTeamItem" items="${sessionScope.playoffsSetupForm.nfcTeamsList}" varStatus="nfcloop">
					  
					  <c:if test="${not nfcloop.first and nfcloop.index % 4 == 0}"> 
                           <TR>
                              <td colspan="4">
                                 <br/>
                              </td>
                           </TR>   
                       </c:if>
                       
					   <TR>
					      <td align="center">
					      	<html:checkbox name="nfcTeamItem" indexed="true" property="selectedInd" onclick="javascript:trColors(this)"/>		      	 
					      </td>	
					      <td align="center">
					      	<html:text name="nfcTeamItem" indexed="true" property="playoffSeedStr" size="2" maxlength="1"/>	 	 
					      </td>	
					      <td align="center">
					      	<c:out value="${nfcTeamItem.division.vdivisionName}"/>		      	 
					      </td>	
					      <td align="center">
					      	<c:out value="${nfcTeamItem.fullTeamName}"/>		      	 
					      </td>			          
					   </TR>  
					     			
					</c:forEach>							  
				  
				</TABLE> 
				
	     	</TD>
	     	
	     </TR>
	     
	     <TR>
	     
	     	<TD align="center">	     	
	     		<html:submit property="operation" value="Update" />
	     	</TD>
	     	<TD align="center">
			  	<html:submit property="operation" value="Cancel Update"/>
			</TD>	 
	     	
	     </TR>	    
	     
     </TABLE>
   
</html:form>

</BODY> 

