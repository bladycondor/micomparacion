<%@ include file="../../Superior.jsp" %>

<jsp:useBean id="now" class="java.util.Date"/>
<div class="titulo">Reimprimir Certificados</div>
<br>
<form name="forma" action="listarConceptosImpresion.fautapo" method="post" target="_blank">
  <input type="hidden" name="hora" value="<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss"/>">

<table class="formulario" >
  <tr>
    <th colspan=3 align="center">INTRODUZCA LOS DATOS</th>
  </tr>
  <tr>
    <td class="etiqueta">Usuario</td>
    <td class="etiqueta">::</td>
    <td><c:out value="${usuario}" /></td>
  </tr>
  <tr>
    <td class="etiqueta">Nro. de Certificado <font color='red'>(*)</font></td>
    <td class="etiqueta">::</td>
    <td><input type="text" name="nro_certificado"></td>
  </tr>
  
   <tr>
    <td class="etiqueta">Sede</td>
    <td class="etiqueta">::</td>
    <td>
      <!--<select name="id_tipo_evaluacion">
       <option value=""> - Elija una sede - </option>    
        <c:forEach var="tipo" items="${lTiposEvaluaciones}">
          <option value="<c:out value="${tipo.id_tipo_evaluacion}"/>"> <c:out value="${tipo.tipo_evaluacion}"/></option>
        </c:forEach>
      </select>-->
	   <select name="sede">
	   <option value="1">CENTRAL-TRINIDAD</option>
       <option value="8">RIBERALTA</option>    
  	   <option value="4">GUAYARAMERIN</option>
       </select>
	  
	  
    </td>
	
	
	
	
	
  </tr>
  
  <tr>
    <td class="etiqueta">Clave <font color='red'>(*)</font></td>
    <td class="etiqueta">::</td>
    <td><input type="password" name="clave<fmt:formatDate value="${now}" pattern="yyyyMMddhhmmss"/>"></td>
  </tr>
  <tr>
	<td align="center" colspan="3">
       <input class="aceptar" type="submit" value="Buscar">
    </td>
    <!--<td colspan="3" align="center"><input type="submit" value="Buscar" class="siguiente"></td>-->
  </tr>
  
  
  
  
</table>

</form>

<%@ include file="../../Inferior.jsp" %>