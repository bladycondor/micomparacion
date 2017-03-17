<%@ include file="../Superior.jsp" %>

<div class="titulo">Cambio de Plan de Estudios</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>

<table class="tabla">
  <tr>
    <th>RU</th>
    <td class="colb"><c:out value="${datosEstudiante.id_estudiante}"/>
    <th>Estudiante</th>
    <td class="colb">
      <c:out value="${datosEstudiante2.nombres}"/> &nbsp; 
      <c:out value="${datosEstudiante2.paterno}"/> &nbsp;
      <c:out value="${datosEstudiante2.materno}"/>
    </td>
    <th>Programa</th>
    <td class="colb"><c:out value="${datosPrograma.programa}"/>
  </tr>
</table>
<br>

<form name="forma" action="<c:url value="/registrarCambioPlan.fautapo"/>" method="POST">
  <table class="formulario">
    <tr>
      <th colspan="3">INTRODUZCA LOS DATOS</th>
    </tr>
    <tr>
      <td class="etiqueta">Plan Actual</td>
      <td class="etiqueta">::</td>
      <td class="etiqueta"><c:out value="${datosEstudiante.id_plan}"/></td>
    </tr>
    <tr>
      <td class="etiqueta">Plan Nuevo <font color='red'>(*)</font> </td>
      <td class="etiqueta">::</td>
      <td>
        <select name="id_plan_nuevo">
          <option value="">-- seleccione --</option>
      	  <c:forEach var="lista" items="${lPlanesEstudio}">
	    <option value="<c:out value="${lista.plan}"/>"> 
	      <c:out value="${lista.tipo_grado}"/> - <c:out value="${lista.plan}"/>
	    </option>
	  </c:forEach>
        </select>
      </td>
    </tr>
  </table>
  <center>
    <input type="submit" class="siguiente" value='Guardar'>
  </center>
  <input type="hidden" name="id_estudiante" value="${datosEstudiante.id_estudiante}">
</form>

<div class="nota">Los campos con <font color='red'>(*)</font>, son obligatorios.</div>

<%@ include file="../Inferior.jsp" %>