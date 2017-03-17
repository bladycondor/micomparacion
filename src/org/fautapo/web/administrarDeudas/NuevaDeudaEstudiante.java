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

public class NuevaDeudaEstudiante implements Controller {

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
    String sId_est_deuda = request.getParameter("id_est_deuda");
    //Votamos los datos
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));
    modelo.put("accion", sAccion);
    System.out.println("LA ACCION ES -->"+sAccion);
    if (("".equals(sId_estudiante))|| (sId_estudiante == null)) {
      return new ModelAndView("administrarDeudas/BuscarEstudiantes", modelo);
    }
    
    //Sacando los datos del estudiante    
    datosEstudiante = new Estudiantes();
    datosEstudiante.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEstudiante = this.mi.getEstBuscarEstudiantePrs(datosEstudiante);
    modelo.put("datosEstudiante", datosEstudiante);
    
    if("Nuevo".equals(sAccion)) {
      //Listamos tipos deudas
      List lTiposDeudas = this.mi.getMiListarTiposDeudas();
      modelo.put("lTiposDeudas", lTiposDeudas);
      
      return new ModelAndView("administrarDeudas/NuevaDeudaEstudiante", modelo);
    }
    
    if(("CancelarDeuda".equals(sAccion)) &&(!"".equals(sId_est_deuda)) &&(sId_est_deuda != null)) {
      //Buscarmos la est_deuda
      datosEstudiante.setId_est_deuda(Integer.parseInt(sId_est_deuda));
      Estudiantes datosEstDeuda = this.mi.getMiBuscarEstDeuda(datosEstudiante);
      modelo.put("datosEstDeuda", datosEstDeuda);
      return new ModelAndView("administrarDeudas/ConfirmarDeudaEstudiante", modelo);
    }
    
    return new ModelAndView("administrarRegularizacionesBloqueos/BuscarEstudiantes", modelo);
  }
}