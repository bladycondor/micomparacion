<%@ include file="../Superior.jsp" %>
<div class="titulo"> Reportes por proceso </div>
<br>
<form name=forma method="POST" action='<c:url value="listarCamposReporte.fautapo"/>'>
  <input type="hidden" name="sw" value='<c:out value="${sw}"/>'>

  <table class="formulario">
    <tr>
      <th>Tipo de proceso</th>
      <th>::</th>
      <td>
        <select name="id_proceso" OnChange="javascript: document.forma.submit()">
	  <option value="0">-- Seleccionar --</option>
          <c:forEach var="lista" items="${lProcesos}" >
	    <option value="<c:out value="${lista.id_proceso}"/>"<c:if test="${lista.id_proceso == id_proceso}">selected</c:if>>
	      <c:out value="${lista.proceso}"/>
            </option>
	  </c:forEach>
	</select>  
      </td>
    </tr>  
  </table>
  <br>

  <c:if test="${!empty lCampos}">
    <table valign="top">
      <tr>
        <td valign="top">
          <table class="tabla">
	    <tr>
              <th colspan="2">CAMPOS</th>
	    </tr>
            <c:forEach var="camp" items="${lCampos}" varStatus="contador">
            <!-- ********** Esto es para el efecto ************ -->
              <tr <c:if test="${(contador.count mod 2) == 0}">bgColor="#FFFFD9" %-- Est&acute;tico :( --%</c:if> onMouseOver="this.className='sobreFila'" onmouseout="this.className=''">
            <!-- ********** Fin  efecto ************ -->
              <td align=center><input type=checkbox name="chequeo" id='campo_<c:out value="${camp.id_campo}"/>' VALUE="<c:out value="${camp.id_campo}"/>" onClick="cargar();"></td>
              <td><c:out value="${camp.campo}"/> </td>
            </tr>
            </c:forEach>
          </table>
        </td>
    
        <td valign="top">
	  <table>
	    <tr>
	      <td>
	        <table class="tabla">
	          <tr>
                    <th>ORDENAR POR</th>
	            <th></th>
                    <th>AGRUPAR POR</th>
                    <th>CONTAR DATOS DE</th>
                  </tr>
                  <tr>
                    <td>
	              <select name="id_campo_o"  id="id_campo_o" size="7" multiple>
	              </select>
                    </td>
                    <td valign="middle" align="center">
                      <input type="button" class="adelante" onClick="copiarOpcionElegida(this.form['id_campo_o'],this.form['chequeo_o'],false);return false;"><br>
                      <input type="button" class="atras" onClick="removerOpcionElegida(this.form['chequeo_o']); return false;">
                    </td>
	            <td>
	              <select name="chequeo_o" size="7" multiple>
	              </select>
	            </td>
                    <td valign="top">
                      <select name="id_campo_contar" id="id_campo_contar">
 	                <option value="">-- seleccione --</option>
	              </select>  
                    </td>
	          </tr>
	          <tr>
                    <td align="center" valign="middle">
                      <input type="button" class="arriba" onClick="moverOpcionArriba(this.form['id_campo_o'])">&nbsp;
                      <input type="button" class="abajo" onClick="moverOpcionAbajo(this.form['id_campo_o'])">
                    </td>
	            <td>&nbsp;</td>
                    <td align="center" valign="middle">
                      <input type="button" class="arriba" onClick="moverOpcionArriba(this.form['chequeo_o'])">&nbsp;
                      <input type="button" class="abajo" onClick="moverOpcionAbajo(this.form['chequeo_o'])">
                    </td>
	            <td>&nbsp;</td>
	          </tr>
	        </table>
	      </td>
	    </tr>
	    <tr>
	      <td>
                <table>
		  <tr>
	            <td class="etiqueta">Mostrar listado general</td>
   	            <td class="colb"><input type="radio" name="listar" value="Si"> Si &nbsp; <input type="radio" name="listar" value="No" checked>No</td>
		  </tr>
		</table>
	      </td>
	    </tr>
            <tr>
              <td colspan="4"><input type="submit" class="aceptar" value="Generar" onclick="seleccionar(this.form['id_campo_o']); seleccionar(this.form['chequeo_o']); document.forma.action='generarReporte.fautapo'"></td>
            </tr>
	  </table>
         </td>
       </tr>
     </table>
   </c:if>
</form>

<script>
  var combito = document.getElementById('id_campo_o');
  var combito1 = document.getElementById('id_campo_contar');
  var campos = new Array();
  h=0;
  <c:forEach var="campo" items="${lCampos}" varStatus="contador1">
      campos[h]=new Array("<c:out value='${campo.id_campo}'/>","<c:out value='${campo.campo}'/>");
      h++;
  </c:forEach>    
 
  function limpiar(){
    if(combito.options.length !=null)
        for (m=combito.options.length-1;m>=0;m--)
          combito.options[m]=null;

    if(combito1.options.length !=null)
        for (m=combito1.options.length-1;m>=0;m--)
          combito1.options[m]=null;
          combito1.options[0]=new Option("-- seleccione --", "0");;
  }
  
    
  function cargar() {
    limpiar();
    k = 0;    
    for (i=0;i<campos.length;i++) {
      objeto = document.getElementById('campo_' + campos[i][0]);
      if(objeto.checked) {
        combito.options[k] = new Option(campos[i][1], campos[i][0]);
        combito1.options[k+1] = new Option(campos[i][1], campos[i][0]);
        k++;
      }
    }
  }
  
 //nuevo
  function habilitado(obj) {
	if (obj!=null && obj.options!=null) { return true; }
	return false;
	}

  function moverOpcionArriba(obj) {
	if (!habilitado(obj)) { return; }
	for (i=0; i<obj.options.length; i++) {
		if (obj.options[i].selected) {
			if (i != 0 && !obj.options[i-1].selected) {
				intercambioOpcion(obj,i,i-1);
				obj.options[i-1].selected = true;
				}
			}
		}
	}

function moverOpcionAbajo(obj) {
	if (!habilitado(obj)) { return; }
	for (i=obj.options.length-1; i>=0; i--) {
		if (obj.options[i].selected) {
			if (i != (obj.options.length-1) && ! obj.options[i+1].selected) {
				intercambioOpcion(obj,i,i+1);
				obj.options[i+1].selected = true;
				}
			}
		}
	}

function intercambioOpcion(obj,i,j) {
	var o = obj.options;
	var i_selected = o[i].selected;
	var j_selected = o[j].selected;
	var temp = new Option(o[i].text, o[i].value, o[i].defaultSelected, o[i].selected);
	var temp2= new Option(o[j].text, o[j].value, o[j].defaultSelected, o[j].selected);
	o[i] = temp2;
	o[j] = temp;
	o[i].selected = j_selected;
	o[j].selected = i_selected;
}

function copiarOpcionElegida(from,to) {
	var options = new Object();
	if (habilitado(to)) {
		for (var i=0; i<to.options.length; i++) {
			options[to.options[i].value] = to.options[i].text;
			}
		}
	if (!habilitado(from)) { return; }
	for (var i=0; i<from.options.length; i++) {
		var o = from.options[i];
		if (o.selected) {
			if (options[o.value] == null || options[o.value] == "undefined" || options[o.value]!=o.text) {
				if (!habilitado(to)) { var index = 0; } else { var index=to.options.length; }
				to.options[index] = new Option( o.text, o.value, false, false);
				}
			}
		}
	if ((arguments.length<3) || (arguments[2]==true)) {
		sortSelect(to);
		}
	from.selectedIndex = -1;
	to.selectedIndex = -1;
	}

function removerOpcionElegida(from) { 
	if (!habilitado(from)) { return; }
	if (from.type=="select-one") {
		from.options[from.selectedIndex] = null;
		}
	else {
		for (var i=(from.options.length-1); i>=0; i--) { 
			var o=from.options[i]; 
			if (o.selected) { 
				from.options[i] = null; 
				} 
			}
		}
	from.selectedIndex = -1; 
	} 

function moverOpcionElegida(from,to) {
	// Unselect matching options, if required
	if (arguments.length>3) {
		var regex = arguments[3];
		if (regex != "") {
			unSelectMatchingOptions(from,regex);
			}
		}
	// Move them over
	if (!habilitado(from)) { return; }
	for (var i=0; i<from.options.length; i++) {
		var o = from.options[i];
		if (o.selected) {
			if (!habilitado(to)) { var index = 0; } else { var index=to.options.length; }
			to.options[index] = new Option( o.text, o.value, false, false);
			}
		}
	// Delete them from original
	for (var i=(from.options.length-1); i>=0; i--) {
		var o = from.options[i];
		if (o.selected) {
			from.options[i] = null;
			}
		}
	if ((arguments.length<3) || (arguments[2]==true)) {
		sortSelect(from);
		sortSelect(to);
		}
	from.selectedIndex = -1;
	to.selectedIndex = -1;
	}


function seleccionar(obj) {
  for (i=0; i<obj.options.length; i++) {
    obj.options[i].selected = true;
  }
}
</script>
<%@ include file="../Inferior.jsp" %>  