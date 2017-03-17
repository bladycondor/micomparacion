package org.fautapo.web.copiaSeguridad;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.io.IOException;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public class ConfirmarRestauro implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) {
      return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente.");
    }
    modelo.put("cliente", cliente);
    String sArchivo = cliente.getString(request, "archivo");
    modelo.put("archivo", sArchivo);
    String fecha[] = sArchivo.split("\\.");
    cliente.setFiltro(fecha[1].substring(0, 10));
    modelo.put("fecha", this.mi.getFechaCadena(cliente));
    modelo.put("hora", fecha[1].substring(11, 13) + ":" + fecha[1].substring(13, 15));

    return new ModelAndView("copiaSeguridad/ConfirmarRestauro", modelo);
  }
}