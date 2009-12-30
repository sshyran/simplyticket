/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data_layer.Collezione;
import data_layer.CollezioneArrayList;
import data_layer.Film;
import data_layer.Proiezione;
import data_layer.Sala;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import web.ProiezioneGiornaliera;

/**
 *
 * @author William The Bloody
 */
public class tabelloneServlet extends HttpServlet {


    private ControllerUtenza controllerUtenza;
    private String rmi_host;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext application = config.getServletContext();
        rmi_host=config.getInitParameter("rmiregistry_host");
        if(rmi_host==null){rmi_host="127.0.0.1";}
        try {
            controllerUtenza = (ControllerUtenza) Naming.lookup("//"+rmi_host+"/controllerUtenza");
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
                //inserisci codice lavoro
                Collection result=new ArrayList();
                Collezione listaProiezioni=null;
                try{
                    listaProiezioni = controllerUtenza.leggi_info_Proiezioni();
                }
                catch(Exception e) {
                    throw new ServletException("Errore nel fetching delle proiezioni", e);
                }
                CollezioneArrayList proiezioniToken=new CollezioneArrayList();
                GregorianCalendar dataAttuale=new GregorianCalendar();
                GregorianCalendar dataProiezione=new GregorianCalendar();
                int indiceProiezione=0;
                int count=0;
                ProiezioneGiornaliera p;
                if(indiceProiezione<0)
                    indiceProiezione=0;
                for (int i = indiceProiezione; i < listaProiezioni.size(); i++) {
                    Proiezione pro=null;
                    //leggi I-esimo oggetto proiezione
                    try {
                      pro = (Proiezione) listaProiezioni.getIndex(i);
                    }
                    catch (Exception e) {
                       throw new ServletException("Errore nella gestione delle proiezioni", e);
                    }
                    dataProiezione=(GregorianCalendar)pro.getDataOraInizio().clone();
                    if(!dataProiezione.after(dataAttuale)){
                     if (i < listaProiezioni.size())
                          indiceProiezione = i+1;
                    }
                    else {
                        Film film=null;
                        Collezione listaFilm = null;

                        //leggi l'oggetto Film dal database con id=pro.getIDFilm()
                        try {
                          listaFilm = controllerUtenza.leggi_info_Film(pro.getIDFilm());
                          film = ((Film) (listaFilm.getIndex(0)));
                        }
                        catch (Exception e) {
                           throw new ServletException("Errore nella gestione dei dati relativi al film in proiezione "+pro.getID(), e);
                        }
                        Sala sala=null;
                        Collezione listaSala = null;

                        //leggi l'oggetto Sala dal database con id=pro.getIDSala()
                        try {
                          listaSala = controllerUtenza.leggi_info_Sala(pro.getIDSala());
                          sala = (Sala) listaSala.getIndex(0);
                        }
                        catch (Exception e) {
                          throw new ServletException("Errore nella gestione dei dati relativi alla sala per la proiezione "+pro.getIDSala(), e);
                        }                     

                        //calcola per la proiezione il numero di posti disponibili
                        int disponibilita = sala.getcapacita() - pro.getBigliettiVenduti();
                        String minuti;
                        String ora;
                        //formatta l'orario da restituire
                        if(pro.getDataOraInizio().get(GregorianCalendar.HOUR_OF_DAY)<10)
                          ora="0"+pro.getDataOraInizio().get(GregorianCalendar.HOUR_OF_DAY);
                        else
                          ora=""+pro.getDataOraInizio().get(GregorianCalendar.HOUR_OF_DAY);
                        if(pro.getDataOraInizio().get(GregorianCalendar.MINUTE)<10)
                          minuti="0"+pro.getDataOraInizio().get(GregorianCalendar.MINUTE);
                        else
                          minuti=""+pro.getDataOraInizio().get(GregorianCalendar.MINUTE);
                        p=new ProiezioneGiornaliera();
                        p.setDisponibilita(disponibilita);
                        p.setTitolo(film.getTitolo());
                        p.setSala(pro.getIDSala());
                        p.setOrario(ora+":"+minuti);
                        result.add(p);
                        count++;

                       //aggiungi questo array di stringhe in una collezione da restituire
                       //proiezioniToken.add(visualizza);
                    }
                }

                //Things to pass to the jsp:
                //number of row
                //a beans for each film
                request.setAttribute("NUM_PG", new Integer(count));
                request.setAttribute("PG", result);
                this.forward("tabellone.jsp", request, response);
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
