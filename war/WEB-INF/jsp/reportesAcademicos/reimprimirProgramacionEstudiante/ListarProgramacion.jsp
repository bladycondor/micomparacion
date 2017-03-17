<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />

<table border="0" width="100%">
  <tr>
    <td width="14%" align="center">
      <img src="<c:url value='${datosInstitucion.logo}'/>" border="0" width=40% heigth=40%  ALT="logo institucion">
    </td>
    <td width="72%" align="center">
      <table width="100%" heigth="100%" cellpading="2" cellspacing="0" >
        <tr>
          <td align="center"><h2><b><c:out value='${datosInstitucion.institucion}'/></h2></td>
        <tr>
        <tr>
          <td align="center"><font size="2"><b><c:out value='${datosInstitucionsede.localidad}'/> - <c:out value='${datosInstitucionsede.departamento}'/> - <c:out value='${datosInstitucionsede.pais}'/></b></font></td>
        <tr>
        </tr>
         <!-- <td align="center"><c:out value='${datosInstitucion.actividad}'/></td>-->
        </tr>
      </table>
    </td>
    <td width="14%">
      Fecha : <a href='javascript: window.print()'><fmt:formatDate value="${now}" pattern="${formatoFecha}"/></a> 
    </td>
  </tr>
</table>
<hr>
<table width="100%" align="center">
  <tr>
    <td align="center"><label><h3>PROGRAMACION DE MATERIAS</h3><h3>Datos del Alumno</h3></label></td>
  </tr>
</table>
<table align="center" width="80%">
  <tr>
    <th>RU</th>
    <th>::</th>
    <td><c:out value="${datosEstudiante.id_estudiante}"/>
    <th>CI</th>
    <th>::</th>
    <td><c:out value="${datosEstudiante2.dip}"/>
    <th>Estudiante</th>
    <th>::</th>
    <td>
      <c:out value="${datosEstudiante2.nombres}"/> &nbsp;
      <c:out value="${datosEstudiante2.paterno}"/> &nbsp;
      <c:out value="${datosEstudiante2.materno}"/>
    </td>
  </tr>
  <tr>
    <th>Tipo</th>
    <th>::</th>
    <td><c:out value="${datosEstudiante2.tipo_estudiante}"/>
    <th>Plan</th>
    <th>::</th>
    <td><c:out value="${datosEstudiante.id_plan}"/>
    <th>Programa</th>
    <th>::</th>
    <td><c:out value="${datosPrograma.programa}"/>
  </tr>
</table>

<center>
<!--<h3> </h3>-->
<h3>Materias Programadas   Gesti&oacute;n: <c:out value="${periodo}"/>-<c:out value="${gestion}"/></h3>
<table class="tabla" width="80%">
  <tr>
    <th>NRO.</th>
    <th>EVALUACI&Oacute;N</th>
    <th>NIVEL</th>
    <th>SIGLA</th>
    <th>MATERIA</th>
    <th>GRUPO</th>
    <th>FECHA</th>
    <th>DOCENTE</th>
  </tr>
  <c:forEach var="lista" items="${lProgramacion}" varStatus="contador">
    <tr>
      <td align="center"><c:out value="${contador.count}"/></td>
      <td><c:out value="${lista.tipo_evaluacion}"/></td>
      <td align="center"><c:out value="${lista.nivel_academico}"/></td>
      <td><c:out value="${lista.sigla}"/></td>
      <td><c:out value="${lista.materia}"/></td>
      <td align="center"><c:out value="${lista.grupo}"/></td>
      <td><fmt:formatDate value="${lista.fec_modificacion}" pattern="${formatoFecha}"/></td>
      <td><c:out value="${lista.nombres}"/></td>
   </tr>
 </c:forEach>
</table>
</center>

<br>
<table align="center" border="0" width="80%">
  <tr>
    <td align="bottom" align="right">
      <fmt:formatDate value="${now}" pattern="${formatoFecha} ${formatoHora}"/> &nbsp; [<c:out value="${cliente.nombres}"/>]
    </td>
  </tr>
</table>

<%@ include file="../../Inferior.jsp" %>