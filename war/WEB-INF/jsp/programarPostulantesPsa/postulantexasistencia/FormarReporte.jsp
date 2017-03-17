<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />
<!--
  /*****************************************
 @usuario          :: Yusara Farah
 @fec_registro     :: 26.02.2015
 @ult_usuario      :: Yusara Farah
 @fec_modificacion :: 26.02.2015
*****************************************/
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
        <td align="center"><label><h2>REPORTE LISTADO DE ASISTENCIA DE POSTULANTES P.S.A.</h2></label></td>
      </tr>
	  
	  <tr>
        <td align="lefth"><label>Periodo <c:out value='${periodo}'/> Gestion: <c:out value='${gestion}'/></label></td>
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
          
    <th>Nro.</th>
    <th>R.P.</th>
    <th>Nombres</th>
    <th>C.I.</th>
	<th>Carrera</th>
	<th>Fecha<br>de Examen P.S.A.</th>
    <th>Lugar <br>Examen</th>
    <th>Hora</th>
    <th>Nro de Maquina asignada</th>
		  
        
		  
        </tr>
	</thead>
  <tbody class="tabla">

  <!-- **************    VALORES     ************ -->
   
   <c:set var="aux" value="0"/> 
    <c:set var="concepto" value="0"/> 
    <c:forEach var="lista" items="${lTransacciones}" varStatus="contador">
	<c:set var="aux" value="${lista.lugar}"/>	
	
	<!--<c:if test="${aux != concepto}">  
	<tr> <td  colspan=6 height="1" style="border-top: 1px solid #000000; border-bottom: 0px solid #000000;" align="left"> <c:out value="${lista.asistencia}"/> :: <c:out value="${lista.lugar}"/> </td>
           </tr> 
		   </c:if>-->
		   <c:set var="concepto" value="${lista.lugar}"/>		
     
    <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
            
      <td><c:out value="${contador.count}"/></td>
      <td><c:out value="${lista.id_postulante}"/></td>
      <td><c:out value="${lista.paterno}"/>&nbsp;<c:out value="${lista.materno}"/>&nbsp;<c:out value="${lista.nombres}"/></td>
      <td><c:out value="${lista.dip}"/></td>
      <td><c:out value="${lista.programa}"/></td>
      <td><c:out value="${lista.fecha}"/></td>
      <td><c:out value="${lista.lugar}"/></td>
      <td><c:out value="${lista.hora}"/></td>
	  <td><c:out value="${lista.nro_maquinas}"/></td>
	 <!-- <td><c:out value="${lista.asistencia}"/></td>-->
      <td>
    <c:if test="${lista.asistencia == 'TRUE'}">
	  <font color="blue"> Asistio </font>
	</c:if>
   <c:if test="${lista.asistencia == 'FALSE'}">
	  <font color="black"> No asistio </font>
	</c:if>

	
      </td>
    </tr>
	
  </c:forEach>
  <!-- **************  FIN VALORES   ************ -->
  <!-- **************    TOTALES     ************ -->
   
 
 </tbody>
</table>


<br>
<br>
<%@ include file="../../Inferior.jsp" %>
