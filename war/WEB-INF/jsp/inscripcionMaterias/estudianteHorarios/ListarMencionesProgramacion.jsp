<%@ include file="../../Superior.jsp" %>
<script language='JavaScript' SRC="../validar.js">  </script>

<div class="titulo">Inscripci&oacute;n de materias </div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<br>
<form method=post action=<c:url value="/inscripcionMateriasEstudianteHorarios/listarProgramacionMateriasMencion.fautapo"/> >

<table class="tabla">
<tr>
  <th>RU </th>
  <th>NOMBRES</th>
  <th>PLAN</th>
  <th>PROGRAMA</th>
  
  
<tr>
  <td class=colb><c:out value="${datosEstudiante.id_estudiante}"/> </td>
  <td class=colb><c:out value="${datosPersona.paterno}"/> &nbsp; <c:out value="${datosPersona.materno}"/> &nbsp; <c:out value="${datosPersona.nombres}"/></td>
  <td class=colb><c:out value="${datosEstudiante.id_plan}"/></td>
  <td class=colb><c:out value="${datosPrograma.programa}"/></td>
</table>
<br>
<table class="tabla">
  <tr>
    <th>SELECCIONAR</th>
    <th>MENCI&Oacute;N</th>    
  </tr>
  <c:forEach var="menciones" items="${lMenciones}">
    <!-- ********** Esto es para el efecto ************ -->
    <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
    <!-- ********** Fin  efecto ************ -->   
      <td><input type=radio name='id_mencion' value="<c:out value="${menciones.id_mencion}"/>" ></td>
      <td><c:out value="${menciones.mencion}"/></td>
    </tr>
  </c:forEach>
  <tr>
    <td colspan=5 align="center"><input class="aceptar" type=submit value=Aceptar></td>
  </tr>
</table>

<input type="hidden" name="id_periodo"           value='<c:out value="${id_periodo}"/>' >
<input type="hidden" name="id_tipo_programacion" value="<c:out value='${id_tipo_programacion}'/>"> 
<input type="hidden" name="gestion"              value='<c:out value="${gestion}"/>' >
<input type="hidden" name="periodo"              value='<c:out value="${periodo}"/>' >

</form>

<%@ include file="../../Inferior.jsp" %>