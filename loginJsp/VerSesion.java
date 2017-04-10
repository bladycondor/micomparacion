package org.fautapo.web.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
 */

public class VerSesion implements Controller {

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (null == (Clientes) request.getSession().getAttribute("__sess_cliente")) {
      // Primera conexión con el sistema
      return new ModelAndView("login/LoginEntrada", null);
    }
    // Mostramos el menú correspondiente
    return new ModelAndView("Distro", "url", "/menu.fautapo' target='_top'");
  }
}