
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Server extends JFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnlTitolo = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JLabel lbltitolo = new JLabel();
  JPanel pnlLogo = new JPanel();
  GridLayout gridLayout2 = new GridLayout();
  JLabel lblLogo = new JLabel();
  JScrollPane pnlStatoServer = new JScrollPane();
  JTextArea txtStatoServer = new JTextArea();

  public Server() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    Toolkit kit=Toolkit.getDefaultToolkit();
    Dimension screenSize=kit.getScreenSize();
    int screenHeight=screenSize.height;
    int screenWidth=screenSize.width;
    this.setLocation(screenWidth/4,screenHeight/4);


    this.getContentPane().setLayout(borderLayout1);

    pnlTitolo.setPreferredSize(new Dimension(40, 40));
    pnlTitolo.setLayout(gridLayout1);
    lbltitolo.setFont(new java.awt.Font("Dialog", 1, 30));
    lbltitolo.setHorizontalAlignment(SwingConstants.CENTER);
    lbltitolo.setText("Simply Ticket");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setTitle("Server");
    pnlLogo.setLayout(gridLayout2);
     ImageIcon imgLogo=new ImageIcon("src/Logo_ST_Final.jpg");


    lblLogo.setBackground(Color.white);
    lblLogo.setBorder(BorderFactory.createEtchedBorder());
    lblLogo.setMaximumSize(new Dimension(0, 0));
    lblLogo.setOpaque(false);
    lblLogo.setPreferredSize(new Dimension(100, 100));
    lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
    lblLogo.setIcon(imgLogo);



    pnlStatoServer.setPreferredSize(new Dimension(72, 40));
    txtStatoServer.setEditable(false);
    txtStatoServer.setLineWrap(true);
    pnlLogo.setBackground(Color.white);
    pnlLogo.setPreferredSize(new Dimension(100, 100));
    this.getContentPane().add(pnlTitolo, BorderLayout.NORTH);
    pnlTitolo.add(lbltitolo, null);
    this.getContentPane().add(pnlLogo, BorderLayout.CENTER);
    pnlLogo.add(lblLogo, null);
    this.getContentPane().add(pnlStatoServer, BorderLayout.SOUTH);
    pnlStatoServer.getViewport().add(txtStatoServer, null);


 try
  {
  System.out.println("Server avviato");
  String serverName=java.net.InetAddress.getLocalHost().getHostName();
  ControllerUtenzaImpl controllerUtenza=new ControllerUtenzaImpl();
  ControllerLoginImpl controllerLogin=new ControllerLoginImpl();
  ControllerBiglietteriaImpl controllerBiglietteria=new ControllerBiglietteriaImpl();
  Naming.rebind("controllerBiglietteria",controllerBiglietteria);
  Naming.rebind("controllerLogin",controllerLogin);
  Naming.rebind("controllerUtenza",controllerUtenza);
  txtStatoServer.setText("Server avviato su: "+serverName+"\n");
  txtStatoServer.append("Waiting...");
 }
 catch(Exception e)
 {
  txtStatoServer.setText(e.getMessage());
  System.out.println(e.getMessage());
   e.printStackTrace();
 }

  }
}
