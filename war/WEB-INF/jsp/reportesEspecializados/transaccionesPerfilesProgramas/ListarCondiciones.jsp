<%@ include file="../../Superior.jsp" %>
<script language='JavaScript' SRC="../js/ajax.js"></script>
<jsp:useBean id="now" class="java.util.Date"/>

<div class="titulo">Listado de transacciones por Perfiles y Programas</div>

<form name='forma' method="post" action="formarReporte.fautapo"  target="_blank">

<table class="formulario">
  <tr>
    <th colspan="3">INTRODUZCA LOS DATOS</th>
  </tr>
  <tr>
    <td class="etiqueta">
      <c:choose>
        <c:when test="${cliente.id_universidad > 0}">Universidad</c:when>
        <c:when test="${cliente.id_facultad > 0}">Facultad</c:when>
        <c:when test="${cliente.id_programa > 0}">Programa</c:when>
      </c:choose>      
    </td>
    <td class="etiqueta">::</td>
    <td><c:out value="${acceso.acceso}"/></td>
  </tr>
  <c:if test="${fn:length(acceso.listaFacultades) > 0}">
    <tr>
      <td class="etiqueta">Facultad <font color="red">(*)</font></td>
      <td class="etiqueta">::</td>
      <td><select name="id_facultad" id="id_facultad" onChange="poblar(this.name, this.options[this.selectedIndex].value)"/></td>
    </tr>
  </c:if>
  <c:if test="${fn:length(acceso.listaProgramas) > 0}">
    <tr>
      <td class="etiqueta">Programa <font color="red">(*)</font></td>
      <td class="etiqueta">::</td>
      <td><select name="id_programa" id="id_programa" onChange="poblar(this.name, this.options[this.selectedIndex].value)"/></td>
    </tr>
  </c:if>
  <tr>
    <td class="etiqueta">Perfil <font color="red">(*)</font></td>
    <td class="etiqueta">::</td>
    <td>
      <select name="id_perfil">
       <option value=""> - Elija una opción - </option>    
        <c:forEach var="tipo" items="${lPerfiles}">
          <option value="<c:out value="${tipo.id_perfil}"/>"> <c:out value="${tipo.perfil}"/></option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td class="etiqueta">Fecha de la transacci&oacute;n</td>
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

<c:if test="${cliente.id_facultad > 0}"><input type="hidden" name="id_facultad" value='<c:out value="${cliente.id_facultad}"/>'></c:if>
<c:if test="${cliente.id_programa > 0}"><input type="hidden" name="id_programa" value='<c:out value="${cliente.id_programa}"/>'></c:if>
</form>

<script language="JavaScript">

var calFormat="<c:out value='${formatoFecha}'/>";
var combo = new Array();
var padre_hijo = new Array();
h = 0;
<c:if test="${fn:length(acceso.listaFacultades) > 0}">
  padre_hijo[h] = new Array("id_facultad", "''");
  combo[h] = new Array();
  <c:forEach var="facultad" items="${acceso.listaFacultades}" varStatus="fac">
    combo[h][<c:out value="${fac.index}"/>] = new Array("<c:out value="${facultad.id_facultad}"/>", "<c:out value="${facultad.facultad}"/>", "");
  </c:forEach>
  h++;
</c:if>
<c:if test="${fn:length(acceso.listaProgramas) > 0}">
  padre_hijo[h] = new Array("id_programa", <c:if test="${fn:length(acceso.listaFacultades) == 0}">"''"</c:if> <c:if test="${fn:length(acceso.listaFacultades) > 0}">"id_facultad"</c:if>);
  combo[h] = new Array();
  <c:forEach var="programa" items="${acceso.listaProgramas}" varStatus="prg">
    combo[h][<c:out value="${prg.index}"/>] = new Array("<c:out value="${programa.id_programa}"/>", "<c:out value="${programa.programa}"/>", "<c:out value="${programa.id_facultad}"/>");
  </c:forEach>
  h++;
</c:if>
</script>

<script language="JavaScript">
  iniciar();
</script>

<%@ include file="../../Inferior.jsp" %>