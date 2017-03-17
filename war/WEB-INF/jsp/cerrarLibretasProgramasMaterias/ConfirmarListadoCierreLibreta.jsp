<%@ include file="../Superior.jsp" %>
<script language = 'JavaScript' SRC="./validar.js">  </script>

<div class="titulo">Cerrar Libretas Por Programa Materia</div>

<tabla>
  <form name=formavolver<c:out value="${contador.count}"/> method=post action='<c:url value="/cerrarLibretasProgramasMaterias/verProgramaPlan.fautapo"/>'>
    <td>
      <div><a class="volver" href="javascript:document.formavolver<c:out value="${contador.count}"/>.submit();"> Volver</a></div>
      <input type="hidden" name="id_programa"   value="<c:out value='${datosPrograma.id_programa}'/>">
      <input type="hidden" name="id_prg_plan"   value="<c:out value='${datosPrgPlan.id_prg_plan}'/>">
      <input type="hidden" name="gestion"       value="<c:out value='${gestion}'/>">
      <input type="hidden" name="periodo"       value="<c:out value='${periodo}'/>">	  
      <input type="hidden" name="id_tipo_evaluacion"   value="<c:out value='${datosAsignacion.id_tipo_evaluacion}'/>">	  	  
    </td>
  </form>
</table>
<br>
<table class="tabla">
  <tr>
    <th>MATERIA</th>
    <th>DOCENTE</th>
    <th>GESTI&Oacute;N</th>
    <th>PERIODO</th>
    <th>TIPO EVALUACION</th>
  </tr>
  <tr>
    <td class="etiqueta"><c:out value="${datosAsignacion.materia}"/>
    <td class="etiqueta"><c:out value="${datosAsignacion.nombres}"/>
    <td class="etiqueta"><c:out value="${gestion}"/>
    <td class="etiqueta"><c:out value="${periodo}"/>
    <td class="etiqueta"><c:out value="${datosTipoEval.tipo_evaluacion}"/>
  </tr>
</table>

<h3> Lista de Estudiantes en Libretas para el Cierre  </h3>
<form name="forma" action="<c:url value='/cerrarLibretasProgramasMaterias/registrarCerrarLibreta.fautapo'/>" method="post">
<table class="tabla">
  <tr>
    <th>Nro</th>
    <th>R.U.</th>
    <th>Nombres</th>
    <th>Nro de Matricula</th>
  </tr>
  <c:if test="${empty lEstudiantesParaCierre}">
  <tr>
    <td colspan="4" align="center">
      No existen estudiantes para el cierre de libreta
    </td>
  </tr>
  </c:if>
  <c:if test="${!empty lEstudiantesParaCierre}">
  <c:forEach var="lista" items="${lEstudiantesParaCierre}" varStatus="contador">
   <!-- ********** Esto es para el efecto ************ -->
  <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
   <!-- ********** Fin  efecto ************ --> 
     <td><c:out value="${contador.count}"/></td>
     <td><c:out value="${lista.id_estudiante}"/></td>
     <td>
        <c:out value="${lista.nombres}"/>
        <c:if test="${lista.nombres == ''}">
         <font color="red"> No esta habilitado como estudiante </font>
        </c:if>
     </td>
     <td>
       <c:if test="${lista.id_matricula != 0}">
         <c:out value="${lista.id_matricula}"/>
       </c:if>
       <c:if test="${lista.id_matricula == 0}">
         <font color="red"> No Matriculado </font>
       </c:if>
     </td>
   </tr>
  </c:forEach>
  <tr>
    <td colspan="4" align="center">
      <input  class="siguiente" type="submit" value="Aceptar" >
    </td>
  </tr>
  </c:if>    
    <input type="hidden" name="id_asignacion"        value="<c:out value='${datosAsignacion.id_asignacion}'/>">
    <input type="hidden" name="gestion"              value="<c:out value='${gestion}'/>">
    <input type="hidden" name="periodo"              value="<c:out value='${periodo}'/>">
    <input type="hidden" name="id_materia"           value="<c:out value='${datosAsignacion.id_materia}'/>">	  
    <input type="hidden" name="id_modelo_ahorro"     value="<c:out value='${datosAsignacion.id_modelo_ahorro}'/>">	  
    <input type="hidden" name="id_grupo"             value="<c:out value='${datosAsignacion.id_grupo}'/>">	  
    <input type="hidden" name="id_docente"           value="<c:out value='${datosAsignacion.id_docente}'/>">	  
    <input type="hidden" name="id_fase"              value="<c:out value='${datosAsignacion.id_fase}'/>">	  
    <input type="hidden" name="id_tipo_evaluacion"   value="<c:out value='${datosAsignacion.id_tipo_evaluacion}'/>">	  	  
    <input type="hidden" name="id_departamento"      value="<c:out value='${datosAsignacion.id_departamento}'/>">	  	  
  
</table>
</form>

<%@ include file="../Inferior.jsp" %>