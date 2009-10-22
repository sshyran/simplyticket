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

public class Film implements Serializable{
  public Film(int id,String titolo,String descrizione,String genere,String regia,String attori,String paese,String locandina) throws Exception{
    if (id>0&&controllaStringa(titolo)&&controllaStringa(descrizione)&&controllaStringa(genere)&&controllaStringa(regia)&&controllaStringa(attori)&&controllaStringa(paese)&&controllaStringa(locandina)) {
      this.ID=id;
      this.Titolo=titolo;
      this.Descrizione=descrizione;
      this.Genere=genere;
      this.Regia=regia;
      this.Attori=attori;
      this.Paese=paese;
      this.Locandina=locandina;
    }
    else
      throw new Exception("Dati del film non corretti o mancanti");
  }

  private static boolean controllaStringa(String daControllare) {
    if ((daControllare!=null && !daControllare.equalsIgnoreCase("")) || daControllare.endsWith("\n"))
      return true;
    else
      return false;
  }

  public int getID() {
    return this.ID;
  }

  public String getTitolo() {
    return this.Titolo;
  }

  public void setTitolo(String titolo) throws Exception{
    if (controllaStringa(titolo))
      Titolo=titolo;
    else
      throw new Exception("Il titolo deve essere specificato");
  }

  public String getDescrizione() {
    return this.Descrizione;
  }

  public void setDescrizione(String descrizione) throws Exception{
    if (controllaStringa(descrizione))
      Descrizione=descrizione;
    else
      throw new Exception("Il titolo deve essere specificato");
  }

  public String getGenere() {
    return this.Genere;
  }

  public void setGenere(String genere) throws Exception{
    if (controllaStringa(genere))
      Genere=genere;
    else
      throw new Exception("Genere non specificato");
  }

  public String getRegia() {
    return this.Regia;
  }

  public void setRegia(String regia) throws Exception{
    if (regia!=null)
      Regia=regia;
    else
      throw new Exception("Errore nel campo regista");
  }

  public String getAttori() {
    return this.Attori;
  }

  public void setAttori(String attori) throws Exception{
    if (controllaStringa(attori))
      Attori=attori;
    else
      throw new Exception("Attori non specificati");
  }

  public String getPaese() {
    return this.Paese;
  }

  public void setPaese(String paese) throws Exception{
    if (controllaStringa(paese))
      Paese=paese;
    else
      throw new Exception("Il paese deve essere specificato");
  }

  public String getLocandina() {
    return this.Locandina;
  }

  public void setLocandina(String percorsoLocandina) throws Exception{
    if (controllaStringa(percorsoLocandina))
      Locandina=percorsoLocandina;
    else
      Locandina="";
  }

  public static Collezione loadFilm(String[] array) throws SQLException,Exception{
    setConnection();
    String query="SELECT * FROM Film";
    boolean bol;
    int primoParametro=-1;
    int numeroParametri=0;
    for (int i=0;i<8;i++) {
      if (i!=0)
        bol=controllaStringa(array[i]);
      else
        bol=Integer.parseInt(array[i])>0;
      if (bol) {
        bol=false;
        numeroParametri++;
        if (primoParametro<0)
          primoParametro=i;
      }
    }
    if (primoParametro>-1) {
      if (primoParametro==0 || primoParametro==7)
        query=query+" WHERE "+nomeDaNumero(primoParametro)+"=?";
      else
        query=query+" WHERE "+nomeDaNumero(primoParametro)+" LIKE ?";
      for (int i = primoParametro+1; i < 8; i++) {
        if (controllaStringa(array[i])) {
          if (i==7)
            query = query + " AND " + nomeDaNumero(i) + "=?";
          else
            query = query + " AND " + nomeDaNumero(i) + " LIKE ?";
        }
      }
    }
    query=query+";";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    int contatore=1;
    if (primoParametro>-1) {
      for (int i = primoParametro; i < 8; i++) {
        if (i != 0 && controllaStringa(array[i])) {
          preparedQuery.setString(contatore, "%"+array[i]+"%");
          contatore++;
        }
        else
          if (i==0) {
            preparedQuery.setInt(contatore, Integer.parseInt(array[i]));
            contatore++;
          }
      }
    }
    ResultSet Risultati=preparedQuery.executeQuery();
    Collezione collezioneDiFilm=new CollezioneArrayList();
    Film nuovo;
    while(Risultati.next()) {
      nuovo=new Film(Risultati.getInt(1),Risultati.getString(2),Risultati.getString(3),Risultati.getString(4),Risultati.getString(5),Risultati.getString(6),Risultati.getString(7),Risultati.getString(8));
      collezioneDiFilm.add(nuovo);
    }
    return collezioneDiFilm;
  }

  public void storageFilm() throws SQLException,Exception{
    setConnection();
    String query="INSERT INTO Film(ID,Titolo,Descrizione,Genere,Regia,Attori,Paese,Locandina) values(?,?,?,?,?,?,?,?);";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setInt(1,this.getID());
    preparedQuery.setString(2,this.getTitolo());
    preparedQuery.setString(3,this.getDescrizione());
    preparedQuery.setString(4,this.getGenere());
    preparedQuery.setString(5,this.getRegia());
    preparedQuery.setString(6,this.getAttori());
    preparedQuery.setString(7,this.getPaese());
    preparedQuery.setString(8,this.getLocandina());
    preparedQuery.execute();
  }

  public void updateFilm() throws SQLException,Exception{
    setConnection();
    String query="UPDATE Film SET Titolo=?, Descrizione=?, Genere=?, Regia=?, Attori=?, Paese=? Locandina=? WHERE Film.ID=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getTitolo());
    preparedQuery.setString(2,this.getDescrizione());
    preparedQuery.setString(3,this.getGenere());
    preparedQuery.setString(4,this.getRegia());
    preparedQuery.setString(5,this.getAttori());
    preparedQuery.setString(6,this.getPaese());
    preparedQuery.setString(7,this.getLocandina());
    preparedQuery.setInt(8,this.getID());
    preparedQuery.execute();
  }

  public void DeleteFilm() throws SQLException,Exception{
    setConnection();
    String query="DELETE FROM Film WHERE Film.ID=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setInt(1,this.getID());
    preparedQuery.execute();
  }

  public void addProiezione(Proiezione p) {
    Proiezioni.add(p);
    p.setFilm(this);
  }

  public void deleteProiezione(Proiezione p) {
    Proiezioni.remove(p);
    p.setFilm(null);
  }

  private static String nomeDaNumero(int i) {
    String ritorno="";
    switch(i) {
      case 0:ritorno="ID";
             break;
      case 1:ritorno="Titolo";
             break;
      case 2:ritorno="Descrizione";
             break;
      case 3:ritorno="Genere";
             break;
      case 4:ritorno="Regia";
             break;
      case 5:ritorno="Attori";
             break;
      case 6:ritorno="Paese";
             break;
    }
    return ritorno;
  }

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

  private int ID;
  private String Titolo;
  private String Descrizione;
  private String Genere;
  private String Regia;
  private String Attori;
  private String Paese;
  private String Locandina;
  private static Connection connessione;
  private CollezioneArrayList Proiezioni;
}
