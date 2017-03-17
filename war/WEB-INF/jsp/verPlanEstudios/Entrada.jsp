<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>Sistema Integrado - Moxos</title>
  <link rel="stylesheet" href="<c:url value='/pagina.css'/>" type="text/css">
  <link rel="stylesheet" href="<c:url value='/basico.css'/>" type="text/css">
  <script language="javascript" src="<c:url value='/js/funciones.js'/>"></script>
</head>
<jsp:useBean id="now" class="java.util.Date"/>
<body onload='inicio(document.forma.clave<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />)'>
<div class="titulo">Ver Plan de Estudios</div>
<br>
<form method="post" action="verPlanesEstudios.fautapo">
  <input type="hidden" name="hora" value='<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />' />
<table class="formulario" >
  <tr>
    <th colspan="3" align="center">INTRODUZCA LOS DATOS</th>
  </tr>
  <tr>
    <td class="etiqueta">Usuario</td>
    <td class="etiqueta">::</td>
    <td><c:out value="${cliente.nombres}" /></td>
  </tr>
  <tr>
    <td class="etiqueta" align="right">Clave</td>
    <td class="etiqueta">::</td>
    <td><input type="password" name="clave<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />"></td>
  </tr>
  <tr>
    <td colspan="3" align="center"><input type="submit" value="Buscar" class="siguiente"></td>
  </tr>
</table>

</form>
</body>
</html>