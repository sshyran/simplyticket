import java.rmi.*;
import java.util.*;
import data_layer.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ControllerAbbonamento extends Remote{

  boolean nuovoAbbonamento(String nome,String cognome,String indirizzo,String telefono,String residenza,String email,String idabbonamento,double costo,String tipo,String data,int filmDisponibili,int addetto) throws RemoteException;

  boolean modificaAbbonamento(String nome,String cognome,String indirizzo,String telefono,String residenza,String email,String idabbonamento,double costo,String tipo,String data,int filmDisponibili,int addetto) throws RemoteException;

  boolean rinnovaAbbonamento(String idabbonamento,double costo,String tipo,String data,int filmDisponibili,int addetto) throws RemoteException;

  boolean eliminaAbbonamento(String idabbonamento) throws RemoteException;

  Collezione leggiAbbonamenti() throws RemoteException;
}
