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

public class FrmGestioneAbbonamenti extends JFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnlIntestazione = new JPanel();
  JPanel pnlSessione = new JPanel();
  JPanel pnlPrincipale = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel lblIntestazione = new JLabel();
  JButton btnModifica = new JButton();
  JButton btnRinnova = new JButton();
  JButton btnElimina = new JButton();
  JButton btnBack = new JButton();
  JButton btnAggiungi = new JButton();
  FrmAggiungiAbb aggiunta = new FrmAggiungiAbb();
  JScrollPane scrollListAbb = new JScrollPane();
  JTable tblListAbb = new JTable(5,3);

  public FrmGestioneAbbonamenti() {
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



    this.getContentPane().setLayout(borderLayout1);
    pnlPrincipale.setLayout(gridBagLayout1);
    lblIntestazione.setFont(new java.awt.Font("Dialog", 1, 24));
    lblIntestazione.setToolTipText("");
    lblIntestazione.setText("Gestione Abbonamenti");
    btnModifica.setText("Modifica");
    btnModifica.addActionListener(new FrmGestioneAbbonamenti_btnModifica_actionAdapter(this));
    btnRinnova.setText("Rinnova");
    btnRinnova.addActionListener(new FrmGestioneAbbonamenti_btnRinnova_actionAdapter(this));
    btnElimina.setSelectedIcon(null);
    btnElimina.setText("Elimina");
    btnElimina.addActionListener(new FrmGestioneAbbonamenti_btnElimina_actionAdapter(this));
    btnBack.setActionCommand("btnBack");
    btnBack.setText("<<Back");
    btnBack.addActionListener(new FrmGestioneAbbonamenti_btnBack_actionAdapter(this));
    btnAggiungi.setText("Aggiungi");
    btnAggiungi.addActionListener(new FrmGestioneAbbonamenti_btnAggiungi_actionAdapter(this));
    tblListAbb.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    pnlSessione.add(btnBack, null);
    pnlSessione.add(btnAggiungi, null);
    this.getContentPane().add(pnlIntestazione,  BorderLayout.NORTH);
    pnlIntestazione.add(lblIntestazione, null);
    this.getContentPane().add(pnlSessione,  BorderLayout.SOUTH);
    pnlSessione.add(btnElimina, null);
    pnlSessione.add(btnRinnova, null);
    pnlSessione.add(btnModifica, null);
    this.getContentPane().add(pnlPrincipale, BorderLayout.CENTER);
    pnlPrincipale.add(scrollListAbb,                new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(1, 0, 0, 0), 334, 196));
    scrollListAbb.getViewport().add(tblListAbb, null);
  }

//Al clic sul bottone Aggiungi si richiama il frame FrmAggiugiAbb
  void btnAggiungi_actionPerformed(ActionEvent e) {
    aggiunta.setVisible(true);
  }
//Al clic sul bottone Modifica si richiama il frame FrmAggiungiAbb
  void btnModifica_actionPerformed(ActionEvent e) {
   aggiunta.setVisible(true);
  }

//Al clic sul bottone Back si chiude tale frame tornando a quella precedente
  void btnBack_actionPerformed(ActionEvent e) {
   this.setVisible(false);
  }

//Al clic sul bottone Rinnova si comunica con una finestra
  void btnRinnova_actionPerformed(ActionEvent e) {
String input=JOptionPane.showInputDialog(null,"Inserire numero spettacoli: ","Inserimento da input",JOptionPane.PLAIN_MESSAGE);
    int numspett= Integer.parseInt(input);
  }

//Al clic sul bottone Elimina si conferma
  void btnElimina_actionPerformed(ActionEvent e) {
JOptionPane.showMessageDialog(null,"Eliminare abbonato?\n (Per annullare chiudere la finestra senza cliccare su OK)","Conferma cancellazione",JOptionPane.QUESTION_MESSAGE);
  }

}

class FrmGestioneAbbonamenti_btnAggiungi_actionAdapter implements java.awt.event.ActionListener {
  FrmGestioneAbbonamenti adaptee;

  FrmGestioneAbbonamenti_btnAggiungi_actionAdapter(FrmGestioneAbbonamenti adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnAggiungi_actionPerformed(e);
  }
}

class FrmGestioneAbbonamenti_btnModifica_actionAdapter implements java.awt.event.ActionListener {
  FrmGestioneAbbonamenti adaptee;

  FrmGestioneAbbonamenti_btnModifica_actionAdapter(FrmGestioneAbbonamenti adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnModifica_actionPerformed(e);
  }
}

class FrmGestioneAbbonamenti_btnBack_actionAdapter implements java.awt.event.ActionListener {
  FrmGestioneAbbonamenti adaptee;

  FrmGestioneAbbonamenti_btnBack_actionAdapter(FrmGestioneAbbonamenti adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnBack_actionPerformed(e);
  }
}

class FrmGestioneAbbonamenti_btnRinnova_actionAdapter implements java.awt.event.ActionListener {
  FrmGestioneAbbonamenti adaptee;

  FrmGestioneAbbonamenti_btnRinnova_actionAdapter(FrmGestioneAbbonamenti adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnRinnova_actionPerformed(e);
  }
}

class FrmGestioneAbbonamenti_btnElimina_actionAdapter implements java.awt.event.ActionListener {
  FrmGestioneAbbonamenti adaptee;

  FrmGestioneAbbonamenti_btnElimina_actionAdapter(FrmGestioneAbbonamenti adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnElimina_actionPerformed(e);
  }
}
