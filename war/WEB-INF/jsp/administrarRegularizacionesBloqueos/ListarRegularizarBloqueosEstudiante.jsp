<%@ include file="../Superior.jsp" %>
<div class="titulo">Regularizar Bloqueos/Desbloqueos </div>
<br>
<form name="fvolver" action="comprobarEntrada.fautapo" method="post">
  <div> <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a> </div>
   <input type="hidden" name="id_estudiante" value="<c:out value="${datosEstudiante.id_estudiante}"/>" >
</form>
<table class="formulario">
  <tr>
    <th colspan="2">Datos del Estudiante</td>
  </tr>
  <tr>
    <td class="etiqueta">R.U. ::</td>
    <td><c:out value="${datosEstudiante.id_estudiante}"/>
    
    </td>
  </tr>
  <tr>
    <td class="etiqueta">Estudiante ::</td>
    <td><c:out value="${datosEstudiante.nombres}"/>&nbsp;<c:out value="${datosEstudiante.paterno}"/>&nbsp;<c:out value="${datosEstudiante.materno}"/></td>
  </tr>
  <tr>
    <td class="etiqueta">Programa ::</td>
    <td><c:out value="${datosEstudiante.programa}"/></td>
  </tr>
  <tr>
    <td class="etiqueta">Estado del Estudiante::</td>
    <td><c:out value="${datosEstudiante.estado}"/>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <c:if test="${datosEstudiante.id_estado =='A'}">
      <form name="fBloquear" method="POST" action="bloquearEstudiante.fautapo" >
       <input class="bloquear" type="submit" value="Bloquear">
       <input type="hidden" name="id_estudiante" value="<c:out value="${datosEstudiante.id_estudiante}"/>" >
       <input type="hidden" name="accion" value="Bloquear">
     </c:if>
     </form>
    </td>
  </tr>  
</table>
<br>
<table class="tabla">
  <tr>
    <th>TIPO DE REGULARIZACION</th>
    <th>GESTION</th>
    <th>PERIODO</th>
    <th>OBSERVACIONES</th> 
    <th>REGULARIZADO?</th>
    <th>REGULARIZAR/DESBLOQUEAR</th>
  </tr>    
  <c:forEach var="datos" items="${lListarRegularizacionesEtudiante}" varStatus="contador">
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ -->
      <td><c:out value="${datos.tipo_regularizacion}"/></td>
      <td><c:out value="${datos.gestion}"/></td>
      <td><c:out value="${datos.periodo}"/></td>
      <td><c:out value="${datos.observacion}" escapeXml="false"/></td>
      <td>
        <c:if test="${datos.regularizado==true}">SI</c:if>
        <c:if test="${datos.regularizado==false}">NO</c:if>
      </td>
      <td>	
	<c:if test="${datos.regularizado==false}">
	 <form name="fDesbloquear<c:out value='${contador.count}'/>" method="POST" action="bloquearEstudiante.fautapo">
	  <a class="agregar" href="javascript:document.fDesbloquear<c:out value='${contador.count}'/>.submit();"> Regularizar y/o Desbloquear</a>
	  <input type="hidden" name="id_estudiante"      value="<c:out value="${datos.id_estudiante}"/>" >
	  <input type="hidden" name="id_regularizacion"  value="<c:out value="${datos.id_regularizacion}"/>" >
	  <input type="hidden" name="accion" value="Desbloquear">
	 </form> 
	</c:if>
      </td>
     </tr>
  </c:forEach>
  </tr>
</table>

<%@ include file="../Inferior.jsp" %>