<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="Common_Tags.jsp" %> 
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html"%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE>NFL</TITLE>
</HEAD>
<BODY class="bodyTag">
	<table width="100%" border="0" CELLPADDING="0" CELLSPACING="0">
	   
	  <tr> 
		<td valign="top" align="center"><img src="<c:url value="/images/nfl-logo.png"/>"/></td>
		<td valign="top" align="center"><img src="<c:url value="/images/nfl-logo.png"/>"/></td>
		<td valign="top" align="center"><img src="<c:url value="/images/nfl-logo.png"/>"/></td>
	  </tr>
	  
	  <tr>
	      <td colspan="3"></td>
	  </tr>
	  
	   <tr>
	      <td colspan="3"></td>
	  </tr>
	          
      <tr>  
	       <td class="blueSmall" colspan="3" align="center">Working On Season:<c:out value="${sessionScope.SeasonYear}"/></td>
	  </tr>
	 
	</table>

</body>

</html>
