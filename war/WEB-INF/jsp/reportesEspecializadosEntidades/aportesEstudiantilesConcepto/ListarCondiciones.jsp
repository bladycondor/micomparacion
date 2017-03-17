<%@ include file="../../Superior.jsp" %>
<!--
  UABJB
  @autor            : Softwate UABJB
  @fec_registro     : 05/04/2016
  @ult_usuario      : Bladimir
  @fec_modificacion : 05/04/2016
-->
<jsp:useBean id="now" class="java.util.Date"/>

<script language="JavaScript">
  var calFormat="<c:out value='${formatoFecha}'/>";
</script>

<!-- ************  Titulo  ********** -->
<div class="titulo">Resumen Obligaciones Diversas Estudiantiles - Entidades Financieras - Por Concepto</div>
<br>
<br>
<!-- ************  fin Tititulo  ********** -->

<form name="forma" method="post" action="formarReporte.fautapo"  target="_blank">
   <input type="hidden" name="tipo_cheque" value="<c:out value='${tipo_cheque}'/>">
  <br>
  <table class="formulario">
    <tr>
      <th align="right">Fecha de la transacci&oacute;n</th>
      <td>
        <input type="text" name="fec_comprobantei" size="10" maxlength="10" value="<fmt:formatDate value="${now}" pattern="${formatoFecha}"/>">
	  <small><a href="javascript:showCal('fec_comprobantei')"><img src="<c:url value='/imagenes/dibRap/calendario.jpeg'/>" border="0" ></a></small>
        <input type="text" name="fec_comprobantef" size="10" maxlength="10" value="<fmt:formatDate value="${now}" pattern="${formatoFecha}"/>">
	  <small><a href="javascript:showCal('fec_comprobantef')"><img src="<c:url value='/imagenes/dibRap/calendario.jpeg'/>" border="0" ></a></small>
      </td>
    </tr>
    <tr>
      <th colspan="8">
        <input type=submit value='Aceptar'>
      </th>
    </tr>
  </table>
</form>

<%@ include file="../../Inferior.jsp" %>
