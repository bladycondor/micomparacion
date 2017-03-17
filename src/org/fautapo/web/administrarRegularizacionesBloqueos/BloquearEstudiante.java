package org.fautapo.web.administrarRegularizacionesBloqueos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Programas;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class BloquearEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante;
    
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sAccion = request.getParameter("accion");
    String sId_regularizacion = request.getParameter("id_regularizacion");
    //Votamos los datos
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));
    modelo.put("accion", sAccion);
    System.out.println("LA ACCION ES -->"+sAccion);
    if (("".equals(sId_estudiante))|| (sId_estudiante == null)) {
      return new ModelAndView("administrarRegularizacionesBloqueos/BuscarEstudiantes", modelo);
    }
    
    //Sacando los datos del estudiante    
    datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    
    if("Bloquear".equals(sAccion)) {
      //Listamos tipos Regularizacion
      List lTiposRegularizaciones = this.mi.getMiListarTiposRegularizaciones();
      modelo.put("lTiposRegularizaciones", lTiposRegularizaciones);
      return new ModelAndView("administrarRegularizacionesBloqueos/BloquearEstudiante", modelo);
    }
    
    if(("Desbloquear".equals(sAccion)) &&(!"".equals(sId_regularizacion))) {
      //Buscarmos la regularizacion
      datosEstudiante.setId_regularizacion(Integer.parseInt(sId_regularizacion));
      Estudiantes datosRegularizacion = this.mi.getMiBuscarEstRegularizacion(datosEstudiante);
      modelo.put("datosRegularizacion", datosRegularizacion);
      return new ModelAndView("administrarRegularizacionesBloqueos/DesBloquearEstudiante", modelo);
    }
    
    return new ModelAndView("administrarRegularizacionesBloqueos/BuscarEstudiantes", modelo);
  }
}