/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function check(param) {

    var fila=document.forms['prenotaPosti'].sceltaFila.value;
    var posto=document.forms['prenotaPosti'].sceltaPosto.value;
    var my_div=document.getElementById(fila+"_"+posto);
    if (param==1) {
        if ((my_div.getAttribute("class")=="true")) {
            alert("Posto già occupato.");
            return false;
        }
        else {
            return true;
        }
    }
    if (param==2) {
        if ((my_div.getAttribute("class")=="false")) {
            alert("Posto non occupato.");
            return false;
        }
        else {
            return true;
        }
    }
}
