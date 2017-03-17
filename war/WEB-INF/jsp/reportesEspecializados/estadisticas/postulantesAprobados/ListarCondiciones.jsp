<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>Sistema Integrado - Moxos</title>
  <link rel="stylesheet" href="<c:url value='/pagina.css'/>" type="text/css">
  <link rel="stylesheet" href="<c:url value='/basico.css'/>" type="text/css">  
</head>
<body>
<div class="titulo">Postulantes Aprobados por Sexo y Nacionalidad</div>
<br>
<br>
<form name="forma" method="post" action="formarReporte.fautapo"  target="_blank">
<table class="formulario">
<tr>
  <th colspan="3">INTRODUZCA LOS DATOS</th>
</tr>
<tr>
  <td class="etiqueta">Admisi&oacute;n <font color='red'>(*)</font></td>
  <td class="etiqueta">::</td>
  <td>
    <select id="id_tipo_admision" name="id_tipo_admision">
      <option value="-1">Elija una opci&oacute;n...</option>
      <c:forEach var="admision" items="${lAdmisiones}">
        <option value="<c:out value="${admision.id_tipo_admision}"/>"><c:out value="${admision.tipo_admision}"/></option>
      </c:forEach>
    </select>
  </td>
</tr>
<tr>
  <td class="etiqueta">Gesti&oacute;n <font color='red'>(*)</td>
  <td class="etiqueta">::</font></td>
  <td><input type="text" name="gestion"></td>
</tr>
<tr>
  <td class="etiqueta">Periodo <font color='red'>(*)</font></td>
  <td class="etiqueta">::</td>
  <td><input type="text" name="periodo"></td>
</tr>
</table>
<center><input type="submit" value="Aceptar >"></center>

</form>
<%@ include file="../../../Inferior.jsp" %>