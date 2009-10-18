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

public class Biglietto implements Serializable{

  /**Costruttore pubblico
   * @param id String - identifcativo biglietto
   * @param costo double - costo del biglietto
   * @param costorid double - costo ridotto del biglietto
   * @param proiezione String -  proiezione relativa al biglietto
   * @param abbonamento String - abbonamento a cui si riferisce il biglietto
   * @param fila int - fila del posto a cui il bilietto fa riferimento
   * @param posto int - posto del biglietto
   * @param addetto int - addetto
   * @throws Exception - eccezione lanciata in caso di parametri non validi
   * */

  public Biglietto(String id,double costo,double costorid,String proiezione,String abbonamento,int fila,int posto,int addetto) throws Exception{
    if (controllaStringa(id) && controllaStringa(proiezione) && costo>=0.0 && costorid>=0.0 && fila>0 && posto>0 && addetto>0) {
      IDBiglietto=id;
      CostoIntero=costo;
      CostoRidotto=costorid;
      IDProiezione=proiezione;
      IDAbbonamento=abbonamento;
      IDFila=fila;
      IDPosto=posto;
      IDAddetto=addetto;
    }
    else
      throw new Exception("Valori non validi");
  }

  /**Metodo che verifica la validità del parametro passato
   * @param daControllare String - stringa da controllare
   * @return boolean - valore booleano ad indicare se il parametro e' valido o meno
   * */

  private static boolean controllaStringa(String daControllare) {
    if ((daControllare!=null && daControllare!=""))
      return true;
    else
      return false;
  }

  /**Setta il proprietario dell'abbonamento
   * @param a Abbonamento - abbonamento di cuoi settare il proprietario
   * */

  public void setOwner(Abbonamento a) {
    if (abbonamento!=a) {
      Abbonamento old=abbonamento;
      abbonamento=a;
      if (a!=null)
        a.addBiglietto(this);
      if (old!=null)
        old.deleteBiglietto(this);
    }
  }

  /**Ritorna l'Id del biglietto
   * @return String - identificativo del biglietto
   * */

  public String getIDBiglietto() {
      return this.IDBiglietto;
    }

  /**Ritorna il costo di un biglietto intero
   * @return double - costo del biglietto intero
   * */

  public double getCostoIntero() {
      return this.CostoIntero;
    }

  /**Setta il costo di un biglietto
   * context Biglietto::setCosto(costo) pre:costo>0
   * context Biglietto::setCosto(costo) post:self.Costo==costo
   * @param costo double - costo del biglietto da settare
   * @throws Exception - eccezione lanciata in caso di parametro non valido
   * */

  public void setCosto(double costo) throws Exception{
    if (costo>0)
      CostoIntero=costo;
    else
      throw new Exception("Costo del biglietto non valido");
  }

  /**Ritorna il costo di un biglietto ridotto
   * @return double - costo del biglietto ridotto
   * */

  public double getCostoRidotto() {
    return this.CostoRidotto;
  }

  /**Setta il costo di un biglietto ridotto
   * context Biglietto::setCostoRidotto(costo) pre:costo>0
   * context Biglietto::setCostoRidotto(costo) post:self.Costo==costo
   * @param costo double - costo da settare del biglietto ridotto
   * @throws Exception - eccezione lanciata se il parametro non e' valido
   * */

  public void setCostoRidotto(double costo) throws Exception{
    if (costo>0)
      CostoRidotto=costo;
    else
      throw new Exception("Costo del biglietto non valido");
  }

  /**Ritorna l'Id della proiezione
   * @return String - identificativo della proiezione
   * */

  public String getIDProiezione() {
    return this.IDProiezione;
  }

  /**Setta l'Id della proiezione
   * context Biglietto::setIDProiezione(proiezione) pre:proiezione!=null and ""
   * context Biglietto::setIDProiezione(proiezione) post:self.IDProiezione==proiezione
   * @param proiezione String- proiezione di cui settare l'ID
   * @throws Exception - eccezione lanciata se il parametro non e' valido
   * */

  public void setIDProiezione(String proiezione) throws Exception{
    if (controllaStringa(proiezione))
      IDProiezione=proiezione;
    else
      throw new Exception("Proiezione non valida");
  }

  /**Ritorna l'ID dell'abbonamento
   * @return String - ID abbonamento
   * */

  public String getIDAbbonamento() {
    return this.IDAbbonamento;
  }

  /**Setta l'ID dell'abbonamento
   * context Biglietto::setIDAbbonamento(abbonamento) pre:abbonamento!=null and ""
   * context Biglietto::setIDAbbonamento(abbonamento) post:self.IDAbbonamento==abbonamento
   * @param abbonamento string - abbonamento di cui settare l'ID
   * @throws Exception - eccezione lanciata se il parametro non e' valido
   * */

  public void setIDAbbonamento(String abbonamento) throws Exception{
    if (abbonamento!=null)
      IDAbbonamento=abbonamento;
    else
      throw new Exception("Abbonametno non valido");
  }

  /**Ritorna l'ID della fila
   * @return int - ID fila
   * */

  public int getIDFila() {
    return this.IDFila;
  }

  /**Setta l'Id della fila
   * context Biglietto::setIDFila(fila) pre:fila>0
   * context Biglietto::setIDFila(fila) post:self.IDFila==fila
   * @param fila int - fila di cui settare l'ID
   * @throws Exception - eccezione lanciata se il parametro non e' valido
   * */

  public void setIDFila(int fila) throws Exception{
    if (fila>0)
      IDFila=fila;
    else
      throw new Exception("Fila non esistente");
  }

  /**Ritona l'ID del posto
   * @return int - Id posto
   * */

  public int getIDPosto() {
    return this.IDPosto;
  }

  /**Setta l'ID del posto
   * context Biglietto::setIDPosto(posto) pre:posto>0
   * context Biglietto::setIDPosto(posto) post:self.IDPosto==posto
   * @param posto int - posto di cui settare l'Id
   * @throws Exception - eccezione lanciata se il parametro non 'e' valido
   * * */

  public void setIDPosto(int posto) throws Exception{
    if (posto>0)
      IDPosto=posto;
    else
      throw new Exception("Posto inesistente");
  }

  /**Ritorna l'ID dell'addetto
   * @return int - ID addetto
   * */

  public int getIDAddetto() {
    return this.IDAddetto;
  }

  /**Setta l'ID dell'addetto
   * context Biglietto::setIDAddetto(addetto) pre:addetto>0
   * context Biglietto::setIDAddetto(addetto) post:self.IDAddetto==addetto
   * @param addetto int - addetto di cui settare l'ID
   * @throws Exception - eccezione lanciata se il parametro non e' valido
   * */

  public void setIDAddetto(int addetto) throws Exception{
    if (addetto>0)
      IDAddetto=addetto;
    else
      throw new Exception("Addetto inesistente");
  }

  /**Riorna una collezione di biglietti
   *
   * @return Collezione - collezione di biglietti
   * @param array String[] - array cpntenente i parametri con i quali selezionare
   * @throws Exception - eccezione lanciata in caso di errore
   * @throws SQLException - eccezione lanciata in caso di errore in fase di caricamento dati dal DB
   * */


  public static Collezione LoadBiglietto(String[] array) throws SQLException,Exception{
    setConnection();
    boolean bol;
    String query="SELECT * FROM Biglietto";
    int primoParametro=-1;
    int numeroParametri=0;
    for (int i=0;i<8;i++) {
      if (i==0 || i==3 || i==4) {
        bol=controllaStringa(array[i]);
      }
      else
        if(i==1 || i==2)
          bol=Double.parseDouble(array[i])>0;
        else
          bol=Integer.parseInt(array[i])>0;
      if (bol) {
        bol=false;
        numeroParametri++;
        if (primoParametro<0)
          primoParametro=i;
      }
    }
    int confronto=0;
    if (primoParametro>-1) {
      query=query+" WHERE "+nomeDaNumero(primoParametro)+"=?";
      for (int i=primoParametro+1;i<8;i++) {
        if (i==0 || i==3 || i==4) {
          if (controllaStringa(array[i]))
            query=query+" AND "+nomeDaNumero(i)+"=?";
        }
        else
          if((i==1 || i==2) && Double.parseDouble(array[i])>0)
            query=query+" AND "+nomeDaNumero(i)+"=?";
          else
            if (i>4 && Integer.parseInt(array[i])>0)
              query=query+" AND "+nomeDaNumero(i)+"=?";
      }
    }
    query=query+";";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    if (primoParametro>-1) {
      if (primoParametro == 0 || primoParametro == 3 || primoParametro == 4) {
        preparedQuery.setString(confronto+1,array[primoParametro]);
        confronto++;
      }
      else {
        if (primoParametro == 1 || primoParametro == 2) {
          preparedQuery.setDouble(confronto + 1,Double.parseDouble(array[primoParametro]));
          confronto++;
        }
        else
        if (Integer.parseInt(array[primoParametro]) > 0) {
          preparedQuery.setInt(confronto + 1,Integer.parseInt(array[primoParametro]));
          confronto++;
        }
      }
      for (int i=primoParametro+1;i<8;i++) {
        if (i==3 || i==4) {
          if (controllaStringa(array[i])) {
            preparedQuery.setString(confronto + 1, array[i]);
            confronto++;
          }
        }
        else
          if((i==1 || i==2) && Double.parseDouble(array[i])>0) {
            preparedQuery.setDouble(confronto + 1, Double.parseDouble(array[i]));
            confronto++;
          }
          else
            if (i>4 && Integer.parseInt(array[i])>0) {
              preparedQuery.setInt(confronto + 1, Integer.parseInt(array[i]));
              confronto++;
            }
      }
    }
    ResultSet Risultati=preparedQuery.executeQuery();
    Collezione collezioneDiBiglietti=new CollezioneArrayList();
    Biglietto nuovo;
    while(Risultati.next()) {
      nuovo=new Biglietto(Risultati.getString(1),Risultati.getDouble(2),Risultati.getDouble(3),Risultati.getString(4),Risultati.getString(5),Risultati.getInt(6),Risultati.getInt(7),Risultati.getInt(8));
      collezioneDiBiglietti.add(nuovo);
    }
    return collezioneDiBiglietti;
  }

  /**Memorizza un biglietto
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di memorizzazione dati nel DB
   * @throws Exception - eccezione lanciata s si verifica un errore
   * */

  public void storageBiglietto() throws SQLException,Exception{
    this.setConnection();
    String query="INSERT INTO Biglietto(IDBiglietto,CostoIntero,CostoRidotto,IDProiezione,IDAbbonamento,IDFila,IDPosto,IDAddetto) values(?,?,?,?,?,?,?,?);";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getIDBiglietto());
    preparedQuery.setDouble(2,this.getCostoIntero());
    preparedQuery.setDouble(3,this.getCostoRidotto());
    preparedQuery.setString(4,this.getIDProiezione());
    preparedQuery.setString(5,this.getIDAbbonamento());
    preparedQuery.setInt(6,this.getIDFila());
    preparedQuery.setInt(7,this.getIDPosto());
    preparedQuery.setInt(8,this.getIDAddetto());
    preparedQuery.execute();
  }

  /**Aggiorna un biglietto
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di aggiornamento dati nel DB
   * @throws Exception -eccezione lanciata se si verifica un errore
   * */

  public void updateBiglietto() throws SQLException,Exception{
    this.setConnection();
    String query;
    if (controllaStringa(this.getIDAbbonamento())) {
      query="UPDATE Biglietto SET CostoIntero=?, CostoRidotto=?, IDProiezione=?, IDAbbonamento=?, IDFila=?, IDPosto=?, IDAddetto=? WHERE Biglietto.IDBiglietto=?;";
    }
    else
      query="UPDATE Biglietto SET CostoIntero=?, CostoRidotto=?, IDProiezione=?, IDFila=?, IDPosto=?, IDAddetto=? WHERE Biglietto.IDBiglietto=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setDouble(1,this.getCostoIntero());
    preparedQuery.setDouble(2,this.getCostoRidotto());
    preparedQuery.setString(3,this.getIDProiezione());
    if (controllaStringa(this.getIDAbbonamento())) {
      preparedQuery.setString(4, this.getIDAbbonamento());
      preparedQuery.setInt(5, this.getIDFila());
      preparedQuery.setInt(6, this.getIDPosto());
      preparedQuery.setInt(7, this.getIDAddetto());
      preparedQuery.setString(8, this.getIDBiglietto());
    }
    else {
      preparedQuery.setInt(4, this.getIDFila());
      preparedQuery.setInt(5, this.getIDPosto());
      preparedQuery.setInt(6, this.getIDAddetto());
      preparedQuery.setString(7, this.getIDBiglietto());

    }
    preparedQuery.execute();
  }

  /**Elimina un biglietto
   * @throws Exception - eccezione lanciata se si verifica un errore
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di eliminazione dati dal DB
   * */

  public void DeleteBiglietto() throws SQLException,Exception{
    this.setConnection();
    String query="DELETE FROM Biglietto WHERE Biglietto.IDBiglietto=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getIDBiglietto());
    preparedQuery.execute();
  }

  /**Setta un proiezione al quale il biglietto è associato
   * @param p Proiezione - proiezione da settare
   * */

  public void setProiezione(Proiezione p) {
    if (p!=proiezione) {
      Proiezione old=proiezione;
      proiezione=p;
      if (p!=null)
        p.addBiglietto(this);
      if (proiezione!=null)
        proiezione.deleteBiglietto(this);
    }
  }

  /**Riporta il nome di un campo dato l'indice dello stesso
   * @param i int - i è l'indice del campo di cui si vuole conoscere il nome
   * @return String - Il nome del campo corrispondente al valori di i
   * */

  private static String nomeDaNumero(int i) {
    String ritorno="";
    switch(i) {
      case 0:ritorno="IDBiglietto";
             break;
      case 1:ritorno="CostoIntero";
             break;
      case 2:ritorno="CostoRidotto";
             break;
      case 3:ritorno="IDProiezione";
             break;
      case 4:ritorno="IDAbbonamento";
             break;
      case 5:ritorno="IDFila";
             break;
      case 6:ritorno="IDPosto";
             break;
      case 7:ritorno="IDAddetto";
             break;
    }
    return ritorno;
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

  private String IDBiglietto;
  private double CostoIntero;
  private double CostoRidotto;
  private String IDProiezione;
  private String IDAbbonamento;
  private int IDFila;
  private int IDPosto;
  private int IDAddetto;
  private static Connection connessione=null;
  private Abbonamento abbonamento;
  private Proiezione proiezione;
}
