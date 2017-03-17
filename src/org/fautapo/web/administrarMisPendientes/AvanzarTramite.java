package org.fautapo.web.administrarMisPendientes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
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
 * @fec_registro 2006-03-24
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-24
*/

public class AvanzarTramite implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    int iResultado; Tramites datosTramite = new Tramites();
    //Sacamos los datos de la session
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_proceso = request.getParameter("id_proceso");
    String sId_tramite = request.getParameter("id_tramite");
    String sId_actividad = request.getParameter("id_actividad");
    String sId_tipo_actuacion = request.getParameter("id_tipo_actuacion");
    String sId_form = request.getParameter("id_form");
    String sOrden = request.getParameter("actuacion");
    String sPara = request.getParameter("para");
    String sAccion = request.getParameter("accion");
    String sNro_pagina = request.getParameter("nro_pagina");

    modelo.put("nro_pagina", sNro_pagina);
    modelo.put("id_tramite", sId_tramite);
    modelo.put("id_actividad", sId_actividad );
    
    //Recuperando los datos de fechas y estados
    String sFecha_ini = request.getParameter("fechainicio");
    String sFecha_fin = request.getParameter("fechafin");
    String sFechadellunes = request.getParameter("fechadellunes");
    String sId_estado = request.getParameter("id_estado");

    modelo.put("fechainicio", sFecha_ini);
    modelo.put("fechafin", sFecha_fin);
    modelo.put("fechadellunes", sFechadellunes);
    modelo.put("id_estado", sId_estado);
    modelo.put("id_form", sId_form);
    modelo.put("id_proceso", sId_proceso);
    //Fin Recuperando los datos de fechas y estados

    //Verificamos si se reviso el formulario
    Tramites datosFrLog = new Tramites();
    datosFrLog.setId_tramite(Integer.parseInt(sId_tramite));
    datosFrLog.setId_proceso(Integer.parseInt(sId_proceso));
    datosFrLog.setId_form(Integer.parseInt(sId_form));
    datosFrLog.setId_actividad(Integer.parseInt(sId_actividad));
    datosFrLog.setUlt_usuario(cliente.getId_usuario());
    datosFrLog = this.mi.getBuscarFrLog(datosFrLog);
    if (datosFrLog == null) {
      return new ModelAndView("Aviso", "mensaje", "Revise el formulario por favor");
    }
    //Fin de verificar si se reviso el formulario

    datosTramite = new Tramites();
    datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
    datosTramite.setId_tipo_proveido(1); //Para formularios
    
    //Listamos las actuaciones de la actividad
    Actividades datosActividad = new Actividades();
    datosActividad.setId_actividad(Integer.parseInt(sId_actividad));
    datosActividad = this.mi.getBuscarActividad(datosActividad);
    List lActuaciones = new ArrayList();
    if (datosActividad.getId_tipo_actuacion() == 3) {
      String sValor = datosActividad.getActuacion();
      String sAuxiliar[] = sValor.split("###");
      modelo.put("tamanio_act", Integer.toString(sAuxiliar.length));
      for (int ac=0; ac < sAuxiliar.length; ac++) {
        String sActuacion[] = sAuxiliar[ac].split(":");
        Tramites datosActuacion = new Tramites();
        datosActuacion.setOrden(Integer.parseInt(sActuacion[0])); 
        datosActuacion.setActuacion(sActuacion[1]);
        lActuaciones.add(datosActuacion);
      }
    }
    modelo.put("lActuaciones", lActuaciones);
    modelo.put("datosActividad", datosActividad);
    //Fin listado de actuaciones
    
    if ("si".equals(request.getParameter("recargado"))) {
      if (sOrden != null) {
        datosTramite.setId_tipo_actuacion(Integer.parseInt(sId_tipo_actuacion));
        datosTramite.setOrden(Integer.parseInt(sOrden));

        //Listamos los usuarios de la siguiente actividad
        Tramites auxiliar = new Tramites();
        auxiliar.setId_tramite(Integer.parseInt(sId_tramite));
        auxiliar.setId_tipo_actuacion(Integer.parseInt(sId_tipo_actuacion));
        auxiliar.setOrden(Integer.parseInt(sOrden));
        List lUsuariosActSig = this.mi.getListarUsuariosActividadSiguiente(auxiliar);
        modelo.put("lUsuariosActSig", lUsuariosActSig);
        modelo.put("nro_usuarios", Integer.toString(lUsuariosActSig.size()));
        //Fin Listamos los usuarios de la siguiente actividad
      }
      else {
        datosTramite.setId_tipo_actuacion(1);
        datosTramite.setOrden(0);
      }
      //Para avanzar el tramite
      if ("avanzar".equals(sAccion)) {
        //Si la actividad es fin de flujo entonces concluimos el tramite 	
	if (datosActividad.getFin_flujo()) {
          datosTramite = new Tramites();
	  datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
	  iResultado = this.mi.setConcluirTramite(datosTramite);
	  if (iResultado == 0) {
	    return new ModelAndView("Error", "mensaje", "El trámite no se pudo concluir");
	  }
	}

        if (sPara == null) {
	  return new ModelAndView("Error", "mensaje", "El proceso no se pudo avanzar");
	}
	else {
          datosTramite.setPara(Integer.parseInt(sPara));
	  datosTramite.setId_tipo_proveido(1); //Formulario
          iResultado = this.mi.setAvanzarTramite(datosTramite);
	  if (iResultado == 0) {
	    return new ModelAndView("Error", "mensaje", "No se encontró una siguiente actividad");
	  }
	  else {
	    //AQUI ENVIAMOS ALERTAS
	    //Sacamos los datos del tramite
	    datosTramite = new Tramites();
	    datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
            datosTramite = this.mi.getBuscarTramite(datosTramite); //Sacamos datos del tramite actual
            //Sacamos los datos de la actividad actual
	    datosActividad = new Actividades();
	    datosActividad.setId_actividad(datosTramite.getId_actividad_actual());
            datosActividad = this.mi.getBuscarActividad(datosActividad);
	    if (datosActividad.getAlerta() == true) {
	      Personas datosPersona = new Personas();
	      datosPersona.setUlt_usuario(datosTramite.getPara());
              datosPersona = this.mi.getBuscarPersonaUsuario(datosPersona);
              // LISTA TIPOS ALERTAS
              List lAlertas = this.mi.getListarTiposAlertasAct(datosActividad);
	      for (int i=0; i < lAlertas.size(); i++) {
	        Actividades alertas = (Actividades) lAlertas.get(i);
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
	    } //FIN DEL ENVIO DE ALERTAS
          }
	}

      	if ((sId_estado ==null) || ("".equals(sId_estado))) {   //CONDICION PARA ENTRAR AGRUPADOS
          datosTramite.setPara(cliente.getId_usuario());
          List lTramites = this.mi.getListarTramitesMios(datosTramite);
 
          for (int i = 0; i < lTramites.size(); i++) {
            Tramites auxiliar = (Tramites) lTramites.get(i);
            auxiliar.setUsuarios(this.mi.getListarUsuariosActividadSiguiente(auxiliar));
            auxiliar.setFilas(auxiliar.getUsuarios().size());
            auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
            lTramites.set(i, auxiliar);
          }
          modelo.put("lMisPendientes", lTramites);
          return new ModelAndView("administrarMisPendientes/ListarMisPendientes", modelo);
        }
	else {
	  //Entrando a mis agrupados
	  datosTramite.setPara(cliente.getId_usuario());
          datosTramite.setId_estado(sId_estado);
          if (((!"".equals(sFecha_ini)) && (!"".equals(sFecha_fin))) && ((sFecha_ini != null) &&(sFecha_fin != null))) {
            String sFechainicio[] = sFecha_ini.split("/");
            int iAnio1 = Integer.parseInt(sFechainicio[2]) - 1900;
            int iMes1 = Integer.parseInt(sFechainicio[1]) -1;
            int iDia1 = Integer.parseInt(sFechainicio[0]);
            String sFechafin[] = sFecha_fin.split("/");
            int iAnio2 = Integer.parseInt(sFechafin[2]) -1900;
            int iMes2 = Integer.parseInt(sFechafin[1]) -1;
            int iDia2 = Integer.parseInt(sFechafin[0]);
    		    
            Date dFechainicio1 = new Date(iAnio1, iMes1, iDia1);
            Date dFechafin1 = new Date(iAnio2, iMes2, iDia2);
            Date dFechainicio2 = new Date(sFecha_ini);
            Date dFechafin2 = new Date(sFecha_ini);
      
            datosTramite.setFechaini(dFechainicio1);
            datosTramite.setFechafin(dFechafin1);
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
            String sFecha_lunes[] = sFechadellunes.split("/");
            int iAnio1 = Integer.parseInt(sFecha_lunes[2]) - 1900;
            int iMes1 = Integer.parseInt(sFecha_lunes[1]) -1;
            int iDia1 = Integer.parseInt(sFecha_lunes[0]);      
	    Date dFecha_lunes_fin = new Date(iAnio1, iMes1, iDia1);
	
            //Date fechaentrada = new Date(fechadellunes);
            datosTramite.setFechafin(dFecha_lunes_fin);
	
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
          return new ModelAndView("administraMisPendientes/MostrarTramitesMiosAgrupadosEstadoSalida", modelo);
        }//Fin entrando a mis agrupados
      }
      //Fin de avanzar el tramite

      //Listamos los usuarios de la siguiente actividad
      Tramites auxiliar = new Tramites();
      auxiliar.setId_tramite(Integer.parseInt(sId_tramite));
      auxiliar.setId_tipo_actuacion(Integer.parseInt(sId_tipo_actuacion));
      auxiliar.setOrden(Integer.parseInt(sOrden));
      List lUsuariosActSig = this.mi.getListarUsuariosActividadSiguiente(auxiliar);
      modelo.put("lUsuariosActSig", lUsuariosActSig);
      modelo.put("nro_usuarios", Integer.toString(lUsuariosActSig.size()));
      modelo.put("orden", sOrden);
      //Fin Listamos los usuarios de la siguiente actividad
    }
    return new ModelAndView("administrarMisPendientes/AvanzarTramite", modelo);
  }
}