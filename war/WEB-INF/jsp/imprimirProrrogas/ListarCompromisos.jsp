<%@ include file="../Superior.jsp" %>

<div class="titulo">Impresi&oacute;n de Compromisos</div>
<br>
<form name="forma" action="imprimirCompromiso.fautapo" method="POST">
  <input type="hidden" name="id_estudiante" value="<c:out value="${estudiante.id_estudiante}"/>">
  <input type="hidden" name="id_compromiso" value="0">
<table class="tabla">
<tr>
  <th>Tipo de Documento</th>
  <th>Fecha de Vencimiento</th>
  <th>Imprimir</th>
</tr>
<c:forEach var="lista" items="${lCompromisos}" varStatus="contador">
  <!-- ********** Esto es para el efecto ************ -->
  <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  <!-- ********** Fin  efecto ************ -->
    <td><c:out value="${lista.tipo_documento}"/></td>
    <td><c:out value="${lista.fec_vencimiento}"/></td>
    <td><a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.id_compromiso.value='<c:out value="${lista.id_compromiso}"/>'">Imprimir</a> </td>
  </tr>
</c:forEach>
</table>
</form>

<%@ include file="../Inferior.jsp" %>