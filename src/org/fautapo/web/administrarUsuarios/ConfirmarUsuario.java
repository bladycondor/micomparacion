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
 * @fec_registro 2006-03-21
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-21
*/

public class ConfirmarUsuario implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    Usuarios datosUsuario = new Usuarios();
    Personas datosPersona = new Personas();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");

    String sAccion      = request.getParameter("accion");
    String sId_usuario    = request.getParameter("id_usuario");
    String sApodo         = request.getParameter("apodo");
    String sClave         = request.getParameter("clave");
    String sRecordatorio  = request.getParameter("recordatorio");
    String sId_persona  = request.getParameter("id_persona");

    if ((!"".equals(sId_usuario)) && (sId_usuario != null)) {
      //Buscamos los valores del Usuario
      datosUsuario = new Usuarios();
      datosUsuario.setId_usuario(Integer.parseInt(sId_usuario));
      datosUsuario = this.mi.getBuscarUsuario(datosUsuario);
      modelo.put("datosUsuario", datosUsuario);

      modelo.put("id_persona", Integer.toString(datosUsuario.getId_persona()));
      modelo.put("id_usuario", Integer.toString(datosUsuario.getId_usuario()));
    }
    
    if ((!"".equals(sId_persona)) && (sId_persona != null)) {
      //Buscamos los valores del formulario
      datosPersona.setId_persona(Integer.parseInt(sId_persona));
      datosPersona = this.mi.getPrsBuscarPersona(datosPersona);
      modelo.put("datosPersona", datosPersona);
      modelo.put("id_persona", Integer.toString(datosPersona.getId_persona()));
      datosUsuario.setNombres(datosPersona.getNombres()+" "+datosPersona.getPaterno()+" "+datosPersona.getMaterno());
    }

    if (("Adicionar".equals(sAccion)) || ("Modificar".equals(sAccion))) {
      if (("".equals(sId_persona)) || ("".equals(sClave)) || ("".equals(sApodo))) {
         return new ModelAndView("Error","mensaje","Faltan introducir datos");
      }
      datosUsuario.setClave_normal(sClave);
      datosUsuario.setApodo_normal(sApodo);
      datosUsuario.setRecordatorio(sRecordatorio);
      modelo.put("datosUsuario", datosUsuario);
    }

    if ("Eliminar".equals(sAccion)) {
      //Buscamos los valores del Usuario
      datosUsuario = new Usuarios();
      datosUsuario.setId_usuario(Integer.parseInt(sId_usuario));
      datosUsuario = this.mi.getBuscarUsuario(datosUsuario);
      modelo.put("datosUsuario", datosUsuario);
      modelo.put("id_usuario", Integer.toString(datosUsuario.getId_usuario()));

      //Buscamos los valores del formulario
      datosPersona.setId_persona(Integer.parseInt(sId_persona));
      datosPersona = this.mi.getPrsBuscarPersona(datosPersona);
      modelo.put("datosPersona", datosPersona);
      modelo.put("id_persona", Integer.toString(datosPersona.getId_persona()));
    }

    modelo.put("accion", sAccion);
    return new ModelAndView("administrarUsuarios/ConfirmarUsuario", modelo);
  }
}