<%@ include file="../Superior.jsp" %>

<c:if test="${!empty id_rol}">
<div class=titulo>Admin. Retroceder Fase Por Programa</div>
<br>
<tabla>
  <form name="formavolver" method=post action='<c:url value="/retrocederFase/listarProgramasFacultad.fautapo"/>'>
    <td>
      <div><a class="volver" href="javascript:document.formavolver.submit();"> Volver</a></div>
      <input type="hidden" name="id_programa"   value="<c:out value='${id_programa}'/>">
      <input type="hidden" name="gestion"       value="<c:out value='${gestion}'/>">
      <input type="hidden" name="periodo"       value="<c:out value='${periodo}'/>">	  
    </td>
  </form>
</table>
<table class="tabla">
  <tr>
    <th>FACULTAD</th>
    <th>PROGRAMA</th>
    <th>PLAN</th>
    <th>GESTI&Oacute;N</th>
    <th>PERIODO</th>
    <th>TIPO EVALUACION</th>
  </tr>
  <tr align="center">
    <td class="etiqueta"><c:out value="${datosFacultad.facultad}"/>
    <td class="etiqueta"><c:out value="${datosPrograma.programa}"/>
    <td class="etiqueta"><c:out value="${datosPrgPlan.id_plan}"/>
    <td class="etiqueta"><c:out value="${gestion}"/>
    <td class="etiqueta"><c:out value="${periodo}"/>
    <td class="etiqueta"><c:out value="${datosTipoEval.tipo_evaluacion}"/>
  </tr>
</table>  
<br>
<h3> Lista de Materias </h3>
<table class="tabla">
  <tr>
    <th><div class="clave">NIVEL ACAD&Eacute;MICO</div></th>
    <th><div class="clave">SIGLA</div></th>
    <th><div class="clave">MATERIA</div></th>
    <th><div class="clave">GRUPO</div></th>
    <th><div class="clave">DOCENTE</div></th>
    <th><div class="clave">FASE</div></th>
    <th><div class="clave">CERRAR LIBRETA</div></th>
  </tr>
  <c:forEach var="listado1" items="${listarplanestudios.pageList}" varStatus="contador">
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ --> 

    <td valign="top" align="center"><c:out value="${listado1.nivel_academico}"/>
    <td valign="top">
      <c:out value="${listado1.sigla}"/>
    </td>
    <td valign="top"><c:out value="${listado1.materia}"/></td>
    <td valign="top">
      <c:if test="${listado1.nro_asignaciones > 0}">
         <table>
           <c:forEach var="lista" items="${listado1.materias}" >
	     <tr>
	       <td>
                <c:out value="${lista.grupo}"/>
	       </td>
	     </tr>
           </c:forEach>
	 </table> 
        </form>
      </c:if>
      <c:if test="${listado1.nro_asignaciones == 0 }">
        <center><font color="red">--</font></center>
      </c:if>
    </td>
    <td valign="top">
      <c:if test="${listado1.nro_asignaciones > 0}">
         <table>
           <c:forEach var="lista" items="${listado1.materias}" >
	     <tr>
	       <td>
                <c:out value="${lista.nombres}"/>
	       </td>
	     </tr>
           </c:forEach>
	 </table> 
        </form>
      </c:if>
      <c:if test="${listado1.nro_asignaciones == 0}">
        <center><font color="red">---</font></center>
      </c:if>
    </td>
    <td valign="top">
      <c:if test="${listado1.nro_asignaciones > 0}">
         <table>
           <c:forEach var="lista" items="${listado1.materias}" >
	     <tr>
	       <td>
                <c:out value="${lista.fase}"/> - <c:out value="${lista.id_fase}"/>
	       </td>
	     </tr>
           </c:forEach>
	 </table> 
        </form>
      </c:if>
      <c:if test="${listado1.nro_asignaciones == 0}">
        <center><font color="red">----</font></center>
      </c:if>
    </td>
    
    <td valign="top">
      <c:if test="${listado1.nro_asignaciones > 0}">
         <table>
           <c:forEach var="lista" items="${listado1.materias}" >
	     <tr>
	        <c:if test="${(lista.id_asignacion != 0) && (lista.id_fase == 0)}">
	         <td>
		   <font color="red">---</font>
	         </td>
	       </c:if>
	      <c:if test="${(lista.id_asignacion != 0) && (lista.id_fase >= 7000)}">
	         <td>
		   <font color="red">LIBRETA CERRADA</font>
	         </td>
	       </c:if> 
	       <c:if test="${(lista.id_asignacion != 0) && (lista.id_fase == 1000)}">
	         <td>
		   <font color="red">---</font>
	         </td>
	       </c:if> 
	       <c:if test="${(lista.id_asignacion != 0) && (lista.id_fase == 7000)}">
	         <td>
		 
                   <form name="fretroceder<c:out value='${lista.id_asignacion}'/>" action="<c:url value='/retrocederFase/confirmarModificarFaseDocente.fautapo'/>" method="post">
		     <input type="hidden" name="id_asignacion"        value="<c:out value='${lista.id_asignacion}'/>">
		     <input type="hidden" name="gestion"              value="<c:out value='${gestion}'/>">
                     <input type="hidden" name="periodo"              value="<c:out value='${periodo}'/>">
                     <input type="hidden" name="id_materia"           value="<c:out value='${lista.id_materia}'/>">	  
	             <input type="hidden" name="id_modelo_ahorro"     value="<c:out value='${lista.id_modelo_ahorro}'/>">	  
	             <input type="hidden" name="id_grupo"             value="<c:out value='${lista.id_grupo}'/>">	  
	             <input type="hidden" name="id_docente"           value="<c:out value='${lista.id_docente}'/>">	  
	             <input type="hidden" name="id_fase"              value="<c:out value='${lista.id_fase}'/>">	  
	             <input type="hidden" name="id_tipo_evaluacion"   value="<c:out value='${lista.id_tipo_evaluacion}'/>">	  	  
		     <input type="hidden" name="id_programa"          value="<c:out value='${datosPrograma.id_programa}'/>">	  	  
		     <input type="hidden" name="id_facultad"          value="<c:out value='${datosFacultad.id_facultad}'/>">	  	  
                     <div> <a class="modificar" href="javascript:document.fretroceder<c:out value='${lista.id_asignacion}'/>.submit();"> Retroceder Fase</a> </div>
                   </form>       
	         </td>
	       </c:if> 
	     </tr>
           </c:forEach>
	 </table> 
        </form>
      </c:if>
      <c:if test="${listado1.nro_asignaciones == 0}">
        <font color="red">NO HABILITADO</font>
      </c:if>
    </td>
  </tr>
  </c:forEach>
</table>
  
</c:if>

<%@ include file="../Inferior.jsp" %>