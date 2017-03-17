<%@ include file="../../Superior.jsp" %>

<div class="titulo">Buscar Estudiantes</div>
<br>

<form name=forma method="POST" action='<c:url value="/listarEstudianteAuxiliar.fautapo"/>'>
<input type="hidden" name="gestion" value="<c:out value='${gestion}'/>">
<input type="hidden" name="periodo" value="<c:out value='${periodo}'/>">
<table class="formulario">
  <tr>
    <td>
    <table width="100%">
      <tr>
        <td>
        <fieldset>
          <legend>Introduzca Registro Universitario</legend>
	    <table align=right>
    	      <tr>
                <td class="etiqueta">RU</td>
	        <td class="etiqueta">::</td>
                <td><input type=text name="id_estudiante" maxlength=8/></td>
      	        <td><input type=submit name="buscar" value='Buscar' class="buscar"></td>
              </tr>
            </table>
         </fieldset>
         </td>
        </tr>
    </table>
    </td>
  </tr>

</table>
</form>

<%@ include file="../../Inferior.jsp" %>
