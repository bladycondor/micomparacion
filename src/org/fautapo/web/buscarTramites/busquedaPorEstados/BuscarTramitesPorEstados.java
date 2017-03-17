package org.fautapo.web.buscarTramites.busquedaPorEstados;

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
    String sId_estado = request.getParameter("id_estado");
    String sAccion = request.getParameter("boton");

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

    //Listamos los  Estados de tr_tramites
    List lEstados = this.mi.getListarEstadosTramites();
    modelo.put("lEstados", lEstados);
    //Recuperamos variables del jsp

    if ("Buscar".equals(sAccion)) {
      String sFecha_ini = request.getParameter("valor_1");
      String sFecha_fin = request.getParameter("valor_2");
      if (("".equals(sFecha_ini)) || ("".equals(sFecha_fin))) {
        return new ModelAndView("buscarTramites/busquedaPorEstados/BuscarTramites", modelo);	  
      }
      else {
	Tramites datosTramite = new Tramites();
        datosTramite.setFecha_ini(sFecha_ini);
	datosTramite.setFecha_fin(sFecha_fin);
	datosTramite.setId_ubicacion_organica(cliente.getId_ubicacion_organica());
	
	if (!"".equals(sId_estado)) {
	  datosTramite.setId_estado(sId_estado);
	  //Listamos lostramite pod Estados
	  List lTramites = this.mi.getListarTramitesEstadoFechaUbicacionOrganica(datosTramite);
	  for (int i = 0; i < lTramites.size(); i++) {
            Tramites auxiliar = (Tramites) lTramites.get(i);
            auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
            lTramites.set(i, auxiliar);
          }
	  modelo.put("lTramites", lTramites); 
        }
	else {
	  if ("".equals(sId_estado)) {
	    List lTramites = this.mi.getListarTramitesFechaUbicacionOrganica(datosTramite);
	    for (int i = 0; i < lTramites.size(); i++) {
              Tramites auxiliar = (Tramites) lTramites.get(i);
              auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
              lTramites.set(i, auxiliar);
            }
	    modelo.put("lTramites", lTramites); 
	  }
          modelo.put("fecha_ini", sFecha_ini);
          modelo.put("fecha_fin", sFecha_fin);
        }  
      }
      
      return new ModelAndView("buscarTramites/busquedaPorEstados/ListarTramitesPorEstados", modelo);
    }
    
    return new ModelAndView("buscarTramites/busquedaPorEstados/BuscarTramites", modelo);
  }
}