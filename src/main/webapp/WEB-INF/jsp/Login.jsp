<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ include file="Common_Tags.jsp" %>

<HEAD>
   <%@ page language="java" contentType="text/html" %>
   <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <META http-equiv="Content-Style-Type" content="text/css">
   <script type="text/javascript" src="scripts/common.js"></script>
   <TITLE>Application Title</TITLE>
</HEAD>

<BODY>

<html:form focus="userId" action="LoginAction">

  <BR>
  <BR>
  <BR>
  <BR>
  <BR>

  <TABLE align="center" CLASS="myBox" WIDTH="30%" CELLSPACING=0 CELLPADDING=4 BORDER=0>
	
	 <TR>
		<TD CLASS="bodyBold" ALIGN="right">User:</TD>
		<td><html:text property="userId" maxlength="10" size="20"/></td>
	 </TR>

	 <TR>
		<TD CLASS="bodyBold" ALIGN="right">Password:</TD>
		<td><html:password property="password" maxlength="20" size="20"/></td>
	 </TR>
	 
  </TABLE> 

  <TABLE align="center" CLASS="myBox" WIDTH="30%" CELLSPACING=0 CELLPADDING=4 BORDER=0>
	<TR>
		<TD align="center">
		   <html:submit property="operation" value="Login" />
		</TD>
		<TD align="center">
		   <html:cancel>Cancel</html:cancel>
		</TD>
	</TR>		 
  </TABLE>

</html:form> 
