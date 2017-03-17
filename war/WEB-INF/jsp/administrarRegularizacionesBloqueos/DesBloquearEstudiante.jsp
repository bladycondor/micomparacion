<%@ include file="../Superior.jsp" %>
<div class="titulo">Regularizar Bloqueos/Desbloqueos Estudiante</div>
<br>
<form name="fvolver" action="listarBloqueosEstudiante.fautapo" method="post">
  <div> <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a> </div>
   <input type="hidden" name="id_estudiante" value="<c:out value="${datosRegularizacion.id_estudiante}"/>" >
</form>
<form name="formulario" method="POST" action="registrarBloquearEstudiante.fautapo">
<table class="formulario">
  <tr>
    <th colspan="2">Introduzca Razones Desbloqueo</td>
  </tr>
  <tr>
    <td class="etiqueta">Gesti&oacute;n de Bloqueo::</td>
    <td><c:out value="${datosRegularizacion.gestion}"/> </td>
  </tr>
  <tr>
    <td class="etiqueta">Periodo de Bloqueo::</td>
    <td><c:out value="${datosRegularizacion.periodo}"/></td>
  </tr>
  <tr>
    <td class="etiqueta">R.U. ::</td>
    <td><c:out value="${datosRegularizacion.id_estudiante}"/></td>
  </tr>
  <tr>
    <td class="etiqueta">Nombres ::</td>
    <td><c:out value="${datosEstudiante.nombres}"/>&nbsp;<c:out value="${datosEstudiante.paterno}"/>&nbsp;<c:out value="${datosEstudiante.materno}"/></td>
  </tr>
  <tr>
    <td class="etiqueta">Estado ::</td>
    <td><c:out value="${datosEstudiante.estado}"/></td>
  </tr>
  <tr>
    <td class="etiqueta">Motivo de Bloqueo ::</td>
    <td><c:out value="${datosRegularizacion.tipo_regularizacion}"/>
    </td>
  </tr>
  <tr>
    <td class="etiqueta">Observaci&oacute;n del Bloqueo ::</td>
    <td><c:out value="${datosRegularizacion.observacion}" escapeXml="false"/>
    </td>
  </tr>
  <tr>
    <td class="etiqueta">Observaciones<font color='red'>(*)</font> ::</td>
    <td><textarea name="observacion" cols="20" rows="3"></textarea>
    </td>
  </tr>
  <tr>
    <td align="center" colspan="2">
       <input class="desbloquear" type="submit" value="Desbloquear">
       <input type="hidden" name="id_regularizacion" value="<c:out value="${datosRegularizacion.id_regularizacion}"/>" >
       <input type="hidden" name="id_estudiante" value="<c:out value="${datosRegularizacion.id_estudiante}"/>" >
       <input type="hidden" name="accion" value="Desbloquear" >
    </td>
  </tr>  
</table>
</form>

<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>
<%@ include file="../Inferior.jsp" %>