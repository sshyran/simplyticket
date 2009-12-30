/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author William The Bloody
 */
public class Poltrona implements Serializable {

    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Poltrona() {
    }


}
