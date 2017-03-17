package org.fautapo.web.administrarProgramasEspecializados.nuevo;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
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
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();

    String sId_proceso = cliente.getString(request, "id_proceso");
    String sId_tramite = cliente.getString(request, "id_tramite");
    String sTabla = cliente.getString(request, "tabla");
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_tramite", sId_tramite);
    Abm tabla = new Abm();
    tabla.setTabla(sTabla);
    tabla = this.mi.getBuscarTabla1(tabla);
    if (tabla == null) { return new ModelAndView("Error", "mensaje", "No existe la Tabla/Relación indicada"); }
    modelo.put("tabla", tabla);
    tabla.setPermiso("a");
    List listaCampos = this.mi.getListarCamposTabla(tabla);
    if (listaCampos.size() == 0) { return new ModelAndView("Error","mensaje", "La relación '" + tabla.getTabla() + "' no permite agregar datos"); }
    String sObligatorios = "";
    for (int i = 0; i < listaCampos.size(); i++) {
      Abm aux = (Abm) listaCampos.get(i);
      if (aux.getPermiso().indexOf("o") > -1) {
        aux.setPermiso("o");
        sObligatorios = sObligatorios + ";" + aux.getCampo();
      } else
        aux.setPermiso("");
      Abm foranea = (Abm) this.mi.getBuscarForanea(aux);
      if (foranea != null) {
        if ("".equals(aux.getCampo_padre()))
          aux.setCampo_padre("''");
	aux.setCondicion(foranea.getCondicion());
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
    if (!"".equals(sObligatorios))
      modelo.put("obligatorios", sObligatorios.substring(1));
    modelo.put("listaCampos", listaCampos);
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    return new ModelAndView("administrarProgramasEspecializados/nuevo/NuevoRegistro", modelo);
  }
}