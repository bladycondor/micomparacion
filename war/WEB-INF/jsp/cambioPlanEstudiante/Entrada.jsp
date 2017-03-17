<%@ include file="../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date"/>
<body onload='inicio(document.forma.id_estudiante)'>
<script language='JavaScript' SRC="./validar.js"></script>

<div class="titulo">Cambio de Plan de Estudios</div>
<br>

<form name="forma" action="<c:url value="/listarPlanes.fautapo"/>" method="POST">
  <input type="hidden" name="hora" value='<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />' />
  <table class="formulario">
  <tr>
    <th colspan="3" align="center">INTRODUZCA LOS DATOS</th>
  </tr>
  <tr>
    <td class="etiqueta">Usuario</td>
    <td class="etiqueta">::</td>
    <td><c:out value="${cliente.nombres}" /></td>
  </tr>
  <tr>
    <td class="etiqueta" align="right">RU <font color='red'>(*)</font> </td>
    <td class="etiqueta">::</td>
    <td><input type="text" name="id_estudiante" onblur='validar(this,"9")'></td>
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
  </tr>
  </table>
</form>

<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>

</body>
<%@ include file="../Inferior.jsp" %>