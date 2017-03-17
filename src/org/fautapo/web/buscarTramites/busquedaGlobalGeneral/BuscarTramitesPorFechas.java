package org.fautapo.web.buscarTramites.busquedaGlobalGeneral;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class BuscarTramitesPorFechas implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi;}
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sAccion = request.getParameter("boton");
    String sFecha_ini = request.getParameter("valor_1");
    String sFecha_fin = request.getParameter("valor_2");
    String sId_ubicacion_organica = request.getParameter("id_ubicacion_organica");

    if ("Buscar".equals(sAccion)) {
      if (("".equals(sFecha_ini)) || ("".equals(sFecha_fin))) {
        int iA=1;
      }
      else {
	Tramites datosTramite = new Tramites();
        datosTramite.setFecha_ini(sFecha_ini);
	datosTramite.setFecha_fin(sFecha_fin);
	datosTramite.setId_ubicacion_organica(Integer.parseInt(sId_ubicacion_organica));
        //Listamos los tramites iniciados entre las fechas ingresadas
	List lTramitesIniciados = this.mi.getListarTramitesIniciados(datosTramite);
        int suma_total = 0;
	for (int i=0; i < lTramitesIniciados.size(); i++) {
	  Tramites auxiliar = (Tramites) lTramitesIniciados.get(i);
	  suma_total = suma_total + auxiliar.getResultado();
	}
	modelo.put("lTramitesIniciados", lTramitesIniciados);
        modelo.put("totalIniciados", Integer.toString(suma_total));
        //Fin - Listamos los tramites iniciados entre las fechas ingresadas
	
        //Listamos los tramites movidos entre las fechas ingresadas
	List lTramitesMovidos = this.mi.getListarTramitesMovidos(datosTramite);
        suma_total = 0;
	for (int i=0; i < lTramitesMovidos.size(); i++) {
	  Tramites auxiliar = (Tramites) lTramitesMovidos.get(i);
	  suma_total = suma_total + auxiliar.getResultado();
	}
	modelo.put("lTramitesMovidos", lTramitesMovidos);
        modelo.put("totalMovidos", Integer.toString(suma_total));
        //Fin - Listamos los tramites movidos entre las fechas ingresadas

        //Listamos los tramites concluidos entre las fechas ingresadas
        List lTramitesConcluidos = this.mi.getListarTramitesConcluidos(datosTramite);
        suma_total = 0;
	for (int i=0; i < lTramitesConcluidos.size(); i++) {
	  Tramites auxiliar = (Tramites) lTramitesConcluidos.get(i);
	  suma_total = suma_total + auxiliar.getResultado();
	}
	modelo.put("lTramitesConcluidos", lTramitesConcluidos);
        modelo.put("totalConcluidos", Integer.toString(suma_total));
        //Fin - Listamos los tramites concluidos entre las fechas ingresadas
	
        modelo.put("accion", sAccion);
	modelo.put("fechaini", sFecha_ini);
	modelo.put("fechafin", sFecha_fin);	
      }
    }
    
    Date fecha = new Date();
    modelo.put("fecha_ini", sFecha_ini);
    modelo.put("fecha_fin", sFecha_fin);
    modelo.put("id_ubicacion_organica", sId_ubicacion_organica);
    
    return new ModelAndView("buscarTramites/busquedaGlobalGeneral/BuscarTramitesPorFechas", modelo);
  }
}