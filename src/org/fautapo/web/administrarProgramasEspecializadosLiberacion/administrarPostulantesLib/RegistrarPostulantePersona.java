package org.fautapo.web.administrarProgramasEspecializadosLiberacion.administrarPostulantesLib;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Postulantes;
import org.fautapo.domain.Planes;
import org.fautapo.domain.Tramites;
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


public class RegistrarPostulantePersona implements Controller {

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
    String sTitulo = request.getParameter("titulo");
    modelo.put("titulo", sTitulo);
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_tramite", sId_tramite);

    int iId_postulante_resultado = 0;    
    String sId_persona = request.getParameter("id_persona");
    String sId_plan = request.getParameter("id_plan");
    String sId_programa = request.getParameter("id_programa");    
    String sId_tipo_admision = request.getParameter("id_tipo_admision");
    String sId_tipo_grado = request.getParameter("id_tipo_grado");
    String sFec_inscripcion = request.getParameter("fec_inscripcion");
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sId_tipo_documento_p[] = request.getParameterValues("id_tipo_documento_p");
    String sDescuento = request.getParameter("descuento");
    String sId_tipo_descuento = request.getParameter("id_tipo_descuento");
    //Nuevo request
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
    int iId_tipo_graduacion = cliente.getInt(request, "id_tipo_graduacion");
    String sId_tipo_empresa_telefonica = cliente.getString(request, "id_tipo_empresa_telefonica");
    String sAnio_egreso = cliente.getString(request, "anio_egreso");
    String sCelular = cliente.getString(request, "celular");
    String sCorreo = cliente.getString(request, "correo");
    //Fin Nuevo
    
    int iResultadoT =0; int iResultado; String sBandera;
    
    if("".equals(sId_persona) || (sId_persona == null)) {
      return new ModelAndView("Error", "mensaje", "Faltan Datos");
    }
    
    //Primero comprobamos si entro  el proceso
    if(!"".equals(sId_proceso) && (sId_proceso != null)) {
      //Para Cajas 
      Perfiles datoPerfil = new Perfiles();
      datoPerfil.setId_proceso(Integer.parseInt(sId_proceso));
      List lPerfilesProcesos = this.mi.getTrnMiListarPerfilesProceso(datoPerfil);
      if(lPerfilesProcesos.size() == 1){
         sBandera ="1";
      }
      modelo.put("lPerfilesProcesos", lPerfilesProcesos);
    }
    else {
      return new ModelAndView("Error", "mensaje", "No existe el proceso. Verifique");
    }

    //Buscamos en prg_planes
    Planes buscarPlan = new Planes();
    buscarPlan.setId_programa(Integer.parseInt(sId_programa));
    buscarPlan.setId_tipo_grado(2); //Vestibular
    Planes buscarPrgPlan = this.mi.getBuscarMaxPrgPlanActual(buscarPlan);
    if (buscarPrgPlan.getPlan() == null) {
      return new ModelAndView("Error", "mensaje", "No existe planificado vestibulares para el programa seleccionado");
    }
    
    //Registramos a Pst Personas
    Postulantes datosP =new Postulantes();
    datosP.setId_persona(Integer.parseInt(sId_persona));
    datosP.setNombres(sNombres);
    datosP.setPaterno(sPaterno);
    datosP.setMaterno(sMaterno);
    datosP.setDip(sDip);
    datosP.setDireccion(sDireccion);
    datosP.setTelefono(sTelefono);
    datosP.setCorreo(sCorreo);
    datosP.setCelular(sCelular);
    datosP.setFec_nacimiento(sFec_nacimiento);
    datosP.setId_pais(Integer.parseInt(sId_pais));
    datosP.setId_departamento(Integer.parseInt(sId_departamento));
    datosP.setId_provincia(Integer.parseInt(sId_provincia));
    datosP.setId_localidad(Integer.parseInt(sId_localidad));
    datosP.setId_tipo_estado_civil(Integer.parseInt(sId_tipo_estado_civil));
    datosP.setId_tipo_sexo(Integer.parseInt(sId_tipo_sexo));
    datosP.setId_tipo_empresa_telefonica(Integer.parseInt(sId_tipo_empresa_telefonica));
    datosP.setUlt_usuario(cliente.getId_usuario());
    datosP.setId_rol(cliente.getId_rol());
    int iId_persona = this.mi.setMiRegistrarPstPersona(datosP);
    //Registramos los cambios de colegios
    datosP.setId_colegio(Integer.parseInt(sId_colegio));
    datosP.setId_tipo_turno(Integer.parseInt(sId_tipo_turno));
    datosP.setAnio_egreso(Integer.parseInt(sAnio_egreso));
    int iResultadoColegio = this.mi.setRegistrarPstPrsColegio(datosP);
     
    //Registramos Postulante
    datosP.setId_programa(Integer.parseInt(sId_programa));
    datosP.setId_plan(buscarPrgPlan.getPlan()); //Plan sacado de prg_planes
    datosP.setId_tipo_grado(2);  // Vestibular
    datosP.setId_tipo_admision(Integer.parseInt(sId_tipo_admision));
    datosP.setId_tipo_clasificacion(1); // Vestibular= Clasificacion=1
    datosP.setGestion(Integer.parseInt(sGestion));    
    datosP.setPeriodo(Integer.parseInt(sPeriodo));
    datosP.setId_rol(cliente.getId_rol());
    datosP.setUlt_usuario(cliente.getId_usuario());
    try {
      iId_postulante_resultado = this.mi.setMiRegistrarPostulanteC(datosP); //Postulante
      System.out.println("El id_postulante_resultado -->" + Integer.toString(iId_postulante_resultado));
      
    }
    catch(Exception e) {
      return new ModelAndView("Aviso","mensaje","La persona ya esta registrada como postulante con un mismo tipo de admision para la gestion "+sGestion+ " y el periodo " + sPeriodo);
    }
      
    if (iId_postulante_resultado > 0) {
      //Buscar Datos Pst_persona
      Postulantes datosPostulante = new Postulantes();
      datosPostulante.setId_persona(Integer.parseInt(sId_persona));
      datosPostulante = this.mi. getPstBuscarPersona(datosPostulante);
      
      //Crear un tramite
      Tramites tramite = new Tramites();
      tramite.setId_proceso(Integer.parseInt(sId_proceso));
      tramite.setPara(cliente.getId_usuario());
      int iId_tramite = this.mi.setInsertarTramiteLimbo(tramite); //CREA UN TRAMITE
      if (iId_tramite == 0) {
        return new ModelAndView("Error","mensaje","El tramite no se creo");
      }
      //Registrando documentos      
      if (sId_tipo_documento_p != null) {
        for (int i = 0; i< sId_tipo_documento_p.length; i++) {
    	  String sId_tipo_documento = sId_tipo_documento_p[i];
	  datosP.setId_tipo_documento(Integer.parseInt(sId_tipo_documento));
	  datosP.setNumero(request.getParameter("numero"+sId_tipo_documento));
	  datosP.setObservacion(request.getParameter("observacion"+sId_tipo_documento));
          iResultadoT  = this.mi.setPstRegistrarDocumentos(datosP);
        }
      }

      //Registramos los valores en wayka
      //Datos del Nombre
      Tramites datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("pst_nombres");
      datosTramite.setValor(datosPostulante.getNombres());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
    
      //Datos del Paterno
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("pst_paterno");
      datosTramite.setValor(datosPostulante.getPaterno());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
  
      //Datos del Materno
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("pst_materno");
      datosTramite.setValor(datosPostulante.getMaterno());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
        
      //Datos del dip
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("pst_dip");
      datosTramite.setValor(datosPostulante.getDip());
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      
      //Datos del Identificador ID_postulante
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("id_postulante");
      datosTramite.setValor(Integer.toString(iId_postulante_resultado));
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
        
      //Mandar a sacar el id_perfil del postulante.Buscar Datos del postulante
      modelo.put("id_postulante",Integer.toString(iId_postulante_resultado)); 
      //Sacando los datos del postulante    
      //Postulantes datosPostulante = new Postulantes();
      datosPostulante.setId_postulante(iId_postulante_resultado);
      datosPostulante = this.mi.getPstBuscarPostulanteNombres(datosPostulante);
      modelo.put("datosPostulante",datosPostulante);
        
      //Votamos el id_tramite
      modelo.put("id_tramite",Integer.toString(iId_tramite)); 
        
      return new ModelAndView("administrarProgramasEspecializadosLiberacion/administrarPostulantesLib/ListarPerfilesPostulante", modelo);
    }
    else {
      return new ModelAndView("Error","mensaje","No se registro los datos como postulante");
    }
    
    //modelo.put("gestion",sGestion);
    //return new ModelAndView("administrarProgramasEspecializados/administrarPostulantes/EntradaBuscarPostulantes", modelo);
    
  }
}
