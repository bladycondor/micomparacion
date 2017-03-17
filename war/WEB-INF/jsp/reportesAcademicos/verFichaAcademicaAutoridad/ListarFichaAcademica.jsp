<%@ include file="../../Superior.jsp" %>

<jsp:useBean id="now" class="java.util.Date" />
<div class="titulo">Ver Ficha Acad&eacute;mica</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>

<table class="tabla">
  <tr>
    <th>RU</th>
    <td class="colb"><c:out value="${datosEstudiante.id_estudiante}"/></td>
    <th>Estudiante</th>
    <td class="colb">
      <c:out value="${datosEstudiante2.nombres}"/> &nbsp; 
      <c:out value="${datosEstudiante2.paterno}"/> &nbsp;
      <c:out value="${datosEstudiante2.materno}"/>
    </td>
  </tr>
  <tr>
    <th>Programa</th>
    <td class="colb"><c:out value="${datosPrograma.programa}"/>
    <th>Plan</th>
    <td class="colb"><c:out value="${datosEstudiante.id_plan}"/>
  </tr>
</table>

<h4>Listado de Materias Cursadas</h4>
<table class="tabla">
  <tr>
    <th>NRO.</th>
    <th>GESTION</th>
    <th>PLAN</th>
    <th>NIVEL</th>
    <th>SIGLA</th>
    <th>MATERIA</th>
    <th>TIPO EVAL</th>
    <th>NOTA</th>
    <th>OBSERVACION</th>
  </tr>
  <c:forEach var="lista" items="${lFichaAcademica}" varStatus="contador">
    <!-- ********** Esto es para el efecto ************ -->
      <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
    <!-- ********** Fin  efecto ************ -->
    <td><c:out value="${contador.count}"/></td>
    <td><c:out value="${lista.periodo}"/>-<c:out value="${lista.gestion}"/></td>
    <td><c:out value="${lista.id_plan}"/></td>
    <td><c:out value="${lista.nivel_academico}"/></td>
    <td><c:out value="${lista.sigla}"/></td>
    <td><c:out value="${lista.materia}"/></td>
     <td><c:out value="${lista.tipo_evaluacion}"/></td>
	 
    <c:if test="${(lista.id_estado == 'R') || (lista.id_estado == 'D')}" >
      <td><font color="red"><c:out value="${lista.nota}"/></font></td>	
      <td><font color="red"><c:out value="${lista.estado}"/></td>
	  
    </c:if>    
    
    <c:if test="${lista.id_estado == 'A'}">
      <td><c:out value="${lista.nota}"/></td>
      <td><c:out value="${lista.estado}"/></td>
    </c:if>
	
    <c:if test="${lista.id_estado == 'C'}">
      <td><c:out value="${lista.nota}"/></td>
      <td><c:out value="${lista.estado}"/></td>
    </c:if>
  
   </tr>
 </c:forEach>
</table>
<br>

<h4>Listado de Materias Convalidadas Autom&aacute;ticamente</h4>
<table class="tabla">
  <tr>
    <th>NRO.</th>
	<th>GESTION</th>
    <th>NIVEL</th>	
    <th>SIGLA</th>
    <th>MATERIA</th>
    <th>NOTA</th>
    <th>TIPO EVAL</th>
  </tr>
  <c:forEach var="lista1" items="${lFichaAcademicaConvalidada}" varStatus="contador">
    <tr>
      <td><c:out value="${contador.count}"/></td>
      <td><c:out value="${lista1.periodo}"/>-<c:out value="${lista1.gestion}"/></td>    
	  <td><c:out value="${lista1.nivel_academico}"/></td>
      <td><c:out value="${lista1.sigla}"/></td>
      <td><c:out value="${lista1.materia}"/></td>
      <td><c:out value="${lista1.nota}"/></td>
      <td><c:out value="${lista1.tipo_evaluacion}"/></td>
   </tr>
 </c:forEach>
  <tr>
    <tr>
    <td colspan="2" algin="right"> 
      <c:out value='Trinidad'/>, <a href='javascript: window.print()'><fmt:formatDate value="${now}" type="date" dateStyle="long"/>
    </td>
  </tr>

<table>

<%@ include file="../../Inferior.jsp" %>