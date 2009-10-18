import data_layer.Addetto;
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

public class ControllerLoginImpl extends UnicastRemoteObject implements ControllerLogin{

  public ControllerLoginImpl() throws RemoteException {

  }

  /**Verifica l'username e password passate
   *
   * @param bigliettaio boolean - Se l'utente che tenta di loggarsi � un gestore o un bigliettaio
   * @param user String - La username inserita
   * @param pass String - La password inserita
   * @throws RemoteException - Viene laciata se c'� qualche errore nella comunicazione col server
   * @return int - L'id dell'addetto se � stato trovato, -1 altrimenti
   * @pre:nel client in cui si effettua la chiamata sia stato fatto il lookup del ControllerLogin
   * @post:ritona un intr indicante l'id dell'addeto ke ha fatto il login se c'� riscontro, -1 altrimenti
   */

  public int verifica(boolean bigliettaio,String user,String pass) throws RemoteException{
    String[] array=new String[5];
    array[0]="0";
    array[1]=user;
    array[2]=pass;
      if (bigliettaio)
        array[3] = "TRUE";
      else
        array[3] = "FALSE";
    array[4]="0";
    Collezione col=null;
    Addetto addetto=null;
    int ritorno=-1;
    try {
      col=Addetto.LoadAddetti(array);
      if (!col.isEmpty()) {
        addetto=(Addetto)col.getNext();
        ritorno=addetto.getIDAddetto();
      }
    }
    catch (SQLException ex) {
      System.out.println("Errore sql: "+ex.getMessage());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return ritorno;
  }

}
