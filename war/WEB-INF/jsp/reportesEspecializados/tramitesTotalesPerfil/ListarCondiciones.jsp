<%@ include file="../../Superior.jsp" %>
<script language='JavaScript' SRC="../js/ajax.js"></script>
<jsp:useBean id="now" class="java.util.Date"/>

<div class="titulo">Listado de tr&aacute;mites atendidos</div>

<form name='forma' method="post" action="formarReporte.fautapo"  target="_blank">

<table class="formulario">
  <tr>
    <th colspan="3">INTRODUZCA LOS DATOS</th>
  </tr>
  <tr>
    <td class="etiqueta">Tr&aacute;mite <font color="red">(*)</font></td>
    <td class="etiqueta">::</td>
    <td>
      <select name="id_proceso">
       <option value=""> - Elija una opción - </option>    
        <c:forEach var="tipo" items="${lProcesos}">
          <option value="<c:out value="${tipo.id_proceso}"/>"> <c:out value="${tipo.proceso}"/></option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td class="etiqueta">Fecha del tr&aacute;mite</td>
    <td class="etiqueta">::</td>
    <td>
      <input type="text" name="fec_comprobantei" size="10" maxlength="10" value="<fmt:formatDate value="${now}" pattern="${formatoFecha}"/>">
        <small><a href="javascript:showCal('fec_comprobantei')"><img src="<c:url value='/imagenes/dibRap/calendario.jpeg'/>" border="0" ></a></small>
      <input type="text" name="fec_comprobantef" size="10" maxlength="10" value="<fmt:formatDate value="${now}" pattern="${formatoFecha}"/>">
        <small><a href="javascript:showCal('fec_comprobantef')"><img src="<c:url value='/imagenes/dibRap/calendario.jpeg'/>" border="0" ></a></small>
    </td>
  </tr>
</table>
<center>
  <input type='button' value='Siguiente' class="siguiente" onClick='obligar(document.forma, "<c:out value='${obligatorios}'/>")'>
</center>

</form>

<script language="JavaScript">
  var calFormat="<c:out value='${formatoFecha}'/>";
</script>

<%@ include file="../../Inferior.jsp" %>