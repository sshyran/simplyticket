<%@page session="false" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="web.ProiezioneGiornaliera" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Simply Ticket - Tabellone</title>
	<link href="simply.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="AJS/AJS.js"></script>
        <script type="text/javascript" src="AJS/AJS_fx.js"></script>
        <script type="text/javascript" src="simplyTicket.js"></script>
	</head>

        <body class="oneColLiqCtrHdr" onload="start_fetching()">
		<div id="container">
		  <div id="header">
		    <h1>Proiezioni Attuali</h1>
		  <!-- end #header --></div>
		  <div id="mainContent">
			<%
                //int limit=((Integer)(request.getAttribute("NUM_PG"))).intValue();
                ArrayList arrayList = (ArrayList)request.getAttribute("PG");
                    if (arrayList!=null && arrayList.size()>0) {
                        out.println("<table id=\"tabellone\" align=\"center\">\n\t<thead>\n\t<tr>\n\t<th>Sala</th>\n\t<th>Titolo Film</th>\n\t<th>Disponibilità</th>\n\t<th>Inizio</th>\n\t</tr>\n\t</thead>\n\t<tbody>");
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
                        out.println("<h3>Non ci sono proiezioni</h3>");
                    }
                    %>

			<!-- end #mainContent --></div>
		  <div id="footer">
          	<p>
		    	<a href="index.jsp"><input type="button" value="Indietro"/></a>
            </p> 
		  <!-- end #footer --></div>
		<!-- end #container --></div>
	</body>
</html>