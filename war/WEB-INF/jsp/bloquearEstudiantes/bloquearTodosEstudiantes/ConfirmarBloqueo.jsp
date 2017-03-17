<%@ include file="../../Superior.jsp" %>
<script language='JavaScript' SRC="../js/ajax.js"></script>

<div class="titulo">Bloquear Estudiantes</div>
<form name="fvolver" method="POST" action='<c:url value="/bloquearEstudiantesTodos/entrada.fautapo"/>' >
  <div class="volver"><a href='javascript: document.fvolver.submit();' > Volver </a></div>
</form>
<table class="formulario">
    <tr>
      <th colspan="2">CONFIRME LA ACCION</th>
    </tr>
    <tr>
      <td colspan="2"> Esta seguro de Bloquear a los estudiantes Activos?
      </td>
    </tr>
    <tr>
      <td align="right">
      <form name="forma" action="<c:url value="/bloquearEstudiantesTodos/bloquear.fautapo"/>" method="POST">
        <input type="submit" value='Aceptar' class="aceptar"> 
        <input type="hidden" name="accion" value='Bloquear'> 
      </form>
      </td>
      <td align="left">
        <form name="fcancelar" method="POST" action='<c:url value="/bloquearEstudiantesTodos/entrada.fautapo"/>' >
         <input type="submit" value='Cancelar' class="cancelar" >
        </form>    
      </td> 
    </tr>
</table>
<%@ include file="../../Inferior.jsp" %>