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

public class Fila implements Serializable{
  public Fila(int id,String numero,int dimensione,String sala) throws Exception{
    if (id>0 && controllaStringa(numero) && dimensione>0 && controllaStringa(sala)) {
      this.ID=id;
      this.NumeroFila=numero;
      this.DimensioneFila=dimensione;
      this.IDSala=sala;
    }
    else
      throw new Exception("Dai per la fila non validi");
  }

  private static boolean controllaStringa(String daControllare) {
    if ((daControllare!=null && !daControllare.equalsIgnoreCase("")))
      return true;
    else
      return false;
  }

  public int getID() {
    return this.ID;
  }

  public String getNumeroFila() {
    return this.NumeroFila;
  }

  public void setNumeroFila(String fila) throws Exception{
    if (controllaStringa(fila))
      this.NumeroFila=fila;
    else
      throw new Exception("Bisogna specificare una lettera per la fila");
  }

  public int getDimensioneFila() {
    return this.DimensioneFila;
  }

  public void setDimensioneFila(int dimensione) throws Exception{
    if (dimensione>0)
      this.DimensioneFila=dimensione;
    else
      throw new Exception("Bisogna specificare la dimensione per la fila");
  }

  public String getIDSala() {
    return this.IDSala;
  }

  public void setIDSala(String sala) throws Exception{
    if (controllaStringa(sala))
      this.IDSala=sala;
    else
      throw new Exception("Bisogna specificare la sala d'appartenenza alle fila");
  }

  public static Collezione loadFila(int id,String numeroFila,int dimensioneFila,String sala) throws SQLException,Exception{
    setConnection();
    String query="SELECT * FROM Fila";
    if (id>0) {
      query=query+" WHERE ID=?";
      if (controllaStringa(numeroFila)) {
        query=query+" AND NumeroFila= ?";
        if (dimensioneFila>0) {
          query=query+" AND DimensioneFila= ?";
          if (controllaStringa(sala))
            query=query+" AND IDSala= ?";
        }
        else {
          if (controllaStringa(sala))
            query = query + " AND IDSala= ?";
        }
      }
      else {
        if (dimensioneFila > 0) {
          query = query + " AND DimensioneFila= ?";
          if (controllaStringa(sala))
            query = query + " AND IDSala= ?";
        }
        else {
          if (controllaStringa(sala))
            query = query + " AND IDSala= ?";
        }
      }
    }
    else {
      if (controllaStringa(numeroFila)) {
        query = query + " WHERE NumeroFila= ?";
        if (dimensioneFila > 0) {
          query = query + " AND DimensioneFila= ?";
          if (controllaStringa(sala))
            query = query + " AND IDSala= ?";
        }
        else {
          if (controllaStringa(sala))
            query = query + " AND IDSala= ?";
        }
      }
      else {
        if (dimensioneFila > 0) {
          query = query + " WHERE DimensioneFila= ?";
          if (controllaStringa(sala))
            query = query + " AND IDSala= ?";
        }
        else {
          if (controllaStringa(sala))
            query = query + " WHERE IDSala= ?";
        }
      }
    }
    query=query+";";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    if (id>0) {
      preparedQuery.setInt(1,id);
      if (controllaStringa(numeroFila)) {
        preparedQuery.setString(2,numeroFila);
        if (dimensioneFila>0) {
          preparedQuery.setInt(3,dimensioneFila);
          if (controllaStringa(sala))
            preparedQuery.setString(4,sala);
        }
        else {
          if (controllaStringa(sala))
            preparedQuery.setString(3,sala);
        }
      }
      else {
        if (dimensioneFila > 0) {
          preparedQuery.setInt(2,dimensioneFila);
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
      if (controllaStringa(numeroFila)) {
        preparedQuery.setString(1,numeroFila);
        if (dimensioneFila > 0) {
          preparedQuery.setInt(2,dimensioneFila);
          if (controllaStringa(sala))
            preparedQuery.setString(3,sala);
        }
        else {
          if (controllaStringa(sala))
            preparedQuery.setString(2,sala);
        }
      }
      else {
        if (dimensioneFila > 0) {
          preparedQuery.setInt(1,dimensioneFila);
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
    Collezione collezioneDiFila=new CollezioneArrayList();
    Fila nuova;
    int uno=0;
    String due="";
    int tre=0;
    String quattro="";
    while(Risultati.next()) {
      uno=Risultati.getInt("ID");
      due=Risultati.getString(2);
      tre=Risultati.getInt(3);
      quattro=Risultati.getString(4);
      nuova=new Fila(uno,due,tre,quattro);
      collezioneDiFila.add(nuova);
    }
    return collezioneDiFila;
  }

  public void storageFila() throws SQLException,Exception{
    setConnection();
    String query="INSERT INTO Fila(NumeroFila,DimensioneFila,IDSala) values(?,?,?);";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getNumeroFila());
    preparedQuery.setInt(2,this.getDimensioneFila());
    preparedQuery.setString(3,this.getIDSala());
    preparedQuery.execute();
  }

  public void updateFila() throws SQLException,Exception{
    setConnection();
    String query="UPDATE Fila SET NumeroFila=?, DimensioneFila=?, IDSala=? WHERE Fila.ID=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setString(1,this.getNumeroFila());
    preparedQuery.setInt(2,this.getDimensioneFila());
    preparedQuery.setString(3,this.getIDSala());
    preparedQuery.setInt(4,this.getID());
    preparedQuery.execute();
  }

  public void DeleteFila() throws SQLException,Exception{
    setConnection();
    String query="DELETE FROM Fila WHERE Fila.ID=?;";
    PreparedStatement preparedQuery=connessione.prepareStatement(query);
    preparedQuery.setInt(1,this.getID());
    preparedQuery.execute();
  }

  public void setSala(Sala s) {
    if (sala!=s) {
      Sala old=sala;
      sala=s;
      if (sala!=null)
        sala.addFila(this);
      if (old!=null)
        old.deleteFila(this);
    }
  }

  public void addPosto(Posto p) {
    Posti.add(p);
    p.setFila(this);
  }

  public void deletePosto(Posto p) {
    Posti.remove(p);
    p.setFila(null);
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
  private String NumeroFila;
  private int DimensioneFila;
  private String IDSala;
  private static Connection connessione;
  private Sala sala;
  private CollezioneArrayList Posti;
}
