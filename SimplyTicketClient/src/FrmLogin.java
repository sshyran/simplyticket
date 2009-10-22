import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.server.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.MalformedURLException;
import java.io.*;

/**
 * <p>Title: Presentation Layer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Squattrinati</p>
 * @author Buonaura, Cannavacciuolo, Memoli, Pappalardo, Pentangelo, Spinelli
 * @version 1.0
 */

public class FrmLogin extends JDialog {
  BorderLayout borderLayout1 = new BorderLayout();
  Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
  Dimension thisSize = new Dimension();
  Dimension centerPos= new Dimension();
  JButton btnOK = new JButton();
  JButton btnAnnulla = new JButton();
  JPanel pnlPrincipale;
  JPanel pnlSessione = new JPanel();
  FrmAddetto padre;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel lblUserName = new JLabel();
  JTextField txtUserName = new JTextField();
  JLabel lblPassw = new JLabel();
  JPasswordField txtPassw = new JPasswordField();
  ControllerLogin controllerLogin;
  int idaddetto=-1;


  public FrmLogin() {
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
   FileReader file=null;
   BufferedReader lettore=null;
   String valore;
   try {
     file=new FileReader("server");
     lettore=new BufferedReader(file);
     valore=lettore.readLine();
   }
   catch(Exception e) {
     valore="";
     System.out.println("File server not found, lookup on localhost");
   }
   try {
     if (valore.length() < 1) {
       controllerLogin = (ControllerLogin) Naming.lookup("controllerLogin");
     }
     else {
       controllerLogin = (ControllerLogin) Naming.lookup("//" + valore +"/controllerLogin");
     }
   }
  catch (RemoteException e) {
    JOptionPane.showMessageDialog(null,"            Connect Refused. Try Again","Errore",JOptionPane.ERROR_MESSAGE);
    System.exit(-1);
  }
  catch(Exception ex) {
    System.out.println(ex.getMessage());
  }


// center frame in screen
   setSize(screenWidth / 2, screenHeight / 2);
   setLocation(screenWidth / 4, screenHeight / 4);


    this.getContentPane().setLayout(borderLayout1);
    btnOK.addActionListener(new FrmLogin_btnOK_actionAdapter(this));
    btnAnnulla.addActionListener(new FrmLogin_btnAnnulla_actionAdapter(this));

    pnlPrincipale = new JPanel();
    btnAnnulla.setMaximumSize(new Dimension(100, 30));
    btnAnnulla.setMinimumSize(new Dimension(100, 30));
    btnOK.setMaximumSize(new Dimension(100, 30));
    btnOK.setMinimumSize(new Dimension(100, 30));
    btnOK.setPreferredSize(new Dimension(100, 30));



     btnOK.setBackground(new Color(204, 204, 253));
    btnOK.setFont(new java.awt.Font("Mirror", 1, 11));
    btnOK.setText("Connect");


    btnAnnulla.setBackground(new Color(204, 204, 253));
    btnAnnulla.setFont(new java.awt.Font("Mirror", 1, 11));
    btnAnnulla.setPreferredSize(new Dimension(100, 30));
    btnAnnulla.setText("Annulla");


    thisSize.width = ((screenSize.width*33)/100);
    thisSize.height =((screenSize.height*20)/100);
    this.setFont(new java.awt.Font("Dialog", 1, 12));
    this.setTitle("Simply Ticket 1.0");
    this.setSize(thisSize);

    this.setLocation((screenSize.width-this.getWidth())/2,(screenSize.height-this.getHeight())/2);
    this.setResizable(false);
    pnlPrincipale.setLayout(gridBagLayout1);

    lblUserName.setFont(new java.awt.Font("Mirror", 3, 13));
    lblUserName.setToolTipText("");
    lblUserName.setText("User Name");
    txtUserName.setFont(new java.awt.Font("Mirror", 0, 12));
    txtUserName.setDebugGraphicsOptions(0);
    txtUserName.setToolTipText("Inserire username");
    txtUserName.setText("");
    lblPassw.setFont(new java.awt.Font("Mirror", 3, 13));
    lblPassw.setRequestFocusEnabled(true);
    lblPassw.setText("Password");
    txtPassw.setFont(new java.awt.Font("Mirror", 0, 12));
    txtPassw.setToolTipText("Inserire password");
    txtPassw.setSelectionStart(0);
    txtPassw.setText("");
    this.getContentPane().add(pnlSessione, BorderLayout.SOUTH);
    pnlSessione.add(btnOK, null);
    pnlSessione.add(btnAnnulla, null);
    this.getContentPane().add(pnlPrincipale, BorderLayout.CENTER);
    pnlPrincipale.add(txtPassw,          new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 76, 1));
    pnlPrincipale.add(txtUserName,              new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 20, 0), 76, 1));
    pnlPrincipale.add(lblPassw,       new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(-20, 0, 0, 15), 10, 0));
    pnlPrincipale.add(lblUserName,              new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(-20, 0, 0, 15), 0, 0));
  }

 void setParent(FrmAddetto frm){
 padre=frm;
 }

 void cleanTextField() {
   txtUserName.setText("");
   txtPassw.setText("");
 }

//Al clic sul bottone OK si chuide il frame e si abilitino i bottoni
  void btnOK_actionPerformed(ActionEvent e) {
    int risultatoLogin=-1;
    char[] password=txtPassw.getPassword();
    int lunghezza=password.length;
    String pass="";
    for (int i=0;i<lunghezza;i++) {
      pass=pass+password[i];
    }
    try {
      risultatoLogin=controllerLogin.verifica(true, txtUserName.getText(),pass);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    if (risultatoLogin>-1) {
      padre.btnGestAbb.setEnabled(false);
      padre.btnGestBigl.setEnabled(true);
      this.setVisible(false);
      padre.btnLogin.setEnabled(false);
      padre.btnLogOut.setEnabled(true);
      padre.setIDAddetto(risultatoLogin);
    }
    else {
      JOptionPane.showMessageDialog(null,"            Login Fallito","Errore",JOptionPane.ERROR_MESSAGE);
      txtUserName.setText("");
      txtPassw.setText("");
      txtUserName.requestFocus(true);
    }
  }

//Al clic sul bottone Annulla si chiude tale frame
  void btnAnnulla_actionPerformed(ActionEvent e) {
  this.setVisible(false);
  }

  int getIDAddetto() {
    return idaddetto;
  }
}

class FrmLogin_btnOK_actionAdapter implements java.awt.event.ActionListener {
  FrmLogin adaptee;

  FrmLogin_btnOK_actionAdapter(FrmLogin adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnOK_actionPerformed(e);
  }
}

class FrmLogin_btnAnnulla_actionAdapter implements java.awt.event.ActionListener {
  FrmLogin adaptee;

  FrmLogin_btnAnnulla_actionAdapter(FrmLogin adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnAnnulla_actionPerformed(e);
  }
}
