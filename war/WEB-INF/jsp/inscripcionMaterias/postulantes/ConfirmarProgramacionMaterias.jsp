<%@ include file="../../Superior.jsp" %>

<div class="titulo">Inscripci&oacute;n de materias postulantes</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<br>

<form method=post action=<c:url value="/inscripcionMateriasPostulante/registrarProgramacionMaterias.fautapo"/> >

<table class="tabla">
<tr>
  <th>RP</th>
  <th>NOMBRES</th>
  <th>PLAN</th>
  <th>PROGRAMA</th>
  <th>TIPO PROGRAMACION</th>
</tr>  
<tr>  
  <td class=colb><c:out value="${datosPostulante.id_postulante}"/>
  <td class=colb><c:out value="${datosPersona.paterno}"/> &nbsp; <c:out value="${datosPersona.materno}"/> &nbsp; <c:out value="${datosPersona.nombres}"/>
  <td class=colb><c:out value="${datosPostulante.id_plan}"/>
  <td class=colb><c:out value="${datosPrograma.programa}"/>
  <td class=colb align="center"> 
    <c:forEach var="parametro" items="${lParametros}" >
     <c:out value="${parametro.tipo_programacion}"/><br>
    </c:forEach>
  </td>
</tr>  
</table>

<br>
<div class="H3">Materias </div>
<table class="tabla">
<tr>
  <th>NRO</th>
  <th>SIGLA</th>
  <th>MATERIA</th>
  <th>GRUPO</th>
<c:forEach var="materias" items="${lMaterias}" varStatus="contador">
  <input type=hidden name='materia<c:out value="${contador.index}"/>' value='<c:out value="${materias.id_materia}"/>:<c:out value="${materias.id_modelo_ahorro}"/>:<c:out value="${materias.id_grupo}"/>' >
   <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
    <!-- ********** Fin  efecto ************ -->        
    <td><c:out value="${contador.count}"/></td>
    <td><c:out value="${materias.sigla}"/></td>
    <td><c:out value="${materias.materia}"/></td>
    <td><c:out value="${materias.grupo}"/></td>
  </tr>
</c:forEach>
<tr>
  <td colspan="5" align="center"><input type=submit value=Confirmar></td>
</table>
<input type=hidden name=id_postulante value=<c:out value="${datosPostulante.id_postulante}"/> >
<input type=hidden name=id_periodo value=<c:out value="${id_periodo}"/> >
<input type=hidden name=total_materias value=<c:out value="${total_materias}"/> >
<input type=hidden name="gestion"       value='<c:out value="${gestion}"/>' >
<input type=hidden name="periodo"       value='<c:out value="${periodo}"/>' >
</form>

<%@ include file="../../Inferior.jsp" %>