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
<div class="titulo">Nro. de Estudiantes programados y no programados</div>
<br>
<br>
<form name="forma" method="post" action="formarReporte.fautapo"  target="_blank">
<br>
<table class="formulario">
<tr>
  <th colspan="3">INTRODUZCA LOS DATOS</th>
</tr>
<tr>
  <td class="etiqueta">Gesti&oacute;n <font color='red'>(*)</font></td>
  <td class="etiqueta">::</td>
  <td><input type="text" name="gestion" value="<c:out value="${gestion}"/>" size="4" maxlength="4"></td>
</tr>
<tr>
  <td class="etiqueta">Periodo <font color='red'>(*)</font></td>
  <td class="etiqueta">::</td>
  <td><input type="text" name="periodo" value="<c:out value="${periodo}"/>" size="1" maxlength="1"></td>
</tr>
<tr>
  <td class="etiqueta">Tipo de Evaluaci&oacute;n <font color='red'>(*)</font></td>
  <td class="etiqueta">::</td>
  <td>
    <select name="id_tipo_evaluacion">
      <option value=""> - Elija una opción - </option>    
      <c:forEach var="tipo" items="${lTiposEvaluaciones}">
        <option value="<c:out value="${tipo.id_tipo_evaluacion}"/>"> <c:out value="${tipo.tipo_evaluacion}"/></option>
      </c:forEach>
    </select>
  </td>
</tr>
</table>
<center><input type="submit" value="Aceptar >"></center>

</form>
<%@ include file="../../../Inferior.jsp" %>