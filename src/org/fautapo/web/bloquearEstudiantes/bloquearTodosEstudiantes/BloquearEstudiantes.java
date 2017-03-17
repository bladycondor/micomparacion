package org.fautapo.web.bloquearEstudiantes.bloquearTodosEstudiantes;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fautapo.domain.Clientes;
import org.fautapo.domain.Accesos;
import org.fautapo.domain.Estudiantes;

import org.fautapo.domain.logic.MiFacade;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2006-07-17
 */

public class BloquearEstudiantes implements Controller {

  private MiFacade mi;
  public void setMi(MiFacade mi) { this.mi = mi; }

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map modelo = new HashMap();
    
    Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
    if (cliente==null) return new ModelAndView("Aviso", "mensaje", "Su sesión ha terminado. Vuelva a la página inicial e ingrese de nuevo.");

    //Si dio volver recuperamos los datos
    String sAccion = request.getParameter("accion");
    int iResultado=0; 
    // Comprobamos si realmente quiere bloquear
    if ("Bloquear".equals(sAccion)) {
      Estudiantes bloquear = new Estudiantes();
      bloquear.setUlt_usuario(cliente.getId_usuario());
      iResultado = this.mi.setBloquearEstudiantesTodos(bloquear);
    }
     
    modelo.put("resultado", Integer.toString(iResultado));
    
    return new ModelAndView("bloquearEstudiantes/bloquearTodosEstudiantes/BloquearEstudiantes", modelo);
  }
}