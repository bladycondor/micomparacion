<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>Sistema Integrado - Moxos</title>
  <link rel="stylesheet" href="<c:url value='/pagina.css'/>" type="text/css">
  <link rel="stylesheet" href="<c:url value='/basico.css'/>" type="text/css">
  <script language="JavaScript">
    var vRequisitos=new Array();
    <c:forEach var="lista" items="${lRequisitos}" varStatus="contador2">vRequisitos.push(new Array(<c:out value="${lista.id_materia}"/>,<c:out value="${lista.id_materia_ant}"/>));</c:forEach>
    function setLimpiar(){
      var celdillas=document.getElementsByName('celdilla');
      for(var i=0;i<celdillas.length;i++)
          celdillas[i].className='';
    }
    function setMarcarRequisitos(obj){
      setLimpiar();
      obj.className='materiaSeleccionada';
      for(var i=0;i<vRequisitos.length;i++)
        if(vRequisitos[i][0]==obj.id)document.getElementById(vRequisitos[i][1]).className='materiaRequisito';
    }
  </script>
</head>
<body>
<div class="titulo">Ver Plan de Estudios</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<br>

<table class="tabla">
<tr>
  <th>Programa</th>
  <td><c:out value="${programa.programa}"/></td>
  <th>Plan</th>
  <td><c:out value="${datosPrgPlan.id_plan}"/></td>
</tr>
</table>

<br>
<c:set var="_nivel_ant" value="0"/>
<table class="tabla">
<tbody id="plancito">
  <c:forEach var="materia" items="${lMaterias}">
    <c:if test="${materia.nivel_academico!=_nivel_ant}"><tr><th><b>Nivel <c:out value="${materia.nivel_academico}"/></b></th></c:if>
    <td id="<c:out value="${materia.id_materia}"/>" name="celdilla" onClick="setMarcarRequisitos(this)">
      <c:out value="${materia.materia}"/><br><b><c:out value="${materia.sigla}"/></b>
    </td>
    <c:if test="${materia.nivel_academico!=_nivel_ant}"><c:set var="_nivel_ant" value="${materia.nivel_academico}"/></c:if>
  </c:forEach>
</tbody>
</table>
</body>
</html>