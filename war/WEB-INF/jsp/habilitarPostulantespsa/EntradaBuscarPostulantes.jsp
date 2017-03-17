<%@ include file="../Superior.jsp"%>
<div class="titulo">Administrar Estudiante Nuevo</div>
<br>
<form action="<c:url value='/buscarPstpsa/listarPostulantes.fautapo'/>" method="post">
<table class="tabla">
  <tr>
    <th> GESTION </th>
    <th>PERIODO </th>
  </tr>
  <tr>
    <td><c:out value="${gestion}"/>
    <input type="hidden" name="gestion" value="<c:out value="${gestion}"/>"/></td>
    <td><c:out value="${periodo}"/>
    <input type="hidden" name="periodo" value="<c:out value="${periodo}"/>" /></td>
  </tr>
  
</table>
<table class="formulario">
  <tr>
    <th colspan="3"> BUSCAR POSTULANTE
    </th>
  </tr>     
  <tr>
    <td colspan="3">
    <fieldset>
      <legend>Introduzca C&eacute;dula de Identidad</legend>
    <table align=right>
      <tr>
        <td class="etiqueta">DIP</td>
        <td class="etiqueta">::</td>
        <td><input type=text name="dip" maxlength="8"/></td>
        <td><input type=submit name="botonDip" value='Buscar' class="buscar"></td>
      </tr>
    </table>
    </fieldset>
    </td>
  </tr>
  <tr>
    <td colspan="3">
    <fieldset>
      <legend>Introduzca nombres</legend>
      <table align=right>
      <tr>
        <td class="etiqueta">Nombres</td>
        <td class="etiqueta">::</td>
        <td><input type=text name="nombre" maxlength="8"/></td>
        <td><input type=submit name="botonNombre" value='Buscar' class="buscar"></td>
      </tr>
      </table>
     </fieldset>
    </td>
  </tr>
</table>
</form>

<%@ include file="../Inferior.jsp" %>