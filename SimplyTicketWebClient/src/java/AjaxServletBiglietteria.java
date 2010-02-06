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
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.ProiezioneGiornaliera;

/**
 *
 * @author Raffaele
 */
public class AjaxServletBiglietteria extends HttpServlet {
   
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
     * Questo metodo risponde a XHR ed effettua la lettura delle proiezione del giorno e le restituisce in formato JSON
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
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String xmlResponse="{\"lista\":\n\t[\n";
        String xmlElement="";
        boolean not_first = false;
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
            if(not_first){
                xmlElement=",\n";
            }else {
                xmlElement="";
            }
            not_first=true;
            String locandina=((String[])listaProiezioni.getIndex(i))[4];
            if (locandina==null || locandina.equals("")) {
                locandina=getServletContext().getInitParameter("defaultImage");
            }
            else {
                locandina=locandina.substring(locandina.lastIndexOf("\\")+1, locandina.length());
                ServletContext context = getServletContext();
                String realContextPath = context.getRealPath(request.getContextPath());
                if (!(new java.io.File(realContextPath+"\\..\\img\\"+locandina).exists())) {
                    locandina=getServletContext().getInitParameter("defaultImage");
                }
            }
            locandina=locandina.trim();
            xmlElement=xmlElement+"\t{\"proiezione\":\n\t\t{\n\t\t\"id\":\""+( (String[]) listaProiezioni.getIndex(i))[0]+"\",\n\t\t" +
                    "\"sala\":\""+( ( (String[]) listaProiezioni.getIndex(i))[1])+"\",\n\t\t" +
                    "\"titolo\":\""+( ( (String[]) listaProiezioni.getIndex(i))[2])+"\",\n\t\t\"disponibilita\":\""+Integer.parseInt( ( (String[]) listaProiezioni.getIndex(i))[3])+"\",\n\t\t" +
                    "\"locandina\":\""+locandina+"\"\n\t\t}\n\t}";
            xmlResponse=xmlResponse+xmlElement;
          }
          catch (Exception e) {
            throw  new ServletException("Error Fetching data",e);
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
