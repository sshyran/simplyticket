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
public class prenotaPosti extends HttpServlet {

    private ControllerBiglietteria controllerBiglietteria;
    private String rmi_host;
    private String error_page;
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
        int IDAddetto=0;
        if (mySession.getAttribute(getServletContext().getInitParameter("loggedSession"))==null)
            throw new ServletException("Login non effettuato");
        try {
            IDAddetto=Integer.parseInt((String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession")));
        }
        catch (NumberFormatException e) {
            throw new ServletException("Addetto non valido",e);
        }
        String[] prenotazione=request.getParameterValues("prenota");
        String[] rimborso=request.getParameterValues("rimborso");
        double intero;
        double ridotto;
        try {
            intero=Double.parseDouble(request.getParameter("txtCostoIntero"));
            ridotto=Double.parseDouble(request.getParameter("txtCostoRidotto"));
        }
        catch (NumberFormatException e) {
            throw new ServletException("Prezzi non validi");
        }
        String interoFlag=request.getParameter("intero");
        String proiezione=null;
        int posto,fila;
        if (prenotazione!=null) {
            for (int i = 0; i < prenotazione.length; i++) {
                String string = prenotazione[i];
                //element.getIDProiezione()+element.getIDFila()+element.getID()
                try {
                    posto=Integer.parseInt(string.substring(7));
                    fila=Integer.parseInt(string.substring(5,7));
                }
                catch(NumberFormatException e) {
                    throw new ServletException("Parametri della prenotazione non corretti", e);
                }
                proiezione=string.substring(0,5);
                if (posto>0 && fila>0) {
                    Collezione postiRitornati=null;
                    String[] postoSuCuiLavorare=null;
                    try {
                      postiRitornati=controllerBiglietteria.leggiPosti(proiezione,fila,posto);
                      postoSuCuiLavorare = ( (String[]) postiRitornati.getIndex(0));
                    }
                    catch (Exception e1) {
                      throw new ServletException("Errore nella comunicazione con il server SimplyTicket", e1);
                    }
                    if (postoSuCuiLavorare[2].equals("TRUE")) {
                        //JOptionPane.showMessageDialog(null,"            PostoOccupato","Errore",JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    else {

                      if (interoFlag!=null) {
                        ridotto=0;
                      }
                      else {
                        intero=0;
                      }
                      try {
                        controllerBiglietteria.emettiTicket(proiezione, fila, posto, "no",intero,ridotto,IDAddetto);
                      }
                      catch (Exception ex) {
                          throw new ServletException("Errore Emissione Ticket", ex);
                      }
                    }
                }
                else {
                    throw new ServletException("Non hai selezionato alcun posto");
                }
            }
        }
        if (rimborso!=null) {
            for (int i = 0; i < rimborso.length; i++) {
                String string = rimborso[i];
                //element.getIDProiezione()+element.getIDFila()+element.getID()
                try {
                    posto=Integer.parseInt(string.substring(7));
                    fila=Integer.parseInt(string.substring(5,7));
                }
                catch(NumberFormatException e) {
                    throw new ServletException("Parametri della prenotazione non corretti", e);
                }
                proiezione=string.substring(0,5);
                if (posto>0 && fila>0) {
                    Collezione postiRitornati=null;
                    String[] postoSuCuiLavorare=null;
                    try {
                      postiRitornati=controllerBiglietteria.leggiPosti(proiezione,fila,posto);
                      postoSuCuiLavorare = ( (String[]) postiRitornati.getIndex(0));
                    }
                    catch (Exception e1) {
                      throw new ServletException("Errore nella comunicazione con il server SimplyTicket", e1);
                    }
                    if (postoSuCuiLavorare[2].equals("FALSE")) {
                        //JOptionPane.showMessageDialog(null,"            PostoOccupato","Errore",JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    else {

                      if (interoFlag!=null) {
                        ridotto=0;
                      }
                      else {
                        intero=0;
                      }
                      try {
                        controllerBiglietteria.annullaTicket(proiezione, posto, fila, "no");
                      }
                      catch (Exception ex) {
                          throw new ServletException("Errore Emissione Ticket", ex);
                      }
                    }
                }
                else {
                    throw new ServletException("Non hai selezionato alcun posto");
                }
            }
        }
        request.setAttribute("film", proiezione);
        this.forward("visualizzaPosti", request, response);
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
