<%@ include file="../Superior.jsp"%>

<c:if test="${accion == 'Modificar'}">
  <div class="titulo"> Modificando Tuplas</div>
</c:if>
<c:if test="${accion == 'Adicionar'}">
  <div class="titulo"> Agregando Tuplas</div>
</c:if>
<c:if test="${accion == 'Eliminar'}">
  <div class="titulo"> Eliminando Tuplas</div>
</c:if>

<div><a class="volver" href='listarTuplas.fautapo?id_dominio=<c:out value="${id_dominio}"/>'>Volver</a></div>
<br>

<form name="forma" method="POST" action='<c:url value="/registrarTupla.fautapo"/>' >
  <table class="formulario">
    <tr>
      <th colspan="3">INTRODUZCA LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta">Padre</td>
      <td class="etiqueta">::</td>
      <td> <c:out value="${datosTuplaPadre.tupla}"/></td>
    </tr>
    <tr>
        <td class="etiqueta">Tupla</td>
        <td class="etiqueta">::</td>
        <td><c:out value="${datosTupla.tupla}"/> </td>
    </tr>
    <tr>
      <td class="etiqueta">Obligatorio</td>
      <td class="etiqueta">::</td>
      <td> <c:if test="${datosTupla.obligatorio == 'true'}"> Si </c:if>
           <c:if test="${datosTupla.obligatorio == 'false' || empty datosTupla.obligatorio}"> No </c:if>
    </tr>
  </table>
  <center>
     <input type="submit" class="aceptar" name='accion1' value='Aceptar'>
  </center>
  <input type="hidden" name='id_tupla'       value='<c:out value="${id_tupla}"/>'>
  <input type="hidden" name='id_dominio'     value='<c:out value="${id_dominio}"/>'>
  <input type="hidden" name='tupla'          value='<c:out value="${datosTupla.tupla}"/>'>
  <input type="hidden" name='obligatorio'    value='<c:out value="${datosTupla.obligatorio}"/>'>
  <input type="hidden" name='id_tupla_padre' value='<c:out value="${datosTuplaPadre.id_tupla}"/>'>
  <input type="hidden" name='accion'         value='<c:out value="${accion}"/>'>
</form>

<%@ include file="../Inferior.jsp" %>