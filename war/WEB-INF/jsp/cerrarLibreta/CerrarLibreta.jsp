<%@ include file="../Superior.jsp" %>

<script language='JavaScript' SRC="./validar.js">  </script>
<div class="titulo">Cerrar libreta</div>

<br>
<tabla>
  <form name=formavolver<c:out value="${contador.count}"/> method=post action='<c:url value="/cerrarLibreta/entrada.fautapo"/>'>
    <td>
      <div><a class="volver" href="javascript:document.formavolver<c:out value="${contador.count}"/>.submit();"> volver</a></div>
      <input type="hidden" name="gestion"     value="<c:out value='${gestion}'/>">
      <input type="hidden" name="periodo"     value="<c:out value='${periodo}'/>">	  
      <input type="hidden" name="bandera"     value='1'>
    </td>
  </form>
</table>

<form name = forma method="post" action='<c:url value="/cerrarLibreta/cerrarLibreta.fautapo"/>'>
  <table class="tabla" border=0> 
    <tr>
      <th align=center>NRO</th>
      <th align=center>MATERIA</th>
      <th align=center>MATERIA/AHORRO</th>      
      <th align=center>DOCENTE</th>
      <th align=center>ESTADO</th>
    </tr>

    <c:forEach var="cerrar" items="${lMateriasCerrarLibreta.pageList}" varStatus="contador">
      <!-- ********** Esto es para el efecto ************ -->
        <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
      <!-- ********** Fin  efecto ************ --> 
    
      <td align=center><c:out value="${contador.count}"/></td>
      <td><c:out value="${cerrar.materia}"/></td>
      <td><c:out value="${cerrar.modelo_ahorro}"/></td>
      <td><c:out value="${cerrar.nombres}"/></td> 
      <c:if test="${cerrar.id_fase == 100}">
       <td class=colb>Habilitado</td>
      </c:if> 
      <c:if test="${cerrar.id_fase != 100}">
       <td align=center>No Habilitado</td>
      </c:if> 
    </tr>
    </c:forEach>
    <br>    
    <tr align=center>
     <th colspan=5><input type=submit name='boton' value='Cerrar'></th>
      <input type="hidden" name="gestion"     value="<c:out value='${gestion}'/>">
      <input type="hidden" name="periodo"     value="<c:out value='${periodo}'/>">	  
     <input type="hidden" name="bandera"     value='1'>
    </table>
</form>  
<%@ include file="../Inferior.jsp" %>
