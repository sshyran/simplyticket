<%-- 
    Document   : mainPage
    Created on : 25-dic-2009, 11.01.30
    Author     : William The Bloody
--%>

<%@page session="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Simply Ticket - Main Page</title>
    </head>
    <body>
        <%
        HttpSession mySession=request.getSession();
        String sessionValue=(String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession"));
        if (sessionValue==null) {
            throw new ServletException("Login non effettuato");
        }
        %>
        <h1>You are in!</h1>
        <a href="proiezioneServlet">Available Films</a>
    </body>
</html>
