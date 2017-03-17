<%@ include file="../../Superior.jsp" %>

<jsp:useBean id="now" class="java.util.Date" />
<div class="titulo"> Buscar Procesos/Tr&aacute;mites por Fecha y Estado</div>
<br>
<form name="forma" method="POST" action='<c:url value="/buscarTramiteEstado.fautapo"/>' >
  <table class="formulario">
    <tr>
      <th>BUSQUEDA DE DATOS</th>
    </tr>
    <tr>
      <td>
        <fieldset> 
          <legend>Introduzca Fechas</legend>
            <table>	     
              <tr>  
  	        <td class="etiqueta">Fecha inicio <font color='red'>(*)</font> </td>
	        <td class="etiqueta"> :: </td>
                <td>
	          <input type="text" name="valor_1" size="10" value='<fmt:formatDate value="${now}" pattern="${formatoFecha}" />' >
		  <small><a href="javascript:showCal('valor_1')"><img src="./imagenes/formularios/calendario.jpeg" border="0" ></a></small>
                </td>
    	      </tr>
	      <tr>
	        <td class="etiqueta">Fecha final <font color='red'>(*)</font> </td>
                <td class="etiqueta">::</td>  
                <td>
	          <input type="text" name="valor_2" size="10" value='<fmt:formatDate value="${now}" pattern="${formatoFecha}" />' readonly>
		  <small><a href="javascript:showCal('valor_2')"><img src="./imagenes/formularios/calendario.jpeg" border="0" ></a></small>
                </td>
	      </tr>
	    </table>
        </fieldset>
      </td>
    </tr>
    <tr>
      <td>
        <fieldset> 
	  <legend>Eliga el estado</legend>
            <table>
	     <c:forEach var="estadito" items="${lEstados}" varStatus="contador" >
              <tr>  
	        <td class="etiqueta2"> 
		  <input type="radio" name="id_estado"  value="<c:out value="${estadito.id_estado}"/>">
		</td>
	        <td class="etiqueta2">
                  <c:out value="${estadito.estado}"/>
		</td>
              </tr>
	      </c:forEach>
	      <tr>
	        <td class="etiqueta2">
		  <input type="radio" name="id_estado" value="" checked>
		</td>
	        <td class="etiqueta2">
		  Todos
		</td>
	      </tr>	
	    </table> 
        </fieldset>
      </td>
    </tr>
  </table>
  <center>
    <input type="submit" name="boton" class="buscar" value="Buscar">
  </center>
</form>

<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>

<script>
<!--
  var calFormat = "<c:out value='${formatoFecha}'/>";
-->
</script>

<%@ include file="../../Inferior.jsp" %>