import javax.swing.*;
import java.awt.Dimension;
import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Poltrona extends JButton{
  private boolean stato;
  public  boolean selezionato;

  private static ImageIcon imgGreen= new ImageIcon("C:\\SimplyTicket\\verde.gif");
  private static ImageIcon imgRed= new ImageIcon("C:\\SimplyTicket\\red.jpg");
  private static ImageIcon imgGreenS=new ImageIcon("C:\\SimplyTicket\\verdeS.jpg");
  private static ImageIcon imgRedS=new ImageIcon("C:\\SimplyTicket\\redS.jpg");


  public void setLibero() {
    this.setIcon(imgGreen);
  }
  public void setOccupato() {
     this.setIcon(imgRed);
  }
  public void selezionaLibero() {
       this.setIcon(imgGreenS);
  }
  public void selezionaOccupato() {
       this.setIcon(imgRedS);
  }

 public boolean GetStato(){
       return stato;
 }

 public void SetStato(boolean st){
        this.stato=st;
         if(st==true)
             this.setIcon(imgGreen);
         else
             this.setIcon(imgRed);
  }

  public Poltrona(boolean val) {
    try {
      jbInit(val);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit(boolean val) throws Exception {
    this.setMaximumSize(new Dimension(50, 50));
    this.setMinimumSize(new Dimension(30, 25));
    this.setPreferredSize(new Dimension(34,29));
    stato=val;
    selezionato=false;
    if(stato==true){
    this.setIcon(imgGreen);
    }else{
    this.setIcon(imgRed);
    }

  }

}
