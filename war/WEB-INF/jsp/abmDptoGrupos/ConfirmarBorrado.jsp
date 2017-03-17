<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>Sistema Integrado - Moxos</title>
  <link rel="stylesheet" href="<c:url value='/pagina.css'/>" type="text/css">
  <link rel="stylesheet" href="<c:url value='/basico.css'/>" type="text/css">
</head>
<body>
<div class="titulo">Administraci&oacute;n de Grupos - Eliminando</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>

<form method="post" action="registrarBorrado.fautapo">
  <input type="hidden" name="id_programa"        value="<c:out value="${id_programa}"/>">
  <input type="hidden" name="id_tipo_evaluacion" value="<c:out value="${id_tipo_evaluacion}"/>">
  <input type="hidden" name="id_prg_plan"        value="<c:out value="${id_prg_plan}"/>">
  <input type="hidden" name="gestion"            value="<c:out value="${gestion}"/>">
  <input type="hidden" name="periodo"            value="<c:out value="${periodo}"/>">
  <input type="hidden" name="_yabe"              value="<c:out value="${_yabe}"/>">
<table class="formulario">
<tr>
  <th colspan="3">INTRODUZCA LOS DATOS</th>
</tr>
<tr>
  <td class="etiqueta">Materia</td>
  <td class="etiqueta">::</td>
  <td><c:out value="${materia.materia}"/></td>
</tr>
<tr>
  <td class="etiqueta">Programa</td>
  <td class="etiqueta">::</td>
  <td><c:out value="${grupito.grupo}"/></td>
</tr>
<tr>
  <td class="etiqueta">Cupo Actual</td>
  <td class="etiqueta">::</td>
  <td><c:out value="${grupo.cupo_actual}"/></td>
</tr>
<tr>
  <td class="etiqueta">Cupo M&aacute;ximo</td>
  <td class="etiqueta">::</td>
  <td><c:out value="${grupo.cupo_max}"/></td>
</tr>
<tr>
  <td class="etiqueta">Horas</td>
  <td class="etiqueta">::</td>
  <td><c:out value="${grupo.horas}"/></td>
</tr>
<tr>
  <td class="etiqueta">Nro. Resoluci&oacute;n</td>
  <td class="etiqueta">::</td>
  <td><c:out value="${grupo.nro_resolucion}"/></td>
</tr>
<tr>
  <td class="etiqueta">Fecha Resoluci&oacute;n</td>
  <td class="etiqueta">::</td>
  <td><fmt:formatDate value="${grupo.fec_resolucion}" pattern="${formato_fecha}"/></td>
</tr>
</table>
<center>
  <input type="submit" value="Siguiente" class="siguiente">
</center>

</form>
</body>
</html>