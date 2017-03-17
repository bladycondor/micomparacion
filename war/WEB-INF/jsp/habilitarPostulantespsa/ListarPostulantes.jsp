<%@ include file="../Superior.jsp"%>
<div class="titulo">Listado Postulantes P.S.A.</div>
<br>
<table>
  <tr>
    <td>
     <form name="fvolver" action="<c:url value='/buscarPstpsa/entrada.fautapo'/>" method="post">
      <input type="hidden" name="gestion" value="<c:out value='${gestion}'/>">
      <input type="hidden" name="periodo"  value="<c:out value='${periodo}'/>">
      <input type="hidden" name="id_programa"     value="<c:out value='${id_programa}'/>">
      <input type="hidden" name="id_facultad"  value="<c:out value='${id_facultad}'/>">
      <div> <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a> </div>
    </form>
    </td>
</tr>
</table>

<form name="forma" action="<c:url value="/buscarPstpsa/mostrarPostulantes.fautapo"/>" method="POST">
  <table class="tabla">
  <tr>
    <th colspan="14">INFORMACION ENCONTRADA</th>
  </tr>
  <tr>
    <th>?</th>
    <th>Nro.</th>
    <th>R.P.</th>
    <th>Nombres</th>
    <th>C.I.</th>
    <th>Programa<br>(Carrera)</th>
	<th>Fecha de<br>Examen</th>
	<th>Lugar<br></th>
	<th>Hora<br></th>
	<th>Nro. de Maquina<br></th>
    <th>Tipo de <br>Admisi&oacute;n</th>
    <th>Gesti&oacute;n</th>
    <th>Periodo</th>
    <th>Situaci&oacute;n</th>
  </tr>
  <c:forEach var="lista" items="${listaPostulantes}" varStatus="contador">
    <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
      <td><c:if test="${lista.asistencia == 'FALSE'}"> <input type="checkbox" name="id_postulante_hab"  id="id_postulante_hab<c:out value='${lista.id_postulante}'/>"  value="<c:out value='${lista.id_postulante}'/>"  >
         </c:if>
         
		 </td> 
      
      <td><c:out value="${contador.count}"/></td>
      <td><c:out value="${lista.id_postulante}"/></td>
      <td><c:out value="${lista.paterno}"/>&nbsp;<c:out value="${lista.materno}"/>&nbsp;<c:out value="${lista.nombres}"/></td>
      <td><c:out value="${lista.dip}"/></td>
      <td><c:out value="${lista.programa}"/></td>
	    <td><c:out value="${lista.fecha}"/></td>
		  <td><c:out value="${lista.lugar}"/></td>
		    <td><c:out value="${lista.hora}"/></td>
			  <td><c:out value="${lista.nro_maquinas}"/></td>
      <td><c:out value="${lista.tipo_admision}"/></td>
      <td><c:out value="${lista.gestion}"/></td>
      <td><c:out value="${lista.periodo}"/></td>
      <td>
    <c:if test="${lista.asistencia == 'FALSE'}">
	  <font color="red"> No asistio </font>
	</c:if>
   <c:if test="${lista.asistencia == 'TRUE'}">
	  <font color="blue"> asistio </font>
	</c:if>

	</td>
    </tr>
  </c:forEach>
  <tr>
    <td colspan="11" align="center">
        <input type='submit' name="boton" value='Aceptar' class="siguiente" >
	<input type="hidden" name="gestion" value="<c:out value='${gestion}'/>">
        <input type="hidden" name="periodo"  value="<c:out value='${periodo}'/>">
        <input type="hidden" name="id_programa"     value="<c:out value='${id_programa}'/>">
        <input type="hidden" name="id_facultad"  value="<c:out value='${id_facultad}'/>">
      </td>
    </tr>
  </table>
</form>

<%@ include file="../Inferior.jsp" %>