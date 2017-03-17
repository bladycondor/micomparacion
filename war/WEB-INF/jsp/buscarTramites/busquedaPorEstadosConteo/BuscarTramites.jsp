<%@ include file="../../Superior.jsp" %>

<jsp:useBean id="now" class="java.util.Date" />
<div class="titulo"> Conteo de Procesos/Tr&aacute;mites por Fecha y Estado</div>

<br>
<form name="forma" method="POST" action='<c:url value="/listarTramitesFecha.fautapo"/>' >
  <table class="formulario">
    <tr>
      <th>INTRODUZCA LOS DATOS</th>
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
	          <input type="text" name="valor_1" size="10" value='<fmt:formatDate value="${now}" pattern="${formatoFecha}"/>' >
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