<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />

<c:if test="${fn:length(datos)>0}">
<table border="0" width="100%">
  <!-- SE REPITE-->
  <thead>
  <tr>
    <td width="100%" align="center" cellspancin="0" cellpading="0">
    <table width="100%">
      <tr>
        <td width="14%" align="center">
          <img src="<c:url value='${datosInstitucion.logo}'/>" border="0" ALT="logo institucion">
        </td>
        <td width="72%" align="center">
          <table width="100%" heigth="100%" cellpading="2" cellspacing="0" >
            <tr>
              <td align="center"><h1><b><c:out value='${datosInstitucion.institucion}'/></h1></td>
            <tr>
            <tr>
              <td align="center"><font size="3"><b><c:out value='${datosInstitucion.localidad}'/> - <c:out value='${datosInstitucion.departamento}'/> - <c:out value='${datosInstitucion.pais}'/></b></font></td>
            <tr>
            </tr>
              <td align="center"><c:out value='${datosInstitucion.actividad}'/></td>
            </tr>
          </table>
        </td>
        <td width="14%">
          Fecha : <a href='javascript: window.print()' style="color:black"><fmt:formatDate value="${now}" pattern="${formatoFecha}"/></a>
        </td>
      </tr>
    </table>
    <hr>
    <table width="100%" align="center">
      <tr>
        <td align="center"><label><h1><c:out value="${titulo}" escapeXml="false"/></h1></label></td>
      </tr>
    </table>
    <table border="0">
      <tr>
        <c:if test="${!empty programa.programa}">
          <td><b>Programa ::</b></td>
          <td><c:out value="${programa.programa}"/></td>
	</c:if>
        <td><b>Periodo ::</b></td>
        <td><c:out value="${periodo}"/> - <c:out value="${gestion}"/></td>
      </tr>
    </table>
    <br>
    </td>
  </tr>
  </thead>
  <!-- HASTA AQUI SE REPITE-->

  <tbody class="tabla"><tr>
    <!-- ************** ETIQUETAS ************ -->
    <table class="tabla" width="97%">
    <tr>
      <c:forEach var="lista" items="${etiquetas}" varStatus="contador2" begin="${desde}">
        <th><c:out value="${etiquetas[contador2.index]}" escapeXml="False"/></th>
      </c:forEach>
    </tr>
    <!-- ************** FIN ETIQUETAS ************ -->
  <!-- **************    VALORES     ************ -->
   <c:forEach varStatus="filas" begin="0" end="${fn:length(datos)-1}">
    <tr>
      <c:forEach var="lista1" items="${etiquetas}" varStatus="columnas" begin="${desde}">
        <c:out value='${datos[filas.index][columnas.index]}' escapeXml='false'/>
      </c:forEach>
    </tr>
  </c:forEach>
  <!-- **************  FIN VALORES   ************ -->
  <!-- **************    TOTALES     ************ -->
   </table>
  </tr></tbody>
</table>
<br>
<br>
</c:if>
<c:if test="${fn:length(datos)==0}">
  <br><br>
  <blink>
    <center>
      <div class='cuadroAviso' >
        <div class="titulo">&iexcl;Aviso!</div>
        No hay registros para mostrar
      </div>
    </center>
  </blink>
</c:if>
<%@ include file="../../Inferior.jsp" %>