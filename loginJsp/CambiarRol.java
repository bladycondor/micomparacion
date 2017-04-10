package org.fautapo.web.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Roles;
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

public class CambiarRol implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();

    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String sId_rol = request.getParameter("id_rol");
    String sEncabezado = request.getParameter("encabezado");
    Roles rol = new Roles();
    rol.setId_rol(Integer.parseInt(sId_rol));
    rol.setId_usuario(cliente.getId_usuario());
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
    //Sacamos el listado de almacenes
    cliente.setAlmacenes(this.mi.getListarAlmacenesCliente(rol));
    if ("si".equals(sEncabezado)) {
      if (cliente.getAlmacenes().size() > 0) {
        Roles aux = (Roles) cliente.getAlmacenes().get(0);
        cliente.setId_almacen(aux.getId_almacen());
        cliente.setPermiso(aux.getPermiso());
        cliente.setAlmacen(aux.getAlmacen());
      }
      if (cliente.getAlmacenes().size() == 0) {
        cliente.setAlmacen("");
      }
    }
    
    // Este es para el MI si o si
    Accesos acceso = new Accesos();
    acceso.setAsignarAccesos(cliente, this.mi);
    request.getSession().setAttribute("__sess_acceso", acceso); // Subimos los 'accesos' a la sesión
    //Fin para el MI  

    request.getSession().setAttribute("__sess_cliente", cliente);
    if ((cliente.getAlmacenes().size() > 1) && (!"si".equals(sEncabezado))) { // tiene más de 1 rol
      return new ModelAndView("Distro", "url", "/elegirAlmacen.fautapo"); //Elegir un almacen de los tantos
    }
    return new ModelAndView("Distro", "url", ".' target='_top'");
  }
}