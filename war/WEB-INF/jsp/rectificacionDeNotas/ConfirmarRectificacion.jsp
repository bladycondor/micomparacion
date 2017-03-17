<%@ include file="../Superior.jsp" %>

<div class="titulo">Rectificaci&oacute;n de Notas</div>
<form name="fvolver" method="POST" action="listarNotas.fautapo">
  <input type="hidden" name="gestion"       value="<c:out value="${gestion}"/>">
  <input type="hidden" name="periodo"       value="<c:out value="${periodo}"/>">
  <input type="hidden" name="id_estudiante" value="<c:out value="${datosEstudiante.id_estudiante}"/>">
  <div class="volver"><a href='javascript: document.fvolver.submit();' > Volver </a></div>
</form>

<table class="tabla">
  <tr>
    <th>RU</th>
    <td class="colb"><c:out value="${datosEstudiante.id_estudiante}"/></td>
    <th>Estudiante</th>
    <td class="colb">
      <c:out value="${datosEstudiante.nombres}"/> &nbsp; 
      <c:out value="${datosEstudiante.paterno}"/> &nbsp;
      <c:out value="${datosEstudiante.materno}"/>
    </td>
  </tr>
  <tr>
    <th>Programa</th>
    <td class="colb"><c:out value="${datosEstudiante.programa}"/>
    <th>Plan</th>
    <td class="colb"><c:out value="${datosEstudiante.id_plan}"/>
  </tr>
</table>
<br>

<form name="forma" method="POST" action="registrarRectificacion.fautapo" >
  <table class="formulario">
    <tr>
      <th colspan="3">INTRODUZCA LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta"> Gesti&oacute;n </td>
      <td class="etiqueta">::
      <td><c:out value="${gestion}"/></td>
    </tr>        
    <tr>
      <td class="etiqueta"> Periodo </td>
      <td class="etiqueta" >::
      <td><c:out value="${periodo}"/></td>
    </tr>  
    <tr>
      <td class="etiqueta"> Tipo de Evaluaci&oacute;n </td>
      <td class="etiqueta">::
      <td><c:out value="${datosTipoEvaluacion.tipo_evaluacion}"/></td>
    </tr>        
    <tr>
      <td class="etiqueta"> Sigla </td>
      <td class="etiqueta" >::
      <td><c:out value="${datosMateria.sigla}"/></td>
    </tr>  
    <tr>
      <td class="etiqueta"> Materia </td>
      <td class="etiqueta">::
      <td><c:out value="${datosMateria.materia}"/></td>
    </tr>        
    <tr>
      <td class="etiqueta">Nota Actual</td>
      <td class="etiqueta">::</td>	      
      <td><c:out value="${datosNota.nota}"/></td>
    </tr>
    <tr>
      <td class="etiqueta">NUEVA NOTA <font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>	      
      <td><input type="text" name="nota" value='<c:out value="${datosNota.nota}"/>' ></td>
    </tr>
    <tr>
      <td class="etiqueta">N&Uacute;MERO DE RESOLUCI&Oacute;N <font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>
      <td><input type="text" name="nro_resolucion" <c:if test="${datosNota.rectificado==true}"> value='<c:out value="${datosNota.observacion}"/>' </c:if>></td>
    </tr>
  </table>
  <center>
    <input type=submit name='accion1' class="aceptar" value='Aceptar'>
  </center>  
  <input type="hidden" name="gestion"            value="<c:out value="${gestion}"/>">
  <input type="hidden" name="periodo"            value="<c:out value="${periodo}"/>">
  <input type="hidden" name="id_nota"            value="<c:out value="${id_nota}"/>">
  <input type="hidden" name="id_matricula"       value="<c:out value="${id_matricula}"/>">
  <input type="hidden" name="id_grupo"           value="<c:out value="${id_grupo}"/>">
  <input type="hidden" name="id_materia"         value="<c:out value="${datosMateria.id_materia}"/>">
  <input type="hidden" name="id_tipo_evaluacion" value="<c:out value="${datosTipoEvaluacion.id_tipo_evaluacion}"/>">
  <input type="hidden" name="id_estudiante"      value="<c:out value="${datosEstudiante.id_estudiante}"/>">
</form>
<%@ include file="../Inferior.jsp" %>