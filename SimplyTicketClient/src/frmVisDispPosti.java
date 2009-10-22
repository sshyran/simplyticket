
import java.lang.Thread;
import java.lang.Runnable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
import data_layer.Collezione;
import data_layer.CollezioneArrayList;
import data_layer.Proiezione;
import data_layer.Sala;
import data_layer.Film;
import java.rmi.server.*;
import java.rmi.*;
import java.sql.SQLException;
import javax.swing.*;
import java.io.*;


/**
 * <p>Title: frmVisDispPosti </p>
 * <p>Description:  Realizzazione del form che visualizza le proiezioni a run-time</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author: Cepparulo Marco
 * @version 1.0
 */


/** Presentation Layer
 *  La classe frmVisDispPosti visualizza le proiezioni quotidiane,aggiornando a run-time la     *  disponibilit� della sala,film proiettato,orario d'inizio.
 *  Quando un film � iniziato la riga corrispondente sulla tabella verr� cancellata
 */

public class frmVisDispPosti extends JFrame implements Runnable {
  static Thread trd;
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane scroller = new JScrollPane();
  JTable listaSpettacoli;
  ControllerUtenza controller;
  BorderLayout borderLayout2 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel pnlTitolo = new JPanel();
  JPanel pnlIntestazione = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  GridLayout gridLayout1 = new GridLayout();
  JLabel lblSala = new JLabel();
  JLabel lblOraInizio = new JLabel();
  JLabel lblfilm = new JLabel();
  JLabel lblDisponibilita = new JLabel();
  JLabel lblTitolo = new JLabel();
  int indiceProiezione;
  JPanel pnlOrario = new JPanel();
  GridLayout gridLayout2 = new GridLayout();
  JLabel lblOrario = new JLabel();

  public frmVisDispPosti() {
    try {
      this.jbInit();
    }
    catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private void jbInit()  {


  //inizializza l'indice delle proiezioni da visualizzare sul terminale statico
  indiceProiezione=-1;

   //ottieni l'oggetto remoto ControllerUtenza
   FileReader file=null;
   BufferedReader lettore=null;
   String valore;
   try {
     file=new FileReader("server");
     lettore=new BufferedReader(file);
     valore=lettore.readLine();
   }
   catch(Exception e) {
     valore="127.0.0.1";
     System.out.println("File server not found, lookup on localhost");
   }
   try {
//     if (valore.length() < 1) {
//       controller = (ControllerUtenza) Naming.lookup("controllerUtenza");
//     }
//     else {
       controller = (ControllerUtenza) Naming.lookup("//" + valore +"/controllerUtenza");
//     }
   }
   catch(Exception e){

    System.out.println("Errore in frmVisDispPosti:");
    e.printStackTrace();
   }


    //listaProiezione verr� utilizzata per contenere le proiezioni attuali
    Collezione listaProiezioni = null;


    //Leggi le dimensioni dello schermo
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;

    this.setSize(screenWidth, screenHeight);
    this.setLocale(java.util.Locale.getDefault());

    this.setTitle("Simply Ticket 1.0");
    this.setLocation(0, 0);
    this.getContentPane().setLayout(borderLayout1);



    //Imposta pannelli intestazione
    IntestazioniTabella();

    //avvia il timer che modifica l'orario nel pnlOrario
    Orario listener=new Orario(lblOrario);
    Timer t=new Timer(1000,listener);
    t.start();


    //crea Thread per il refresh delle proiezioni ogni secondo
    if(trd == null){
    trd = new Thread(this);
    trd.start();
   }
  }


  /*E la funzione che permette di creare i vari pannelli presenti sul terminale statico*/

  private void IntestazioniTabella() {

   //crea il pannello che contienne l'orario attuale
    lblOrario.setFont(new java.awt.Font("Dialog", 1, 20));
    lblOrario.setHorizontalAlignment(SwingConstants.CENTER);
    pnlOrario.setDebugGraphicsOptions(0);
    pnlOrario.setPreferredSize(new Dimension(30, 30));
    pnlOrario.setLayout(gridLayout2);
    pnlOrario.add(lblOrario, null);

   //crea il pannello che contiene il titolo "Proiezioni Attuali"
    pnlTitolo.setBorder(null);
    pnlTitolo.setPreferredSize(new Dimension(10, 130));
    pnlTitolo.setRequestFocusEnabled(true);
    pnlTitolo.setLayout(borderLayout4);

    //crea il pannello intestazioni della tabella
    pnlIntestazione.setPreferredSize(new Dimension(10, 30));
    pnlIntestazione.setLayout(gridLayout1);

    //crea label Sala
    lblSala.setFont(new java.awt.Font("Dialog", 1, 20));
    lblSala.setBorder(BorderFactory.createEtchedBorder());
    lblSala.setHorizontalAlignment(SwingConstants.CENTER);
    lblSala.setHorizontalTextPosition(SwingConstants.TRAILING);
    lblSala.setText("Sala");

    //crea label OraInizio
    lblOraInizio.setFont(new java.awt.Font("Dialog", 1, 20));
    lblOraInizio.setBorder(BorderFactory.createEtchedBorder());
    lblOraInizio.setHorizontalAlignment(SwingConstants.CENTER);
    lblOraInizio.setText("Inizio");

    //crea label film
    lblfilm.setFont(new java.awt.Font("Dialog", 1, 20));
    lblfilm.setBorder(BorderFactory.createEtchedBorder());
    lblfilm.setHorizontalAlignment(SwingConstants.CENTER);
    lblfilm.setText("Film");

    //crea label Disponibilit�
    lblDisponibilita.setFont(new java.awt.Font("Dialog", 1, 20));
    lblDisponibilita.setBorder(BorderFactory.createEtchedBorder());
    lblDisponibilita.setDebugGraphicsOptions(0);
    lblDisponibilita.setHorizontalAlignment(SwingConstants.CENTER);
    lblDisponibilita.setText("Disponibilità");


    lblTitolo.setFont(new java.awt.Font("Georgia", 3, 50));
    lblTitolo.setBorder(null);
    lblTitolo.setHorizontalAlignment(SwingConstants.CENTER);
    lblTitolo.setText("Proiezioni Attuali");


    this.getContentPane().add(scroller, BorderLayout.CENTER);
    this.getContentPane().add(pnlTitolo, BorderLayout.NORTH);
    this.getContentPane().add(pnlOrario, BorderLayout.SOUTH);

    pnlTitolo.add(pnlIntestazione, BorderLayout.SOUTH);

    //Aggiungi componenti al pnlIntestazione
    pnlIntestazione.add(lblSala, null);
    pnlIntestazione.add(lblfilm, null);
    pnlIntestazione.add(lblDisponibilita, null);
    pnlIntestazione.add(lblOraInizio, null);

    pnlTitolo.add(lblTitolo, BorderLayout.CENTER);


  }


 /*Questa funzione serve per visualizzare le proiezioni odierne ancora attive(non iniziate),
  *Viene richiamata per visualizzare le proiezioni nella tabella del form
  *@pre:la lista da Stampare deve essere una collezione di array di stringhe ognuno composto da 4   *elementi
  *@post:viene visualizzata la lista nella tabella sul form
 */

  public void VisInfoSpettacoli(Collezione listaDaStampare) {

      //imposta  parametri della tabella
      listaSpettacoli = new JTable(listaDaStampare.size(), 4);
      listaSpettacoli.setTableHeader(null);
      listaSpettacoli.setFont(new java.awt.Font("Copperplate Gothic Light", 3,13));
      listaSpettacoli.setDoubleBuffered(false);
      listaSpettacoli.setRowSelectionAllowed(false);
      listaSpettacoli.setAutoCreateColumnsFromModel(true);
      listaSpettacoli.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

      listaSpettacoli.setEnabled(false);



      String[] proiezione = null;

      int i = 0;

      //per ogni proiezione ricevuta visualizzarla nella tabella
      while (i < listaDaStampare.size()) {
       try{
        proiezione = ((String[]) (listaDaStampare.getIndex(i)));
       }
       catch(Exception e)
       {
        System.out.println("Errore in frmVisDispPosti:");
        e.printStackTrace();
       }
        //copia i valorinella tabella

        //copia il nome della sala(proiezione[0] nella colonna Sala
        listaSpettacoli.getModel().setValueAt("                          "+proiezione[0], i, 0);

        //copia il Titolo del Film(proiezione[1] nella colonna Titolo
        listaSpettacoli.getModel().setValueAt(proiezione[1], i, 1);

        //copia il #posti disponibili della proiezione(proiezione[2] nella colonna Disponibilit�
        listaSpettacoli.getModel().setValueAt("                      "+proiezione[2], i, 2);

        //copia l'orario d'inizio della proiezione(proiezione[3] nella colonna Inizio
        listaSpettacoli.getModel().setValueAt("                         "+proiezione[3], i, 3);

        i++;
      }

      //aggiungi la tabella allo scroller
      scroller.getViewport().add(listaSpettacoli, null);
    }




  /*Inizializzazione del Thread: testa  ogni 5 secondii se vi � stato un
  aggiornamento rilevante sulla base di dati e fa aggironare la tabella proiezioni*/
    public void run(){

     while(Thread.currentThread() == trd){
     try//Eccezione x il thread
     {

       Collezione listaProiezioni=null;

       //leggi la lista di proiezioni dal database
       try{

           listaProiezioni = controller.leggi_info_Proiezioni();
       }
       catch(Exception e) {
         System.out.println("Errore in frmVisDispPosti:");
         e.printStackTrace();
       }

       CollezioneArrayList proiezioniToken=new CollezioneArrayList();


      //leggi la Data attuale
      GregorianCalendar dataAttuale=new GregorianCalendar();


     //Data Proiezione
     GregorianCalendar dataProiezione=new GregorianCalendar();

     if(indiceProiezione<0)
       indiceProiezione=0;

     //posizionati sulla 1� proiezione attiva
      for (int i = indiceProiezione; i < listaProiezioni.size(); i++) {

        Proiezione pro=null;

        //leggi I-esimo oggetto proiezione
        try {
          pro = (Proiezione) listaProiezioni.getIndex(i);
        }
        catch (Exception e) {
           System.out.println("Errore in frmVisDispPosti:");
           e.printStackTrace();
        }

        //Leggi orario proiezione
        dataProiezione=(GregorianCalendar)pro.getDataOraInizio().clone();
        System.out.println(dataProiezione.get(GregorianCalendar.YEAR)+"-"+(dataProiezione.get(GregorianCalendar.MONTH)+1)+"-"+dataProiezione.get(GregorianCalendar.DAY_OF_MONTH)+"-"+dataProiezione.get(GregorianCalendar.HOUR_OF_DAY)+"-"+dataProiezione.get(GregorianCalendar.MINUTE));
        System.out.println(dataAttuale.get(GregorianCalendar.YEAR)+"-"+(dataAttuale.get(GregorianCalendar.MONTH)+1)+"-"+dataAttuale.get(GregorianCalendar.DAY_OF_MONTH)+"-"+dataAttuale.get(GregorianCalendar.HOUR_OF_DAY)+"-"+dataAttuale.get(GregorianCalendar.MINUTE));

        //Dopo che un film � iniziato non aggiungerlo alla lista delle proiezioni del token

       if(!dataProiezione.after(dataAttuale)){
         if (i < listaProiezioni.size()){
              indiceProiezione = i+1;

           }

       }
       else{


        Film film=null;
        Collezione listaFilm = null;

        //leggi l'oggetto Film dal database con id=pro.getIDFilm()
        try {
          listaFilm = controller.leggi_info_Film(pro.getIDFilm());
        }
        catch (Exception e) {
           System.out.println("Errore in frmVisDispPosti:");
           e.printStackTrace();
        }
        try {
          film = ((Film) (listaFilm.getIndex(0)));
        }
        catch (Exception e) {
          System.out.println("Errore in frmVisDispPosti:");
          e.printStackTrace();
        }

        Sala sala=null;
        Collezione listaSala = null;

        //leggi l'oggetto Sala dal database con id=pro.getIDSala()
        try {
          listaSala = controller.leggi_info_Sala(pro.getIDSala());

        }
        catch (Exception e) {
          System.out.println("Errore in frmVisDispPosti:");
          e.printStackTrace();
        }

        try {
           sala = (Sala) listaSala.getIndex(0);
        }
        catch (Exception e) {
          System.out.println("Errore in frmVisDispPosti:");
          e.printStackTrace();
        }

        //calcola per la proiezione il numero di posti disponibili
        int disponibilita = sala.getcapacita() - pro.getBigliettiVenduti();
        String[] visualizza = new String[4];
        //copia i valori in un array di stringhe da restituire in una collezione
        visualizza[0] = pro.getIDSala();
        visualizza[1] = film.getTitolo();
        visualizza[2] = "" + disponibilita;
        String minuti;
        String ora;

        //formatta l'orario da restituire
        if(pro.getDataOraInizio().get(Calendar.HOUR_OF_DAY)<10)
         ora="0"+pro.getDataOraInizio().get(Calendar.HOUR_OF_DAY);
       else
         ora=""+pro.getDataOraInizio().get(Calendar.HOUR_OF_DAY);

        if(pro.getDataOraInizio().get(Calendar.MINUTE)<10)
          minuti="0"+pro.getDataOraInizio().get(Calendar.MINUTE);
        else
          minuti=""+pro.getDataOraInizio().get(Calendar.MINUTE);


       visualizza[3]=ora+":"+minuti;

       //aggiungi questo array di stringhe in una collezione da restituire
       proiezioniToken.add(visualizza);
       }
      }

      //chiama la funzione VisInfospettacoli x visualizzare le proiezioni sul form
       this.VisInfoSpettacoli(proiezioniToken);
       trd.sleep(5000);

      }catch(Exception e){
          System.out.println("Errore in frmVisDispPosti:");
          e.printStackTrace();
      }
      }
  }




   /*Viene utilizzato un timer per visualizzare l'ora attuale sul pnlOrario*/
   class Orario implements ActionListener
   {
     public Orario(JLabel label){
       lblOrario=label;

     }
      public void actionPerformed(ActionEvent event)
      {
       orario=new GregorianCalendar();
       String minuti;
       String ora;

      //formatta orario
       if(orario.get(Calendar.HOUR_OF_DAY)<10)
        ora="0"+orario.get(Calendar.HOUR_OF_DAY);
      else
        ora=""+orario.get(Calendar.HOUR_OF_DAY);

       if(orario.get(Calendar.MINUTE)<10)
         minuti="0"+orario.get(Calendar.MINUTE);
       else
         minuti=""+orario.get(Calendar.MINUTE);
       //visualizzalo nella lblOrario
       lblOrario.setText(ora+":"+minuti);
      }
      JLabel lblOrario;
      GregorianCalendar orario;
   }


}
