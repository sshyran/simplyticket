import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * <p>Title: FrmInfo
 * <p>Description: Finestra che mostra le informazione principali sul software
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Elettronic Works
 * @author Luca Pappalardo
 * @version 1.0
 */

public class FrmInfo extends JFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnlIntestazione = new JPanel();
  JPanel pnlGenerale = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel pnlSessione = new JPanel();
  JButton btnChiudi = new JButton();
  JLabel lblIntestazione = new JLabel();
  JLabel lblAutori = new JLabel();
  JTextField txtBuonaura = new JTextField();
  JTextField txtCannavacciuolo = new JTextField();
  JTextField txtCepparulo = new JTextField();
  JTextField txtMemoli = new JTextField();
  JTextField txtPappalardo = new JTextField();
  JTextField txtPentangelo = new JTextField();
  JTextField txtSantangelo = new JTextField();
  JTextField txtSpinelli = new JTextField();
  JLabel lblVersione = new JLabel();
  JTextField txtVersione = new JTextField();
  JPanel pnlLogo = new JPanel();
  JLabel lblLogo = new JLabel();
  FlowLayout flowLayout1 = new FlowLayout();
  JLabel lblElettronicWorks = new JLabel();


  public FrmInfo() {
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
   this.getContentPane().setBackground(Color.white);
    this.setResizable(false);
    this.setTitle("Simply Ticket 1.0 Informazioni");

// center frame in screen
   setSize((screenWidth / 2)+50, (screenHeight / 2)+50);
   setLocation(screenWidth / 4, screenHeight / 4);

    this.getContentPane().setLayout(borderLayout1);
    pnlGenerale.setLayout(gridBagLayout1);
    btnChiudi.setFont(new java.awt.Font("Mirror", 1, 11));
    btnChiudi.setText("Chiudi");
    btnChiudi.addActionListener(new FrmInfo_btnChiudi_actionAdapter(this));
    lblIntestazione.setFont(new java.awt.Font("Mirror", 1, 24));
    lblIntestazione.setText("Simply Ticket 1.0");
    lblAutori.setFont(new java.awt.Font("Mirror", 3, 12));
    lblAutori.setText("Autori");
    txtBuonaura.setBackground(Color.white);
    txtBuonaura.setFont(new java.awt.Font("Mirror", 1, 12));
    txtBuonaura.setBorder(null);
    txtBuonaura.setEditable(false);
    txtBuonaura.setText("Buonaura Bernardo Pio");
    txtBuonaura.setHorizontalAlignment(SwingConstants.LEADING);
    txtCannavacciuolo.setBackground(Color.white);
    txtCannavacciuolo.setFont(new java.awt.Font("Mirror", 1, 12));
    txtCannavacciuolo.setBorder(null);
    txtCannavacciuolo.setEditable(false);
    txtCannavacciuolo.setText("Cannavacciuolo Fiorenzo");
    txtCepparulo.setBackground(Color.white);
    txtCepparulo.setFont(new java.awt.Font("Mirror", 1, 12));
    txtCepparulo.setAlignmentY((float) 0.5);
    txtCepparulo.setBorder(null);
    txtCepparulo.setEditable(false);
    txtCepparulo.setText("Cepparulo Marco");
    txtMemoli.setBackground(Color.white);
    txtMemoli.setFont(new java.awt.Font("Mirror", 1, 12));
    txtMemoli.setBorder(null);
    txtMemoli.setEditable(false);
    txtMemoli.setText("Memoli Roberto");
    txtPappalardo.setBackground(Color.white);
    txtPappalardo.setFont(new java.awt.Font("Mirror", 1, 12));
    txtPappalardo.setBorder(null);
    txtPappalardo.setEditable(false);
    txtPappalardo.setText("Pappalardo Luca");
    txtPentangelo.setBackground(Color.white);
    txtPentangelo.setFont(new java.awt.Font("Mirror", 1, 12));
    txtPentangelo.setBorder(null);
    txtPentangelo.setEditable(false);
    txtPentangelo.setSelectionEnd(23);
    txtPentangelo.setText("Pentangelo Antonio");
    txtSantangelo.setBackground(Color.white);
    txtSantangelo.setFont(new java.awt.Font("Mirror", 1, 12));
    txtSantangelo.setBorder(null);
    txtSantangelo.setEditable(false);
    txtSantangelo.setText("Santangelo Massimiliano");
    txtSpinelli.setBackground(Color.white);
    txtSpinelli.setFont(new java.awt.Font("Mirror", 1, 12));
    txtSpinelli.setBorder(null);
    txtSpinelli.setEditable(false);
    txtSpinelli.setText("Spinelli Raffaele");
    lblVersione.setFont(new java.awt.Font("Mirror", 3, 11));
    lblVersione.setText("Versione");
    txtVersione.setBackground(Color.white);
    txtVersione.setFont(new java.awt.Font("Mirror", 1, 11));
    txtVersione.setBorder(null);
    txtVersione.setEditable(false);
    txtVersione.setText("1.0");
    Image immagine=Toolkit.getDefaultToolkit().getImage("C:\\SimplyTicket\\Logo_ST_Final.jpg");
    immagine.getScaledInstance(240,320,1);
    ImageIcon img= new ImageIcon(immagine);
    //lblLogo.setPreferredSize(new Dimension(40, 40));
    lblLogo.setIcon(img);
    pnlGenerale.setBackground(Color.white);
    pnlLogo.setBackground(Color.white);
    pnlIntestazione.setBackground(Color.lightGray);
    pnlSessione.setBackground(Color.lightGray);
    pnlSessione.setDebugGraphicsOptions(0);
    pnlSessione.setLayout(flowLayout1);
    lblElettronicWorks.setEnabled(true);
    lblElettronicWorks.setFont(new java.awt.Font("Book Antiqua", 3, 15));
    lblElettronicWorks.setText("Electronic Works");
    borderLayout1.setHgap(10);
    this.getContentPane().add(pnlIntestazione,  BorderLayout.NORTH);
    pnlIntestazione.add(lblIntestazione, null);
    this.getContentPane().add(pnlGenerale,  BorderLayout.WEST);
    this.getContentPane().add(pnlSessione, BorderLayout.SOUTH);
    this.getContentPane().add(pnlLogo,  BorderLayout.CENTER);
    pnlGenerale.add(lblAutori,                            new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    pnlGenerale.add(txtBuonaura,                                       new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 10, 0));
    pnlGenerale.add(txtCannavacciuolo,                               new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 1, 0));
    pnlGenerale.add(txtCepparulo,                                new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 45, 0));
    pnlGenerale.add(txtMemoli,                         new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 52, 0));
    pnlGenerale.add(txtPappalardo,                       new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 45, 0));
    pnlGenerale.add(txtPentangelo,                         new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 32, 0));
    pnlGenerale.add(txtSantangelo,                 new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
    pnlGenerale.add(txtSpinelli,                new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 50, 0));
    pnlGenerale.add(lblVersione,           new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    pnlGenerale.add(txtVersione,        new GridBagConstraints(1, 11, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    pnlLogo.add(lblLogo, null);
    pnlSessione.add(btnChiudi, null);
    pnlGenerale.add(lblElettronicWorks,  new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }

//Al clic sul bottone Chiudu si chiude il frame
  void btnChiudi_actionPerformed(ActionEvent e) {
this.setVisible(false);
  }
}

class FrmInfo_btnChiudi_actionAdapter implements java.awt.event.ActionListener {
  FrmInfo adaptee;

  FrmInfo_btnChiudi_actionAdapter(FrmInfo adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnChiudi_actionPerformed(e);
  }
}
