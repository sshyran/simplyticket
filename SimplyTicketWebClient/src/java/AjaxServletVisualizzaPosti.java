/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import data_layer.Collezione;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Raffaele
 */
public class AjaxServletVisualizzaPosti extends HttpServlet {
   
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
     * Questo metodo risponde a XHR ed effettua la lettura dei posti per la proiezione selezionata e restituisce i dati in formato JSON
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
        }
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String xmlResponse="{\"lista\":\n\t[\n";
        String xmlElement="";
        boolean not_first = false;
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
              if(not_first){
                xmlElement=",\n";
              }else {
                    xmlElement="";
              }
              not_first=true;
              xmlElement=xmlElement+"\t{\"posto\":\n\t\t{\n\t\t\"id\":\""+Integer.parseInt(((String[]) listaPoltrone.getIndex((i*lunghezzaFila)+j))[0])+"\",\n\t\t" +
                      "\"occupato\":\""+stato+"\",\n\t\t" +
                      "\"fila\":\""+Integer.parseInt(((String[]) listaPoltrone.getIndex((i*lunghezzaFila)+j))[1])+"\",\n\t\t" +
                      "\"proiezione\":\""+IDProiezione+"\"\n\t\t}\n\t}";
              xmlResponse=xmlResponse+xmlElement;
            }
            catch (Exception e) {
              throw new ServletException("Error Constructiong \"Posto\" Object", e);
            }
          }
        }
        xmlResponse=xmlResponse+"\n\t]\n}";
        out.print(xmlResponse);
        out.close();
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

}
