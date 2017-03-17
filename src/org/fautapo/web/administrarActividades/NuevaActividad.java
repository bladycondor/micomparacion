package org.fautapo.web.administrarActividades;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-15
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-16
*/

public class NuevaActividad implements Controller {

   private MiFacade mi;
   public void setMi(MiFacade mi) { this.mi = mi; }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
     Map modelo = new HashMap();
     int iId_tipo_actuacionx = 0;
      
     Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

     String sAccion = request.getParameter("accion");
     String sId_actividad = request.getParameter("id_actividad");
     String sActividad = request.getParameter("actividad");
     String sId_proceso = request.getParameter("id_proceso");
     String sId_ubicacion_organica = request.getParameter("id_ubicacion_organica");
     String sId_rol = request.getParameter("id_rol");
     String sDuracion = request.getParameter("duracion");
     String sOrden = request.getParameter("orden");
     String sUbicacion_organica = request.getParameter("ubicacion_organica");
     String sId_tipo_actuacion = request.getParameter("id_tipo_actuacion");
     String sId_actividad_modificar = request.getParameter("id_actividad_modificar");
     String sBanderita = request.getParameter("banderita");
     String sAlerta = request.getParameter("alerta");
     String sPuente = request.getParameter("puente");
     String sRuta = request.getParameter("ruta");
     String sFin_flujo = request.getParameter("fin_flujo");
     String sId_tipo_duracion = request.getParameter("id_tipo_duracion");

     modelo.put("actividad", sActividad);
     modelo.put("id_rol", sId_rol);
     modelo.put("duracion", sDuracion);
     modelo.put("orden", sOrden);
     modelo.put("id_ubicacion_organica", sId_ubicacion_organica);
     modelo.put("id_tipo_actuacion", sId_tipo_actuacion);
     modelo.put("id_actividad_modificar", sId_actividad_modificar);
     modelo.put("alerta", sAlerta);
     modelo.put("puente", sPuente);
     modelo.put("ruta", sRuta);
     modelo.put("fin_flujo", sFin_flujo);
     modelo.put("id_tipo_duracion", sId_tipo_duracion);
     
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
 	 sCadena = sAlertas[i] + ":" + sCadena;
       }
       modelo.put("lTiposAlertas", lAlertas);
       modelo.put("cadena", sCadena);
     }
     // Fin Recuperando los tipos de alertas seleccionados
     //Recuperando BIFURCACION
     List lActuacionesOrden1=new ArrayList();
     if ("3".equals(request.getParameter("id_tipo_actuacion"))) {
       //String cadenas;
       String sId_actividad_s[] = request.getParameterValues("id_actividad");
       String sOrden_actividad_s[] = request.getParameterValues("orden_actividad");
       String sCadenat = "";
       if (sId_actividad_s != null) {
         for (int i = 0; i< sId_actividad_s.length; i++) {
           if (!"".equals(sId_actividad_s[i])) {
             int iId_actividad_a = Integer.parseInt(sId_actividad_s[i]);
       	     //Buscamos el orden del id_actividad
	     Actividades actividad = new Actividades();
             actividad.setId_actividad(iId_actividad_a);
             actividad = (Actividades) this.mi.getBuscarActividad(actividad);
	     String sOrden_actividad_a = Integer.toString(actividad.getOrden());
	     String sActuacion_b = request.getParameter("actuacion_"+Integer.toString(iId_actividad_a));
	     sCadenat = sCadenat + sOrden_actividad_a + ":" + sActuacion_b + "###";
	   }
         }
         String sActuacion_a_meter = sCadenat;
         String sValorCadena = sCadenat;
         String sAuxiliar[] = sValorCadena.split("###");
         modelo.put("tamanio_act", Integer.toString(sAuxiliar.length));
         for (int ac=0; ac < sAuxiliar.length; ac++) {
           String sActuacion_c[] = sAuxiliar[ac].split(":");
    	   for (int j=0; j < sActuacion_c.length; j++) {
	     if (!"".equals(sActuacion_c[j])) {
               Actividades almacenar = new Actividades();
	       almacenar.setOrden(Integer.parseInt(sActuacion_c[j]));
	       almacenar.setActuacion(sActuacion_c[j+1]);
	       //SACANDO EL ID ACTIVIDAD Y ACTIVIDAD
	       int iOrden_s = Integer.parseInt(sActuacion_c[j]);
  	       Actividades actividad2 = new Actividades();
               actividad2.setId_proceso(Integer.parseInt(sId_proceso));
               actividad2.setOrden(iOrden_s);
               actividad2 = (Actividades) this.mi.getBuscarActividadOrden(actividad2); //Buscamos el  id_actividad y actividad por Orden
	       almacenar.setId_actividad(actividad2.getId_actividad());
	       almacenar.setActividad(actividad2.getActividad());
	       lActuacionesOrden1.add(almacenar);
	       j++;
	     }  
           }
         }
         modelo.put("lActuacionesOrden", lActuacionesOrden1);
       }
     }
     // Fin Recuperando 

     //Lista Area
     List lUbicacionesOrganicas = this.mi.getListarUbicacionesOrganicas();
     modelo.put("lUbicacionesOrganicas", lUbicacionesOrganicas);

     // LISTA ROLES
     List lRoles = this.mi.getListarRoles();
     modelo.put("lRoles", lRoles);
    
     // LISTA TIPOS ACTUACIONES
     List lTiposActuaciones = this.mi.getListarTiposActuaciones();
     modelo.put("lTiposActuaciones", lTiposActuaciones);
     
     // LISTA TIPOS DURACIONES
     List lTiposDuraciones = this.mi.getListarTiposDuraciones();
     modelo.put("lTiposDuraciones", lTiposDuraciones);
     
     // LISTA TIPOS ALERTAS
     Actividades auxi = new Actividades();
     auxi.setId_actividad(0);
     List lTiposAlertas = this.mi.getListarTiposAlertasAct(auxi);
     modelo.put("listaTiposAlertas", lTiposAlertas);
     
     //Lista actividades
     Actividades proceso = new Actividades();
     proceso.setId_proceso(Integer.parseInt(request.getParameter("id_proceso")));
     List listaActividades = this.mi.getListarActividades(proceso);
     modelo.put("lActividades", listaActividades);
     
     //Buscamos los datos del proceso
     proceso = this.mi.getBuscarProceso(proceso);
     modelo.put("proceso", proceso.getProceso());
      
     if (sId_actividad_modificar != null) {
       if ("Modificar".equals(sAccion)) {
         // LISTA TIPOS ALERTAS
         auxi = new Actividades();
         auxi.setId_actividad(Integer.parseInt(sId_actividad_modificar));
         lTiposAlertas = this.mi.getListarTiposAlertasAct(auxi);
         modelo.put("lTiposAlertas", lTiposAlertas);
	 
	 //Recuperando los id_tipos de alertas seleccionados
         List lTiposAlertas2 = this.mi.getListarTiposAlertasAct(auxi);
         if (lTiposAlertas2.size() != 0) {
	   List lTiposAlertas3 = new ArrayList();  
           for (int i=0; i < lTiposAlertas2.size(); i++) {
	     Actividades tipoAlerta =(Actividades) lTiposAlertas2.get(i);
	     if (tipoAlerta.getId_actividad() != 0) {
	       tipoAlerta.setId_tipo_alerta(tipoAlerta.getId_tipo_alerta());
               tipoAlerta = this.mi.getBuscarTipoAlerta(tipoAlerta);
               lTiposAlertas3.add(tipoAlerta);
	     }
           }
           modelo.put("lTiposAlertas", lTiposAlertas3);
         }
         // Fin Recuperando los tipos de alertas seleccionados

 	 Actividades actividad = new Actividades();
	 actividad.setId_actividad(Integer.parseInt(request.getParameter("id_actividad_modificar")));
         actividad = (Actividades) this.mi.getBuscarActividad(actividad);// Saca un registro a ser modificado
	 if (request.getParameter("orden") == null) {
	   modelo.put("orden", Integer.toString(actividad.getOrden()));
	 }
	 else {
           modelo.put("orden", request.getParameter("orden"));
	 }
	 if (request.getParameter("id_tipo_actuacion") == null) {
	   iId_tipo_actuacionx = actividad.getId_tipo_actuacion();

	   if (iId_tipo_actuacionx == 2) {  //CUANDO ES GOTO
             Actividades actividadGoto = new Actividades();
             actividadGoto.setOrden(Integer.parseInt(actividad.getActuacion()));
             actividadGoto = this.mi.getBuscarActividadOrden(actividadGoto);
	     if (actividadGoto != null) {
               int iId_actividad_g = actividadGoto.getId_actividad();
	       modelo.put("id_actividad", Integer.toString(iId_actividad_g));
               modelo.put("actividadGoto", actividadGoto);
	     }
           }
	 }
 	 else {
           iId_tipo_actuacionx = Integer.parseInt(request.getParameter("id_tipo_actuacion"));
	 }
         modelo.put("actividad", actividad);

         List lActuacionesOrden = new ArrayList();
         if (iId_tipo_actuacionx == 3) {   //CUANDO ES FORK
           String sValor = actividad.getActuacion();
	  
	   int iAux = sValor.lastIndexOf('#');
	   String sSubcadena = sValor.substring(iAux+1, sValor.length());
	  
	   if (!"-1".equals(Integer.toString(iAux))) {
             String sAuxiliar[] = sValor.split("###");
             modelo.put("tamanio_act", Integer.toString(sAuxiliar.length));
             for (int ac=0; ac < sAuxiliar.length; ac++) {
               String sActuacion_c[] = sAuxiliar[ac].split(":");
  	       for (int j=0; j < sActuacion_c.length; j++) {
	         Actividades almacenar = new Actividades();
	         almacenar.setOrden(Integer.parseInt(sActuacion_c[j]));
	         almacenar.setActuacion(sActuacion_c[j+1]);
	         lActuacionesOrden.add(almacenar);
	         j++;
	       }
             }
             modelo.put("lActuacionesOrden",lActuacionesOrden);
	   }
         }
	
	 modelo.put("id_actividad_modificar", Integer.toString(actividad.getId_actividad()));
	 modelo.put("id_tipo_actuacion", Integer.toString(iId_tipo_actuacionx));
	 modelo.put("id_tipo_duracion", Integer.toString(actividad.getId_tipo_duracion()));
	 modelo.put("actuacion", actividad.getActuacion());
	 if (sAlerta == null) {
	   modelo.put("alerta", Boolean.toString(actividad.getAlerta()));
	 }
	 if (sPuente == null) {
	   modelo.put("puente", Boolean.toString(actividad.getPuente()));
	   modelo.put("ruta", actividad.getRuta());
	 }
	 if (sFin_flujo == null) {
	   modelo.put("fin_flujo", Boolean.toString(actividad.getFin_flujo()));
	 }
	 modelo.put("buscarActividad1", actividad);
	 if ((sActividad == null) || (sId_rol == null) || (sDuracion == null)) {
	   modelo.put("actividad", actividad.getActividad());
	   modelo.put("id_rol", Integer.toString(actividad.getId_rol()));
	   modelo.put("id_ubicacion_organica", Integer.toString(actividad.getId_ubicacion_organica()));
	   modelo.put("id_tipo_duracion", Integer.toString(actividad.getId_tipo_duracion()));
	   modelo.put("duracion", Integer.toString(actividad.getDuracion()));
         }
	 else {
	   modelo.put("actividad", request.getParameter("actividad"));
	   modelo.put("id_rol", request.getParameter("id_rol"));
	   modelo.put("id_tipo_duracion", request.getParameter("id_tipo_duracion"));
	   modelo.put("duracion", request.getParameter("duracion"));
	 }
       }
     }
    
     modelo.put("id_actividad_modificar", sId_actividad_modificar);
     modelo.put("id_proceso", sId_proceso);
     modelo.put("accion",sAccion);
    
     return new ModelAndView("administrarActividades/NuevaActividad", modelo);
   }
}