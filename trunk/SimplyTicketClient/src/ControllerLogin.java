import java.rmi.*;
import java.util.*;
//import data_layer.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ControllerLogin extends Remote{

  /**Verifica l'username e password passate
   *
   * @param bigliettaio boolean - Se l'utente che tenta di loggarsi è un gestore o un bigliettaio
   * @param user String - La username inserita
   * @param pass String - La password inserita
   * @throws RemoteException - Viene laciata se c'è qualche errore nella comunicazione col server
   * @return int - L'id dell'addetto se è stato trovato, -1 altrimenti
   */

  int verifica(boolean bigliettaio,String user,String pass) throws RemoteException;
}
