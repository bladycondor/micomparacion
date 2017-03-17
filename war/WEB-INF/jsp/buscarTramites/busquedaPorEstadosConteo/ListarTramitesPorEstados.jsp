<%@ include file="../../Superior.jsp" %>

<div class="titulo"> Conteo procesos de negocio por fecha y estado</div>
<div><a class="volver" href="<c:url value="/buscarTramiteFecha.fautapo?"></c:url> ">Volver</a></div>

<table class="tabla">
  <tr>
    <th>Periodo de</th>
    <td class="colb"><c:out value="${fecha_ini}"/>
    <th>al</th>
    <td class="colb"><c:out value="${fecha_fin}"/>
  </tr>
</table>

<br>
<table class="tabla">
  <tr>
    <th colspan="3">DATOS ENCONTRADOS</th>	       
  <tr>
    <th>PROCESO</th>
    <th>ESTADO</th>
    <th>CANTIDAD</th>
  </tr>
  <c:forEach var="lista" items="${lProcesos}" varStatus="contador">
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ -->

     <td><c:out value="${lista.proceso}"/></td>
     <td><img width='25' height='25' src='./imagenes/procesos/<c:out value="${lista.imagen}"/>' border="0">
         <b><c:out value="${lista.estado}"/></b>
     </td>
     <td><c:out value="${lista.cantidad}"/></td>
  </tr>
  </c:forEach>
</table>

<%@ include file="../../Inferior.jsp" %>
