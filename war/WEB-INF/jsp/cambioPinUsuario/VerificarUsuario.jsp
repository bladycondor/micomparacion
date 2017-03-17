<%@ include file="../Superior.jsp" %>

<jsp:useBean id="now" class="java.util.Date"/>
<body onload='inicio(document.forma.clave<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />)'>

<div class="titulo">Cambio Clave(PIN)</div>
<br>

<form method="post" action='<c:url value="/verificarUsuario.fautapo"/>'>
  <input type="hidden" name="hora" value='<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />' />
  <table class="formulario">
    <tr>
      <th colspan="3">INTRODUZCA LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta">Usuario</td>
      <td class="etiqueta">::</td>
      <td><c:out value="${nombres}" /></td>
    </tr>
    <tr>
      <td class="etiqueta" align="right">Clave</td>
      <td class="etiqueta">::</td>
      <td>
        <input type="password" name='clave<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />'>
      </td>
    </tr>
    <tr>
      <td colspan="3" align="center"><input type="submit" name="boton" value="Buscar" class="buscar"></td>
    </tr>
  </table>
</form>

<%@ include file="../Inferior.jsp" %>