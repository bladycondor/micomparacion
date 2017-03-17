<%@ include file="../../Superior.jsp" %>
<jsp:useBean id="now" class="java.util.Date"/>
<div class="titulo">Ver Datos Del Estudiante</div>
<a class="volver" href="javascript:history.back();">Volver</a>
<br>

<table class="formulario" width="100%">
  <tr>
    <th> DATOS GENERALES</th>
    <th> DATOS PROGRAMA </th>
    <th> FOTO ESTUDIANTE</th>
  </tr>
  <tr>
    <td width="40%">
      <table class="tabla" width="100%">
        <tr>
          <td class="colb">Ap. Paterno</td>
          <td class="colb">Ap. Materno</td>
          <td class="colb">Nombres</td>
        </tr>
        <tr>
          <td><c:out value='${datosPrs.paterno}'/></td>
          <td><c:out value='${datosPrs.materno}'/></td>
          <td><c:out value='${datosPrs.nombres}'/></td>
        </tr>
        <tr>
 	  <td class="colb">DIP</td>
	  <td class="colb">Sexo</td>
 	  <td class="colb">Estado Civil</td>
        </tr>
        <tr>
	  <td><c:out value='${datosPrs.dip}'/></td>
	  <td><c:out value='${datosEstudiante.tipo_sexo}'/></td>
	  <td><c:out value='${datosEstudiante.tipo_estado_civil}'/></td>
        </tr>
        <tr>
	  <td class="colb">Domicilio</td>
	  <td class="colb">Tel&eacute;fono</td>
 	  <td class="colb">Correo</td>
        </tr>
        <tr>
 	  <td><c:out value='${datosPrs.direccion}'/></td>
	  <td><c:out value='${datosPrs.telefono}'/></td>
	  <td><c:out value='${datosPrs.correo}'/></td>
        </tr>
        <tr>
	  <td class="colb">Celular</td>
	  <td class="colb">Empresa Telef&oacute;nica</td>
	  <td class="colb">Grupo Sanguineo</td>
        </tr>
        <tr>
	  <td><c:out value='${datosPrs.celular}'/></td>
	  <td><c:out value='${datosEstudiante.tipo_empresa_telefonica}'/></td>
	  <td><c:out value='${datosPrs.tipo_sanguineo}'/></td>
        </tr>
        <tr>
	  <th colspan="3"> Lugar de Nacimiento</th>
        </tr>
        <tr>
	  <td class="colb">Pa&iacute;s Nac.</td>
	  <td class="colb">Departamento Nac.</td>
	  <td class="colb">Provincia Nac. <br>
        </tr>
        <tr>
	  <td><c:out value='${datosEstudiante.pais}'/></td>
	  <td><c:out value='${datosEstudiante.departamento}'/></td>
	  <td><c:out value='${datosEstudiante.provincia}'/></td>
        <tr>
        <tr>
          <td class="colb"> Localidad Nac.</td>
          <td class="colb" coslpan="2"> Fecha de Nacimiento</td>
		  <td class="colb"></td>
        </tr>
        <tr>
          <td><c:out value='${datosEstudiante.localidad}'/></td>
          <td><c:out value="${datosPrs.fec_nacimiento}"/></td>
          <td></td>
        </tr>
        <tr>
	  <th colspan="3">Datos de Bachillerato</th>
        </tr>
        <tr>
          <td class="colb">Tipo Instituci&oacute;n</td>
          <td class="colb">Colegio</td>
          <td class="colb">Turno</td>
        </tr>
        <tr>
          <td><c:out value='${datosCol.tipo_institucion}'/></td>
          <td><c:out value='${datosCol.colegio}'/></td>
          <td><c:out value='${datosCol.tipo_turno}'/></td>
        </tr>
        <tr>
 	  <td class="colb">A&ntilde;o de Bachiller</td>	  
	  <td class="colb"></td>
	  <td class="colb"></td>
        </tr>
        <tr>
	  <td><c:out value='${datosCol.anio_egreso}'/></td>	  
	  <td></td>
	  <td></td>
        </tr>
        <tr>
	  <th colspan="3">Datos Profesionales</th>
        </tr>
        <tr>
	  <td class="colb">Titulo Adquirido</td>
	  <td class="colb">Universidad</td>	 
      <td class="colb"></td>	  
        </tr>
        <tr>
	  <td><c:out value='${datosPrs.titulo}'/></td>	  
	  <td><c:out value='${datosPrs.nro_seguro_medico}'/></td>
	  <td></td>
        </tr>

        <tr>
	  <th colspan="3">Datos Adicionales</th>
        </tr>
        <tr>
	  <td class="colb">Nro. Hijos</td>
	  <td class="colb">Nro. Dependientes</td>
      <td class="colb"></td>		  
        </tr>
        <tr>
	  <td><c:out value='${datosPrs.nro_hijos}'/></td>
	  <td><c:out value='${datosPrs.nro_dependientes}'/></td>
	  <td></td>
        </tr>
      </table>
    </td>
    <td valign="top" width="40%">
      <table class="formulario" width="100%">
        <tr>
          <td class="etiqueta">Fecha de Inscripci&oacute;n ::</td>
          <td><fmt:formatDate value="${datosEstudiante.fec_inscripcion}" pattern="dd/MM/yyyy"/></td>
        </tr>
        <tr>
          <td class="etiqueta">Programa :: </td>
          <td><c:out value="${datosEstudiante.programa}"/></td>
        </tr>
        <tr>
	  <td class="etiqueta"> Plan ::</td>
          <td><c:out value="${datosEstudiante.id_plan}"/></td>
        </tr>
        <tr>
          <td class="etiqueta">Tipo Grado :: </td>
          <td><c:out value="${datosEstudiante.tipo_grado}"/></td>
        </tr>
        <tr>
          <td class="etiqueta">Tipo Admisi&oacute;n / Trámite Academico Administrativo ::
          <td><c:out value="${datosEstudiante.tipo_admision}"/></td>
        </tr>
        <tr>
	  <td class="etiqueta">Tipo Clasificaci&oacute;n ::</td>
          <td>ACTUAL :: <c:out value="${datosClas.tipo_clasificacion}"/> <br>
              INICIAL :: <c:out value="${datosClas.tipo_clasificacion_inicial}"/></td>
          </td>
        </tr>
        <tr>
	  <td class="etiqueta">Tipo de Estudiante</td>
          <td><c:out value="${datosEstudiante.tipo_estudiante}"/></td>
        </tr>
        <tr>
	  <th colspan="2"> DATOS <br> MATRICULAS ANTERIORES </th>
        </tr>
	<tr>
	  <td colspan="2">
      	    <table class="tabla" width="100%">
	      <tr>
	       <td class="colh">? </td>
	       <td class="colh">Nro. MATRICULA </td>
	       <td class="colh">GESTION </td>
	       <td class="colh">PERIODO </td>
	       <td class="colh">FECHA </td>
	       <td class="colh">ESTADO<br>DE MATRICULA </td>
	     </tr>
	     <c:forEach var="lMatricula" items="${lMatriculasEstudiante}" varStatus="contador">
	     <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
	       <td> <c:out value='${contador.count}'/> </td>
	       <td> <c:out value='${lMatricula.id_matricula}'/> </td>
	       <td> <c:out value='${lMatricula.gestion}'/> </td>
	       <td> <c:out value='${lMatricula.periodo}'/> </td>  </td>
	       <td> <fmt:formatDate value='${lMatricula.fec_registro}' pattern='dd/MM/yyyy'/> </td>  </td>
	       <td> <font color="red" > <c:out value='${lMatricula.id_estado}'/></font> </td>  </td>
	     </tr>
	     </c:forEach>
            </table>
	  </td>
        </tr>
      </table>
    </td>
    <td valign="top" align="center" width="20%">
      <table align="center" width="100%">
        <c:forEach var="listaFoto" items="${lImagenes}" varStatus="contador">
          <tr>
            <td align="center">
              <img  src='<c:url value="/"/>adjuntosMi/fotosEstudiantes/<c:out value="${listaFoto.adjunto}"/>' width="100" height="100" border="1"/>
            </td>
          </tr>
        </c:forEach>
        <tr>
 	  <th>RU : <c:out value='${datosEstudiante.id_estudiante}'/></th>
	</tr>
      </table>
    </td>
  </tr>
</table>

<table class="formulario" width="100%">
  <tr>
    <td valign="top" witdh="50%">
      <table class="tabla" width="100%">
        <tr>
          <th colspan="4">DOCUMENTOS PRESENTADOS</th>
        </tr>
        <tr>
          <td class="colh">? </td>
	  <td class="colh">TIPO DOCUMENTO </td>
	  <td class="colh">PRESENT&Oacute; </td>
	  <td class="colh">OBSERVACION </td>
        </tr>
	<c:if test="${empty lPrsDocumentosTodo }">
	  <tr>
	  <td colspan="4"> <div class="nota"> No tiene documentos registrados </div></td>
	</tr>
	</c:if>
        <c:forEach var="lDocumento" items="${lPrsDocumentosTodo}" varStatus="contadorClas">
	  <tr <c:if test="${(contadorClas.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  	    <td><c:out value='${contadorClas.count}'/></td>
	    <td><c:out value='${lDocumento.tipo_documento}'/></td>
            <td><c:if test="${lDocumento.presento == true}"> Si</c:if> &nbsp;
	        <c:if test="${lDocumento.presento == false}"> No</c:if>
	    </td>
	    <td><c:out value='${lDocumento.observacion}'/></td>
          </tr>
        </c:forEach>
      </table>
    </td>
    <td valign="top" witdh="50%">
      <table class="tabla" width="100%">
        <tr>
	  <th colspan="6">PR&Oacute;RROGAS REALIZADAS</th>
	</tr>
	<tr>
	  <td class="colh">Nro. </td>
	  <td class="colh">DOCUMENTO </td>
	  <td class="colh">TIPO COMPROMISO </td>
	  <td class="colh">GESTION DE PRORROGA </td>
	  <td class="colh">DETALLE</td>
	  <td class="colh">FECHA DE VENCIMIENTO </td>
	</tr>
	<c:if test="${empty lPrsCompromisosTodo }">
	  <tr>
	  <td colspan="6"> <div class="nota"> No tiene pr&oacute;rrogas registradas </div></td>
	</tr>
	</c:if>
	<c:forEach var="lCompromisoT" items="${lPrsCompromisosTodo}" varStatus="contadorC">
	  <tr <c:if test="${(contadorC.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
	    <td><c:out value='${contadorC.count}'/></td>
	    <td><c:out value='${lCompromisoT.tipo_documento}'/></td>
	    <td><c:out value='${lCompromisoT.tipo_compromiso}'/></td>
        <td><c:out value="${lCompromisoT.gestion}"/> / <c:out value="${lCompromisoT.periodo}"/></td>
	    <td><c:out value='${lCompromisoT.observacion}'/> </td>
	    <td><c:out value='${lCompromisoT.fec_vencimiento}'/></td>
	  </tr>
	</c:forEach>
      </table>
    </td>
  </tr>
  <tr>
    <td valign="top" colspan="1">
      <table class="tabla" width="100%">
        <tr>
          <th colspan="6">DATOS DE RECIBOS ACTIVOS</th>
        </tr>
        <tr>
          <td class="colh">RECIBO </td>
	  <td class="colh">TOTAL </td>
	  <td class="colh">GESTION </td>
	  <td class="colh">PERIODO </td>
	  <td class="colh">FECHA PAGO </td>
	  <td class="colh">DETALLE </td>

        </tr>
	<c:if test="${empty lDetalleVentas}">
	  <tr>
	  <td colspan="6"> <div class="nota"> El estudiante no presenta recibos realizados </div></td>
	</tr>
	</c:if>
        <c:forEach var="lVentas" items="${lDetalleVentas}" varStatus="contadorD">
	  <tr <c:if test="${(contadorD.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  	    <td><c:out value='${lVentas.nro_recibo}'/></td>
	    <td><c:out value='${lVentas.total}'/></td>
	    <td><c:out value='${lVentas.gestion}'/></td>
	    <td><c:out value='${lVentas.periodo}'/></td>
		<td><fmt:formatDate value='${lVentas.fec_pago}' pattern='dd/MM/yyyy'/></td>
		<td><c:out value='${lVentas.perfil}'/></td>
          </tr>
        </c:forEach>
      </table>
    </td>

	  <td valign="top" colspan="1">
      <table class="tabla" width="100%">
        <tr>
          <th colspan="6">TRANSACCIONES ENTIDADES FINANCIERAS</th>
        </tr>
        <tr>
      <td class="colh">ENTIDAD </td>
	  <td class="colh">TOTAL</td>
	  <td class="colh">GESTION </td>
	  <td class="colh">PERIODO </td>
	  <td class="colh">FECHA PAGO</td>
	  <td class="colh">DETALLE </td>
      </tr>
	<c:if test="${empty lDetalleVentasEntidad}">
	  <tr>
	  <td colspan="6"> <div class="nota"> El estudiante no presenta transacciones realizadas </div></td>
	</tr>
	</c:if>
        <c:forEach var="lEntidades" items="${lDetalleVentasEntidad}" varStatus="contadorF">
	  <tr <c:if test="${(contadorF.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  	    <td><c:out value='${lEntidades.perfil}'/></td>
	    <td><c:out value='${lEntidades.total}'/></td>
	    <td><c:out value='${lEntidades.gestion}'/></td>
		<td><c:out value='${lEntidades.periodo}'/></td>
	    <td><c:out value='${lEntidades.nro_recibo}'/></td>
		<td>Matricula Universitaria</td>
          </tr>
        </c:forEach>
      </table>
    </td>

  </tr>

  <tr>
    <td colspan="1">
      <table class="tabla" width="100%">
        <tr>
          <th colspan="5">DEUDAS DEL ESTUDIANTE</th>
        </tr>
        <tr>
          <td class="colh">? </td>
	  <td class="colh">TIPO DE DEUDA </td>
	  <td class="colh">GESTION </td>
	  <td class="colh">PERIODO </td>
	  <td class="colh">OBSERVACIÓN </td>
        </tr>
	<c:if test="${empty lDeudasEstudiante }">
	  <tr>
	  <td colspan="5"> <div class="nota"> El estudiante no presenta tener deudas </div></td>
	</tr>
	</c:if>
        <c:forEach var="lDeudas" items="${lDeudasEstudiante}" varStatus="contadorD">
	  <tr <c:if test="${(contadorD.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  	    <td><c:out value='${contadorD.count}'/></td>
	    <td><c:out value='${lDeudas.tipo_deuda}'/></td>
	    <td><c:out value='${lDeudas.gestion}'/></td>
	    <td><c:out value='${lDeudas.periodo}'/></td>
		<td><c:if test="${lDeudas.cancelado == true}"> Deuda Cancelada</c:if> &nbsp;
	        <c:if test="${lDeudas.cancelado == false}"> Deuda Pendiente</c:if>
	    </td>
          </tr>
        </c:forEach>
      </table>
    </td>
		  <td valign="top" colspan="1">
      <table class="tabla" width="100%">
        <tr>
          <th colspan="6">DATOS DE AUXILIARIA DE DOCENCIA</th>
        </tr>
        <tr>
          <td class="colh">GESTION </td>
	  <td class="colh">PERIODO </td>
	  <td class="colh">SIGLA </td>
	  <td class="colh">MATERIA </td>
	  <td class="colh">CARGA HORARIA </td>
      </tr>
	<c:if test="${empty lDetalleAuxiliares}">
	  <tr>
	  <td colspan="5"> <div class="nota"> El estudiante no presenta Auxiliarias de Docencia </div></td>
	</tr>
	</c:if>
        <c:forEach var="lAuxiliares" items="${lDetalleAuxiliares}" varStatus="contadorF">
	  <tr <c:if test="${(contadorF.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
  	    <td><c:out value='${lAuxiliares.gestion}'/></td>
	    <td><c:out value='${lAuxiliares.periodo}'/></td>
	    <td><c:out value='${lAuxiliares.sigla}'/></td>
		<td><c:out value='${lAuxiliares.materia}'/></td>
	    <td><c:out value='${lAuxiliares.carga_horaria}'/></td>
          </tr>
        </c:forEach>
      </table>
    </td>

  </tr>
</table>

<%@ include file="../../Inferior.jsp" %>
