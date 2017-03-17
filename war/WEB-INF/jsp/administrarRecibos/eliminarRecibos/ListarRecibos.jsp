<%@ include file="../../Superior.jsp" %>

<div class="titulo">Lista de Recibos</div>
<div class="volver"><a href="entrada.fautapo">Volver</a></div>
<br>

<form name="forma" action="listarConceptosImpresion.fautapo" method="post">
  <input type="hidden" name="id_transaccion" value="">
<table class="tabla">
<tr>
  <th>Transacci&oacute;n</th>
  <th>Nombres</th>
  <th>Fecha</th>
  <th>Hora</th>
  <th>Costo</th>
  <th>Cantidad</th>
  <th>Pagado</th>
  <th>Eliminar</th>
</tr>
<c:forEach var="recibo" items="${lRecibos}" varStatus="contador">
  <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
    <td><c:out value="${recibo.perfil}"/></td>
    <td><c:out value="${recibo.nombres}"/></td>
    <td><fmt:formatDate value="${recibo.fec_pago}" pattern="${formatoFecha}"/></td>
    <td><fmt:formatDate value="${recibo.fec_pago}" pattern="${formatoHora}"/></td>
    <td><c:out value="${recibo.total}"/></td>
    <td><c:out value="${recibo.cantidad}"/></td>
    <td><c:out value="${recibo.pagado}"/></td>
    <td><a class="eliminar" href="javascript: document.forma.submit();" onClick="document.forma.id_transaccion.value='<c:out value="${recibo.id_transaccion}"/>'; document.forma.action='confirmarBorrado.fautapo'">Eliminar</a></td>
  </tr>
</c:forEach>
</table>
</form>

<%@ include file="../../Inferior.jsp" %>