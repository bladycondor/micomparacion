<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />
<table width="100%">
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
              <td align="center"><font size="3"><b><c:out value='${datosInstitucionsede.localidad}'/> - <c:out value='${datosInstitucionsede.departamento}'/> - <c:out value='${datosInstitucionsede.pais}'/></b></font></td>
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
        <td align="center"><label><h2>ASIGNACION DOCENTE - MATERIAS</h2></label></td>
      </tr>
    </table>
    <table border="0" width="97%">
      <tr>
        <td>CARRERA::</td>
        <td><c:out value="${datosPrograma.programa}"/></td>
        <td>FACULTAD::</td>
        <td><c:out value="${datosFacultad.facultad}"/></td>
      </tr>
      <tr>
        <td>PLAN::</td>
        <td><c:out value="${id_plan}"/></td>
        <td>PERIODO ::</td>
        <td><c:out value="${periodo}"/> - <c:out value="${gestion}"/></td>
      </tr>
	  
	   <tr>
        <td>TIPO EVALUACION::</td>
	  	 
		  <td> <c:if test ="${evaluacion == 1}">REGULAR</c:if> 
		       <c:if test ="${evaluacion == 3}">CURSO DE VERANO</c:if> 
		       <c:if test ="${evaluacion == 4}">EXAMEN DE MESA</c:if> 
		  </td>
		 <tr>  
	</table>
    <br>
    </td>
  </tr>
  </thead>
  <!-- HASTA AQUI SE REPITE-->
  <tbody>

  <c:if test="${ empty lAsignacionDocentesMateria}">
    <center><div class="cuadroAviso" align="center">No existen materias para la gesti&oacute;n <c:out value="${gestion}"/>, periodo <c:out value="${periodo}"/></div></center>
  </c:if>

  <table class="tabla" width="98%">
  <thead>
      <tr>
        <th>NRO</th>
		<th>CI</th>
		<th>PATERNO</th>
		<th>MATERNO</th>
	    <th>NOMBRES</th>
        <th>FUNCION</th>
        <th> MATERIA - SIGLA - NIVEL - GRUPO - HRS - TIPO </th>
      	<th>DEDICACION</th>
	   </tr>
	   
		
	       <c:forEach var="nroasignacion" items="${lNroAsignacionDocentesMateria}" varStatus="contador2">
   	   <tr <c:if test="${(contador2.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">

				<td rowspan = "${nroasignacion.nro_designacion}"><c:out value="${contador2.count}"/></td>
				
				<td rowspan = "${nroasignacion.nro_designacion}"><c:out value="${nroasignacion.dip}"/></td>
				<td rowspan = "${nroasignacion.nro_designacion}"><c:out value="${nroasignacion.paterno}"/></td>
				<td rowspan = "${nroasignacion.nro_designacion}"><c:out value="${nroasignacion.materno}"/></td>
				<td rowspan = "${nroasignacion.nro_designacion}"><c:out value="${nroasignacion.nombres}"/></td>
				<td rowspan = "${nroasignacion.nro_designacion}"><c:out value="${nroasignacion.descripcion}"/></td>
			    
				<c:if test="${(nroasignacion.nro_designacion) > 1}"> 
				<td rowspan = "1" >
				   <table class="tabla" width="100%" border = "0">
					   <c:forEach var="asignacion" items="${lAsignacionDocentesMateria}" varStatus="contador">
					   
							<c:if test = "${(nroasignacion.id_docente) == (asignacion.id_docente)}"> 
									  
							     	 <tr>
										  <td width = "60%"><c:out value="${asignacion.materia}"/></td>
										  <td width = "15%"><c:out value="${asignacion.sigla}"/></td>
										  <td width = "5%" align="center"><c:out value="${asignacion.nivel_academico}"/></td>
										  <td width = "5%" align="center"><c:out value="${asignacion.grupo}"/></td>
										  <td width = "5%" ><c:out value="${asignacion.hrs_periodo}"/></td>
										  <td width = "10%" ><c:out value="${asignacion.tipo_docente}"/></td>
								     </tr> 
							</c:if>	
					     </c:forEach> 
				    </table>
				 </td>
			      <td rowspan ><c:out value="${nroasignacion.tipo_asignacion}"/></td>
				    <tr></tr><tr></tr>
				</c:if>	
               
				
		     <c:if test="${(nroasignacion.nro_designacion) <= 1}"> 
		     <td collspan = "6"> 
					<table class="tabla" width="100%" border = "0">
						<c:forEach var="asignacion" items="${lAsignacionDocentesMateria}" varStatus="contador">
							<c:if test = "${(nroasignacion.id_docente) == (asignacion.id_docente)}"> 
								<td width  = "60%"><c:out value="${asignacion.materia}"/></td>
								<td width = "15%"><c:out value="${asignacion.sigla}"/></td>
								<td width = "5%" align="center"><c:out value="${asignacion.nivel_academico}"/></td>
								<td width = "5%" align="center"><c:out value="${asignacion.grupo}"/></td>
								<td width = "5%" ><c:out value="${asignacion.hrs_periodo}"/></td>
								<td width = "10%" ><c:out value="${asignacion.tipo_docente}"/></td>
							
							 </c:if>	
						</c:forEach> 
					</table>
				</td>
				  <td rowspan = "${nroasignacion.nro_designacion}"><c:out value="${nroasignacion.tipo_asignacion}"/></td>  	
			<tr>
			</c:if>
		    	 		 
	        </c:forEach>  
	 </table>
  </tbody>
  </table>
<%@ include file="../../Inferior.jsp" %>