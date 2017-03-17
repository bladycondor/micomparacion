package org.fautapo.web.administrarProgramasEspecializados.desBloquearMatricula;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class RegistrarDesBloquearMatricula implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    String sId_estudiante = request.getParameter("id_estudiante");
    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");
    String sMensaje="";Tramites datosTramite = new Tramites();
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_estudiante", sId_estudiante);
    modelo.put("id_tramite", sId_tramite);

    try {
      int iId_estudiante = Integer.parseInt(sId_estudiante);
    }
    catch(Exception e) {
      return new ModelAndView("Error", "mensaje","No  se encuentra al estudiante");
    }

    //Buscamos los datos del proceso
    Actividades datosProceso = new Actividades();
    datosProceso.setId_proceso(Integer.parseInt(request.getParameter("id_proceso")));
    datosProceso = this.mi.getBuscarProceso(datosProceso);
    modelo.put("datosProceso", datosProceso);
    
    //Registrar la Anulacion del RU
    Estudiantes datosEst = new Estudiantes();
    datosEst.setId_estudiante(Integer.parseInt(sId_estudiante));
    datosEst.setId_estado("A"); //Anulado
    datosEst.setGestion(cliente.getGestion());
    datosEst.setPeriodo(cliente.getPeriodo());
    datosEst.setUlt_usuario(cliente.getUlt_usuario());
    int iResultado = this.mi.setRegistrarCambiarEstadoMatricula(datosEst);
    if(iResultado == 1) {
      sMensaje="La matricula del Estudiante con R.U. "+ sId_estudiante+" ha sido Desbloqueado para la gestion "+ Integer.toString(cliente.getGestion()) + " y el periodo "+ Integer.toString(cliente.getPeriodo());  
    
      //Registramos en tr_pr_fr_log
      datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarTrPrFrLogLimbo(datosTramite);      
    }
    else {
      // O puede que ya haya sido bloqueado
      sMensaje="La matricula del Estudiante con R.U. "+ sId_estudiante+" no se encuentra bloqueadoo para la gestion "+ Integer.toString(cliente.getGestion()) + " y el periodo "+ Integer.toString(cliente.getPeriodo());  
    }
    
    modelo.put("mensaje", sMensaje);
    return new ModelAndView("administrarProgramasEspecializados/desBloquearMatricula/SalidaEstudiante", modelo);
    
  }
}
