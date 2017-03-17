package org.fautapo.web.verPlanEstudios;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Usuarios;
import org.fautapo.domain.Facultades;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-01-13
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class ListarPlanes implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if(cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    // Comprobamos si es quien debe ingresar al módulo, de acuerdo a su clave
    Usuarios usuario = new Usuarios();
    usuario.setId_usuario(cliente.getId_usuario());
    usuario.setClave(request.getParameter("clave"+request.getParameter("hora")));
    //if (null == this.mi.getComprobarUsuario(usuario)) { return new ModelAndView("verPlanEstudios/Entrada", "cliente", cliente); }
    Map modelo = new HashMap();
    modelo.put("cliente", cliente);
    Facultades facultad = new Facultades();
    facultad.setId_universidad(cliente.getId_universidad());
    modelo.put("lFacultades", this.mi.getUnvListarFacultades(facultad));

    modelo.put("acceso", (Accesos) request.getSession().getAttribute("__sess_acceso"));

    return new ModelAndView("verPlanEstudios/ListarPlanes", modelo);
  }
}