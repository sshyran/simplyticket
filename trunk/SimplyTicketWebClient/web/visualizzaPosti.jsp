<%-- 
    Document   : visualizzaPosti
    Created on : 27-dic-2009, 16.32.59
    Author     : William The Bloody
--%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="web.ProiezioneGiornaliera" %>
<%@ page language="java" import="data_layer.Posto" %>

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
            throw new ServletException("Login non effettuato");
        }
        %>
        <h1>Posti!</h1>
        <%
                out.println("<form name=\"prenotaPosti\" action=\"prenotaPosti\">");
                out.println("<table border=\"1\">\n\t<thead>\n\t<tr>\n\t<th>Prenota</th>\n\t<th>Rimborso</th>\n\t<th>Proiezione</th>\n\t<th>ID Posto</th>\n\t<th>ID Fila</th>\n\t<th>Occupato</th>\n\t</tr>\n\t</thead>\n\t<tbody>");
                ArrayList arrayList = (ArrayList)request.getAttribute("posti");
                for (Iterator iter = arrayList.iterator(); iter.hasNext();) {
                    Posto element = (Posto) iter.next();
                    out.println("<tr>");
                    out.println("<td><input type=\"checkbox\" name=\"prenota\" value=\""+element.getIDProiezione()+"0"+element.getIDFila()+element.getID()+"\" /></td>");
                    out.println("<td><input type=\"checkbox\" name=\"rimborso\" value=\""+element.getIDProiezione()+"0"+element.getIDFila()+element.getID()+"\" /></td>");
                    out.println("<td>"+element.getIDProiezione()+"</td>");
                    out.println("<td>"+element.getID()+"</td>");
                    out.println("<td>"+element.getIDFila()+"</td>");
                    out.println("<td>"+element.getOccupato()+"</td>");
                    out.println("</tr>");
                }
                out.println("<input type=\"text\" name=\"txtCostoIntero\" value=\"5.0\" /><input type=\"text\" name=\"txtCostoRidotto\" value=\"3.5\" />");
                out.println("</tbody></table><input type=\"submit\" value=\"invio\" name=\"invio\" /></form>");
                
                %>
                <a href="index.jsp">Back</a>

    </body>
</html>
