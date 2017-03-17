<%@ include file="../Superior.jsp" %>

<form action='<c:url value="/cambiarRol.fautapo"/>' method="post">

<br>
<table align="center">
  <tr class="colh" align="center">
    <td>Elegir un Rol</td>
    <td>::</td>
    <td>
      <SELECT NAME="id_rol" class="textoEncabezado">
        <c:forEach var="roles" items="${cliente.roles}">
          <OPTION value="<c:out value='${roles.id_rol}' />"><c:out value="${roles.rol}"/>
        </c:forEach>
      </SELECT>
    </td>
  </tr>
  <tr align="center">
    <td colspan='3'><input type="submit" value="Ingresar" /></td>
  </tr>
</table>

</form>

<%@ include file="../Inferior.jsp" %>