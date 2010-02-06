/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author William The Bloody
 */
public class logout extends HttpServlet {
   
    /**
     * Questo metodo risponde alle richieste GET/POST ed effettua il logout dal sistema
     * @param request Richiesta alla servlet
     * @param response Risposta della servlet
     * @throws ServletException Se un eccezione viene sollevata
     * @throws IOException Se si verifica un errore di I/O
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession mySession=request.getSession();
        /*String sessionValue=(String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession"));
        if (sessionValue==null) {
            throw new ServletException("Login non effettuato");
        }
        else {*/
            mySession.removeAttribute(getServletContext().getInitParameter("loggedSession"));
            this.forward(getServletContext().getInitParameter("mainPage"), request, response);
        // }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Gestisce uan richiesta HTTP con metodo GET
     * @param oggetto request della servlet
     * @param oggetto response della servlet
     * @throws ServletException Se si verifica un errore nel container
     * @throws IOException se accade un errore di I/O
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Gestisce uan richiesta HTTP con metodo POST
     * @param oggetto request della servlet
     * @param oggetto response della servlet
     * @throws ServletException Se si verifica un errore nel container
     * @throws IOException se accade un errore di I/O
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Restituisce una breve descrizione della servlet
     * @return Una stringa contenente una piccola descrizione della servlet
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Effettua il redirect ad una nuova risorsa sul server
     * @param aDestinationPage l'url della risorsa verso la quale deve essere fatto il redirect
     * @param aResponse L'oggetto response della servlet
     * @throws IOException Eccezione sollevata in caso di errori di I/O
     */
    private void redirect(String aDestinationPage, HttpServletResponse aResponse) throws IOException {
        String urlWithSessionID = aResponse.encodeRedirectURL(aDestinationPage);
        aResponse.sendRedirect( urlWithSessionID );
    }

    /**
     * Effettua il forward ad una nuova risorsa sul server
     * @param aResponsePage l'url della risorsa verso la quale deve essere fatto il forward
     * @param aRequest L'oggetto request della servlet
     * @param aResponse L'oggetto response della servlet
     * @throws ServletException
     * @throws IOException Eccezione sollevata in caso di errori di I/O
     */
    private void forward(String aResponsePage, HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException {
        RequestDispatcher dispatcher = aRequest.getRequestDispatcher(aResponsePage);
        dispatcher.forward(aRequest, aResponse);
    }
}
