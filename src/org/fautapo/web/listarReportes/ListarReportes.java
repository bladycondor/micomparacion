package org.fautapo.web.listarReportes;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Abm;
import org.fautapo.domain.Usuarios;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @autor FAUTAPO
 * @fec_registro 2006-04-08
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-04-08
 */

public class ListarReportes implements Controller {
  private MiFacade mi;
  public void setMi(MiFacade mi) {
    this.mi = mi;
  }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    String _nombres = cliente.getNombres();

    int _id_usuario = cliente.getId_usuario();

    modelo.put("nombres", _nombres);


    String clave = request.getParameter("clave");
    
    Usuarios usuario = new Usuarios();
    usuario.setId_usuario(_id_usuario);
    usuario.setClave(clave);
    Clientes comprobar = this.mi.getComprobarUsuario(usuario);

    if(comprobar == null) {   
       return new ModelAndView("listarReportes/Entrada", modelo);
    }
    
      /*
    Docentes verificar = new Docentes();
    verificar.setId_docente(_id_docente);
    verificar.setClave(clave);
    
    if(!"1".equals(request.getParameter("bandera"))) {
      verificar = this.mi.getComprobarDocente(verificar);
      if(verificar == null) {
        String mensaje = "Clave incorrecta";
        modelo.put("mensaje",mensaje);
        return new ModelAndView("Error",modelo);
      }
    }
      */
    
    //Sacamos la asignacion del docente
    Abm abm = new Abm();

    List listaReportes = this.mi.getListarConsultas();

    modelo.put("listaReportes", listaReportes);
    return new ModelAndView("listarReportes/ListarReportes", modelo);
  }
}