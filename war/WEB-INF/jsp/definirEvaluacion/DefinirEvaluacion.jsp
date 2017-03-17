<%@ include file="../Superior.jsp" %>

<script language='JavaScript' SRC="./validar.js">  </script>
<body onload='inicio(document.fevaluacion.cantidad:1)'>

<div class="titulo">Definir Evaluaci&oacute;n</div>
<br>

<form name="fvolver" method="POST" action='<c:url value="/definirEvaluacion/listarAsignaciones.fautapo"/>'>
  <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a>
  <input type="hidden" name="bandera"             value='1'>
  <input type=hidden name="id_docente"         value="<c:out value="${datosAsignacion.id_docente}"/>" >    
  <input type=hidden name="id_programa"        value="<c:out value="${id_programa}"/>" >
  <input type=hidden name="id_modelo_ahorro"   value="<c:out value="${datosAsignacion.id_modelo_ahorro}"/>" >
  <input type=hidden name="gestion"            value="<c:out value="${datosAsignacion.gestion}"/>">
  <input type=hidden name="periodo"            value="<c:out value="${datosAsignacion.periodo}"/>">
        </table>  
</form>

<table class="tabla" border="0">
  <tr>
    <th>CARRERA/PROGRAMA</th>
    <th>SIGLA</th>
    <th>MATERIA</th>
    <c:if test="{!empty materia_ahorro}"> <th>MATERIA AHORRO</th></c:if>
    <th>GRUPO</th>
    <th>GESTI&Oacute;N</th>
    <th>PERIODO</th>
    <th>FASE ACTUAL</th>
  <tr>    
    <td align="center"><c:out value="${programa}"/></td>
    <td align="center"><c:out value="${datosAsignacion.sigla}"/></td>    
    <td align="center"><c:out value="${materia}"/></td>    
    <c:if test="{!empty materia_ahorro}"><td class="colb" align="center"><c:out value="${materia_ahorro}"/></td></c:if>
    <td align="center"><c:out value="${datosAsignacion.grupo}"/></td>
    <td align="center"><c:out value="${datosAsignacion.gestion}"/></td>
    <td align="center"><c:out value="${datosAsignacion.periodo}"/></td>
    <td class="colb" align="center"><c:out value="${datosAsignacion.fase}"/></td>
   </tr>
</table>
<br>
<div class="h3"><b> Tipo de Evaluaci&oacute;n ::&nbsp;<c:out value="${datosAsignacion.tipo_evaluacion}"/></b></div><br>

<form name="fevaluacion" action="<c:url value="/definirEvaluacion/confirmarDefinicion.fautapo"/>" method="post">
  <table class="tabla" border=0 align="center">
    <tr>
      <th align="center">Nro.</th>
      <th align="center">TIPO NOTA</th>
      <th align="center">C.A</th>
      <th align="center">CANTIDAD</th>
      <th align="center">P.A</th>
      <th align="center">PORCENTAJE</th>
    </tr>

    <c:forEach var="nota" items="${lTiposNotas}" varStatus="contador">
      <!-- ********** Esto es para el efecto ************ -->
        <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
       <!-- ********** Fin  efecto ************ --> 
    
      <td align="center"><c:out value="${contador.count}"/></td>
      <td><c:out value="${nota.tipo_nota}"/></td>
      <td align="center"><c:out value="${nota.cantidad}"/></td>
      <td align="center"><input type="text" name="cantidad:<c:out value="${nota.id_tipo_nota}"/>" value="<c:out value="${nota.cantidad}"/>" maxlenght="3" size="3" onblur='validarNota(this,0,100)'></td>
      <td align="center"><c:out value="${nota.ponderacion}"/></td></td>
      <td align="center"><input type="text" name="ponderacion:<c:out value="${nota.id_tipo_nota}"/>" value="<c:out value="${nota.ponderacion}"/>" maxlenght="3" size="3" onblur='validarNota(this,0,100)'></td>
    </tr>
    </c:forEach>
    <tr>
      <td colspan="6" align="center"><input type="submit" value="Aceptar"></td>
    </tr>
  <input type=hidden name="id_asignacion"      value="<c:out value="${id_asignacion}"/>" >        
  <input type=hidden name="id_programa"        value="<c:out value="${id_programa}"/>" >
  <input type=hidden name="id_fase"            value="<c:out value="${datosAsignacion.id_fase}"/>" >
  <input type=hidden name="id_tipo_evaluacion" value="<c:out value="${datosAsignacion.id_tipo_evaluacion}"/>" >
  <input type=hidden name="id_modelo_ahorro"   value="<c:out value="${datosAsignacion.id_modelo_ahorro}"/>" >
  <input type=hidden name="id_departamento"    value="<c:out value="${id_departamento}"/>" >    
  </table>
  <table class="formulario">    
    <tr>
      <td class="cabeceraForm"><b>ACLARACIONES:</b></td>
    </tr>
    <tr>
      <td class="colb"><b><font color="red">*</font> TIPO NOTA:</b> Actividades a ser evaluadas.</td>
    </tr>
    <tr>
      <td class="colb"><b><font color="red">*</font> C.A:</b> Cantidad actual de actividades a ser evaluadas.</td>
    </tr>
    <tr>
      <td class="colb"><b><font color="red">*</font> CANTIDAD:</b> Numero de actividades a ser evaluadas.</td>
    </tr>
    
    <tr>
      <td class="colb"><b><font color="red">*</font> P.A:</b> Ponderaci&oacute;n actual de las actividades a ser evaluadas.</td>
    </tr>
    <tr>
      <td class="colb"><b><font color="red">*</font> PORCENTAJE:</b> Valor porcentual de las actividades a ser evaluadas.</td>
    </tr>
  </table>
</form>  

<%@ include file="../Inferior.jsp" %>