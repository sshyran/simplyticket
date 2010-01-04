<%-- 
    Document   : index
    Created on : 24-dic-2009, 16.28.16
    Author     : William The Bloody
--%>

<%@page session="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Simply Ticket - Biglietteria</title>
        <link href="simply.css" rel="stylesheet" type="text/css" />
    </head>
    
    <body class="oneColLiqCtrHdr">
    <div id="container">
    	<form name="biglietteria">
        
        <div id="header">
        	<h1>Biglietteria</h1>
        <!-- end #header --></div>
        
        <div id="mainContent">
        <%
        HttpSession mySession=request.getSession();
        String sessionValue=(String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession"));
        if (sessionValue==null) {
        %>
	        <input type="button" id="bottone" value="Gestione Abbonamenti" disabled="disabled"/><br />
                <input type="button" id="bottone" value="Gestione Biglietteria" disabled="disabled"/>
        <!-- end #mainContent --></div>

        <div id="footer">
        	<p>
                <input type="button" value="Log Out" disabled="disabled"/>
                <a href="login.jsp"><input type="button" value="Login"/></a>
        		
        <%
        } else {
        %>
                <input type="button" id="bottone" value="Gestione Abbonamenti" disabled="disabled"/><br />
        	<a href="proiezioneServlet"><input type="button" id="bottone" value="Gestione Biglietteria"/></a>
        	
        <!-- end #mainContent --></div>
        
              <div id="footer">
                <p>
	                <a href="logout"><input type="button" value="Log Out"/></a>
                    <input type="button" value="Login" disabled="disabled"/>
                                    
        <%
        }
        %>
        			
                    <a href="tabelloneServlet"><input type="button" value="Tabellone"></a>
                    <a href="info.jsp"><input type="button" value="Info"/></a>
                </p>
              <!-- end #footer --></div>
			</form>
		<!-- end #container --></div>
	</body>
</html>