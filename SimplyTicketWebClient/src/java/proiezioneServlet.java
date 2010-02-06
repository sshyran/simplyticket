/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import data_layer.Collezione;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.ProiezioneGiornaliera;

/**
 *
 * @author William The Bloody
 */
public class proiezioneServlet extends HttpServlet {
    
    private ControllerBiglietteria controllerBiglietteria;
    private String rmi_host;

    /**
     * Questo metodo serve per prelevare alcuni parametri che serviranno durante tutta la vita della servler
     * @param config Oggetto di tipo ServletConfig, server per recuperare informazioni sulla configurazione del container
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        rmi_host=config.getInitParameter("rmiregistry_host");
        if(rmi_host==null){rmi_host="127.0.0.1";}
        this.initialize();
    }

    /**
     * Questo metodo serve per inizializzare gli oggetti remoti
     */
    private void initialize() {
        try {
            controllerBiglietteria = (ControllerBiglietteria) Naming.lookup("//"+rmi_host+"/controllerBiglietteria");
        } catch (NotBoundException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    /**
     * Questo metodo risponde alle richieste GET/POST ed effettua la lettura delle proiezione del giorno e le prepara per la jsp relativa
     * @param request Richiesta alla servlet
     * @param response Risposta della servlet
     * @throws ServletException Se un eccezione viene sollevata
     * @throws IOException Se si verifica un errore di I/O
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        if (controllerBiglietteria==null) {
            this.initialize();
        }
        HttpSession mySession=request.getSession();
        String sessionValue=(String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession"));
        if (sessionValue==null) {
            throw new ServletException("Login non effettuato");
        }
        Collezione listaProiezioni = null;
        try {
            listaProiezioni = this.controllerBiglietteria.leggiListaSpettacoli();
        } catch (java.rmi.ConnectException e) {
            this.initialize();
            listaProiezioni = this.controllerBiglietteria.leggiListaSpettacoli();
        }
        ProiezioneGiornaliera p=null;
        Collection result=new ArrayList();
        for (int i=0;i<listaProiezioni.size();i++) {
          try {
            p=new ProiezioneGiornaliera();
            p.setId(( (String[]) listaProiezioni.getIndex(i))[0]);
            //tblListFilm.getModel().setValueAt(id, i, 0);
            p.setSala(( ( (String[]) listaProiezioni.getIndex(i))[1]));
            p.setTitolo(( ( (String[]) listaProiezioni.getIndex(i))[2]));
            p.setDisponibilita(Integer.parseInt( ( (String[]) listaProiezioni.getIndex(i))[3]));
            p.setLocandina(((String[])listaProiezioni.getIndex(i))[4]);
            result.add(p);
          }
          catch (Exception e) {
            throw  new ServletException("Error Fetching data",e);
          }
        }
        request.setAttribute("films_availables", result);
        //request.getSession().setAttribute("films_availables", result);
        this.forward("gestioneBiglietteria.jsp", request, response);
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
