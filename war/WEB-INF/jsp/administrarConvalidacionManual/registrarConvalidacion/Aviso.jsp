<%@ include file="../../Superior.jsp"%>
<div class="titulo">Convalidaci&oacute;n Manual</div>
<br>
<center>
<table class='cuadroAviso'>
  <tr>
    <td align="center">  
      <div class="titulo">¡Aviso!</div>
    </td>
  </tr>
  <tr>    
    <td aling="center">  
      <c:out value="${mensaje}"/>
    </td>
  </tr>
  <tr>    
    <td align="center">
      <form name="fvolver" action="<c:url value='/convalidacionManual/comprobarEntrada.fautapo'/>" method="post">
        <input type="hidden" name="id_programa"    value="<c:out value='${datosPrograma.id_programa}'/>">
	<input type="hidden" name="id_universidad" value="<c:out value='${id_universidad}'/>">
	<input type="hidden" name="gestion"       value="<c:out value='${gestion}'/>">
	<input type="hidden" name="periodo"       value="<c:out value='${periodo}'/>">
      <div> <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a> </div>
    </form>
    </td>
  </tr>
</table>        
</center>

<%@ include file="../../Inferior.jsp"%>