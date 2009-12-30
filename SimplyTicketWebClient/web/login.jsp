<%-- 
    Document   : login
    Created on : 25-dic-2009, 16.19.22
    Author     : William The Bloody
--%>

<%@page session="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
        HttpSession mySession=request.getSession();
        String sessionValue=(String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession"));
        if (sessionValue==null) {
        %>
        <form name="login" action="loginServlet">
          <input type="text" name="username" value="" />
          <input type="password" name="password" value="" />
          <input type="submit" value="invio" name="invio" />
        </form>
        <%
        }else{
        %>
        Already logged in
        <%
        }
        %>
        <a href="index.jsp">Back</a>
    </body>
</html>
