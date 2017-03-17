<%@ include file="../../Superior.jsp" %>

<div class="titulo">Convalidaci&oacute;n Manual </div>
<br>
<table>
  <tr>    
    <td>
      <form name="fvolver" action="<c:url value='comprobarEntrada.fautapo'/>" method="post">
        <input type="hidden" name="gestion" value="<c:out value='${gestion}'/>">
        <input type="hidden" name="periodo" value="<c:out value='${periodo}'/>">
	<input type="hidden" name="id_programa" value="<c:out value='${datosPrograma.id_programa}'/>">
      <div> <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a> </div>
    </form>
    </td>
  </tr>
</table>
<form method=post action=<c:url value="/convalidacionManual/listarMateriasConvalidar.fautapo"/> >

<table class="tabla">
<tr>
  <th class=colh>RU </th>
  <th class=colh>NOMBRES</th>
  <th class=colh>PLAN</th>
  <th class=colh>PROGRAMA</th>
  <th class=colh>GESTION</th>
  <th class=colh>PERIODO</th>
  <th class=colh>TIPO CONVALIDACION</th>
</tr>  
<tr>
  <td class=colb><c:out value="${datosEstudiante.id_estudiante}"/></td>
  <td class=colb><c:out value="${datosPersona.paterno}"/> &nbsp; <c:out value="${datosPersona.materno}"/> &nbsp; <c:out value="${datosPersona.nombres}"/></td>
  <td class=colb><c:out value="${datosEstudiante.id_plan}"/></td>
  <td class=colb><c:out value="${datosPrograma.programa}"/></td>
  <td class=colb><c:out value="${gestion}"/></td>
  <td class=colb><c:out value="${periodo}"/></td>
  <td class=colb>
   <select name="id_tipo_convalidacion">
              <option value="">--Seleccione--</option>
                <c:forEach var="lista" items="${lTiposConvalidaciones}" >
                  <option value='<c:out value="${lista.id_tipo_convalidacion}"/>' <c:if test="${lista.id_tipo_convalidacion ==  id_tipo_convalidacion}">selected</c:if> >
                  <c:out value="${lista.tipo_convalidacion}"/> 
                </option>
              </c:forEach>
            </select>
  </td>
<tr>  
</table>
<br>
<h3>Materias del Plan a Convalidar </h3>
<table class="tabla" width="100%">
    <tr>
          <th>NIVEL</th>
          <th>SIGLA</th>
          <th>NOMBRE DE LA ASIGNATURA</th>
          <th>HORAS</th>
          <th>PRE-REQUISITO</th>
    </tr>
        <c:set var="id_mencion_ant" value="0"/>
        <c:set var="id_nivel_ant" value="0"/>
        <c:forEach var="lista" items="${lPlanDeEstudios}" varStatus="contador">
          <c:if test="${(id_mencion_ant != lista.id_mencion) && (lista.id_mencion != 0)}">
	    <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
              <th colspan="5">MENCION :: <c:out value="${lista.mencion}"/></th>
            </tr>
          </c:if>
          <c:if test="${id_nivel_ant != lista.nivel_academico}">
            <tr>
              <td>NIVEL :: <c:out value="${lista.nivel_academico}"/></td>
              <td colspan="4"></td>
            </tr>
          </c:if>
          <td></td>
          <td valign="top"><input type="checkbox" name="id_materia_conv"  id="id_materia_con<c:out value='${lista.id_materia}'/>"  value="<c:out value='${lista.id_materia}'/>"  > <c:out value="${lista.sigla}"/></td>
          <td valign="top"><c:out value="${lista.materia}"/></td>
          <td valign="top"><c:out value="${lista.creditos}"/></td>
          <td valign="top"><c:out value="${lista.materias_anteriores}" escapeXml="false"/></td>
        </tr>
        <c:set var="id_mencion_ant" value="${lista.id_mencion}"/>
        <c:set var="id_nivel_ant" value="${lista.nivel_academico}"/>
      </c:forEach>
      <tr>
        <td align="center" colspan="5">
	  <input type='submit' value='Siguiente' class="siguiente" >
	  <input type='hidden' name="id_estudiante"  value="<c:out value="${datosEstudiante.id_estudiante}"/>" >
	  <input type='hidden' name="gestion"  value="<c:out value="${gestion}"/>" >
	  <input type='hidden' name="periodo"  value="<c:out value="${periodo}"/>" >
	</td>
    </tr>
</table>
</form>

<%@ include file="../../Inferior.jsp" %>