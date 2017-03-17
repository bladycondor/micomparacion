<%@ include file="../Superior.jsp" %>

<jsp:useBean id="now" class="java.util.Date"/>
<body onload="inicio(document.forma.apodo<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />)">

<form name='forma' action='<c:url value="/buscarConexion.fautapo"/>' method='post'>
  <input type="hidden" name="hora" value='<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />' />
  <center>
    <table border="0" class="formulario">
      <tr class="colh">
	<th colspan="3" align="center">INGRESAR AL SISTEMA</th>
      <tr class="colh">
	<td align="right" class="etiqueta">Usuario</td>
        <td class="etiqueta">::</td>
	<td class="colb"><input type="password" name='apodo<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />' onblur='validar(this,"A9")'/></td>
      </tr>
      <tr class="colh">
	<td align="right" class="etiqueta">Clave (PIN)</td>
        <td class="etiqueta">::</td>
        <td class="colb"><input type="password" name='clave<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />' /></td>
      </tr>
      <tr>
	<td colspan="3" align="center"><input type="submit" value="Iniciar Sesi&oacute;n"></td>
      </tr>
    </table>
  <br/>
  <font color="red"><c:out value="${mensaje}"/></font>
  </center>
</form>

<%@ include file="../Inferior.jsp" %>