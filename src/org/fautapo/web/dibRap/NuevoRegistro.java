package org.fautapo.web.dibRap;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Enlaces;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class NuevoRegistro implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) {
      return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente.");
    }

    String sId_enlace = request.getParameter("e");
    modelo.put("id_enlace", sId_enlace);
    String sPermiso = request.getParameter("p"); // Permiso
    modelo.put("permiso", sPermiso);
    String sFiltro = request.getParameter("f"); // Condicion (filtro)
    modelo.put("condicion", sFiltro);
    int iId_tabla = Integer.parseInt(request.getParameter("t"));
    Abm tabla = new Abm();
    tabla.setId_tabla(iId_tabla);
    tabla = this.mi.getBuscarTabla(tabla);
    if (tabla == null) {
      return new ModelAndView("Error", "mensaje", "No existe esta dirección");
    }
    modelo.put("tabla", tabla);
    tabla.setPermiso("a");
    List listaCampos = null;
    // Inicio Combustible
    //buscar enlace en "_dib_enl_campos"
    Enlaces enlace = new Enlaces();
    enlace.setId_enlace(Integer.parseInt(sId_enlace));
    enlace = this.mi.getEnlBuscarEnlace(enlace);
    if (enlace != null) {
      tabla.setColumnas(Integer.parseInt(sId_enlace));  // columnas <- id_enlace
      listaCampos = this.mi.getEnlListarCamposTabla(tabla);
    }
    // Fin Combustible
    else {
      listaCampos = this.mi.getListarCamposTabla(tabla);
    }

    if (listaCampos.size() == 0) {
      return new ModelAndView("Error","mensaje", "La relación '" + tabla.getTabla() + "' no permite agregar datos");
    }
    String sObligatorios = "";
    for (int i = 0; i < listaCampos.size(); i++) {
      Abm aux = (Abm) listaCampos.get(i);
      if (aux.getPermiso().indexOf("o") > -1) {
        aux.setPermiso("o");
        sObligatorios = sObligatorios + ";" + aux.getCampo();
      } else {
        aux.setPermiso("");
      }
      Abm foranea = (Abm) this.mi.getBuscarForanea(aux);
      if (foranea != null) {
        if ("".equals(aux.getCampo_padre())) {
          aux.setCampo_padre("''");
        }
	aux.setCondicion(foranea.getCondicion());
//        foranea.setCondicion(("".equals(sFiltro))? foranea.getCondicion() : sFiltro);
        foranea.setCampo(foranea.getId_campo_foraneo() + "#~~#" + foranea.getCampo_foraneo() + "#~~#" + aux.getCampo_padre());
	try {
          aux.setCombo(this.mi.getListarCombos(foranea));
        } catch (Exception e) {
          foranea.setCondicion(aux.getCondicion());
          aux.setCombo(this.mi.getListarCombos(foranea));
	}
      }
      aux.setPagina(1);
      listaCampos.set(i, aux);
    }
    if (!"".equals(sObligatorios)) {
      modelo.put("obligatorios", sObligatorios.substring(1));
    }
    modelo.put("listaCampos", listaCampos);
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    return new ModelAndView("dibRap/NuevoRegistro", modelo);
  }
}