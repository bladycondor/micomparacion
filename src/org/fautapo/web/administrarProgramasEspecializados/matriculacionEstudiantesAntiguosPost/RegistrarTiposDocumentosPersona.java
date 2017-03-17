package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesAntiguosPost;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.lang.Boolean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Instituciones;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Perfiles;

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
    String sNombres = cliente.getNombres();
    modelo.put("usuario", sNombres);
    String sGestion =request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    
    Tramites datosTramite = new Tramites();
    //Para wayka
    String sId_proceso = request.getParameter("id_proceso");
    String sTitulo = request.getParameter("titulo");
    String sId_tramite = cliente.getString(request,"id_tramite");
    modelo.put("id_tramite",sId_tramite);
    modelo.put("titulo", sTitulo);
    modelo.put("id_proceso", sId_proceso);
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    
    String sProrroga= request.getParameter("prorroga");
    boolean bProrroga =false;
    String sId_persona = request.getParameter("id_persona");
    String sId_estudiante = request.getParameter("id_estudiante");
    String sId_tipo_documento_p[] = request.getParameterValues("id_tipo_documento_p");
    String sId_tipo_compromiso_p[] = request.getParameterValues("id_tipo_compromiso_p");
    String sId_perfil_proceso_p[] = request.getParameterValues("id_perfil_proceso_p");
    String sId_perfil_proceso="";
    String sHabilitar = request.getParameter("habilitar");
    int iResultadoDoc=0; int iResultadoCom=0;
    int iBand=0;
    
    //Sacamos el identificador de la prorroga de parametros
    Abm prorroga = new Abm();
    prorroga.setCampo("id_perfil_prorroga");
    prorroga.setCodigo("mi");
    String sId_prorroga = this.mi.getDibBuscarParametro(prorroga);
    if(("".equals(sId_prorroga)) || (sId_prorroga ==null))
      return new ModelAndView("Error","mensaje","El id_perfil_prorroga no existe registrado en _parametros");
    
    //Recuperando los datos de id_perfil_proceso
    if ((sId_tramite == null) || ("".equals(sId_tramite))) {
      return new ModelAndView("Error","mensaje","El tramite no ha pasado");
    }
    
    if (Integer.parseInt(sId_estudiante) > 0) {
       //Busco datos del Estudiante
      Estudiantes datosEst = new Estudiantes();
      datosEst.setId_estudiante(Integer.parseInt(sId_estudiante));
      datosEst = this.mi.getEstBuscarEstudiantePrs(datosEst);
      modelo.put("datosEst",datosEst);
      //Buscar Tipo clasificacion estudiante
      Estudiantes datosClas = new Estudiantes();
      datosClas.setId_estudiante(datosEst.getId_estudiante());
      datosClas = this.mi.getBuscarTipoClasificacionEstudiante(datosClas);
      modelo.put("datosClas",datosClas);
      
      //Registramos el desbloqueo del estudiante
      /*Estudiantes estado = new Estudiantes();
      estado.setUlt_usuario(cliente.getId_usuario());
      estado.setId_estudiante(datosEst.getId_estudiante());
      estado.setId_estado(datosEst.getId_estado());
      if("Si".equals(sHabilitar)) {
        estado.setId_estado("A");
      }
      int iResBloquear = this.mi.setRegistrarCambiarEstadoEstudiante(estado);
      //Fin desbloquear
      */
      
      Personas datosP = new Personas();
      datosP.setId_persona(datosEst.getId_persona());
      datosP.setUlt_usuario(cliente.getId_usuario());
      datosP.setId_tipo_clasificacion(datosClas.getId_tipo_clasificacion());
      System.out.println("El datosP.setId_tipo_clasificacion -->"+ Integer.toString(datosP.getId_tipo_clasificacion()));
      //Listamos los prs_documentos clasificacion
      List lPrsDocumentosClasificacion= this.mi.getListarPrsDocumentosClasificacion(datosP);
      modelo.put("lPrsDocumentosClasificacion",lPrsDocumentosClasificacion);
      System.out.println("El tamanio de la lista -->"+ Integer.toString(lPrsDocumentosClasificacion.size()));
      
      //Registramos los tipos documentos
      int iContador =0;
      //if (lPrsDocumentosClasificacion.size() > 0) {
      for(int j=0; j<lPrsDocumentosClasificacion.size(); j++) {
        iBand = 0;
        Personas documento = (Personas) lPrsDocumentosClasificacion.get(j);
	String sId_tipo_documento_sac = Integer.toString(documento.getId_tipo_documento());
	//System.out.println(" sId_tipo_documento_sac -->"+  sId_tipo_documento_sac);
	datosP.setId_tipo_documento(Integer.parseInt(sId_tipo_documento_sac));
        String sPresento = request.getParameter("presento"+sId_tipo_documento_sac);
	if ("true".equals(sPresento)) {
          datosP.setPresento(true);
        } 
	else {
          datosP.setPresento(false);
        }
	datosP.setNumero(request.getParameter("numero"+sId_tipo_documento_sac));
	datosP.setObservacion(request.getParameter("observacion"+sId_tipo_documento_sac));
        iResultadoDoc  = this.mi.setRegistrarPrsDocumentos(datosP);
	iBand=1;
	//Registramos compromisos
	//String sId_tipo_compromiso_sac = request.getParameter("id_tipo_compromiso_"+sId_tipo_documento_sac);
	String sProrroga_sac = request.getParameter("prorroga"+sId_tipo_documento_sac);
        //System.out.println("QUE ES  sId_tipo_compromiso_sac -->"+ sId_tipo_compromiso_sac);
	//if ((!"".equals(sId_tipo_compromiso_sac)) && (sId_tipo_compromiso_sac != null)) {
	if (("true".equals(sProrroga_sac)) && (sProrroga_sac != null)) {
          String sId_tipo_compromiso_sac = request.getParameter("id_tipo_compromiso_"+sId_tipo_documento_sac);
	  if("".equals(sId_tipo_compromiso_sac))
	    return new ModelAndView("Error", "mensaje", "No selecciono el tipo de prorroga.");
	  datosP.setId_tipo_compromiso(Integer.parseInt(sId_tipo_compromiso_sac));
          datosP.setGestion(Integer.parseInt(sGestion));
	  datosP.setPeriodo(Integer.parseInt(sPeriodo));
	  datosP.setObservacion(request.getParameter("observacionCompromiso"+sId_tipo_documento_sac));
	  datosP.setFec_vencimiento(request.getParameter("fec_vencimiento"+sId_tipo_documento_sac));
	  iResultadoCom  = this.mi.setRegistrarPrsCompromisos(datosP);
	  if(iResultadoCom ==1) {
	    bProrroga=true;
            iContador = iContador+1;		 
	  }  
	  System.out.println("el contador es -->"+iContador);
        }
      }
      //}
      
     
      if (sId_perfil_proceso_p != null) {
        //RECUPERAMOS id_proceso_perfil
        for (int i = 0; i< sId_perfil_proceso_p.length; i++) {
          int iCantidad = 1;
	  //Buscar id_perfil en perfil_procesos
	  Perfiles datosPerfilProceso= new Perfiles();
	  datosPerfilProceso.setId_perfil_proceso(sId_perfil_proceso_p[i]);
	  //System.out.println("datosPerfilProceso.getId_perfil_proceso2---------"+Integer.toString(datosPerfilProceso.getId_perfil_proceso2()));
	  datosPerfilProceso= this.mi.getTrnBuscarPerfilProceso(datosPerfilProceso);
	  //System.out.println("datosPerfilProceso.getId_perfil()---------"+Integer.toString(datosPerfilProceso.getId_perfil()));
          if (datosPerfilProceso.getId_perfil()==Integer.parseInt(sId_prorroga)) {
	    iCantidad = iContador;
          }
          if(i==0) {
  	    sId_perfil_proceso += sId_perfil_proceso_p[i]+"~"+iCantidad;
	  } else {
	    sId_perfil_proceso += ":"+sId_perfil_proceso_p[i]+"~"+iCantidad;
	  }  
        }
        System.out.println("id_perfil_proceso---------"+sId_perfil_proceso);
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
      
      //Sacamos los datos del tramite
      datosTramite = new Tramites(); 
      datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      int iResultado = this.mi.setRegistrarTrPrFrLogLimbo(datosTramite);      
      
      //Sacamos los datos de la institucion
      Instituciones datosInstitucion = new Instituciones();
      datosInstitucion.setId_institucion(1); //--------------------------ESTATICO
      datosInstitucion = this.mi.getBuscarInstitucion(datosInstitucion);
      if (datosInstitucion !=null) {
        modelo.put("datosInstitucion", datosInstitucion);
      }
    
      //Sacamos el formato de la fecha
      Abm formatoFecha = new Abm();
      formatoFecha.setCampo("formato_fecha");
      formatoFecha.setCodigo("dibrap");
      modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoFecha));
      //Sacamos el formato de la hora
      formatoFecha.setCampo("formato_hora");
      formatoFecha.setCodigo("dibrap");
      modelo.put("formatoHora", this.mi.getDibBuscarParametro(formatoFecha));
    
      Tramites tramite = new Tramites();
      tramite.setId_tramite(Integer.parseInt(sId_tramite));
      //Sacando la gestion de wayka
      tramite.setEtiqueta("gestion_matricula");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      int iGestion = Integer.parseInt(tramite.getValores());
      //Sacando el periodo de wayka  
      tramite.setEtiqueta("periodo_matricula");
      tramite = (Tramites) this.mi.getBuscarCampoGw(tramite);
      int iPeriodo = Integer.parseInt(tramite.getValores());  
      String sMensaje="Se realizo el registro";
      modelo.put("mensaje",sMensaje);
      modelo.put("prorroga",Boolean.toString(bProrroga));  
      modelo.put("contador",Integer.toString(iContador));
      modelo.put("gestion_matricula", Integer.toString(iGestion));
      modelo.put("periodo_matricula", Integer.toString(iPeriodo));
      //return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesNuevos/SalidaImpresionEstudiante", modelo);
      return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosPost/SalidaImpresionEstudiante", modelo);
    }
    
    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosPost/EntradaBuscarEstudiantes", modelo);
  }
}
