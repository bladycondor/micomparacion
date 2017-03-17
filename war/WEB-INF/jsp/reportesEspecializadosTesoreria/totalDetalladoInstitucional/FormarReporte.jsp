<%@ include file="../../Superior.jsp" %>
<!--
  FUNDACION AUTAPO
  @autor            : Luis A. Jordan P.
  @fec_registro     : 11/04/2006
  @ult_usuario      : Jorge Copa
  @fec_modificacion : 2007-11-13
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
        <td align="center"><label><h2>REPORTE - DETALLADO INSTITUCIONAL - POR CAJEROS</h2></label></td>
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
   <c:set var="descuento" value="0"/>
   <c:set var="pagado" value="0"/>
  
  <tbody>

  <tr>
    <td>
      
      <table class="tabla" width="100%">
        <thead>
        <tr>
          <th>Nro</th>		  
		  <th>Concepto</th>
          <th>Cantidad</th>
          <th>Costo</th>
		  <th>Descuento</th>
		  <th>Pagado</th>		  
        </tr>
	</thead>
  <tbody class="tabla">

  <!-- **************    VALORES     ************ -->
   <c:set var="total" value="0"/>
   <c:set var="subtotal" value="0"/>
   <c:set var="descuento" value="0"/>
   <c:set var="subdescuento" value="0"/>
   <c:set var="pagado" value="0"/>
   <c:set var="subpagado" value="0"/>    
    
   <c:set var="concepto" value="0"/>  
   <c:set var="id_concepto" value="0"/> 
   <c:set var="aux" value="0"/> 
   
   <c:forEach var="lista" items="${lTransacciones}" varStatus="contador">
          <c:set var="aux" value="${lista.id_concepto}"/>	
          <c:if test="${aux != concepto}">            
          
          <c:if test="${subtotal !=0}">
            <tr>
               <td colspan=3 height="1" style="border-top: 1px solid #000000; border-bottom: 1px solid #000000;" align="right">Sub Total&nbsp;&nbsp;&nbsp;&nbsp;</td>
               <td  height="1" style="border-top: 1px solid #000000; border-bottom: 1px solid #000000;" align="left"> <c:out value="${subtotal}"/> </td>
               <td  height="1" style="border-top: 1px solid #000000; border-bottom: 1px solid #000000;" align="left"> <c:out value="${subdescuento}"/> </td>
               <td  height="1" style="border-top: 1px solid #000000; border-bottom: 1px solid #000000;" align="right"> <c:out value="${subpagado}"/> </td>              
           </tr>
           </c:if>          
           <tr> <td  colspan=6 height="1" style="border-top: 1px solid #000000; border-bottom: 0px solid #000000;" align="left"> <c:out value="${lista.concepto}"/> :: <c:out value="${lista.id_concepto}"/></td>
           </tr>   
           <c:set var="concepto" value="${lista.id_concepto}"/>		   
           <c:set var="id_concepto" value="${lista.id_concepto}"/>          
           
           <c:set var="subtotal" value="0"/>
           <c:set var="subdescuento" value="0"/>
           <c:set var="subpagado" value="0"/>

          
          </c:if> 
       
       <tr>        
           <td align="center"> </td>            			
		   <td><c:out value="${lista.programa}"/></td>            			
		   <td align="center"><c:out value="${lista.cantidad}"/></td>            			
		   <td align="right"><c:out value="${lista.total}"/></td>            			
		   <td align="right"><c:out value="${lista.descuento}"/></td>            			
		   <td align="right"><c:out value="${lista.pagado}"/></td>            			
      
           <!--   <c:set var="aux" value="${lista1.id_concepto}"/>         -->
          
              <c:set var="total" value="${total + lista.total}"/>
              <c:set var="descuento" value="${descuento + lista.descuento}"/>
              <c:set var="pagado" value="${pagado + lista.pagado}"/>
          
              <c:set var="subtotal" value="${subtotal + lista.total}"/>
              <c:set var="subdescuento" value="${subdescuento + lista.descuento}"/>
              <c:set var="subpagado" value="${subpagado + lista.pagado}"/>
          
              
             
     
    </tr>
  </c:forEach>
  <!-- **************  FIN VALORES   ************ -->
  <!-- **************    TOTALES     ************ -->
   
 
  <tr>
     <td colspan=3 height="1" style="border-top: 1px solid #000000; border-bottom: 2px solid #000000;" align="right">SubTotal&nbsp;&nbsp;&nbsp;&nbsp;</td>
     <td  height="1" style="border-top: 1px solid #000000; border-bottom: 2px solid #000000;" align="left"> <c:out value="${subtotal}"/> </td>
     <td  height="1" style="border-top: 1px solid #000000; border-bottom: 2px solid #000000;" align="left"> <c:out value="${subdescuento}"/></td>
     <td  height="1" style="border-top: 1px solid #000000; border-bottom: 2px solid #000000;" align="right"> <c:out value="${subpagado}"/></td>
   </tr>



  <tr>
     <td colspan=3 height="1" style="border-top: 2px solid #000000; border-bottom: 2px solid #000000;" align="right">TOTAL&nbsp;&nbsp;&nbsp;&nbsp;</td>
     <td  height="1" style="border-top: 2px solid #000000; border-bottom: 2px solid #000000;" align="left"> <c:out value="${total}"/> </td>
     <td  height="1" style="border-top: 2px solid #000000; border-bottom: 2px solid #000000;" align="left"> <c:out value="${descuento}"/></td>
     <td  height="1" style="border-top: 2px solid #000000; border-bottom: 2px solid #000000;" align="right"> <c:out value="${pagado}"/></td>
   </tr>
 </tbody>
</table>
<br>
<br>
<%@ include file="../../Inferior.jsp" %>
