package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesNuevos;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Planes;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2008-03-05
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2008-03-05
 */


public class RegistrarPrsEstAdmisionEspecial implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    //Para wayka
    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");
    String sTitulo_proceso = request.getParameter("titulo_proceso");
    modelo.put("titulo", sTitulo_proceso);
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_tramite", sId_tramite);
    
    String sDescuento= request.getParameter("descuento");;
    String sId_tipo_descuento= Integer.toString(cliente.getInt(request, "id_tipo_descuento"));
    //Recuperando variables de entrada
    String sId_persona = request.getParameter("id_persona");
    if("".equals(sId_persona) || (sId_persona == null))
      return new ModelAndView("Error", "mensaje", "No ingreso el dato persona");
    int iId_persona = Integer.parseInt(sId_persona);
    String sPaterno = (request.getParameter("paterno")).trim();
    String sMaterno = (request.getParameter("materno")).trim();
    String sNombres = (request.getParameter("nombres")).trim();
    String sFec_nacimiento = request.getParameter("fec_nacimiento");
    String sDireccion = (request.getParameter("direccion")).trim();
    String sTelefono = (request.getParameter("telefono")).trim();
    
    String sDip = (request.getParameter("dip")).trim();
    String sId_tipo_sexo = cliente.getString(request, "id_tipo_sexo");
    String sId_tipo_estado_civil = cliente.getString(request, "id_tipo_estado_civil");
    String sId_pais = cliente.getString(request, "id_pais_n");
    String sId_departamento = cliente.getString(request, "id_departamento_n");
    String sId_provincia = cliente.getString(request, "id_provincia_n");
    String sId_localidad = cliente.getString(request, "id_localidad_n");
    String sId_tipo_institucion = cliente.getString(request, "id_tipo_institucion");
    String sId_colegio = cliente.getString(request, "id_colegio");
    String sId_tipo_turno = cliente.getString(request, "id_tipo_turno");
    String sId_tipo_clasificacion = cliente.getString(request, "id_tipo_clasificacion");
    int iId_tipo_graduacion = cliente.getInt(request, "id_tipo_graduacion");
    String sId_tipo_empresa_telefonica = cliente.getString(request, "id_tipo_empresa_telefonica");
    int iAnio_titulacion = cliente.getInt(request, "anio_titulacion");
    String sTitulo = cliente.getString(request, "titulo");
    String sTipo_sanguineo = cliente.getString(request, "tipo_sanguineo");
    int iNro_hijos = cliente.getInt(request, "nro_hijos");
    int iNro_dependientes = cliente.getInt(request, "nro_dependientes");
    String sNro_seguro_medico = cliente.getString(request, "nro_seguro_medico");
    String sCelular = cliente.getString(request, "celular");
    String sCorreo = cliente.getString(request, "correo");
    
    //String sId_plan = request.getParameter("id_plan");
    
    
    String sId_programa = request.getParameter("id_programa");    
    String sId_tipo_admision = cliente.getString(request, "id_tipo_admision");
    String sId_tipo_grado =  cliente.getString(request, "id_tipo_grado");
    String sId_tipo_estudiante = cliente.getString(request, "id_tipo_estudiante");
    String sFec_egreso = request.getParameter("fec_egreso");
    String sFec_inscripcion = request.getParameter("fec_inscripcion");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    modelo.put("gestion", sGestion);
    modelo.put("periodo", sPeriodo);
    int iResultadoT =0;
    int iId_prg_plan = cliente.getInt(request, "id_prg_plan");
    
    //Buscamos el plan de prg_planes
    Planes datosPlan = new Planes();
    datosPlan.setId_prg_plan(iId_prg_plan);
    datosPlan = this.mi.getBuscarPrgPlan2(datosPlan);
    if(datosPlan ==null) return new ModelAndView("Error", "mensaje", "No se puede encontrar el plan en Prg Planes");
    String sId_plan = datosPlan.getId_plan();
    //Listar Perfiles Procesos
    //String sId_proceso = request.getParameter("id_proceso");
    if(!"".equals(sId_proceso) && (sId_proceso != null)) {
      //Para Cajas 
      Perfiles datoPerfil = new Perfiles();
      datoPerfil.setId_proceso(Integer.parseInt(sId_proceso));
      List lPerfilesProcesos = this.mi.getTrnMiListarPerfilesProceso(datoPerfil);
      if(lPerfilesProcesos.size() == 1) {
	modelo.put("contador","1");
      }
      modelo.put("lPerfilesProcesos", lPerfilesProcesos);
    }
    else {
      return new ModelAndView("Error", "mensaje", "No existe el proceso. Verifique");
    }
    //Fin Listar Perfiles Procesos
    //Verificacamos si es ya es un estudiante => existe persona   
    //if (iId_estudiante > 0  &&  iId_persona > 0) {
      
      if ((!"".equals(sId_plan)) && (sId_plan != null) && (!"".equals(sId_programa)) && (sId_programa != null) && (!"".equals(sId_tipo_admision)) && (!"".equals(sId_tipo_grado)) &&
          (!"".equals(sId_tipo_estudiante)) && (!"".equals(sId_tipo_clasificacion))  && 
  	  (!"".equals(sId_pais)) && (!"".equals(sId_departamento)) && (!"".equals(sId_provincia)) && (!"".equals(sId_localidad)) && (!"".equals(sId_tipo_institucion)) && (!"".equals(sId_colegio)) && (!"".equals(sId_tipo_turno)) &&
	  (!"".equals(sNombres)) && (sNombres != null) && (!"".equals(sDip)) && (sDip != null) && (!"".equals(sFec_nacimiento)) && (sFec_nacimiento != null) &&
	  (!"".equals(sDireccion)) && (!"".equals(sId_tipo_estado_civil)) && (!"".equals(sId_tipo_sexo))  && (!"".equals(sId_tipo_empresa_telefonica)) && (!"".equals(sFec_inscripcion)))
     {
        Personas datosP = new Personas();
	datosP.setId_persona(Integer.parseInt(sId_persona));
        datosP.setId_pais(Integer.parseInt(sId_pais));
        datosP.setId_departamento(Integer.parseInt(sId_departamento));
        datosP.setId_provincia(Integer.parseInt(sId_provincia));
        datosP.setId_localidad(Integer.parseInt(sId_localidad));
        datosP.setId_tipo_estado_civil(Integer.parseInt(sId_tipo_estado_civil));
        datosP.setId_tipo_sexo(Integer.parseInt(sId_tipo_sexo));
        datosP.setId_tipo_empresa_telefonica(Integer.parseInt(sId_tipo_empresa_telefonica));
        datosP.setNombres(sNombres);
        datosP.setPaterno(sPaterno);
        datosP.setMaterno(sMaterno);
        datosP.setDip(sDip);
        datosP.setFec_nacimiento(sFec_nacimiento);
        datosP.setDireccion(sDireccion);
        datosP.setTelefono(sTelefono);
        datosP.setCelular(sCelular);
        datosP.setCorreo(sCorreo);
        datosP.setAnio_titulacion(iAnio_titulacion);
        datosP.setTitulo(sTitulo);
        datosP.setTipo_sanguineo(sTipo_sanguineo);
        datosP.setNro_hijos(iNro_hijos);
        datosP.setNro_dependientes(iNro_dependientes);
        datosP.setNro_seguro_medico(sNro_seguro_medico);
        datosP.setUlt_usuario(cliente.getId_usuario());
        int iId_persona_resultado = this.mi.setRegistrarPersona(datosP);
        if(iId_persona_resultado != 0) {
          //datosP.setId_persona(iId_persona_resultado);
          datosP.setId_colegio(Integer.parseInt(sId_colegio)); 
          datosP.setId_tipo_turno(Integer.parseInt(sId_tipo_turno)); 
          datosP.setAnio_egreso(iAnio_titulacion); 
          //Registrando prs_colegios
          int iResultadoCole = this.mi.setRegistrarPrsColegio(datosP);
          if(iResultadoCole == 0)
            return new ModelAndView("Error","mesnaje", "Los datos de colegios no se registraron");
	
        }
        else {
          return new ModelAndView("Error","mensaje","Ocurrio un error. Los datos no se registraron");
        } 
    
        //Sacando la fecha actual
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dFec_inscripcion=df.parse(sFec_inscripcion);
  
        Estudiantes datosEst = new Estudiantes(); 
        datosEst.setId_persona(Integer.parseInt(sId_persona));
	System.out.println("EL ID persona en estudiante -->"+Integer.toString(datosEst.getId_persona()));
        datosEst.setId_programa(Integer.parseInt(sId_programa));
        datosEst.setId_plan(sId_plan);
        datosEst.setId_tipo_admision(Integer.parseInt(sId_tipo_admision));
        datosEst.setId_tipo_estudiante(Integer.parseInt(sId_tipo_estudiante));
        datosEst.setId_tipo_grado(Integer.parseInt(sId_tipo_grado));
        datosEst.setId_tipo_graduacion(iId_tipo_graduacion);
        datosEst.setFec_inscripcion(dFec_inscripcion);
        datosEst.setUlt_usuario(cliente.getId_usuario());
        int iId_estudiante_resultado = this.mi.setRegistrarEstudiante(datosEst);
        System.out.println("EL ID estudiante RESULTADO -->"+Integer.toString(iId_estudiante_resultado));
        int iResultado;
        
        if (iId_estudiante_resultado > 0) {
          //Crear un tramite
          Tramites tramite = new Tramites();
          tramite.setId_proceso(Integer.parseInt(sId_proceso));
          tramite.setPara(cliente.getId_usuario());
          int iId_tramite = this.mi.setInsertarTramiteLimbo(tramite); //CREA UN TRAMITE
          if (iId_tramite == 0) {
            return new ModelAndView("Error","mensaje","El tr�mite no se cre�");
          }

          //Registramos los valores en wayka
          //Datos del Nombre
          Tramites datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("prs_nombres");
          datosTramite.setValor(sNombres);
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
        
          //Datos del Paterno
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("prs_paterno");
          datosTramite.setValor(sPaterno);
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
  
          //Datos del Materno
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("prs_materno");
          datosTramite.setValor(sMaterno);
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
  	
    	  //Datos del Materno
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("prs_dip");
          datosTramite.setValor(sDip);
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
        
          //Datos del Identificador Id_persona
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("id_persona");
          datosTramite.setValor(Integer.toString(iId_persona_resultado));
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
        
          //Datos del Identificador Id_etudiante
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("id_estudiante");
          datosTramite.setValor(Integer.toString(iId_estudiante_resultado));
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
  	
      	  //Datos del Identificador descuento
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("descuento");
          datosTramite.setValor(sDescuento);
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
	  
	  //Datos del Identificador id_tipo_descuento
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("id_tipo_descuento");
          datosTramite.setValor(sId_tipo_descuento);
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
        
          //Datos de la gestion
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("gestion_matricula");
          datosTramite.setValor(sGestion);
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
        
          //Datos del Paterno
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("periodo_matricula");
          datosTramite.setValor(sPeriodo);
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
          
          //Sacando los datos del estudiante 
          Estudiantes buscarEst = new Estudiantes();
          buscarEst.setId_estudiante(iId_estudiante_resultado);
          buscarEst= this.mi.getEstBuscarEstudiantePrs(buscarEst);
          modelo.put("buscarEst",buscarEst);
  	
    	  //Registrando en est_clasificaciones
	  Estudiantes registrarClas = new Estudiantes();
	  registrarClas.setId_estudiante(iId_estudiante_resultado);
	  registrarClas.setId_tipo_clasificacion(Integer.parseInt(sId_tipo_clasificacion));
          registrarClas.setId_tipo_clasificacion_inicial(Integer.parseInt(sId_tipo_clasificacion));
	  registrarClas.setUlt_usuario(cliente.getId_usuario());  
          int iResultadoClasif = this.mi.setRegistrarEstClasificacion(registrarClas);
	  
	  //Buscar Tipo clasificacion estudiante
          Estudiantes datosClas = new Estudiantes();
          datosClas.setId_estudiante(buscarEst.getId_estudiante());
	  datosClas = this.mi.getBuscarTipoClasificacionEstudiante(datosClas);
          modelo.put("datosClas",datosClas);
	  
          //Listar TiposDocumentos*tipoclasificacion
          Postulantes tiposDoc = new Postulantes();
          tiposDoc.setId_tipo_clasificacion(Integer.parseInt(sId_tipo_clasificacion));
          List lTiposDocumentosClasf = this.mi.getListarTiposDocumentosClasificacionVigente(tiposDoc);
          modelo.put("lTiposDocumentosClasf", lTiposDocumentosClasf);
	  
	  datosClas.setId_persona(buscarEst.getId_persona());
	  datosClas.setId_tipo_clasificacion(Integer.parseInt(sId_tipo_clasificacion));
	  List lPrsDocumentosTodo= this.mi.getListarPrsDocumentosPersona(datosClas);
	  modelo.put("lPrsDocumentosTodo",lPrsDocumentosTodo);
	  List lPrsCompromisosTodo= this.mi.getListarPrsCompromisosPersona(datosClas);
	  modelo.put("lPrsCompromisosTodo",lPrsCompromisosTodo);
	  List lPrsDocumentosClasificacion= this.mi.getListarPrsDocumentosClasificacion(datosClas);
	  modelo.put("lPrsDocumentosClasificacion",lPrsDocumentosClasificacion);
	  
	  //Listando Tipos Compromisos y Documentos Presentados
	  List lTiposCompromisos = this.mi.getListarTiposCompromisos();
	  modelo.put("lTiposCompromisos",lTiposCompromisos);
	  
	  //Sacando el id_perfil_prorroga de _parametros
	  Abm formatoParametro = new Abm();
          formatoParametro.setCampo("id_perfil_prorroga");
          formatoParametro.setCodigo("mi");
          modelo.put("formatoPerfilProrroga", this.mi.getDibBuscarParametro(formatoParametro));
	  //Sacamos el formato de la fecha
          formatoParametro.setCampo("formato_fecha");
          formatoParametro.setCodigo("dibrap");
          modelo.put("formatoFecha", this.mi.getDibBuscarParametro(formatoParametro));
    
	  
          //Votamos el id_tramite
          modelo.put("id_tramite",Integer.toString(iId_tramite)); 
          modelo.put("id_estudiante",Integer.toString(iId_estudiante_resultado)); 
	  
	  return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesNuevos/ListarTiposDocumentosPersona", modelo);
	  
        }
        else {
         return new ModelAndView("Error","mensaje", "No se registro el Estudiante. Por que ya esta existe registrado en el mismo programa");
        }	  
      }
      else {
       return new ModelAndView("Error","mensaje", "LLene todos los datos obligatorios");
      }
    
  }
}
