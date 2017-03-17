package org.fautapo.web.administrarProgramasEspecializados;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-23
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-23
*/

public class VerEncabezadoProgramasEspecializados implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Tramites datosTramite;
    
    //Sacamos los datos de la session
    String sId_proceso = request.getParameter("id_proceso");
    String sId_actividad = request.getParameter("id_actividad");
    String sId_tramite = request.getParameter("id_tramite");
    String sAplicacion = request.getParameter("aplicacion");
    
    //Sacamos los datos del tramite
    datosTramite = new Tramites();
    datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
    datosTramite = (Tramites) this.mi.getBuscarTramite(datosTramite);
    modelo.put("datosTramite", datosTramite);
	  
    //Listamos los campos de referencia
    List lReferencias = this.mi.getListarCamposReferencia(datosTramite);
    modelo.put("lReferencias", lReferencias);
    //Fin Listamos los campos de referencia

    modelo.put("id_tramite", sId_tramite);
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_actividad", sId_actividad);
    modelo.put("aplicacion", sAplicacion);
    
    return new ModelAndView("administrarProgramasEspecializados/VerEncabezadoProgramasEspecializados", modelo);
  }

}