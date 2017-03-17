<%@ include file="../Superior.jsp" %>
<script language = 'JavaScript' SRC="./validar.js">  </script>

<div class="titulo">Administraci&oacute;n de Horarios</div>

<tabla>
  <form name=formavolver<c:out value="${contador.count}"/> method=post action='<c:url value="/administrarHorarios/listarProgramasPlanes.fautapo"/>'>
    <td>
      <div><a class="volver" href="javascript:document.formavolver<c:out value="${contador.count}"/>.submit();"> Volver</a></div>
      <input type="hidden" name="id_programa"   value="<c:out value='${id_programa}'/>">
      <input type="hidden" name="id_plan"       value="<c:out value='${datosPrgPlan.id_plan}'/>">
      <input type="hidden" name="id_prg_plan"   value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">
      <input type="hidden" name="gestion"       value="<c:out value='${gestion}'/>">
      <input type="hidden" name="periodo"       value="<c:out value='${periodo}'/>">	  
      <input type="hidden" name="bandera"       value='1'>
    </td>
  </form>
</table>

<br>
<table class="tabla">
  <tr>
    <th>PROGRAMA</th>
    <th>PLAN</th>
    <th>TIPO GRADO</th>
    <th>TIPO EVALUACION</th>
    <th>GESTI&Oacute;N</th>
    <th>PERIODO</th>
  </tr>
  <tr align="center">
    <td><c:out value="${datosPrograma.programa}"/>
    <td><c:out value="${datosPrgPlan.id_plan}"/>
    <td><c:out value="${datosPrgPlan.tipo_grado}"/>
    <td><c:out value="${datosTipoEval.tipo_evaluacion}"/>
    <td><c:out value="${gestion}"/>
    <td><c:out value="${periodo}"/>
  </tr>
</table>

<h3> Lista de Materias </h3>
<table class="tabla">
  <tr>
    <th><div class="clave">NIVEL ACAD&Eacute;MICO</div></th>
    <th><div class="clave">SIGLA</div></th>
    <th><div class="clave">MATERIA</div></th>
    <th><div class="clave">GRUPOS</div></th>
    
  </tr>
  <c:forEach var="listado1" items="${lPlanEstudios}" varStatus="contador">
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ --> 

    <td align="center"><c:out value="${listado1.nivel_academico}"/>
    <td>
      <c:if test="${listado1.nro_grupos > 0}">
        <form name='forma<c:out value="${contador.index}"/>' method=post action='<c:url value="/administrarHorarios/listarHorarios.fautapo"/>' >
          <input type="hidden" name="id_programa"              value='<c:out value="${datosPrograma.id_programa}"/>' >
          <input type="hidden" name="id_plan"                  value='<c:out value="${datosPrgPlan.id_plan}"/>' >
	  <input type="hidden" name="id_prg_plan"              value='<c:out value="${datosPrgPlan.id_prg_plan}"/>' >
          <input type="hidden" name="gestion"                  value='<c:out value="${gestion}"/>' >
          <input type="hidden" name="periodo"                  value='<c:out value="${periodo}"/>' >
          <input type="hidden" name="id_materia"               value='<c:out value="${listado1.id_materia}"/>' >
          <input type="hidden" name="id_modelo_ahorro"         value='<c:out value="${listado1.id_modelo_ahorro}"/>' >
	  <input type="hidden" name="id_tipo_evaluacion"       value='<c:out value="${datosTipoEval.id_tipo_evaluacion}"/>' >
        <a href='javascript: document.forma<c:out value="${contador.index}"/>.submit()' >
      </c:if>
      <c:out value="${listado1.sigla}"/>
      </a>
    </td>
    <td><c:out value="${listado1.materia}"/></td>
    <td>
      <c:if test="${listado1.nro_grupos > 0}">
        <select name="id_dpto_grupo">
          <c:forEach var="lista" items="${listado1.grupos}" >
            <option value="<c:out value="${lista.id_dpto_grupo}"/>" >
              <c:out value="${lista.grupo}"/>
            </option>
          </c:forEach>
        </select>
        </form>
      </c:if>
      <c:if test="${listado1.nro_grupos == 0 && listado1.id_modelo_ahorro != -1}">
        <font color="red">Sin grupos</font>
      </c:if>
      <c:if test="${listado1.nro_grupos == 0 && listado1.id_modelo_ahorro == -1}">
        <center> -- </center>
      </c:if>      
    </td>
  </tr>
  </c:forEach>
  <tr>
    <th colspan=4><div class="clave">&nbsp;</th>
  </tr>
</table>

<%@ include file="../Inferior.jsp" %>