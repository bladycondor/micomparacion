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

public class RegistrarBloquearEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    Estudiantes datosEstudiante;
    
    //Recuperando variables del jsp
    String sId_estudiante = request.getParameter("id_estudiante");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sAccion = request.getParameter("accion");
    String sId_regularizacion = request.getParameter("id_regularizacion");
    String sId_tipo_regularizacion = request.getParameter("id_tipo_regularizacion");
    String sObservacion = request.getParameter("observacion");
    
    //Votamos los datos
    modelo.put("accion", sAccion);
    modelo.put("id_estudiante", sId_estudiante);

    if (("".equals(sId_estudiante)) && ("".equals(sGestion)) && ("".equals(sPeriodo)) && ("".equals(sAccion))
         && (sId_estudiante == null) && (sGestion == null) && (sPeriodo == null)) {
      //return new ModelAndView("administrarRegularizacionesBloqueos/BuscarEstudiantes", modelo);
      return new ModelAndView("Error","mensaje", "Faltan datos");
    }
    
    datosEstudiante = new Estudiantes();
    if(("Bloquear".equals(sAccion)) && (!"".equals(sId_tipo_regularizacion) &&(!"".equals(sGestion))&&(!"".equals(sPeriodo)))) {
      //Registramos est_regularizacion
      datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosEstudiante.setId_tipo_regularizacion(Integer.parseInt(sId_tipo_regularizacion));
      datosEstudiante.setGestion(Integer.parseInt(sGestion));
      datosEstudiante.setPeriodo(Integer.parseInt(sPeriodo));
      datosEstudiante.setObservacion(sObservacion);
      datosEstudiante.setUlt_usuario(cliente.getId_usuario());
      int iResultadoBloq = this.mi.setRegistrarEstRegularizacionBloqueoEst(datosEstudiante);
    }
    
    if("Desbloquear".equals(sAccion) && (!"".equals(sId_regularizacion))) {
      //Buscarmos la regularizacion
      datosEstudiante.setId_regularizacion(Integer.parseInt(sId_regularizacion));
      datosEstudiante.setRegularizado(true);
      datosEstudiante.setObservacion(sObservacion);
      datosEstudiante.setUlt_usuario(cliente.getId_usuario());
      int iResultadoDes = this.mi.setModificarRegularizar(datosEstudiante);
      //Si ha regularizado entonces el estudiante cambia de estado=A
      if(iResultadoDes ==1)
      datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosEstudiante.setId_estado("A");
      int iResultadoCambioEst = this.mi.setRegistrarCambiarEstadoEstudiante(datosEstudiante);
    }
    
    //Sacando los datos del Estudiante
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    //Listamos las regularizaciones del estudiante
    List lListarRegularizacionesEtudiante = this.mi.getMiListarRegularizacionesEstudiante(datosEstudiante);
    modelo.put("lListarRegularizacionesEtudiante", lListarRegularizacionesEtudiante);
  
    return new ModelAndView("administrarRegularizacionesBloqueos/ListarRegularizarBloqueosEstudiante", modelo);    
  }
}