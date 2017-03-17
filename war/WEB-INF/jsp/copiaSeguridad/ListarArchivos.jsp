<%@ include file="../Superior.jsp" %>

<div class="titulo">Copias de Seguridad</div>

<form name="forma" id="forma" method='post' action="confirmarRestauro.fautapo">
  <input type="hidden" name="sistema" value="<c:url value='/'/>" >
  <input type="hidden" name="archivo" value="" />
<br/>
<table class="tabla">
<tr>
  <th>Fecha y hora</th>
  <th>Descripci&oacute;n</th>
  <th>RESTAURAR</th>
</tr>
<c:forEach var="lista" items="${listaArchivos}" varStatus="contador">
  <!-- ********** Esto es para el efecto ************ -->
  <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  <!-- ********** Fin  efecto ************ -->
    <td><c:out value='${listaArchivos[contador.index][1]}'/></td>
    <td><c:out value='${listaArchivos[contador.index][2]}'/></td>
    <td><div class="modificar">
          <a href="javascript: document.forma.submit()" OnClick="javascript: document.forma.archivo.value='<c:out value="${listaArchivos[contador.index][0]}"/>'">Restaurar</a>
        </div>
    </td>
  </tr>
</c:forEach>
</table>
</form>
<br/>
<form name="upload" method="post" enctype="multipart/form-data" action="restaurarUpLoad.fautapo">
  Desde archivo :: <input type="file" id="copia" name="copia">
  <input type="button" value="Restaurar" class="modificar" onClick="confirmar(document.upload);">
</form>

<script language="JavaScript">
  function confirmar(formilla) {
    var copia = document.getElementById('copia');
    if (copia.value == "") {
      alert("Por favor, indique la carpeta y el nombre del archivo");
      copia.focus();
    } else if (0 > copia.value.indexOf("tar.bz2")) {
      alert("El archivo debe estar comprimido con los formatos TAR y BZIP2");
      copia.focus();
    } else {
      var estado = window.confirm("Esta a punto que restaurar una Base de Datos con el archivo seleccionado");
      if (estado) {
        formilla.submit();
      }
    }
    return false;
  }
</script>

<%@ include file="../Inferior.jsp" %>