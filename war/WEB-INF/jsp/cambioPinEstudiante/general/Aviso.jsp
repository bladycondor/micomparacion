<%@ include file="../../Superior.jsp" %>

<div class="titulo">Cambio Clave (PIN) Estudiante - Autoridad </div>
<br>

<table class="formulario">
  <tr>
    <th> Aviso!</th>
  </tr>
  <tr>    
    <td align="center"><c:out value="${mensaje}" escapeXml="False" /></td>
  </tr>
  <tr>    
    <td align="center">
      <form name="fvolver" action="comprobarEntrada.fautapo" method="post">
        <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a>
	<input type="hidden" name="id_estudiante" value="<c:out value='${id_estudiante}'/>" >
      </form>
    </td>
  </tr>
  
</table>
</form>

<%@ include file="../../Inferior.jsp" %>
