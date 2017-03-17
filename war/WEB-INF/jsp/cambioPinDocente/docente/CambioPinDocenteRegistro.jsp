<%@ include file="../../Superior.jsp" %>

<div class="titulo"> Cambio clave(PIN) docente</div>
<br>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<table class="tabla">
  <tr>
    <td class="etiqueta">Docente</td>
    <td class="etiqueta">::</td>
    <td>
      <c:out value="${nombres}"/>
    </td>
  </tr>    
</table>  
<br>
<form action="<c:url value="/cambioPinDocente/registrarNuevoPin.fautapo"/>" method="POST">
  <table class="tabla">
    <tr>
      <th align=center colspan=3>INTRODUCIR NUEVA CLAVE</th>
    </tr>
    <tr>
      <td class="etiqueta" align=right>Nueva Clave <font color='red'>(*)</font></td>
      <td class="etiqueta">::</td>
      <td><input type=password name="nueva_clave" onblur='validar(nueva_clave,"A9")' maxlength=10/></td>
    </tr>
    <tr>
      <td class="etiqueta" align=right>Confirmar Nueva Clave <font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>
      <td><input type=password name="conf_nueva_clave" maxlength=10/></td>
    </tr>
    <tr>
      <td align="center" colspan="3">
        <input type="submit" value="Modificar" class="aceptar">
      </td>
    </tr>
</table>
  <div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>        
</form>

<%@ include file="../../Inferior.jsp" %>