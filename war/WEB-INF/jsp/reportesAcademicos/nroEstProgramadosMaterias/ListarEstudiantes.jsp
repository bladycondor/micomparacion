<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date" />

<table border="0" width="100%">
  <!-- SE REPITE-->
  <thead>
  <tr>
    <td width="100%" align="center" cellspancin="0" cellpading="0">
    <table width="100%">
      <tr>
        <td width="14%" align="center">
          <img src="<c:url value='${datosInstitucion.logo}'/>" border="0" ALT="logo institucion">
        </td>
        <td width="72%" align="center">
          <table width="100%" heigth="100%" cellpading="2" cellspacing="0" >
            <tr>
              <td align="center"><h1><b><c:out value='${datosInstitucion.institucion}'/></h1></td>
            <tr>
            <tr>
              <td align="center"><font size="3"><b><c:out value='${datosInstitucionsede.localidad}'/> - <c:out value='${datosInstitucionsede.departamento}'/> - <c:out value='${datosInstitucionsede.pais}'/></b></font></td>
            <tr>
            </tr>
              <td align="center"><c:out value='${datosInstitucion.actividad}'/></td>
            </tr>
          </table>
        </td>
        <td width="14%">
          Fecha : <a href='javascript: window.print()'><fmt:formatDate value="${now}" pattern="${formatoFecha}"/></a> 
        </td>
      </tr>
    </table>
    <hr>
    <table width="100%" align="center">
      <tr>
        <td align="center"><label><h1>Nro. DE ESTUDIANTES APROBADOS, REPROBADOS Y ABANDONOS</h1></label></td>
      </tr>
    </table>
    </td>  
  </tr>      
  </thead>
  <tr>
    <td>
      <table border="0" width="97%">
        <tr>
          <td>TIPO DE EVALUACI&Oacute;N ::</td>
          <td><c:out value="${datosTipoEvaluacion.tipo_evaluacion}"/></td>
          <td>PERIODO ::</td>
          <td><c:out value="${periodo}"/> - <c:out value="${gestion}"/></td>
        </tr>  
        <tr>
          <td>PROGRAMA::</td>
          <td><c:out value="${datosPrograma.programa}"/></td>
          <td>AREA::</td>
          <td><c:out value="${datosFacultad.facultad}"/></td>
        </tr>
        <tr>
          <td>PLAN ::</td>
          <td><c:out value="${datosPrgPlan.id_plan}"/></td>
          <td></td>
          <td></td>
        </tr>
      </table>
       <br>
     </td> 
  </tr>
  <tr>
    <td>
    <table class="tabla" border=1 width="97%">
      <tr>
        <th rowspan="2">Nro</th>
        <th rowspan="2">Nivel</th>
        <th rowspan="2">Sigla</th>
        <th rowspan="2">Materia</th>
        <th rowspan="2">Grupo</th>
        <th colspan="2">Nro. Aprobados</th>
        <th colspan="2">Nro. Reprobados</th>
        <th colspan="2">Nro. Abandonos</th>
        <th rowspan="2">TOTAL PROGRAMADOS</th>
      </tr>
      <tr>
        <c:forEach var="ltsexos" items="${lTiposSexos}">
          <th><c:out value="${ltsexos.tipo_sexo}"/></th>
        </c:forEach>
        <c:forEach var="ltsexos" items="${lTiposSexos}">
          <th><c:out value="${ltsexos.tipo_sexo}"/></th>    
        </c:forEach>
        <c:forEach var="ltsexos" items="${lTiposSexos}">
          <th><c:out value="${ltsexos.tipo_sexo}"/></th>    
        </c:forEach>
      </tr>
      <c:set var="total" value="0"/>
      <c:set var="totalAprob" value="0"/>
      <c:set var="totalReprob" value="0"/>
      <c:set var="totalAband" value="0"/>
      <c:forEach var="lmateria" items="${lMaterias}" varStatus="contador">
        <tr>
          <td><c:out value="${contador.count}"/></td>   
          <td align="center"><c:out value="${lmateria.nivel_academico}"/></td>
  	  <td><c:out value="${lmateria.sigla}"/></td>
          <td><c:out value="${lmateria.materia}"/></td>
          <td align="center"><c:out value="${lmateria.grupo}"/></td>

          <c:forEach var="ltsexos" items="${lTiposSexos}">
	    <c:set var="cont" value="0"/>
            <c:forEach var="lestudiante" items="${lEstudiantes}" varStatus="contador">
	      <c:if test="${lmateria.id_materia == lestudiante.id_materia && lmateria.id_grupo == lestudiante.id_grupo && lestudiante.id_tipo_sexo == ltsexos.id_tipo_sexo}">
	        <td align="center"><c:out value="${lestudiante.aprobados}"/></td>
	        <c:set var="cont" value="${cont+1}"/> 
	      </c:if>
	    </c:forEach>
	    <c:if test="${cont == '0'}">
	      <td align="center">0</td>
	    </c:if>
          </c:forEach>

          <c:forEach var="ltsexos" items="${lTiposSexos}">
	    <c:set var="cont" value="0"/>
            <c:forEach var="lestudiante" items="${lEstudiantes}" varStatus="contador">
	      <c:if test="${lmateria.id_materia == lestudiante.id_materia && lmateria.id_grupo == lestudiante.id_grupo && lestudiante.id_tipo_sexo == ltsexos.id_tipo_sexo}">
	        <td align="center"><c:out value="${lestudiante.reprobados}"/></td>
	        <c:set var="cont" value="${cont+1}"/> 
	      </c:if>
	    </c:forEach>
	    <c:if test="${cont == '0'}">
	      <td align="center">0</td>
	    </c:if>
          </c:forEach>

          <c:forEach var="ltsexos" items="${lTiposSexos}">
	    <c:set var="cont" value="0"/>
            <c:forEach var="lestudiante" items="${lEstudiantes}" varStatus="contador">
	      <c:if test="${lmateria.id_materia == lestudiante.id_materia && lmateria.id_grupo == lestudiante.id_grupo && lestudiante.id_tipo_sexo == ltsexos.id_tipo_sexo}">
	        <td align="center"><c:out value="${lestudiante.abandonos}"/></td>
	        <c:set var="cont" value="${cont+1}"/> 
	      </c:if>
	    </c:forEach>
	    <c:if test="${cont == '0'}">
	      <td align="center">0</td>
	    </c:if>
          </c:forEach>

          <td align="center"><c:out value="${lmateria.numero}"/></td>
      </c:forEach>
      <tr>
        <td class="colb" colspan="5">
          TOTALES:
        </td>
        <c:set var="total" value="0"/> 
        <c:set var="c" value="0"/> 
        <c:forEach var="ltsexos" items="${lTiposSexos}">
          <c:set var="cont" value="0"/>
          <c:forEach var="lestudiante" items="${lEstudiantes}">
	    <c:if test="${lestudiante.id_tipo_sexo == ltsexos.id_tipo_sexo}">
	      <c:set var="cont" value="${cont+lestudiante.aprobados}"/> 
	      <c:set var="total" value="${total+lestudiante.aprobados}"/> 
	    </c:if>
	  </c:forEach>
          <td class="colb" align="center"><c:out value="${cont}"/></td>   
	  <c:set var="c" value="${c+1}"/> 
        </c:forEach>

        <c:forEach var="ltsexos" items="${lTiposSexos}">
          <c:set var="cont" value="0"/>
          <c:forEach var="lestudiante" items="${lEstudiantes}">
	    <c:if test="${lestudiante.id_tipo_sexo == ltsexos.id_tipo_sexo}">
	      <c:set var="cont" value="${cont+lestudiante.reprobados}"/> 
	      <c:set var="total" value="${total+lestudiante.reprobados}"/> 
	    </c:if>
	  </c:forEach>
          <td class="colb" align="center"><c:out value="${cont}"/></td>   
	  <c:set var="c" value="${c+1}"/> 
        </c:forEach>

        <c:forEach var="ltsexos" items="${lTiposSexos}">
          <c:set var="cont" value="0"/>
          <c:forEach var="lestudiante" items="${lEstudiantes}">
	    <c:if test="${lestudiante.id_tipo_sexo == ltsexos.id_tipo_sexo}">
	      <c:set var="cont" value="${cont+lestudiante.abandonos}"/> 
	      <c:set var="total" value="${total+lestudiante.abandonos}"/> 
	    </c:if>
	  </c:forEach>
          <td class="colb" align="center"><c:out value="${cont}"/></td>   
	  <c:set var="c" value="${c+1}"/> 
        </c:forEach>
        <td class="colb" align="center">
          <c:out value="${total}"/> Programados
        </td>
      </tr>
    </table>
    </td>
  </tr>
</table>

<%@ include file="../../Inferior.jsp" %>