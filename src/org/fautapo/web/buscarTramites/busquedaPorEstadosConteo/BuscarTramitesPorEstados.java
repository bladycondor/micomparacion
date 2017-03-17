package org.fautapo.web.buscarTramites.busquedaPorEstadosConteo;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-04
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-04
*/

public class BuscarTramitesPorEstados implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi;}
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sFecha_ini = request.getParameter("valor_1");
    String sFecha_fin = request.getParameter("valor_2");

    //Sacamos el formato de la fecha definida en parametros
    Abm formatoFecha = new Abm();
    formatoFecha.setCampo("formato_fecha");
    formatoFecha.setCodigo("dibrap");
    modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
    //FIN - Sacamos el formato de la fecha definida en parametros

    //Sacamos el formato de la hora definida en parametros
    Abm formatoHora = new Abm();
    formatoHora.setCampo("formato_hora");
    formatoHora.setCodigo("dibrap");
    modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoHora));
    //FIN - Sacamos el formato de la hora definida en parametros

    if (("".equals(sFecha_ini)) || ("".equals(sFecha_fin))) {
      return new ModelAndView("buscarTramites/busquedaPorEstadosConteo/BuscarTramites", modelo);
    }
    modelo.put("fecha_ini", sFecha_ini);
    modelo.put("fecha_fin", sFecha_fin);

    try {
      Tramites datosTramite = new Tramites();
      datosTramite.setFecha_ini(sFecha_ini);
      datosTramite.setFecha_fin(sFecha_fin);
      List lProcesos = this.mi.getListarTramitesPorEstadoFecha(datosTramite);
      modelo.put("lProcesos", lProcesos); 
    }
    catch(Exception e) {
      return new ModelAndView("buscarTramites/busquedaPorEstadosConteo/BuscarTramites", modelo);
    }
      
    return new ModelAndView("buscarTramites/busquedaPorEstadosConteo/ListarTramitesPorEstados", modelo);
  }
}