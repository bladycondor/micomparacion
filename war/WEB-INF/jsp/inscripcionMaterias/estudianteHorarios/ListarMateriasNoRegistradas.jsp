<%@ include file="../../Superior.jsp" %>

<div class="titulo">Inscripci&oacute;n de materias</class>
<br>
<h5>Materias que no fueron inscritas por falta de cupo</h5>
<table class="tabla">
  <tr>
    <th>Sigla</th>
    <th>Materia</th>
  <c:forEach var="bucle" items="${lMaterias}" varStatus="contador">
  <tr class=colb>
    <td><c:out value="${bucle.sigla}"/></td>
    <td><c:out value="${bucle.materia}"/></td>
  </tr>
  </c:forEach>
</table>

<%@ include file="../../Inferior.jsp" %>