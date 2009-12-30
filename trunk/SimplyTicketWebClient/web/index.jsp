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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Simply Ticket</title>
    </head>
    <body>
        <%
        HttpSession mySession=request.getSession();
        String sessionValue=(String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession"));
        if (sessionValue==null) {
        %>
        <a href="login.jsp">login</a><br>
        <%
        }
        else {
        %>
        <a href="logout">logout</a><br>
        <%
        }
        %>
        <a href="info.jsp">Info</a><br>
        <a href="tabelloneServlet">Tabellone</a><br>
    </body>
</html>
