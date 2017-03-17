<%@ include file="../Superior.jsp" %>

<div class="titulo">Rectificaci&oacute;n de Notas</div>
<a class="volver" href="javascript:history.back();">Volver</a>

<table class="tabla">
  <tr>
    <th>RU</th>
    <td class="colb"><c:out value="${datosEstudiante.id_estudiante}"/></td>
    <th>Estudiante</th>
    <td class="colb">
      <c:out value="${datosPersona.nombres}"/> &nbsp; 
      <c:out value="${datosPersona.paterno}"/> &nbsp;
      <c:out value="${datosPersona.materno}"/>
    </td>
    <th>Plan</th>
    <td class="colb"><c:out value="${datosEstudiante.id_plan}"/>
  </tr>
  <tr>
    <th>Programa</th>
    <td class="colb"><c:out value="${datosPrograma.programa}"/>
    <th>Gesti&oacute;n</th>
    <td class="colb"><c:out value="${gestion}"/></td>
    <th>Periodo</th>
    <td class="colb"><c:out value="${periodo}"/></td>
  </tr>
</table>
<br>

<table class="tabla">
  <tr>
    <th>MATRICULA</th>
    <th>TIPO EVALUACION</th>
    <th>GESTION</th>
    <th>PERIODO</th>
    <th>GRUPO</th>
    <th>SIGLA</th>
    <th>MATERIA</th>
    <th>NOTA</th>
    <th>OBSERVACION</th>
    <th>RECTIFICADO</th>
    <th>RECTIFICAR</th>
  </tr>
  <c:forEach var="lista" items="${lNotas}" varStatus="contador">
    <!-- ********** Esto es para el efecto ************ -->
      <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
    <!-- ********** Fin  efecto ************ -->
      <td align="center"><c:out value="${lista.id_matricula}"/></td>
      <td><c:out value="${lista.tipo_evaluacion}"/></td>
      <td align="center"><c:out value="${lista.gestion}"/></td>
      <td align="center"><c:out value="${lista.periodo}"/></td>
      <td align="center"><c:out value="${lista.grupo}"/></td>
      <td><c:out value="${lista.sigla}"/></td>
      <td><c:out value="${lista.materia}"/></td>
      <td><c:if test="${lista.nota == '-1'}"><font color="red">Sin nota</font></c:if>
          <c:if test="${lista.nota != '-1'}"><c:out value="${lista.nota}"/></c:if>
      </td>
      <td><c:out value="${lista.observacion}"/></td>
      <td align="center"><c:if test="${lista.rectificado == false}">NO</c:if>
          <c:if test="${lista.rectificado == true}"><font color="red"><b>SI</b></font></c:if>
      </td>
      <form name='forma<c:out value="${contador.count}"/>' method="POST" action="confirmarRectificacion.fautapo">
        <input type="hidden" name="gestion"            value="<c:out value="${gestion}"/>">
        <input type="hidden" name="periodo"            value="<c:out value="${periodo}"/>">
        <input type="hidden" name="id_nota"            value="<c:out value="${lista.id_nota}"/>">
        <input type="hidden" name="id_matricula"       value="<c:out value="${lista.id_matricula}"/>">
        <input type="hidden" name="id_materia"         value="<c:out value="${lista.id_materia}"/>">
        <input type="hidden" name="id_grupo"           value="<c:out value="${lista.id_grupo}"/>">
        <input type="hidden" name="id_tipo_evaluacion" value="<c:out value="${lista.id_tipo_evaluacion}"/>">
        <input type="hidden" name="id_estudiante"      value="<c:out value="${datosEstudiante.id_estudiante}"/>">
        <td>
	  <c:if test="${lista.rectificado==false}">
            <a class="agregar" href='javascript: document.forma<c:out value="${contador.count}"/>.submit();' > Rectificar </a>
	  </c:if>
	  <c:if test="${lista.rectificado==true}">
            <a class="modificar" href='javascript: document.forma<c:out value="${contador.count}"/>.submit();' > Modificar Rectificaci&oacute;n </a>
	  </c:if>
	</td>
      </form>
    </tr>
  </c:forEach>
</table>

<%@ include file="../Inferior.jsp" %>