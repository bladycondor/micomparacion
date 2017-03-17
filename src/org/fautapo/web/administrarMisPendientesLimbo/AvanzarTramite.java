package org.fautapo.web.administrarMisPendientesLimbo;

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
    String sOrden = request.getParameter("actuacion");
    String sPara = request.getParameter("para");
    String sAccion = request.getParameter("accion");

    modelo.put("id_tramite", sId_tramite);
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_actividad", sId_actividad );
    
    datosTramite = new Tramites();
    datosTramite.setId_tramite(Integer.parseInt(sId_tramite));
    
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
        if (sPara == null) {
          Tramites auxiliar = new Tramites();
          auxiliar.setId_tramite(Integer.parseInt(sId_tramite));
          iResultado = this.mi.setConcluirTramite(auxiliar);
	  if (iResultado == 0) {
	    return new ModelAndView("Error", "mensaje", "El trámite no se pudo concluir");
	  }
	}
	else {
          datosTramite.setPara(Integer.parseInt(sPara));
	  datosTramite.setId_tipo_proveido(1); //Formulario
          iResultado = this.mi.setAvanzarTramiteLimbo(datosTramite);
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

        //Listamos mis pendientes Limbo
        datosTramite.setId_proceso(Integer.parseInt(sId_proceso));
        datosTramite.setPara(cliente.getId_usuario());
        List lTramites = this.mi.getListarTramitesMiosLimbo(datosTramite);
 
        for (int i = 0; i < lTramites.size(); i++) {
          Tramites auxiliar = (Tramites) lTramites.get(i);
          auxiliar.setUsuarios(this.mi.getListarUsuariosActividadSiguiente(auxiliar));
          auxiliar.setFilas(auxiliar.getUsuarios().size());
          auxiliar.setLista(this.mi.getListarCamposReferencia(auxiliar));
          lTramites.set(i, auxiliar);
        }
        modelo.put("lMisPendientesLimbo", lTramites);
        //Listamos los procesos según el acceso del usuario
        List lProcesos = this.mi.getListarProcesosAcceso(cliente);
        modelo.put("lProcesos", lProcesos);
        return new ModelAndView("administrarMisPendientesLimbo/ListarMisPendientes", modelo);
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
    return new ModelAndView("administrarMisPendientesLimbo/AvanzarTramite", modelo);
  }

}