package data_layer;
import java.sql.*;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


/**
 * <p>Title: Data Layer</p>
 * <p>Description: Il Package contenente tutto il Data Layer;le classi in esso contenute, sono classi ke interagiscono direttamente con il database</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Spinelli Raffaele
 * @version 1.0
 */

public class Posto implements Serializable{

  /**Costruttore pubblico
   * @param id int - identificativo posto
   * @param occupato boolean - valore booleano ad indicare se un posto e' occupato o meno
   * @param fila int - numero di fila
   * @param proiezione String - proiezione a cui si riferisce il posto
   * @throws Exception - eccezione lancviata in caso di parametri non validi
   * * */

  public Posto(int id,boolean occupato,int fila,String proiezione) throws Exception{
    if (id>0 && fila>0 && proiezione!=null && proiezione!="") {
      ID=id;
      Occupato=occupato;
      IDFila=fila;
      IDProiezione=proiezione;
    }
    else
      throw new Exception("Posizione specificata non valida. Controlla i valori immessi");
  }

  /**Ritorna l'ID del posto
    * @return int - identificativo del posto
    * */

  public int getID() {
    return this.ID;
  }

  /**Ritorna un valore booleano ad indicare se il posto e' occupat o meno
 * @return boolean - valore booleano ad indicare se un posto e' occupato o meno
 * */


  public boolean getOccupato() {
    return this.Occupato;
  }

  /**Setta il posto come occupato
  * context Posto::setOccupato(occupato) post:self.Occupato==occupato
  * @param occupato boolean - valore booleano per settare un posto come occupato o meno
  * */


  public void setOccupato(boolean occupato) {
      Occupato=occupato;
  }

  /**Ritorna l'ID della fila
  * @return int - numero della fila
  * */


  public int getIDFila() {
    return this.IDFila;
  }

  /**Setta l'ID della fila
  * context Posto::setIDFila(fila) pre:fila>0
  * context Posto::setIDFila(fila) post:self.IDFila==fila
  * @param fila int - identificativo della fila da settare
  * @throws Exception - eccezione lanciata in caso di parametro non valido
  * */


  public void setIDFila(int fila) throws Exception{
    if (fila>0)
      IDFila=fila;
    else
      throw new Exception("Bisogna specificare la fila di appartenenza");
  }

  /**Ritorna l'Id della proiezione
    * @return String - identificativo della proiezione
    * */

  public String getIDProiezione() {
    return this.IDProiezione;
  }

  /**Setta l'Id della proiezione
  * context Posto::setIDProiezione(proiezione) pre:proiezione!=null and ""
  * context Posto::setIDProiezione(proiezione) post:self.IDProiezione==proiezione
  * @param proiezione String - identificativo della proiezione di cuoi settare l'ID
  * @throws Exception - eccezione lanciata in caso di parametro non valido
  * */


  public void setIDProiezione(String proiezione) throws Exception{
    if (proiezione!=null && proiezione!="")
      IDProiezione=proiezione;
    else
      throw new Exception("Bisogna specificare la fila di appartenenza");
  }

  /**Ritorna una collezione di sale
    * @return Collezone -collezione di sale
    * @param id int - identificativo della sala
    * @param occupato boolean - valore booleano ad indicare se il posto e' occupato o meno
    * @param libero boolean - valore booleano ad indicare se il posto e' libero o meno
    * @param fila int - numero di fila
    * @param proiezione String - proiezione associata alla sala
    * @throws SQLException - eccezione lanciata in caso di errore nel caricamento dati dal DB
    * @throws Exception - eccezione lanciata in caso che i dati letti per costruire un oggetto non siano validi
    * */

  public static Collezione loadPosto(int id,boolean occupato,boolean libero,int fila,String proiezione) throws SQLException,Exception{
    setConnection();
    String query="SELECT * FROM Posto";
    if (id>0) {
      query=query+" WHERE ID=?";
      if (occupato && libero) {
        query=query+" AND Occupato=?";
        if (fila>0) {
          query = query + " AND IDFila=?";
          if (proiezione!= null && proiezione!="")
            query = query + " AND IDProiezione=?";
        }
        else {
          if (proiezione!= null && proiezione!="")
            query = query + " AND IDProiezione=?";
        }
      }
      else
      if (fila>0) {
          query = query + " AND IDFila=?";
          if (proiezione!= null && proiezione!="")
            query = query + " AND IDProiezione=?";
        }
        else {
          if (proiezione!= null && proiezione!="")
            query = query + " AND IDProiezione=?";
        }
    }
    else {
      if (occupato && libero) {
        query=query+" WHERE Occupato=?";
        if (fila>0) {
          query = query + " AND IDFila=?";
          if (proiezione!= null && proiezione!="")
            query = query + " AND IDProiezione=?";
        }
        else {
          if (proiezione!= null && proiezione!="")
            query = query + " AND IDProiezione=?";
        }
      }
      else
      if (fila>0) {
          query = query + " WHERE IDFila=?";
          if (proiezione!= null && proiezione!="")
            query = query + " AND IDProiezione=?";
        }
        else {
          if (proiezione!= null && proiezione!="")
            query = query + " WHERE IDProiezione=?";
        }
    }
    query=query+";";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    if (id>0) {
      preparedQuery.setInt(1,id);
      if (occupato && libero) {
        preparedQuery.setBoolean(2,occupato);
        if (fila>0) {
          preparedQuery.setInt(3,fila);
          if (proiezione!= null && proiezione!="")
            preparedQuery.setString(4,proiezione);
        }
        else {
          if (proiezione!=null && proiezione!="")
            preparedQuery.setString(3,proiezione);
        }
      }
      else
      if (fila>0) {
          preparedQuery.setInt(2,fila);
          if (proiezione!= null && proiezione!="")
            preparedQuery.setString(3,proiezione);
        }
        else {
          if (proiezione!= null && proiezione!="")
            preparedQuery.setString(2,proiezione);
        }
    }
    else {
      if (occupato && libero) {
        preparedQuery.setBoolean(1,occupato);
        if (fila>0) {
          preparedQuery.setInt(2,fila);
          if (proiezione!= null && proiezione!="")
            preparedQuery.setString(3,proiezione);
        }
        else {
          if (proiezione!= null && proiezione!="")
            preparedQuery.setString(2,proiezione);
        }
      }
      else
      if (fila>0) {
          preparedQuery.setInt(1,fila);
          if (proiezione!= null && proiezione!="")
            preparedQuery.setString(2,proiezione);
        }
        else {
          if (proiezione!= null && proiezione!="")
            preparedQuery.setString(1,proiezione);
        }
    }
    ResultSet Risultati=preparedQuery.executeQuery();
    Collezione collezioneDiPosti=new CollezioneArrayList();
    Posto nuova;
    while(Risultati.next()) {
      nuova=new Posto(Risultati.getInt("ID"),Risultati.getBoolean("Occupato"),Risultati.getInt("IDFila"),Risultati.getString("IDProiezione"));
      collezioneDiPosti.add(nuova);
    }
    return collezioneDiPosti;
  }

  /**Memorizza un posto nella sala
     * context Posto::storagePosto(con) pre:con!=null
     * @throws SQLException - eccezione lanciata in caso di errore in fase memorizzazione dati nel DB
     * @throws Exception - eccezione lanciata in caso di errore
     * */

  public void storagePosto() throws SQLException,Exception{
    this.setConnection();
    String query="INSERT INTO Posto(ID,Occupato,IDFila,IDProiezione) values(?,?,?,?);";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setInt(1,this.getID());
    preparedQuery.setBoolean(2,this.getOccupato());
    preparedQuery.setInt(3,this.getIDFila());
    preparedQuery.setString(4,this.getIDProiezione());
    preparedQuery.execute();
  }

  /**Aggiorna un posto in sala
     * context Posto::updatePosto(con) pre:con!=null
     * @throws SQLException - eccezione lanciata in caso di errore in fase di aggiornamento dati nel DB
     * @throws Exception - eccezione lanciata in caso di errore
     * */

  public void updatePosto() throws SQLException,Exception{
    this.setConnection();
    String query="UPDATE Posto SET ID=?, Occupato=?, IDFila=? IDProiezione=? WHERE Posto.ID=? AND Posto.IDFila=? AND Posto.IDProiezione=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setInt(1,this.getID());
    preparedQuery.setBoolean(2,this.getOccupato());
    preparedQuery.setInt(3,this.getIDFila());
    preparedQuery.setString(4,this.getIDProiezione());
    preparedQuery.execute();
  }

  /**Elimina un posto dalla sala
  * context Posto::DeletePosto(con) pre:con!=null
  * @throws Exception - eccezione lanciata in caso di errore
  * @throws SQLException - eccezione lanciata in caso di errore in fase di calcellazione dati nel DB
  * */


  public void DeletePosto() throws SQLException,Exception{
    this.setConnection();
    String query="DELETE FROM Posto WHERE Posto.ID=? AND Posto.IDFila=? AND Posto.IDProiezione=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setInt(1,this.getID());
    preparedQuery.setInt(2,this.getIDFila());
    preparedQuery.setString(3,this.getIDProiezione());
    preparedQuery.execute();
  }

  /**Setta una fila a cui appartiene il posto
   * @param f Fila - fila di appartenenza
   * */


  public void setFila(Fila f) {
    if (fila!=f) {
      Fila old=fila;
      fila=f;
      if (fila!=null)
        fila.addPosto(this);
      if (old!=null)
        old.deletePosto(this);
    }
  }

  /**
   *
   * Setta la connessione al DataBase se non è stata già definita
   */

  private static void setConnection() {
    if (connessione==null)
      try {
      Class.forName("com.mysql.jdbc.Driver");
      String url="jdbc:mysql://localhost/DB_SimplyTicket?user=root";
      connessione=DriverManager.getConnection(url);
    }
    catch (Exception e) {
      System.exit(1);
    }
  }

  /**
   * Serializzazione dell'oggetto
   * @param oos ObjectOutputStream -
   * @throws IOException - viene lanciata se nn è possibile scrivere l'oggetto
   */

  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }

  /**
   * Serializzazione dell'oggetto
   * @param ois ObjectInputStream -
   * @throws IOException - viene lanciata se nn è possibile leggere l'oggetto
   * @throws ClassNotFoundException - viene lanciata se il cast fallisce
   */

  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }

  /**Invarianti:
   * L'ID dell posto deve essere maggiore o uguale a 0
   * context Posto inv:self.ID>=0
   * L'ID della fila deve essere maggiore o uguale a 0
   * context Posto inv:self.IDFila>=0
   * L'Id della proiezione deve essere diverso da nulla e dalla stringa vuota
   * context Posto inv:IDProiezione!=null and ""
   *
   * */

  private int ID;
  private boolean Occupato;
  private int IDFila;
  private String IDProiezione;
  private static Connection connessione;
  private Fila fila;
}
