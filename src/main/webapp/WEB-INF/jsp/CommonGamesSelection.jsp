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

<html:form action="CommonGamesSelectionAction">  
     
     <TABLE align="center" id="outerTable" CLASS="myBox" WIDTH="100%" CELLSPACING=0 CELLPADDING=2 BORDER=1>
	     
	      <TR>
	     
	     	<TD class="bodyBold" colspan="2" align="center">
	     	    <c:out value="Select 2 or more teams for common games analysis"/>
	     	</TD>
	     	
	     </TR>
	     	
	     <TR>
	     
	     	<TD width="50%">
	     	
		     	<TABLE align="center" id="afcTeamsTable" CLASS="myBox" WIDTH="100%" CELLSPACING=0 CELLPADDING=2 BORDER=1>
				 
					<colgroup>
				       <col width="10%">
				       <col width="30%">
				       <col width="60%">
				    </colgroup>	
						 
					<TR>
					 	<td align="center">Y/N</td>
					 	<td align="center">Division</td>					 	
					 	<td align="center">Team</td>
					</TR>
					 
					<c:forEach var="afcTeamItem" items="${sessionScope.commonGamesSelectionForm.afcTeamsList}" varStatus="afcloop">
										  
					   <c:if test="${not afcloop.first and afcloop.index % 4 == 0}"> 
                           <TR>
                              <td colspan="3">
                              	  <br/>
                              </td>
                           </TR>   
                       </c:if>
                       
					   <TR>
					      <td align="center">
					      	<html:checkbox name="afcTeamItem" indexed="true" property="selectedInd" onclick="javascript:trColors(this)"/>		      	 
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
	     	
	     	<TD width="50%">
	     	
	     		<TABLE align="center" id="nfcTeamsTable" CLASS="myBox" WIDTH="100%" CELLSPACING=0 CELLPADDING=2 BORDER=1>
				 
					<colgroup>
				       <col width="10%">
				       <col width="30%">
				       <col width="60%">
				      
				    </colgroup>	
						 
					<TR>
					 	<td align="center">Y/N</td>
					 	<td align="center">Division</td>
					 	<td align="center">Team</td>
					</TR>
					 
					<c:forEach var="nfcTeamItem" items="${sessionScope.commonGamesSelectionForm.nfcTeamsList}" varStatus="nfcloop">
					  
					  <c:if test="${not nfcloop.first and nfcloop.index % 4 == 0}"> 
                           <TR>
                              <td colspan="3">
                                 <br/>
                              </td>
                           </TR>   
                       </c:if>
                       
					   <TR>
					      <td align="center">
					      	<html:checkbox name="nfcTeamItem" indexed="true" property="selectedInd" onclick="javascript:trColors(this)"/>		      	 
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
	     		<html:submit property="operation" value="Submit" />
	     	</TD>
	     	<TD align="center">
			  	<html:submit property="operation" value="Cancel common games"/>
			</TD>	 
	     	
	     </TR>	
	     
     </TABLE>
   
</html:form>

</BODY> 

<script type="text/javascript">

</script>
