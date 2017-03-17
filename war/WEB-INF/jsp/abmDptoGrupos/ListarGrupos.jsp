<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>Sistema Integrado - Moxos</title>
  <link rel="stylesheet" href="<c:url value='/pagina.css'/>" type="text/css">
  <link rel="stylesheet" href="<c:url value='/basico.css'/>" type="text/css">
</head>
<body>
<div class="titulo">Administraci&oacute;n de Grupos</div>
<form name="fvolver" method="post" action="entrada.fautapo">
  <input type="hidden" name="gestion"            value="<c:out value="${gestion}"/>">
  <input type="hidden" name="periodo"            value="<c:out value="${periodo}"/>">
  <div class="volver"><a href='javascript: document.fvolver.submit();' > Volver </a></div>
</form>

<form name="forma" action="nuevoRegistro.fautapo" method="post">
  <input type="hidden" name="_yabe" value="">
  <input type="hidden" name="id_programa"        value="<c:out value="${programa.id_programa}"/>">
  <input type="hidden" name="id_prg_plan"        value="<c:out value="${id_prg_plan}"/>">
  <input type="hidden" name="id_tipo_evaluacion" value="<c:out value="${id_tipo_evaluacion}"/>">
  <input type="hidden" name="gestion"            value="<c:out value="${gestion}"/>">
  <input type="hidden" name="periodo"            value="<c:out value="${periodo}"/>">

<table class="tabla">
<tr>
  <th>Programa</th>
  <td><c:out value="${programa.programa}"/></td>
  <th>Plan</th>
  <td><c:out value="${datosPrgPlan.id_plan}"/></td>
  <th>Tipo Evaluaci&oacute;n</th>
  <td><c:out value="${datosTipoEvaluacion.tipo_evaluacion}"/></td>
  <th>Gesti&oacute;n</th>
  <td><c:out value="${gestion}"/></td>
  <th>Periodo</th>
  <td><c:out value="${periodo}"/></td>
</tr>
</table>
<div class="agregar"><a href="javascript: document.forma.submit()">Nuevo</a></div>
<table class="tabla">
<tr>
  <th>Materia</th><th>Grupo</th><th>Cupo Actual</th><th>Cupo M&aacute;ximo</th>
  <th>Horas</th><th>Nro. Resoluci&oacute;n</th><th>Fecha Resoluci&oacute;n</th>
  <th>MODIFICAR</th>
  <th>ELIMINAR</th>
</tr>
<c:forEach var="grupo" items="${lGrupos}" varStatus="cont">
  <tr <c:if test="${(cont.count mod 2) == 0}">bgColor="#FFFFD9"</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
    <td><c:out value="${grupo.materia}"/></td>
    <td><c:out value="${grupo.grupo}"/></td>
    <td><c:out value="${grupo.cupo_actual}"/></td>
    <td><c:out value="${grupo.cupo_max}"/></td>
    <td><c:out value="${grupo.horas}"/></td>
    <td><c:out value="${grupo.nro_resolucion}"/></td>
    <td><c:out value="${grupo.fechita}"/></td>
    <td><div class="modificar">
          <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action='modificaRegistro.fautapo';document.forma._yabe.value='<c:out value="${grupo.id_dpto_grupo}"/>'">Modificar</a>
        </div>
    </td>
    <td><div class="eliminar">
          <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action='confirmarBorrado.fautapo';document.forma._yabe.value='<c:out value="${grupo.id_dpto_grupo}"/>'">Eliminar</a>
        </div>
    </td>
  </tr>
</c:forEach>
</table>

</form>
</body>
</html>