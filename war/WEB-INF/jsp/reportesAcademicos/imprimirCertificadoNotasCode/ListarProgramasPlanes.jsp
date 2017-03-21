<%@ include file="../../Superior.jsp" %>

<div class="titulo">Imprimir Certificado de Notas</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>

<body onLoad='iniciar()'>
<form name='forma' method="post" action="listarCertificadoNotas.fautapo">

<table class="formulario">
  <tr>
    <th colspan="3">DATOS</th>
  </tr>
  <tr>
    <td class="etiqueta">Gesti&oacute;n</td>
    <td class="etiqueta">::</td>
    <td><c:out value="${gestion}"/></td>
  </tr>
  <tr>
    <td class="etiqueta">Periodo</td>
    <td class="etiqueta">::</td>
    <td><c:out value="${periodo}"/></td>
  </tr>
  <tr>
    <td class="etiqueta">Programa</td>
    <td class="etiqueta">::</td>
    <td><c:out value="${estudiante.programa}"/></td>
  </tr>
  <tr>
    <td class="etiqueta">Estudiante</td>
    <td class="etiqueta">::</td>
    <td><c:out value="${estudiante.nombres}"/> <c:out value="${estudiante.paterno}"/> <c:out value="${estudiante.materno}"/></td>
  </tr>
  
  <tr>
    <td class="etiqueta">Imprimir <font color='red'>(*)</font> </td>
    <td class="etiqueta">::</td>
    <td>
       Curso de Verano<input type="radio" name="todas" value="No" checked> &nbsp; 
       Regular/Mesa de Examen<input type="radio" name="todas" value="Si">
    </td>
  </tr>
  
 
 <input type="hidden" name="gestion"              value='<c:out value="${gestion}"/>' >
 <input type="hidden" name="periodo"              value='<c:out value="${periodo}"/>' >
 <input type="hidden" name="id_estudiante"        value='<c:out value="${estudiante.id_estudiante}"/>' >
 <input type="hidden" name="id_programa"          value='<c:out value="${estudiante.id_programa}"/>' >
 <input type="hidden" name="nrocertificado"       value='<c:out value="${nrocertificado}"/>' >
 <input type="hidden" name="todas"                value='<c:out value="${todas}"/>'> 
 <input type="hidden" name="nombres"              value='<c:out value="${estudiante.nombres}"/>'> 
  
  
</table>
<center>
  <input type='button' value='Siguiente' class="siguiente" onClick='obligar(document.forma, "<c:out value='${obligatorios}'/>")'>
</center>
<c:if test="${cliente.id_facultad > 0}"><input type="hidden" name="id_facultad" value='<c:out value="${cliente.id_facultad}"/>'></c:if>
<c:if test="${cliente.id_programa > 0}"><input type="hidden" name="id_programa" value='<c:out value="${cliente.id_programa}"/>'></c:if>
<c:if test="${cliente.id_departamento > 0}"><input type="hidden" name="id_departamento" value='<c:out value="${cliente.id_departamento}"/>'></c:if>
</form>

</body>
<%@ include file="../../Inferior.jsp" %>