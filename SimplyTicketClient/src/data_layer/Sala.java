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

public class Sala implements Serializable{

  /**
   * Costruttore: crea un oggetto Sala se i parametri per la generazione sono validi
   * @param nome String - nome della sala
   * @param capacita int - capienza della sala
   * @param struttura String - struttura della sala
   * @throws Exception - eccezione lanciata se un parametro e' non valido
   * */

  public Sala(String nome,int capacita,String struttura) throws Exception{
    if (controllaStringa(nome) && capacita>0 && controllaStringa(struttura)) {
      this.Nome=nome;
      this.capacita=capacita;
      this.IDStruttura=struttura;
    }
    else
      throw new Exception("Dati per la sala non validi");
  }

  /**
   * Metodo che verifica la validit� di un parametro di tipo String
   * @return boolean - valore booleano ad indicare se la stringa e' valida o meno
   * @param daControllare String -stringa da controllare
   * */

  private static boolean controllaStringa(String daControllare) {
    if ((daControllare!=null && !daControllare.equalsIgnoreCase("")))
      return true;
    else
      return false;
  }

  /**
     * Restituisce il nome della sala
     * @return String - nome della sala
     * */

  public String getNome() {
      return this.Nome;
    }

    /**
     * Cambia il nome della sala, impostandolo al valore del parametro nome
     * context Sala::setNome(nome) pre:nome!=null and ""
     * context Sala::setNome(nome) post:self.Nome==nome
     * @param nome String - nome della sala da settare
     * @throws Exception - eccezione lanciata se il parametro e' non valido
     * */


  public void setNome(String nome) throws Exception{
    if (controllaStringa(nome))
      this.Nome=nome;
    else
      throw new Exception("Nome Sala non definito");
  }

  /**
   * Ritorna la capacita della sala
   * @return int - capacita' della sala
   * */


  public int getcapacita() {
      return this.capacita;
    }

    /**
     * Cambia la capacita della sala, impostandola al valore del parametro capacita
     * context Sala::setcapacita(capacita) pre:capacita>0
     * context Sala::setcapacita(capacita) post:self.capacita==capacita
     * @param capacita int - capacita' della sala da settare
     * @throws Exception - eccezione lanciata se il parametro e' non valido
     *
     * * */


  public void setcapacita(int capacita) throws Exception{
    if (capacita>0)
      this.capacita=capacita;
    else
      throw new Exception("capacita non definita");
  }

  /**
   * Ritorna l'ID della struttura
   * @return String - Id della struttura
   * */


  public String getIDStruttura() {
      return this.IDStruttura;
    }

    /**
         * Setta l'ID della struttura
         * context Sala::setIDStruttura(idstruttura) pre:idstruttura!=null and ""
         * context Sala::setIDStruttura(idstruttura) post:self.IDStruttura==idstruttura
         * @param idstruttura - Id della struttura da settare
         * @throws Exception - eccezione lanciata se il parametro e' non valido
         * */

  public void setIDStruttura(String idstruttura) throws Exception{
    if (controllaStringa(idstruttura))
      this.IDStruttura=idstruttura;
    else
      throw new Exception("Struttura inesistente");
  }

  /**
   * Ritorna una collezione di info sulla sala
   * context Sala::loadSala(nome,capacita,struttura,con) pre:con!=null
   * @return Collezione - collezione di sale
   * @param nome String -nome della sala
   * @param capacita int -capacita' della sala
   * @param struttura String - struttura della sala
   * @throws Exception - eccezione lanciata se un parametro e' non valido
   * @throws SQLException - eccezione lanciata se la query non viene eseguita correttamente
   * */


  public static Collezione loadSala(String nome,int capacita,String struttura) throws SQLException,Exception{
    setConnection();
    String query="SELECT * FROM Sala";
    if (controllaStringa(nome)) {
      query=query+" WHERE Nome=?";
      if (capacita>0) {
        query=query+" AND capacita= ?";
        if (controllaStringa(struttura)) {
          query=query+" AND IDStruttura= ?";
        }
      }
      else {
        if (controllaStringa(struttura)) {
          query = query + " AND IDStruttura= ?";
        }
      }
    }
    else {
      if (capacita > 0) {
        query = query + " WHERE capacita= ?";
        if (controllaStringa(struttura)) {
          query = query + " AND IDStruttura= ?";
        }
      }
      else {
        if (controllaStringa(struttura)) {
          query = query + " WHERE IDStruttura= ?";
        }
      }
    }
    query=query+";";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    if (controllaStringa(nome)) {
      preparedQuery.setString(1,nome);
      if (capacita>0) {
        preparedQuery.setInt(2,capacita);
        if (controllaStringa(struttura)) {
          preparedQuery.setString(3,struttura);
        }
      }
      else {
        if (controllaStringa(struttura)) {
          preparedQuery.setString(2,struttura);
        }
      }
    }
    else {
      if (capacita > 0) {
        preparedQuery.setInt(1,capacita);
        if (controllaStringa(struttura)) {
          preparedQuery.setString(2,struttura);
        }
      }
      else {
        if (controllaStringa(struttura)) {
          preparedQuery.setString(1,struttura);
        }
      }
    }
    ResultSet Risultati=preparedQuery.executeQuery();
    Collezione collezioneDiSala=new CollezioneArrayList();
    Sala nuova;
    while(Risultati.next()) {
      nuova=new Sala(Risultati.getString("Nome"),Risultati.getInt("capacita"),Risultati.getString("IDStruttura"));
      collezioneDiSala.add((Object)nuova);
    }
    return collezioneDiSala;
  }

  /**
  * Memorizza un sala
  * context Sala::storageSala(con) pre:con!=null
  * @throws SQLException - eccezione lanciata se si verifica un errore in fase  di memorizzazione  dati nel DB
  * @throws Exception - eccezione lanciata se si verifica un errore
  * */


  public void storageSala() throws SQLException,Exception{
    setConnection();
    String query="INSERT INTO Sala(Nome,capacita,IDStruttura) values(?,?,?);";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,getNome());
    preparedQuery.setInt(2,getcapacita());
    preparedQuery.setString(3,getIDStruttura());
    preparedQuery.execute();
  }

  /**
   * Aggiorna una sala
   * context Sala::updateSala(con) pre:con!=null
   * @throws Exception - eccezione lanciata se si verifica un errore
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase  di aggiornamento dati nel DB
   * */


  public void updateSala() throws SQLException,Exception{
    setConnection();
    String query="UPDATE Sala SET Nome= ?, capacita=?, IDStruttura=? WHERE Sala.Nome=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getNome());
    preparedQuery.setInt(2,this.getcapacita());
    preparedQuery.setString(3,this.getIDStruttura());
    preparedQuery.setString(4,this.getNome());
    preparedQuery.execute();
  }

  /**
     * Elimina una sala
     * context Sala::DeleteSala(sala) pre:con!=null
     * @throws Exception - eccezione lanciata se si verifica un errore
     * @throws SQLException - eccezione lanciata se si verifica un errore in fase di eliminazione dati dal DB
     * */

  public void DeleteSala() throws SQLException,Exception{
    setConnection();
    String query="DELETE FROM Sala WHERE Sala.Nome=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getNome());
    preparedQuery.execute();
  }

  /**
  * Aggiunge una proiezione che si svolger� nella sala istanziata
  * @param p Proiezione - proiezione da aggiungere
  * */


  public void addProiezione(Proiezione p) {
    Proiezioni.add(p);
    p.setSala(this);
  }

  /**
   * Elimina una proiezione dall'insieme associato alla sala istanziata
   * @param p Proiezione - proiezione da eliminare
   * */


  public void deleteProiezione(Proiezione p) {
    Proiezioni.remove(p);
    p.setSala(null);
  }

  /**
  * Aggiunge una fila che appartiene alla sala istanziata
  * @param f Fila - fila da aggiungere
  * */


  public void addFila(Fila f) {
    File.add(f);
    f.setSala(this);
  }

  /**Elimina una fila che appartiene alla sala istanziata
   * @param f Fila - fila da eliminare
   * */


  public void deleteFila(Fila f) {
    File.remove(f);
    f.setSala(null);
  }

  /**
   *
   * Setta la connessione al DataBase se non � stata gi� definita
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
   * @throws IOException - viene lanciata se nn � possibile scrivere l'oggetto
   */

  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }

  /**
   * Serializzazione dell'oggetto
   * @param ois ObjectInputStream -
   * @throws IOException - viene lanciata se nn � possibile leggere l'oggetto
   * @throws ClassNotFoundException - viene lanciata se il cast fallisce
   */

  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }

  /**
   * Invarianti:
   * Il nome della sala deve essere diverso da nulla dalla stringa vuota
   * context Sala inv:self.Nome!=null and ""
   * La capacita' della sala deve essere maggiore o uguale a 0
   * context Sala inv:self.capacita>=0
   * L'ID struttura deve essere diverso da null e dalla stringa vuota
   * context Sala inv:self.IDStruttura!=null and ""
   *
   * */

  private String Nome;
  private int capacita;
  private String IDStruttura;
  private static Connection connessione=null;
  private CollezioneArrayList Proiezioni;
  private CollezioneArrayList File;
}
