<%@ include file="../Superior.jsp" %>

<jsp:useBean id="now" class="java.util.Date"/>
<table width="100%">
  <tr>
    <td width="14%" align="center">
      <form name="fvolver" action="<c:url value='/listarMisPendientes.fautapo'/>" method="post">
        <input type="hidden" name="aplicacion" value="/" >
	<input type="hidden" name="accion"     value='Formularito' >
        <div> <a href="javascript:document.fvolver.submit();">
        <IMG SRC="<c:url value='${datosInstitucion.logo}'/>" border="0" ALT="logo institucion" width="50%"></a></div>
      </form>
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
        <tr>
          <td align="center"><font size="1">DIRECCI&Oacute;N DE PLANIFICACI&Oacute;N ACAD&Eacute;MICA</font></td>
        </tr>
      </table>
    </td>
    <td width="14%">
      Fecha : <a href='javascript: window.print()'><fmt:formatDate value="${now}" pattern="${formatoFecha}"/></a> 
    </td>
  </tr>
</table>
<hr>
<br>
<center>
<table>
  <tr>
    <td>
      <h1>SOLICITUD DE PR&Oacute;RROGA</h1>
     </td>
   </tr>
   <tr>
    <td>
      <h1>EN LA PRESENTACI&Oacute;N DE DOCUMENTACI&Oacute;N</h1>
     </td>
   </tr>
   <tr>
     <td>
     <table>
       <tr>
         <td> Se&ntilde;or Director:
         </td>
       </tr>
       <tr>
         <td>
           Yo,&nbsp; &nbsp; &nbsp;<b><c:out value="${estudiante.paterno}"/> <c:out value="${estudiante.materno}"/> </b> <c:out value="${estudiante.nombres}"/>,&nbsp; con R.U.&nbsp; &nbsp; &nbsp;<b> <c:out value="${estudiante.id_estudiante}"/></b>
         </td>
       </tr>
       <tr>
         <td>
           Solicito acogerme a la pr&oacute;rroga en la presentaci&oacute;n del documento de:<br>
         </td>
       </tr>
       <tr>
         <td>
           <b><c:out value="${compromiso.tipo_documento}"/></b>, esta solicitud la realizo por : <c:out value="${compromiso.observacion}"/>.
        </td>
       </tr>
       <tr>
         <td> Las causas que motivaron la demora en la presentaci&oacute;n del documento son:<br></td>
       </tr>
       <tr>
         <td>
           <ul><li><b><c:out value="${compromiso.tipo_compromiso}"/></b></li></ul>
         </td>
       </tr>
     </table>
     </td>
     <td valign="top">
       <table border="0" cellpaddin="0" cellspacing="0" width="100%" height="100%">
         <tr>
	   <td><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       <br><br><br><br><br>
	   </td>
	 </tr>
       </table>
     </td>
   </tr>
   <tr><td colspan="2"></td> </tr>
   <tr><td colspan="2"></td> </tr>
   <tr>
    <td colspan="2">
      <a href='javascript: window.print()'>
       Bolivia - Trinidad, <fmt:formatDate value="${now}" type="date" dateStyle="long"/><br>
      </a>
    </td>
   </tr>
   <tr>
    <td colspan="2" align="center">
      ____________________________ 
      <br>FIRMA DEL INTERESADO
    </td>
   </tr>
</table>   
<br>
<br>
<br>
<hr>
<br><br><br>
<center>
<table>
  <tr>
    <td colspan="2">
      <h1>COMPROMISO DE PRESENTACI&Oacute;N DE DOCUMENTACI&Oacute;N<h1>
    <td>
  </tr>    
  <tr>
    <td>
    <table>
      <tr>
        <td>
	  Yo,&nbsp; &nbsp; &nbsp;<c:out value="${estudiante.paterno}"/> <c:out value="${estudiante.materno}"/> <c:out value="${estudiante.nombres}"/>,&nbsp; con C.I. N&ordm;&nbsp; &nbsp; &nbsp;<b> <c:out value="${estudiante.dip}"/></b>.
        <td>
      </tr>
      <tr>
        <td>
          Firmo el Compromiso de Presentaci&oacute;n del documento de:
	  <br><b><c:out value="${compromiso.tipo_documento}"/></b>,&nbsp; antes del &nbsp;
        <td>
      </tr>
      <tr>
        <td>
          <b><c:out value="${compromiso.fec_vencimiento}"/></b>, en la Seccion de Registros e Inscripciones de la U.A.B. Si por alg&uacute;n motivo incumplo
        <td>
      </tr>
      <tr>
        <td>
          con el plazo de entrega, el tr&aacute;mite iniciado quedar&aacute; nulo.<br>
        <td>
      </tr>
      <tr>
        <td>
         <br>     
        <td>
      </tr>
    </table>
    </td>
    <td valign="top">
      <!--<table border="0" cellpaddin="0" cellspacing="0" width="100%" height="100%">
        <tr>
          <td><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <br><br><br><br><br>
          </td>
        </tr>
      </table>-->
    </td>
  </tr>
  <tr>
    <td colspan="2">
      Bolivia - Trinidad, <fmt:formatDate value="${now}" type="date" dateStyle="long"/><br>
    <td>
  </tr>
  <tr>
    <td colspan="2"></td> 
  </tr>
  <tr>
    <td colspan="2"> </td> 
  </tr>
  <tr>
    <td colspan="2" align="center">
      ____________________________
                 
	</td>
	
  </tr>
  
</table>  

<%@ include file="../Inferior.jsp" %>