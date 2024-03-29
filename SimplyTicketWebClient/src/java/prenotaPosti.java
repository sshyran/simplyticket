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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.parser.JSONParser;
import org.json.simple.*;
import org.json.simple.parser.ParseException;

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
     * Questo metodo risponde alle richieste GET/POST ed effettua la prenotazione e il rimborso di posti per una proiezione
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
        int IDAddetto=0;
        if (mySession.getAttribute(getServletContext().getInitParameter("loggedSession"))==null)
            throw new ServletException("Login non effettuato");
        try {
            IDAddetto=Integer.parseInt((String)mySession.getAttribute(getServletContext().getInitParameter("loggedSession")));
        }
        catch (NumberFormatException e) {
            throw new ServletException("Addetto non valido",e);
        }
        double intero=0;
        double ridotto=0;
        String tipoBiglietto=request.getParameter("tipo_biglietto");
        if (tipoBiglietto==null || tipoBiglietto.equals("")) {
            throw new ServletException("Tipo biglietto non selezionato");
        }
        try {
            if (tipoBiglietto.equalsIgnoreCase("intero")) {
                intero=Double.parseDouble(request.getParameter("txtCostoIntero"));
            }
            else {
                ridotto=Double.parseDouble(request.getParameter("txtCostoRidotto"));
            }
        }
        catch (NumberFormatException e) {
            throw new ServletException("Prezzo non valido");
        }
        String proiezione = request.getParameter("idProiezione");
        proiezione=proiezione.trim();
        if (proiezione==null || proiezione.equals("")) {
            throw new ServletException("Proiezione non valida"+proiezione);
        }
        String value=request.getParameter("dati");
        if (value!=null && !value.equals("")) {
            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(value);
                JSONObject root = (JSONObject) obj;
                JSONObject lista = (JSONObject) root.get("lista");
                JSONArray prenota = (JSONArray)(lista.get("prenota"));
                JSONObject posto=null;
                int fila=0,id=0;
                for (Iterator i = prenota.iterator(); i.hasNext();) {
                    posto = (JSONObject)i.next();
                    posto=(JSONObject) posto.get("posto");
                    fila=Integer.parseInt((String)(posto.get("fila")));
                    id=Integer.parseInt((String)(posto.get("id")));
//                    out.println("Prenota: "+posto.get("proiezione")+" "+posto.get("id")+" "+posto.get("fila")+"<br>\n");
                    if (id>0 && fila>0) {
                        Collezione postiRitornati=null;
                        String[] postoSuCuiLavorare=null;
                        try {
                            postiRitornati=controllerBiglietteria.leggiPosti(proiezione,fila,id);
                            postoSuCuiLavorare = ( (String[]) postiRitornati.getIndex(0));
                        }
                        catch (java.rmi.ConnectException e) {
                            this.initialize();
                            try {
                                postiRitornati=controllerBiglietteria.leggiPosti(proiezione,fila,id);
                                postoSuCuiLavorare = ( (String[]) postiRitornati.getIndex(0));
                            } catch (Exception ex) {
                                throw new ServletException("Errore nella comunicazione con il server SimplyTicket", ex);
                            }
                        }
                        catch (Exception e1) {
                          throw new ServletException("Errore nella comunicazione con il server SimplyTicket", e1);
                        }
                        if (postoSuCuiLavorare[2].equals("TRUE")) {
                            //JOptionPane.showMessageDialog(null,"            PostoOccupato","Errore",JOptionPane.ERROR_MESSAGE);
//                            throw new ServletException("posto gia' occupato");
                        }
                        else {
                          if (tipoBiglietto.equalsIgnoreCase("intero")) {
                            ridotto=0;
                          }
                          else {
                            intero=0;
                          }
                          try {
                            controllerBiglietteria.emettiTicket(proiezione, fila, id, "no",intero,ridotto,IDAddetto);
                          }
                          catch (java.rmi.ConnectException e) {
                              this.initialize();
                              try {
                                  controllerBiglietteria.emettiTicket(proiezione, fila, id, "no",intero,ridotto,IDAddetto);
                              } catch (Exception ex) {
                                  throw new ServletException("Errore Emissione Ticket", ex);
                              }
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
                JSONArray rimborso = (JSONArray)lista.get("rimborsa");
                for (Iterator i = rimborso.iterator(); i.hasNext();) {
                    posto = (JSONObject)i.next();
                    posto=(JSONObject) posto.get("posto");
                    fila=Integer.parseInt((String)(posto.get("fila")));
                    id=Integer.parseInt((String)(posto.get("id")));
//                    out.println("Rimborso: "+posto.get("proiezione")+" "+posto.get("id")+" "+posto.get("fila")+"<br>\n");
                    if (id>0 && fila>0) {
                        Collezione postiRitornati=null;
                        String[] postoSuCuiLavorare=null;
                        try {
                            postiRitornati=controllerBiglietteria.leggiPosti(proiezione,fila,id);
                            postoSuCuiLavorare = ( (String[]) postiRitornati.getIndex(0));
                        }
                        catch (java.rmi.ConnectException e) {
                            this.initialize();
                            try {
                                postiRitornati=controllerBiglietteria.leggiPosti(proiezione,fila,id);
                                postoSuCuiLavorare = ( (String[]) postiRitornati.getIndex(0));
                            } catch (Exception ex) {
                                throw new ServletException("Errore nella comunicazione con il server SimplyTicket", ex);
                            }
                        }
                        catch (Exception e1) {
                            throw new ServletException("Errore nella comunicazione con il server SimplyTicket", e1);
                        }
                        if (postoSuCuiLavorare[2].equals("FALSE")) {
                                    //JOptionPane.showMessageDialog(null,"            PostoOccupato","Errore",JOptionPane.ERROR_MESSAGE);
//                                    throw new ServletException("Posto già rimborsato");
                        }
                        else {
                            if (tipoBiglietto.equalsIgnoreCase("intero")) {
                                ridotto=0;
                            }
                            else {
                                intero=0;
                            }
                            try {
                                controllerBiglietteria.annullaTicket(proiezione, id, fila, "no");
                            }
                            catch (java.rmi.ConnectException e) {
                                this.initialize();
                                try {
                                    controllerBiglietteria.annullaTicket(proiezione, id, fila, "no");
                                } catch (Exception ex) {
                                    throw new ServletException("Errore Emissione Ticket", ex);
                                }
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
            } catch (ParseException ex) {
//                    Logger.getLogger(prenotaPosti2.class.getName()).log(Level.SEVERE, null, ex);
                throw new ServletException("Errore json",ex);
            }
        }
        String locandinaPath=request.getParameter("locandina"+proiezione);
        if (locandinaPath==null || locandinaPath.equals("")) {
            throw new ServletException("Locandina non pervenuta");
        }
        else {
            request.setAttribute("locandina"+proiezione, locandinaPath);
        }
        request.setAttribute("film", proiezione);
        this.forward("visualizzaPosti", request, response);
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
