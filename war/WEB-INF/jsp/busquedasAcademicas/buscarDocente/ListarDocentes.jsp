<%@ include file="../../Superior.jsp" %>

<div class="titulo">Buscar Docentes</div>
<div class="volver"><a href='<c:url value="/buscarDocenteEntrada.fautapo"/>'>Volver</a></div>
<br>

<table class="tabla">
  <tr>
    <th>Nro.</th>
    <th>DIP</th>
    <th>NOMBRES</th>
    <th>VER</th>
  </tr>    
  <c:forEach var="datos" items="${lDocentes}" varStatus="contador">
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ --> 
      <td><c:out value="${contador.count}"/></td>
      <td><c:out value="${datos.dip}"/></td>
      <td><c:out value="${datos.nombre_completo}"/></td>
      <form name=forma<c:out value="${contador.count}"/> method="POST" action='<c:url value="/buscarDocente.fautapo"/>'>
        <td>
          <input type="hidden" name="id_docente" value='<c:out value="${datos.id_docente}"/>' >
          <a href='javascript: document.forma<c:out value="${contador.count}"/>.submit();' > Ver asignaci&oacute;n </a>
        </td>
      </form>
    </tr>
  </form>
  </c:forEach>
  </tr>    
</table>  

<%@ include file="../../Inferior.jsp" %>