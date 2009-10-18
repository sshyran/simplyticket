/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data_layer;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author William The Bloody
 */
public class Data_Layer_Connector {

    private static Connection connessione;

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

}
