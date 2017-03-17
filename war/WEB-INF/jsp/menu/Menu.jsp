<%@ include file="../SuperiorMenu.jsp" %>
  <table class="principal">
    <tr>
      <td align="center" class="encabezado" colspan="3"> 
        <img src="imagenes/encabezado/banner.jpeg"></img> 
      </td>
    </tr>
    <tr valign="top" height="10px">
      <td align="left" bgcolor="#eeeeee">
        &nbsp;&nbsp;&nbsp;<b>Usuario:</b> <c:out value="${cliente.nombres}"/>  <b> Rol: </b><c:out value="${cliente.rol}"/>   <c:out value="${cliente.almacen}"/>
      </td>
      <td align="right" bgcolor="#eeeeee">      
	<form name="formamenu" action='<c:url value="/cambiarRol.fautapo"/>' method="post">
        <c:if test="${fn:length(cliente.roles) > 1}"> 
          Rol actual&nbsp;::&nbsp;
          <select name="id_rol" class='comboRoles' onChange='javascript: document.formamenu.submit();'>
            <c:forEach var="roles" items="${cliente.roles}">
              <option value='<c:out value="${roles.id_rol}"/>' <c:if test="${cliente.id_rol == roles.id_rol}">selected</c:if> >
                <c:out value="${roles.rol}"/>
            </c:forEach>
          </select>
       </c:if>
      <input type="hidden" name="encabezado" value="si">
    </form>
      </td>
      <td align="right" bgcolor="#eeeeee">
	<a href='<c:url value="/logout.fautapo" />' target="_top" class="desconectar" width="89">
          Cerrar Sesi&oacute;n
        </a>
      </td>
    </tr>     
    <tr valign="top" height="10px">
      <td colspan="3" valign="top">
        <table width="100%" class="menubar" cellpadding="0" cellspacing="0" border="0">
          <tr>
	    <td class="menubackgr" style="padding-left:5px;"> 
		<div id="myMenuID"> </div>
		<script>
		<!--
		  var myMenu =[
		  
		    <c:forEach items="${listaEnlaces}" step="1" varStatus="contador">
	              <c:if test="${contador.index == 0 || listaEnlaces[contador.index][2] == 0}">	
		        <c:if test="${contador.index ==0}">
			  [null,'<c:out value="${listaEnlaces[contador.index][3]}"/>',null,'null',''
			</c:if>
			<c:if test="${listaEnlaces[contador.index][2] == 0 && contador.index != 0}">
		         ], _cmSplit ,[null,'<c:out value="${listaEnlaces[contador.index][3]}"/> ',null,'null',''
			    <c:if test="${contador.index == fil}">
			      ]
			    </c:if>
			</c:if>  
			
	              </c:if>
		      
		      <c:if test="${contador.index != 0 && listaEnlaces[contador.index][2] != 0}">
		        <c:if test="${listaEnlaces[contador.index][2] > listaEnlaces[contador.index-1][2]}">
			
			  <c:if test= "${listaEnlaces[contador.index][4] == '#' || listaEnlaces[contador.index][4]=='NINGUNO'}">
			    ,['<img src="imagenes/menu/<c:out value="${listaEnlaces[contador.index][5]}"/>">','<c:out value="${listaEnlaces[contador.index][3]}"/>',null,'null',''
			  </c:if>
			  <c:if test= "${listaEnlaces[contador.index][4] != '#' && listaEnlaces[contador.index][4]!='NINGUNO'}">
			    ,['<img src="imagenes/menu/<c:out value="${listaEnlaces[contador.index][5]}"/>">','<c:out value="${listaEnlaces[contador.index][3]}"/>','<c:url value="${listaEnlaces[contador.index][4]}"/>','marco',''
			  </c:if>			  
			  
			</c:if>
			<c:if test="${listaEnlaces[contador.index][2] <= listaEnlaces[contador.index-1][2]}">
			
			  <c:if test= "${listaEnlaces[contador.index][4] == '#' || listaEnlaces[contador.index][4]=='NINGUNO' }">
			    ] ,['<img src="imagenes/menu/<c:out value="${listaEnlaces[contador.index][5]}"/>">','<c:out value="${listaEnlaces[contador.index][3]}"/>',null,'null',''
			  </c:if>
			  <c:if test= "${listaEnlaces[contador.index][4] != '#' && listaEnlaces[contador.index][4]!='NINGUNO'}">
			    ] ,['<img src="imagenes/menu/<c:out value="${listaEnlaces[contador.index][5]}"/>">','<c:out value="${listaEnlaces[contador.index][3]}"/>','<c:url value="${listaEnlaces[contador.index][4]}"/>','marco',''
			  </c:if>
			  
			</c:if>
			<c:if test="${listaEnlaces[contador.index][2] > listaEnlaces[contador.index+1][2]}">
			  ]
			</c:if>			
		      </c:if>
		      
		      <c:if test="${listaEnlaces[contador.index+1][2] == 0 }">
		        <c:if test="${listaEnlaces[contador.index][2] ==2 }">
			  ]
			</c:if> 		        						
		      </c:if>	      
		      
		      <c:if test="${listaEnlaces[contador.index+1][2] == null}" >
		        <c:if test="${listaEnlaces[contador.index][2] == 2 }"> 
		          ]
			  ]
			  ]
			</c:if>
			<c:if test="${listaEnlaces[contador.index][2]==1}">
			  ]
			  ]
			</c:if>  
		      </c:if> 		      		       
		    </c:forEach>		    
		  ];
		
		cmDraw ('myMenuID', myMenu, 'hbr', cmThemeOffice, 'ThemeOffice'); 
		-->		
		</script>
	    </td>
	  </tr>
        </table>          
      </td>      
    </tr>
    <tr valign="top">
      <td colspan="3">
        <iframe name="marco" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" src="verCuerpo.fautapo">
	  Contenido de IFRAME
	</iframe>
      </td>
    </tr>
    <tr>
      <td class="pie" colspan="3">
        <table border="0" cellpadding="0" cellspacing="0" colspacing="0" valign='midle' align="center">
          <tr>
            <td>
              <font size=-2><b>Potenciado por :: </b></font>
            </td>
            <td>
              &nbsp;
              <a href="http://www.uabjb.edu.bo/" title="Universidad Autonoma del Beni Jose Ballivian" target="_blank">
                <img border="0" src="imagenes/pie/fautapo.png" >
              </a>
            </td>
            <td>
              &nbsp;
              <a href="http://www.springframework.org/" title="Spring - java/j2ee Application Framework" target="_blank">
                <img border="0" src="imagenes/pie/spring_80x15.png" >
              </a>
            </td>
            <td>
              &nbsp;
              <a href="http://tomcat.apache.org/" title="Apache Tomcat" target="_blank">
                <img border="0" src="imagenes/pie/tomcat-power.gif" >
              </a>
            </td>
            <td>
              &nbsp;
              <a href="http://www.postgresql.org/" title="PostgreSQL" target="_blank">
                <img border="0" src="imagenes/pie/poweredby_postgresql2.png" alt="postgres">
              </a>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
<%@ include file="../Inferior.jsp" %>