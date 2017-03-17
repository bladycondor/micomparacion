<%@ include file="../../Superior.jsp" %>

<div class="titulo">Convalidaci&oacute;n Manual</div>
<br>
<table>
  <tr>    
    <td>
      <form name="fvolver" action="<c:url value='/convalidacionManual/listarPlanEstudiante.fautapo'/>" method="post">
        <input type="hidden" name="gestion" value="<c:out value='${gestion}'/>">
        <input type="hidden" name="periodo" value="<c:out value='${periodo}'/>">
	<input type="hidden" name="id_programa" value="<c:out value='${datosEstudiante.id_programa}'/>">
	<input type="hidden" name="id_estudiante" value="<c:out value='${datosEstudiante.id_estudiante}'/>">
	<input type="hidden" name="id_tipo_convalidacion"  value="<c:out value="${buscarTipoConv.id_tipo_convalidacion}"/>" >
      <div> <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a> </div>
    </form>
    </td>
  </tr>
</table>
<form  name="forma" method=post action=<c:url value="/convalidacionManual/confirmarMateriasConvalidar.fautapo"/> >

<table class="tabla">
<tr>
  <th class=colh>RU </th>
  <th class=colh>NOMBRES</th>
  <th class=colh>PLAN</th>
  <th class=colh>PROGRAMA</th>
  <th class=colh>GESTION</th>
  <th class=colh>PERIODO</th>
</tr>  
<tr>
  <td class=colb><c:out value="${datosEstudiante.id_estudiante}"/></td>
  <td class=colb><c:out value="${datosEstudiante.paterno}"/> &nbsp; <c:out value="${datosEstudiante.materno}"/> &nbsp; <c:out value="${datosEstudiante.nombres}"/></td>
  <td class=colb><c:out value="${datosEstudiante.id_plan}"/></td>
  <td class=colb><c:out value="${datosEstudiante.programa}"/></td>
  <td class=colb><c:out value="${gestion}"/></td>
  <td class=colb><c:out value="${periodo}"/></td>
<tr>  
</table>
<br>
<table class="tabla">
  <tr>
    <th colspan="2" align="center">
      MATERIAS DEL PLAN A CONVALIDAR
    </th>
  </tr>    
  <tr>
    <td align="center">
      <table class="tabla" width="60%">
        <tr>
            <th colspan="2">DATOS A CONVALIDAR</th>
	</tr>    
        <tr>
            <td class="etiqueta">R.U. ::</th>
	    <td><c:out value="${datosEstudiante.id_estudiante}"/></td>
        </tr>

	<tr>
            <td class="etiqueta">Tipo de Convalidaci&oacute;n ::</th>
	    <td><c:out value="${buscarTipoConv.tipo_convalidacion}"/></td>
        </tr>
	<c:if test="${buscarTipoConv.id_tipo_convalidacion ==1}" >
	<tr>
          <td class="etiqueta">Universidad <font color='red'>(*)</font>::</th>
	  <td>
	    <select name="id_universidad">
                  <c:forEach var="lista" items="${lUniversidades}" >
                  <option value='<c:out value="${lista.id_universidad}"/>' <c:if test="${lista.id_universidad == 0}">selected</c:if> >
                    <c:out value="${lista.universidad}"/> 
                  </option>
                  </c:forEach>
            </select>
	  </td>
        </tr>
	</c:if>
	<c:if test="${buscarTipoConv.id_tipo_convalidacion ==2}" >
	<tr>
            <td class="etiqueta">Facultad<font color='red'>(*)</font>:: </td>
            <td>
                <select id='id_facultad' name='id_facultad' size='1'
                  onChange="poblar('id_facultad', this.options[this.selectedIndex].value); document.forma.facultad.value = Afacultades[document.forma.id_facultad.value];">
                </select>
            </td>
            <script>
                var Afacultades = new Array();
                padre_hijo[h] = new Array ("id_facultad", "''", "<c:out value = "${id_facultad}"/>");
                combo[h] = new Array();
                combo[h][0] = new Array("0", "- Elija una opcion -", "");
                <c:forEach var = "lista" items = "${lFacultades}" varStatus="contador">
                  Afacultades[<c:out value = "${lista.id_facultad}"/>] = '<c:out value = "${lista.facultad}"/>';
                  combo[h][<c:out value = "${contador.count}"/>] = new Array("<c:out value = "${lista.id_facultad}"/>", "<c:out value = "${lista.facultad}"/>", "");
                </c:forEach>
                h++;
            </script>
          </tr>
          <tr>
            <td class="etiqueta">Programa <font color='red'>(*)</font> ::</td>
            <td>
              <select id='id_programa' name='id_programa' size='1'
                onChange="poblar('id_programa', this.options[this.selectedIndex].value); document.forma.id_programa.value = Aprogramas[document.forma.id_programa.value];">
              </select>
            </td>
            <script>
                var Aprograma = new Array();
                padre_hijo[h] = new Array ("id_programa", "id_facultad", "<c:out value = "${bPst.id_programa}"/>");
                combo[h] = new Array();
                combo[h][0] = new Array("0", "- Elija una opcion -", "");
                <c:forEach var = "cod" items = "${lProgramas}" varStatus = "programac">
                  Aprograma[<c:out value = "${cod.id_programa}"/>] = '<c:out value = "${cod.programa}"/>';
                  combo[h][<c:out value = "${programac.count}"/>] = new Array("<c:out value = "${cod.id_programa}"/>", "<c:out value = "${cod.programa}"/>", "<c:out value = "${cod.id_facultad}"/>");
                </c:forEach>
                h++;
            </script>
          </tr>
	  </c:if>
	  <c:if test="${buscarTipoConv.id_tipo_convalidacion ==3}" >
	  <tr>
            <td class="etiqueta">Plan Origen <font color='red'>(*)</font>::</th>
	    <td><input type="text" name="plan_origen" size="6"></td>
          </tr>
	  </c:if> 
	  <tr>
	    <td class="etiqueta">Resoluci&oacute;n <font color='red'>(*)</font> ::</td>
	    <td><input type="text" name="resolucion" size="20"></td>
	  </tr>
      </table>
    </td>
  </tr>
  <tr>    
    <td>  
      <table class="tabla" width="100%">
        <tr>
	    <th>NRO.</th>
            <th>SIGLA <br> ORIGEN</th>
	    <th>MATERIA <br> ORIGEN</th>
            <th>SIGLA<br>ACTUAL</th>
            <th>MATERIA<br>ACTUAL</th>
            <th>MODALIDAD<br></th>
	    <th>% SIMILITUD</th>
	    <th>NOTA</th>
	    <th>GESTION</th>
	    <th>PERIODO</th>
        </tr>
        <c:forEach var="lista" items="${lMateriasSeleccionadas}" varStatus="contador">
        <tr>
	  <td><c:out value="${contador.count}"/></td>
          <td><input type="text" name="sigla_origen<c:out value="${lista.id_materia}"/>" size="6"></td>
          <td><input type="text" name="materia_origen<c:out value="${lista.id_materia}"/>" size="6"></td>
          <td><c:out value="${lista.sigla}"/></td>
          <td><c:out value="${lista.materia}"/>
	    <input type="hidden" name="id_materia<c:out value="${lista.id_materia}"/>" value="<c:out value="${lista.id_materia}"/>">
	    <input type="hidden" name="id_materia_conv" value="<c:out value="${lista.id_materia}"/>">
	  </td>
	  <td>
	    <select name="id_tipo_materia<c:out value='${lista.id_materia}'/>">
               <c:forEach var="listaT" items="${lTiposMaterias}" >
                  <option value='<c:out value="${listaT.id_tipo_materia}"/>' <c:if test="${listaT.id_tipo_materia == id_tipo_materia}">selected</c:if> >
                    <c:out value="${listaT.tipo_materia}"/> 
                  </option>
                </c:forEach>
            </select>
	  </td>
          <td><input type="text" name="similitud<c:out value="${lista.id_materia}"/>"   onblur="validar(similitud<c:out value="${lista.id_materia}"/>,'9')"  size="3" maxlength="3"></td>
          <td><input type="text" name="nota_origen<c:out value="${lista.id_materia}"/>"  id='nota<c:out value="${lista.id_materia}"/>'  onblur="validar(nota_origen<c:out value="${lista.id_materia}"/>,'9');" onKeyUp="cargar_nota(nota_origen<c:out value="${lista.id_materia}"/>);"  size="3" maxlength="3"></td>
          <td><c:out value="${gestion}"/></td>
          <td><c:out value="${periodo}"/></td>
        </tr>  
        </c:forEach>
      </table>
      </td>
    </tr>
    <tr>
      <td align="center" colspan="2">
	  <input type="submit" value="Guardar" class="agregar" >
	  <input type="hidden" name="id_estudiante"  value="<c:out value="${datosEstudiante.id_estudiante}"/>" >
	  <input type="hidden" name="gestion"  value="<c:out value="${gestion}"/>" >
	  <input type="hidden" name="periodo"  value="<c:out value="${periodo}"/>" >
	  <input type="hidden" name="id_tipo_convalidacion"  value="<c:out value="${buscarTipoConv.id_tipo_convalidacion}"/>" >
      </td>
    </tr>
  </table>       
</form>

<script>
 function cargar_nota(objeto) {
  var nota_entrada = objeto.value;
    if (nota_entrada > 100) {
      //alert("ENTRANDO PO SI--->"+ objeto.value);
      alert("La dato introducido: '"+objeto.value+"'  es mayor al permitido. Introduzca un número del 0 al 100");
    }
 }
  iniciar();
</script>

<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>   
<%@ include file="../../Inferior.jsp" %>