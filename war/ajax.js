calFormat = "dd/mm/yyyy";
  var combo = new Array();
  var padre_hijo = new Array();
  h = 0;
  function dominios(nombre){
    valor=-1;
    for (d=0;d<padre_hijo.length;d++){
      if(padre_hijo[d][0]==nombre)
        valor=d;
    }
    return valor;
  }


  function buscarHijo(padre){
    var hijo = new Array();
    k = 0;
    for (i=0;i<padre_hijo.length;i++){
      if (padre_hijo[i][1]==padre){
        hijo[k] = padre_hijo[i][0];
        k++;
      }
    }
    return hijo;
  }

  function limpiarHijo(objeto){
    if(objeto!='')
      for(i=0;i<objeto.length;i++){
        limpiarPadre(objeto[i]);
      }
  }


  function limpiarPadre(objeto){
    if (eval("typeof(document.getElementById(objeto))!='undefined'")){
      combito = document.getElementById(objeto);
      if(combito.options.length !=null)
        for (m=combito.options.length-1;m>0;m--)
          combito.options[m]=null;
          combito.options[0]= new Option(" - Elija una opción - ","0");
    }
    objetos=buscarHijo(objeto);
    limpiarHijo(objetos);
  }

  function poblar(nombre, filtro){
    dominio = dominios(nombre);
    hijos = buscarHijo(nombre);
    limpiarHijo(hijos);

    for (j=0;j<hijos.length;j++){
      if (eval("typeof(document.getElementById(hijos[j]))!='undefined'")) {
        objeto = document.getElementById(hijos[j]);
        k = 1;
        objeto.options[0] = new Option(" - Elija una opción - ","0");
        hijo = hijos[j];
        hijo1 = dominios(hijos[j]);
        if(filtro != '-1'){
          for (i=0;i<combo[hijo1].length;i++){
            if(filtro==combo[hijo1][i][2]){
              objeto.options[k] = new Option(combo[hijo1][i][1],combo[hijo1][i][0]);
              k++;
            }
          }
        } else {
          for (i=0;i<combo[hijo1].length;i++){
            objeto.options[k] = new Option(combo[hijo1][i][1],combo[hijo1][i][0]);
            k++;
          }
        }
        objeto.options[0].selected=true;
      }
    }
  }
  function f(a,b)
  {
        a.value=b;
  }

  function iniciar(){
    for (i=0;i<padre_hijo.length;i++){
      valor_hijo=dominios(padre_hijo[i][0]);
      objeto = document.getElementById(padre_hijo[i][0]);
      if (padre_hijo[valor_hijo][1]=="''"){
        for(j=0;j<combo[valor_hijo].length;j++){
          objeto.options[j] = new Option(combo[valor_hijo][j][1], combo[valor_hijo][j][0])
          if(padre_hijo[valor_hijo][2]==combo[valor_hijo][j][0])
            objeto.options[j].selected=true;
        }
      }
      else {
        valor_padre=dominios(padre_hijo[i][1]);
        p=1;
        objeto.options[0] = new Option("- Elija una opción -","0");
        for(j=1;j<combo[valor_hijo].length+1;j++){
          if(padre_hijo[valor_padre][2]==combo[valor_hijo][j-1][2]){
            objeto.options[p] = new Option(combo[valor_hijo][j-1][1], combo[valor_hijo][j-1][0])
            if(padre_hijo[valor_hijo][2]==combo[valor_hijo][j-1][0])
              objeto.options[p].selected=true;
            p++;
          }
        }
      }
    }
  }






























var DireccionRecibir = "actCodigos.fautapo";
var httpTextoRecibido = getHTTPObject();
var httpTextoEnviado = getHTTPObject();
var tabla;
var contenido;
var parametro;

function cargarTexto(parametro, tabla) {
  this.tabla = tabla;
  this.parametro = parametro;
  if (httpTextoRecibido.readyState == 4 || httpTextoRecibido.readyState == 0) {
    httpTextoRecibido.open("GET",DireccionRecibir + '?' + parametro, true);
    httpTextoRecibido.onreadystatechange = manejoTextoRecibido; 
    httpTextoRecibido.send(null);
  }
}

function manejoTextoRecibido() {
  if (httpTextoRecibido.readyState == 4) {
    document.getElementById(tabla).innerHTML = httpTextoRecibido.responseText; 
  }
}


function getHTTPObject() {
  var xmlhttp;
  try {
    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
  } catch (e) {
    xmlhttp = false;
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

function abrirVentanita(a) {
  if (document.getElementById(tabla).style.visibility == 'hidden') {
    document.getElementById(tabla).style.visibility="visible"; 
    document.getElementById(tabla).style.left = a.left;
    document.getElementById(tabla).style.top = a.top+25;        
  }
}

function cerrarVentanita() {
  document.getElementById(tabla).style.visibility = 'hidden';
}



  function fcombo(a, b, c)
  { 
//      alert(a+"?patron="+b+c);
      window.open(a+"?patron="+b+c,"cal","scrollbars=yes,toolbar=0,width="+2*calWidth+",height="+300+",left="+(winX+calOffsetX)+",top="+(winY+calOffsetY));
  } 
