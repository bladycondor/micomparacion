<%@ include file="../../../Superior.jsp" %>
</body>
<script language="JavaScript">
var resultado=new Array();
<c:forEach begin="0" end="${fn:length(lSexos)*fn:length(cabeza)-1}">resultado.push(0);</c:forEach>
function sumar(){
  var cuerpo=document.getElementById("cuerpo");
  for (var i=0;i<cuerpo.rows.length;i++)
    for (var j=0;j<resultado.length;j++){
      resultado[j]+=parseInt(cuerpo.rows[i].cells[j+2].innerHTML);
    }
  var res=document.getElementById("resultado");
  for (var j=0;j<resultado.length;j++){
    var celda=document.createElement('td');
    celda.innerHTML=resultado[j];
    celda.setAttribute('align', 'center');
    celda.setAttribute('class', 'colb');
    res.appendChild(celda);
  }
}
</script>
<body onLoad="sumar()">
<c:if test="${fn:length(datos)>0}">
<jsp:useBean id="now" class="java.util.Date" />
<table border="0" width="100%">
  <!-- SE REPITE-->
  <thead>
  <tr>
    <td width="100%" align="center" cellspancin="0" cellpading="0">
    <table width="100%">
      <tr>
        <td width="14%" align="center">
          <img src="<c:url value='${datosInstitucion.logo}'/>" border="0" ALT="logo institucion">
        </td>
        <td width="72%" align="center">
          <table width="100%" heigth="100%" cellpading="2" cellspacing="0" >
            <tr>
              <td align="center"><h1><b><c:out value='${datosInstitucion.institucion}'/></h1></td>
            <tr>
            <tr>
              <td align="center"><font size="3"><b><c:out value='${datosInstitucion.localidad}'/> - <c:out value='${datosInstitucion.departamento}'/> - <c:out value='${datosInstitucion.pais}'/></b></font></td>
            <tr>
            </tr>
              <td align="center"><c:out value='${datosInstitucion.actividad}'/></td>
            </tr>
          </table>
        </td>
        <td width="14%">
          Fecha : <a href='javascript: window.print()' style="color:black"><fmt:formatDate value="${now}" pattern="${formatoFecha}"/></a>
        </td>
      </tr>
    </table>
    <hr>
    <table width="100%" align="center">
      <tr>
        <td align="center"><label><h1><c:out value="${titulo}" escapeXml="false"/></h1></label></td>
      </tr>
    </table>
    <table border="0">
      <tr>
        <td><b>Admisi&oacute;n ::</b></td>
	<td><c:out value="${admision.tipo_admision}"/></td>
        <c:if test="${!empty programa.programa}">
          <td><b>Programa ::</b></td>
          <td><c:out value="${programa.programa}"/></td>
	</c:if>
        <td><b>Periodo ::</b></td>
        <td><c:out value="${periodo}"/> - <c:out value="${gestion}"/></td>
      </tr>
    </table>
    <br>
    </td>
  </tr>
  </thead>
  <!-- HASTA AQUI SE REPITE-->

  <tbody class="tabla"><tr>
    <!-- ************** ETIQUETAS ************ -->
    <table class="tabla">
    <thead>
      <tr>
        <th rowspan="2">Programa</th>
        <th rowspan="2">Plan</th>
        <c:forEach begin="0" end="${fn:length(cabeza) -1}" var="i">
          <th colspan="<c:out value="${fn:length(lSexos)}"/>"><c:out value="${cabeza[i][1]}"/></th>
        </c:forEach>
      </tr>
      <tr>
        <c:forEach begin="0" end="${fn:length(cabeza) -1}">
          <c:forEach var="sexo" items="${lSexos}">
            <th><c:out value="${sexo.tipo_sexo}"/></th>
          </c:forEach>
        </c:forEach>
      </tr>
    </thead>
    <!-- ************** FIN ETIQUETAS ************ -->
    <!-- **************    VALORES     ************ -->
    <c:set var="prog_ant" value=""/>
    <c:set var="plan_ant" value=""/>
    <c:set var="nro_celda" value="10000000"/>
    <tbody id="cuerpo">
      <c:forEach begin="0" end="${fn:length(datos)-1}" var="i">
        <c:if test="${prog_ant != datos[i][0] || plan_ant != datos[i][1]}">
          <c:forEach begin="${nro_celda}" end="${fn:length(lSexos)*fn:length(cabeza)-1}">
            <td align="center">0</td>
          </c:forEach>
          <c:set var="prog_ant" value="${datos[i][0]}"/>
          <c:set var="plan_ant" value="${datos[i][1]}"/>
          <c:set var="nro_celda" value="0"/>
          <tr>
            <td><c:out value="${datos[i][0]}"/></td>
            <td><c:out value="${datos[i][1]}"/></td>
        </c:if>
  
        <c:set var="conta" value="0"/>
        <c:forEach begin="0" end="${fn:length(cabeza)-1}" var="j">
          <c:forEach var="sexo" items="${lSexos}">
            <c:set var="conta" value="${conta+1}"/>
            <c:if test="${datos[i][2] == cabeza[j][1] && datos[i][3] == sexo.tipo_sexo}">
              <td align="center"><c:out value="${datos[i][4]}"/></td>
              <c:set var="nro_celda" value="${conta}"/>
              <c:set var="conta" value="${-100000}"/>
            </c:if>
            <c:if test="${conta > nro_celda}"><td align="center">0</td></c:if>
          </c:forEach>
        </c:forEach>
      </c:forEach>
      <c:forEach begin="${nro_celda}" end="${fn:length(lSexos)*fn:length(cabeza)-1}">
        <td align="center">0</td>
      </c:forEach>
      </tr>
    </tbody>
    <tr id="resultado"><th colspan="2" class="colb">TOTALES</th></tr>
    </table>
    <!-- **************    FIN VALORES     ************ -->
  </tr></tbody>
</table>
<br>
<br>
</c:if>
<c:if test="${fn:length(datos)==0}">
  <br><br>
  <blink>
    <center>
      <div class='cuadroAviso' >
        <div class="titulo">&iexcl;Aviso!</div>
        No hay registros para mostrar
      </div>
    </center>
  </blink>
</c:if>
<%@ include file="../../../Inferior.jsp" %>