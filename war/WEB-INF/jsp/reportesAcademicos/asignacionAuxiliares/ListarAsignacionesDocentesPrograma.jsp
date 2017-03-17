<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />
<table width="100%">
  <!-- SE REPITE-->
  <thead>
  <tr>
    <td width="100%" align="center" cellspancin="0" cellpading="0">
    <table width="100%">
      <tr>
        <td width="14%" align="center">
          <img src="<c:url value='${datosInstitucion.logo}'/>" border="0" ALT="logo institucion" width="50%">
        </td>
        <td width="72%" align="center">
          <table width="100%" heigth="100%" cellpading="2" cellspacing="0" >
            <tr>
              <td align="center"><h1><b><c:out value='${datosInstitucion.institucion}'/></h1></td>
            <tr>
            <tr>
              <td align="center"><font size="3"><b><c:out value='${datosInstitucionsede.localidad}'/> - <c:out value='${datosInstitucionsede.departamento}'/> - <c:out value='${datosInstitucionsede.pais}'/></b></font></td>
            <tr>
            </tr>
              <td align="center"><c:out value='${datosInstitucion.actividad}'/></td>
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
        <td align="center"><label><h2>ASIGNACION AUXILIARES - MATERIAS</h2></label></td>
      </tr>
    </table>
    <table border="0" width="97%">
      <tr>
        <td>CARRERA::</td>
        <td><c:out value="${datosPrograma.programa}"/></td>
        <td>FACULTAD::</td>
        <td><c:out value="${datosFacultad.facultad}"/></td>
      </tr>
      <tr>
        <td>PLAN::</td>
        <td><c:out value="${id_plan}"/></td>
        <td>PERIODO ::</td>
        <td><c:out value="${periodo}"/> - <c:out value="${gestion}"/></td>
      </tr>
    </table>
    <br>
    </td>
  </tr>
  </thead>
  <!-- HASTA AQUI SE REPITE-->
  <tbody>

  <c:if test="${ empty lAsignacionDocentesMateria}">
    <center><div class="cuadroAviso" align="center">No existen materias para la gesti&oacute;n <c:out value="${gestion}"/>, periodo <c:out value="${periodo}"/></div></center>
  </c:if>

  <table class="tabla" width="100%">
  <thead>
      <tr>
        <th>NRO</th>
	<th>NIVEL<br>ACADEMICO</th>       
        <th>SIGLA</th>
        <th>MATERIA</th>
	<th>R.U.</th>
	<th>AUXILIAR</th>
	<th>TIPO EVALUACION</th>
      </tr>
      <c:forEach var="asignacion" items="${lAsignacionDocentesMateria}" varStatus="contador">
        <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
	  <td><c:out value="${contador.count}"/></td>
	  <td align="center"><c:out value="${asignacion.nivel_academico}"/></td>          
          <td><c:out value="${asignacion.sigla}"/></td>
	  <td><c:out value="${asignacion.materia}"/></td>
	  <td><c:out value="${asignacion.id_persona}"/></td>
	  <td><c:out value="${asignacion.paterno}"/>&nbsp;<c:out value="${asignacion.materno}"/>&nbsp;<c:out value="${asignacion.nombres}"/></td>
	  <td><c:out value="${asignacion.tipo_evaluacion}"/></td>
      </tr>
    </c:forEach>
  </table>
<%@ include file="../../Inferior.jsp" %>