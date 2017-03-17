<%@ include file="../../../Superior.jsp" %>
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
                <img SRC="<c:url value='${logo}' />" width="70" height="70" border="0" ALT="logo institucion"><br>
              </c:if>
              <font size=1>
                <c:out value='${sigla}'escapeXml="False"/>-<c:out value='${anio}'escapeXml="False"/>
              </font>
            </td>
            <td width="60%">
              <center><h6><c:out value='${institucion}'escapeXml="False"/></h6></center>
              <label><center><h2>Mayor y Menor Nota por Materia</h2></center></label>
            </td>
            <td width="20%">
              <script>
                var mydate=new Date();
                var year=mydate.getYear();
                if (year < 1000)
                  year+=1900;
                var day=mydate.getDay();
                var month=mydate.getMonth()+1;
                if (month<10)
                  month="0"+month;
                var daym=mydate.getDate();
                if (daym<10)
                daym="0"+daym;
  	        //document.write("<sup>Pagina:</sup><sup>1</sup><br>");
                document.write("<font size=1>Fecha:<a href = 'javascript: window.print()'>" + daym+"/"+month+"/"+year+"</a></font>");
              </script>
            </td>
          </tr>
        </table>
        </div>
        <br>
        <table>
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
   <c:forEach varStatus="filas" begin="0" end="${fn:length(datos)-1}">
    <tr>
      <c:forEach var="lista1" items="${etiquetas}" varStatus="columnas" begin="${desde}">
        <c:out value='${datos[filas.index][columnas.index]}' escapeXml='false'/>
      </c:forEach>
    </tr>
  </c:forEach>
  <!-- **************  FIN VALORES   ************ -->
  <!-- **************    TOTALES     ************ -->
   <tr>
     <td colspan="100" height="1" style="border-top: 2px solid #000000; border-bottom: 2px solid #000000;" align="right">&nbsp;</td>
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
<%@ include file="../../../Inferior.jsp" %>