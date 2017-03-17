<%@ include file="../Superior.jsp" %>

<div class="titulo"> Reingresar proceso de negocio </div>

<br>
<form name="forma" method="POST">
  <table class="formulario">
    <tr>
      <th colspan="3">INTRODUZCA LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta">N&uacute;mero de proceso<font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>
      <td><input type="text" name="id_tramite" onblur='validar(id_tramite,"9")'></td>
    </tr>
  </table>
  <center>
    <input class="buscar" type="submit" name="boton" value="Buscar">
  </center>
  <div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>
  <br>

<c:if test="${sw=='1'}">   
  <table class="tabla">
    <tr>
      <th colspan=8 align=center>RESULTADO DE LA B&Uacute;SQUEDA</th>
    </tr>
    <tr align=center>
      <th>#</th>
	<th>PROCESO</th>
	<th>REFERENCIAS</th>
	<th>REGISTRO</th>
	<th>USUARIO</th>
	<c:if test="${ datosTramite.para == -1}" >
	  <th>REDIRECCIONAR</th>
	</c:if>
    </tr>
    <tr>
      <td align="center"><c:out value="${datosTramite.id_tramite}"/></td>
      <td><img width='25' height='25' src='./imagenes/procesos/<c:out value="${datosTramite.imagen}"/>' border="0">
          <b><c:out value="${datosTramite.actividad}"/></b><br>
          <i><c:out value="${datosTramite.proceso}"/></i>
      </td>
      <td><c:forEach var="referencias" items="${datosTramite.lista}" >
          <b><c:out value="${referencias.campo}"/>   :  </b><c:out value="${referencias.valor}"/><br>
          </c:forEach>
      </td>
      <td><fmt:formatDate value="${datosTramite.fec_registro}" pattern="${formatoFecha} ${formatoHora}"/></td>
      <td><c:out value="${datosTramite.usuario}"/><br>Cargo:<c:out value="${datosTramite.cargo}"/></td>
      <c:if test="${datosTramite.para == -1}" >
         <input type="hidden" name="id_tramite_dato" value="<c:out value="${datosTramite.id_tramite}"/>">
	 <td><input type="submit" value="Reingresar" onClick="javascript:document.forma.boton1.value='Reingresar';"></td>
 	     <input type="hidden" name="boton1" value="">
      </c:if>
      <input type="hidden" name="id_proceso" value="<c:out value="${datosTramite.id_proceso}"/>">
    </tr>
  </table> 
</c:if>
<input type="hidden" name="sw" value="<c:out value="${sw}"/>">
</form>

<%@ include file="../Inferior.jsp" %>