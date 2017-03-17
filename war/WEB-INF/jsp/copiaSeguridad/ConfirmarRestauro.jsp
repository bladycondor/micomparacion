<%@ include file="../Superior.jsp" %>

<div class="titulo">Sacando Copia de Seguridad</div>

<form name='forma' method='post' action="restaurar.fautapo">
  <input type="hidden" name="sistema" value="<c:url value='/'/>" >
  <input type="hidden" name="archivo" value="<c:out value='${archivo}'/>" />
<br/>
<table class="formulario">
  <tr>
    <th colspan="3" align="center">CONFIRME LA RESTAURACI&Oacute;N</th>
  </tr>
  <tr>
    <td class="etiqueta" align="left">
      Est&aacute; a punto de restaurar la copia de:<br/>
      &nbsp;&nbsp;&nbsp;fecha :: <c:out value='${fecha}'/><br/>
      &nbsp;&nbsp;&nbsp;hora :: <c:out value='${hora}'/>
    </td>
  </tr>
  <tr>
    <td>
      Es recomendable que advierta a TODOS los clientes<br>
      para que suspendan las operaciones que est&eacute;n realizando.<br/>
      Esto tomar&aacute; tiempo.
    </td>
  </tr>
</table>
<center>
  <input type='submit' value='Aceptar' class="aceptar" >
</center>

<div class="nota">La restauraci&oacute;n de la copia de seguridad se realizar&aacute; en el momento que confirme la acci&oacute;n</div>
</form>

<%@ include file="../Inferior.jsp" %>