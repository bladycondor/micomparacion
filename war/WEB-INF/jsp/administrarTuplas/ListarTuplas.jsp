<%@ include file="../Superior.jsp"%>

<div class="titulo"> Administraci&oacute;n de tuplas</div>
<br>
<table class="tabla">
 <tr>
  <th>Dominio</th>
  <td class="colb"><c:out value="${datosDominio.dominio}"/>
 </tr>
</table>
<br>

<table>
  <tr>
  <form name="forma" method="post" action='<c:url value="/nuevaTupla.fautapo"/>'>
    <td><div><a class="volver" href="<c:url value="/listarDominios.fautapo?"></c:url>">Volver</a></div></td>
    <td><div class="agregar"><a href="javascript:document.forma.submit();" >Nuevo</a></div></td>
       <input type="hidden" name="id_dominio" value='<c:out value="${id_dominio}" />'>
       <input type="hidden" name="accion"     value='Adicionar'>
    </td>
  </form>
  <tr>
</table>
 
<table class="tabla">
  <tr>
    <th> Nro</th>
    <th> TUPLA</th>
    <th> PADRE</th>
    <th> OBLIGATORIO</th>
    <th> MODIFICAR</th>
    <th> ELIMINAR</th>
  </tr>
  <c:forEach var="lista1" items="${lTuplas}" varStatus="contador">
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ -->
      <td align=center><c:out value="${contador.count}"/></td>
      <td><c:out value="${lista1.tupla}"/></td>
      <td><c:out value="${lista1.tupla_padre}"/></td>
      <td align="center"><c:if test="${lista1.obligatorio == 'true'}"> SI </c:if>
	                 <c:if test="${lista1.obligatorio == 'false'}"> NO </c:if></td>
     <form name=formamodificar<c:out value="${contador.count}"/> method="post" action='<c:url value="/nuevaTupla.fautapo"/>'>
      <td>
        <div class="modificar"><a href="javascript:document.formamodificar<c:out value="${contador.count}"/>.submit();">Modificar</a></div>
	<input type="hidden" name="id_tupla"   value=<c:out value="${lista1.id_tupla}"/> >
	<input type="hidden" name="id_dominio" value=<c:out value="${lista1.id_dominio}"/> >
	<input type="hidden" name="accion"     value='Modificar' >
      </td>
     </form>
     <form name=formaeliminar<c:out value="${contador.count}"/> method="post" action='<c:url value="/confirmarTupla.fautapo"/>'>
       <td>     
       <div class="eliminar"><a href="javascript:document.formaeliminar<c:out value="${contador.count}"/>.submit();"> Eliminar</a></div>
       <input type="hidden" name="id_tupla"   value=<c:out value="${lista1.id_tupla}"/> >
       <input type="hidden" name="id_dominio" value=<c:out value="${lista1.id_dominio}"/> >
       <input type="hidden" name="accion"     value='Eliminar' >
     </td>
     </form>
   <tr>	   
   </c:forEach>
</table>

<%@ include file="../Inferior.jsp"%>