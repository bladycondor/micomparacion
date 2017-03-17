<%@ include file="../Superior.jsp" %>

<div class="titulo">Administrar Usuarios</div>
<table>
  <tr>
    <td>
     <form name="fvolver" action="<c:url value='listarUsuarios.fautapo'/>" method="post">
      <div> <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a> </div>
      <input type="hidden" name="pagina" value='<c:out value="${pagina}"/>'>
    </form>
    </form>
    </td>
  </tr>
</table>
<br>
<form  name="forma" action="<c:url value='confirmarUsuario.fautapo'/>" method="POST">
  <table class="formulario">
  
    <tr>
      <th colspan="3">
        <c:if test="${accion == 'Modificar'}">
          Modificando Usuario
        </c:if>
        <c:if test="${accion == 'Adicionar'}">
          Agregando Usuario
        </c:if>
      <br>SELECCIONE LA PERSONA</th>
    </tr>
    <tr>
      <td class="etiqueta"> Persona <font color = 'red'>(*)</font></td>
      <td class="etiqueta"> ::</td>
      <td>
        <input type="hidden" name="id_persona" value='<c:out value="${persona.id_persona}"/>'>
        <input type="text" name="persona" value='<c:out value="${persona.nombres}"/>' size="50" >
        <input type='button' value="" class="buscar" onClick='fcombo("listarPersonas.fautapo",forma.persona.value, "");' >
      </td>
    <tr>
    <tr>
      <td class="etiqueta"> Apodo <font color = 'red'>(*)</font></td>
      <td class="etiqueta"> ::</td>
      <td>
        <input type="text" name="apodo" size="30" onblur="validar(this,'A9')">
      </td>
    <tr>
    <tr>
      <td class="etiqueta"> Clave <font color = 'red'>(*)</font></td>
      <td class="etiqueta"> ::</td>
      <td>
        <input type="text" name="clave" size="30" onblur="validar(this,'A9')">
      </td>
    <tr>
    
    <tr>
      <td class="etiqueta"> Recordatorio</td>
      <td class="etiqueta"> ::</td>
      <td>
        <input type="text" name="recordatorio" size="30"  value="<c:out value='${persona.recordatorio}'/>">
      </td>
    </tr>
    
    <!--tr>
      <td colspan="3" align="center"><input class="aceptar" type="submit" name="boton" value="Guardar"></td>
      <input type="hidden" name="accion" value='<c:out value="${accion}"/>'>
    </tr -->
    <tr>
      <td colspan="3" align="center">
        <input type='button' name="boton" value='Guardar' class="siguiente" onClick="fguardar()">
        <input type="hidden" name="accion"      value="<c:out value='${accion}'/>">
	<input type="hidden" name="id_usuario"      value="<c:out value='${id_usuario}'/>">
      </td>
    </tr>
  </table>
</form>

<script language="JavaScript">
  function fguardar()
  {
    if((document.forma.id_persona.value!="") &&(document.forma.apodo.value!="")&&(document.forma.clave.value!=""))
    {
      document.forma.submit();
    }
    else
    {
      alert("Los campos con (*), son obligatorios");
    }
  }
  iniciar();
</script>

<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>       
<%@ include file="../Inferior.jsp" %>