package org.fautapo.web.administrarDeudas;

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

public class RegistrarDeudaEstudiante implements Controller {

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
    String sId_est_deuda = request.getParameter("id_est_deuda");
    String sId_tipo_deuda = request.getParameter("id_tipo_deuda");
    String sObservacion = request.getParameter("observacion");
    String sId_programa = request.getParameter("id_programa");
    //Votamos los datos
    modelo.put("accion", sAccion);
    modelo.put("id_estudiante", sId_estudiante);
    modelo.put("id_programa", sId_programa);

    if (("".equals(sId_estudiante))  && ("".equals(sAccion))
         && (sId_estudiante == null)) {
      return new ModelAndView("Error","mensaje", "Faltan datos");
    }
    
    datosEstudiante = new Estudiantes();
    if(("Nuevo".equals(sAccion)) && (!"".equals(sId_tipo_deuda) && (sId_tipo_deuda != null) &&(!"".equals(sGestion))&&(!"".equals(sPeriodo)))) {
      //Registramos est_deudas
      datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosEstudiante.setId_tipo_deuda(Integer.parseInt(sId_tipo_deuda));
      datosEstudiante.setGestion(Integer.parseInt(sGestion));
      datosEstudiante.setPeriodo(Integer.parseInt(sPeriodo));
      datosEstudiante.setObservacion(sObservacion);
      datosEstudiante.setUlt_usuario(cliente.getId_usuario());
      int iResultadoDeuda = this.mi.setRegistrarEstDeuda(datosEstudiante);
    }
    
    if("CancelarDeuda".equals(sAccion) && (!"".equals(sId_est_deuda)) && (sId_est_deuda != null)) {
      //Buscarmos la regularizacion
      datosEstudiante.setId_est_deuda(Integer.parseInt(sId_est_deuda));
      //datosEstudiante.setCancelado(true);
      datosEstudiante.setObservacion(sObservacion);
      datosEstudiante.setUlt_usuario(cliente.getId_usuario());
      int iResultadoCanc = this.mi.setModificarEstDeuda(datosEstudiante);
    }
    
    //Sacando los datos del Estudiante
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    //Listamos las regularizaciones del estudiante
    List lListarDeudasEstudiantes = this.mi.getListarDeudasEstudiante(datosEstudiante);
    modelo.put("lListarDeudasEstudiantes", lListarDeudasEstudiantes);  
    
    return new ModelAndView("administrarDeudas/ListarDeudasEstudiante", modelo);
  }
}