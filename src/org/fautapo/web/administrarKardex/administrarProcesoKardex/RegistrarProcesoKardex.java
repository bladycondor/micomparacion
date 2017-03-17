package org.fautapo.web.administrarKardex.administrarProcesoKardex;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.Abm;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-18
*/

public class RegistrarProcesoKardex implements Controller {

   private MiFacade mi;
   public void setMi(MiFacade mi) { this.mi = mi; }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
     Map modelo = new HashMap();
     
     Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

     //
     String sId_tipo_proceso   = request.getParameter("id_tipo_proceso");
     String sProceso           = request.getParameter("proceso");
     String sForm              = request.getParameter("form");
     String sId_proceso        = request.getParameter("id_proceso");
     String sId_form           = request.getParameter("id_form");
     
     
     //Guardar    
     String sAccion            = request.getParameter("accion");
     
     
     if (("Adicionar".equals(sAccion))) {
       if (("".equals(sId_tipo_proceso)) || ("".equals(sProceso))|| ("".equals(sForm))) {
         return new ModelAndView("Aviso","mensaje","Faltan Introducir Datos");
       }
       Actividades datosActividad = new Actividades();
       if ((sId_tipo_proceso != null) && (!"".equals(sId_tipo_proceso))) {
         datosActividad.setId_tipo_proceso(Integer.parseInt(sId_tipo_proceso));
       }
       datosActividad.setId_tipo_proceso(Integer.parseInt(sId_tipo_proceso));
       datosActividad.setProceso(sProceso);
       datosActividad.setForm(sForm);
       //datosActividad.setActividad(sActividad);
       datosActividad.setUlt_usuario(cliente.getId_usuario());
       
       int iResultado = this.mi.setRegistrarProcesoKardex(datosActividad);
       if (iResultado == 0) {
         return new ModelAndView("Error","mensaje","Los datos no fueron registrados");
       }
     }
     
     if (("Modificar".equals(sAccion))) {
       if (("".equals(sId_proceso)) ||("".equals(sId_form))||("".equals(sProceso))|| ("".equals(sForm))) {
         return new ModelAndView("Aviso","mensaje","Faltan Introducir Datos");
       }
       Actividades datosActividad = new Actividades();
       if ((sId_proceso != null) && (sId_form != null)) {
         datosActividad.setId_proceso(Integer.parseInt(sId_proceso));
	 datosActividad.setId_form(Integer.parseInt(sId_form));
	
       }
       datosActividad.setProceso(sProceso);
       datosActividad.setForm(sForm);
       datosActividad.setUlt_usuario(cliente.getId_usuario());
       int iResultado = this.mi.setModificarProcesoKardex(datosActividad);
       if (iResultado == 0) {
         return new ModelAndView("Error","mensaje","Los datos no fueron registrados");
       }
       
     }
    
     if ("Eliminar".equals(sAccion)) {
       if ((sId_proceso != null) && (sId_form != null)) {
         Actividades eliminar = new Actividades();
         eliminar.setId_proceso(Integer.parseInt(sId_proceso));
	 eliminar.setId_form(Integer.parseInt(sId_form));
	 eliminar.setUlt_usuario(cliente.getId_usuario());
         int iValor = this.mi.setEliminarProcesoKardex(eliminar);
	 //Si el id_dominio es dominio_padre
	 if (iValor == 0) {
           String sMensaje = "No se pudo eliminar el Kardex :  "+"'"+ sProceso +"'. "+ " El formulario del Kardex :  "+"'"+ sForm +"'. " ;
           return new ModelAndView("Error", "mensaje", sMensaje);
	 }
       }
     }
     
     //Listamos los Procesos Kardex
     List lProcesosKardexs = this.mi.getListarProcesosKardexs();
     modelo.put("lProcesosKardexs",lProcesosKardexs);
     modelo.put("id_tipo_proceso",sId_tipo_proceso);

     return new ModelAndView("administrarKardex/administrarProcesoKardex/ListarProcesosKardexs", modelo);
   }
}