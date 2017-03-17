<%@ include file="../Superior.jsp" %>

<div class="titulo">Procesos/Tr&aacute;mites Concluidos</div>
<br>

<form name="formaProceso" method="POST" action="<c:url value='/listarTramitesConcluidos.fautapo'/>">
  <table class="formulario">
    <tr>
      <th>Tipo de proceso <c:out value="${id_proceso}"/></th>
      <th>::</th>
      <td>
        <select name="id_proceso" OnChange="javascript: document.formaProceso.submit()">
          <option value="0">-- Seleccionar --</option>
          <c:forEach var="lista" items="${lProcesos}">
            <option value="<c:out value="${lista.id_proceso}"/>"<c:if test="${lista.id_proceso == id_proceso}">selected</c:if>>
              <c:out value="${lista.proceso}"/>
            </option>
          </c:forEach>
        </select>  
      </td>
    </tr>  
  </table>
</form>

<c:if test="${id_proceso != null}">
  <br>
  <form name="forma" id="forma" method='post' action="<c:url value='/listarTramitesConcluidos.fautapo'/>">
    <input type="hidden" name="id_proceso" value='<c:out value="${id_proceso}"/>' >
    <input type="hidden" name="nro_pagina"  value="1" />
    <input type="hidden" name="_botoncillo" value="" />
    <table width="100%" class="formulario">
      <tr>
        <td width="50%">
          <table border="0">
            <tr>
              <td> Nro de Tr&aacute;mite :: </td>
              <td><input type="text" name="nro_filtro"></td>
              <td><input type="submit" value="Filtrar" onClick="javascript: document.forma.action=''; document.forma._botoncillo.value='filtro'" class="filtro" /></td>
              <td><input type="submit" value="Todo" onClick="javascript: document.forma.action=''; document.forma._botoncillo.value='todo'" class="filtro" /></td>
            </tr>
          </table>
        </td>
        <td width="50%" align="right">
          <table border="0" align="right">
            <tr>
              <td>
                <c:if test='${nro_pagina > 1}'>
                  <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action=''; document.forma._botoncillo.value='principio'; document.forma.nro_pagina.value='1';">&laquo; Principio</a>&nbsp;
                  <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action=''; document.forma._botoncillo.value='anterior'; document.forma.nro_pagina.value='<c:out value="${nro_pagina - 1}"/>';">&lsaquo; Anterior</a>
                </c:if>
                <b><c:out value="${(paginacion * (nro_pagina - 1)) + 1}"/></b> - <b><c:out value="${(paginacion * (nro_pagina - 1)) + fn:length(lMisPendientes)}"/></b> de <b><c:out value="${totalRegistros}"/></b>
                <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action=''; document.forma._botoncillo.value='siguiente'; document.forma.nro_pagina.value='<c:out value="${nro_pagina + 1}"/>';">Siguiente &rsaquo;</a>&nbsp;
                <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.action=''; document.forma._botoncillo.value='final'; document.forma.nro_pagina.value='<c:out value="${totalPaginas}"/>';">Final &raquo;</a>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </form>

  <table class="tabla" width="100%">
    <tr>
      <th>#</th>
      <th>REMITENTE</th>
      <th>REFERENCIAS</th>
      <th>ACTIVIDAD</th>
      <th>VER DATOS</th>
    </tr>
    <c:forEach var="lista" items="${lMisPendientes}" varStatus="contador">
       <!-- ********** Esto es para el efecto ************ -->
         <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
       <!-- ********** Fin  efecto ************ -->
        <td class="colb" align="center"><c:out value="${lista.correlativo2}"/>/<c:out value="${lista.gestion}"/></td>
        <td>
          <c:out value="${lista.usuario}"/>
  	<br><c:out value="${lista.cargo}"/>
        </td>
        <td>
          <c:forEach var="referencias" items="${lista.lista}" >
  	  <b><c:out value="${referencias.campo}"/> : </b><c:out value="${referencias.valor}"/><br>
  	</c:forEach>
        </td>
        <td><b><c:out value="${lista.actividad}"/></b><br>
            <i><c:out value="${lista.proceso}"/></i>
        </td>
        <td align="right">
          <form name=forma<c:out value="${contador.count}"/> method="POST" action='<c:url value="/buscarTramiteConcluido.fautapo"/>' >
            <input type="hidden" name="id_tramite" value='<c:out value="${lista.id_tramite}"/>' >
            <input type="hidden" name="id_proceso" value='<c:out value="${lista.id_proceso}"/>' >
            <input type="hidden" name="aplicacion" value='<c:url value="/"/>' >
            <input type="hidden" name="nro_pagina" value='<c:out value="${nro_pagina}"/>' >
            <a href='javascript: document.forma<c:out value="${contador.count}"/>.submit();'> Ver Datos </a>
       	  </form>
        </td>
      </tr>
    </c:forEach>
  </table>
</c:if>
  
<%@ include file="../Inferior.jsp" %>