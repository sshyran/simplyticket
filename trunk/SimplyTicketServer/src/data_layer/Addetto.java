package data_layer;
import java.util.*;
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

public class Addetto extends Persona implements Serializable{

  /**Costruttore pubblico
   * @param nome String - Il nome della persona
   * @param cognome String - Il cognome della persona
   * @param indirizzo String - L'indirizzo della persona
   * @param telefono String - Il numero di telefono della persona
   * @param residenza String - La residenza della persona
   * @param email String - L'email della persona
   * @param idaddetto String - codice identificativo dell'addetto
   * @param login String - login addetto
   * @param password String -password addetto
   * @param bigliettaio boolean - valore booleano per indicare se l'addetto e' o meno un bigliettaio
   * @param idpersona int - identificativo persona
   * @throws Exception - eccezione lanciata se si verifica un errore nel creare un account
   * */

  public Addetto(String nome, String cognome, String indirizzo, String telefono, String residenza, String email,int idaddetto,String login,String password, boolean bigliettaio,int idpersona) throws Exception{
    super(idpersona,nome,cognome,indirizzo,telefono,residenza,email,true);
    if (idaddetto>0 && !login.equalsIgnoreCase("") && !password.equalsIgnoreCase("") && idpersona >0) {
      this.IDAddetto=idaddetto;
      this.Login = login;
      this.Password = password;
      this.Bigliettaio=bigliettaio;
    }
    else
      throw new Exception("Errore nel creare l'account.Controllare che esistano i dati dell'addetto");
  }

  /**Ritorna l'Id dell'addetto
   * @return int - ID addetto
   * */

  public int getIDAddetto() {
    return this.IDAddetto;
  }

  /**Ritorna la login dell'adddetto
   * @return String - login addetto
   * */

  public String getLogin() {
    return this.Login;
  }

  /**Setta la login dell'addetto
   * context Addetto::setLogin(login) pre:login!=""
   * context Addetto::setLogin(login) post:self.Login==login
   * @param login String - login da settare
   * @throws Exception - eccezione lanciata in caso di parametro non valido
   * */

  public void setLogin(String login) throws Exception{
    if (!login.equalsIgnoreCase(""))
      this.Login=login;
    else
      throw new Exception("Campo Login vuota");
  }

  /**Ritorna la password dell'addetto
   * @return String - password addetto
   * */

  public String getPassword() {
    return this.Password;
  }

  /**Setta la password dell'addetto
   * context Addetto::setPassword(password) pre:password!=""
   * context Addetto::setPassword(password) post:self.Password==password
   * @param password String - password da settare
   * @throws Exception - eccezione lanciata in caso di parametro non valido
   * * */

  public void setPassword(String password) throws Exception{
    if (!password.equalsIgnoreCase(""))
      this.Password=password;
    else
      throw new Exception("Campo Password vuoto");
  }

  /**Ritorna il bigliettaio
   * @return boolean - valore booleano ad indicare se l'addetto e' o meno un bigliettaio
   * */

  public boolean getBigliettaio() {
    return this.Bigliettaio;
  }

  /**Setta il bigliettaio
   * @param bigliettaio boolean - valore booleano per settare un addetto come bigliettaio o meno
   * */

  public void setBigliettaio(boolean bigliettaio) {
    this.Bigliettaio=bigliettaio;
  }

  /**Ritorna l'ID della persona
   * @return int - ID persona
   * */

  public int getIDPersona() {
    return this.IDPersona;
  }

  /**Setta l'Id della persona
   * context Addetto::setIDPersona(persona) pre:persona>0
   * context Addetto::setIDPersona(persona) post:self.IDPersona==persona
   * @param persona int - persona di cui cettare l'ID
   * @throws Exception - eccezione lanciata in caso di parametro non valido
   * */

  public void setIDPersona(int persona) throws Exception{
    if (persona>0)
      this.IDPersona=persona;
    else
      throw new Exception("Campo Password vuoto");
  }

  /**Ritorna una collezione di addetti
   * @return Collezione - collezione di addetti
   * @param array String[] - array di addetti
   * @throws SQLException - eccezione lanciata in caso di errore in fase di caricamento dati dal DB
   * @throws Exception - eccezione lanciata in caso di errore
   * */

  public static Collezione LoadAddetti(String[] array) throws SQLException,Exception{
    setConnection();
    int primoParametro=-1;
    int numeroParametri=0;
    int conta=0;
    for (int i=0;i<5;i++) {
      if(i>0 && i<4 && !array[i].equalsIgnoreCase("")) {
        numeroParametri++;
        if (primoParametro<0)
          primoParametro=i;
      }
      else {
        if ((i==0 || i==4)&& Integer.parseInt(array[i])>0) {
          numeroParametri++;
          if (primoParametro<0)
            primoParametro=i;
        }
      }
    }
    String query="SELECT * FROM Persona INNER JOIN Addetto ON Persona.ID=Addetto.IDPersona";
    if (primoParametro==-1)
      query=query+";";
    else {
      query=query+" WHERE "+nomeParametroAddetto(primoParametro)+" = ?";
      conta++;
      for (int i=primoParametro+1;i<5;i++) {
        if ((i>0 && i<4) && !array[i].equalsIgnoreCase("")) {
          query = query + " AND " + nomeParametroAddetto(i) + " =?";
          conta++;
        }
        else {
          if ((i==0 || i==4)&& Integer.parseInt(array[i])>0) {
            query = query + " AND " + nomeParametroAddetto(i) + " =?";
            conta++;
          }
        }
      }
      query=query+";";
    }
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    int confrontaConta=1;
    for (int i=primoParametro;i<5 && confrontaConta<=conta;i++) {
      if (((i>0 && i<4) && !array[i].equalsIgnoreCase("")) || ((i==0 || i==4) && Integer.parseInt(array[i])>0))
        switch(i) {
          case 0:preparedQuery.setInt(confrontaConta,Integer.parseInt(array[i]));
                 confrontaConta++;
                 break;
          case 1:preparedQuery.setString(confrontaConta,array[i]);
                 confrontaConta++;
                 break;
          case 2:preparedQuery.setString(confrontaConta,array[i]);
                 confrontaConta++;
                 break;
          case 3:if (array[i].equalsIgnoreCase("TRUE")) {
                   preparedQuery.setBoolean(confrontaConta, true);
                   confrontaConta++;
                 }
                 else
                   if (array[i].equalsIgnoreCase("FALSE")) {
                     preparedQuery.setBoolean(confrontaConta, false);
                     confrontaConta++;
                   }
                 break;
          case 4:preparedQuery.setInt(confrontaConta,Integer.parseInt(array[i]));
                 confrontaConta++;
                 break;
          default:break;
        }
    }
    ResultSet Risultati=preparedQuery.executeQuery();
    System.out.println(query);
    Collezione collezioneDiAddetti=new CollezioneArrayList();
    Addetto nuovo;
    boolean bol;
    while(Risultati.next()) {
      if (Risultati.getBoolean("Bigliettaio"))
        bol=true;
      else
        bol=false;
      nuovo=new Addetto(Risultati.getString("Nome"),Risultati.getString("Cognome"),Risultati.getString("Indirizzo"),Risultati.getString("Telefono"),Risultati.getString("Residenza"),Risultati.getString("Email"),Risultati.getInt("IDAddetto"),Risultati.getString("Login"),Risultati.getString("Password"),bol,Risultati.getInt("IDPersona"));
      collezioneDiAddetti.add(nuovo);
      }
    Risultati.close();
    return collezioneDiAddetti;
  }

  /**Memorizza un addetto
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di inserimento di dati nel DB
   * @throws Exception - eccezione lanciata in caso di errore
   * */

  public void storageAddetto() throws SQLException,Exception{
    setConnection();
    String array[]=new String[8];
    array[0]=""+super.getID();
    boolean IDEsistente=true;
    if ((super.LoadPersona(array)).isEmpty()) {
      super.storagePersona();
      IDEsistente=false;
    }
    String query="INSERT INTO Addetto(Login,Password,Bigliettaio,IDPersona) values(?,?,?,?);";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getLogin());
    preparedQuery.setString(2,this.getPassword());
    preparedQuery.setBoolean(3,this.getBigliettaio());
    if (IDEsistente)
      preparedQuery.setInt(4,this.getIDPersona());
    else
      preparedQuery.setInt(4,this.lastInserted());
    preparedQuery.execute();
  }

  /**Aggiorna un addetto
   * @return int - L'id dell'ultima persona inserita
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di aggiornamento dati nel DB
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

  /**Elimina un addetto
   * @throws SQLException - eccezione lanciatas se si verifica un errore in fase di eliminazione dati nel DB
   * @throws Exception - eccezione lanciata in caso di errore
   * */

  public void updateAddetto() throws SQLException,Exception{
    setConnection();
    super.updatePersona();
    String query="UPDATE Addetto SET Login= ?, Password=?, Bigliettaio=?, IDPersona=? WHERE Addetto.IDAddetto=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getLogin());
    preparedQuery.setString(2,this.getPassword());
    preparedQuery.setBoolean(3,this.getBigliettaio());
    preparedQuery.setInt(4,super.getID());
    preparedQuery.setInt(5,this.getIDAddetto());
    preparedQuery.execute();
  }

  /**Elimina un addetto
   * @throws SQLException - eccezione lanciatas se si verifica un errore in fase di eliminazione dati nel DB
   * @throws Exception - eccezione lanciata in caso di errore
   * */

  public void DeleteAddetto() throws SQLException,Exception{
    setConnection();
    String query="DELETE FROM Addetto WHERE Addetto.IDAddetto=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setInt(1,this.getIDAddetto());
    preparedQuery.execute();
  }

  /**Ritorna il nome di un campo dato l'indice
   * @return String - valore del campo specificato da posizione
   * @param posizione int - posizione parametro
   * */

  private static String nomeParametroAddetto(int posizione) {
    String valore="";
    switch (posizione) {
              case 0:valore="IDAddetto";
                     break;
              case 1:valore="Login";
                     break;
              case 2:valore="Password";
                     break;
              case 3:valore="Bigliettaio";
                     break;
              case 4:valore="IDPersona";
                     break;
              default: break;
            }
    return valore;
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

  private int IDAddetto;
  private String Login;
  private String Password;
  private boolean Bigliettaio;
  private int IDPersona;
  private static Connection connessione=null;

}
