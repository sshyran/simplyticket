<%-- 
    Document   : gestioneBiglietteria.jsp
    Created on : 26-dic-2009, 19.01.00
    Author     : William The Bloody
--%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="web.ProiezioneGiornaliera" %>

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
        <h1>Proiezioni!</h1>
        <%
        HttpSession mySession=request.getSession();
        String sessionValue=(String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession"));
        if (sessionValue==null) {
            throw new ServletException("Login non effettuato");
        }
        %>
                <%
                out.println("<form name=\"visualizzaPosti\" action=\"visualizzaPosti\">");
                out.println("<table border=\"1\">\n\t<thead>\n\t<tr>\n\t<th></th>\n\t<th>Sala</th>\n\t<th>Film</th>\n\t<th>Disponibilità</th>\n\t</tr>\n\t</thead>\n\t<tbody>");
                ArrayList arrayList = (ArrayList)request.getAttribute("films_availables");
                for (Iterator iter = arrayList.iterator(); iter.hasNext();) {
                    ProiezioneGiornaliera element = (ProiezioneGiornaliera) iter.next();
                    out.println("<tr>");
                    out.println("<td><input type=\"radio\" name=\"film\" value=\""+element.getId()+"\" /></td>");
                    out.println("<td>"+element.getSala()+"</td>");
                    out.println("<td>"+element.getTitolo()+"</td>");
                    out.println("<td>"+element.getDisponibilita()+"</td>");
                    out.println("</tr>");
                }
                out.println("</tbody></table><input type=\"submit\" value=\"invio\" name=\"invio\" /></form>");
                %>
                <a href="index.jsp">Back</a>
    </body>
</html>
