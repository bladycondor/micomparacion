<%@ include file="../Superior.jsp"%>

<!-- <c:if test="${accion == 'Modificar'}">
  <div class="titulo"> Modificando Usuario</div>
</c:if>
<c:if test="${accion == 'Adicionar'}">
  <div class="titulo"> Agregando Usuario</div>
</c:if>
<c:if test="${accion == 'Eliminar'}">
  <div class="titulo"> Eliminando Usuario</div>
</c:if> -->
 <div class="titulo"> Administrar Usuarios</div>
<table>
  <tr>
    <td>
     <form name="fvolver" action="<c:url value='listarUsuarios.fautapo'/>" method="post">
      <div> <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a> </div>
      <input type="hidden"  name="pagina"   value='<c:out value="${pagina}"/>'>
      <input type="hidden"  name="accion"   value='<c:out value="${accion}"/>'>
    </form>
    </form>
    </td>
  </tr>
</table>
<br>
<br>
<form name="adicionarUsr" method="POST" action='<c:url value="registrarUsuario.fautapo"/>' >
  <table class="formulario">
    <tr>
      <th colspan="3">
        <c:if test="${accion == 'Modificar'}">
          Modificando Usuario
        </c:if>
        <c:if test="${accion == 'Adicionar'}">
          Agregando Usuario
        </c:if>  
	<c:if test="${accion == 'Eliminar'}">
          Eliminando Usuario
        </c:if>
        <br>CONFIRME LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta"> Persona </td>
      <td class="etiqueta">::
      <td><c:out value="${datosUsuario.nombres}"/></td>
    </tr>        
    <tr>
      <td class="etiqueta">Apodo</td>
      <td class="etiqueta" >::
      <td><c:out value="${datosUsuario.apodo_normal}"/>
      </td>
    </tr>
    <tr>
      <td class="etiqueta">Clave</td>
      <td class="etiqueta" >::
      <td><c:out value="${datosUsuario.clave_normal}"/></td>
    </tr>
    <tr>
      <td class="etiqueta">Recordatorio</td>
      <td class="etiqueta" >::
      <td><c:out value="${datosUsuario.recordatorio}"/></td>
    </tr>
  </table>
  <center>
    <input type="submit" class="aceptar" value='Aceptar'>
  </center>
  <input type="hidden"   name="id_usuario"   value='<c:out value="${id_usuario}"/>'>        
  <input type="hidden"   name="id_persona"   value='<c:out value="${id_persona}"/>'>
  <input type="hidden"   name="clave"        value='<c:out value="${datosUsuario.clave_normal}"/>'>
  <input type="hidden"   name="apodo"        value='<c:out value="${datosUsuario.apodo_normal}"/>'>
  <input type="hidden"   name="recordatorio" value='<c:out value="${datosUsuario.recordatorio}"/>'>
  <input type="hidden"   name="accion"       value='<c:out value="${accion}"/>'>
</form>

<%@ include file="../Inferior.jsp"%>