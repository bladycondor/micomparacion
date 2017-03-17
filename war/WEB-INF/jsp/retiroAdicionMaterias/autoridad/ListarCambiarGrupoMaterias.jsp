<%@ include file="../../Superior.jsp" %>

<script language='JavaScript' SRC="../validar.js">  </script>

<div class="titulo">Retiro y adici&oacute;n de materias</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<br>
<form method=post action=<c:url value="/retiroAdicionMaterias/confirmarAccionProgramacionMaterias.fautapo"/> >

<table class="tabla">
<tr>
  <th>RU</th>
  <th>NOMBRES</th>
  <th>PLAN</th>
  <th>CARRERA</th>
  <th class=colh>TIPO PROGRAMACION</th>
  <th>TIPO EVALUACION</th>
  <th>GESTION</th>
  <th>PERIODO</th>
</tr> 
<tr>
  <td class=colb><c:out value="${datosEstudiante.id_estudiante}"/></td>
  <td class=colb><c:out value="${datosPersona.paterno}"/> &nbsp; <c:out value="${datosPersona.materno}"/> &nbsp; <c:out value="${datosPersona.nombres}"/></td>
  <td class=colb><c:out value="${datosEstudiante.id_plan}"/></td>
  <td class=colb><c:out value="${datosPrograma.programa}"/></td>
  <td class=colb align="center"> 
    <c:forEach var="parametro" items="${lParametros}" >
     <c:out value="${parametro.tipo_programacion}"/><br>
    </c:forEach>
  </td>
  <td class=colb><c:out value="${datosTipoEval.tipo_evaluacion}"/></td>
  <td class=colb><c:out value="${gestion}"/></td>
  <td class=colb><c:out value="${periodo}"/></td>
</table>
<br>

<table class="tabla">
<tr  align=center>
<th colspan="5"> CAMBIO DE GRUPO DE MATERIAS
</th>
</tr>
<tr align=center>
  <th>ELEGIR</th>
  <th>NIVEL</th>
  <th>SIGLA</th>
  <th>MATERIA</th>
  <th>GRUPO</th>
<c:set var="nombremateria" value=""/>  
<c:forEach var="materias" items="${lMaterias}" varStatus="contador">
  <c:if test="${materias.id_estado == 'A'}">  
  <c:if test="${materias.id_modelo_ahorro > 0}">
  <c:if test="${materias.materia != nombremateria}">
  <!-- ********** Esto es para el efecto ************ -->
  <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  <!-- ********** Fin  efecto ************ -->    
    <td></td>
    <td align="center"><b><c:out value="${materias.nivel_academico}"/></b></td>
    <td align="center"><b><c:out value="${materias.sigla}"/></b></td>
    <td  align="left"><b><c:out value="${materias.materia}"/></b></td>
    <td> </td>
    <c:set var="nombremateria" value="${materias.materia}"/>
   </tr>
   </c:if>
</c:if>
  <!-- ********** Esto es para el efecto ************ -->
  <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  <!-- ********** Fin  efecto ************ -->    
      <td>
        <c:if test="${materias.cupo_restante > 0}">
          <input type=checkbox name="materia" VALUE="<c:out value="${materias.id_materia}"/>:<c:out value="${materias.id_modelo_ahorro}"/>" >
        </c:if>
        <c:if test="${materias.cupo_restante == 0}">
          Sin plazas
        </c:if>
      </td>
    <c:if test="${materias.id_modelo_ahorro <= 0 }">
      <td align=center><b><c:out value="${materias.nivel_academico}"/><b>
      <td><b><c:out value="${materias.sigla}"/></b>
      <td align=left><b><c:out value="${materias.materia}"/><b>
    </c:if>
    <c:if test="${materias.id_modelo_ahorro > 0}">
      <td class="colb" colspan="2" align=right>-- &nbsp;
      <td class="colb" align=left><c:out value="${materias.modelo_ahorro}"/>
    </c:if>
    
    
    <td align=left>
      <c:if test="${materias.id_estado == 'A'}">
        <c:if test="${materias.cupo_restante > 0}">
           <table class="tabla">
	     <tr>
              <c:forEach var="grupos" items="${materias.grupos}">
	         <td><c:out value="${grupos.grupo}"/><br>
	             <input type=radio name='id_grupo:<c:out value="${materias.id_materia}"/>:<c:out value="${materias.id_modelo_ahorro}"/>' value="<c:out value="${grupos.id_grupo}"/>" 
		       <c:if test="${grupos.id_grupo == materias.id_grupo}">checked</c:if> ><br>
		     (<c:out value="${grupos.cupo_restante}"/>)
                 </td>		   
	      </c:forEach>
             </tr>
	   </table>  
        </c:if>
      </c:if>
    </td>
  </tr>
  </c:if>  
</c:forEach>
<tr class=colh>
  <td colspan="5" align=center>
    <input class="aceptar" type=submit value=Aceptar>
</table>
<input type="hidden" name="id_tipo_periodo"    value=<c:out value="${id_tipo_periodo}"/> >
<input type="hidden" name="id_estudiante"      value=<c:out value="${datosEstudiante.id_estudiante}"/> >
<input type="hidden" name="gestion"            value=<c:out value="${gestion}"/> >
<input type="hidden" name="periodo"            value=<c:out value="${periodo}"/> >
<input type="hidden" name="id_programa"        value=<c:out value="${id_programa}"/>>
<input type="hidden" name="accion"             value='<c:out value="${accion}"/>' >
<input type="hidden" name="id_tipo_evaluacion" value='<c:out value="${id_tipo_evaluacion}"/>' >
</form>

<%@ include file="../../Inferior.jsp" %>