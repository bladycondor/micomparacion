package org.fautapo.web.administrarProgramasEspecializados.bloquearMatricula;

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


public class RegistrarBloquearMatricula implements Controller {

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
    System.out.println("El id_estudiante ->"+ Integer.toString(datosEst.getId_estudiante()));
    datosEst.setId_estado("B"); //Anulado
    System.out.println("El id_estado ->"+ datosEst.getId_estado());
    datosEst.setGestion(cliente.getGestion());
    System.out.println("La gestion ->"+ Integer.toString(datosEst.getGestion()));
    datosEst.setPeriodo(cliente.getPeriodo());
    System.out.println("El periodo ->"+ Integer.toString(datosEst.getPeriodo()));
    datosEst.setUlt_usuario(cliente.getId_usuario());
    System.out.println("El usuario ->"+ Integer.toString(datosEst.getUlt_usuario()));
	datosEst.setId_tipo_regularizacion(1);
	//System.out.println("Tipo de Regularizacion ->"+ Integer.toString(datosEst.getid_tipo_regularizacion()));
	datosEst.setObservacion("SUSPENCION VOLUNTARIA");
    //int iResultado = this.mi.setRegistrarCambiarEstadoMatricula(datosEst);
	int iResultado = this.mi.setRegistrarCambiarEstadoEstudiante(datosEst);
	int iResultado1 = this.mi.setRegistrarEstRegularizacionBloqueoEst(datosEst);
    System.out.println("El resultado -->"+ Integer.toString(iResultado));
    if((iResultado == 1)||(iResultado1 == 1)) {
      sMensaje="El Estudiante con R.U. "+ sId_estudiante+" ha sido Suspendido en la gestion "+ Integer.toString(cliente.getGestion()) + " y el periodo "+ Integer.toString(cliente.getPeriodo());  
    
      //Registramos en tr_pr_fr_log
      datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarTrPrFrLogLimbo(datosTramite);      
    }
    else {
      // O puede que ya haya sido bloqueado
      sMensaje="El Estudiante con R.U. "+ sId_estudiante+" no ha sido Suspendido  en la gestion "+ Integer.toString(cliente.getGestion()) + " y el periodo "+ Integer.toString(cliente.getPeriodo());  
    }
    
    modelo.put("mensaje", sMensaje);
    return new ModelAndView("administrarProgramasEspecializados/bloquearMatricula/SalidaEstudiante", modelo);
    
  }
}
