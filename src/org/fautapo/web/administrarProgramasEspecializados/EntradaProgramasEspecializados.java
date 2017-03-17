package org.fautapo.web.administrarProgramasEspecializados;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Informes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-23
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-23
*/

public class EntradaProgramasEspecializados implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }
 
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    String sId_proceso = request.getParameter("id_proceso");
    String sId_actividad = request.getParameter("id_actividad");
    String sId_tramite = request.getParameter("id_tramite");
    String sRuta = request.getParameter("ruta");
    String sAplicacion = request.getParameter("aplicacion");
    String sFiltro = request.getParameter("filtro");

    modelo.put("aplicacion", sAplicacion);
    modelo.put("id_tramite", sId_tramite);
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_actividad", sId_actividad);
    modelo.put("ruta", sRuta);

    Tramites tramite1 = new Tramites();
    tramite1.setId_proceso(Integer.parseInt(sId_proceso));
    tramite1.setId_actividad(Integer.parseInt(sId_actividad));
    String sTabla = this.mi.getBuscarTablaLimbo(tramite1);
    Abm abm = new Abm();
    abm.setTabla(sTabla);
    Abm tabla = (Abm) this.mi.getBuscarTabla1(abm);
    modelo.put("datosTabla", tabla);

    modelo.put("filtro", sFiltro);
    modelo.put("ruta", "/dibRap.fautapo");
    modelo.put("permiso", "m");
    
    return new ModelAndView("administrarProgramasEspecializados/EntradaProgramasEspecializados", modelo);
  }

}