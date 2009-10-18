/**
 * <p>Title: ControllerUtenzaImpl </p>
 * <p>Description:  Realizzazione della porzione relativa al server. </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author: Cepparulo Marco
 * @version 1.0
 */



import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Calendar;
import data_layer.Film;
import data_layer.Proiezione;
import data_layer.Sala;
import data_layer.Collezione;
import data_layer.CollezioneArrayList;




/** Application Layer
 *  La classe ControllerUtenzaImpl implementa l'interfaccia dell'oggetto remoto
 *  con le relative funzionalità di cui il terminale staico ha bisogno.
 *  La realizzazione è necessaria per l'implementazione delle funzionalità
 *  che il terminale Statico utilizzerà per visualizzare le info spettacoli.
 */

public class ControllerUtenzaImpl extends UnicastRemoteObject implements ControllerUtenza
{

  public ControllerUtenzaImpl() throws RemoteException{
  }


 /**Funzione che passa la data attuale alla funzione Proiezione.loadProiezioni per ottenere
  * le proiezioni odierne dal database.
  * @pre:nel client in cui si effettua la chiamata sia stato fatto il lookup del ControllerUtenza
  * @post:viene restituita una collezione di oggetti Proiezione ordinati in base all'ora oppure   * restituisce una collezione vuota
 */

 public Collezione leggi_info_Proiezioni() throws   RemoteException
 {

   Collezione listaProiezioni=null;

   //Leggi la data Attuale
   GregorianCalendar calendario=new GregorianCalendar();
   //calendario.set(Calendar.MONTH,calendario.get(Calendar.MONTH)+1);

   //leggi le proiezioni della giornata dal  database settando i giusti parametri di    //loadProiezioni
   try {

    listaProiezioni=Proiezione.loadProiezioni("",calendario,-1,"",-1);

   }
   catch(Exception e) {

    System.out.println("Errore in ControllerUtenzaImpl:");
    e.printStackTrace();
   }

  //ritorna le proiezioni in una collezione
  return listaProiezioni;
 }


 /**Funzione che ottiene dal database l'oggetto Film con id=idFilm dato in input
  * @pre: idFilm!=null;
  * @pre:nel client in cui si effettua la chiamata sia stato fatto il lookup del ControllerUtenza
  * @post:viene restituita un oggetto Film
 */
 public Collezione leggi_info_Film(int idFilm) throws RemoteException
 {

    Collezione listaFilm=null;

  //ottieni l'oggetto film che ha id=idfilm
  try
   {

    //setta i parametri da passare a Film.loadFilm
    String[] parametri=new String[8];
    parametri[0]=""+idFilm;
    for(int i=1;i<=7;i++)
      parametri[i]="";

    listaFilm=Film.loadFilm(parametri);
   }
  catch(Exception e)
  {

   System.out.println("Errore in ControllerUtenzaImpl:");
    e.printStackTrace();
  }

  //restituisci il film
  return listaFilm;
  }



  /**Funzione che ottiene dal database l'oggetto sala con id=idSala dato in input
  * @pre: idSala!=null;
  * @pre:nel client in cui si effettua la chiamata sia stato fatto il lookup del ControllerUtenza
  * @post:viene restituito un oggetto Sala
 */
 public Collezione leggi_info_Sala(String idSala) throws RemoteException
 {

   Collezione listaSala=null;
   //ottieni l'oggetto sala dal database
   try
   {
     listaSala = Sala.loadSala(idSala, 0, "");
   }
  catch(Exception e)
   {
    System.out.println("Errore in ControllerUtenzaImpl:");
    e.printStackTrace();
   }

   //restituisci la sala
  return listaSala;

 }

}
