<%@ include file="../Superior.jsp" %>

<div class="titulo">Mover Notas Matriculadas</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>

<body onLoad='iniciar()'>
<form name='forma' method="post" action='<c:url value="/grabarNotasMatriculadas.fautapo"/>'>
  <input type="hidden" name="gestion" value='<c:out value="${gestion}"/>' >
  <input type="hidden" name="accion" value='Aceptar' >
  <input type="hidden" name="periodo" value='<c:out value="${periodo}"/>' >

<table  class='formulario'>
  <center>
   <tr>
    <th colspan=3 align="center">CONFIRME LOS DATOS</th>
   </tr>
   <tr>
     <td colspan=3 class="etiqueta"><c:out value="${mensaje}" /></td>
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
  </center>
</table>

<center>
  <input type='button' value='Aceptar' class="aceptar" onClick='obligar(document.forma, "<c:out value='${obligatorios}'/>")'>
</center>

</form>
</body>
<%@ include file="../Inferior.jsp" %>