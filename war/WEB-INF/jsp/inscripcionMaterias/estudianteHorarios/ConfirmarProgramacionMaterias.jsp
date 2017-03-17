<%@ include file="../../Superior.jsp" %>
<script language='JavaScript' SRC="../validar.js">  </script>

<div class="titulo">Inscripci&oacute;n de materias</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<br>
<form method=post action=<c:url value="/inscripcionMateriasEstudianteHorarios/registrarProgramacionMaterias.fautapo"/> >
<table class="tabla">
<tr>
  <th>RU</th>
  <th>NOMBRES</th>
  <th>PLAN</th>
  <th>PROGRAMA</th>
  <th>TIPO PROGRAMACION</th>
<tr>
  <td class=colb><c:out value="${datosEstudiante.id_estudiante}"/>
  <td class=colb><c:out value="${datosPersona.paterno}"/> &nbsp;<c:out value="${datosPersona.materno}"/> &nbsp; <c:out value="${datosPersona.nombres}"/>
  <td class=colb><c:out value="${datosEstudiante.id_plan}"/>
  <td class=colb><c:out value="${datosPrograma.programa}"/>
  <td class=colb align="center"> 
    <c:forEach var="parametro" items="${lParametros}" >
     <c:out value="${parametro.tipo_programacion}"/><br>
    </c:forEach>
  </td>
</table>


<c:if test="${tamanio_choque_periodo > 0}">
  <h3>Inscripci&oacute;n de materias no permitida</h3>
  <table>
  <tr class=colh align=center>
    <td>Nro</td>
    <td>Sigla</td>
    <td>Materia</td>
  <c:forEach var="materias" items="${lChoquePeriodos}" varStatus="contador">
    <tr class=colb>
      <td align=center><c:out value="${contador.count}"/></td>
      <td align=center><c:out value="${materias.sigla}"/></td>
      <td><c:out value="${materias.materia}"/></td>
   </tr>
  </c:forEach>
  </table>
</c:if>

<c:if test="${tamanio_choque > '0'}">
  <h3>Materias con choque de horario</h3>
  <table class="tabla">
  <tr>
    <th>Nro</th>
    <th>SIGLA</th>
    <th>MATERIA</th>
    <th>GRUPO</th>
  <c:forEach var="materias" items="${lChoqueMaterias}" varStatus="contador">
    <tr class="colb">
      <td align=center><c:out value="${contador.count}"/></td>
      <td align=center><c:out value="${materias.sigla}"/>  </td>
      <td><c:if test="${empty materias.modelo_ahorro}"><c:out value="${materias.materia}"/></c:if><c:if test="${!empty materias.modelo_ahorro}"><c:out value="${materias.modelo_ahorro}"/></c:if></td>
      <td><c:out value="${materias.grupo}"/></td>
   </tr>
  </c:forEach>
  </table>
</c:if>

<h3>Materias</h3>
<table class="tabla">
<tr>
  <th>Nro</th>
  <th>SIGLA</th>
  <th>MATERIA</th>
  <th>GRUPO</th>    
<c:forEach var="materias" items="${lMaterias}" varStatus="contador">
  <input type=hidden name='materia<c:out value="${contador.index}"/>' value='<c:out value="${materias.id_materia}"/>:<c:out value="${materias.id_grupo}"/>:<c:out value="${materias.id_modelo_ahorro}"/>' >
   <!-- ********** Esto es para el efecto ************ -->
   <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
   <!-- ********** Fin  efecto ************ -->   
    <td align=center><c:out value="${contador.count}"/></td>
    <td align=center><c:out value="${materias.sigla}"/></td>
    <td><c:out value="${materias.materia}"/></td>
    <td align=center><c:out value="${materias.grupo}"/></td>
  </tr>
</c:forEach>
<tr>
  <td colspan=5 align=center>
    <c:if test="${tamanio_choque == 0}">
      <input type=submit value=Confirmar>
    </c:if>
    <c:if test="${(tamanio_choque != '0') || (tamanio_choque_periodo != 0)}">
      <input type=button value=Volver onClick=javascript:history.back();> &nbsp; <input type=submit value=Confirmar>
    </c:if>
  </td>

</table>
<input type="hidden" name=id_tipo_periodo  value=<c:out value="${id_tipo_periodo}"/> >
<input type="hidden" name="total_materias" value=<c:out value="${total_materias}"/> >
<input type="hidden" name="gestion"        value=<c:out value="${gestion}"/> >
<input type="hidden" name="periodo"        value=<c:out value="${periodo}"/> >
</form>

<%@ include file="../../Inferior.jsp" %>