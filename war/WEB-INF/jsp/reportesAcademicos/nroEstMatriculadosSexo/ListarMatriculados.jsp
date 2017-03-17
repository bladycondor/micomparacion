<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />

<table border="0" width="100%">
  <!-- SE REPITE-->
  <thead>
  <tr>
    <td width="100%" align="center" cellspancin="0" cellpading="0">
    <table width="100%">
      <tr>
        <td width="14%" align="center">
          <img src="<c:url value='${datosInstitucion.logo}'/>" border="0" ALT="logo institucion" width="40%">
        </td>
        <td width="72%" align="center">
          <table width="100%" heigth="100%" cellpading="2" cellspacing="0" >
            <tr>
              <td align="center"><h1><b><c:out value='${datosInstitucion.institucion}'/></h1></td>
            <tr>
            <tr>
              <td align="center"><font size="3"><b><c:out value='${datosInstitucionsede.localidad}'/> - <c:out value='${datosInstitucionsede.departamento}'/> - <c:out value='${datosInstitucion.pais}'/></b></font></td>
            <tr>
            </tr>
              <td align="center"><c:out value='${datosInstitucion.actividad}'/></td>
            </tr>
          </table>
        </td>
        <td width="14%">
          Fecha : <a href='javascript: window.print()'><fmt:formatDate value="${now}" pattern="${formatoFecha}"/></a> 
        </td>
      </tr>
    </table>
    <hr>
    <table width="100%" align="center">
      <tr>
        <td align="center"><label><h1>Nro. DE MATRICULADOS POR SEXO Y TIPO ESTUDIANTE</h1></label></td>
      </tr>
    </table>
    <table border="0">
      <tr>
        <td>PERIODO ::</td>
        <td><c:out value="${periodo}"/> - <c:out value="${gestion}"/></td>
      </tr>
    </table>
    <br>
    </td>
  </tr>
  </thead>
  <!-- HASTA AQUI SE REPITE-->
  <tr>
    <td>
    <table class="tabla" border=1 width="97%">
      <tr>
        <th rowspan="2">Nro</th>
		<th rowspan="2">Facultad</th>
        <th rowspan="2">Programa</th>	
		<th colspan="3">Femenino</th>	
		<th colspan="3">Masculino</th>			
      </tr>
      <tr>  
        <th>Nuevo</th>    
		<th>Antiguo</th>    
		<th>Total</th>    
        <th>Nuevo</th>    
		<th>Antiguo</th>    
		<th>Total</th>    
		</tr>     
        <tr>
      	  <c:set var="cont" value="0"/>
	      <c:set var="cont1" value="0"/>
	      <c:set var="cont2" value="0"/>
		  <c:set var="cont3" value="0"/>
		  <c:set var="cont4" value="0"/>
		  <c:set var="cont5" value="0"/>
		  
          <c:forEach var="lEstudiante" items="${lEstudiantes}" varStatus="contador">
          <tr>
           <td><c:out value="${contador.count}"/></td>    
		   <td><c:out value="${lEstudiante.facultad}"/></td>
           <td><c:out value="${lEstudiante.programa}"/></td>	 
		   <td><c:out value="${lEstudiante.reprobados}"/></td>
		   <td><c:out value="${lEstudiante.cantidad-lEstudiante.reprobados}"/></td>	  
		   <td class="colb" ><c:out value="${lEstudiante.cantidad}"/></td>	  
		   <td><c:out value="${lEstudiante.abandonos}"/></td>	  
		   <td><c:out value="${lEstudiante.aprobados-lEstudiante.abandonos}"/></td>		   
		   <td class="colb"><c:out value="${lEstudiante.aprobados}"/></td>	  
		   
           <c:set var="cont" value="${cont+lEstudiante.reprobados}"/> 		  
		   <c:set var="cont1" value="${cont1+lEstudiante.cantidad-lEstudiante.reprobados}"/> 		  
	  	   <c:set var="cont2" value="${cont2+lEstudiante.cantidad}"/> 		  
		   <c:set var="cont3" value="${cont3+lEstudiante.abandonos}"/> 	
		   <c:set var="cont4" value="${cont4+lEstudiante.aprobados-lEstudiante.abandonos}"/> 	
		   <c:set var="cont5" value="${cont5+lEstudiante.aprobados}"/> 	
          </tr>
         </c:forEach>

        </tr>
   
      <tr>
        <td class="colb" colspan="3">
          TOTALES POR SEXO Y TIPO ESTUDIANTE :
        </td>
            <td class="colb"><c:out value="${cont}"/></td>   
			<td class="colb"><c:out value="${cont1}"/></td>   
			<td class="colb"><c:out value="${cont2}"/></td>   
			<td class="colb"><c:out value="${cont3}"/></td>   
			<td class="colb"><c:out value="${cont4}"/></td>  
            <td class="colb"><c:out value="${cont5}"/></td>   			
        </tr>  
      <tr>
        <td class="colb" colspan="5">
          TOTAL MATRICULADOS :
        </td>
        <td class="colb" colspan="4">
          <c:out value="${cont+cont1+cont3+cont4}"/> Matriculados
        </td>
      </tr>
    </table>
    </td>
  </tr>
</table>

<%@ include file="../../Inferior.jsp" %>