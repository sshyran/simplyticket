<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="web.ProiezioneGiornaliera" %>
<%@ page language="java" import="data_layer.Posto" %>

<%@page session="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Simply Ticket - Prenota/Rimborsa Posti</title>
	<link href="simply.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="AJS/AJS.js"></script>
        <script type="text/javascript" src="AJS/AJS_fx.js"></script>
        <script type="text/javascript" src="simplyTicket.js"></script>
    </head>

    <body class="oneColLiqCtrHdr" onload="start_fetching_places()">
		<div id="container">
		  <div id="header">
		    <h1>Prenota/Rimborsa Posti</h1>
		  <!-- end #header --></div>
                    <%
			HttpSession mySession=request.getSession();
			String sessionValue=(String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession"));
			if (sessionValue==null) {
				throw new ServletException("Login non effettuato");
			}
			ArrayList arrayList = (ArrayList)request.getAttribute("posti");
                        String idProiezione = null;
				if (arrayList!=null && arrayList.size()>0) {
                                    Posto element2 = (Posto) (arrayList.get(0));
                                    idProiezione = element2.getIDProiezione();
                                    out.println("<form name=\"prenotaPosti\" action=\"prenotaPosti\">");
                    %>


                    <div id="barrasx">
                        <div id="locandina">
                            <a href="img/<%= request.getAttribute("locandina"+idProiezione) %>" target="_blank"> <img src="img/<%= request.getAttribute("locandina"+idProiezione) %>" alt="Locandina del film" ></img></a><br /><br /><!-- getLocandina from Film -->
                        </div>
                    <%
                    out.print("<input type=\"hidden\" name=\"locandina"+idProiezione+"\" value=\""+request.getAttribute("locandina"+idProiezione)+"\" />");
                    out.print("<input type=\"hidden\" id=\"dati\" name=\"dati\"value=\"\" />");
                    out.println("<table id=\"acquisto\"><tr><td>Intero:</td><td><input type=\"text\" name=\"txtCostoIntero\" value=\"5.0\" size=\"4\"/></td><td><input type=\"radio\" name=\"tipo_biglietto\" value=\"intero\" checked /></td></tr>");
                    out.println("<tr><td>Ridotto:</td><td><input type=\"text\" name=\"txtCostoRidotto\" value=\"3.5\" size=\"4\"/></td><td><input type=\"radio\" name=\"tipo_biglietto\" value=\"ridotto\" /></td></tr>");
                    out.println("</table>");
                    %>
                    </div>
                    <div id="mainContent_mod">
                    
                 <%
					out.println("<table id=\"platea\" align=\"center\">\n\t<tr><td>1</td>");
                                        int num_fila = 1;
					for (Iterator iter = arrayList.iterator(); iter.hasNext();) {
						Posto element = (Posto) iter.next();
                                                if (num_fila == element.getIDFila()) {
                                                    out.println("<td><div id=\""+element.getIDFila()+"_"+element.getID()+"\" class="+element.getOccupato()+" title=\""+element.getOccupato()+"\" ondblclick='echo(\""+element.getIDFila()+"_"+element.getID()+"\")'>"+element.getID()+"</div></td>");
                                                    if (element.getID()==10){
                                                        out.println("<td style=\"background-color: orange; \">&nbsp&nbsp</td>");
                                                    }
                                                }else{
                                                    num_fila = element.getIDFila();
                                                    out.println("</tr>\n<tr><td>"+num_fila+"</td><td><div class="+element.getOccupato()+">"+element.getID()+"</div></td>");
                                                }						
					}
					
					out.println("</tr></table>");
	
				}
				else {
					out.println("Parametri non corretti");
				}
					%>		
			
			<!-- end #mainContent --></div>
		  <div id="footer">
          	<p>
                        <input type="submit" value="Invia" name="prenota" onclick="return submit_places();"/>
                        <input type="hidden" value="<%out.println(idProiezione);%>" name="idProiezione" id="idProiezione"></input>
                        <a href="proiezioneServlet"><input type="button" value="<< Indietro"/></a>
                        <%
                        if (arrayList!=null && arrayList.size()>0) {
                            out.print("</form>");
                            }
                    %>
            </p> 
		  <!-- end #footer --></div>
		<!-- end #container --></div>
	</body>
</html>