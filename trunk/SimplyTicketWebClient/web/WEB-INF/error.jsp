<%-- 
    Document   : error
    Created on : 25-dic-2009, 15.57.41
    Author     : William The Bloody
--%>

<%@page session="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Simply Ticket - Errore</title>
	<link href="simply.css" rel="stylesheet" type="text/css" />
    </head>

	<body class="oneColLiqCtrHdr">
		<div id="container">
		  <div id="header">
		    <h1>Simply Ticket - Errore!</h1>
		  <!-- end #header --></div>
                    <div id="mainContent">
                        <table align="center">
                            <tr><td><span>uri </span></td><td> ${pageContext.errorData.requestURI} </td></tr>
                            <tr><td><span>status code </span></td><td> ${pageContext.errorData.statusCode} </td></tr>
                            <tr><td><span>exception </span></td><td> ${pageContext.errorData.throwable.message} </td></tr>
                            <tr><td><span>caused by </span></td><td> ${pageContext.errorData.throwable.cause.message} in ${pageContext.errorData.throwable.cause.class} </td></tr>
                        </table>
                        <!-- end #mainContent --></div>
		  <div id="footer">
          	<p>
                    <a href="index.jsp"><input type="button" value="<< Indietro"/></a>
                </p>
		  <!-- end #footer --></div>
		<!-- end #container --></div>
	</body>
</html>