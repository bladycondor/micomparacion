package org.fautapo.web.administrarActividades;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.lang.Boolean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Roles;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-16
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-17
*/

public class ConfirmarActividad implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Actividades auxiliar2 = new Actividades();
    Actividades auxiliar = new Actividades();
    Actividades datosActividad = new Actividades();
    Actividades datosActividad2 = new Actividades();
    Actividades tipoActuacion = new Actividades();
    Actividades tipoDuracion = new Actividades();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    
    //Guardar Nuevo    
    String sAccion  = request.getParameter("accion");
    String sAccion1 = request.getParameter("accion1");
    
    String sId_actividad = request.getParameter("id_actividad");
    String sActividad = request.getParameter("actividad");
    String sId_proceso = request.getParameter("id_proceso");
    String sId_ubicacion_organica = request.getParameter("id_ubicacion_organica");
    String sId_rol = request.getParameter("id_rol");
    String sDuracion = request.getParameter("duracion");
    String sOrden = request.getParameter("orden");
    String sId_tipo_actuacion  = request.getParameter("id_tipo_actuacion");
    String sAlerta = request.getParameter("alerta");
    String sPuente = request.getParameter("puente");
    String sRuta = request.getParameter("ruta");
    String sFin_flujo = request.getParameter("fin_flujo");
    String sId_tipo_duracion = request.getParameter("id_tipo_duracion");
    String sId_actividad_modificar = request.getParameter("id_actividad_modificar");

    //Recuperando los tipos de alertas seleccionados
    List lAlertas = new ArrayList();
    String sAlertas[] = request.getParameterValues("alertas");
    if (sAlertas != null) {
      String sCadena = "";
      for (int i=0; i < sAlertas.length; i++) {
        Actividades tiposAlertas = new Actividades();
        tiposAlertas.setId_tipo_alerta(Integer.parseInt(sAlertas[i]));
        tiposAlertas = this.mi.getBuscarTipoAlerta(tiposAlertas);
        lAlertas.add(tiposAlertas);
	sCadena = sAlertas[i]+":"+sCadena;
      }
      modelo.put("lTiposAlertas", lAlertas);
      modelo.put("cadena", sCadena);
    }
    // Fin Recuperando los tipos de alertas seleccionados
    
    //Buscamos los datos del Rol
    if ((!"".equals(sId_rol)) && (!"Eliminar".equals(sAccion))) {
      Roles rol = new Roles();
      rol.setId_rol(Integer.parseInt(sId_rol));
      rol = this.mi.getBuscarRol(rol);
      modelo.put("datosRol", rol);
    }
    //Fin - Buscamos los datos del Rol

    //Buscamos los datos de la ubicacion_organica
    if ((!"".equals(sId_ubicacion_organica)) && (!"Eliminar".equals(sAccion))) {
      Actividades ubicacionOrganica = new Actividades();
      ubicacionOrganica.setId_ubicacion_organica(Integer.parseInt(sId_ubicacion_organica));
      ubicacionOrganica = this.mi.getBuscarUbicacionOrganica(ubicacionOrganica);
      modelo.put("datosUbicacionOrganica", ubicacionOrganica);
    }
    //Fin - Buscamos los datos de la ubicacion_organica

    if ((!"".equals(sId_tipo_actuacion)) && (!"Eliminar".equals(sAccion))) {
      //Buscamos el tipo de actuacion
      tipoActuacion.setId_tipo_actuacion(Integer.parseInt(sId_tipo_actuacion));
      tipoActuacion = this.mi.getBuscarTipoActuacion(tipoActuacion);
      modelo.put("datosTipoActuacion", tipoActuacion);
      //Fin - Buscamos el tipo de actuacion
    }

    if ((!"".equals(sId_tipo_duracion)) && (!"Eliminar".equals(sAccion))) {
      //Buscamos el tipo de duracion
      tipoDuracion.setId_tipo_duracion(Integer.parseInt(sId_tipo_duracion));
      tipoDuracion = this.mi.getBuscarTipoDuracion(tipoDuracion);
      modelo.put("datosTipoDuracion", tipoDuracion);
      //Fin - Buscamos el tipo de actuacion
    }

    //Sacamos los datos de la actividad a modificar
    if ((sId_actividad_modificar != null) && (!"".equals(request.getParameter("id_actividad_modificar"))) && (!"Eliminar".equals(sAccion))) {
      datosActividad.setId_actividad(Integer.parseInt(sId_actividad_modificar));
      datosActividad = this.mi.getBuscarActividad(datosActividad);
      modelo.put("datosActividad", datosActividad);
    }
    //Fin - Sacamos los datos de la actividad a modificar

    Actividades actividad = new Actividades();
    if ("Adicionar".equals(sAccion)) {
      if (("".equals(request.getParameter("actividad"))) || ("".equals(request.getParameter("id_ubicacion_organica")))|| 
        ("".equals(request.getParameter("id_rol"))) || ("".equals(request.getParameter("duracion")))||
  	("".equals(request.getParameter("id_tipo_actuacion"))) || ("".equals(request.getParameter("orden"))) ||
  	("".equals(request.getParameter("id_tipo_duracion"))) || ("".equals(request.getParameter("fin_flujo"))) 
	|| ("".equals(request.getParameter("alerta"))) || ("".equals(request.getParameter("puente")))) {
	return new ModelAndView("Error", "mensaje", "Faltan datos");
      }
      actividad.setActividad(sActividad);
      actividad.setDuracion(Integer.parseInt(sDuracion));  
      actividad.setOrden(Integer.parseInt(sOrden));
      //Alertas
      if ("true".equals(sAlerta)) {
        actividad.setAlerta(true);
      }
      else {
        actividad.setAlerta(false);
      }

      //Puente
      if ("true".equals(sPuente)) {
        actividad.setPuente(true);
	actividad.setRuta(sRuta);
      }
      else {
        actividad.setPuente(false);
      }
      
      //Fin_flujo
      if ("true".equals(sFin_flujo)) {
        actividad.setFin_flujo(true);
      }
      else {
        actividad.setFin_flujo(false);
      }

      if (!"".equals(sId_tipo_actuacion)) {
        if ((!"2".equals(request.getParameter("id_tipo_actuacion"))) && (!"3".equals(request.getParameter("id_tipo_actuacion")))) {
	  modelo.put("actuacion_a_meter", "");
        }
        if ("2".equals(request.getParameter("id_tipo_actuacion"))) {
	  if ("".equals(request.getParameter("id_actividad"))) {
	    return new ModelAndView("Error", "mensaje", "Faltan introducir datos");
	  } 
	  else {
	    //Sacamos los datos de la actividad goto
            datosActividad.setId_actividad(Integer.parseInt(sId_actividad));
            datosActividad = this.mi.getBuscarActividad(datosActividad);
            modelo.put("datosActividadGoto", datosActividad);
	    modelo.put("id_actividad", sId_actividad);
          }
         //Fin - Sacamos los datos de la actividad a modificar
          modelo.put("actuacion_a_meter", request.getParameter("orden_actividad"));
        }
      
        //Concatenamos la cadena
        List lActuacionesOrden = new ArrayList();
        if ("3".equals(request.getParameter("id_tipo_actuacion"))) {
	  try {
            String sId_actividad_s[] = request.getParameterValues("id_actividad");
	    String sOrden_actividad_s[] = request.getParameterValues("orden_actividad");
	    String sCadena="";
            for (int i = 0; i< sId_actividad_s.length; i++) {
              int iId_actividad_a = Integer.parseInt(sId_actividad_s[i]);
     	      //Buscamos el orden de la actividad
              auxiliar.setId_actividad(iId_actividad_a);
              auxiliar = this.mi.getBuscarActividad(auxiliar);
	      String sOrden_actividad_a = Integer.toString(auxiliar.getOrden());
	      //Sacamos actuacion
	      String sActuacion_b= request.getParameter("actuacion_"+Integer.toString(iId_actividad_a));
  	      //Concatenamos la cadena
	      sCadena = sCadena + sOrden_actividad_a +":"+ sActuacion_b +"###";
            }
	    String sActuacion_a_meter = sCadena;
	    modelo.put("actuacion_a_meter", sActuacion_a_meter);
	    String sValorCadena = sCadena;
            String sAuxiliar[] = sValorCadena.split("###");
            modelo.put("tamanio_act", Integer.toString(sAuxiliar.length));
            for (int ac=0; ac < sAuxiliar.length; ac++) {
              String sActuacion_c[] = sAuxiliar[ac].split(":");
    	      for (int j=0; j < sActuacion_c.length; j++) {
                Actividades actividad2 = new Actividades();
	        actividad2.setOrden(Integer.parseInt(sActuacion_c[j]));
	        actividad2.setActuacion(sActuacion_c[j+1]);
                //Sacamos los datos de la actividad por su orden
  	        Actividades actividad3 = new Actividades();
                actividad3.setId_proceso(Integer.parseInt(sId_proceso));
                actividad3.setOrden(Integer.parseInt(sActuacion_c[j]));
                actividad3 = this.mi.getBuscarActividadOrden(actividad3);
                //Fin Sacamos los datos de la actividad por su orden
	        actividad2.setId_actividad(actividad3.getId_actividad());
	        actividad2.setActividad(actividad3.getActividad());
	        lActuacionesOrden.add(actividad2);
	        j++;
              }
            }
            modelo.put("listaActuacionesOrden",lActuacionesOrden);
	  }
	  catch (Exception e) {
	    return new ModelAndView("Error", "mensaje", "Faltan introducir datos");
	  }
        }
      }
      modelo.put("datosActividad", actividad);
      modelo.put("accion", sAccion);
      modelo.put("accion1", sAccion1);
      modelo.put("alerta", sAlerta);
      modelo.put("puente", sPuente);
      modelo.put("ruta", sRuta);
    }
    
    if ("Modificar".equals(sAccion)) {
      if (("".equals(request.getParameter("actividad"))) || ("".equals(request.getParameter("id_ubicacion_organica"))) || 
        ("".equals(request.getParameter("id_rol"))) || ("".equals(request.getParameter("duracion"))) ||
  	("".equals(request.getParameter("id_tipo_actucion"))) || ("".equals(request.getParameter("orden"))) ||
  	("".equals(request.getParameter("id_tipo_duracion"))) || ("".equals(request.getParameter("fin_flujo"))) 
	|| ("".equals(request.getParameter("alerta"))) || ("".equals(request.getParameter("puente")))) {
	return new ModelAndView("Error", "mensaje", "Faltan introducir datos");     
      }
    
      actividad.setActividad(sActividad);
      actividad.setDuracion(Integer.parseInt(sDuracion));  
      actividad.setOrden(Integer.parseInt(sOrden));
      //alertas
      if ("true".equals(sAlerta)) {
        actividad.setAlerta(true);
      }
      else {
        actividad.setAlerta(false);
      }
      //puente
      if ("true".equals(sPuente)) {
        actividad.setPuente(true);
	actividad.setRuta(sRuta);
      }
      else {
        actividad.setPuente(false);
      }
      //Fin_flujo
      if ("true".equals(sFin_flujo)) {
        actividad.setFin_flujo(true);
      }
      else {
        actividad.setFin_flujo(false);
      }
      if (!"".equals(sId_tipo_actuacion)) {
        if ((!"2".equals(request.getParameter("id_tipo_actuacion"))) && (!"3".equals(request.getParameter("id_tipo_actuacion")))) {
	  modelo.put("actuacion_a_meter", "");
        }
	
	if ("2".equals(request.getParameter("id_tipo_actuacion"))) {
	  if ("".equals(request.getParameter("id_actividad"))) {
	    return new ModelAndView("Error", "mensaje", "Faltan introducir datos");
	  }
	  else {
	    //Sacamos los datos de la actividad goto
            datosActividad.setId_actividad(Integer.parseInt(sId_actividad));
            datosActividad = this.mi.getBuscarActividad(datosActividad);
            modelo.put("datosActividadGoto", datosActividad);
	    modelo.put("id_actividad", sId_actividad);
          }
         //Fin - Sacamos los datos de la actividad a modificar
	  modelo.put("actuacion_a_meter",request.getParameter("orden_actividad"));
        }
        //Concatenamosla CADENA
        List lActuacionesOrden = new ArrayList();
        if ("3".equals(request.getParameter("id_tipo_actuacion"))) {
	  String sId_actividad_s[] = request.getParameterValues("id_actividad");
	  String sOrden_actividad_s[] = request.getParameterValues("orden_actividad");
	  
	  String sCadena="";
          for (int i = 0; i< sId_actividad_s.length; i++) {
	    int iId_actividad_a = Integer.parseInt(sId_actividad_s[i]);
	    //Buscamos el orden del id_actividad
            auxiliar.setId_actividad(iId_actividad_a);
            datosActividad = this.mi.getBuscarActividad(auxiliar);
	    String sOrden_actividad_a = Integer.toString(datosActividad.getOrden());
	    //Sacamos actuacion
	    String sActuacion_b= request.getParameter("actuacion_"+Integer.toString(iId_actividad_a));
	    //Concatenamos la cadena
	    sCadena = sCadena + sOrden_actividad_a +":"+ sActuacion_b +"###";
          }
	  String sActuacion_a_meter = sCadena;
	  modelo.put("actuacion_a_meter", sActuacion_a_meter);
	  String sValorCadena = sCadena;
          String sAuxiliar[] = sValorCadena.split("###");
          modelo.put("tamanio_act", Integer.toString(sAuxiliar.length));
          for (int ac=0; ac < sAuxiliar.length; ac++) {
            String sActuacion_c[] = sAuxiliar[ac].split(":");
	    for (int j=0; j < sActuacion_c.length; j++) {
	      datosActividad.setOrden(Integer.parseInt(sActuacion_c[j]));
	      datosActividad.setActuacion(sActuacion_c[j+1]);
	      //Buscamos el  id_actividad y actividad por Orden
              auxiliar.setId_proceso(Integer.parseInt(sId_proceso));
              auxiliar.setOrden(Integer.parseInt(sActuacion_c[j]));
              auxiliar = this.mi.getBuscarActividadOrden(auxiliar);
	      datosActividad.setId_actividad(auxiliar.getId_actividad());
	      datosActividad.setActividad(auxiliar.getActividad());
	      lActuacionesOrden.add(datosActividad);
	      j++;
            }
          }
          modelo.put("listaActuacionesOrden", lActuacionesOrden);
        }
      }
      
      modelo.put("datosActividad", actividad);  
      modelo.put("id_actividad_modificar", sId_actividad_modificar);
      modelo.put("accion", sAccion);
      modelo.put("accion1", sAccion1);
      modelo.put("alerta", sAlerta);
      modelo.put("puente", sPuente);
      modelo.put("ruta", sRuta);
    }
    
    if ("Eliminar".equals(sAccion)) {
      //Sacamos los datos de la actividad a modificar
      if ((sId_actividad != null) && (!"".equals(request.getParameter("id_actividad")))) {
        datosActividad2.setId_actividad(Integer.parseInt(sId_actividad));
        datosActividad2 = this.mi.getBuscarActividad(datosActividad2);
        modelo.put("datosActividad", datosActividad2);
      }
      if (!"".equals(Integer.toString(datosActividad2.getId_tipo_actuacion()))) {
        tipoActuacion.setId_tipo_actuacion(datosActividad2.getId_tipo_actuacion());
        tipoActuacion = this.mi.getBuscarTipoActuacion(tipoActuacion);
        modelo.put("datosTipoActuacion", tipoActuacion);
	 
        if ((!"2".equals(Integer.toString(datosActividad2.getId_tipo_actuacion()))) && (!"3".equals(Integer.toString(datosActividad2.getId_tipo_actuacion())))) {
	  modelo.put("actuacion_a_meter", "");
	}
	if ("2".equals(Integer.toString(datosActividad2.getId_tipo_actuacion()))) {
	  modelo.put("actuacion_a_meter",request.getParameter("orden_actividad"));
          datosActividad.setId_proceso(Integer.parseInt(sId_proceso));
          datosActividad.setOrden(Integer.parseInt(datosActividad2.getActuacion()));
          datosActividad = this.mi.getBuscarActividadOrden(datosActividad);
          modelo.put("datosActividadGoto", datosActividad);
	}
      
        //Concatenamosla CADENA
        List lActuacionesOrden = new ArrayList();
        if ("3".equals(Integer.toString(datosActividad2.getId_tipo_actuacion()))) {
	  String sValorCadena = datosActividad2.getActuacion();
          String sAuxiliar[] = sValorCadena.split("###");
          modelo.put("tamanio_act", Integer.toString(sAuxiliar.length));
          for (int ac=0; ac < sAuxiliar.length; ac++) {
            String sActuacion_c[] = sAuxiliar[ac].split(":");
	    for (int j=0; j < sActuacion_c.length; j++) {
	      auxiliar.setOrden(Integer.parseInt(sActuacion_c[j]));
	      auxiliar.setActuacion(sActuacion_c[j+1]);
	      //Buscamos el  id_actividad y actividad por Orden
              auxiliar2.setId_proceso(Integer.parseInt(sId_proceso));
              auxiliar2.setOrden(Integer.parseInt(sActuacion_c[j]));
              auxiliar2 = this.mi.getBuscarActividadOrden(auxiliar2);
	      auxiliar.setId_actividad(auxiliar2.getId_actividad());
	      auxiliar.setActividad(auxiliar2.getActividad());
	      lActuacionesOrden.add(auxiliar);
	      j++;
            }
          }
          modelo.put("listaActuacionesOrden", lActuacionesOrden);
         }
       }
       //Buscamos los datos del Rol
       Roles rol = new Roles();
       rol.setId_rol(datosActividad2.getId_rol());
       rol = this.mi.getBuscarRol(rol);
       modelo.put("datosRol", rol);
       //Fin - Buscamos los datos del Rol

       //Buscamos los datos de la ubicacion_organica
       Actividades ubicacionOrganica = new Actividades();
       ubicacionOrganica.setId_ubicacion_organica(datosActividad2.getId_ubicacion_organica());
       ubicacionOrganica = this.mi.getBuscarUbicacionOrganica(ubicacionOrganica);
       modelo.put("datosUbicacionOrganica", ubicacionOrganica);
       //Fin - Buscamos los datos de la ubicacion_organica

       //Buscamos los datos de la duracion
       Actividades datosTipoDuracion = new Actividades();
       datosTipoDuracion.setId_tipo_duracion(datosActividad2.getId_tipo_duracion());
       datosTipoDuracion = this.mi.getBuscarTipoDuracion(datosTipoDuracion);
       modelo.put("datosTipoDuracion", datosTipoDuracion);
       //Fin - Buscamos los datos de la ubicacion_organica

       modelo.put("datosActividad", datosActividad2);
       modelo.put("accion", sAccion);
       modelo.put("id_proceso", sId_proceso);
     }
     modelo.put("id_proceso", sId_proceso);
     modelo.put("id_actividad", sId_actividad);

     return new ModelAndView("administrarActividades/ConfirmarActividad", modelo);
   }
}