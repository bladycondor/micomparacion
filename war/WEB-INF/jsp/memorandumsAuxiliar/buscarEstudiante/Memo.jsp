<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />
<table width="100%" border="0">
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
              <td align="center"><font size=3>VICE-RECTORADO DE PREGRADO</font></td>	      
            </tr>	    
            <tr>
              <td align="center"><font size=2>Trinidad - Beni - Bolivia</font></td>	      
            </tr>	    	    
            <tr>
              <td align="center"><c:out value='${datosInstitucion.actividad}'/></td>
            </tr>
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
	<b>Cite No.</b><a href='javascript: window.print()'> 00<c:out value="${id_perfil}"/>/<c:out value="${gestion}"/></a><br><br>        
	<b>Fecha:</b> <font size=1>Santisima Trinidad, 	
	<fmt:formatDate value="${fecha_i}" pattern="dd"/> de 
	<fmt:formatDate value="${fecha_i}" pattern="MMMM"/>  del 	
	<fmt:formatDate value="${fecha_i}" pattern="yyyy"/>        
	</font>
	</td>   
	<td border="0">
	    <img src="../imagenes/logos/linea.gif" border="0" ALT="logo institucion" >
	</td>
	<td width="49%"><font size="1">	
	De : Vicerrector de Pregrado U.A.B. <br>
	<br>

	Se&ntilde;or(a)
	Univ. <c:out value="${nombre_completo}"/><br>
	<u>Presente.-</u> <br><br>
	Ref.: <b>Designaci&oacute;n <u>Auxiliar de Docencia</u></b>
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
    Distinguido(a) Universitario (a): 
    <br><br>        
    Comunico a usted, que de acuerdo al Cap. VIII Art. 41,42,43,44 del Reglamento General de Auxiliar&iacute;a 
    de Docencia y en m&eacute;rito a su ex&aacute;men de <b>COMPETENCIA Y/O SUFICIENCIA</b>, ha sido designado(a) como <b>Auxiliar de Docencia</b> de la Carrera de <b><c:out value="${programa}"/></b> dependendiente de la Facultad de <b><c:out value="${area}"/></b>, 
    en la Asignatura de <b><c:out value="${sigla}"/> - </b> <b><c:out value="${materia}"/></b>
    a partir del
	<fmt:formatDate value="${fecha_i}" pattern="dd"/> de 
	<fmt:formatDate value="${fecha_i}" pattern="MMMM"/>  del a&ntilde;o  	
	<fmt:formatDate value="${fecha_i}" pattern="yyyy"/>            
    hasta el 
	<fmt:formatDate value="${fecha_f}" pattern="dd"/> de 
	<fmt:formatDate value="${fecha_f}" pattern="MMMM"/>  del a&ntilde;o  	
	<fmt:formatDate value="${fecha_f}" pattern="yyyy"/>, con una carga horaria de <c:out value="${carga_horaria}"/> Hrs./Mes.        
    <br><br>    
    Dese&aacute;ndole &eacute;xito en el desempe&ntilde;o de sus funciones, saludo a ud. muy atentamente.
    </font>
    </td>
    </tr>
</table> 
<br><br><br><br><br>

<table width="100%" border="0">
   <tr>
   <td width="100%" colspan="2">
   c.c.: Direcci&oacute;n de Carrera<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;D.A.F.<br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;D.E.F.<br>
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

<%@ include file="../../Inferior.jsp" %>
</div>
