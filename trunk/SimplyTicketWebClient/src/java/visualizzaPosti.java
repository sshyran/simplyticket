/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import data_layer.Collezione;
import data_layer.Posto;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
public class visualizzaPosti extends HttpServlet {

    private ControllerBiglietteria controllerBiglietteria;
    private String rmi_host;
    private Collezione listaPoltrone;
    private int lunghezzaFila;
    private int numeroFile;

    /**
     * Questo metodo serve per prelevare alcuni parametri che serviranno durante tutta la vita della servler
     * @param config Oggetto di tipo ServletConfig, server per recuperare informazioni sulla configurazione del container
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //ServletContext application = config.getServletContext();
        //error_page=application.getInitParameter("error_page");
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
     * Questo metodo risponde alle richieste GET/POST ed effettua la lettura dello stato dei posti, e prepara le informazioni per essere processate dalla addetta
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
        String IDProiezione=request.getParameter("film");
        if (IDProiezione==null || IDProiezione.equals("")) {
            IDProiezione=(String)request.getAttribute("film");
            if (IDProiezione==null || IDProiezione.equals("")) {
                throw new ServletException("Proiezione non valida");
            }
            IDProiezione=IDProiezione.trim();
            //IDProiezione.replaceAll("\r\n", "");
        }
        try {
            listaPoltrone = controllerBiglietteria.leggiPosti(IDProiezione, -1, -1);
        }
        catch (java.rmi.ConnectException e) {
            this.initialize();
            listaPoltrone = controllerBiglietteria.leggiPosti(IDProiezione, -1, -1);
        }
        String[] poltrona;
         try {
            poltrona = ((String[]) listaPoltrone.getIndex(0));
        } catch (Exception ex) {
            //Logger.getLogger(visualizzaPosti.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Error retrieving available chair", ex);
        }
        int IDFila;
        try {
            IDFila=Integer.parseInt(poltrona[1]);
        }
            catch (Exception ex) {
            //Logger.getLogger(visualizzaPosti.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Error retrieving available chair", ex);
        }
        boolean finito=false;
        lunghezzaFila=0;
        for(int i=0;!finito;i++){
           try {
             poltrona = (String[]) listaPoltrone.getIndex(i);
           }
           catch (Exception e) {
             e.printStackTrace();
           }
           if(Integer.parseInt(poltrona[1])==IDFila)
              lunghezzaFila++;
            else
              finito=true;
        }
        numeroFile=listaPoltrone.size()/lunghezzaFila;
        ArrayList result=new ArrayList();
        boolean stato=false;
        for (int i=0;i<numeroFile;i++) {
          for (int j=0;j<lunghezzaFila;j++) {
            try {
              if (((String[]) listaPoltrone.getIndex((i*lunghezzaFila)+j))[2].equals("TRUE")) {
                  //serve per costruire l'indice della poltrona all�internodella collezione
                stato = true;
              }
              else
                stato=false;
              Posto o=new Posto(Integer.parseInt(((String[]) listaPoltrone.getIndex((i*lunghezzaFila)+j))[0]), stato, Integer.parseInt(((String[]) listaPoltrone.getIndex((i*lunghezzaFila)+j))[1]), IDProiezione);
              result.add(o);
            }
            catch (Exception e) {
              throw new ServletException("Error Constructiong \"Posto\" Object", e);
            }
          }
        }
        String locandinaPath=request.getParameter("locandina"+IDProiezione);
        if (locandinaPath==null || locandinaPath.equals("")) {
            locandinaPath=(String)request.getAttribute("locandina"+IDProiezione);
            if (locandinaPath==null || locandinaPath.equals("")) {
                locandinaPath=getServletContext().getInitParameter("defaultImage");
            }
            else {
                if (!(new java.io.File(locandinaPath).exists())) {
                    locandinaPath=getServletContext().getInitParameter("defaultImage");
                }
            }
        }
        else {
            locandinaPath=locandinaPath.substring(locandinaPath.lastIndexOf("\\")+1, locandinaPath.length());
            ServletContext context = getServletContext();
            String realContextPath = context.getRealPath(request.getContextPath());
            if (!(new java.io.File(realContextPath+"\\..\\img\\"+locandinaPath).exists())) {
                locandinaPath=getServletContext().getInitParameter("defaultImage");
            }
        }
        locandinaPath=locandinaPath.trim();
        request.setAttribute("locandina"+IDProiezione, locandinaPath);
        request.setAttribute("posti", result);
        this.forward("visualizzaPosti.jsp", request, response);
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
