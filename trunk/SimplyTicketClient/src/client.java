import data_layer.*;
import java.rmi.server.*;
import java.rmi.*;
import java.sql.SQLException;
import java.net.MalformedURLException;
import java.io.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class client {

  public static void main(String[] args) throws RemoteException,NotBoundException,MalformedURLException{
    control=(ControllerLogin)Naming.lookup("controllerLogin");
    InputStreamReader isr= new InputStreamReader(System.in);
    BufferedReader brkeyb = new BufferedReader(isr);
    System.out.print("Digita la username: ");
    String user="";
    String pass="";
    boolean bigliettaio=false;
    try {
      user=brkeyb.readLine();
    }
    catch(IOException e) {
      System.out.println(e.getMessage());
    }
    System.out.print("Digita la password: ");
    try {
      pass=brkeyb.readLine();
    }
    catch(IOException e) {
      System.out.println(e.getMessage());
    }
    System.out.print("é un bigliettaio(s/n): ");
    String big="";
    try {
      big=brkeyb.readLine();
    }
    catch(IOException e) {
      big="s";
    }
    if (big.equalsIgnoreCase("s"))
      bigliettaio=true;
    else
      bigliettaio=false;
    System.out.println(user+"   "+pass+"   "+bigliettaio);
    try {
      brkeyb.read();
    }
    catch (IOException e) {

    }
    if (control.verifica(bigliettaio,user,pass)>-1)
      System.out.println("login effettuato con successo");
    else
      System.out.println("login fallito");
  }

  private static ControllerLogin control;
}
