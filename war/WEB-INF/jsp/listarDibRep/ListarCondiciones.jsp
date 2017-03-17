<%@ include file="../Superior.jsp" %>
<div><a class="volver" href='javascript:history.back();'>Volver</a></div>
<br>
<!-- ************  Titulo  ********** -->
<div class="titulo"><c:out value='${consulta.descripcion}' escapeXml='False' /></div>
<br>
<br>
<!-- ************  fin Tititulo  ********** -->
<!-- en caso de que exista tiene que sacar de sesion-->
<body onLoad="iniciar();">
<form name='forma' method=post target="_blank">
<br>
 <table class="formulario">
   <c:if test="${consulta.glosa}">
     <tr><th>Glosa <td><input type="text" name="glosa_texto" size='40'>
   </c:if>

 <c:forEach var="listadoCamposCondicion" items="${listarCamposCondicion}" varStatus="contador">
   <tr>
     <th><c:out value="${listadoCamposCondicion.etiqueta}"/></th>
     <td>
        <!--  INICIO JOJO  -->
        <c:if test="${!empty listadoCamposCondicion.combo}">
	      <select name="condicion_<c:out value='${listadoCamposCondicion.campo}'/>" >
	        <option value="="> = </option>
	        <option value=">"> > </option>
	        <option value="<"> < </option>
	        <option value="<>"> <> </option>
	        <option value=" like "> incluye </option>
	      </select>
	
	    <SELECT id='<c:out value="${listadoCamposCondicion.campo}"/>' NAME='<c:out value="${listadoCamposCondicion.campo}"/>' size='<c:out value="${listadoCamposCondicion.filas}"/>'
                    onChange="poblar('<c:out value='${listadoCamposCondicion.campo}'/>', this.options[this.selectedIndex].value )" >
            </SELECT>
	</c:if>

        <c:if test="${empty listadoCamposCondicion.combo}">
	  <c:if test="${'dfecha' != listadoCamposCondicion.tipo_dato}">
	    <c:if test="${'dfecha2' != listadoCamposCondicion.tipo_dato}">
	      <select name="condicion_<c:out value='${listadoCamposCondicion.campo}'/>" >
	        <option value="="> = </option>
	        <option value=">"> > </option>
	        <option value="<"> < </option>
	        <option value="<>"> <> </option>
	        <option value=" like "> incluye </option>
	      </select>
	      <input type="text" name='<c:out value="${listadoCamposCondicion.campo}"/>' ></th>
	    </c:if>
	  </c:if>
	  <!-- RENE -->
	  <c:if test="${'dfecha' == listadoCamposCondicion.tipo_dato}">
	    <input type="text" name='<c:out value="${listadoCamposCondicion.campo}"/>1' value='<fmt:formatDate value="${fec_hoy}" pattern="dd/MM/yyyy" />' size="10" readonly><small>
	    <a href="javascript:showCal('<c:out value="${listadoCamposCondicion.campo}"/>1')"><img src="./imagenes/dibRap/calendario.jpeg" border="0" ></a></small>
	   
	    <input type="text" name='<c:out value="${listadoCamposCondicion.campo}"/>' value='<fmt:formatDate value="${fec_hoy}" pattern="dd/MM/yyyy" />' size="10" readonly><small>
	    <a href="javascript:showCal('<c:out value="${listadoCamposCondicion.campo}"/>')"><img src="./imagenes/dibRap/calendario.jpeg" border="0" ></a></small>
	    </th>
	  </c:if>

	  <c:if test="${'dfecha2' == listadoCamposCondicion.tipo_dato}">
	    <input type="text" name='<c:out value="${listadoCamposCondicion.campo}"/>1' value='<fmt:formatDate value="${fec_hoy}" pattern="dd/MM/yyyy" />' size="10" readonly><small>
	    <a href="javascript:showCal('<c:out value="${listadoCamposCondicion.campo}"/>1')"><img src="./imagenes/dibRap/calendario.jpeg" border="0" ></a></small>
	   
	    <input type="text" name='<c:out value="${listadoCamposCondicion.campo}"/>' value='<fmt:formatDate value="${fec_hoy}" pattern="dd/MM/yyyy" />' size="10" readonly><small>
	    <a href="javascript:showCal('<c:out value="${listadoCamposCondicion.campo}"/>')"><img src="./imagenes/dibRap/calendario.jpeg" border="0" ></a></small>
	    </th>    
	  </c:if>
        </c:if>
        <!--  FIN JOJO  -->
      </td>
   </tr>

   <c:forEach var="combo" items="${listadoCamposCondicion.combo}">
     <input type="hidden" name='<c:out value="${listadoCamposCondicion.campo}" /><c:out value="${combo.id_campo}" />' value='<c:out value="${combo.campo}"/>' >
   </c:forEach>
 </c:forEach>
 <tr> <td colspan='8'> 
 <div class="etiqueta_rep">  <input type='text' id='paginacion' name='paginacion' value='50' size='4' onKeyPress="return aceptaNumeros(event)"><label class="etiqueta">PAGINACION PDF</label><br></div>
 <div class="etiqueta_rep">  <INPUT type="checkbox" name='op_total' value="si"><label class="etiqueta">Mostrar Totales</label>  <br></div>
 <tr> <td colspan='8' align="top"> 
     <INPUT type="radio" name='op' value="EXCEL"><label class="etiqueta">  EXCEL</label> <INPUT type="radio" name='op' value="PDF"><label class="etiqueta">  PDF </label><INPUT type="radio" name='op' value="HTML" checked> <label class="etiqueta"> HTML </label>
 </tr>
<tr>
  <th colspan="8">
    <input class='aceptar' type=submit value='Aceptar' OnClick='javascript: document.forma.action="<c:url value='/listarDibRep1.fautapo'/>";'>
  </th>
</tr>
</table>
  <input type="hidden" name="c" value='<c:out value="${consulta.id_consulta}"/>' >
  <input type="hidden" name='ruta' value="<c:url value='/'/>" >
</form>


<script language="JavaScript">

<!--
   var calFormat = "dd/MM/yyyy";
//----------------
  var combo = new Array();
  var padre_hijo = new Array();
  h = 0;

    <c:forEach var="listaCampos" items="${listarCamposCondicion}" varStatus="contador">
      <c:if test="${(!empty listaCampos.combo) and (listaCampos.id_componente != 5)}">
        padre_hijo[h] =new Array("<c:out value='${listaCampos.campo}'/>", "<c:out value='${listaCampos.campo_padre}' escapeXml='false' />");
        combo[h]=new Array();
        <c:forEach var="combo" items="${listaCampos.combo}"  varStatus="contador1">
          combo[h][<c:out value="${contador1.index}"/>]=new Array("<c:out value='${combo.id_campo}'/>","<c:out value='${combo.campo}'/>","<c:out value='${combo.campo_padre}'/>");
        </c:forEach>
        h++;
      </c:if>
    </c:forEach>

//-------------------

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
    //alert("objeto=" + objeto);
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

  function iniciar(){
    for (i=0;i<padre_hijo.length;i++){
      valor=dominios(padre_hijo[i][0]);
      objeto = document.getElementById(padre_hijo[valor][0]);
      if(padre_hijo[valor][1]=="''"){
        for(j=0;j<combo[valor].length;j++){
          objeto.options[j] = new Option(combo[valor][j][1],combo[valor][j][0]);
        }
      } else {
        objeto.options[0] = new Option(" - Elija una opción - ","0");
      }
    }
  }


  var nav4 = window.Event ? true : false;
  function aceptaNumeros(evt) {
    var key = nav4 ? evt.which : evt.keyCode;
    return (key <= 13 || (key >= 48 && key <= 57));
  }
-->
</script>
</body>
<%@ include file="../Inferior.jsp" %>