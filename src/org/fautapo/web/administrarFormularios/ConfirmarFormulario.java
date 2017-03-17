package org.fautapo.web.administrarFormularios;

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
 * @fec_registro 2006-03-21
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-21
*/

public class ConfirmarFormulario implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Campos datosFormulario = new Campos();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sAccion      = request.getParameter("accion");
    String sId_form     = request.getParameter("id_form");
    String sId_proceso  = request.getParameter("id_proceso");
    String sForm        = request.getParameter("form");

    if ((!"".equals(sId_form)) && (sId_form != null)) {
      //Buscamos los valores del formulario
      datosFormulario = new Campos();
      datosFormulario.setId_form(Integer.parseInt(sId_form));
      datosFormulario = (Campos) this.mi.getBuscarFormulario(datosFormulario);
      modelo.put("datosFormulario", datosFormulario);

      modelo.put("id_proceso", Integer.toString(datosFormulario.getId_proceso()));
      modelo.put("id_form", Integer.toString(datosFormulario.getId_form()));
    }
    
    if ((!"".equals(sId_proceso)) && (sId_proceso != null)) {
      //Buscamos los valores del formulario
      Actividades datosProceso = new Actividades();
      datosProceso.setId_proceso(Integer.parseInt(sId_proceso));
      datosProceso = (Actividades) this.mi.getBuscarProceso(datosProceso);
      modelo.put("datosProceso", datosProceso);
      modelo.put("id_proceso", Integer.toString(datosProceso.getId_proceso()));
    }

    if (("Adicionar".equals(sAccion)) || ("Modificar".equals(sAccion))) {
      if (("".equals(sId_proceso)) || ("".equals(sForm))) {
         return new ModelAndView("Error","mensaje","Faltan introducir datos");
      }
      datosFormulario.setForm(sForm);
      modelo.put("datosFormulario", datosFormulario);
    }

    if ("Eliminar".equals(sAccion)) {
      //Buscamos los valores del formulario
      datosFormulario = new Campos();
      datosFormulario.setId_form(Integer.parseInt(sId_form));
      datosFormulario = (Campos) this.mi.getBuscarFormulario(datosFormulario);
      modelo.put("datosFormulario", datosFormulario);
      modelo.put("id_form", Integer.toString(datosFormulario.getId_form()));

      //Buscamos los valores del formulario
      Actividades datosProceso = new Actividades();
      datosProceso.setId_proceso(datosFormulario.getId_proceso());
      datosProceso = (Actividades) this.mi.getBuscarProceso(datosProceso);
      modelo.put("datosProceso", datosProceso);
      modelo.put("id_proceso", Integer.toString(datosProceso.getId_proceso()));
    }

    modelo.put("accion", sAccion);
    return new ModelAndView("administrarFormularios/ConfirmarFormulario", modelo);
  }
}