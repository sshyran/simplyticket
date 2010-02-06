/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author William The Bloody
 */
public class loginServlet extends HttpServlet {

    private ControllerLogin controllerLogin;
    private String rmi_host;
    private String error_page;

    /**
     * Questo metodo serve per prelevare alcuni parametri che serviranno durante tutta la vita della servler
     * @param config Oggetto di tipo ServletConfig, server per recuperare informazioni sulla configurazione del container
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext application = config.getServletContext();
        rmi_host=config.getInitParameter("rmiregistry_host");
        error_page=application.getInitParameter("error_page");
        if(rmi_host==null){rmi_host="127.0.0.1";}
        this.initialize();
    }

    /**
     * Questo metodo serve per inizializzare gli oggetti remoti
     */
    private void initialize() {
        try {
            controllerLogin = (ControllerLogin) Naming.lookup("//"+rmi_host+"/controllerLogin");
        } catch (NotBoundException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    /**
     * Questo metodo risponde alle richieste GET/POST ed effettua il login nel sistema
     * @param request Richiesta alla servlet
     * @param response Risposta della servlet
     * @throws ServletException Se un eccezione viene sollevata
     * @throws IOException Se si verifica un errore di I/O
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        if (controllerLogin==null) {
            this.initialize();
        }
        HttpSession mySession=request.getSession();
        String sessionValue=(String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession"));
        if (sessionValue!=null) {
            throw new ServletException("Login già effettuato");
        }
        String user=request.getParameter("username");
        String pass=request.getParameter("password");
        if ((user==null || pass==null)) {
            throw new ServletException("Parametri non corretti");
        }
        if (user.length()<6 || user.length()>10) {
            throw new ServletException("Il Nome Utente inserito non è corretto. La sua lunghezza deve essere compresa tra 6 e 10 caratteri");
        }
        if (!user.matches("\\w{6,10}")) {
            throw new ServletException("Il Nome Utente inserito non è corretto.Sono ammessi solamente caratteri alfanumerici");
        }
        if (pass.length()<6 || pass.length()>10) {
            throw new ServletException("La Password inserita non è corretta. La sua lunghezza deve essere compresa tra 6 e 10 caratteri");
        }
        if (!pass.matches("\\w{6,10}")) {
            throw new ServletException("La password inserita non è corretta.Sono ammessi solamente caratteri alfanumerici");
        }
        int id=-1;
        try {
            id = controllerLogin.verifica(true, user, pass);
        } catch (java.rmi.ConnectException e) {
            this.initialize();
            id = controllerLogin.verifica(true, user, pass);
        }
        if(id>-1) {
            //out.println("<br>Utente trovato<br>");
            mySession.setAttribute(getServletContext().getInitParameter("loggedSession"), ""+id);
            this.forward(getServletContext().getInitParameter("mainPage"), request, response);

        }
        else {
            throw new ServletException("Username/Password non esistenti");
        }
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
