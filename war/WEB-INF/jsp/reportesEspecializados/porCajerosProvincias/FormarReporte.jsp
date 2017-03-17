<%@ include file="../../Superior.jsp" %>
<!--
  FUNDACION AUTAPO
  @autor            : Luis A. Jordan P.
  @fec_registro     : 11/04/2006
  @ult_usuario      : Jorge Copa
  @fec_modificacion : 2007-11-13
-->
<c:if test="${fn:length(datos)>0}">

<table class = "tabla1">
  <thead>
    <tr>
      <td colspan=20>
        <!-- ************  Titulo  ********** -->
        <div class="titulo">
        <table width="100%">
          <tr>
            <td width="20%" align=center>
              <c:if test="${!empty logo}">
                <IMG SRC="<c:url value='${logo}' />" width="70" height="70" border="0" ALT="logo institucion"><br>
              </c:if>
              <font size=1>
                <c:out value='${sigla}'escapeXml="False"/>-<c:out value='${anio}'escapeXml="False"/>
              </font>
            </td>
            <td width="60%">
              <center><h6><c:out value='${institucion}'escapeXml="False"/></h6></center>
              <label><center><h2>Total por cada Cajero</h2></center></label>
            </td>
            <td width="20%">
              <script>
                hora = new Date();
                var dia = hora.getDate();
				var mes = hora.getMonth()+1;
                var anio = hora.getFullYear();
                horas = hora.getHours()
				minutos = hora.getMinutes()
				segundos = hora.getSeconds()
				if (mes <= 9) mes = "0" + mes
				if (horas >= 12) tiempo = " p.m."
				else tiempo = " a.m."
				if (horas > 12) horas -= 12
				if (horas == 0) horas = 12
				if (minutos <= 9) minutos = "0" + minutos
				if (segundos <= 9) segundos = "0" + segundos		
							
  	        //document.write("<sup>Pagina:</sup><sup>1</sup><br>");
                document.write("<font size=1>Fecha:<a href = 'javascript: window.print()'>" +dia+"/"+mes+"/"+anio+" " + horas+":"+minutos+":"+segundos+"  "+tiempo+"</a></font>");
			</script>
            </td>
          </tr>
        </table>
        </div>
        <br>
        <table>
  <c:if test="${fec_comprobantei!=''}">
    <tr><td colspan=3>Del: <c:out value="${fec_comprobantei}" />&nbsp; &nbsp; Al: <c:out value="${fec_comprobantef}" /></td></tr>
  </c:if>
          </table>
        <br>
        <br>
        <!-- ************  fin Titulo  ********** -->
      </td>
    </tr>
    <!-- ************** ETIQUETAS ************ -->
    <tr class="tabla" style="border-top: 2px solid #000000;border-bottom: 2px solid #000000;">
      <c:forEach var="lista" items="${etiquetas}" varStatus="contador2" begin="${desde}">
        <th class='etiqueta'>
          <div class="clave" align="left">
            <c:out value="${etiquetas[contador2.index]}" escapeXml="False"/>&nbsp;&nbsp;
          </div>
        </th>
      </c:forEach>
    </tr>
  </thead>
  <!-- ************** FIN ETIQUETAS ************ -->
  <tbody class="tabla">

  <!-- **************    VALORES     ************ -->
   <c:set var="total" value="0"/>
   <c:forEach varStatus="filas" begin="0" end="${fn:length(datos)-1}">
    <tr>
      <c:forEach var="lista1" items="${etiquetas}" varStatus="columnas" begin="${desde}">
        <c:out value='${datos[filas.index][columnas.index]}' escapeXml='false'/>
        <c:if test="${columnas.last}">
          <c:set var="total" value="${total + datos1[filas.index][columnas.index]}"/>
        </c:if>
      </c:forEach>
    </tr>
  </c:forEach>
  <!-- **************  FIN VALORES   ************ -->
  <!-- **************    TOTALES     ************ -->
   <tr>
     <td colspan="100" height="1" style="border-top: 2px solid #000000; border-bottom: 2px solid #000000;" align="right">TOTAL &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <c:out value="${total}"/></td>
   </tr>
 </tbody>
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
<%@ include file="../../Inferior.jsp" %>