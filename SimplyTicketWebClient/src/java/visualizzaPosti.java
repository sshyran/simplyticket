/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import data_layer.Collezione;
import data_layer.Posto;
import web.Poltrona;
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
    private String error_page;
    private Collezione listaPoltrone;
    private int lunghezzaFila;
    private int numeroFile;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //ServletContext application = config.getServletContext();
        //error_page=application.getInitParameter("error_page");
        rmi_host=config.getInitParameter("rmiregistry_host");
        if(rmi_host==null){rmi_host="127.0.0.1";}
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
        HttpSession mySession=request.getSession();
        String sessionValue=(String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession"));
        if (sessionValue==null) {
            throw new ServletException("Login non effettuato");
        }
        String IDProiezione=request.getParameter("film");
        if (IDProiezione==null) {
            IDProiezione=(String)request.getAttribute("film");
            if (IDProiezione==null) {
                throw new ServletException("Proiezione non valida");
            }
        }
        listaPoltrone = controllerBiglietteria.leggiPosti(IDProiezione,-1,-1);
        String[] poltrona;
        try {
            poltrona = ((String[]) listaPoltrone.getIndex(0));
        } catch (Exception ex) {
            //Logger.getLogger(visualizzaPosti.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Error retrieving available chair", ex);
        }
        int IDFila=Integer.parseInt(poltrona[1]);
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
          //pnlDx.add(new JLabel(this.generaLettera(i)));
          //pnlSx.add(new JLabel(this.generaLettera(i)));
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
            
            /*if (j<lunghezzaFila/2) {
              pnlSx.add(oggettoPoltrona);
            }
            else {
              pnlDx.add(oggettoPoltrona);
            }*/
          }
        }
        request.setAttribute("posti", result);
        this.forward("visualizzaPosti.jsp", request, response);
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

    private void redirect(String aDestinationPage, HttpServletResponse aResponse) throws IOException {
        String urlWithSessionID = aResponse.encodeRedirectURL(aDestinationPage);
        aResponse.sendRedirect( urlWithSessionID );
    }

    private void forward(String aResponsePage, HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException {
        RequestDispatcher dispatcher = aRequest.getRequestDispatcher(aResponsePage);
        dispatcher.forward(aRequest, aResponse);
    }
}
