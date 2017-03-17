<%@ include file="../Superior.jsp" %>

<div class=titulo>  Reportes por proceso </div>
<table>
  <tr>
    <td><div><a class="volver" href="<c:url value="/listarCamposReporte.fautapo"/>?id_proceso=<c:out value="${id_proceso}"/>">Volver</a></div></td>
  </tr>
</table>

<c:if test="${!empty lDatos}">

  <table class="tabla">
    <c:forEach var="total" items="${lDatos}" varStatus="contador">
      <!-- ********** Esto es para el efecto ************ -->
        <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
      <!-- ********** Fin  efecto ************ -->
      <c:forEach var="var" items="${total.lista}">
        <c:if test="${contador.count == 1}">
          <th><c:out value="${var.valor}"/></th>
        </c:if>
        <c:if test="${contador.count != 1}">
          <td>
            <c:if test="${contador.last and existe_sumita == '1'}">
              <font color="red">
            </c:if>
            <c:out value="${var.valor}" escapeXml="False"/>
            <c:if test="${contador.last and existe_sumita == '1'}">
              </font>
            </c:if>
          </td> 
        </c:if>
      </c:forEach>    
      </tr>
    </c:forEach>
  </table>

  <br>
  <table class="tabla">
    <tr>
      <th>Resultados encontrados</th>
      <c:if test="${existe_sumita == '1'}">
        <th><c:out value="${cantidad_order_by - 2}"/></th>
      </c:if>
      <c:if test="${existe_sumita == '0'}">
        <th><c:out value="${cantidad_order_by - 1}"/></th>
      </c:if>
    </tr>
  </table>
  <hr>
</c:if>

<c:if test="${cantidad_group_by != '0'}">
  <table class="tabla">
    <c:forEach var="total" items="${lDatosGB}" varStatus="contador">
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ -->
      <c:if test="${contador.count != 1}">
        <td><c:out value="${contador.count - 1}"/></td>
      </c:if>
      <c:forEach var="var" items="${total.lista}">
        <c:if test="${contador.count == 1}">
          <th><c:out value="${var.valor}"/></th>
        </c:if>
        <c:if test="${contador.count != 1}">
          <td>
            <c:out value="${var.valor}" escapeXml="False"/>
          </td> 
        </c:if>
      </c:forEach>    
      </tr>
    </c:forEach>
  </table>
  <br>
  <table class="tabla">
    <tr>
      <th>Resultados encontrados</th>
      <th class="etiqueta"><c:out value="${cantidad_group_by - 1}"/></th>
    </tr>
  </table>
</c:if>

<c:if test="${(id_campo_contar != '') || (id_campo_contar != null)}">
  <hr>
  <div class="titulo"><c:out value="${datosCampo.campo}"/></div>
  <br>
  <table class="tabla">
    <c:forEach var="total2" items="${lCamposContados}" varStatus="contador">
     <!-- ********** Esto es para el efecto ************ -->
       <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
     <!-- ********** Fin  efecto ************ -->
      <c:if test="${contador.count == 1}">
        <th>Nro</th>
        <c:forEach var="var" items="${total2.lista}">
          <th><c:out value="${var.valor}"/></th>
        </c:forEach>
      </c:if>
      <c:if test="${contador.count != 1}">
        <td><c:out value="${contador.count-1}"/></td>
        <c:forEach var="var" items="${total2.lista}">
          <td><c:out value="${var.valor}"/></td>
        </c:forEach>
      </c:if>
    </tr>
    </c:forEach>
  </table>
</c:if>

<%@ include file="../Inferior.jsp" %>