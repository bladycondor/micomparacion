package org.fautapo.web.reportesTramites.imprimirReporteTramites;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-25
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-25
*/

public class VerEncabezado implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi;}

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String sId_tramite = request.getParameter("id_tramite");
    modelo.put("id_tramite", sId_tramite);
      
    return new ModelAndView("reportesTramites/imprimirReporteTramites/VerEncabezado", modelo);
  }
}