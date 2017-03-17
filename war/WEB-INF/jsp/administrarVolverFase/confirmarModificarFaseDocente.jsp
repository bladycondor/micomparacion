<%@ include file="../Superior.jsp" %>
<div class="titulo"> Admin. Retroceder fase</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<br>
<form name="forma" method="POST" action='<c:url value="/modificarFaseDocente.fautapo"/>' >
  <table class="formulario">
    <tr>
      <th colspan="3">CONFIRME LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta">Docente</td>
      <td class="etiqueta">::</td>
      <td> <c:out value="${docente}"/></td>
    </tr>
    <tr>
      <td class="etiqueta">Programa</td>
      <td class="etiqueta">::</td>
      <td> <c:out value="${programa}"/></td>
    </tr>
    <tr>
      <td class="etiqueta">Tipo evaluacion</td>
      <td class="etiqueta">::</td>
      <td> <c:out value="${tipo_evaluacion}"/></td>
    </tr>
    <tr>
      <td class="etiqueta">Gestion</td>
      <td class="etiqueta">::</td>
      <td> <c:out value="${gestion}"/></td>
    </tr>
    <tr>
      <td class="etiqueta">Periodo</td>
      <td class="etiqueta">::</td>
      <td> <c:out value="${periodo}"/></td>
    </tr>
    <tr>
      <td class="etiqueta">Fase</td>
      <td class="etiqueta">::</td>
      <td> <c:out value="${fase}"/></td>
    </tr>
    <tr>
      <td class="etiqueta">Materia</td>
      <td class="etiqueta">::</td>
      <td> <c:out value="${materia}"/></td>
    </tr>
    <tr>
      <td class="etiqueta">Grupo</td>
      <td class="etiqueta">::</td>
      <td> <c:out value="${grupo}"/></td>
    </tr>
  </table>
  <center>
     <input type="submit" class="aceptar" name='accion1' value='Aceptar'>
  </center>
  <input type="hidden" name='id_asignacion'       value='<c:out value="${id_asignacion}"/>'>
  <input type="hidden" name='id_docente'          value='<c:out value="${id_docente}"/>'>
  <input type="hidden" name='id_materia'          value='<c:out value="${id_materia}"/>'>
  <input type="hidden" name='id_grupo'            value='<c:out value="${id_grupo}"/>'>
  <input type="hidden" name='gestion'             value='<c:out value="${gestion}"/>'>
  <input type="hidden" name='periodo'             value='<c:out value="${periodo}"/>'>
  <input type="hidden" name='id_fase'             value='<c:out value="${id_fase}"/>'>
  <input type="hidden" name='id_tipo_evaluacion'  value='<c:out value="${id_tipo_evaluacion}"/>'>
  <input type="hidden" name='id_departamento'     value='<c:out value="${id_departamento}"/>'>
  <input type="hidden" name='id_programa'         value='<c:out value="${id_programa}"/>'>
  <input type="hidden" name='id_modelo_ahorro'    value='<c:out value="${id_modelo_ahorro}"/>'>
  <input type="hidden" name='id_tipo_docente'     value='<c:out value="${id_tipo_docente}"/>'>
</form>
<%@ include file="../Inferior.jsp" %>