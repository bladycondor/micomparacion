<%@ include file="../../Superior.jsp" %>

<div class="titulo">Cambio Clave (PIN) Estudiante - Autoridad </div>
<br>
<form name="fvolver" action="entrada.fautapo" method="post">
  <div> <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a> </div>
</form>
<form name=forma method="POST" action="listarEstudiantes.fautapo" >
<table class="formulario">
  <tr>
    <th> Introduzca los datos</th>
  </tr>
  <tr>
    <td align="center">
      <table>
        <tr>
          <td class="etiqueta"> Gesti&oacute;n Acad&eacute;mica</th>
          <td class="etiqueta">::</td>
          <td><input type=text name="gestion" maxlength="4" size="4" value="<c:out value="${gestion}"/>" onblur='validar(this,"9")'/></td>
        </tr>
        <tr>
          <td class="etiqueta"> Periodo Acad&eacute;mico</th>
          <td class="etiqueta">::</td>
          <td><input type=text name="periodo"  value="<c:out value="${periodo}"/>" maxlength="2" size="2" onblur='validar(this,"9")'/></td>
        </tr>
      </table>
    </td>  	
  <tr>
    <td  align="center">
    <table width=100%>
      <tr>
        <td  align="center">
        <fieldset>
          <legend>Introduzca Registro Universitario</legend>
	    <table align="right">
    	      <tr>
                <td class="etiqueta">RU</td>
	        <td class="etiqueta">::</td>
                <td><input type=text name="id_estudiante" maxlength="8"/></td>
      	        <td><input type=submit name="buscar" value='Buscar' class="buscar"></td>
              </tr>
            </table>
         </fieldset>
         </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td align="center">
    <table width=100%>
      <tr>
        <td  align="center">
        <fieldset>
          <legend>Introduzca C&eacute;dula de Identidad</legend>
          <table align="right">
            <tr>
              <td class="etiqueta">CI</td>
              <td class="etiqueta">::</td>	
              <td><input type=text name="ci" maxlength="35" onblur='validar(this,"A9")'></td>
    	      <td><input type=submit name="buscar" value='Buscar' class="buscar"></td>
            </tr>
          </table>
        </fieldset>
        </td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>    
    <td align="center">
    <table width=100%>
      <tr>
        <td>
        <fieldset>
          <legend>Introduzca un Nombre</legend>
          <table align="right">
            <tr>
              <td class="etiqueta">Nombres</td>
              <td class="etiqueta">::</td>	
              <td><input type=text name="nombres" maxlength="35" onblur='validar(this,"A9")'></td>
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
