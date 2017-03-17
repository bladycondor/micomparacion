<%@ include file="../../SuperiorCajas.jsp" %>

<table border="0" width="100%">
  <tr>
    <td width="14%" align="right">
      <IMG SRC="<c:url value='${logo}'/>" width="70" height="70" border="0" ALT="logo institucion">
    </td>
    <td width="72%" align="center">
      <table width="100%" heigth="100%" cellpading="2" cellspacing="0" >
        <tr>
          <td align="center"><font size="5"><b><c:out value='${datosInstitucion.institucion}'/></font></td>
        <tr>
        <tr>
          <td align="center"><font size="3"><b><c:out value='${datosInstitucion.localidad}'/> - <c:out value='${datosInstitucion.departamento}'/> - <c:out value='${datosInstitucion.pais}'/></b></font></td>
        <tr>
        </tr>
          <td align="center"><font size="2"><c:out value='${datosInstitucion.actividad}'/></font></td>
        </tr>
      </table>
    </td>
    <td width="14%">
      <font size="2">Fecha : <a href='javascript: window.print()'><fmt:formatDate value="${now}" pattern="${formatoFecha}"/></a></font> 
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center"><font size="5"><b>RECIBO &nbsp; <c:out value="${datosTransaccion.nro_recibo}"/></font></b></td>
  </tr>
</table>

<br>
<br>
<!-- Imprime los datos del Estudiante, Docente, Postulante, Administrativo o Persona ajena -->
<table class="tabla2">
  <tr>
    <th>Nombre Completo</th>
    <td>
      <c:out value="${estudiante.nombres}"/> <c:out value="${estudiante.paterno}"/> <c:out value="${estudiante.materno}"/>
    </td>
    <c:if test="${estudiante.id_estudiante > 0}">
      <th>Reg. Univ.</th>
      <td><c:out value="${estudiante.id_estudiante}"/></td>
    </c:if>
    <th>Gesti&oacute;n</th>
    <td><c:out value="${cliente.periodo}"/> - <c:out value="${cliente.gestion}"/></td>
  <c:if test="${!empty estudiante.programa}">
    </tr>
    <tr>
      <th>Programa</th>
      <td><c:out value="${estudiante.programa}"/>
  </c:if>
    <c:if test="${estudiante.id_estudiante > 0}">
      <th>Plan</th>
      <td><c:out value="${estudiante.id_plan}"/>
    </c:if>
    <th>Fecha de Pago</th>
    <td><fmt:formatDate value="${datosTransaccion.fec_pago}" pattern="${formatoFecha} ${formatoHora}"/></td>
  </tr>
</table>

<br>
<c:if test="${!empty descuento}">
  <h4> Tipo de descuento : <c:out value="${descuento.tipo_descuento}"/> </h4>
</c:if>

<table class="tabla">
  <tr>
    <th colspan="5"><c:out value="${datosPerfil.perfil}"/></th>
  </tr>
  <tr>
    <th>Concepto</th>
    <th>Precio/Unit. (Bs.)</th>
    <th>Cantidad</th>
    <th>Descuento</th>
    <th>Monto (Bs.)</th>
  </tr>
  <c:forEach var="lista" items="${lDetalles}" varStatus="contador">
    <tr>
      <td><c:out value="${lista.concepto}"/></td>
      <td align="center"><c:out value="${lista.cantidad}"/></td>
      <td align="right"><c:out value="${lista.descuento}"/></td>
      <td align="right"><c:out value="${lista.pagado}"/></td>
    </tr>
  </c:forEach>
  <tr>
    <th colspan="4">Son:: <c:out value="${literal}"/></th>
    <td align="right"><b><c:out value="${datosTransaccion.pagado}"/></b></td>
  </tr>
</table>
<br>

<br><br><br><br>
<center>
<table class="tabla2" width="100%">
  <tr>
    <td>
      ...............................................
      <br><c:out value="${datosTransaccion.nombres}"/>
      <br><b>Encargado(a) de cajas</b>
    </td>
  </tr>
</table>
</center>

<%@ include file="../../Inferior.jsp" %>