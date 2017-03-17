<%@ include file="../Superior.jsp" %>
<script language = 'JavaScript' SRC="./validar.js">  </script>
<jsp:useBean id="now" class="java.util.Date"/>
<div class="titulo">Asignaci&oacute;n de Tribunales</div>

<script language='JavaScript' SRC="../ajax.js"></script>
<tabla>
  <form name=formavolver<c:out value="${contador.count}"/> method=post action='<c:url value="/asignaciontribunal/listarMateriasProgramaPlan.fautapo"/>'>
    <td>
      <div><a class="volver" href="javascript:document.formavolver<c:out value="${contador.count}"/>.submit();"> Volver</a></div>
      <input type="hidden" name="gestion"            value="<c:out value="${gestion}"/>">
      <input type="hidden" name="periodo"            value="<c:out value="${periodo}"/>">
      <input type="hidden" name="id_programa"        value="<c:out value="${id_programa}"/>">	
      <input type="hidden" name="id_prg_plan"        value="<c:out value="${datosPrgPlan.id_prg_plan}"/>">	
      <input type="hidden" name="id_plan"            value="<c:out value="${datosPrgPlan.id_plan}"/>">	
      <input type="hidden" name="id_materia"         value="<c:out value="${id_materia}"/>">	 
      <input type="hidden" name="id_dpto_grupo"      value='<c:out value="${datosDptoGrupo.id_dpto_grupo}"/>' >
      <input type="hidden" name="id_tipo_evaluacion" value='<c:out value="${datosDptoGrupo.id_tipo_evaluacion}"/>' >
      <input type="hidden" name="id_asignacion"      value='<c:out value="${datosAsignacion.id_asignacion}"/>' >     
    </td>
  </form>
</table>

<br>
<table class="tabla">
  <tr>
    <th>PROGRAMA/CARRERA</th>
    <th>PLAN</th>
    <th>GESTI&Oacute;N</th>
    <th>PERIODO</th>
    <th>MATERIA</th>
    <th>GRUPO</th>
    <th>TIPO EVALUACION</th>
  </tr>
  <tr align="center">
    <td class="etiqueta"><c:out value="${programa.programa}"/>
    <td class="etiqueta"><c:out value="${datosPrgPlan.id_plan}"/>
    <td class="etiqueta"><c:out value="${gestion}"/>
    <td class="etiqueta"><c:out value="${periodo}"/>
    <td class="etiqueta"><c:out value="${materia.materia}"/>
    <td class="etiqueta"><c:out value="${datosDptoGrupo.grupo}"/>
    <td class="etiqueta"><c:out value="${datosDptoGrupo.tipo_evaluacion}"/>
  </tr>
</table>

<br>
<form  name=forma  action="<c:url value="/asignaciontribunal/registrarAsignacionDocente.fautapo"/>" method=post>
  <input type="hidden" name="id_programa"         value='<c:out value="${programa.id_programa}"/>' >
  <input type="hidden" name="programa"            value='<c:out value="${programa.programa}"/>' >
  <input type="hidden" name="id_prg_plan"         value='<c:out value="${datosPrgPlan.id_prg_plan}"/>' >
  <input type="hidden" name="gestion"             value='<c:out value="${gestion}"/>' >
  <input type="hidden" name="periodo"             value='<c:out value="${periodo}"/>' >
  <input type="hidden" name="id_materia"          value='<c:out value="${materia.id_materia}"/>' >
  <input type="hidden" name="materia"             value='<c:out value="${materia.materia}"/>' >
  <input type="hidden" name="id_dpto_grupo"       value='<c:out value="${datosDptoGrupo.id_dpto_grupo}"/>' >
  <input type="hidden" name="id_tipo_evaluacion"  value='<c:out value="${datosDptoGrupo.id_tipo_evaluacion}"/>' >
  <input type="hidden" name="id_asignacion"       value='<c:out value="${datosAsignacion.id_asignacion}"/>' >
  <input type="hidden" name="accion"              value='<c:out value="${accion}"/>' >
  <table class="formulario">
    <tr>
      <th colspan="3">INTRODUCIR LOS DATOS</th>
    </tr>
      
    <tr>
	 <td class="etiqueta" colspan="2"> Docente <font color="red" >(*)</font> ::</td>
	 <td colspan="2">
           <select id = 'id_docente' name = 'id_docente' size = '1'
            onChange = "poblar('id_docente', this.options[this.selectedIndex].value); document.forma.id_docente.value = Adocentes[document.forma.id_docente.value];">
          </select>
        </td>
        <script>
          var Adocentes = new Array();
          padre_hijo[h] = new Array ("id_docente", "''", "<c:out value = "${datosAsignacion.id_docente}"/>");
          combo[h] = new Array();
          combo[h][0] = new Array("0", "- Elija una opcion -", "");
          <c:forEach var = "cod" items = "${lDocentesTodos}" varStatus = "docentec">
            Adocentes[<c:out value = "${cod.id_docente}"/>] = '<c:out value = "${cod.nombre_completo}"/>';
            combo[h][<c:out value = "${docentec.count}"/>] = new Array("<c:out value = "${cod.id_docente}"/>", "<c:out value = "${cod.nombre_completo}"/>", "");
          </c:forEach>
          h++;
        </script>
    </tr>
    <tr>
	 <td class="etiqueta"  colspan="2"> Tipo Docente <font color="red" >(*)</font></td>
	 <td colspan="2">
           <select id = 'id_tipo_docente' name = 'id_tipo_docente' size = '1'
            onChange = "poblar('id_tipo_docente', this.options[this.selectedIndex].value); document.forma.id_tipo_docente.value = Atipo_docente[document.forma.id_tipo_docente.value];">
          </select>
        </td>
        <script>
          var Atipo_docente = new Array();
          padre_hijo[h] = new Array ("id_tipo_docente", "''", "<c:out value = "${datosAsignacion.id_tipo_docente}"/>");
          combo[h] = new Array();
          combo[h][0] = new Array("0", "- Elija una opcion -", "");
          <c:forEach var = "cod" items = "${lTiposDocentes}" varStatus = "tipo_docentec">
            Atipo_docente[<c:out value = "${cod.id_tipo_docente}"/>] = '<c:out value = "${cod.tipo_docente}"/>';
            combo[h][<c:out value = "${tipo_docentec.count}"/>] = new Array("<c:out value = "${cod.id_tipo_docente}"/>", "<c:out value = "${cod.tipo_docente}"/>", "");
          </c:forEach>
          h++;
        </script>
    </tr>
    <tr>
      <td class="etiqueta" colspan="2"> Tipo Asignaci&oacute;n <font color="red" >(*)</font></td>
      <td colspan="2">
        <select id = 'id_tipo_asignacion' name = 'id_tipo_asignacion' size = '1'
          onChange = "poblar('id_tipo_asignacion', this.options[this.selectedIndex].value); document.forma.id_tipo_asignacion.value = Aasignacion[document.forma.id_tipo_asignacion.value];">
        </select>
      </td>
          <script>
            var Aasignacion = new Array();
            padre_hijo[h] = new Array ("id_tipo_asignacion", "''", "<c:out value = "${datosAsignacion.id_tipo_asignacion}"/>");
            combo[h] = new Array();
            combo[h][0] = new Array("0", "- Elija una opcion -", "");
            <c:forEach var = "cod" items = "${lTiposAsignaciones}" varStatus = "asignacionc">
              Aasignacion[<c:out value = "${cod.id_tipo_asignacion}"/>] = '<c:out value = "${cod.tipo_asignacion}"/>';
              combo[h][<c:out value = "${asignacionc.count}"/>] = new Array("<c:out value = "${cod.id_tipo_asignacion}"/>", "<c:out value = '${cod.tipo_asignacion}'/>", "");
            </c:forEach>
            h++;
          </script>
    </tr>     
    <tr>
      <td class="etiqueta" colspan="2"> Fecha Inicio <font color="red" >(*)</font>::&nbsp;
         <input type="text" name='fec_inicio' <c:if test="${!empty datosAsignacion.fec_inicio2}"> value='<fmt:formatDate value="${datosAsignacion.fec_inicio2}" pattern="${formatoFecha}"/>' </c:if>
	  <c:if test="${empty datosAsignacion.fec_inicio2}"> value='<fmt:formatDate value="${now}" pattern="${formatoFecha}"/>' </c:if>
          maxlength='10' size='10' title="Fecha de Inicio" /> &nbsp; <small> <a href="javascript:showCal('fec_inicio')"> <img src="../imagenes/dibRap/calendario.jpeg" border="0" title="Seleccione la fecha" /></a></small>
      </td>
      <td class="etiqueta" colspan="2"> Fecha Fin <font color="red" >(*)</font>::&nbsp;
         <input type="text" name='fec_fin'  <c:if test="${!empty datosAsignacion.fec_fin2}">value='<fmt:formatDate value="${datosAsignacion.fec_fin2}" pattern="${formatoFecha}"/>' </c:if>
	  <c:if test="${empty datosAsignacion.fec_fin2}"> value='<fmt:formatDate value="${now}" pattern="${formatoFecha}"/>' </c:if>
          maxlength='10' size='10' title="Fecha Fin" /> &nbsp; <small> <a href="javascript:showCal('fec_fin')"> <img src="../imagenes/dibRap/calendario.jpeg" border="0" title="Seleccione la fecha" /></a></small>
      </td>
    </tr>   	
    <tr>
      <td class="etiqueta" colspan="2"> Nro Resoluci&oacute;n <font color="red" >(*)</font>::&nbsp; 
         <input type="text" name='nro_resolucion'  value='<c:out value="${datosAsignacion.nro_resolucion}"/>' onblur='validar(this,"A9")' size="10">
      </td>
      <td class="etiqueta" colspan="2"> Fecha Resolucion <font color="red" >(*)</font>::&nbsp;
         <input type="text" name='fec_resolucion'  <c:if test="${!empty datosAsignacion.fec_resolucion}"> value='<fmt:formatDate value="${datosAsignacion.fec_resolucion}" pattern="${formatoFecha}"/>' </c:if>
	  <c:if test="${empty datosAsignacion.fec_resolucion}"> value='<fmt:formatDate value="${now}" pattern="${formatoFecha}"/>' </c:if>
          maxlength='10' size='10' title="Fecha Resolucion" /> &nbsp; <small> <a href="javascript:showCal('fec_resolucion')"> <img src="../imagenes/dibRap/calendario.jpeg" border="0" title="Seleccione la fecha" /></a></small>
      </td>
    </tr>
    <tr>
      <td colspan="2" class="etiqueta"> Observaciones ::</td>
      <td colspan="2">
	 <textarea name='observacion' cols='20' rows='1'><c:out value="${datosAsignacion.observacion}"/></textarea>
      </td>
    </tr>
    <tr>
      <th colspan="4"><input class="aceptar" type="button" value='Registrar' onClick="fguardar();"></th>
    </tr>
  </table>
</form>

<script language="JavaScript">
  
  function fguardar()
  {
    if((document.forma.id_docente.value!=0) && (document.forma.id_tipo_docente.value!=0) && (document.forma.id_tipo_asignacion.value!="") && (document.forma.nro_resolucion.value!="") && (document.forma.fec_inicio.value!="")  && (document.forma.fec_fin.value!="") && (document.forma.fec_resolucion.value!=""))
    {
      document.forma.submit();
    }
    else
    {
      alert("Los campos con (*), son obligatorios");
    }
  }
  iniciar();
</script>

<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>   

<%@ include file="../Inferior.jsp" %>