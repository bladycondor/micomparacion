<%@ include file="../Superior.jsp" %>

<div class="titulo">Impresi&oacute;n de Compromisos</div>
<br>

<form name="forma" action="listarCompromisos.fautapo" method="POST">
  <table class="formulario">
  <tr>
    <th colspan="3" align="center">INTRODUZCA LOS DATOS</th>
  </tr>
  <tr>
    <td class="etiqueta" align="right">RU <font color='red'>(*)</font> </td>
    <td class="etiqueta">::</td>
    <td><input type="text" name="id_estudiante" onblur='validar(this,"9")'></td>
  </tr>
  <tr>
    <td colspan="3" align="center"><input class="siguiente" type="submit" value="Buscar"></td>
  </tr>
  </table>
</form>

<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>

<%@ include file="../Inferior.jsp" %>