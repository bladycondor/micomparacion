package org.fautapo.web.administrarProgramasEspecializados.matriculacionEstudiantesAntiguosPost;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Estudiantes;
import org.fautapo.domain.Perfiles;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * @autor FAUTAPO
 * @fec_registro 2008-01-07
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2008-01-07
 */


public class RegistrarModificarPersonaEstudiante implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    //Para wayka
    String sId_proceso = request.getParameter("id_proceso");
    String sTitulo_proceso = request.getParameter("titulo_proceso");
    String sGestion_matricula = cliente.getString(request, "gestion_matricula");
    String sPeriodo_matricula = cliente.getString(request, "periodo_matricula");
    modelo.put("gestion_matricula", sGestion_matricula);
    modelo.put("periodo_matricula", sPeriodo_matricula);
    
    modelo.put("titulo", sTitulo_proceso);
    modelo.put("id_proceso", sId_proceso);
    
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
    
    String sId_tipo_estudiante = cliente.getString(request, "id_tipo_estudiante"); //Nuevo-Antiguo
    String sFec_egreso = request.getParameter("fec_egreso");
    String sFec_inscripcion = request.getParameter("fec_inscripcion");
    String sDescuento = request.getParameter("descuento");
    String sId_tipo_descuento = request.getParameter("id_tipo_descuento");
    int iId_persona = cliente.getInt(request,"id_persona");
    int iId_estudiante = cliente.getInt(request,"id_estudiante");
    
    //Para wayka listar los perfiles procesos
    if(!"".equals(sId_proceso) && (sId_proceso != null)){
      //Para Cajas 
      Perfiles datoPerfil = new Perfiles();
      datoPerfil.setId_proceso(Integer.parseInt(sId_proceso));
      List lPerfilesProcesos = this.mi.getTrnMiListarPerfilesProceso(datoPerfil);
      if(lPerfilesProcesos.size() == 1){
        int iContador =1;
      }
      modelo.put("lPerfilesProcesos", lPerfilesProcesos);
    }
    else {
      return new ModelAndView("Error", "mensaje", "No existe el proceso. Verifique");
    }
    //fin
    
    int iResultadoT =0;
    if (iId_estudiante > 0  &&  iId_persona > 0) {
      if ((!"".equals(sId_tipo_estudiante)) && (!"".equals(sId_tipo_clasificacion))  && 
	  (!"".equals(sId_pais)) && (!"".equals(sId_departamento)) && (!"".equals(sId_provincia)) && (!"".equals(sId_localidad)) && (!"".equals(sId_tipo_institucion)) && (!"".equals(sId_colegio)) && (!"".equals(sId_tipo_turno)) &&
	  (!"".equals(sNombres)) && (sNombres != null) && (!"".equals(sDip)) && (sDip != null) && (!"".equals(sFec_nacimiento)) && (sFec_nacimiento != null) &&
	  (!"".equals(sDireccion)) && (!"".equals(sId_tipo_estado_civil)) && (!"".equals(sId_tipo_sexo))  && (!"".equals(sId_tipo_empresa_telefonica)) && (!"".equals(sFec_inscripcion)))
      {
        //Verificamos si el estudiante ya tiene matricula para la gestion y periodo
        Estudiantes datosMatricula = new Estudiantes();
        datosMatricula.setId_estudiante(iId_estudiante);
        datosMatricula.setGestion(Integer.parseInt(sGestion_matricula));
        datosMatricula.setPeriodo(Integer.parseInt(sPeriodo_matricula));
        datosMatricula = this.mi.getMtrBuscarMatricula(datosMatricula);
        if (datosMatricula != null) return new ModelAndView("Error","mensaje", "El estudiante con R.U. "+iId_estudiante+" ya tiene matricula para la gestion "+sGestion_matricula+" y el periodo "+sPeriodo_matricula);

        Personas datosP = new Personas();
        datosP.setId_persona(iId_persona);
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
        if (iId_persona_resultado != 0) {
          datosP.setId_colegio(Integer.parseInt(sId_colegio)); 
          datosP.setId_tipo_turno(Integer.parseInt(sId_tipo_turno)); 
          datosP.setAnio_egreso(iAnio_titulacion); 
          //Registrando prs_colegios
          int iResultadoCole = this.mi.setRegistrarPrsColegio(datosP);
          if(iResultadoCole == 0)
            return new ModelAndView("Error","mensaje", "Los datos de colegios no se registraron");
          //Registrando en est_clasificaciones
	  Estudiantes registrarClas = new Estudiantes();
	  registrarClas.setId_estudiante(iId_estudiante);
          registrarClas.setId_tipo_clasificacion(Integer.parseInt(sId_tipo_clasificacion));
	  registrarClas.setUlt_usuario(cliente.getId_usuario());  
          int iResultadoClasif = this.mi.setRegistrarEstClasificacion(registrarClas);
	}  
        else {
          return new ModelAndView("Error","mensaje","Ocurrio un error. Los datos no se registraron");
        } 
      
        //Sacando la fecha actual
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dFec_inscripcion=df.parse(sFec_inscripcion);
        //Cambiando solo el tipo de estudiante= Nuevo-Antiguo
        Estudiantes datosEst = new Estudiantes(); 
	datosEst.setId_estudiante(iId_estudiante);
	datosEst.setId_tipo_estudiante(Integer.parseInt(sId_tipo_estudiante));
	datosEst.setUlt_usuario(cliente.getId_usuario());
	int iResultadoTipoEst = this.mi.setModificarTipoEstudiante(datosEst); 
	
        //System.out.println("EL ID estudiante RESULTADO -->"+Integer.toString(iId_estudiante));
        int iResultado;
	//Si registro el tipo_estudiante => vemos sus documentos
	if (iResultadoTipoEst > 0) {
	  //Crear un tramite
          Tramites tramite = new Tramites();
          tramite.setId_proceso(Integer.parseInt(sId_proceso));
          tramite.setPara(cliente.getId_usuario());
          int iId_tramite = this.mi.setInsertarTramiteLimbo(tramite); //CREA UN TRAMITE
          if (iId_tramite == 0) {
            return new ModelAndView("Error","mensaje","El tramite no se creo");
          }
	  //Datos de nombres
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
	  //Datos del Dip
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("prs_dip");
          datosTramite.setValor(sDip);
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
	  
	  //Datos del id_estudiante
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("id_estudiante");
          datosTramite.setValor(Integer.toString(iId_estudiante));
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
	  
	  //Datos el descuento
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("descuento");
          datosTramite.setValor(sDescuento);
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      
          //Datos del id_tipo_descuento
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("id_tipo_descuento");
          datosTramite.setValor(sId_tipo_descuento);
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
	  //Datos de gestion matriculacion
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("gestion_matricula");
          datosTramite.setValor(sGestion_matricula);
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
	  //Datos del periodo de matriculacion
          datosTramite = new Tramites();
          datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("periodo_matricula");
          datosTramite.setValor(sPeriodo_matricula);
          datosTramite.setUlt_usuario(cliente.getId_usuario());
          iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
	  modelo.put("gestion_matricula",sGestion_matricula);
	  modelo.put("periodo_matricula",sPeriodo_matricula);
          //Listados para mostrar en la siguiente pantalla	
	  //Sacando los datos del estudiante 
          Estudiantes buscarEst = new Estudiantes();
          buscarEst.setId_estudiante(iId_estudiante);
          buscarEst= this.mi.getEstBuscarEstudiantePrs(buscarEst);
          modelo.put("buscarEst",buscarEst);
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
          modelo.put("id_estudiante",Integer.toString(iId_estudiante)); 
	  
          return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosPost/ListarTiposDocumentosPersona", modelo);
	}
	
      }
      else {
         return new ModelAndView("Error","mensaje", "LLene todos los datos obligatorios");
      }
    } 
    else {
         return new ModelAndView("Error","mensaje", "No existe el estudiante");
    }

    return new ModelAndView("administrarProgramasEspecializados/matriculacionEstudiantesAntiguosPost/EntradaBuscarEstudiantes", modelo);
  }
}
