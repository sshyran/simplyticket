-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generato il: 26 ott, 2009 at 06:37 
-- Versione MySQL: 5.1.37
-- Versione PHP: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `db_simplyticket`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `abbonamento`
--

CREATE TABLE IF NOT EXISTS `abbonamento` (
  `Codice` varchar(20) NOT NULL,
  `Costo` double(10,2) DEFAULT NULL,
  `Tipo` varchar(20) DEFAULT NULL,
  `Scadenza` varchar(35) DEFAULT NULL,
  `FilmDisponibili` int(11) DEFAULT NULL,
  `IDAbbonato` int(11) DEFAULT NULL,
  PRIMARY KEY (`Codice`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `abbonamento`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `abbonato`
--

CREATE TABLE IF NOT EXISTS `abbonato` (
  `IDPersona` int(11) NOT NULL,
  PRIMARY KEY (`IDPersona`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `abbonato`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `addetto`
--

CREATE TABLE IF NOT EXISTS `addetto` (
  `IDAddetto` int(11) NOT NULL AUTO_INCREMENT,
  `Login` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Bigliettaio` tinyint(1) NOT NULL,
  `IDPersona` int(11) NOT NULL,
  PRIMARY KEY (`IDAddetto`),
  UNIQUE KEY `Login` (`Login`),
  UNIQUE KEY `Login_2` (`Login`),
  UNIQUE KEY `Login_3` (`Login`),
  UNIQUE KEY `Login_4` (`Login`),
  UNIQUE KEY `Login_5` (`Login`),
  UNIQUE KEY `Login_6` (`Login`),
  UNIQUE KEY `Login_7` (`Login`),
  UNIQUE KEY `Login_8` (`Login`),
  UNIQUE KEY `Login_9` (`Login`),
  UNIQUE KEY `Login_10` (`Login`),
  UNIQUE KEY `Login_11` (`Login`),
  UNIQUE KEY `Login_12` (`Login`),
  UNIQUE KEY `Login_13` (`Login`),
  UNIQUE KEY `Login_14` (`Login`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dump dei dati per la tabella `addetto`
--

INSERT INTO `addetto` (`IDAddetto`, `Login`, `Password`, `Bigliettaio`, `IDPersona`) VALUES
(1, 'mrossi', 'mrossi', 1, 1),
(2, 'frossi', 'frossi', 0, 3);

-- --------------------------------------------------------

--
-- Struttura della tabella `biglietto`
--

CREATE TABLE IF NOT EXISTS `biglietto` (
  `IDBiglietto` varchar(20) NOT NULL,
  `CostoIntero` double(4,2) NOT NULL,
  `CostoRidotto` double(4,2) NOT NULL,
  `IDProiezione` varchar(20) NOT NULL,
  `IDAbbonamento` varchar(20) DEFAULT NULL,
  `IDFila` varchar(2) NOT NULL,
  `IDPosto` int(11) NOT NULL,
  `IDAddetto` int(11) NOT NULL,
  PRIMARY KEY (`IDBiglietto`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `biglietto`
--

INSERT INTO `biglietto` (`IDBiglietto`, `CostoIntero`, `CostoRidotto`, `IDProiezione`, `IDAbbonamento`, `IDFila`, `IDPosto`, `IDAddetto`) VALUES
('000040101', 5.00, 0.00, '00004', '', '1', 1, 1),
('000040419', 5.00, 0.00, '00004', '', '4', 19, 1),
('000040119', 5.00, 0.00, '00004', '', '1', 19, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `fila`
--

CREATE TABLE IF NOT EXISTS `fila` (
  `ID` int(3) NOT NULL,
  `NumeroFila` varchar(2) NOT NULL,
  `DimensioneFila` int(11) DEFAULT NULL,
  `IDSala` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`ID`,`IDSala`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `fila`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `film`
--

CREATE TABLE IF NOT EXISTS `film` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Titolo` varchar(35) DEFAULT NULL,
  `Descrizione` text,
  `Genere` varchar(35) DEFAULT NULL,
  `Regia` varchar(35) DEFAULT NULL,
  `Attori` text,
  `Paese` varchar(40) DEFAULT NULL,
  `Locandina` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dump dei dati per la tabella `film`
--

INSERT INTO `film` (`ID`, `Titolo`, `Descrizione`, `Genere`, `Regia`, `Attori`, `Paese`, `Locandina`) VALUES
(1, 'Viola di mare', 'In una piccola isola immaginaria della Sicilia ottocentesca, durante lo sbarco dei Mille, la venticinquenne Angela cerca di sopravvivere allo scandalo della propria omosessualità, accettando di fingersi uomo. Rinchiusa in una grotta dal padre-padrone, dopo il suo rifiuto a sposare l''uomo scelto per lei, la donna viene salvata con uno stratagemma dalla madre, che convince il curato a cambiarle nome e sesso sulle carte dell''anagrafe. Angela diventa Angelo: coppola, sigaro in bocca, una famiglia benedetta dal Signore...', 'Drammatico', 'Donatella Maiorca', 'Valeria Solarino, Isabella Ragonese, Ennio Fantastichini, Giselda Volodi, Marco Foschi, Alessio Vassallo, Aurora Quattrocchi, Lucrezia Lante della Rovere, Maria Grazia Cucinotta', 'Italia 2008', 'C:\\simplyticket\\2052.jpg'),
(2, 'Io, Don Giovanni', 'Venezia, 1763. In una chiesa colma di fedeli e di curiosi si sta per compiere il rito del battesimo. Un vescovo presiede la cerimonia. Giacomo Casanova, seduto tra la folla, osserva con disapprovazione il battesimo dei quattro ebrei. Tra di loro c’è anche un ragazzo, Emanuele Conegliano. Il giovane non è convinto di voler passare nel "grembo della Santa Madre Chiesa", ma il vescovo, notata la curiosità del ragazzo verso il testo della Divina Commedia trovato nella sacrestia, lo convince ad accettare, promettendogli l''accesso all’intera biblioteca. Una volta battezzato, il ragazzo diventa Lorenzo da Ponte? Lorenzo prende gli ordini da sacerdote, ma l''amicizia con Casanova gli permette di non rinunciare al suo animo libertino. Frequenta donne, prostitute, ma soprattutto usa la sua dote di poeta per scrivere e diffondere versi contro la Chiesa ed il potere dell''Inquisizione. Dopo una serata in una sala da gioco in compagnia di un vecchio giocatore, Lorenzo conosce Annetta, sua figlia, di cui si innamora follemente. Lorenzo giura di accudire e proteggere Annetta, ma non appena fatta la promessa torna in sé e fugge dal palazzo vittima del senso di colpa. Esiliato dlla Santa Inquisizione con l''accusa di appartenere ad una società massonica e di aver agito contro il volere della Chiesa, Lorenzo si trasferisce a Vienna, città dalla mentalità molto più liberale. Qui conosce le due cantanti migliori di Vienna, la Ferrarese e la Cavalieri, il maestro Salieri e l''imperatore Giuseppe II che, dimostrando subito simpatia verso il prete libertino, gli propone di occuparsi del libretto de "Le nozze di Figaro" di Mozart. Il primo lavoro di Lorenzo è un successo. Il teatro è colmo di gente, tutti lo applaudono. Ad assistere allo spettacolo, tra la folla, c''è Annetta. Passano gli anni. Lorenzo e la sua compagna, la cantante Ferrarese, si dirigono presso il Castello di Dux, dove incontrano Casanova, che spinge Lorenzo a ritornare a collaborare con Mozart, consigliandogli di realizzare una nuova versione del Don Giovanni. Inizia così una nuova, strettissima collaborazione con il maestro. Le prove a teatro sono per Lorenzo e Mozart un momento di grande estro creativo? e durante le prove Lorenzo ritrova Annetta, divenuta nel frattempo allieva di Mozart. Tra i due scoppia nuovamente l''amore, ma per Annetta è troppo tardi: ormai è promessa sposa. Lorenzo lascia la Ferrarese per dedicarsi solamente alla riconquista di Annetta? E l''immagine di Don Giovanni cambia: non è più come Casanova, un libertino amante della libertà e della passione, bensì come Lorenzo, un uomo che dopo una vita di vizi incontra il vero amore e lo sposa. I due innamorati finiscono per perdonarsi, ma proprio nel momento decisivo arriva una missiva. Si tratta dell''elenco, che Casanova ha dato alla Ferrarese, di tutte le donne che Lorenzo ha avuto nella sua vita. Sentitasi tradita, Annetta caccia Lorenzo dalla propria casa e dalla propria vita. Il rapporto tra Mozart e Lorenzo si fa sempre più stretto, diventando ognuno la spalla dell’altro: Lorenzo è vicino a Mozart, le cui condizioni di salute peggiorano, nel momento della morte del padre; Mozart è vicino a Lorenzo nella difficile riconquista di Annetta. Durante le prove, l’amore di Don Giovanni e Zerlina si mescola e si sovrappone a quello di Lorenzo e Annetta.', 'Drammatico', 'Carlos Saura', 'Lorenzo Balducci, Tobias Moretti, María Valverde, Ennio Fantastichini, Lino Guanciale, Francesco Barilli, Emilia Verginelli', 'Germania, Italia 200', 'C:\\simplyticket\\47539.jpg'),
(3, 'The Imaginarium of Doctor Parnassus', 'Il Dottor Parnassus (Christopher Plummer), millenario personaggio immortale vecchio, viaggia per il mondo alla guida del suo straordinario “Imaginarium”, sorta di fiera itinerante che offre ai suoi spettatori la possibilità di vivere esperienze fantastiche e che travalicano la normale realtà grazie ad uno specchio magico in suo possesso. Parnassus è anche in grado di guidare l’immaginazione del prossimo grazie ad un patto che ha stretto con Mr. Nick (Tom Waits), alias il Diavolo in persona. Il problema è che Mr. Nick inizia a pretendere da Parnassus il compenso che gli era stato promesso, ovvero l’anima (e non solo) dell’affascinante figlia Valentina (la modella Lily Cole). Per evitare di perdere l’amata figlia Parnassus negozia un nuovo patto: Valentina sarà del primo tra i due in grado di sedurre cinque anime. Ad aiutare Parnassus nella sua opera sarà un nuovo arrivato dell’Imaginarium, Tony, che dovrà affrontare viaggi attraverso mondi paralleli pur di salvare la bella Valentina. Il personaggio di Tony era in origine stato affidato a Heath Ledger: alla morte di quest’ultimo, Gilliam ha deciso mantenere il girato con l’attore e per le nuove scene di affidare il personaggio a tre diversi attori - Johnny Depp, Colin Farrell e Jude Law – dando così a Tony un volto diverso per ogni mondo che è costretto ad attraversare.', 'Fantasy, Avventura', 'Terry Gilliam', 'Christopher Plummer, Heath Ledger, Johnny Depp, Colin Farrell, Jude Law, Simon Day, Tom Waits, Lily Cole, Johnny Harris, Andrew Garfield, Richard Riddell, Verne Troyer, Paloma Faith', 'Canada, Francia, Gra', 'C:\\simplyticket\\1823.jpg');

-- --------------------------------------------------------

--
-- Struttura della tabella `palinsesto`
--

CREATE TABLE IF NOT EXISTS `palinsesto` (
  `ID` varchar(20) NOT NULL,
  `DataInizioValidità` datetime DEFAULT NULL,
  `DataFineValidità` datetime DEFAULT NULL,
  `IDSala` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `palinsesto`
--

INSERT INTO `palinsesto` (`ID`, `DataInizioValidità`, `DataFineValidità`, `IDSala`) VALUES
('00001', '2009-10-24 08:43:23', '2009-10-31 08:43:36', 'P01'),
('00002', '2009-10-24 08:43:53', '2009-10-31 08:43:57', 'P02');

-- --------------------------------------------------------

--
-- Struttura della tabella `persona`
--

CREATE TABLE IF NOT EXISTS `persona` (
  `ID` int(11) NOT NULL,
  `Nome` varchar(25) DEFAULT NULL,
  `Cognome` varchar(25) DEFAULT NULL,
  `Indirizzo` varchar(35) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Residenza` varchar(35) DEFAULT NULL,
  `Email` varchar(35) DEFAULT NULL,
  `Dipendente` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `persona`
--

INSERT INTO `persona` (`ID`, `Nome`, `Cognome`, `Indirizzo`, `Telefono`, `Residenza`, `Email`, `Dipendente`) VALUES
(1, 'Mario', 'Rossi', 'Via Roma 182', '02654125', 'Via Roma 182', 'm.rossi@email.it', 1),
(2, 'Maria', 'Bianchi', 'Via Garibaldi 12', '0278125', 'Via Garibaldi 12', 'm.bianchi@email.it', 0),
(3, 'Fabio', 'Rossi', 'Via Roma 182', '02654125', 'Via Roma 182', 'f.rossi@email.it', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `posto`
--

CREATE TABLE IF NOT EXISTS `posto` (
  `ID` int(11) NOT NULL,
  `Occupato` tinyint(1) NOT NULL DEFAULT '0',
  `IDFila` int(3) NOT NULL,
  `IDProiezione` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`,`IDFila`,`IDProiezione`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `posto`
--

INSERT INTO `posto` (`ID`, `Occupato`, `IDFila`, `IDProiezione`) VALUES
(1, 0, 1, '00001'),
(2, 0, 1, '00001'),
(3, 0, 1, '00001'),
(4, 0, 1, '00001'),
(5, 0, 1, '00001'),
(6, 0, 1, '00001'),
(7, 0, 1, '00001'),
(8, 0, 1, '00001'),
(9, 0, 1, '00001'),
(10, 0, 1, '00001'),
(11, 0, 1, '00001'),
(12, 0, 1, '00001'),
(13, 0, 1, '00001'),
(14, 0, 1, '00001'),
(15, 0, 1, '00001'),
(16, 0, 1, '00001'),
(17, 0, 1, '00001'),
(18, 0, 1, '00001'),
(19, 0, 1, '00001'),
(20, 0, 1, '00001'),
(1, 0, 2, '00001'),
(2, 0, 2, '00001'),
(3, 0, 2, '00001'),
(4, 0, 2, '00001'),
(5, 0, 2, '00001'),
(6, 0, 2, '00001'),
(7, 0, 2, '00001'),
(8, 0, 2, '00001'),
(9, 0, 2, '00001'),
(10, 0, 2, '00001'),
(11, 0, 2, '00001'),
(12, 0, 2, '00001'),
(13, 0, 2, '00001'),
(14, 0, 2, '00001'),
(15, 0, 2, '00001'),
(16, 0, 2, '00001'),
(17, 0, 2, '00001'),
(18, 0, 2, '00001'),
(19, 0, 2, '00001'),
(20, 0, 2, '00001'),
(1, 0, 3, '00001'),
(2, 0, 3, '00001'),
(3, 0, 3, '00001'),
(4, 0, 3, '00001'),
(5, 0, 3, '00001'),
(6, 0, 3, '00001'),
(7, 0, 3, '00001'),
(8, 0, 3, '00001'),
(9, 0, 3, '00001'),
(10, 0, 3, '00001'),
(11, 0, 3, '00001'),
(12, 0, 3, '00001'),
(13, 0, 3, '00001'),
(14, 0, 3, '00001'),
(15, 0, 3, '00001'),
(16, 0, 3, '00001'),
(17, 0, 3, '00001'),
(18, 0, 3, '00001'),
(19, 0, 3, '00001'),
(20, 0, 3, '00001'),
(1, 0, 4, '00001'),
(2, 0, 4, '00001'),
(3, 0, 4, '00001'),
(4, 0, 4, '00001'),
(5, 0, 4, '00001'),
(6, 0, 4, '00001'),
(7, 0, 4, '00001'),
(8, 0, 4, '00001'),
(9, 0, 4, '00001'),
(10, 0, 4, '00001'),
(11, 0, 4, '00001'),
(12, 0, 4, '00001'),
(13, 0, 4, '00001'),
(14, 0, 4, '00001'),
(15, 0, 4, '00001'),
(16, 0, 4, '00001'),
(17, 0, 4, '00001'),
(18, 0, 4, '00001'),
(19, 0, 4, '00001'),
(20, 0, 4, '00001'),
(1, 0, 5, '00001'),
(2, 0, 5, '00001'),
(3, 0, 5, '00001'),
(4, 0, 5, '00001'),
(5, 0, 5, '00001'),
(6, 0, 5, '00001'),
(7, 0, 5, '00001'),
(8, 0, 5, '00001'),
(9, 0, 5, '00001'),
(10, 0, 5, '00001'),
(11, 0, 5, '00001'),
(12, 0, 5, '00001'),
(13, 0, 5, '00001'),
(14, 0, 5, '00001'),
(15, 0, 5, '00001'),
(16, 0, 5, '00001'),
(17, 0, 5, '00001'),
(18, 0, 5, '00001'),
(19, 0, 5, '00001'),
(20, 0, 5, '00001'),
(1, 0, 1, '00003'),
(2, 0, 1, '00003'),
(3, 0, 1, '00003'),
(4, 0, 1, '00003'),
(5, 0, 1, '00003'),
(6, 0, 1, '00003'),
(7, 0, 1, '00003'),
(8, 0, 1, '00003'),
(9, 0, 1, '00003'),
(10, 0, 1, '00003'),
(11, 0, 1, '00003'),
(12, 0, 1, '00003'),
(13, 0, 1, '00003'),
(14, 0, 1, '00003'),
(15, 0, 1, '00003'),
(16, 0, 1, '00003'),
(17, 0, 1, '00003'),
(18, 0, 1, '00003'),
(19, 0, 1, '00003'),
(20, 0, 1, '00003'),
(1, 0, 2, '00003'),
(2, 0, 2, '00003'),
(3, 0, 2, '00003'),
(4, 0, 2, '00003'),
(5, 0, 2, '00003'),
(6, 0, 2, '00003'),
(7, 0, 2, '00003'),
(8, 0, 2, '00003'),
(9, 0, 2, '00003'),
(10, 0, 2, '00003'),
(11, 0, 2, '00003'),
(12, 0, 2, '00003'),
(13, 0, 2, '00003'),
(14, 0, 2, '00003'),
(15, 0, 2, '00003'),
(16, 0, 2, '00003'),
(17, 0, 2, '00003'),
(18, 0, 2, '00003'),
(19, 0, 2, '00003'),
(20, 0, 2, '00003'),
(1, 0, 3, '00003'),
(2, 0, 3, '00003'),
(3, 0, 3, '00003'),
(4, 0, 3, '00003'),
(5, 0, 3, '00003'),
(6, 0, 3, '00003'),
(7, 0, 3, '00003'),
(8, 0, 3, '00003'),
(9, 0, 3, '00003'),
(10, 0, 3, '00003'),
(11, 0, 3, '00003'),
(12, 0, 3, '00003'),
(13, 0, 3, '00003'),
(14, 0, 3, '00003'),
(15, 0, 3, '00003'),
(16, 0, 3, '00003'),
(17, 0, 3, '00003'),
(18, 0, 3, '00003'),
(19, 0, 3, '00003'),
(20, 0, 3, '00003'),
(1, 0, 4, '00003'),
(2, 0, 4, '00003'),
(3, 0, 4, '00003'),
(4, 0, 4, '00003'),
(5, 0, 4, '00003'),
(6, 0, 4, '00003'),
(7, 0, 4, '00003'),
(8, 0, 4, '00003'),
(9, 0, 4, '00003'),
(10, 0, 4, '00003'),
(11, 0, 4, '00003'),
(12, 0, 4, '00003'),
(13, 0, 4, '00003'),
(14, 0, 4, '00003'),
(15, 0, 4, '00003'),
(16, 0, 4, '00003'),
(17, 0, 4, '00003'),
(18, 0, 4, '00003'),
(19, 0, 4, '00003'),
(20, 0, 4, '00003'),
(1, 0, 5, '00003'),
(2, 0, 5, '00003'),
(3, 0, 5, '00003'),
(4, 0, 5, '00003'),
(5, 0, 5, '00003'),
(6, 0, 5, '00003'),
(7, 0, 5, '00003'),
(8, 0, 5, '00003'),
(9, 0, 5, '00003'),
(10, 0, 5, '00003'),
(11, 0, 5, '00003'),
(12, 0, 5, '00003'),
(13, 0, 5, '00003'),
(14, 0, 5, '00003'),
(15, 0, 5, '00003'),
(16, 0, 5, '00003'),
(17, 0, 5, '00003'),
(18, 0, 5, '00003'),
(19, 0, 5, '00003'),
(20, 0, 5, '00003'),
(1, 0, 1, '00002'),
(2, 0, 1, '00002'),
(3, 0, 1, '00002'),
(4, 0, 1, '00002'),
(5, 0, 1, '00002'),
(6, 0, 1, '00002'),
(7, 0, 1, '00002'),
(8, 0, 1, '00002'),
(9, 0, 1, '00002'),
(10, 0, 1, '00002'),
(11, 0, 1, '00002'),
(12, 0, 1, '00002'),
(13, 0, 1, '00002'),
(14, 0, 1, '00002'),
(15, 0, 1, '00002'),
(16, 0, 1, '00002'),
(17, 0, 1, '00002'),
(18, 0, 1, '00002'),
(19, 0, 1, '00002'),
(20, 0, 1, '00002'),
(1, 0, 2, '00002'),
(2, 0, 2, '00002'),
(3, 0, 2, '00002'),
(4, 0, 2, '00002'),
(5, 0, 2, '00002'),
(6, 0, 2, '00002'),
(7, 0, 2, '00002'),
(8, 0, 2, '00002'),
(9, 0, 2, '00002'),
(10, 0, 2, '00002'),
(11, 0, 2, '00002'),
(12, 0, 2, '00002'),
(13, 0, 2, '00002'),
(14, 0, 2, '00002'),
(15, 0, 2, '00002'),
(16, 0, 2, '00002'),
(17, 0, 2, '00002'),
(18, 0, 2, '00002'),
(19, 0, 2, '00002'),
(20, 0, 2, '00002'),
(1, 0, 3, '00002'),
(2, 0, 3, '00002'),
(3, 0, 3, '00002'),
(4, 0, 3, '00002'),
(5, 0, 3, '00002'),
(6, 0, 3, '00002'),
(7, 0, 3, '00002'),
(8, 0, 3, '00002'),
(9, 0, 3, '00002'),
(10, 0, 3, '00002'),
(11, 0, 3, '00002'),
(12, 0, 3, '00002'),
(13, 0, 3, '00002'),
(14, 0, 3, '00002'),
(15, 0, 3, '00002'),
(16, 0, 3, '00002'),
(17, 0, 3, '00002'),
(18, 0, 3, '00002'),
(19, 0, 3, '00002'),
(20, 0, 3, '00002'),
(1, 0, 4, '00002'),
(2, 0, 4, '00002'),
(3, 0, 4, '00002'),
(4, 0, 4, '00002'),
(5, 0, 4, '00002'),
(6, 0, 4, '00002'),
(7, 0, 4, '00002'),
(8, 0, 4, '00002'),
(9, 0, 4, '00002'),
(10, 0, 4, '00002'),
(11, 0, 4, '00002'),
(12, 0, 4, '00002'),
(13, 0, 4, '00002'),
(14, 0, 4, '00002'),
(15, 0, 4, '00002'),
(16, 0, 4, '00002'),
(17, 0, 4, '00002'),
(18, 0, 4, '00002'),
(19, 0, 4, '00002'),
(20, 0, 4, '00002'),
(1, 0, 5, '00002'),
(2, 0, 5, '00002'),
(3, 0, 5, '00002'),
(4, 0, 5, '00002'),
(5, 0, 5, '00002'),
(6, 0, 5, '00002'),
(7, 0, 5, '00002'),
(8, 0, 5, '00002'),
(9, 0, 5, '00002'),
(10, 0, 5, '00002'),
(11, 0, 5, '00002'),
(12, 0, 5, '00002'),
(13, 0, 5, '00002'),
(14, 0, 5, '00002'),
(15, 0, 5, '00002'),
(16, 0, 5, '00002'),
(17, 0, 5, '00002'),
(18, 0, 5, '00002'),
(19, 0, 5, '00002'),
(20, 0, 5, '00002'),
(1, 0, 6, '00002'),
(2, 0, 6, '00002'),
(3, 0, 6, '00002'),
(4, 0, 6, '00002'),
(5, 0, 6, '00002'),
(6, 0, 6, '00002'),
(7, 0, 6, '00002'),
(8, 0, 6, '00002'),
(9, 0, 6, '00002'),
(10, 0, 6, '00002'),
(11, 0, 6, '00002'),
(12, 0, 6, '00002'),
(13, 0, 6, '00002'),
(14, 0, 6, '00002'),
(15, 0, 6, '00002'),
(16, 0, 6, '00002'),
(17, 0, 6, '00002'),
(18, 0, 6, '00002'),
(19, 0, 6, '00002'),
(20, 0, 6, '00002'),
(1, 1, 1, '00004'),
(2, 0, 1, '00004'),
(3, 0, 1, '00004'),
(4, 0, 1, '00004'),
(5, 0, 1, '00004'),
(6, 0, 1, '00004'),
(7, 0, 1, '00004'),
(8, 0, 1, '00004'),
(9, 0, 1, '00004'),
(10, 0, 1, '00004'),
(11, 0, 1, '00004'),
(12, 0, 1, '00004'),
(13, 0, 1, '00004'),
(14, 0, 1, '00004'),
(15, 0, 1, '00004'),
(16, 0, 1, '00004'),
(17, 0, 1, '00004'),
(18, 0, 1, '00004'),
(19, 1, 1, '00004'),
(20, 0, 1, '00004'),
(1, 0, 2, '00004'),
(2, 0, 2, '00004'),
(3, 0, 2, '00004'),
(4, 0, 2, '00004'),
(5, 0, 2, '00004'),
(6, 0, 2, '00004'),
(7, 0, 2, '00004'),
(8, 0, 2, '00004'),
(9, 0, 2, '00004'),
(10, 0, 2, '00004'),
(11, 0, 2, '00004'),
(12, 0, 2, '00004'),
(13, 0, 2, '00004'),
(14, 0, 2, '00004'),
(15, 0, 2, '00004'),
(16, 0, 2, '00004'),
(17, 0, 2, '00004'),
(18, 0, 2, '00004'),
(19, 0, 2, '00004'),
(20, 0, 2, '00004'),
(1, 0, 3, '00004'),
(2, 0, 3, '00004'),
(3, 0, 3, '00004'),
(4, 0, 3, '00004'),
(5, 0, 3, '00004'),
(6, 0, 3, '00004'),
(7, 0, 3, '00004'),
(8, 0, 3, '00004'),
(9, 0, 3, '00004'),
(10, 0, 3, '00004'),
(11, 0, 3, '00004'),
(12, 0, 3, '00004'),
(13, 0, 3, '00004'),
(14, 0, 3, '00004'),
(15, 0, 3, '00004'),
(16, 0, 3, '00004'),
(17, 0, 3, '00004'),
(18, 0, 3, '00004'),
(19, 0, 3, '00004'),
(20, 0, 3, '00004'),
(1, 0, 4, '00004'),
(2, 0, 4, '00004'),
(3, 0, 4, '00004'),
(4, 0, 4, '00004'),
(5, 0, 4, '00004'),
(6, 0, 4, '00004'),
(7, 0, 4, '00004'),
(8, 0, 4, '00004'),
(9, 0, 4, '00004'),
(10, 0, 4, '00004'),
(11, 0, 4, '00004'),
(12, 0, 4, '00004'),
(13, 0, 4, '00004'),
(14, 0, 4, '00004'),
(15, 0, 4, '00004'),
(16, 0, 4, '00004'),
(17, 0, 4, '00004'),
(18, 0, 4, '00004'),
(19, 1, 4, '00004'),
(20, 0, 4, '00004'),
(1, 0, 5, '00004'),
(2, 0, 5, '00004'),
(3, 0, 5, '00004'),
(4, 0, 5, '00004'),
(5, 0, 5, '00004'),
(6, 0, 5, '00004'),
(7, 0, 5, '00004'),
(8, 0, 5, '00004'),
(9, 0, 5, '00004'),
(10, 0, 5, '00004'),
(11, 0, 5, '00004'),
(12, 0, 5, '00004'),
(13, 0, 5, '00004'),
(14, 0, 5, '00004'),
(15, 0, 5, '00004'),
(16, 0, 5, '00004'),
(17, 0, 5, '00004'),
(18, 0, 5, '00004'),
(19, 0, 5, '00004'),
(20, 0, 5, '00004'),
(1, 0, 6, '00004'),
(2, 0, 6, '00004'),
(3, 0, 6, '00004'),
(4, 0, 6, '00004'),
(5, 0, 6, '00004'),
(6, 0, 6, '00004'),
(7, 0, 6, '00004'),
(8, 0, 6, '00004'),
(9, 0, 6, '00004'),
(10, 0, 6, '00004'),
(11, 0, 6, '00004'),
(12, 0, 6, '00004'),
(13, 0, 6, '00004'),
(14, 0, 6, '00004'),
(15, 0, 6, '00004'),
(16, 0, 6, '00004'),
(17, 0, 6, '00004'),
(18, 0, 6, '00004'),
(19, 0, 6, '00004'),
(20, 0, 6, '00004');

-- --------------------------------------------------------

--
-- Struttura della tabella `proiezione`
--

CREATE TABLE IF NOT EXISTS `proiezione` (
  `ID` varchar(20) NOT NULL,
  `Data_Ora_Inizio` datetime DEFAULT NULL,
  `BigliettiVenduti` int(11) NOT NULL DEFAULT '0',
  `IDSala` varchar(20) NOT NULL,
  `IDFilm` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `proiezione`
--

INSERT INTO `proiezione` (`ID`, `Data_Ora_Inizio`, `BigliettiVenduti`, `IDSala`, `IDFilm`) VALUES
('00001', '2009-10-25 22:00:00', 0, 'P01', 3),
('00002', '2009-10-26 18:00:00', 0, 'P02', 2),
('00003', '2009-10-27 11:30:00', 0, 'P01', 1),
('00004', '2009-10-26 21:30:00', 3, 'P02', 3);

-- --------------------------------------------------------

--
-- Struttura della tabella `sala`
--

CREATE TABLE IF NOT EXISTS `sala` (
  `Nome` varchar(20) NOT NULL,
  `capacita` int(11) NOT NULL,
  `IDStruttura` varchar(20) NOT NULL,
  PRIMARY KEY (`Nome`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `sala`
--

INSERT INTO `sala` (`Nome`, `capacita`, `IDStruttura`) VALUES
('P01', 100, 'Odeon'),
('P02', 120, 'Odeon');

-- --------------------------------------------------------

--
-- Struttura della tabella `struttura`
--

CREATE TABLE IF NOT EXISTS `struttura` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(30) NOT NULL,
  `Indirizzo` varchar(50) NOT NULL,
  `Telefono` varchar(13) NOT NULL,
  `Sito` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dump dei dati per la tabella `struttura`
--

INSERT INTO `struttura` (`ID`, `Nome`, `Indirizzo`, `Telefono`, `Sito`) VALUES
(1, 'Odeon', 'Viale Mazzini 5', '', 'www.odeon.it');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
