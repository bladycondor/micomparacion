package org.fautapo.web.administrarMisPendientesAgrupados;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Informes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Tramites;
import org.fautapo.domain.EnviarCorreo;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-28
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-28
*/

public class ListarMisPendientesAgrupados2 implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Tramites datosTramite = new Tramites(); Tramites datosFrLog;
    int iResultado;
    
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    
    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");    
    String sId_form = request.getParameter("id_form");
    String sId_actividad = request.getParameter("id_actividad"); 
    String sAccion = request.getParameter("accion");
    String sId_tipo_proceso = request.getParameter("id_tipo_proceso");    
    String sId_tipo_actuacion = request.getParameter("id_tipo_actuacion");
    String sActuacion = request.getParameter("actuacion");
    String sPara = request.getParameter("para");
    String sAplicacion = request.getParameter("aplicacion");
    modelo.put("id_tipo_proceso", sId_tipo_proceso);
    modelo.put("aplicacion", sAplicacion);

    //RECUPERANDO EL ESTADO Y FECHAS 
    String sFecha_ini = request.getParameter("fechainicio");
    String sFecha_fin = request.getParameter("fechafin");
    String sFechadellunes = request.getParameter("fechadellunes");
    String sId_estado = request.getParameter("id_estado");

    //Lista Tipos procesos
    List lTiposProcesos = this.mi.getListarTiposProcesos();
    modelo.put("lTiposProcesos", lTiposProcesos);     

    //Para registrar como recibido
    if ("Recibir".equals(sAccion)) {
      datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
      datosTramite.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setRecibirTramite(datosTramite);
      if (iResultado == 0) {
        return new ModelAndView("Error", "mensaje", "No se pudo recibir el proceso");
      }
    }
    //Para avanzar el tramite
    if ("Avanzar".equals(sAccion)) {
      datosFrLog = new Tramites();
      datosFrLog.setId_tramite(Integer.parseInt(sId_tramite));
      datosFrLog.setId_proceso(Integer.parseInt(sId_proceso));
      datosFrLog.setId_form(Integer.parseInt(sId_form));
      datosFrLog.setId_actividad(Integer.parseInt(sId_actividad));
      datosFrLog.setUlt_usuario(cliente.getId_usuario());
      datosFrLog = this.mi.getBuscarFrLog(datosFrLog);
      if (datosFrLog != null) {
        datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
        datosTramite.setId_tipo_proveido(1); //Formulario
        if ((request.getParameter("actuacion") != null) && (!"".equals(request.getParameter("actuacion")))) {
          datosTramite.setId_tipo_actuacion(Integer.parseInt(sId_tipo_actuacion));
          datosTramite.setOrden(Integer.parseInt(request.getParameter("actuacion")));
        }
        else {
          datosTramite.setId_tipo_actuacion(1);
          datosTramite.setOrden(0);
        }
        if (sPara != null) {
          datosTramite.setPara(Integer.parseInt(sPara));
          iResultado = this.mi.setAvanzarTramite(datosTramite);
          if (iResultado != 0) {
  	    //AQUI ENVIAMOS ALERTAS
	    //Sacamos los datos del tramite
	    datosTramite = new Tramites();
	    datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
            datosTramite = this.mi.getBuscarTramite(datosTramite); //Sacamos datos del tramite actual
            //Sacamos los datos de la actividad actual
	    Actividades datosActividad = new Actividades();
	    datosActividad.setId_actividad(datosTramite.getId_actividad_actual());
            datosActividad = this.mi.getBuscarActividad(datosActividad);
	    if (datosActividad.getAlerta() == true) {
	      Personas datosPersona = new Personas();
	      datosPersona.setUlt_usuario(datosTramite.getPara());
              datosPersona = this.mi.getBuscarPersonaUsuario(datosPersona);
              // LISTA TIPOS ALERTAS
              List lAlertas = this.mi.getListarTiposAlertasAct(datosActividad);
	      for (int i=0; i < lAlertas.size(); i++) {
	        Tramites alertas = (Tramites) lAlertas.get(i);
                //Enviamos alerta al correo
    	        if ((alertas.getId_tipo_alerta() == 1) && (alertas.getId_actividad() != 0) && (!"".equals(datosPersona.getCorreo()))) {
	          try {
        	    EnviarCorreo correo = new EnviarCorreo();
	            correo.setTo(datosPersona.getCorreo());
	            correo.setSubject("WAYKA - "+ datosTramite.getId_tramite());
	            correo.setBody(datosTramite.getProceso()+" - "+datosTramite.getActividad());
	            correo.setEnviarCorreo();
		  } catch (Exception e) {
		    System.out.println("no se pudo enviar el correo al usuario"+datosPersona.getUlt_usuario());
		  }
	        }
	        //Enviamos alerta al celular
    	        if ((alertas.getId_tipo_alerta() == 2) && (alertas.getId_actividad() != 0) && (!"0".equals(datosPersona.getCelular()))) {
	          try {
        	    EnviarCorreo correo = new EnviarCorreo();
                    if ((datosPersona.getId_tipo_empresa_telefonica()) == 2) {
	              correo.setTo("591"+datosPersona.getCelular()+"@"+datosPersona.getDireccion());
		    }
		    else {
		      correo.setTo(datosPersona.getCelular()+"@"+datosPersona.getDireccion());
		    }
	            correo.setSubject("WAYKA - "+ datosTramite.getId_tramite());
	            correo.setBody(datosTramite.getProceso()+" - "+datosTramite.getActividad());
	            correo.setEnviarCorreo();
		  } catch (Exception e) {
		    System.out.println("no se pudo enviar el SMS al celular del usuario"+datosPersona.getUlt_usuario());
	  	  }
	        }
	      }
	    }
	    //FIN DEL ENVIO DE ALERTAS
	  }
        }
        else {
          datosTramite = new Tramites();
	  datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
	  this.mi.setConcluirTramite(datosTramite);
        }
      }
      else{
        return new ModelAndView("Aviso", "mensaje", "Revise el formulario por favor");
      }
      //Modificar de estado el tramite en la tabla tr_fr_log
      datosFrLog = new Tramites();
      datosFrLog.setId_tramite(Integer.parseInt(sId_tramite));
      datosFrLog.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setEliminarFrLog(datosFrLog);
    }

    //Para retroceder el tramite
    if ("Retroceder".equals(sAccion)) {
      datosFrLog = new Tramites();
      datosFrLog.setId_tramite(Integer.parseInt(sId_tramite));
      datosFrLog.setId_proceso(Integer.parseInt(sId_proceso));
      datosFrLog.setId_form(Integer.parseInt(sId_form));
      datosFrLog.setId_actividad(Integer.parseInt(sId_actividad));    
      datosFrLog.setUlt_usuario(cliente.getId_usuario());
      datosFrLog = this.mi.getBuscarFrLog(datosFrLog);
      if (datosFrLog != null) {
        modelo.put("id_tramite", sId_tramite); 
        modelo.put("id_actividad", sId_actividad);
        modelo.put("para", sPara);
        return new ModelAndView("administrarMisPendientes/RetrocederTramite", modelo);
      }
      else {
        return new ModelAndView("Aviso", "mensaje", "Revise el formulario por favor");
      }
    }
    //Dando formato a las fechas
    if (!"".equals(sId_estado)) {
      datosTramite.setPara(cliente.getId_usuario());
      datosTramite.setId_estado(sId_estado);
      if (((!"".equals(sFecha_ini)) && (!"".equals(sFecha_fin))) && ((sFecha_ini != null) &&(sFecha_fin != null)))  {
        datosTramite.setFecha_ini(sFecha_ini);
        datosTramite.setFecha_fin(sFecha_fin);
        List lTramites = this.mi.getListarTramitesMiosAgrupados(datosTramite);
	modelo.put("fechainicio", sFecha_ini);
        modelo.put("fechafin", sFecha_fin);
	
	for (int i = 0; i < lTramites.size(); i++) {
          Tramites auxiliar = (Tramites) lTramites.get(i);
          if (auxiliar.getId_tipo_actuacion() == 2) {
            auxiliar.setOrden(Integer.parseInt(auxiliar.getActuacion()));
          }
          else {
            auxiliar.setOrden(0);
          }
          auxiliar.setUsuarios(this.mi.getListarUsuariosActividadSiguiente(auxiliar));
          auxiliar.setFilas(auxiliar.getUsuarios().size());
          auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
          lTramites.set(i, auxiliar);
        }
	modelo.put("lMisPendientes", lTramites);
      }
      else {
        datosTramite.setFecha_fin(sFechadellunes);
	List lTramites = this.mi.getListarTramitesMiosAgrupados2(datosTramite);
	modelo.put("fechadellunes", sFechadellunes);
	for (int i = 0; i < lTramites.size(); i++) {
          Tramites auxiliar = (Tramites) lTramites.get(i);
          if (auxiliar.getId_tipo_actuacion() == 2) {
            auxiliar.setOrden(Integer.parseInt(auxiliar.getActuacion()));
          }
          else {
            auxiliar.setOrden(0);
          }
          auxiliar.setUsuarios(this.mi.getListarUsuariosActividadSiguiente(auxiliar));
          auxiliar.setFilas(auxiliar.getUsuarios().size());
          auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
          lTramites.set(i, auxiliar);
        }
	modelo.put("lMisPendientes", lTramites);
      }
    }//Fin control de id_estado
    modelo.put("nombres", cliente.getNombres());
    modelo.put("id_estado", sId_estado);
    return new ModelAndView("administrarMisPendientesAgrupados/ListarMisPendientesAgrupados2", modelo);
  }
}