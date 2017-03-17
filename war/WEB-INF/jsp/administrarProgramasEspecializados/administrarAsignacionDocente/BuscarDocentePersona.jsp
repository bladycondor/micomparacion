<%@ include file="../../Superior.jsp" %>

<div class="titulo">Buscar Docente Persona</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<br>

<form name=forma method="POST" action='<c:url value="/docentes/listarDocentesPersonas.fautapo"/>' >
<table class="formulario">
  <tr>
    <td>
    <table width=100%>
      <tr>
        <td>
        <fieldset>
          <legend>Introduzca los Datos</legend>
	    <table align=right>
    	      <tr>
                <td class="etiqueta">DIP</td>
	        <td class="etiqueta">::</td>
                <td><input type=text name="dip" maxlength=8/></td>
      	        <td><input type=submit name="buscar" value='Buscar' class="buscar"></td>
              </tr>
            </table>
         </fieldset>
         </td>
        </tr>
	<tr>
        <td>
        <fieldset>
          <legend>Introduzca nombres</legend>
	    <table align=right>
    	      <tr>
                <td class="etiqueta">Nombres</td>
	        <td class="etiqueta">::</td>
                <td><input type=text name="nombre" maxlength=8/></td>
      	        <td><input type=submit name="buscar" value='Buscar' class="buscar"></td>
              </tr>
            </table>
         </fieldset>
         </td>
        </tr>
      </table>
    </td>
  </tr>
  <input type="hidden" name="gestion" value="<c:out value='${gestion}'/>">
  <input type="hidden" name="periodo" value="<c:out value='${periodo}'/>">
  <input type="hidden" name="id_proceso" value="<c:out value='${id_proceso}'/>">
  <input type="hidden" name="id_tramite" value="<c:out value='${id_tramite}'/>">
  
  
</table>
</form>

<%@ include file="../../Inferior.jsp" %>
