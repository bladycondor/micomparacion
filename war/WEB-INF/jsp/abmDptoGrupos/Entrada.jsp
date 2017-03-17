<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>Sistema Integrado - Moxos</title>
  <link rel="stylesheet" href="<c:url value='/pagina.css'/>" type="text/css">
  <link rel="stylesheet" href="<c:url value='/basico.css'/>" type="text/css">
 <script language="javascript" src="<c:url value='/js/funciones.js'/>"></script>
</head>
<body onload='inicio(document.forma.gestion)'>
<div class="titulo">Administraci&oacute;n de Grupos</div>
<br>
<form method="post" action="listarFacultades.fautapo">
<table class="formulario" >
  <tr>
    <th colspan="3" align="center">INTRODUZCA LOS DATOS</th>
  </tr>
  <tr>
    <td class="etiqueta">Usuario</td>
    <td class="etiqueta">::</td>
    <td><c:out value="${usuario}" /></td>
  </tr>
  <tr>
    <td class="etiqueta">Gesti&oacute;n <font color='red'>(*)</font></td>
    <td class="etiqueta">::</td>
    <td><input type="text" name="gestion" value='<c:out value="${gestion}" />' onblur='validar(gestion,"9")' size="4" maxlength="4"></td>
  </tr>
  <tr>
    <td class="etiqueta">Periodo <font color='red'>(*)</font></td>
    <td class="etiqueta">::</td>
    <td><input type="text" name="periodo" value='<c:out value="${periodo}" />' onblur='validar(periodo,"9")' size="1" maxlength="1"> </td>
  </tr>
  <tr>
    <td class="etiqueta" align="right">Clave</td>
    <td class="etiqueta">::<font color='red'>(*)</font></td>
    <td><input type="password" name="clave"></td>
  </tr>
  <tr>
    <td colspan="3" align="center"><input type="submit" value="Buscar" class="siguiente"></td>
  </tr>
</table>

</form>
</body>
</html>