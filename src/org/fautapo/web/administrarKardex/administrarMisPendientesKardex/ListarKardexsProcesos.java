package org.fautapo.web.administrarKardex.administrarMisPendientesKardex;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.support.PagedListHolder;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Informes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.EnviarCorreo;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-23
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-23
*/

public class ListarKardexsProcesos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Tramites datosFrLog = new Tramites(); Tramites datosTramite = new Tramites(); Character caracter;

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    //Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAccesoKardex();
    modelo.put("lProcesos", lProcesos);
    modelo.put("banderakardex","1");
    
    return new ModelAndView("administrarKardex/administrarMisPendientesKardex/ListarKardexsProcesos", modelo);
  }
}