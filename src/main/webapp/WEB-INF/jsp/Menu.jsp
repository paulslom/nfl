<!DOCTYPE html>
<%@ include file="Common_Tags.jsp" %> 
<html>

<head>
        
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />        
    <META http-equiv="Content-Style-Type" content="text/css">
    <link rel=stylesheet href="styles/general.css" type="text/css"> 
 
</head>

<body>

  <nav class="menu" role='navigation'>
	
	<ol>
	  
	  	 <li class="menu-item" aria-haspopup="true">
	     
	        <a href="#0">Scores By Week</a>
	        <ol class="sub-menu" aria-label="submenu">
	     
	        <c:forEach var="mItem" items="${sessionScope.MenuScoresByWeek}">	 
	        	<li class="menu-item"><a href = '<c:out value="${mItem.menuLocation}"/>'>	<c:out value="${mItem.menuTitle}"/></a></li>	     
	        </c:forEach>
	       	            
	        </ol>
	        
	     </li> 
	     
	     <li class="menu-item" aria-haspopup="true">
	     
	        <a href="#0">Scores By Team</a>
	        <ol class="sub-menu" aria-label="submenu">
	     
	        <c:forEach var="mItem" items="${sessionScope.MenuScoresByTeam}">	 
	        	<li class="menu-item"><a href = '<c:out value="${mItem.menuLocation}"/>'>	<c:out value="${mItem.menuTitle}"/></a></li>	     
	        </c:forEach>
	       	            
	        </ol>
	        
	     </li> 
	     
	     <li class="menu-item" aria-haspopup="true">
	     
	        <a href="#0">Reports</a>
	        <ol class="sub-menu" aria-label="submenu">
	     
	        <c:forEach var="mItem" items="${sessionScope.MenuReports}">	 
	        	<li class="menu-item"><a href = '<c:out value="${mItem.menuLocation}"/>'>	<c:out value="${mItem.menuTitle}"/></a></li>	     
	        </c:forEach>
	       	            
	        </ol>
	        
	     </li> 
	     
	     <li class="menu-item" aria-haspopup="true">
	     
	        <a href="#0">Games</a>
	        <ol class="sub-menu" aria-label="submenu">
	     
	        <c:forEach var="mItem" items="${sessionScope.MenuGames}">	 
	        	<li class="menu-item"><a href = '<c:out value="${mItem.menuLocation}"/>'>	<c:out value="${mItem.menuTitle}"/></a></li>	     
	        </c:forEach>
	       	            
	        </ol>
	        
	     </li> 
	     
	     <li class="menu-item" aria-haspopup="true">
	     
	        <a href="#0">Playoffs</a>
	        <ol class="sub-menu" aria-label="submenu">
	     
	        <c:forEach var="mItem" items="${sessionScope.MenuPlayoffs}">	 
	        	<li class="menu-item"><a href = '<c:out value="${mItem.menuLocation}"/>'>	<c:out value="${mItem.menuTitle}"/></a></li>	     
	        </c:forEach>
	       	            
	        </ol>
	        
	     </li> 
	     
	     <li class="menu-item" aria-haspopup="true">
	     
	        <a href="#0">Miscellaneous</a>
	        <ol class="sub-menu" aria-label="submenu">
	     
	        <c:forEach var="mItem" items="${sessionScope.MenuMisc}">	 
	        	<li class="menu-item"><a href = '<c:out value="${mItem.menuLocation}"/>'>	<c:out value="${mItem.menuTitle}"/></a></li>	     
	        </c:forEach>
	       	            
	        </ol>
	        
	     </li> 
	    
	</ol>
	  
  </nav>
    
</BODY>
</html>

	