package org.fautapo.web.administrarConceptosVarios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Abm;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor Jorge Copa
 * @fec_registro 2007-11-15
 * @ult_usuario Jorge Copa
 * @fec_modificacion 2007-11-15
*/

public class ListarConceptos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();

    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("id_perfil_varios");
    formatoFecha.setCodigo("cajas");
    int iId_perfil = Integer.parseInt(this.mi.getDibBuscarParametro(formatoFecha));
    Perfiles perfil = new Perfiles();
    perfil.setId_perfil(iId_perfil);
    modelo.put("lConceptos", this.mi.getPrfListarConceptos(perfil));

    return new ModelAndView("administrarConceptosVarios/ListarConceptos", modelo);
  }
}