<%@ include file="../../Superior.jsp" %>
<!--
  AREA SOFTWARE UABJB
  @autor            : 
  @fec_registro     : 04/04/2016
  @ult_usuario      : Bladimir
  @fec_modificacion : 05/04/2016
-->

<table class = "tabla1">
  <!-- SE REPITE-->
  <thead>
  <tr>
    <td width="90%" align="center" cellspancin="0" cellpading="0">
    <table width="90%">
      <tr>
        <td width="5%" align="center">
          <img width="90%" src="<c:url value='${datosInstitucion.logo}'/>" border="0" ALT="logo institucion">
        </td>
        <td width="72%" align="center">
          <table width="90%" heigth="90%" cellpading="2" cellspacing="0" >
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
    <hr>
    <table width="100%" align="center">
      <tr>
        <td align="center"><label><h2>RESUMEN OBLIGACIONES DIVERSAS INSTITUCIONALES - ENTIDADES FINANCIERAS - AGRUPADO POR CONCEPTO </h2></label></td>
      </tr>
	  <tr>
        <td align="lefth"><label>Usuario(a)::<c:out value='${nombres}'/></label></td>
      </tr>
	  <tr>
        <td align="lefth"><label>Fecha de la Transaccion:: Del <c:out value='${fec_comprobantei}'/> Al  <c:out value='${fec_comprobantef}'/></label></td>
      </tr>
    </table>
    <br>
    </td>
  </tr>
  </thead>
  <!-- HASTA AQUI SE REPITE-->

<!-- **************    VALORES     ************ -->
    <c:set var="total" value="0"/>

<!--
<tbody>

  <tr>
    <td>
      
      <table class="tabla" width="100%">
        <thead>
        <tr>
          <th>Nro</th>		  
		  <th>Concepto</th>
          <th>Cantidad</th>
          <th>Total Bs.</th>
		  
        </tr>
	</thead>-->
  <tbody class="tabla">

  <!-- **************    VALORES     ************ -->
       
      <table class="tabla" width="100%">
        <thead>
        <tr>
          <th>Nro</th>		  
		  <th>Concepto</th>
          <th>Cantidad</th>
          <th>Total Bs.</th>		  
        </tr>
	</thead>
        <c:forEach var="lista" items="${lTransacciones}" varStatus="contador">
          <tr>
            <td align="center"><c:out value="${contador.count}"/></td>            			
			<td><c:out value="${lista.concepto}"/></td>
			<td align="center" ><c:out value="${lista.cantidad}"/></td>
            <td align="right"><c:out value="${lista.total}"/></td>
			<c:set var="total" value="${total + lista.total}"/>
           </tr>
        </c:forEach>
		  <tr>
           <td colspan=3 height="1" style="border-top: 2px solid #000000; border-bottom: 2px solid #000000;" align="right">TOTAL Bs. &nbsp;&nbsp;&nbsp;&nbsp;</td>
           <td  height="1" style="border-top: 2px solid #000000; border-bottom: 2px solid #000000;" align="right"> <c:out value="${total}"/> </td>
          </tr>
      </table>
   
   <br><br><br><br><br>

<table width="68%"  class="tabla" style="border-top: 2px solid #000000;border-bottom: 2px solid #000000;">
<tr>
<th class='etiqueta' align="center"><div class="clave" align="left">TOTAL ENTREGADO FUL (CUB + Pro Deporte + Pro Facultativo)   </th>
<th class='etiqueta'><c:out value="${total}"/></th>
<th class='etiqueta'>FIRMA</th>
   
   </tr>
 </tbody>
</table>
<br>
<br>
<%@ include file="../../Inferior.jsp" %>
