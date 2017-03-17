<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>Sistema Integrado - Moxos</title>
  <link rel="stylesheet" href="<c:url value='/pagina.css'/>" type="text/css">
  <link rel="stylesheet" href="<c:url value='/basico.css'/>" type="text/css">  
  <script type="text/javascript" src="<c:url value='/js/ajax.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/js/combos.js'/>"></script>
  <script language="JavaScript">
  function setCargarProgramas(valor) {
    martillo='id_programa';
    campo_id='id_programa';
    campito='programa';
    param='id_facultad='+valor;
    getConexion('<c:url value="/ajax/listarFclProgramas.fautapo"/>', 'setCargarCombito');
  }
  function setCargarPlanes(valor) {
    martillo='id_prg_plan';
    campo_id='id_plan';
    campito='plan';
    param='id_programa='+valor;
    getConexion('<c:url value="/ajax/listarPrgPlanes.fautapo"/>', 'setCargarCombito');
  }
  </script>
</head>
<body>
<div class="titulo">Promedio de Notas de Estudiantes, por Materia</div>
<br>
<br>
<form name="forma" method="post" action="formarReporte.fautapo"  target="_blank">
  <input type="hidden" name="tipo_cheque" value="<c:out value='${tipo_cheque}'/>">
<br>
<table class="formulario">
<tr>
  <th colspan="3">INTRODUZCA LOS DATOS</th>
</tr>
<tr>
  <td class="etiqueta">Facultad</td>
  <td class="etiqueta">::<font color='red'>(*)</font></td>
  <td>
    <select id="id_facultad" name="id_facultad" onChange="setCargarProgramas(this.options[this.selectedIndex].value)">
      <option value="-1">Elija una opci&oacute;n...</option>
      <c:forEach var="facultad" items="${lFacultades}" >
        <option value="<c:out value="${facultad.id_facultad}"/>"><c:out value="${facultad.facultad}"/></option>
      </c:forEach>
    </select>
  </td>
</tr>
<tr>
  <td class="etiqueta">Programa</td>
  <td class="etiqueta">::<font color='red'>(*)</font></td>
  <td>
    <select id="id_programa" name="id_programa" disabled="true" onchange="setCargarPlanes(this.options[this.selectedIndex].value)">
      <option value="-1">Elija una opci&oacute;n...</option>
    </select>
  </td>
</tr>
<tr>
  <td class="etiqueta">Plan</td>
  <td class="etiqueta">::<font color='red'>(*)</font></td>
  <td>
    <select id="id_prg_plan" name="id_prg_plan" disabled="true" >
      <option value="-1">Elija una opci&oacute;n...</option>
    </select>
  </td>
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