package org.fautapo.web.administrarProgramasEspecializados.administrarConvalidacionManual;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Tramites;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarPlanConvalidacion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_tramite", sId_tramite);

    if ((sId_tramite != null) && (!"".equals(sId_tramite))) {
      try {
        //Sacamos los datos del Campo
        Tramites datosTramite = new Tramites();
        datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
        datosTramite.setEtiqueta("id_estudiante");
        datosTramite = (Tramites) this.mi.getBuscarCampoGw(datosTramite);
        String sId_estudiante = datosTramite.getValores();
        modelo.put("id_estudiante", sId_estudiante);
 
        //Sacamos los datos del Estudiante
        Estudiantes datosEstudiante = new Estudiantes();
        datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
        datosEstudiante = this.mi.getEstBuscarEstudianteNombres(datosEstudiante);
        modelo.put("datosEstudiante", datosEstudiante);

        //Sacamos el listado del plan
        Planes datosPlan = new Planes();
        datosPlan.setId_programa(datosEstudiante.getId_programa());
        datosPlan.setId_plan(datosEstudiante.getId_plan());
        List lMateriasPlan = this.mi.getListarMateriasPlan(datosPlan);
        modelo.put("lMateriasPlan", lMateriasPlan);
      } catch(Exception e) {}
    }
    return new ModelAndView("administrarProgramasEspecializados/administrarConvalidacionManual/ListarPlanConvalidacion", modelo);
  }
}