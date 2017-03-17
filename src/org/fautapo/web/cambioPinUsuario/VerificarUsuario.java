package org.fautapo.web.cambioPinUsuario;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-20
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-20
*/

public class VerificarUsuario implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    modelo.put("nombres", cliente.getNombres());
    
    String sBoton = request.getParameter("boton");
    String sClave = request.getParameter("clave"+ request.getParameter("hora"));
    
    if ("Buscar".equals(sBoton)) {
      Usuarios registrar = new Usuarios();
      registrar.setId_usuario(cliente.getId_usuario());
      registrar.setClave(sClave);
      int iResultado = this.mi.getVerificarUsuario(registrar);
      if (iResultado  == 0) {
        return new ModelAndView("Error", "mensaje", "Clave incorrecta");
      }
      else {
        return new ModelAndView("cambioPinUsuario/RecomendacionesClave", modelo);
      }
    }
    return new ModelAndView("cambioPinUsuario/VerificarUsuario", modelo);
  }
}