<%@ include file="../../Superior.jsp" %>
<script language='JavaScript' SRC="../validar.js">  </script>

<div class="titulo">Retiro y adici&oacute;n de materias</div>
<br>
<form  name="fvolver" method='post' action="<c:url value='/retiroAdicionMaterias/comprobarEntrada.fautapo'/>">
    <input type="hidden" name="gestion"          value="<c:out value='${gestion}'/>" >
    <input type="hidden" name="periodo"          value="<c:out value='${periodo}'/>" >
    <input type=hidden name="id_tipo_evaluacion" value=<c:out value="${id_tipo_evaluacion}"/> >
    <input type=hidden name="id_programa"        value=<c:out value="${id_programa}"/> >
  <div><a class="volver" href="javascript:document.fvolver.submit();">Volver</a></div>      
</form>


<form method=post action=<c:url value="/retiroAdicionMaterias/listarProgramacionMaterias.fautapo"/> >

<table class="tabla">
<tr>
  <th class=colh>RU </th>
  <th class=colh>NOMBRES</th>
  <th class=colh>PLAN</th>
  <th class=colh>PROGRAMA</th>
  <th class=colh>TIPO EVALUACION</th>
  <th class=colh>GESTION</th>
  <th class=colh>PERIODO</th>
<tr>
  <td class=colb><c:out value="${datosEstudiante.id_estudiante}"/></td>
  <td class=colb><c:out value="${datosPersona.paterno}"/> &nbsp; <c:out value="${datosPersona.materno}"/> &nbsp; <c:out value="${datosPersona.nombres}"/> </td>
  <td class=colb><c:out value="${datosEstudiante.id_plan}"/> </td>
  <td class=colb><c:out value="${datosPrograma.programa}"/> </td>
  <td class=colb><c:out value="${datosTipoEval.tipo_evaluacion}"/> </td>
  <td class=colb><c:out value="${gestion}"/> </td>
  <td class=colb><c:out value="${periodo}"/> </td>
</table>
<br>

<table class="tabla">
  <tr class=colh>
    <td align=center colspan=3>SELECCIONE  UNA  ACCI&Oacute;N</td>
  </tr>
  <tr class=colh>
    <td align=center><input type=submit name="accion" value="Retiro"></td>
    <td align=center><input type=submit name="accion" value="" disabled ="false"></td>
    <td align=center><input type=submit name="accion" value="Cambio de grupo"></td>
  </tr>
</table>

<input type=hidden name="id_tipo_periodo"     value=<c:out value="${id_tipo_periodo}"/> >
<input type=hidden name="id_estudiante"       value=<c:out value="${datosEstudiante.id_estudiante}"/> >
<input type=hidden name="id_programa"         value=<c:out value="${datosEstudiante.id_programa}"/> >
<input type=hidden name="gestion"             value=<c:out value="${gestion}"/> >
<input type=hidden name="periodo"             value=<c:out value="${periodo}"/> >
<input type=hidden name="id_tipo_evaluacion"  value=<c:out value="${id_tipo_evaluacion}"/> >
</form>

<%@ include file="../../Inferior.jsp" %>