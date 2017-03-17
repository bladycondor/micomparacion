package org.fautapo.web.administrarRecibos.eliminarRecibos;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Usuarios;
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

public class ListarRecibos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente == null) { return new ModelAndView("Error", "mensaje", "Tu sesión terminó. Por favor, ingresa nuevamente."); }
    Map modelo = new HashMap();

    String sClave = cliente.getString(request, "clave"+ request.getParameter("hora"));
    if("".equals(sClave)) return new ModelAndView("administrarRecibos/Entrada", null);

    //Comprobamos la clave del usuario
    Usuarios usuario = new Usuarios();
    usuario.setId_usuario(cliente.getId_usuario());
    usuario.setClave(sClave);
    if (this.mi.getVerificarUsuario(usuario) == 0) 
      return new ModelAndView("administrarRecibos/eliminarRecibos/Error", "mensaje", "Clave incorrecta, vuelva a intentar.");

    String sNro_recibo = cliente.getString(request, "nro_recibo");
    Perfiles perfil = new Perfiles();
    perfil.setNro_recibo(sNro_recibo);
    modelo.put("lRecibos", this.mi.getTrnBuscarPorNroRecibo(perfil));
    //Sacamos el formato de la fecha
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));

    return new ModelAndView("administrarRecibos/eliminarRecibos/ListarRecibos", modelo);
  }

}