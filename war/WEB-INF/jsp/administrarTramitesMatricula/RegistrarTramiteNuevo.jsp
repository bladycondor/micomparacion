<%@ include file="../Superior.jsp"%>

<jsp:useBean id="now" class="java.util.Date" />
<script language="JavaScript">
var puentes = new Array();
<c:forEach var="lista" items="${lProcesos}" >
  puentes.push(new Array(<c:out value="${lista.puente}"/>, '<c:out value="${lista.ruta}"/>'));
</c:forEach>

  function mueveReloj() {
    momentoActual = new Date()
    hora = momentoActual.getHours()
    minuto = momentoActual.getMinutes()
    segundo = momentoActual.getSeconds()
    horaImprimible = hora + " : " + minuto + " : " + segundo
    document.forma.reloj.value = horaImprimible
    setTimeout("mueveReloj()", 1000)
  }

  function direccionarProceso() {
    var combo = document.getElementById('id_proceso');
    if (puentes[combo.selectedIndex-1][0]) {
      document.forma.action = puentes[combo.selectedIndex-1][1];
    }
    else {
      document.forma.action = 'registrarTramiteNuevo.fautapo';
    }
    document.forma.submit();
  }
</script>
<body onload="mueveReloj()">

<div class="titulo"> Creaci&oacute;n de procesos de negocio </div>
<br>

<form name="forma" method="POST">
  <table class="formulario">
    <tr>
      <th align="center">Proceso de negocio</th>
      <td>
        <select id="id_proceso" name="id_proceso" OnChange='javascript: direccionarProceso();'>
          <option value="">-- Seleccione --</option>
          <c:forEach var="lista" items="${lProcesos}" >
            <option value="<c:out value="${lista.id_proceso}"/>" <c:if test="${lista.id_proceso == id_proceso}">selected</c:if>>
              <c:out value="${lista.proceso}"/> 
  	    </option>
          </c:forEach>
        </select>  
      </td>
    </tr>
  </table>
  <br>
    
  <c:if test="${!empty lformulario}">
    <table border="0">
      <tr>
        <td valign="top">
          <table border="0" cellspacing="0" cellpadding="0">
	    <tr>
	      <td>
	      <table width="100%" class="tabla">
	        <tr>
  	          <th>Fecha :: <fmt:formatDate value="${now}" pattern="${formatoFecha}" /></th>
                  <th>Hora :: <input type="text" name="reloj" size="10" style="background-color : #f0f0f0" onfocus="window.document.form_reloj.reloj.blur()" readonly></th>
   	        </tr>
                <c:set var="nro_fila_ant" value="0"/>
                <c:forEach var="lista1" items="${lformulario}" varStatus="contador">
                  <c:if test="${contador.last==true}">
                    <input type="hidden" name="nu_registros" value="<c:out value="${contador.count}"/>">
                  </c:if>
                  <input type="hidden" name='id_campo_<c:out value="${contador.count}"/>'        value='<c:out value="${lista1.id_campo}"/>' >
                  <input type="hidden" name="id_form"                                            value='<c:out value="${lista1.id_form}"/>' >
                  <input type="hidden" name='id_tipo_permiso_<c:out value="${contador.count}"/>' value='<c:out value="${lista1.id_tipo_permiso}"/>' >
                  <input type="hidden" name='id_dominio_<c:out value="${contador.count}"/>'      value='<c:out value="${lista1.id_dominio}"/>' >
                  <c:if test='${lista1.id_tipo_permiso!=""}'>
                    <c:if test="${lista1.nro_fila == fila_minima}">
	             <tr>
		       <td>
		       <table border="0" width="100%" class="tabla">
		         <tr>
	            </c:if>
  	            <c:if test="${nro_fila_ant != lista1.nro_fila}">
		            </tr>
		          </table>
		        </td>
		      </tr>
		      <tr>
		        <td>
		        <table border="0" class="tabla">
		          <tr>
                      <c:set var="nro_fila_ant" value="${lista1.nro_fila}"/>
                    </c:if>

	            <c:if test="${nro_fila_ant == lista1.nro_fila}">
  		      <c:if test="${lista1.permiso == 'no'}">
  	                <td class="etiqueta" align="right"><c:out value="${lista1.campo}"/>
			  <c:if test="${lista1.obligatorio == true}">
                            <font color='red'>(*)</font>
			  </c:if>
			</td>
                        <td valign="top">
		      </c:if>
		      <c:if test="${lista1.permiso == 'si'}">
                        <td valign="top" class="etiqueta2">
		        <p>
  	              <c:out value="${lista1.campo}"/>
		      <c:if test="${lista1.obligatorio == true}">
                        <font color='red'>(*)</font>
		      </c:if>
                      <br>
		     </c:if>

		      <c:if test='${lista1.id_tipo_permiso == "W"}'>
                        <c:if test='${lista1.filas ==1 && lista1.columnas!=0}'>
	 	          <c:if test='${(lista1.rango1=="0")&&(lista1.rango2=="0")}'>
	                    <input type='text' id='<c:out value="${lista1.id_campo}"/>' name='valor_<c:out value="${contador.count}"/>' value='<c:out value="${lista1.valor}"/>' size='<c:out value="${lista1.columnas}"/>' maxlength='<c:out value="${lista1.caracteres}"/>' <c:if test="${lista1.id_tipo_validacion != 'T'}"> onblur='validar(this,"<c:out value='${lista1.id_tipo_validacion}'/>")' </c:if> onkeyup='funciones()' >
	                  </c:if>
		          <c:if test='${(lista1.rango1!="0")||(lista1.rango2!="0")}'>
	                    <input type='text' id='<c:out value="${lista1.id_campo}"/>' name='valor_<c:out value="${contador.count}"/>' value='<c:out value="${lista1.valor}"/>' size='<c:out value="${lista1.columnas}"/>' maxlength='<c:out value="${lista1.caracteres}"/>' onblur='validarNota(this,"<c:out value='${lista1.rango1}'/>","<c:out value='${lista1.rango2}'/>")' onkeyup='funciones()' >
	                  </c:if>
		        </c:if>
	                <c:if test='${lista1.filas > 1 && lista1.columnas != 0}'>
		          <c:if test='${lista1.rango1=="0"||lista1.rango2=="0"}'>
                            <textArea id='<c:out value="${lista1.id_campo}"/>' name='valor_<c:out value="${contador.count}"/>' rows='<c:out value="${lista1.filas}"/>' cols='<c:out value="${lista1.columnas}"/>' maxlength='<c:out value="${lista1.caracteres}"/>' <c:if test="${lista1.id_tipo_validacion != 'T'}">  onblur='validar(this,"<c:out value='${lista1.id_tipo_validacion}'/>")' </c:if> ><c:out value="${lista1.valor}"/></textarea>
	                  </c:if>
		          <c:if test='${lista1.rango1!="0"||lista1.rango2!="0"}'>
                            <textArea id='<c:out value="${lista1.id_campo}"/>' name='valor_<c:out value="${contador.count}"/>' rows='<c:out value="${lista1.filas}"/>' cols='<c:out value="${lista1.columnas}"/>' maxlength='<c:out value="${lista1.caracteres}"/>' onblur='validarNota(this,"<c:out value='${lista1.rango1}'/>","<c:out value='${lista1.rango2}'/>")'><c:out value="${lista1.valor}"/></textarea>
	                  </c:if>
                        </c:if>
                      </c:if>
		      
		      <!--PARA LA FECHA -->
		      <c:if test='${lista1.id_tipo_permiso=="D"}'>
			<input type="text" name='valor_<c:out value="${contador.count}"/>' value='<fmt:formatDate value="${now}" pattern="${formatoFecha}" />' size="20">
			<small><a href="javascript:showCal('valor_<c:out value="${contador.count}"/>')"><img src="./imagenes/formularios/calendario.jpeg" border="0" ></a></small>
                      </c:if>

		      <!--PARA LA TABLA -->
		      <c:if test='${lista1.id_tipo_permiso=="T"}'>
                        <input type='text' id='<c:out value="${lista1.id_campo}"/>' name='valor_<c:out value="${contador.count}"/>' value='<c:out value="${lista1.valor}"/>' size='<c:out value="${lista1.columnas}"/>' maxlength='<c:out value="${lista1.caracteres}"/>' readonly onkeyup='funciones()' >
   		        <small><a href="javascript:showTabla(document.forma.valor_<c:out value="${contador.count}"/>, document.forma.primarios_<c:out value="${contador.count}"/>, 
			document.forma.campos_<c:out value="${contador.count}"/>, '<c:out value="${lista1.id_dominio_padre}"/>', '<c:out value="${lista1.id_campo_foraneo}"/>', '<c:out value="${lista1.valores}"/>', '<c:out value="${lista1.campos}"/>', '<c:out value="${lista1.primarios}"/>', '<c:out value="${lista1.cadena}"/>', '<c:out value="${lista1.etiqueta}"/>');">
                        <img src="./imagenes/formularios/baseDatos.gif" border="0" ></a></small>
                        <input type="hidden" id='p<c:out value="${lista1.id_campo}"/>' name='primarios_<c:out value="${contador.count}"/>' >
			<input type="hidden" name='campos_<c:out value="${contador.count}"/>' >
			<script>
			  dominio_padreA[<c:out value="${lista1.id_dominio}"/>]='<c:out value="${lista1.id_campo}"/>';
                        </script>
                      </c:if>

                      <!--Dibuja un combo -->
		      <c:if test='${lista1.id_tipo_permiso=="C"}'>
	                <c:if test='${lista1.filas!=0 && lista1.columnas!=0}'>
  		  	  <c:if test="${lista1.id_tipo_dominio == '1'}">
 		            <select id='<c:out value="${lista1.id_campo}"/>' name='combo_<c:out value="${lista1.id_dominio}"/>'
			      onChange='funciones(); <c:if test="${ lista1.id_dato == 1}">
			      poblar(<c:out value='${lista1.id_dominio}' />, this.options[this.selectedIndex].value)
            		      </c:if>' >
                            <c:if test='${lista1.resultado!=1}'>
	                      <option value="0">--Seleccione--</option>
                            </c:if>
		            <c:forEach var="lista" items="${lista1.tuplas}" varStatus="contador1">
  	                      <c:if test='${lista.id_tupla_padre=="0"}'>
			        <option value="<c:out value="${lista.id_tupla}"/>" >
	                          <c:out value="${lista.tupla}"/>
	                        </option>
	                      </c:if>
			    </c:forEach>
	                    </select>
                          </c:if>

			  <c:if test="${lista1.id_tipo_dominio != '1'}">
       	                    <select id='<c:out value="${lista1.id_campo}"/>' name='combo_<c:out value="${lista1.id_dominio}"/>' <c:if test="${ lista1.id_dato == 1}">	  
      	                                                                   onchange="javascript:document.forma.combito.value='<c:out value="${contador.count}"/>'; document.forma.submit();"
       		                                                           </c:if> >
                            <c:if test="${lista1.resultado != 1}">
	                      <option value="0">--Seleccione--</option>
                            </c:if>
		            <c:forEach var="lista" items="${lista1.tuplas}">
  	                      <option value="<c:out value="${lista.id_tupla}"/>"  <c:if test="${lista.id_tupla == lista1.seleccionado}">selected </c:if> <c:if test="${lista1.resultado == 1}">selected </c:if>>
	                        <c:out value="${lista.tupla}"/>
	                      </option>
	                    </c:forEach>
	                    </select>
                          </c:if>
			</c:if>
                      </c:if>

                      <!-- Dibujamos un check -->
                      <c:if test='${lista1.id_tipo_permiso=="K"}'>
                        <c:if test='${lista1.filas!=0 && lista1.columnas!=0}'>
		          <table valign="top" width="100%" cellspacing="0" cellpadding="0">
			    <tr>
	   	            <c:forEach var="lista" items="${lista1.tuplas}" varStatus="cont1">
		              <td class="etiqueta">
			        <c:out value="${lista.tupla}"/>
			        <c:if test="${lista.obligatorio == true}">
                                  <font color='red'>(*)</font>
			        </c:if>
			      </td>
   	                      <td class="colh"><input type=checkbox name="check<c:out value="${contador.count}"/>" value="<c:out value="${lista.id_tupla}"/>" <c:if test="${lista.id_tupla == lista.seleccionado}">checked</c:if> >
                              <c:if test="${cont1.count % 4  == '0'}" >
 			        </tr>
			        <tr>
		              </c:if>
	                    </c:forEach>
		            </tr>
		          </table>
                        </c:if>
                      </c:if>

                      </td>
  	            </c:if>
	          </c:if>
               </c:forEach>
             </table>
             </td>
	   </tr>
	 </table>
	</td>
 
        <!-- columna 2 -->
        <td valign="top">
          <table class="tabla">
            <tr>
              <th>PROVEIDO</th>
            </tr>
            <tr>
              <td><textArea name="proveido" rows="5" cols="30"><c:out value="${proveido}"/></textarea></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <center>
      <input type="button" name="botonAceptar" class="aceptar" value='Aceptar' onClick='registrarTramite()';>
    </center>
    <input type="hidden" name="accion11"   value="">
    <input type="hidden" name="id_campo"   value='<c:out value="${id_campo}"/>'>
    <input type="hidden" name="id_form"    value='<c:out value="${id_form}"/>'>
    <input type="hidden" name="valor"      value='<c:out value="${valor}"/>'>	 
    <input type="hidden" name="recargado"  value='si'> 
    <input type="hidden" name="combito"    value='0'>
    <input type="hidden" name="aplicacion" value='<c:url value="/"/>' >
    <input type="hidden" id="basurero"     value='0'>
  </c:if>
</form>

<script>
<!--
  var calFormat = "<c:out value='${formatoFecha}'/>";

  var variables = new Array();
  n=0;

  var operaciones = new Array();
  m=0;

  var combo = new Array();
  var padre_hijo = new Array();
  h = 0;
  <c:forEach var="lista1" items="${lformulario}" varStatus="contador">
      <c:if test='${lista1.operacion}'>
        operaciones[m] = new Array("<c:out value='${lista1.id_campo}'/>","<c:out value='${lista1.formula}'/>");
        ++m;
      </c:if>
    <c:if test='${(lista1.id_tipo_permiso=="C")||(lista1.id_tipo_permiso=="W")}'>
      variables[n] = '#<c:out value="${lista1.id_campo}"/>#';
      ++n;
      <c:if test='${lista1.id_tipo_permiso=="C"}'>
        padre_hijo[h] =new Array("<c:out value='${lista1.id_dominio_padre}'/>", "<c:out value='${lista1.id_dominio}'/>"); 
        h++;
        combo[<c:out value="${lista1.id_dominio}"/>]=new Array();
        <c:forEach var="lista" items="${lista1.tuplas}"  varStatus="contador1">
          combo[<c:out value="${lista1.id_dominio}"/>][<c:out value="${contador1.index}"/>]=new Array("<c:out value='${lista.id_tupla}'/>","<c:out value='${lista.tupla}'/>","<c:out value='${lista.id_tupla_padre}'/>");
        </c:forEach>
      </c:if>
    </c:if>
  </c:forEach>
  
 function limpiar(obj_combo) {
    for (i=1; i<obj_combo.length; i++) {
      if (eval("typeof(document.getElementsByName('combo_' + obj_combo[i])[0])!='undefined'")) {
        combito = document.getElementsByName("combo_" + obj_combo[i])[0];
        if(combito.options.length !=null)
          for (m=combito.options.length-1;m>0;m--)
            combito.options[m]=null;
            combito.options[0]= new Option("--Seleccione--","0");    
      }      
    }
 } 

  function buscarHijo(padre) {
    hijo = new Array();
    kkk = 1;
    hijo[0] = padre ;
    for (j=0; j<kkk; j++) {
      for (i=0;i<padre_hijo.length;i++) {
        if (padre_hijo[i][0]==hijo[j]) {
          hijo[kkk] = padre_hijo[i][1];
          kkk = kkk + 1;
        } 
      }
    }
    return hijo;
  }

  function poblar(dominio, filtro) {
    dominio1= buscarHijo(dominio);
    limpiar(dominio1);
    for (kk=1;kk<dominio1.length;kk++)
    if (eval("typeof(document.getElementsByName('combo_'+ dominio1[kk])[0])!='undefined'")) {
      objeto = document.getElementsByName("combo_"+ dominio1[kk])[0];
      var burbuja = new Array();
      k = 1;
      if(filtro != '-1'){
        for (i=0;i<combo[dominio1[kk]].length;i++)
          if(filtro == combo[dominio1[kk]][i][2]){
            objeto.options[k] = new Option(combo[dominio1[kk]][i][1],combo[dominio1[kk]][i][0]);
            k++;
          }
      } else {
        for (i=0;i<combo[dominio1[kk]].length;i++){
          objeto.options[k] = new Option(combo[dominio1[kk]][i][1],combo[dominio1[kk]][i][0]);
          k++;
        }
      }
      objeto.options[0] = new Option("--Seleccione--","0");    
      if (k == 2) {
        objeto.options[1].selected=true;
      } else {
        objeto.options[0].selected=true;
      }
    }
  }

 
  function funciones() {
    var objetos;
    for (k=0; k<operaciones.length; k++) {
      resultado = document.getElementById(operaciones[k][0]);
      if (resultado == null) {
        resultado = document.getElementById('basurero');
      }
      if (true) {
        operaciones1 = "";
        for (i=0;i<variables.length;i++) {
          t=true;
          prueba = operaciones[k][1].split(variables[i]);
  	  s = prueba[1];
	  if(prueba[1]==undefined)
	    t=false;
	  if (t) {
            if (operaciones1=="") {
              variables1=operaciones[k][1].split(variables[i])
            } else {
              variables1=operaciones1.split(variables[i]);	
	    }
            variable = variables[i].split('#');
            objetos = document.getElementById(variable[1]);
	  
	    if(objetos.type=='select-one')
	      operaciones1 = variables1[0] + objetos.options[objetos.selectedIndex].text + variables1[1] ;
	    else 
              operaciones1 = variables1[0] + objetos.value + variables1[1] ;
	  }
        }
        try{
          resultado.value = eval(operaciones1);
        } catch(e){
          resultado.value ='0';
        }
      }
    }
  }

  function registrarTramite() {
    document.forma.botonAceptar.disabled=true;
    document.forma.action="registrarTramiteNuevo1.fautapo";
    document.forma.accion11.value = "Guardar";
    document.forma.submit();
  }

-->
</script>

<%@ include file="../Inferior.jsp" %>