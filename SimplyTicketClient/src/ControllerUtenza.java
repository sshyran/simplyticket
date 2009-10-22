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


  Collezione leggi_info_Proiezioni() throws RemoteException;

  Collezione leggi_info_Film(int idFilm) throws RemoteException;

  Collezione leggi_info_Sala(String idSala) throws RemoteException;
}
