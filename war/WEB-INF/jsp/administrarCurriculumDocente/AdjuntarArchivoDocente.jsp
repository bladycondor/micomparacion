<%@ include file="../Superior.jsp" %>

<div class="titulo">Adjuntando Archivos Docente</div>
<br>
<form action="cvListarCurriculum.fautapo" method=post name="fvolver">
  <input type = "hidden" name = "id_persona" value = "<c:out value = "${id_persona}"/>">
  <input type = "hidden" name = "bandera" value="<c:out value = "${bandera}"/>">
 <div> <a class="volver" href="javascript: document.fvolver.submit();">Volver</a></div>
</form>
<center>
<form name="forma" method="POST" enctype='multipart/form-data'>
  <table class="formulario">
    <tr>
      <th>Direcci&oacute;n</th>
      <th>::</th>
      <td><input type=file name="fichero"></td>
    </tr>
    <tr>
  </table>
  <input type="submit" class="adjuntar" value="Adjuntar" onclick="document.forma.action='<c:url value="/docente/adjuntarArchivoDocente1.fautapo">
      <c:param name="id_docente"          value="${id_docente}"/>
      <c:param name="id_persona"          value="${id_persona}"/>
      <c:param name="aplicacion"          value="${aplicacion}"/>
     </c:url>'">
</form>
</center>
<%@ include file="../Inferior.jsp" %>