package org.fautapo.web.reportesTramites.imprimirReporteTramites;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-25
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-25
*/

public class BuscarTramite implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi;}
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    int iId_tramite;
    Tramites datosTramite;  Tramites datosTramite2;
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    
    String sAccion = request.getParameter("boton");
    String sCorrelativo = request.getParameter("correlativo");
    String sId_tramite = request.getParameter("id_tramite");
    String sAplicacion = request.getParameter("aplicacion");
    String sGestion = request.getParameter("gestion");
    
    if (sGestion == null) {
      sGestion = Integer.toString(cliente.getGestion());
      modelo.put("gestion", sGestion);
    }
    
    if (("Buscar".equals(sAccion)) && (!"".equals(sCorrelativo)) && (sCorrelativo != null)) {
      try {
        if ((sId_tramite == null) && (!"".equals(sGestion)) && (sGestion != null)) {
	  datosTramite = new Tramites();
   	  datosTramite.setCorrelativo2(sCorrelativo);
   	  datosTramite.setGestion(Integer.parseInt(sGestion));
  	  List lTramites = this.mi.getListarTramitesCorrelativo(datosTramite);
          //En el caso de que se tengan tramites paralelos
          if (lTramites.size() > 1) {
            for (int i = 0; i < lTramites.size(); i++) {
              Tramites auxiliar = (Tramites) lTramites.get(i);
              auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
              lTramites.set(i, auxiliar);
            }
	    modelo.put("lTramitesParalelos", lTramites);
	    return new ModelAndView("buscarTramites/busquedaEspecifica/ListarTramitesParalelos", modelo);
	  }
	  //Si se encontr�el tramite
	  else if (lTramites.size() == 1) {
	    datosTramite2 = (Tramites) lTramites.get(0);
	    iId_tramite = datosTramite2.getId_tramite();
 	  }
          //Si el tramite no existe
	  else {
	    return new ModelAndView("Aviso", "mensaje", "El proceso no existe");
	  }
        }
	else {
	  iId_tramite = Integer.parseInt(sId_tramite);
	}
        modelo.put("id_tramite", Integer.toString(iId_tramite));
        return new ModelAndView("reportesTramites/imprimirReporteTramites/Principal", modelo);
      }
      catch(Exception e) {
        return new ModelAndView("reportesTramites/imprimirReporteTramites/Principal", modelo);
      }
    }
    return new ModelAndView("reportesTramites/imprimirReporteTramites/BuscarTramite", modelo);
  }
}