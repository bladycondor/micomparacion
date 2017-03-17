package org.fautapo.web.administrarKardex.redireccionarKardex;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-30
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-30
*/

public class RedireccionarTramiteKardex implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi;}
  
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    String sSw="0"; int iContador=0;

    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    //Recuperamos los valores del jsp
    String sTodo = request.getParameter("todo");
    String sId_usuario = request.getParameter("id_usuario");
    String sBoton = request.getParameter("boton");
    String sBoton1 = request.getParameter("boton1");
    String sId_actividad = request.getParameter("id_actividad");
    String sValor = request.getParameter("valor");
    String sId_proceso = request.getParameter("id_proceso");
    //Listamos todos los procesos kardes
    //try {
      List lProcesos = this.mi.getListarProcesosAccesoKardex();
      modelo.put("lProcesos", lProcesos);
    //}
    //catch(Exception e) {
      //return new ModelAndView("Error","mensaje","El usuario no tiene área definida");
    //}
    
    if ((!"".equals(sId_proceso)) && (sId_proceso != null)) {
      try {
        //Lista actividades
        Actividades proceso = new Actividades();
        proceso.setId_proceso(Integer.parseInt(request.getParameter("id_proceso")));
        List lActividades = this.mi.getListarActividades(proceso);
        modelo.put("lActividades", lActividades);
        modelo.put("id_proceso", sId_proceso);
      }
      catch(Exception e) {
        return new ModelAndView("Error","mensaje","Seleccione un tipo de proceso");
      }
    }
    
    if (("Buscar".equals(sBoton))&&(!"".equals(sId_actividad))&&(!"".equals(sId_proceso))) {
      try {
        sSw="1";
	//Listamos los tramites para la impresion
	Tramites datosTramite = new Tramites();
        datosTramite.setId_proceso(Integer.parseInt(sId_proceso));
        datosTramite.setId_actividad(Integer.parseInt(sId_actividad));
        List lTramites = this.mi.getListarTramites(datosTramite);
        for (int i = 0; i < lTramites.size(); i++) {
          Tramites auxiliar = (Tramites) lTramites.get(i);
          auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
          lTramites.set(i, auxiliar);
        }
        modelo.put("lTramites", lTramites);
	
	//Lista actividades excepto la actividad del tramite
	String sId_actividad_excep = request.getParameter("id_actividad_excep");
        Actividades actividad = new Actividades();
        try {
          actividad.setId_proceso(Integer.parseInt(sId_proceso));
	  actividad.setId_actividad(Integer.parseInt(sId_actividad));
        }
        catch(Exception e) {
          return new ModelAndView("Error","mensaje","Seleccione un tipo de proceso adentro");
        }
        List lActividadesExcep = this.mi.getListarActividades2(actividad);
        modelo.put("lActividadesExcep", lActividadesExcep);
	modelo.put("id_actividad_excep",sId_actividad_excep);
	modelo.put("boton",sBoton);  
	if ((sId_actividad_excep!= null) || ("".equals(request.getParameter("id_actividad_excep")))){
	  Actividades usuario = new Actividades();
	  usuario.setId_actividad(Integer.parseInt(sId_actividad_excep));
	  List lUsuarios = this.mi.getListarUsuariosRolActividad(usuario);
	  modelo.put("lUsuariosActividad", lUsuarios);
	}
	
	//Redireccionar los escogidos  
	if("false".equals(request.getParameter("todo"))) {
	  if (("Redireccionar".equals(sBoton1))&&(!"".equals(request.getParameter("id_actividad_excep")))&&(!"".equals(request.getParameter("id_usuario")))) {
	    //Sacar los id_tramite checkeados
	    String sTramites[] = request.getParameterValues("id_tramite");
	    if (sTramites != null) {
	      for(int i=0; i< sTramites.length; i++) {
	        Tramites tramite = new Tramites();
                tramite.setId_tramite(Integer.parseInt(sTramites[i]));
	        tramite.setId_actividad_actual(Integer.parseInt(sId_actividad_excep));
	        tramite.setPara(Integer.parseInt(request.getParameter("id_usuario")));
	        int iResultado = this.mi.setRedireccionarTramite(tramite); 
	        if (iResultado ==1) {
	          iContador++;
	        }
	      }
	      if (iContador==sTramites.length) {
	        sSw="0";
	      }    
	    }
	  }
        } //FIN
	 
        //Redireccionar todos
        if ("true".equals(request.getParameter("todo"))) {
	  if (("Redireccionar".equals(sBoton1))&&(!"".equals(request.getParameter("id_actividad_excep")))&&(!"".equals(request.getParameter("id_usuario")))) {
	   //Sacar los id_tramite checkeados
	    int iLongitudTramites = lTramites.size() ;
	    if (iLongitudTramites != 0) {
	      for(int i=0; i< iLongitudTramites; i++){
	        Tramites tramite =(Tramites) lTramites.get(i);
	        int iId_tramite_a = tramite.getId_tramite();
	      
	        Tramites tramites = new Tramites();
	        tramites.setId_tramite(iId_tramite_a);
	        tramites.setId_actividad_actual(Integer.parseInt(sId_actividad_excep));
	        tramites.setPara(Integer.parseInt(request.getParameter("id_usuario")));
	        int iResultado = this.mi.setRedireccionarTramite(tramites); 
	        if (iResultado ==1) { 
	          iContador++;
	        }
	      }
	      if (iContador==lTramites.size()) {
	        sSw="0";
	      }
	    }
 	  }
        }  //FIN
      }
      catch(Exception e) {
        sSw = "0";
      }
    }
      
    modelo.put("id_actividad",sId_actividad);  
    modelo.put("id_proceso",sId_proceso);  
    modelo.put("valor",sValor);      
    modelo.put("sw", sSw);
    modelo.put("todo",sTodo);
    modelo.put("id_usuario", sId_usuario);
    
    return new ModelAndView("administrarKardex/redireccionarKardex/RedireccionarTramiteKardex", modelo);
  }
}