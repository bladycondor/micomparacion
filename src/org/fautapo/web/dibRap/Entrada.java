package org.fautapo.web.dibRap;

import java.util.HashMap;
import java.util.Map;

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

public class Entrada implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String sId_tabla = request.getParameter("t");
    String sPermiso  = request.getParameter("p");
    Abm tabla = new Abm();
    tabla.setId_tabla(Integer.parseInt(sId_tabla));
    tabla = this.mi.getBuscarTabla(tabla);
    if (tabla == null) {
      return new ModelAndView("Error", "mensaje", "No existe esta dirección");
    }
    String sId_enlace = request.getParameter("e");
    modelo.put("id_enlace", sId_enlace);
    modelo.put("tabla", tabla);
    modelo.put("cliente", cliente);
    if (sPermiso.indexOf("c") > -1) {
      return new ModelAndView("dibRap/Entrada", modelo);
    }
    sPermiso = sPermiso + "&f=";
    if (null != request.getParameter("f")) {
      sPermiso = sPermiso + request.getParameter("f");
    }
    sPermiso = sPermiso + "&a=";
    if (null != request.getParameter("a")) {
      sPermiso = sPermiso + request.getParameter("a");
    }
    return new ModelAndView("Distro", "url", "/dibRap/listarDatos.fautapo?t=" + sId_tabla + "&e=" + sId_enlace + "&p=" + sPermiso);
  }
}