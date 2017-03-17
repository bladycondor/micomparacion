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
 * @fec_registro 2006-03-17
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-17
*/

public class RegistrarActividad implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String sAccion            = request.getParameter("accion");
    String sId_actividad      = request.getParameter("id_actividad");
    String sActividad         = request.getParameter("actividad");
    String sId_proceso        = request.getParameter("id_proceso");
    String sId_ubicacion_organica = request.getParameter("id_ubicacion_organica");
    String sId_rol            = request.getParameter("id_rol");
    String sDuracion          = request.getParameter("duracion");
    String sOrden             = request.getParameter("orden");
    String sId_tipo_actuacion = request.getParameter("id_tipo_actuacion");
    String sId_actividad_modificar = request.getParameter("id_actividad_modificar");
    String sAlerta            = request.getParameter("alerta");
    String sPuente            = request.getParameter("puente");
    String sRuta              = request.getParameter("ruta");
    String sFin_flujo         = request.getParameter("fin_flujo");
    String sId_tipo_duracion  = request.getParameter("id_tipo_duracion");

    if (("Adicionar".equals(sAccion)) || ("Modificar".equals(sAccion))) {
      if (("".equals(sId_proceso)) || ("".equals(sActividad)) || ("".equals(sDuracion)) ||
        ("".equals(sId_rol))||("".equals(sId_ubicacion_organica))||("".equals(sId_tipo_duracion))) {
        return new ModelAndView("Error", "mensaje", "Faltan Introducir Datos");
      }
      Actividades actividad = new Actividades();
      if ((sId_actividad_modificar != null) && (!"".equals(sId_actividad_modificar))) {
        actividad.setId_actividad(Integer.parseInt(sId_actividad_modificar));
      }
      actividad.setId_proceso(Integer.parseInt(sId_proceso));
      actividad.setId_rol(Integer.parseInt(sId_rol));
      actividad.setId_tipo_actuacion(Integer.parseInt(sId_tipo_actuacion));
      actividad.setId_tipo_duracion(Integer.parseInt(sId_tipo_duracion));
      actividad.setId_ubicacion_organica(Integer.parseInt(sId_ubicacion_organica));
      actividad.setActividad(sActividad);

      try {
        actividad.setDuracion(Integer.parseInt(sDuracion));
        actividad.setOrden(Integer.parseInt(sOrden));
	//Alerta
	if ("true".equals(sAlerta) && (!"".equals(request.getParameter("cadena")))) {
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
      }
      catch(Exception e) {
        return new ModelAndView ("Error", "mensaje", "Faltan datos");
      }
      actividad.setActuacion(request.getParameter("actuacion_a_meter"));
      actividad.setUlt_usuario(cliente.getId_usuario());
      int iId_actividad_nueva = this.mi.setRegistrarActividad(actividad);  //CREA UNA ACTIVIDAD
      if (iId_actividad_nueva == 0) {
        String mensaje = "No se registro la actividad " +"'" +actividad.getActividad() + "',"+" por que el orden " + Integer.toString(actividad.getOrden()) + " ya existe.";
        return new ModelAndView("Error","mensaje", mensaje);
      }
      else {
        //Recuperando los tipos de alertas seleccionados
        Actividades auxiliar = new Actividades();
        auxiliar.setId_actividad(iId_actividad_nueva);
        int iRes = this.mi.setReiniciarTiposAlertas(auxiliar);
        List lAlertas = new ArrayList();
        String sCadena = request.getParameter("cadena");
	if (!"".equals(sCadena) && ("true".equals(sAlerta))) {
          String sAlertas[] = sCadena.split(":");
          if (sAlertas != null) {
            for (int i=0; i < sAlertas.length; i++) {
              Actividades tiposAlertas = new Actividades();
              tiposAlertas.setId_tipo_alerta(Integer.parseInt(sAlertas[i]));
              tiposAlertas.setId_actividad(iId_actividad_nueva);
              tiposAlertas.setUlt_usuario(cliente.getId_usuario());
              int iResultado = this.mi.setRegistrarTipoAlerta(tiposAlertas);
              if (iResultado == 0) {
  	        String mensaje = "No se registro el tipo de alerta";
	        return new ModelAndView("Error","mensaje", mensaje);
	      }
	    }
	  }
	}
      }
    }

    if ("Eliminar".equals(sAccion)) {
      if (request.getParameter("id_actividad_eliminar") != null){
        Actividades actividad = new Actividades();
 	actividad.setId_actividad(Integer.parseInt(request.getParameter("id_actividad_eliminar")));
 	actividad.setUlt_usuario(cliente.getId_usuario());
        int iResultado = this.mi.setEliminarActividad(actividad);
	if (iResultado == 0) {
	  String sActividad_e = request.getParameter("actividad");
          String sMensaje = "El proceso "+"'"+ sActividad_e +"'"+ " esta en uso. "+"Elimine o cambie primero en los registros correspondientes";
          return new ModelAndView("Aviso","mensaje", sMensaje);
	}
      }
    }
    
    //Lista actividades
    Actividades proceso = new Actividades();
    proceso.setId_proceso(Integer.parseInt(request.getParameter("id_proceso")));
    List lActividades2 = new ArrayList();
    List lActividades = this.mi.getListarActividades(proceso);
    for (int i=0; i < lActividades.size(); i++) {
      Actividades auxiliar = (Actividades) lActividades.get(i);
      String sValor = auxiliar.getActuacion();
      String sCadena = "";
      //Listando las actuaciones
      String sAuxiliar[] = sValor.split("###");
      for (int ac=0; ac < sAuxiliar.length; ac++) {
        String sActuacion[] = sAuxiliar[ac].split(":");
        if (sActuacion.length > 1) {
          sCadena = sActuacion[1]+ "[" +sActuacion[0]+ "], "+sCadena;
        }
        else {
	  sCadena = sAuxiliar[ac];
	}
      }
      auxiliar.setActuacion(sCadena);
      //Fin Listando las actuaciones
      
      //Listando los tipos de alertas
      List lTiposAlertas = this.mi.getListarTiposAlertasAct(auxiliar);
      sCadena = "";
      for (int ac=0; ac < lTiposAlertas.size(); ac++) {
        Actividades auxiliar2 = (Actividades) lTiposAlertas.get(ac);
        if (auxiliar2.getId_actividad() != 0) {
  	  if (ac == 0) {
  	    sCadena = auxiliar2.getTipo_alerta() + ", ";
	  }
	  else {
            sCadena = sCadena + auxiliar2.getTipo_alerta() + ", ";
	  }
	}
      }
      auxiliar.setTipo_alerta(sCadena);
      lActividades2.add(auxiliar);
    }
    modelo.put("lActividades", lActividades2);

    //Buscamos los datos del proceso
    proceso = this.mi.getBuscarProceso(proceso);
    modelo.put("proceso", proceso.getProceso());
    modelo.put("id_proceso", sId_proceso);
    
    //Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAcceso(cliente);
    modelo.put("lProcesos", lProcesos);

    return new ModelAndView("administrarActividades/ListarActividades", modelo);
  }
}