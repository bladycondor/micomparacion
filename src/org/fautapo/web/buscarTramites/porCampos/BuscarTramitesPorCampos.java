package org.fautapo.web.buscarTramites.porCampos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-29
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-29
*/

public class BuscarTramitesPorCampos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
  
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    String sSw="0";
    Campos datosCampo;
    List lReferencias = new ArrayList();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    //Recuperamos los valores del jsp
    String sId_proceso = request.getParameter("id_proceso");
    String sBoton = request.getParameter("boton");
    String sId_campo = request.getParameter("id_campo");
    String sValor = request.getParameter("valor");
    modelo.put("id_proceso", sId_proceso);

    //Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAcceso(cliente);
    modelo.put("lProcesos", lProcesos);
    if ((sId_proceso != null) && (!"".equals(sId_proceso))) {
      datosCampo = new Campos();
      datosCampo.setId_proceso(Integer.parseInt(sId_proceso));
      List lCampos = this.mi.getListarCamposProceso(datosCampo);
      modelo.put("lCampos", lCampos);
    }
    
    if (("Buscar".equals(sBoton))&&(!"".equals(sId_campo))&&(!"".equals(sValor))) {
      try {
        sSw="1";
	Tramites datosValor = new Tramites();
        datosValor.setId_campo(Integer.parseInt(sId_campo));
	datosValor.setValor("%"+sValor+"%");
	datosValor.setTablita("___"+cliente.getId_usuario());
        //Listamos los tramites resultado de la búsqueda
	List lTramites = this.mi.getListarTramitesPorCampos(datosValor);
        for (int i = 0; i < lTramites.size(); i++) {
          Tramites datosTramite = (Tramites) lTramites.get(i);
          Tramites datosTramite3 = new Tramites();
          datosTramite3.setId_proceso(datosTramite.getId_proceso());
          datosTramite3.setId_tramite(datosTramite.getId_tramite());
          datosTramite3.setCampo(datosTramite.getCampo());
	  lReferencias = this.mi.getListarCamposReferencia(datosTramite3);
	  datosTramite.setLista(lReferencias);
  	  lTramites.set(i, datosTramite);
	}
        modelo.put("lTramites", lTramites);
      }
        catch(Exception e) {
        sSw = "0";
      }
    }
      
    modelo.put("id_campo", sId_campo);
    modelo.put("valor", sValor);      
    modelo.put("sw", sSw);

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
    
    return new ModelAndView("buscarTramites/porCampos/BuscarTramitesPorCampos", modelo);
  }
}