<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="web.ProiezioneGiornaliera" %>
<%@page session="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Simply Ticket - Gestione Biglietteria</title>
	<link href="simply.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="AJS/AJS.js"></script>
        <script type="text/javascript" src="AJS/AJS_fx.js"></script>
        <script type="text/javascript" src="simplyTicket.js"></script>
	</head>

        <body class="oneColLiqCtrHdr" onload="start_fetching_biglietteria()">
		<div id="container">
		  <div id="header">
		    <h1>Gestione Biglietteria</h1>
		  <!-- end #header --></div>
                  <form name="visualizzaPosti" action="visualizzaPosti">
		  <div id="mainContent">
                   <%
            HttpSession mySession=request.getSession();
            String sessionValue=(String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession"));
            if (sessionValue==null) {
                throw new ServletException("Login non effettuato");
            }
            ArrayList arrayList = (ArrayList)request.getAttribute("films_availables");
            %>
		    <% if (arrayList!=null && arrayList.size()>0) {
                out.println("<form name=\"visualizzaPosti\" action=\"visualizzaPosti\">");
                out.println("<table id=\"tabellone\" align=\"center\">\n\t<thead>\n\t<tr>\n\t<th></th>\n\t<th>Sala</th>\n\t<th>Titolo Film</th>\n\t<th>Disponibilità</th>\n\t</tr>\n\t</thead>\n\t<tbody>");
                
                for (Iterator iter = arrayList.iterator(); iter.hasNext();) {
                    ProiezioneGiornaliera element = (ProiezioneGiornaliera) iter.next();
                    out.println("<input type=\"hidden\" name=\"locandina"+element.getId()+"\" value=\""+element.getLocandina()+"\" />");
                    out.println("<tr>");
                    out.println("<td><input type=\"radio\" id=\"film\" name=\"film\" value=\""+element.getId()+"\" /></td>");
                    out.println("<td>"+element.getSala()+"</td>");
                    out.println("<td>"+element.getTitolo()+"</td>");
                    out.println("<td>"+element.getDisponibilita()+"</td>");
                    out.println("</tr>");
                }
                out.println("</tbody></table>");
                }
                else {
                    if(arrayList==null){
                    throw new ServletException("Parametri non corretti");
                    }
                out.println("<h3>Nessuna proiezione</h3>");
                }
                %>
			<!-- end #mainContent --></div>
          <div id="footer">
            <p>
              <a href="index.jsp"><input type="button" value="<< Indietro"/></a>
            <% if (arrayList!=null && arrayList.size()>0) {
                out.println("<input type=\"submit\" value=\"Invio\" name=\"invio\" />");
                //out.println("</form>");
                }else{
                out.println("<input type=\"submit\" value=\"Invio\" name=\"invio\"/>");
                        }%>
            </p>
		  <!-- end #footer --></div>
            </form>
                
		<!-- end #container --></div>
	</body>
</html>