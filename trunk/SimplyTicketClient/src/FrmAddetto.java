import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * <p>Title: Presentation Layer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Squattrinati</p>
 * @author Buonaura, Cannavacciuolo, Memoli, Pappalardo, Pentangelo, Spinelli
 * @version 1.0
 */

public class FrmAddetto extends JFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnlPrincipale = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel pnlSessione = new JPanel();
  JButton btnExit = new JButton();
  JButton btnLogin = new JButton();
  JButton btnGestBigl = new JButton();
  JButton btnGestAbb = new JButton();
  JPanel pnlIntestazione = new JPanel();
  JLabel lblIntestazione = new JLabel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JButton btnInfo = new JButton();
  JPanel pnlSx = new JPanel();
  JPanel pnlDx = new JPanel();
  FrmInfo info=new FrmInfo();
  FrmLogin login=new FrmLogin();
  FrmGestioneAbbonamenti gestAbb = new FrmGestioneAbbonamenti();
  FrmGestioneBiglietteria gestBigl = new FrmGestioneBiglietteria();
  JButton btnLogOut = new JButton();
  int idaddetto=-1;
  TitledBorder titledBorder1;
  TitledBorder titledBorder2;
  TitledBorder titledBorder3;
  TitledBorder titledBorder4;
  TitledBorder titledBorder5;


  public FrmAddetto() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize(); // get screen dimensions
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;

// center frame in screen
    titledBorder1 = new TitledBorder("");
    titledBorder2 = new TitledBorder("");
    titledBorder3 = new TitledBorder("");
    titledBorder4 = new TitledBorder("");
    titledBorder5 = new TitledBorder("");
    setSize(screenWidth / 2, screenHeight / 2);
    setLocation(screenWidth / 4, screenHeight / 4);



    btnGestAbb.setEnabled(false);
    btnGestAbb.setFont(new java.awt.Font("Mirror", 0, 11));
    btnGestAbb.setBorder(titledBorder3);
    btnGestAbb.setMaximumSize(new Dimension(145, 25));
    btnGestAbb.setMinimumSize(new Dimension(145, 25));
    btnGestAbb.setPreferredSize(new Dimension(160, 30));
    btnGestAbb.setToolTipText("Permette di accedere alle funzionalit� per la gestione degli abbonamenti");
    btnGestAbb.setText("Gestione Abbonamenti");
    btnGestAbb.addActionListener(new FrmAddetto_btnGestAbb_actionAdapter(this));
    btnGestBigl.setEnabled(false);
    btnGestBigl.setFont(new java.awt.Font("Mirror", 0, 11));
    btnGestBigl.setBorder(titledBorder5);
    btnGestBigl.setMaximumSize(new Dimension(145, 25));
    btnGestBigl.setMinimumSize(new Dimension(145, 25));
    btnGestBigl.setPreferredSize(new Dimension(160, 30));
    btnGestBigl.setToolTipText("Permette di accedere alle funzionalità per la gestione della biglietteria");
    btnGestBigl.setHorizontalTextPosition(SwingConstants.LEADING);
    btnGestBigl.setText("Gestione Biglietteria");
    btnGestBigl.addActionListener(new FrmAddetto_btnGestBigl_actionAdapter(this));
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setTitle("Simply Ticket 1.0");
    this.getContentPane().setLayout(borderLayout1);
    btnLogOut.setEnabled(false);
    btnLogOut.setFont(new java.awt.Font("Mirror", 0, 11));
    btnLogOut.setMaximumSize(new Dimension(90, 21));
    btnLogOut.setMinimumSize(new Dimension(90, 21));
    btnLogOut.setPreferredSize(new Dimension(90, 21));
    pnlPrincipale.setLayout(gridBagLayout1);
    pnlSessione.setLayout(gridBagLayout2);
    btnExit.setFont(new java.awt.Font("Mirror", 0, 11));
    btnExit.setMaximumSize(new Dimension(90, 21));
    btnExit.setMinimumSize(new Dimension(90, 21));
    btnExit.setPreferredSize(new Dimension(90, 21));
    btnExit.setText(" Exit");
    btnExit.addActionListener(new FrmAddetto_btnExit_actionAdapter(this));
    btnLogin.setFont(new java.awt.Font("Mirror", 0, 11));
    btnLogin.setMaximumSize(new Dimension(81, 21));
    btnLogin.setMinimumSize(new Dimension(81, 21));
    btnLogin.setPreferredSize(new Dimension(81, 21));
    btnLogin.setText("Login");
    btnLogin.addActionListener(new FrmAddetto_btnLogin_actionAdapter(this));
    lblIntestazione.setFont(new java.awt.Font("Mirror", 1, 24));
    lblIntestazione.setText("Biglietteria");
    pnlIntestazione.setBackground(Color.gray);
    pnlIntestazione.setForeground(Color.black);
    pnlSessione.setBackground(Color.gray);
    btnInfo.setText(" Info");
    btnInfo.addActionListener(new FrmAddetto_btnInfo_actionAdapter(this));
    btnInfo.setPreferredSize(new Dimension(81, 21));
    btnInfo.setMinimumSize(new Dimension(81, 21));
    btnInfo.setFont(new java.awt.Font("Mirror", 0, 11));
    btnInfo.setMaximumSize(new Dimension(81, 21));
    btnLogOut.setText("Log Out");
    btnLogOut.addActionListener(new FrmAddetto_btnLogOut_actionAdapter(this));
    this.getContentPane().add(pnlSessione,  BorderLayout.SOUTH);
    this.getContentPane().add(pnlPrincipale, BorderLayout.CENTER);
    pnlPrincipale.add(btnGestBigl,         new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(30, 0, 0, 0), 0, 23));
    pnlPrincipale.add(btnGestAbb,         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 30, 0), 0, 23));
    this.getContentPane().add(pnlIntestazione, BorderLayout.NORTH);
    pnlIntestazione.add(lblIntestazione, null);
    this.getContentPane().add(pnlSx,  BorderLayout.WEST);
    pnlSessione.add(btnLogin,      new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 25, 5, 25), 0, 0));
    pnlSessione.add(btnExit,     new GridBagConstraints(3, 0, 1, 2, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    pnlSessione.add(btnInfo,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 25, 0, 0), 0, 0));
    pnlSessione.add(btnLogOut,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(pnlDx,  BorderLayout.EAST);


  }

  void setIDAddetto(int addetto) {
    idaddetto=addetto;
  }

  void btnLogin_actionPerformed(ActionEvent e) {
   login.setParent(this);
   login.setVisible(true);
   login.cleanTextField();
  }

  void btnExit_actionPerformed(ActionEvent e) {
  System.exit(0);
  }

//Al clic sul bottone Gestione Abbonamenti si richiama il frame FrmGestAbb
  void btnGestAbb_actionPerformed(ActionEvent e) {
  gestAbb.setVisible(true);
  }

//Al clic sul bottone Gestione Biglietteria si richiama il frame FrmGestBigl
  void btnGestBigl_actionPerformed(ActionEvent e) {
  gestBigl.setVisible(true);
  gestBigl.setProiezione(idaddetto);
  }

//Al clic sul bottone Log Out si cambia utente
  void btnLogOut_actionPerformed(ActionEvent e) {
this.btnLogOut.setEnabled(false);
this.btnLogin.setEnabled(true);
    this.btnGestAbb.setEnabled(false);
    this.btnGestBigl.setEnabled(false);
  }

  void btnInfo_actionPerformed(ActionEvent e) {
    info.setVisible(true);
  }
}

class FrmAddetto_btnLogin_actionAdapter implements java.awt.event.ActionListener {
  FrmAddetto adaptee;

  FrmAddetto_btnLogin_actionAdapter(FrmAddetto adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnLogin_actionPerformed(e);
  }
}

class FrmAddetto_btnExit_actionAdapter implements java.awt.event.ActionListener {
  FrmAddetto adaptee;

  FrmAddetto_btnExit_actionAdapter(FrmAddetto adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnExit_actionPerformed(e);
  }
}

class FrmAddetto_btnGestAbb_actionAdapter implements java.awt.event.ActionListener {
  FrmAddetto adaptee;

  FrmAddetto_btnGestAbb_actionAdapter(FrmAddetto adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnGestAbb_actionPerformed(e);
  }
}

class FrmAddetto_btnGestBigl_actionAdapter implements java.awt.event.ActionListener {
  FrmAddetto adaptee;

  FrmAddetto_btnGestBigl_actionAdapter(FrmAddetto adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnGestBigl_actionPerformed(e);
  }
}

class FrmAddetto_btnLogOut_actionAdapter implements java.awt.event.ActionListener {
  FrmAddetto adaptee;

  FrmAddetto_btnLogOut_actionAdapter(FrmAddetto adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnLogOut_actionPerformed(e);
  }
}

class FrmAddetto_btnInfo_actionAdapter implements java.awt.event.ActionListener {
  FrmAddetto adaptee;

  FrmAddetto_btnInfo_actionAdapter(FrmAddetto adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnInfo_actionPerformed(e);
  }
}
