<%@ include file="../Superior.jsp" %>

<c:if test="${!empty id_rol}">
  <div class=titulo>Administrar Libretas</div>
  <br>
  <tabla>
    <form name=formavolver<c:out value="${contador.count}"/> method=post action='<c:url value="/definirEvaluacion/entrada.fautapo"/>'>
      <td>
        <div><a class="volver" href="javascript:document.formavolver<c:out value="${contador.count}"/>.submit();"> Volver</a></div>
          <input type="hidden" name="nombres"            value="<c:out value='${nombres}'/>">
          <input type="hidden" name="gestion"            value="<c:out value='${gestion}'/>">
          <input type="hidden" name="periodo"            value="<c:out value='${periodo}'/>">	  
	  <input type="hidden" name="bandera"             value='1'>
      </td>
    </form>
  </table>
  
  <div class="H3"></div>
  <table class="tabla" border="0">
    <tr>
      <th>NOMBRES</th>
      <th>GESTI&Oacute;N</th>
      <th>PERIODO</th>
    </tr>
    <tr>
      <td class="etiqueta" align="center"><c:out value="${nombres}"/></td>
      <td class="etiqueta" align="center"><c:out value="${gestion}"/></td>
      <td class="etiqueta" align="center"><c:out value="${periodo}"/></td>
    </tr>
  </table>
  <br>
  <c:if test="${ empty datosAsignacion}">
    <center><div class="cuadroAviso" align="center">No existen materias asignadas para la gesti&oacute;n <c:out value="${gestion}"/>, periodo <c:out value="${periodo}"/></div></center>
  </c:if>
  <c:if test="${ !empty datosAsignacion}">
    <div="H3">Materias asignadas</div>
    <table class="tabla" border="0">
      <tr>
        <th align="center">FASE</th>
        <th align="center">EVALUACI&Oacute;N</th>
        <th align="center">CARRERA/PROGRAMA</th>
        <th align="center">GRUPO</th>
        <th align="center">SIGLA</th>
        <th align="center">MATERIA</th>
	<th align="center">MATERIA AHORRO</th>
        <th align="center" colspan="3">ADMINISTRACI&Oacute;N</th>
      </tr>
      <c:forEach var="asignacion" items="${datosAsignacion}" varStatus="contador">
        <!-- ********** Esto es para el efecto ************ -->
          <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
        <!-- ********** Fin  efecto ************ --> 
        <td>
          <c:out value="${asignacion.fase}"/>
        </td>
        <td>
          <c:out value="${asignacion.tipo_evaluacion}"/>
        </td>
        <td>
          <c:out value="${asignacion.programa}"/>
        </td>
        <td align="center">
          <c:out value="${asignacion.grupo}"/>
        </td>
        <td align="center">
          <c:out value="${asignacion.sigla}"/>
        </td>
	<td align="center">
          <c:out value="${asignacion.materia}"/>
        </td>
	<c:if test="${asignacion.id_modelo_ahorro < 0}">
	  <td align="center">
	    ---
	  </td>
	</c:if>    
        <c:if test="${asignacion.id_modelo_ahorro > 0}">
          <c:forEach var="asignacionahorro" items="${asignacion.materia_ahorro}">
	    <td>
              <c:out value="${asignacionahorro.modelo_ahorro}"/>
	    </td>
          </c:forEach>
        </c:if>
	<c:if test="${asignacion.id_fase !='7000' && asignacion.id_fase !='1000'}">
        <form name='formaEvaluaciones<c:out value="${contador.count}"/>' method='post' action="<c:url value='/definirEvaluacion/definirEvaluacion.fautapo'/>">
          <td>     
            <a class="agregar" href='javascript:document.formaEvaluaciones<c:out value="${contador.count}"/>.submit();'>Definir evaluaci&oacute;n</a>
	    <input type=hidden name="id_asignacion"      value="<c:out value="${asignacion.id_asignacion}"/>"> 
	    <input type=hidden name="id_materia"         value="<c:out value="${asignacion.id_materia}"/>"> 
            <input type=hidden name="materia"            value="<c:out value="${asignacion.materia}"/>">
            <input type=hidden name="id_grupo"           value="<c:out value="${asignacion.id_grupo}"/>">
            <input type=hidden name="grupo"              value="<c:out value="${asignacion.grupo}"/>">    
            <input type=hidden name="id_programa"        value="<c:out value="${asignacion.id_programa}"/>">
            <input type=hidden name="id_fase"            value="<c:out value="${asignacion.id_fase}"/>">
            <input type=hidden name="id_tipo_evaluacion" value="<c:out value="${asignacion.id_tipo_evaluacion}"/>">
            <input type=hidden name="tipo_evaluacion"    value="<c:out value="${asignacion.tipo_evaluacion}"/>">
            <input type=hidden name="gestion"            value="<c:out value="${gestion}"/>">
            <input type=hidden name="periodo"            value="<c:out value="${periodo}"/>">
            <input type=hidden name="id_docente"         value="<c:out value="${asignacion.id_docente}"/>">
            <input type=hidden name="id_modelo_ahorro"   value="<c:out value="${asignacion.id_modelo_ahorro}"/>">
	    <input type=hidden name="id_departamento"    value="<c:out value="${asignacion.id_departamento}"/>">
	    <c:forEach var="asignacionahorro" items="${asignacion.materia_ahorro}">
	      <input type=hidden name="modelo_ahorro"      value="<c:out value="${asignacionahorro.modelo_ahorro}"/>">
	    </c:forEach>  
	    <input type=hidden name="id_tipo_grado"      value="<c:out value="${asignacion.id_tipo_grado}"/>">
          </td>
        </form>
        
        <form name='formaLibretas<c:out value="${contador.count}"/>' method='post' action='<c:url value="/administrarLibretas/listarEstudiantesProgramados.fautapo"/>'>
          <td>     
            <a href='javascript:document.formaLibretas<c:out value="${contador.count}"/>.submit();'>Administrar libretas</a>
	    <input type=hidden name="id_asignacion"      value="<c:out value="${asignacion.id_asignacion}"/>"> 
	    <input type=hidden name="id_materia"         value="<c:out value="${asignacion.id_materia}"/>"> 
            <input type=hidden name="materia"            value="<c:out value="${asignacion.materia}"/>">
            <input type=hidden name="id_grupo"           value="<c:out value="${asignacion.id_grupo}"/>">
            <input type=hidden name="grupo"              value="<c:out value="${asignacion.grupo}"/>">    
            <input type=hidden name="id_programa"        value="<c:out value="${asignacion.id_programa}"/>">
            <input type=hidden name="id_fase"            value="<c:out value="${asignacion.id_fase}"/>">
            <input type=hidden name="id_tipo_evaluacion" value="<c:out value="${asignacion.id_tipo_evaluacion}"/>">
            <input type=hidden name="tipo_evaluacion"    value="<c:out value="${asignacion.tipo_evaluacion}"/>">
            <input type=hidden name="gestion"            value="<c:out value="${gestion}"/>">
            <input type=hidden name="periodo"            value="<c:out value="${periodo}"/>">
            <input type=hidden name="id_docente"         value="<c:out value="${asignacion.id_docente}"/>">
            <input type=hidden name="id_modelo_ahorro"   value="<c:out value="${asignacion.id_modelo_ahorro}"/>">
	    <input type=hidden name="id_departamento"    value="<c:out value="${asignacion.id_departamento}"/>">	    
	    <c:forEach var="asignacionahorro" items="${asignacion.materia_ahorro}">
	      <input type=hidden name="modelo_ahorro"      value="<c:out value="${asignacionahorro.modelo_ahorro}"/>">
	    </c:forEach>  
	    <input type=hidden name="id_tipo_grado"      value="<c:out value="${asignacion.id_tipo_grado}"/>">
	    <input type=hidden name="avanzado"            value="<c:out value="${avanzado}"/>">
          </td>
       </form>
  
        <form name='formaFases<c:out value="${contador.count}"/>' method=post action='<c:url value="/avanzarFase.fautapo"/>'>
         <td>     
           <a href='javascript:document.formaFases<c:out value="${contador.count}"/>.submit();'>Avanzar fase</a>
	    <input type=hidden name="id_asignacion"      value="<c:out value="${asignacion.id_asignacion}"/>"> 
	    <input type=hidden name="id_materia"         value="<c:out value="${asignacion.id_materia}"/>"> 
            <input type=hidden name="materia"            value="<c:out value="${asignacion.materia}"/>">
            <input type=hidden name="id_grupo"           value="<c:out value="${asignacion.id_grupo}"/>">
            <input type=hidden name="grupo"              value="<c:out value="${asignacion.grupo}"/>">    
            <input type=hidden name="id_programa"        value="<c:out value="${asignacion.id_programa}"/>">
            <input type=hidden name="id_fase"            value="<c:out value="${asignacion.id_fase}"/>">
            <input type=hidden name="id_tipo_evaluacion" value="<c:out value="${asignacion.id_tipo_evaluacion}"/>">
            <input type=hidden name="tipo_evaluacion"    value="<c:out value="${asignacion.tipo_evaluacion}"/>">
            <input type=hidden name="gestion"            value="<c:out value="${gestion}"/>">
            <input type=hidden name="periodo"            value="<c:out value="${periodo}"/>">
            <input type=hidden name="id_docente"         value="<c:out value="${asignacion.id_docente}"/>">
            <input type=hidden name="id_modelo_ahorro"   value="<c:out value="${asignacion.id_modelo_ahorro}"/>">
	    <input type=hidden name="id_departamento"    value="<c:out value="${asignacion.id_departamento}"/>">
	    <c:forEach var="asignacionahorro" items="${asignacion.materia_ahorro}">
	      <input type=hidden name="modelo_ahorro"      value="<c:out value="${asignacionahorro.modelo_ahorro}"/>">
	    </c:forEach>  
	    <input type=hidden name="id_tipo_grado"      value="<c:out value="${asignacion.id_tipo_grado}"/>">
          </td>
        </form>
	</c:if>
	<c:if test="${asignacion.id_fase =='7000'}">
	  <td colspan="3">
	  <font color="red">Libreta Cerrada </font>
	  </td>
	</c:if>
	<c:if test="${asignacion.id_fase =='1000'}">
	  <td colspan="3">
	  <font color="red">Libreta Pre-Cerrada </font>
	  </td>
	</c:if>
      </tr>
    </form>
  </c:forEach>
</table>
</c:if>
</c:if>

<%@ include file="../Inferior.jsp" %>