<%@ include file="../Superior.jsp"%>

<div class="titulo"> Anular Procesos/Tr&aacute;mites</div>
<br>

<form name="forma" action="<c:url value="/anularTramite.fautapo"/>" method="POST" >
  <table class="formulario">
    <tr>
      <th> ANULAR </th>
    </tr>
    <tr>
      <td align="center"> &iquest;Confirma anular el proceso de negocio? </td>
    </tr>
    <tr>
      <td align="center"><c:out value="${datosTramite.correlativo2}"/>/<c:out value="${datosTramite.gestion}"/></td>
    </tr>
    <tr>
      <td align="center">
        <input type="submit" name="boton" class="aceptar" value="Si" >
        <input type="submit" name="boton" class="cancelar" value="No" onclick="document.forma.action='<c:url value="/listarTramitesAnulados.fautapo">
          <c:param name="id_tramite" value="${id_tramite}"/>
        </c:url>'">
      </td>
      <input type="hidden" name="id_tramite" value="<c:out value='${id_tramite}'/>" >
    </tr>
  </table>
</form>

<%@ include file="../Inferior.jsp" %>