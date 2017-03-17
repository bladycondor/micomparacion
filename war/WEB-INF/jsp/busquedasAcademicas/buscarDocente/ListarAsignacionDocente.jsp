<%@ include file="../../Superior.jsp" %>

<div class="titulo">Listar Asignaci&oacute;n Docente</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<br>

<table class="tabla">
  <tr>
    <th>Docente</th>
    <td class="colb"><c:out value="${datosDocente.nombre_completo}"/>
  </tr>
</table>
<br>

<h3> Lista de Materias Asignadas al Docente</h3>
<table class="tabla">
  <tr>
    <th>Nro.</th>
    <th>GESTION</th>
    <th>PERIODO</th>
	<th>CARRERA</th>
    <th>SIGLA</th>
    <th>MATERIA</th>
    <th>GRUPO</th>
	<th>CARGA HORARIA SEMANAL</th>
	<th>NRO. RESOLUCION</th>	
	<th>TIPO DE DOCENTE</th>
	<th>DEDICACION</th>
	<th>TIPO DE EVALUACION</th>
  </tr>    
  <c:forEach var="datos" items="${lAsignaciones}" varStatus="contador">
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ --> 
      <td><c:out value="${contador.count}"/></td>
      <td><c:out value="${datos.gestion}"/></td>
      <td><c:out value="${datos.periodo}"/></td>
	  <td><c:out value="${datos.departamento}"/></td>
      <td><c:out value="${datos.sigla}"/></td>
      <td><c:out value="${datos.materia}"/></td>
      <td><c:out value="${datos.grupo}"/></td>
	  <td><c:out value="${datos.total_horas}"/></td>
	  <td><c:out value="${datos.nro_resolucionhcu}"/></td>
	  
	  
	
	  
	  <td><c:out value="${datos.tipo_docente}"/></td>
	  <td><c:out value="${datos.tipo_asignacion}"/></td>
	  <td><c:out value="${datos.tipo_evaluacion}"/></td>
	  
    </tr>
  </c:forEach>
  </tr>
</table>

<%@ include file="../../Inferior.jsp" %>