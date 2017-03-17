<%@ include file="../../Superior.jsp" %>
<body onload='inicio(document.forma.clave"${now}")'>

<div class="titulo">Imprimir Plan De Estudios</div>
<br>
<form name="forma" action="listarProgramasPlanes.fautapo" method="POST">
  <table class="formulario">
    <tr>
      <th colspan="3" align="center">INTRODUZCA LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta">Usuario</td>
      <td class="etiqueta">::</td>
      <td><c:out value="${usuario}" /></td>
    </tr>
    <tr>
      <td class="etiqueta" align="right">Clave <font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>
      <td>
      <input type="password" name='clave<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />'>
      </td>
    </tr>
    <tr>
      <td colspan="3" align="center"><input class="siguiente" type="submit" value="Buscar"></td>
      <input type="hidden" name="nombres"  value="<c:out value='${usuario}'/>">
    </tr>
  </table>
</form>

<%@ include file="../../Inferior.jsp" %>