package data_layer;
import java.sql.*;
import java.util.*;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * <p>Title: Data Layer</p>
 * <p>Description: Il Package contenente tutto il Data Layer;le classi in esso contenute, sono classi ke interagiscono direttamente con il database</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Spinelli Raffaele
 * @version 1.0
 */

public class Abbonamento implements Serializable{

  /**Costruttore pubblico.
   * @param codice String - il codice dell'abbonamento
   * @param costo Double- il prezzo dell'abbonamento
   * @param tipo String - il tipo dell'abbonamento
   * @param data GregorianCalendar - la data di emissione dell'abbonamento
   * @param filmDisponibili int - il numero di film disponibili per un abbonamento
   * @param idabbonato int - l'identificatifo dell'abbonato
   * @throws Exception - Viene lanciato una eccezione se un parametro per la generazione dell'oggetto non è corretto
   * */

  public Abbonamento(String codice,double costo,String tipo,GregorianCalendar data,int filmDisponibili,int idabbonato) throws Exception{
    if (controllaStringa(codice) && controllaStringa(tipo) && costo>0 && data!=null && filmDisponibili>=0 && idabbonato>0) {
      this.Codice=codice;
      this.Costo=costo;
      this.Tipo=tipo;
      this.Scadenza=data;
      this.FilmDisponibili=filmDisponibili;
      this.IDAbbonato=idabbonato;
    }
    else
      throw new Exception("Dati non validi");
  }

  /**Metodo che verifica la validità dell parametro passato
   * @param daControllare String - parametro che contiene la stringa da verificare
   * @return boolean - se la stringa e' verificabile o meno(stringa non vuota o nulla)
   * */

  private static boolean controllaStringa(String daControllare) {
    if ((daControllare!=null && !daControllare.equalsIgnoreCase("")))
      return true;
    else
      return false;
  }

  /**Ritorna il codice dell'abbonamento
   * @return String - codice dell'abbonamento
   * */

  public String getCodice() {
    return this.Codice;
  }

  /**Ritorna il costo dell'abbonamento
   * @return double - costo dell'abbonamento
   * */

  public double getCosto() {
      return this.Costo;
  }

  /**Setta il costo dell'abbonamento
   * context Abbonamento::setCosto(costo) pre:costo>0
   * context Abbonamento::setCosto(costo) post:self.Costo==costo
   * @param costo double - costo da settare
   * @throws Exception - eccezione lanciata se il parametro passato non e' valido
   * */

  public void setCosto(double costo) throws Exception{
    if (costo>0)
      this.Costo=costo;
    else
      throw new Exception("Costo dell'abbonametno non valido");
  }

  /**Ritorna il tipo dell' abbonamento
   * @return String - tipo dell'abbonamento
   * */

  public String getTipo() {
    return this.Tipo;
  }

  /**Setta il tipo dell'abbonamento
   * context Abbonamento::setTipo(tipo) pre:tipo!=null ad ""
   * context Abbonamento::setTipo(tipo) post:self.Tipo==tipo
   * @param tipo String - tipo dell'abbonamento da settare
   * @throws Exception - eccezione lanciata se il paramento non e' valido
   * */

  public void setTipo(String tipo) throws Exception{
    if (controllaStringa(tipo))
      this.Tipo=tipo;
    else
      throw new Exception("Tipo non riconosciuto");
  }

  /**Ritorna da data di scadenza dell'abbonamento
   * @return GregorianCalendar - data di scadenza
   * */

  public GregorianCalendar getScadenza() {
    return this.Scadenza;
  }

  /**Setta la dsta di scadenza dell'abbonamento
   * context Abbonamento::setScadenza(scadenza) pre:scadenza!=null
   * context Abbonamento::setScadenza(scadenza) post:self.Scadenza==scadenza
   * @param scadenza GregorianCalendar - data di scadenza da settare
   * @throws Exception - eccezione lanciata se il parametro non e' valido
   * */

  public void setScadenza(GregorianCalendar scadenza) throws Exception{
    if (scadenza!=null)
      this.Scadenza=scadenza;
    else
      throw new Exception("Data non specificata");
  }

  /**Ritorna il numero di fil disponibili per l'abbonamento
   * @return int - numero di film disponibili
   * */

  public int getFilmDisponibili() {
      return this.FilmDisponibili;
  }

  /**Setta il numero di film disponibili per l'abbonamento
   * context Abbonamento::setFilmDisponibili(film) pre:film>0
   * context Abbonamento::setFilmDisponibili(film) post:self.FilmDisponibili==FilmDisponibili+film
   * @param film int - numero di film disponibili da settare
   * @throws Exception - eccezione lanciata se il parametro non e' valido
   * */

  public void setFilmDisponibili(int film) throws Exception{
    if (film>0)
      this.FilmDisponibili+=film;
    else
      throw new Exception("Non puoi aggiungere una quantita negativa");
  }

  /**Ritorna l'ID dell'abbonamento
   * @return int -identificatore abbonato
   * */

  public int getIDAbbonato() {
      return this.IDAbbonato;
  }

  /**Setta l'Id dell'abbonato
   * context Abbonamento::setIDAbbonato(abbonato) pre:abbonato>0
   * context Abbonamento::setIDAbbonato(abbonato) post:self.IDAbbonato==abbonato
   * @param abbonato int - identificatore abbonato
   * @throws Exception - eccezione lanciata se l'ID non e' valido
   * */

  public void setIDAbbonato(int abbonato) throws Exception{
    if (abbonato>0)
      this.IDAbbonato=abbonato;
    else
      throw new Exception("Costo dell'abbonametno non valido");
  }

  /**Ritorna una collezione di abbonamenti
   * @return Collezione - collezione di abbonamenti
   * @param codice String - codice dell'abbonamento
   * @param costo Double- il prezzo dell'abbonamento
   * @param tipo String - il tipo dell'abbonamento
   * @param scadenza GregorianCalendar - la data di emissione dell'abbonamento
   * @param filmDisponibili int - il numero di film disponibili per un abbonamento
   * @param idAbbonato int - l'identificatifo dell'abbonato
   * @throws SQLException - eccezione lanciata se si verifica un errore in fase di caricamento dati dal DB
   * @throws Exception - eccezione lanciata in caso di errore
   * */

  public static Collezione LoadAbbonamento(String codice,double costo,String tipo,GregorianCalendar scadenza,int filmDisponibili,int idAbbonato) throws SQLException,Exception{
    setConnection();
    String query="SELECT * FROM Abbonamento";
    if (controllaStringa(codice)) {
      query = query + " WHERE Codice=?";
      if (costo > 0)
        query = query + " AND Costo=?";
      if (controllaStringa(tipo))
        query = query + " AND Tipo=?";
      if (scadenza != null)
        query = query + " AND Scadenza<?";
      if (filmDisponibili > 0)
        query = query + " AND FilmDisponibili=?";
      if (idAbbonato > 0)
        query = query + " AND IDAbbonato=?";
    }
    else {
      if (costo > 0) {
        query = query + " WHERE Costo=?";
        if (controllaStringa(tipo))
          query = query + " AND Tipo=?";
        if (scadenza != null)
          query = query + " AND Scadenza<?";
        if (filmDisponibili > 0)
          query = query + " AND FilmDisponibili=?";
        if (idAbbonato > 0)
          query = query + " AND IDAbbonato=?";
      }
      else {
        if (controllaStringa(tipo)) {
          query = query + " WHERE Tipo=?";
          if (scadenza != null)
            query = query + " AND Scadenza<?";
          if (filmDisponibili > 0)
            query = query + " AND FilmDisponibili=?";
          if (idAbbonato > 0)
            query = query + " AND IDAbbonato=?";
        }
        else {
          if (scadenza != null) {
            query = query + " WHERE Scadenza<?";
            if (filmDisponibili > 0)
              query = query + " AND FilmDisponibili=?";
            if (idAbbonato > 0)
              query = query + " AND IDAbbonato=?";
          }
          else {
            if (filmDisponibili > 0) {
              query = query + " WHERE FilmDisponibili=?";
              if (idAbbonato > 0)
                query = query + " AND IDAbbonato=?";
            }
            else {
              if (idAbbonato > 0)
                query = query + " WHERE IDAbbonato=?";
            }
          }
        }
      }
    }
    query=query+";";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    if (controllaStringa(codice)) {
      preparedQuery.setString(1,codice);
      if (costo>0) {
        preparedQuery.setDouble(2,costo);
        if (controllaStringa(tipo)) {
          preparedQuery.setString(3,tipo);
          if (scadenza!=null) {
            preparedQuery.setString(4,""+scadenza.get(GregorianCalendar.YEAR)+"-"+(scadenza.get(GregorianCalendar.MONTH)+1)+"-"+scadenza.get(GregorianCalendar.DAY_OF_MONTH)+" "+scadenza.get(GregorianCalendar.HOUR_OF_DAY)+":"+scadenza.get(GregorianCalendar.MINUTE));
            if (filmDisponibili>0) {
              preparedQuery.setInt(5,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(6,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(5, idAbbonato);
            }
          }
          else {
            if (filmDisponibili>0) {
              preparedQuery.setInt(4,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(5,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(4, idAbbonato);
            }
          }
        }
        else {
          if (scadenza!=null) {
            preparedQuery.setString(3,""+scadenza.get(GregorianCalendar.YEAR)+"-"+(scadenza.get(GregorianCalendar.MONTH)+1)+"-"+scadenza.get(GregorianCalendar.DAY_OF_MONTH)+" "+scadenza.get(GregorianCalendar.HOUR_OF_DAY)+":"+scadenza.get(GregorianCalendar.MINUTE));
            if (filmDisponibili>0) {
              preparedQuery.setInt(4,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(5,idAbbonato);
            }
            else {
               if (idAbbonato > 0)
                preparedQuery.setInt(4, idAbbonato);
            }
          }
          else {
            if (filmDisponibili>0) {
              preparedQuery.setInt(3,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(4,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(3, idAbbonato);
            }
          }
        }
      }
      else {
        if (controllaStringa(tipo)) {
          preparedQuery.setString(2,tipo);
          if (scadenza!=null) {
            preparedQuery.setString(3,""+scadenza.get(GregorianCalendar.YEAR)+"-"+(scadenza.get(GregorianCalendar.MONTH)+1)+"-"+scadenza.get(GregorianCalendar.DAY_OF_MONTH)+" "+scadenza.get(GregorianCalendar.HOUR_OF_DAY)+":"+scadenza.get(GregorianCalendar.MINUTE));
            if (filmDisponibili>0) {
              preparedQuery.setInt(4,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(5,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(4, idAbbonato);
            }
          }
          else {
            if (filmDisponibili>0) {
              preparedQuery.setInt(3,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(4,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(3, idAbbonato);
            }
          }
        }
        else {
          if (scadenza!=null) {
            preparedQuery.setString(2,""+scadenza.get(GregorianCalendar.YEAR)+"-"+(scadenza.get(GregorianCalendar.MONTH)+1)+"-"+scadenza.get(GregorianCalendar.DAY_OF_MONTH)+" "+scadenza.get(GregorianCalendar.HOUR_OF_DAY)+":"+scadenza.get(GregorianCalendar.MINUTE));
            if (filmDisponibili>0) {
              preparedQuery.setInt(3,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(4,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(3, idAbbonato);
            }
          }
          else {
            if (filmDisponibili>0) {
              preparedQuery.setInt(2,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(3,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(2, idAbbonato);
            }
          }
        }
      }
    }
    else {
      if (costo>0) {
        preparedQuery.setDouble(1,costo);
        if (controllaStringa(tipo)) {
          preparedQuery.setString(2,tipo);
          if (scadenza!=null) {
            preparedQuery.setString(3,""+scadenza.get(GregorianCalendar.YEAR)+"-"+(scadenza.get(GregorianCalendar.MONTH)+1)+"-"+scadenza.get(GregorianCalendar.DAY_OF_MONTH)+" "+scadenza.get(GregorianCalendar.HOUR_OF_DAY)+":"+scadenza.get(GregorianCalendar.MINUTE));
            if (filmDisponibili>0) {
              preparedQuery.setInt(4,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(5,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(4, idAbbonato);
            }
          }
          else {
            if (filmDisponibili>0) {
              preparedQuery.setInt(3,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(4,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(3, idAbbonato);
            }
          }
        }
        else {
          if (scadenza!=null) {
            preparedQuery.setString(2,""+scadenza.get(GregorianCalendar.YEAR)+"-"+(scadenza.get(GregorianCalendar.MONTH)+1)+"-"+scadenza.get(GregorianCalendar.DAY_OF_MONTH)+" "+scadenza.get(GregorianCalendar.HOUR_OF_DAY)+":"+scadenza.get(GregorianCalendar.MINUTE));
            if (filmDisponibili>0) {
              preparedQuery.setInt(3,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(4,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(3, idAbbonato);
            }
          }
          else {
            if (filmDisponibili>0) {
              preparedQuery.setInt(2,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(3,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(2, idAbbonato);
            }
          }
        }
      }
      else {
        if (controllaStringa(tipo)) {
          preparedQuery.setString(1,tipo);
          if (scadenza!=null) {
            preparedQuery.setString(2,""+scadenza.get(GregorianCalendar.YEAR)+"-"+(scadenza.get(GregorianCalendar.MONTH)+1)+"-"+scadenza.get(GregorianCalendar.DAY_OF_MONTH)+" "+scadenza.get(GregorianCalendar.HOUR_OF_DAY)+":"+scadenza.get(GregorianCalendar.MINUTE));
            if (filmDisponibili>0) {
              preparedQuery.setInt(3,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(4,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(3, idAbbonato);
            }
          }
          else {
            if (filmDisponibili>0) {
              preparedQuery.setInt(2,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(3,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(2, idAbbonato);
            }
          }
        }
        else {
          if (scadenza!=null) {
            preparedQuery.setString(1,""+scadenza.get(GregorianCalendar.YEAR)+"-"+(scadenza.get(GregorianCalendar.MONTH)+1)+"-"+scadenza.get(GregorianCalendar.DAY_OF_MONTH)+" "+scadenza.get(GregorianCalendar.HOUR_OF_DAY)+":"+scadenza.get(GregorianCalendar.MINUTE));
            if (filmDisponibili>0) {
              preparedQuery.setInt(2,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(3,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(2, idAbbonato);
            }
          }
          else {
            if (filmDisponibili>0) {
              preparedQuery.setInt(1,filmDisponibili);
              if (idAbbonato>0)
                preparedQuery.setInt(2,idAbbonato);
            }
            else {
              if (idAbbonato > 0)
                preparedQuery.setInt(1, idAbbonato);
            }
          }
        }
      }
    }
    ResultSet Risultati=preparedQuery.executeQuery();
    Collezione collezioneDiAbbonamenti=new CollezioneArrayList();
    Abbonamento nuova;
    String lavoro;
    while(Risultati.next()) {
      lavoro=Risultati.getString("Scadenza");
      GregorianCalendar dataTemporanea=new GregorianCalendar(Integer.parseInt((lavoro.substring(0,4))),(Integer.parseInt((lavoro.substring(5,7)))-1),Integer.parseInt((lavoro.substring(8,10))),Integer.parseInt((lavoro.substring(11,13))),Integer.parseInt((lavoro.substring(14,16))));
      nuova=new Abbonamento(Risultati.getString("Codice"),Risultati.getDouble("Costo"),Risultati.getString("Tipo"),dataTemporanea,Risultati.getInt("FilmDisponibili"),Risultati.getInt("IDAbbonato"));
      collezioneDiAbbonamenti.add(nuova);
    }
    Risultati.close();
    return collezioneDiAbbonamenti;
  }

  /**Memorizza un abbonamento
   * context Abbonamento::storageAbbonamento(con) pre:con!=null
   * @throws SQLException - eccezione che viene lanciata in caso di errore in fase di memorizzazione dati nel DB
   * @throws Exception - eccezione lanciata in caso di errore
   * */

  public void storageAbbonamento() throws SQLException,Exception{
    setConnection();
    String query="INSERT INTO Abbonamento(Codice,Costo,Tipo,Scadenza,FilmDisponibili,IDAbbonato) values(?,?,?,?,?,?);";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    GregorianCalendar lavoro=this.getScadenza();
    preparedQuery.setString(1,this.getCodice());
    preparedQuery.setDouble(2,this.getCosto());
    preparedQuery.setString(3,this.getTipo());
    preparedQuery.setString(4,""+lavoro.get(GregorianCalendar.YEAR)+"-"+lavoro.get(GregorianCalendar.MONTH)+"-"+lavoro.get(GregorianCalendar.DAY_OF_MONTH));
    preparedQuery.setInt(5,this.getFilmDisponibili());
    preparedQuery.setInt(6,this.getIDAbbonato());
    preparedQuery.execute();
  }

  /**Aggiorna un addetto
   * context Abbonamento::updateAddetto(con) pre:con!=null
   * @throws SQLException - eccezione lanciata in caso di errore in fase di aggiornamento dati nel DB
   * @throws Exception - eccezione lanciata in caso di errore
   * */

  public void updateAbbonamento() throws SQLException,Exception{
    setConnection();
    String query="UPDATE Abbonamento SET Costo= ?, Tipo= ?, Scadenza= ?, FilmDisponibili= ?, IDAbbonato= ? WHERE Abbonamento.Codice= ?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    GregorianCalendar lavoro=this.getScadenza();
    preparedQuery.setDouble(1,this.getCosto());
    preparedQuery.setString(2,this.getTipo());
    preparedQuery.setString(3,""+lavoro.get(GregorianCalendar.YEAR)+"-"+lavoro.get(GregorianCalendar.MONTH)+"-"+lavoro.get(GregorianCalendar.DAY_OF_MONTH));
    preparedQuery.setInt(4,this.getFilmDisponibili());
    preparedQuery.setInt(5,this.getIDAbbonato());
    preparedQuery.setString(6,this.getCodice());
    preparedQuery.execute();
  }

  /**Elimina un addetto
   * context Abbonamento::DeleteAddetto(con) pre:con!=null
   * @throws SQLException - eccezione lanciata in caso di errore in fase di cancellazione dati nel DB
   * @throws Exception - eccezione lanciata in caso di errore
   * */

  public void DeleteAbbonamento() throws SQLException,Exception{
    setConnection();
    String query="DELETE FROM Abbonamento WHERE Abbonamento.Codice=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getCodice());
    preparedQuery.execute();
  }

  /**Setta il proprietario dell'abbonamento
   * @param newOwner - nuovo proprietario da settare
   * */

  public void setOwner(Abbonato newOwner) {
    if (owner!=newOwner) {
      Abbonato old=owner;
      owner=newOwner;
      if (newOwner!=null)
        newOwner.addAbbonamento(this);
      if (old!=null)
        old.deleteAbbonamento(this);
    }
  }

  /**Aggiunge un biglietto all'abbonamento
   * @param a Biglietto - oggetto biglietto
   * */

  public void addBiglietto(Biglietto a) {
    Biglietti.add(a);
    a.setOwner(this);
  }

  /**Elimina un biglietto
   * @param a - oggetto di tipo biglietto
   * */

  public void deleteBiglietto(Biglietto a) {
    Biglietti.remove(a);
    a.setOwner(null);
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
   * Il codice del biglietto deve essere diverso da null a dalla stringa vuota
   * context abbonamento inv:self.Codice!=null and ""
   * Il costo dell'abbonamento deve essere maggiore o uguale a 0
   * context Abbonamento inv:self.Costo>=0
   * Il tipo dell'abbonamento deve essere diverso da null e dalla stringa vuota
   * context Abbonamento inv:self.Tipo!=null and ""
   * La data di scadenza deve essere docersa da null
   * context Abbonamento inv:self.Scadenza!=null
   * Il numero di film disponibili deve essere maggiore di 0
   * context Abbonamento inv:self.FilmDisponibili>0
   * L'Id abbonato deve essere maggiore di 0
   * context Abbonamento inv:self.IDAbbonato>0
   * */

  private Abbonato owner;
  private String Codice;
  private double Costo;
  private String Tipo;
  private GregorianCalendar Scadenza;
  private int FilmDisponibili;
  private int IDAbbonato;
  private static Connection connessione=null;
  private Collezione Biglietti;
}
