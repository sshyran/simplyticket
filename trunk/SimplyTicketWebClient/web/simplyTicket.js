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
function asd() {
    alert("Ciao");
    return true;
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
    var url = "http://localhost:8080/SimplyTicketWebClient/AjaxServlet";
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
    var url = "http://localhost:8080/SimplyTicketWebClient/AjaxServletBiglietteria";
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
    setInterval(ajax_fetch_places, 2000);
}

function ajax_fetch_places() {
    var idProiezione = document.prenotaPosti.idProiezione.value;
    var url = "http://localhost:8080/SimplyTicketWebClient/AjaxServletVisualizzaPosti?film="+idProiezione;
    var d = AJS.getRequest(url);
    d.addCallbacks(
     function(res_txt, req) {
         var res=AJS.evalTxt("("+res_txt+")");
         var table = AJS.$("platea");
         var table_p = AJS.$("mainContent_mod");
         var table_new="<table id=\"platea\" align=\"center\">\n\t<tr><td>1</td>";
         var num_fila=1;
         for each (f in res.lista) {
             if (num_fila==f.posto.fila) {
                 table_new+="<td><div id=\""+f.posto.fila+"_"+f.posto.id+"\" class="+f.posto.occupato+">"+f.posto.id+"</div></td>"
                 if (f.posto.id==10){
                     table_new+="<td style=\"background-color: orange; \">&nbsp&nbsp</td>";
                 }
             }
             else {
                 num_fila=f.posto.fila;
                 table_new+="</tr>\n<tr><td>"+num_fila+"</td><td><div id=\""+f.posto.fila+"_"+f.posto.id+"\" class="+f.posto.occupato+">"+f.posto.id+"</div></td>"
             }
         }
         table_new+="</tr></table>";
         AJS.removeElement(table);
//         alert(table_new);
         AJS.setHTML(table_p,table_new);
     },
     function(res_txt, req) {
         //alert("Error encountered on fetching new data");
     }
    )
    d.sendReq();
    return true;
}