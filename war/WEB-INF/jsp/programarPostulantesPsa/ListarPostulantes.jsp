<%@ include file="../Superior.jsp"%>
<jsp:useBean id="now" class="java.util.Date"/>
<div class="titulo">Programar Postulantes P.S.A.</div>
<br>
<table>
  <tr>
    <td>
     <form name="fvolver" action="<c:url value='/habilitarPstpsa/entrada.fautapo'/>" method="post">
      <input type="hidden" name="gestion" value="<c:out value='${gestion}'/>">
      <input type="hidden" name="periodo"  value="<c:out value='${periodo}'/>">
      <input type="hidden" name="fecha"     value="<c:out value='${fecha}'/>">
	   <input type="hidden" name="hora"     value="<c:out value='${hora}'/>">
	    <input type="hidden" name="lugar"     value="<c:out value='${lugar}'/>">
      <input type="hidden" name="nro_maquinas"  value="<c:out value='${nro_maquinas}'/>">
      <div> <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a> </div>
    </form>
    </td>
</tr>
</table>

<form name="forma" action="<c:url value="/habilitarPstpsa/mostrarPostulantes.fautapo"/>" method="POST">
      <table class="tabla" width="100%">
        <thead>
        <tr>
          
    <th>Nro.</th>
    <th>R.P.</th>
    <th>Nombres</th>
    <th>C.I.</th>
	<th>Carrera</th>
	<th>Fecha<br>de Examen P.S.A.</th>
    <th>Lugar <br>Examen</th>
    <th>Hora</th>
    <th>Nro de Maquina asignada</th>
		  
        
		  
        </tr>
	</thead>
  <tbody class="tabla">

  <!-- **************    VALORES     ************ -->
   
   <c:set var="aux" value="0"/> 
    <c:set var="concepto" value="0"/> 
    <c:forEach var="lista" items="${lTransacciones}" varStatus="contador">
	<c:set var="aux" value="${lista.lugar}"/>	
	
	<!--<c:if test="${aux != concepto}">  
	<tr> <td  colspan=6 height="1" style="border-top: 1px solid #000000; border-bottom: 0px solid #000000;" align="left"> <c:out value="${lista.asistencia}"/> :: <c:out value="${lista.lugar}"/> </td>
           </tr> 
		   </c:if>-->
		   <c:set var="concepto" value="${lista.lugar}"/>		
    
    <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
            
      <td><c:out value="${contador.count}"/></td>
      <td><c:out value="${lista.id_postulante}"/></td>
      <td><c:out value="${lista.paterno}"/>&nbsp;<c:out value="${lista.materno}"/>&nbsp;<c:out value="${lista.nombres}"/></td>
      <td><c:out value="${lista.dip}"/></td>
      <td><c:out value="${lista.programa}"/></td>
      <td><c:out value="${lista.fecha}"/></td>
      <td><c:out value="${lista.lugar}"/></td>
      <td><c:out value="${lista.hora}"/></td>
	  <td><c:out value="${lista.nro_maquinas}"/></td>
	 <!-- <td><c:out value="${lista.asistencia}"/></td>-->
      <td>
    <c:if test="${lista.asistencia == 'TRUE'}">
	  <font color="blue"> Asistio </font>
	</c:if>
   <c:if test="${lista.asistencia == 'FALSE'}">
	  <font color="black"> No asistio </font>
	</c:if>

	
      </td>
    </tr>
	
  </c:forEach>
  <!-- **************  FIN VALORES   ************ -->
  <!-- **************    TOTALES     ************ -->
   
 
 </tbody>
</table>
</form>

<%@ include file="../Inferior.jsp" %>