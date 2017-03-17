package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesAntiguosPost;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-05
 */


public class RegistrarPrsCompromisos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    //Para wayka
    String sId_proceso = request.getParameter("id_proceso");
    String sTitulo = request.getParameter("titulo");
    String sId_tramite = request.getParameter("id_tramite");
    modelo.put("id_tramite",sId_tramite);
    modelo.put("titulo", sTitulo);
    modelo.put("id_proceso", sId_proceso);
    
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    
    String sId_persona = request.getParameter("id_persona");
    String sId_estudiante = request.getParameter("id_estudiante");
    String sId_tipo_compromiso_p[] = request.getParameterValues("id_tipo_compromiso_p");
    String sBloquear = request.getParameter("bloquear");
    int iResultadoDoc=0;
    int iBand=0;
    
    if (Integer.parseInt(sId_estudiante) > 0) {
      if (Integer.parseInt(sId_tramite) == 0) {
        return new ModelAndView("Error","mensaje","El tramite no ha pasado");
      }
      //Busco datos del Estudiante
      Estudiantes datosEst = new Estudiantes();
      datosEst.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosEst = this.mi.getEstBuscarEstudiantePrs(datosEst);
      modelo.put("datosEst",datosEst);
      
      //REgistramos el desbloqueo del estudiante
      Estudiantes estado = new Estudiantes();
      estado.setUlt_usuario(cliente.getId_usuario());
      estado.setId_estudiante(datosEst.getId_estudiante());
      estado.setId_estado("B");
      if("Si".equals(sBloquear)){
        estado.setId_estado("A");
      }
      int iResBloquear = this.mi.setRegistrarCambiarEstadoEstudiante(estado);
      //Fin desbloquear
      
      Personas datosP = new Personas();
      datosP.setId_persona(datosEst.getId_persona());
      datosP.setUlt_usuario(cliente.getId_usuario());
      datosP.setId_tipo_clasificacion(datosEst.getId_tipo_clasificacion());
      //Listamos los prs_documentos clasificacion
      List lPrsDocumentosClasificacion= this.mi.getListarPrsDocumentosClasificacion(datosP);
      modelo.put("lPrsDocumentosClasificacion",lPrsDocumentosClasificacion);
     
      //Registramos los prs_compromisos
      if ( lPrsDocumentosClasificacion.size() > 0) {
          for (int i = 0; i< lPrsDocumentosClasificacion.size(); i++) {
	     String sId_tipo_compromiso = sId_tipo_compromiso_p[i];
	     System.out.println("EL ID TIPO compromiso -->"+ i +" = "+ sId_tipo_compromiso);
	     datosP.setId_tipo_compromiso(Integer.parseInt(sId_tipo_compromiso));
	     datosP.setGestion(cliente.getGestion());
	     datosP.setPeriodo(cliente.getPeriodo());
	     datosP.setObservacion(request.getParameter("observacion"+sId_tipo_compromiso));
	     datosP.setFec_vencimiento(request.getParameter("fec_vencimiento"+sId_tipo_compromiso));
	     System.out.println("La observacion de compromiso-->"+ datosP.getObservacion());
	     System.out.println("LA PERSONA-->"+ Integer.toString(datosP.getId_persona()));
	     System.out.println("EL uLT _USUARIO-->"+ Integer.toString(datosP.getUlt_usuario()));
             iResultadoDoc  = this.mi.setRegistrarPrsCompromisos(datosP);
          }
      }
      
      
      if (iResultadoDoc == 1) {
        //Sacamos los datos del tramite
	Tramites datosTramite = new Tramites();
        datosTramite = new Tramites();
	datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
        datosTramite = this.mi.getBuscarTramite(datosTramite);
        
	//Sacamos los datos del formulario
	Campos datosForm = new Campos();
	datosForm.setId_proceso(datosTramite.getId_proceso());
	datosForm = this.mi.getBuscarFormulario1(datosForm);

        //insertamos los datos en la tabla tr_fr_log para verficar que el usuario reviso el formulario
        Tramites datosFrLog = new Tramites();
        datosFrLog.setId_tramite(Integer.parseInt(sId_tramite)); 
        datosFrLog.setId_proceso(datosTramite.getId_proceso());
        datosFrLog.setId_form(datosForm.getId_form());
        datosFrLog.setId_actividad(datosTramite.getId_actividad_actual());
        datosFrLog.setId_estado("R");
        datosFrLog.setUlt_usuario(cliente.getId_usuario());
        int iResultado = this.mi.setInsertarFrLog(datosFrLog);
      }
      
      String sMensaje="Se realizo el registro";
      modelo.put("mensaje",sMensaje);
      
      return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosPost/SalidaEstudiante", modelo);
    }
    
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosPost/EntradaBuscarEstudiantes", modelo);
  }
}
