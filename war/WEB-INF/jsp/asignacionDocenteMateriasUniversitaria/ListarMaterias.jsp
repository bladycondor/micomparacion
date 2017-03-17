<%@ include file="../Superior.jsp" %>
<script language = 'JavaScript' SRC="./validar.js">  </script>

<div class="titulo">Asignacion Docente Materias Universitaria</div>

<table>
  <form name=formavolver<c:out value="${contador.count}"/> method=post action='<c:url value="/docentesUniversitaria/verProgramaPlanAsignacion.fautapo"/>'>
    <td>
      <div><a class="volver" href="javascript:document.formavolver<c:out value="${contador.count}"/>.submit();"> Volver</a></div>
      <input type="hidden" name="id_programa" value="<c:out value='${id_programa}'/>">
      <input type="hidden" name="id_prg_plan"     value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">
      <input type="hidden" name="gestion"     value="<c:out value='${gestion}'/>">
      <input type="hidden" name="periodo"     value="<c:out value='${periodo}'/>">	  
      <input type="hidden" name="bandera"     value='1'>
    </td>
  </form>
</table>

<br>
<table class="tabla">
  <tr>
    <th>CARRERA</th>
    <th>PLAN</th>
    <th>GESTI&Oacute;N</th>
    <th>PERIODO</th>
    <th>TIPO EVALUACION</th>
	  <th>AVANZAR DESIGNACIÓN</th>
  </tr>
  <tr align="center">
    <td class="etiqueta"><c:out value="${programa.programa}"/>
    <td class="etiqueta"><c:out value="${datosPrgPlan.id_plan}"/>
    <td class="etiqueta"><c:out value="${gestion}"/>
    <td class="etiqueta"><c:out value="${periodo}"/>
    <td class="etiqueta"><c:out value="${datosTipoEval.tipo_evaluacion}"/>
	<!--<th><form name="favanzar<c:out value='${lista.id_dpto_grupo}'/>" action="<c:url value='/docentes/confirmarAccionAsignacionDocenteAvanzar.fautapo'/>" method="post">
	             <input type="hidden" name="id_dpto_grupo"  value="<c:out value='${lista.id_dpto_grupo}'/>">
		     <input type="hidden" name="id_asignacion"  value="<c:out value='${lista.id_asignacion}'/>">
		     <input type="hidden" name="id_prg_plan"    value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">
		     <input type="hidden" name="id_programa"    value='<c:out value="${programa.id_programa}"/>' >
		     <input type="hidden" name="gestion"        value="<c:out value='${gestion}'/>">
                     <input type="hidden" name="periodo"        value="<c:out value='${periodo}'/>">	  
		     <input type="hidden" name="accion"         value="Modificar">	  
                     <div> <a class="modificar" href="javascript:document.favanzar<c:out value='${lista.id_dpto_grupo}'/>.submit();"> Avanzar</a> </div>
                   </form>    </th> 
  
	<th><input type="button" value="Avanzar" onClick=window.location.href="/docentes/verProgramaPlanAsignacion.fautapo"> </th>-->
<c:if test="${iResultadoFaseResolucion == 2}" > 	
	<form name='formaFases<c:out value="${contador.count}"/>' method=post action='<c:url value="/avanzarFaseDesignacionuni.fautapo"/>'>
         <td> 
		 
           <a href='javascript:document.formaFases<c:out value='${lista.gestion}'/>.submit();'>Avanzar fase</a>
	    <input type="hidden" name="id_dpto_grupo"  value="<c:out value='${lista.id_dpto_grupo}'/>">
		<input type="hidden" name="id_asignacion"  value="<c:out value='${lista.id_asignacion}'/>">
		     <input type="hidden" name="gestion"            value="<c:out value="${gestion}"/>">
      <input type="hidden" name="periodo"            value="<c:out value="${periodo}"/>">
      <input type="hidden" name="id_programa"        value="<c:out value="${programa.id_programa}"/>">
     <!-- <input type="hidden" name="id_plan"        value="<c:out value="${datosPrgPlan.id_plan}"/>	-->  
           <input type="hidden" name="id_tipo_evaluacion" value='<c:out value="${datosTipoEval.id_tipo_evaluacion}"/>' >   
			<input type="hidden" name="iResultadoFaseResolucion" value='<c:out value="${iResultadoFaseResolucion}"/>' > 
                     	  
		          
    </form>
	</c:if>
	
	<c:if test="${iResultadoFaseResolucion == 3}" > 
	 <td class="etiqueta"><c:out value="Fase Cerrada"/>
</c:if>
  </tr>
</table>
<!-- ESTA MODIFICACION ERA PARA QUE APAREZCA EN LA DESIGNACION UNA OPCION QUE PERMITA SI ES QUE NO HUBIESEN AVANZADO FASE ELEGIR RECIBI INFORMACION Y PASARLO A FASE DE SECRETARIA GENERAL
<c:if test="${iResultadoFaseResolucion != 2}" > 
<br><br>-->
<!--<table class="tabla">
 <th><h3>EN CASO DE HABER RECIBIDO LA DOCUMENTACION FISICA, PERO NO HABER REALIZADO LAS CARRERAS EL REGISTRO EN EL SISTEMA</h3></th>
 	
	<form name='formaFases<c:out value="${contador.count}"/>' method=post action='<c:url value="/avanzarFaseDesignacionuni.fautapo"/>'>
         <td> 
		 
           <a href='javascript:document.formaFases<c:out value='${lista.gestion}'/>.submit();'><h3>RECIBI DOCUMENTACION</h3></a>
	    <input type="hidden" name="id_dpto_grupo"  value="<c:out value='${lista.id_dpto_grupo}'/>">
		<input type="hidden" name="id_asignacion"  value="<c:out value='${lista.id_asignacion}'/>">
		     <input type="hidden" name="gestion"            value="<c:out value="${gestion}"/>">
      <input type="hidden" name="periodo"            value="<c:out value="${periodo}"/>">
      <input type="hidden" name="id_programa"        value="<c:out value="${programa.id_programa}"/>">
     <!-- <input type="hidden" name="id_plan"        value="<c:out value="${datosPrgPlan.id_plan}"/> -->	
 <!--          <input type="hidden" name="id_tipo_evaluacion" value='<c:out value="${datosTipoEval.id_tipo_evaluacion}"/>' >   
			<input type="hidden" name="iResultadoFaseResolucion" value='<c:out value="${iResultadoFaseResolucion}"/>' > 
                     	  
		          
    </form>
	</c:if>
	</table> -->
<!--<script language="JavaScript">
  
  function fguardar()
  {
    if((document.forma.id_fase_resolucion.value=0))
    {
      document.forma.id_fase_resolucion=1;
    }
    else
    {
      alert("Los campos con (*), son obligatorios");
    }
  }
  iniciar();
</script> -->
<h3> Lista de Materias </h3>
<table class="tabla">
  <tr>
    <th>NIVEL ACAD&Eacute;MICO</th>
    <th>SIGLA</th>
    <th>MATERIA</th>
    <th>GRUPO</th>
    <th>DOCENTE</th>
    <th><ASIGNACI&Oacute;N </th>
	 
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
                   <form name="fnuevo<c:out value='${lista.id_dpto_grupo}'/>" action="<c:url value='/docentesUniversitaria/nuevoAsignacionDocente.fautapo'/>" method="post">
	              <input type="hidden" name="id_dpto_grupo" value="<c:out value='${lista.id_dpto_grupo}'/>">
		      <input type="hidden" name="id_asignacion" value="<c:out value='${lista.id_asignacion}'/>">
		      <input type="hidden" name="id_prg_plan"   value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">
		      <input type="hidden" name="id_programa"   value='<c:out value="${programa.id_programa}"/>' >
		      <input type="hidden" name="gestion"       value="<c:out value='${gestion}'/>">
                      <input type="hidden" name="periodo"       value="<c:out value='${periodo}'/>">	  
		      <input type="hidden" name="accion"         value="Nuevo">	  
                      <div> <a class="agregar" href="javascript:document.fnuevo<c:out value='${lista.id_dpto_grupo}'/>.submit();"> Nuevo</a> </div>
                   </form>       
	         </td>
	       </c:if>
	       <c:if test="${lista.id_asignacion != 0 && iResultadoFaseResolucion == 2}" >
	         <td>
                   <form name="fmodificar<c:out value='${lista.id_dpto_grupo}'/>" action="<c:url value='/docentesUniversitaria/nuevoAsignacionDocente.fautapo'/>" method="post">
	             <input type="hidden" name="id_dpto_grupo"  value="<c:out value='${lista.id_dpto_grupo}'/>">
		     <input type="hidden" name="id_asignacion"  value="<c:out value='${lista.id_asignacion}'/>">
		     <input type="hidden" name="id_prg_plan"    value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">
		     <input type="hidden" name="id_programa"    value='<c:out value="${programa.id_programa}"/>' >
		     <input type="hidden" name="gestion"        value="<c:out value='${gestion}'/>">
                     <input type="hidden" name="periodo"        value="<c:out value='${periodo}'/>">	  
		     <input type="hidden" name="accion"         value="Modificar">	  
                     <div> <a class="modificar" href="javascript:document.fmodificar<c:out value='${lista.id_dpto_grupo}'/>.submit();"> Modificar</a> </div>
                   </form>       
	         </td>
		 <td>
                   <form name="feliminar<c:out value='${lista.id_dpto_grupo}'/>" action="<c:url value='/docentesUniversitaria/nuevoAsignacionDocente.fautapo'/>" method="post">
	             <input type="hidden" name="id_dpto_grupo"  value="<c:out value='${lista.id_dpto_grupo}'/>">
		     <input type="hidden" name="id_asignacion"  value="<c:out value='${lista.id_asignacion}'/>">
		     <input type="hidden" name="id_prg_plan"    value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">
		     <input type="hidden" name="id_programa"    value='<c:out value="${programa.id_programa}"/>' >
		     <input type="hidden" name="gestion"        value="<c:out value='${gestion}'/>">
                     <input type="hidden" name="periodo"        value="<c:out value='${periodo}'/>">	  
		     <input type="hidden" name="accion"         value="Eliminar">	  
                     <div> <a class="eliminar" href="javascript:document.feliminar<c:out value='${lista.id_dpto_grupo}'/>.submit();"> Eliminar</a> </div>
                   </form>       
	         </td>
	       </c:if>
		   <c:if test="${lista.id_asignacion != 0 && iResultadoFaseResolucion != 2 && iResultadoFaseResolucion !=3}" >
 <td>
                   <form name="fmodificar<c:out value='${lista.id_dpto_grupo}'/>" action="<c:url value='/docentesUniversitaria/nuevoAsignacionDocente.fautapo'/>" method="post">
	             <input type="hidden" name="id_dpto_grupo"  value="<c:out value='${lista.id_dpto_grupo}'/>">
		     <input type="hidden" name="id_asignacion"  value="<c:out value='${lista.id_asignacion}'/>">
		     <input type="hidden" name="id_prg_plan"    value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">
		     <input type="hidden" name="id_programa"    value='<c:out value="${programa.id_programa}"/>' >
		     <input type="hidden" name="gestion"        value="<c:out value='${gestion}'/>">
                     <input type="hidden" name="periodo"        value="<c:out value='${periodo}'/>">	  
		     <input type="hidden" name="accion"         value="Modificar">	  
                     <div> <a class="modificar" href="javascript:document.fmodificar<c:out value='${lista.id_dpto_grupo}'/>.submit();"> Ver Datos</a> </div>
                   </form>       
	         </td>	
	 
</c:if>			

 <c:if test="${ iResultadoFaseResolucion == 3}" >
 <td>
                   <form name="fmodificar<c:out value='${lista.id_dpto_grupo}'/>" action="<c:url value='/docentesUniversitaria/imprimirMemoDocente.fautapo'/>" method="post">
	             <input type="hidden" name="id_dpto_grupo"  value="<c:out value='${lista.id_dpto_grupo}'/>">
		     <input type="hidden" name="id_asignacion"  value="<c:out value='${lista.id_asignacion}'/>">
		     <input type="hidden" name="id_prg_plan"    value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">
		     <input type="hidden" name="id_programa"    value='<c:out value="${programa.id_programa}"/>' >
		     <input type="hidden" name="gestion"        value="<c:out value='${gestion}'/>">
                     <input type="hidden" name="periodo"        value="<c:out value='${periodo}'/>">	  
		     <input type="hidden" name="accion"         value="Modificar">	  
                     <div> <a class="modificar" href="javascript:document.fmodificar<c:out value='${lista.id_dpto_grupo}'/>.submit();"> Imprimir</a> </div>
                   </form>       
	         </td>	
	 
</c:if>			  
 
  <!--<c:if test="${lista.id_fase_resolucion == 3}" >
 <td>
                   <form name="fmodificar<c:out value='${lista.id_dpto_grupo}'/>" action="<c:url value='/docentesUniversitaria/nuevoAsignacionDocente.fautapo'/>" method="post">
	             <input type="hidden" name="id_dpto_grupo"  value="<c:out value='${lista.id_dpto_grupo}'/>">
		     <input type="hidden" name="id_asignacion"  value="<c:out value='${lista.id_asignacion}'/>">
		     <input type="hidden" name="id_prg_plan"    value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">
		     <input type="hidden" name="id_programa"    value='<c:out value="${programa.id_programa}"/>' >
		     <input type="hidden" name="gestion"        value="<c:out value='${gestion}'/>">
                     <input type="hidden" name="periodo"        value="<c:out value='${periodo}'/>">	  
		     <input type="hidden" name="accion"         value="Modificar">	  
                     <div> <a class="modificar" href="javascript:document.fmodificar<c:out value='${lista.id_dpto_grupo}'/>.submit();"> Imprimir Memo</a> </div>
                   </form>       
	         </td>	
	 
</c:if>		!-->	 
	 			 
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