import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * <p>Title: Presentation Layer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Squattrinati</p>
 * @author Buonaura, Cannavacciuolo, Memoli, Pappalardo, Pentangelo, Spinelli
 * @version 1.0
 */

public class FrmAggiungiAbb extends JFrame {
  JPanel pnlSessione = new JPanel();
  JPanel pnlPrincipale = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton btnOK = new JButton();
  JButton btnAnnulla = new JButton();
  JLabel lblNome = new JLabel();
  JLabel lblCognome = new JLabel();
  JLabel lblIndirizzo = new JLabel();
  JLabel lblTel = new JLabel();
  JLabel lblMail = new JLabel();
  JLabel lblResidenza = new JLabel();
  JLabel lblNumeroSpett = new JLabel();
  JTextField txtIndirizzo = new JTextField();
  JTextField txtMail = new JTextField();
  JTextField txtTel = new JTextField();
  JTextField txtResidenza = new JTextField();
  JTextField txtNumSpett = new JTextField();
  JTextField txtCognome = new JTextField();
  JTextField txtNome = new JTextField();
  JLabel lblTipo = new JLabel();
  JRadioButton rdIntero = new JRadioButton();
  JRadioButton rdRidotto = new JRadioButton();

  public FrmAggiungiAbb() {
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
    setSize(screenWidth / 2, screenHeight / 2);
    setLocation(screenWidth / 4, screenHeight / 4);

    pnlPrincipale.setLayout(gridBagLayout1);
    btnOK.setText("   OK  ");
    btnOK.addActionListener(new FrmAggiungiAbb_btnOK_actionAdapter(this));
    btnAnnulla.setText("Annulla");
    btnAnnulla.addActionListener(new FrmAggiungiAbb_btnAnnulla_actionAdapter(this));
    lblNome.setFont(new java.awt.Font("Dialog", 1, 14));
    lblNome.setMaximumSize(new Dimension(130, 20));
    lblNome.setMinimumSize(new Dimension(130, 20));
    lblNome.setPreferredSize(new Dimension(130, 20));
    lblNome.setText("Nome");
    lblCognome.setFont(new java.awt.Font("Dialog", 1, 14));
    lblCognome.setMaximumSize(new Dimension(130, 20));
    lblCognome.setMinimumSize(new Dimension(130, 20));
    lblCognome.setPreferredSize(new Dimension(130, 20));
    lblCognome.setText("Cognome");
    lblIndirizzo.setFont(new java.awt.Font("Dialog", 1, 14));
    lblIndirizzo.setMaximumSize(new Dimension(130, 20));
    lblIndirizzo.setMinimumSize(new Dimension(130, 20));
    lblIndirizzo.setPreferredSize(new Dimension(130, 20));
    lblIndirizzo.setText("Indirizzo");
    lblTel.setFont(new java.awt.Font("Dialog", 1, 14));
    lblTel.setMaximumSize(new Dimension(130, 20));
    lblTel.setMinimumSize(new Dimension(130, 20));
    lblTel.setPreferredSize(new Dimension(130, 20));
    lblTel.setText("Telefono");
    lblMail.setFont(new java.awt.Font("Dialog", 1, 14));
    lblMail.setMaximumSize(new Dimension(130, 20));
    lblMail.setMinimumSize(new Dimension(130, 20));
    lblMail.setPreferredSize(new Dimension(130, 20));
    lblMail.setText("E-Mail");
    lblResidenza.setFont(new java.awt.Font("Dialog", 1, 14));
    lblResidenza.setMaximumSize(new Dimension(130, 20));
    lblResidenza.setMinimumSize(new Dimension(130, 20));
    lblResidenza.setOpaque(false);
    lblResidenza.setPreferredSize(new Dimension(130, 20));
    lblResidenza.setText("Residenza");
    lblNumeroSpett.setFont(new java.awt.Font("Dialog", 1, 14));
    lblNumeroSpett.setMaximumSize(new Dimension(130, 20));
    lblNumeroSpett.setMinimumSize(new Dimension(130, 20));
    lblNumeroSpett.setPreferredSize(new Dimension(130, 20));
    lblNumeroSpett.setVerifyInputWhenFocusTarget(true);
    lblNumeroSpett.setText("Num Spettacoli");
    txtIndirizzo.setMinimumSize(new Dimension(100, 20));
    txtIndirizzo.setPreferredSize(new Dimension(100, 20));
    txtIndirizzo.setToolTipText("Inserire l\'indirizzo dell\'abbonato");
    txtIndirizzo.setText("");
    txtMail.setFont(new java.awt.Font("SansSerif", 0, 11));
    txtMail.setMinimumSize(new Dimension(100, 20));
    txtMail.setPreferredSize(new Dimension(100, 20));
    txtMail.setToolTipText("Inserire l\'e mail dell\'abbonato");
    txtMail.setText("");
    txtTel.setFont(new java.awt.Font("SansSerif", 0, 11));
    txtTel.setMinimumSize(new Dimension(100, 20));
    txtTel.setPreferredSize(new Dimension(100, 20));
    txtTel.setRequestFocusEnabled(true);
    txtTel.setToolTipText("Inserire il tel dell\'abbonato");
    txtTel.setText("");
    txtResidenza.setFont(new java.awt.Font("SansSerif", 0, 11));
    txtResidenza.setMinimumSize(new Dimension(100, 20));
    txtResidenza.setPreferredSize(new Dimension(100, 20));
    txtResidenza.setToolTipText("Inserire la residenza dell\'abbonato");
    txtResidenza.setText("");
    txtNumSpett.setFont(new java.awt.Font("SansSerif", 0, 11));
    txtNumSpett.setMinimumSize(new Dimension(100, 20));
    txtNumSpett.setPreferredSize(new Dimension(100, 20));
    txtNumSpett.setToolTipText("Inserire il numero di spettacoli previsti dall\'abbonamento");
    txtNumSpett.setText("");
    txtCognome.setMinimumSize(new Dimension(100, 20));
    txtCognome.setPreferredSize(new Dimension(100, 20));
    txtCognome.setToolTipText("Inserire il cognome dell\'abbonato");
    txtCognome.setText("");
    txtNome.setMinimumSize(new Dimension(100, 20));
    txtNome.setPreferredSize(new Dimension(100, 20));
    txtNome.setToolTipText("Inserire il nome dell\'abbonato");
    txtNome.setText("");
    this.setTitle("Simply Ticket 1.0");
    lblTipo.setFont(new java.awt.Font("Dialog", 1, 14));
    lblTipo.setMaximumSize(new Dimension(130, 20));
    lblTipo.setMinimumSize(new Dimension(130, 20));
    lblTipo.setPreferredSize(new Dimension(130, 20));
    lblTipo.setRequestFocusEnabled(true);
    lblTipo.setText("Tipo");
    rdIntero.setDoubleBuffered(true);
    rdIntero.setMaximumSize(new Dimension(60, 25));
    rdIntero.setMinimumSize(new Dimension(60, 25));
    rdIntero.setPreferredSize(new Dimension(60, 25));
    rdIntero.setText("Intero");
    rdRidotto.setDoubleBuffered(true);
    rdRidotto.setMaximumSize(new Dimension(60, 25));
    rdRidotto.setMinimumSize(new Dimension(60, 25));
    rdRidotto.setPreferredSize(new Dimension(60, 25));
    rdRidotto.setText("Ridotto");
    pnlSessione.add(btnOK, null);
    pnlSessione.add(btnAnnulla, null);
    this.getContentPane().add(pnlPrincipale, BorderLayout.CENTER);
    this.getContentPane().add(pnlSessione, BorderLayout.SOUTH);

    ButtonGroup Tipo=new ButtonGroup();
        Tipo.add(rdIntero);
        Tipo.add(rdRidotto);

    pnlPrincipale.add(lblTel,                                                                                       new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
    pnlPrincipale.add(lblMail,                                                                                          new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
    pnlPrincipale.add(lblCognome,                                                                                      new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
    pnlPrincipale.add(lblNome,                                                                                                     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 10, 0), 0, 0));
    pnlPrincipale.add(lblIndirizzo,                                                                       new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
    pnlPrincipale.add(lblResidenza,                                                         new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
    pnlPrincipale.add(lblNumeroSpett,                                                                         new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 6, 0), 0, 0));
    pnlPrincipale.add(txtNumSpett,                                     new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
    pnlPrincipale.add(txtResidenza,                           new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
    pnlPrincipale.add(txtTel,                  new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
    pnlPrincipale.add(txtMail,                 new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
    pnlPrincipale.add(txtCognome,                  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
    pnlPrincipale.add(txtNome,                  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 10, 0), 0, 0));
    pnlPrincipale.add(txtIndirizzo,                   new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
    pnlPrincipale.add(lblTipo,              new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    pnlPrincipale.add(rdIntero,      new GridBagConstraints(1, 7, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    pnlPrincipale.add(rdRidotto,  new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }

//Al clic sul bottone OK si chiude tale finestra
  void btnOK_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
//Al clic sul bottone Annulla si chiude tale finestra
  void btnAnnulla_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
}

class FrmAggiungiAbb_btnOK_actionAdapter implements java.awt.event.ActionListener {
  FrmAggiungiAbb adaptee;

  FrmAggiungiAbb_btnOK_actionAdapter(FrmAggiungiAbb adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnOK_actionPerformed(e);
  }
}

class FrmAggiungiAbb_btnAnnulla_actionAdapter implements java.awt.event.ActionListener {
  FrmAggiungiAbb adaptee;

  FrmAggiungiAbb_btnAnnulla_actionAdapter(FrmAggiungiAbb adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnAnnulla_actionPerformed(e);
  }
}
