<%@ include file="../../Superior.jsp" %>

<jsp:useBean id="now" class="java.util.Date"/>
<div class="titulo">Reimprimir Recibos</div>
<br>
<form name="forma" action="listarConceptosImpresion.fautapo" method="post" target="_blank">
  <input type="hidden" name="hora" value="<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss"/>">

<table class="formulario" >
  <tr>
    <th colspan=3 align="center">INTRODUZCA LOS DATOS</th>
  </tr>
  <tr>
    <td class="etiqueta">Usuario</td>
    <td class="etiqueta">::</td>
    <td><c:out value="${usuario}" /></td>
  </tr>
  <tr>
    <td class="etiqueta">Nro. de Recibo <font color='red'>(*)</font></td>
    <td class="etiqueta">::</td>
    <td><input type="text" name="nro_recibo"></td>
  </tr>
  <tr>
    <td class="etiqueta">Clave <font color='red'>(*)</font></td>
    <td class="etiqueta">::</td>
    <td><input type="password" name="clave<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss"/>"></td>
  </tr>
  <tr>
    <td colspan="3" align="center"><input type="submit" value="Buscar" class="siguiente"></td>
  </tr>
</table>

</form>

<%@ include file="../../Inferior.jsp" %>