package data_layer;
import java.util.GregorianCalendar;
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

/**Un palinsesto e' un rappresenta la programmazione per un dato periodo della struttura,cioe' i film proiettati ogni giorno
*  per un dato periodo
* */

public class Palinsesto implements Serializable{

  /**@param id String - codice identificativo del palinsesto
   * @param inizio GregorianCalendar - data di inizio del palinsesto
   * @param fine GregorianCalendar - data di fine del palinsesto
   * @param sala String - sala dove impostare il palinsesto
   * @throws Exception - eccezione lanciata in caso di parametri non validi
   * * */

  public Palinsesto(String id,GregorianCalendar inizio,GregorianCalendar fine,String sala) throws Exception{
    if (controllaStringa(id) && inizio!=null && fine!=null && controllaStringa(sala)) {
      this.ID=id;
      this.DataInizioValidita=inizio;
      this.DataFineValidita=fine;
      this.IDSala=sala;
    }
    else
      throw new Exception("Dati per il palinsesto non validi");
  }

  /**Verifica la Validita del parametro passato
    * @param daControllare - parametro da controllare
    * @return boolean - valore booleano ad indicare se il parametro e' valido o meno
    * */

  private static boolean controllaStringa(String daControllare) {
    if ((daControllare!=null && !daControllare.equalsIgnoreCase("")) || daControllare.endsWith("�"))
      return true;
    else
      return false;
  }

  /**Ritorna L'ID del palinsesto
   * @return String - identificativo palinsesto
   * */


  public String getID() {
    return this.ID;
  }

  /**Ritorna la data di inizio del palinsesto
   * @return GregorianCalendar - data di inizio del palinsesto
   * */


  public GregorianCalendar getDataInizioValidita() {
    return this.DataInizioValidita;
  }

  /**Imposta la data di inizio Validita del palinsesto
  * context Palinsesto::setDataInizioValidita(inizio) post:self.DataInizioValidita==inizio
  * @param inizio GregorianCalendar - data da settare come inizio del palinsesto
  * @throws Exception - eccezione lanciata in caso di parametro errato
  * */


  public void setDataInizioValidita(GregorianCalendar inizio) throws Exception{
    if (inizio!=null)
      this.DataInizioValidita=inizio;
    else
      throw new Exception("La Data Specificata non � valida");
  }

  /**Ritorna la data di scadenza del palinsesto
   * @return GregorianCalendar - data di fine Validita del palinsesto
   * */


  public GregorianCalendar getDataFineValidita() {
    return this.DataFineValidita;
  }

  /**Imposta la data di scadenza del palinsesto
  * context Palinsesto::setDataFineValidita(fine) pre:fine!=null or ""
  * context Palinsesto::setDataFineValidita(fine) post:self.DataFineValidita==fine
  * @param fine GregorianCalendar - data da settare come fine Validita del palinsesto
  * @throws Exception - eccezione lanciata in caso di parametro non valido
  * * */


  public void setDataFineValidita(GregorianCalendar fine) throws Exception{
    if (fine!=null)
      this.DataFineValidita=fine;
    else
      throw new Exception("La Data di Chiusura Specificata non � valida");
  }

  /**Ritorna l'ID della sala
  * @return String - identificativo della sala
  * */


  public String getIDSala() {
    return this.IDSala;
  }

  /**Imposta l'ID della sala
  * context Palinsesto::setIDSala(sala) pre:sala!=null or ""
  * context Palinsesto::setIDSala(sala) post:@self.IDSala==sala
  * @param sala String - identificativo della sala da settare
  * @throws Exception - eccezione lanciata in caso di parametro non valido
  * */


  public void setIDSala(String sala) throws Exception{
    if (controllaStringa(sala))
      this.IDSala=sala;
    else
      throw new Exception("La Sala Specificata per questo palinsesto non � valida");
  }

  /**Riorna una collezione di palinsesti
   *
   * @return Collezione - collezione di palinsesti
   * @param id String - identificativo del palinsesto
   * @param inizio GregorianCalendar - data di nizio del palinsesto
   * @param fine GregorianCalendar - data di scadenza del palinsesto
   * @param sala String - sala di riferimento del palinsesto
   * @throws Exception - eccezione lanciata in caso di errore
   * @throws SQLException - eccezione lanciata in caso di errore in fase di caricamento dati dal DB
   * */


  public static Collezione loadPalinsesto(String id,GregorianCalendar inizio,GregorianCalendar fine,String sala) throws SQLException,Exception{
    setConnection();
    String query="SELECT * FROM Palinsesto";
    if (controllaStringa(id)) {
      query=query+" WHERE ID=?";
      if (inizio!=null) {
        query=query+" AND DataInizioValidita=?";
        if (fine!=null) {
          query=query+" AND DataFineValidita=?";
          if (controllaStringa(sala))
            query=query+" AND IDSala=?";
        }
        else {
          if (controllaStringa(sala))
            query=query+" AND IDSala=?";
        }
      }
      else {
        if (fine!=null) {
          query=query+" AND DataFineValidita=?";
          if (controllaStringa(sala))
            query=query+" AND IDSala=?";
        }
        else {
          if (controllaStringa(sala))
            query=query+" AND IDSala=?";
        }
      }
    }
    else {
      if (inizio!=null) {
        query=query+" WHERE DataInizioValidita=?";
        if (fine!=null) {
          query=query+" AND DataFineValidita=?";
          if (controllaStringa(sala))
            query=query+" AND IDSala=?";
        }
        else {
          if (controllaStringa(sala))
            query=query+" AND IDSala=?";
        }
      }
      else {
        if (fine!=null) {
          query=query+" WHERE DataFineValidita=?";
          if (controllaStringa(sala))
            query=query+" AND IDSala=?";
        }
        else {
          if (controllaStringa(sala))
            query=query+" WHERE IDSala=?";
        }
      }
    }
    query = query+";";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    if (controllaStringa(id)) {
      preparedQuery.setString(1,id);
      if (inizio!=null) {
        preparedQuery.setString(2,""+inizio.get(GregorianCalendar.YEAR)+"-"+(inizio.get(GregorianCalendar.MONTH)+1)+"-"+inizio.get(GregorianCalendar.DAY_OF_MONTH)+" "+inizio.get(GregorianCalendar.HOUR_OF_DAY)+":"+inizio.get(GregorianCalendar.MINUTE));
        if (fine!=null) {
          preparedQuery.setString(3,""+fine.get(GregorianCalendar.YEAR)+"-"+(fine.get(GregorianCalendar.MONTH)+1)+"-"+fine.get(GregorianCalendar.DAY_OF_MONTH)+" "+fine.get(GregorianCalendar.HOUR_OF_DAY)+":"+fine.get(GregorianCalendar.MINUTE));
          if (controllaStringa(sala))
            preparedQuery.setString(4,sala);
        }
        else {
          if (controllaStringa(sala))
            preparedQuery.setString(3,sala);
        }
      }
      else {
        if (fine!=null) {
          preparedQuery.setString(2,""+fine.get(GregorianCalendar.YEAR)+"-"+(fine.get(GregorianCalendar.MONTH)+1)+"-"+fine.get(GregorianCalendar.DAY_OF_MONTH)+" "+fine.get(GregorianCalendar.HOUR_OF_DAY)+":"+fine.get(GregorianCalendar.MINUTE));
          if (controllaStringa(sala))
            preparedQuery.setString(3,sala);
        }
        else {
          if (controllaStringa(sala))
            preparedQuery.setString(2,sala);
        }
      }
    }
    else {
      if (inizio!=null) {
        preparedQuery.setString(1,""+inizio.get(GregorianCalendar.YEAR)+"-"+(inizio.get(GregorianCalendar.MONTH)+1)+"-"+inizio.get(GregorianCalendar.DAY_OF_MONTH)+" "+inizio.get(GregorianCalendar.HOUR_OF_DAY)+":"+inizio.get(GregorianCalendar.MINUTE));
        if (fine!=null) {
          preparedQuery.setString(2,""+fine.get(GregorianCalendar.YEAR)+"-"+(fine.get(GregorianCalendar.MONTH)+1)+"-"+fine.get(GregorianCalendar.DAY_OF_MONTH)+" "+fine.get(GregorianCalendar.HOUR_OF_DAY)+":"+fine.get(GregorianCalendar.MINUTE));
          if (controllaStringa(sala))
            preparedQuery.setString(3,sala);
        }
        else {
          if (controllaStringa(sala))
            preparedQuery.setString(2,sala);
        }
      }
      else {
        if (fine!=null) {
          preparedQuery.setString(1,""+fine.get(GregorianCalendar.YEAR)+"-"+(fine.get(GregorianCalendar.MONTH)+1)+"-"+fine.get(GregorianCalendar.DAY_OF_MONTH)+" "+fine.get(GregorianCalendar.HOUR_OF_DAY)+":"+fine.get(GregorianCalendar.MINUTE));
          if (controllaStringa(sala))
            preparedQuery.setString(2,sala);
        }
        else {
          if (controllaStringa(sala))
            preparedQuery.setString(1,sala);
        }
      }
    }
    ResultSet Risultati=preparedQuery.executeQuery();
    Collezione collezioneDiPalinsesti=new CollezioneArrayList();
    Palinsesto nuovo;
    String lavoro="";
    String lavoro2="";
    while(Risultati.next()) {
      lavoro=Risultati.getString("DataInizioValidita");
      lavoro2=Risultati.getString("DataFineValidita");
      GregorianCalendar dataTemporanea=new GregorianCalendar(Integer.parseInt(lavoro.substring(0,4)),Integer.parseInt(lavoro.substring(5,7)),Integer.parseInt(lavoro.substring(8,10)));
      GregorianCalendar dataTemporaneaFine=new GregorianCalendar(Integer.parseInt(lavoro2.substring(0,4)),Integer.parseInt(lavoro2.substring(5,7)),Integer.parseInt(lavoro2.substring(8,10)));
      nuovo=new Palinsesto(Risultati.getString("ID"),dataTemporanea,dataTemporaneaFine,Risultati.getString("IDSala"));
      collezioneDiPalinsesti.add(nuovo);
      }
    Risultati.close();
    return collezioneDiPalinsesti;
  }

  /**Memorizza un palinsesto
   *
   * @throws SQLException - eccezione lanciata in caso di errore in fase di memorizzazione dati nel DB
   * @throws Exception - eccezione lanciata in caso di errore
   * */


  public void storagePalinsesto() throws SQLException,Exception{
    setConnection();
    String query="INSERT INTO Palinsesto(ID,DataInizioValidita,DataFineValidita,IDSala) values(?,?,?,?);";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    GregorianCalendar lavoro=this.getDataInizioValidita();
    GregorianCalendar lavoro2=this.getDataFineValidita();
    preparedQuery.setString(1,this.getID());
    preparedQuery.setString(2,""+lavoro.get(GregorianCalendar.YEAR)+"-"+lavoro.get(GregorianCalendar.MONTH)+"-"+lavoro.get(GregorianCalendar.DAY_OF_MONTH));
    preparedQuery.setString(3,""+lavoro2.get(GregorianCalendar.YEAR)+"-"+lavoro2.get(GregorianCalendar.MONTH)+"-"+lavoro2.get(GregorianCalendar.DAY_OF_MONTH));
    preparedQuery.setString(4,this.getIDSala());
    preparedQuery.execute();
  }

  /**Aggiorna un palinsesto
  * @throws SQLException - eccezione lanciata in caso di errore in fase di aggiornamento dati nel DB
  * @throws Exception - eccezione lanciata in caso di errore
  * */


  public void updatePalinsesto() throws SQLException,Exception{
    setConnection();
    String query="UPDATE Palinsesto SET ID=?, DataInizioValidita=?, DataFineValidita=?, IDSala=? WHERE Palinsesto.ID=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    GregorianCalendar lavoro=this.getDataInizioValidita();
    GregorianCalendar lavoro2=this.getDataFineValidita();
    preparedQuery.setString(1,this.getID());
    preparedQuery.setString(2,""+lavoro.get(GregorianCalendar.YEAR)+"-"+lavoro.get(GregorianCalendar.MONTH)+"-"+lavoro.get(GregorianCalendar.DAY_OF_MONTH)+" "+lavoro.get(GregorianCalendar.HOUR_OF_DAY)+":"+lavoro.get(GregorianCalendar.MINUTE));
    preparedQuery.setString(3,""+lavoro2.get(GregorianCalendar.YEAR)+"-"+lavoro2.get(GregorianCalendar.MONTH)+"-"+lavoro2.get(GregorianCalendar.DAY_OF_MONTH)+" "+lavoro2.get(GregorianCalendar.HOUR_OF_DAY)+":"+lavoro2.get(GregorianCalendar.MINUTE));
    preparedQuery.setString(4,this.getIDSala());
    preparedQuery.setString(5,this.getID());
    preparedQuery.execute();
  }

  /**Elimina un palinsesto
 * @throws Exception - eccezione lanciata in caso di errore
 * @throws SQLException - eccezione lanciata in caso di errore in fase di cancellazione dati nel DB
 * */


  public void DeletePalinsesto() throws SQLException,Exception{
    setConnection();
    String query="DELETE FROM Palinsesto WHERE Palinsesto.ID=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getID());
    preparedQuery.execute();
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

  /**Invarianti:
   * L'ID del palinsesto deve essese diverso da null e dalla stringa vuota
   * context Palinsesto inv:self.ID!=null and ""
   * La data di inizio dell palinsesto deve essere diversa da null
   * context Palinsesto inv:self.DataInizioValidita!=null
   * La data di fine Validita del palinsesto deve essere diversa da null
   * context Palinsesto inv:self.DataFineValidita!=null
   * La sala deve essere diversa da null e dalla stringa vuota
   * context Palinsesto inv:self.IDSala!=null and ""
   * * */

  private String ID;
  private GregorianCalendar DataInizioValidita;
  private GregorianCalendar DataFineValidita;
  private String IDSala;
  private static Connection connessione;
}
