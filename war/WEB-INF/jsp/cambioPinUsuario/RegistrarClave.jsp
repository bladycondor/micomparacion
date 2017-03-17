<%@ include file="../Superior.jsp" %>

<body onload='inicio(document.forma.clave)'>

<div class="titulo">Cambio Clave(PIN)</div>
<br>

<form method="post" action='<c:url value="/registrarClave.fautapo"/>'>
  <table class="formulario">
    <tr>
      <th colspan="3">INTRODUZCA LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta">Usuario</td>
      <td class="etiqueta">::</td>
      <td><c:out value="${nombres}"/></td>
    </tr>
    <tr>
      <td class="etiqueta">Nueva clave</td>
      <td class="etiqueta">::</td>
      <td><input type="password" name="nueva_clave" maxlength="10" onblur="validarClave(this,'A9')"></td>
    </tr>
    <tr>
      <td class="etiqueta">Confirmar clave</td>
      <td class="etiqueta">::</td>
      <td><input type="password" name="confirmacion_clave" maxlength="10" onblur="validarClave(this,'A9')"></td>
    </tr>
    <tr>
      <td colspan="3" align="center"><input type="submit" name="boton" value="Aceptar" class="aceptar"></td>
    </tr>
  </table>
</form>

<%@ include file="../Inferior.jsp" %>