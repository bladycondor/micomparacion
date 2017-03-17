<%@ include file="../Superior.jsp"%>

<div class="titulo"> Administrar Usuarios</div>
<br>
<table>
  <tr>
    <td>
     <form name="fvolver" action="<c:url value='entrada.fautapo'/>" method="post">
      <div> <a class="volver" href="javascript:document.fvolver.submit();"> Volver</a> </div>
       <input type="hidden" name="pagina" value=<c:out value="${pagina}"/> >
    </form>
    </form>
    </td>
  </tr>
</table>
<br>
<table witdh="97%">
  <tr>
    <td>
      <form name="forma" method="POST">
        <div class="agregar">
          <a href="<c:url value="nuevoUsuario.fautapo"><c:param name="accion" value="Adicionar"/></c:url>">Nuevo</a>
        </div>
      </form>
    </td>
    <td align="right">  
      <form action="<c:out value="${direccion}"/>" method=post name="forma">
       <table width="100%">
          <tr>
            <td align="right" nowrap>
              <c:if test='${pagina > 1}'>
            	  <a href=<c:out value="${direccion}"/>?&pagina=1&patron=<c:out value="${patron}"/>> &lt;&lt; Primera P&aacute;gina</a>
                <a href=<c:out value="${direccion}"/>?&pagina=<c:out value="${pagina - 1}"/>&patron=<c:out value="${patron}"/>>&lt; Anterior</a>
              </c:if>
                    -
            	<a href=<c:out value="${direccion}"/>?pagina=<c:out value="${pagina+1}"/>&patron=<c:out value="${patron}"/>>Siguiente > </a>
            </td>
          </tr>
        </table>
      </form>
    </td>
   </tr>
   <tr>
     <td colspan="2">
       <table class="tabla">  
         <tr>
           <th> Nro </th>
           <th> USUARIO </th>
           <th> PERSONA </th>
	   <th> RECORDATORIO </th>
           <th> MODIFICAR </th>
           <th> ELIMINAR </th>
         </tr>
         <c:forEach var="lista" items="${lUsuarios}" varStatus="contador">
           <!-- ********** Esto es para el efecto ************ -->
             <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
           <!-- ********** Fin  efecto ************ -->
           <td align="center"><c:out value="${contador.count}"/></td>
           <td><c:out value = "${lista.id_usuario}"/></td>
           <td><c:out value = "${lista.nombres}"/></td>
	   <td><c:out value = "${lista.recordatorio}"/></td>
           <form name=formaM<c:out value="${contador.count}"/> method=post action='<c:url value="nuevoUsuario.fautapo"/>'>
             <td>     
               <div class="modificar"><a href="javascript:document.formaM<c:out value="${contador.count}"/>.submit();">Modificar</a></div>
         	  <input type="hidden" name="id_usuario" value=<c:out value="${lista.id_usuario}"/> >
		  <input type="hidden" name="id_persona" value=<c:out value="${lista.id_persona}"/> >
         	  <input type="hidden" name="accion" value='Modificar'>
             </td>
           </form>
           <form name=formaE<c:out value="${contador.count}"/> method=post action='<c:url value="confirmarUsuario.fautapo"/>'>
             <td>     
               <div class="eliminar"><a href="javascript:document.formaE<c:out value="${contador.count}"/>.submit();"> Eliminar</a></div>
               <input type="hidden" name="id_usuario" value=<c:out value="${lista.id_usuario}"/> >
	       <input type="hidden" name="id_persona" value=<c:out value="${lista.id_persona}"/> >
               <input type="hidden" name="accion"  value='Eliminar'>
             </td>
           </form>
         </tr>
         </c:forEach>
       </table>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="right">  
      <form action="<c:out value="${direccion}"/>" method=post name="forma">
       <table width="100%">
          <tr>
            <td align="right" nowrap>
              <c:if test='${pagina > 1}'>
            	  <a href=<c:out value="${direccion}"/>?&pagina=1&patron=<c:out value="${patron}"/>> &lt;&lt; Primera P&aacute;gina</a>
                <a href=<c:out value="${direccion}"/>?&pagina=<c:out value="${pagina - 1}"/>&patron=<c:out value="${patron}"/>>&lt; Anterior</a>
              </c:if>
                    -
            	<a href=<c:out value="${direccion}"/>?pagina=<c:out value="${pagina+1}"/>&patron=<c:out value="${patron}"/>>Siguiente > </a>
            </td>
          </tr>
        </table>
      </form>
    </td>
   </tr>
</table>           
<%@ include file="../Inferior.jsp"%>