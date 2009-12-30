/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import java.io.Serializable;

/**
 *
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

    public String getLocandina() {
        return locandina;
    }

    public void setLocandina(String locandina) {
        this.locandina = locandina;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(int disponibilita) {
        this.disponibilita = disponibilita;
    }

    public String getOrario() {
        return orario;
    }

    public void setOrario(String orario) {
        this.orario = orario;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

}
