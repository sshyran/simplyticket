/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import java.io.Serializable;

/**
 * Questa classe è un beans per rappresentare una proiezione
 * @author Ser
 */
public class ProiezioneGiornaliera implements Serializable {

    public static final String PROP_TITOLO = "titolo";
    public static final String PROP_ORARIO = "orario";
    public static final String PROP_DISPONIBILITA = "disponibilita";
    public static final String PROP_SALA= "sala";

    private String titolo;
    private String sala;
    private int disponibilita;
    private String orario;
    private String id;
    private String locandina;

    /**
     *
     * @return il path dell'immagine della locandina
     */
    public String getLocandina() {
        return locandina;
    }

    /**
     * Cambia il path dove è immaganizzata la locandina
     * @param locandina Il nuovo path della locandina
     */
    public void setLocandina(String locandina) {
        this.locandina = locandina;
    }

    /**
     *
     * @return restituisce l'id della proiezione
     */
    public String getId() {
        return id;
    }

    /**
     * Cambia l'id della proiezione
     * @param id Ilnuovo id della proiezione
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return La disponibilità di posti per la proiezione
     */
    public int getDisponibilita() {
        return disponibilita;
    }

    /**
     * Cambia la disponibilità dei posti
     * @param disponibilita La nuovo disponibilità
     */
    public void setDisponibilita(int disponibilita) {
        this.disponibilita = disponibilita;
    }

    /**
     * L'orario di inizio della proiezione
     * @return
     */
    public String getOrario() {
        return orario;
    }

    /**
     * Cambia l'orario di inizio della proiezione
     * @param orario Il nuovo orario di inizio della proiezione
     */
    public void setOrario(String orario) {
        this.orario = orario;
    }

    /**
     *
     * @return La sala dove ha luogo la proiezione
     */
    public String getSala() {
        return sala;
    }

    /**
     * Questo metodo serve per cambiare la sala dove ha luogo la proiezione
     * @param sala La nuova sala dove avrà luogo la proiezione
     */
    public void setSala(String sala) {
        this.sala = sala;
    }

    /**
     *
     * @return Il titolo della del film proiettato
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Questo metodo cambia il titolo per la proiezione
     * @param titolo Il nuovo titolo della proiezione
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

}
