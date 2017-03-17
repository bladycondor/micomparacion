package org.fautapo.web.administrarFormularios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Campos;
import org.fautapo.domain.Dominios;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-21
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-21
*/

public class RegistrarFormulario implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
   
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sAccion        = request.getParameter("accion");
    String sId_form       = request.getParameter("id_form");
    String sId_proceso    = request.getParameter("id_proceso");    
    String sForm          = request.getParameter("form");

    if (("Adicionar".equals(sAccion)) || ("Modificar".equals(sAccion))) {
      if (("".equals(sId_proceso)) || ("".equals(sForm))) {
         return new ModelAndView("Error","mensaje","Faltan introducir datos");
      }
      Campos datosFormulario = new Campos();
      if ((sId_form != null) && (!"".equals(sId_form))) {
        datosFormulario.setId_form(Integer.parseInt(sId_form));
      }
      else {
        datosFormulario.setId_form(0);
      }
      datosFormulario.setId_proceso(Integer.parseInt(sId_proceso));
      datosFormulario.setForm(sForm);
      datosFormulario.setUlt_usuario(cliente.getId_usuario());
      int iResultado = this.mi.setRegistrarFormulario(datosFormulario);
    }

    if ("Eliminar".equals(sAccion)) {
      if ("".equals(sId_form)) {
        return new ModelAndView("Error","mensaje","Faltan introducir datos");
      }
      Campos datosFormulario = new Campos();
      datosFormulario.setId_form(Integer.parseInt(sId_form));
      datosFormulario.setUlt_usuario(cliente.getId_usuario());
      int iResultado = this.mi.setEliminarFormulario(datosFormulario);
      if (iResultado == 0) {
        return new ModelAndView("Error","mensaje","El registro a eliminar tiene dependencias");
      }
    }
    
    //Listamos los formularios
    List lFormularios = this.mi.getListarFormulariosAcceso(cliente);
    modelo.put("lFormularios", lFormularios);
    
    return new ModelAndView("administrarFormularios/ListarFormularios", modelo);
  }
}