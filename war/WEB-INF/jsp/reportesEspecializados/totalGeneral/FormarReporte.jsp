<%@ include file="../../Superior.jsp" %>

<table width="60%">
  <!-- SE REPITE-->
  <thead>
   
  <table  width="50%">
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
              <center><h3><c:out value='${institucion}'escapeXml="False"/></h3></center>
            
              <label><center><h2>Reporte Por Programas</h2></center></label>
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
                document.write("<font size=1>Fecha:<a href = 'javascript: window.print()'>" +dia+"/"+mes+"/"+anio+" " + horas+":"+minutos+":"+segundos+"  "+tiempo+"</a></font>");            </script>
              </script>
            </td>
          </tr>
        </table>
  </div>
        <br>
          <table>
  <c:if test="${fec_comprobantei!=''}">
    <tr><td colspan=3>Cajero (a) : <c:out value="${nombres}"/></td>
    <tr><td colspan=3>Fecha de transacci&oacute;n: <c:out value="${fec_comprobantei}"  />&nbsp; &nbsp;</td></tr>
  </c:if>
          </table>
       
  <tr>
    <td width="100%" align="center" cellspancin="0" cellpading="0">
       <br>
    </td>
  </tr>
  </thead>
  <!-- HASTA AQUI SE REPITE-->
  <tbody>

  <tr>
    <td>
      
      <table class="tabla" width="50%" style="border-top: 2px solid #000000;border-bottom: 2px solid #000000;">
        <thead>
        <tr style="border-top: 2px solid #000000;border-bottom: 2px solid #000000;">
          <th>FECHA</th>
		  <th>Nro.</th>
          <th>CODIGO</th>
          <th>NOMBRES</th>
          <th>P. UNITARIO</th>
          <th>CANTIDAD</th>
		  <th>TOTAL</th>
		  <th>DESCUENTO</th>
		  <th>PAGADO</th>
        </tr>
	</thead>
    
   <c:set var="total" value="0"/>
   <c:set var="descuento" value="0"/>
   <c:set var="pagado" value="0"/>
   
        <c:forEach var="lista" items="${lTransacciones}" varStatus="contador">
          <tr>
            <td align="center"><c:out value="${contador.count}"/></td>            
			<td align="center"><c:out value="${lista.fecha_ini}"/></td>
            <td align="center"><c:out value="${lista.id_transaccion}"/></td>
            <td align="left"><c:out value="${lista.codigo}"/></td>
			<td align="left"><c:out value="${lista.nombres}"/></td>
            <td align="right"><c:out value="${lista.total}"/></td>	
            <td align="left"><c:out value="${lista.cantidad}"/></td>
            <td align="right"><c:out value="${lista.total}"/></td>
            <td align="right"><c:out value="${lista.descuento}"/></td>
			<td align="right"><c:out value="${lista.pagado}"/></td>
          <c:set var="total" value="${total + lista.total}"/>
          <c:set var="descuento" value="${descuento + lista.descuento}"/>
          <c:set var="pagado" value="${pagado + lista.pagado}"/>
    
          </tr>
        </c:forEach>
      
       <tr >
     <td colspan=3 height="1" style="border-top: 2px solid #000000; border-bottom: 2px solid #000000;" align="right">TOTAL POR PROGRAMA &nbsp;&nbsp;&nbsp;&nbsp;</td>
     <td align="right" height="1" style="border-top: 2px solid #000000; border-bottom: 2px solid #000000;" > <c:out value="${total}"/> </td>
     <td align="right" height="1" style="border-top: 2px solid #000000; border-bottom: 2px solid #000000;" > <c:out value="${descuento}"/></td>
     <td align="right" height="1" style="border-top: 2px solid #000000; border-bottom: 2px solid #000000;" > <c:out value="${pagado}"/></td>
   </tr>  
  
      
      </table>
    </td>
  </tr>
  </tbody>
</table>
<%@ include file="../../Inferior.jsp" %>