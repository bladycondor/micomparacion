<%@ include file="../Superior.jsp" %>
<script language='JavaScript' SRC="../js/ajax.js"></script>

<div class="titulo">Habilitar Postulantes</div>
<form name="fvolver" method="POST" action='<c:url value="/adminPlanesDeEstudio.fautapo"/>' >
  <div class="volver"><a href='javascript: document.fvolver.submit();' > Volver </a></div>
</form>

<form name="forma" action="<c:url value="/habilitarPst/listarPostulantes.fautapo"/>" method="POST">
  <table class="formulario">
    <tr>
      <th colspan="3">INTRODUZCA LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta">Gesti&oacute;n</td>
      <td class="etiqueta">::</td>
      <td> <c:out value="${gestion}"/><input type="hidden" name="gestion" value='<c:out value="${gestion}"/>' > </td>
    </tr>
  <tr>
    <td class="etiqueta">Periodo</td>
    <td class="etiqueta">::</td>
    <td><c:out value="${periodo}"/> <input type="hidden" name="periodo" value='<c:out value="${periodo}"/>'> </td>
  </tr>
    <tr>
      <td class="etiqueta">Facultad<font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>
      <td>
        <select id='id_facultad' name='id_facultad' size='1'
          onChange="poblar('id_facultad', this.options[this.selectedIndex].value); document.forma.facultad.value = Afacultades[document.forma.id_facultad.value];">
        </select>
      </td>
    </tr>
    <tr>
      <td class="etiqueta">Programa <font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>
      <td>
        <select id='id_programa' name='id_programa' size='1'
          onChange="poblar('id_programa', this.options[this.selectedIndex].value); document.forma.id_programa.value = Aprogramas[document.forma.id_programa.value];">
        </select>
      </td>
    </tr>
  </table>
  <center>
    <input type="submit" class="siguiente" value='Aceptar'>
  </center>
</form>
  
<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>

<script>
  var Afacultades = new Array();
  padre_hijo[h] = new Array ("id_facultad", "''", "<c:out value = "${id_facultad}"/>");
  combo[h] = new Array();
  combo[h][0] = new Array("0", "- Elija una opcion -", "");
  <c:forEach var = "lista" items = "${lFacultades}" varStatus="contador">
    Afacultades[<c:out value = "${lista.id_facultad}"/>] = '<c:out value = "${lista.facultad}"/>';
    combo[h][<c:out value = "${contador.count}"/>] = new Array("<c:out value = "${lista.id_facultad}"/>", "<c:out value = "${lista.facultad}"/>", "");
  </c:forEach>
  h++;
</script>

<script>
  var Aprograma = new Array();
  padre_hijo[h] = new Array ("id_programa", "id_facultad", "<c:out value = "${id_programa}"/>");
  combo[h] = new Array();
  combo[h][0] = new Array("0", "- Elija una opcion -", "");
  <c:forEach var = "cod" items = "${lProgramas}" varStatus = "programac">
    Aprograma[<c:out value = "${cod.id_programa}"/>] = '<c:out value = "${cod.programa}"/>';
    combo[h][<c:out value = "${programac.count}"/>] = new Array("<c:out value = "${cod.id_programa}"/>", "<c:out value = "${cod.programa}"/>", "<c:out value = "${cod.id_facultad}"/>");
  </c:forEach>
  h++;
</script>

<script language="JavaScript">
  iniciar();
</script>

<%@ include file="../Inferior.jsp" %>