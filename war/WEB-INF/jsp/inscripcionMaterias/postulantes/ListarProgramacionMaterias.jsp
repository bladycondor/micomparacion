<%@ include file="../../Superior.jsp" %>

<div class="titulo">Inscripci&oacute;n de materias postulantes</div>
<div class="volver"><a href="javascript:history.back();">Volver</a></div>
<br>

<form method=post action=<c:url value="/inscripcionMateriasPostulante/confirmarProgramacionMaterias.fautapo"/> >

<table class="tabla">
<tr>
  <th class=colh>RP </th>
  <th class=colh>NOMBRES</th>
  <th class=colh>PLAN</th>
  <th class=colh>PROGRAMA</th>
  <th class=colh>TIPO PROGRAMACION</th>
</tr>  
<tr>
  <td class=colb><c:out value="${datosPostulante.id_postulante}"/>
  <td class=colb><c:out value="${datosPersona.paterno}"/> &nbsp; <c:out value="${datosPersona.materno}"/> &nbsp; <c:out value="${datosPersona.nombres}"/>
  <td class=colb><c:out value="${datosPostulante.id_plan}"/>
  <td class=colb><c:out value="${datosPrograma.programa}"/>
  <td class=colb align="center"> 
    <c:forEach var="parametro" items="${lParametros}" >
     <c:out value="${parametro.tipo_programacion}"/><br>
    </c:forEach>
  </td>
<tr>  
</table>
<br>

<table class="tabla">
<tr align="center">
  <th>ELEGIR</th>
  <th>NIVEL / MODULO</th>
  <th>SIGLA</th>
  <th>MENCION</th>
  <th>MATERIA</th>
  <th>GRUPO</th>
<c:forEach var="materias" items="${lMaterias}" varStatus="contador">
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
      <td align=center><c:out value="${materias.nivel_academico}"/>
      <td><c:out value="${materias.sigla}"/> 
      <td class="cabeceraForm" aling="center"><b><c:out value="${materias.id_mencion}"/></b></td>    
    </c:if>
    <c:if test="${materias.id_modelo_ahorro > 0}">
      <td align=center><c:out value="${materias.nivel_academico}"/>
      <td><c:out value="${materias.sigla}"/> 
      <td class="cabeceraForm" aling="center"><b><c:out value="${materias.id_mencion}"/></b></td>    
      <!--<td colspan=3 align=right>-- &nbsp;-->
      
    </c:if>
    <c:if test="${materias.id_modelo_ahorro > 0}">
      <td align=left>
        <table class="tabla" width=100%>
	  <tr><td align="center"> <c:out value="${materias.materia}"/></td></tr>
	  <tr> <th>SUBMATERIA</th></tr>
          <tr> <td><c:out value="${materias.modelo_ahorro}"/></td> </tr>
	</table>
      </td>
    </c:if>
    <c:if test="${materias.id_modelo_ahorro <= 0}">
    <td align=left><c:out value="${materias.materia}"/></td>
    </c:if>
    <td>
      <c:if test="${materias.id_estado == 'A'}">
        <font color=red><c:out value="${materias.grupo}"/></font>
      </c:if>
      <c:if test="${materias.id_estado != 'A'}">
        <c:if test="${materias.cupo_restante > '0'}">
           <table border=0 cellspacing=0 colpadding=0>
	     <tr>
              <c:forEach var="grupos" items="${materias.grupos}">
	         <td><font size=-1><c:out value="${grupos.grupo}"/><br>
	             <input type=radio name='id_grupo:<c:out value="${materias.id_materia}"/>:<c:out value="${materias.id_modelo_ahorro}"/>' value="<c:out value="${grupos.id_grupo}"/>"><br>
		     (<c:out value="${grupos.cupo_restante}"/>)</font>
                 </td>		   
	      </c:forEach>
             </tr>
	   </table>  
        </c:if>
      </c:if>
    </td>
  </tr>
</c:forEach>
<tr>
  <td colspan="6" align=center>
    <input type=submit value=Aceptar>
</table>
<input type=hidden name="id_periodo"    value=<c:out value="${id_periodo}"/> >
<input type=hidden name="id_postulante" value=<c:out value="${datosPostulante.id_postulante}"/> >
<input type=hidden name="gestion"       value='<c:out value="${gestion}"/>' >
<input type=hidden name="periodo"       value='<c:out value="${periodo}"/>' >
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