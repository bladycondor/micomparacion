package org.fautapo.web.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Roles;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
 */

public class CambiarAlmacen implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String sId_almacen = request.getParameter("id_almacen");
    Roles rol = new Roles();
    rol.setId_almacen(Integer.parseInt(sId_almacen));
    rol.setId_rol(cliente.getId_rol());
    rol.setId_usuario(cliente.getId_usuario());
    //Buscamos los datos del almacen
    rol = this.mi.getBuscarAlmacenCliente(rol);
    cliente.setId_almacen(rol.getId_almacen());
    cliente.setAlmacen(rol.getAlmacen());
    cliente.setPermiso(rol.getPermiso());
    request.getSession().setAttribute("__sess_cliente", cliente);
    return new ModelAndView("Distro", "url", ".");
  }

}