<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />

<table border="0" width="100%">
  <!-- SE REPITE-->
  <tr>
    <td width="100%" align="center" cellspancin="0" cellpading="0">
    <table width="100%">
      <tr>
        <td width="14%" align="center">
          <form name="fvolver" action="listarPlanEstudios.fautapo" method="post">
            <input type="hidden" name="aplicacion"  value="/" >
            <input type="hidden" name="id_programa" value='<c:out value="${id_programa}"/>' >
            <div> <a href="javascript:document.fvolver.submit();">
            <img width="40%" src="<c:url value='${datosInstitucion.logo}'/>" border="0" ALT="logo institucion"></a></div>
          </form>
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
          <!--Fecha : <a href='javascript: window.print()'><fmt:formatDate value="${now}" pattern="${formatoFecha}"/></a> -->
        </td>
      </tr>
    </table>
    <hr>
    <table width="100%" align="center">
      <tr>
        <td align="center"><label><h1>PLAN DE ESTUDIOS</h1></label></td>
      </tr>
    </table>
    <table width="97%">
      <tr>
        <th align="right">RU::</th>
        <td><c:out value="${datosEstudiante.id_estudiante}"/></td>
        <th align="right">ESTUDIANTE::</th>
        <td>
          <c:out value="${datosPersona.nombres}"/> &nbsp; 
          <c:out value="${datosPersona.paterno}"/> &nbsp;
          <c:out value="${datosPersona.materno}"/>
        </td>
      </tr>
      <tr>
        <th align="right">CARRERA::</th>
        <td><c:out value="${datosPrograma.programa}"/></td>
        <th align="right">FACULTAD::</th>
        <td><c:out value="${datosFacultad.facultad}"/></td>
      </tr>
      <tr>
        <th align="right">PLAN ::</th>
        <td><c:out value="${datosEstudiante.id_plan}"/></td>
        <th align="right">NIVEL ACADEMICO::</th>
        <td><c:out value="${datosGrados.grado_academico}"/></td>
      </tr>
    </table>
    <br>
    </td>
  </tr>
  <!-- HASTA AQUI SE REPITE-->
  <tr>
    <td>
      <table class="tabla" width="100%">
        <thead>
        <tr>
          <th>NIVEL</th>
          <th>SIGLA</th>
          <th>NOMBRE DE LA ASIGNATURA</th>
          <th>H.T.</th>
		  <th>H.P.</th>
		  <th>T.H.</th>
          <th>PRE-REQUISITO</th>
        </tr>
	</thead>
        <c:set var="id_mencion_ant" value="0"/>
        <c:set var="id_nivel_ant" value="0"/>
        <c:forEach var="lista" items="${lPlanDeEstudios}" varStatus="contador">
          <c:if test="${(id_mencion_ant != lista.id_mencion) && (lista.id_mencion != 0)}">
            <tr>
              <th colspan="5">MENCION :: <c:out value="${lista.mencion}"/></th>
            </tr>
          </c:if>
          <c:if test="${id_nivel_ant != lista.nivel_academico}">
            <tr>
              <td>NIVEL :: <c:out value="${lista.nivel_academico}"/></td>
              <td colspan="4"></td>
            </tr>
          </c:if>
          <td></td>
          <td valign="top"><c:out value="${lista.sigla}"/></td>
          <td valign="top"><c:out value="${lista.materia}"/></td>
          <td valign="top"><c:out value="${lista.hrs_teoricas}"/></td>
		  <td valign="top"><c:out value="${lista.hrs_practicas}"/></td>
		  <td valign="top"><c:out value="${lista.hrs_practicas+lista.hrs_teoricas}"/></td>
          <td valign="top"><c:out value="${lista.materias_anteriores}" escapeXml="false"/></td>
        </tr>
        <c:set var="id_mencion_ant" value="${lista.id_mencion}"/>
        <c:set var="id_nivel_ant" value="${lista.nivel_academico}"/>
      </c:forEach>
    </table>
    </td>
  </tr>
</table>

<br><br>
<table width="100%">
  <tr>
    <th colspan="3"> <a href='javascript: window.print()'><c:out value='${datosInstitucionsede.localidad}'/>, <fmt:formatDate value="${now}" type="date" dateStyle="long"/></a></th>
  </tr>
  <tr>
    <td colspan="3"><br><br><br><br><br><br></td>
  </tr>
  <tr>
    <th width="35%">_____________________</th><th width="30%"></th><th width="35%">____________________</th>
  </tr>
  <tr>
    <th width="35%"><b>Jefe de Estudios</b></th><th width="30%"></th><th width="35%"><b>Director de Carrera</b></th>
  </tr>
</table>

<%@ include file="../../Inferior.jsp" %>