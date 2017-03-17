<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date"/>
<body onload='inicio(document.forma.clave<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />)'>
<script language='JavaScript' SRC="./validar.js">  </script>
<body onload='inicio(document.forma.clave)'>

<div class="titulo"> Cambio clave(PIN) </div>
<br>

<form name="forma" action="<c:url value="/cambioPinEstudiante/entrada.fautapo"/>" method="POST">
    <input type="hidden" name="hora"     value='<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />' />
    <input type="hidden" name="bandera"  value="<c:out value="${bandera}"/>" >    
<table class="formulario" >
  <tr>
    <th colspan="3" align="center">Introduzca los datos</th>
  </tr>    
  <tr>
    <td class="etiqueta">Estudiante</td>
    <td class="etiqueta">::</td>
    <td>
      <c:out value="${nombres}"/>
    </td>
  </tr>
  <tr>
    <td class="etiqueta">Clave(PIN)</td>
    <td class="etiqueta">::</td>
    <td>
      <input type="password" maxlength="30" name='clave<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />'>
    </td>
  <tr>    
    <td colspan="3" align="center"> <input class="siguiente" type="submit" name="buscar" value='Buscar'></td>
  </tr>
</table>
</form>
<%@ include file="../../Inferior.jsp" %>

