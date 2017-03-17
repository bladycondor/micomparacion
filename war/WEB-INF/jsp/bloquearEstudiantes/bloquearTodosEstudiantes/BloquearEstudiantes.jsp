<%@ include file="../../Superior.jsp" %>
<script language='JavaScript' SRC="../js/ajax.js"></script>

<div class="titulo">Bloquear Estudiantes</div>
<br>
<table class="formulario">
    <tr>
      <th colspan="2">Aviso</th>
    </tr>
    <tr>
      <td colspan="2"> Se ha bloqueado <b><c:out value="${resultado}"/> </b> estudiantes.
      </td>
    </tr>
</table>
<center>
  <form name="forma" action="<c:url value="/bloquearEstudiantesTodos/entrada.fautapo"/>" method="POST">
    <input type="submit" value='Aceptar' class="aceptar"> 
  </form>
</center>

<%@ include file="../../Inferior.jsp" %>