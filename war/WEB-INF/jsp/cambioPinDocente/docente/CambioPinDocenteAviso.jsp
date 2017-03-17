<%@ include file="../../Superior.jsp" %>
<script language='JavaScript' SRC="./validar.js">  </script>

<div class=titulo> Cambio clave(PIN) docente</div>
<br>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<form action="<c:url value="/cambioPinDocente/ingresarNuevoPin.fautapo"/>" method="POST">
<table class="formulario">
  <tr>
    <th class="colh">Atenci&oacute;n señor/a docente:<c:out value="${nombres}"/></th>
  </tr>
  <tr>
     <th>RECOMENDACIONES:</th>
  </tr>
  <tr>
     <td class="colb"> <b>* Digite un m&aacute;ximo de 10 caracteres y un m&iacute;nimo de 6.</b></td>
  </tr>
  <tr>
     <td class="colb"> <b>* Utilize caracteres validos [A-Z],[a-z] y [1-9].</b></td>
  </tr>
  <tr>
     <td class="colb"> <b> * No utilize palabras del diccionario ni nombres propios.</b></td>
  </tr>
  <tr>
     <td class="colb"> <b> * Invente una palabra que pueda recordar.</b></td>
  </tr>
   <tr>
     <td class="colb"> <b> * Componga palabras e inserte números.</b></td>
  </tr>
  <tr>
       <td align="center"><input class="aceptar" type=submit value="Buscar"></td> 
  </tr>       
   </table>
</form>      
        
<%@ include file="../../Inferior.jsp" %>  
  
  
  
  
  
  
  