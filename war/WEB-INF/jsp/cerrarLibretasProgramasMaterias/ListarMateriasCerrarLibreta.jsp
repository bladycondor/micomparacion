<%@ include file="../Superior.jsp" %>

<script language='JavaScript' SRC="./validar.js">  </script>
<div class="titulo">Cerrar libreta Por Materia</div>

<br>
<table class="tabla" border=0> 
  <tr>
    <th align=center>GESTION</th>
    <th align=center>PERIODO</th>
  </tr>
  <tr>
    <td><c:out value="${gestion}"/></td>
    <td><c:out value="${periodo}"/></td>
  </tr>
</table>

<br>
<tabla>
  <form name=formavolver<c:out value="${contador.count}"/> method=post action='<c:url value="/cerrarLibretaMateria/entrada.fautapo"/>'>
    <td>
      <div><a class="volver" href="javascript:document.formavolver<c:out value="${contador.count}"/>.submit();"> Volver</a></div>
      <input type="hidden" name="gestion"     value="<c:out value='${gestion}'/>">
      <input type="hidden" name="periodo"     value="<c:out value='${periodo}'/>">	  
      <input type="hidden" name="bandera"     value='1'>
    </td>
  </form>
</table>
<br>
<table class="tabla" border=0> 
  <tr>
    <th align=center>NRO</th>
    <th align=center>MATERIA</th>
    <th align=center>MATERIA/AHORRO</th>      
    <th align=center>GRUPO</th>      
    <th align=center>DOCENTE</th>
    <th align=center>ESTADO</th>
  </tr>

  <c:forEach var="cerrar" items="${lMateriasCerrarLibreta}" varStatus="contador">
  <!-- ********** Esto es para el efecto ************ -->
  <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  <!-- ********** Fin  efecto ************ --> 
    
    <td align=center><c:out value="${contador.count}"/></td>
    <td><c:out value="${cerrar.materia}"/></td>
    <td><c:if test="${empty cerrar.modelo_ahorro}">Ninguno</c:if>
      <c:if test="${!empty cerrar.modelo_ahorro}"><c:out value="${cerrar.modelo_ahorro}"/></c:if>
    </td>
    <td><c:out value="${cerrar.grupo}"/></td>
    <td><c:out value="${cerrar.nombres}"/></td> 
    <c:if test="${cerrar.id_fase == 1000}">
    <td>
      <form name='forma<c:out value="${contador.count}"/>' method="post" action='<c:url value="/cerrarLibretaMateria/registrarCerrarLibreta.fautapo"/>'>
         <a href="javascript:document.forma<c:out value='${contador.count}'/>.submit();" > Habilitado</a>
        <input type="hidden" name="gestion"              value="<c:out value='${gestion}'/>">
        <input type="hidden" name="periodo"              value="<c:out value='${periodo}'/>">	  
        <input type="hidden" name="id_materia"           value="<c:out value='${cerrar.id_materia}'/>">	  
	<input type="hidden" name="id_modelo_ahorro"     value="<c:out value='${cerrar.id_modelo_ahorro}'/>">	  
	<input type="hidden" name="id_grupo"             value="<c:out value='${cerrar.id_grupo}'/>">	  
	<input type="hidden" name="id_docente"           value="<c:out value='${cerrar.id_docente}'/>">	  
	<input type="hidden" name="id_fase"              value="<c:out value='${cerrar.id_fase}'/>">	  
	<input type="hidden" name="id_tipo_evaluacion"   value="<c:out value='${cerrar.id_tipo_evaluacion}'/>">	  
	<input type="hidden" name="bandera"     value='1'>
      </form>
    </td> 
    </c:if> 
    <c:if test="${cerrar.id_fase != 1000}">
      <td align=center>No Habilitado</td>
    </c:if> 
  </tr>
  </c:forEach>
</table>  
<%@ include file="../Inferior.jsp" %>
