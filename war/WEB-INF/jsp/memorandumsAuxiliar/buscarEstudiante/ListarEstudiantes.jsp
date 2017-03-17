<%@ include file="../../Superior.jsp" %>
<div class="titulo">Memorandums Estudiante Auxiliar</div>
<div><a class="volver" href="javascript:history.back();">Volver</a></div>
<br>
<table class="tabla">
  <tr>
    <th>Nro.</th>
    <th>RU</th>
    <th>PATERNO</th>
    <th>MATERNO</th>
    <th>NOMBRES</th>
    <th>SIGLA</th>
    <th>MATERIA</th>
    <th>PROGRAMA</th> 
    <th>FACULTAD</th>
    <th>IMPRIMIR</th>
  </tr>    
  <c:forEach var="datos" items="${lEstudiantes}" varStatus="contador">
  <form name=forma<c:out value="${contador.count}"/> method="POST" action='<c:url value="/memo/impresionMemorandumEstudiante.fautapo"/>'>  
     <input type="hidden" value="${gestion}" name="gestion">     
     <input type="hidden" value="${datos.id_perfil}" name="id_perfil">     
     <input type="hidden" value="${datos.paterno} ${datos.materno} ${datos.nombres}" name="nombre_completo">     
     <input type="hidden" value="${datos.programa}" name="programa">
     <input type="hidden" value="${datos.facultad}" name="area">
     <input type="hidden" value="${datos.sigla}" name="sigla">
     <input type="hidden" value="${datos.materia}" name="materia">
     <input type="hidden" value="${datos.carga_horaria}" name="carga_horaria">
     
     <input type="hidden" value='<fmt:formatDate value="${datos.fecha_i}" pattern="yyyy-MM-dd"/>' name="fecha_i">
     <input type="hidden" value='<fmt:formatDate value="${datos.fecha_f}" pattern="yyyy-MM-dd"/>' name="fecha_f">

     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ --> 
      <td><c:out value="${contador.count}"/></td>
      <td><c:out value="${datos.id_estudiante}"/></td>
      <td><c:out value="${datos.paterno}"/></td>
      <td><c:out value="${datos.materno}"/></td>
      <td><c:out value="${datos.nombres}"/></td>
      <td><c:out value="${datos.sigla}"/></td>
      <td><c:out value="${datos.materia}"/></td>
      <td><c:out value="${datos.programa}"/></td>
      <td><c:out value="${datos.facultad}"/></td>
      <td>
        <input type="submit" value='Memorandum' name="accion">
      </td>
    </tr>
  </form>        
  </c:forEach>
  </tr>    
</table>  
<%@ include file="../../Inferior.jsp" %>
