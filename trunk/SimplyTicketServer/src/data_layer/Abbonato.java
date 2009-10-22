package data_layer;
import java.sql.*;
import java.util.Set;
import java.util.HashSet;
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

public class Abbonato extends Persona implements Serializable{

  /**Costruttore pubblico
   * @param idabbonato int - codice identificativo dell'abbonato
   * @param idpersona int - codice identificativo della persona
   * @param nome String - Il nome della persona
   * @param cognome String - Il cognome della persona
   * @param indirizzo String - L'indirizzo della persona
   * @param residenza String - La residenza della persona
   * @param telefono String - Il numero di telefono della persona
   * @param email String - L'email della persona
   * @throws Exception - eccezione lanciata in caso di parametri non validi
   * */

  public Abbonato(String nome, String cognome, String indirizzo, String telefono, String residenza, String email,int idabbonato,int idpersona) throws Exception{
    super(idpersona,nome,cognome,indirizzo,telefono,residenza,email,false);
    if (idabbonato>0 && idpersona>0) {
      this.IDAbbonato = idabbonato;
      this.IDPersona = idpersona;
    }
    else
      throw new Exception("ID non validi");
    this.Account = new HashSet();
  }

  /**Aggunge un nuovo abbonamento
   * @param a Abbonamento - abbonamento da aggiungere
   * */

  public void addAbbonamento(Abbonamento a) {
    this.Account.add(a);
    a.setOwner(this);
  }

  /**Elimina un abbonamento
   * @param a Abbonamento - abbonamento da eliminare
   * */

  public void deleteAbbonamento(Abbonamento a) {
    this.Account.remove(a);
    a.setOwner(null);
  }

  /**Ritorna l'Id dell'abbonato
   * @return int - ID abbonamento
   * */

  public int getIDAbbonato() {
    return this.IDAbbonato;
  }

  /**Ritorna l'Id della persona
   * @return int - ID persona
   * */

  public int getIDPersona() {
    return this.IDPersona;
  }

  /**setta l'Id della persona
   * context Abbonato::setIDPersona(persona) pre:persona>0
   * context Abbonato::setIDPersona(persona) post:self.IDPersona==persona
   * @param persona int - persona di cuoi settare l'ID
   * @throws Exception - eccezione lanciata in caso di parametro non valido
   * */

  public void setIDPersona(int persona) throws Exception{
    if (persona>0)
      this.IDPersona=persona;
    else
      throw new Exception("Campo Password vuoto");
  }

  /**Ritorna una collezione di abbonati
   * context Abbonato::loadAbbonato(array,con) pre:con!=null
   * @return Collezione - collezione di abbonati
   * @param array int[] - array di abbonati
   * @throws SQLException - eccezioine lanciata se si verifica un errore in fase di caricamento dati nel DB
   * @throws Exception - eccezione lanciata in caso di errore
   * */

  public static Collezione loadAbbonato(int[] array) throws SQLException,Exception{
    setConnection();
    String query="SELECT * FROM Persona INNER JOIN Abbonato ON Persona.ID=Abbonato.IDPersona";
    if (array[0]>0 || array[1]>0) {
      query = query + " WHERE";
      if (array[0]>0) {
        query = query + " IDAbbonato= ?";
        if (array[1]>0)
          query = query + " AND Abbonato.IDPersona= ?";
      }
      else
        query = query + " Abbonato.IDPersona= ?";
    }
    query = query+";";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    if (array[0]>0) {
      preparedQuery.setInt(1, array[0]);
      if (array[1]>0)
        preparedQuery.setInt(2, array[1]);
    }
    else {
      if (array[1] > 0)
        preparedQuery.setInt(1, array[1]);
    }
    ResultSet Risultati=preparedQuery.executeQuery();
    Collezione collezioneDiAbbonati=new CollezioneArrayList();
    Abbonato nuovo;
    while(Risultati.next()) {
      nuovo=new Abbonato(Risultati.getString("Nome"),Risultati.getString("Cognome"),Risultati.getString("Indirizzo"),Risultati.getString("Telefono"),Risultati.getString("Residenza"),Risultati.getString("Email"),Risultati.getInt("IDAbbonato"),Risultati.getInt("IDPersona"));
      collezioneDiAbbonati.add(nuovo);
      }
    Risultati.close();
    return collezioneDiAbbonati;
  }

  /**Memorizza un addetto
   * contezt Abbonato::storageAddetto(con) pre:con!=null
   * @throws SQLException -eccezione lanciata se si verifica un errore in fase di memorizzazione dati nel DB
   * @throws Exception - eccezione lanciata in caso di errore
   * */

  public void storageAbbonato() throws SQLException,Exception{
    setConnection();
    String array[]=new String[8];
    array[0]=""+super.getID();
    if ((super.LoadPersona(array)).isEmpty())
      super.storagePersona();
    String query="INSERT INTO Abbonato(IDPersona) values(?);";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setInt(1,this.lastInserted());
    preparedQuery.execute();
  }

  /**Aggiorna un addetto
   * context Abbonato::updateAddetto(con) pre:con!=null
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di aggiornamento dati nel DB
   * @throws Exception - eccezione lanciata se si verifica un errore
   * @return int - L'id dell'ultima persona inserita nel DB
   * */

  private int lastInserted() throws SQLException{
    String query = "SELECT Max(ID) FROM Persona;";
    Statement statementQuery = connessione.createStatement();
    ResultSet risultati = statementQuery.executeQuery(query);
    int ultimo = 0;
    while (risultati.next()) {
      ultimo = risultati.getInt(1);
    }
    return ultimo;
  }

  /**Aggiorna un addetto
   * context Abbonato::updateAddetto(con) pre:con!=null
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di aggiornamento dati nel DB
   * @throws Exception - eccezione lanciata se si verifica un errore
   * */

  public void updateAbbonato() throws SQLException,Exception{
    setConnection();
    super.updatePersona();
    String query="UPDATE Abbonato SET IDPersona=? WHERE Abbonato.IDAbbonato=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setInt(1,super.getID());
    preparedQuery.setInt(2,this.getIDAbbonato());
    preparedQuery.execute();
  }

  /**Elimina un addetto
   * @throws Exception - eccezione lanciata se si verifica un errore
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di eliminazione dati nel DB
   * */

  public void DeleteAbbonato() throws SQLException,Exception{
    setConnection();
    String query="DELETE FROM Abbonato WHERE Abbonato.IDAbbonato=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setInt(1,this.getIDAbbonato());
    preparedQuery.execute();
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

  private int IDAbbonato;
  private int IDPersona;
  private static Connection connessione=null;
  private Set Account;
}
