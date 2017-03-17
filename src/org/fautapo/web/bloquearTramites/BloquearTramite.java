package org.fautapo.web.bloquearTramites;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Adjuntos;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class BloquearTramite implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_tramite = request.getParameter("id_tramite");
    String sProveido = request.getParameter("proveido");

    if (("".equals(sProveido)) || (sProveido == null)) {
      sProveido = "";
    }
    //Fin Listamos los adjuntos del tramite
    Tramites datosTramite = new Tramites();
    datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
    datosTramite = (Tramites) this.mi.getBuscarTramite(datosTramite);

    Tramites tramite = new Tramites();
    tramite.setId_tramite(Integer.parseInt(sId_tramite));
    tramite.setId_actividad(datosTramite.getId_actividad_actual());
    tramite.setId_tipo_proveido(3);
    tramite.setProveido(sProveido);
    tramite.setUlt_usuario(cliente.getId_usuario());
    int iResultado = this.mi.setBloquearTramite(tramite);
    if (iResultado == 1) {
      return new ModelAndView("Aviso", "mensaje", "El tramite se bloqueo correctamente");
    }
    else {
      return new ModelAndView("Error", "mensaje", "El tramite no se bloqueo");
    }
  }
}