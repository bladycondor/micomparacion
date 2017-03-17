<%@ include file="../../Superior.jsp" %>
<script language='JavaScript' SRC="./validar.js">  </script>

<div class=titulo> Cambio clave(PIN) docente</div>
<br>

<table class="formulario">
  <tr>
    <th class="colh">Aviso !</th>
  </tr>
  <tr>
    <td  align="center" class="colb"> 
      <c:out value="${mensaje}"/>
    </td>
  </tr>
  <tr>
   <form name="fvolver" action="<c:url value="/cambioPinDocente/entrada.fautapo"/>" method="POST">
       <a class="volver" href="javascript:document.fvolver.submit();" >Volver</a></td> 
    </form>   
  </tr>       
</table>
        
<%@ include file="../../Inferior.jsp" %>  
  
  
  
  
  
  
  