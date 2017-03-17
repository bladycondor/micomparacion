<%@ include file="../../Superior.jsp" %>
<div class="titulo">Admisi&oacute;n Estudiante Auxiliar</div>
<div><a class="volver" href="javascript:history.back();">Volver</a></div>
<br>

<table class="tabla">
  <tr>
    <th>Nro.</th>
    <th>RU</th>
    <th>DIP</th>
    <th>NOMBRES</th>
    <th>PROGRAMA</th> 
    <th>PLAN</th>
    <th>TIPO ESTUDIANTE</th>
    <th>ACCION</th>
  </tr>    
  <c:forEach var="datos" items="${lEstudiantes}" varStatus="contador">
  <form name=forma<c:out value="${contador.count}"/> method="POST" action='<c:url value="/registrarAdmisionEstudianteAuxiliar.fautapo"/>'>
     <input type="hidden" value="${datos.id_estudiante}" name="id_estudiante">
     <input type="hidden" value="${datos.adjunto}" name="accion">
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ --> 
      <td><c:out value="${contador.count}"/></td>
      <td><c:out value="${datos.id_estudiante}"/></td>
      <td><c:out value="${datos.dip}"/></td>        
      <td><c:out value="${datos.nombre_completo}"/></td>
      <td><c:out value="${datos.programa}"/></td>
      <td><c:out value="${datos.id_plan}"/></td>
      <td><c:out value="${datos.tipo_estudiante}"/></td>
      <td>
        <input type="submit" value='<c:out value="${datos.adjunto}"/>' name="accion">
      </td>
    </tr>
  </form>        
  </c:forEach>
  </tr>    
</table>  

<%@ include file="../../Inferior.jsp" %>