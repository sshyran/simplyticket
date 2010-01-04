<%@page session="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Simply Ticket - Login</title>
    <link href="simply.css" rel="stylesheet" type="text/css" />
    </head>

    <body class="oneColLiqCtrHdr">
        <div id="container">
          <div id="header">
            <h1>Login</h1>
          <!-- end #header --></div>
          <div id="mainContent">
            <%
                HttpSession mySession=request.getSession();
                String sessionValue=(String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession"));
                if (sessionValue==null) {
            %>
            <form name="login" action="loginServlet">
                <table id="logintable" align="center">
                    <tr><td>Username:</td><td><input type="text" name="username" value="" /></td></tr>
                    <tr><td>Password:</td><td><input type="password" name="password" value="" /></td></tr>
                    <tr><td colspan="2" align="center">
                            <input type="submit" value="Invio" name="invio" />
                            <input type="reset" value="Annulla" />
                        </td>
                    </tr>
                </table>
            </form>
            <%
            }else{
            %>
            <h3>Accesso già effettuato</h3>
            <%
            }
            %>
    <!-- end #mainContent --></div>

          <div id="footer">
            <p>
            <!--
    link disattivato, il menu principale è index.jsp per tutti gli utenti, loggati o meno

    <a href="mainPage.jsp"><input type="button" value="Menù Principale"/></a>
    -->
            <a href="index.jsp"><input type="button" value="Indietro"/></a>
            </p>
          <!-- end #footer --></div>
        <!-- end #container --></div>
    </body>
</html>