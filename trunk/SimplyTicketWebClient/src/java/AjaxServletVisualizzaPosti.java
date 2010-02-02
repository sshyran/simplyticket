/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import data_layer.Collezione;
import data_layer.Posto;
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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.Poltrona;

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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        rmi_host=config.getInitParameter("rmiregistry_host");
        if(rmi_host==null){rmi_host="127.0.0.1";}
        this.initialize();
    }

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
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
        Poltrona oggettoPoltrona=null;
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
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
