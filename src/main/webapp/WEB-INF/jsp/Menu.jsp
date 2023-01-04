<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="Common_Tags.jsp" %> 
<html>

<head>
        
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        
    <link rel="stylesheet" type="text/css" media="screen" href="styles/coolmenu.css" />
    
    <script type="text/javascript" src="scripts/coolmenus4.js"></script>
    <script type="text/javascript" src="scripts/cm_addins.js"></script>

</head>

<body>

	<script type="text/javascript" src="scripts/coolmenu4-config.js"></script>
	
	<menu:useMenuDisplayer name="Velocity" bundle="org.apache.struts.action.MESSAGE"
	  config="/templates/coolmenus.html" repository="SFMenuRepository">
	  
     <div style="position:relative;">
       <script type="text/javascript">
<!--       
         <menu:displayMenu name="Scores"/>
         <menu:displayMenu name="Reports"/>
         <menu:displayMenu name="Games"/> 
         <menu:displayMenu name="Playoffs"/> 
         <menu:displayMenu name="Miscellaneous"/>        
         oCMenu.construct();
// -->
       </script>
      </div>    

	</menu:useMenuDisplayer>

    <BR>
    <BR>
    
</BODY>
</html>
