package org.fautapo.web.administrarCampos;

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
 * @fec_modificacion 2006-03-20
*/

public class ListarCampos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
  Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sId_form = request.getParameter("id_form");
    
    if ((sId_form != null) && (!"".equals(sId_form))) {
      //Buscamos los valores del formulario
      Campos datosFormulario = new Campos();
      datosFormulario.setId_form(Integer.parseInt(sId_form));
      datosFormulario = (Campos) this.mi.getBuscarFormulario(datosFormulario);
      modelo.put("datosFormulario", datosFormulario);

      //Listamos los campos de un formulario
      Campos auxiliar = new Campos();
      auxiliar.setId_form(Integer.parseInt(sId_form));
      List lCampos = this.mi.getListarCampos(auxiliar);
      modelo.put("lCampos", lCampos);
    }

    modelo.put("id_form", sId_form);
    return new ModelAndView("administrarCampos/ListarCampos", modelo);
  }
}