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

public class Persona implements Serializable{

  /**Costruttore pubblico
   * @param identificatore int - ID della persona
   * @param nome String - nome della persona
   * @param cognome string - cognome della persona
   * @param indirizzo String -indirizzo della persona
   * @param telefono String - telefono della persona
   * @param residenza String - paese di residenza della persona
   * @param email String -email della persona
   * @param dipendente boolean - valore booleano ad indicare se la persona e' un dipendente o meno
   * @throws Exception - viene lanciata se i parametri che definiscono un oggetto di questo tipo non sono validi
   *  * */

  public Persona(int identificatore, String nome, String cognome, String indirizzo, String telefono, String residenza, String email, boolean dipendente) throws Exception{
    if (identificatore>0 && controllaStringa(nome) && controllaStringa(cognome) && controllaStringa(indirizzo) && controllaStringa(telefono)) {
      this.ID=identificatore;
      this.Nome=nome;
      this.Cognome=cognome;
      this.Indirizzo=indirizzo;
      this.Telefono=telefono;
      this.Residenza=residenza;
      this.Email=email;
      this.Dipendente=dipendente;
    }
    else
      throw new Exception("Dati per la persona non validi");
  }

  /**Metodo che verifica la validità del parametro passato
  * @param daControllare String - stringa da controllare
  * @return boolean - valore booleano ad indicare la validita' della stringa
  * */


  private boolean controllaStringa(String daControllare) {
    if (daControllare!=null && !daControllare.equalsIgnoreCase(""))
      return true;
    else
      return false;
  }

  /**Ritorna l'ID della persona
  * @return int - Id persona
  * */


  public int getID() {
    return this.ID;
  }

  /**Ritorna il nome della persona
  * @return String - nome della persona
  * */


  public String getNome() {
    return this.Nome;
  }

  /**Setta il nome della persona
   * context Persona ::setNome(nome) pre:nome!=""
   * context Persona ::setNome(nome) post:self.Nome==nome
   * @param nome String -nome della persona da settare
   * @throws Exception - eccezione lanciata se il parametro non e' valido
   * */


  public void setNome(String nome) throws Exception{
    if (!nome.equalsIgnoreCase(""))
      this.Nome=nome;
    else
      throw new Exception("Nome non valido");
  }

  /**Ritorna il cognome
  * @return String - cognome della persona
  * */


  public String getCognome() {
    return this.Cognome;
  }

  /**Setta il cognome
   * context Persona::setCognome(cognome) pre:cognome!=""
   * context Persona ::setCognome(cognome) post:Cognome==cognome
   * @param cognome String - cofnome della persona da settare
   * @throws Exception - eccezione lanciata se il parametro e' non valido
   * */


  public void setCognome(String cognome) throws Exception{
    if (!cognome.equalsIgnoreCase(""))
      this.Cognome=cognome;
    else
      throw new Exception("Cognome non valido");
  }

  /**Ritorna l'indirizzo della persona
   * @return String - indirizzo della persona
   * */


  public String getIndirizzo() {
    return this.Indirizzo;
  }

  /**Setta l'indirizzo della persona
  * context Persona::setIndirizzo(indirizzo) pre:indirizzo!=""
  * context Persona::setIndirizzo(indirizzo) post:self.Indirizzo==indirizzo
  * @param indirizzo String - indirizzo della persona da settare
  * @throws Exception - eccezione lanciata se il parametro e' non valido
  * */


  public void setIndirizzo(String indirizzo) throws Exception{
    if (!indirizzo.equalsIgnoreCase(""))
      this.Indirizzo=indirizzo;
    else
      throw new Exception("Nome non valido");
  }

  /**Ritorna il telefono della persona
   * @return String - telefono della persona
   * */


  public String getTelefono() {
    return this.Telefono;
  }

  /**Setta il parametro della persona
  * context Persona::setIndirizzo(indirizzo) pre:indirizzo!=""
  * context Persona::setIndirizzo(indirizzo) post:self.Indirizzo==indirizzo
  * @param telefono string - telefono della persona da settare
  * @throws Exception - eccezione lanciata se il parametro e' non valido
  * */


  public void setTelefono(String telefono) throws Exception{
    if (!telefono.equalsIgnoreCase(""))
      this.Telefono=telefono;
    else
      throw new Exception("Numero di telefono non valido");
  }

  /**Ritorna la residenza
  * @return String - residenza della persona
  * */


  public String getResidenza() {
    return this.Residenza;
  }

  /**Setta la residenza
  * context Persona::setResidenza(residenza) pre:residenza!=""
  * context Persona::setResidenza(residenza) post:Residenza==residenza
  * @param residenza String -residenza della persona da settare
  * @throws Exception -eccezione lanciata se il parametro e' non valido
  * */


  public void setResidenza(String residenza) throws Exception{
    if (!residenza.equalsIgnoreCase(""))
      this.Residenza=residenza;
    else
      throw new Exception("Residenza non valido");
  }

  /**Ritorna l'email della persona
 * @return String - email della persona
 * */


  public String getEmail() {
    return this.Email;
  }

  /**Setta l'email della persona
  * context Persona::setEmail(email) pre:email!=""
  * context Persona::setEmail(email) post:Email==email
  * @param email String - email della persona da settare
  * @throws Exception - eccezione lanciata se il parametro e' non valido
  * */


  public void setEmail(String email) throws Exception{
    if (!email.equalsIgnoreCase(""))
      this.Email=email;
    else
      throw new Exception("Email non valido");
  }

  /**Ritorna un valore booleano ad indicare se la persona e' un dipendente o meno
    * @return boolean - valore booleano ad indicare se la persona e' un dipendente o meno
    * */

  public boolean getDipendente() {
    return this.Dipendente;
  }

  /**Setta a true o false un dipendente
  * context Persona::setDipendente(dipendente) post:Dipendente==dipendente
  * @param dipendente boolean - valore booleano per settare un dipendente
  * */


  public void setDipendente(boolean dipendente) {
    this.Dipendente=dipendente;
  }

  /**Ritorna una collezone di persone
   *context Persona::LoadPersona(array,con) pre:con!=null
   *
   * @return Collezione - collezione di persone
   * @param array String[] - array di persone
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di caricamento dati da DB
   * @throws Exception - eccezione lanciata se si verifica un errore
   * */


  public static Collezione LoadPersona(String[] array) throws SQLException,Exception{
    setConnection();
    int primoParametro = -1;
    int numeroParametri = 0;
    for (int i = 0; i < 8; i++)
      if (array[i] != "") {
        numeroParametri++;
        if (primoParametro < 0)
          primoParametro = i;
      }
    int id = -1;
    int conta = 0;
    String[] param = new String[numeroParametri];
    String query = "SELECT * FROM Persona";
    if (primoParametro != -1) {
      query = query + " WHERE " + nomeParametro(primoParametro) + " = ?";
      if (primoParametro == 0)
        id = Integer.parseInt(array[0]);
      else {
        param[conta] = array[primoParametro];
        conta++;
      }
    }
    for (int i = primoParametro + 1; i < 8; i++) {
      if (array[i] != "") {
        String valore = nomeParametro(i);
        query = query + " AND " + valore + " = ?";
        param[conta] = array[i];
        conta++;
      }
    }
    query = query + ";";
    PreparedStatement preparedQuery = connessione.prepareStatement(query);
    if (id != -1) {
      preparedQuery.setInt(1, id);
      for (int i = 1; i <= conta; i++) {
        if (param[i-1]=="TRUE")
          preparedQuery.setBoolean(i + 1,true);
        else
          if (param[i-1]=="FALSE")
            preparedQuery.setBoolean(i + 1,false);
          else
            preparedQuery.setString(i + 1, param[i - 1]);
      }
    }
    else
      for (int i = 0; i < conta; i++) {
        preparedQuery.setString(i + 1, param[i]);
      }
      ResultSet Risultati=preparedQuery.executeQuery();
      Collezione collezioneDiPersone=new CollezioneArrayList();
      Persona nuova;
      boolean bol;
      while(Risultati.next()) {
        if (Risultati.getBoolean("Dipendente"))
          bol=true;
        else
          bol=false;
        nuova=new Persona(Risultati.getInt("ID"),Risultati.getString("Nome"),Risultati.getString("Cognome"),Risultati.getString("Indirizzo"),Risultati.getString("Telefono"),Risultati.getString("Residenza"),Risultati.getString("Email"),bol);
        collezioneDiPersone.add(nuova);
      }
    Risultati.close();
    return collezioneDiPersone;
  }

  /**Ritorna il nome del parametro dato l'indice
  * @param posizione int - è l'indice del campo di cui si vuole conoscere il nome
  * @return String - Il nome del campo indicizzato dal parametro passato
  * */


  private static String nomeParametro(int posizione) {
    String valore="";
    switch (posizione) {
              case 0:valore="ID";
                     break;
              case 1:valore="Nome";
                     break;
              case 2:valore="Cognome";
                     break;
              case 3:valore="Indirizzo";
                     break;
              case 4:valore="Telefono";
                     break;
              case 5:valore="Residenza";
                     break;
              case 6:valore="Email";
                     break;
              case 7:valore="Dipendente";
                     break;
              default: break;
            }
    return valore;
  }

  /**Memorizza una persona
  * context Persona ::storagePersona(con) pre:con!=null
  * @throws Exception - eccezione lanciata se si verifica un errore
  * @throws SQLException - eccezione lanciata se si verifica un errore in fase di memorizzazione dati nel DB
  * */


  public void storagePersona() throws SQLException,Exception{
    this.setConnection();
    String query="INSERT INTO Persona(Nome,Cognome,Indirizzo,Telefono,Residenza,Email,Dipendente) values(?,?,?,?,?,?,?);";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getNome());
    preparedQuery.setString(2,this.getCognome());
    preparedQuery.setString(3,this.getIndirizzo());
    preparedQuery.setString(4,this.getTelefono());
    preparedQuery.setString(5,this.getResidenza());
    preparedQuery.setString(6,this.getEmail());
    preparedQuery.setBoolean(7,this.getDipendente());
    preparedQuery.execute();
  }

  /**Aggiorna una persona
   * context Persona ::updatePersona(con) pre:con!=null
   * @throws Exception - eccezione lanciata se si verifica un errore
   * @throws  SQLException - eccezione lanciata se si verifica un errore in fase di aggiornamento dati nel DB
   * */


  public void updatePersona() throws SQLException,Exception{
    this.setConnection();
    String query="UPDATE Persona SET Nome= ?, Cognome=?, Indirizzo=?, Telefono=?, Residenza=?,Email=?, Dipendente=? WHERE Persona.ID=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getNome());
    preparedQuery.setString(2,this.getCognome());
    preparedQuery.setString(3,this.getIndirizzo());
    preparedQuery.setString(4,this.getTelefono());
    preparedQuery.setString(5,this.getResidenza());
    preparedQuery.setString(6,this.getEmail());
    preparedQuery.setBoolean(7,this.getDipendente());
    preparedQuery.setInt(8,this.getID());
    preparedQuery.execute();
  }

  /**Elimina una persona
   * context Persona::DeletePersona(con) pre:con!=null
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di eliminazione dati dal DB
   * @throws Exception - eccezione lanciata se si verifica un errore
   *
   * */

  public void DeletePersona() throws SQLException,Exception{
    setConnection();
    String query="DELETE FROM Persona WHERE Persona.ID=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setInt(1,this.getID());
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

  /**Invarianti:
   * L'Id della persona deve essere maggiore o uguale a 0
   * context Persona inv:self.ID>=0
   * Il nome della persona deve essere diverso dalla stringa vuota
   * context Persona inv:self.Nome!=""
   * Il cognome della persona deve essere diverso dalla stringa vuota
   * context Persona inv:self.Cognome!=""
   * Il telefono  della persona deve essere diverso dalla stringa vuota
   * context Persona inv:self.Telefono!=""
   * L'indirizzo della persona deve essere diverso dalla stringa vuota
   * context Persona inv:self.Indirizzo!=""
   * LA residenza della persona deve essere diversa dalla stringa vuota
   * context Persona inv:self.Residenza!=""
   * L'email della persona deve esserd iversa dalla stringa vuota
   * context Persona inv:self.Email!=""
   *
   *
   * */

  private int ID;
  private String Nome;
  private String Cognome;
  private String Indirizzo;
  private String Telefono;
  private String Residenza;
  private String Email;
  private boolean Dipendente;
  private static Connection connessione=null;
}
