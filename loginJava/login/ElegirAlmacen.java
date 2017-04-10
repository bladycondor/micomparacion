package org.fautapo.web.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Roles;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
 */

public class ElegirAlmacen implements Controller {

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return new ModelAndView("login/ElegirAlmacen", "cliente", (Clientes) request.getSession().getAttribute("__sess_cliente"));
  }
}