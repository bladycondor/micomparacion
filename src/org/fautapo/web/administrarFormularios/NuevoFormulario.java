package org.fautapo.web.administrarFormularios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Campos;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-20
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-21
*/

public class NuevoFormulario implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
  Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String sAccion   = request.getParameter("accion");
    String sId_form  = request.getParameter("id_form");

    //Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAcceso(cliente);
    modelo.put("lProcesos", lProcesos);

    if ((!"".equals(sId_form)) && (sId_form != null)) {
      //Buscamos los valores del formulario
      Campos datosFormulario = new Campos();
      datosFormulario.setId_form(Integer.parseInt(sId_form));
      datosFormulario = (Campos) this.mi.getBuscarFormulario(datosFormulario);
      modelo.put("datosFormulario", datosFormulario);
    }
    modelo.put("id_form", sId_form);
    modelo.put("accion", sAccion);
    return new ModelAndView("administrarFormularios/NuevoFormulario", modelo);
  }
}