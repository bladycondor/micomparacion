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

public class NuevoProcesoKardex implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Campos auxiliar = new Campos();
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
     
     //
    String sAccion            = request.getParameter("accion"); 
    String sSw                = request.getParameter("sw"); 
    String sId_tipo_proceso   = request.getParameter("id_tipo_proceso");
    String sId_proceso        = request.getParameter("id_proceso");
    String sId_form           = request.getParameter("id_form");
    String sId_actividad      = request.getParameter("id_actividad");
    String sProceso           = request.getParameter("proceso");
    String sForm              = request.getParameter("form");
    System.out.println("El ID PROCESO -->"+ sId_proceso);
    System.out.println("El ID FORM -->"+ sId_form);
    System.out.println("LA ACCION -->"+ sAccion);
    //String sActividad         = request.getParameter("actividad");
    
    
    if ((sId_proceso != null) && (sId_form != null)) {
      if (("Modificar".equals(sAccion))) {
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
    }
    
    
    modelo.put("id_tipo_proceso", sId_tipo_proceso);
    modelo.put("id_proceso", sId_proceso);
    modelo.put("id_form", sId_form);
    modelo.put("id_actividad", sId_actividad);
    modelo.put("proceso", sProceso);
    //modelo.put("actividad", sActividad);
    modelo.put("form", sForm);
    modelo.put("accion", sAccion);
    
    
    return new ModelAndView("administrarKardex/administrarProcesoKardex/NuevoProcesoKardex", modelo);
      
   }
}