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

public class ConfirmarNuevo implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
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

    for (int i = 0; i < listaCampos.size(); i++) {
      Abm abm = (Abm) listaCampos.get(i);
      if (!"".equals(request.getParameter(abm.getCampo()))) {
        abm.setValores(request.getParameter(abm.getCampo()).replace("\\\"", "\""));
        abm.setValores(abm.getValores().replace("\\'", "'"));
      } else {
        abm.setValores("<NULL>");
      }
      if ((Abm) this.mi.getBuscarForanea(abm) != null) {
        abm.setCombo(listaCampos);
        String id = request.getParameter(abm.getCampo());
        abm.setDetalle(request.getParameter(abm.getCampo() + id).replace("\\\"", "\""));
        abm.setDetalle(abm.getDetalle().replace("\\'", "'"));
      }
      listaCampos.set(i, abm);
    }
    modelo.put("listaCampos", listaCampos);
    return new ModelAndView("dibRap/ConfirmarNuevo", modelo);
  }
}