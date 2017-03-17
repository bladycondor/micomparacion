package org.fautapo.web.administrarMaterias;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Universidades;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarDepartamentos implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");
    
    String sId_departamento = request.getParameter("id_departamento");
    String sSigla = request.getParameter("sigla");
    String sMateria = request.getParameter("materia");
    
    if ((!"".equals(sId_departamento)) && (!"".equals(sSigla)) && (!"".equals(sMateria))) {
      // Comprobamos es quien debe, de acuerdo a su clave
      Usuarios datosUsuario = new Usuarios();
      datosUsuario.setId_usuario(cliente.getId_usuario());
      datosUsuario.setClave(request.getParameter("clave"+request.getParameter("hora")));
      if (null == this.mi.getComprobarUsuario(datosUsuario)) {
        modelo.put("mensaje", "Clave incorrecta, por favor intente nuevamente.");
        return new ModelAndView("Error", modelo);
      }
    }
     
    modelo.put("cliente", cliente);
    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));

    return new ModelAndView("administrarMaterias/ListarDepartamentos", modelo);
  }
}