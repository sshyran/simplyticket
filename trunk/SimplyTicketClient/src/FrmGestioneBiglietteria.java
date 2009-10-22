import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import data_layer.Collezione;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.*;
import java.sql.SQLException;
import java.lang.*;
import java.io.*;


/**
 * <p>Title: Presentation Layer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Squattrinati</p>
 * @author Buonaura, Cannavacciuolo, Memoli, Pappalardo, Pentangelo, Spinelli
 * @version 1.0
 */

public class FrmGestioneBiglietteria extends JFrame implements Runnable {
  static Thread trd=null;
  JPanel pnlIntestazioniTbl = new JPanel();
  JPanel pnlIntestazione = new JPanel();
  FlowLayout flowLayout2 = new FlowLayout();
  JLabel lblGestBigl = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnlSessione = new JPanel();
  JButton btnBack = new JButton();
  JButton btnPosti = new JButton();
  JPanel pnlPrincipale = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JScrollPane ScrollListFilm = new JScrollPane();
  JTable tblListFilm;
  FrmSpettacoli visualPosti= new FrmSpettacoli();
  ControllerBiglietteria controllerBiglietteria;
  String[] array;
  String[] locandine;
  int IDAddetto=0;
  boolean ultimo=false;
  Toolkit kit = Toolkit.getDefaultToolkit();
  Dimension screenSize = kit.getScreenSize(); // get screen dimensions
  int screenHeight = screenSize.height;
  int screenWidth = screenSize.width;
  BorderLayout borderLayout2 = new BorderLayout();
  JLabel lblSala = new JLabel();
  JLabel lblTitolo = new JLabel();
  JLabel lblDisponibilita = new JLabel();


  public FrmGestioneBiglietteria() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void run(){
   while(Thread.currentThread() == trd){
    try{
           trd.sleep(4000);
           controllerBiglietteria.leggiListaSpettacoli();
           this.aggiornaTabella();
           tblListFilm.repaint();
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
   }
  }


  void jbInit() throws Exception {
  if(trd==null){
     trd=new Thread(this);
     trd.start();
  }

   this.setResizable(false);
    this.setTitle("Simply Ticket 1.0 - Scelta Proiezioni della giornata");

// center frame in screen
   setSize(screenWidth / 2, screenHeight / 2);
   setLocation(screenWidth / 4, screenHeight / 4);

    pnlIntestazione.setDoubleBuffered(true);
    lblGestBigl.setFont(new java.awt.Font("Mirror", 1, 24));
    lblGestBigl.setAlignmentX((float) 0.0);
    lblGestBigl.setHorizontalAlignment(SwingConstants.CENTER);
    lblGestBigl.setText("Gestione Biglietteria");
    this.getContentPane().setLayout(borderLayout1);
    pnlIntestazione.setLayout(borderLayout2);
    flowLayout2.setAlignment(FlowLayout.CENTER);
    btnBack.setFont(new java.awt.Font("Mirror", 0, 11));
    btnBack.setText("<<Back");
    btnBack.addActionListener(new FrmGestioneBiglietteria_btnBack_actionAdapter(this));
    btnPosti.setFont(new java.awt.Font("Mirror", 0, 11));
    btnPosti.setText(" Posti ");
    btnPosti.addActionListener(new FrmGestioneBiglietteria_btnPosti_actionAdapter(this));
    pnlPrincipale.setLayout(gridBagLayout1);
    FileReader file=null;
    BufferedReader lettore=null;
    String valore="";
    try {
      file=new FileReader("server");
      lettore=new BufferedReader(file);
      valore=lettore.readLine();
    }
    catch(Exception e) {
      valore="";
      System.out.println("File server not found, lookup on localhost");
    }
    if (valore.length()<1) {
      controllerBiglietteria = (ControllerBiglietteria) Naming.lookup("controllerBiglietteria");
    }
    else {
      controllerBiglietteria = (ControllerBiglietteria) Naming.lookup("//"+valore+"/controllerBiglietteria");
    }
    pnlIntestazioniTbl.setPreferredSize(new Dimension(10, 20));
    lblSala.setPreferredSize(new Dimension(140, 15));
    lblSala.setHorizontalAlignment(SwingConstants.CENTER);
    lblSala.setFont(new java.awt.Font("Mirror", 3, 11));
    lblSala.setText("Sala");
    lblSala.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
    lblSala.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
    lblTitolo.setPreferredSize(new Dimension(140, 15));
    lblTitolo.setHorizontalAlignment(SwingConstants.CENTER);
    lblTitolo.setFont(new java.awt.Font("Mirror", 3, 11));
    lblTitolo.setText("Titolo");
    lblTitolo.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
    lblDisponibilita.setPreferredSize(new Dimension(140, 15));
    lblDisponibilita.setHorizontalAlignment(SwingConstants.CENTER);
    lblDisponibilita.setFont(new java.awt.Font("Mirror", 3, 11));
    lblDisponibilita.setText("Disponibilit�");
    lblDisponibilita.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
    pnlIntestazioniTbl.add(lblSala);
    pnlIntestazioniTbl.add(lblTitolo);
    pnlIntestazioniTbl.add(lblDisponibilita);
    pnlIntestazione.add(pnlIntestazioniTbl, BorderLayout.SOUTH);
    ScrollListFilm.setAutoscrolls(true);
    this.getContentPane().add(pnlIntestazione, BorderLayout.NORTH);
    pnlIntestazione.add(lblGestBigl, null);
    this.getContentPane().add(pnlSessione,  BorderLayout.SOUTH);
    pnlSessione.add(btnBack, null);
    pnlSessione.add(btnPosti, null);
    this.getContentPane().add(pnlPrincipale,  BorderLayout.CENTER);
    pnlPrincipale.add(ScrollListFilm,              new GridBagConstraints(1, 0, GridBagConstraints.REMAINDER, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 350, 200));
    this.aggiornaTabella();
  }

  void aggiornaTabella () {
    Collezione listaProiezioni=null;
    try{
      listaProiezioni = controllerBiglietteria.leggiListaSpettacoli();
    }
    catch(Exception e){
       e.printStackTrace();
    }
    if (listaProiezioni.size()==1)
      ultimo=true;
    else
      ultimo=false;
    tblListFilm=new JTable(listaProiezioni.size(),3);
    tblListFilm.setTableHeader(null);
    tblListFilm.setDoubleBuffered(false);
    tblListFilm.setCellSelectionEnabled(false);
    tblListFilm.setRowSelectionAllowed(true);
    tblListFilm.setEnabled(true);
    tblListFilm.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    tblListFilm.setFont(new java.awt.Font("Mirror", 0, 11));
    array=new String[listaProiezioni.size()];
    locandine=new String[listaProiezioni.size()];
    String id="";
    for (int i=0;i<listaProiezioni.size();i++) {
      try {
        id = ( (String[]) listaProiezioni.getIndex(i))[0];
        //tblListFilm.getModel().setValueAt(id, i, 0);
        tblListFilm.getModel().setValueAt( ( ( (String[]) listaProiezioni.
                                              getIndex(i))[1]), i, 0);
        tblListFilm.getModel().setValueAt( ( ( (String[]) listaProiezioni.
                                              getIndex(i))[2]), i, 1);
        tblListFilm.getModel().setValueAt( ( ( (String[]) listaProiezioni.
                                              getIndex(i))[3]), i, 2);
        array[i] = id;
        locandine[i]=((String[])listaProiezioni.getIndex(i))[4];
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    ScrollListFilm.getViewport().add(tblListFilm, null);
  }

//Al clic sul bottone Back si chiude tale frame e si torna a quella precedente
  void btnBack_actionPerformed(ActionEvent e) {
  this.setVisible(false);
  }

//Al clic sul bottone Posti si richiama il frame FrmSpettacoli
  void btnPosti_actionPerformed(ActionEvent e) {
    if ((tblListFilm.getSelectedRow()>-1) /*|| ultimo*/) {
      /*if (ultimo)
        visualPosti.settaProiezione(array[0],controllerBiglietteria,IDAddetto,locandine[0]);
      else*/
      visualPosti.setSize(screenSize);
      visualPosti.settaProiezione(array[tblListFilm.getSelectedRow()],controllerBiglietteria,IDAddetto,locandine[tblListFilm.getSelectedRow()]);
      visualPosti.setVisible(true);
    }
    else {
      JOptionPane.showMessageDialog(null," Non hai selezionato la proiezione","Errore",JOptionPane.ERROR_MESSAGE);
    }
  }

  void setProiezione(int addetto) {
    IDAddetto=addetto;
  }
}

class FrmGestioneBiglietteria_btnBack_actionAdapter implements java.awt.event.ActionListener {
  FrmGestioneBiglietteria adaptee;

  FrmGestioneBiglietteria_btnBack_actionAdapter(FrmGestioneBiglietteria adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnBack_actionPerformed(e);
  }
}

class FrmGestioneBiglietteria_btnPosti_actionAdapter implements java.awt.event.ActionListener {
  FrmGestioneBiglietteria adaptee;

  FrmGestioneBiglietteria_btnPosti_actionAdapter(FrmGestioneBiglietteria adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnPosti_actionPerformed(e);
  }

}
