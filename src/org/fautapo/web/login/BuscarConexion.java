package org.fautapo.web.login;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Roles;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
 */

public class BuscarConexion implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    String sHora  = request.getParameter("hora");
    String sApodo = request.getParameter("apodo" + sHora);
    String sClave = request.getParameter("clave" + sHora);
    Usuarios usuario = new Usuarios();
    usuario.setApodo(sApodo);
    usuario.setClave(sClave);
    Clientes cliente = this.mi.getBuscarConexion(usuario);
 if (cliente.getId_rol() != 9){
   if (cliente == null) {
      return new ModelAndView("login/LoginEntrada", "mensaje", "No puedo encontrar al usuario");
    }
    if (cliente.getId_rol() == 1) { // es Administrativo
      Roles rol = new Roles();
      rol.setId_usuario(cliente.getId_usuario());
      cliente.setRoles(this.mi.getListarRolesCliente(rol));
      if (cliente.getRoles().size() == 0) {  // expiraron sus roles
        return new ModelAndView("login/LoginEntrada", "mensaje", "No puede ingresar, porque terminó su periodo de acceso al sistema");
      }
      Roles aux = (Roles) cliente.getRoles().get(0);
      cliente.setId_rol(aux.getId_rol());
      cliente.setRol(aux.getRol());
    }
    request.getSession().setAttribute("__sess_cliente", cliente); // Subimos 'cliente' a la sesión
 }
 else
     return new ModelAndView("login/LoginEntrada", "mensaje", "No puede ingresar, solo Acceso Academico - Administrativo y Docente");
    // Mostramos el menú correspondiente
if (cliente.getId_rol() != 9){	
    if (cliente.getRoles() != null) {
      if (cliente.getRoles().size() > 1) { // tiene más de 1 rol
        return new ModelAndView("Distro", "url", "/elegirRol.fautapo"); //Elegir un rol de los tantos
      }
      else {
        Roles rol = new Roles();
        rol.setId_usuario(cliente.getId_usuario());
        rol.setId_rol(cliente.getId_rol());
        rol = this.mi.getBuscarRolCliente(rol);
        cliente.setId_rol(rol.getId_rol());
        cliente.setRol(rol.getRol());
        cliente.setId_universidad(rol.getId_universidad());
        cliente.setId_facultad(rol.getId_facultad());
        cliente.setId_programa(rol.getId_programa());
        cliente.setId_departamento(rol.getId_departamento());
        cliente.setId_ubicacion_organica(rol.getId_ubicacion_organica());
        cliente.setId_almacen(rol.getId_almacen());
        cliente.setFiltro(rol.getFiltro());
        cliente.setPermiso(rol.getPermiso());
        cliente.setAlmacen("");
        //Sacamos el listado de almacenes
        cliente.setAlmacenes(this.mi.getListarAlmacenesCliente(rol));
        if (cliente.getAlmacenes().size() == 1) {
          Roles aux = (Roles) cliente.getAlmacenes().get(0);
          cliente.setId_almacen(aux.getId_almacen());
          cliente.setPermiso(aux.getPermiso());
          cliente.setAlmacen(aux.getAlmacen());
        }
      }
      
      // Este es para el MI si o si
      Accesos acceso = new Accesos();
      acceso.setAsignarAccesos(cliente, this.mi);
      request.getSession().setAttribute("__sess_acceso", acceso); // Subimos los 'accesos' a la sesión
      //Fin para el MI
      
      request.getSession().setAttribute("__sess_cliente", cliente); // Subimos 'cliente' a la sesión
      if (cliente.getAlmacenes().size() > 1) { // tiene más de 1 rol
        return new ModelAndView("Distro", "url", "/elegirAlmacen.fautapo"); //Elegir un almacen de los tantos
      }
    }
 }
 else
     return new ModelAndView("login/LoginEntrada", "mensaje", "No puede ingresar, solo Acceso Academico - Administrativo y Docente");
 
 // Mostramos el menú correspondiente
    return new ModelAndView("Distro", "url", ".' target='_top'");
  }

}