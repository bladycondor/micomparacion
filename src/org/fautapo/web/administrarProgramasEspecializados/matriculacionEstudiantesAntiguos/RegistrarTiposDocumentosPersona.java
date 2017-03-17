package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesAntiguos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Postulantes;
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


public class RegistrarTiposDocumentosPersona implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    Tramites datosTramite = new Tramites();
    //Para wayka
    String sId_proceso = request.getParameter("id_proceso");
    String sTitulo = request.getParameter("titulo");
    String sId_tramite = cliente.getString(request,"id_tramite");
    modelo.put("id_tramite",sId_tramite);
    modelo.put("titulo", sTitulo);
    modelo.put("id_proceso", sId_proceso);
    //String sId_proceso = "2"; //request.getParameter("id_proceso");
    
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    
    String sId_persona = request.getParameter("id_persona");
    String sId_estudiante = request.getParameter("id_estudiante");
    String sId_tipo_documento_p[] = request.getParameterValues("id_tipo_documento_p");
    String sId_perfil_proceso_p[] = request.getParameterValues("id_perfil_proceso_p");
    String sId_perfil_proceso="";
    int iResultadoDoc=0;
    int iBand=0;
    
    if ((sId_tramite == null) || ("".equals(sId_tramite))) {
      return new ModelAndView("Error","mensaje","El tramite no ha pasado");
    }
    
    //Recuperando los datos de id_perfil_proceso
    if (sId_perfil_proceso_p != null) {
      //RECUPERAMOS id_proceso_perfil 
      for (int i = 0; i< sId_perfil_proceso_p.length; i++) {
        System.out.println("EL ID perfil proceso NUEVO MATRIC-->"+ i +" = "+ sId_perfil_proceso_p[i]);
        if(i==0) {
	  sId_perfil_proceso += sId_perfil_proceso_p[i];
	}
	else{
	  sId_perfil_proceso += ":"+sId_perfil_proceso_p[i];
	}  
      }
      //Registramos los valores en wayka
      //Datos del id_perfil_proceso
      datosTramite = new Tramites();
      datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
      datosTramite.setEtiqueta("id_perfil_proceso");
      datosTramite.setValor(sId_perfil_proceso);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      int iResultadoR = this.mi.setRegistrarValorLimbo2(datosTramite);
    }
    else {
      return new ModelAndView("Error","mensaje","Seleccione datos para realizar el cobro respectivo en cajas");
    }
    //Fin Recupera
    
    
    if (Integer.parseInt(sId_estudiante) > 0) {
       //Busco datos del Estudiante
      Estudiantes datosEst = new Estudiantes();
      datosEst.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosEst = this.mi.getEstBuscarEstudiantePrs(datosEst);
      modelo.put("datosEst",datosEst);
      
      Personas datosP = new Personas();
      datosP.setId_persona(datosEst.getId_persona());
      datosP.setUlt_usuario(cliente.getId_usuario());
      //Registramos los tipos documentos
      if (sId_tipo_documento_p != null) {
        System.out.println("longitud del listado id_tipo_docuemnto->"+Integer.toString(sId_tipo_documento_p.length));
        //Buscar Tipo clasificacion estudiante
        Estudiantes datosClas = new Estudiantes();
        datosClas.setId_estudiante(Integer.parseInt(sId_estudiante));
        datosClas = this.mi.getBuscarTipoClasificacionEstudiante(datosClas);
        modelo.put("datosClas",datosClas);

	Postulantes datClasif = new Postulantes();
	datClasif.setId_tipo_clasificacion(datosClas.getId_tipo_clasificacion());
	List lTiposDocumentosClasf = this.mi.getListarTiposDocumentosClasificacionVigente(datClasif);
	for(int j=0; j<lTiposDocumentosClasf.size(); j++) {
	  iBand = 0;
          Postulantes documento = (Postulantes) lTiposDocumentosClasf.get(j);
          int iId_tipo_documento_l = documento.getId_tipo_documento();
          for (int i = 0; i< sId_tipo_documento_p.length; i++) {
	    if(iId_tipo_documento_l == Integer.parseInt(sId_tipo_documento_p[i])) {
	      String sId_tipo_documento = sId_tipo_documento_p[i];
	      System.out.println("EL ID TIPO DOCUMENTO -->"+ i +" = "+ sId_tipo_documento);
	      datosP.setId_tipo_documento(Integer.parseInt(sId_tipo_documento));
	      datosP.setNumero(request.getParameter("numero"+sId_tipo_documento));
	      System.out.println("El numero de documento-->"+ datosP.getNumero());
	      datosP.setObservacion(request.getParameter("observacion"+sId_tipo_documento));
	      System.out.println("La observacion de documento-->"+ datosP.getObservacion());
	      System.out.println("LA PERSONA-->"+ Integer.toString(datosP.getId_persona()));
	      System.out.println("EL uLT _USUARIO-->"+ Integer.toString(datosP.getUlt_usuario()));
	      datosP.setId_estado("A");
              iResultadoDoc  = this.mi.setRegistrarPrsDocumentos(datosP);
	      iBand=1;
	    }  
          }
	  if(iBand==0){
	    datosP.setId_tipo_documento(iId_tipo_documento_l);
	    datosP.setNumero("0");
	    datosP.setObservacion("");
	    datosP.setId_estado("N");
            iResultadoDoc  = this.mi.setRegistrarPrsDocumentos(datosP);
	  }
	}  
      }
      
      if (iResultadoDoc == 1) {
        //Listamos los documentos que se registraron con estado N
	datosP.setId_estado("N");
	List lPrsDocumentosEstadoN = this.mi.getListarPrsDocumentosPersona(datosP);
	if(lPrsDocumentosEstadoN.size() >0) {
	  //Sacando los datos del estudiante 
          Estudiantes buscarEst = new Estudiantes();
          buscarEst.setId_estudiante(Integer.parseInt(sId_estudiante));
          buscarEst= this.mi.getEstBuscarEstudiantePrs(buscarEst);
          modelo.put("buscarEst",buscarEst);
	  //Buscar Tipo clasificacion persona
	  Estudiantes datosClas = new Estudiantes();
	  datosClas = this.mi.getBuscarTipoClasificacionEstudiante(buscarEst);
	  modelo.put("datosClas",datosClas);
	  //Listando Tipos Compromisos
	  List lTiposCompromisos = this.mi.getListarTiposCompromisos();
	  modelo.put("lTiposCompromisos",lTiposCompromisos);
	  modelo.put("lPrsDocumentosEstadoN",lPrsDocumentosEstadoN);
	  modelo.put("id_persona", sId_persona);
	  modelo.put("gestion",Integer.toString(cliente.getGestion()));
	  modelo.put("periodo",Integer.toString(cliente.getPeriodo()));
	  return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguos/ListarDocCompromisosPersona", modelo);
	}
	else {
	
          //Sacamos los datos del tramite
	  datosTramite = new Tramites();
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
          int iResultadoLog = this.mi.setInsertarFrLog(datosFrLog);
	}  
      }
      
      String sMensaje="Se realizo el registro";
      modelo.put("mensaje",sMensaje);
      
      return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguos/SalidaEstudiante", modelo);
    }
    
    modelo.put("gestion", Integer.toString(cliente.getGestion()));
    modelo.put("periodo", Integer.toString(cliente.getPeriodo()));
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguos/EntradaBuscarEstudiantes", modelo);
  }
}
