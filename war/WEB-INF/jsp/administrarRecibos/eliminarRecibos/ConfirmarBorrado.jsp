<%@ include file="../../Superior.jsp" %>

<div class="titulo">Confirmar Borrado del Recibo</div>
<a class="volver" href="javascript:history.back();">Volver</a>
<br>
<h3><center>RECIBO : <c:out value="${datosTransaccion.nro_recibo}"/></center></h3>

<br>
<!-- Imprime los datos del Estudiante, Docente, Postulante, Administrativo o Persona ajena -->
<table class="tabla">
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
    <td><c:out value="${datosTransaccion.periodo}"/> - <c:out value="${datosTransaccion.gestion}"/></td>
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
    <th>Fecha</th>
    <td><fmt:formatDate value="${datosTransaccion.fec_pago}" pattern="${formatoFecha}"/></td>
  </tr>
</table>

<br>
<c:if test="${!empty descuento}">
  <h4> Tipo de descuento : <c:out value="${descuento.tipo_descuento}"/> </h4>
</c:if>

<center>
<table class="tabla">
  <tr>
    <th colspan="2"><c:out value="${datosPerfil.perfil}"/></th>	
  </tr>
  <tr>
    <th>Concepto</th>
    <th>Monto (Bs.)</th>
  </tr>
  <c:forEach var="lista" items="${lDetalles}" varStatus="contador">
    <tr>
      <td><c:out value="${lista.concepto}"/></td>
      <td align="right"><c:out value="${lista.pagado}"/></td>
    </tr>
  </c:forEach>
  <tr>
    <th>Son:: <c:out value="${literal}"/></th>
    <th align="right"><b><c:out value="${datosTransaccion.pagado}"/></b></th>
  </tr>
</table>
<br>
<form method="post" action="borrarRecibo.fautapo">
   <input type="hidden" name="id_estudiante" value="<c:out value="${estudiante.id_estudiante}"/>">
   <input type="hidden" name="gestion" value="<c:out value="${datosTransaccion.gestion}"/>">
   <input type="hidden" name="periodo" value="<c:out value="${datosTransaccion.periodo}"/>">   
   <input type="hidden" name="id_transaccion" value="<c:out value="${datosTransaccion.id_transaccion}"/>">
   <input type="hidden" name="id_perfil" value="<c:out value="${datosPerfil.id_perfil}"/>">
  
  <input type="submit" class="cancelar" value="Eliminar">
</form>
</center>
<%@ include file="../../Inferior.jsp" %>