<%@ include file="../../Superior.jsp" %>

<div class="titulo">Inscripci&oacute;n de materias</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<br>

<form method=post action=<c:url value="/inscripcionMateriasAutomatica/confirmarProgramacionMaterias.fautapo"/> >

<table class="tabla">
<tr>
  <th class=colh>RU </th>
  <th class=colh>NOMBRES</th>
  <th class=colh>PLAN</th>
  <th class=colh>PROGRAMA</th>
  <th class=colh>TIPO PROGRAMACION</th>
  <th class=colh>TIPO EVALUACI&Oacute;N</th>
  <th class=colh>GESTION</th>
  <th class=colh>PERIODO</th>
</tr>  
<tr>
  <td class=colb><c:out value="${datosEstudiante.id_estudiante}"/>
  <td class=colb><c:out value="${datosPersona.paterno}"/> &nbsp; <c:out value="${datosPersona.materno}"/> &nbsp; <c:out value="${datosPersona.nombres}"/>
  <td class=colb><c:out value="${datosEstudiante.id_plan}"/>
  <td class=colb><c:out value="${datosPrograma.programa}"/>
   <td class=colb align="center"> 
    <c:forEach var="parametro" items="${lParametros}" >
     <c:out value="${parametro.tipo_programacion}"/><br>
    </c:forEach>
  </td>
  <td class=colb><c:out value="${datosTipoEval.tipo_evaluacion}"/>
  <td class=colb><c:out value="${gestion}"/>
  <td class=colb><c:out value="${periodo}"/>
<tr>  
</table>
<br>

<table class="tabla">
<tr align="center">
  <th>ELEGIR</th>
  <th>NIVEL</th>
  <th>SIGLA</th>
  <th>MENCION</th>
  <th>MATERIA</th>
  <th>GRUPO</th>
 <th>OBSERVACION</th>

<c:set var="nombremateria" value=""/>
<c:set var="colordt" value=""/>
<c:forEach var="materias" items="${lMaterias}" varStatus="contador">
  
<c:if test="${materias.id_modelo_ahorro > 0}">
  <c:if test="${materias.materia != nombremateria}">
  <!-- ********** Esto es para el efecto ************ -->
  <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  <!-- ********** Fin  efecto ************ -->    
    <td></td>
    <td align="center"><b><c:out value="${materias.nivel_academico}"/></b></td>
    <td align="center"><b><c:out value="${materias.sigla}"/></b></td>
    <td align="center"><b><c:out value="${materias.mencion}"/></b></td>
    <td><b><c:out value="${materias.materia}"/></b></td>
    <td></td>
    <c:set var="nombremateria" value="${materias.materia}"/>
   </tr>
   </c:if>
</c:if>
  <!-- ********** Esto es para el efecto ************ -->
  <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  <!-- ********** Fin  efecto ************ -->   
    <td>
      <c:if test="${materias.id_estado == 'A'}">
        <font color=red>Inscrita</font>
      </c:if>
      <c:if test="${materias.id_estado != 'A'}">
        <c:if test="${materias.cupo_restante > '0'}">
          <input type=checkbox name="materia" VALUE="<c:out value="${materias.id_materia}"/>:<c:out value="${materias.id_modelo_ahorro}"/>" >
        </c:if>
        <c:if test="${materias.cupo_restante == '0'}">
          Sin plazas
        </c:if>
      </c:if>
    </td>
    <c:if test="${materias.id_modelo_ahorro <= 0 }">
      <td align="center"><b><c:out value="${materias.nivel_academico}"/></b></td>
      <c:if test="${materias.id_tipo_materia != 3}">
        <td align="center"><b><c:out value="${materias.sigla}"/></b></td>
        <td align="center"><b><c:out value="${materias.mencion}"/></b></td>
      </c:if>
      <c:if test="${materias.id_tipo_materia == 3}">
        <td colspan="2">&nbsp;</td>
      </c:if>
    </c:if>
    <c:if test="${materias.id_modelo_ahorro > 0}">
      <td class="colb" align="center">--</td>
      <td class="colb" align="center">-- </td>
      <td class="colb" align="center" aling="center"> --</td>    
      <td class="colb" align=left><c:out value="${materias.modelo_ahorro}"/></td>
    </c:if>
    <c:if test="${materias.id_modelo_ahorro <= 0}">
    <td><b><c:if test="${materias.id_tipo_materia == 3}">&nbsp; &nbsp; &nbsp;</c:if><c:out value="${materias.materia}"/></b></td>
    </c:if>
    <td>
      <c:if test="${materias.id_estado == 'A'}">
        <font color=red><c:out value="${materias.grupo}"/></font>
      </c:if>
      <c:if test="${materias.id_estado != 'A'}">
        <c:if test="${materias.cupo_restante > '0'}">
           <table class="tabla">
	     <tr>
              <c:forEach var="grupos" items="${materias.grupos}">
	         <td><font size=-1><c:out value="${grupos.grupo}"/>
	             <input type=radio name='id_grupo:<c:out value="${materias.id_materia}"/>:<c:out value="${materias.id_modelo_ahorro}"/>' value="<c:out value="${grupos.id_grupo}"/>">
		     (<c:out value="${grupos.cupo_restante}"/>)</font>
                 </td>		   
	      </c:forEach>
             </tr>
	   </table>  
        </c:if>
      </c:if>
    </td>
<td>



     <c:forEach var="materiasEst" items="${lMateriasEst}" varStatus="contador">
      <c:if test="${materias.id_materia == materiasEst.id_materia}">
         
           <c:if test="${materias.id_estado != 'A'}">
             <font color=red> Debe Programar</font>
           </c:if>
     
      </c:if>
    
    
     </c:forEach>

</td>
  </tr>
</c:forEach>
<tr>
  <td colspan="6" align=center>
    <input type=submit value=Aceptar>
</table>
<input type=hidden name="id_periodo"    value=<c:out value="${id_periodo}"/> >
<input type=hidden name="id_estudiante" value=<c:out value="${datosEstudiante.id_estudiante}"/> >
<input type=hidden name="gestion"       value='<c:out value="${gestion}"/>' >
<input type=hidden name="periodo"       value='<c:out value="${periodo}"/>' >
<input type="hidden" name="id_tipo_programacion" value="<c:out value='${id_tipo_programacion}'/>"> 
<input type="hidden" name="id_tipo_evaluacion" value="<c:out value='${id_tipo_evaluacion}'/>"> 
<br>
<c:if test="${!empty mensaje }">
<blink>
  <center>
    <div class='cuadroAviso'>
      <div class="titulo">Aviso</div>
      <c:out value="${mensaje}"/>
    </div>
  </center>
</blink>
</c:if>
</form>

<%@ include file="../../Inferior.jsp" %>