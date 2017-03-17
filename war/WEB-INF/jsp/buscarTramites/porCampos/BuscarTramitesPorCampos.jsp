<%@ include file="../../Superior.jsp" %>

<div class="titulo"> Buscar Procesos/Tr&aacute;mites por Campos</div>

<br>
<form name="forma" method="POST" action='<c:url value="/buscarTramitesPorCampos.fautapo"/>' >
  <table class="formulario">
    <tr>
      <th colspan="3">INTRODUZCA LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta">Tipo de proceso</td>
      <td class="etiqueta">::</td>
      <td>
        <select name="id_proceso" OnChange="javascript: document.forma.submit()">
	  <option value="0">-- Seleccionar --</option>
          <c:forEach var="lista" items="${lProcesos}" >
	    <option value="<c:out value="${lista.id_proceso}"/>" <c:if test="${lista.id_proceso == id_proceso}">selected</c:if>>
	      <c:out value="${lista.proceso}"/>
            </option>
	  </c:forEach>
	</select>  
      </td>
    </tr>  
    <tr> 
      <td class="etiqueta">Campo <font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>
      <td>
      <select name="id_campo">
        <OPTION value="">-- seleccione --
        <c:forEach var="lista" items="${lCampos}">
          <option value="<c:out value="${lista.id_campo}"/>" <c:if test="${lista.id_campo == id_campo}">selected </c:if>>
	    <c:out value="${lista.campo}"/>
	  </option>
        </c:forEach>
      </select>
      </td>
    </tr>
    <tr>  
      <td class="etiqueta">Valor <font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>
      <td><input type="text" name="valor" value='<c:out value="${valor}"/>'></td>
    </tr>
  </table>
  <center>
    <input type="submit" name="boton" class="buscar" value="Buscar">
  </center>
</form>
<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>
<br>
  <c:if test="${sw=='1'}">   
    <table class="tabla">
      <tr>
        <th colspan="8">RESULTADO DE LA B&Uacute;SQUEDA</th>
      </tr>
      <tr>
        <th> # </th>
	<th>PROCESO</th>
	<th>REFERENCIAS</th>
	<th>REGISTRO</th>
	<th>DATOS ENCONTRADOS</th>
      </tr>
      <c:forEach var="lista" items="${lTramites}" varStatus="contador">
        <!-- ********** Esto es para el efecto ************ -->
           <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
        <!-- ********** Fin  efecto ************ -->
        <form  name='flistar<c:out value="${contador.index}"/>' method="POST" action='<c:url value="/listarDetalleTramite.fautapo"/>' >
          <td align="center"><c:out value="${lista.correlativo2}"/>/<c:out value="${lista.gestion}"/></td>
          <td><img width='25' height='25' src='./imagenes/procesos/<c:out value="${lista.imagen}"/>' border="0">
	    <b><c:out value="${lista.actividad}"/></b><br>
            <a href='javascript: document.flistar<c:out value="${contador.index}"/>.submit()' >
              <i><c:out value="${lista.proceso}"/></i>
  	    </a>
	    <br><c:out value="${lista.ubicacion_organica}"/> 
          </td>
          <td><c:forEach var="referencias" items="${lista.lista}" >
	        <b><c:out value="${referencias.campo}"/>   :  </b><c:out value="${referencias.valor}"/><br>
	      </c:forEach>
          </td>
          <td><fmt:formatDate value="${lista.fec_registro}" pattern="${formatoFecha} ${formatoHora}"/></td>
  	  <td><c:out value="${lista.valor}"/></td>
           <input type="hidden" name="id_tramite"  value="<c:out value="${lista.id_tramite}"/>">
           <input type="hidden" name="correlativo" value="<c:out value="${lista.correlativo2}"/>">
           <input type="hidden" name="boton"       value="Buscar">
	   <input type="hidden" name="id_proceso"  value="<c:out value="${lista.id_proceso}"/>">
           <input type="hidden" name="aplicacion"  value='<c:url value="/"/>' >
	</form>
      </tr>
      </c:forEach>
    </table> 
  </c:if>

<%@ include file="../../Inferior.jsp" %>