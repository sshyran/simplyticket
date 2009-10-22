package data_layer;
import java.util.GregorianCalendar;
import java.util.Calendar;
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

public class Proiezione implements Serializable{

 /**
  * Costruttore: crea un oggetto proiezione se i dati per generarlo sono validi
  * @param id String -ID della proiezione
  * @param data GregorianCalendar - data della proiezione
  * @param bigliettiVenduti int - numero di biglietti venduti
  * @param sala String -sala della priezione
  * @param film int - numero del film
  * @throws Exception - eccezione lanciata se un parametro e' non valido
  * */

  public Proiezione(String id,GregorianCalendar data,int bigliettiVenduti,String sala,int film) throws Exception{
    if (controllaStringa(id) && data!=null && bigliettiVenduti>=0 && controllaStringa(sala) && film>0) {
      this.ID=id;
      this.Data_Ora_Inizio=data;
      this.BigliettiVenduti=bigliettiVenduti;
      this.IDSala=sala;
      this.IDFilm=film;
    }
    else
      throw new Exception("Valori non validi per la proiezione");
  }

  /**Metodo che verifica la validità di un parametro di tipo String
   * @param daControllare - stringa da controllare
   * @return boolean - valore booleano d indicare se la stringa e' valida o meno
   * */

  private static boolean controllaStringa(String daControllare) {
     if ((daControllare!=null && !daControllare.equalsIgnoreCase("")) || daControllare.endsWith("\n"))
       return true;
     else
       return false;
   }

   /**Ritorna l'ID della proiezione
    * @return String - ID della proiezione
    * */


  public String getID() {
    return this.ID;
  }

  /**Ritorna la data di inizio della proiezione
     * @return GregorianCalendar - data di inizo della proiezione
     * */

  public Calendar getDataOraInizio() {
    return this.Data_Ora_Inizio;
  }

  /**Setta la data di inizio della proiezione
   * context Proiezione::setDataOraInizio(data) pre:data!=null
   * context Proiezione::setDataOraInizio(data) post:self.Data_Ora_Inizio==data
   * @param data GregorianCalendar - data e ora di inizo della proiezione
   * @throws Exception - eccezione lanciata se il parametro e' non valido
   * */


  public void setDatOraInizio(GregorianCalendar data) throws Exception{
    if (data!=null)
      this.Data_Ora_Inizio=data;
    else
      throw new Exception("Data non valida");
  }

  /**Ritorna il numero di biglietti venduti
   * @return int - numero biglietti venduti
   * */

  public int getBigliettiVenduti() {
    return this.BigliettiVenduti;
  }

  /**Setta il numero di biglietti venduti
   * context Proiezione::setBigliettiVenduti(biglietti) pre:biglietti>=0
   * context Proiezione::setBigliettiVenduti(biglietti) post:self.BigliettiVenduti==biglietti
   * @param biglietti int - numero di biglietti vendti da settare
   * @throws Exception - eccezione lanciata se il parametro e' non valido
   * */


  public void setBigliettiVenduti(int biglietti) throws Exception{
    if (biglietti>0)
      this.BigliettiVenduti=biglietti;
    else
      throw new Exception("Nuemro di biglietti venduti non valido");
  }

  /**Ritorna l'ID della sala
   * @return String - ID della sala
   * */


  public String getIDSala() {
    return this.IDSala;
  }

  /**Setta l'Id della sala
   * context Proiezione::setIDSala(sala) pre:sala!=null and ""
   * context Proiezione::setIDSala(sala) post:IDSala==sala
   * @param sala String - sala di cui settare l'ID
   * @throws Exception -eccezione lanciata se il parametro e' non valido
   * */


  public void setIDSala(String sala) throws Exception{
    if (controllaStringa(sala))
      this.IDSala=sala;
    else
      throw new Exception("Sala non valida");
  }

  /**Ritorna l'ID del film
   * @return int - ID del film
   * */

  public int getIDFilm() {
    return this.IDFilm;
  }

  /**Setta l'Id del film
   * context Proiezione::setIDFilm(film) pre:film>0
   * context Proiezione::setIDFilm(film) post:self.IDFilm==film
   * @param film int - film di cui settare l'ID
   * @throws Exception - eccezione lanciata se il parametro e' non valido
   *
   * */


  public void setIDFilm(int film) throws Exception{
    if (film>0)
      this.IDFilm=film;
    else
      throw new Exception("Film non esistente");
  }

  /**Collezione di proiezioni
   * context Proiezioni::loadProiezioni(id,data,bigliettiVenduti,sala,film,con) pre:con!=null
   * @return Collezione - collezione di proiezioni
   * @param id String -Id della proiezione
   * @param data GregorianCalendar - data della proiezione
   * @param bigliettiVenduti int - numero di biglietti venduti
   * @param sala String -sala della proiezione
   * @param film int - numero di film
   * @throws Exception - eccezione lanciata se i valori letti sono inconsistenti
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di caricamento dati dal DB
   * */


  public static Collezione loadProiezioni(String id,GregorianCalendar data,int bigliettiVenduti,String sala,int film) throws SQLException,Exception{
    setConnection();
    String query="SELECT * FROM Proiezione";
    if (controllaStringa(id)) {
      query=query+" WHERE ID=?";
      if (data!=null) {
        query = query + " AND Data_Ora_Inizio>? AND Data_Ora_Inizio<?";
        if (bigliettiVenduti>=0) {
          query=query+" AND BigliettiVenduti=?";
          if (controllaStringa(sala)) {
            query=query+" AND IDSala=?";
            if (film>0)
              query=query+" AND IDFilm=?";
          }
          else {
            if (film>0)
              query=query+" AND IDFilm=?";
          }
        }
        else {
          if (controllaStringa(sala)) {
            query=query+" AND IDSala=?";
            if (film>0)
              query=query+" AND IDFilm=?";
          }
          else {
            if (film>0)
              query=query+" AND IDFilm=?";
          }
        }
      }
      else {
        if (bigliettiVenduti>=0) {
          query=query+" AND BigliettiVenduti=?";
          if (controllaStringa(sala)) {
            query=query+" AND IDSala=?";
            if (film>0)
              query=query+" AND IDFilm=?";
          }
          else {
            if (film>0)
              query=query+" AND IDFilm=?";
          }
        }
        else {
          if (controllaStringa(sala)) {
            query=query+" AND IDSala=?";
            if (film>0)
              query=query+" AND IDFilm=?";
          }
          else {
            if (film>0)
              query=query+" AND IDFilm=?";
          }
        }
      }
    }
    else {
      if (data!=null) {
        query = query + " WHERE Data_Ora_Inizio>? AND Data_Ora_Inizio<?";
        if (bigliettiVenduti>=0) {
          query=query+" AND BigliettiVenduti=?";
          if (controllaStringa(sala)) {
            query=query+" AND IDSala=?";
            if (film>0)
              query=query+" AND IDFilm=?";
          }
          else {
            if (film>0)
              query=query+" AND IDFilm=?";
          }
        }
        else {
          if (controllaStringa(sala)) {
            query=query+" AND IDSala=?";
            if (film>0)
              query=query+" AND IDFilm=?";
          }
          else {
            if (film>0)
              query=query+" AND IDFilm=?";
          }
        }
      }
      else {
        if (bigliettiVenduti>=0) {
          query=query+" WHERE BigliettiVenduti=?";
          if (controllaStringa(sala)) {
            query=query+" AND IDSala=?";
            if (film>0)
              query=query+" AND IDFilm=?";
          }
          else {
            if (film>0)
              query=query+" AND IDFilm=?";
          }
        }
        else {
          if (controllaStringa(sala)) {
            query=query+" WHERE IDSala=?";
            if (film>0)
              query=query+" AND IDFilm=?";
          }
          else {
            if (film>0)
              query=query+" WHERE IDFilm=?";
          }
        }
      }
    }
    query=query+" ORDER BY Data_Ora_Inizio;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    //setteggio parametri query
    if (controllaStringa(id)) {
      preparedQuery.setString(1,id);
      if (data!=null) {
        int mese=data.get(data.MONTH)+1;
        preparedQuery.setString(2,""+data.get(data.YEAR)+"-"+mese+"-"+data.get(data.DAY_OF_MONTH)+" "+data.get(data.HOUR_OF_DAY)+":"+data.get(data.MINUTE));
        data.add(data.DAY_OF_MONTH,1);
//        data.set(data.HOUR,03);
//        data.set(data.MINUTE,00);
        preparedQuery.setString(3,""+data.get(data.YEAR)+"-"+mese+"-"+data.get(data.DAY_OF_MONTH)+" "+data.get(data.HOUR_OF_DAY)+":"+data.get(data.MINUTE));
        if (bigliettiVenduti>=0) {
          preparedQuery.setInt(4,bigliettiVenduti);
          if (controllaStringa(sala)) {
            preparedQuery.setString(5,sala);
            if (film>0)
              preparedQuery.setInt(6,film);
          }
          else {
            if (film>0)
              preparedQuery.setString(5,id);
          }
        }
        else {
          if (controllaStringa(sala)) {
            preparedQuery.setString(4,sala);
            if (film>0)
              preparedQuery.setInt(5,film);
          }
          else {
            if (film>0)
              preparedQuery.setInt(4,film);
          }
        }
      }
      else {
        if (bigliettiVenduti>=0) {
          preparedQuery.setInt(2,bigliettiVenduti);
          if (controllaStringa(sala)) {
            preparedQuery.setString(3,sala);
            if (film>0)
              preparedQuery.setInt(4,film);
          }
          else {
            if (film>0)
              preparedQuery.setInt(3,film);
          }
        }
        else {
          if (controllaStringa(sala)) {
            preparedQuery.setString(2,sala);
            if (film>0)
              preparedQuery.setInt(3,film);
          }
          else {
            if (film>0)
              preparedQuery.setInt(2,film);
          }
        }
      }
    }
    else {
      if (data!=null) {
        int mese=data.get(data.MONTH)+1;
        preparedQuery.setString(1,""+data.get(data.YEAR)+"-"+mese+"-"+data.get(data.DAY_OF_MONTH)+" "+data.get(data.HOUR_OF_DAY)+":"+data.get(data.MINUTE));
        data.add(data.DAY_OF_MONTH,1);
//        data.set(data.HOUR,03);
//        data.set(data.MINUTE,00);
        preparedQuery.setString(2,""+data.get(data.YEAR)+"-"+mese+"-"+data.get(data.DAY_OF_MONTH)+" "+data.get(data.HOUR_OF_DAY)+":"+data.get(data.MINUTE));
        if (bigliettiVenduti>=0) {
          preparedQuery.setInt(3,bigliettiVenduti);
          if (controllaStringa(sala)) {
            preparedQuery.setString(4,sala);
            if (film>0)
              preparedQuery.setInt(5,film);
          }
          else {
            if (film>0)
              preparedQuery.setInt(4,film);
          }
        }
        else {
          if (controllaStringa(sala)) {
            preparedQuery.setString(3,sala);
            if (film>0)
              preparedQuery.setInt(4,film);
          }
          else {
            if (film>0)
              preparedQuery.setInt(3,film);
          }
        }
      }
      else {
        if (bigliettiVenduti>=0) {
          preparedQuery.setInt(1,bigliettiVenduti);
          if (controllaStringa(sala)) {
            preparedQuery.setString(2,sala);
            if (film>0)
              preparedQuery.setInt(3,film);
          }
          else {
            if (film>0)
              preparedQuery.setInt(2,film);
          }
        }
        else {
          if (controllaStringa(sala)) {
            preparedQuery.setString(1,sala);
            if (film>0)
              preparedQuery.setInt(2,film);
          }
          else {
            if (film>0)
              preparedQuery.setInt(1,film);
          }
        }
      }
    }
    ResultSet Risultati=preparedQuery.executeQuery();
    System.out.println(preparedQuery.toString());
    Collezione collezioneDiProiezioni=new CollezioneArrayList();
    Proiezione nuova;
    String lavoro="";
    while(Risultati.next()) {
      lavoro=Risultati.getString(2);
      GregorianCalendar dataTemporanea=new GregorianCalendar(Integer.parseInt((lavoro.substring(0,4))),(Integer.parseInt((lavoro.substring(5,7)))-1),Integer.parseInt((lavoro.substring(8,10))),Integer.parseInt((lavoro.substring(11,13))),Integer.parseInt((lavoro.substring(14,16))));
//      System.out.println(dataTemporanea.get(GregorianCalendar.YEAR)+"-"+(dataTemporanea.get(GregorianCalendar.MONTH)+1)+"-"+dataTemporanea.get(GregorianCalendar.DAY_OF_MONTH)+"-"+dataTemporanea.get(GregorianCalendar.HOUR_OF_DAY)+"-"+dataTemporanea.get(GregorianCalendar.MINUTE));
      nuova=new Proiezione(Risultati.getString("ID"),dataTemporanea,Risultati.getInt("BigliettiVenduti"),Risultati.getString("IDSala"),Risultati.getInt("IDFilm"));
      collezioneDiProiezioni.add(nuova);
    }
    return collezioneDiProiezioni;
  }

  /**Memorizza una proiezione nel DataBase
   * context Proiezione::storageProiezione(con) pre:con!=null
   * @throws Exception - eccezione lanciata se si verifica un errore
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di memorizzazione dati nel DB
   * */


  public void storageProiezione() throws SQLException,Exception{
    setConnection();
    String query="INSERT INTO Proiezione(ID,Data_Ora_Inizio,BigliettiVenduti,IDSala,IDFilm) values(?,?,?,?,?);";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    GregorianCalendar lavoro=(GregorianCalendar)this.getDataOraInizio();
    preparedQuery.setString(1,this.getID());
    int mese=lavoro.get(GregorianCalendar.MONTH)+1;
    preparedQuery.setString(2,""+lavoro.get(GregorianCalendar.YEAR)+"-"+mese+"-"+lavoro.get(GregorianCalendar.DAY_OF_MONTH)+" "+lavoro.get(GregorianCalendar.HOUR_OF_DAY)+":"+lavoro.get(GregorianCalendar.MINUTE));
    preparedQuery.setInt(3,this.getBigliettiVenduti());
    preparedQuery.setString(4,this.getIDSala());
    preparedQuery.setInt(5,this.getIDFilm());
    preparedQuery.execute();
  }

  /**Aggiorna una proiezione presente nel Database
   * context Proiezione::updatePersona(con) pre:con!=null
   * param con Connection - oggetto di tipo connessione
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di aggiornamento dati del DB
   * @throws Exception - eccezione lanciata se si verifica un errore
   * */

  public void updateProiezione() throws SQLException,Exception{
    setConnection();
    String query="UPDATE Proiezione SET Data_Ora_Inizio=?, BigliettiVenduti=?, IDSala=?, IDFilm=? WHERE Proiezione.ID=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    GregorianCalendar lavoro=(GregorianCalendar)this.getDataOraInizio();
    int mese=lavoro.get(GregorianCalendar.MONTH)+1;
    preparedQuery.setString(1,""+lavoro.get(GregorianCalendar.YEAR)+"-"+mese+"-"+lavoro.get(GregorianCalendar.DAY_OF_MONTH)+" "+lavoro.get(GregorianCalendar.HOUR_OF_DAY)+":"+lavoro.get(GregorianCalendar.MINUTE));
    preparedQuery.setInt(2,this.getBigliettiVenduti());
    preparedQuery.setString(3,this.getIDSala());
    preparedQuery.setInt(4,this.getIDFilm());
    preparedQuery.setString(5,this.getID());
    preparedQuery.execute();
  }

  /**Elimina una proiezione
   * context Proiezione::DeletePersona(con)
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di eliminazione dati dal DB
   * @throws Exception -eccezione lanciata se si verifica un errore
   * */


  public void DeleteProiezione() throws SQLException,Exception{
    setConnection();
    String query="DELETE FROM Proiezione WHERE Proiezione.ID=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getID());
    preparedQuery.execute();
  }

  /**Aggiunge un biglietto alla lista di biglietti venduti per la proiezioen istanziata
   * @param b Biglietto - biglietto da aggiungere
   * */


  public void addBiglietto(Biglietto b) {
    Biglietti.add(b);
    b.setProiezione(this);
  }

  /**Elimina un biglietto dalla lista di biglietti venduti per la proiezioen istanziata
   * @param b Biglietto - biglietto da eliminare
   * */


  public void deleteBiglietto(Biglietto b) {
    Biglietti.remove(b);
    b.setProiezione(null);
  }

  /**Setta il film a cui la proiezione si riferisce
   * @param f Film - film da settare
   * */


  public void setFilm(Film f) {
    if (film!=f) {
      Film old=film;
      film=f;
      if (film!=null)
        film.addProiezione(this);
      if (old!=null)
        old.deleteProiezione(this);
    }
  }

  /**Setta la sala in cui si terrà al proiezione
   * @param s Sala -sala da settare
   * */

  public void setSala(Sala s) {
    if (sala!=s) {
      Sala old=sala;
      sala=s;
      if (sala!=null)
        sala.addProiezione(this);
      if (old!=null)
        old.deleteProiezione(this);
    }
  }

  /**
   *
   * Setta la connessione al DataBase se non è stata già definita
   */

  private static void setConnection() {
    if (connessione==null)
      try {
      //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      //String url="jdbc:odbc:DB_SimplyTicket";?user=monty&password=greatsqldb
      Class.forName("com.mysql.jdbc.Driver");
      String url="jdbc:mysql://localhost/DB_SimplyTicket?user=root";
      connessione=DriverManager.getConnection(url);
    }
    catch (Exception e) {
        e.printStackTrace();
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
   * L'ID della proiezione deve essere diverso da null e dalla stringa vuota
   * context Proiezione inv:self.ID!=null and ""
   * La data di inizio della proiezione deve essere diversa da null
   * context Proiezione inv:self.Data_Ora_Inizio!=null
   * Il numero di biglietti venduti per una proiezione deve essere maggiore o ugiuale di 0
   * context Proiezione inv:self.BigliettiVenduti>=0
   * L'ID della sala deve essere diverso da nulla e dalla stringa vuota
   * context Proiezione inv:self.IDSala!=null and ""
   * L'Id del film deve essere maggiore di 0
   * context Proiezione inv:self.IDFilm>0
   *
   * */

  private String ID;
  private GregorianCalendar Data_Ora_Inizio;
  private int BigliettiVenduti;
  private String IDSala;
  private int IDFilm;
  private static Connection connessione;
  private CollezioneArrayList Biglietti;
  private Film film;
  private Sala sala;
}
