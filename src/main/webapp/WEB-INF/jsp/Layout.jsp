<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="Common_Tags.jsp" %>

<html:html>
<HEAD>
	<%@ page language="java" contentType="text/html" %>
	
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel=stylesheet href="styles/general.css" type="text/css"> 
	<META http-equiv="Content-Style-Type" content="text/css">
	<script type="text/javascript" src="scripts/common.js"></script>
	
	<script language="JavaScript">
		window.onbeforeunload = function()
		{
			//alert("reached the inside of onbeforeunload");
			//if(!oEvent) oEvent = window.event; 
			destroySession();
		}
	</script>
	
	<TITLE>Slomkowski NFL</TITLE>

</HEAD>

<tiles:importAttribute/>

<BODY class="bodyTag">

<TABLE WIDTH="100%" CELLSPACING=0 CELLPADDING=0 BORDER=0>
   <TR>
     <TD valign="top"><tiles:insert attribute="header" flush="true"/>
     </TD>
   </TR>   
</TABLE>

<TABLE WIDTH="100%" CELLSPACING=0 CELLPADDING=0 BORDER=0>

	<TR>
	    <TD>
	       <TABLE  align="center" width="100%" CELLSPACING=0 BORDER=0>
		     
		     <logic:present name="menu">
               <TR>
                 <TD valign="top">
                   <tiles:insert attribute="menu"/>
                 </TD>
               </TR>     
             </logic:present>	
   
		     <TR>
		       <TD valign="top" align="center">
				  <logic:present name="errorMessages">
					 <tiles:insert attribute="errorMessages"/>
				  </logic:present>	
				 
				  <DIV>
					 <tiles:insert attribute="fullBody" ignore="true" />
				  </DIV>
		       </TD>
		     </TR>			
	
	       </TABLE>
	  
	    </TD>
	  </TR>	

</TABLE>	
	
</BODY>

</html:html>
