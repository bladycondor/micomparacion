<%@ include file="../../Superior.jsp" %>

<jsp:useBean id="now" class="java.util.Date" />
<div class="titulo"> Reporte Global de Procesos/Tr&aacute;mites</div>

<c:if test="${accion != 'Buscar'}">
  <form  name="forma" method="POST" action='<c:url value="/reporteGlobalGeneral2.fautapo"/>'>
    <br>
    <table class="formulario"> 
      <tr>
        <th colspan="3">INTRODUZCA LOS DATOS</th>
      </tr>  
      <tr>
        <td class="etiqueta">Área <font color='red'>(*)</font> </td>
        <td class="etiqueta">::</td>
        <td>
          <select name="id_ubicacion_organica">
    	    <option value="0">-- Seleccionar --</option>
            <c:forEach var="lista" items="${lUbicacionesOrganicas}" >
	      <option value="<c:out value="${lista.id_ubicacion_organica}"/>">
	        <c:out value="${lista.ubicacion_organica}"/>
              </option>
	    </c:forEach>
	  </select>
        </td>
      </tr>
      <tr> 
        <td class="etiqueta">Fecha inicio <font color='red'>(*)</font> </td>
        <td class="etiqueta">::</td>
        <td>
          <input type="text" name="valor_1" size="10" value='<fmt:formatDate value="${now}" pattern="${formatoFecha}"/>' readonly>
	  <small><a href="javascript:showCal('valor_1')"><img src="./imagenes/formularios/calendario.jpeg" border="0" ></a></small>
        </td>
      </tr>
      <tr>
        <td class="etiqueta">Fecha final <font color='red'>(*)</font> </td>
        <td class="etiqueta">::</td>  
        <td>
          <input type="text" name="valor_2" size="10" value='<fmt:formatDate value="${now}" pattern="${formatoFecha}"/>' readonly>
	  <small><a href="javascript:showCal('valor_2')"><img src="./imagenes/formularios/calendario.jpeg" border="0" ></a></small>
        </td>
      </tr>
    </table>
    <center>
      <input type="submit" name="boton" class="buscar" value="Buscar">
    </center>
  </form>
  <div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>
</c:if>

<c:if test="${accion == 'Buscar'}">
  <div><a class="volver" href='reporteGlobalGeneral.fautapo'> Volver</a></div>
  <br>
  <table class="tabla">
    <tr>
      <th>Periodo de</th>
      <td class="colb"><c:out value="${fecha_ini}"/>
      <th>al</th>
      <td class="colb"><c:out value="${fecha_fin}"/>
    </tr>
  </table>

  <br>
  <table class="tabla"> 
    <tr>
      <th colspan="2">INICIADOS</th>
    </tr> 
    <tr>
      <th>PROCESO</th>
      <th>CANTIDAD</th>
    </tr>  
    <c:forEach var="cod" items="${lTramitesIniciados}" varStatus="contador" >
    <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9"</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ -->
     <form name='forma<c:out value="${contador.index}"/>' method="post" action='<c:url value="/listarTramitesGlobalesGeneral.fautapo"/>' >
       <td>
         <a href='javascript: document.forma<c:out value="${contador.index}"/>.submit()' >
          <c:out value="${cod.proceso}"/>
            <input type="hidden" name="id_proceso" value='<c:out value="${cod.id_proceso}"/>'> 
	    <input type="hidden" name="estado"     value="Iniciados">
	    <input type="hidden" name="fecha_ini"  value="<c:out value="${fecha_ini}"/>" >
            <input type="hidden" name="fecha_fin"  value="<c:out value="${fecha_fin}"/>">
	    <input type="hidden" name="id_ubicacion_organica"  value="<c:out value="${id_ubicacion_organica}"/>" >
	  </a>      
         </td>
         <td align="right"><c:out value="${cod.resultado}"/></td>
     </form> 
    </tr>
    </c:forEach>
    <tr>
      <th>TOTAL</th>
      <th align="right"><c:out value="${totalIniciados}"/></th>
    </tr>
  </table>

  <br>
  <table class="tabla"> 
    <tr>
      <th colspan="2">MOVIDOS</th>
    </tr>   
    <tr>
      <th>PROCESO</th>
      <th>CANTIDAD</th>
    </tr>  
    <c:forEach var="cod" items="${lTramitesMovidos}" varStatus="contador" >
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ -->
     <form name='formaM<c:out value="${contador.index}"/>' method=post action='<c:url value="/listarTramitesGlobalesGeneral.fautapo"/>' >
         <td>
           <a href='javascript: document.formaM<c:out value="${contador.index}"/>.submit()' >
            <c:out value="${cod.proceso}"/>
	    <input type="hidden" name="id_proceso" value='<c:out value="${cod.id_proceso}"/>'> 
	    <input type="hidden" name="estado"     value="Movidos"> 
	    <input type="hidden" name="fecha_ini"  value="<c:out value="${fecha_ini}"/>" >
            <input type="hidden" name="fecha_fin"  value="<c:out value="${fecha_fin}"/>">
	    <input type="hidden" name="id_ubicacion_organica" value="<c:out value="${id_ubicacion_organica}"/>" >
	   </a>      
         </td>	  
         <td align="right"><c:out value="${cod.resultado}"/></td>
     </form> 
    </tr>
    </c:forEach>
    <tr>
      <th>TOTAL</th>
      <th align="right"><c:out value="${totalMovidos}"/></th>
    </tr>
  </table>

  <!--PROCESOS CONCLUIDOS-->
  <br>
  <table class="tabla"> 
    <tr>
      <th colspan="2">CONCLUIDOS</th>
    </tr>   
    <tr>
      <th>PROCESO</th>
      <th>CANTIDAD</th>
    </tr>  
    <c:forEach var="cod" items="${lTramitesConcluidos}" varStatus="contador" >
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ -->
      <form name='formaC<c:out value="${contador.index}"/>' method=post action='<c:url value="/listarTramitesGlobalesGeneral.fautapo"/>' >
          <td>
          <a href='javascript: document.formaC<c:out value="${contador.index}"/>.submit()' >
            <c:out value="${cod.proceso}"/>
	    <input type="hidden" name="id_proceso" value='<c:out value="${cod.id_proceso}"/>'> 
	    <input type="hidden" name="estado"     value="Concluidos">
	    <input type="hidden" name="fecha_ini"  value="<c:out value="${fecha_ini}"/>" >
            <input type="hidden" name="fecha_fin"  value="<c:out value="${fecha_fin}"/>">
	    <input type="hidden" name="id_ubicacion_organica" value="<c:out value="${id_ubicacion_organica}"/>" >
	  </a>
          </td>
          <td align="right"><c:out value="${cod.resultado}"/></td>
      </form> 
    </tr>
    </c:forEach>
    <tr>
      <th>TOTAL</th>
      <th align="right"><c:out value="${totalConcluidos}"/></th>
    </tr>
  </table>
</c:if>

<script>
<!--
  var calFormat = "<c:out value='${formatoFecha}'/>";
-->
</script>

<%@ include file="../../Inferior.jsp" %>