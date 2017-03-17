<%@ include file="../Superior.jsp" %>

<script language="JavaScript" type="text/javascript">
<!--
function imprimirFrame() {
  parent.frames["detalle"].focus();
  parent.frames["detalle"].print();
}
-->
</script>

<form name="forma" method=post>
<table width="100%" cellspacing=0 cellpadding=0>
  <tr class="colb">
    <td width="20%">
      <c:if test="${empty banderakardex && cantInformes == 1}">
        <table>
          <tr>
            <td>
	      <c:if test='${empty fechainicio  && empty fechafin  && empty fechadellunes && empty id_estado}'>
                <div><a class="volver" href="<c:url value="/listarMisPendientes.fautapo"><c:param name="aplicacion" value="${aplicacion}"/>&<c:param name="nro_pagina" value="${nro_pagina}"/></c:url>" target="marco"> Volver</a></div>
              </c:if> 
	      <!--VOLVER PARA AGRUPADOS--> 
              <c:if test='${!empty fechainicio  || !empty fechafin  || !empty fechadellunes || !empty id_estado}'>
	        <div><a class="volver" href="<c:url value="/listarMisPendientesAgrupadosPorEstado.fautapo"><c:param name="aplicacion" value="${aplicacion}"/><c:param name="fechainicio" value="${fechainicio}"/><c:param name="fechafin" value="${fechafin}"/><c:param name="fechadellunes" value="${fechadellunes}"/><c:param name="id_estado" value="${id_estado}"/></c:url>" target="marco"> Volver</a></div>
              </c:if>
              <!--Fin-->
            </td>
          </tr>
        </table>
      </c:if>

      <c:if test="${empty banderakardex && cantInformes != '1' && cantInformes != '0'}">
        <table>
          <tr>
            <td>
	      <c:if test='${empty fechainicio  && empty fechafin  && empty fechadellunes && empty id_estado }'>
               <div><a class="volver" href="<c:url value="/listarInformesActividad.fautapo"><c:param name="id_tramite" value="${id_tramite}"/>&<c:param name="id_proceso" value="${id_proceso}"/>&<c:param name="id_actividad" value="${id_actividad}"/>&<c:param name="aplicacion" value="${aplicacion}"/>&<c:param name="nro_pagina" value="${nro_pagina}"/></c:url>" target="marco">Volver</a></div>
              </c:if>
	      <!--VOLVER PARA AGRUPADOS--> 
              <c:if test='${!empty fechainicio  || !empty fechafin  || !empty fechadellunes || !empty id_estado }'>
	        <div><a class="volver" href="<c:url value="/listarInformesActividad.fautapo"><c:param name="id_tramite" value="${id_tramite}"/>&<c:param name="id_proceso" value="${id_proceso}"/>&<c:param name="id_actividad" value="${id_actividad}"/>&<c:param name="aplicacion" value="${aplicacion}"/><c:param name="fechainicio" value="${fechainicio}"/><c:param name="fechafin" value="${fechafin}"/><c:param name="fechadellunes" value="${fechadellunes}"/><c:param name="id_estado" value="${id_estado}"/></c:url>" target="marco">Volver</a></div>
	      </c:if>
              <!--Fin-->
	    </td>
          </tr>
        </table>
      </c:if>

      <! --  PARA KARDEX-->
      <c:if test="${!empty banderakardex}">
        <table>
          <tr>
            <td>
	      <c:if test='${empty fechainicio  && empty fechafin  && empty fechadellunes && empty id_estado}'>
                <div><a class="volver" href="<c:url value="/listarMisPendientesKardex.fautapo"><c:param name="aplicacion" value="${aplicacion}"/>&<c:param name="nro_pagina" value="${nro_pagina}"/>&<c:param name="id_proceso" value="${id_proceso}"/>&<c:param name="banderakardex" value="${banderakardex}"/></c:url>" target="marco"> Volver</a></div>
              </c:if> 
            </td>
          </tr>
        </table>
      </c:if>

      <c:if test="${!empty banderakardex && cantInformes != '1' && cantInformes != '0'}">
        <table>
          <tr>
            <td>
	      <c:if test='${empty fechainicio  && empty fechafin  && empty fechadellunes && empty id_estado }'>
               <div><a class="volver" href="<c:url value="/listarInformesActividadKardex.fautapo"><c:param name="id_tramite" value="${id_tramite}"/>&<c:param name="id_proceso" value="${id_proceso}"/>&<c:param name="id_actividad" value="${id_actividad}"/>&<c:param name="aplicacion" value="${aplicacion}"/>&<c:param name="nro_pagina" value="${nro_pagina}"/>&<c:param name="banderakardex" value="${banderakardex}"/></c:url>" target="marco">Volver</a></div>
              </c:if>
	    </td>
          </tr>
        </table>
      </c:if>
    </td>
    <td width="40%"><a class="enlace2" href="javascript:document.forma.submit()" onClick="imprimirFrame()";>Imprimir html</a></td>
    <td width="40%"><a class="enlace2" href='./informes/<c:out value="${nombre_informe}"/>.pdf'>Imprimir pdf</a></td>
  </tr>
</table>

</form>

<%@ include file="../Inferior.jsp" %>