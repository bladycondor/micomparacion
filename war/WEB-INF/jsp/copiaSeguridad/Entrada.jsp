<%@ include file="../Superior.jsp" %>

<body onload='inicio(document.forma.clave)'>

<div class="titulo">Restaurar Copia de Seguridad</div>

<br>
<form action='<c:url value="/copiaSeguridad/listarArchivos.fautapo"/>' method="post">

<table class="formulario" >
  <tr>
    <th colspan=3 align="center">INTRODUZCA LOS DATOS</th>
  </tr>
  <tr>
    <td class="etiqueta">Usuario</td>
    <td class="etiqueta">::</td>
    <td><c:out value="${cliente.nombres}" /></td>
  </tr>
  <tr>
    <td class="etiqueta" align="right">Clave</td>
    <td class="etiqueta">::</td>
    <td><input type="password" name="clave"></td>
  </tr>
  <tr>
    <td colspan="3" align="center"><input type="submit" value="Buscar" class="siguiente"></td>
  </tr>
</table>

</form>

<%@ include file="../Inferior.jsp" %>