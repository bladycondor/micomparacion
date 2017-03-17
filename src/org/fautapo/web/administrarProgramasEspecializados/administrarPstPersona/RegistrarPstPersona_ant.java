package org.fautapo.web.administrarProgramasEspecializados.administrarPstPersona;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
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


public class RegistrarPstPersona_ant implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
    
    //Para wayka
    Tramites datosTramite = new Tramites();
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
    String sId_perfil_proceso_p[] = request.getParameterValues("id_perfil_proceso_p");
    String sId_perfil_proceso="";
    int iResultado;String sBandera;
    
    //Registramos a Pst Personas
    Postulantes datosP =new Postulantes();
    datosP.setNombres(sNombres);
    datosP.setPaterno(sPaterno);
    datosP.setMaterno(sMaterno);
    datosP.setDip(sDip);
    datosP.setDireccion(sDireccion);
    datosP.setTelefono(sTelefono);
    datosP.setFec_nacimiento(sFec_nacimiento);
    datosP.setUlt_usuario(cliente.getId_usuario());
    int iId_persona = this.mi.setMiRegistrarPstPersona(datosP);
    if(iId_persona != 0) {
      
      //Crear un tramite
      Tramites tramite = new Tramites();
      tramite.setId_proceso(Integer.parseInt(sId_proceso));
      tramite.setPara(cliente.getId_usuario());
      int iId_tramite = this.mi.setInsertarTramiteLimbo(tramite); //CREA UN TRAMITE
      if (iId_tramite == 0) {
        return new ModelAndView("Error","mensaje","El trámite no se creó");
      }    

      if (sId_perfil_proceso_p != null) {
        //RECUPERAMOS id_proceso_perfil 
        for (int i = 0; i< sId_perfil_proceso_p.length; i++) {
          if(i==0) {
    	    sId_perfil_proceso += sId_perfil_proceso_p[i];
  	  }
	  else{
	    sId_perfil_proceso += ":"+sId_perfil_proceso_p[i];
	  }  
        }
        //Registramos los valores de id_perfil en wayka
        datosTramite.setId_tramite(iId_tramite);
        datosTramite.setEtiqueta("id_perfil_proceso");
        datosTramite.setValor(sId_perfil_proceso);
        datosTramite.setUlt_usuario(cliente.getId_usuario());
        iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      }
      
      //Datos del Nombre
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("nombre_completo");
      datosTramite.setValor(sNombres_completo);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      
      //Datos del Nombre
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("nombres");
      datosTramite.setValor(sNombres);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      
      //Datos del Paterno
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("paterno");
      datosTramite.setValor(sPaterno);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);

      //Datos del Materno
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("materno");
      datosTramite.setValor(sMaterno);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      
      //Datos del dip
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("dip");
      datosTramite.setValor(sDip);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      
      //Datos del Identificador ID_postulante
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("id_persona");
      datosTramite.setValor(Integer.toString(iId_persona));
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);
      
      //Datos el descuento
      datosTramite = new Tramites();
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setEtiqueta("descuento");
      datosTramite.setValor(sDescuento);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarValorLimbo2(datosTramite);

      //Registramos para eÃ± formulario Av.
      datosTramite = new Tramites(); 
      datosTramite.setId_tramite(iId_tramite);
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRegistrarTrPrFrLogLimbo(datosTramite);      
      
      String sMensaje="Se realizo el registro";
      modelo.put("mensaje",sMensaje);
      
    }
    else {
      return new ModelAndView("Error","mensaje","Ocurrio un error. Los datos no se registraron");
    } 
     
    return new ModelAndView("administrarProgramasEspecializados/administrarPstPersona/SalidaPstPersona", modelo);
  }
}
