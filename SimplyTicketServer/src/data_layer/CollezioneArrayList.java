package data_layer;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


/**
 * <p>Title: Data Layer</p>
 * <p>Description: Il Package contenente tutto il Data Layer;le classi in esso contenute, sono classi ke interagiscono direttamente con il database</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Spinelli Raffaele
 * @version 1.0
 * @see data_layer.Collezione
 */

/**
 * Questa classe implementa l'interfaccia Collezione utilizzando un TreeSet
 */

public class CollezioneArrayList implements Collezione, Serializable{

  /**
   * Costruisce un nuovo oggetto della classe e inizializza la strutura TreeSet
   */
  public CollezioneArrayList() {
    Struttura=new ArrayList();

  }

  /**
   *
   * @return boolean - Se esiste un altro elemento
   */
  public boolean hasNext() {
    if (iteratore==null)
      iteratore=Struttura.iterator();
    return iteratore.hasNext();
  }

  /**
   *
   * @return Object - IL prossimo oggetto nella collezione
   */
  public Object getNext() {
    if (iteratore==null)
      iteratore=Struttura.iterator();
    return iteratore.next();
  }

  /**
   *
   * @return int - Il numero di elementi nella collezione
   */
  public int size() {
    return Struttura.size();
  }

  /**
   *
   * @return boolean - Se nn ci sono più elementi
   */
  public boolean isEmpty() {
    return Struttura.isEmpty();
  }

  /**
   * @param o Object - L'oggetto da inserire nella collezione
   * @return boolean - Se è stato inserito o meno
   */
  public boolean add(Object o) {
    return Struttura.add(o);
  }

  /**
   * @param o Object - L'oggetto da rimuovere
   * @return boolean - Se è stato rimosso o no
   */
  public boolean remove(Object o) {
    return Struttura.remove(o);
  }

  /**
   * @return Iterator - L'iteratore della collezione
   */
  public Iterator iterator() {
    return Struttura.iterator();
  }

  public Object getIndex(int i) throws Exception{
    Object o;
    try {
      o=Struttura.get(i);
    }
    catch(IndexOutOfBoundsException e) {
      throw new Exception("Indice fuori dal range");
    }
    return o;
  }

  /**
   * Serializzazione dell'oggetto
   * @param oos ObjectOutputStream -
   * @throws IOException - viene lanciata se nn è possibile scrivere l'oggetto
   */

  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }

  /**
   * Serializzazione dell'oggetto
   * @param ois ObjectInputStream -
   * @throws IOException - viene lanciata se nn è possibile leggere l'oggetto
   * @throws ClassNotFoundException - viene lanciata se il cast fallisce
   */

  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }

  private List Struttura;
  private Iterator iteratore=null;
}
