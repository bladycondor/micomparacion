package org.fautapo.web.administrarUsuarios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Personas;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-20
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-21
*/

public class NuevoUsuario implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
  Map modelo = new HashMap();
     
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String sAccion   = request.getParameter("accion");
    String sId_usuario  = request.getParameter("id_usuario");

    /*//Listamos los procesos según el acceso del usuario
    List lProcesos = this.mi.getListarProcesosAcceso(cliente);
    modelo.put("lProcesos", lProcesos);
    */

    if ((!"".equals(sId_usuario)) && (sId_usuario != null)) {
      //Buscamos los valores del Usuario
      Usuarios datosUsuario = new Usuarios();
      datosUsuario.setId_usuario(Integer.parseInt(sId_usuario));
      datosUsuario = this.mi.getBuscarUsuario(datosUsuario);
      modelo.put("persona", datosUsuario);
    }
    modelo.put("id_usuario", sId_usuario);
    modelo.put("accion", sAccion);
    
    return new ModelAndView("administrarUsuarios/NuevoUsuario", modelo);
  }
}