<%@ include file="../Superior.jsp" %>
<script language='JavaScript' SRC="../js/ajax.js"></script>
<script language = 'JavaScript' SRC="./validar.js">  </script>
<jsp:useBean id="now" class="java.util.Date"/>
<script language='JavaScript' SRC="../ajax.js"></script>

<div class="titulo">Programacion de Postulantes para Examen P.S.A.</div>
<form name="fvolver" method="POST" action='<c:url value="/habilitarPstpsa/entrada.fautapo"/>' >
  <div class="volver"><a href='javascript: document.fvolver.submit();' > Volver </a></div>
</form>

<form name="forma" action="<c:url value="/habilitarPstpsa/listarPostulantes.fautapo"/>" method="POST">
  <table class="formulario">
  <c:forEach var="listaPostulantesNro" items="${listaPostulantesNro}" varStatus="contador">
    <tr>
      <th colspan="3">ASIGNACION DE CENTROS DE COMPUTO</th>
    </tr>
	<tr>
	<td class="etiqueta"> Numero de Postulantes no asignados: <font color="red" >(*)</font>::&nbsp; 
          <td> <c:out value="${listaPostulantesNro.num}"/><input type="hidden" name="sql" value='<c:out value="${listaPostulantesNro.num}"/>' > </td>
      </td>
	  </tr>
    <tr>
      <td class="etiqueta">Gesti&oacute;n</td>
      <td class="etiqueta">::</td>
      <td> <c:out value="${gestion}"/><input type="hidden" name="gestion" value='<c:out value="${gestion}"/>' > </td>
    
  
    <td class="etiqueta">Periodo</td>
    <td class="etiqueta">::</td>
    <td><c:out value="${periodo}"/> <input type="hidden" name="periodo" value='<c:out value="${periodo}"/>'> </td>
  </tr>
    <tr>
   <!--   <td class="etiqueta"> Fecha de Examen <font color="red" >(*)</font>::&nbsp;
	   <input type="text" name='fec_inicio' <c:if test="${!empty datosAsignacion.fec_inicio2}"> value='<fmt:formatDate value="${datosAsignacion.fec_inicio}" pattern="${formatoFecha}"/>' </c:if>
	  <c:if test="${empty datosAsignacion.fec_inicio2}"> value='<fmt:formatDate value="${now}" pattern="${formatoFecha}"/>' </c:if>
          maxlength='10' size='10' title="Fecha de Inicio" /> &nbsp; <small> <a href="javascript:showCal('fecha')"> <img src="../imagenes/dibRap/calendario.jpeg" border="0" title="Seleccione la fecha" /></a></small>
	         
      </td> -->
	  
	   <td class="etiqueta"> Fecha de Examen <font color="red" >(*)</font>::&nbsp;
	  <input type="text" name="fecha" size="10" maxlength="10" value="<fmt:formatDate value="${now}" pattern="${formatoFecha}"/>">
	  <small><a href="javascript:showCal('fecha')"><img src="<c:url value='/imagenes/dibRap/calendario.jpeg'/>" border="0" ></a></small>
        
      </td>
    </tr>
	<tr>
	<td class="etiqueta"> Hora del Examen<font color="red" >(*)</font>::&nbsp; 
         <input type="text" name='hora'  value='<c:out value="${datosAsignacion.nro_resolucionhcc}"/>' style="background-color:pink"  size="10">
      </td>
	  </tr>
	  <tr>
	<td class="etiqueta"> Lugar del Examen<font color="red" >(*)</font>::&nbsp; 
         <input type="text" name='lugar'  value='<c:out value="${datosAsignacion.nro_resolucionhcc}"/>' style="background-color:pink"  size="15">
      </td>
	  </tr>
	  <tr>
    <td class="etiqueta" > Nro. de Maquinas Habilitadas<font color="red" >(*)</font>::&nbsp; 
         <input type="text" name='nro_maquinas'  value='<c:out value="${datosAsignacion.nro_resolucionhcc}"/>' style="background-color:pink"  size="10">
      </td>
	  </tr>
	    </c:forEach>
  </table>
  <center>
    <input type="submit" class="siguiente" value='Aceptar'>
	<input type="hidden" name="gestion"     value="<c:out value='${gestion}'/>">
      <input type="hidden" name="periodo"     value="<c:out value='${periodo}'/>">	
	  <input type="hidden" name="fec_inicio"     value="<c:out value='${fecha}'/>">
      <input type="hidden" name="lugar"     value="<c:out value='${lugar}'/>">
<input type="hidden" name="hora"     value="<c:out value='${hora}'/>">
      <input type="hidden" name="nro_maquinas"     value="<c:out value='${nro_maquinas}'/>">	
  </center>
</form>
  
<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>


<script language="JavaScript">
  iniciar();
</script>

<%@ include file="../Inferior.jsp" %>