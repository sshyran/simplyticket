package data_layer;
import java.util.Collection;

/**
 * <p>Title: Data Layer</p>
 * <p>Description: Il Package contenente tutto il Data Layer;le classi in esso contenute, sono classi ke interagiscono direttamente con il database</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Spinelli Raffaele
 * @version 1.0
 * @see java.utl.Collection
 */

/**
 * Questa interfaccia serve per mascherare la reale struttura dati che viene utilizata per memorizzare le collezzioni d'oggetti
 */

public abstract interface Collezione {

  /**
   * @return <d>boolean<d> - se è presente o no un altro elemento
   */
  public boolean hasNext();

  /**
   * @return <d>Object<d> - Il Prossimo elemento nela collezione
   */
  public Object getNext();

  /**
   * @param o Object - L'oggetto da inserire
   * @return <d>boolean<d> - Se l'elemento è stato inserito o no
   */
  public boolean add(Object o);

  /**
   * @param o Object - L'oggetto da rimuovere
   * @return <d>Object<d> - Se l'elemento è stato rimosso dalla struttura
   */
  public boolean remove(Object o);

  /**
   * @return int - Il numero di oggetti contenuti nella struttura
   */

  public int size();

  /**
   *
   * @return boolean - Se la struttura dati è vuota oppure no
   */

  public boolean isEmpty();

  /**
   *
   * @param i int - la posizione dell'oggetto all'interno della struttura
   * @throws Exception - nel caso l'indice sia fuori dal range
   * @return Object - L'oggetto alla posizione i
   */

  public Object getIndex(int i) throws Exception;

}
