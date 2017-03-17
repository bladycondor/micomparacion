<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />

<div class="titulo">Imprimir Plan De Estudios</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<br>
<table class="tabla">
  <tr>
    <th>PROGRAMA</th>
    <td><c:out value='${datosPrograma.programa}'/></td>
  </tr> 
</table>  
<br>

<form name='forma' method="post" action="listarPlanEstudios.fautapo" target="_blank">
  <table class="formulario">
    <tr>
      <th colspan="3">INTRODUZCA LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta">RU</td>
      <td class="etiqueta">::</td>
      <td><c:out value="${datosEstudiante.id_estudiante}"/></td>
    </tr>
    <tr>
      <td class="etiqueta">Estudiante</td>
      <td class="etiqueta">::</td>
      <td>
        <c:out value="${datosPersona.nombres}"/> &nbsp;
        <c:out value="${datosPersona.paterno}"/> &nbsp;
        <c:out value="${datosPersona.materno}"/>
      </td>
    </tr>
    <tr>
      <td class="etiqueta">Programa</td>
      <td class="etiqueta">::</td>
      <td><c:out value="${datosPrograma.programa}"/></td>
    </tr>
    <tr>
      <td class="etiqueta">Plan</td>
      <td class="etiqueta">::</td>
      <td><c:out value="${datosEstudiante.id_plan}"/></td>
    </tr>
    <c:if test="${!empty datosMencion.mencion}">
      <tr>
        <td class="etiqueta">Menci&oacute;n del Estudiante</td>
        <td class="etiqueta">::</td>
        <td><c:out value="${datosMencion.mencion}"/></td>
      </tr>
      <input type="hidden" name="id_mencion" value="<c:out value='${datosMencion.id_mencion}'/>">
    </c:if>
    <c:if test="${empty datosMencion.mencion}">
      <tr>
        <td class="etiqueta"> Menci&oacute;n <font color='red'>(*)</font> </td>
        <td class="etiqueta">::</td>
        <td>
          <select name="id_mencion">
            <option value="0">-- seleccione --
            <c:forEach var="lista" items="${lMenciones}">
              <option value="<c:out value="${lista.id_mencion}"/>" <c:if test="${lista.id_mencion == datosMencion.id_mencion}"> selected </c:if>>
                <c:out value="${lista.mencion}"/>
              </option>
            </c:forEach>
          </select>
        </td>
      </tr>
    </c:if>
  </table>
  <center>
    <input type='submit' value='Aceptar' class="aceptar">
  </center>
  <input type="hidden" name="id_programa" value="<c:out value='${datosPrograma.id_programa}'/>">
  <input type="hidden" name="id_estudiante" value="<c:out value='${datosEstudiante.id_estudiante}'/>">
</form>

<%@ include file="../../Inferior.jsp" %>