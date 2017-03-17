package org.fautapo.web.administrarProgramasEspecializados.buscarPostulanteHabilitado;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Perfiles;
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
    String sDip     = cliente.getString(request, "dip");
    String sNombre  = cliente.getString(request, "nombre");
    String sBotonDip = request.getParameter("botonDip");
    String sBotonNombre = request.getParameter("botonNombre");

    //Buscamos los datos del proceso
    Actividades datosProceso = new Actividades();
    datosProceso.setId_proceso(Integer.parseInt(sId_proceso));
    datosProceso = this.mi.getBuscarProceso(datosProceso);
    modelo.put("datosProceso", datosProceso);
    modelo.put("id_tramite", sId_tramite);

    if ("".equals(sDip) && "".equals(sNombre))
      return new ModelAndView("administrarProgramasEspecializados/buscarPostulanteHabilitado/Entrada", modelo);
    List lPstPersonas; //List lPostulantes;
    Postulantes postulante = new Postulantes();
    if (!"".equals(sNombre)) {
      postulante.setNombres(sNombre);
      lPstPersonas = this.mi.getMiListarPostulantesNombre(postulante);
      modelo.put("lPstPersonas", lPstPersonas);
      return new ModelAndView("administrarProgramasEspecializados/buscarPostulanteHabilitado/ListarPostulantes", modelo);
    } 
    if (!"".equals(sDip)) {
      postulante.setDip(sDip);
      lPstPersonas = this.mi.getMiListarPostulantesDip(postulante);
      modelo.put("lPstPersonas", lPstPersonas);
    }

    return new ModelAndView("administrarProgramasEspecializados/buscarPostulanteHabilitado/ListarPostulantes", modelo);
  }

}