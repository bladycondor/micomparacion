package org.fautapo.web.administrarAdjuntos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.lang.Boolean;
import org.apache.commons.fileupload.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-27
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-27
*/

public class AdjuntarArchivo implements Controller {
        
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos las variables de la sesion
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sAplicacion = request.getParameter("aplicacion");
    String sId_tramite = request.getParameter("id_tramite");   
    String sId_proceso = request.getParameter("id_proceso");    
    String sId_actividad_actual = request.getParameter("id_actividad_actual");    
    String sId_form = request.getParameter("id_form");    

    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_actividad_actual", sId_actividad_actual);
    modelo.put("id_actividad", sId_actividad_actual);
    modelo.put("id_form", sId_form);
    modelo.put("id_tramite", sId_tramite);
    modelo.put("aplicacion", sAplicacion);
    
    return new ModelAndView("administrarAdjuntos/AdjuntarArchivo", modelo);
  }
}
