<%-- 
    Document   : error
    Created on : 25-dic-2009, 15.57.41
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
        <h1>Error Page!</h1>
        Uri: ${pageContext.errorData.requestURI}<br>
        Status Code: ${pageContext.errorData.statusCode}<br>
        Exception: ${pageContext.errorData.throwable.message}<br>
        <a href="index.jsp">Back to the main page</a>
    </body>
</html>
