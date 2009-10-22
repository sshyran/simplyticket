import java.rmi.*;
import java.util.*;
import data_layer.Collezione;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Spinelli Raffaele
 * @version 1.0
 */

public interface ControllerBiglietteria extends Remote{

  /**Effettua la registrazione dell'emmisione di un ticket
   *
   * @param idProiezione String - La Proiezione per la quale è stato venduto il biglietto
   * @param fila int - La fila di appartenenza del biglietto
   * @param posto int - Il numero identificativo del posto
   * @param idAbbonamento String - Il codice dell'abbonamento
   * @param costo double - Il costo del biglietto intero
   * @param costoRid double - Il costo del biglietto intero
   * @param addetto int - L'addetto che ha effettuato la registrazione
   * @throws RemoteException - Viene laciata se ci sono problemi con la comunicazione col server
   * @throws Exception - Viene laciata se i valori passatti non sono corretti e/o definiti
   */

  void emettiTicket(String idProiezione,int fila,int posto,String idAbbonamento,double costo,double costoRid,int addetto) throws RemoteException,Exception;

  /**Effettua la registrazione dell'annullamento di un ticket
   *
   * @param idproiezione String - La Proiezione per la quale è stato venduto il biglietto
   * @param posto int - Il numero identificativo del posto
   * @param fila int - La fila di appartenenza del biglietto
   * @param idAbbonamento String - Il codice dell'abbonamento
   * @throws RemoteException - Viene laciata se ci sono problemi con la comunicazione col server
   * @throws Exception - Viene laciata se i valori passatti non sono corretti e/o definiti
   */

  void annullaTicket(String idproiezione,int posto,int fila,String idAbbonamento) throws RemoteException,Exception;

  /**Elenca tutti gli spettacoli/Proiezioni della giornata
   *
   * @throws RemoteException - Viene laciata se ci sono problemi con la comunicazione col server
   * @return Collezione - Una collezione di oggetti, dove ogni oggetto è un array di String di cinque elementi: il primo è l'id della proiezione,il secondo è l'id della sala, il terzo è il titolo del film proiettato, il quarto è la disponibilità di posti e il quinto è il PATH dellla locandina
   */

  Collezione leggiListaSpettacoli() throws RemoteException;

  /**Elenca tutti i posti di una proiezione
   *
   * @param idProiezione String - La Proiezione per la quale si vuole conoscere lo stato dei posti
   * @throws RemoteException - Viene laciata se ci sono problemi con la comunicazione col server
   * @return Collezione - Una collezione di oggetti, dove ogni oggetto è un array di String di tre elementi: il primo è l'id della fila,il secondo è l'id del posto e il terzo è lo stato del posto
   */

  Collezione leggiPosti(String idProiezione,int fila,int posto) throws RemoteException;
}
