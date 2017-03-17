<%@ include file="../Superior.jsp" %>

<jsp:useBean id="now" class="java.util.Date"/>

<div class="titulo">Habilitar  Postulante</div>
<br>
<form name="fvolver" action="<c:url value='/habilitarPstUAB/listarPostulantes.fautapo'/>" method="post">
   <input type="hidden" name="gestion" value="<c:out value='${gestion}'/>">
   <input type="hidden" name="periodo"  value="<c:out value='${periodo}'/>">
   <input type="hidden" name="id_programa" value="<c:out value='${id_programa}'/>">
   <input type="hidden" name="id_facultad" value="<c:out value='${id_facultad}'/>">
   <div> <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a> </div>
</form>
<br>
<form  name=forma action="<c:url value="/habilitarPstUAB/habilitarPostulante.fautapo"/>" method="POST">
  <table class="tabla">
    <tr>
      <th colspan="7"> CONFIRMAR HABILITAR</th>
    </tr>  
    <tr>
      <th>?</th>
      <th>Nro.</th>
      <th>NOMBRES</th>
      <th>C.I.</th>
      <th>Programa<br>(Carrera)</th>
      <th>Gesti&oacute;n</th>
      <th>Periodo</th>
    </tr>
    <c:forEach var="lista" items="${lPostulantesSelec}" varStatus="contador">
    <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
      <td><c:out value="${contador.count}"/></td>
      <td> <input type="checkbox" name="id_postulante_hab"  id="id_postulante_hab<c:out value='${lista.id_postulante}'/>"  value="<c:out value='${lista.id_postulante}'/>"  checked>  </td> 
      <td><c:out value="${lista.paterno}"/>&nbsp;<c:out value="${lista.materno}"/>&nbsp;<c:out value="${lista.nombres}"/></td>
      <td><c:out value="${lista.dip}"/></td>
      <td><c:out value="${lista.programa}"/></td>
      <td><c:out value="${lista.gestion}"/></td>
      <td><c:out value="${lista.periodo}"/></td>
    </tr>
  </c:forEach>
  <tr>
      <td colspan="7" align="center">
        <input type='submit' name="boton" value='Habilitar' class="siguiente" >
	<input type="hidden" name="id_programa"  value="<c:out value="${id_programa}"/>">
	<input type="hidden" name="gestion" value="<c:out value='${gestion}'/>">
        <input type="hidden" name="periodo"  value="<c:out value='${periodo}'/>">
        <input type="hidden" name="id_facultad" value="<c:out value='${id_facultad}'/>">
      </td>
    </tr>
  </table>
</form>


<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>   
<%@ include file="../Inferior.jsp" %>