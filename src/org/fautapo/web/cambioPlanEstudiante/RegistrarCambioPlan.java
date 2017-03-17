package org.fautapo.web.cambioPlanEstudiante;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Planes;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class RegistrarCambioPlan implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    int iId_estudiante = 0; int iId_plan_estudio_nuevo=0;
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    
    if (("".equals(request.getParameter("id_estudiante"))) || ("".equals(request.getParameter("id_plan_nuevo")))) {
      return new ModelAndView("Error", "mensaje", "Faltan datos el cambio no puede completarse");
    }
    
    String sId_estudiante = request.getParameter("id_estudiante");
    String sId_plan_nuevo = request.getParameter("id_plan_nuevo");
	
    iId_estudiante = Integer.parseInt(sId_estudiante);
    //Sacamos los datos del Estudiante
    Estudiantes datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(iId_estudiante);	
    datosEstudiante = this.mi.getEstBuscarEstudiante(datosEstudiante);

	
    if (!(sId_plan_nuevo).equals(datosEstudiante.getId_plan())) {
      Planes datosPlan = new Planes();
      datosPlan.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosPlan.setId_plan(sId_plan_nuevo);
      datosPlan.setId_rol(cliente.getId_rol());
      datosPlan.setUlt_usuario(cliente.getId_usuario());
      int iResultado = this.mi.setEstRegistrarCambioPlan(datosPlan);
      if (iResultado == 1) {
        return new ModelAndView("Aviso", "mensaje", "El cambio de plan se registro correctamente");
      }
    }
    return new ModelAndView("Error", "mensaje", "El cambio de plan no se registro");
  }
}