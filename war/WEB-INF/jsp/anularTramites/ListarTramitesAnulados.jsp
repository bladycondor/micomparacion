<%@ include file="../Superior.jsp"%>

<div class="titulo">Anular Procesos/Tr&aacute;mites</div>
<br>
<form name="forma" method="POST" action='<c:url value="/buscarTramiteAnular.fautapo"/>' >
  <table class="formulario">
    <tr>
      <th colspan="3">INTRODUZCA LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta">Gesti&oacute;n  <font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>
      <td><input type="text" name="gestion" value="<c:out value="${gestion}"/>" onblur='validar(this,"9")' size="10"></td>
    </tr>
    <tr>  
      <td class="etiqueta">N&uacute;mero de proceso <font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>
      <td><input type="text" name="correlativo" size="10"></td>
    </tr>
  </table>
  <center>
    <input type="submit" class="siguiente" value="Siguiente" onclick="document.forma.boton.value='Buscar'"></td>
  </center>
  <input type="hidden" name="boton" value="">
  <input type=hidden name="aplicacion" value='<c:url value="/"/>' >
</form>
<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>

<!--LISTAR -->
<br>
<div class="titulo">Listado de Procesos/Tr&aacute;mites Anulados</div>
  <table class="tabla">
    <tr>
      <th>#</th>
      <th>PROCESO</th>
      <th>ACTIVIDAD</th>
      <th>ESTADO</th>
      <th>REFERENCIAS</th>
    </tr>
    <c:forEach var="lista1" items="${lTramitesAnulados}" varStatus="contador">
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ -->
	<td><c:out value = "${lista1.correlativo2}"/>/<c:out value = "${lista1.gestion}"/></td>
	<td><c:out value = "${lista1.proceso}"/></td>
	<td><c:out value = "${lista1.actividad}"/></td>
	<td><c:out value = "${lista1.estado}"/></td>
	<td>
        <c:forEach var="referencias" items="${lista1.lista}">
          <b><c:out value="${referencias.campo}"/>   :  </b><c:out value="${referencias.valor}"/><br>
        </c:forEach>
      </td>
      </tr>
    </c:forEach>
  </table>

<%@ include file="../Inferior.jsp" %>