<%@ include file="../Superior.jsp"%>
<script language='JavaScript' SRC="../ajax.js"></script>
<div class="titulo">Cerrar Libreta Por Materia</div>
<br>
<center>
  <blink>
    <div class='cuadroAviso' >
      <div class="titulo">Aviso</div>
        <c:out value="${mensaje}"/>
      </div>
  </blink>
  <br/>
  <form method='post' action="<c:url value='/cerrarLibretaMateria/entrada.fautapo'/>">
    <input type="hidden" name="aplicacion" value="/" >
    <input type='submit' value='Aceptar' class="aceptar">
  </form>
</center>

<%@ include file="../Inferior.jsp"%>