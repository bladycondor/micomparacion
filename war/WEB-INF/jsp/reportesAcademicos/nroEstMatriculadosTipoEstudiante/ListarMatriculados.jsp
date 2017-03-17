<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />

<table border="0" width="100%">
  <!-- SE REPITE-->
  <thead>
  <tr>
    <td width="100%" align="center" cellspancin="0" cellpading="0">
    <table width="100%">
      <tr>
        <td width="14%" align="center">
          <img src="<c:url value='${datosInstitucion.logo}'/>" border="0" ALT="logo institucion" width="40%">
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
        <td align="center"><label><h1>N&Uacute;MERO DE MATRICULADOS POR TIPO ESTUDIANTE</h1></label></td>
      </tr>
    </table>
    <table border="0">
      <tr>
        <td>PERIODO ::</td>
        <td><c:out value="${periodo}"/> - <c:out value="${gestion}"/></td>
      </tr>
    </table>
    <br>
    </td>
  </tr>
  </thead>
  <!-- HASTA AQUI SE REPITE-->
  <tr>
    <td>
    <table class="tabla" border="1" width="97%">
      <tr>
        <th>Nro</th>
		<th>Facultad</th>
        <th>Programa</th>
		<th>Nuevos</th>
		<th>Antiguos</th>
		<th>Total</th>
     <!--   <c:forEach var="ltiposestudiantes" items="${lTiposEstudiantes}" varStatus="contador">
          <th><c:out value="${ltiposestudiantes.tipo_estudiante}"/></th>    
        </c:forEach>-->
      </tr>
	  <c:set var="cont" value="0"/>
	  <c:set var="cont1" value="0"/>
	  <c:set var="cont2" value="0"/>
      <c:forEach var="lEstudiante" items="${lEstudiantes}" varStatus="contador">
        <tr>
          <td><c:out value="${contador.count}"/></td>    
		  <td><c:out value="${lEstudiante.facultad}"/></td>
          <td><c:out value="${lEstudiante.programa}"/></td>	 
		  <td><c:out value="${lEstudiante.aprobados}"/></td>
		  <td><c:out value="${lEstudiante.cantidad-lEstudiante.aprobados}"/></td>
		  <td><c:out value="${lEstudiante.cantidad}"/></td>	  
          <c:set var="cont" value="${cont+lEstudiante.aprobados}"/> 		  
		  <c:set var="cont1" value="${cont1+lEstudiante.cantidad-lEstudiante.aprobados}"/> 		  
		  <c:set var="cont2" value="${cont2+lEstudiante.cantidad}"/> 		  
        </tr>
      </c:forEach>
      <tr>
        <td class="colb" colspan="3">
          TOTALES POR TIPO ESTUDIANTE :
        </td>
		<td class="colb"><c:out value="${cont}"/></td> 
		<td class="colb"><c:out value="${cont1}"/></td> 
		<td class="colb"><c:out value="${cont2}"/></td> 		
      </tr>  
      <tr>
        <td class="colb" colspan="2">
          TOTAL MATRICULADOS :
        </td>
        <td class="colb" colspan="4">
          <c:out value="${cont2}"/> Matriculados
        </td>
      </tr>
    </table>
    </td>
  </tr>
</table>

<%@ include file="../../Inferior.jsp" %>