<%@ include file="../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date"/>
<body onload='inicio(document.forma.clave<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />)'>
<script language='JavaScript' SRC="./validar.js"> </script>

<body onload='inicio(document.forma.clave)'>

<div class="titulo">Cerrar libreta Por Materia</div>
<br>
<form action="<c:url value="/cerrarLibretaMateria/cerrarLibreta.fautapo"/>"  method="post">
  <input type="hidden" name="hora" value='<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />' />
  <table class="formulario" >
    <tr>
      <th colspan="3" align="center">INTRODUZCA LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta">Usuario</td>
      <td class="etiqueta">::</td>
      <td><c:out value="${usuario}" /></td>
    </tr>
    <tr>
      <td class="etiqueta">Gesti&oacute;n</td>
      <td class="etiqueta">::</td>
      <td><input type="text" name="gestion" value='<c:out value="${gestion}" />' onblur='validar(gestion,"9")' size=4 maxlength=4></td>
    </tr>
    <tr>
      <td class="etiqueta">Periodo</td>
      <td class="etiqueta">::</td>
      <td><input type="text" name="periodo" value='<c:out value="${periodo}" />' onblur='validar(periodo,"9")' size=1 maxlength=1> </td>
    </tr>
    <tr>
      <td class="etiqueta">Tipo de Evaluaci&oacute;n</td>
      <td class="etiqueta">::</td>
      <td>
        <select name="id_tipo_evaluacion">
          <option value=""> - Elija una opción - </option>    
          <c:forEach var="tipo" items="${lTiposEvaluaciones}">
            <option value="<c:out value="${tipo.id_tipo_evaluacion}"/>"> <c:out value="${tipo.tipo_evaluacion}"/></option>
          </c:forEach>
        </select>
      </td>
    </tr>
    <tr>
      <td class="etiqueta" align="right">Clave</td>
      <td class="etiqueta">::</td>
      <td>
      <input type="password" name='clave<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss" />'>
      </td>
    </tr>
    <tr>
      <td colspan=3 align=center><input class="siguiente" type="submit" value="Buscar"></td>
    </tr>
  </table>
</form>

<%@ include file="../Inferior.jsp" %>