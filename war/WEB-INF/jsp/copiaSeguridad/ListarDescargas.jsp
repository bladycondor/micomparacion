<%@ include file="../Superior.jsp" %>

<div class="titulo">Copias de Seguridad</div>

<form name="forma" id="forma" method='post' action="confirmarRestauro.fautapo">
  <input type="hidden" name="sistema" value="<c:url value='/'/>" >
  <input type="hidden" name="archivo" value="" />
<br/>
<table class="tabla">
<tr>
  <th>Fecha y hora</th>
  <th>Descripci&oacute;n</th>
  <th>RESTAURAR</th>
</tr>
<c:forEach var="lista" items="${listaArchivos}" varStatus="contador">
  <!-- ********** Esto es para el efecto ************ -->
  <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  <!-- ********** Fin  efecto ************ -->
    <td><c:out value='${listaArchivos[contador.index][1]}'/></td>
    <td><c:out value='${listaArchivos[contador.index][2]}'/></td>
    <td><div class="modificar">
          <a href="<c:out value="${carpeta}${listaArchivos[contador.index][0]}"/>">Descargar</a>
        </div>
    </td>
  </tr>
</c:forEach>
</table>
</form>

<%@ include file="../Inferior.jsp" %>   