<%@ include file="../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />
<table width="80%" border="1" align="center">
  <!-- SE REPITE-->
  <thead>
  <tr>
    <td width="100%" align="center" cellspancin="0" cellpading="0">
    
    <table width="100%" border="0">
      <tr>
        <td width="14%" align="center">
          <img src="<c:url value='${datosInstitucion.logo}'/>" border="0" ALT="logo institucion" width="50%">
        </td>
        <td width="80%" align="center">
          <table width="100%" heigth="100%" cellpading="0" cellspacing="0" >	  
            <tr>
              <td align="center"><font size=4><b><c:out value='${datosInstitucion.institucion}'/></font></td>
            </tr>
             
            <tr>
              <td align="center"><font size=2>Trinidad - Beni - Bolivia</font></td>	      
            </tr>	    	    
            <tr>
              <td align="center"><c:out value='${datosInstitucion.actividad}'/></td>
            </tr>
			<td width="14%" align="right">
          Fecha de Impresion:<fmt:formatDate value="${now}" pattern="${formatoFecha}"/></a> 
        </td>
          </table>
        </td>
        <td width="14%">
        </td>
      </tr>
    </table>
    <hr width="100%">   
<div align="center"><H1>MEMORANDUM</H1></div>
<div align="center">
<table width="100%" border="0">
    <tr>    
	<td width="49%"><font size="1">		
	<b>DES-Cite No.</b><a href='javascript: window.print()'> 00<c:out value="${datosAsignacion.nro_memo}"/> / <c:out value="${datosAsignacion.gestion}"/></a><br><br>        
	<b>Resolucion Nro:</b> <font size=1><c:out value='${datosAsignacion.nro_resolucionhcu}'/> <b>de fecha: </b><fmt:formatDate value="${datosAsignacion.fec_resolucion}" pattern="${formatoFecha}"/>
	     
	</font>
	</td>   
	<td border="0">
	    <img src="../imagenes/logos/linea.gif" border="0" ALT="logo institucion" >
	</td>
	<td width="49%"><font size="1">	
	De : MSC. Ing. Luis Carlos Zambrano Aguirre <br>
	RECTOR
	<br>

	Se&ntilde;or(a)
	<c:out value="${datosDocente.paterno}"/>&nbsp;<c:out value="${datosDocente.materno}"/>&nbsp;<c:out value="${datosDocente.nombres}"/><br>
	<u>Presente.-</u> <br><br>
	Ref.: <b>Designaci&oacute;n <u>Docencia</u></b>
	</font>
	</td>
    </tr>	
</table>
<table width="100%">
    <tr>    
    <td>
	<img src="../imagenes/logos/linea.gif" border="0" height="1" width="100%">
    </td>
    </tr>
</table>    

<table width="100%">
    <tr><td align=justify>    
    <font size="1">
    Distinguido(a) Docente (a): 
    <br><br>        
    Comunico a usted, que en virtud de lo establecido por el Art. 10 y 11 del Reglamento del R&eacute;gimen Academico- Docente de la Universidad, ha sido designado docente, en funci&oacute;n al calendario acad&eacute;mico y bajo las siguientes condiciones</b>, 
    
    </font>
    </td>
    </tr>
</table> 
<br>
<table class="tabla" width="90%" >
  <thead>
      <tr>
        
	<th>NIVEL<br>ACADEMICO</th>
        <th>GRUPO</th>
        <th>SIGLA</th>
        <th>MATERIA</th>
		<th>CARGA HR.SEMANAL</th>
	<th>CARRERA</th>
	<th>TIPO EVALUACION</th>
	<th>FUNCION ADMINISTRATIVA</th>
	<th>TIPO DOCENTE</th>
	<th>DEDICACION</th>
      </tr>
      
        <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
	  
	  <td align="center"><c:out value="${datosAsignacion.nivel_academico}"/></td>
          <td align="center"><c:out value="${datosAsignacion.grupo}"/></td>
          <td align="center"><c:out value="${datosAsignacion.sigla}"/></td>
	  <td align="center"><c:out value="${datosAsignacion.materia}"/></td>
	   <td align="center"><c:out value="${datosAsignacion.hrs_periodo}"/></td>
	  <td align="center"><c:out value="${datosAsignacion.departamento}"/></td>
	  <td align="center"><c:out value="${datosAsignacion.tipo_evaluacion}"/></td>
	  <td align="center"><c:out value="${datosAsignacion.descripcion}"/></td>
	  <td align="center"><c:out value="${datosAsignacion.tipo_docente}"/></td>
	  <td align="center"><c:out value="${datosAsignacion.tipo_asignacion}"/></td>
      </tr>
   
  </table>
  <br><br><br><br>
<table width="100%" border="0">
   <tr>
   <td width="100%" colspan="2">
   c.c.: Vice. Pre<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Decanatura<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RR.HH.<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ARCH.<br>
   </td>
   </tr>
</table>

<table width="100%" border="0">
   <tr>
   <td width="100%" colspan="2"><hr width="100%"></td>
   </tr>
   <tr>    
    <td coslpan="1" width="50%">Telefono: 346-24101 Int.(104)</td>   
    <td coslpan="1" width="50%" align="right">Edificio Central UAB<br>Antonio Vaca Diez - Vicerrectorado de Pregrado</td>   
   </tr>         

</table>   

<%@ include file="../Inferior.jsp" %>
</div>
