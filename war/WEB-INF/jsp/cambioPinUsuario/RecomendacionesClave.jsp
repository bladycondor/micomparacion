<%@ include file="../Superior.jsp" %>

<body onload='inicio(document.forma.clave)'>

<div class="titulo">Cambio clave(PIN)</div>
<br>

<form method="post" action='<c:url value="/registrarClave.fautapo"/>'>
  <table class="formulario">
    <tr>
      <th>RECOMENDACIONES</th>
    </tr>
    <tr>
      <td class="etiqueta">Digite un m&aacute;ximo de 10 caracteres y un m&iacute;nimo de 6</td>
    </tr>
    <tr>
      <td class="etiqueta">Utilice caracteres v&aacute;lidos como [A-Z], [a-z], [0-9]</td>
    </tr>
    <tr>
      <td class="etiqueta">No utilice palabras del diccionario ni nombres propios</td>
    </tr>
    <tr>
      <td class="etiqueta">Componga palabras combinando letras con n&uacute;meros</td>
    </tr>
    <tr>
      <td align="center"><input type="submit" value="Siguiente" class="siguiente"></td>
    </tr>
  </table>
</form>

<%@ include file="../Inferior.jsp" %>