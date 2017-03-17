<%@ include file="../Superior.jsp" %>

<div class="titulo"><c:out value="${tabla.etiqueta}" /></div>

<form name="forma" id="forma" method='post' action="<c:url value='/dibRap/nuevoRegistro.fautapo'/>">
  <input type="hidden" name="t" value="<c:out value='${tabla.id_tabla}' />" />
  <input type="hidden" name="e" value="<c:out value='${id_enlace}' />" />
  <input type="hidden" name="c" value="" id="c" />
  <input type="hidden" name="p" value="<c:out value='${permiso}' />" />
  <input type="hidden" name="_f" value="<c:out value='${tabla.condicion}' />" />
  <input type="hidden" name="a" value="<c:out value='${id_actividad}' />" />
  <input type="hidden" name="n" value="1" />
  <input type="hidden" name="_botoncillo" value="" />
  <input type="hidden" name="_order" value="<c:out value='${tabla.campo}' />" />
  <input type="hidden" name="_order_ant" value="" />

<table width="100%">
<tr>
  <td>
    <c:if test='${fn:indexOf(permiso, "a") > -1}'>
      <div class="agregar">
        <a href="javascript: document.forma.submit()">Nuevo</a>
      </div>
    </c:if>
  </td>
  <td align="center">
    Filtro ::
    <select name="_campo">
      <c:forEach var="nombresCampos" items="${listaNombresCampos}">
        <option value="<c:out value='${nombresCampos.campo}'/>" <c:if test='${nombresCampos.campo == campo}'>selected="selected"</c:if> ><c:out value="${nombresCampos.etiqueta}"/></option>
      </c:forEach>
    </select>
    <select name='_condicion'>
      <optgroup label="Similitud">
        <option value=" like " <c:if test='${condicion == " like "}'>selected="selected"</c:if> >incluye</option>
      </optgroup>
      <optgroup label="Simples">
        <option value=" = " <c:if test='${condicion == " = "}'>selected="selected"</c:if> >=</option>
        <option value=" > " <c:if test='${condicion == " > "}'>selected="selected"</c:if> >&gt;</option>
        <option value=" < " <c:if test='${condicion == " < "}'>selected="selected"</c:if> >&lt;</option>
      </optgroup>
      <optgroup label="Combinaciones">
        <option value=" >= " <c:if test='${condicion == " >= "}'>selected="selected"</c:if> >&gt;=</option>
        <option value=" <= " <c:if test='${condicion == " <= "}'>selected="selected"</c:if> >&lt;=</option>
      </optgroup>
    </select>
    <input type="text" name="_filtro" value="<c:out value='${filtro}'/>" />
    <input type="submit" value="Filtrar" onClick="javascript: document.forma.action=''; document.forma._botoncillo.value='filtro'" class="filtro" />
    <input type="submit" value="Todo" onClick="javascript: document.forma.action=''; document.forma._botoncillo.value='todo'" class="filtro" />
  </td>
  <td align="right">
    <c:if test='${tabla.pagina > 1}'>
      <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action=''; document.forma._botoncillo.value='principio'; document.forma.n.value='1';">&laquo; Principio</a>&nbsp;
      <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action=''; document.forma._botoncillo.value='anterior'; document.forma.n.value='<c:out value="${tabla.pagina - 1}"/>';">&lsaquo; Anterior</a>
    </c:if>
    <b><c:out value="${(paginacion * (tabla.pagina - 1)) + 1}"/></b> - <b><c:out value="${(paginacion * (tabla.pagina - 1)) + fn:length(listaRegistros)}"/></b> de <b><c:out value="${totalRegistros}"/></b>
    <c:if test='${tabla.pagina < totalPaginas}'>
      <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action=''; document.forma._botoncillo.value='siguiente'; document.forma.n.value='<c:out value="${tabla.pagina + 1}"/>';">Siguiente &rsaquo;</a>&nbsp;
      <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action=''; document.forma._botoncillo.value='final'; document.forma.n.value='<c:out value="${totalPaginas}"/>';">Final &raquo;</a>
    </c:if>
  </td>
</tr>
</table>

<table class="tabla">
<tr>
  <c:forEach var="campos" items="${listaNombresCampos}" varStatus="contador1">
    <th>
      <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action=''; document.forma._order.value='<c:out value="${campos.campo}"/>'; document.forma._order_ant.value='<c:out value="${tabla.campo}"/>'" >
        <c:out value="${campos.etiqueta}"/>
        <c:if test='${tabla.campo == campos.campo}'>
          <img border="0" src="../imagenes/dibRap/ascendente.gif"></img>
        </c:if>
        <c:if test='${(fn:indexOf(tabla.campo, campos.campo) == 0) && (fn:indexOf(tabla.campo, " DESC") > -1)}'>
          <img border="0" src="../imagenes/dibRap/descendente.gif"></img>
        </c:if>
      </a>
    </th>
  </c:forEach>
  <c:if test='${fn:indexOf(permiso, "m") > -1}'>
    <th>MODIFICAR</th>
  </c:if>
  <c:if test='${fn:indexOf(permiso, "b") > -1}'>
    <th>ELIMINAR</th>
  </c:if>
</tr>
<c:forEach var="lista" items="${listaRegistros}" varStatus="contador">
  <!-- ********** Esto es para el efecto ************ -->
  <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  <!-- ********** Fin  efecto ************ -->
  <c:forEach begin="0" end="${nro_campos - 1}" var="i">
    <td><c:out value='${matrizDatos[contador.index][i]}' escapeXml="false"/></td>
  </c:forEach>
  <c:if test='${fn:indexOf(permiso, "m") > -1}'>
    <td><div class="modificar">
          <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action='<c:url value="/dibRap/modificaRegistro.fautapo"/>';
	    document.forma.c.value='<c:out value="${listaValoresPrimarios[contador.index].valores}"/>'">Modificar</a>
        </div>
    </td>
  </c:if>
  <c:if test='${fn:indexOf(permiso, "b") > -1}'>
    <td><div class="eliminar">
          <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action='<c:url value="/dibRap/confirmarBorrado.fautapo"/>';
            document.forma.c.value='<c:out value="${listaValoresPrimarios[contador.index].valores}"/>'">Eliminar</a>
        </div>
    </td>
  </c:if>
  </tr>
</c:forEach>
</table>

<table width="100%">
<tr>
  <td align="right">
    <c:if test='${tabla.pagina > 1}'>
      <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action=''; document.forma.n.value='1';">&laquo; Principio</a>&nbsp;
      <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action=''; document.forma.n.value='<c:out value="${tabla.pagina - 1}"/>';">&lsaquo; Anterior</a>
    </c:if>
    <b><c:out value="${(paginacion * (tabla.pagina - 1)) + 1}"/></b> - <b><c:out value="${(paginacion * (tabla.pagina - 1)) + fn:length(listaRegistros)}"/></b> de <b><c:out value="${totalRegistros}"/></b>
    <c:if test='${tabla.pagina < totalPaginas}'>
      <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action=''; document.forma.n.value='<c:out value="${tabla.pagina + 1}"/>';">Siguiente &rsaquo;</a>&nbsp;
      <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action=''; document.forma.n.value='<c:out value="${totalPaginas}"/>';">Final &raquo;</a>
    </c:if>
  </td>
</tr>
</table>

<div class="nota"><sup>(1)</sup>Una de las tablas maestras (for&aacute;neas) no contiene un dato, al cual hace referencia el actual registro</div>

</form>

<%@ include file="../Inferior.jsp" %>