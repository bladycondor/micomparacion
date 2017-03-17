package org.fautapo.web.administrarUsuarios;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-03-21
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-03-21
*/

public class RegistrarUsuario implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
   
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    
    Usuarios datosUsuario = new Usuarios();
    String sAccion        = request.getParameter("accion");
    String sId_usuario       = request.getParameter("id_usuario");
    String sId_persona    = request.getParameter("id_persona");    
    String sClave         = request.getParameter("clave");
    String sApodo         = request.getParameter("apodo");
    String sRecordatorio  = request.getParameter("recordatorio");
    int iPagina = cliente.getInt(request, "pagina");

    if (("Adicionar".equals(sAccion)) || ("Modificar".equals(sAccion))) {
      if (("".equals(sId_persona)) || ("".equals(sApodo)) || ("".equals(sClave))) {
         return new ModelAndView("Error","mensaje","Faltan introducir datos");
      }
      
      if ((sId_usuario != null) && (!"".equals(sId_usuario))) {
        datosUsuario.setId_usuario(Integer.parseInt(sId_usuario));
      }
      else {
        datosUsuario.setId_usuario(0);
      }
      datosUsuario.setId_persona(Integer.parseInt(sId_persona));
      datosUsuario.setApodo(sApodo);
      datosUsuario.setClave(sClave);
      datosUsuario.setRecordatorio(sRecordatorio);
      datosUsuario.setUlt_usuario(cliente.getId_usuario());
      int iResultado = this.mi.setRegistrarUsuario(datosUsuario);
    }

    if ("Eliminar".equals(sAccion)) {
      if ("".equals(sId_usuario)) {
        return new ModelAndView("Error","mensaje","Faltan introducir datos");
      }
      datosUsuario.setId_usuario(Integer.parseInt(sId_usuario));
      datosUsuario.setUlt_usuario(cliente.getId_usuario());
      int iResultado = this.mi.setEliminarUsuario(datosUsuario);
      if (iResultado == 0) {
        return new ModelAndView("Error","mensaje","El registro a eliminar tiene dependencias");
      }
    }
    
    //Listamos los Usuarios
    if (iPagina == 0)
      datosUsuario.setPagina(1);
    else
      datosUsuario.setPagina(iPagina);
    modelo.put("pagina", Integer.toString(iPagina));
      
    List lUsuarios = this.mi.getListarUsuarios(datosUsuario);
    System.out.println("EL TAMANIO DE USUARIOS -->" +Integer.toString(lUsuarios.size()));
    modelo.put("lUsuarios", lUsuarios);
    modelo.put("size", Integer.toString(lUsuarios.size()));
    modelo.put("direccion", "listarUsuarios.fautapo");
    
    return new ModelAndView("administrarUsuarios/ListarUsuarios", modelo);
  }
}