var DireccionRecibir; 
var httpTextoRecibido = getHTTPObject();
var id_campo;
var pagina;
var condicion;
var estatico;
var contenido;
var nombre;
var valor;

//--------------------------------Inicio Combo Modificacion------------
function llamarAjaxModificacion(id_campo,valor,estatico) {
        this.DireccionRecibir=document.getElementById("urlRecibir").value;;
        this.id_campo = id_campo;
        this.estatico = estatico;
        this.valor = valor;
        cargarTextoModificacion();
}

function cargarTextoModificacion() {
	if (httpTextoRecibido.readyState == 4 || httpTextoRecibido.readyState == 0) {
  	  httpTextoRecibido.open("GET",DireccionRecibir + '?id_campo=' + id_campo +'&estatico=' + estatico + '&valor='+valor, true);
          httpTextoRecibido.onreadystatechange = manejoTextoRecibidoModificar;
  	  httpTextoRecibido.send(null);
	}
}

function manejoTextoRecibidoModificar() {
  if ((httpTextoRecibido.readyState==4)&&(httpTextoRecibido.status==200)) {
    var oDocumento = httpTextoRecibido.responseXML.documentElement;  
    var nombre1 = oDocumento.getElementsByTagName('nombre')[0].firstChild.data;
    var nombre = document.getElementById(nombre1);
    var pagina = oDocumento.getElementsByTagName('pagina')[0].firstChild.data;
    document.getElementById("pagina_" + id_campo).value=pagina;
    //alert(id_campo + "--" + document.getElementById("pagina_" + id_campo).value);
    var cantidad = oDocumento.getElementsByTagName('cantidad')[0].firstChild.data;
    for (i=0;i< oDocumento.getElementsByTagName('combo').length;i++) {
      var id_combo = oDocumento.getElementsByTagName('combo')[i].getAttribute("id");
      var combo = oDocumento.getElementsByTagName('combo')[i].firstChild.data;
      nombre.options[i] = new Option(combo,id_combo);
      if (valor == id_combo)
        nombre.options[i].selected=true;
        crearHidden(nombre1+id_combo,combo);
    }
  }
}
//--------------------------------Fin Combo Modificacion------------


//--------------------------------Inicio Combo------------
function llamarAjax(id_campo,pagina,condicion,estatico) {
        this.DireccionRecibir=document.getElementById("urlRecibir").value;;
        this.id_campo = id_campo;
        this.pagina = pagina;
        this.condicion = condicion;
        this.estatico = estatico;
        cargarTexto();
}

function limpiarCombo(objeto) {
  if(objeto!='')
    for (m=objeto.options.length-1;m>0;m--){
      objeto.options[m]=null;
    }
}

function cargarTexto() {
	if (httpTextoRecibido.readyState == 4 || httpTextoRecibido.readyState == 0) {
  	httpTextoRecibido.open("GET",DireccionRecibir + '?id_campo=' + id_campo +'&pagina='+pagina+'&condicion='+condicion+'&estatico='+estatico, true);
        httpTextoRecibido.onreadystatechange = manejoTextoRecibido; 
  	httpTextoRecibido.send(null);
	}
}

function manejoTextoRecibido() {
  if (httpTextoRecibido.readyState == 4) {
    var oDocumento = httpTextoRecibido.responseXML.documentElement;  
    var nombre1 = oDocumento.getElementsByTagName('nombre')[0].firstChild.data;
    var nombre = document.getElementById(nombre1);
    limpiarCombo(nombre);
    var cantidad = oDocumento.getElementsByTagName('cantidad')[0].firstChild.data;
    if ((pagina > 1)&&(cantidad<2)) {
      document.getElementById("sig_" + id_campo).style.visibility='hidden';
    } else {
      if (pagina < 1){
      document.getElementById("ant_" + id_campo).style.visibility='hidden';      
      } else {
       document.getElementById("sig_" + id_campo).style.visibility='visible';       
       document.getElementById("ant_" + id_campo).style.visibility='visible';      
      }
    }
    for (i=0;i< oDocumento.getElementsByTagName('combo').length;i++) {
      var id_combo = oDocumento.getElementsByTagName('combo')[i].getAttribute("id");
      var combo = oDocumento.getElementsByTagName('combo')[i].firstChild.data;
      nombre.options[i] = new Option(combo,id_combo);
      crearHidden(nombre1+id_combo,combo);
    }
  }
}

function crearHidden(nombre2,valor2) {
      var nHidden = document.createElement("input");
      nHidden.setAttribute("type", "hidden");
      nHidden.setAttribute("name", nombre2);
      nHidden.setAttribute("value", valor2);
      var form = document.getElementsByTagName("form")[0];
      form.appendChild(nHidden);
}

//---------------------------Fin Combo----------------------------------
function getHTTPObject() {
  var xmlhttp;
  var ids = ["Msxml2.XMLHTTP.5.0","Msxml2.XMLHTTP.4.0", "Msxml2.XMLHTTP.3.0","Msxml2.XMLHTTP", "Microsoft.XMLHTTP"]; 
  for(var i=0; !xmlhttp && i<ids.length; i++) { 
    try { xmlhttp = new ActiveXObject(ids[i]); } 
    catch(ex) { xmlhttp = false; }
  }
  if (!xmlhttp && typeof XMLHttpRequest != 'undefined') {
    try {
      xmlhttp = new XMLHttpRequest();
    } catch (e) {
      xmlhttp = false;
    }
  }
  return xmlhttp;
}