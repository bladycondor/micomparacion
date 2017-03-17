<%@ include file="../Superior.jsp" %>

<script language='JavaScript' SRC="./validar.js">  </script>
<body onload='inicio(document.forma.clave)'>
  <div class="titulo">Lista de Reportes</div>
  <br>
  <form name=forma action="<c:url value='/listarReportes/listarReportes.fautapo'/>" method="POST">
    <table class="formulario">
      <tr>
        <th colspan=3 align="center">INTRODUZCA LOS DATOS</th>
      </tr>
      <tr>
        <td class="etiqueta">Usuario</td>
        <td class="etiqueta">::</td>
        <td><c:out value="${nombres}" /></td>
      </tr>
      <tr>
        <td class="etiqueta" align="right">Clave</td>
        <td class="etiqueta">::</td>
        <td><input type="password" name="clave"></td>
      </tr>
      <tr>
        <td colspan=3 align=center><input type="submit" value="Buscar"></td>
      </tr>
    </table>
  </form>
</body>

<%@ include file="../Inferior.jsp" %>