package org.fautapo.web.administrarProgramasEspecializados.administrarPstPersona;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Postulantes;
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


public class RegistrarPstPersona implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    //Para wayka
    int iResultado; int iId_tramite; String sCadena = ""; Tramites datosTramite = new Tramites();
    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");
    String sTitulo = request.getParameter("titulo");
    modelo.put("titulo", sTitulo);
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_tramite", sId_tramite);
    
    
    String sPaterno = (request.getParameter("paterno")).trim();
    String sMaterno = (request.getParameter("materno")).trim();
    String sNombres = (request.getParameter("nombres")).trim();
    String sNombres_completo = (request.getParameter("nombres")).trim()+" "+(request.getParameter("paterno")).trim()+" "+(request.getParameter("materno")).trim();
    String sFec_nacimiento = request.getParameter("fec_nacimiento");
    String sDireccion = (request.getParameter("direccion")).trim();
    String sTelefono = (request.getParameter("telefono")).trim();
    String sDip = (request.getParameter("dip")).trim();
    String sGestion = request.getParameter("gestion");
    String sPeriodo = request.getParameter("periodo");
    String sDescuento = request.getParameter("descuento");
    String sId_tipo_descuento = request.getParameter("id_tipo_descuento");
    String sId_perfil_proceso_p[] = request.getParameterValues("id_perfil_proceso_p");
    String sId_perfil_proceso="";
    String sBandera;String sMensaje;
 

   //Nuevo request
//    String sId_tipo_sexo = cliente.getString(request, "id_tipo_sexo");
//    String sId_tipo_estado_civil = cliente.getString(request, "id_tipo_estado_civil");
//    String sId_pais = cliente.getString(request, "id_pais_n");
//    String sId_departamento = cliente.getString(request, "id_departamento_n");
//    String sId_provincia = cliente.getString(request, "id_provincia_n");
//    String sId_localidad = cliente.getString(request, "id_localidad_n");
//    String sId_tipo_institucion = cliente.getString(request, "id_tipo_institucion");
//    String sId_colegio = cliente.getString(request, "id_colegio");
//    String sId_tipo_turno = cliente.getString(request, "id_tipo_turno");
//    int iId_tipo_graduacion = cliente.getInt(request, "id_tipo_graduacion");
//    String sId_tipo_empresa_telefonica = cliente.getString(request, "id_tipo_empresa_telefonica");
//    String sAnio_egreso = cliente.getString(request, "anio_egreso");
//    String sCelular = cliente.getString(request, "celular");
//    String sCorreo = cliente.getString(request, "correo");

	String sId_tipo_sexo = "1";
    String sId_tipo_estado_civil = "1";
    String sId_pais = "1";
    String sId_departamento = "1";
    String sId_provincia = "1";
    String sId_localidad = "1";
    String sId_tipo_institucion = "1";
    String sId_colegio = "1";
    String sId_tipo_turno = "1";
    int iId_tipo_graduacion = 1;
    String sId_tipo_empresa_telefonica = "1";
    String sAnio_egreso = "1";
    String sCelular = "1";
    String sCorreo = "1";

	
    //Registramos a Pst Personas
//    Postulantes datosP =new Postulantes();
//    datosP.setNombres(sNombres);
//    datosP.setPaterno(sPaterno);
//    datosP.setMaterno(sMaterno);
//    datosP.setDip(sDip);
//    datosP.setDireccion(sDireccion);
//    datosP.setTelefono(sTelefono);
//    datosP.setFec_nacimiento(sFec_nacimiento);
   Postulantes datosP =new Postulantes();
    //datosP.setId_plan(buscarPrgPlan.getPlan());  //Plan sacado de prg_planes
 //   datosP.setId_programa(Integer.parseInt(sId_programa));
    //datosP.setId_tipo_admision(Integer.parseInt(sId_tipo_admision));
    //datosP.setId_tipo_admision(3); //Vestibular Pre-Universitario
    datosP.setId_tipo_grado(2);  // Vestibular
    datosP.setNombres(sNombres);
    datosP.setPaterno(sPaterno);
    datosP.setMaterno(sMaterno);
    datosP.setDip(sDip);
    datosP.setDireccion(sDireccion);
    datosP.setTelefono(sTelefono);
    datosP.setCorreo(sCorreo);
    datosP.setCelular(sCelular);
    datosP.setFec_nacimiento(sFec_nacimiento);
    datosP.setGestion(Integer.parseInt(sGestion));    
    datosP.setPeriodo(Integer.parseInt(sPeriodo));
    datosP.setUlt_usuario(cliente.getId_usuario());
    datosP.setId_rol(cliente.getId_rol());
    
    datosP.setId_pais(Integer.parseInt(sId_pais));
    datosP.setId_departamento(Integer.parseInt(sId_departamento));
    datosP.setId_provincia(Integer.parseInt(sId_provincia));
    datosP.setId_localidad(Integer.parseInt(sId_localidad));
    datosP.setId_tipo_estado_civil(Integer.parseInt(sId_tipo_estado_civil));
    datosP.setId_tipo_sexo(Integer.parseInt(sId_tipo_sexo));
    datosP.setId_tipo_empresa_telefonica(Integer.parseInt(sId_tipo_empresa_telefonica));
 	
	
    datosP.setUlt_usuario(cliente.getId_usuario());
    int iId_persona = this.mi.setMiRegistrarPstPersona(datosP);
    
    if(iId_persona != 0) {
      if ((sId_tramite == null) || ("".equals(sId_tramite))) {
        //Crear un tramite
        Tramites tramite = new Tramites();
        tramite.setId_proceso(Integer.parseInt(sId_proceso));
        tramite.setPara(cliente.getId_usuario());
        iId_tramite = this.mi.setInsertarTramiteLimbo(tramite); //CREA UN TRAMITE
        if (iId_tramite == 0) {
          return new ModelAndView("Error","mensaje","El trámite no se creó");
        }
      }
      else {
        iId_tramite = Integer.parseInt(sId_tramite);
      }

      //Sacamos los datos del Postulante
      Postulantes datosPstPersona = new Postulantes();
      datosPstPersona.setId_persona(iId_persona);
      datosPstPersona = this.mi.getPstBuscarPersona(datosPstPersona);
      modelo.put("datosPstPersona", datosPstPersona);
      
      String sNombre_completo = datosPstPersona.getNombres()+" "+datosPstPersona.getPaterno()+" "+datosPstPersona.getMaterno();
      //Registramos los valores en wayka
      try {
        //Datos del Nombre
        datosTramite = new Tramites();
        datosTramite.setId_tramite(iId_tramite);
        datosTramite.setEtiqueta("nombre_completo");
        datosTramite.setValor(sNombre_completo);
        datosTramite.setUlt_usuario(cliente.getId_usuario());
        iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      } catch(Exception e) {}
  
      try {
        //Datos de los Nombres
        datosTramite = new Tramites();
        datosTramite.setId_tramite(iId_tramite);
        datosTramite.setEtiqueta("nombres");
        datosTramite.setValor(datosPstPersona.getNombres());
        datosTramite.setUlt_usuario(cliente.getId_usuario());
        iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      } catch(Exception e) {}
    
      try {
        //Datos del Paterno
        datosTramite = new Tramites();
        datosTramite.setId_tramite(iId_tramite);
        datosTramite.setEtiqueta("paterno");
        datosTramite.setValor(datosPstPersona.getPaterno());
        datosTramite.setUlt_usuario(cliente.getId_usuario());
        iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      } catch(Exception e) {}
  
      try {
        //Datos del Materno
        datosTramite = new Tramites();
        datosTramite.setId_tramite(iId_tramite);
        datosTramite.setEtiqueta("materno");
        datosTramite.setValor(datosPstPersona.getMaterno());
        datosTramite.setUlt_usuario(cliente.getId_usuario());
        iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      } catch(Exception e) {}
  
      try {
        //Datos del Materno
        datosTramite = new Tramites();
        datosTramite.setId_tramite(iId_tramite);
        datosTramite.setEtiqueta("dip");
        datosTramite.setValor(datosPstPersona.getDip());
        datosTramite.setUlt_usuario(cliente.getId_usuario());
        iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      } catch(Exception e) {}
    
   
      try {
        //Datos del Identificador Id_etudiante
        datosTramite = new Tramites();
        datosTramite.setId_tramite(iId_tramite);
          datosTramite.setEtiqueta("id_persona");
        datosTramite.setValor(Integer.toString(iId_persona));
        datosTramite.setUlt_usuario(cliente.getId_usuario());
        iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      } catch(Exception e) {}
          
  
      try {
       //Registramos los requisitos
        String sRequisitos[] = request.getParameterValues("id_tupla");
        for (int k=0; k < sRequisitos.length; k++) {
          if (sRequisitos[k] != null ) {
            sCadena = sCadena+"id_codigo:"+sRequisitos[k]+"###";
          }
        }
        datosTramite = new Tramites();
        datosTramite.setId_tramite(iId_tramite);
        datosTramite.setEtiqueta("requisitos");
        datosTramite.setValor(sCadena);
        datosTramite.setUlt_usuario(cliente.getId_usuario());
        iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      } catch(Exception e) {}
  
      try {
        //Registramos el descuento
        datosTramite = new Tramites();
        datosTramite.setId_tramite(iId_tramite);
        datosTramite.setEtiqueta("descuento");
        datosTramite.setValor(sDescuento);
        datosTramite.setUlt_usuario(cliente.getId_usuario());
        iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      } catch(Exception e) {}
      
      try {
        //Registramos el id_tipo_descuento
        datosTramite = new Tramites();
        datosTramite.setId_tramite(iId_tramite);
        datosTramite.setEtiqueta("id_tipo_descuento");
        datosTramite.setValor(sId_tipo_descuento);
        datosTramite.setUlt_usuario(cliente.getId_usuario());
        iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      } catch(Exception e) {}
  
      //Registramos en tr_pr_fr_log
      iResultado = this.mi.setRegistrarTrPrFrLogLimbo(datosTramite);
  
      //Sacamos los datos del tramite
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite = this.mi.getBuscarTramite(datosTramite);
      modelo.put("datosTramite", datosTramite);
          
      //Sacamos la lista de informes de esta actividad
      List lInformes = this.mi.getListarInformesActividad(datosTramite);
      modelo.put("lInformes", lInformes);
  
      int iCantInformes = lInformes.size();
      modelo.put("cantInformes", Integer.toString(iCantInformes));
  
      String sNombreInforme = Integer.toString(iId_tramite)+"_"+cliente.getId_usuario(); 
      modelo.put("nombre_informe", sNombreInforme);  
      //String sMensaje="Se realizo el registro";
      //modelo.put("mensaje",sMensaje);
      return new ModelAndView("administrarProgramasEspecializados/administrarPstPersona/ListarInformesProceso", modelo);  
    }
    else {
      return new ModelAndView("Error","mensaje","Los datos no se registraron");
    } 
     
    //sMensaje="No se realizo el registro";
    //modelo.put("mensaje",sMensaje);    
    //return new ModelAndView("administrarProgramasEspecializados/administrarPstPersona/SalidaPstPersona", modelo);
  }
}
