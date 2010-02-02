/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import data_layer.Collezione;
import data_layer.CollezioneArrayList;
import data_layer.Film;
import data_layer.Proiezione;
import data_layer.Sala;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.ProiezioneGiornaliera;

/**
 *
 * @author Raffaele
 */
public class AjaxServlet extends HttpServlet {

    private ControllerUtenza controllerUtenza;
    private String rmi_host;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext application = config.getServletContext();
        rmi_host=config.getInitParameter("rmiregistry_host");
        if(rmi_host==null){rmi_host="127.0.0.1";}
        this.initialize();
    }

    private void initialize() {
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
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String xmlResponse="{\"lista\":\n\t[\n";
        boolean not_first = false;
        if (controllerUtenza==null) {
                    this.initialize();
                }
                //inserisci codice lavoro
                Collection result=new ArrayList();
                Collezione listaProiezioni=null;
                try{
                    listaProiezioni = controllerUtenza.leggi_info_Proiezioni();
                }
                catch (java.rmi.ConnectException e) {
                    this.initialize();
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
                String xmlElement;
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
                        catch (java.rmi.ConnectException e) {
                            this.initialize();
                            listaFilm = controllerUtenza.leggi_info_Film(pro.getIDFilm());
                            try {
                                film = ((Film) (listaFilm.getIndex(0)));
                            } catch (Exception ex) {
                                throw new ServletException("Errore nella gestione dei dati relativi al film in proiezione "+pro.getID(), e);
                            }
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
                        catch (java.rmi.ConnectException e) {
                            this.initialize();
                            listaSala = controllerUtenza.leggi_info_Sala(pro.getIDSala());
                            try {
                                sala = (Sala) listaSala.getIndex(0);
                            } catch (Exception ex) {
                                throw new ServletException("Errore nella gestione dei dati relativi alla sala per la proiezione "+pro.getIDSala(), e);
                            }
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
                        if(not_first){
                            xmlElement=",\n";
                        }else {
                            xmlElement="";
                        }
                        not_first=true;
                        xmlElement=xmlElement+"\t{\"proiezione\":\n\t\t{\n\t\t\"id\":\""+pro.getID()+"\",\n\t\t" +
                                "\"sala\":\""+pro.getIDSala()+"\",\n\t\t" +
                                "\"titolo\":\""+film.getTitolo()+"\",\n\t\t\"disponibilita\":\""+disponibilita+"\",\n\t\t" +
                                "\"inizio\":\""+ora+":"+minuti+"\"\n\t\t}\n\t}";
                        xmlResponse=xmlResponse+xmlElement;
                        count++;

                       //aggiungi questo array di stringhe in una collezione da restituire
                       //proiezioniToken.add(visualizza);
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
