package org.fautapo.web.administrarProgramasEspecializados.buscarDocente;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-01-13
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class Entrada implements Controller {

  private MiFacade mi;

  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");

    //Buscamos los datos del proceso
    Actividades datosProceso = new Actividades();
    datosProceso.setId_proceso(Integer.parseInt(request.getParameter("id_proceso")));
    datosProceso = this.mi.getBuscarProceso(datosProceso);
    modelo.put("datosProceso", datosProceso);
    modelo.put("id_tramite", sId_tramite);

    if ((sId_tramite != null) && (!"".equals(sId_tramite))) {
      //Sacamos los datos del Campo
      Tramites datosTramite = new Tramites();
      datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
      datosTramite.setEtiqueta("id_docente");
      datosTramite = (Tramites) this.mi.getBuscarCampoGw(datosTramite);
      String sId_docente = datosTramite.getValores();
      modelo.put("id_docente", sId_docente);

      //Sacamos los datos del Docente
      Docentes datosDocente = new Docentes();
      datosDocente.setId_docente(Integer.parseInt(sId_docente));
      datosDocente = this.mi.getBuscarDocente(datosDocente);
      modelo.put("datosDocente", datosDocente);
 
      try {
        //Sacamos los datos de los requisitos
        datosTramite = new Tramites();
        datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
        datosTramite.setEtiqueta("requisitos");
        datosTramite = (Tramites) this.mi.getBuscarCampoGw(datosTramite);
        modelo.put("requisitos", datosTramite.getValores());
      } catch(Exception e) {}

      try {
        //Sacamos el descuento del perfil
        datosTramite = new Tramites();
        datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
        datosTramite.setEtiqueta("descuento");
        datosTramite = (Tramites) this.mi.getBuscarCampoGw(datosTramite);
        modelo.put("descuento", datosTramite.getValores());
      } catch(Exception e) {}
      return new ModelAndView("administrarProgramasEspecializados/buscarDocente/ListarDatosDocente", modelo);
    }
    return new ModelAndView("administrarProgramasEspecializados/buscarDocente/Entrada", modelo);
  }

}