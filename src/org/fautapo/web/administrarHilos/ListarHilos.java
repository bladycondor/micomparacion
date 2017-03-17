package org.fautapo.web.administrarHilos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

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

public class ListarHilos implements Controller {
        
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    int iId_usuario = cliente.getId_usuario();

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

    Hilos hilos = new Hilos();
    hilos.setId_duenio(iId_usuario);
    List lHilos = this.mi.getListarHilosMios(hilos);
    for (int i = 0; i < lHilos.size(); i++) {
      Hilos datosHilo = (Hilos) lHilos.get(i);
      datosHilo.setId_duenio(iId_usuario);
      datosHilo.setNro_mensajes(this.mi.getNroMensajes(datosHilo));
      datosHilo.setNro_mensajes_nuevos(this.mi.getNroMensajesNuevos(datosHilo));
      lHilos.set(i, datosHilo);
    }
    modelo.put("lHilosMios", lHilos);

    List lHilos2 = this.mi.getListarHilosAMi(hilos);
    for (int i = 0; i < lHilos2.size(); i++) {
      Hilos datosHilo = (Hilos) lHilos2.get(i);
      datosHilo.setId_duenio(iId_usuario);
      datosHilo.setNro_mensajes(this.mi.getNroMensajes(datosHilo));
      datosHilo.setNro_mensajes_nuevos(this.mi.getNroMensajesNuevos(datosHilo));
      lHilos2.set(i, datosHilo);
    }
    modelo.put("lHilosAMi", lHilos2);
  
    return new ModelAndView("administrarHilos/ListarHilos", modelo);
  }
}
