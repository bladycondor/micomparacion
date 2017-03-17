<%@ include file="Superior.jsp"%>

<br/>
<br/>
<blink>
  <center>
    <div class='cuadroAviso' >
      <div class="titulo">¡Aviso!</div>
      <font><c:out value="${mensaje}" escapeXml="false"/></font>
      <br>
      <br>
      <a class="volver" href="javascript:history.back();">Volver</a>
    </div>
  </center>
</blink>

<%@ include file="Inferior.jsp"%>