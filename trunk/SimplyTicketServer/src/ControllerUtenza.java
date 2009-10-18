import java.rmi.*;
import data_layer.Collezione;

/**
 * <p>Title: ControllerUtenza </p>
 * <p>Description:  Interfaccia remota dell'oggetto ControllerUtenza. </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author: Cepparulo Marco
 * @version 1.0
 */

public interface ControllerUtenza extends Remote
{
  /**Carica tutte le proiezioni del giorno corrente
   *
   * @throws RemoteException
   * @return Collezione - Una collezione di oggetti contenenti informazioni sulle proiezioni attuali
   * @pre:nel client in cui si effettua la chiamata sia stato fatto il lookup del ControllerUtenza
   * @post:viene restituita una collezione di oggetti Proiezione ordinati in base all'ora oppure   * restituisce una collezione vuota
   */
  Collezione leggi_info_Proiezioni() throws RemoteException;

  /**Legge le informazioni relative ad un solo film
   *
   * @param idFilm int - L'ID del film di cui si vogliono conoscere i particolari
   * @throws RemoteException - Se la comunicazione col server viene interrota
   * @return Collezione - Una collezione di oggetti cotenenti inormazioni sui film (normalmente la collezione ha al suo interno solo un oggetto)
   * @pre: idFilm!=null;
   * @pre:nel client in cui si effettua la chiamata sia stato fatto il lookup del ControllerUtenza
   * @post:viene restituita un oggetto Film
   */

  Collezione leggi_info_Film(int idFilm) throws RemoteException;

  /**Legge le informazioni relative ad una sala
   *
   * @param idSala String - L'ID della sala di cui si vogliono conoscere la caratteristiche
   * @throws RemoteException - Se la comunicazione col server viene interrota
   * @return Collezionev - Una collezione di oggetti cotenenti inormazioni sulle sale (normalmente la collezione ha al suo interno solo un oggetto)
   * @pre: idSala!=null;
   * @pre:nel client in cui si effettua la chiamata sia stato fatto il lookup del ControllerUtenza
   * @post:viene restituito un oggetto Sala
   */
  Collezione leggi_info_Sala(String idSala) throws RemoteException;
}
