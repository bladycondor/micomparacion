<%@ include file="../Superior.jsp" %>
  <div class=titulo>Lista Reportes</div>
  <c:if test="${empty listaReportes}">
    <center><div class="cuadroAviso" align="center">No se tiene ningun reporte creado</div></center>
  </c:if>


<form name='forma' id="forma" method='post' action="<c:url value='/dibRep.fautapo'/>">
  <div class="agregar">
    <a href="javascript: document.forma.submit()">Nuevo</a>
  </div>
</form>

  <c:if test="${ !empty listaReportes}">
    <table class="tabla" border="0">
      <tr>
        <th align="center">ID REPORTE</th>
        <th align="center">DESCRIPCION</th>
        <th align="center">VER</th>
        <th align="center">MODIFICAR</th>
        <th align="center">BORRAR</th>
      </tr>
      <c:forEach var="reportes" items="${listaReportes}" varStatus="contador">
        <!-- ********** Esto es para el efecto ************ -->
          <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
        <!-- ********** Fin  efecto ************ --> 
        <td>
          <c:out value="${reportes.id_consulta}"/>
        </td>
        <td>
          <c:out value="${reportes.descripcion}"/>
        </td>
        <form name='formaMostrar<c:out value="${contador.count}"/>' method='post' action="<c:url value='/listarDibRep.fautapo'/>">
          <td>
            <div class="nota" ><a href='javascript:document.formaMostrar<c:out value="${contador.count}"/>.submit();'>Ver</a></div>
	    <input type=hidden name="c" value="<c:out value="${reportes.id_consulta}"/>">
          </td>
        </form>
	
        <form name='formaModificar<c:out value="${contador.count}"/>' method='post' action="<c:url value='/modificarDibRep.fautapo'/>">
          <td>
            <div class="modificar"><a href='javascript:document.formaModificar<c:out value="${contador.count}"/>.submit();'>Modificar</a></div>
	    <input type=hidden name="c" value="<c:out value="${reportes.id_consulta}"/>">
          </td>
        </form>

        <form name='formaBorrar<c:out value="${contador.count}"/>' method='post' action="<c:url value='/confirmarBorrarDibRep.fautapo'/>">
          <td>
            <div class="eliminar"><a href='javascript:document.formaBorrar<c:out value="${contador.count}"/>.submit();'>Eliminar</a></div>
	    <input type=hidden name="c" value="<c:out value="${reportes.id_consulta}"/>">
          </td>
        </form>

      </c:forEach>
    </table>
  </c:if>

<%@ include file="../Inferior.jsp" %>