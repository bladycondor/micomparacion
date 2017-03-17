<%@ include file="../Superior.jsp" %>
<div class="Titulo">Confirmar</div>
<br>
<center>
<blink>
  <div class='cuadroAviso' >
    <div class="titulo">Aviso</div> 
  <c:out value="${mensaje}" /><br>
  </div>
</blink>
    <br/>
    <form method='post' action='moverNotasNoMatriculadas.fautapo'>
      <input type='submit' value='Aceptar' class="aceptar">
    </form>
</center>
<%@ include file="../Inferior.jsp" %>
