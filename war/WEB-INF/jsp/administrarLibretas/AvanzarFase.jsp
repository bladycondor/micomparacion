<%@ include file="../Superior.jsp" %>

  <div class=titulo> Administrar Libretas</div>
  <br>
<form name=formavolver method="post" action='<c:url value="/definirEvaluacion/listarAsignaciones.fautapo"/>'>
        <div><a class="volver" href="javascript:document.formavolver.submit();"> Volver</a></div>
          <input type="hidden" name="id_docente" value="<c:out value='${datosAsignacion.id_docente}'/>">
          <input type="hidden" name="gestion"    value="<c:out value='${datosAsignacion.gestion}'/>">
          <input type="hidden" name="periodo"    value="<c:out value='${datosAsignacion.periodo}'/>">	  
	  <input type="hidden" name="bandera"    value='1'>
</form>  

  <c:if test="${mensaje != null}">
    <center><div class="cuadroAviso"><c:out value="${mensaje}"/></div> </center>
  </c:if>
  <c:if test="${datosAsignacion.id_fase >= 1000}">
    <center><div class="cuadroAviso">Usted ya realiz&oacute; el Pre-Cierre de Libreta</div> </center>
  </c:if>
  <c:if test="${datosAsignacion.id_fase != 1000}">
    <form method="post" action="<c:url value="/registrarAvanzarFase.fautapo"/>">
      <table class="tabla" border="0">
        <tr>
          <th>CARRERA/PROGRAMA</th>
          <th>SIGLA - MATERIA</th>
          <th>GRUPO</th>
          <th>GESTI&Oacute;N</th>
          <th>PERIODO</th>
	<tr>    
          <td><c:out value="${buscarPrograma.programa}"/></td>
    	  <td><c:out value="${datosAsignacion.sigla}"/>&nbsp;<b>-</b>&nbsp;<c:out value="${datosAsignacion.materia}"/></td>    
          <td align="center"><c:out value="${datosAsignacion.grupo}"/></td>
          <td align="center"><c:out value="${datosAsignacion.gestion}"/></td>
          <td align="center"><c:out value="${datosAsignacion.periodo}"/></td>
        </tr>
      </table>
      <br>
      <table class="formulario">
        <tr>
          <th>Atenci&oacute;n!</th>
        </tr>
        <tr>
          <td align="center">&iquest;Esta seguro que desea avanzar de :&nbsp;<b><c:out value="${fase_actual}"/></b><br> a la fase de :&nbsp;<b><c:out value="${fase_siguiente}"/></b>?</td>
        </tr>
        <tr>
          <td align="center"><input type="submit" name="accion" value="Avanzar >>"></td>
        </tr>    
      </table>
      <input type="hidden" name="id_asignacion" value="<c:out value="${datosAsignacion.id_asignacion}"/>">
      <input type="hidden" name="id_programa"   value="<c:out value="${id_programa}"/>">
      <input type="hidden" name="gestion"       value="<c:out value="${datosAsignacion.gestion}"/>">
      <input type="hidden" name="periodo"       value="<c:out value="${datosAsignacion.periodo}"/>">
      <input type="hidden" name="id_tipo_grado" value="<c:out value="${id_tipo_grado}"/>">      
    </form>
  </c:if>

<%@ include file="../Inferior.jsp" %>