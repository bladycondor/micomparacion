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
import org.fautapo.domain.Abm;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-18
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-18
*/

public class ConfirmarProcesoKardex implements Controller {

   private MiFacade mi;
   public void setMi(MiFacade mi) { this.mi = mi; }

   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
     Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    //
    String sId_tipo_proceso   = request.getParameter("id_tipo_proceso");
    String sId_proceso        = request.getParameter("id_proceso");
    String sId_form           = request.getParameter("id_form");
    String sProceso           = request.getParameter("proceso");
    String sForm              = request.getParameter("form");
    
    
    
    //Guardar Nuevo    
    String sAccion           = request.getParameter("accion");
    String sAccion1          = request.getParameter("accion1");
    
    
    Actividades datosActividad = new Actividades();
    
    if ("Adicionar".equals(sAccion)) {
      if (("".equals(request.getParameter("id_tipo_proceso"))) || ("".equals(request.getParameter("proceso")))||
         ("".equals(request.getParameter("form"))) ) {
	 return new ModelAndView("Error", "mensaje", "Faltan introducir datos");
      }
      modelo.put("id_tipo_proceso", sId_tipo_proceso);
      modelo.put("proceso", sProceso);
      modelo.put("form", sForm);
      modelo.put("accion", sAccion);

    }
    
    if ("Modificar".equals(sAccion)) { 
      if (("".equals(request.getParameter("id_proceso")))||("".equals(request.getParameter("id_form"))) || ("".equals(request.getParameter("proceso")))||
         ("".equals(request.getParameter("form")))) {
	 return new ModelAndView("Error", "mensaje", "Faltan introducir datos");
      }
      modelo.put("id_tipo_proceso", sId_tipo_proceso);
      modelo.put("proceso", sProceso);
      modelo.put("form", sForm);
      modelo.put("accion", sAccion);
      
    } 
    
    if ("Eliminar".equals(sAccion)) {
      //Buscamos el proceso
      Actividades datosProceso = new Actividades();
      datosProceso.setId_proceso(Integer.parseInt(sId_proceso));
      datosProceso = this.mi.getBuscarProceso(datosProceso);
      modelo.put("proceso", datosProceso.getProceso());	
      modelo.put("datosProceso", datosProceso);
      //Buscamos el formulario
      Campos datosForm = new Campos();
      datosForm.setId_form(Integer.parseInt(sId_proceso));
      datosForm = this.mi.getBuscarFormulario(datosForm);
      modelo.put("form",datosForm.getForm());
      modelo.put("datosForm",datosForm);
      
    }
    
    modelo.put("id_tipo_proceso", sId_tipo_proceso);
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_form", sId_form);
    modelo.put("accion", sAccion);
    modelo.put("accion1", sAccion1);
    return new ModelAndView("administrarKardex/administrarProcesoKardex/ConfirmarProcesoKardex", modelo);
      
   }
}