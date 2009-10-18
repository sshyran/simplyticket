import data_layer.Posto;
import data_layer.Biglietto;
import data_layer.Proiezione;
import data_layer.Sala;
import data_layer.Film;
import data_layer.CollezioneArrayList;
import data_layer.Collezione;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.*;
import java.util.*;
import data_layer.*;
import java.lang.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ControllerBiglietteriaImpl extends UnicastRemoteObject implements ControllerBiglietteria{

  public ControllerBiglietteriaImpl() throws RemoteException {
  }

  /**Effettua la registrazione dell'emmisione di un ticket
   *
   * @param idProiezione String - La Proiezione per la quale � stato venduto il biglietto
   * @param fila int - La fila di appartenenza del biglietto
   * @param posto int - Il numero identificativo del posto
   * @param idAbbonamento String - Il codice dell'abbonamento
   * @param costo double - Il costo del biglietto intero
   * @param costoRid double - Il costo del biglietto intero
   * @param addetto int - L'addetto che ha effettuato la registrazione
   * @throws RemoteException - Viene laciata se ci sono problemi con la comunicazione col server
   * @throws Exception - Viene laciata se i valori passatti non sono corretti e/o definiti
   * @pre:nel client in cui si effettua la chiamata sia stato fatto il lookup del ControllerLogin
   * @post:Registra i dati del ticket venduto, aggiornando la disponibilit� dei posti
   */

  public void emettiTicket(String idProiezione, int fila, int posto,String idAbbonamento,double costo,double costoRid,int addetto) throws RemoteException,Exception {
    Collezione proiezioni=null;
    try {
      proiezioni = Proiezione.loadProiezioni(idProiezione, null, -1, "", -1);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    Proiezione proiezione=null;
    proiezione=(Proiezione)proiezioni.getIndex(0);
     proiezione.setBigliettiVenduti(proiezione.getBigliettiVenduti()+1);
     try {
       proiezione.updateProiezione();
     }
     catch (Exception ex) {
       ex.printStackTrace();
     }
      Posto postoDaSettare=null;
      Collezione posti=null;
      try {
        posti=Posto.loadPosto(posto,true,false,fila,idProiezione);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      postoDaSettare=(Posto)posti.getIndex(0);
      postoDaSettare.setOccupato(true);
      try {
        postoDaSettare.updatePosto();
      }
      catch(SQLException e) {
        e.printStackTrace();
      }
      Biglietto nuovo=null;
      String id=idProiezione;
      if (fila<10) {
        id=id+"0"+fila;
      }
      else {
        id=id+fila;
      }
      if (posto<10) {
        id=id+"0"+posto;
      }
      else {
        id = id + posto;
      }
      try {
        nuovo = new Biglietto(id, costo, costoRid,idProiezione, "", fila,posto, addetto);
      }
      catch (Exception e) {

        e.printStackTrace();
      }
      try {
        nuovo.storageBiglietto();
      }
      catch(Exception e) {
        System.out.println("Errore storage biglietto: ");
        e.printStackTrace();
      }
  }

  /**Effettua la registrazione dell'annullamento di un ticket
   *
   * @param idproiezione String - La Proiezione per la quale � stato venduto il biglietto
   * @param posto int - Il numero identificativo del posto
   * @param fila int - La fila di appartenenza del biglietto
   * @param idAbbonamento String - Il codice dell'abbonamento
   * @throws RemoteException - Viene laciata se ci sono problemi con la comunicazione col server
   * @throws Exception - Viene laciata se i valori passatti non sono corretti e/o definiti
   * @pre:nel client in cui si effettua la chiamata sia stato fatto il lookup del ControllerLogin
   * @post:Rimuove i dati relativi al biglietto che si vuole annullare, se mancano pi� di dieci minuti alla proiezione
   */

  public void annullaTicket(String idproiezione, int posto, int fila, String idAbbonamento) throws RemoteException,Exception{
    GregorianCalendar calendario = new GregorianCalendar();
    //calendario.set(Calendar.MONTH, calendario.get(Calendar.MONTH) + 1);
    calendario.set(calendario.MINUTE, calendario.get(calendario.MINUTE) + 10);
    int ora = calendario.get(calendario.HOUR_OF_DAY);
    int minuti = calendario.get(calendario.MINUTE);
    Collezione proiezioni = null;
    try {
      proiezioni = Proiezione.loadProiezioni(idproiezione, null, -1, "", -1);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    Proiezione proiezione = null;
    proiezione = (Proiezione) proiezioni.getIndex(0);
    GregorianCalendar orarioProiezione=(GregorianCalendar)proiezione.getDataOraInizio();
    if ((ora<orarioProiezione.get(orarioProiezione.HOUR_OF_DAY))||((ora==orarioProiezione.get(orarioProiezione.HOUR_OF_DAY)) &&(minuti<orarioProiezione.get(orarioProiezione.MINUTE)))) {
    try {
      proiezione.setBigliettiVenduti(proiezione.getBigliettiVenduti() - 1);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    try {
      proiezione.updateProiezione();
    }
    catch (Exception e1) {
      e1.printStackTrace();
    }
    Posto postoDaSettare = null;
    Collezione posti = null;
    try {
      posti = Posto.loadPosto(posto, true, false, fila, idproiezione);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    postoDaSettare = (Posto) posti.getIndex(0);
    postoDaSettare.setOccupato(false);
    try {
      postoDaSettare.updatePosto();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    Collezione biglietti = null;
    Biglietto nuovo = null;
    String id=idproiezione;
      if (fila<10) {
        id=id+"0"+fila;
      }
      else {
        id=id+fila;
      }
      if (posto<10) {
        id=id+"0"+posto;
      }
      else {
        id = id + posto;
      }
    String[] array = new String[8];
    array[0] = id;
    array[1] = "0.0";
    array[2] = "0.0";
    array[3] = "";
    array[4] = "";
    array[5] = "0";
    array[6] = "0";
    array[7] = "0";
    try {
      biglietti = Biglietto.LoadBiglietto(array);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    try {
      nuovo = (Biglietto) biglietti.getIndex(0);
    }
    catch (Exception e1) {
      System.out.println("Errore cast Biglietto");
      e1.printStackTrace();
    }
    try {
      nuovo.DeleteBiglietto();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    }
    else
      throw new Exception("Troppo tardi per rimborsare");
  }

  /**Elenca tutti gli spettacoli/Proiezioni della giornata
   *
   * @throws RemoteException - Viene laciata se ci sono problemi con la comunicazione col server
   * @return Collezione - Una collezione di oggetti, dove ogni oggetto � un array di String di cinque elementi: il primo � l'id della proiezione,il secondo � l'id della sala, il terzo � il titolo del film proiettato, il quarto � la disponibilit� di posti e il quinto � il PATH dellla locandina
   * @pre:nel client in cui si effettua la chiamata sia stato fatto il lookup del ControllerLogin
   * @post:Una collezione di oggetti contenenti le informazioni sulla proiezione odierne, se oggi non ci sono proiezioni restituisce una collezione vuota
   */

  public Collezione leggiListaSpettacoli() throws RemoteException {
    Collezione col = null;
    GregorianCalendar calendario=new GregorianCalendar();
    //calendario.set(calendario.MONTH,calendario.get(calendario.MONTH)+1);
    int ora=calendario.get(calendario.HOUR_OF_DAY);
    int minuti=calendario.get(calendario.MINUTE);
    try {
      col = data_layer.Proiezione.loadProiezioni("", calendario, -1, "", -1);
    }
    catch (Exception e) {
      System.out.println("Errore in ControllerBiglietteriaImpl: " +e.getMessage());
      e.printStackTrace();
    }
    Collezione daRestituire = new CollezioneArrayList();
    String[] array;
    Proiezione proiezione=null;
    Sala sala = null;
    Film film = null;
    GregorianCalendar cal=null;
    int oraProiezione=0;
    while (col.hasNext()) {
      array = new String[5];
      proiezione = (Proiezione) col.getNext();
      cal=(GregorianCalendar)proiezione.getDataOraInizio();
      oraProiezione=cal.get(cal.HOUR_OF_DAY);
      if (((cal.get(cal.HOUR_OF_DAY))>ora) || (((cal.get(cal.HOUR_OF_DAY))>=ora)&&(cal.get(cal.MINUTE))>minuti)) {
        array[0] = proiezione.getID();
        array[1] = proiezione.getIDSala();
        String[] parametri = new String[8];
        parametri[0] = "" + proiezione.getIDFilm();
        for (int i = 1; i <= 7; i++)
          parametri[i] = "";
        Collezione listaFilm = null;
        try {
          listaFilm = Film.loadFilm(parametri);
        }
        catch (SQLException e) {
          System.out.println("Errore LoadFilm:");
          e.printStackTrace();
        }
        catch (Exception ex) {
          System.out.println("Errore LoadFilm:");
          ex.printStackTrace();
        }
        while(listaFilm.hasNext()) {
          film=( (Film) listaFilm.getNext());
          array[2] = film.getTitolo();
          array[4] = film.getLocandina();
        }
        try {
          sala = (Sala) (Sala.loadSala(proiezione.getIDSala(), 0, "").getNext());
        }
        catch (SQLException e) {
          e.printStackTrace();
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
        int disponibilita = sala.getcapacita() - proiezione.getBigliettiVenduti();
        array[3] = "" + disponibilita;
        daRestituire.add(array);
      }
    }
    return daRestituire;
  }

  /**Elenca tutti i posti di una proiezione
   *
   * @param idProiezione String - La Proiezione per la quale si vuole conoscere lo stato dei posti
   * @param fila int - La fila nella quale sta il posto da caricare
   * @param posto int - IL posto da caricare
   * @throws RemoteException - Viene laciata se ci sono problemi con la comunicazione col server
   * @return Collezione - Una collezione di oggetti, dove ogni oggetto � un array di String di tre elementi: il primo � l'id della fila,il secondo � l'id del posto e il terzo � lo stato del posto
   */

    public Collezione leggiPosti(String idProiezione,int fila,int posto) throws RemoteException {
      Collezione col = null;
      try {
        col = Posto.loadPosto( posto, true, false, fila, idProiezione);
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
      Collezione daRestiture = new CollezioneArrayList();
      String[] array = null;
      Posto postoDaRestituire = null;
      for (int i = 0; i < col.size(); i++) {
        try {
          postoDaRestituire = (Posto) col.getIndex(i);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        array = new String[3];
        array[0] = "" + postoDaRestituire.getID();
        array[1] = "" + postoDaRestituire.getIDFila();
        if (postoDaRestituire.getOccupato())
          array[2] = "TRUE";
        else
          array[2] = "FALSE";
        daRestiture.add(array);
      }
      return daRestiture;
    }
  }
