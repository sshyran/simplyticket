import javax.swing.*;
import java.awt.*;
import data_layer.*;
import java.awt.event.*;
import java.lang.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class FrmSpettacoli extends JFrame implements Runnable{
  static Thread trd;
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnlPrincipale = new JPanel();
  JPanel pnlOpzioni = new JPanel();
  JPanel pnlSala = new JPanel();
  JPanel pnlControllo = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton btnTrovaPosti= new JButton();
  JButton btnAcquista = new JButton();
  JButton btnRimborsa = new JButton();
  JPanel pnlCosto = new JPanel();
  JPanel pnlLocandina = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JLabel lblCostoIntero = new JLabel();
  JTextField txtCostoIntero = new JTextField();
  JLabel lblCostoRidotto = new JLabel();
  JTextField txtCostoRidotto = new JTextField();
  ButtonGroup buttonGroup1 = new ButtonGroup();
  JRadioButton rdCostoIntero = new JRadioButton();
  JRadioButton rdCostoRidotto = new JRadioButton();
  Image immagine;
  Icon icona;
  JLabel lblImage = new JLabel();
  BorderLayout borderLayout3 = new BorderLayout();
  Collezione listaPoltrone=null;
  String IDProiezione="";
  String Locandina="C:"+"\\"+"SimplyTicket"+"\\"+"locandina.jpg";
  ControllerBiglietteria controllerBiglietteria;
  int IDAddetto;
  int dimensioneSala=0;
  int lunghezzaFila=0;
  int IDFila=0;
  static String[] alfabeto=new String[26];
  JPanel pnlDx= new JPanel();
  JPanel pnlSx= new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JComboBox cbFila = new JComboBox();
  JComboBox cbPosto = new JComboBox();
  FrmSpettacoli_btnAcquista_actionAdapter listenerAcquista=new FrmSpettacoli_btnAcquista_actionAdapter(this);
  FrmSpettacoli_btnRimborsa_actionAdapter listenerRimborsa=new FrmSpettacoli_btnRimborsa_actionAdapter(this);

  public FrmSpettacoli() {
    /*try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }*/
  }

  private void inizializzaAlfabeto() {
    alfabeto[0]="a";
    alfabeto[1]="b";
    alfabeto[2]="c";
    alfabeto[3]="d";
    alfabeto[4]="e";
    alfabeto[5]="f";
    alfabeto[6]="g";
    alfabeto[7]="h";
    alfabeto[8]="i";
    alfabeto[9]="j";
    alfabeto[10]="k";
    alfabeto[11]="l";
    alfabeto[12]="m";
    alfabeto[13]="n";
    alfabeto[14]="o";
    alfabeto[15]="p";
    alfabeto[16]="q";
    alfabeto[17]="r";
    alfabeto[18]="s";
    alfabeto[19]="t";
    alfabeto[20]="u";
    alfabeto[21]="v";
    alfabeto[22]="w";
    alfabeto[23]="x";
    alfabeto[24]="y";
    alfabeto[25]="z";
  }

  public void settaProiezione(String idProiezione,ControllerBiglietteria controllerbiglietteria,int addetto,String locandina) {
    pnlDx.removeAll();
    pnlSx.removeAll();
    dimensioneSala=0;
    lunghezzaFila=0;
    IDFila=0;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    IDProiezione=idProiezione;
    controllerBiglietteria=controllerbiglietteria;
    IDAddetto=addetto;
    Locandina=locandina;
    immagine = Toolkit.getDefaultToolkit().getImage(locandina);
    immagine=immagine.getScaledInstance(180,240,3);
    icona=new ImageIcon(immagine);
    lblImage.setIcon(icona);
    pnlLocandina.add(lblImage,  BorderLayout.CENTER);
    IDFila=0;
    String[] poltrona=null;
    this.leggiValoriPosti();
    dimensioneSala=listaPoltrone.size();
    try {
         poltrona=((String[])listaPoltrone.getIndex(0));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    IDFila=Integer.parseInt(poltrona[1]);
    boolean finito=false;
    for(int i=0;!finito;i++){
       try {
         poltrona = (String[]) listaPoltrone.getIndex(i);
       }
       catch (Exception e) {
         e.printStackTrace();
       }
       if(Integer.parseInt(poltrona[1])==IDFila)
          lunghezzaFila++;
        else
          finito=true;
    }
    int numeroFile=listaPoltrone.size()/lunghezzaFila;
    this.visualizzaPosti(lunghezzaFila,numeroFile);
    pnlDx.setSize(55*lunghezzaFila, 55*numeroFile);
    pnlSx.setSize(55*lunghezzaFila, 55*numeroFile);
    cbFila.removeAllItems();
    for (int i=0;i<numeroFile;i++) {
        cbFila.addItem(""+generaLettera(i));
    }
    cbPosto.setVisible(false);
    if(trd == null){
    trd = new Thread(this);
    trd.start();
   }
  }

  private void leggiValoriPosti() {
    try
    {
      listaPoltrone = controllerBiglietteria.leggiPosti(IDProiezione,-1,-1);
    }
    catch(Exception e){
      System.out.println("Errore in frmSala:");
      e.printStackTrace();
    }
  }

  private void visualizzaPosti(int lunghezzaFila,int numeroFile) {
    Poltrona oggettoPoltrona=null;
    boolean stato=false;
    for (int i=0;i<numeroFile;i++) {
      pnlDx.add(new JLabel(this.generaLettera(i)));
      pnlSx.add(new JLabel(this.generaLettera(i)));
      for (int j=0;j<lunghezzaFila;j++) {
        try {
          if (((String[]) listaPoltrone.getIndex((i*lunghezzaFila)+j))[2].equals("TRUE")) {
              //serve per costruire l'indice della poltrona all�internodella collezione
            stato = true;
          }
          else
            stato=false;
          oggettoPoltrona = new Poltrona(!stato);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        if (j<lunghezzaFila/2) {
          pnlSx.add(oggettoPoltrona);
        }
        else {
          pnlDx.add(oggettoPoltrona);
        }
      }
    }
  }

  private static String generaLettera(int fila) {
    return alfabeto[fila];
  }

  public void run(){
    while (Thread.currentThread() == trd) {
      try {
        trd.sleep(2000);
        this.leggiValoriPosti();
        pnlDx.removeAll();
        pnlSx.removeAll();
        int x=0;
        try {
          x=listaPoltrone.size()/lunghezzaFila;
        }
        catch (Exception e) {
          System.out.println("LUMNGHEZZAADASDASD �: "+x);
        }
        this.visualizzaPosti(lunghezzaFila,x);
        pnlDx.setVisible(true);
        pnlSx.setVisible(true);
        /*if (cbFila.getSelectedIndex()>-1) {
      int x=0;
      cbPosto.removeAllItems();
      for (int i=0;i<lunghezzaFila;i++) {
        x=(cbFila.getSelectedIndex()*lunghezzaFila)+i+1;
        cbPosto.addItem(""+x);
      }*/
      if (cbPosto.isVisible()) {
        cbPosto.setVisible(false);
        cbPosto.setVisible(true);
      }
      else {
        cbPosto.setVisible(true);
        cbPosto.setVisible(false);
      }
      }
      catch (Exception e) {
        System.out.println("Errore Thread: ");
        e.printStackTrace();
      }
    }
  }

  private void jbInit() throws Exception {
    this.inizializzaAlfabeto();
    this.setTitle("Simply Ticket 1.0 - Acquisto/Rimborso biglietti");
    this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    //this.setSize(new Dimension(800,600));
    //this.setLocation(120,100);
    this.setResizable(false);
    this.addComponentListener(new FrmSpettacoli_this_componentAdapter(this));
    this.getContentPane().setLayout(borderLayout1);
    pnlControllo.setLayout(gridBagLayout1);
    btnAcquista.setFont(new java.awt.Font("Mirror", 1, 11));
    btnAcquista.setMaximumSize(new Dimension(135, 23));
    btnAcquista.setMinimumSize(new Dimension(135, 23));
    btnAcquista.setPreferredSize(new Dimension(135, 23));
    btnAcquista.setText("Acquista");
    btnAcquista.addActionListener(listenerAcquista);
    btnRimborsa.setFont(new java.awt.Font("Mirror", 1, 11));
    btnRimborsa.setMaximumSize(new Dimension(135, 23));
    btnRimborsa.setMinimumSize(new Dimension(135, 23));
    btnRimborsa.setPreferredSize(new Dimension(135, 23));
    btnRimborsa.setToolTipText("");
    btnRimborsa.setActionCommand("jButton2");
    btnRimborsa.setText("Rimborsa");
    btnRimborsa.addActionListener(listenerRimborsa);
    pnlOpzioni.setLayout(borderLayout2);
    pnlCosto.setLayout(gridBagLayout2);
    lblCostoIntero.setFont(new java.awt.Font("Mirror", 0, 11));
    lblCostoIntero.setText("CostoIntero");
    txtCostoIntero.setFont(new java.awt.Font("Mirror", 0, 11));
    txtCostoIntero.setEditable(false);
    txtCostoIntero.setText("5.0");
    lblCostoRidotto.setFont(new java.awt.Font("Mirror", 0, 11));
    lblCostoRidotto.setText("CostoRidotto");
    txtCostoRidotto.setFont(new java.awt.Font("Mirror", 0, 11));
    txtCostoRidotto.setToolTipText("");
    txtCostoRidotto.setEditable(false);
    txtCostoRidotto.setText("3.5");
    rdCostoIntero.setBackground(Color.lightGray);
    rdCostoIntero.setFont(new java.awt.Font("Mirror", 0, 11));
    rdCostoIntero.setSelected(true);
    rdCostoIntero.setText("CostoIntero");
    rdCostoRidotto.setBackground(Color.lightGray);
    rdCostoRidotto.setFont(new java.awt.Font("Mirror", 0, 11));
    rdCostoRidotto.setText("CostoRidotto");
    lblImage.setText("");
    pnlLocandina.setLayout(borderLayout3);
    pnlSala.setLayout(gridLayout1);
    pnlSala.setBackground(Color.orange);
    pnlOpzioni.setBackground(Color.lightGray);
    pnlCosto.setBackground(Color.lightGray);
    pnlControllo.setBackground(Color.gray);
    pnlControllo.setDebugGraphicsOptions(0);
    pnlDx.setBackground(new Color(153, 0, 51));
    pnlSx.setBackground(new Color(153, 0, 51));
    gridLayout1.setColumns(4);
    gridLayout1.setHgap(10);
    gridLayout1.setVgap(20);
    cbFila.setFont(new java.awt.Font("Mirror", 0, 11));
    cbFila.setAutoscrolls(true);
    cbFila.addItemListener(new FrmSpettacoli_cbFila_itemAdapter(this));
    cbPosto.setFont(new java.awt.Font("Mirror", 0, 11));
    cbPosto.setAutoscrolls(true);
    btnTrovaPosti.setFont(new java.awt.Font("Mirror", 1, 11));
    btnTrovaPosti.setMaximumSize(new Dimension(135, 23));
    btnTrovaPosti.setMinimumSize(new Dimension(135, 23));
    btnTrovaPosti.setPreferredSize(new Dimension(135, 23));
    btnTrovaPosti.setToolTipText("");
    btnTrovaPosti.setText("Trova Posti");
    btnTrovaPosti.addActionListener(new FrmSpettacoli_btnTrovaPosti_actionAdapter(this));
    this.getContentPane().add(pnlPrincipale, BorderLayout.EAST);
    this.getContentPane().add(pnlOpzioni,  BorderLayout.WEST);
    pnlOpzioni.add(pnlCosto, BorderLayout.SOUTH);
    pnlCosto.add(txtCostoRidotto,                                             new GridBagConstraints(2, 2, 2, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnlCosto.add(rdCostoRidotto,                   new GridBagConstraints(0, 5, 3, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 91), 0, 0));
    pnlCosto.add(lblCostoIntero,          new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    pnlCosto.add(txtCostoIntero,       new GridBagConstraints(2, 0, 1, 3, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnlCosto.add(lblCostoRidotto,      new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    pnlCosto.add(rdCostoIntero,           new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 98), 0, 0));
    pnlCosto.add(cbFila,      new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    pnlCosto.add(cbPosto,      new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    pnlOpzioni.add(pnlLocandina, BorderLayout.NORTH);
    this.getContentPane().add(pnlSala, BorderLayout.CENTER);
    pnlSala.add(pnlSx, null);
    pnlSala.add(pnlDx, null);
    this.getContentPane().add(pnlControllo,  BorderLayout.SOUTH);
    pnlControllo.add(btnTrovaPosti,    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnlControllo.add(btnAcquista,    new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnlControllo.add(btnRimborsa,  new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    buttonGroup1.add(rdCostoIntero);
    buttonGroup1.add(rdCostoRidotto);
  }

  void this_componentHidden(ComponentEvent e) {
    try {
    /*pnlDx.removeAll();
    pnlSx.removeAll();*/
    pnlCosto.removeAll();
    /*dimensioneSala=0;
    lunghezzaFila=0;
    IDFila=0;*/
    btnAcquista.removeActionListener(listenerAcquista);
    btnRimborsa.removeActionListener(listenerRimborsa);
    //trd.destroy();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  void btnTrovaPosti_actionPerformed(ActionEvent e) {
    if (cbFila.getSelectedIndex()>-1) {
      int x=0;
      cbPosto.removeAllItems();
      for (int i=0;i<lunghezzaFila;i++) {
        x=(cbFila.getSelectedIndex()*lunghezzaFila)+i+1;
        cbPosto.addItem(""+x);
      }
      cbPosto.setVisible(true);
    }
    else
      JOptionPane.showMessageDialog(null,"            Non hai selezionato nessuna fila","Errore",JOptionPane.ERROR_MESSAGE);
  }

  void btnAcquista_actionPerformed(ActionEvent e) {
    if ((cbFila.getSelectedIndex()>-1) && (cbPosto.getSelectedIndex()>-1)) {
      int fila=cbFila.getSelectedIndex()+IDFila;
      int posto=cbPosto.getSelectedIndex()+1;
      Collezione postiRitornati=null;
      try {
        postiRitornati=controllerBiglietteria.leggiPosti(IDProiezione,fila,posto);
      }
      catch (Exception e1) {
        System.out.println("controlloposto");
        e1.printStackTrace();
      }
      String[] postoSuCuiLavorare=null;
      try {
        postoSuCuiLavorare = ( (String[]) postiRitornati.getIndex(0));
      }
      catch (Exception e2)       {
        e2.printStackTrace();
      }
      if (postoSuCuiLavorare[2].equals("TRUE")) {
        JOptionPane.showMessageDialog(null,"            PostoOccupato","Errore",JOptionPane.ERROR_MESSAGE);
      }
      else {
        double intero=Double.parseDouble(txtCostoIntero.getText());
        double ridotto=Double.parseDouble(txtCostoRidotto.getText());
        if (rdCostoIntero.isSelected()) {
          ridotto=0;
        }
        else {
          intero=0;
        }
        try {
          controllerBiglietteria.emettiTicket(IDProiezione, fila, posto, "no",intero,ridotto,IDAddetto);
        }
        catch (Exception ex) {
          System.out.println("Errore Emissione Ticket" + ex.getMessage());
          ex.printStackTrace();
        }
      }
    }
    else
      JOptionPane.showMessageDialog(null,"            Non hai selezionato un posto","Errore",JOptionPane.ERROR_MESSAGE);

  }

  void btnRimborsa_actionPerformed(ActionEvent e) {
    if ((cbFila.getSelectedIndex()>-1) && (cbPosto.getSelectedIndex()>-1)) {
      int fila=cbFila.getSelectedIndex()+IDFila;
      int posto=cbPosto.getSelectedIndex()+1;
      Collezione postiRitornati=null;
      try {
        postiRitornati=controllerBiglietteria.leggiPosti(IDProiezione,fila,posto);
      }
      catch (Exception e1) {
        System.out.println("controlloposto");
        e1.printStackTrace();
      }
      String[] postoSuCuiLavorare=null;
      try {
        postoSuCuiLavorare = ( (String[]) postiRitornati.getIndex(0));
      }
      catch (Exception e2)       {
        e2.printStackTrace();
      }
      if (postoSuCuiLavorare[2].equals("FALSE")) {
        JOptionPane.showMessageDialog(null,"            PostoLibero","Errore",JOptionPane.ERROR_MESSAGE);
      }
      else {
        try {
          controllerBiglietteria.annullaTicket(IDProiezione, posto, fila, "no");
        }
        catch (Exception ex) {
          JOptionPane.showMessageDialog(null,"            Ormai � tardi, dovevi venire prima.","Errore",JOptionPane.ERROR_MESSAGE);
        }
      }
    }
    else
      JOptionPane.showMessageDialog(null,"            Non hai selezionato un posto","Errore",JOptionPane.ERROR_MESSAGE);
  }

  void cbFila_itemStateChanged(ItemEvent e) {
    cbPosto.setVisible(false);
  }

}

class FrmSpettacoli_this_componentAdapter extends java.awt.event.ComponentAdapter {
  FrmSpettacoli adaptee;

  FrmSpettacoli_this_componentAdapter(FrmSpettacoli adaptee) {
    this.adaptee = adaptee;
  }
  public void componentHidden(ComponentEvent e) {
    adaptee.this_componentHidden(e);
  }
}

class FrmSpettacoli_btnTrovaPosti_actionAdapter implements java.awt.event.ActionListener {
  FrmSpettacoli adaptee;

  FrmSpettacoli_btnTrovaPosti_actionAdapter(FrmSpettacoli adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnTrovaPosti_actionPerformed(e);
  }
}

class FrmSpettacoli_btnAcquista_actionAdapter implements java.awt.event.ActionListener {
  FrmSpettacoli adaptee;

  FrmSpettacoli_btnAcquista_actionAdapter(FrmSpettacoli adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnAcquista_actionPerformed(e);
  }
}

class FrmSpettacoli_btnRimborsa_actionAdapter implements java.awt.event.ActionListener {
  FrmSpettacoli adaptee;

  FrmSpettacoli_btnRimborsa_actionAdapter(FrmSpettacoli adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnRimborsa_actionPerformed(e);
  }
}

class FrmSpettacoli_cbFila_itemAdapter implements java.awt.event.ItemListener {
  FrmSpettacoli adaptee;

  FrmSpettacoli_cbFila_itemAdapter(FrmSpettacoli adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.cbFila_itemStateChanged(e);
  }
}
