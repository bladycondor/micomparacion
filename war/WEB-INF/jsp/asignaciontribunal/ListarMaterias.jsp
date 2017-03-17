<%@ include file="../Superior.jsp" %>
<script language = 'JavaScript' SRC="./validar.js">  </script>

<div class="titulo">Asignacion de Tribunales</div>

<tabla>
  <form name=formavolver<c:out value="${contador.count}"/> method=post action='<c:url value="/asignaciontribunal/verProgramaPlanAsignacion.fautapo"/>'>
    <td>
      <div><a class="volver" href="javascript:document.formavolver<c:out value="${contador.count}"/>.submit();"> Volver</a></div>
      <input type="hidden" name="id_programa" value="<c:out value='${id_programa}'/>">
      <!--<input type="hidden" name="id_prg_plan"     value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">-->
      <input type="hidden" name="gestion"     value="<c:out value='${gestion}'/>">
      <input type="hidden" name="periodo"     value="<c:out value='${periodo}'/>">	  
      <input type="hidden" name="bandera"     value='1'>
    </td>
  </form>
</table>

<br>

<table class="tabla">
  <tr>
    <th>RU</th>
    <td class="colb"><c:out value="${datosEstudiante.id_estudiante}"/></td>
    <th>Estudiante</th>
    <td class="colb">
      <c:out value="${datosEstudiante2.nombres}"/> &nbsp; 
      <c:out value="${datosEstudiante2.paterno}"/> &nbsp;
      <c:out value="${datosEstudiante2.materno}"/>
    </td>
  </tr>
  <tr>
    <th>Programa</th>
    <td class="colb"><c:out value="${datosEstudiante2.programa}"/>
    <th>Plan</th>
    <td class="colb"><c:out value="${datosEstudiante.id_plan}"/>
  </tr>
  <tr>
    <th>Gestión</th>
    <td class="colb"><c:out value="${gestion}"/>
    <th>Periodo</th>
    <td class="colb"><c:out value="${periodo}"/>
  </tr>
  <tr>
    <th>Tipo Evaluacion</th>
    <td class="colb" colspan="3"><c:out value="${datosTipoEval.tipo_evaluacion}"/>
    
  </tr>

</table>

<h3> Lista de Miembros del Tribunal </h3>
<table class="tabla">
  <tr>
    <th>TIPO MIEMBRO</th>
    <th>SIGLA</th>
    <th>MATERIA</th>
    <th>GRUPO</th>
    <th>DOCENTE</th>
    <th>ASIGNACI&Oacute;N </th>
  </tr>
  <c:forEach var="listado1" items="${listarplanestudios.pageList}" varStatus="contador">
  <!-- ********** Esto es para el efecto ************ -->
  <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  <!-- ********** Fin  efecto ************ --> 
    <td valign="top" align="center"><c:out value="${listado1.nivel_academico}"/>
    <td valign="top"> <c:out value="${listado1.sigla}"/>  </td>
    <td valign="top"><c:out value="${listado1.materia}"/></td>
    <td valign="top">
      <c:if test="${listado1.nro_grupos > 0}">
         <table>
           <c:forEach var="lista" items="${listado1.grupos}" >
	     <tr>
	       <td>
                <c:out value="${lista.grupo}"/>
	       </td>
	     </tr>
           </c:forEach>
	 </table> 
      </c:if>
      <c:if test="${listado1.nro_grupos == 0 && listado1.id_modelo_ahorro != -1}">
        <font color="red">Sin grupos</font>
      </c:if>
      <c:if test="${listado1.nro_grupos == 0 && listado1.id_modelo_ahorro == -1}">
        <center> -- </center>
      </c:if>      
    </td>
    <td valign="top">
      <c:if test="${listado1.nro_grupos > 0}">
         <table>
           <c:forEach var="lista" items="${listado1.grupos}" >
	     <tr>
	       <td>
                <c:out value="${lista.nombres}"/>
	       </td>
	     </tr>
           </c:forEach>
	 </table> 
        </form>
      </c:if>
      <c:if test="${listado1.nro_grupos == 0 && listado1.id_modelo_ahorro != -1}">
        <font color="red">Sin grupos</font>
      </c:if>
      <c:if test="${listado1.nro_grupos == 0 && listado1.id_modelo_ahorro == -1}">
        <center> -- </center>
      </c:if>      
    </td>
    <td valign="top">
      <c:if test="${listado1.nro_grupos > 0}">
         <table>
           <c:forEach var="lista" items="${listado1.grupos}" >
	     <tr>
	       <c:if test="${lista.id_asignacion == 0}">
	         <td colspan="2">
                   <form name="fnuevo<c:out value='${lista.id_dpto_grupo}'/>" action="<c:url value='/asignaciontribunal/nuevoAsignacionDocente.fautapo'/>" method="post">
	              <input type="hidden" name="id_dpto_grupo" value="<c:out value='${lista.id_dpto_grupo}'/>">
		      <input type="hidden" name="id_asignacion" value="<c:out value='${lista.id_asignacion}'/>">
		      <!--<input type="hidden" name="id_prg_plan"   value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">-->
		      <input type="hidden" name="id_programa"   value='<c:out value="${programa.id_programa}"/>' >
		      <input type="hidden" name="gestion"       value="<c:out value='${gestion}'/>">
                      <input type="hidden" name="periodo"       value="<c:out value='${periodo}'/>">	  
		      <input type="hidden" name="accion"         value="Nuevo">	  
                      <div> <a class="agregar" href="javascript:document.fnuevo<c:out value='${lista.id_dpto_grupo}'/>.submit();"> Nuevo</a> </div>
                   </form>       
	         </td>
	       </c:if>
	       <c:if test="${lista.id_asignacion != 0}">
	         <td>
                   <form name="fmodificar<c:out value='${lista.id_dpto_grupo}'/>" action="<c:url value='/asignaciontribunal/nuevoAsignacionDocente.fautapo'/>" method="post">
	             <input type="hidden" name="id_dpto_grupo"  value="<c:out value='${lista.id_dpto_grupo}'/>">
		     <input type="hidden" name="id_asignacion"  value="<c:out value='${lista.id_asignacion}'/>">
		     <!--<input type="hidden" name="id_prg_plan"    value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">-->
		     <input type="hidden" name="id_programa"    value='<c:out value="${programa.id_programa}"/>' >
		     <input type="hidden" name="gestion"        value="<c:out value='${gestion}'/>">
                     <input type="hidden" name="periodo"        value="<c:out value='${periodo}'/>">	  
		     <input type="hidden" name="accion"         value="Modificar">	  
                     <div> <a class="modificar" href="javascript:document.fmodificar<c:out value='${lista.id_dpto_grupo}'/>.submit();"> Modificar</a> </div>
                   </form>       
	         </td>
		 <td>
                   <form name="feliminar<c:out value='${lista.id_dpto_grupo}'/>" action="<c:url value='/asignaciontribunal/nuevoAsignacionDocente.fautapo'/>" method="post">
	             <input type="hidden" name="id_dpto_grupo"  value="<c:out value='${lista.id_dpto_grupo}'/>">
		     <input type="hidden" name="id_asignacion"  value="<c:out value='${lista.id_asignacion}'/>">
		     <!--<input type="hidden" name="id_prg_plan"    value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">-->
		     <input type="hidden" name="id_programa"    value='<c:out value="${programa.id_programa}"/>' >
		     <input type="hidden" name="gestion"        value="<c:out value='${gestion}'/>">
                     <input type="hidden" name="periodo"        value="<c:out value='${periodo}'/>">	  
		     <input type="hidden" name="accion"         value="Eliminar">	  
                     <div> <a class="eliminar" href="javascript:document.feliminar<c:out value='${lista.id_dpto_grupo}'/>.submit();"> Eliminar</a> </div>
                   </form>       
	         </td>
	       </c:if> 
	     </tr>
           </c:forEach>
	 </table> 
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
</table>

<%@ include file="../Inferior.jsp" %>