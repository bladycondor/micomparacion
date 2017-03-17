package org.fautapo.web.administrarHilos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Hilos;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-04
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-04
*/

public class ListarSegmentos implements Controller {
        
  private MiFacade mi;
 
  public void setMi(MiFacade mi) { this.mi = mi;}

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    int iId_usuario = cliente.getId_usuario();
    Hilos auxiliar;
    
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

    String sId_hilo = request.getParameter("id_hilo");
    String sAplicacion = request.getParameter("aplicacion");
    modelo.put("id_hilo", sId_hilo);
    modelo.put("aplicacion", sAplicacion);
    
    Hilos hilo = new Hilos();
    hilo.setId_hilo(Integer.parseInt(sId_hilo));
    hilo.setId_destinatario(iId_usuario);

    //Listando los segmentos
    List lista = new ArrayList();
    List lSegmentos = this.mi.getListarSegmentos(hilo);
    for (int i=0; i < lSegmentos.size(); i++) {
      auxiliar = (Hilos) lSegmentos.get(i);
      auxiliar.setLista(this.mi.getListarAdjuntosHilos(auxiliar));
      lista.add(auxiliar);
    }
    modelo.put("lSegmentos", lista);

    auxiliar = new Hilos();
    auxiliar = this.mi.getBuscarHilo(hilo);
    modelo.put("hilo", auxiliar);

    return new ModelAndView("administrarHilos/ListarSegmentos", modelo);
  }
}
