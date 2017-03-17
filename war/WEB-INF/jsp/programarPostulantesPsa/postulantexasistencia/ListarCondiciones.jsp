<%@ include file="../../Superior.jsp" %>
<!--
 @usuario          :: Yusara Farah
 @fec_registro     :: 19.02.2016
 @ult_usuario      :: Yusara Farah
 @fec_modificacion :: 19.02.2016
-->
<jsp:useBean id="now" class="java.util.Date"/>

<script language="JavaScript">
  var calFormat="<c:out value='${formatoFecha}'/>";
</script>

<!-- ************  Titulo  ********** -->
<div class="titulo">Reporte de Asistencia de Postulantes P.S.A.</div>
<br>
<br>
<!-- ************  fin Tititulo  ********** -->

<form name="forma" method="post" action="formarReporte.fautapo"  target="_blank">
   <input type="hidden" name="tipo_cheque" value="<c:out value='${tipo_cheque}'/>">
  <br>
  <table class="formulario">
     <tr>
      <td class="etiqueta">Gesti&oacute;n</td>
      <td class="etiqueta">::</td>
      <td> <c:out value="${gestion}"/><input type="text" name="gestion" value='<c:out value="${gestion}"/>' > </td>
    
  
    <td class="etiqueta">Periodo</td>
    <td class="etiqueta">::</td>
    <td><c:out value="${periodo}"/> <input type="text" name="periodo" value='<c:out value="${periodo}"/>'> </td>
  </tr>
	
    <tr>
      <th colspan="8">
        <input type=submit value='Aceptar'>
      </th>
    </tr>
  </table>
</form>

<%@ include file="../../Inferior.jsp" %>
