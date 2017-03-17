<%@ include file="../../Superior.jsp" %>
<script language='JavaScript' SRC="../js/ajax.js"></script>
<div class="titulo">Buscar Postulantes P.S.A.</div>
<br>

<form name=forma method="POST" action='<c:url value="/buscarPostulantepsa.fautapo"/>'>
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
      <td class="etiqueta">Gesti&oacute;n</td>
      <td class="etiqueta">::</td>
      <td><input type="text" name="gestion" value='<c:out value="${gestion}"/>' size="4" onblur='validar(this,"9")'> </td>
    
  
    <td class="etiqueta">Periodo</td>
    <td class="etiqueta">::</td>
    <td><input type="text" name="periodo" value='<c:out value="${cliente.periodo}"/>' size="4" onblur='validar(this,"9")'> </td>
  </tr>
    	      
            </table>
         </fieldset>
         </td>
        </tr>
      <tr>
        <td>
        <fieldset>
          <legend>Introduzca C&eacute;dula de Identidad</legend>
          <table align=right>
            <tr>
              <td class="etiqueta">CI</td>
              <td class="etiqueta">::</td>	
              <td><input type=text name="ci" maxlength=35 onblur='validar(this,"A9")'></td>
    	      <td><input type=submit name="buscar" value='Buscar' class="buscar"></td>
			  <input type="hidden" name="gestion"     value="<c:out value='${gestion}'/>">
      <input type="hidden" name="periodo"     value="<c:out value='${periodo}'/>">	
            </tr>
          </table>
        </fieldset>
        </td>
      </tr>
      <tr>
       
      </tr>
    </table>
    </td>
  </tr>
</table>
</form>

<%@ include file="../../Inferior.jsp" %>
