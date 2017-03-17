<%@ include file="../../Superior.jsp" %>
<div class=titulo> Cambio Clave(PIN) </div>
<br>
<tabla>
  <form name=formavolver<c:out value="${contador.count}"/> method=post action='<c:url value="/cambioPinEstudiante/entrada.fautapo"/>'>
    <td>
      <div><a class="volver" href="javascript:document.formavolver<c:out value="${contador.count}"/>.submit();"> Volver</a></div>
      <input type="hidden" name="bandera"     value='1'>
    </td>
  </form>
</table>
<br>
<form action="<c:url value="/cambioPinEstudiante/guardarNuevoPin.fautapo"/>" method="POST">
<table>
  <tr>
    <td>
      <table class="tabla">
        <tr>
	<th>ESTUDIANTE</th>
	<th>RU</th>
	<th>CI</th>
	</tr>
	<tr>
        <td class="etiqueta"><c:out value="${datosEstudiante.nombres}"/>&nbsp;<c:out value="${datosEstudiante.paterno}"/>&nbsp;<c:out value="${datosEstudiante.materno}"/></td> 
        <td class="etiqueta"><c:out value="${datosEstudiante.id_estudiante}"/></td>
        <td class="etiqueta"><c:out value="${datosEstudiante.dip}"/></td>
	</tr>
      </table>
    <td>
  </tr>
  <tr>    
    <td>
      <table class="tabla">
        <tr>
          <th align=center colspan=3>INTRODUCIR NUEVA CLAVE</th>
        </tr>
        <tr>
          <td class="etiqueta" align=right>Nueva Clave <font color='red'>(*)</font></td>
          <td class="etiqueta">::</td>
          <td><input type=password name="clave_nueva" onblur='validar(nueva_clave,"A9")' maxlength=10/></td>
        </tr>
        <tr>
          <td class="etiqueta" align=right>Confirmar Nueva Clave <font color='red'>(*)</font> </td>
          <td class="etiqueta">::</td>
          <td><input type=password name="confirmar_clave" maxlength=10/></td>
        </tr>
        <tr>
          <td align="center" colspan="3">
            <input type="submit" value="Modificar" class="aceptar">
          </td>
       </tr>
     </table>  
    </td></tr>
    </td>
    </tr>     
  </table>     
</form>

<%@ include file="../../Inferior.jsp" %>
      

      
      
      
      
      






