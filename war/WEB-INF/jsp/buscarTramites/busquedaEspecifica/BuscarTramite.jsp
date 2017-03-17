<%@ include file="../../Superior.jsp" %>

<jsp:useBean id="now" class="java.util.Date" />
<div class="titulo"> Buscar Procesos/Tr&aacute;mites</div>
<br>
<form name="forma" method="POST" action='<c:url value="/listarDetalleTramite.fautapo"/>' >
  <table class="formulario">
    <tr>
      <th>BUSQUEDA DE DATOS</th>
    </tr>
    <tr>
      <td>
        <fieldset> 
	  <legend>Introduzca Nro proceso de negocio</legend>
            <table>
              <tr>
                <td class="etiqueta">Gesti&oacute;n  <font color='red'>(*)</font> </td>
                <td class="etiqueta">::</td>
                <td><input type="text" name="gestion" value="<c:out value="${gestion}"/>" onblur='validar(this,"9")' size="10"></td>
              </tr>
              <tr>  
	        <td class="etiqueta">N&uacute;mero de proceso <font color='red'>(*)</font> </td>
    	        <td class="etiqueta">::</td>
	        <td><input type="text" name="correlativo" size="10"></td>
		<td><input type="submit" class="buscar" value="Buscar" onclick="document.forma.boton.value='Buscar'"></td>
              </tr>
	    </table> 
        </fieldset>
      </td>
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
	          <input type="text" name="valor_1" size="10" value='<fmt:formatDate value="${now}" pattern="${formatoFecha}" />' readonly>
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
	        <td><input type="submit" class="buscar" value="Buscar" onclick="document.forma.boton.value='Buscar por Fecha'"></td>
	      </tr>
	    </table>
        </fieldset>
      </td>
    </tr>      
  </table>
  <input type="hidden" name="boton" value="">
  <input type=hidden name="aplicacion" value='<c:url value="/"/>' >
</form>

<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>
<script>
<!--
  var calFormat = "<c:out value='${formatoFecha}'/>";
-->
</script>
