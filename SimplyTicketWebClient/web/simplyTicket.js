/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var timerID;

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

function asd() {
    alert("Ciao");
    return true;
}

function echo(id) {
    var div=document.getElementById(id);
    if (div.getAttribute("class")!="reserved") {
        div.setAttribute("title", div.getAttribute("class"));
        div.setAttribute("class", "reserved");
    }
    else {
        div.setAttribute("class", div.getAttribute("title"));
    }
}

function getCheckedValue(radioObj) {
        if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}


function start_fetching() {
    setInterval(ajax_fetch_film, 5000);
}

function ajax_fetch_film() {
    var url = "/SimplyTicketWebClient/AjaxServlet";
    var d = AJS.getRequest(url,null,"get");
    d.addCallbacks(
     function(res_txt, req) {
         var res=AJS.evalTxt("("+res_txt+")");
         var table = AJS.$("tabellone");
         var table_p = AJS.$("mainContent");
         var table_new="<table id=\"tabellone\" align=\"center\">\n\t<thead>\n\t<tr>\n\t<th>Sala</th>\n\t<th>Titolo Film</th>\n\t<th>Disponibilità</th>\n\t<th>Inizio</th>\n\t</tr>\n\t</thead>\n\t<tbody>";
         for each (f in res.lista) {
             table_new+="<tr>\n\t<td>"+f.proiezione.sala+"</td><td>"+f.proiezione.titolo+"</td><td>"+f.proiezione.disponibilita+"</td><td>"+f.proiezione.inizio+"</td></tr>";
         }
         table_new+="</tbody></table>";
         AJS.removeElement(table);
         AJS.setHTML(table_p,table_new);
     },
     function(res_txt, req) {
         alert("Error encountered on fetching new data.")
     }
    )
    d.sendReq();
    return true;
}

function start_fetching_biglietteria() {
    setInterval(ajax_fetch_film_biglietteria, 5000);
}

function ajax_fetch_film_biglietteria() {
    var url = "/SimplyTicketWebClient/AjaxServletBiglietteria";
    var d = AJS.getRequest(url,null,"get");
    d.addCallbacks(
     function(res_txt, req) {
         var oldFilmSelected=getCheckedValue(document.visualizzaPosti.film);
         var res=AJS.evalTxt("("+res_txt+")");
         var table = AJS.$("tabellone");
         var table_p = AJS.$("mainContent");
         var table_new="<table id=\"tabellone\" align=\"center\">\n\t<thead>\n\t<tr>\n\t<th></th>\n\t<th>Sala</th>\n\t<th>Titolo Film</th>\n\t<th>Disponibilità</th>\n\t</tr>\n\t</thead>\n\t<tbody>";
         for each (f in res.lista) {
             table_new+="<input type=\"hidden\" name=\"locandina"+f.proiezione.id+"\" value=\""+f.proiezione.locandina+"\" />";
             table_new+="<tr>\n\t<td><input type=\"radio\" name=\"film\" value=\""+f.proiezione.id+"\" ";
             if (f.proiezione.id==oldFilmSelected) {
                 table_new+="checked";
             }
             table_new+=" /></td><td>"+f.proiezione.sala+"</td><td>"+f.proiezione.titolo+"</td><td>"+f.proiezione.disponibilita+"</td></tr>";
         }
         table_new+="</tbody></table>";
         AJS.removeElement(table);
         AJS.setHTML(table_p,table_new);
     },
     function(res_txt, req) {
         alert("Error encountered on fetching new data.")
     }
    )
    d.sendReq();
    return true;
}

function start_fetching_places() {
    timerID=setInterval(ajax_fetch_places, 2000);
}

function ajax_fetch_places() {
    var idProiezione = document.prenotaPosti.idProiezione.value;
    var url = "/SimplyTicketWebClient/AjaxServletVisualizzaPosti?film="+idProiezione;
    var d = AJS.getRequest(url);
    d.addCallbacks(
     function(res_txt, req) {
         var res=AJS.evalTxt("("+res_txt+")");
         var table_p = AJS.$("platea");
         for each (f in res.lista) {
             var div=document.getElementById(f.posto.fila+"_"+f.posto.id);
             if (div.getAttribute("class")!="reserved") {
                 div.setAttribute("class", f.posto.occupato);
                 div.focus();
             }
             else {
                 if (div.getAttribute("title")=="true" && f.posto.occupato=="false") {
                     alert("Il posto è stato reso di nuovo disponibile da un altro operatore.");
                     div.setAttribute("class", f.posto.occupato);
                     div.setAttribute("title", f.posto.occupato);
                 }
                 if (div.getAttribute("title")=="false" && f.posto.occupato=="true") {
                     alert("Il posto è stato venduto da un altro operatore.");
                     div.setAttribute("class", f.posto.occupato);
                     div.setAttribute("title", f.posto.occupato);
                 }
             }
         }
         table_new+="</tr></table>";
         AJS.removeElement();
         AJS.setHTML(table_p,table_new);
     },
     function(res_txt, req) {
         alert("Error encountered on fetching new data");
     }
    )
    d.sendReq();
    return true;
}

function submit_places() {
    clearInterval(timerID);
    var no_places=true;
    var table_p = AJS.$("platea");
    var divs=AJS.getElementsByTagAndClassName("div", "reserved", table_p,null);
    var proiezione = document.prenotaPosti.idProiezione.value;
    var param="{\"lista\":\n\t";
    var param_buy="{\"prenota\":\n\t\t[\n";
    var param_refuse="\"rimborsa\":\n\t\t[\n";
    var not_first_buy=false;
    var not_first_refuse=false;
    var temp;
    for each (f in divs) {
        if (f.getAttribute("title")=="false") {
            //add to something
            if (not_first_buy) {
                param_buy+=",";
            }
            no_places=false;
            temp=f.getAttribute("id");
            param_buy+="\t\t{\"posto\":\n\t\t{\n\t\t\t\"proiezione\":\""+proiezione+"\",\n\t\t\t\"id\":\""+temp.substring(temp.indexOf('_')+1)+"\",\n\t\t\t\"fila\":\""+temp.substring(0,temp.indexOf('_'))+"\"\n\t\t\t}\n\t\t}";
            not_first_buy=true;
        }
        else {
            //add to something
            if (not_first_refuse) {
                param_refuse+=",";
            }
            no_places=false;
            temp=f.getAttribute("id");
            param_refuse+="\t\t{\"posto\":\n\t\t{\n\t\t\t\"proiezione\":\""+proiezione+"\",\n\t\t\t\"id\":\""+temp.substring(temp.indexOf('_')+1)+"\",\n\t\t\t\"fila\":\""+temp.substring(0,temp.indexOf('_'))+"\"\n\t\t\t}\n\t\t}";
            not_first_refuse=true;
        }
    }
    param_buy+="],";
    param_refuse+="]";
    param+=param_buy+param_refuse+"\n\t}\n}";
    var dati=document.prenotaPosti.dati;
    dati.value=param;
    var intero=document.forms['prenotaPosti'].txtCostoIntero.value;
    var ridotto=document.forms['prenotaPosti'].txtCostoRidotto.value;
    if (!intero.match(/^[1-9]\d*(\.\d{1,2})?$/)) {
        alert("Costo biglietto intero non valido");
        return false;
    }
    if (!ridotto.match(/^[1-9]\d*(\.\d{1,2})?$/)) {
        alert("Costo biglietto ridotto non valido");
        return false;
    }
    if (no_places){
        alert("Non hai selezionato alcun posto.");
        return false;
    }
    return true;
}
