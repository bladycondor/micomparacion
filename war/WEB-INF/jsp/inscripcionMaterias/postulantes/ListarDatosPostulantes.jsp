<%@ include file="../../Superior.jsp" %>

<div class="titulo">Inscripci&oacute;n de materias postulantes</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<br>

<table class="tabla"  valign=left border="0" cellspacing="2" cellpadding="3">

  <tr>
    <th align=center>RP</th>
    <th align=center>DIP</th>
    <th align=center>NOMBRES</th>
    <th align=center>CARRERA/PROGRAMA</th> 
    <th align=center>VER</th>
  </tr>    
  <c:forEach var="datos" items="${lPostulantes}" varStatus="contador">
  <form name=forma<c:out value="${contador.count}"/> method="POST" action='<c:url value="/inscripcionMateriasPostulante/listarProgramacionMaterias.fautapo"/>'>  
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ --> 
      <td><c:out value="${datos.id_postulante}"/></td>
      <td><c:out value="${datos.dip}"/></td>        
      <td><c:out value="${datos.nombre_completo}"/></td>
      <td><c:out value="${datos.programa}"/></td>
      <td><a href="javascript: document.forma<c:out value="${contador.count}"/>.submit()">Ver datos</a>
      <input type="hidden" name="id_periodo" value="<c:out value="${id_periodo}"/>"/>
      <input type="hidden" name="id_postulante" value="<c:out value="${datos.id_postulante}"/>"/> 
      <input type=hidden name="gestion"       value='<c:out value="${gestion}"/>' >
      <input type=hidden name="periodo"       value='<c:out value="${periodo}"/>' >
      <input type=hidden name="id_programa"   value='<c:out value="${id_programa}"/>' >
  </tr>
  </form>        
  </c:forEach>
  </tr>    

</table>  

<%@ include file="../../Inferior.jsp" %>