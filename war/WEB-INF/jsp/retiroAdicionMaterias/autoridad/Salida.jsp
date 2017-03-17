<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />
<div class="titulo">Retiro y adici&oacute;n de materias</div>
<br>
<center>
  <div class='cuadroAviso' >
      <div class="titulo">Aviso</div>
        <c:out value="${mensaje}"/>
  </div>
  <br>
  <form method='post' action="<c:url value='/retiroAdicionMaterias/comprobarEntrada.fautapo'/>">
      <input type="hidden" name="id_tipo_evaluacion" value="<c:out value='${id_tipo_evaluacion}'/>" >
      <input type="hidden" name="gestion" value="<c:out value='${gestion}'/>" >
      <input type="hidden" name="periodo" value="<c:out value='${periodo}'/>" >
      <input type="hidden" name="id_programa" value="<c:out value='${id_programa}'/>" >
      <input type='submit' value='Aceptar' class="aceptar">
  </form>
</center>

<%@ include file="../../Inferior.jsp" %>