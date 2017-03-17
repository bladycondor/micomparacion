package org.fautapo.web.reportesTramites.tramitesPorFuncionarios;

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

public class BuscarTramitesPorFuncionarios implements Controller {

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
        //Listamos los tramites por funcionarios
	List lTramitesFuncionarios = this.mi.getListarTramitesFuncionarios(datosTramite);
        int suma_total = 0;
	for (int i=0; i < lTramitesFuncionarios.size(); i++) {
	  Tramites auxiliar = (Tramites) lTramitesFuncionarios.get(i);
	  suma_total = suma_total + auxiliar.getResultado();
	}
	modelo.put("lTramitesFuncionarios", lTramitesFuncionarios);
        modelo.put("totalFuncionarios", Integer.toString(suma_total));
        //Fin - Listamos los tramites iniciados entre las fechas ingresadas
	
        modelo.put("accion", sAccion);
      }
    }
    
    modelo.put("fecha_ini", sFecha_ini);
    modelo.put("fecha_fin", sFecha_fin);
    modelo.put("id_ubicacion_organica", sId_ubicacion_organica);
    
    return new ModelAndView("reportesTramites/tramitesPorFuncionarios/BuscarTramitesPorFuncionarios", modelo);
  }
}