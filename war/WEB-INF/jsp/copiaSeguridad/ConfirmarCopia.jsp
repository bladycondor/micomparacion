<%@ include file="../Superior.jsp" %>

<div class="titulo">Sacando Copia de Seguridad</div>

<form name='forma' method='post' action="<c:url value='/copiaSeguridad/copiar.fautapo'/>">
  <input type="hidden" name="sistema" value="<c:url value='/'/>" >
<br/>
<table class="formulario">
  <tr>
    <th colspan="3" align="center">CONFIRME LA COPIA</th>
  </tr>
  <tr>
    <td>
      Es recomendable que advierta a TODOS los clientes<br>
      para que suspendan las operaciones que est&eacute;n realizando.<br/><br/>
      Esto tomar&aacute; tiempo; por favor, sea paciente.
    </td>
  </tr>
  <tr>
    <td>
      Descripci&oacute;n de la copia ::<br/><input type="text" name="descripcion" size="50"></textarea>
    </td>
  </tr>
</table>
<center>
  <input type='submit' value='Aceptar' class="aceptar" >
</center>

<div class="nota">La copia de seguridad se realizar&aacute; en el momento que confirme la acci&oacute;n</div>
</form>

<%@ include file="../Inferior.jsp" %>