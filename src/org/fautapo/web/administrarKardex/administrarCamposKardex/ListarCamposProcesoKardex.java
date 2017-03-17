package org.fautapo.web.administrarKardex.administrarCamposKardex;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Actividades;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-20
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-20
*/

public class ListarCamposProcesoKardex implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
  Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_form = request.getParameter("id_form");
    String sId_proceso = request.getParameter("id_proceso");
    //String sId_actividad = request.getParameter("id_actividad");
    
    if ((sId_form != null) && (!"".equals(sId_form))) {
      //Listamos los campos de un formulario
      Campos auxiliar = new Campos();
      auxiliar.setId_form(Integer.parseInt(sId_form));
      List lCampos = this.mi.getListarCampos(auxiliar);
      modelo.put("lCampos", lCampos);
    }
    
    //Buscamos el proceso 
    /*if(!"".equals(sId_actividad) && (sId_actividad != null)){
      //Buscamos la actividad
      Actividades datosActividad = new Actividades();
      datosActividad.setId_actividad(Integer.parseInt(sId_actividad));
      datosActividad = this.mi.getBuscarActividad(datosActividad);
      modelo.put("actividad", datosActividad.getActividad());
      modelo.put("datosActividad", datosActividad);
    } */ 
    //Buscamos el proceso 
    if(!"".equals(sId_proceso) && (sId_proceso != null)){      
      //Buscamos los datos del proceso
      Actividades datosProceso = new Actividades();
      datosProceso.setId_proceso(Integer.parseInt(sId_proceso));
      datosProceso = this.mi.getBuscarProceso(datosProceso);
      modelo.put("datosProceso", datosProceso);
      modelo.put("proceso", datosProceso.getProceso());
    }
    
    //Buscamos los valores del formulario
    Campos datosFormulario = new Campos();
    datosFormulario.setId_form(Integer.parseInt(sId_form));
    datosFormulario = (Campos) this.mi.getBuscarFormulario(datosFormulario);
    modelo.put("datosFormulario", datosFormulario);
    

    modelo.put("id_form", sId_form);
    modelo.put("id_proceso", sId_proceso);
    //modelo.put("id_actividad", sId_actividad);
    
    return new ModelAndView("administrarKardex/administrarCamposKardex/ListarCamposProcesoKardex", modelo);
  }
}