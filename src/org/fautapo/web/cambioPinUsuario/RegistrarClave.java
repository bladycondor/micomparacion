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

public class RegistrarClave implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
  
    modelo.put("nombres", cliente.getNombres());
     
    String sBoton = request.getParameter("boton");
    String sNuevaClave = request.getParameter("nueva_clave");
    String sConfirmacionClave = request.getParameter("confirmacion_clave");
    
    if ("Aceptar".equals(sBoton)) {
      if (!sNuevaClave.equals(sConfirmacionClave)) {
        return new ModelAndView("Error", "mensaje", "La confirmación de la clave no coincide");
      }
      if (((sNuevaClave.trim()).length() >= 6) && ((sNuevaClave.trim()).length() <= 10)) {
        Usuarios usuario = new Usuarios();
        usuario.setId_usuario(cliente.getId_usuario());
        usuario.setClave(sNuevaClave);
        int iResultado = this.mi.setRegistrarNuevaClave(usuario);
        if (iResultado == 0) {
          return new ModelAndView("Error", "mensaje", "La nueva clave no se registr&oacute;");
        }
        else {
          return new ModelAndView("Aviso", "mensaje", "La clave fu&eacute; registrada correctamente");
        }
      }
      else {
        return new ModelAndView("Error", "mensaje", "La nueva clave debe tener un m&iacute;nimo de 6 caracteres y un m&aacute;ximo de 10");
      }
    }
    return new ModelAndView("cambioPinUsuario/RegistrarClave", modelo);
  }
}