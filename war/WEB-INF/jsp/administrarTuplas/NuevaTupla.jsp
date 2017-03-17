<%@ include file="../Superior.jsp"%>

<c:if test="${accion == 'Modificar'}">
  <div class="titulo"> Modificando Tuplas</div>
</c:if>
<c:if test="${accion == 'Adicionar'}">
  <div class="titulo"> Agregando Tuplas</div>
</c:if>

<div><a class="volver" href='listarTuplas.fautapo?id_dominio=<c:out value="${id_dominio}"/>'>Volver</a></div>
<br>

<form name="forma" method="POST" action='<c:url value="/confirmarTupla.fautapo"/>'>
  <table class="formulario">
    <tr>
      <th colspan="3">INTRODUZCA LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta">Padre</td>
      <td class="etiqueta">::</td>
      <td>
         <select name="id_tupla_padre">
	   <option value="0">--Seleccione--</option>
           <c:forEach var="lista" items="${lTuplas}" >
             <option value="<c:out value="${lista.id_tupla}"/>"<c:if test="${lista.id_tupla == datosTupla.id_tupla_padre}">selected</c:if>>
	     <c:out value="${lista.tupla}"/>-<c:out value="${lista.tupla_padre}"/>
           </c:forEach>
	 </select>
      </td>  
    </tr>
    <tr>
      <td class="etiqueta">Tupla <font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>
      <td><textarea name=tupla rows=2 cols=50><c:out value="${datosTupla.tupla}"/></textarea>
    </tr>
    <tr>
      <td class="etiqueta">Obligatorio <font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>
      <td>Si<input type="radio" name="obligatorio" value='si'<c:if test="${obligatorio == 'true'}"> checked </c:if> > &nbsp; 
          No<input type="radio" name="obligatorio" value='no' <c:if test="${obligatorio == 'false' || empty datosTupla.obligatorio}"> checked </c:if>></td>
    </tr>
  </table>
  <center>
    <input type="submit" class="siguiente" value='Siguiente' onclick="document.forma.accion1.value='Guardar'">
  </center>
    <input type="hidden" name='accion1'    value=''>
    <input type="hidden" name='accion'     value='<c:out value="${accion}"/>'>
    <input type="hidden" name='id_tupla'   value='<c:out value="${id_tupla}"/>'>
    <input type="hidden" name='id_dominio' value='<c:out value="${id_dominio}"/>'>
</form>
<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>

<%@ include file="../Inferior.jsp"%>