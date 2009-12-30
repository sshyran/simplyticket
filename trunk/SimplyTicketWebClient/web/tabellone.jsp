<%-- 
    Document   : tabellone
    Created on : 25-dic-2009, 16.46.50
    Author     : William The Bloody
--%>

<%@page session="false" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="web.ProiezioneGiornaliera" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Simply Ticket - Tabellone</title>
    </head>
    <body>
        <%
            //int limit=((Integer)(request.getAttribute("NUM_PG"))).intValue();
            ArrayList arrayList = (ArrayList)request.getAttribute("PG");
        %>
        <h1>Proiezioni!</h1>    
                <%
                if (arrayList!=null && arrayList.size()>0) {
                    out.println("<table border=\"1\">\n\t<thead>\n\t<tr>\n\t<th>sala</th>\n\t<th>film</th>\n\t<th>disponibilità</th>\n\t<th>inizio</th>\n\t</tr>\n\t</thead>\n\t<tbody>");
                    for (Iterator iter = arrayList.iterator(); iter.hasNext();) {
                        ProiezioneGiornaliera element = (ProiezioneGiornaliera) iter.next();
                        out.println("<tr>");
                                out.println("<td>"+element.getSala()+"</td>");
                                out.println("<td>"+element.getTitolo()+"</td>");
                                out.println("<td>"+element.getDisponibilita()+"</td>");
                                out.println("<td>"+element.getOrario()+"</td>");
                         out.println("</tr>");
                    }
                    out.println("</tbody></table>");
                }
                else {
                    out.println("Non ci sono proiezioni");
                }
                %>
         <a href="index.jsp">Back</a>
    </body>
</html>
