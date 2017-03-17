<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>Sistema Integrado - Moxos</title>
  <link rel="stylesheet" href="<c:url value='/pagina.css'/>" type="text/css">
  <link rel="stylesheet" href="<c:url value='/basico.css'/>" type="text/css">  
  <script type="text/javascript" src="<c:url value='/js/ajax.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/js/combos.js'/>"></script>
</head>
<body>
<div class="titulo">N&uacute;mero de Programados</div>
<br>
<br>
<form name="forma" method="post" action="formarReporte.fautapo"  target="_blank">
<table class="formulario">
<tr>
  <th colspan="3">INTRODUZCA LOS DATOS</th>
</tr>
<tr>
  <td class="etiqueta">Gesti&oacute;n</td>
  <td class="etiqueta">::<font color='red'>(*)</font></td>
  <td><input type="text" name="gestion"></td>
</tr>
<tr>
  <td class="etiqueta">Periodo</td>
  <td class="etiqueta">::<font color='red'>(*)</font></td>
  <td><input type="text" name="periodo"></td>
</tr>
</table>
<center><input type="submit" value="Aceptar >"></center>

</form>
<%@ include file="../../../Inferior.jsp" %>