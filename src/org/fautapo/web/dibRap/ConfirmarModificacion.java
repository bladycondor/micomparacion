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

public class ConfirmarModificacion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String sPermiso = request.getParameter("p");
    String sId_enlace = request.getParameter("e");
    modelo.put("id_enlace", sId_enlace);
    modelo.put("permiso", sPermiso);
    String sFiltro = request.getParameter("f"); // Condicion (filtro)
    modelo.put("condicion", sFiltro);
    int iId_tabla = Integer.parseInt(request.getParameter("t"));
    String sValoresPrimarios = request.getParameter("c");
    Abm tabla = new Abm();
    tabla.setId_tabla(iId_tabla);
    tabla = this.mi.getBuscarTabla(tabla);
    modelo.put("tabla", tabla);

    List listaCampos;
    String sId_actividad = request.getParameter("a");
    tabla.setPermiso("lm");
    if (!"".equals(sId_actividad)) {  // Variable viene desde huaica
      tabla.setColumnas(Integer.parseInt(sId_actividad));  // columnas <- id_actividad
      listaCampos = this.mi.getListarCamposTablaActividad(tabla);
      modelo.put("id_actividad", sId_actividad);
    } else {  // dibRap clásico
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
        tabla.setPermiso("m");
        listaCampos = this.mi.getListarCamposTabla(tabla);
      }
    }

    for (int i = 0; i < listaCampos.size(); i++) {
      Abm abm = (Abm)listaCampos.get(i);
      abm.setValores(request.getParameter(abm.getCampo()).replace("\\\"", "\""));
      abm.setValores(abm.getValores().replace("\\'", "'"));
      if ((Abm) this.mi.getBuscarForanea(abm) != null) {
        abm.setCombo(listaCampos);
        String id = request.getParameter(abm.getCampo());
	if (abm.getPermiso().indexOf("m") == -1) {
          abm.setDetalle(request.getParameter(abm.getCampo()));
	} else {
          abm.setDetalle(request.getParameter(abm.getCampo() + id).replace("\\\"", "\""));
          abm.setDetalle(abm.getDetalle().replace("\\'", "'"));
	}
      }
      listaCampos.set(i, abm);
    }
    modelo.put("valoresPrimarios", sValoresPrimarios);
    modelo.put("listaCampos", listaCampos);
    return new ModelAndView("dibRap/ConfirmarModificacion", modelo);
  }
}